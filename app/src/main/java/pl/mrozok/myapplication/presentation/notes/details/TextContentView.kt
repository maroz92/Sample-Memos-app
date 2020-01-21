package pl.mrozok.myapplication.presentation.notes.details

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import kotlinx.android.synthetic.main.text_content_view.view.*
import pl.mrozok.myapplication.R
import pl.mrozok.myapplication.infrastructure.Graph
import pl.mrozok.myapplication.infrastructure.NotesApplication
import pl.mrozok.myapplication.presentation.tools.SimpleTextWatcher
import pl.mrozok.notes.domain.model.Text
import pl.mrozok.notes.presentation.details.TextContentPresenter
import pl.mrozok.notes.presentation.details.TextContentUI
import pl.mrozok.toolbox.presentation.UILifecycleBindable

class TextContentView : ConstraintLayout, TextContentUI, UILifecycleBindable {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    private var presenter: TextContentPresenter? = null

    init {
        val layoutInflater = LayoutInflater.from(context)
        layoutInflater.inflate(R.layout.text_content_view, this, true)
    }

    fun setup(uuid: String) {
        presenter = graph().textContentPresenter(uuid).apply {
            showContent()

            content_title.addTextChangedListener(SimpleTextWatcher { text ->
                saveTitle(text)
            })
            content_value.addTextChangedListener(SimpleTextWatcher { text ->
                saveContent(text)
            })
        }
    }

    override fun showContent(textContent: Text) {
        textContent.apply {
            visibility = View.VISIBLE
            content_title.setText(textContent.title)
            content_value.setText(textContent.value)
        }
    }

    fun saveContent() {
        presenter?.apply {
            saveTitle(content_title.text.toString())
            saveContent(content_value.text.toString())
        }
    }

    override fun onResume() {
        presenter?.attachUI(this)
    }

    override fun onPause() {
        presenter?.detachUI()
    }

    private fun graph(): Graph = (context.applicationContext as NotesApplication).graph

}
