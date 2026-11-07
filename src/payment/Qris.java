package payment;

/**
 * Pembayaran lewat QRIS. Pada simulasi ini sistem hanya menampilkan
 * instruksi scan lalu menunggu konfirmasi kasir.
 */
public class Qris implements Pembayaran {

    /**
     * Mengambil nama metode pembayaran.
     *
     * @return teks "QRIS"
     */
    @Override
    public String getNamaMetode() {
        return "QRIS";
    }

    /**
     * Memproses pembayaran QRIS.
     *
     * @param total jumlah yang harus dibayar pelanggan
     * @return true jika kasir mengonfirmasi pembayaran berhasil
     */
    @Override
    public boolean bayar(double total) {
        // TODO: tampilkan total dan instruksi scan QRIS,
        //   lalu tanyakan "Pembayaran berhasil? (1 = ya, 0 = batal)"
        //   dan return sesuai jawaban kasir.
        return false;
    }
}
