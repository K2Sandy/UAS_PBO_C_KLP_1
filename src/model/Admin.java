package model;

import service.AkunService;
import service.LaporanService;
import service.MenuService;
import util.ConsoleHelper;

/**
 * Akun Admin (pemilik atau manajer restoran). Admin mengelola menu dan
 * akun kasir, serta melihat riwayat transaksi dan laporan penjualan.
 */
public class Admin extends Akun {

    private final MenuService menuService = new MenuService();
    private final AkunService akunService = new AkunService();
    private final LaporanService laporanService = new LaporanService();

    /**
     * Membuat akun Admin.
     *
     * @param username    nama pengguna untuk login
     * @param password    kata sandi untuk login
     * @param namaLengkap nama lengkap admin
     */
    public Admin(String username, String password, String namaLengkap) {
        super(username, password, namaLengkap);
    }

    /**
     * Mengambil peran akun.
     *
     * @return teks "Admin"
     */
    @Override
    public String getPeran() {
        return "Admin";
    }

    /**
     * Menampilkan menu Admin dan menjalankan pilihan yang dipilih
     * sampai Admin memilih logout.
     */
    @Override
    public void jalankanMenu() {
        boolean berjalan = true;
        while (berjalan) {
            ConsoleHelper.cetakJudul("MENU ADMIN - " + getNamaLengkap());
            ConsoleHelper.cetak("1. Lihat Daftar Menu");
            ConsoleHelper.cetak("2. Tambah Menu");
            ConsoleHelper.cetak("3. Edit Menu");
            ConsoleHelper.cetak("4. Hapus Menu");
            ConsoleHelper.cetak("5. Kelola Akun Kasir");
            ConsoleHelper.cetak("6. Lihat Riwayat Transaksi");
            ConsoleHelper.cetak("7. Laporan Penjualan Harian");
            ConsoleHelper.cetak("0. Logout");
            int pilihan = ConsoleHelper.bacaPilihan("Pilih: ", 0, 7);

            switch (pilihan) {
                case 1 -> menuService.tampilkanSemuaMenu();
                case 2 -> menuService.tambahMenu();
                case 3 -> menuService.editMenu();
                case 4 -> menuService.hapusMenu();
                case 5 -> akunService.kelolaAkunKasir();
                case 6 -> laporanService.tampilkanSemuaRiwayat();
                case 7 -> laporanService.laporanHarian();
                case 0 -> berjalan = false;
                default -> ConsoleHelper.cetak("Pilihan tidak dikenal.");
            }
        }
    }
}
