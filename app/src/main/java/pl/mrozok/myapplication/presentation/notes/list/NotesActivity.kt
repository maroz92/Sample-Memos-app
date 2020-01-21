package pl.mrozok.myapplication.presentation.notes.list

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.content_note_list.*
import kotlinx.android.synthetic.main.notes.*
import pl.mrozok.myapplication.R
import pl.mrozok.myapplication.presentation.notes.details.NoteDetails
import pl.mrozok.myapplication.presentation.notes.details.NoteTypePicker
import pl.mrozok.myapplication.presentation.tools.BaseActivity
import pl.mrozok.myapplication.presentation.tools.VerticalSpaceItemDecoration
import pl.mrozok.notes.domain.model.Note
import pl.mrozok.notes.presentation.list.NotesListPresenter
import pl.mrozok.notes.presentation.list.NotesListUI

class NotesActivity : BaseActivity(), NotesListUI {

    private lateinit var notesListPresenter: NotesListPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.notes)
        setSupportActionBar(toolbar)
        notesListPresenter = graph().notesListPresenter()

        new_note.setOnClickListener {
            NoteTypePicker.show(this,
                { type -> NoteDetails.start(this, type) },
                { /* nothing to do */ })
        }

        setupRecyclerView()
        notesListPresenter.setup()
        notesListPresenter.loadNotes()
    }

    private fun setupRecyclerView() {
        val adapter = NotesListAdapter { noteUuid -> NoteDetails.start(this, noteUuid) }
        note_list.layoutManager = LinearLayoutManager(this)
        note_list.adapter = adapter
        note_list.addItemDecoration(
            VerticalSpaceItemDecoration(
                6,
                resources.displayMetrics
            )
        )
    }

    override fun onResume() {
        super.onResume()
        notesListPresenter.attachUI(this)
    }

    override fun onPause() {
        notesListPresenter.detachUI()
        super.onPause()
    }

    override fun showNotes(notes: List<Note>) {
        (note_list.adapter as NotesListAdapter).replaceData(notes)
    }

    override fun onDestroy() {
        notesListPresenter.destroy()
        super.onDestroy()
    }

}
