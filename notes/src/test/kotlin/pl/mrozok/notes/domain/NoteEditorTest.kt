package pl.mrozok.notes.domain

import com.nhaarman.mockitokotlin2.*
import org.junit.Test
import pl.mrozok.notes.domain.NotesModule.Type
import pl.mrozok.notes.domain.model.Content
import pl.mrozok.notes.domain.model.Note
import pl.mrozok.notes.domain.model.Text
import pl.mrozok.notes.infrastructure.EventsEmitter
import pl.mrozok.notes.infrastructure.NotesRepository
import pl.mrozok.toolbox.presentation.IllegalOperationException
import java.util.*

class NoteEditorTest {

    private val uuid = UUID.randomUUID().toString()

    private lateinit var notesRepository: NotesRepository
    private lateinit var eventsEmitter: EventsEmitter

    private lateinit var systemUnderTest: NoteEditor

    @Test(expected = IllegalOperationException::class)
    fun `cannot update note with different type of content`() {
        `given events emitter`()
        `given notes repository`()
            .`with new list type note`(Note(uuid, mock()))
        `given note editor`()

        systemUnderTest.new(Type.LIST)
        val differentContent = Text("Any", "content")
        systemUnderTest.updateContent(differentContent)

        verify(eventsEmitter).errorOccurred(any())
    }

    @Test
    fun `editor should be initialized first`() {
        `given events emitter`()
        `given notes repository`()
        `given note editor`()

        systemUnderTest.updateContent(mock())

        verify(eventsEmitter).errorOccurred(any())
    }

    @Test
    fun `should update content of new note`() {
        val content = mock<Content>()
        val note = Note(uuid, content)
        `given events emitter`()
        `given notes repository`()
            .`with new list type note`(note)
        `given note editor`()

        systemUnderTest.new(Type.LIST)
        systemUnderTest.updateContent(content)

        inOrder(eventsEmitter).apply {
            verify(eventsEmitter).noteCreated(note)
            verify(eventsEmitter).contentUpdated(uuid, content)
        }
    }

    @Test
    fun `should update content of existing note`() {
        val content = mock<Content>()
        `given events emitter`()
        `given notes repository`()
            .`with existing note`(Note(uuid, content))
        `given note editor`()

        systemUnderTest.existing(uuid)
        systemUnderTest.updateContent(content)

        verify(eventsEmitter).contentUpdated(uuid, content)
    }

    @Test
    fun `should delete initialized note`() {
        `given events emitter`()
        `given notes repository`()
            .`with existing note`(Note(uuid, mock()))
        `given note editor`()

        systemUnderTest.existing(uuid)
        systemUnderTest.delete()

        verify(eventsEmitter).noteDeleted(uuid)
    }

    private fun `given events emitter`() {
        eventsEmitter = mock()
    }

    private fun `given notes repository`(): NotesRepository {
        notesRepository = mock()
        return notesRepository
    }

    private fun NotesRepository.`with new list type note`(note: Note) {
        whenever(new(Type.LIST)).thenReturn(note)
    }

    private fun NotesRepository.`with existing note`(note: Note) {
        whenever(getLatestVersion(uuid)).thenReturn(note)
    }

    private fun `given note editor`() {
        systemUnderTest = NoteEditor(eventsEmitter, notesRepository)
    }

}
