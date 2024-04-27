package com.do55anto5.taskapp.util

import com.do55anto5.taskapp.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class FirebaseHelper {

    companion object {
        fun getDatabase() = Firebase.database.reference

        fun getAuth() = FirebaseAuth.getInstance()

        fun getUserId() = getAuth().currentUser?.uid ?: ""

        fun isAuthenticated() = getAuth().currentUser != null

        fun translateFirebaseAuthErrorMessagesToPortuguese (error: String): Int {
            return when {
                error.contains("The supplied auth credential is incorrect, malformed or has expired") ->{
                    R.string.error_supplied_credentials_incorrect_register_fragment
                }
                error.contains("There is no user record corresponding to this identifier") -> {
                    R.string.error_invalid_email_register_fragment
                }
                error.contains("The email address is badly formatted") -> {
                    R.string.error_invalid_email_register_fragment
                }
                error.contains("The password is invalid or the user does not have a password") -> {
                    R.string.error_invalid_password_register_fragment
                }
                error.contains("The email address is already in use by another account") -> {
                    R.string.error_email_already_registered_register_fragment
                }
                error.contains("Password should be at least 6 characters") -> {
                    R.string.error_weak_password_register_fragment
                }
                else -> {
                    R.string.generic_error
                }
            }
        }
    }
}