package pl.mrozok.notes.presentation.details

import pl.mrozok.notes.domain.NoteEditor
import pl.mrozok.notes.domain.model.CheckList
import pl.mrozok.toolbox.presentation.BasePresenter
import pl.mrozok.toolbox.presentation.UICommand
import pl.mrozok.toolbox.presentation.reactivex.SchedulersHolder

class CheckListContentPresenter(
    private val noteEditor: NoteEditor,
    schedulersHolder: SchedulersHolder
) : BasePresenter<CheckListContentUI>(schedulersHolder) {

    fun saveTitle(text: String) {
        content().apply {
            if (title == text) return
            val newContent = copy(title = text)
            noteEditor.updateContent(newContent)
        }
    }

    fun saveItems(items: List<CheckList.CheckItem>) {
        content().apply {
            val newContent = copy(items = items.toMutableList())
            noteEditor.updateContent(newContent)
        }
    }

    fun showContent() {
        val content = content()
        executeCommand(object : UICommand<CheckListContentUI> {
            override fun execute(ui: CheckListContentUI) {
                ui.showContent(content)
            }
        })
    }

    private fun content(): CheckList = noteEditor.noteContent()

}
