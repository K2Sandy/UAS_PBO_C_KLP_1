package payment;

import java.util.Scanner;

/**
 * Pembayaran lewat QRIS. Pada simulasi ini sistem hanya menampilkan
 * instruksi scan lalu menunggu konfirmasi kasir.
 */
public class Qris implements Pembayaran {

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
        System.out.println("\n=== PEMBAYARAN QRIS ===");
        System.out.printf("Total yang harus dibayar: Rp%,.0f%n", total);
        System.out.println("Instruksi:");
        System.out.println("1. Tunjukkan kode QRIS restoran kepada pelanggan.");
        System.out.println("2. Pelanggan scan memakai aplikasi e-wallet atau mobile banking.");
        System.out.println("3. Pastikan nominal pembayaran sesuai total di atas.");
        System.out.print("Pembayaran berhasil? (1 = ya, 0 = batal): ");

        Scanner scanner = new Scanner(System.in);
        String jawaban = scanner.nextLine().trim();

        if (jawaban.equals("1")) {
            System.out.println("Pembayaran QRIS berhasil.");
            return true;
        }
        System.out.println("Pembayaran QRIS dibatalkan.");
        return false;
    }
}
