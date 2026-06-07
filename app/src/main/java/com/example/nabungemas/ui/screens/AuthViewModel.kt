package com.example.nabungemas.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nabungemas.UIState // Memanggil sealed interface UIState kelompok
import com.example.nabungemas.data.SupabaseClient
import io.github.jan.supabase.gotrue.auth // Menggunakan jan.supabase sesuai SupabaseClient-mu
import io.github.jan.supabase.gotrue.providers.builtin.Email // Menggunakan jan.supabase sesuai SupabaseClient-mu
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AuthViewModel : ViewModel() {

    private val _authState = MutableStateFlow<String?>(null)
    val authState: StateFlow<String?> = _authState

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    // Fungsi Registrasi ke Supabase Auth Cloud
    fun signUp(emailInput: String, passwordInput: String, fullName: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            try {
                SupabaseClient.client.auth.signUpWith(io.github.jan.supabase.gotrue.providers.builtin.Email) {
                    email = emailInput
                    password = passwordInput

                    // PERBAIKAN: Menggunakan JsonPrimitive agar string dikonversi menjadi JsonElement yang valid
                    data = kotlinx.serialization.json.buildJsonObject {
                        put("full_name", kotlinx.serialization.json.JsonPrimitive(fullName))
                    }
                }
                _authState.value = "REGISTER_SUCCESS"
            } catch (e: Exception) {
                _errorMessage.value = e.localizedMessage ?: "Registrasi gagal, periksa kembali data Anda."
            } finally {
                _isLoading.value = false
            }
        }
    }

    // Fungsi Login ke Supabase Auth Cloud
    fun signIn(emailInput: String, passwordInput: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            try {
                SupabaseClient.client.auth.signInWith(Email) {
                    email = emailInput
                    password = passwordInput
                }
                _authState.value = "LOGIN_SUCCESS"
            } catch (e: Exception) {
                _errorMessage.value = e.localizedMessage ?: "Email atau Password salah."
            } finally {
                _isLoading.value = false
            }
        }
    }

    // Ambil nama user aktif saat ini untuk halaman Profile
    fun getCurrentUserFullName(): String {
        val user = SupabaseClient.client.auth.currentUserOrNull()
        val metadata = user?.userMetadata
        return metadata?.get("full_name")?.toString()?.replace("\"", "") ?: "Pengguna Emas"
    }

    // Ambil email user aktif
    fun getCurrentUserEmail(): String {
        return SupabaseClient.client.auth.currentUserOrNull()?.email ?: "email@pribadi.com"
    }

    // Fungsi Logout
    fun signOut(onComplete: () -> Unit) {
        viewModelScope.launch {
            try {
                SupabaseClient.client.auth.signOut()
                _authState.value = null
                onComplete()
            } catch (e: Exception) {
                // handle error
            }
        }
    }

    fun clearError() {
        _errorMessage.value = null
    }
}
