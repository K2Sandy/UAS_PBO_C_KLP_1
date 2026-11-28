package service;

import model.ItemPesanan;
import model.Kasir;
import model.MenuItem;
import model.Pesanan;
import util.ConsoleHelper;
import util.StokHabisException;

/**
 * Layanan pembuatan pesanan oleh Kasir.
 * Saat pesanan dibuat stok hanya diperiksa, belum dikurangi. Stok baru
 * dikurangi oleh TransaksiService setelah pembayaran berhasil, jadi
 * pesanan yang batal atau program yang tertutup di tengah jalan tidak
 * membuat stok hilang.
 */
public class PesananService {

    /** Dipakai untuk menampilkan daftar menu dengan format yang sama dengan Admin. */
    private final MenuService menuService = new MenuService();

    /** Membuat layanan pesanan. */
    public PesananService() {
    }

    /**
     * Membuat pesanan baru lewat tanya-jawab dengan kasir.
     *
     * @param kasir kasir yang sedang membuat pesanan
     * @return pesanan yang sudah terisi, atau null jika dibatalkan
     *         atau tidak ada item yang dipesan
     */
    public Pesanan buatPesananBaru(Kasir kasir) {
        ConsoleHelper.cetakJudul("PESANAN BARU - " + kasir.getNamaLengkap());
        int meja = bacaNomorMeja();
        if (meja == 0) {
            ConsoleHelper.cetak("Pesanan dibatalkan.");
            return null;
        }
        Pesanan pesanan = new Pesanan(DataStore.kodeBerikutnya("P", true), meja);

        menuService.tampilkanSemuaMenu();
        while (true) {
            ConsoleHelper.cetak("");
            String id = ConsoleHelper.bacaTeks("ID menu (0 = selesai, M = lihat menu lagi): ");
            if (id.equals("0")) {
                break;
            }
            if (id.equalsIgnoreCase("M")) {
                menuService.tampilkanSemuaMenu();
                continue;
            }

            MenuItem menu = DataStore.cariMenu(id);
            if (menu == null) {
                ConsoleHelper.cetak("Menu dengan ID " + id + " tidak ditemukan.");
                continue;
            }
            int sisa = menu.getStok() - jumlahDiPesanan(pesanan, menu);
            if (sisa <= 0) {
                ConsoleHelper.cetak("Stok " + menu.getNama() + " habis.");
                continue;
            }

            int jumlah = ConsoleHelper.bacaAngka("Jumlah " + menu.getNama() + ": ");
            try {
                if (jumlah <= 0) {
                    ConsoleHelper.cetak("Jumlah harus lebih dari 0.");
                    continue;
                }
                if (jumlah > sisa) {
                    throw new StokHabisException("Stok " + menu.getNama() + " tidak cukup (sisa "
                            + sisa + ").");
                }
                tambahAtauGabung(pesanan, menu, jumlah);
                ConsoleHelper.cetak("+ " + menu.getNama() + " x" + jumlah
                        + "  (isi pesanan: " + pesanan.getDaftarItem().size() + " menu, subtotal "
                        + ConsoleHelper.formatRupiah(pesanan.hitungSubtotal()) + ")");
            } catch (StokHabisException e) {
                ConsoleHelper.cetak(e.getMessage());
            }
        }

        if (pesanan.getDaftarItem().isEmpty()) {
            ConsoleHelper.cetak("Belum ada menu yang dipesan. Pesanan dibatalkan.");
            return null;
        }
        return pesanan;
    }

    /**
     * Menanyakan nomor meja sampai valid (0 berarti batal).
     *
     * @return nomor meja lebih dari 0, atau 0 jika kasir membatalkan
     */
    private int bacaNomorMeja() {
        while (true) {
            int meja = ConsoleHelper.bacaAngka("Nomor meja (0 = batal): ");
            if (meja >= 0) {
                return meja;
            }
            ConsoleHelper.cetak("Nomor meja tidak boleh negatif.");
        }
    }

    /**
     * Menghitung berapa porsi sebuah menu yang sudah masuk ke pesanan ini,
     * supaya total yang dipesan tidak melebihi stok.
     *
     * @param pesanan pesanan yang sedang dibuat
     * @param menu    menu yang diperiksa
     * @return jumlah porsi menu tersebut di pesanan (0 jika belum ada)
     */
    private int jumlahDiPesanan(Pesanan pesanan, MenuItem menu) {
        for (ItemPesanan item : pesanan.getDaftarItem()) {
            if (item.getMenu() == menu) {
                return item.getJumlah();
            }
        }
        return 0;
    }

    /**
     * Menambahkan menu ke pesanan. Jika menu yang sama sudah ada di pesanan,
     * jumlahnya ditambahkan ke baris yang sudah ada supaya struk tidak
     * menampilkan menu yang sama dua kali.
     *
     * @param pesanan pesanan yang sedang dibuat
     * @param menu    menu yang dipesan
     * @param jumlah  banyak porsi yang ditambahkan
     */
    private void tambahAtauGabung(Pesanan pesanan, MenuItem menu, int jumlah) {
        for (ItemPesanan item : pesanan.getDaftarItem()) {
            if (item.getMenu() == menu) {
                item.setJumlah(item.getJumlah() + jumlah);
                return;
            }
        }
        pesanan.tambahItem(new ItemPesanan(menu, jumlah));
    }
}
