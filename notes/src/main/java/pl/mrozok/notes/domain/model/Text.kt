package pl.mrozok.notes.domain.model

import pl.mrozok.notes.domain.NotesModule

data class Text(
    val title: String,
    val value: String
) : Content {

    override val type: NotesModule.Type = NotesModule.Type.TEXT

    override fun isEmpty(): Boolean = title.isEmpty() && value.isEmpty()

    override fun getCopy(): Content = copy()
}
