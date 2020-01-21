package pl.mrozok.notes.domain.model

import pl.mrozok.notes.domain.NotesModule

interface Content {

    companion object {
        fun from(type: NotesModule.Type): Content = when (type) {
            NotesModule.Type.TEXT -> Text("", "")
            NotesModule.Type.LIST -> CheckList("", mutableListOf())
        }
    }

    val type: NotesModule.Type

    fun isEmpty(): Boolean

    fun getCopy(): Content

}
