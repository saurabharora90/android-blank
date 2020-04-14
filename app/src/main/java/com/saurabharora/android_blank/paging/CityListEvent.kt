package com.saurabharora.android_blank.paging

sealed class CityListEvent {

    sealed class Success : CityListEvent() {
        object SuccessInitial : Success()
        object SuccessPreviousPage : Success()
        object SuccessNextPage : Success()
    }

    object Empty : CityListEvent()

    sealed class Loading : CityListEvent() {
        object LoadingInitial : Loading()
        object LoadingPreviousPage : Loading()
        object LoadingNextPage : Loading()
    }

    sealed class Error(open val throwable: Throwable) : CityListEvent() {
        data class ErrorInitial(override val throwable: Throwable) : Error(throwable)
        data class ErrorPreviousPage(override val throwable: Throwable) : Error(throwable)
        data class ErrorNextPage(override val throwable: Throwable) : Error(throwable)
    }
}

sealed class CityListAction {
    object Refresh : CityListAction()
}