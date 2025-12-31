import java.util.Scanner;
import java.util.Date;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import java.util.Locale;
import service.KoperasiService;

/**
 * POINT A: Implementasi Class Utama (Entry Point)
 * Kelas ini berfungsi sebagai motor penggerak seluruh siklus hidup sistem 
 * manajemen koperasi, mulai dari login hingga manajemen dashboard.
 */
public class Main {
    public static void main(String[] args) {
        /**
         * POINT A: Instansiasi objek dari class KoperasiService
         * Menciptakan objek 'koperasi' untuk mengakses seluruh layanan logika bisnis.
         */
        KoperasiService koperasi = new KoperasiService();
        Scanner terminalInput = new Scanner(System.in);
        
        /**
         * POINT D: Perulangan (while-loop) Utama
         * Menjaga aplikasi tetap berjalan pada halaman login hingga data valid ditemukan.
         */
        while (true) {
            // --- HALAMAN LOGIN PREMIUM ---
            System.out.println("\n" + "╔" + "═".repeat(53) + "╗");
            System.out.println("║    SISTEM MANAJEMEN KOPERASI DPW FKDT SUMBAR        ║");
            System.out.println("║  DISTRIBUSI SERAGAM NASIONAL SANTRI MDTA SE-SUMBAR  ║");
            System.out.println("╚" + "═".repeat(53) + "╝");
            System.out.println(" Silakan login dengan username dan PIN Admin.");
            System.out.println("-".repeat(55));
            
            System.out.print(" USERNAME : ");
            String username = terminalInput.nextLine();
            System.out.print(" PIN      : ");
            String pinInput = terminalInput.nextLine();

            /**
             * POINT D: Percabangan (if-else) Validasi Login
             * Menggunakan logic gate untuk membatasi hak akses sistem hanya bagi admin.
             */
            if (username.equalsIgnoreCase("admin") && pinInput.equals("123")) {
                System.out.print("\n[*] Memverifikasi Identitas");
                animasiLoading(3, 400); 
                
                System.out.println("\n[*] LOGIN BERHASIL! Selamat Datang, " + username.toUpperCase());
                
                /**
                 * Mengalihkan alur program ke Dashboard Utama
                 */
                jalankanDashboard(koperasi, terminalInput, username);
            } else {
                /**
                 * POINT F: Error Handling Sederhana
                 * Memberikan feedback kegagalan jika kredensial tidak sesuai.
                 */
                System.err.println("\n[!] LOGIN GAGAL: Username atau PIN Salah!");
                try { Thread.sleep(1000); } catch (InterruptedException e) {}
            }
        }
    }

    /**
     * Method untuk mengelola alur Menu Utama (Dashboard)
     */
    private static void jalankanDashboard(KoperasiService koperasi, Scanner input, String user) {
        boolean loggedIn = true;
        
        /**
         * POINT D: Perulangan (while-loop) Dashboard
         * Menjaga admin tetap berada di dalam sistem selama status loggedIn true.
         */
        while (loggedIn) {
            /**
             * POINT E: Manipulasi String & Date
             * Menggunakan class Calendar untuk mendeteksi waktu secara real-time 
             * dan SimpleDateFormat untuk formatting tanggal Indonesia yang human-readable.
             */
            Calendar cal = Calendar.getInstance();
            int jam = cal.get(Calendar.HOUR_OF_DAY);
            String sapaan = (jam < 11) ? "Pagi" : (jam < 15) ? "Siang" : (jam < 18) ? "Sore" : "Malam";
            
            Locale lokalIndo = new Locale("id", "ID");
            SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd MMMM yyyy, HH:mm", lokalIndo);
            String tglIndo = sdf.format(new Date());

            // --- HEADER DASHBOARD UTAMA ---
            System.out.println("\n" + "╔" + "═".repeat(53) + "╗");
            System.out.println("║    SISTEM MANAJEMEN KOPERASI DPW FKDT SUMBAR        ║");
            System.out.println("║  DISTRIBUSI SERAGAM NASIONAL SANTRI MDTA SE-SUMBAR  ║");
            System.out.println("╠" + "═".repeat(53) + "╣");
            
            // Centering Waktu (Manipulasi Spasi String)
            int padTgl = (53 - tglIndo.length()) / 2;
            System.out.printf("║%s%s%s║%n", " ".repeat(padTgl), tglIndo, " ".repeat(53 - tglIndo.length() - padTgl));
            
            // Centering Sapaan (Manipulasi String toUpperCase/toLowerCase)
            String strSapaan = "Selamat " + sapaan + ", " + user.substring(0, 1).toUpperCase() + user.substring(1).toLowerCase() + "!";
            int padSapaan = (53 - strSapaan.length()) / 2;
            System.out.printf("║%s%s%s║%n", " ".repeat(padSapaan), strSapaan, " ".repeat(53 - strSapaan.length() - padSapaan));
            
            System.out.println("╠" + "═".repeat(53) + "╣");
            
            // --- MENU LAYANAN ---
            System.out.println("║                                                     ║");
            System.out.println("║   [1] Cek Informasi Stok & Katalog Harga            ║");
            System.out.println("║   [2] Kelola Persediaan (Tambah/Edit Stok)          ║");
            System.out.println("║   [3] Buat Pesanan Baru (Input Faktur)              ║");
            System.out.println("║   [4] Update Status Kiriman & Kelola Pesanan        ║");
            System.out.println("║   [5] Lihat Laporan Penjualan & Omzet               ║");
            System.out.println("║   [0] Logout & Selesai Bertugas                     ║");
            System.out.println("║                                                     ║");
            System.out.println("╚" + "═".repeat(53) + "╝");
            
            System.out.println("═".repeat(55)); 
            System.out.print("  PILIH MENU [0-5] >> "); 
            String pilihan = input.nextLine();
            System.out.println("═".repeat(55));

            /**
             * POINT D: Percabangan (Switch-Case)
             * Menangani logika pemilihan menu layanan koperasi secara efisien.
             */
            switch (pilihan) {
                case "1": 
                    koperasi.cekSeluruhStok(); 
                    break; 
                case "2": 
                    koperasi.kelolaStok(); 
                    break; 
                case "3": 
                    koperasi.tambahPesanan(); 
                    break; 
                case "4": 
                    koperasi.updateStatusKelola(); 
                    break; 
                case "5": 
                    koperasi.riwayatLaporan();     
                    break; 
                case "0":
                    System.out.println("\n" + "─".repeat(55));
                    System.out.print("  [*] Mengamankan Data & Menutup Koneksi");
                    animasiLoading(5, 300);
                    System.out.println("\n  [*] Sesi Berakhir. Seluruh Data Tersimpan Aman.");
                    System.out.println("─".repeat(55));
                    loggedIn = false; 
                    break;
                default:
                    /**
                     * Feedback Gagal jika input menu tidak sesuai range 0-5
                     */
                    System.err.println("\n[!] PERINGATAN: Menu '" + pilihan + "' Tidak Tersedia!");
                    break;
            }
        }
    }

    /**
     * Method Pendukung Animasi
     * POINT D: Implementasi Perulangan for-loop untuk visual efek loading
     */
    private static void animasiLoading(int titik, int speed) {
        try {
            for (int i = 0; i < titik; i++) {
                Thread.sleep(speed); 
                System.out.print(".");
            }
        } catch (InterruptedException e) {
            /**
             * POINT F: Exception Handling untuk menangani gangguan pada thread sleep
             */
            Thread.currentThread().interrupt();
        }
    }
}