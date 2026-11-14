
package service;

import model.*;
import util.*;
import java.util.Scanner;

public class MenuService {

    private Scanner input = new Scanner(System.in);

    /**
     * Menampilkan semua menu restoran.
     */
    public void tampilkanSemuaMenu() {
        if (DataStore.daftarMenu.isEmpty()) {
            System.out.println("Belum ada menu.");
            return;
        }

        System.out.println("\n========== DAFTAR MENU ==========");
        System.out.printf("%-5s %-20s %-12s %-15s %-8s%n",
                "ID", "Nama", "Kategori", "Harga", "Stok");

        for (MenuItem menu : DataStore.daftarMenu) {
            System.out.printf("%-5s %-20s %-12s %-15s %-8d%n",
                    menu.getId(),
                    menu.getNama(),
                    menu.getKategori(),
                    ConsoleHelper.formatRupiah(menu.getHarga()),
                    menu.getStok());
        }
    }

    /**
     * Menambahkan menu makanan atau minuman.
     */
    public void tambahMenu() {
        try {
            System.out.println("\n===== TAMBAH MENU =====");
            System.out.println("1. Makanan");
            System.out.println("2. Minuman");
            System.out.print("Pilih jenis menu: ");
            int jenis = Integer.parseInt(input.nextLine());

            System.out.print("Masukkan ID: ");
            String id = input.nextLine();

            for (MenuItem menu : DataStore.daftarMenu) {
                if (menu.getId().equalsIgnoreCase(id)) {
                    throw new InputTidakValidException("ID menu sudah digunakan!");
                }
            }

            System.out.print("Masukkan nama: ");
            String nama = input.nextLine();

            System.out.print("Masukkan harga: ");
            double harga = Double.parseDouble(input.nextLine());

            System.out.print("Masukkan stok: ");
            int stok = Integer.parseInt(input.nextLine());

            if (harga <= 0 || stok < 0 || nama.trim().isEmpty()) {
                throw new InputTidakValidException("Data menu tidak valid!");
            }

            MenuItem menu;

            if (jenis == 1) {
                menu = new Makanan(id, nama, harga, stok);
            } else if (jenis == 2) {
                menu = new Minuman(id, nama, harga, stok);
            } else {
                throw new InputTidakValidException("Pilihan jenis tidak valid!");
            }

            DataStore.daftarMenu.add(menu);
            System.out.println("Menu berhasil ditambahkan!");

        } catch (InputTidakValidException e) {
            System.out.println("Gagal: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Harga, stok, atau pilihan harus berupa angka!");
        }
    }

    /**
     * Mengubah data menu.
     */
    public void editMenu() {
        tampilkanSemuaMenu();

        System.out.print("\nMasukkan ID menu yang ingin diedit: ");
        String id = input.nextLine();

        for (MenuItem menu : DataStore.daftarMenu) {
            if (menu.getId().equalsIgnoreCase(id)) {
                System.out.println("1. Ubah nama");
                System.out.println("2. Ubah harga");
                System.out.println("3. Ubah stok");
                System.out.print("Pilih data yang ingin diubah: ");

                try {
                    int pilihan = Integer.parseInt(input.nextLine());

                    switch (pilihan) {
                        case 1:
                            System.out.print("Nama baru: ");
                            menu.setNama(input.nextLine());
                            break;

                        case 2:
                            System.out.print("Harga baru: ");
                            double harga = Double.parseDouble(input.nextLine());
                            if (harga <= 0) {
                                System.out.println("Harga harus lebih dari 0.");
                                return;
                            }
                            menu.setHarga(harga);
                            break;

                        case 3:
                            System.out.print("Stok baru: ");
                            int stok = Integer.parseInt(input.nextLine());
                            if (stok < 0) {
                                System.out.println("Stok tidak boleh negatif.");
                                return;
                            }
                            menu.setStok(stok);
                            break;

                        default:
                            System.out.println("Pilihan tidak valid.");
                            return;
                    }

                    System.out.println("Menu berhasil diperbarui!");
                } catch (NumberFormatException e) {
                    System.out.println("Input harus berupa angka!");
                }
                return;
            }
        }

        System.out.println("Menu dengan ID tersebut tidak ditemukan.");
    }

    /**
     * Menghapus menu berdasarkan ID.
     */
    public void hapusMenu() {
        tampilkanSemuaMenu();

        System.out.print("\nMasukkan ID menu yang ingin dihapus: ");
        String id = input.nextLine();

        for (MenuItem menu : DataStore.daftarMenu) {
            if (menu.getId().equalsIgnoreCase(id)) {
                System.out.print("Yakin ingin menghapus menu ini? (y/n): ");
                String konfirmasi = input.nextLine();

                if (konfirmasi.equalsIgnoreCase("y")) {
                    DataStore.daftarMenu.remove(menu);
                    System.out.println("Menu berhasil dihapus!");
                } else {
                    System.out.println("Penghapusan dibatalkan.");
                }
                return;
            }
        }

        System.out.println("Menu dengan ID tersebut tidak ditemukan.");
    }
}
