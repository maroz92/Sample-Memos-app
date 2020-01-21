package pl.mrozok.notes.presentation.details

import pl.mrozok.toolbox.presentation.BasePresenter
import pl.mrozok.toolbox.presentation.UICommand
import pl.mrozok.toolbox.presentation.reactivex.SchedulersHolder

class CheckListPresenter : BasePresenter<CheckListUI>(SchedulersHolder.immediateSchedulersHolder()) {

    private val items: MutableList<CheckListItem> = mutableListOf()

    fun newItem() {
        items.add(CheckListItem("", false))
        updateUI()
    }

    private fun updateUI() {
        executeCommand(object : UICommand<CheckListUI> {
            override fun execute(ui: CheckListUI) {
                ui.showItems(items)
            }
        })
    }

    fun removeItem(position: Int) {
        items.removeAt(position)
        updateUI()
    }

    fun saveItems(items: List<CheckListItem>) {
        this.items.apply {
            clear()
            addAll(items)
        }
    }

    fun replaceData(items: List<CheckListItem>) {
        saveItems(items)
        updateUI()
    }

    fun items(): List<CheckListItem> = items

    data class CheckListItem(
        val value: String,
        val checked: Boolean
    )

}
