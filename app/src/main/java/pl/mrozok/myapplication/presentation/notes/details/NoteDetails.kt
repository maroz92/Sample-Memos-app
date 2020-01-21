package pl.mrozok.myapplication.presentation.notes.details

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_note_details.*
import kotlinx.android.synthetic.main.content_note_details.*
import pl.mrozok.myapplication.R
import pl.mrozok.myapplication.presentation.tools.BaseActivity
import pl.mrozok.notes.domain.NotesModule
import pl.mrozok.notes.presentation.details.NoteDetailsPresenter
import pl.mrozok.notes.presentation.details.NoteDetailsUI

class NoteDetails : BaseActivity(), NoteDetailsUI {

    companion object {

        private const val EXTRA_UUID = "NoteDetails.uuid"
        private const val EXTRA_TYPE = "NoteDetails.type"

        fun start(
            context: Context,
            uuid: String
        ) {
            val intent = Intent(context, NoteDetails::class.java).apply {
                putExtra(EXTRA_UUID, uuid)
            }
            context.startActivity(intent)
        }

        fun start(
            context: Context,
            type: NotesModule.Type
        ) {
            val intent = Intent(context, NoteDetails::class.java).apply {
                putExtra(EXTRA_TYPE, type)
            }
            context.startActivity(intent)
        }

    }

    private lateinit var presenter: NoteDetailsPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note_details)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        setup()
    }

    private fun setup() {
        presenter = if (intent.hasExtra(EXTRA_UUID)) {
            val uuid = intent.getStringExtra(EXTRA_UUID)
            graph().noteDetailsPresenterForExistingNote(uuid)
        } else {
            val type = intent.getSerializableExtra(EXTRA_TYPE) as NotesModule.Type
            graph().noteDetailsPresenterForNewNote(type)
        }
        presenter.setup()
    }

    override fun setupView(
        type: NotesModule.Type,
        uuid: String
    ) {
        when (type) {
            NotesModule.Type.TEXT -> text_content.setup(uuid)
            NotesModule.Type.LIST -> check_list_content.setup(uuid)
        }
    }

    override fun onResume() {
        super.onResume()
        presenter.attachUI(this)
        text_content.onResume()
        check_list_content.onResume()
    }

    override fun onPause() {
        text_content.onPause()
        check_list_content.onPause()
        presenter.detachUI()
        super.onPause()
    }

    override fun onBackPressed() {
        handleBackNavigation()
        super.onBackPressed()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (android.R.id.home == item?.itemId) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun handleBackNavigation() {
        text_content.saveContent()
        check_list_content.saveContent()
        presenter.handleBackNavigation()
    }

}
