package pl.mrozok.myapplication.presentation.notes.view

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import pl.mrozok.myapplication.infrastructure.Graph
import pl.mrozok.myapplication.infrastructure.NotesApplication
import pl.mrozok.notes.presentation.details.CheckListPresenter
import pl.mrozok.notes.presentation.details.CheckListUI
import pl.mrozok.toolbox.presentation.UILifecycleBindable

class CheckListView : LinearLayout, CheckListUI, UILifecycleBindable {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    private val checkListPresenter: CheckListPresenter = graph().checkListPresenter()

    fun newItem() {
        checkListPresenter.saveItems(collectItemsFromUI())
        checkListPresenter.newItem()
    }

    private fun collectItemsFromUI(): List<CheckListPresenter.CheckListItem> {
        val childCount = childCount
        return (0 until childCount)
            .map { index ->
                val view = getChildAt(index) as EditableCheckItem
                CheckListPresenter.CheckListItem(view.value(), view.isChecked())
            }
    }

    override fun showItems(items: List<CheckListPresenter.CheckListItem>) {
        removeAllViews()
        (items.indices).map { index ->
            val item = items[index]
            EditableCheckItem.ViewModel(item.value, item.checked) {
                checkListPresenter.saveItems(collectItemsFromUI())
                checkListPresenter.removeItem(index)
            }
        }.forEach { viewModel ->
            val view = EditableCheckItem(context)
            addView(view)
            view.fill(viewModel)
        }
    }

    fun setItems(items: List<CheckListPresenter.CheckListItem>) {
        checkListPresenter.replaceData(items)
    }

    fun items(): List<CheckListPresenter.CheckListItem> {
        checkListPresenter.saveItems(collectItemsFromUI())
        return checkListPresenter.items()
    }

    override fun onResume() {
        checkListPresenter.attachUI(this)
    }

    override fun onPause() {
        checkListPresenter.detachUI()
    }

    private fun graph(): Graph = (context.applicationContext as NotesApplication).graph

}
