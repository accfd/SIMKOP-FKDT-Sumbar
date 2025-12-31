# 🚀 SIMKOP DPW FKDT SUMBAR
### Sistem Manajemen Koperasi Distribusi Seragam Nasional Santri MDTA Se-Sumatera Barat

Aplikasi ini merupakan **Tugas Besar (TB)** sebagai pengganti **Ujian Akhir Semester (UAS)** untuk mata kuliah **Pemrograman Berorientasi Objek (JSI61116)**. Sistem ini dikembangkan untuk mendigitalisasi proses bisnis pada Koperasi Syariah DPW FKDT Sumatera Barat dalam mengelola distribusi seragam santri secara efisien dan akurat.

## 👤 Identitas Mahasiswa
* **Nama**: Fuadi Dhiyaulhaq
* **NIM**: 2411522001
* **Jurusan**: Departemen Sistem Informasi
* **Fakultas**: Teknologi Informasi, Universitas Andalas

## 📌 Fitur Utama & Kriteria PBO (POINT A-H)
Sistem ini telah memenuhi seluruh kriteria penilaian UAS PBO SI 2025:
* **Otentikasi Admin**: Sistem keamanan login untuk akses dashboard utama.
* **Manajemen Stok & Katalog**: Informasi stok *real-time* dengan kategori harga otomatis menggunakan **Inheritance** (Umum/Distributor).
* **Kalkulasi Otomatis**: Perhitungan matematika untuk potongan harga distributor dan akumulasi omzet harian/bulanan.
* **Sistem Transaksi Kolektif**: Mendukung pembelian multi-item dalam satu faktur menggunakan **Collection Framework (ArrayList)**.
* **Logistik & Status Pesanan**: Fitur **CRUD** lengkap (Create, Read, Update, Delete) untuk manajemen status pesanan dan pembatalan transaksi.
* **Exception Handling**: Penanganan kesalahan input menggunakan blok *try-catch* untuk menjaga stabilitas program.
* **Persistensi Data**: Integrasi database **MySQL** melalui koneksi **JDBC** yang solid.

## 🛠️ Teknologi yang Digunakan
* **Bahasa Pemrograman**: Java (JDK 17/21)
* **Database**: MySQL
* **Library**: JDBC (Java Database Connectivity)
* **Arsitektur**: Model-View-Service Architecture

## ⚙️ Cara Menjalankan
1. Download file `db_koperasi_fkdt.sql` dari repository ini dan import ke MySQL server Anda.
2. Pastikan koneksi database di `database/Config.java` sudah sesuai dengan kredensial MySQL lokal Anda.
3. Jalankan file `Main.java` sebagai *entry point* aplikasi.
4. **Kredensial Default Admin**:
    * **Username**: `admin`
    * **PIN**: `123`
