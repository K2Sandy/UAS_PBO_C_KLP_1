package service;

import model.Akun;
import util.LoginGagalException;

/**
 * Layanan login. Mencocokkan username dan password dengan data akun.
 */
public class AuthService {

    /**
     * Memeriksa username dan password.
     *
     * @param username username yang diketik pengguna
     * @param password password yang diketik pengguna
     * @return akun yang cocok (bisa berupa Admin atau Kasir)
     * @throws LoginGagalException jika username tidak ada atau password salah
     */
    public Akun login(String username, String password) throws LoginGagalException {
        Akun akun = DataStore.daftarAkun.get(username);
        if (akun == null || !akun.getPassword().equals(password)) {
            throw new LoginGagalException("Username atau password salah.");
        }
        return akun;
    }
}
