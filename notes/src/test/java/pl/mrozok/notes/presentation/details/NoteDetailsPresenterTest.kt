package pl.mrozok.notes.presentation.details

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import org.junit.Test
import pl.mrozok.notes.domain.NoteEditor
import pl.mrozok.toolbox.presentation.reactivex.SchedulersHolder

class NoteDetailsPresenterTest {

    @Test
    fun `delete empty note on back navigation`() {
        val noteEditor: NoteEditor = mock()
        whenever(noteEditor.noteHasEmptyContent()).thenReturn(true)
        val systemUnderTest =
            NoteDetailsPresenter(noteEditor, SchedulersHolder.immediateSchedulersHolder())

        systemUnderTest.handleBackNavigation()
        verify(noteEditor).delete()
    }

}
