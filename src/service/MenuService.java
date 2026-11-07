package service;

/**
 * Layanan pengelolaan menu restoran. Dipakai oleh Admin (tambah, edit, hapus)
 * dan oleh Kasir (hanya melihat daftar menu).
 * Semua data menu ada di DataStore.daftarMenu.
 */
public class MenuService {

    /**
     * Menampilkan semua menu dalam bentuk tabel rapi:
     * id, nama, kategori, harga, dan stok.
     */
    public void tampilkanSemuaMenu() {
        // TODO: cetak isi DataStore.daftarMenu sebagai tabel.
        //   Pakai ConsoleHelper.formatRupiah untuk harga.
        //   Kalau daftar kosong, tampilkan "Belum ada menu."
    }

    /**
     * Menambah menu baru. Method ini sendiri yang bertanya ke pengguna:
     * jenis (makanan atau minuman), id, nama, harga, stok.
     */
    public void tambahMenu() {
        // TODO: tanya data lewat ConsoleHelper, buat objek
        //   Makanan atau Minuman, lalu add ke DataStore.daftarMenu.
        //   Id tidak boleh sama dengan menu lain
        //   (lempar atau tangkap InputTidakValidException).
    }

    /**
     * Mengubah data menu yang sudah ada (nama, harga, atau stok).
     */
    public void editMenu() {
        // TODO: tampilkan menu, minta id yang mau diedit,
        //   cari di DataStore.daftarMenu, lalu ubah lewat setter.
        //   Kalau id tidak ditemukan, tampilkan pesan.
    }

    /**
     * Menghapus menu berdasarkan id.
     */
    public void hapusMenu() {
        // TODO: minta id menu, cari di DataStore.daftarMenu,
        //   minta konfirmasi, lalu remove. Kalau tidak ketemu, tampilkan pesan.
    }
}
