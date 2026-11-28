package service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import model.ItemPesanan;
import model.Kasir;
import model.MenuItem;
import model.Pesanan;
import model.Transaksi;
import payment.Debit;
import payment.Pembayaran;
import payment.Qris;
import payment.Tunai;
import util.ConsoleHelper;
import util.StrukPrinter;

/**
 * Layanan pembayaran dan riwayat transaksi untuk Kasir.
 */
public class TransaksiService {

    /** Tarif pajak 10 persen. Dipakai juga oleh StrukPrinter. */
    public static final double TARIF_PAJAK = 0.10;

    /** Label pajak untuk ringkasan dan struk, contoh: "Pajak (10%)". */
    public static final String LABEL_PAJAK = "Pajak (" + Math.round(TARIF_PAJAK * 100) + "%)";

    /** Format tanggal dan jam di tabel riwayat kasir. */
    private static final DateTimeFormatter FORMAT_WAKTU = DateTimeFormatter.ofPattern("dd-MM HH:mm");

    /** Format satu baris tabel riwayat kasir: id, waktu, meja, metode, total. */
    private static final String FORMAT_RIWAYAT = "%-5s %-11s %4s  %-6s %13s";

    /** Membuat layanan transaksi. */
    public TransaksiService() {
    }

    /**
     * Memproses pembayaran sebuah pesanan sampai struk tercetak.
     *
     * @param pesanan pesanan yang akan dibayar
     * @param kasir   kasir yang memproses pembayaran
     */
    public void prosesPembayaran(Pesanan pesanan, Kasir kasir) {
        // 1. Hitung subtotal, pajak, dan total
        double subtotal = pesanan.hitungSubtotal();
        // pajak dibulatkan ke rupiah terdekat supaya total tidak berkoma
        double pajak = Math.round(subtotal * TARIF_PAJAK);
        double total = subtotal + pajak;

        // 2. Tampilkan ringkasan pesanan
        ConsoleHelper.cetakJudul("RINGKASAN PESANAN " + pesanan.getIdPesanan()
                + " (Meja " + pesanan.getNomorMeja() + ")");
        for (ItemPesanan item : pesanan.getDaftarItem()) {
            MenuItem menu = item.getMenu();
            ConsoleHelper.cetak(menu.getNama() + " (" + menu.getKeterangan() + ")");
            ConsoleHelper.cetak(ConsoleHelper.baris(
                    "  " + item.getJumlah() + " x " + ConsoleHelper.formatRupiah(menu.hitungHarga(1)),
                    ConsoleHelper.formatRupiah(item.getSubtotal())));
        }
        ConsoleHelper.cetak("-".repeat(ConsoleHelper.LEBAR));
        ConsoleHelper.cetak(ConsoleHelper.baris("Subtotal", ConsoleHelper.formatRupiah(subtotal)));
        ConsoleHelper.cetak(ConsoleHelper.baris(LABEL_PAJAK, ConsoleHelper.formatRupiah(pajak)));
        ConsoleHelper.cetak(ConsoleHelper.baris("TOTAL", ConsoleHelper.formatRupiah(total)));
        ConsoleHelper.cetak("");

        // Pilihan metode pembayaran (polymorphism lewat interface Pembayaran)
        Pembayaran[] metode = { new Tunai(), new Qris(), new Debit() };

        while (true) {
            ConsoleHelper.cetak("Pilih metode pembayaran:");
            for (int i = 0; i < metode.length; i++) {
                ConsoleHelper.cetak((i + 1) + ". " + metode[i].getNamaMetode());
            }
            ConsoleHelper.cetak("0. Batalkan pembayaran");
            int pilihan = ConsoleHelper.bacaPilihan("Pilih: ", 0, metode.length);

            if (pilihan == 0) {
                batalkanPesanan(pesanan);
                return;
            }

            Pembayaran dipilih = metode[pilihan - 1];

            // 3. Pembayaran berhasil: buat transaksi, simpan, cetak struk
            if (dipilih.bayar(total)) {
                kurangiStok(pesanan);
                bekukanIsiPesanan(pesanan);
                Transaksi transaksi = new Transaksi();
                transaksi.setIdTransaksi(DataStore.kodeBerikutnya("T", false));
                transaksi.setPesanan(pesanan);
                transaksi.setNamaKasir(kasir.getNamaLengkap());
                transaksi.setUsernameKasir(kasir.getUsername());
                transaksi.setMetodePembayaran(dipilih.getNamaMetode());
                transaksi.setSubtotal(subtotal);
                transaksi.setPajak(pajak);
                transaksi.setTotal(total);

                DataStore.riwayat.add(transaksi);
                // simpan data transaksi langsung ke file transaksi
                new FileManager().simpanSemuaData();
                ConsoleHelper.cetak("");
                StrukPrinter.cetak(transaksi);
                return;
            }

            // 4. Gagal: transaksi tidak disimpan, kasir boleh coba lagi atau batal
            ConsoleHelper.cetak("Pembayaran belum berhasil. Transaksi tidak disimpan.");
            int ulang = ConsoleHelper.bacaPilihan(
                    "Coba metode lain? (1 = ya, 0 = batalkan pesanan): ", 0, 1);
            if (ulang == 0) {
                batalkanPesanan(pesanan);
                return;
            }
        }
    }

