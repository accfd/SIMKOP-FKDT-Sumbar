# SIMKOP DPW FKDT Sumbar 🏢
### Sistem Informasi Manajemen Koperasi - Distribusi Seragam Nasional Santri MDTA Se-Sumbar

[![Java: 17](https://img.shields.io/badge/Java-17-ED8B00?logo=openjdk)](https://openjdk.org/)
[![Database: MySQL](https://img.shields.io/badge/Database-MySQL-4479A1?logo=mysql)](https://www.mysql.com/)
[![JDBC: Supported](https://img.shields.io/badge/JDBC-Supported-blue)](https://docs.oracle.com/javase/8/docs/technotes/guides/jdbc/)
[![Course: PBO 2025](https://img.shields.io/badge/Course-PBO--SI--UNAND--2025-green)](https://it.unand.ac.id/)

**SISTEM MANAJEMEN KOPERASI (SIMKOP)** adalah solusi sistem informasi berbasis **Java** yang dirancang untuk mengelola distribusi seragam nasional santri MDTA di seluruh wilayah Sumatera Barat. Sistem ini dikembangkan untuk mendigitalisasi proses operasional Koperasi Syariah DPW FKDT Sumatera Barat yang sebelumnya masih manual, guna meningkatkan efisiensi dan validitas data organisasi.

---

## 👨‍💻 Identitas Pengembang
* **Nama**: Fuadi Dhiyaulhaq
* **NIM**: 2411522001
* **Instansi**: Departemen Sistem Informasi, Universitas Andalas
* **Mata Kuliah**: Pemrograman Berorientasi Objek (PBO) 2025

---

## 🎯 Analisis Masalah & Solusi (CPMK-01)
Proyek ini mengidentifikasi dan memberikan solusi atas kendala krusial organisasi:
1. **Pencatatan Manual**: Mengganti sistem pencatatan transaksi konvensional yang sering tercecer dengan basis data terpusat.
2. **Inefisiensi Stok**: Menyediakan fitur pemantauan stok otomatis tanpa perlu penghitungan fisik gudang secara berulang.
3. **Inkonsistensi Data**: Mengelola status pesanan (Pending, Packing, Sent) untuk melacak posisi barang secara akurat.
4. **Kesalahan Kalkulasi**: Otomasi perhitungan harga berjenjang (Diskon Khusus Distributor) untuk menghindari *human error*.

---

## 💻 Implementasi Paradigma PBO (CPMK-02)

Aplikasi ini dibangun dengan menerapkan prinsip-prinsip utama Pemrograman Berorientasi Objek sebagai berikut:

| Kriteria PBO | Implementasi Teknis dalam Sistem |
| :--- | :--- |
| **Class & Object** | Pemodelan entitas nyata menjadi kelas terstruktur seperti `Pelanggan`, `Distributor`, dan `KoperasiService`. |
| **Constructor** | Digunakan untuk inisialisasi awal atribut saat objek dibuat di memori. |
| **Interface** | Implementasi `SistemAksi` sebagai kontrak standar untuk seluruh fungsi utama sistem. |
| **Inheritance** | Penerapan pewarisan dari Super Class `Pelanggan` ke Sub Class `Distributor` untuk logika diskon otomatis. |
| **Logic & Math** | Penggunaan *perulangan*, *percabangan*, dan kalkulasi matematika otomatis untuk pemotongan stok real-time. |
| **String & Date** | Manipulasi String untuk nomor faktur unik dan SimpleDateFormat untuk laporan pendapatan. |
| **Exception Handling** | Penggunaan blok *try-catch* untuk menangani kesalahan input pengguna dan kegagalan database. |
| **Collection Framework**| Pemanfaatan `ArrayList` sebagai penampung "Keranjang Belanja" dinamis sebelum disimpan permanen. |
| **JDBC & CRUD** | Operasional database lengkap: *Create* (Pesanan), *Read* (Laporan), *Update* (Status), dan *Delete* (Batal). |

---

## 🚀 Fitur Utama
1. **Keamanan & Otentikasi**: Halaman login admin berbasis PIN untuk membatasi hak akses sistem.
2. **Dashboard Utama**: Menampilkan sapaan dinamis dan waktu real-time yang human-readable.
3. **Cek Stok & Katalog**: Informasi stok dan harga yang sinkron antara database MySQL dan tampilan console.
4. **Kelola Persediaan**: Fitur *Restock* (tambah) dan *Edit* (revisi) data inventaris gudang secara permanen.
5. **Input Transaksi Baru**: Pembuatan pesanan dengan sistem *Auto-Merge* jika terdapat item yang sama di keranjang.
6. **Manajemen Logistik**: Pemfilteran antrean pesanan berdasarkan status pengiriman (Pending, Packing, Sent).
7. **Update & Batal Pesanan**: Perubahan status logistik atau pembatalan transaksi yang otomatis memulihkan stok baju.
8. **Laporan & Omzet**: Rekapitulasi penjualan harian, bulanan, tahunan, serta fitur cetak ulang nota.

---

## 📂 Struktur Proyek
* `database/Config.java`: Mengatur konfigurasi dan koneksi JDBC MySQL.
* `model/Pelanggan.java`: Super Class entitas pembeli.
* `model/Distributor.java`: Sub Class dengan logika diskon Distributor.
* `service/SistemAksi.java`: Interface sebagai kontrak metode utama sistem.
* `service/KoperasiService.java`: Implementasi logika bisnis dan operasional CRUD.
* `Main.java`: Entry point aplikasi dan manajemen dashboard utama.

---

## 🛠️ Cara Instalasi dan Penggunaan

1. **Prasyarat**:
   * Instal Java Development Kit (JDK) versi 17 atau terbaru.
   * Instal MySQL Server (XAMPP/WampServer).
   * Sertakan Driver `mysql-connector-java` dalam Library proyek untuk akses JDBC.

2. **Persiapan Database**:
   * Buat database baru bernama `db_koperasi_fkdt`.
   * Eksekusi skema SQL yang mencakup tabel `seragam`, `transaksi`, dan `detail_transaksi`.

3. **Clone & Run**:
   ```bash
   git clone [https://github.com/accfd/SIMKOP-FKDT-Sumbar.git](https://github.com/accfd/SIMKOP-FKDT-Sumbar.git)
   cd SIMKOP-FKDT-Sumbar
   
   # Jalankan Main.java melalui IDE atau Terminal

   ---
---

## 📄 Lisensi & Penutup
Proyek ini dikembangkan secara individu sebagai **Tugas Besar Pengganti UAS PBO Semester Ganjil TA 2025/2026** di Departemen Sistem Informasi Universitas Andalas. Seluruh kode dikembangkan dengan menjunjung tinggi integritas akademik tanpa plagiarisme sesuai petunjuk pengerjaan.

**© 2025 Fuadi Dhiyaulhaq**
