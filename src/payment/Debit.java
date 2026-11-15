package payment;

import java.util.Scanner;

/**
 * Pembayaran memakai kartu debit. Pada simulasi ini sistem meminta
 * kasir mengonfirmasi bahwa transaksi di mesin EDC berhasil.
 */
public class Debit implements Pembayaran {

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
        System.out.println("\n=== PEMBAYARAN DEBIT ===");
        System.out.printf("Total yang harus dibayar: Rp%,.0f%n", total);
        System.out.println("Silakan gesek/tap kartu debit pelanggan di mesin EDC.");
        System.out.print("Konfirmasi transaksi di mesin EDC (1 = berhasil, 0 = gagal): ");

        Scanner scanner = new Scanner(System.in);
        String jawaban = scanner.nextLine().trim();

        if (jawaban.equals("1")) {
            System.out.println("Pembayaran debit berhasil.");
            return true;
        }
        System.out.println("Pembayaran debit gagal atau dibatalkan.");
        return false;
    }
}
