package pl.mrozok.toolbox.presentation

import pl.mrozok.toolbox.presentation.reactivex.SchedulersHolder
import java.util.*

open class BasePresenter<T : UI>(protected val schedulersHolder: SchedulersHolder) : Presenter<T> {

    private val commandQueue: Queue<UICommand<T>> = LinkedList()
    private var ui: T? = null

    override fun attachUI(ui: T) {
        this.ui = ui
        var toBeExecuted = commandQueue.poll()
        while (null != toBeExecuted) {
            toBeExecuted.execute(ui)
            toBeExecuted = commandQueue.poll()
        }
    }

    override fun detachUI() {
        ui = null
    }

    fun executeCommand(command: UICommand<T>) {
        val ui = this.ui
        if (null == ui) {
            val iterator = commandQueue.iterator()
            while (iterator.hasNext()) {
                val element = iterator.next()
                if (command.javaClass == element.javaClass) {
                    iterator.remove()
                }
            }
            commandQueue.offer(command)
            return
        }
        command.execute(ui)
    }

    override fun destroy() {} // as default do nothing

}
