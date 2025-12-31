-- phpMyAdmin SQL Dump
-- version 5.2.2
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Waktu pembuatan: 31 Des 2025 pada 08.53
-- Versi server: 8.0.30
-- Versi PHP: 8.1.10

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `db_koperasi_fkdt`
--

-- --------------------------------------------------------

--
-- Struktur dari tabel `detail_transaksi`
--

CREATE TABLE `detail_transaksi` (
  `id_detail` int NOT NULL,
  `no_faktur` varchar(25) DEFAULT NULL,
  `id_seragam` int DEFAULT NULL,
  `jumlah` int NOT NULL,
  `subtotal` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data untuk tabel `detail_transaksi`
--

INSERT INTO `detail_transaksi` (`id_detail`, `no_faktur`, `id_seragam`, `jumlah`, `subtotal`) VALUES
(37, 'FKDT-1767164112946', 11, 1, 140000);

-- --------------------------------------------------------

--
-- Struktur dari tabel `seragam`
--

CREATE TABLE `seragam` (
  `id_seragam` int NOT NULL,
  `nomor_ukuran` varchar(5) NOT NULL,
  `jenis_kelamin` enum('Putra','Putri') NOT NULL,
  `harga` double NOT NULL,
  `stok` int DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data untuk tabel `seragam`
--

INSERT INTO `seragam` (`id_seragam`, `nomor_ukuran`, `jenis_kelamin`, `harga`, `stok`) VALUES
(1, '2', 'Putra', 125000, 50),
(2, '2', 'Putri', 125000, 50),
(3, '3', 'Putra', 125000, 50),
(4, '3', 'Putri', 125000, 50),
(5, '4', 'Putra', 130000, 50),
(6, '4', 'Putri', 130000, 50),
(7, '5', 'Putra', 130000, 50),
(8, '5', 'Putri', 130000, 50),
(9, '6', 'Putra', 130000, 50),
(10, '6', 'Putri', 130000, 50),
(11, '7', 'Putra', 140000, 49),
(12, '7', 'Putri', 140000, 50),
(13, '8', 'Putra', 140000, 50),
(14, '8', 'Putri', 140000, 50),
(15, '9', 'Putra', 140000, 50),
(16, '9', 'Putri', 140000, 50),
(17, '10', 'Putra', 150000, 50),
(18, '10', 'Putri', 150000, 50);

-- --------------------------------------------------------

--
-- Struktur dari tabel `transaksi`
--

CREATE TABLE `transaksi` (
  `no_faktur` varchar(25) NOT NULL,
  `tgl_transaksi` datetime NOT NULL,
  `nama_pembeli` varchar(100) NOT NULL,
  `kategori_pembeli` enum('Distributor','Umum') NOT NULL,
  `total_bayar_akhir` double DEFAULT '0',
  `status_order` enum('Pending','Packing','Sent') DEFAULT 'Pending'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data untuk tabel `transaksi`
--

INSERT INTO `transaksi` (`no_faktur`, `tgl_transaksi`, `nama_pembeli`, `kategori_pembeli`, `total_bayar_akhir`, `status_order`) VALUES
('FKDT-1767164112946', '2025-12-31 13:55:49', 'Fuadi Dhiyaulhaq', 'Umum', 140000, 'Sent');

--
-- Indexes for dumped tables
--

--
-- Indeks untuk tabel `detail_transaksi`
--
ALTER TABLE `detail_transaksi`
  ADD PRIMARY KEY (`id_detail`),
  ADD KEY `fk_transaksi` (`no_faktur`),
  ADD KEY `fk_seragam` (`id_seragam`);

--
-- Indeks untuk tabel `seragam`
--
ALTER TABLE `seragam`
  ADD PRIMARY KEY (`id_seragam`);

--
-- Indeks untuk tabel `transaksi`
--
ALTER TABLE `transaksi`
  ADD PRIMARY KEY (`no_faktur`);

--
-- AUTO_INCREMENT untuk tabel yang dibuang
--

--
-- AUTO_INCREMENT untuk tabel `detail_transaksi`
--
ALTER TABLE `detail_transaksi`
  MODIFY `id_detail` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=40;

--
-- AUTO_INCREMENT untuk tabel `seragam`
--
ALTER TABLE `seragam`
  MODIFY `id_seragam` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=19;

--
-- Ketidakleluasaan untuk tabel pelimpahan (Dumped Tables)
--

--
-- Ketidakleluasaan untuk tabel `detail_transaksi`
--
ALTER TABLE `detail_transaksi`
  ADD CONSTRAINT `fk_seragam` FOREIGN KEY (`id_seragam`) REFERENCES `seragam` (`id_seragam`),
  ADD CONSTRAINT `fk_transaksi` FOREIGN KEY (`no_faktur`) REFERENCES `transaksi` (`no_faktur`) ON DELETE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
