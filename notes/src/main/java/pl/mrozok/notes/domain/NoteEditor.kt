package pl.mrozok.notes.domain

import pl.mrozok.notes.domain.model.Content
import pl.mrozok.notes.domain.model.Note
import pl.mrozok.notes.infrastructure.EventsEmitter
import pl.mrozok.notes.infrastructure.NotesRepository
import pl.mrozok.toolbox.presentation.IllegalOperationException

class NoteEditor constructor(
    private val eventsEmitter: EventsEmitter,
    private val notesRepository: NotesRepository
) {

    private var note: Note? = null

    @Throws(IllegalOperationException::class)
    fun updateContent(content: Content) {
        val currentNote = note
        if (null == currentNote) {
            eventsEmitter.errorOccurred("Editor not initialized, call existing or new first")
            return
        }
        if (currentNote.content.javaClass != content.javaClass) {
            eventsEmitter.errorOccurred("Invalid content used")
            throw IllegalOperationException()
        }
        currentNote.content = content
        notesRepository.saveNewVersion(currentNote)
        eventsEmitter.contentUpdated(currentNote.uuid, content)
    }

    fun existing(uuid: String) {
        note = notesRepository.getLatestVersion(uuid)
    }

    fun new(type: NotesModule.Type) {
        notesRepository.new(type).apply {
            note = this
            eventsEmitter.noteCreated(copy())
        }
    }

    fun delete() {
        note?.apply {
            notesRepository.delete(uuid)
            eventsEmitter.noteDeleted(uuid)
        }
    }

    fun noteUuid(): String = note!!.uuid

    fun noteHasEmptyContent(): Boolean = note!!.hasEmptyContent()

    fun noteType(): NotesModule.Type = note!!.type()

    fun <T : Content> noteContent(): T = note!!.content.getCopy() as T

}
