package service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import model.Admin;
import model.Akun;
import model.Kasir;
import model.Makanan;
import model.MenuItem;
import model.Minuman;

/**
 * Menyimpan dan membaca data ke file teks (fitur tambahan).
 * Dipanggil dari Main: muat data saat program mulai,
 * simpan data saat program selesai.
 * Yang disimpan: menu dan akun, di folder data/.
 * Setiap baris satu data, dipisah dengan tanda "|".
 */
public class FileManager {

    private static final String FILE_MENU = "data/menu.txt";
    private static final String FILE_AKUN = "data/akun.txt";

    /**
     * Menyimpan menu dan akun dari DataStore ke file.
     */
    public void simpanSemuaData() {
        try {
            Files.createDirectories(Paths.get("data"));

            try (PrintWriter out = new PrintWriter(new FileWriter(FILE_MENU))) {
                for (MenuItem m : DataStore.daftarMenu) {
                    String ekstra = "";
                    if (m instanceof Makanan) {
                        ekstra = String.valueOf(((Makanan) m).isPedas());
                    } else if (m instanceof Minuman) {
                        ekstra = ((Minuman) m).getUkuran();
                    }
                    out.println(m.getKategori() + "|" + m.getId() + "|" + m.getNama()
                            + "|" + m.getHarga() + "|" + m.getStok() + "|" + ekstra);
                }
            }

            try (PrintWriter out = new PrintWriter(new FileWriter(FILE_AKUN))) {
                for (Akun a : DataStore.daftarAkun.values()) {
                    out.println(a.getPeran() + "|" + a.getUsername() + "|"
                            + a.getPassword() + "|" + a.getNamaLengkap());
                }
            }
        } catch (IOException e) {
            System.out.println("Gagal menyimpan data: " + e.getMessage());
        }
    }

    /**
     * Membaca menu dan akun dari file ke DataStore. Jika file belum ada,
     * data awal dari DataStore.isiDataAwal() tetap dipakai.
     */
    public void muatSemuaData() {
        if (Files.exists(Paths.get(FILE_MENU))) {
            try (BufferedReader in = new BufferedReader(new FileReader(FILE_MENU))) {
                DataStore.daftarMenu.clear();
                String baris;
                while ((baris = in.readLine()) != null) {
                    String[] f = baris.split("\\|");
                    if (f.length < 6) {
                        continue;
                    }
                    double harga = Double.parseDouble(f[3]);
                    int stok = Integer.parseInt(f[4]);
                    if (f[0].equals("Makanan")) {
                        DataStore.daftarMenu.add(new Makanan(f[1], f[2], harga, stok,
                                Boolean.parseBoolean(f[5])));
                    } else {
                        DataStore.daftarMenu.add(new Minuman(f[1], f[2], harga, stok, f[5]));
                    }
                }
            } catch (IOException | NumberFormatException e) {
                System.out.println("Gagal membaca menu: " + e.getMessage());
            }
        }

        if (Files.exists(Paths.get(FILE_AKUN))) {
            try (BufferedReader in = new BufferedReader(new FileReader(FILE_AKUN))) {
                DataStore.daftarAkun.clear();
                String baris;
                while ((baris = in.readLine()) != null) {
                    String[] f = baris.split("\\|");
                    if (f.length < 4) {
                        continue;
                    }
                    Akun akun = f[0].equals("Admin")
                            ? new Admin(f[1], f[2], f[3])
                            : new Kasir(f[1], f[2], f[3]);
                    DataStore.daftarAkun.put(f[1], akun);
                }
            } catch (IOException e) {
                System.out.println("Gagal membaca akun: " + e.getMessage());
            }
        }
    }
}