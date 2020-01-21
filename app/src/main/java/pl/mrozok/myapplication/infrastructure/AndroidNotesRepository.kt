package pl.mrozok.myapplication.infrastructure

import android.content.SharedPreferences
import kotlinx.serialization.json.Json
import pl.mrozok.notes.domain.NotesModule
import pl.mrozok.notes.domain.model.Content
import pl.mrozok.notes.domain.model.Note
import pl.mrozok.notes.infrastructure.NotesRepository
import java.util.*

class AndroidNotesRepository(
    private val sharedPreferences: SharedPreferences,
    private val marshaller: Json
) : NotesRepository {

    companion object {
        private const val KEY_NOTE_FORMAT = "Note-"
        private const val KEY_UUIDS = "Notes.uuid"
    }

    override fun new(type: NotesModule.Type): Note {
        return Note(uuid(), Content.from(type)).apply {
            saveNoteToSharedPrefs(this)
        }
    }

    private fun uuid(): String = UUID.randomUUID().toString()

    private fun saveNoteToSharedPrefs(note: Note) {
        val jsonNote = marshaller.stringify(SerializableNote.serializer(), SerializableNote.from(note))
        val uuids = sharedPreferences.getStringSet(KEY_UUIDS, null) ?: mutableSetOf()
        uuids.add(note.uuid)
        sharedPreferences.edit()
            .putString(noteKey(note.uuid), jsonNote)
            .putStringSet(KEY_UUIDS, uuids)
            .commit()
    }

    private fun noteKey(uuid: String): String = KEY_NOTE_FORMAT + uuid

    override fun getLatestVersion(uuid: String): Note {
        return loadNoteFromSharedPreferences(uuid).toNote()
    }

    private fun loadNoteFromSharedPreferences(uuid: String): SerializableNote {
        val jsonNote = sharedPreferences.getString(noteKey(uuid), null)
            ?: throw IllegalStateException("Note with $uuid doesn't exist")
        return marshaller.parse(SerializableNote.serializer(), jsonNote)
    }

    override fun saveNewVersion(note: Note) {
        saveNoteToSharedPrefs(note)
    }

    override fun delete(uuid: String) {
        val uuids = sharedPreferences.getStringSet(KEY_UUIDS, null) ?: mutableSetOf()
        uuids.remove(uuid)
        sharedPreferences.edit()
            .remove(noteKey(uuid))
            .putStringSet(KEY_UUIDS, uuids)
            .commit()
    }

    override fun getAll(): List<Note> {
        val uuids = sharedPreferences.getStringSet(KEY_UUIDS, null) ?: return emptyList()
        return uuids.map { uuid -> getLatestVersion(uuid) }
    }

}
