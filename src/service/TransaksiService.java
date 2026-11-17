package service;

import java.time.format.DateTimeFormatter;

import model.ItemPesanan;
import model.Kasir;
import model.Pesanan;
import model.Transaksi;
import payment.Debit;
import payment.Pembayaran;
import payment.Qris;
import payment.Tunai;
import util.ConsoleHelper;
import util.StrukPrinter;

/**
 * Layanan pembayaran dan riwayat transaksi untuk Kasir.
 */
public class TransaksiService {

    /** Tarif pajak 10 persen. */
    private static final double TARIF_PAJAK = 0.10;

    /**
     * Memproses pembayaran sebuah pesanan sampai struk tercetak.
     *
     * @param pesanan pesanan yang akan dibayar
     * @param kasir   kasir yang memproses pembayaran
     */
    public void prosesPembayaran(Pesanan pesanan, Kasir kasir) {
        // TODO:
        // 1. Hitung subtotal, pajak, dan total
        double subtotal = pesanan.hitungSubtotal();
        double pajak = subtotal * TARIF_PAJAK;
        double total = subtotal + pajak;
        // 2. Tampilkan ringkasan pesanan
        ConsoleHelper.cetakJudul("RINGKASAN PESANAN " + pesanan.getIdPesanan()
                + " (Meja " + pesanan.getNomorMeja() + ")");
        for (ItemPesanan item : pesanan.getDaftarItem()) {
            ConsoleHelper.cetak(String.format("%-20s %3d x %12s",
                    item.getMenu().getNama(),
                    item.getJumlah(),
                    ConsoleHelper.formatRupiah(item.getSubtotal())));
        }
        ConsoleHelper.cetak("-".repeat(44));
        ConsoleHelper.cetak(String.format("%-20s %23s", "Subtotal", ConsoleHelper.formatRupiah(subtotal)));
        ConsoleHelper.cetak(String.format("%-20s %23s", "Pajak (10%)", ConsoleHelper.formatRupiah(pajak)));
        ConsoleHelper.cetak(String.format("%-20s %23s", "TOTAL", ConsoleHelper.formatRupiah(total)));

        // Pilihan metode pembayaran (polymorphism lewat interface Pembayaran)
        Pembayaran[] metode = { new Tunai(), new Qris(), new Debit() };

        while (true) {
            ConsoleHelper.cetak("");
            ConsoleHelper.cetak("Pilih metode pembayaran:");
            for (int i = 0; i < metode.length; i++) {
                ConsoleHelper.cetak((i + 1) + ". " + metode[i].getNamaMetode());
            }
            ConsoleHelper.cetak("0. Batalkan pembayaran");
            int pilihan = ConsoleHelper.bacaPilihan("Pilih: ", 0, metode.length);

            Pembayaran dipilih = metode[pilihan - 1];
        }
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
