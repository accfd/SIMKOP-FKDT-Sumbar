package model;

/**
 * POINT C: Implementasi Inheritance (Sub Class)
 * Kelas ini membuktikan prinsip pewarisan, di mana Distributor mewarisi 
 * seluruh atribut dari Super Class Pelanggan.
 */
public class Distributor extends Pelanggan {
    
    /**
     * POINT A: Constructor Sub Class
     * Digunakan untuk menciptakan objek Distributor di memori sistem.
     */
    public Distributor(String nama) {
        /**
         * POINT C: Penggunaan keyword 'super'
         * Berfungsi untuk mengirim data nama ke Constructor milik Super Class (Pelanggan).
         */
        super(nama);
    }

    /**
     * POINT D: Perhitungan Matematika Bisnis
     * Implementasi logika pemotongan harga tetap sebesar Rp5.000 khusus untuk Distributor.
     * Metode ini melakukan kalkulasi otomatis: (Harga - 5000) dikali Jumlah Pesanan.
     */
    public double hitungTotalDistributor(double hargaAsli, int jumlah) {
        return (hargaAsli - 5000) * jumlah;
    }
}