package model;

/**
 * Menu berjenis minuman. Turunan dari MenuItem.
 * Ukuran "Large" dikenai tambahan biaya per gelas.
 */
public class Minuman extends MenuItem {

    private static final double TAMBAHAN_LARGE = 2000;

    private String ukuran;

    /**
     * Membuat menu minuman.
     *
     * @param id     kode unik menu
     * @param nama   nama menu
     * @param harga  harga satuan dalam rupiah (ukuran Regular)
     * @param stok   jumlah stok
     * @param ukuran "Regular" atau "Large"
     */
    public Minuman(String id, String nama, double harga, int stok, String ukuran) {
        super(id, nama, harga, stok);
        this.ukuran = ukuran;
    }

    /**
     * Menghitung harga minuman. Ukuran Large ditambah biaya per gelas.
     *
     * @param jumlah banyak gelas
     * @return total harga
     */
    @Override
    public double hitungHarga(int jumlah) {
        double hargaPerGelas = getHarga();
        if ("Large".equalsIgnoreCase(ukuran)) {
            hargaPerGelas += TAMBAHAN_LARGE;
        }
        return hargaPerGelas * jumlah;
    }

    /**
     * Mengambil kategori menu.
     *
     * @return teks "Minuman"
     */
    @Override
    public String getKategori() {
        return "Minuman";
    }

    /** @return ukuran minuman: "Regular" atau "Large" */
    public String getUkuran() {
        return ukuran;
    }

    /** @param ukuran ukuran baru: "Regular" atau "Large" */
    public void setUkuran(String ukuran) {
        this.ukuran = ukuran;
    }
}
