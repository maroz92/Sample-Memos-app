package pl.mrozok.toolbox.presentation.reactivex

import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers

data class SchedulersHolder(
    val observingScheduler: Scheduler,
    val subscribingScheduler: Scheduler
) {

    companion object {
        fun immediateSchedulersHolder(): SchedulersHolder =
            SchedulersHolder(Schedulers.trampoline(), Schedulers.trampoline())
    }

}
