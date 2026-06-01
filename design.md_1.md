# DESIGN.md — NabungEmas App

> Design specification dan panduan UI/UX untuk aplikasi **NabungEmas** — aplikasi manajemen tabungan emas berbasis Android dengan Kotlin + Jetpack Compose.

---

## 1. Overview

**NabungEmas** adalah aplikasi mobile Android yang membantu pengguna mengelola target tabungan emas, mencatat transaksi pembelian, serta memantau harga emas dan kurs mata uang secara real-time.

- **Platform:** Android (min SDK 24 / Android 7.0)
- **UI Framework:** Jetpack Compose
- **Language:** Kotlin
- **Theme System:** Material Design 3 (Material You)

---

## 2. Design Philosophy

| Prinsip | Penerapan |
|---------|-----------|
| **Clean & Modern** | Mengutamakan ruang kosong (whitespace), hierarki visual yang jelas, dan tipografi yang kuat |
| **Eye-Friendly Brightness** | Aksen cerah namun tidak saturasi tinggi — menggunakan tone amber-gold yang warm dan nyaman |
| **Purposeful Interaction** | Setiap elemen interaktif memiliki feedback visual (ripple, scale, elevation) |
| **Consistency** | Komponen yang sama diperlakukan identik di seluruh halaman |
| **Data-First** | Angka dan progres menjadi elemen visual utama, bukan dekorasi |

---

## 3. Color Palette

### Primary Colors

| Token | Hex | Penggunaan |
|-------|-----|------------|
| `Gold400` | `#F5B93A` | Primary accent, CTA button, active state |
| `Gold300` | `#F7CA6A` | Gradient pair, highlight |
| `Gold200` | `#FAE0A8` | Subtle accent, chip background |
| `Gold100` | `#FEF5E1` | Surface tint, card background tint |

### Neutral Colors

| Token | Hex | Penggunaan |
|-------|-----|------------|
| `Neutral900` | `#1A1814` | Primary text (dark mode surface) |
| `Neutral800` | `#2C2A25` | Card background (dark mode) |
| `Neutral700` | `#3E3C37` | Secondary surface (dark mode) |
| `Neutral200` | `#EDEBE6` | Divider, skeleton loader |
| `Neutral100` | `#F8F6F2` | Page background (light mode) |
| `Neutral050` | `#FDFCFA` | Card background (light mode) |

### Semantic Colors

| Token | Hex | Penggunaan |
|-------|-----|------------|
| `Success500` | `#22C55E` | Progres selesai, positif |
| `Success100` | `#DCFCE7` | Success chip background |
| `Error500` | `#EF4444` | Error state, harga turun |
| `Error100` | `#FEE2E2` | Error chip background |
| `Info500` | `#3B82F6` | Info badge, harga stabil |
| `Warning500` | `#F59E0B` | Warning state |

### Gradient Definitions

```
GoldGradient:     #F5B93A → #F7CA6A  (direction: 135°)
GoldSurface:      #F5B93A1A → #F7CA6A0D  (subtle card tint, 10-6% opacity)
DarkOverlay:      #1A1814CC (80% opacity) — for modal backdrop
```

---

## 4. Typography

Font utama: **Plus Jakarta Sans** (Google Fonts)

| Style Token | Size | Weight | Line Height | Penggunaan |
|-------------|------|--------|-------------|------------|
| `Display/Large` | 32sp | 700 (Bold) | 40sp | Hero angka besar (total saving) |
| `Display/Medium` | 28sp | 700 (Bold) | 36sp | Heading halaman utama |
| `Headline/Large` | 24sp | 600 (SemiBold) | 32sp | Section title |
| `Headline/Medium` | 20sp | 600 (SemiBold) | 28sp | Card title, dialog title |
| `Title/Large` | 18sp | 600 (SemiBold) | 26sp | List item title |
| `Title/Medium` | 16sp | 500 (Medium) | 24sp | Subheading, label form |
| `Body/Large` | 16sp | 400 (Regular) | 24sp | Body text utama |
| `Body/Medium` | 14sp | 400 (Regular) | 20sp | Deskripsi, metadata |
| `Label/Large` | 14sp | 500 (Medium) | 20sp | Button text, tab label |
| `Label/Medium` | 12sp | 500 (Medium) | 16sp | Chip, badge, caption |
| `Label/Small` | 11sp | 400 (Regular) | 16sp | Timestamp, helper text |

---

## 5. Spacing System

Menggunakan **8dp base grid**:

