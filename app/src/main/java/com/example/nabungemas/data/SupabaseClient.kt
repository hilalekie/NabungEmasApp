package com.example.nabungemas.data

import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.gotrue.Auth
import io.github.jan.supabase.postgrest.Postgrest

object SupabaseClient {
    //  Project URL
    private const val SUPABASE_URL = "https://kxihnnjbsvcapwyucviv.supabase.co"

    // Anon Public Key
    private const val SUPABASE_ANON_KEY = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6Imt4aWhubmpic3ZjYXB3eXVjdml2Iiwicm9sZSI6ImFub24iLCJpYXQiOjE3ODA4NDQyMjcsImV4cCI6MjA5NjQyMDIyN30.nqDd9_z-qWvaE-9jCm0DWMcavFwXVEGBAt5aj4I_wEE"

    val client = createSupabaseClient(SUPABASE_URL, SUPABASE_ANON_KEY) {
        install(Auth.Companion)
        install(Postgrest.Companion)
    }
}
