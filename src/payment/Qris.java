package payment;

import util.ConsoleHelper;

/**
 * Pembayaran lewat QRIS. Pada simulasi ini sistem hanya menampilkan
 * instruksi scan lalu menunggu konfirmasi kasir.
 */
public class Qris implements Pembayaran {

    /** Membuat metode pembayaran QRIS. */
    public Qris() {
    }

    /**
     * Mengambil nama metode pembayaran.
     *
     * @return teks "QRIS"
     */
    @Override
    public String getNamaMetode() {
        return "QRIS";
    }

    /**
     * Memproses pembayaran QRIS.
     *
     * @param total jumlah yang harus dibayar pelanggan
     * @return true jika kasir mengonfirmasi pembayaran berhasil
     */
    @Override
    public boolean bayar(double total) {
        ConsoleHelper.cetakJudul("PEMBAYARAN QRIS");
        ConsoleHelper.cetak("Total yang harus dibayar: " + ConsoleHelper.formatRupiah(total));
        ConsoleHelper.cetak("Instruksi:");
        ConsoleHelper.cetak("1. Tunjukkan kode QRIS restoran kepada pelanggan.");
        ConsoleHelper.cetak("2. Pelanggan scan dengan e-wallet atau mobile banking.");
        ConsoleHelper.cetak("3. Pastikan nominal sesuai total di atas.");

        int jawaban = ConsoleHelper.bacaPilihan("Pembayaran berhasil? (1 = ya, 0 = batal): ", 0, 1);

        if (jawaban == 1) {
            ConsoleHelper.cetak("Pembayaran QRIS berhasil.");
            return true;
        }
        ConsoleHelper.cetak("Pembayaran QRIS dibatalkan.");
        return false;
    }
}
