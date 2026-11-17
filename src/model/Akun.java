package model;

/**
 * Class abstrak untuk semua akun pengguna (abstraction).
 * Admin dan Kasir adalah turunannya (inheritance), dan masing-masing
 * punya menu sendiri lewat method jalankanMenu (polymorphism).
 */
public abstract class Akun {

    /** Nama pengguna untuk login (unik). */
    private String username;
    /** Kata sandi untuk login. */
    private String password;
    /** Nama lengkap pemilik akun. */
    private String namaLengkap;

    /**
     * Membuat akun.
     *
     * @param username    nama pengguna untuk login
     * @param password    kata sandi untuk login
     * @param namaLengkap nama lengkap pemilik akun
     */
    public Akun(String username, String password, String namaLengkap) {
        this.username = username;
        this.password = password;
        this.namaLengkap = namaLengkap;
    }

    /**
     * Menampilkan menu utama sesuai jenis akun dan menjalankannya
     * sampai pengguna memilih logout.
     */
    public abstract void jalankanMenu();

    /**
     * Mengambil peran akun.
     *
     * @return teks peran, contoh: "Admin"
     */
    public abstract String getPeran();

    /**
     * Mengambil username untuk login.
     *
     * @return username untuk login
     */
    public String getUsername() {
        return username;
    }

    /**
     * Mengubah username.
     *
     * @param username username yang baru
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Mengambil password akun.
     *
     * @return password akun
     */
    public String getPassword() {
        return password;
    }

    /**
     * Mengubah password.
     *
     * @param password password yang baru
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Mengambil nama lengkap pemilik akun.
     *
     * @return nama lengkap pemilik akun
     */
    public String getNamaLengkap() {
        return namaLengkap;
    }

    /**
     * Mengubah nama lengkap.
     *
     * @param namaLengkap nama lengkap yang baru
     */
    public void setNamaLengkap(String namaLengkap) {
        this.namaLengkap = namaLengkap;
    }
}
