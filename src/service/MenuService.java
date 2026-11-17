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

    /** Harga tertinggi yang boleh diisi Admin (muat di kolom tabel). */
    private static final long HARGA_MAKSIMAL = 9_999_999;

    /** Membuat layanan menu. */
    public MenuService() {
    }

    /** Format satu baris tabel menu: id, nama, kategori, keterangan, harga, stok. */
    private static final String FORMAT_BARIS = "%-4s %-17s %-8s %-11s %11s %5s";

    /** Panjang satu baris tabel menu. */
    private static final int LEBAR_TABEL = 61;

    /**
     * Menampilkan semua menu dalam bentuk tabel: id, nama, kategori,
     * keterangan (pedas / ukuran), harga per porsi, dan stok.
     * Harga diambil dari hitungHarga(1), jadi minuman Large langsung
     * tampil dengan harga yang benar-benar dibayar pelanggan.
     */
    public void tampilkanSemuaMenu() {
        ConsoleHelper.cetakJudul("DAFTAR MENU");
        if (DataStore.daftarMenu.isEmpty()) {
            ConsoleHelper.cetak("Belum ada menu.");
            return;
        }
        ConsoleHelper.cetak(String.format(FORMAT_BARIS,
                "ID", "Nama", "Kategori", "Keterangan", "Harga", "Stok"));
        ConsoleHelper.cetak("-".repeat(LEBAR_TABEL));
        for (MenuItem menu : DataStore.daftarMenu) {
            String stok = menu.getStok() > 0 ? String.valueOf(menu.getStok()) : "HABIS";
            ConsoleHelper.cetak(String.format(FORMAT_BARIS,
                    menu.getId(),
                    ConsoleHelper.potong(menu.getNama(), 17),
                    menu.getKategori(),
                    ConsoleHelper.potong(menu.getKeterangan(), 11),
                    ConsoleHelper.formatRupiah(menu.hitungHarga(1)),
                    stok));
        }
        ConsoleHelper.cetak("-".repeat(LEBAR_TABEL));
        ConsoleHelper.cetak("Harga minuman Large sudah termasuk tambahan "
                + ConsoleHelper.formatRupiah(Minuman.TAMBAHAN_LARGE) + ".");
    }

    /**
     * Menambahkan menu makanan atau minuman. Setiap data ditanyakan ulang
     * sampai valid, jadi Admin tidak perlu mengulang dari awal.
     */
    public void tambahMenu() {
        ConsoleHelper.cetakJudul("TAMBAH MENU");
        ConsoleHelper.cetak("1. Makanan");
        ConsoleHelper.cetak("2. Minuman");
        ConsoleHelper.cetak("0. Batal");
        int jenis = ConsoleHelper.bacaPilihan("Pilih jenis menu: ", 0, 2);
        if (jenis == 0) {
            ConsoleHelper.cetak("Tambah menu dibatalkan.");
            return;
        }

        String id = bacaIdBaru();
        String nama = ConsoleHelper.bacaTeksWajib("Nama menu: ");
        double harga = bacaHarga("Harga (Rp): ");
        int stok = bacaStok("Stok awal: ");

        MenuItem menu;
        if (jenis == 1) {
            boolean pedas = ConsoleHelper.bacaPilihan("Pedas? (1 = ya, 0 = tidak): ", 0, 1) == 1;
            menu = new Makanan(id, nama, harga, stok, pedas);
        } else {
            menu = new Minuman(id, nama, harga, stok, bacaUkuran());
        }

        DataStore.daftarMenu.add(menu);
        ConsoleHelper.cetak("Menu " + menu.getId() + " - " + menu.getNama() + " berhasil ditambahkan.");
    }

    /**
     * Menanyakan id menu baru sampai didapat id yang valid dan belum dipakai.
     * Id disimpan dengan huruf besar, contoh: "m10" menjadi "M10".
     *
     * @return id menu baru yang valid
     */
    private String bacaIdBaru() {
        while (true) {
            String id = ConsoleHelper.bacaTeksWajib("ID menu, 2-4 huruf/angka (contoh M04 / N03): ").toUpperCase();
            try {
                validasiIdBaru(id);
                return id;
            } catch (InputTidakValidException e) {
                ConsoleHelper.cetak(e.getMessage());
            }
        }
    }

    /**
     * Memeriksa apakah id boleh dipakai untuk menu baru.
     *
     * @param id id yang akan diperiksa (sudah huruf besar)
     * @throws InputTidakValidException jika id bukan 2-4 huruf/angka
     *                                  atau sudah dipakai menu lain
     */
    private void validasiIdBaru(String id) throws InputTidakValidException {
        // 2-4 karakter supaya muat di kolom tabel, dan tidak bisa bentrok
        // dengan perintah satu karakter di layar pesanan kasir (misalnya "0")
        if (!id.matches("[A-Z0-9]{2,4}")) {
            throw new InputTidakValidException("ID harus 2-4 karakter huruf atau angka, tanpa spasi.");
        }
        if (DataStore.cariMenu(id) != null) {
            throw new InputTidakValidException("ID " + id + " sudah dipakai menu lain.");
        }
    }

    /**
     * Menanyakan harga sampai lebih dari 0 dan paling banyak Rp9.999.999.
     * Titik ribuan boleh ditulis.
     *
     * @param prompt pertanyaan yang ditampilkan
     * @return harga dalam rupiah
     */
    private double bacaHarga(String prompt) {
        while (true) {
            long harga = ConsoleHelper.bacaRupiah(prompt);
            if (harga > 0 && harga <= HARGA_MAKSIMAL) {
                return harga;
            }
            ConsoleHelper.cetak("Harga harus antara Rp1 dan "
                    + ConsoleHelper.formatRupiah(HARGA_MAKSIMAL) + ".");
        }
    }

    /**
     * Menanyakan jumlah stok sampai tidak negatif.
     *
     * @param prompt pertanyaan yang ditampilkan
     * @return jumlah stok
     */
    private int bacaStok(String prompt) {
        while (true) {
            int stok = ConsoleHelper.bacaAngka(prompt);
            if (stok >= 0) {
                return stok;
            }
            ConsoleHelper.cetak("Stok tidak boleh negatif.");
        }
    }

    /**
     * Menanyakan ukuran minuman lewat pilihan angka.
     *
     * @return "Regular" atau "Large"
     */
    private String bacaUkuran() {
        ConsoleHelper.cetak("Ukuran: 1. Regular   2. Large (+"
                + ConsoleHelper.formatRupiah(Minuman.TAMBAHAN_LARGE) + ")");
        return ConsoleHelper.bacaPilihan("Pilih ukuran: ", 1, 2) == 1 ? "Regular" : "Large";
    }

    /**
     * Mengubah data menu yang sudah ada: nama, harga, stok, atau
     * keterangan khasnya (pedas untuk makanan, ukuran untuk minuman).
     */
    public void editMenu() {
        tampilkanSemuaMenu();
        String id = ConsoleHelper.bacaTeks("\nMasukkan ID menu yang ingin diedit (Enter = batal): ");
        if (id.isEmpty()) {
            return;
        }
        MenuItem menu = DataStore.cariMenu(id);
        if (menu == null) {
            ConsoleHelper.cetak("Menu dengan ID tersebut tidak ditemukan.");
            return;
        }

        ConsoleHelper.cetak("Mengedit " + menu.getId() + " - " + menu.getNama()
                + " (" + ConsoleHelper.formatRupiah(menu.getHarga()) + ", stok " + menu.getStok()
                + ", " + menu.getKeterangan() + ")");
        ConsoleHelper.cetak("1. Ubah nama");
        ConsoleHelper.cetak("2. Ubah harga");
        ConsoleHelper.cetak("3. Ubah stok");
        ConsoleHelper.cetak(menu instanceof Minuman ? "4. Ubah ukuran" : "4. Ubah status pedas");
        ConsoleHelper.cetak("0. Batal");
        int pilihan = ConsoleHelper.bacaPilihan("Pilih data yang ingin diubah: ", 0, 4);

        switch (pilihan) {
            case 1 -> menu.setNama(ConsoleHelper.bacaTeksWajib("Nama baru: "));
            case 2 -> menu.setHarga(bacaHarga("Harga baru (Rp): "));
            case 3 -> menu.setStok(bacaStok("Stok baru: "));
            case 4 -> {
                if (menu instanceof Minuman minuman) {
                    minuman.setUkuran(bacaUkuran());
                } else if (menu instanceof Makanan makanan) {
                    makanan.setPedas(ConsoleHelper.bacaPilihan(
                            "Pedas? (1 = ya, 0 = tidak): ", 0, 1) == 1);
                }
            }
            default -> {
                ConsoleHelper.cetak("Edit dibatalkan.");
                return;
            }
        }
        ConsoleHelper.cetak("Menu berhasil diperbarui.");
    }

    /**
     * Menghapus menu berdasarkan ID setelah Admin mengonfirmasi.
     * Riwayat transaksi lama tidak ikut terhapus karena item transaksi
     * disimpan sebagai salinan.
     */
    public void hapusMenu() {
        tampilkanSemuaMenu();
        String id = ConsoleHelper.bacaTeks("\nMasukkan ID menu yang ingin dihapus (Enter = batal): ");
        if (id.isEmpty()) {
            return;
        }
        MenuItem menu = DataStore.cariMenu(id);
        if (menu == null) {
            ConsoleHelper.cetak("Menu dengan ID tersebut tidak ditemukan.");
            return;
        }

        int yakin = ConsoleHelper.bacaPilihan("Hapus " + menu.getId() + " - " + menu.getNama()
                + "? (1 = ya, 0 = tidak): ", 0, 1);
        if (yakin == 1) {
            DataStore.daftarMenu.remove(menu);
            ConsoleHelper.cetak("Menu " + menu.getNama() + " berhasil dihapus.");
        } else {
            ConsoleHelper.cetak("Penghapusan dibatalkan.");
        }
    }
}
