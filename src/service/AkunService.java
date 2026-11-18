package service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import model.Akun;
import model.Kasir;
import model.Transaksi;
import util.ConsoleHelper;

/**
 * Layanan pengelolaan akun kasir. Hanya dipakai oleh Admin.
 * Semua data akun disimpan di DataStore.daftarAkun.
 */
public class AkunService {

    /** Format satu baris tabel kasir: nomor, username, nama lengkap. */
    private static final String FORMAT_BARIS = "%-3s %-15s %-24s";

    /** Membuat layanan akun. */
    public AkunService() {
    }

    /**
     * Menampilkan submenu kelola akun kasir: melihat daftar kasir,
     * menambah kasir, menghapus kasir, dan mengganti password kasir.
     */
    public void kelolaAkunKasir() {
        boolean berjalan = true;

        while (berjalan) {
            ConsoleHelper.cetakJudul("KELOLA AKUN KASIR");
            ConsoleHelper.cetak("1. Lihat Daftar Kasir");
            ConsoleHelper.cetak("2. Tambah Kasir");
            ConsoleHelper.cetak("3. Hapus Kasir");
            ConsoleHelper.cetak("4. Ganti Password Kasir");
            ConsoleHelper.cetak("0. Kembali");

            int pilihan = ConsoleHelper.bacaPilihan("Pilih: ", 0, 4);

            switch (pilihan) {
                case 1 -> tampilkanDaftarKasir();
                case 2 -> tambahKasir();
                case 3 -> hapusKasir();
                case 4 -> gantiPasswordKasir();
                case 0 -> berjalan = false;
                default -> ConsoleHelper.cetak("Pilihan tidak dikenal.");
            }
        }
    }

    /**
     * Mengambil semua akun Kasir, diurutkan berdasarkan username
     * (HashMap tidak menjamin urutan, jadi diurutkan dulu).
     *
     * @return daftar kasir yang sudah terurut
     */
    private List<Kasir> ambilDaftarKasir() {
        List<Kasir> daftar = new ArrayList<>();
        for (Akun akun : DataStore.daftarAkun.values()) {
            if (akun instanceof Kasir kasir) {
                daftar.add(kasir);
            }
        }
        daftar.sort(Comparator.comparing(Akun::getUsername));
        return daftar;
    }

    /** Menampilkan semua akun Kasir dalam bentuk tabel terurut. */
    private void tampilkanDaftarKasir() {
        ConsoleHelper.cetakJudul("DAFTAR AKUN KASIR");
        List<Kasir> daftar = ambilDaftarKasir();
        if (daftar.isEmpty()) {
            ConsoleHelper.cetak("Belum ada akun kasir.");
            return;
        }

        ConsoleHelper.cetak(String.format(FORMAT_BARIS, "No", "Username", "Nama Lengkap"));
        ConsoleHelper.cetak("-".repeat(ConsoleHelper.LEBAR));
        int nomor = 1;
        for (Kasir kasir : daftar) {
            ConsoleHelper.cetak(String.format(FORMAT_BARIS,
                    nomor++,
                    ConsoleHelper.potong(kasir.getUsername(), 15),
                    ConsoleHelper.potong(kasir.getNamaLengkap(), 24)));
        }
        ConsoleHelper.cetak("-".repeat(ConsoleHelper.LEBAR));
        ConsoleHelper.cetak("Jumlah kasir: " + daftar.size());
    }

    /**
     * Menambahkan akun Kasir baru. Username disimpan dengan huruf kecil
     * dan harus unik; nama lengkap juga harus unik karena dipakai di struk
     * dan riwayat. Isian yang salah ditanyakan ulang.
     */
    private void tambahKasir() {
        ConsoleHelper.cetakJudul("TAMBAH AKUN KASIR");

        String username;
        while (true) {
            username = ConsoleHelper.bacaTeks("Username (3-15 huruf/angka, Enter = batal): ").toLowerCase();
            if (username.isEmpty()) {
                ConsoleHelper.cetak("Tambah kasir dibatalkan.");
                return;
            }
            String masalah = cekUsername(username);
            if (masalah == null) {
                break;
            }
            ConsoleHelper.cetak(masalah);
        }

        String password = bacaPasswordBaru("Password (minimal 4 karakter): ");

        String namaLengkap;
        while (true) {
            namaLengkap = ConsoleHelper.bacaTeksWajib("Nama lengkap: ");
            if (!namaSudahDipakai(namaLengkap)) {
                break;
            }
            ConsoleHelper.cetak("Nama itu sudah dipakai akun lain. Tambahkan nama belakang atau inisial.");
        }

        DataStore.daftarAkun.put(username, new Kasir(username, password, namaLengkap));
        ConsoleHelper.cetak("Akun kasir " + username + " (" + namaLengkap + ") berhasil ditambahkan.");
    }

