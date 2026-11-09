package service;

import java.util.Map;
import model.Akun;
import model.Kasir;
import util.ConsoleHelper;

/**
 * Layanan pengelolaan akun kasir. Hanya dipakai oleh Admin.
 * Semua data akun disimpan di DataStore.daftarAkun.
 */
public class AkunService {

    /**
     * Menampilkan submenu kelola akun kasir:
     * melihat daftar kasir, menambah kasir, dan menghapus kasir.
     */
    public void kelolaAkunKasir() {
        boolean berjalan = true;

