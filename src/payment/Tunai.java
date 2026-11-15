package payment;

import util.ConsoleHelper;

/**
 * Pembayaran secara tunai. Kasir memasukkan uang yang diterima,
 * lalu sistem menghitung kembaliannya.
 */
public class Tunai implements Pembayaran {

    /**
     * Mengambil nama metode pembayaran.
     *
     * @return teks "Tunai"
     */
    @Override
    public String getNamaMetode() {
        return "Tunai";
    }

    /**
     * Memproses pembayaran tunai.
     *
     * @param total jumlah yang harus dibayar pelanggan
     * @return true jika uang yang diterima cukup, false jika tidak
     */
        @Override
    public boolean bayar(double total) {
        ConsoleHelper.cetakJudul("PEMBAYARAN TUNAI");
        ConsoleHelper.cetak("Total yang harus dibayar: " + ConsoleHelper.formatRupiah(total));

        double uangDiterima = ConsoleHelper.bacaDesimal("Uang diterima: Rp");

        if (uangDiterima < total) {
            ConsoleHelper.cetak("Uang kurang " + ConsoleHelper.formatRupiah(total - uangDiterima)
                    + ". Pembayaran dibatalkan.");
            return false;
        }

        ConsoleHelper.cetak("Kembalian: " + ConsoleHelper.formatRupiah(uangDiterima - total));
        ConsoleHelper.cetak("Pembayaran tunai berhasil.");
        return true;
    }
}
