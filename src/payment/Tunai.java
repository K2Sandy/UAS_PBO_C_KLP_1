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
        System.out.println("\n=== PEMBAYARAN TUNAI ===");
        System.out.printf("Total yang harus dibayar: Rp%,.0f%n", total);

        double uangDiterima = ConsoleHelper.bacaDesimal("Uang diterima: Rp");

        if (uangDiterima < total) {
            double kurang = total - uangDiterima;
            System.out.printf("Uang kurang Rp%,.0f. Pembayaran dibatalkan.%n", kurang);
            return false;
        }

        double kembalian = uangDiterima - total;
        System.out.printf("Kembalian: Rp%,.0f%n", kembalian);
        System.out.println("Pembayaran tunai berhasil.");
        return true;
    }
}
