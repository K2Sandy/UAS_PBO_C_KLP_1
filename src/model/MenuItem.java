package model;

/**
 * Class abstrak untuk semua item menu restoran (abstraction).
 * Makanan dan Minuman adalah turunannya (inheritance).
 */
public abstract class MenuItem {

    private String id;
    private String nama;
    private double harga;
    private int stok;

    /**
     * Membuat item menu.
     *
     * @param id    kode unik menu, contoh: "M01"
     * @param nama  nama menu
     * @param harga harga satuan dalam rupiah
     * @param stok  jumlah stok yang tersedia
     */
    public MenuItem(String id, String nama, double harga, int stok) {
        this.id = id;
        this.nama = nama;
        this.harga = harga;
        this.stok = stok;
    }

    /**
     * Menghitung harga untuk sejumlah porsi. Tiap jenis menu
     * boleh menghitung dengan caranya sendiri (polymorphism).
     *
     * @param jumlah banyak porsi yang dipesan
     * @return total harga untuk jumlah tersebut
     */
    public abstract double hitungHarga(int jumlah);

    /**
     * Mengambil kategori menu.
     *
     * @return teks kategori, contoh: "Makanan"
     */
    public abstract String getKategori();

    /** @return kode unik menu */
    public String getId() {
        return id;
    }

    /** @param id kode unik menu yang baru */
    public void setId(String id) {
        this.id = id;
    }

    /** @return nama menu */
    public String getNama() {
        return nama;
    }

    /** @param nama nama menu yang baru */
    public void setNama(String nama) {
        this.nama = nama;
    }

    /** @return harga satuan dalam rupiah */
    public double getHarga() {
        return harga;
    }

    /** @param harga harga satuan yang baru */
    public void setHarga(double harga) {
        this.harga = harga;
    }

    /** @return jumlah stok saat ini */
    public int getStok() {
        return stok;
    }

    /** @param stok jumlah stok yang baru */
    public void setStok(int stok) {
        this.stok = stok;
    }
}
