package util;

import model.Transaksi;

/**
 * Mencetak struk transaksi ke layar CLI.
 */
public class StrukPrinter {

    /** Class bantu, tidak perlu dibuat objeknya. */
    private StrukPrinter() {
    }

    /**
     * Mencetak struk dari sebuah transaksi.
     *
     * @param transaksi transaksi yang akan dicetak strukinya
     */
    public static void cetak(Transaksi transaksi) {
        // TODO: cetak struk yang rapi memakai ConsoleHelper:
        //   judul restoran, id transaksi, waktu, nama kasir, nomor meja,
        //   daftar item (nama, jumlah, subtotal), subtotal, pajak, total,
        //   dan metode pembayaran. Pakai ConsoleHelper.formatRupiah untuk uang.
    }
}
