package util;

import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Kumpulan fungsi bantu untuk tampilan dan input di CLI.
 * Semua anggota tim memakai class ini supaya input selalu divalidasi
 * dan tampilan seragam. Semua method bersifat static.
 */
public class ConsoleHelper {

    private static final Scanner scanner = new Scanner(System.in);
    private static final int LEBAR = 44;

    /** Class bantu, tidak perlu dibuat objeknya. */
    private ConsoleHelper() {
    }

    /** Mencetak garis pemisah. */
    public static void cetakGaris() {
        System.out.println("=".repeat(LEBAR));
    }

    /**
     * Mencetak judul di dalam garis pemisah.
     *
     * @param judul teks judul yang ditampilkan
     */
    public static void cetakJudul(String judul) {
        cetakGaris();
        System.out.println("  " + judul);
        cetakGaris();
    }

    /**
     * Mencetak satu baris pesan biasa.
     *
     * @param pesan teks yang ditampilkan
     */
    public static void cetak(String pesan) {
        System.out.println(pesan);
    }

    /**
     * Membaca satu baris teks dari pengguna.
     * Jika input sudah habis (misalnya Ctrl+D), program berhenti dengan rapi.
     *
     * @param prompt pertanyaan yang ditampilkan
     * @return teks yang diketik pengguna (tanpa spasi di pinggir)
     */
    public static String bacaTeks(String prompt) {
        System.out.print(prompt);
        try {
            return scanner.nextLine().trim();
        } catch (NoSuchElementException e) {
            System.out.println();
            System.out.println("Input berakhir. Program ditutup.");
            System.exit(0);
            return "";
        }
    }

    /**
     * Membaca angka bulat. Akan bertanya ulang sampai input berupa angka.
     *
     * @param prompt pertanyaan yang ditampilkan
     * @return angka bulat yang dimasukkan pengguna
     */
    public static int bacaAngka(String prompt) {
        while (true) {
            String input = bacaTeks(prompt);
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Input harus berupa angka bulat. Coba lagi.");
            }
        }
    }

    /**
     * Membaca angka bulat dalam rentang tertentu. Bertanya ulang sampai valid.
     *
     * @param prompt pertanyaan yang ditampilkan
     * @param min    nilai terkecil yang diperbolehkan
     * @param max    nilai terbesar yang diperbolehkan
     * @return angka bulat di antara min dan max
     */
    public static int bacaPilihan(String prompt, int min, int max) {
        while (true) {
            int angka = bacaAngka(prompt);
            try {
                if (angka < min || angka > max) {
                    throw new InputTidakValidException(
                            "Pilihan harus antara " + min + " dan " + max + ".");
                }
                return angka;
            } catch (InputTidakValidException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    /**
     * Membaca angka desimal (misalnya harga). Bertanya ulang sampai valid.
     *
     * @param prompt pertanyaan yang ditampilkan
     * @return angka desimal yang dimasukkan pengguna
     */
    public static double bacaDesimal(String prompt) {
        while (true) {
            String input = bacaTeks(prompt);
            try {
                return Double.parseDouble(input);
            } catch (NumberFormatException e) {
                System.out.println("Input harus berupa angka. Coba lagi.");
            }
        }
    }

    /**
     * Mengubah angka menjadi format rupiah, contoh: 25000 menjadi "Rp25.000".
     *
     * @param nilai jumlah uang
     * @return teks dalam format rupiah
     */
    public static String formatRupiah(double nilai) {
        return String.format("Rp%,.0f", nilai).replace(',', '.');
    }
}
