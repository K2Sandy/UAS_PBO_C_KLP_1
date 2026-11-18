package model;

/**
 * Class abstrak untuk semua item menu restoran (abstraction).
 * Makanan dan Minuman adalah turunannya (inheritance).
 */
public abstract class MenuItem {

    /** Kode unik menu, contoh: "M01". */
    private String id;
    /** Nama menu. */
    private String nama;
    /** Harga satuan dalam rupiah. */
    private double harga;
    /** Jumlah stok yang tersedia. */
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

    /**
     * Membuat salinan menu ini dengan id, nama, harga, dan keterangan yang
     * sama (stok salinan 0). Dipakai untuk menyimpan isi transaksi supaya
     * riwayat tidak ikut berubah saat Admin mengedit menu (polymorphism:
     * Makanan menyalin sebagai Makanan, Minuman sebagai Minuman).
     *
     * @return objek menu baru yang terpisah dari menu aslinya
     */
    public abstract MenuItem salin();

    /**
     * Mengambil keterangan singkat khas jenis menu untuk ditampilkan
     * di daftar menu, contoh: "Pedas" untuk makanan atau "Large" untuk
     * minuman (polymorphism).
     *
     * @return teks keterangan
     */
    public abstract String getKeterangan();

    /**
     * Mengambil kode unik menu.
     *
     * @return kode unik menu
     */
    public String getId() {
        return id;
    }

    /**
     * Mengubah kode unik menu.
     *
     * @param id kode unik menu yang baru
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Mengambil nama menu.
     *
     * @return nama menu
     */
    public String getNama() {
        return nama;
    }

    /**
     * Mengubah nama menu.
     *
     * @param nama nama menu yang baru
     */
    public void setNama(String nama) {
        this.nama = nama;
    }

    /**
     * Mengambil harga satuan dalam rupiah.
     *
     * @return harga satuan dalam rupiah
     */
    public double getHarga() {
        return harga;
    }

    /**
     * Mengubah harga satuan.
     *
     * @param harga harga satuan yang baru
     */
    public void setHarga(double harga) {
        this.harga = harga;
    }

    /**
     * Mengambil jumlah stok saat ini.
     *
     * @return jumlah stok saat ini
     */
    public int getStok() {
        return stok;
    }

    /**
     * Mengubah jumlah stok.
     *
     * @param stok jumlah stok yang baru
     */
    public void setStok(int stok) {
        this.stok = stok;
    }
}
