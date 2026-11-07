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
