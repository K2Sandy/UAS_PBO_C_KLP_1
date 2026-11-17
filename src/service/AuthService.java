package service;

import model.Akun;
import util.LoginGagalException;

/**
 * Layanan login. Mencocokkan username dan password dengan data akun.
 */
public class AuthService {

    /** Membuat layanan login. */
    public AuthService() {
    }

    /**
     * Memeriksa username dan password.
     *
     * @param username username yang diketik pengguna
     * @param password password yang diketik pengguna
     * @return akun yang cocok (bisa berupa Admin atau Kasir)
     * @throws LoginGagalException jika username tidak ada atau password salah
     */
    public Akun login(String username, String password) throws LoginGagalException {
        // Username tidak membedakan huruf besar/kecil: "Kasir" sama dengan "kasir".
        Akun akun = DataStore.daftarAkun.get(username.trim());
        if (akun == null) {
            akun = DataStore.daftarAkun.get(username.trim().toLowerCase());
        }
        if (akun == null || !akun.getPassword().equals(password)) {
            throw new LoginGagalException("Username atau password salah.");
        }
        return akun;
    }
}
