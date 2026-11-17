package service;

import model.Makanan;
import model.MenuItem;
import model.Minuman;
import util.ConsoleHelper;
import util.InputTidakValidException;

/**
 * Layanan pengelolaan menu restoran. Dipakai oleh Admin (tambah, edit, hapus)
 * dan oleh Kasir (hanya melihat daftar menu).
 * Semua data menu ada di DataStore.daftarMenu, dan semua input dibaca
 * lewat ConsoleHelper.
 */
public class MenuService {

    /** Membuat layanan menu. */
    public MenuService() {
    }

    /**
     * Menampilkan semua menu restoran.
     */
    public void tampilkanSemuaMenu() {
        if (DataStore.daftarMenu.isEmpty()) {
            ConsoleHelper.cetak("Belum ada menu.");
            return;
        }
        System.out.println("\n========== DAFTAR MENU ==========");
        System.out.printf("%-5s %-20s %-12s %-15s %-8s%n",
                "ID", "Nama", "Kategori", "Harga", "Stok");
        for (MenuItem menu : DataStore.daftarMenu) {
            System.out.printf("%-5s %-20s %-12s %-15s %-8d%n",
                    menu.getId(),
                    menu.getNama(),
                    menu.getKategori(),
                    ConsoleHelper.formatRupiah(menu.getHarga()),
                    menu.getStok());
        }
    }

    /**
     * Menambahkan menu makanan atau minuman.
     */
    public void tambahMenu() {
        try {
            ConsoleHelper.cetakJudul("TAMBAH MENU");
            ConsoleHelper.cetak("1. Makanan");
            ConsoleHelper.cetak("2. Minuman");
            int jenis = ConsoleHelper.bacaAngka("Pilih jenis menu: ");

            String id = ConsoleHelper.bacaTeks("Masukkan ID: ");
            if (DataStore.cariMenu(id) != null) {
                throw new InputTidakValidException("ID menu sudah digunakan!");
            }

            String nama = ConsoleHelper.bacaTeks("Masukkan nama: ");
            double harga = ConsoleHelper.bacaDesimal("Masukkan harga: ");
            int stok = ConsoleHelper.bacaAngka("Masukkan stok: ");

            if (harga <= 0 || stok < 0 || nama.isEmpty()) {
                throw new InputTidakValidException("Data menu tidak valid!");
            }

            MenuItem menu;
            if (jenis == 1) {
                String jawaban = ConsoleHelper.bacaTeks("pedas atau tidak (ya/tidak): ");
                boolean pedas = jawaban.equalsIgnoreCase("ya");
                menu = new Makanan(id, nama, harga, stok, pedas);
            } else if (jenis == 2) {
                String ukuran = ConsoleHelper.bacaTeks("Masukkan ukuran (Regular/Large): ");
                menu = new Minuman(id, nama, harga, stok, ukuran);
            } else {
                throw new InputTidakValidException("Pilihan jenis tidak valid!");
            }

            DataStore.daftarMenu.add(menu);
            ConsoleHelper.cetak("Menu berhasil ditambahkan!");
        } catch (InputTidakValidException e) {
            ConsoleHelper.cetak("Gagal: " + e.getMessage());
        }
    }

    /**
     * Mengubah data menu.
     */
    public void editMenu() {
        tampilkanSemuaMenu();
        String id = ConsoleHelper.bacaTeks("\nMasukkan ID menu yang ingin diedit: ");
        MenuItem menu = DataStore.cariMenu(id);
        if (menu == null) {
            ConsoleHelper.cetak("Menu dengan ID tersebut tidak ditemukan.");
            return;
        }

        ConsoleHelper.cetak("1. Ubah nama");
        ConsoleHelper.cetak("2. Ubah harga");
        ConsoleHelper.cetak("3. Ubah stok");
        int pilihan = ConsoleHelper.bacaAngka("Pilih data yang ingin diubah: ");
        switch (pilihan) {
            case 1:
                menu.setNama(ConsoleHelper.bacaTeks("Nama baru: "));
                break;
            case 2:
                double harga = ConsoleHelper.bacaDesimal("Harga baru: ");
                if (harga <= 0) {
                    ConsoleHelper.cetak("Harga harus lebih dari 0.");
                    return;
                }
                menu.setHarga(harga);
                break;
            case 3:
                int stok = ConsoleHelper.bacaAngka("Stok baru: ");
                if (stok < 0) {
                    ConsoleHelper.cetak("Stok tidak boleh negatif.");
                    return;
                }
                menu.setStok(stok);
                break;
            default:
                ConsoleHelper.cetak("Pilihan tidak valid.");
                return;
        }
        ConsoleHelper.cetak("Menu berhasil diperbarui!");
    }

    /**
     * Menghapus menu berdasarkan ID.
     */
    public void hapusMenu() {
        tampilkanSemuaMenu();
        String id = ConsoleHelper.bacaTeks("\nMasukkan ID menu yang ingin dihapus: ");
        MenuItem menu = DataStore.cariMenu(id);
        if (menu == null) {
            ConsoleHelper.cetak("Menu dengan ID tersebut tidak ditemukan.");
            return;
        }

        String konfirmasi = ConsoleHelper.bacaTeks("Yakin ingin menghapus menu ini? (y/n): ");
        if (konfirmasi.equalsIgnoreCase("y")) {
            DataStore.daftarMenu.remove(menu);
            ConsoleHelper.cetak("Menu berhasil dihapus!");
        } else {
            ConsoleHelper.cetak("Penghapusan dibatalkan.");
        }
    }
}
