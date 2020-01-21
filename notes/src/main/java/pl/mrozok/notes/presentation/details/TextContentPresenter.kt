package pl.mrozok.notes.presentation.details

import pl.mrozok.notes.domain.NoteEditor
import pl.mrozok.notes.domain.model.Text
import pl.mrozok.toolbox.presentation.BasePresenter
import pl.mrozok.toolbox.presentation.UICommand
import pl.mrozok.toolbox.presentation.reactivex.SchedulersHolder

class TextContentPresenter(
    private val noteEditor: NoteEditor,
    schedulersHolder: SchedulersHolder
) : BasePresenter<TextContentUI>(schedulersHolder) {

    fun saveTitle(text: String) {
        content().apply {
            if (title == text) return
            val newContent = copy(title = text)
            noteEditor.updateContent(newContent)
        }
    }

    fun saveContent(text: String) {
        content().apply {
            if (value == text) return
            val newContent = copy(value = text)
            noteEditor.updateContent(newContent)
        }
    }

    fun showContent() {
        executeCommand(object : UICommand<TextContentUI> {
            override fun execute(ui: TextContentUI) {
                ui.showContent(content())
            }
        })
    }

    private fun content(): Text = noteEditor.noteContent()

}
