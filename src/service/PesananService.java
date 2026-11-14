package service;

import model.ItemPesanan;
import model.Kasir;
import model.MenuItem;
import model.Pesanan;
import util.ConsoleHelper;
import util.StokHabisException;

/**
 * Layanan pembuatan pesanan oleh Kasir.
 */
public class PesananService {

    private static int counter = 1;

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
        Pesanan pesanan = new Pesanan(String.format("P%03d", counter), meja);

        while (true) {
            for (MenuItem m : DataStore.daftarMenu) {
                ConsoleHelper.cetak(m.getId() + " - " + m.getNama() + " - "
                        + ConsoleHelper.formatRupiah(m.getHarga())
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
            try {
                if (jumlah <= 0 || jumlah > menu.getStok()) {
                    throw new StokHabisException("Jumlah tidak valid atau stok "
                            + menu.getNama() + " kurang (sisa " + menu.getStok() + ").");
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
        counter++;
        return pesanan;
    }
}
