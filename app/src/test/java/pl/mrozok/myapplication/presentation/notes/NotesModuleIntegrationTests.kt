package pl.mrozok.myapplication.presentation.notes

import android.app.Application
import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.inOrder
import com.nhaarman.mockitokotlin2.mock
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import pl.mrozok.myapplication.infrastructure.AndroidNotesRepository
import pl.mrozok.myapplication.infrastructure.JsonSerializerProvider
import pl.mrozok.notes.domain.NoteEditor
import pl.mrozok.notes.domain.NotesModule
import pl.mrozok.notes.domain.NotesModule.Type
import pl.mrozok.notes.domain.model.Note
import pl.mrozok.notes.domain.model.Text
import pl.mrozok.notes.infrastructure.EventsEmitter
import pl.mrozok.notes.infrastructure.NotesRepository

@RunWith(RobolectricTestRunner::class)
class NotesModuleIntegrationTests {

    private lateinit var systemUnderTest: NotesModule.API
    private lateinit var notesRepository: NotesRepository
    private val eventsEmitter: EventsEmitter = mock()

    @Test
    fun `create note and fill with content`() {
        `given notes repository`()
        `given notes module`()

        val editor = systemUnderTest.newNote(Type.TEXT)
        val content = Text("Some title", "Lorem ipsum dolor sit amet")
        editor.updateContent(content)

        val uuid = editor.noteUuid()
        inOrder(eventsEmitter).apply {
            verify(eventsEmitter).noteCreated(Note(uuid, Text("", "")))
            verify(eventsEmitter).contentUpdated(uuid, content)
        }
        verifyNoteContentIsSaved(uuid, "Some title", "Lorem ipsum dolor sit amet")
        assertThat(systemUnderTest.allNotes()).hasSize(1)
    }

    @Test(expected = IllegalStateException::class)
    fun `note is deleted`() {
        `given notes repository`()
        `given notes module`()

        val editor = systemUnderTest.newNote(Type.LIST)
        editor.delete()

        val uuid = editor.noteUuid()
        inOrder(eventsEmitter).apply {
            verify(eventsEmitter).noteCreated(any())
            verify(eventsEmitter).noteDeleted(uuid)
        }

        assertThat(systemUnderTest.allNotes()).hasSize(0)
        notesRepository.getLatestVersion(uuid)
    }

    private fun `given notes module`() {
        systemUnderTest = NotesModule.create(
            NotesModule.Dependencies(
                notesRepository,
                eventsEmitter,
                NoteEditor(eventsEmitter, notesRepository)
            )
        )
    }

    private fun `given notes repository`() {
        val context = ApplicationProvider.getApplicationContext<Application>()
        val sharedProvider = context.getSharedPreferences("AcceptanceTests", Context.MODE_PRIVATE)
        notesRepository = AndroidNotesRepository(sharedProvider, JsonSerializerProvider.provide())
    }

    private fun verifyNoteContentIsSaved(
        noteUuid: String,
        expectedTitle: String,
        expectedContent: String
    ) {
        val note = notesRepository.getLatestVersion(noteUuid)
        assertThat(note.content).isExactlyInstanceOf(Text::class.java)
        val textContent = note.content as Text
        assertThat(textContent.title).isEqualTo(expectedTitle)
        assertThat(textContent.value).isEqualTo(expectedContent)
    }

}
