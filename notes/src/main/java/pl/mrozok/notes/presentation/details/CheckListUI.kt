package pl.mrozok.notes.presentation.details

import pl.mrozok.toolbox.presentation.UI

interface CheckListUI : UI {
    fun showItems(items: List<CheckListPresenter.CheckListItem>)
}
