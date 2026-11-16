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

         for (Transaksi t : DataStore.riwayat) {
            System.out.printf("%-8s %-17s %-12s %-10s %,12.0f%n",
                    t.getIdTransaksi(),
                    t.getWaktu().format(format),
                    t.getNamaKasir(),
                    t.getMetodePembayaran(),
                    t.getTotal());
        }
    }

    /**
     * Menampilkan laporan penjualan untuk hari ini:
     * jumlah transaksi dan total pendapatan.
     */
    public void laporanHarian() {
        LocalDate hariIni = LocalDate.now();
        int jumlah = 0;
        double totalPendapatan = 0;

        for (Transaksi t : DataStore.riwayat) {
            if (t.getWaktu().toLocalDate().equals(hariIni)) {
                jumlah++;
                totalPendapatan += t.getTotal();
            }
        }

        System.out.println("=== LAPORAN PENJUALAN HARI INI ===");
        System.out.println("Jumlah transaksi : " + jumlah);
        System.out.printf("Total pendapatan : Rp %,.0f%n", totalPendapatan);
    }
}