    /**
     * Menampilkan riwayat transaksi milik satu kasir saja, lengkap dengan
     * jumlah dan total hari ini. Kasir juga bisa mencetak ulang struk
     * transaksinya sendiri dengan mengetik id transaksi.
     *
     * @param kasir kasir yang riwayatnya ditampilkan
     */
    public void tampilkanRiwayatKasir(Kasir kasir) {
        ConsoleHelper.cetakJudul("RIWAYAT TRANSAKSI - " + kasir.getNamaLengkap());

        List<Transaksi> milikKasir = new ArrayList<>();
        for (Transaksi t : DataStore.riwayat) {
            if (t.milikKasir(kasir)) {
                milikKasir.add(t);
            }
        }
        if (milikKasir.isEmpty()) {
            ConsoleHelper.cetak("Belum ada transaksi.");
            return;
        }

        ConsoleHelper.cetak(String.format(FORMAT_RIWAYAT, "ID", "Waktu", "Meja", "Metode", "Total"));
        ConsoleHelper.cetak("-".repeat(ConsoleHelper.LEBAR));
        LocalDate hariIni = LocalDate.now();
        double totalSemua = 0;
        double totalHariIni = 0;
        int jumlahHariIni = 0;
        for (Transaksi t : milikKasir) {
            ConsoleHelper.cetak(String.format(FORMAT_RIWAYAT,
                    t.getIdTransaksi(),
                    t.getWaktu().format(FORMAT_WAKTU),
                    t.getPesanan().getNomorMeja(),
                    t.getMetodePembayaran(),
                    ConsoleHelper.formatRupiah(t.getTotal())));
            totalSemua += t.getTotal();
            if (t.getWaktu().toLocalDate().equals(hariIni)) {
                jumlahHariIni++;
                totalHariIni += t.getTotal();
            }
        }
        ConsoleHelper.cetak("-".repeat(ConsoleHelper.LEBAR));
        ConsoleHelper.cetak(ConsoleHelper.baris("Hari ini (" + jumlahHariIni + " transaksi)",
                ConsoleHelper.formatRupiah(totalHariIni)));
        ConsoleHelper.cetak(ConsoleHelper.baris("Semua (" + milikKasir.size() + " transaksi)",
                ConsoleHelper.formatRupiah(totalSemua)));

        cetakUlangStruk(milikKasir);
    }

    /**
     * Menanyakan id transaksi lalu mencetak ulang struknya, hanya untuk
     * transaksi di daftar yang diberikan, sampai kasir menekan Enter.
     *
     * @param daftar transaksi yang boleh dicetak ulang
     */
    private void cetakUlangStruk(List<Transaksi> daftar) {
        while (true) {
            String id = ConsoleHelper.bacaTeks("\nID transaksi untuk cetak ulang struk (Enter = kembali): ");
            if (id.isEmpty()) {
                return;
            }
            Transaksi dipilih = null;
            for (Transaksi t : daftar) {
                if (t.getIdTransaksi().equalsIgnoreCase(id)) {
                    dipilih = t;
                }
            }
            if (dipilih == null) {
                ConsoleHelper.cetak("Transaksi " + id + " tidak ada di riwayat Anda.");
            } else {
                StrukPrinter.cetak(dipilih);
            }
        }
    }

    /**
     * Mengurangi stok setiap menu di pesanan. Dipanggil hanya setelah
     * pembayaran berhasil.
     *
     * @param pesanan pesanan yang sudah dibayar
     */
    private void kurangiStok(Pesanan pesanan) {
        for (ItemPesanan item : pesanan.getDaftarItem()) {
            MenuItem menu = item.getMenu();
            menu.setStok(Math.max(0, menu.getStok() - item.getJumlah()));
        }
    }

    /**
     * Mengganti menu di setiap item pesanan dengan salinannya. Setelah ini
     * transaksi tidak lagi terhubung ke menu asli, jadi kalau Admin
     * mengubah nama atau harga menu, riwayat dan struk lama tetap sesuai
     * dengan harga saat dibayar.
     *
     * @param pesanan pesanan yang sudah dibayar
     */
    private void bekukanIsiPesanan(Pesanan pesanan) {
        for (ItemPesanan item : pesanan.getDaftarItem()) {
            item.setMenu(item.getMenu().salin());
        }
    }

    /**
     * Membatalkan pesanan yang tidak jadi dibayar. Stok tidak perlu
     * dikembalikan karena stok baru dikurangi setelah pembayaran berhasil.
     *
     * @param pesanan pesanan yang dibatalkan
     */
    private void batalkanPesanan(Pesanan pesanan) {
        ConsoleHelper.cetak("Pesanan " + pesanan.getIdPesanan() + " dibatalkan. Stok tidak berubah.");
    }
}