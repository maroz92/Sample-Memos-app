package pl.mrozok.myapplication.presentation.notes.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import kotlinx.android.synthetic.main.checkable_item.view.*
import pl.mrozok.myapplication.R

class CheckableItem : ConstraintLayout {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    init {
        val layoutInflater = LayoutInflater.from(context)
        layoutInflater.inflate(R.layout.checkable_item, this, true)
    }

    fun fill(
        value: String,
        checked: Boolean = false
    ) {
        is_done.isChecked = checked
        content.text = value
    }

}
