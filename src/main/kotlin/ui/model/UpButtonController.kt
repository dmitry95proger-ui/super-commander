package ui.model

import domain.model.ViewMode

class UpButtonController(
    private var currentColumnPresenter: ColumnPresenter? = null,
    private var opColumnPresenter: ColumnPresenter? = null,
    private var columnController: ColumnController? = null
) {

    fun setPresenters(currentColumnPresenter: ColumnPresenter, opColumnPresenter: ColumnPresenter){
        this.currentColumnPresenter = currentColumnPresenter
        this.opColumnPresenter = opColumnPresenter
    }

    fun setColumnController(columnController: ColumnController?){
        this.columnController = columnController
    }

    fun onEvent(event: UserUpButtonEvent){

        val viewMode = currentColumnPresenter?.content?.viewMode ?: return

        when(event) {
            is UserUpButtonEvent.F2 -> {
                when(viewMode){
                    ViewMode.LIST_ITEMS -> {
                        columnController?.onEvent(
                            event = UserColumnEvent.ShowDialog(dialog = DialogWindow.EDIT),
                            columnPresenter = currentColumnPresenter!!,
                            opColumnPresenter = opColumnPresenter!!
                        )
                    }
                    else -> {
                        columnController?.onEvent(
                            event = UserColumnEvent.ShowDialog(dialog = DialogWindow.ERROR_SELECTED_FILE),
                            columnPresenter = currentColumnPresenter!!,
                            opColumnPresenter = opColumnPresenter!!
                        )
                    }
                }
            }
            is UserUpButtonEvent.F3 -> {
                when(viewMode){
                    ViewMode.LIST_ITEMS -> {
                        val dialog = if(currentColumnPresenter?.content?.selectedListFiles?.isEmpty() == true) DialogWindow.CUT else DialogWindow.CUT_LIST
                        columnController?.onEvent(
                            event = UserColumnEvent.ShowDialog(dialog = dialog),
                            columnPresenter = currentColumnPresenter!!,
                            opColumnPresenter = opColumnPresenter!!
                        )
                    }
                    else -> {
                        columnController?.onEvent(
                            event = UserColumnEvent.ShowDialog(dialog = DialogWindow.ERROR_SELECTED_FILE),
                            columnPresenter = currentColumnPresenter!!,
                            opColumnPresenter = opColumnPresenter!!
                        )
                    }
                }


            }
            is UserUpButtonEvent.F4 -> {
                when(viewMode){
                    ViewMode.LIST_ITEMS -> {
                        val dialog = if(currentColumnPresenter?.content?.selectedListFiles?.isEmpty() == true) DialogWindow.COPY else DialogWindow.COPY_LIST
                        columnController?.onEvent(
                            event = UserColumnEvent.ShowDialog(dialog = dialog),
                            columnPresenter = currentColumnPresenter!!,
                            opColumnPresenter = opColumnPresenter!!
                        )
                    }
                    else -> {
                        columnController?.onEvent(
                            event = UserColumnEvent.ShowDialog(dialog = DialogWindow.ERROR_SELECTED_FILE),
                            columnPresenter = currentColumnPresenter!!,
                            opColumnPresenter = opColumnPresenter!!
                        )
                    }
                }
            }
            is UserUpButtonEvent.F5 -> {
                when(viewMode){
                    ViewMode.LIST_ITEMS -> {
                        val dialog = if(currentColumnPresenter?.content?.selectedListFiles?.isEmpty() == true) DialogWindow.DELETE else DialogWindow.DELETE_LIST
                        columnController?.onEvent(
                            event = UserColumnEvent.ShowDialog(dialog = dialog),
                            columnPresenter = currentColumnPresenter!!,
                            opColumnPresenter = opColumnPresenter!!
                        )
                    }
                    else -> {
                        columnController?.onEvent(
                            event = UserColumnEvent.ShowDialog(dialog = DialogWindow.ERROR_SELECTED_FILE),
                            columnPresenter = currentColumnPresenter!!,
                            opColumnPresenter = opColumnPresenter!!
                        )
                    }
                }
            }
            is UserUpButtonEvent.F6 -> {
                when(viewMode){
                    ViewMode.LIST_ITEMS -> {
                        columnController?.onEvent(
                            event = UserColumnEvent.ShowDialog(dialog = DialogWindow.ADD_FILE),
                            columnPresenter = currentColumnPresenter!!,
                            opColumnPresenter = opColumnPresenter!!
                        )
                    }
                    else -> {
                        columnController?.onEvent(
                            event = UserColumnEvent.ShowDialog(dialog = DialogWindow.ERROR_SELECTED_DIRECTORY),
                            columnPresenter = currentColumnPresenter!!,
                            opColumnPresenter = opColumnPresenter!!
                        )
                    }
                }
            }
            is UserUpButtonEvent.F7 -> {
                when(viewMode){
                    ViewMode.LIST_ITEMS -> {
                        columnController?.onEvent(
                            event = UserColumnEvent.ShowDialog(dialog = DialogWindow.ADD_FOLDER),
                            columnPresenter = currentColumnPresenter!!,
                            opColumnPresenter = opColumnPresenter!!
                        )
                    }
                    else -> {
                        columnController?.onEvent(
                            event = UserColumnEvent.ShowDialog(dialog = DialogWindow.ERROR_SELECTED_DIRECTORY),
                            columnPresenter = currentColumnPresenter!!,
                            opColumnPresenter = opColumnPresenter!!
                        )
                    }
                }
            }
        }
    }
}