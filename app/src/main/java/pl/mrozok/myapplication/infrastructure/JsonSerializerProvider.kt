package pl.mrozok.myapplication.infrastructure

import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import pl.mrozok.myapplication.infrastructure.SerializableNote.SerializableContent

object JsonSerializerProvider {

    fun provide(): Json {
        val serializableContentModule = SerializersModule {
            polymorphic<SerializableContent> {
                SerializableContent.Text::class with SerializableContent.Text.serializer()
                SerializableContent.List::class with SerializableContent.List.serializer()
            }
        }
        return Json(context = serializableContentModule)
    }

}