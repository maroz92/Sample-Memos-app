package pl.mrozok.myapplication.infrastructure

import pl.mrozok.notes.domain.NotesModule
import pl.mrozok.notes.presentation.details.CheckListContentPresenter
import pl.mrozok.notes.presentation.details.CheckListPresenter
import pl.mrozok.notes.presentation.details.NoteDetailsPresenter
import pl.mrozok.notes.presentation.details.TextContentPresenter
import pl.mrozok.notes.presentation.list.NotesListPresenter

interface Graph {

    fun notesListPresenter(): NotesListPresenter

    fun noteDetailsPresenterForNewNote(type: NotesModule.Type): NoteDetailsPresenter
    fun noteDetailsPresenterForExistingNote(uuid: String): NoteDetailsPresenter
    fun textContentPresenter(uuid: String): TextContentPresenter
    fun checkListContentPresenter(uuid: String): CheckListContentPresenter
    fun checkListPresenter(): CheckListPresenter

}
