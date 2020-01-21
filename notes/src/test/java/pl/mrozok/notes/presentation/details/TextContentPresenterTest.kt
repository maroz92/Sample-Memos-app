package pl.mrozok.notes.presentation.details

import com.nhaarman.mockitokotlin2.*
import org.junit.Test
import pl.mrozok.notes.domain.NoteEditor
import pl.mrozok.notes.domain.model.Text
import pl.mrozok.toolbox.presentation.reactivex.SchedulersHolder

class TextContentPresenterTest {

    private val noteEditor: NoteEditor = mock()

    @Test
    fun `update title if different`() {
        `given note with content`(Text("Some", ""))
        val systemUnderTest = TextContentPresenter(noteEditor, SchedulersHolder.immediateSchedulersHolder())

        systemUnderTest.saveTitle("Some title")

        verify(noteEditor).updateContent(Text("Some title", ""))
    }

    @Test
    fun `update title only if different`() {
        `given note with content`(Text("Some", ""))
        val systemUnderTest = TextContentPresenter(noteEditor, SchedulersHolder.immediateSchedulersHolder())

        systemUnderTest.saveTitle("Some")

        verify(noteEditor, never()).updateContent(any())
    }

    private fun `given note with content`(content: Text) {
        whenever(noteEditor.noteContent<Text>()).thenReturn(content)
    }

}
