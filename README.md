# 💰NabungEmas App

<p align="center">
  <img src="https://img.shields.io/badge/Platform-Android-3DDC84?style=for-the-badge&logo=android&logoColor=white"/>
  <img src="https://img.shields.io/badge/Language-Kotlin-7F52FF?style=for-the-badge&logo=kotlin&logoColor=white"/>
  <img src="https://img.shields.io/badge/UI-Jetpack%20Compose-4285F4?style=for-the-badge&logo=jetpackcompose&logoColor=white"/>
  <img src="https://img.shields.io/badge/Status-Completed-22C55E?style=for-the-badge"/>
</p>

<p align="center">
  Aplikasi Android untuk manajemen tabungan emas secara terstruktur—catat, pantau, dan raih targetmu.
</p>

---

## Tentang Aplikasi

**NabungEmas** adalah aplikasi mobile berbasis Android yang dirancang untuk membantu pengguna mengelola tabungan emas secara terencana dan terstruktur. Pengguna dapat menetapkan target tabungan emas, mencatat setiap transaksi pembelian, serta memantau progres ketercapaian target secara real-time lengkap dengan referensi harga emas dan kurs mata uang terkini.

Aplikasi ini merupakan hasil **migrasi UI dari XML ke Jetpack Compose** yang memenangkan **Compose Migration Champion Challenge 2023**, kompetisi kolaborasi antara **Google** dan **Dicoding Indonesia**. Pada pengembangan lanjutan ini, kami melakukan sejumlah modifikasi UI untuk pemenuhan proyek akhir mata kuliah **Pengembangan Aplikasi Mobile**.

---

## Kreator Asli

| Informasi | Detail |
|-----------|--------|
| 👤 **Nama** | Muhammad Fatahila |
| 🐙 **GitHub** | [@artfath](https://github.com/artfath) |
| 🏅 **Kompetisi** | Compose Migration Champion Challenge 2023 |
| 🤝 **Penyelenggara** | Google × Dicoding Indonesia |
| 🥇 **Capaian** | Pemenang Resmi (13 Juara Nasional) |
| 🔗 **Repo Asli** | [artfath/Nabung-Emas-App](https://github.com/artfath/Nabung-Emas-App) |

---

## Tim Pengembang — Kelompok 5

Proyek ini dikembangkan lebih lanjut oleh **Kelompok 5** dalam rangka pemenuhan tugas akhir mata kuliah **Pengembangan Aplikasi Mobile**, Program Studi Teknik Informatika.

| No | Nama | NIM |
|----|------|-----|
| 1 | Naufaldi Alfaghani | 245150707111034 |
| 2 | Alfarel Abhipraya | 245150707111041 |
| 3 | Hilal Immawan Steven Ekie | 245150707111060 |
| 4 | Samuel Christopher Tandayu | 245150707111001 |

---

## Fitur Aplikasi

| Fitur | Deskripsi |
|-------|-----------|
| 🏠 **Home Dashboard** | Ringkasan total tabungan, progres keseluruhan, dan transaksi terbaru |
| 💰 **Manajemen Tabungan** | Buat dan kelola target tabungan emas yang ingin dicapai |
| 📋 **Riwayat Transaksi** | Catat setiap pembelian emas beserta harga, tanggal, dan catatan |
| 📈 **Grafik Harga Emas** | Visualisasi pergerakan harga emas dengan line chart interaktif |
| 💱 **Referensi Harga & Kurs** | Harga emas terkini dan kurs USD/EUR yang diperbarui via API |
| ℹ️ **About** | Informasi aplikasi, kreator, dan library yang digunakan |

---

## Tech Stack

| Komponen | Teknologi |
|----------|-----------|
| **Bahasa** | Kotlin |
| **UI Framework** | Jetpack Compose (Material Design 3) |
| **Arsitektur** | MVVM (Model-View-ViewModel) |
| **Database Lokal** | Room Database |
| **Async/Coroutines** | Kotlin Coroutines + Flow |
| **Networking** | Retrofit 2 + OkHttp |
| **Dependency Injection** | Hilt |
| **Navigation** | Jetpack Navigation Compose |
| **Charting** | Composable Graphs |
| **Build System** | Gradle (Kotlin DSL) |
| **Min SDK** | 24 (Android 7.0) |
| **Target SDK** | 34 (Android 14) |

---

## Modifikasi UI

Dalam pengembangan lanjutan ini, Kelompok 5 melakukan sejumlah modifikasi pada antarmuka pengguna (UI) dari versi asli, mencakup:

- **Redesign Home Screen** — Hero card bergradien emas dengan ringkasan total tabungan yang lebih informatif
- **Penyesuaian Color Palette** — Penggunaan sistem warna berbasis token (Gold400 `#F5B93A`, dark background `#1A1814`) yang konsisten di seluruh layar
- **Komponen Baru** — `GoldButton`, `GoldProgressBar`, dan `SavingCard` yang seragam dan reusable
- **Empty State & Error State** — Ilustrasi dan pesan yang lebih ramah pengguna saat data kosong atau koneksi bermasalah
- **Skeleton Loading** — Animasi shimmer placeholder sesuai layout masing-masing screen
- **Dialog Konfirmasi** — Konfirmasi hapus data dengan animasi scale-fade yang halus
- **Dark Mode Support** — Seluruh screen mendukung light dan dark mode secara konsisten

---

## Cara Menjalankan Aplikasi

### Prasyarat

Pastikan kamu sudah menginstal:
- [Android Studio](https://developer.android.com/studio) versi **Flamingo (2022.2.1)** atau lebih baru
- **JDK 17** atau lebih baru
- Perangkat Android (fisik atau emulator) dengan **Android 7.0 (API 24)** ke atas

### Langkah-Langkah

1. **Clone repository ini**
   ```bash
   git clone https://github.com/hilalekie/NabungEmasApp.git
   cd NabungEmasApp
   ```

2. **Buka di Android Studio**
   - Pilih **File → Open**, lalu arahkan ke folder hasil clone

3. **Sinkronisasi Gradle**
   - Klik **Sync Project with Gradle Files** atau tunggu Android Studio melakukannya otomatis

4. **Jalankan aplikasi**
   - Pilih perangkat/emulator di toolbar
   - Klik tombol **▶ Run** atau tekan `Shift + F10`

5. **Build APK (opsional)**
   ```bash
   ./gradlew assembleDebug
   # APK tersedia di: app/build/outputs/apk/debug/app-debug.apk
   ```

---

## Struktur Proyek
NabungEmasApp/
├── app/
│   └── src/main/
│       ├── java/.../nabungemas/
│       │   ├── data/           # Room DB, Repository, API Service
│       │   ├── di/             # Hilt Dependency Injection modules
│       │   ├── domain/         # Use cases & model
│       │   ├── ui/
│       │   │   ├── components/ # Reusable Compose components
│       │   │   ├── screen/     # Screen composables (Home, Saving, Price, About)
│       │   │   └── theme/      # Color, Typography, Theme tokens
│       │   └── viewmodel/      # ViewModels per feature
│       └── res/
├── DESIGN.md                   # Design system documentation
├── README.md
└── build.gradle.kts
---

## Lisensi

Proyek ini dikembangkan untuk keperluan akademik berdasarkan karya asli [Muhammad Fatahila](https://github.com/artfath) yang merupakan pemenang **Compose Migration Champion Challenge 2023**. Seluruh hak cipta atas konsep dan implementasi awal tetap menjadi milik kreator asli.

---

<p align="center">
  Dibuat dengan oleh Kelompok 5 — Pengembangan Aplikasi Mobile
</p>
