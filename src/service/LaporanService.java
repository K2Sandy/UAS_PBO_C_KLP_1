package service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import model.Transaksi;

/**
 * Layanan laporan untuk Admin: riwayat seluruh transaksi
 * dan laporan penjualan harian.
 * Datanya diambil dari DataStore.riwayat.
 */
public class LaporanService {

    /**
     * Menampilkan semua transaksi yang pernah terjadi:
     * id, waktu, kasir, metode pembayaran, dan total.
     */
    public void tampilkanSemuaRiwayat() {
        if (DataStore.riwayat.isEmpty()) {
            System.out.println("Belum ada transaksi.");
            return;
        }

        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

        System.out.println("=== RIWAYAT SEMUA TRANSAKSI ===");
        System.out.printf("%-8s %-17s %-12s %-10s %12s%n",
                "ID", "Waktu", "Kasir", "Metode", "Total");
        System.out.println("-------------------------------------------------------------");
