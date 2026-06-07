package com.example.nabungemas.data

import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.gotrue.Auth
import io.github.jan.supabase.postgrest.Postgrest

object SupabaseHelper {
    val client = createSupabaseClient(
        supabaseUrl = "https://kxihnnjbsvcapwyucviv.supabase.co",
        supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6Imt4aWhubmpic3ZjYXB3eXVjdml2Iiwicm9sZSI6ImFub24iLCJpYXQiOjE3ODA4NDQyMjcsImV4cCI6MjA5NjQyMDIyN30.nqDd9_z-qWvaE-9jCm0DWMcavFwXVEGBAt5aj4I_wEE"
    ) {
        install(Auth)
        install(Postgrest)
    }
}
