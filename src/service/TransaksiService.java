package service;

import java.time.format.DateTimeFormatter;

import model.ItemPesanan;
import model.Kasir;
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

    /** Tarif pajak 10 persen. */
    private static final double TARIF_PAJAK = 0.10;

    /**
     * Memproses pembayaran sebuah pesanan sampai struk tercetak.
     *
     * @param pesanan pesanan yang akan dibayar
     * @param kasir   kasir yang memproses pembayaran
     */
    public void prosesPembayaran(Pesanan pesanan, Kasir kasir) {
        // 1. Hitung subtotal, pajak, dan total
        double subtotal = pesanan.hitungSubtotal();
        double pajak = subtotal * TARIF_PAJAK;
        double total = subtotal + pajak;

        // 2. Tampilkan ringkasan pesanan
        ConsoleHelper.cetakJudul("RINGKASAN PESANAN " + pesanan.getIdPesanan()
                + " (Meja " + pesanan.getNomorMeja() + ")");
        for (ItemPesanan item : pesanan.getDaftarItem()) {
            ConsoleHelper.cetak(String.format("%-20s %3d x %12s",
                    item.getMenu().getNama(),
                    item.getJumlah(),
                    ConsoleHelper.formatRupiah(item.getSubtotal())));
        }
        ConsoleHelper.cetak("-".repeat(44));
        ConsoleHelper.cetak(String.format("%-20s %23s", "Subtotal", ConsoleHelper.formatRupiah(subtotal)));
        ConsoleHelper.cetak(String.format("%-20s %23s", "Pajak (10%)", ConsoleHelper.formatRupiah(pajak)));
        ConsoleHelper.cetak(String.format("%-20s %23s", "TOTAL", ConsoleHelper.formatRupiah(total)));

        // Pilihan metode pembayaran (polymorphism lewat interface Pembayaran)
        Pembayaran[] metode = { new Tunai(), new Qris(), new Debit() };

        while (true) {
            ConsoleHelper.cetak("");
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
                Transaksi transaksi = new Transaksi();
                transaksi.setIdTransaksi(String.format("T%03d", DataStore.riwayat.size() + 1));
                transaksi.setPesanan(pesanan);
                transaksi.setNamaKasir(kasir.getNamaLengkap());
                transaksi.setMetodePembayaran(dipilih.getNamaMetode());
                transaksi.setSubtotal(subtotal);
                transaksi.setPajak(pajak);
                transaksi.setTotal(total);

                DataStore.riwayat.add(transaksi);
                // simpan data transaksi langsung ke file transaksi
                new FileManager().simpanSemuaData();
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
     * Menampilkan riwayat transaksi milik satu kasir saja.
     *
     * @param kasir kasir yang riwayatnya ditampilkan
     */
    public void tampilkanRiwayatKasir(Kasir kasir) {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        boolean adaData = false;
        double totalPendapatan = 0;

        for (Transaksi t : DataStore.riwayat) {
            if (!t.getNamaKasir().equals(kasir.getNamaLengkap())) {
                continue;
            }
            if (!adaData) {
                ConsoleHelper.cetakJudul("RIWAYAT TRANSAKSI - " + kasir.getNamaLengkap());
                System.out.printf("%-8s %-17s %-6s %-10s %12s%n",
                        "ID", "Waktu", "Meja", "Metode", "Total");
                System.out.println("-".repeat(57));
                adaData = true;
            }
            System.out.printf("%-8s %-17s %-6d %-10s %12s%n",
                    t.getIdTransaksi(),
                    t.getWaktu().format(format),
                    t.getPesanan().getNomorMeja(),
                    t.getMetodePembayaran(),
                    ConsoleHelper.formatRupiah(t.getTotal()));
            totalPendapatan += t.getTotal();
        }

        if (!adaData) {
            ConsoleHelper.cetak("Belum ada transaksi.");
            return;
        }
        System.out.println("-".repeat(57));
        System.out.printf("%-43s %12s%n", "Total", ConsoleHelper.formatRupiah(totalPendapatan));
    }

    /**
     * Membatalkan pesanan yang tidak jadi dibayar dan mengembalikan stok
     * menu yang sebelumnya sudah dikurangi saat pesanan dibuat.
     *
     * @param pesanan pesanan yang dibatalkan
     */
    private void batalkanPesanan(Pesanan pesanan) {
        for (ItemPesanan item : pesanan.getDaftarItem()) {
            item.getMenu().setStok(item.getMenu().getStok() + item.getJumlah());
        }
        ConsoleHelper.cetak("Pesanan dibatalkan. Stok menu dikembalikan.");
    }
}