| Token | Value | Penggunaan |
|-------|-------|------------|
| `space-2` | 2dp | Micro gap (ikon-teks) |
| `space-4` | 4dp | Tight spacing |
| `space-8` | 8dp | Default inner padding |
| `space-12` | 12dp | Card inner padding kecil |
| `space-16` | 16dp | Standard padding, gap antar elemen |
| `space-20` | 20dp | Medium spacing |
| `space-24` | 24dp | Section spacing |
| `space-32` | 32dp | Large section gap |
| `space-48` | 48dp | XL spacing, hero section |
| `space-64` | 64dp | Bottom nav safe area |

**Page horizontal padding:** 20dp kiri-kanan (screen edge)

---

## 6. Shape / Corner Radius

| Token | Value | Penggunaan |
|-------|-------|------------|
| `radius-xs` | 6dp | Chip, tag kecil |
| `radius-sm` | 10dp | Button pill kecil, badge |
| `radius-md` | 16dp | Card standar |
| `radius-lg` | 20dp | Card hero, modal sheet |
| `radius-xl` | 28dp | Bottom sheet, FAB |
| `radius-full` | 999dp | Circular button, avatar |

---

## 7. Elevation & Shadow

```
Elevation-0:  No shadow (flat)
Elevation-1:  0 1dp 2dp rgba(0,0,0,0.06)    — default card
Elevation-2:  0 4dp 12dp rgba(0,0,0,0.08)   — card hover, floating
Elevation-3:  0 8dp 24dp rgba(0,0,0,0.10)   — dialog, bottom sheet
Elevation-4:  0 16dp 48dp rgba(0,0,0,0.12)  — full-screen modal
```

---

## 8. Component Library

### 8.1 Primary Button (GoldButton)

