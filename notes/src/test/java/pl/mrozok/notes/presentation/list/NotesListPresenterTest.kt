package pl.mrozok.notes.presentation.list

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import org.junit.Test
import pl.mrozok.notes.domain.Event
import pl.mrozok.notes.domain.NotesModule
import pl.mrozok.notes.domain.model.Note
import pl.mrozok.toolbox.presentation.reactivex.SchedulersHolder

class NotesListPresenterTest {

    private val ui = mock<NotesListUI>()

    private lateinit var systemUnderTest: NotesListPresenter
    private lateinit var notesModule: NotesModule.API

    @Test
    fun `should load notes`() {
        val notes = listOf<Note>(mock(), mock())
        `given notes module`()
            .`with notes`(notes)
        `given notes list presenter`(notesModule)
        systemUnderTest.loadNotes()

        verify(ui).showNotes(notes)
    }

    @Test
    fun `should reload data if note created`() {
        val eventEmitter = TestEventEmitter()
        `given notes module`()
            .`with event emitter`(eventEmitter)
        `given notes list presenter`(notesModule)
        systemUnderTest.setup()

        eventEmitter.emitNoteCreated()

        verify(ui).showNotes(any())
    }

    @Test
    fun `should reload data if note had changed`() {
        val eventEmitter = TestEventEmitter()
        `given notes module`()
            .`with event emitter`(eventEmitter)
        `given notes list presenter`(notesModule)
        systemUnderTest.setup()

        eventEmitter.emitNoteChanged()

        verify(ui).showNotes(any())
    }

    @Test
    fun `should reload data if note deleted`() {
        val eventEmitter = TestEventEmitter()
        `given notes module`()
            .`with event emitter`(eventEmitter)
        `given notes list presenter`(notesModule)
        systemUnderTest.setup()

        eventEmitter.emitNoteDeleted()

        verify(ui).showNotes(any())
    }

    private fun `given notes module`(): NotesModule.API {
        notesModule = mock()
        return notesModule
    }

    private fun NotesModule.API.`with notes`(notes: List<Note>) {
        whenever(allNotes()).thenReturn(notes)
    }

    private fun NotesModule.API.`with event emitter`(eventEmitter: TestEventEmitter) {
        whenever(eventsObservable()).thenReturn(eventEmitter.observable())
    }

    private fun `given notes list presenter`(notesModule: NotesModule.API) {
        systemUnderTest = NotesListPresenter(notesModule, SchedulersHolder.immediateSchedulersHolder())
        systemUnderTest.attachUI(ui)
    }

    class TestEventEmitter {

        private val eventsSubject = BehaviorSubject.create<Event>()

        fun observable(): Observable<Event> = eventsSubject
        fun emitNoteCreated() {
            eventsSubject.onNext(Event.NoteCreated(mock()))
        }

        fun emitNoteDeleted() {
            eventsSubject.onNext(Event.NoteDeleted(""))
        }

        fun emitNoteChanged() {
            eventsSubject.onNext(Event.NoteContentChanged("", mock()))
        }

    }

}
