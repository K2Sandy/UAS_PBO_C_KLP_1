package util;

/**
 * Exception yang dilempar saat stok menu tidak cukup
 * untuk jumlah yang dipesan.
 */
public class StokHabisException extends Exception {

    /**
     * Membuat exception stok habis.
     *
     * @param pesan penjelasan menu mana yang stoknya tidak cukup
     */
    public StokHabisException(String pesan) {
        super(pesan);
    }
}
