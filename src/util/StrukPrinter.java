package util;

import java.time.format.DateTimeFormatter;
import model.ItemPesanan;
import model.MenuItem;
import model.Transaksi;
import service.TransaksiService;

/**
 * Mencetak struk transaksi ke layar CLI. Dipakai Kasir setelah pembayaran
 * berhasil dan Admin saat melihat rincian transaksi di riwayat.
 */
public class StrukPrinter {

    /** Format tanggal dan jam di struk. */
    private static final DateTimeFormatter FORMAT_WAKTU =
            DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

    /** Class bantu, tidak perlu dibuat objeknya. */
    private StrukPrinter() {
    }

    /**
     * Mencetak struk dari sebuah transaksi.
     *
     * @param transaksi transaksi yang akan dicetak struknya
     */
    public static void cetak(Transaksi transaksi) {
        String garis = "-".repeat(ConsoleHelper.LEBAR);

        System.out.println();
        ConsoleHelper.cetakJudul("STRUK PEMBAYARAN - KASIR RESTO");
        ConsoleHelper.cetak(ConsoleHelper.baris("ID Transaksi", transaksi.getIdTransaksi()));
        ConsoleHelper.cetak(ConsoleHelper.baris("ID Pesanan", transaksi.getPesanan().getIdPesanan()));
        ConsoleHelper.cetak(ConsoleHelper.baris("Waktu", transaksi.getWaktu().format(FORMAT_WAKTU)));
        ConsoleHelper.cetak(ConsoleHelper.baris("Kasir", transaksi.getNamaKasir()));
        ConsoleHelper.cetak(ConsoleHelper.baris("Meja",
                String.valueOf(transaksi.getPesanan().getNomorMeja())));
        ConsoleHelper.cetak(garis);

        for (ItemPesanan item : transaksi.getPesanan().getDaftarItem()) {
            MenuItem menu = item.getMenu();
            // harga satuan diambil dari hitungHarga(1), jadi tambahan Large ikut terhitung
            ConsoleHelper.cetak(ConsoleHelper.potong(
                    menu.getNama() + " (" + menu.getKeterangan() + ")", ConsoleHelper.LEBAR));
            ConsoleHelper.cetak(ConsoleHelper.baris(
                    "  " + item.getJumlah() + " x " + ConsoleHelper.formatRupiah(menu.hitungHarga(1)),
                    ConsoleHelper.formatRupiah(item.getSubtotal())));
        }

        ConsoleHelper.cetak(garis);
        ConsoleHelper.cetak(ConsoleHelper.baris("Subtotal",
                ConsoleHelper.formatRupiah(transaksi.getSubtotal())));
        ConsoleHelper.cetak(ConsoleHelper.baris(TransaksiService.LABEL_PAJAK,
                ConsoleHelper.formatRupiah(transaksi.getPajak())));
        ConsoleHelper.cetak(ConsoleHelper.baris("TOTAL",
                ConsoleHelper.formatRupiah(transaksi.getTotal())));
        ConsoleHelper.cetak(garis);
        ConsoleHelper.cetak(ConsoleHelper.baris("Metode Bayar", transaksi.getMetodePembayaran()));
        ConsoleHelper.cetakGaris();
        ConsoleHelper.cetak("   Terima kasih atas kunjungan Anda!");
        ConsoleHelper.cetakGaris();
    }
}
