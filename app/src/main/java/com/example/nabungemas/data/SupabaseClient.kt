package com.example.nabungemas.data

import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.gotrue.Auth
import io.github.jan.supabase.postgrest.Postgrest

object SupabaseClient {
    //  Project URL
    private const val SUPABASE_URL = "https://kxihnnjbsvcapwyucviv.supabase.co"

    // Anon Public Key
    private const val SUPABASE_ANON_KEY = "sb_secret_1JzMm2mzV7gO3kFTolLHAA_S4-Ggzdx"

    val client = createSupabaseClient(SUPABASE_URL, SUPABASE_ANON_KEY) {
        install(Auth.Companion)
        install(Postgrest.Companion)
    }
}
