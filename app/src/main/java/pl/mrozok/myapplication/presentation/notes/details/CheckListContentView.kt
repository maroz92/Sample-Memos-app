package pl.mrozok.myapplication.presentation.notes.details

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import kotlinx.android.synthetic.main.check_list_content_view.view.*
import pl.mrozok.myapplication.R
import pl.mrozok.myapplication.infrastructure.Graph
import pl.mrozok.myapplication.infrastructure.NotesApplication
import pl.mrozok.myapplication.presentation.tools.SimpleTextWatcher
import pl.mrozok.notes.domain.model.CheckList
import pl.mrozok.notes.presentation.details.CheckListContentPresenter
import pl.mrozok.notes.presentation.details.CheckListContentUI
import pl.mrozok.notes.presentation.details.CheckListPresenter
import pl.mrozok.toolbox.presentation.UILifecycleBindable

class CheckListContentView : ConstraintLayout, CheckListContentUI, UILifecycleBindable {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    private var presenter: CheckListContentPresenter? = null

    init {
        val layoutInflater = LayoutInflater.from(context)
        layoutInflater.inflate(R.layout.check_list_content_view, this, true)
    }

    fun setup(uuid: String) {
        presenter = graph().checkListContentPresenter(uuid).apply {
            showContent()

            content_title.addTextChangedListener(SimpleTextWatcher { text ->
                saveTitle(text)
            })

            add_item.setOnClickListener {
                content_items_container.newItem()
            }
        }
    }

    override fun showContent(checkListContent: CheckList) {
        checkListContent.apply {
            visibility = View.VISIBLE
            content_title.setText(title)
        }
        val items = checkListContent.items
            .map { item ->
                CheckListPresenter.CheckListItem(
                    item.value,
                    item.checked
                )
            }

        content_items_container.setItems(items)
    }

    fun saveContent() {
        presenter?.apply {
            saveTitle(content_title.text.toString())
            val items = content_items_container.items()
                .map { item -> CheckList.CheckItem(item.value, item.checked) }
            saveItems(items)
        }
    }

    override fun onResume() {
        content_items_container.onResume()
        presenter?.attachUI(this)
    }

    override fun onPause() {
        content_items_container.onPause()
        presenter?.detachUI()
    }

    private fun graph(): Graph = (context.applicationContext as NotesApplication).graph

}
