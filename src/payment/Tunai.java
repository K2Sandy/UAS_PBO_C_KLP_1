package payment;

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
        // TODO: minta uang diterima lewat ConsoleHelper.bacaDesimal,
        //   kalau kurang dari total tampilkan pesan dan return false,
        //   kalau cukup tampilkan kembalian dan return true.
        return false;
    }
}
