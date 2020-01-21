package pl.mrozok.notes.domain.model

import pl.mrozok.notes.domain.NotesModule

data class CheckList(
    val title: String,
    val items: MutableList<CheckItem>
) : Content {

    override val type: NotesModule.Type = NotesModule.Type.LIST

    data class CheckItem(
        val value: String = "",
        val checked: Boolean = false
    )

    override fun isEmpty(): Boolean = title.isEmpty() && items.isEmpty()

    override fun getCopy(): Content = copy()
}
