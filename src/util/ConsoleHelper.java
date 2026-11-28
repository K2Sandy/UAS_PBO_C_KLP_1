package util;

import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Kumpulan fungsi bantu untuk tampilan dan input di CLI.
 * Semua anggota tim memakai class ini supaya input selalu divalidasi
 * dan tampilan seragam. Semua method bersifat static.
 */
public class ConsoleHelper {

    /** Satu-satunya Scanner untuk membaca keyboard di seluruh program. */
    private static final Scanner scanner = new Scanner(System.in);

    /** Lebar garis dan tampilan (dalam karakter), dipakai juga oleh struk. */
    public static final int LEBAR = 44;

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
     * Membaca satu baris teks dari pengguna, lalu mencetak satu baris kosong.
     * Jika input sudah habis (misalnya Ctrl+D), program berhenti dengan rapi
     * dan data tetap disimpan lewat shutdown hook yang didaftarkan di Main.
     *
     * @param prompt pertanyaan yang ditampilkan
     * @return teks yang diketik pengguna (tanpa spasi di pinggir)
     */
    public static String bacaTeks(String prompt) {
        System.out.print(prompt);
        try {
            String input = scanner.nextLine().trim();
            System.out.println();
            return input;
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
                double angka = Double.parseDouble(input);
                if (Double.isFinite(angka)) {
                    return angka;
                }
            } catch (NumberFormatException e) {
                // ditangani di bawah: tanya ulang
            }
            System.out.println("Input harus berupa angka. Coba lagi.");
        }
    }

    /**
     * Membaca nominal rupiah (bilangan bulat, tidak negatif). Titik pemisah
     * ribuan dan awalan "Rp" boleh ditulis, jadi "25000", "25.000", dan
     * "Rp25.000" sama-sama dibaca 25000. Titik harus memisahkan tiga angka,
     * jadi "12.5" atau "50000.00" ditolak (bukan dibaca 125 / 5.000.000).
     * Bertanya ulang sampai valid.
     *
     * @param prompt pertanyaan yang ditampilkan
     * @return nominal rupiah yang dimasukkan pengguna
     */
    public static long bacaRupiah(String prompt) {
        while (true) {
            String input = bacaTeks(prompt).replace("Rp", "").replace("rp", "").replace(" ", "");
            // boleh: 25000 atau 25.000 / 1.250.000 (titik tiap tiga angka)
            if (input.matches("\\d{1,12}") || input.matches("\\d{1,3}(\\.\\d{3}){1,3}")) {
                return Long.parseLong(input.replace(".", ""));
            }
            System.out.println("Masukkan nominal rupiah tanpa koma, contoh: 25000 atau 25.000.");
        }
    }

    /**
     * Membaca teks yang wajib diisi. Bertanya ulang jika kosong atau
     * mengandung tanda "|" (tanda itu dipakai sebagai pemisah di file data).
     *
     * @param prompt pertanyaan yang ditampilkan
     * @return teks yang tidak kosong dan tanpa tanda "|"
     */
    public static String bacaTeksWajib(String prompt) {
        while (true) {
            String input = bacaTeks(prompt);
            if (input.isEmpty()) {
                System.out.println("Tidak boleh kosong. Coba lagi.");
            } else if (input.contains("|")) {
                System.out.println("Tidak boleh mengandung tanda |. Coba lagi.");
            } else {
                return input;
            }
        }
    }

    /**
     * Membuat satu baris dengan teks kiri dan teks kanan yang rata kanan
     * pada lebar tampilan, contoh: "Subtotal            Rp40.000".
     *
     * @param kiri  teks di sisi kiri
     * @param kanan teks di sisi kanan
     * @return baris yang sudah diformat selebar LEBAR
     */
    public static String baris(String kiri, String kanan) {
        int spasi = Math.max(1, LEBAR - kiri.length() - kanan.length());
        return kiri + " ".repeat(spasi) + kanan;
    }

    /**
     * Memotong teks yang terlalu panjang supaya kolom tabel tetap lurus,
     * contoh: potong("Nasi Goreng Spesial Telur", 15) menjadi "Nasi Goreng ...".
     *
     * @param teks     teks yang akan ditampilkan
     * @param panjang  panjang maksimal (minimal 4)
     * @return teks asli jika cukup pendek, atau potongannya diakhiri "..."
     */
    public static String potong(String teks, int panjang) {
        if (teks == null) {
            return "";
        }
        if (teks.length() <= panjang) {
            return teks;
        }
        return teks.substring(0, Math.max(1, panjang - 3)) + "...";
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
