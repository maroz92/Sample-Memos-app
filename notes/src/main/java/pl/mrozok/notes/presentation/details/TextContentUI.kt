package pl.mrozok.notes.presentation.details

import pl.mrozok.notes.domain.model.Text
import pl.mrozok.toolbox.presentation.UI

interface TextContentUI : UI {
    fun showContent(textContent: Text)
}