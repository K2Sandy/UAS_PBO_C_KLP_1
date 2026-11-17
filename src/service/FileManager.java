package service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import model.Admin;
import model.Akun;
import model.ItemPesanan;
import model.Kasir;
import model.Makanan;
import model.MenuItem;
import model.Minuman;
import model.Pesanan;
import model.Transaksi;

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
    private static final String FILE_TRANSAKSI = "data/transaksi.txt";

    /**
     * Menyimpan menu dan akun dari DataStore ke file.
     */
    public void simpanSemuaData() {
        try {
            Files.createDirectories(Paths.get("data"));

            try (PrintWriter out = new PrintWriter(new FileWriter(FILE_MENU))) {
                for (MenuItem m : DataStore.daftarMenu) {
                    out.println(m.getKategori() + "|" + bersih(m.getId()) + "|" + bersih(m.getNama())
                            + "|" + m.getHarga() + "|" + m.getStok() + "|" + bersih(ekstraMenu(m)));
                }
            }

            try (PrintWriter out = new PrintWriter(new FileWriter(FILE_AKUN))) {
                for (Akun a : DataStore.daftarAkun.values()) {
                    out.println(a.getPeran() + "|" + bersih(a.getUsername()) + "|"
                            + bersih(a.getPassword()) + "|" + bersih(a.getNamaLengkap()));
                }
            }

            simpanTransaksi();
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
        muatTransaksi();
    }

    /**
     * Menyimpan seluruh riwayat transaksi ke data/transaksi.txt.
     * Satu transaksi ditulis sebagai satu baris "T" (data transaksi),
     * diikuti baris "I" untuk setiap item pesanannya. Item disimpan
     * sebagai salinan (id, nama, harga, jumlah) supaya riwayat tetap
     * benar walaupun menu diubah atau dihapus Admin di kemudian hari.
     *
     * @throws IOException jika file tidak bisa ditulis
     */
    private void simpanTransaksi() throws IOException {
        try (PrintWriter out = new PrintWriter(new FileWriter(FILE_TRANSAKSI))) {
            for (Transaksi t : DataStore.riwayat) {
                Pesanan p = t.getPesanan();
                out.println("T|" + t.getIdTransaksi() + "|" + t.getWaktu()
                        + "|" + bersih(t.getNamaKasir()) + "|" + t.getMetodePembayaran()
                        + "|" + t.getSubtotal() + "|" + t.getPajak() + "|" + t.getTotal()
                        + "|" + p.getIdPesanan() + "|" + p.getNomorMeja());
                for (ItemPesanan item : p.getDaftarItem()) {
                    MenuItem m = item.getMenu();
                    out.println("I|" + m.getKategori() + "|" + bersih(m.getId())
                            + "|" + bersih(m.getNama()) + "|" + m.getHarga()
                            + "|" + bersih(ekstraMenu(m)) + "|" + item.getJumlah());
                }
            }
        }
    }

    /**
     * Membaca riwayat transaksi dari data/transaksi.txt ke DataStore.riwayat.
     * Baris yang rusak dilewati supaya satu baris buruk tidak menghapus
     * seluruh riwayat. Jika file belum ada, riwayat dibiarkan kosong.
     */
    private void muatTransaksi() {
        if (!Files.exists(Paths.get(FILE_TRANSAKSI))) {
            return;
        }
        try (BufferedReader in = new BufferedReader(new FileReader(FILE_TRANSAKSI))) {
            DataStore.riwayat.clear();
            Pesanan pesananAktif = null;
            String baris;
            while ((baris = in.readLine()) != null) {
                String[] f = baris.split("\\|", -1);
                try {
                    if (f[0].equals("T") && f.length >= 10) {
                        Transaksi t = new Transaksi();
                        t.setIdTransaksi(f[1]);
                        t.setWaktu(LocalDateTime.parse(f[2]));
                        t.setNamaKasir(f[3]);
                        t.setMetodePembayaran(f[4]);
                        t.setSubtotal(Double.parseDouble(f[5]));
                        t.setPajak(Double.parseDouble(f[6]));
                        t.setTotal(Double.parseDouble(f[7]));
                        pesananAktif = new Pesanan(f[8], Integer.parseInt(f[9]));
                        t.setPesanan(pesananAktif);
                        DataStore.riwayat.add(t);
                    } else if (f[0].equals("I") && f.length >= 7 && pesananAktif != null) {
                        double harga = Double.parseDouble(f[4]);
                        MenuItem menu = f[1].equals("Makanan")
                                ? new Makanan(f[2], f[3], harga, 0, Boolean.parseBoolean(f[5]))
                                : new Minuman(f[2], f[3], harga, 0, f[5]);
                        pesananAktif.tambahItem(new ItemPesanan(menu, Integer.parseInt(f[6])));
                    }
                } catch (NumberFormatException | DateTimeParseException e) {
                    // baris rusak: lewati, dan jangan tempelkan item berikutnya ke transaksi lain
                    if (f[0].equals("T")) {
                        pesananAktif = null;
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Gagal membaca transaksi: " + e.getMessage());
        }
    }

    /**
     * Mengambil data tambahan khas jenis menu: pedas (Makanan) atau ukuran (Minuman).
     *
     * @param m menu yang dibaca
     * @return teks data tambahan
     */
    private String ekstraMenu(MenuItem m) {
        if (m instanceof Makanan) {
            return String.valueOf(((Makanan) m).isPedas());
        } else if (m instanceof Minuman) {
            return ((Minuman) m).getUkuran();
        }
        return "";
    }

    /**
     * Membuang karakter pemisah "|" dan baris baru dari teks bebas
     * supaya format file tidak rusak.
     *
     * @param teks teks yang akan ditulis ke file
     * @return teks yang aman ditulis
     */
    private String bersih(String teks) {
        return teks == null ? "" : teks.replace('|', '/').replace('\n', ' ').replace('\r', ' ');
    }
}