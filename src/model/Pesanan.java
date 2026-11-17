package model;

import java.util.ArrayList;

/**
 * Satu pesanan pelanggan di sebuah meja, berisi beberapa ItemPesanan.
 */
public class Pesanan {

    /** Kode unik pesanan, contoh: "P001". */
    private String idPesanan;
    /** Nomor meja pelanggan. */
    private int nomorMeja;
    /** Daftar item yang dipesan. */
    private ArrayList<ItemPesanan> daftarItem;

    /**
     * Membuat pesanan baru yang masih kosong.
     *
     * @param idPesanan kode unik pesanan, contoh: "P001"
     * @param nomorMeja nomor meja pelanggan
     */
    public Pesanan(String idPesanan, int nomorMeja) {
        this.idPesanan = idPesanan;
        this.nomorMeja = nomorMeja;
        this.daftarItem = new ArrayList<>();
    }

    /**
     * Menambahkan satu item ke pesanan.
     *
     * @param item item pesanan yang ditambahkan
     */
    public void tambahItem(ItemPesanan item) {
        daftarItem.add(item);
    }

    /**
     * Menjumlahkan subtotal semua item (belum termasuk pajak).
     *
     * @return subtotal pesanan dalam rupiah
     */
    public double hitungSubtotal() {
        double total = 0;
        for (ItemPesanan item : daftarItem) {
            total += item.getSubtotal();
        }
        return total;
    }

    /**
     * Mengambil kode unik pesanan.
     *
     * @return kode unik pesanan
     */
    public String getIdPesanan() {
        return idPesanan;
    }

    /**
     * Mengubah kode unik pesanan.
     *
     * @param idPesanan kode unik pesanan yang baru
     */
    public void setIdPesanan(String idPesanan) {
        this.idPesanan = idPesanan;
    }

    /**
     * Mengambil nomor meja pelanggan.
     *
     * @return nomor meja pelanggan
     */
    public int getNomorMeja() {
        return nomorMeja;
    }

    /**
     * Mengubah nomor meja.
     *
     * @param nomorMeja nomor meja yang baru
     */
    public void setNomorMeja(int nomorMeja) {
        this.nomorMeja = nomorMeja;
    }

    /**
     * Mengambil daftar item dalam pesanan.
     *
     * @return daftar item dalam pesanan
     */
    public ArrayList<ItemPesanan> getDaftarItem() {
        return daftarItem;
    }

    /**
     * Mengubah daftar item.
     *
     * @param daftarItem daftar item yang baru
     */
    public void setDaftarItem(ArrayList<ItemPesanan> daftarItem) {
        this.daftarItem = daftarItem;
    }
}
