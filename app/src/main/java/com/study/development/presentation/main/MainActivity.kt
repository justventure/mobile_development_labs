package com.study.development.presentation.main

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.study.development.application.use_cases.auth.IsLoggedInUseCase
import com.study.development.presentation.login.LoginActivity
import com.study.development.presentation.main.ui.MainHostActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var isLoggedInUseCase: IsLoggedInUseCase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        startActivity(
            if (isLoggedInUseCase()) {
                Intent(this, MainHostActivity::class.java)
            } else {
                Intent(this, LoginActivity::class.java)
            }
        )

        finish()
    }
}
