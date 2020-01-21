package pl.mrozok.notes.presentation.details

import pl.mrozok.notes.domain.model.CheckList
import pl.mrozok.toolbox.presentation.UI

interface CheckListContentUI : UI {
    fun showContent(checkListContent: CheckList)
}