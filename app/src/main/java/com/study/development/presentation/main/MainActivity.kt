package com.study.development.presentation.main

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.study.development.data.local.SessionPreferences
import com.study.development.data.repository.AuthRepositoryImpl
import com.study.development.domain.usecase.auth.IsLoggedInUseCase
import com.study.development.presentation.catalog.CatalogActivity
import com.study.development.presentation.login.LoginActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val isLoggedIn = IsLoggedInUseCase(AuthRepositoryImpl(SessionPreferences(this)))

        if (isLoggedIn()) {
            startActivity(Intent(this, CatalogActivity::class.java))
        } else {
            startActivity(Intent(this, LoginActivity::class.java))
        }
        finish()
    }
}
