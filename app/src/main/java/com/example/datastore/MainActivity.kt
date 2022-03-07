package com.example.datastore

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import com.example.datastore.databinding.ActivityMainBinding
import com.example.datastore.datastore.UserDataStoreManager
import com.example.datastore.model.Users
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var userDataStoreManager: UserDataStoreManager
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        userDataStoreManager = UserDataStoreManager(applicationContext)

        observeUsers()

        binding.save.setOnClickListener {
            lifecycleScope.launch {
                userDataStoreManager.updateUser(
                    Users(
                        binding.firstname.text.toString(),
                        binding.lastname.text.toString(),
                        arrayListOf("android", "datastore", "proto")
                    )
                )
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun observeUsers() {
        lifecycleScope.launchWhenResumed {
            userDataStoreManager.userFlow.collect {
                binding.info.visibility = when {
                    it.firstName.isNotEmpty() && it.firstName.isNotEmpty() && it.items.isNotEmpty() -> View.VISIBLE
                    else -> View.GONE
                }

                binding.info.text = "${it.firstName}\n${it.lastName}\nList : ${it.items}"
            }
        }
    }
}