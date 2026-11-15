package util;

import java.time.format.DateTimeFormatter;

import model.ItemPesanan;
import model.Transaksi;

/**
 * Mencetak struk transaksi ke layar CLI.
 */
public class StrukPrinter {

    private static final int LEBAR = 44;
    private static final DateTimeFormatter FORMAT_WAKTU =
            DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

    /** Class bantu, tidak perlu dibuat objeknya. */
    private StrukPrinter() {
    }

    /**
     * Mencetak struk dari sebuah transaksi.
     *
     * @param transaksi transaksi yang akan dicetak strukinya
     */
    public static void cetak(Transaksi transaksi) {
        System.out.println();
        ConsoleHelper.cetakJudul("STRUK PEMBAYARAN - KASIR RESTO");

        ConsoleHelper.cetak(baris("ID Transaksi", transaksi.getIdTransaksi()));
        ConsoleHelper.cetak(baris("Waktu", transaksi.getWaktu().format(FORMAT_WAKTU)));
        ConsoleHelper.cetak(baris("Kasir", transaksi.getNamaKasir()));
        ConsoleHelper.cetak(baris("Meja", String.valueOf(transaksi.getPesanan().getNomorMeja())));
        ConsoleHelper.cetak("-".repeat(LEBAR));

        for (ItemPesanan item : transaksi.getPesanan().getDaftarItem()) {
            double subtotalItem = item.getSubtotal();
            double hargaSatuan = item.getJumlah() > 0 ? subtotalItem / item.getJumlah() : 0;

            ConsoleHelper.cetak(item.getMenu().getNama());
            ConsoleHelper.cetak(baris(
                    "  " + item.getJumlah() + " x " + ConsoleHelper.formatRupiah(hargaSatuan),
                    ConsoleHelper.formatRupiah(subtotalItem)));
        }

        ConsoleHelper.cetak("-".repeat(LEBAR));
        ConsoleHelper.cetak(baris("Subtotal", ConsoleHelper.formatRupiah(transaksi.getSubtotal())));
        ConsoleHelper.cetak(baris("Pajak (10%)", ConsoleHelper.formatRupiah(transaksi.getPajak())));
        ConsoleHelper.cetak(baris("TOTAL", ConsoleHelper.formatRupiah(transaksi.getTotal())));
        ConsoleHelper.cetak("-".repeat(LEBAR));
        ConsoleHelper.cetak(baris("Metode Bayar", transaksi.getMetodePembayaran()));

        ConsoleHelper.cetakGaris();
        ConsoleHelper.cetak("   Terima kasih atas kunjungan Anda!");
        ConsoleHelper.cetakGaris();
    }

    /**
     * Membuat satu baris dengan teks kiri dan teks kanan rata pada lebar struk.
     *
     * @param kiri  teks di sisi kiri
     * @param kanan teks di sisi kanan
     * @return baris yang sudah diformat
     */
    private static String baris(String kiri, String kanan) {
        int spasi = Math.max(1, LEBAR - kiri.length() - kanan.length());
        return kiri + " ".repeat(spasi) + kanan;
    }
}
