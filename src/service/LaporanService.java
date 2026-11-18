package service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import model.Transaksi;
import util.ConsoleHelper;
import util.StrukPrinter;

/**
 * Layanan laporan untuk Admin: riwayat seluruh transaksi
 * dan laporan penjualan harian.
 * Datanya diambil dari DataStore.riwayat.
 */
public class LaporanService {

    /** Format tanggal dan jam di tabel riwayat. */
    private static final DateTimeFormatter FORMAT_WAKTU =
            DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

    /** Format tanggal di judul laporan harian. */
    private static final DateTimeFormatter FORMAT_TANGGAL =
            DateTimeFormatter.ofPattern("dd-MM-yyyy");

    /** Format satu baris tabel riwayat: id, waktu, kasir, metode, total. */
    private static final String FORMAT_BARIS = "%-6s %-16s %-14s %-6s %12s";

    /** Panjang satu baris tabel riwayat. */
    private static final int LEBAR_TABEL = 58;

    /** Membuat layanan laporan. */
    public LaporanService() {
    }

    /**
     * Menampilkan semua transaksi yang pernah terjadi:
     * id, waktu, kasir, metode pembayaran, dan total,
     * lalu jumlah transaksi dan total pendapatan keseluruhan.
     * Setelah itu Admin bisa mengetik id transaksi untuk melihat
     * rinciannya dalam bentuk struk.
     */
    public void tampilkanSemuaRiwayat() {
        ConsoleHelper.cetakJudul("RIWAYAT SEMUA TRANSAKSI");
        if (DataStore.riwayat.isEmpty()) {
            ConsoleHelper.cetak("Belum ada transaksi.");
            return;
        }

        ConsoleHelper.cetak(String.format(FORMAT_BARIS, "ID", "Waktu", "Kasir", "Metode", "Total"));
        ConsoleHelper.cetak("-".repeat(LEBAR_TABEL));
        double totalSemua = 0;
        for (Transaksi t : DataStore.riwayat) {
            ConsoleHelper.cetak(String.format(FORMAT_BARIS,
                    t.getIdTransaksi(),
                    t.getWaktu().format(FORMAT_WAKTU),
                    ConsoleHelper.potong(t.getNamaKasir(), 14),
                    t.getMetodePembayaran(),
                    ConsoleHelper.formatRupiah(t.getTotal())));
            totalSemua += t.getTotal();
        }
        ConsoleHelper.cetak("-".repeat(LEBAR_TABEL));
        ConsoleHelper.cetak(String.format("%-45s %12s",
                DataStore.riwayat.size() + " transaksi, total pendapatan",
                ConsoleHelper.formatRupiah(totalSemua)));

        lihatDetailTransaksi();
    }

    /**
     * Menanyakan id transaksi lalu mencetak ulang struknya lewat
     * StrukPrinter, sampai Admin menekan Enter tanpa mengisi apa-apa.
     */
    private void lihatDetailTransaksi() {
        while (true) {
            String id = ConsoleHelper.bacaTeks("\nID transaksi untuk lihat detail (Enter = kembali): ");
            if (id.isEmpty()) {
                return;
            }
            Transaksi transaksi = cariTransaksi(id);
            if (transaksi == null) {
                ConsoleHelper.cetak("Transaksi " + id + " tidak ditemukan.");
            } else {
                StrukPrinter.cetak(transaksi);
            }
        }
    }

    /**
     * Mencari transaksi di riwayat berdasarkan id (huruf besar/kecil sama).
     *
     * @param id id transaksi, contoh: "T001" atau "t001"
     * @return transaksi yang cocok, atau null jika tidak ada
     */
    private Transaksi cariTransaksi(String id) {
        for (Transaksi t : DataStore.riwayat) {
            if (t.getIdTransaksi().equalsIgnoreCase(id.trim())) {
                return t;
            }
        }
        return null;
    }

    /**
     * Menampilkan laporan penjualan untuk hari ini: jumlah transaksi,
     * subtotal, pajak, total pendapatan, lalu rinciannya per metode
     * pembayaran dan per kasir.
     */
    public void laporanHarian() {
        LocalDate hariIni = LocalDate.now();
        List<Transaksi> hariIniSaja = new ArrayList<>();
        for (Transaksi t : DataStore.riwayat) {
            if (t.getWaktu().toLocalDate().equals(hariIni)) {
                hariIniSaja.add(t);
            }
        }

        ConsoleHelper.cetakJudul("LAPORAN PENJUALAN " + hariIni.format(FORMAT_TANGGAL));
        if (hariIniSaja.isEmpty()) {
            ConsoleHelper.cetak("Belum ada transaksi hari ini.");
            return;
        }

        double subtotal = 0;
        double pajak = 0;
        double total = 0;
        Map<String, Double> perMetode = new TreeMap<>();
        Map<String, Double> perKasir = new TreeMap<>();
        for (Transaksi t : hariIniSaja) {
            subtotal += t.getSubtotal();
            pajak += t.getPajak();
            total += t.getTotal();
            perMetode.merge(t.getMetodePembayaran(), t.getTotal(), Double::sum);
            perKasir.merge(t.getNamaKasir(), t.getTotal(), Double::sum);
        }

        ConsoleHelper.cetak(baris("Jumlah transaksi", String.valueOf(hariIniSaja.size())));
        ConsoleHelper.cetak(baris("Subtotal", ConsoleHelper.formatRupiah(subtotal)));
        ConsoleHelper.cetak(baris("Pajak", ConsoleHelper.formatRupiah(pajak)));
        ConsoleHelper.cetak(baris("Total pendapatan", ConsoleHelper.formatRupiah(total)));

        cetakRincian("Per metode pembayaran", perMetode);
        cetakRincian("Per kasir", perKasir);
    }

    /**
     * Mencetak satu bagian rincian laporan, satu baris per kunci.
     *
     * @param judul   judul bagian, contoh: "Per kasir"
     * @param rincian pasangan nama dan total rupiahnya
     */
    private void cetakRincian(String judul, Map<String, Double> rincian) {
        ConsoleHelper.cetak("-".repeat(ConsoleHelper.LEBAR));
        ConsoleHelper.cetak(judul + ":");
        for (Map.Entry<String, Double> data : rincian.entrySet()) {
            ConsoleHelper.cetak(baris("  " + ConsoleHelper.potong(data.getKey(), 24),
                    ConsoleHelper.formatRupiah(data.getValue())));
        }
    }

    /**
     * Membuat satu baris dengan teks kiri dan teks kanan rata kanan
     * pada lebar tampilan.
     *
     * @param kiri  teks di sisi kiri
     * @param kanan teks di sisi kanan
     * @return baris yang sudah diformat
     */
    private String baris(String kiri, String kanan) {
        int spasi = Math.max(1, ConsoleHelper.LEBAR - kiri.length() - kanan.length());
        return kiri + " ".repeat(spasi) + kanan;
    }
}
