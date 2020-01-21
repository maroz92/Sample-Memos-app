package pl.mrozok.notes.presentation.details

import pl.mrozok.notes.domain.NotesModule
import pl.mrozok.toolbox.presentation.UI

interface NoteDetailsUI : UI {
    fun setupView(
        type: NotesModule.Type,
        uuid: String
    )
}