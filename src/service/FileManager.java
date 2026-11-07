package service;

/**
 * Menyimpan dan membaca data ke file teks (fitur tambahan).
 * Dipanggil dari Main: muat data saat program mulai,
 * simpan data saat program selesai.
 */
public class FileManager {

    /**
     * Menyimpan menu, akun, dan riwayat transaksi dari DataStore ke file.
     */
    public void simpanSemuaData() {
        // TODO (opsional): tulis isi DataStore ke folder data/
        //   memakai FileWriter, tangkap IOException dengan try-catch.
    }

    /**
     * Membaca data dari file ke DataStore. Jika file belum ada,
     * biarkan data awal dari DataStore.isiDataAwal() tetap dipakai.
     */
    public void muatSemuaData() {
        // TODO (opsional): baca file di folder data/ memakai
        //   FileReader atau Scanner, isi DataStore, tangkap IOException.
    }
}
