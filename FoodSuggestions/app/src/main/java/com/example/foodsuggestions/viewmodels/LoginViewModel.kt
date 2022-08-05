package com.example.foodsuggestions.viewmodels

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.hadilq.liveevent.LiveEvent


class LoginViewModel: ViewModel() {
    val mail = MutableLiveData("test@test.com")
    val password = MutableLiveData("123456")

    val navigateToRegisterEvent = LiveEvent<Unit>()
    val navigateToHomeEvent = LiveEvent<Unit>()

    private val _loading = MutableLiveData(false)
    val loading: LiveData<Boolean>
        get() = _loading

    fun userLogin() {
        _loading.value = true
        val loginMail = mail.value!!
        val loginPassword = password.value!!
        FirebaseAuth.getInstance().signInWithEmailAndPassword(loginMail, loginPassword)
            .addOnCompleteListener(OnCompleteListener<AuthResult?> { task ->
                _loading.postValue(false)
                if (task.isSuccessful) {
                    navigateToHomeEvent.postValue(Unit)
                } else {
//                    Toast.makeText(
//                        this@LoginActivity,
//                        "Filed to login! Please check your credentials or register.",
//                        Toast.LENGTH_SHORT
//                    ).show()
                }

            })

    }

    private fun validate() {
//        val emailLogin: String = binding.idLEmail.getText().toString().trim()
//        val passwordLogin: String = binding.idLPassword.getText().toString().trim()
//
//        if (loginViewModel.mail.getValue().isEmpty()) {
//            binding.idLEmail.setError("Email is required!")
//            binding.idLEmail.requestFocus()
//            return
//        }
//        if (!Patterns.EMAIL_ADDRESS.matcher(emailLogin).matches()) {
//            binding.idLEmail.setError("Please provide valid email")
//            binding.idLEmail.requestFocus()
//            return
//        }
//        if (passwordLogin.isEmpty()) {
//            binding.idLPassword.setError("Email is required!")
//            binding.idLPassword.requestFocus()
//            return
//        }
//        if (passwordLogin.length < 6) {
//            binding.idLPassword.setError("Min password length should be 6 characters!")
//            binding.idLPassword.requestFocus()
//            return
//        }
    }

    fun navigateToRegisterScreen() {
        navigateToRegisterEvent.value = Unit
    }
}