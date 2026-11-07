package service;

import model.Kasir;
import model.Pesanan;

/**
 * Layanan pembayaran dan riwayat transaksi untuk Kasir.
 */
public class TransaksiService {

    /**
     * Memproses pembayaran sebuah pesanan sampai struk tercetak.
     *
     * @param pesanan pesanan yang akan dibayar
     * @param kasir   kasir yang memproses pembayaran
     */
    public void prosesPembayaran(Pesanan pesanan, Kasir kasir) {
        // TODO:
        //   1. Hitung subtotal, pajak 10 persen, dan total.
        //   2. Tampilkan ringkasan, minta kasir memilih metode
        //      (Tunai, Qris, atau Debit) lalu panggil bayar(total) milik
        //      objek Pembayaran yang dipilih.
        //   3. Kalau pembayaran berhasil: buat objek Transaksi, isi dengan
        //      setter, add ke DataStore.riwayat, lalu StrukPrinter.cetak(transaksi).
        //   4. Kalau gagal: tampilkan pesan dan jangan simpan transaksi.
    }

    /**
     * Menampilkan riwayat transaksi milik satu kasir saja.
     *
     * @param kasir kasir yang riwayatnya ditampilkan
     */
    public void tampilkanRiwayatKasir(Kasir kasir) {
        // TODO: dari DataStore.riwayat, pilih transaksi yang
        //   getNamaKasir()-nya sama dengan kasir.getNamaLengkap(), lalu cetak
        //   sebagai tabel. Kalau kosong, tampilkan "Belum ada transaksi."
    }
}
