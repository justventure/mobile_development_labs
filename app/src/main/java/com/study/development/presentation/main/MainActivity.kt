package com.study.development.presentation.main

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.study.development.R
import com.study.development.application.use_cases.auth.IsLoggedInUseCase
import com.study.development.infrastructure.data.dao.CartDao
import com.study.development.infrastructure.data.entity.ProductEntity
import com.study.development.presentation.login.LoginActivity
import com.study.development.presentation.main.ui.MainHostActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var isLoggedInUseCase: IsLoggedInUseCase

    @Inject
    lateinit var dao: CartDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        seedDatabase()

        startActivity(
            if (isLoggedInUseCase()) {
                Intent(this, MainHostActivity::class.java)
            } else {
                Intent(this, LoginActivity::class.java)
            }
        )

        finish()
    }

    private fun seedDatabase() {
        CoroutineScope(Dispatchers.IO).launch {
            val products = listOf(
                ProductEntity(1, "Apple", 2.0, R.drawable.apple, "Fresh red apple from local farms."),
                ProductEntity(2, "Banana", 1.5, R.drawable.banana, "Sweet yellow banana, rich in potassium."),
                ProductEntity(3, "Orange", 3.0, R.drawable.orange, "Juicy orange packed with vitamin C."),
                ProductEntity(4, "Mango", 4.0, R.drawable.mango, "Tropical mango with sweet aroma."),
                ProductEntity(5, "Grapes", 2.5, R.drawable.grapes, "Seedless green grapes, perfect for snacking."),
                ProductEntity(7, "Watermelon", 5.0, R.drawable.watermelon, "Large juicy watermelon, perfect for summer."),
                ProductEntity(8, "Pineapple", 4.5, R.drawable.pineapple, "Tropical pineapple with tangy sweet taste."),
                ProductEntity(9, "Watermelon", 5.0, R.drawable.watermelon, "Large juicy watermelon, perfect for summer."),
                ProductEntity(10, "Pineapple", 4.5, R.drawable.pineapple, "Tropical pineapple with tangy sweet taste."),
                ProductEntity(11, "Pineapple", 4.5, R.drawable.pineapple, "Tropical pineapple with tangy sweet taste.")
            )

            products.forEach {
                dao.insertProduct(it)
            }
        }
    }
}
