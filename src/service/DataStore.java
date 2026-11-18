package service;

import java.util.ArrayList;
import java.util.HashMap;
import model.Admin;
import model.Akun;
import model.Kasir;
import model.Makanan;
import model.MenuItem;
import model.Minuman;
import model.Transaksi;

/**
 * Tempat penyimpanan data bersama untuk seluruh program (Collection).
 * Semua service membaca dan menulis data lewat class ini,
 * jadi data Admin dan Kasir otomatis saling terhubung.
 */
public class DataStore {

    /** Daftar semua menu restoran. */
    public static final ArrayList<MenuItem> daftarMenu = new ArrayList<>();

    /** Daftar semua akun. Kuncinya adalah username. */
    public static final HashMap<String, Akun> daftarAkun = new HashMap<>();

    /** Riwayat semua transaksi yang sudah dibayar. */
    public static final ArrayList<Transaksi> riwayat = new ArrayList<>();

    /** Class penyimpan data, tidak perlu dibuat objeknya. */
    private DataStore() {
    }

    /**
     * Mencari menu berdasarkan id (huruf besar/kecil dianggap sama,
     * spasi di pinggir diabaikan). Dipakai oleh MenuService dan PesananService
     * supaya cara mencari menu cukup ditulis di satu tempat.
     *
     * @param id kode menu yang dicari, contoh: "M01" atau "m01"
     * @return menu yang cocok, atau null jika tidak ada
     */
    public static MenuItem cariMenu(String id) {
        if (id == null) {
            return null;
        }
        String dicari = id.trim();
        for (MenuItem menu : daftarMenu) {
            if (menu.getId().equalsIgnoreCase(dicari)) {
                return menu;
            }
        }
        return null;
    }

    /**
     * Membuat kode berikutnya dari angka terbesar yang sudah dipakai di
     * riwayat, contoh: jika riwayat sudah punya T001 sampai T007, kode
     * transaksi berikutnya T008. Karena dihitung dari riwayat yang dimuat
     * dari file, nomor tidak kembali ke 001 saat program dibuka lagi.
     *
     * @param awalan       huruf awal kode: "T" untuk transaksi, "P" untuk pesanan
     * @param pakaiPesanan true untuk menghitung dari id pesanan,
     *                     false untuk menghitung dari id transaksi
     * @return kode baru, contoh: "T008"
     */
    public static String kodeBerikutnya(String awalan, boolean pakaiPesanan) {
        int terbesar = 0;
        for (Transaksi t : riwayat) {
            String kode = pakaiPesanan ? t.getPesanan().getIdPesanan() : t.getIdTransaksi();
            if (kode != null && kode.startsWith(awalan)) {
                try {
                    terbesar = Math.max(terbesar, Integer.parseInt(kode.substring(awalan.length())));
                } catch (NumberFormatException e) {
                    // kode tidak berformat angka: abaikan
                }
            }
        }
        return String.format("%s%03d", awalan, terbesar + 1);
    }

    /**
     * Mengisi data awal agar program bisa langsung dicoba:
     * satu akun Admin, satu akun Kasir, dan beberapa menu contoh.
     */
    public static void isiDataAwal() {
        daftarAkun.put("admin", new Admin("admin", "admin123", "Admin Resto"));
        daftarAkun.put("kasir", new Kasir("kasir", "kasir123", "Kasir Satu"));

        daftarMenu.add(new Makanan("M01", "Nasi Goreng", 20000, 20, true));
        daftarMenu.add(new Makanan("M02", "Ayam Bakar", 25000, 15, false));
        daftarMenu.add(new Makanan("M03", "Mie Goreng", 18000, 20, true));
        daftarMenu.add(new Minuman("N01", "Es Teh", 5000, 30, "Regular"));
        daftarMenu.add(new Minuman("N02", "Jus Jeruk", 12000, 20, "Large"));
    }
}
