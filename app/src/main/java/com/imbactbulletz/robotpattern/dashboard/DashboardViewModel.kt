package com.imbactbulletz.robotpattern.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.imbactbulletz.robotpattern.R

class DashboardViewModel: ViewModel() {

    private val _dashboardScreenState: MutableLiveData<DashboardScreenState> =
        MutableLiveData(DashboardScreenState.Counter(0))

    val dashboardScreenState: LiveData<DashboardScreenState> = _dashboardScreenState

    private val _dashboardScreenEvent: MutableLiveData<DashboardScreenEvent> = MutableLiveData()
    val dashboardScreenEvent: LiveData<DashboardScreenEvent> = _dashboardScreenEvent

    private var counterValue: Int = 0

    fun onIncrementCounter() {
        counterValue++
        _dashboardScreenState.value = DashboardScreenState.Counter(counterValue)
    }

    fun onDecrementCounter() {
        if (counterValue > 0) {
            counterValue--
            _dashboardScreenState.value = DashboardScreenState.Counter(counterValue)
        } else {
            _dashboardScreenEvent.value = DashboardScreenEvent.Error(
                errorMessageResource = R.string.decrement_error
            )
        }
    }

}

sealed class DashboardScreenState {
    data class Counter(val value: Int): DashboardScreenState()
}

sealed class DashboardScreenEvent {
    data class Error(val errorMessageResource: Int): DashboardScreenEvent()
}