package service;

import java.util.Map;
import model.Akun;
import model.Kasir;
import util.ConsoleHelper;

/**
 * Layanan pengelolaan akun kasir. Hanya dipakai oleh Admin.
 * Semua data akun disimpan di DataStore.daftarAkun.
 */
public class AkunService {

    /**
     * Menampilkan submenu kelola akun kasir:
     * melihat daftar kasir, menambah kasir, dan menghapus kasir.
     */
    public void kelolaAkunKasir() {
        boolean berjalan = true;

while (berjalan) {
            ConsoleHelper.cetakJudul("KELOLA AKUN KASIR");
            ConsoleHelper.cetak("1. Lihat Daftar Kasir");
            ConsoleHelper.cetak("2. Tambah Kasir");
            ConsoleHelper.cetak("3. Hapus Kasir");
            ConsoleHelper.cetak("0. Kembali");

            int pilihan = ConsoleHelper.bacaPilihan("Pilih: ", 0, 3);

            switch (pilihan) {
                case 1 -> tampilkanDaftarKasir();
                case 2 -> tambahKasir();
                case 3 -> hapusKasir();
                case 0 -> berjalan = false;
                default -> ConsoleHelper.cetak("Pilihan tidak dikenal.");
            }
        }
    }
    
    /** Menampilkan semua akun yang bertipe Kasir. */
    private void tampilkanDaftarKasir() {
        ConsoleHelper.cetakJudul("DAFTAR AKUN KASIR");
        boolean ditemukan = false;

        for (Map.Entry<String, Akun> data : DataStore.daftarAkun.entrySet()) {
            Akun akun = data.getValue();
            if (akun instanceof Kasir) {
                ConsoleHelper.cetak(
                        "Username: " + akun.getUsername()
                        + " | Nama: " + akun.getNamaLengkap()
                        + " | Peran: " + akun.getPeran()
                );
                ditemukan = true;
            }
        }

        if (!ditemukan) {
            ConsoleHelper.cetak("Belum ada akun kasir.");
        }
    }

    /** Menambahkan akun Kasir baru dengan username yang unik. */
    private void tambahKasir() {
        ConsoleHelper.cetakJudul("TAMBAH AKUN KASIR");

        String username = ConsoleHelper.bacaTeks("Masukkan username: ");
        if (username.isBlank()) {
            ConsoleHelper.cetak("Username tidak boleh kosong.");
            return;
        }
        if (DataStore.daftarAkun.containsKey(username)) {
            ConsoleHelper.cetak("Username sudah digunakan. Gunakan username lain.");
            return;
        }

        String password = ConsoleHelper.bacaTeks("Masukkan password: ");
        if (password.isBlank()) {
            ConsoleHelper.cetak("Password tidak boleh kosong.");
            return;
        }

        String namaLengkap = ConsoleHelper.bacaTeks("Masukkan nama lengkap: ");
        if (namaLengkap.isBlank()) {
            ConsoleHelper.cetak("Nama lengkap tidak boleh kosong.");
            return;
        }

        Kasir kasirBaru = new Kasir(username, password, namaLengkap);
        DataStore.daftarAkun.put(username, kasirBaru);
        ConsoleHelper.cetak("Akun kasir berhasil ditambahkan.");
    }
    
    /** Menghapus akun Kasir berdasarkan username; akun Admin dilindungi. */
    private void hapusKasir() {
        ConsoleHelper.cetakJudul("HAPUS AKUN KASIR");

        String username = ConsoleHelper.bacaTeks(
                "Masukkan username kasir yang akan dihapus: "
        );
        if (username.isBlank()) {
            ConsoleHelper.cetak("Username tidak boleh kosong.");
            return;
        }

        Akun akun = DataStore.daftarAkun.get(username);
        if (akun == null) {
            ConsoleHelper.cetak("Akun dengan username tersebut tidak ditemukan.");
            return;
        }
        if (!(akun instanceof Kasir)) {
            ConsoleHelper.cetak("Akun Admin tidak boleh dihapus.");
            return;
        }

        DataStore.daftarAkun.remove(username);
        ConsoleHelper.cetak("Akun kasir berhasil dihapus.");
    }
}
