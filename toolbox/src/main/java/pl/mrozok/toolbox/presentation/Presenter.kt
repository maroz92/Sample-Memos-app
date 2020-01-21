package pl.mrozok.toolbox.presentation

interface Presenter<T : UI> {
    fun attachUI(ui: T)
    fun detachUI()
    fun destroy()
}
