package com.example.datastore.datastore

import android.content.Context
import com.example.datastore.datastore.UserSerializer.userDataStore
import com.example.datastore.model.Users
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserDataStoreManager(private val context: Context) {
    private val dataStore
        get() = context.userDataStore

    val userFlow: Flow<Users> = dataStore.data.map { preferences ->
        val firstName = preferences.firstname
        val lastName = preferences.lastname

        Users(firstName, lastName, preferences.itemsList)
    }

    suspend fun updateUser(user: Users) {
        dataStore.updateData {
            it.toBuilder()
                .setFirstname(user.firstName)
                .setLastname(user.lastName)
                .clearItems()
                .addAllItems(user.items)
                .build()
        }
    }
}