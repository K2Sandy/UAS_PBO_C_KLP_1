package model;

import service.MenuService;
import service.PesananService;
import service.TransaksiService;
import util.ConsoleHelper;

/**
 * Akun Kasir (User). Kasir melihat daftar menu, membuat pesanan,
 * memproses pembayaran, dan melihat riwayat transaksinya sendiri.
 */
public class Kasir extends Akun {

    private final MenuService menuService = new MenuService();
    private final PesananService pesananService = new PesananService();
    private final TransaksiService transaksiService = new TransaksiService();

    /**
     * Membuat akun Kasir.
     *
     * @param username    nama pengguna untuk login
     * @param password    kata sandi untuk login
     * @param namaLengkap nama lengkap kasir
     */
    public Kasir(String username, String password, String namaLengkap) {
        super(username, password, namaLengkap);
    }

    /**
     * Mengambil peran akun.
     *
     * @return teks "Kasir"
     */
    @Override
    public String getPeran() {
        return "Kasir";
    }

    /**
     * Menampilkan menu Kasir dan menjalankan pilihan yang dipilih
     * sampai Kasir memilih logout.
     */
    @Override
    public void jalankanMenu() {
        boolean berjalan = true;
        while (berjalan) {
            ConsoleHelper.cetakJudul("MENU KASIR - " + getNamaLengkap());
            ConsoleHelper.cetak("1. Lihat Daftar Menu");
            ConsoleHelper.cetak("2. Buat Pesanan Baru");
            ConsoleHelper.cetak("3. Riwayat Transaksi Saya");
            ConsoleHelper.cetak("0. Logout");
            int pilihan = ConsoleHelper.bacaPilihan("Pilih: ", 0, 3);

            switch (pilihan) {
                case 1 -> menuService.tampilkanSemuaMenu();
                case 2 -> buatPesananDanBayar();
                case 3 -> transaksiService.tampilkanRiwayatKasir(this);
                case 0 -> berjalan = false;
                default -> ConsoleHelper.cetak("Pilihan tidak dikenal.");
            }
        }
    }

    /**
     * Alur pesanan: buat pesanan dulu, kalau pesanan jadi (tidak dibatalkan)
     * lanjut ke pembayaran.
     */
    private void buatPesananDanBayar() {
        Pesanan pesanan = pesananService.buatPesananBaru(this);
        if (pesanan != null) {
            transaksiService.prosesPembayaran(pesanan, this);
        }
    }
}
