package pl.mrozok.myapplication.presentation.notes.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.notes_list_check_list_item.view.*
import kotlinx.android.synthetic.main.notes_list_text_item.view.*
import pl.mrozok.myapplication.R
import pl.mrozok.myapplication.presentation.notes.view.CheckableItem
import pl.mrozok.notes.domain.model.CheckList
import pl.mrozok.notes.domain.model.Note
import pl.mrozok.notes.domain.model.Text

sealed class NoteViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    companion object {
        private fun inflateView(
            parentView: ViewGroup,
            layoutResId: Int
        ): View {
            val layoutInflater = LayoutInflater.from(parentView.context)
            return layoutInflater.inflate(layoutResId, parentView, false)
        }
    }

    protected fun fillTitle(
        titleTextView: TextView,
        title: String
    ) {
        if (title.isEmpty()) {
            titleTextView.visibility = View.GONE
        } else {
            titleTextView.visibility = View.VISIBLE
            titleTextView.text = title
        }
    }

    class TextItem(view: View) : NoteViewHolder(view) {
        companion object {
            fun create(parentView: ViewGroup): TextItem =
                TextItem(inflateView(parentView, R.layout.notes_list_text_item))
        }

        fun bind(note: Note) {
            (note.content as Text).apply {
                fillTitle(itemView.note_item_text_title, title)
                fillValue(value)
            }
        }

        private fun fillValue(content: String) {
            if (content.isEmpty())
                itemView.note_item_text_content.setText(R.string.note_placeholder_text)
            else
                itemView.note_item_text_content.text = content
        }
    }

    class CheckListItem(view: View) : NoteViewHolder(view) {
        companion object {
            fun create(parentView: ViewGroup): CheckListItem =
                CheckListItem(inflateView(parentView, R.layout.notes_list_check_list_item))

            private const val ITEMS_TO_SHOW_MORE_INDICATOR = 3
        }

        fun bind(note: Note) {
            (note.content as CheckList).apply {
                fillTitle(itemView.note_item_check_list_title, title)
                fillItems(items)
            }
        }

        private fun fillItems(items: MutableList<CheckList.CheckItem>) {
            val showMoreIndicator = ITEMS_TO_SHOW_MORE_INDICATOR <= items.size
            itemView.note_item_check_list_more_indicator.visibility = if (showMoreIndicator) View.VISIBLE else View.GONE
            itemView.note_item_check_list_more_indicator.text = itemView.context.getString(
                R.string.more_items_format,
                items.size - 2
            )
            itemView.first_item.visibility = View.GONE
            itemView.second_item.visibility = View.GONE
            when (items.size) {
                0 -> { /* intentionally do nothing */
                }

                1 -> {
                    fillItem(itemView.first_item, items.first())
                }

                else -> {
                    fillItem(itemView.first_item, items.first())
                    fillItem(itemView.second_item, items[1])
                }

            }
        }

        private fun fillItem(
            view: CheckableItem,
            item: CheckList.CheckItem
        ) {
            view.visibility = View.VISIBLE
            view.fill(item.value, item.checked)
        }
    }

}
