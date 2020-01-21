package pl.mrozok.myapplication.infrastructure

import android.content.Context
import android.preference.PreferenceManager
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import pl.mrozok.notes.domain.NoteEditor
import pl.mrozok.notes.domain.NotesModule
import pl.mrozok.notes.infrastructure.EventsEmitter
import pl.mrozok.notes.presentation.details.CheckListContentPresenter
import pl.mrozok.notes.presentation.details.CheckListPresenter
import pl.mrozok.notes.presentation.details.NoteDetailsPresenter
import pl.mrozok.notes.presentation.details.TextContentPresenter
import pl.mrozok.notes.presentation.list.NotesListPresenter
import pl.mrozok.toolbox.presentation.reactivex.SchedulersHolder

class ProductionGraph(private val context: Context) : Graph {

    private val notesModule: NotesModule.API by lazy {
        val eventsEmitter = EventsEmitter()
        val notesRepository = AndroidNotesRepository(
            PreferenceManager.getDefaultSharedPreferences(context),
            JsonSerializerProvider.provide()
        )
        val noteEditor = NoteEditor(eventsEmitter, notesRepository)
        val dependencies = NotesModule.Dependencies(notesRepository, eventsEmitter, noteEditor)
        NotesModule.create(dependencies)
    }

    override fun notesListPresenter(): NotesListPresenter =
        NotesListPresenter(notesModule, defaultSchedulersHolder())

    override fun noteDetailsPresenterForNewNote(type: NotesModule.Type): NoteDetailsPresenter =
        NoteDetailsPresenter(
            notesModule.newNote(type),
            defaultSchedulersHolder()
        )

    override fun noteDetailsPresenterForExistingNote(uuid: String): NoteDetailsPresenter =
        NoteDetailsPresenter(
            notesModule.existingNote(uuid),
            defaultSchedulersHolder()
        )

    override fun textContentPresenter(uuid: String): TextContentPresenter =
        TextContentPresenter(notesModule.existingNote(uuid), defaultSchedulersHolder())

    override fun checkListContentPresenter(uuid: String): CheckListContentPresenter =
        CheckListContentPresenter(notesModule.existingNote(uuid), defaultSchedulersHolder())

    override fun checkListPresenter(): CheckListPresenter = CheckListPresenter()

    private fun defaultSchedulersHolder() = SchedulersHolder(AndroidSchedulers.mainThread(), Schedulers.io())

}
