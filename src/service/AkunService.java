package service;

/**
 * Layanan pengelolaan akun kasir. Hanya dipakai oleh Admin.
 * Semua data akun ada di DataStore.daftarAkun.
 */
public class AkunService {

    /**
     * Menampilkan submenu kelola akun kasir: lihat daftar kasir,
     * tambah kasir baru, dan hapus kasir.
     */
    public void kelolaAkunKasir() {
        // TODO: buat submenu dengan ConsoleHelper.bacaPilihan.
        //   Lihat daftar: tampilkan akun di DataStore.daftarAkun yang bertipe Kasir.
        //   Tambah: tanya username, password, nama; username tidak boleh kembar;
        //   buat objek Kasir dan put ke DataStore.daftarAkun.
        //   Hapus: minta username, remove dari DataStore.daftarAkun.
        //   Akun admin tidak boleh dihapus.
    }
}
