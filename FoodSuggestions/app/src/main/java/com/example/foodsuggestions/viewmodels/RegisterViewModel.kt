package com.example.foodsuggestions.viewmodels

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.foodsuggestions.models.User
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.hadilq.liveevent.LiveEvent

class RegisterViewModel : ViewModel() {

    val fullName = MutableLiveData("")
    val mail = MutableLiveData("")
    val password = MutableLiveData("")
    val confirmPassword = MutableLiveData("")

    val fullNameError = MutableLiveData<Boolean>(false)
    val mailError = MutableLiveData<Boolean>(false)
    val passwordError = MutableLiveData<Boolean>(false)
    val confirmPasswordError = MutableLiveData<Boolean>(false)

    val navigateToLoginEvent = LiveEvent<Unit>()

    private val _loading = MutableLiveData(false)
    val loading: LiveData<Boolean>
        get() = _loading

    val status = MutableLiveData<Boolean>(null)

    fun navigateToLoginScreen() {
        navigateToLoginEvent.value = Unit
    }

    fun userRegister() {
        val registerMail = mail.value!!
        val registerPassword = password.value!!
        val registerName = fullName.value!!

        validateRegister()
        if (fullNameError.value == false && mailError.value == false
            && passwordError.value == false && confirmPasswordError.value == false
        ) {

            _loading.value = true
            FirebaseAuth.getInstance()
                .createUserWithEmailAndPassword(registerMail, registerPassword)
                .addOnCompleteListener(OnCompleteListener<AuthResult>() { task ->
                    if (task.isSuccessful) {
                        val user = User(registerName, registerMail)
                        FirebaseDatabase
                            .getInstance("https://registerandloginproject-default-rtdb.europe-west1.firebasedatabase.app/")
                            .getReference("users")
                            .child(FirebaseAuth.getInstance().getCurrentUser()!!.getUid())
                            .setValue(user)
                            .addOnCompleteListener(OnCompleteListener { task ->
                                _loading.postValue(false)
                                if (task.isSuccessful) {
                                    navigateToLoginEvent.postValue(Unit)
                                }
                            })
                    } else {
                        status.value = true
                        _loading.value = false
                    }
                })
        }
    }

    fun validateRegister() {
        fullNameError.value = fullName.value.isNullOrBlank()
        mailError.value =
            mail.value.isNullOrBlank() || !Patterns.EMAIL_ADDRESS.matcher(mail.value!!).matches()
        passwordError.value = password.value.isNullOrBlank() || password.value!!.length < 6
        confirmPasswordError.value =
            confirmPassword.value.isNullOrBlank() || !confirmPassword.value.equals(password.value)
    }
}