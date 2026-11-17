package util;

/**
 * Exception yang dilempar saat input pengguna tidak valid,
 * misalnya angka di luar rentang atau data yang sudah dipakai.
 */
public class InputTidakValidException extends Exception {

    /** Versi class untuk serialisasi (diwajibkan oleh Exception). */
    private static final long serialVersionUID = 1L;

    /**
     * Membuat exception input tidak valid.
     *
     * @param pesan penjelasan kenapa input tidak valid
     */
    public InputTidakValidException(String pesan) {
        super(pesan);
    }
}
