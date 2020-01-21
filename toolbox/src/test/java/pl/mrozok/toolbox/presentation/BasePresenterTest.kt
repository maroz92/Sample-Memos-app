package pl.mrozok.toolbox.presentation

import com.nhaarman.mockitokotlin2.*
import org.junit.Test
import pl.mrozok.toolbox.presentation.reactivex.SchedulersHolder

class BasePresenterTest {

    private val systemUnderTest = TestPresenter()
    private val ui = TestUI()

    @Test
    fun `if ui attached execute command immediately`() {
        val command = givenUICommand()

        systemUnderTest.attachUI(ui)
        systemUnderTest.executeCommand(command)

        verify(command).execute(ui)
    }

    @Test
    fun `should execute command after ui is attached`() {
        val command = givenUICommand()

        systemUnderTest.executeCommand(command)

        verify(command, never()).execute(ui)
        systemUnderTest.attachUI(ui)
        verify(command).execute(ui)
    }

    @Test
    fun `queued commands should be executed chronologically`() {
        val firstCommand = givenUICommand()
        val secondCommand = givenOtherUICommand()

        systemUnderTest.executeCommand(firstCommand)
        systemUnderTest.executeCommand(secondCommand)

        systemUnderTest.attachUI(ui)
        inOrder(firstCommand, secondCommand).run {
            verify(firstCommand).execute(ui)
            verify(secondCommand).execute(ui)
        }
    }

    private fun givenOtherUICommand() = spy(object : UICommand<TestUI> {
        override fun execute(ui: TestUI) {}
    })

    @Test
    fun `should execute only latest command of given type`() {
        val firstCommand = givenUICommand()
        val secondCommand = givenUICommand()

        systemUnderTest.executeCommand(firstCommand)
        systemUnderTest.executeCommand(secondCommand)

        systemUnderTest.attachUI(ui)
        verify(secondCommand, only()).execute(ui)
    }

    private fun givenUICommand() = mock<UICommand<TestUI>>()

    private class TestPresenter : BasePresenter<TestUI>(SchedulersHolder.immediateSchedulersHolder())

    private class TestUI : UI

}
