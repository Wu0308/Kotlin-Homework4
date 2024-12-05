package com.example.lab13_kotlinnew

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MessageViewModel : ViewModel() {
    // 用來儲存訊息
    private val _message = MutableLiveData<String>()
    val message: LiveData<String> get() = _message

    // 更新訊息
    fun updateMessage(msg: String) {
        _message.value = msg
    }
}