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
 * Dipanggil dari Main: muat data saat program mulai, simpan data saat
 * program berhenti, dan dari TransaksiService setiap pembayaran berhasil.
 * Yang disimpan di folder data/ (relatif terhadap folder tempat program
 * dijalankan): menu.txt, akun.txt, dan transaksi.txt.
 * Setiap baris satu data, dipisah dengan tanda "|".
 */
public class FileManager {

    /** Lokasi file data menu. */
    private static final String FILE_MENU = "data/menu.txt";
    /** Lokasi file data akun. */
    private static final String FILE_AKUN = "data/akun.txt";
    /** Lokasi file riwayat transaksi. */
    private static final String FILE_TRANSAKSI = "data/transaksi.txt";

    /** Membuat pengelola file. */
    public FileManager() {
    }

    /**
     * Menyimpan menu, akun, dan riwayat transaksi dari DataStore ke file.
     * Setiap file disimpan terpisah, jadi kalau satu file gagal ditulis
     * file lainnya tetap tersimpan.
     */
    public void simpanSemuaData() {
        try {
            Files.createDirectories(Paths.get("data"));
        } catch (IOException e) {
            System.out.println("Gagal membuat folder data: " + e.getMessage());
            return;
        }

        try (PrintWriter out = new PrintWriter(new FileWriter(FILE_MENU))) {
            for (MenuItem m : DataStore.daftarMenu) {
                out.println(m.getKategori() + "|" + bersih(m.getId()) + "|" + bersih(m.getNama())
                        + "|" + m.getHarga() + "|" + m.getStok() + "|" + bersih(ekstraMenu(m)));
            }
        } catch (IOException e) {
            System.out.println("Gagal menyimpan menu: " + e.getMessage());
        }

        try (PrintWriter out = new PrintWriter(new FileWriter(FILE_AKUN))) {
            for (Akun a : DataStore.daftarAkun.values()) {
                out.println(a.getPeran() + "|" + bersih(a.getUsername()) + "|"
                        + bersih(a.getPassword()) + "|" + bersih(a.getNamaLengkap()));
            }
        } catch (IOException e) {
            System.out.println("Gagal menyimpan akun: " + e.getMessage());
        }

        try {
            simpanTransaksi();
        } catch (IOException e) {
            System.out.println("Gagal menyimpan transaksi: " + e.getMessage());
        }
    }

    /**
     * Membaca menu, akun, dan riwayat transaksi dari file ke DataStore.
     * Jika sebuah file belum ada, data awal dari DataStore.isiDataAwal()
     * tetap dipakai. Baris yang rusak dilewati satu per satu, jadi satu
     * baris buruk tidak menghilangkan data lain.
     */
    public void muatSemuaData() {
        muatMenu();
        muatAkun();
        pastikanAdminAda();
        muatTransaksi();
    }

    /**
     * Memastikan selalu ada minimal satu akun Admin. Kalau akun.txt kosong
     * atau rusak sehingga tidak ada Admin, akun admin bawaan dibuat lagi
     * supaya program tetap bisa dipakai (tidak terkunci).
     */
    private void pastikanAdminAda() {
        for (Akun a : DataStore.daftarAkun.values()) {
            if (a instanceof Admin) {
                return;
            }
        }
        String username = "admin";
        while (DataStore.daftarAkun.containsKey(username)) {
            username = username + "1";
        }
        DataStore.daftarAkun.put(username, new Admin(username, "admin123", "Admin Resto"));
        System.out.println("Tidak ada akun Admin di data. Akun bawaan dibuat: "
                + username + " / admin123");
    }

