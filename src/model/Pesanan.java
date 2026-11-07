package model;

import java.util.ArrayList;

/**
 * Satu pesanan pelanggan di sebuah meja, berisi beberapa ItemPesanan.
 */
public class Pesanan {

    private String idPesanan;
    private int nomorMeja;
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

    /** @return kode unik pesanan */
    public String getIdPesanan() {
        return idPesanan;
    }

    /** @param idPesanan kode unik pesanan yang baru */
    public void setIdPesanan(String idPesanan) {
        this.idPesanan = idPesanan;
    }

    /** @return nomor meja pelanggan */
    public int getNomorMeja() {
        return nomorMeja;
    }

    /** @param nomorMeja nomor meja yang baru */
    public void setNomorMeja(int nomorMeja) {
        this.nomorMeja = nomorMeja;
    }

    /** @return daftar item dalam pesanan */
    public ArrayList<ItemPesanan> getDaftarItem() {
        return daftarItem;
    }

    /** @param daftarItem daftar item yang baru */
    public void setDaftarItem(ArrayList<ItemPesanan> daftarItem) {
        this.daftarItem = daftarItem;
    }
}
