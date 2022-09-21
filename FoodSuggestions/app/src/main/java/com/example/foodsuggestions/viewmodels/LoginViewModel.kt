package com.example.foodsuggestions.viewmodels

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.hadilq.liveevent.LiveEvent


class LoginViewModel : ViewModel() {
    val mail = MutableLiveData("test@test.com")
    val password = MutableLiveData("123456")

    val mailError = MutableLiveData<Boolean>(false)
    val passwordError = MutableLiveData<Boolean>(false)

    val navigateToRegisterEvent = LiveEvent<Unit>()
    val navigateToHomeEvent = LiveEvent<Unit>()

    private val _loading = MutableLiveData(false)
    val loading: LiveData<Boolean>
        get() = _loading

    val status = MutableLiveData<Boolean>(null)

    fun userLogin() {

        val loginMail = mail.value!!
        val loginPassword = password.value!!

        validate()

        if (mailError.value == false && passwordError.value == false){
            _loading.value = true
            FirebaseAuth.getInstance().signInWithEmailAndPassword(loginMail, loginPassword)
                .addOnCompleteListener(OnCompleteListener<AuthResult?> { task ->
                    _loading.postValue(false)
                    if (task.isSuccessful) {
                        navigateToHomeEvent.postValue(Unit)
                    } else {
                        status.value = true
                    }

                })
        }
    }

    private fun validate() {
        mailError.value = mail.value.isNullOrBlank() || !Patterns.EMAIL_ADDRESS.matcher(mail.value!!).matches()
        passwordError.value = password.value.isNullOrBlank() || password.value!!.length < 6
    }

    fun navigateToRegisterScreen() {
        navigateToRegisterEvent.value = Unit
    }

}