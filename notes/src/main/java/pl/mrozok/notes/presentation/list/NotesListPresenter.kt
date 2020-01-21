package pl.mrozok.notes.presentation.list

import io.reactivex.disposables.CompositeDisposable
import pl.mrozok.notes.domain.Event.*
import pl.mrozok.notes.domain.NotesModule
import pl.mrozok.toolbox.presentation.BasePresenter
import pl.mrozok.toolbox.presentation.UICommand
import pl.mrozok.toolbox.presentation.reactivex.SchedulersHolder

class NotesListPresenter(
    private val notesModule: NotesModule.API,
    schedulersHolder: SchedulersHolder
) : BasePresenter<NotesListUI>(schedulersHolder) {

    private val subscriptions: CompositeDisposable = CompositeDisposable()

    fun setup() {
        subscriptions.add(
            notesModule.eventsObservable()
                .subscribeOn(schedulersHolder.subscribingScheduler)
                .subscribe({ event ->
                    when (event) {
                        is NoteCreated, is NoteContentChanged, is NoteDeleted -> loadNotes()
                    }
                }, { error ->
                })
        )
    }

    fun loadNotes() {
        executeCommand(object : UICommand<NotesListUI> {
            override fun execute(ui: NotesListUI) {
                ui.showNotes(notesModule.allNotes())
            }
        })
    }

    override fun destroy() {
        subscriptions.clear()
        super.destroy()
    }

}
