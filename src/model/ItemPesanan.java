package model;

/**
 * Satu baris dalam pesanan: sebuah menu beserta jumlah yang dipesan.
 */
public class ItemPesanan {

    /** Menu yang dipesan. */
    private MenuItem menu;
    /** Banyak porsi yang dipesan. */
    private int jumlah;

    /**
     * Membuat item pesanan.
     *
     * @param menu   menu yang dipesan
     * @param jumlah banyak porsi yang dipesan
     */
    public ItemPesanan(MenuItem menu, int jumlah) {
        this.menu = menu;
        this.jumlah = jumlah;
    }

    /**
     * Menghitung subtotal item ini dengan memanggil hitungHarga milik menu.
     *
     * @return subtotal dalam rupiah
     */
    public double getSubtotal() {
        return menu.hitungHarga(jumlah);
    }

    /**
     * Mengambil menu yang dipesan.
     *
     * @return menu yang dipesan
     */
    public MenuItem getMenu() {
        return menu;
    }

    /**
     * Mengubah menu.
     *
     * @param menu menu yang baru
     */
    public void setMenu(MenuItem menu) {
        this.menu = menu;
    }

    /**
     * Mengambil banyak porsi yang dipesan.
     *
     * @return banyak porsi yang dipesan
     */
    public int getJumlah() {
        return jumlah;
    }

    /**
     * Mengubah banyak porsi.
     *
     * @param jumlah banyak porsi yang baru
     */
    public void setJumlah(int jumlah) {
        this.jumlah = jumlah;
    }
}
