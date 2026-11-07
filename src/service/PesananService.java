package service;

import model.Kasir;
import model.Pesanan;

/**
 * Layanan pembuatan pesanan oleh Kasir.
 */
public class PesananService {

    /**
     * Membuat pesanan baru lewat tanya-jawab dengan kasir:
     * nomor meja, lalu menambahkan item satu per satu sampai selesai.
     *
     * @param kasir kasir yang sedang membuat pesanan
     * @return pesanan yang sudah terisi, atau null jika dibatalkan
     *         atau tidak ada item yang dipesan
     */
    public Pesanan buatPesananBaru(Kasir kasir) {
        // TODO:
        //   1. Tanya nomor meja.
        //   2. Buat objek Pesanan (id otomatis, contoh "P001").
        //   3. Ulangi: tampilkan menu, tanya id menu dan jumlah,
        //      cek stok (kalau kurang, tangkap StokHabisException),
        //      kurangi stok menu, lalu pesanan.tambahItem(new ItemPesanan(...)).
        //   4. Kalau selesai dan ada item, return pesanan. Kalau batal, return null.
        return null;
    }
}
