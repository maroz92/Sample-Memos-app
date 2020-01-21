package pl.mrozok.notes.domain

import io.reactivex.Observable
import pl.mrozok.notes.domain.model.Note
import pl.mrozok.notes.infrastructure.EventsEmitter
import pl.mrozok.notes.infrastructure.NotesRepository

class NotesModule {

    companion object {
        fun create(dependencies: Dependencies): API = dependencies.run {
            NotesAPI(notesRepository, eventsEmitter, noteEditor)
        }
    }

    interface API {
        fun newNote(type: Type): NoteEditor
        fun existingNote(uuid: String): NoteEditor
        fun allNotes(): List<Note>
        fun eventsObservable(): Observable<Event>
    }

    data class Dependencies(
        val notesRepository: NotesRepository,
        val eventsEmitter: EventsEmitter,
        val noteEditor: NoteEditor
    )

    enum class Type { TEXT, LIST }

}
