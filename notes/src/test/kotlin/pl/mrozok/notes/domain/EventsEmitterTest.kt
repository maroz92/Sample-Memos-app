package pl.mrozok.notes.domain

import com.nhaarman.mockitokotlin2.mock
import org.junit.Test
import pl.mrozok.notes.domain.model.Content
import pl.mrozok.notes.domain.model.Note
import pl.mrozok.notes.infrastructure.EventsEmitter
import java.util.*

class EventsEmitterTest {

    private val uuid = UUID.randomUUID().toString()

    @Test
    fun `emit note deleted event`() {
        val systemUnderTest = EventsEmitter()

        val tester = systemUnderTest.observable().test()
        systemUnderTest.noteDeleted(uuid)

        tester.assertValuesOnly(Event.NoteDeleted(uuid))
    }

    @Test
    fun `emit content updated event`() {
        val systemUnderTest = EventsEmitter()

        val tester = systemUnderTest.observable().test()
        val content = mock<Content>()
        systemUnderTest.contentUpdated(uuid, content)

        tester.assertValuesOnly(Event.NoteContentChanged(uuid, content))
    }

    @Test
    fun `emit note created event`() {
        val systemUnderTest = EventsEmitter()

        val tester = systemUnderTest.observable().test()
        val note: Note = mock()
        systemUnderTest.noteCreated(note)

        tester.assertValuesOnly(Event.NoteCreated(note))
    }

}
