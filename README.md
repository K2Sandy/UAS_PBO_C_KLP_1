# KasirResto

Aplikasi kasir restoran berbasis CLI (Command Line Interface) menggunakan Java,
dibuat untuk UAS Praktikum Pemrograman Berorientasi Objek (PBO) kelas C.

## Deskripsi Sistem

KasirResto membantu restoran mencatat pesanan dan pembayaran. Ada dua jenis akun:

- Admin: mengelola menu dan akun kasir, melihat riwayat transaksi, dan melihat laporan penjualan.
- Kasir (User): melihat daftar menu, membuat pesanan, memproses pembayaran, dan melihat riwayat transaksinya.

## Fitur

| Admin | Kasir |
|-------|-------|
| Lihat, tambah, edit (termasuk ukuran/pedas), dan hapus menu | Lihat daftar menu (harga Large sudah dihitung) |
| Kelola akun kasir: tambah, hapus, ganti password | Buat pesanan per nomor meja; stok dicek, menu yang sama digabung |
| Riwayat semua transaksi dan rincian struk tiap transaksi | Bayar dengan Tunai, QRIS, atau Debit |
| Laporan harian: per metode bayar, per kasir, menu terlaris, stok menipis | Cetak struk, lihat riwayat sendiri, dan cetak ulang struk |

Konsep PBO yang dipakai:

- Abstraction: class abstrak `Akun`, `MenuItem`, dan interface `Pembayaran`
- Inheritance: `Admin` dan `Kasir` turunan `Akun`; `Makanan` dan `Minuman` turunan `MenuItem`
- Polymorphism: `jalankanMenu()`, `hitungHarga()`, dan `bayar()` punya isi berbeda di tiap turunan
- Encapsulation: semua atribut `private` dengan getter dan setter
- Collection: `ArrayList` dan `HashMap` di `DataStore`
- Exception kustom: `LoginGagalException`, `InputTidakValidException`, `StokHabisException`

## Cara Menjalankan

Syarat: Java JDK 17 atau lebih baru. Jalankan dari folder utama repo
(folder yang berisi `src/`).

Cara cepat:

- Windows: klik dua kali `jalankan.bat`, atau ketik `jalankan.bat`
- Linux, macOS, atau Git Bash: `sh jalankan.sh`

Cara manual (berlaku di semua sistem operasi):

```
javac -encoding UTF-8 -d out -sourcepath src src/Main.java
java -cp out Main
```

## Penyimpanan Data

Program menyimpan data ke folder `data/` di folder tempat program dijalankan:

| File | Isi |
|------|-----|
| `data/menu.txt` | daftar menu beserta stok |
| `data/akun.txt` | akun Admin dan Kasir |
| `data/transaksi.txt` | riwayat transaksi beserta item pesanannya |

- Data dimuat saat program dibuka dan disimpan saat program berhenti,
  termasuk saat ditutup paksa (Ctrl+C) atau input habis (Ctrl+D).
- Setiap pembayaran yang berhasil langsung disimpan.
- Hapus folder `data/` untuk kembali ke data awal.
- Folder `data/` tidak ikut di-commit (ada di `.gitignore`).

## Akun untuk Uji Coba

| Peran | Username | Password |
|-------|----------|----------|
| Admin | admin | admin123 |
| Kasir | kasir | kasir123 |

## Struktur Folder

```
src/
├─ model/     Akun, Admin, Kasir, MenuItem, Makanan, Minuman,
│             Pesanan, ItemPesanan, Transaksi
├─ service/   DataStore, AuthService, AkunService, MenuService,
│             PesananService, TransaksiService, LaporanService, FileManager
├─ payment/   Pembayaran (interface), Tunai, Qris, Debit
├─ util/      ConsoleHelper, StrukPrinter, dan exception kustom
└─ Main.java
data/         dibuat otomatis saat program dijalankan (tidak di-commit)
jalankan.bat  skrip compile dan jalankan untuk Windows
jalankan.sh   skrip compile dan jalankan untuk Linux/macOS
```

## Skenario Uji

Langkah singkat untuk memastikan semua bagian tersambung:

1. Login `kasir` / `kasir123`, pilih 2, isi meja 1, pesan `M01` x2 dan `N02` x1,
   isi `0`, bayar Tunai Rp70.000. Struk dan kembalian tampil.
2. Pilih 3: transaksi tadi muncul di riwayat kasir. Logout.
3. Login `admin` / `admin123`, pilih 1: stok M01 berkurang 2.
4. Pilih 6 dan 7: transaksi muncul di riwayat dan laporan hari ini.
5. Keluar, jalankan lagi, ulangi langkah 4: data masih ada.

## Pembagian Tugas

Pekerjaan dibagi per pasangan. Pembagian di dalam pasangan diatur sendiri oleh kedua anggotanya.

| Pasangan | Anggota | Bagian | File yang dikerjakan |
|----------|---------|--------|----------------------|
| 1 | Muhammad Albharaka Putrosandy (250810701100022)<br>Farras Arthada Siregar (250810701100111) | Fitur Admin: kelola menu, kelola akun kasir, riwayat dan laporan | service/MenuService.java, service/AkunService.java, service/LaporanService.java |
| 2 | M. Dzaky Syraj (250810701100051)<br>Nabil Al Fijar (250810701100031) | Fitur Kasir: pesanan, pembayaran, struk, riwayat kasir | service/PesananService.java, service/TransaksiService.java, payment/*, util/StrukPrinter.java |
| 3 | Mirdha Aulia Zahra (2408107010115)<br>Putroe Zalfa (250810701100024) | Finalisasi: simpan ke file, perbaikan bug, Javadoc, PPT | service/FileManager.java dan seluruh kode |

## Aturan Kerja Tim

- Satu pasangan mengerjakan file miliknya sendiri. Jangan mengubah file pasangan lain tanpa bilang.
- Nama class, nama method, dan parameter pada kerangka jangan diubah.
- Selalu pakai `ConsoleHelper` untuk input dan tampilan (jangan membuat `Scanner` baru).
- Setiap pasangan bekerja di branch sendiri, lalu `git pull` sebelum `git push`.
- Pesan commit memakai awalan: `feat:`, `fix:`, `docs:`, atau `refactor:`.

## Lisensi

Proyek ini dirilis di bawah [MIT License](LICENSE).
