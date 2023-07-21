package com.longhrk.app.ui

import com.longhrk.app.ui.event.NavEvent
import com.longhrk.app.ui.viewmodel.NavViewModel

class EventHandler(
    private val navigationViewModel: NavViewModel
) {
    fun postNavEvent(event: NavEvent){
        navigationViewModel.updateEvent(event)
    }
    fun navEvent() = navigationViewModel.event

    override fun toString(): String {
        return navEvent().value.toString()
    }
}