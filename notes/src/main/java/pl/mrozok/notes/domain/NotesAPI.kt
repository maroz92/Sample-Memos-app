package pl.mrozok.notes.domain

import io.reactivex.Observable
import pl.mrozok.notes.domain.model.Note
import pl.mrozok.notes.infrastructure.EventsEmitter
import pl.mrozok.notes.infrastructure.NotesRepository

internal class NotesAPI(
    private val notesRepository: NotesRepository,
    private val eventsEmitter: EventsEmitter,
    private val noteEditor: NoteEditor
) : NotesModule.API {

    override fun newNote(type: NotesModule.Type): NoteEditor =
        noteEditor.apply { new(type) }

    override fun existingNote(uuid: String): NoteEditor =
        noteEditor.apply { existing(uuid) }

    override fun allNotes(): List<Note> = notesRepository.getAll()

    override fun eventsObservable(): Observable<Event> = eventsEmitter.observable()

}
