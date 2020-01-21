package pl.mrozok.notes.presentation.list

import pl.mrozok.notes.domain.model.Note
import pl.mrozok.toolbox.presentation.UI

interface NotesListUI : UI {
    fun showNotes(notes: List<Note>)
}
