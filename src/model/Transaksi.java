package model;

import java.time.LocalDateTime;

/**
 * Catatan transaksi yang sudah dibayar. Disimpan di riwayat
 * dan dipakai untuk struk serta laporan penjualan.
 */
public class Transaksi {

    /** Kode unik transaksi, contoh: "T001". */
    private String idTransaksi;
    /** Waktu transaksi dibuat. */
    private LocalDateTime waktu;
    /** Pesanan yang dibayar. */
    private Pesanan pesanan;
    /** Nama lengkap kasir yang memproses transaksi. */
    private String namaKasir;
    /** Nama metode pembayaran. */
    private String metodePembayaran;
    /** Total harga item sebelum pajak. */
    private double subtotal;
    /** Besar pajak. */
    private double pajak;
    /** Total yang dibayar (subtotal + pajak). */
    private double total;

    /**
     * Membuat transaksi kosong. Waktu otomatis diisi dengan waktu saat ini,
     * atribut lain diisi lewat setter.
     */
    public Transaksi() {
        this.waktu = LocalDateTime.now();
    }

    /**
     * Mengambil kode unik transaksi.
     *
     * @return kode unik transaksi
     */
    public String getIdTransaksi() {
        return idTransaksi;
    }

    /**
     * Mengubah kode unik transaksi.
     *
     * @param idTransaksi kode unik transaksi yang baru
     */
    public void setIdTransaksi(String idTransaksi) {
        this.idTransaksi = idTransaksi;
    }

    /**
     * Mengambil waktu transaksi dibuat.
     *
     * @return waktu transaksi dibuat
     */
    public LocalDateTime getWaktu() {
        return waktu;
    }

    /**
     * Mengubah waktu transaksi.
     *
     * @param waktu waktu transaksi yang baru
     */
    public void setWaktu(LocalDateTime waktu) {
        this.waktu = waktu;
    }

    /**
     * Mengambil pesanan yang dibayar.
     *
     * @return pesanan yang dibayar
     */
    public Pesanan getPesanan() {
        return pesanan;
    }

    /**
     * Mengubah pesanan yang dibayar.
     *
     * @param pesanan pesanan yang dibayar
     */
    public void setPesanan(Pesanan pesanan) {
        this.pesanan = pesanan;
    }

    /**
     * Mengambil nama kasir yang memproses transaksi.
     *
     * @return nama kasir yang memproses transaksi
     */
    public String getNamaKasir() {
        return namaKasir;
    }

    /**
     * Mengubah nama kasir yang memproses transaksi.
     *
     * @param namaKasir nama kasir yang memproses transaksi
     */
    public void setNamaKasir(String namaKasir) {
        this.namaKasir = namaKasir;
    }

    /**
     * Mengambil nama metode pembayaran.
     *
     * @return nama metode pembayaran, contoh: "Tunai"
     */
    public String getMetodePembayaran() {
        return metodePembayaran;
    }

    /**
     * Mengubah nama metode pembayaran.
     *
     * @param metodePembayaran nama metode pembayaran
     */
    public void setMetodePembayaran(String metodePembayaran) {
        this.metodePembayaran = metodePembayaran;
    }

    /**
     * Mengambil subtotal sebelum pajak.
     *
     * @return subtotal sebelum pajak
     */
    public double getSubtotal() {
        return subtotal;
    }

    /**
     * Mengubah subtotal sebelum pajak.
     *
     * @param subtotal subtotal sebelum pajak
     */
    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    /**
     * Mengambil jumlah pajak.
     *
     * @return jumlah pajak
     */
    public double getPajak() {
        return pajak;
    }

    /**
     * Mengubah jumlah pajak.
     *
     * @param pajak jumlah pajak
     */
    public void setPajak(double pajak) {
        this.pajak = pajak;
    }

    /**
     * Mengambil total yang dibayar (subtotal + pajak).
     *
     * @return total yang dibayar (subtotal + pajak)
     */
    public double getTotal() {
        return total;
    }

    /**
     * Mengubah total yang dibayar.
     *
     * @param total total yang dibayar
     */
    public void setTotal(double total) {
        this.total = total;
    }
}
