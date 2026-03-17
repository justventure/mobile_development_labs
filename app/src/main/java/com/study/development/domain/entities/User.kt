package com.study.development.domain.entities

data class User(
    val name: String,
    val email: String
) {
    init {
        require(email.contains("@")) { "Invalid email" }
        require(name.isNotBlank()) { "Name cannot be blank" }
    }
}
