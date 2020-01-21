package pl.mrozok.notes.presentation.details

import pl.mrozok.notes.domain.NoteEditor
import pl.mrozok.toolbox.presentation.BasePresenter
import pl.mrozok.toolbox.presentation.UICommand
import pl.mrozok.toolbox.presentation.reactivex.SchedulersHolder

class NoteDetailsPresenter(
    private val noteEditor: NoteEditor,
    schedulersHolder: SchedulersHolder
) : BasePresenter<NoteDetailsUI>(schedulersHolder) {

    fun setup() {
        executeSetupViewCommand(noteEditor.noteUuid())
    }

    private fun executeSetupViewCommand(uuid: String) {
        executeCommand(object : UICommand<NoteDetailsUI> {
            override fun execute(ui: NoteDetailsUI) {
                ui.setupView(noteEditor.noteType(), uuid)
            }
        })
    }

    fun handleBackNavigation() {
        if (noteEditor.noteHasEmptyContent()) {
            deleteNote()
        }
    }

    private fun deleteNote() {
        noteEditor.delete()
    }

}
