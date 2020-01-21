package pl.mrozok.myapplication.presentation.notes.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import kotlinx.android.synthetic.main.checkable_item.view.content
import kotlinx.android.synthetic.main.checkable_item.view.is_done
import kotlinx.android.synthetic.main.editable_check_item_view.view.*
import pl.mrozok.myapplication.R

class EditableCheckItem : ConstraintLayout {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    private var initializer: () -> Unit = {}

    init {
        val layoutInflater = LayoutInflater.from(context)
        layoutInflater.inflate(R.layout.editable_check_item_view, this, true)
    }

    fun fill(viewModel: ViewModel) {
        if (isAttachedToWindow) {
            fillWithViewModel(viewModel)
        } else {
            initializer = { fillWithViewModel(viewModel) }
        }
    }

    private fun fillWithViewModel(viewModel: ViewModel) {
        is_done.isChecked = viewModel.checked
        content.text = viewModel.content
        remove.setOnClickListener {
            viewModel.onRemoveAction.invoke()
        }
    }

    override fun onAttachedToWindow() {
        initializer.invoke()
        super.onAttachedToWindow()
    }

    data class ViewModel(
        val content: String,
        val checked: Boolean,
        val onRemoveAction: () -> Unit
    )

    fun isChecked(): Boolean = is_done.isChecked

    fun value(): String = content.text.toString()

}
