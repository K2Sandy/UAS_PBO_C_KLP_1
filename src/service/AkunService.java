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
