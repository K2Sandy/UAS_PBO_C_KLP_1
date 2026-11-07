package payment;

/**
 * Pembayaran memakai kartu debit. Pada simulasi ini sistem meminta
 * kasir mengonfirmasi bahwa transaksi di mesin EDC berhasil.
 */
public class Debit implements Pembayaran {

    /**
     * Mengambil nama metode pembayaran.
     *
     * @return teks "Debit"
     */
    @Override
    public String getNamaMetode() {
        return "Debit";
    }

    /**
     * Memproses pembayaran debit.
     *
     * @param total jumlah yang harus dibayar pelanggan
     * @return true jika kasir mengonfirmasi transaksi berhasil
     */
    @Override
    public boolean bayar(double total) {
        // TODO: tampilkan total, minta konfirmasi dari kasir
        //   (1 = berhasil, 0 = gagal), lalu return sesuai jawaban.
        return false;
    }
}
