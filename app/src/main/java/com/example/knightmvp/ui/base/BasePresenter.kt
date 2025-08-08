package com.example.knightmvp.ui.base

interface Presenter<V> {
    fun attachView(view: V)
    fun detachView()
}
