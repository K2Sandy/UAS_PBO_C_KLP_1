package service;

import model.ItemPesanan;
import model.Kasir;
import model.MenuItem;
import model.Pesanan;
import model.Transaksi;
import util.ConsoleHelper;
import util.StokHabisException;

/**
 * Layanan pembuatan pesanan oleh Kasir.
 */
public class PesananService {

    /**
     * Membuat kode pesanan berikutnya berdasarkan kode terbesar di riwayat,
     * sehingga kode tidak kembali ke P001 setelah program dijalankan ulang.
     *
     * @return kode pesanan baru, contoh: "P003"
     */
    private String buatIdPesanan() {
        int terbesar = 0;
        for (Transaksi t : DataStore.riwayat) {
            String id = t.getPesanan().getIdPesanan();
            if (id != null && id.length() > 1 && id.charAt(0) == 'P') {
                try {
                    terbesar = Math.max(terbesar, Integer.parseInt(id.substring(1)));
                } catch (NumberFormatException e) {
                    // kode tidak berformat Pxxx: abaikan
                }
            }
        }
        return String.format("P%03d", terbesar + 1);
    }

    /**
     * Membuat pesanan baru lewat tanya-jawab dengan kasir.
     *
     * @param kasir kasir yang sedang membuat pesanan
     * @return pesanan yang sudah terisi, atau null jika dibatalkan
     *         atau tidak ada item yang dipesan
     */
    public Pesanan buatPesananBaru(Kasir kasir) {
        int meja = ConsoleHelper.bacaAngka("Nomor meja (0 = batal): ");
        if (meja <= 0) {
            return null;
        }
        Pesanan pesanan = new Pesanan(buatIdPesanan(), meja);

        while (true) {
            for (MenuItem m : DataStore.daftarMenu) {
                ConsoleHelper.cetak(m.getId() + " - " + m.getNama() + " - "
                        + ConsoleHelper.formatRupiah(m.hitungHarga(1))
                        + " (stok " + m.getStok() + ")");
            }

            String id = ConsoleHelper.bacaTeks("Id menu (0 = selesai): ");
            if (id.equals("0")) {
                break;
            }

            MenuItem menu = null;
            for (MenuItem m : DataStore.daftarMenu) {
                if (m.getId().equalsIgnoreCase(id)) {
                    menu = m;
                }
            }
            if (menu == null) {
                ConsoleHelper.cetak("Menu tidak ditemukan.");
                continue;
            }

            int jumlah = ConsoleHelper.bacaAngka("Jumlah: ");
            if (jumlah <= 0) {
                ConsoleHelper.cetak("Jumlah harus lebih dari 0.");
                continue;
            }
            try {
                if (jumlah > menu.getStok()) {
                    throw new StokHabisException("Stok " + menu.getNama()
                            + " kurang (sisa " + menu.getStok() + ").");
                }
                menu.setStok(menu.getStok() - jumlah);
                pesanan.tambahItem(new ItemPesanan(menu, jumlah));
            } catch (StokHabisException e) {
                ConsoleHelper.cetak(e.getMessage());
            }
        }

        if (pesanan.getDaftarItem().isEmpty()) {
            return null;
        }
        return pesanan;
    }
}
