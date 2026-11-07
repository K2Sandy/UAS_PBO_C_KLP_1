package model;

/**
 * Class abstrak untuk semua akun pengguna (abstraction).
 * Admin dan Kasir adalah turunannya (inheritance), dan masing-masing
 * punya menu sendiri lewat method jalankanMenu (polymorphism).
 */
public abstract class Akun {

    private String username;
    private String password;
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

    /** @return username untuk login */
    public String getUsername() {
        return username;
    }

    /** @param username username yang baru */
    public void setUsername(String username) {
        this.username = username;
    }

    /** @return password akun */
    public String getPassword() {
        return password;
    }

    /** @param password password yang baru */
    public void setPassword(String password) {
        this.password = password;
    }

    /** @return nama lengkap pemilik akun */
    public String getNamaLengkap() {
        return namaLengkap;
    }

    /** @param namaLengkap nama lengkap yang baru */
    public void setNamaLengkap(String namaLengkap) {
        this.namaLengkap = namaLengkap;
    }
}
