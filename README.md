# 🚀 SIMKOP DPW FKDT SUMBAR
### Sistem Manajemen Koperasi Distribusi Seragam Nasional Santri MDTA Se-Sumatera Barat

[cite_start]Aplikasi ini merupakan **Tugas Besar (TB)** sebagai pengganti **Ujian Akhir Semester (UAS)** untuk mata kuliah **Pemrograman Berorientasi Objek (JSI61116)**[cite: 13, 29]. [cite_start]Sistem ini dikembangkan untuk mendigitalisasi proses bisnis pada Koperasi Syariah DPW FKDT Sumatera Barat dalam mengelola distribusi seragam santri secara efisien dan akurat [cite: 55, 77-78].

## 👤 Identitas Mahasiswa
* [cite_start]**Nama**: Fuadi Dhiyaulhaq [cite: 64]
* [cite_start]**NIM**: 2411522001 [cite: 66]
* [cite_start]**Jurusan**: Departemen Sistem Informasi [cite: 67]
* [cite_start]**Fakultas**: Teknologi Informasi, Universitas Andalas [cite: 68-69]

## 📌 Fitur Utama & Kriteria PBO (POINT A-H)
[cite_start]Sistem ini telah memenuhi seluruh kriteria penilaian UAS PBO SI 2025 [cite: 39-47]:
* [cite_start]**Otentikasi Admin**: Sistem keamanan login untuk akses dashboard utama [cite: 1355-1356].
* [cite_start]**Manajemen Stok & Katalog**: Informasi stok *real-time* dengan kategori harga otomatis menggunakan **Inheritance** (Umum/Distributor) [cite: 42, 1387-1389].
* [cite_start]**Kalkulasi Otomatis**: Perhitungan matematika untuk potongan harga distributor dan akumulasi omzet[cite: 43, 186].
* [cite_start]**Sistem Transaksi Kolektif**: Mendukung pembelian multi-item dalam satu faktur menggunakan **Collection Framework (ArrayList)**[cite: 46, 519, 546].
* [cite_start]**Logistik & Status Pesanan**: Fitur **CRUD** lengkap (Create, Read, Update, Delete) untuk manajemen status pesanan dan pembatalan transaksi [cite: 47, 739-741].
* [cite_start]**Exception Handling**: Penanganan kesalahan input menggunakan blok *try-catch* untuk menjaga stabilitas program[cite: 45, 128, 136, 1212].
* [cite_start]**Persistensi Data**: Integrasi database **MySQL** melalui koneksi **JDBC** yang solid[cite: 47, 131, 254].

## 🛠️ Teknologi yang Digunakan
* [cite_start]**Bahasa Pemrograman**: Java (JDK 17/21) [cite: 1236]
* [cite_start]**Database**: MySQL [cite: 134, 111]
* [cite_start]**Library**: JDBC (Java Database Connectivity) [cite: 131]
* [cite_start]**Arsitektur**: Model-View-Service Architecture [cite: 118, 145, 189]

## ⚙️ Cara Menjalankan
1. [cite_start]Import file database yang tersedia di laporan (atau gunakan skema di `C. Struktur Basis Data`) ke MySQL server Anda [cite: 2124-2136].
2. [cite_start]Pastikan koneksi database di `database/Config.java` sudah sesuai dengan kredensial MySQL lokal Anda [cite: 122-129].
3. [cite_start]Jalankan file `Main.java` sebagai *entry point* aplikasi [cite: 1161-1165].
4. **Kredensial Default Admin**:
    * [cite_start]**Username**: `admin` [cite: 1196]
    * [cite_start]**PIN**: `123` [cite: 1197]
