package service;

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
        // TODO: cetak isi DataStore.riwayat sebagai tabel.
        //   Kalau kosong, tampilkan "Belum ada transaksi."
    }

    /**
     * Menampilkan laporan penjualan untuk hari ini:
     * jumlah transaksi dan total pendapatan.
     */
    public void laporanHarian() {
        // TODO: pilih transaksi di DataStore.riwayat yang tanggalnya
        //   hari ini (Transaksi.getWaktu().toLocalDate() dibandingkan
        //   LocalDate.now()), lalu hitung jumlah dan total pendapatannya.
    }
}
