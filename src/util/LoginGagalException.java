package util;

/**
 * Exception yang dilempar saat login gagal,
 * misalnya username tidak ditemukan atau password salah.
 */
public class LoginGagalException extends Exception {

    /**
     * Membuat exception login gagal.
     *
     * @param pesan penjelasan penyebab login gagal
     */
    public LoginGagalException(String pesan) {
        super(pesan);
    }
}
