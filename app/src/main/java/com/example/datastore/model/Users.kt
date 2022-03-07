package com.example.datastore.model

data class Users(
    var firstName: String,
    var lastName: String,
    val items: List<String>
)