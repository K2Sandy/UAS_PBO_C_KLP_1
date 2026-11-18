package payment;

import util.ConsoleHelper;

/**
 * Pembayaran secara tunai. Kasir memasukkan uang yang diterima,
 * lalu sistem menghitung kembaliannya.
 */
public class Tunai implements Pembayaran {

    /** Membuat metode pembayaran tunai. */
    public Tunai() {
    }

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
     * Memproses pembayaran tunai. Kasir memasukkan uang yang diterima
     * (boleh pakai titik ribuan, contoh 50.000). Jika uang kurang,
     * kasir diminta memasukkan ulang; ketik 0 untuk membatalkan.
     *
     * @param total jumlah yang harus dibayar pelanggan
     * @return true jika uang yang diterima cukup, false jika dibatalkan
     */
    @Override
    public boolean bayar(double total) {
        ConsoleHelper.cetakJudul("PEMBAYARAN TUNAI");
        ConsoleHelper.cetak("Total yang harus dibayar: " + ConsoleHelper.formatRupiah(total));

        while (true) {
            long uangDiterima = ConsoleHelper.bacaRupiah("Uang diterima (Rp, 0 = batal): ");
            if (uangDiterima == 0) {
                ConsoleHelper.cetak("Pembayaran tunai dibatalkan.");
                return false;
            }
            if (uangDiterima < total) {
                ConsoleHelper.cetak("Uang kurang " + ConsoleHelper.formatRupiah(total - uangDiterima)
                        + ". Masukkan lagi jumlah uang yang diterima.");
                continue;
            }
            ConsoleHelper.cetak("Kembalian: " + ConsoleHelper.formatRupiah(uangDiterima - total));
            ConsoleHelper.cetak("Pembayaran tunai berhasil.");
            return true;
        }
    }
}
