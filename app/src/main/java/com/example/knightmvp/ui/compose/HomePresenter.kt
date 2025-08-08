package com.example.knightmvp.ui.compose

import com.example.knightmvp.ui.base.Presenter
import com.example.knightmvp.ui.base.BaseView

class HomePresenter : Presenter<HomeView> {
    private var view: HomeView? = null

    override fun attachView(view: HomeView) {
        this.view = view
        loadData()
    }

    override fun detachView() {
        this.view = null
    }

    fun loadData() {
        view?.showLoading(true)
        // Здесь может быть асинхронная логика
        view?.showMessage("Данные загружены вручную (MVP)")
        view?.showLoading(false)
    }

    fun onNavigateToSecond() {
        view?.navigateToSecond()
    }
}

interface HomeView : BaseView {
    fun showLoading(isLoading: Boolean)
    fun showMessage(message: String)
    fun showError(error: String)
    fun navigateToSecond()
}
