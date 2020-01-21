package pl.mrozok.notes.infrastructure

import pl.mrozok.notes.domain.NotesModule
import pl.mrozok.notes.domain.model.Note

interface NotesRepository {

    fun new(type: NotesModule.Type): Note

    fun saveNewVersion(note: Note)

    @Throws(IllegalStateException::class)
    fun getLatestVersion(uuid: String): Note

    @Throws(IllegalStateException::class)
    fun delete(uuid: String)

    fun getAll(): List<Note>

}