    /**
     * Memeriksa apakah username boleh dipakai untuk akun baru.
     *
     * @param username username huruf kecil yang akan diperiksa
     * @return pesan kesalahan, atau null jika username valid
     */
    private String cekUsername(String username) {
        if (!username.matches("[a-z0-9_.]{3,15}")) {
            return "Username harus 3-15 karakter: huruf, angka, titik, atau garis bawah (tanpa spasi).";
        }
        if (DataStore.daftarAkun.containsKey(username)) {
            return "Username sudah digunakan. Gunakan username lain.";
        }
        // username kasir yang sudah dihapus tidak boleh dipakai lagi,
        // supaya kasir baru tidak mewarisi riwayat transaksi kasir lama
        for (Transaksi t : DataStore.riwayat) {
            if (username.equals(t.getUsernameKasir())) {
                return "Username ini pernah dipakai kasir lain dan masih ada di riwayat. Pilih username lain.";
            }
        }
        return null;
    }

    /**
     * Menanyakan password baru sampai minimal 4 karakter, tanpa spasi
     * dan tanpa tanda "|".
     *
     * @param prompt pertanyaan yang ditampilkan
     * @return password yang valid
     */
    private String bacaPasswordBaru(String prompt) {
        while (true) {
            String password = ConsoleHelper.bacaTeks(prompt);
            if (password.length() >= 4 && !password.contains(" ") && !password.contains("|")) {
                return password;
            }
            ConsoleHelper.cetak("Password minimal 4 karakter, tanpa spasi dan tanpa tanda |.");
        }
    }

    /**
     * Memeriksa apakah nama lengkap sudah dipakai akun lain
     * (huruf besar/kecil dianggap sama).
     *
     * @param namaLengkap nama yang akan diperiksa
     * @return true jika sudah ada akun dengan nama tersebut
     */
    private boolean namaSudahDipakai(String namaLengkap) {
        for (Akun akun : DataStore.daftarAkun.values()) {
            if (akun.getNamaLengkap().equalsIgnoreCase(namaLengkap)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Mencari akun Kasir berdasarkan username yang diketik Admin
     * (huruf besar/kecil dianggap sama).
     *
     * @param username username yang diketik
     * @return akun Kasir, atau null jika tidak ada / bukan Kasir
     *         (pesan penjelasan sudah ditampilkan)
     */
    private Kasir cariKasir(String username) {
        Akun akun = DataStore.daftarAkun.get(username.toLowerCase());
        if (akun == null) {
            akun = DataStore.daftarAkun.get(username);
        }
        if (akun == null) {
            ConsoleHelper.cetak("Akun dengan username tersebut tidak ditemukan.");
            return null;
        }
        if (!(akun instanceof Kasir kasir)) {
            ConsoleHelper.cetak("Akun Admin tidak bisa diubah atau dihapus dari menu ini.");
            return null;
        }
        return kasir;
    }

    /**
     * Mengganti password akun Kasir, misalnya saat kasir lupa password.
     * Password baru diketik dua kali supaya tidak salah ketik.
     */
    private void gantiPasswordKasir() {
        tampilkanDaftarKasir();
        String username = ConsoleHelper.bacaTeks("Username kasir (Enter = batal): ");
        if (username.isEmpty()) {
            return;
        }
        Kasir kasir = cariKasir(username);
        if (kasir == null) {
            return;
        }

        String passwordBaru = bacaPasswordBaru("Password baru (minimal 4 karakter): ");
        String ulangi = ConsoleHelper.bacaTeks("Ketik ulang password baru: ");
        if (!ulangi.equals(passwordBaru)) {
            ConsoleHelper.cetak("Password tidak sama. Password lama tetap dipakai.");
            return;
        }
        kasir.setPassword(passwordBaru);
        ConsoleHelper.cetak("Password " + kasir.getUsername() + " berhasil diganti.");
    }

    /**
     * Menghapus akun Kasir setelah Admin mengonfirmasi. Akun Admin
     * dilindungi. Riwayat transaksi kasir tersebut tetap tersimpan.
     */
    private void hapusKasir() {
        tampilkanDaftarKasir();
        String username = ConsoleHelper.bacaTeks("Username kasir yang akan dihapus (Enter = batal): ");
        if (username.isEmpty()) {
            return;
        }
        Kasir kasir = cariKasir(username);
        if (kasir == null) {
            return;
        }

        int yakin = ConsoleHelper.bacaPilihan("Hapus akun " + kasir.getUsername() + " ("
                + kasir.getNamaLengkap() + ")? (1 = ya, 0 = tidak): ", 0, 1);
        if (yakin == 0) {
            ConsoleHelper.cetak("Penghapusan dibatalkan.");
            return;
        }
        DataStore.daftarAkun.remove(kasir.getUsername());
        ConsoleHelper.cetak("Akun kasir berhasil dihapus. Riwayat transaksinya tetap tersimpan.");
    }
}