- Background: `GoldGradient` (#F5B93A → #F7CA6A)
- Text: `Neutral900`, `Label/Large`, weight 600
- Corner radius: `radius-xl` (28dp)
- Min height: 52dp
- Padding: 16dp vertikal, 24dp horizontal
- Shadow: `Elevation-2`
- Pressed state: scale 0.97, brightness -5%
- Disabled: opacity 40%, `Neutral200` background

### 8.2 Secondary Button (OutlineButton)

- Background: transparent
- Border: 1.5dp solid `Gold400`
- Text: `Gold400`, `Label/Large`
- Corner radius: `radius-xl`
- Min height: 52dp

### 8.3 Text Button

- No background, no border
- Text: `Gold400`, `Label/Large`
- Ripple: `Gold200`

### 8.4 Card (SavingCard)

- Background: `Neutral050` (light) / `Neutral800` (dark)
- Corner radius: `radius-md` (16dp)
- Padding: 20dp
- Shadow: `Elevation-1`
- Border (optional): 1dp `Gold200` untuk active state
- Hover/press: `Elevation-2`, slight upward translate (-2dp)

### 8.5 Progress Bar (GoldProgressBar)

- Track: `Neutral200` (light) / `Neutral700` (dark), height 8dp, radius full
- Fill: `GoldGradient`, animated expand dari kiri
- Dengan percentage label di kanan

### 8.6 Bottom Navigation Bar

- Background: `Neutral050` (light) / `Neutral800` (dark)
- Top border: 1dp `Neutral200`
- Icon size: 24dp
- Active icon: `Gold400`, filled
- Inactive icon: `Neutral400`, outlined
- Active indicator pill: `Gold100` background, width 64dp, height 32dp, radius full
- Label: `Label/Small`, `Gold400` (active) / `Neutral500` (inactive)

### 8.7 Top App Bar

- Background: sama dengan page background (merged)
- Title: `Headline/Medium`, center-aligned atau start-aligned
- Navigation icon: 24dp, ripple
- Actions: max 2 icon di kanan

### 8.8 FAB (Floating Action Button)

- Background: `GoldGradient`
- Icon: putih, 24dp
- Size: 56dp × 56dp
- Corner radius: `radius-full`
- Shadow: `Elevation-3`
- Extended FAB: radius `radius-xl`, padding 16dp-20dp, icon + label

### 8.9 Input Field (TextField)

- Style: Outlined
- Border radius: `radius-md` (16dp)
- Border default: 1dp `Neutral300`
- Border focused: 2dp `Gold400`
- Label: `Body/Medium`, `Gold400` saat focused
- Background: `Neutral050`
- Text: `Body/Large`, `Neutral900`
- Helper text: `Label/Small`, `Neutral500`
- Error border: `Error500`
- Min height: 56dp

### 8.10 Chip

- Background: `Gold100`, border `Gold300`
- Text: `Label/Medium`, `Gold500`
- Corner radius: `radius-xs`
- Padding: 8dp vertikal, 12dp horizontal

### 8.11 Skeleton Loader

- Base: `Neutral200` (light) / `Neutral700` (dark)
- Shimmer: animated gradient sweep dari kiri ke kanan
- Duration: 1.2s, ease-in-out, infinite
- Shape sesuai elemen yang di-load

### 8.12 Snackbar / Toast

- Background: `Neutral800` (dark) / `Neutral900` (dark mode)
- Text: white, `Body/Medium`
- Action text: `Gold300`
- Corner radius: `radius-md`
- Padding: 14dp 16dp
- Duration: 3000ms
- Muncul dari bawah dengan animasi slide-up + fade-in

### 8.13 Dialog

- Background: `Neutral050` (light) / `Neutral800` (dark)
- Corner radius: `radius-lg` (20dp)
- Padding: 24dp
- Backdrop: `DarkOverlay`
- Muncul dengan animasi scale-in + fade-in dari center

---

## 9. Screen Inventory

### Navigasi Utama (Bottom Navigation)
1. **Home Screen** — tab 1
2. **Saving Screen** — tab 2
3. **Price Screen** — tab 3
4. **About Screen** — tab 4

### Full Screens
5. **Splash Screen**
6. **Add Saving Screen**
7. **Saving Detail Screen**
8. **Edit Saving Screen**
9. **Add Transaction Screen**
10. **Transaction Detail Screen**
11. **Edit Transaction Screen**
12. **Transaction List Screen**

### Overlays / Dialogs
13. **Delete Saving Confirmation Dialog**
14. **Delete Transaction Confirmation Dialog**

### States
15. **Empty State** (Saving List, Transaction List)
16. **Loading State** (Skeleton Loader)
17. **Error State** (API gagal, no connection)

---

## 10. Screen Specifications

### 10.1 Splash Screen
- Background: full gradient `Gold400 → Gold300` (135°)
- Logo NabungEmas: centered, putih, animated scale-in
- Tagline: "*Kelola target, raih impianmu*", putih, fade-in
- Durasi: 2 detik → navigate ke Home

### 10.2 Home Screen
- **Top App Bar:** "NabungEmas" + ikon notifikasi + ikon settings
- **Hero Card (Total Summary):**
  - Background: `GoldGradient`
  - Total tabungan (gram): `Display/Large`, putih
  - Estimasi nilai IDR: `Body/Medium`, putih 80% opacity
  - Overall progress bar: putih semi-transparent
  - Persentase pencapaian: `Headline/Medium`, putih
- **Section "Tabungan Aktif":** HorizontalScrollRow dari SavingCard mini
- **Section "Transaksi Terbaru":** 3 transaksi terakhir dalam list
- **FAB:** Extended FAB "Tambah Transaksi" di kanan bawah

### 10.3 Saving Screen
- **Top App Bar:** "Tabungan Saya"
- **Summary chip:** total X tabungan aktif
- **List SavingCard:**
  - Nama target (Title/Large)
  - Progress bar gold (persentase)
  - "X gram / Y gram" (Body/Medium)
  - Estimasi nilai IDR (Label/Medium, muted)
  - Tanggal dibuat (Label/Small)
- **FAB:** Extended "Buat Tabungan"
- **Empty State:** ilustrasi + teks + tombol CTA

### 10.4 Price Screen
- **Top App Bar:** "Harga Emas" + tombol refresh
- **Harga Emas Card:**
  - Harga per gram (Display/Large, Gold400)
  - Label "per gram" dan kurs USD
  - Timestamp last updated
  - Badge naik/turun dengan persentase perubahan
- **Line Chart Card:**
  - Grafik pergerakan 7 hari / 30 hari
  - Tab selector (7H | 1B | 3B)
- **Kurs Mata Uang Card:**
  - USD → IDR, EUR → IDR
  - Last updated timestamp

### 10.5 About Screen
- **App logo + versi**
- Info developer (nama, link GitHub)
- Daftar open source libraries
- Link privacy policy / terms

### 10.6 Add/Edit Saving Screen
- Title: "Buat Tabungan Baru" / "Edit Tabungan"
- Form:
  - TextField: Nama Target
  - TextField: Target (gram)
  - TextField: Deskripsi (opsional)
- Tombol simpan (GoldButton full width)

### 10.7 Saving Detail Screen
- **Header Card:** nama target, progress besar, gram terkumpul/target
- **Stats Row:** 3 chip (Total Transaksi | Rata-rata/beli | Estimasi Nilai)
- **Daftar Transaksi** terkait saving ini
- **FAB:** "Tambah Transaksi"
- **TopAppBar actions:** edit (pensil), delete (trash)

### 10.8 Add/Edit Transaction Screen
- Title: "Tambah Transaksi" / "Edit Transaksi"
- Form:
  - Dropdown: Pilih Tabungan
  - TextField: Jumlah (gram)
  - TextField: Harga per gram (auto-fill dari API)
  - DatePicker: Tanggal transaksi
  - TextField: Catatan (opsional)
- Preview total nominal (auto-kalkulasi)
- Tombol simpan

### 10.9 Transaction Detail Screen
- Card detail transaksi
- Nama tabungan terkait
- Gram dibeli, harga/gram, total IDR
- Tanggal & catatan
- Action: edit, delete

### 10.10 Transaction List Screen
- List semua transaksi, diurutkan terbaru
- Filter chip: Semua | Bulan ini | Berdasarkan tabungan
- Grouped by tanggal

---

## 11. State Designs

### 11.1 Empty State
- Ilustrasi SVG: celengan emas kosong dengan bintang kecil
- Headline: kontekstual (contoh: "Belum ada tabungan")
- Subtext: 1-2 kalimat motivasi
- CTA Button: GoldButton

### 11.2 Loading State (Skeleton)
- Skeleton shape sesuai layout halaman terkait
- Shimmer animation dengan gold-tinted shimmer

### 11.3 Error State (API/Network)
- Ilustrasi: ikon sinyal putus / cloud error
- Headline: "Koneksi Bermasalah"
- Subtext: "Tidak dapat memuat data. Periksa koneksi internet Anda."
- Tombol: "Coba Lagi" (GoldButton outlined)

### 11.4 Delete Confirmation Dialog
- Ikon: trash merah (32dp)
- Title: "Hapus [Nama Item]?"
- Body: peringatan aksi tidak bisa dibatalkan
- Buttons: "Batal" (text button) + "Hapus" (merah, solid button)

---

## 12. Animation & Motion

| Interaksi | Animasi | Durasi | Easing |
|-----------|---------|--------|--------|
| Screen transition | Slide horizontal + fade | 300ms | FastOutSlowIn |
| Bottom sheet expand | Slide up + fade | 280ms | DecelerateInterpolator |
| FAB | Scale-in dari kanan bawah | 250ms | OvershootInterpolator |
| Card press | Scale 0.97 | 100ms | Linear |
| Progress bar fill | Expand dari 0 ke nilai | 800ms | FastOutSlowIn |
| Skeleton shimmer | Sweep kiri → kanan | 1200ms | Linear (infinite) |
| Dialog appear | Scale 0.9→1 + fade | 200ms | FastOutSlowIn |
| Snackbar | Slide up + fade | 200ms | FastOutSlowIn |
| Number counter | Count-up | 600ms | EaseOut |

---

## 13. Dark Mode

Seluruh color token memiliki variant dark mode:

| Light Token | Dark Equivalent |
|-------------|-----------------|
| `Neutral100` (bg) | `Neutral900` |
| `Neutral050` (card) | `Neutral800` |
| `Neutral200` (divider) | `Neutral700` |
| `Neutral900` (text) | `Neutral050` |
| `Gold400` (accent) | `Gold300` |

Status bar dan navigation bar mengikuti theme (transparent + light/dark icons).

---

## 14. Iconography

- Library: **Material Symbols Rounded** (Google)
- Default size: 24dp
- Active/highlight: `Gold400`
- Default/inactive: `Neutral500`
- Stroke weight: 300 (Rounded style)

---

## 15. Responsive Behavior

- **Minimum screen:** 360dp wide
- **Target screen:** 390-430dp (modern Android)
- **Tablet (600dp+):** Two-column layout untuk Saving List dan Transaction List

---

## 16. Accessibility

- Minimum touch target: 48×48dp
- Contrast ratio: min 4.5:1 untuk body text, 3:1 untuk large text
- Content description pada semua ikon interaktif
- Support talkback navigation
- Minimum font size: 11sp

---

*Terakhir diperbarui: Mei 2026*
*Versi desain: 1.0.0*
