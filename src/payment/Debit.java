package payment;

import util.ConsoleHelper;

/**
 * Pembayaran memakai kartu debit. Pada simulasi ini sistem meminta
 * kasir mengonfirmasi bahwa transaksi di mesin EDC berhasil.
 */
public class Debit implements Pembayaran {

    /** Membuat metode pembayaran debit. */
    public Debit() {
    }

    /**
     * Mengambil nama metode pembayaran.
     *
     * @return teks "Debit"
     */
    @Override
    public String getNamaMetode() {
        return "Debit";
    }

    /**
     * Memproses pembayaran debit.
     *
     * @param total jumlah yang harus dibayar pelanggan
     * @return true jika kasir mengonfirmasi transaksi berhasil
     */
    @Override
    public boolean bayar(double total) {
        ConsoleHelper.cetakJudul("PEMBAYARAN DEBIT");
        ConsoleHelper.cetak("Total yang harus dibayar: " + ConsoleHelper.formatRupiah(total));
        ConsoleHelper.cetak("Silakan gesek/tap kartu debit pelanggan di mesin EDC.");

        int jawaban = ConsoleHelper.bacaPilihan(
                "Konfirmasi transaksi di mesin EDC (1 = berhasil, 0 = gagal): ", 0, 1);

        if (jawaban == 1) {
            ConsoleHelper.cetak("Pembayaran debit berhasil.");
            return true;
        }
        ConsoleHelper.cetak("Pembayaran debit gagal atau dibatalkan.");
        return false;
    }
}
