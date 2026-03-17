package com.study.development

import android.app.Application
import com.study.development.data.local.CartStorage
import com.study.development.data.repository.CartRepositoryImpl

class App : Application() {
    val cartRepository by lazy { CartRepositoryImpl(CartStorage(this)) }
}
