package pl.mrozok.myapplication.presentation.notes.details

import android.content.Context
import androidx.appcompat.app.AlertDialog
import pl.mrozok.myapplication.R
import pl.mrozok.notes.domain.NotesModule.Type

object NoteTypePicker {

    fun show(
        context: Context,
        onTypePickedAction: (Type) -> Unit,
        onCancelAction: () -> Unit
    ) {
        AlertDialog.Builder(context)
            .setTitle(R.string.new_note_action)
            .setItems(R.array.note_content_types) { dialog, which -> onTypePickedAction.invoke(Type.values()[which]) }
            .setNegativeButton(R.string.cancel_action) { dialog, which -> onCancelAction.invoke() }
            .setOnCancelListener { onCancelAction.invoke() }
            .show()
    }

}
