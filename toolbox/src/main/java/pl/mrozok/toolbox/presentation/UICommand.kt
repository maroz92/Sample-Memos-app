package pl.mrozok.toolbox.presentation

interface UICommand<T : UI> {
    fun execute(ui: T)
}
