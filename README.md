# SIMKOP DPW FKDT Sumbar 🏛️
### Sistem Informasi Manajemen Koperasi - Distribusi Seragam Nasional Santri MDTA Se-Sumbar

[![Java: 17](https://img.shields.io/badge/Java-17-ED8B00?logo=openjdk)](https://openjdk.org/)
[![Database: MySQL](https://img.shields.io/badge/Database-MySQL-4479A1?logo=mysql)](https://www.mysql.com/)
[![JDBC: Supported](https://img.shields.io/badge/JDBC-Supported-blue)](https://docs.oracle.com/javase/8/docs/technotes/guides/jdbc/)
[![Teams: PBO A (2025)](https://img.shields.io/badge/Teams-PBO%20A%20(2025)-6264A7?logo=microsoft-teams)](https://teams.microsoft.com/l/channel/19%3AoAYRZ5UukCdT_1wtiOPzO7cAgcBZ9XHg-EuB_22wTUs1%40thread.tacv2/Umum?groupId=44fe8322-c6aa-4351-b208-fdd27ce90e8f&tenantId=34627874-ed3a-497c-8fb9-16ce7e9764f1)

**SISTEM MANAJEMEN KOPERASI (SIMKOP)** adalah solusi sistem informasi berbasis **Java** yang dirancang untuk mengelola distribusi seragam nasional santri MDTA di seluruh wilayah Sumatera Barat. Sistem ini dikembangkan untuk mendigitalisasi proses operasional Koperasi Syariah DPW FKDT Sumatera Barat guna meningkatkan efisiensi dan validitas data organisasi.

---

## 👨‍💻 Identitas Pengembang
* **Nama**: Fuadi Dhiyaulhaq
* **NIM**: 2411522001
* **Instansi**: Departemen Sistem Informasi, Fakultas Teknologi Informasi, Universitas Andalas
* **Mata Kuliah**: Pemrograman Berorientasi Objek (PBO)
* **Tahun**: 2025

---

## 🎯 Analisis Masalah & Solusi (CPMK-01)
Proyek ini mengidentifikasi dan memberikan solusi atas kendala krusial organisasi:
1. **Pencatatan Manual**: Mengganti sistem pencatatan transaksi konvensional (WhatsApp) yang rawan tercecer dengan basis data terpusat.
2. **Inefisiensi Stok**: Menyediakan fitur pemantauan stok otomatis tanpa perlu penghitungan fisik gudang secara berulang.
3. **Inkonsistensi Data**: Mengelola status pesanan (Pending, Packing, Sent) untuk melacak posisi barang secara akurat.
4. **Kesalahan Kalkulasi**: Otomasi perhitungan harga berjenjang (Diskon Distributor Rp5.000) untuk menghindari *human error*.

---

## 💻 Implementasi Paradigma PBO (CPMK-02)
Aplikasi ini dibangun dengan menerapkan prinsip-prinsip utama Pemrograman Berorientasi Objek (PBO) secara mendalam sesuai kriteria penilaian:

| Kriteria PBO | Implementasi Spesifik dalam Proyek SIMKOP |
| :--- | :--- |
| **Class & Object** | Pemodelan entitas seperti `Pelanggan` sebagai blueprint dan `Distributor` sebagai objek terspesialisasi. |
| **Constructor** | Digunakan di kelas `Seragam` untuk inisialisasi data stok dan di `Pelanggan` untuk setting identitas awal. |
| **Interface** | Penggunaan `SistemAksi` sebagai kontrak metode wajib (blueprint) untuk fungsi operasional koperasi. |
| **Inheritance** | Kelas `Distributor` mewarisi atribut dari `Pelanggan`, mendemonstrasikan hubungan *Is-A*. |
| **Polymorphism** | Overriding metode `hitungTotal()` untuk memberikan logika harga berbeda antara Pelanggan Umum dan Distributor. |
| **Encapsulation** | Penggunaan akses modifier `private` pada atribut krusial dengan `Getter` dan `Setter` sebagai gerbang aksesnya. |
| **Logic & Math** | Logika perulangan `while` untuk navigasi menu dan operasi matematika untuk pengurangan stok otomatis. |
| **String & Date** | Format `SimpleDateFormat` untuk mencatat waktu transaksi secara presisi di database MySQL. |
| **Exception Handling** | Blok `try-catch` pada koneksi `JDBC` dan validasi input `Scanner` untuk mencegah *program crash*. |
| **Collection** | Penggunaan `ArrayList<Seragam>` sebagai penampung sementara (Keranjang Belanja) sebelum *Check-out*. |
| **JDBC & CRUD** | Koneksi penuh ke MySQL untuk operasi Simpan (Create), Tampil (Read), Ubah (Update), dan Hapus (Delete). |

---

## 🚀 Fitur Utama Sistem
1. **Keamanan & Otentikasi**: Login sistem menggunakan PIN Admin yang divalidasi melalui database.
2. **Dashboard Dinamis**: Tampilan antarmuka konsol yang rapi dengan informasi waktu real-time.
3. **Cek Stok & Katalog**: Sinkronisasi data stok seragam antara database dan tampilan program.
4. **Input Transaksi Cerdas**: Fitur **Auto-Merge** yang menggabungkan item barang yang sama di dalam satu keranjang belanja.
5. **Update Status Logistik**: Pelacakan status barang mulai dari *Pending*, *Packing*, hingga *Sent*.
6. **Logika Pemulihan Stok**: Jika transaksi dibatalkan, jumlah barang otomatis dikembalikan ke stok gudang (*Update JDBC*).
7. **Laporan Omzet**: Rekapitulasi pendapatan berdasarkan filter harian, bulanan, dan tahunan.

---

## 🗄️ Struktur Basis Data (MySQL)
Seluruh data disimpan pada basis data `db_koperasi_fkdt` dengan rincian tabel utama:
* **Tabel `seragam`**: Master data stok baju (Jenis, Ukuran, Harga, Stok).
* **Tabel `transaksi`**: Header data pesanan (Faktur, Pembeli, Kategori, Total Bayar, Status).
* **Tabel `detail_transaksi`**: Rincian item per faktur (Foreign Key: `ON DELETE CASCADE`).

---

## 🛠️ Cara Instalasi dan Penggunaan

Ikuti langkah-langkah di bawah ini untuk menjalankan sistem di perangkat lokal Anda:

### 1. Prasyarat Sistem
* **Java Development Kit (JDK)**: Versi 17 atau yang terbaru.
* **Database Server**: MySQL (Sangat disarankan menggunakan XAMPP atau WampServer).
* **IDE (Opsional)**: NetBeans, IntelliJ IDEA, atau VS Code untuk kemudahan pengelolaan proyek.
* **Konektor Database**: Pastikan Library `mysql-connector-java` sudah ditambahkan ke dalam *Libraries* proyek Anda.

### 2. Persiapan Database
1. Buka **phpMyAdmin** (biasanya di `localhost/phpmyadmin`).
2. Buat database baru dengan nama: `db_koperasi_fkdt`.
3. Impor file SQL yang tersedia di folder proyek atau buat tabel secara manual dengan struktur berikut:
   * **Tabel `seragam`**: (id, jenis, ukuran, harga, stok).
   * **Tabel `transaksi`**: (faktur, tanggal, nama_pembeli, kategori, total_bayar, status).
   * **Tabel `detail_transaksi`**: (id_detail, faktur, id_seragam, jumlah, subtotal).

### 3. Konfigurasi Koneksi
Pastikan kredensial database di file `database/Config.java` sudah sesuai dengan pengaturan MySQL Anda:
* **URL**: `jdbc:mysql://localhost:3306/db_koperasi_fkdt`
* **User**: `root` (default XAMPP)
* **Password**: ` ` (kosongkan jika default XAMPP)

### 4. Menjalankan Aplikasi
Buka terminal atau command prompt, lalu jalankan perintah berikut:

* Clone repositori ini
```bash
git clone https://github.com/accfd/SIMKOP-FKDT-Sumbar.git
```
* Masuk ke direktori proyek
```
cd SIMKOP-FKDT-Sumbar
```
* Kompilasi dan jalankan (atau buka melalui IDE pilihan Anda)
* Jalankan file Main.java

---

## 📄 Lisensi & Penutup
Proyek ini dikembangkan secara individu sebagai **Tugas Besar Pengganti UAS PBO Semester Ganjil TA 2025/2026** di Departemen Sistem Informasi Universitas Andalas. Seluruh kode dikembangkan dengan menjunjung tinggi integritas akademik tanpa plagiarisme sesuai petunjuk pengerjaan.

**© 2025 Fuadi Dhiyaulhaq**
