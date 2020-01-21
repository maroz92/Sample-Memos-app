package pl.mrozok.myapplication.infrastructure

import kotlinx.serialization.Polymorphic
import kotlinx.serialization.Serializable
import pl.mrozok.notes.domain.NotesModule
import pl.mrozok.notes.domain.model.CheckList
import pl.mrozok.notes.domain.model.Note
import pl.mrozok.notes.domain.model.Text

@Serializable
internal data class SerializableNote(
    val uuid: String,
    @Polymorphic val content: SerializableContent
) {

    companion object {
        fun from(note: Note): SerializableNote {
            return when (note.type()) {
                NotesModule.Type.TEXT -> {
                    val textContent = note.content as Text
                    SerializableNote(
                        note.uuid,
                        SerializableContent.Text(textContent.title, textContent.value)
                    )
                }
                NotesModule.Type.LIST -> {
                    val listContent = note.content as CheckList
                    SerializableNote(
                        note.uuid,
                        SerializableContent.List(
                            listContent.title,
                            listContent.items.map { item ->
                                SerializableContent.List.CheckItem(
                                    item.value,
                                    item.checked
                                )
                            }.toMutableList()
                        )
                    )
                }
            }

        }
    }

    fun toNote(): Note {
        val noteContent = when (content) {
            is SerializableContent.Text -> Text(content.title, content.content)
            is SerializableContent.List -> CheckList(
                content.title,
                content.items.map { item -> CheckList.CheckItem(item.value, item.checked) }.toMutableList()
            )
        }
        return Note(uuid, noteContent)
    }

    sealed class SerializableContent {

        @Serializable
        data class Text(
            val title: String,
            val content: String
        ) : SerializableContent()

        @Serializable
        data class List(
            val title: String,
            val items: MutableList<CheckItem>
        ) : SerializableContent() {
            @Serializable
            data class CheckItem(
                val value: String,
                val checked: Boolean = false
            )
        }

    }
}
