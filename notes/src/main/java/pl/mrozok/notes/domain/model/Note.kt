package pl.mrozok.notes.domain.model

import pl.mrozok.notes.domain.NotesModule

data class Note(
    val uuid: String,
    var content: Content
) {

    fun type(): NotesModule.Type = content.type

    fun hasEmptyContent(): Boolean = content.isEmpty()

}