    /**
     * Membaca data/menu.txt ke DataStore.daftarMenu.
     */
    private void muatMenu() {
        if (!Files.exists(Paths.get(FILE_MENU))) {
            return;
        }
        try (BufferedReader in = new BufferedReader(new FileReader(FILE_MENU))) {
            DataStore.daftarMenu.clear();
            String baris;
            int nomorBaris = 0;
            while ((baris = in.readLine()) != null) {
                nomorBaris++;
                if (baris.isBlank()) {
                    continue;
                }
                String[] f = baris.split("\\|", -1);
                try {
                    if (f.length < 6) {
                        throw new IllegalArgumentException("jumlah kolom kurang");
                    }
                    double harga = Double.parseDouble(f[3]);
                    int stok = Integer.parseInt(f[4]);
                    if (f[1].isBlank() || f[1].equals("0") || idMenuSudahAda(f[1])) {
                        throw new IllegalArgumentException("ID kosong, \"0\", atau dobel: " + f[1]);
                    }
                    if (!Double.isFinite(harga) || harga <= 0 || stok < 0) {
                        throw new IllegalArgumentException("harga atau stok tidak valid");
                    }
                    if (f[0].equals("Makanan")) {
                        DataStore.daftarMenu.add(new Makanan(f[1], f[2], harga, stok,
                                Boolean.parseBoolean(f[5])));
                    } else if (f[0].equals("Minuman")) {
                        String ukuran = f[5].isBlank() ? "Regular" : f[5];
                        DataStore.daftarMenu.add(new Minuman(f[1], f[2], harga, stok, ukuran));
                    } else {
                        throw new IllegalArgumentException("kategori tidak dikenal: " + f[0]);
                    }
                } catch (IllegalArgumentException e) {
                    // NumberFormatException juga turunan IllegalArgumentException
                    System.out.println("Baris " + nomorBaris + " di menu.txt dilewati ("
                            + e.getMessage() + ").");
                }
            }
        } catch (IOException e) {
            System.out.println("Gagal membaca menu: " + e.getMessage());
        }
    }

    /**
     * Memeriksa apakah id menu sudah dimuat sebelumnya (mencegah id dobel).
     *
     * @param id id menu yang diperiksa
     * @return true jika id tersebut sudah ada di DataStore.daftarMenu
     */
    private boolean idMenuSudahAda(String id) {
        for (MenuItem m : DataStore.daftarMenu) {
            if (m.getId().equalsIgnoreCase(id)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Membaca data/akun.txt ke DataStore.daftarAkun.
     */
    private void muatAkun() {
        if (!Files.exists(Paths.get(FILE_AKUN))) {
            return;
        }
        try (BufferedReader in = new BufferedReader(new FileReader(FILE_AKUN))) {
            DataStore.daftarAkun.clear();
            String baris;
            int nomorBaris = 0;
            while ((baris = in.readLine()) != null) {
                nomorBaris++;
                if (baris.isBlank()) {
                    continue;
                }
                String[] f = baris.split("\\|", -1);
                if (f.length < 4 || f[1].isBlank()) {
                    System.out.println("Baris " + nomorBaris + " di akun.txt dilewati (format salah).");
                    continue;
                }
                Akun akun;
                if (f[0].equals("Admin")) {
                    akun = new Admin(f[1], f[2], f[3]);
                } else if (f[0].equals("Kasir")) {
                    akun = new Kasir(f[1], f[2], f[3]);
                } else {
                    System.out.println("Baris " + nomorBaris + " di akun.txt dilewati (peran tidak dikenal).");
                    continue;
                }
                DataStore.daftarAkun.put(f[1], akun);
            }
        } catch (IOException e) {
            System.out.println("Gagal membaca akun: " + e.getMessage());
        }
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
                        + "|" + p.getIdPesanan() + "|" + p.getNomorMeja()
                        + "|" + bersih(t.getUsernameKasir()));
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
                        // kolom ke-11 (username kasir) belum ada di data lama
                        t.setUsernameKasir(f.length >= 11 ? f[10] : "");
                        DataStore.riwayat.add(t);
                    } else if (f[0].equals("T")) {
                        // baris transaksi kurang kolom: item di bawahnya jangan ditempel ke transaksi lain
                        pesananAktif = null;
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