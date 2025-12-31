package service;

/**
 * POINT B: Implementasi Interface (Abstraksi Sistem)
 * File ini berfungsi sebagai kontrak atau cetak biru (blueprint) yang mendefinisikan 
 * seluruh tindakan utama yang harus tersedia di dalam sistem manajemen koperasi.
 */
public interface SistemAksi {
    
    // POINT H: Operasi READ - Mendefinisikan kontrak untuk menampilkan informasi stok
    void cekSeluruhStok();     
    
    // POINT H: Operasi UPDATE - Mendefinisikan kontrak untuk pembaruan inventaris/stok gudang
    void kelolaStok();         
    
    // POINT H: Operasi CREATE - Mendefinisikan kontrak untuk pembuatan transaksi pesanan baru
    void tambahPesanan();      
    
    // POINT H: Operasi UPDATE & DELETE - Kontrak untuk manajemen logistik dan pembatalan data
    void updateStatusKelola(); 
    
    // POINT H: Operasi READ - Mendefinisikan kontrak untuk penarikan laporan omzet periodik
    void riwayatLaporan();     
}