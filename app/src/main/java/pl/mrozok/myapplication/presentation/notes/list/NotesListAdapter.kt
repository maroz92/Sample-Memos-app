package pl.mrozok.myapplication.presentation.notes.list

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import pl.mrozok.notes.domain.NotesModule.Type
import pl.mrozok.notes.domain.model.Note

class NotesListAdapter(private val onNoteClickAction: (String) -> Unit) :
    RecyclerView.Adapter<NoteViewHolder>() {

    init {
        setHasStableIds(true)
    }

    private var notes: MutableList<Note> = mutableListOf()

    fun replaceData(notes: List<Note>) {
        this.notes = notes.toMutableList()
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(
        holder: NoteViewHolder,
        position: Int
    ) {
        val note = notes[position]
        when (holder) {
            is NoteViewHolder.TextItem -> holder.bind(note)
            is NoteViewHolder.CheckListItem -> holder.bind(note)
        }
        holder.itemView.setOnClickListener { onNoteClickAction.invoke(note.uuid) }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NoteViewHolder {
        val type = Type.values()[viewType]
        return when (type) {
            Type.TEXT -> NoteViewHolder.TextItem.create(parent)
            Type.LIST -> NoteViewHolder.CheckListItem.create(parent)
        }
    }

    override fun getItemViewType(position: Int): Int {
        val type = notes[position].type()
        return type.ordinal
    }

    override fun getItemCount(): Int = notes.size

    override fun getItemId(position: Int): Long = notes[position].uuid.hashCode().toLong()

}
