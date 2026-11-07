package model;

/**
 * Menu berjenis makanan. Turunan dari MenuItem.
 */
public class Makanan extends MenuItem {

    private boolean pedas;

    /**
     * Membuat menu makanan.
     *
     * @param id    kode unik menu
     * @param nama  nama menu
     * @param harga harga satuan dalam rupiah
     * @param stok  jumlah stok
     * @param pedas true jika makanan ini pedas
     */
    public Makanan(String id, String nama, double harga, int stok, boolean pedas) {
        super(id, nama, harga, stok);
        this.pedas = pedas;
    }

    /**
     * Menghitung harga makanan: harga satuan dikali jumlah porsi.
     *
     * @param jumlah banyak porsi
     * @return total harga
     */
    @Override
    public double hitungHarga(int jumlah) {
        return getHarga() * jumlah;
    }

    /**
     * Mengambil kategori menu.
     *
     * @return teks "Makanan"
     */
    @Override
    public String getKategori() {
        return "Makanan";
    }

    /** @return true jika makanan ini pedas */
    public boolean isPedas() {
        return pedas;
    }

    /** @param pedas true jika makanan ini pedas */
    public void setPedas(boolean pedas) {
        this.pedas = pedas;
    }
}
