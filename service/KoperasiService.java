package service;

import database.Config;
import model.*; 
import java.sql.*;
import java.util.Scanner;
import java.util.ArrayList; // POINT G: Implementasi Collection Framework (Daftar Dinamis di Memori)
import java.util.Calendar;
import java.text.NumberFormat; // POINT E: Manipulasi String format mata uang Rupiah
import java.util.Locale;

// POINT B: Implementasi Interface SistemAksi (Penerapan Kontrak Method/Abstraksi Sistem)
public class KoperasiService implements SistemAksi {
    // Inisialisasi properti global untuk class (Encapsulation)
    private Connection conn;
    private Scanner input = new Scanner(System.in);
    
    // POINT G: Penggunaan ArrayList untuk mencatat aktivitas secara dinamis (Collection Framework)
    private ArrayList<String> logSesi = new ArrayList<>();
    
    // POINT E: Manipulasi String untuk format mata uang lokal Indonesia (IDR) menggunakan Locale
    private NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));

    // POINT A: Constructor class KoperasiService (Eksekusi otomatis saat objek dibuat untuk inisialisasi)
    public KoperasiService() {
        logSesi.add("Sistem Koperasi FKDT Berhasil Dijalankan");
    }

    // Method internal untuk estetika tampilan console
    private void animasiKecil(String pesan, int titik) {
        System.out.print(pesan);
        try {
            // POINT D: Implementasi Perulangan (for-loop) untuk membuat efek visual loading
            for (int i = 0; i < titik; i++) {
                Thread.sleep(300); 
                System.out.print(".");
            }
            System.out.println();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    // POINT H: Implementasi JDBC - Operasi READ (Membaca/Mengambil data stok dari MySQL)
    @Override
    public void cekSeluruhStok() {
        // POINT D: Implementasi Perulangan (while-loop) agar menu tetap aktif hingga user memilih keluar
        while (true) {
            // POINT F: Exception Handling untuk menangani potensi error database atau salah input pengguna
            try {
                // POINT H: Membangun koneksi ke database melalui JDBC via class Config
                conn = Config.configDB();
                System.out.println("\n" + "╔" + "═".repeat(53) + "╗");
                System.out.println("║            PILIHAN KATEGORI HARGA KATALOG           ║");
                System.out.println("╠" + "═".repeat(53) + "╣");
                System.out.println("║  [1] Harga Distributor                              ║");
                System.out.println("║  [2] Harga Umum                                     ║");
                System.out.println("║  [0] Kembali                                        ║");
                System.out.println("╚" + "═".repeat(53) + "╝");
                
                System.out.println("═".repeat(55)); 
                System.out.print("  Pilih kategori [1-2/0] >> ");
                String inp = input.nextLine();
                System.out.println("═".repeat(55));
                
                // POINT D: Implementasi Percabangan (if-else) untuk logika alur navigasi menu
                if (inp.equals("0")) {
                    animasiKecil("\n[*] Sedang kembali ke Menu Utama", 3);
                    return; 
                }
                
                if (!inp.equals("1") && !inp.equals("2")) {
                    System.err.println("\n[!] GAGAL: Pilihan '" + inp + "' tidak tersedia!");
                    continue;
                }

                int pil = Integer.parseInt(inp);
                animasiKecil("\n[*] Menyiapkan Katalog Seragam", 4);
                
                // POINT D: Penggunaan Operator Ternary (Percabangan singkat) untuk menentukan label kategori
                String label = (pil == 1) ? "DISTRIBUTOR" : "UMUM";
                System.out.println("\n===== INFO STOK & HARGA " + label + " =====");
                
                // POINT A: Instansiasi Objek dari class Distributor (Turunan/Subclass dari Pelanggan)
                Distributor dObj = new Distributor("Dummy");

                System.out.println("\nSeragam Batik Nasional MDT Putra:");
                String sqlPutra = "SELECT * FROM seragam WHERE jenis_kelamin='Putra' ORDER BY CAST(nomor_ukuran AS UNSIGNED) ASC";
                
                // POINT H: Eksekusi SQL READ menggunakan PreparedStatement dan ResultSet via JDBC
                try (PreparedStatement psPutra = conn.prepareStatement(sqlPutra);
                     ResultSet rsPutra = psPutra.executeQuery()) {
                    while (rsPutra.next()) {
                        // POINT C: Konsep Inheritance - Memanggil method hitungTotalDistributor dari Subclass Distributor
                        // POINT D: Perhitungan Matematika untuk menentukan harga jual akhir
                        double harga = (pil == 1) ? dObj.hitungTotalDistributor(rsPutra.getDouble("harga"), 1) : rsPutra.getDouble("harga");
                        // POINT E: Manipulasi String untuk memformat output angka menjadi mata uang Rupiah
                        System.out.println("- Putra No." + rsPutra.getString("nomor_ukuran") + ": " + rsPutra.getInt("stok") + " Stel (" + formatter.format(harga) + ")");
                    }
                }

                System.out.println("\nSeragam Batik Nasional MDT Putri:");
                String sqlPutri = "SELECT * FROM seragam WHERE jenis_kelamin='Putri' ORDER BY CAST(nomor_ukuran AS UNSIGNED) ASC";
                try (PreparedStatement psPutri = conn.prepareStatement(sqlPutri);
                     ResultSet rsPutri = psPutri.executeQuery()) {
                    while (rsPutri.next()) {
                        double harga = (pil == 1) ? dObj.hitungTotalDistributor(rsPutri.getDouble("harga"), 1) : rsPutri.getDouble("harga");
                        System.out.println("- Putri No." + rsPutri.getString("nomor_ukuran") + ": " + rsPutri.getInt("stok") + " Stel (" + formatter.format(harga) + ")");
                    }
                }
                logSesi.add("Admin melihat stok kategori " + label);
                System.out.println("\n[ Tekan ENTER untuk kembali ke Pilihan Kategori ]");
                input.nextLine();
            } catch (Exception e) { 
                // POINT F: Menangani error jika user memasukkan data non-numerik saat parsing integer
                System.err.println("\n[!] GAGAL: Input tidak valid!"); 
            }
        }
    }

    // POINT H: Implementasi JDBC - Operasi UPDATE (Memperbarui/Mengubah data stok di database)
    @Override
    public void kelolaStok() {
        while (true) {
            try {
                conn = Config.configDB();
                System.out.println("\n" + "╔" + "═".repeat(53) + "╗");
                System.out.println("║                 KELOLA STOK GUDANG                  ║");
                System.out.println("╠" + "═".repeat(53) + "╣");
                System.out.println("║  [1] Tambah Stok (Restock)                          ║");
                System.out.println("║  [2] Edit Stok (Revisi Data)                        ║");
                System.out.println("║  [0] Kembali                                        ║");
                System.out.println("╚" + "═".repeat(53) + "╝");
                System.out.print("  Pilih aksi [1-2/0] >> ");
                String pilMenu = input.nextLine();
                
                if (pilMenu.equals("0")) {
                    animasiKecil("\n[*] Sedang kembali ke Menu Utama", 3);
                    return;
                }
                if (!pilMenu.equals("1") && !pilMenu.equals("2")) {
                    System.err.println("\n[!] GAGAL: Pilihan '" + pilMenu + "' tidak tersedia!");
                    continue;
                }

                int sub = Integer.parseInt(pilMenu);
                String labelSub = (sub == 1) ? "TAMBAH STOK (RESTOCK)" : "EDIT STOK (REVISI DATA)";
                
                System.out.println("\n" + "╔" + "═".repeat(53) + "╗");
                System.out.println("║            MODUL PEMBARUAN INVENTARIS               ║");
                System.out.println("╟─────────────────────────────────────────────────────╢");
                System.out.printf("║  AKSI : %-43s ║%n", labelSub);
                System.out.println("╚" + "═".repeat(53) + "╝");

                boolean formAktif = true;
                while (formAktif) {
                    System.out.println("╔" + "═".repeat(53) + "╗");
                    System.out.println("║                PILIH JENIS SERAGAM                  ║");
                    System.out.print("╟─────────────────────────────────────────────────────╢\n");
                    System.out.println("║  [1] Seragam Putra                                  ║");
                    System.out.println("║  [2] Seragam Putri                                  ║");
                    System.out.println("║  [0] Batal (Kembali ke Kelola Stok)                 ║");
                    System.out.println("╚" + "═".repeat(53) + "╝");

                    String j = "";
                    while (true) {
                        System.out.print("  Pilih Jenis >> ");
                        String pilJ = input.nextLine();
                        if (pilJ.equals("0")) { animasiKecil("  [*] Membatalkan", 2); break; }
                        if (pilJ.equals("1") || pilJ.equals("2")) {
                            j = (pilJ.equals("1")) ? "Putra" : "Putri";
                            break; 
                        }
                        System.err.println("[!] GAGAL: Pilihan '" + pilJ + "' tidak tersedia!");
                    }
                    if (j.isEmpty()) { formAktif = false; continue; }

                    while (true) {
                        System.out.println("\n" + "╔" + "═".repeat(53) + "╗");
                        // POINT E: Manipulasi String menggunakan toUpperCase() untuk visual konsisten
                        System.out.printf("║  JENIS DIPILIH      : SERAGAM %-21s ║%n", j.toUpperCase());
                        System.out.println("║  UKURAN TERSEDIA    : 2 - 10                        ║");
                        System.out.println("║  [0] Kembali Pilih Jenis Seragam                    ║");
                        System.out.println("╚" + "═".repeat(53) + "╝");

                        System.out.print("  Pilih Ukuran >> ");
                        String u = input.nextLine();
                        if (u.equals("0")) { break; }
                        
                        animasiKecil("  [*] Mencari Data di Database", 2);
                        PreparedStatement psU = conn.prepareStatement("SELECT stok FROM seragam WHERE nomor_ukuran=? AND jenis_kelamin=?");
                        psU.setString(1, u); psU.setString(2, j);
                        ResultSet rsU = psU.executeQuery();

                        if (rsU.next()) {
                            int stokSkrg = rsU.getInt("stok");
                            String infoBarang = "Seragam " + j + " No." + u;
                            System.out.println("\n  ┌───────────────────────────────────────────────────┐");
                            System.out.printf("  │  DATA DITEMUKAN: %-32s │%n", infoBarang);
                            System.out.printf("  │  STOK SAAT INI : %-32s │%n", stokSkrg + " Stel");
                            System.out.println("  └───────────────────────────────────────────────────┘");

                            System.out.print(sub == 1 ? "  [+] Jumlah Unit Tambahan >> " : "  [*] Revisi Stok Jadi >> ");
                            int val = Integer.parseInt(input.nextLine());

                            // POINT H: Operasi JDBC UPDATE untuk mengubah jumlah stok fisik di MySQL
                            String sqlUp = (sub == 1) ? "UPDATE seragam SET stok = stok + ? WHERE nomor_ukuran=? AND jenis_kelamin=?" : 
                                                        "UPDATE seragam SET stok = ? WHERE nomor_ukuran=? AND jenis_kelamin=?";
                            PreparedStatement pup = conn.prepareStatement(sqlUp);
                            pup.setInt(1, val); pup.setString(2, u); pup.setString(3, j);
                            pup.executeUpdate();

                            // POINT D: Perhitungan Matematika untuk menghitung representasi stok baru di console
                            int stokBaru = (sub == 1) ? stokSkrg + val : val;
                            animasiKecil("  [*] Mengsinkronkan data ke MySQL", 4);
                            System.out.println("\n  ┌───────────────────────────────────────────────────┐");
                            System.out.println("  │  UPDATE BERHASIL!                                 │");
                            System.out.printf("  │  STOK TERBARU  : %-32s │%n", stokBaru + " Stel");
                            System.out.println("  └───────────────────────────────────────────────────┘");
                            System.out.println("  [!] BERHASIL: Stok Inventaris Telah Diperbarui.");
                            System.out.println("\n[ Tekan ENTER untuk kembali ]");
                            input.nextLine();
                            formAktif = false; break; 
                        } else {
                            System.err.println("\n[!] GAGAL: Pilihan '" + u + "' tidak tersedia!");
                        }
                    }
                }
            } catch (Exception e) { 
                // POINT F: Exception Handling untuk menangani input karakter ilegal saat meminta jumlah stok
                System.err.println("\n[!] GAGAL: Masukkan angka yang valid!"); 
            }
        }
    }

    // POINT H: Implementasi JDBC - Operasi CREATE (Menambahkan baris Transaksi baru ke Database)
    // POINT G: Penggunaan ArrayList sebagai penampung "Keranjang Belanja" sementara sebelum disimpan permanen
    @Override
    public void tambahPesanan() {
        try {
            // POINT H: Membangun koneksi ke database melalui JDBC
            conn = Config.configDB();
            System.out.println("\n" + "╔" + "═".repeat(53) + "╗");
            System.out.println("║                INPUT TRANSAKSI BARU                 ║");
            System.out.println("╚" + "═".repeat(53) + "╝");
            
            System.out.print("  Nama Pembeli [0 Batal] >> "); 
            String nama = input.nextLine();
            // POINT D: Percabangan (if) untuk fitur pembatalan transaksi di awal
            if (nama.equals("0")) { animasiKecil("[*] Membatalkan", 3); return; }
            
            System.out.print("  Kategori: [1] Distributor [2] Umum >> ");
            String ikat = input.nextLine();
            if (!ikat.equals("1") && !ikat.equals("2")) {
                System.err.println("\n[!] GAGAL: Pilihan '" + ikat + "' tidak tersedia!");
                return;
            }
            int kat = Integer.parseInt(ikat);
            String labelKat = (kat == 1) ? "DISTRIBUTOR" : "UMUM";
            
            // POINT E: Manipulasi String untuk membuat nomor faktur unik berbasis Timestamp (Waktu saat ini)
            String noFaktur = "FKDT-" + System.currentTimeMillis();
            
            // POINT G: Menginisialisasi ArrayList sebagai wadah koleksi barang yang akan dibeli dalam satu transaksi
            ArrayList<String> keranjang = new ArrayList<>();
            // POINT A: Instansiasi objek Distributor untuk mengakses metode perhitungan harga khusus
            Distributor d = new Distributor(nama); 
            boolean adaBarang = false;

            // POINT D: Perulangan (while) untuk memproses input banyak jenis barang sekaligus
            while (true) {
                System.out.println("\n" + "╔" + "═".repeat(53) + "╗");
                System.out.println("║                PILIH BARANG PESANAN                 ║");
                System.out.println("╟─────────────────────────────────────────────────────╢");
                System.out.println("║  [1] Seragam Putra                                  ║");
                System.out.println("║  [2] Seragam Putri                                  ║");
                System.out.println("║  [0] Selesai & Simpan Faktur                        ║");
                System.out.println("╚" + "═".repeat(53) + "╝");
                System.out.print("  Pilihan >> ");
                String pilJ = input.nextLine();
                
                if (pilJ.equals("0")) break; // Keluar dari loop input barang
                
                if (!pilJ.equals("1") && !pilJ.equals("2")) {
                    System.err.println("\n[!] GAGAL: Pilihan '" + pilJ + "' tidak tersedia!");
                    continue;
                }
                
                String jen = (pilJ.equals("1")) ? "Putra" : "Putri";

                while (true) {
                    System.out.println("\n" + "╔" + "═".repeat(53) + "╗");
                    // POINT E: Manipulasi String menggunakan method toUpperCase()
                    System.out.printf("║  JENIS DIPILIH      : SERAGAM %-21s ║%n", jen.toUpperCase());
                    System.out.println("║  UKURAN TERSEDIA    : 2 - 10                        ║");
                    System.out.println("║  [0] Kembali Pilih Jenis Seragam                    ║");
                    System.out.println("╚" + "═".repeat(53) + "╝");
                    
                    System.out.print("  Pilih Ukuran >> ");
                    String uk = input.nextLine();
                    if (uk.equals("0")) { break; }
                    
                    // POINT H: Operasi JDBC READ untuk mengambil detail harga dan stok dari tabel seragam
                    PreparedStatement pc = conn.prepareStatement("SELECT id_seragam, harga, stok FROM seragam WHERE nomor_ukuran=? AND jenis_kelamin=?");
                    pc.setString(1, uk); pc.setString(2, jen);
                    ResultSet rs = pc.executeQuery();

                    if (rs.next()) {
                        int idS = rs.getInt("id_seragam");
                        int stokDB = rs.getInt("stok");
                        double hrg = rs.getDouble("harga");

                        // LOGIKA AUTO-MERGE: Mengecek apakah barang yang sama sudah ada di keranjang ArrayList
                        int indexExisting = -1;
                        int qtySudahAda = 0;
                        
                        // POINT G: Iterasi melalui Collection ArrayList untuk validasi stok lokal dalam keranjang
                        for (int i = 0; i < keranjang.size(); i++) {
                            String[] check = keranjang.get(i).split(",");
                            if (Integer.parseInt(check[0]) == idS) {
                                indexExisting = i; // Temukan posisi barang yang sama
                                qtySudahAda = Integer.parseInt(check[1]);
                                break;
                            }
                        }

                        // POINT D: Perhitungan Matematika untuk menentukan sisa stok yang benar-benar bisa dipesan
                        int stokTersediaReal = stokDB - qtySudahAda;
                        System.out.print("  Jumlah Stel (Tersedia: " + stokTersediaReal + ") [0 Batal] >> ");
                        int q = Integer.parseInt(input.nextLine());

                        if (q == 0) {
                            System.out.println("  [*] Input dibatalkan, kembali ke menu barang.");
                            break; 
                        }

                        // POINT D: Percabangan logika untuk validasi kecukupan stok di database
                        if (q > 0 && q <= stokTersediaReal) {
                            adaBarang = true;
                            // POINT D: Perhitungan matematika untuk penggabungan kuantitas
                            int qtyTotalBaru = qtySudahAda + q;
                            // POINT C: Konsep Inheritance - Menggunakan metode di subclass Distributor untuk diskon khusus
                            double subBaru = (kat == 1) ? d.hitungTotalDistributor(hrg, qtyTotalBaru) : hrg * qtyTotalBaru;
                            
                            // POINT G: Manipulasi data di dalam Collection ArrayList (Update atau Add baru)
                            if (indexExisting != -1) {
                                // Menggunakan method set() pada ArrayList untuk memperbarui item
                                keranjang.set(indexExisting, idS + "," + qtyTotalBaru + "," + subBaru);
                                System.out.println("  [*] Jumlah item dalam keranjang diperbarui: " + qtyTotalBaru + " Stel.");
                            } else {
                                // Menggunakan method add() pada ArrayList untuk menambah item baru
                                keranjang.add(idS + "," + q + "," + subBaru);
                                System.out.println("  [*] Berhasil masuk keranjang.");
                            }
                            break; 
                        } else { 
                            System.err.println("  [!] GAGAL: Stok tidak mencukupi!"); 
                        }
                    } else { 
                        System.err.println("\n[!] GAGAL: Pilihan '" + uk + "' tidak tersedia!"); 
                    }
                }
            }

            // POINT H: Operasi CREATE & UPDATE Database secara kolektif setelah user menekan tombol 'Selesai'
            if (adaBarang) {
                // POINT H: INSERT baris Header Transaksi ke MySQL
                conn.createStatement().executeUpdate("INSERT INTO transaksi (no_faktur, tgl_transaksi, nama_pembeli, kategori_pembeli, total_bayar_akhir, status_order) VALUES ('"+noFaktur+"', NOW(), '"+nama+"', '"+labelKat+"', 0, 'Pending')");
                
                // POINT G: Memproses setiap elemen data String di dalam ArrayList keranjang menggunakan loop
                for (String item : keranjang) {
                    String[] data = item.split(",");
                    int id = Integer.parseInt(data[0]);
                    int qtyFinal = Integer.parseInt(data[1]);
                    double subFinal = Double.parseDouble(data[2]);
                    
                    // POINT H: INSERT detail item barang ke database dan UPDATE stok di gudang
                    conn.createStatement().executeUpdate("INSERT INTO detail_transaksi (no_faktur, id_seragam, jumlah, subtotal) VALUES ('"+noFaktur+"', "+id+", "+qtyFinal+", "+subFinal+")");
                    conn.createStatement().executeUpdate("UPDATE seragam SET stok = stok - "+qtyFinal+" WHERE id_seragam = "+id);
                }

                // POINT H: UPDATE kolom total bayar akhir pada tabel transaksi setelah semua subtotal dihitung
                double totalFinal = hitungDanTampilkanRingkasan(noFaktur, nama, labelKat);
                conn.createStatement().executeUpdate("UPDATE transaksi SET total_bayar_akhir = "+totalFinal+" WHERE no_faktur = '"+noFaktur+"'");
                
                System.out.println("\n[*] PESANAN BERHASIL DISIMPAN!");
                System.out.println("[ Tekan ENTER untuk kembali ]");
                input.nextLine();
            } else { 
                // Menghapus record transaksi kosong jika user membatalkan semua input barang
                conn.createStatement().executeUpdate("DELETE FROM transaksi WHERE no_faktur='"+noFaktur+"'"); 
            }
        } catch (Exception e) { 
            // POINT F: Menangkap error SQLException atau NumberFormatException pada level fungsionalitas tambah pesanan
            System.err.println("\n[!] Gagal: " + e.getMessage()); 
        }
    }

    // Method pembantu untuk menghasilkan nota belanja digital di console
    private double hitungDanTampilkanRingkasan(String noFaktur, String nama, String kat) throws SQLException {
        double total = 0;
        animasiKecil("\n[*] Menghasilkan Ringkasan", 3);
        
        System.out.println("\n=================================");
        System.out.println("*DOKUMEN PESANAN SERAGAM MDTA - KOPERASI SYARIAH DPW FKDT SUMBAR*");
        System.out.println("=================================");
        
        System.out.println("\nFaktur  : " + noFaktur);
        // POINT E: Manipulasi format Date menggunakan class SimpleDateFormat untuk tampilan laporan
        System.out.println("Tanggal : " + new java.text.SimpleDateFormat("dd/MM/yyyy").format(new java.util.Date()));
        System.out.println("Nama    : " + nama.toUpperCase());
        System.out.println("Status  : " + kat);
        System.out.println("____________________________________");
        
        System.out.println("\nPESANAN:");

        // POINT H: Operasi JDBC READ menggunakan SQL JOIN untuk menggabungkan data transaksi dan data seragam
        ResultSet rs = conn.createStatement().executeQuery(
            "SELECT s.jenis_kelamin, s.nomor_ukuran, d.jumlah, d.subtotal, (d.subtotal/d.jumlah) as harga_satuan " +
            "FROM detail_transaksi d JOIN seragam s ON d.id_seragam=s.id_seragam " +
            "WHERE d.no_faktur='" + noFaktur + "'"
        );

        while (rs.next()) {
            double hrgSatuan = rs.getDouble("harga_satuan");
            System.out.println("\n- Seragam " + rs.getString(1) + " (No. " + rs.getString(2) + ") " + 
                               rs.getInt(3) + " Stel x " + formatter.format(hrgSatuan) + " = Total: " + formatter.format(rs.getDouble(4)));
            // POINT D: Perhitungan Matematika Akumulasi untuk mendapatkan total tagihan akhir
            total += rs.getDouble(4);
        }
        
        System.out.println("____________________________________");
        System.out.println("\n*TOTAL BAYAR: " + formatter.format(total) + "*");
        System.out.println("\nPembayaran melalui Transfer:");
        System.out.println("*Bank Syariah Indonesia (BSI)*");
        System.out.println("*7211-1425-08 a.n Koperasi FKDT Sumbar*");
        System.out.println("\nMohon kirimkan bukti transfer agar pesanan segera kami proses.");
        System.out.println("____________________________________");
        
        return total;
    }

    // POINT H: CRUD - Operasi UPDATE & DELETE (Manajemen Antrean Logistik dan Pembatalan Data)
    @Override
    public void updateStatusKelola() {
        while (true) {
            try {
                conn = Config.configDB();
                System.out.println("\n" + "╔" + "═".repeat(53) + "╗");
                System.out.println("║              KELOLA STATUS & PESANAN                ║");
                System.out.println("╠" + "═".repeat(53) + "╣");
                System.out.println("║  [1] Lihat Semua Daftar Pesanan (Filter Status)     ║");
                System.out.println("║  [2] Cari Faktur & Eksekusi (Bayar/Kirim/Batal)     ║");
                System.out.println("║  [0] Kembali                                        ║");
                System.out.println("╚" + "═".repeat(53) + "╝");
                
                System.out.println("═".repeat(55)); 
                System.out.print("  Pilih aksi [1-2/0] >> ");
                String pStr = input.nextLine();
                System.out.println("═".repeat(55));

                if (pStr.equals("0")) {
                    animasiKecil("\n[*] Sedang kembali ke Menu Utama", 3);
                    return;
                }

                if (pStr.equals("1")) {
                    // Perulangan internal untuk fitur pemfilteran status pesanan
                    while (true) {
                        System.out.println("\n  [*] Membuka Modul Filter Antrean...");
                        System.out.println("  ╔" + "═".repeat(51) + "╗");
                        System.out.println("  ║            PILIH KATEGORI DAFTAR PESANAN          ║");
                        System.out.println("  ╠" + "═".repeat(51) + "╣");
                        System.out.println("  ║  [1] Menunggu Pembayaran (PENDING)                ║");
                        System.out.println("  ║  [2] Sedang Dikemas (PACKING)                     ║");
                        System.out.println("  ║  [3] Sudah Terkirim (SENT)                        ║");
                        System.out.println("  ║  [0] Kembali                                      ║");
                        System.out.println("  ╚" + "═".repeat(51) + "╝");
                        System.out.println("  " + "═".repeat(53)); 
                        System.out.print("    Pilih filter [1-3/0] >> ");
                        String fStr = input.nextLine();
                        System.out.println("  " + "═".repeat(53));

                        if (fStr.equals("0")) break;

                        String statusMap = (fStr.equals("1")) ? "Pending" : (fStr.equals("2")) ? "Packing" : (fStr.equals("3")) ? "Sent" : "";

                        // Validasi Input agar program tidak memproses filter ilegal
                        if (statusMap.isEmpty()) {
                            System.err.println("\n[!] GAGAL: Pilihan '" + fStr + "' tidak tersedia!");
                            continue;
                        }
                        
                        // POINT H: Operasi JDBC READ untuk memfilter data transaksi berdasarkan status logistik
                        PreparedStatement ps = conn.prepareStatement("SELECT tgl_transaksi, no_faktur, nama_pembeli, total_bayar_akhir FROM transaksi WHERE status_order=?");
                        ps.setString(1, statusMap);
                        ResultSet rs = ps.executeQuery();
                        
                        // POINT G: Implementasi Collection Framework (ArrayList) untuk menampung baris data tabel secara dinamis
                        ArrayList<String> dataArrayList = new ArrayList<>();
                        while (rs.next()) {
                            // POINT E: Manipulasi format Date Indonesia untuk kolom TGL di tabel console
                            String tgl = new java.text.SimpleDateFormat("dd/MM").format(rs.getDate(1));
                            String nama = rs.getString(3);
                            // Manipulasi String substring() agar tampilan tabel tidak berantakan jika nama pelanggan terlalu panjang
                            if (nama.length() > 15) nama = nama.substring(0, 12) + "...";
                            // Manipulasi String format() untuk merapikan spasi antar kolom tabel
                            dataArrayList.add(String.format("  │ %-6s │ %-18s │ %-15s │ %15s │", tgl, rs.getString(2), nama, formatter.format(rs.getDouble(4))));
                        }
                        
                        System.out.println("\n  --- DATA PESANAN STATUS: " + statusMap.toUpperCase() + " ---");
                        System.out.println("  ┌────────┬────────────────────┬─────────────────┬─────────────────┐");
                        System.out.println("  │  TGL   │       FAKTUR       │    PELANGGAN    │      TOTAL      │");
                        System.out.println("  ├────────┼────────────────────┼─────────────────┼─────────────────┤");
                        
                        if (dataArrayList.isEmpty()) {
                            System.out.println("  │            ( Belum ada transaksi dengan status ini )            │");
                        } else {
                            // POINT G: Iterasi for-each melalui objek Collection ArrayList untuk mencetak baris data
                            for (String baris : dataArrayList) {
                                System.out.println(baris);
                            }
                        }
                        System.out.println("  └────────┴────────────────────┴─────────────────┴─────────────────┘");
                        System.out.println("  [ Tekan ENTER untuk kembali ke Pilihan Kategori ]");
                        input.nextLine();
                    }

                } else if (pStr.equals("2")) {
                    System.out.print("  Masukkan No Faktur >> "); 
                    String f = input.nextLine();
                    animasiKecil("  [*] Mencari data di server database", 3);

                    // POINT H: Operasi JDBC READ untuk memverifikasi keberadaan nomor faktur
                    PreparedStatement ps = conn.prepareStatement("SELECT nama_pembeli, total_bayar_akhir, status_order FROM transaksi WHERE no_faktur = ?");
                    ps.setString(1, f);
                    ResultSet rs = ps.executeQuery();
                    
                    if (rs.next()) {
                        String st = rs.getString(3).toUpperCase();
                        System.out.println("\n  ┌───────────────────────────────────────────────────┐");
                        System.out.println("  │              DATA PESANAN DITEMUKAN               │");
                        System.out.println("  ├───────────────────────────────────────────────────┤");
                        System.out.printf("  │ Pelanggan : %-37s │%n", rs.getString(1).toUpperCase());
                        System.out.printf("  │ Total     : %-37s │%n", formatter.format(rs.getDouble(2)));
                        System.out.printf("  │ Status    : %-37s │%n", st + (st.equals("PENDING") ? " (BELUM BAYAR)" : ""));
                        System.out.println("  └───────────────────────────────────────────────────┘");
                        
                        System.out.println("\n  APA YANG INGIN ANDA LAKUKAN?");
                        System.out.println("  [1] Pesanan Sudah Dibayar (Pindah ke: PACKING)");
                        System.out.println("  [2] Barang Sudah Diambil/Kirim (Pindah ke: SENT)");
                        System.out.println("  [3] Batalkan Pesanan (Hapus & Kembalikan Stok Baju)");
                        System.out.println("  [0] Kembali / Cari No Faktur Lain");
                        System.out.print("\n  >> Pilih Aksi [1/2/3/0] : ");
                        String aksi = input.nextLine();

                        // POINT H: Operasi JDBC UPDATE untuk memperbarui status pesanan di database MySQL
                        if (aksi.equals("1")) {
                            conn.createStatement().executeUpdate("UPDATE transaksi SET status_order='Packing' WHERE no_faktur='"+f+"'");
                            System.out.println("  [!] BERHASIL: Status diperbarui ke PACKING.");
                        } else if (aksi.equals("2")) {
                            conn.createStatement().executeUpdate("UPDATE transaksi SET status_order='Sent' WHERE no_faktur='"+f+"'");
                            System.out.println("  [!] BERHASIL: Status diperbarui ke TERKIRIM (SENT).");
                        } else if (aksi.equals("3")) {
                            System.out.print("  Yakin batalkan pesanan ini? (Y/N) >> ");
                            if (input.nextLine().equalsIgnoreCase("Y")) {
                                animasiKecil("  [*] Mengakses detail transaksi", 3);
                                System.out.println("\n  ┌───────────────────────────────────────────────────┐");
                                System.out.println("  │           DETAIL PENGEMBALIAN STOK BAJU           │");
                                System.out.println("  ├───────────────────────────────────────────────────┤");
                                
                                // Memulihkan stok barang ke database sebelum data pesanan dihapus
                                ResultSet rd = conn.createStatement().executeQuery("SELECT s.jenis_kelamin, s.nomor_ukuran, d.jumlah, s.stok, d.id_seragam FROM detail_transaksi d JOIN seragam s ON d.id_seragam=s.id_seragam WHERE d.no_faktur = '"+f+"'");
                                while (rd.next()) {
                                    int jml = rd.getInt(3), lama = rd.getInt(4), baru = lama + jml;
                                    // POINT H: Operasi UPDATE stok sisa di gudang akibat pembatalan
                                    conn.createStatement().executeUpdate("UPDATE seragam SET stok="+baru+" WHERE id_seragam="+rd.getInt(5));
                                    System.out.printf("  │ > Seragam %-5s No. %-2s : [+%-2d] (%2d -> %2d)           │%n", rd.getString(1), rd.getString(2), jml, lama, baru);
                                }
                                System.out.println("  └───────────────────────────────────────────────────┘");
                                // POINT H: Operasi JDBC DELETE untuk menghapus record transaksi dan detailnya dari database
                                conn.createStatement().executeUpdate("DELETE FROM detail_transaksi WHERE no_faktur='"+f+"'");
                                conn.createStatement().executeUpdate("DELETE FROM transaksi WHERE no_faktur='"+f+"'");
                                System.out.println("  [!] BERHASIL: Pesanan dihapus & stok telah kembali.");
                            }
                        }
                    } else System.err.println("  [!] GAGAL: Nomor faktur tidak ditemukan!");
                    System.out.println("\n  [ Tekan ENTER untuk kembali ]");
                    input.nextLine();
                }
            } catch (Exception e) { System.err.println("[!] Gagal."); }
        }
    }

// POINT H: Implementasi JDBC - Operasi READ (Laporan Multi-Filter dengan Format Presisi Baru)
    @Override
    public void riwayatLaporan() {
        // POINT D: Implementasi Perulangan (while-loop) agar menu laporan tetap aktif hingga admin memilih kembali
        while (true) {
            // POINT F: Exception Handling untuk menangani potensi error pada koneksi database atau input user
            try {
                // POINT H: Inisialisasi koneksi database MySQL menggunakan driver JDBC via class Config
                conn = Config.configDB();
                System.out.println("\n" + "╔" + "═".repeat(53) + "╗");
                System.out.println("║              LAPORAN & PENJUALAN SISTEM             ║");
                System.out.println("╠" + "═".repeat(53) + "╣");
                System.out.println("║  [1] Rekap Penjualan Hari Ini                       ║");
                System.out.println("║  [2] Rekap Penjualan Per Bulan                      ║");
                System.out.println("║  [3] Rekap Penjualan Per Tahun                      ║");
                System.out.println("║  [4] Rekap Seluruh Transaksi                        ║");
                System.out.println("║  [5] Cari & Cetak Ulang Nota (Per Faktur)           ║");
                System.out.println("║  [0] Kembali                                        ║");
                System.out.println("╚" + "═".repeat(53) + "╝");
                
                System.out.print("  Pilih jenis rekap [1-5/0] >> ");
                String pStr = input.nextLine();

                // POINT D: Percabangan (if) untuk menangani aksi kembali ke Menu Utama
                if (pStr.equals("0")) {
                    animasiKecil("\n[*] Sedang kembali ke Menu Utama", 3);
                    return;
                }

                String sql = "";
                String judulRekap = "";
                PreparedStatement ps = null;

                /**
                 * POINT D: Implementasi Percabangan (Switch-Case) 
                 * Digunakan untuk menentukan Query SQL dan filter data secara dinamis berdasarkan input admin.
                 */
                switch (pStr) {
                    case "1":
                        // POINT H: SQL Query untuk filter data berdasarkan tanggal saat ini (CURDATE)
                        sql = "SELECT tgl_transaksi, nama_pembeli, total_bayar_akhir FROM transaksi WHERE DATE(tgl_transaksi) = CURDATE() ORDER BY tgl_transaksi ASC";
                        judulRekap = "HARI INI (" + new java.text.SimpleDateFormat("dd/MM/yyyy").format(new java.util.Date()) + ")";
                        ps = conn.prepareStatement(sql);
                        break;
                    case "2":
                        System.out.print("  Masukkan Bulan (1-12) >> ");
                        String m = input.nextLine();
                        // POINT H: SQL Query untuk filter data berdasarkan Bulan (MONTH) dan Tahun berjalan
                        sql = "SELECT tgl_transaksi, nama_pembeli, total_bayar_akhir FROM transaksi WHERE MONTH(tgl_transaksi) = ? AND YEAR(tgl_transaksi) = YEAR(CURDATE()) ORDER BY tgl_transaksi ASC";
                        judulRekap = "BULAN " + m + " TAHUN " + Calendar.getInstance().get(Calendar.YEAR);
                        ps = conn.prepareStatement(sql);
                        ps.setString(1, m);
                        break;
                    case "3":
                        System.out.print("  Masukkan Tahun (Contoh: 2025) >> ");
                        String y = input.nextLine();
                        // POINT H: SQL Query untuk filter data berdasarkan variabel Tahun (YEAR) tertentu
                        sql = "SELECT tgl_transaksi, nama_pembeli, total_bayar_akhir FROM transaksi WHERE YEAR(tgl_transaksi) = ? ORDER BY tgl_transaksi ASC";
                        judulRekap = "TAHUN " + y;
                        ps = conn.prepareStatement(sql);
                        ps.setString(1, y);
                        break;
                    case "4":
                        // POINT H: SQL Query untuk menarik seluruh sejarah transaksi tanpa filter
                        sql = "SELECT tgl_transaksi, nama_pembeli, total_bayar_akhir FROM transaksi ORDER BY tgl_transaksi ASC";
                        judulRekap = "SELURUH TRANSAKSI";
                        ps = conn.prepareStatement(sql);
                        break;
                    case "5":
                        System.out.print("  Masukkan Nomor Faktur >> ");
                        String f = input.nextLine();
                        // POINT H: Operasi READ untuk mencari satu data spesifik menggunakan PreparedStatement
                        PreparedStatement psT = conn.prepareStatement("SELECT nama_pembeli, kategori_pembeli FROM transaksi WHERE no_faktur=?");
                        psT.setString(1, f);
                        ResultSet rsT = psT.executeQuery();
                        if (rsT.next()) {
                            // Memanggil method helper untuk mencetak nota rincian
                            hitungDanTampilkanRingkasan(f, rsT.getString(1), rsT.getString(2));
                        } else System.err.println("\n[!] GAGAL: Faktur tidak ditemukan!");
                        System.out.println("\n[ Tekan ENTER untuk kembali ]");
                        input.nextLine();
                        continue;
                    default:
                        // POINT F: Error handling jika user memasukkan pilihan di luar jangkauan menu
                        System.err.println("\n[!] GAGAL: Pilihan '" + pStr + "' tidak tersedia!");
                        Thread.sleep(1000);
                        continue;
                }

                // Melakukan pemrosesan jika objek PreparedStatement telah terisi
                if (ps != null) {
                    animasiKecil("  [*] Menarik data dari database", 3);
                    ResultSet rs = ps.executeQuery();
                    
                    // POINT G: Implementasi ArrayList sebagai wadah penampung baris laporan secara dinamis (Collection Framework)
                    ArrayList<String> listRekap = new ArrayList<>();
                    double grandTotal = 0;
                    
                    // POINT D: Perulangan (while) untuk memproses kursor data ResultSet
                    while (rs.next()) {
                        // POINT E: Manipulasi format Date untuk tampilan tanggal yang lebih ringkas
                        String tgl = new java.text.SimpleDateFormat("dd/MM/yy").format(rs.getTimestamp(1));
                        String nm = rs.getString(2);
                        
                        // POINT E: Manipulasi String (substring) untuk membatasi panjang nama agar tabel tetap rapi
                        if (nm.length() > 17) nm = nm.substring(0, 14) + "...";
                        
                        double tot = rs.getDouble(3);
                        
                        // POINT E: Implementasi format String menggunakan printf-style untuk meratakan kolom tabel
                        listRekap.add(String.format("  │ %-10s │ %-17s │ %17s │", tgl, nm, formatter.format(tot)));
                        
                        // POINT D: Perhitungan Matematika untuk akumulasi total pendapatan dalam rekap tersebut
                        grandTotal += tot;
                    }

                    System.out.println("\n  " + "═".repeat(50));
                    System.out.println("  REKAP: " + judulRekap);
                    System.out.println("  " + "═".repeat(50));
                    
                    // Konstruksi visual tabel dengan pembatas yang presisi
                    System.out.println("  ┌────────────┬───────────────────┬───────────────────┐");
                    System.out.println("  │   TANGGAL  │     PELANGGAN     │       TOTAL       │");
                    System.out.println("  ├────────────┼───────────────────┼───────────────────┤");

                    // Validasi apakah data ditemukan atau kosong
                    if (listRekap.isEmpty()) {
                        System.out.println("  │          --- Tidak ada data ditemukan ---          │");
                    } else {
                        // POINT G: Iterasi (For-Each) melalui Collection ArrayList untuk menampilkan seluruh baris laporan
                        for (String baris : listRekap) { System.out.println(baris); }
                    }
                    System.out.println("  └────────────┴───────────────────┴───────────────────┘");
                    // Menampilkan akumulasi akhir pendapatan
                    System.out.println("   TOTAL PENDAPATAN : " + formatter.format(grandTotal));
                    System.out.println("  " + "═".repeat(50));
                    System.out.println("\n[ Tekan ENTER untuk kembali ]");
                    input.nextLine();
                }

            } catch (Exception e) {
                // POINT F: Exception Handling global pada method laporan
                System.err.println("[!] Gagal memproses laporan.");
            }
        }
    }
}