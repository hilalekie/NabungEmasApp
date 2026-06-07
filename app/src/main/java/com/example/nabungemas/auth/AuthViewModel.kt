package com.example.nabungemas.ui.auth

import androidx.lifecycle.ViewModel
import com.example.nabungemas.data.SupabaseHelper
import io.github.jan.supabase.gotrue.auth
import io.github.jan.supabase.gotrue.providers.builtin.Email

class AuthViewModel : ViewModel() {
    private val supabase = SupabaseHelper.client

    suspend fun register(email: String, pass: String): Boolean {
        return try {
            supabase.auth.signUpWith(Email) {
                this.email = email
                this.password = pass
            }
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    suspend fun login(email: String, pass: String): Boolean {
        return try {
            supabase.auth.signInWith(Email) {
                this.email = email
                this.password = pass
            }
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }
}
