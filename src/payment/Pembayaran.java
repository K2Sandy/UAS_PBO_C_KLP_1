package payment;

/**
 * Kontrak untuk semua metode pembayaran (abstraction).
 * Setiap metode pembayaran (Tunai, QRIS, Debit) wajib mengimplementasikan
 * method di bawah ini dengan caranya masing-masing (polymorphism).
 */
public interface Pembayaran {

    /**
     * Mengambil nama metode pembayaran untuk ditampilkan di menu dan struk.
     *
     * @return nama metode, contoh: "Tunai"
     */
    String getNamaMetode();

    /**
     * Memproses pembayaran sejumlah total yang harus dibayar.
     *
     * @param total jumlah yang harus dibayar pelanggan
     * @return true jika pembayaran berhasil, false jika gagal atau dibatalkan
     */
    boolean bayar(double total);
}
