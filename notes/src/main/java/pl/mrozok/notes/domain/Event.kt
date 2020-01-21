package pl.mrozok.notes.domain

import pl.mrozok.notes.domain.model.Content
import pl.mrozok.notes.domain.model.Note

sealed class Event {
    class ErrorOccured : Event()
    data class NoteCreated(val note: Note) : Event()
    data class NoteDeleted(val uuid: String) : Event()
    data class NoteContentChanged(
        val uuid: String,
        val content: Content
    ) : Event()
}

