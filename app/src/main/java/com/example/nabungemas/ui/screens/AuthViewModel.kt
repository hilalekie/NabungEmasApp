package com.example.nabungemas.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nabungemas.UIState
import com.example.nabungemas.data.SupabaseClient
import io.github.jan.supabase.gotrue.auth
import io.github.jan.supabase.gotrue.providers.builtin.Email
import io.github.jan.supabase.postgrest.postgrest
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
    fun signUp(emailInput: String, passwordInput: String, fullName: String, phone: String = "") {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            try {
                SupabaseClient.client.auth.signUpWith(io.github.jan.supabase.gotrue.providers.builtin.Email) {
                    email = emailInput
                    password = passwordInput
                    data = kotlinx.serialization.json.buildJsonObject {
                        put("full_name", kotlinx.serialization.json.JsonPrimitive(fullName))
                    }
                }
                // Setelah signup berhasil, buat baris profil di tabel profiles
                // Ini lebih handal daripada trigger database karena kita tahu skema tabel
                createProfileForCurrentUser(fullName, phone)
                _authState.value = "REGISTER_SUCCESS"
            } catch (e: Exception) {
                _errorMessage.value = e.localizedMessage ?: "Registrasi gagal, periksa kembali data Anda."
            } finally {
                _isLoading.value = false
            }
        }
    }

    // Buat baris profil di tabel public.profiles setelah signup
    private suspend fun createProfileForCurrentUser(fullName: String, phone: String) {
        val user = SupabaseClient.client.auth.currentUserOrNull() ?: return
        try {
            SupabaseClient.client.postgrest["profiles"].insert(
                kotlinx.serialization.json.buildJsonObject {
                    put("id", kotlinx.serialization.json.JsonPrimitive(user.id))
                    put("full_name", kotlinx.serialization.json.JsonPrimitive(fullName))
                }
            )
        } catch (e: Exception) {
            e.printStackTrace()
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

    // Ambil tanggal pendaftaran
    fun getCurrentUserCreatedAtFormatted(): String {
        val user = SupabaseClient.client.auth.currentUserOrNull()
        val createdAt = user?.createdAt
        if (createdAt != null) {
            try {
                val date = java.util.Date(createdAt.toEpochMilliseconds())
                val format = java.text.SimpleDateFormat("MMMM yyyy", java.util.Locale("id", "ID"))
                return "Member sejak ${format.format(date)}"
            } catch (e: Exception) {
                // Fallback
            }
        }
        return "Member sejak 2024"
    }

    // Fungsi untuk menyimpan perubahan profil (Edit Profile)
    fun updateProfile(fullName: String, newEmail: String, onComplete: (Boolean, String?) -> Unit) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val user = SupabaseClient.client.auth.currentUserOrNull()
                if (user != null) {
                    // 1. Update nama di public.profiles
                    SupabaseClient.client.postgrest["profiles"].update(
                        {
                            set("full_name", fullName)
                        }
                    ) {
                        filter {
                            eq("id", user.id)
                        }
                    }

                    // 2. Update auth metadata (termasuk full_name di session auth)
                    SupabaseClient.client.auth.modifyUser {
                        data = kotlinx.serialization.json.buildJsonObject {
                            put("full_name", kotlinx.serialization.json.JsonPrimitive(fullName))
                        }
                        // Update email (Catatan: ini mungkin memicu konfirmasi email jika diaktifkan di Supabase)
                        if (newEmail.isNotBlank() && newEmail != user.email) {
                            email = newEmail
                        }
                    }
                    onComplete(true, null)
                } else {
                    onComplete(false, "User belum login.")
                }
            } catch (e: Exception) {
                e.printStackTrace()
                onComplete(false, e.localizedMessage)
            } finally {
                _isLoading.value = false
            }
        }
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
