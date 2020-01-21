package pl.mrozok.notes.infrastructure

import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject
import pl.mrozok.notes.domain.Event
import pl.mrozok.notes.domain.model.Content
import pl.mrozok.notes.domain.model.Note

class EventsEmitter {

    private val eventsSubject: Subject<Event> = PublishSubject.create()

    fun noteDeleted(uuid: String) {
        eventsSubject.onNext(Event.NoteDeleted(uuid))
    }

    fun contentUpdated(
        uuid: String,
        content: Content
    ) {
        eventsSubject.onNext(Event.NoteContentChanged(uuid, content))
    }

    fun noteCreated(note: Note) {
        eventsSubject.onNext(Event.NoteCreated(note))
    }

    fun errorOccurred(message: String) {} // to implement

    fun observable(): Observable<Event> = eventsSubject

}
