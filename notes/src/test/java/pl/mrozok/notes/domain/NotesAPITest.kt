package pl.mrozok.notes.domain

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import pl.mrozok.notes.domain.NotesModule.Type
import pl.mrozok.notes.domain.model.Note
import pl.mrozok.notes.infrastructure.NotesRepository
import java.util.*

internal class NotesAPITest {

    private val uuid = UUID.randomUUID().toString()

    private lateinit var noteEditor: NoteEditor
    private lateinit var notesRepository: NotesRepository
    private lateinit var systemUnderTest: NotesModule.API

    @Test
    fun `setup for existing note`() {
        `given notes repository`()
        `given note editor`()
        `given notes module`()

        systemUnderTest.existingNote(uuid)

        verify(noteEditor).existing(uuid)
    }

    @Test
    fun `setup for new note`() {
        `given notes repository`()
        `given note editor`()
        `given notes module`()

        systemUnderTest.newNote(Type.TEXT)

        verify(noteEditor).new(Type.TEXT)
    }

    @Test
    fun `should return notes`() {
        val expectedNotes = listOf<Note>(mock(), mock(), mock())
        `given notes repository`()
            .`with existing notes`(expectedNotes)
        `given note editor`()
        `given notes module`()

        val notes = systemUnderTest.allNotes()
        assertThat(notes).isSameAs(expectedNotes)
    }

    private fun `given notes repository`(): NotesRepository {
        notesRepository = mock()
        return notesRepository
    }

    private fun NotesRepository.`with existing notes`(notes: List<Note>) {
        whenever(getAll()).thenReturn(notes)
    }

    private fun `given note editor`() {
        noteEditor = mock()
    }

    private fun `given notes module`() {
        systemUnderTest = NotesModule.create(NotesModule.Dependencies(notesRepository, mock(), noteEditor))
    }

}
