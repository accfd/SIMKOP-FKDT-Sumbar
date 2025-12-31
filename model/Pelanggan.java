package model;

/**
 * POINT A: Implementasi Class (Entitas Dasar)
 * POINT C: Implementasi Super Class (Induk dari kelas Distributor)
 */
public class Pelanggan {
    // Properti publik untuk menyimpan data identitas pelanggan
    public String nama;

    /**
     * POINT A: Constructor Class Pelanggan
     * Digunakan untuk inisialisasi awal atribut saat objek dibuat di memori
     */
    public Pelanggan(String nama) {
        this.nama = nama;
    }
}