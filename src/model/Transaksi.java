package model;

import java.time.LocalDateTime;

/**
 * Catatan transaksi yang sudah dibayar. Disimpan di riwayat
 * dan dipakai untuk struk serta laporan penjualan.
 */
public class Transaksi {

    private String idTransaksi;
    private LocalDateTime waktu;
    private Pesanan pesanan;
    private String namaKasir;
    private String metodePembayaran;
    private double subtotal;
    private double pajak;
    private double total;

    /**
     * Membuat transaksi kosong. Waktu otomatis diisi dengan waktu saat ini,
     * atribut lain diisi lewat setter.
     */
    public Transaksi() {
        this.waktu = LocalDateTime.now();
    }

    /** @return kode unik transaksi */
    public String getIdTransaksi() {
        return idTransaksi;
    }

    /** @param idTransaksi kode unik transaksi yang baru */
    public void setIdTransaksi(String idTransaksi) {
        this.idTransaksi = idTransaksi;
    }

    /** @return waktu transaksi dibuat */
    public LocalDateTime getWaktu() {
        return waktu;
    }

    /** @param waktu waktu transaksi yang baru */
    public void setWaktu(LocalDateTime waktu) {
        this.waktu = waktu;
    }

    /** @return pesanan yang dibayar */
    public Pesanan getPesanan() {
        return pesanan;
    }

    /** @param pesanan pesanan yang dibayar */
    public void setPesanan(Pesanan pesanan) {
        this.pesanan = pesanan;
    }

    /** @return nama kasir yang memproses transaksi */
    public String getNamaKasir() {
        return namaKasir;
    }

    /** @param namaKasir nama kasir yang memproses transaksi */
    public void setNamaKasir(String namaKasir) {
        this.namaKasir = namaKasir;
    }

    /** @return nama metode pembayaran, contoh: "Tunai" */
    public String getMetodePembayaran() {
        return metodePembayaran;
    }

    /** @param metodePembayaran nama metode pembayaran */
    public void setMetodePembayaran(String metodePembayaran) {
        this.metodePembayaran = metodePembayaran;
    }

    /** @return subtotal sebelum pajak */
    public double getSubtotal() {
        return subtotal;
    }

    /** @param subtotal subtotal sebelum pajak */
    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    /** @return jumlah pajak */
    public double getPajak() {
        return pajak;
    }

    /** @param pajak jumlah pajak */
    public void setPajak(double pajak) {
        this.pajak = pajak;
    }

    /** @return total yang dibayar (subtotal + pajak) */
    public double getTotal() {
        return total;
    }

    /** @param total total yang dibayar */
    public void setTotal(double total) {
        this.total = total;
    }
}
