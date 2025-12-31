package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// POINT A: Implementasi Class untuk mengatur konfigurasi database
public class Config {
    // Properti private untuk menyimpan state koneksi database
    private static Connection mysqlConfig;

    // POINT A: Method static (Objek) untuk menyediakan akses koneksi database ke seluruh sistem
    public static Connection configDB() throws SQLException {
        // POINT F: Exception Handling untuk mencegah program berhenti jika server MySQL mati
        try {
            // Konfigurasi parameter akses database
            String url = "jdbc:mysql://localhost:3306/db_koperasi_fkdt";
            String user = "root";
            String pass = "";

            // POINT H: Menggunakan JDBC (Java Database Connectivity) untuk registrasi driver
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
            
            // POINT H: Proses inisialisasi koneksi (READ/WRITE Akses) ke database MySQL
            mysqlConfig = DriverManager.getConnection(url, user, pass);
            
        } catch (Exception e) {
            // POINT F: Menampilkan pesan error spesifik jika terjadi kegagalan sistem database
            System.err.println("Koneksi Database Gagal: " + e.getMessage());
        }
        return mysqlConfig;
    }
}