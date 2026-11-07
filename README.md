# KasirResto

Aplikasi kasir restoran berbasis CLI (Command Line Interface) menggunakan Java,
dibuat untuk UAS Praktikum Pemrograman Berorientasi Objek (PBO) kelas C.

## Deskripsi Sistem

KasirResto membantu restoran mencatat pesanan dan pembayaran. Ada dua jenis akun:

- Admin: mengelola menu dan akun kasir, melihat riwayat transaksi, dan melihat laporan penjualan.
- Kasir (User): melihat daftar menu, membuat pesanan, memproses pembayaran, dan melihat riwayat transaksinya.

## Cara Menjalankan

Syarat: Java JDK 17 atau lebih baru. Jalankan dari folder utama repo.

Compile:

```
javac -d out src/*.java src/*/*.java
```

Jalankan:

```
java -cp out Main
```

Untuk saat ini beberapa fitur masih kerangka (belum ada isinya), jadi memilih
menu tertentu belum menampilkan apa-apa. Fitur diisi bertahap oleh anggota tim.

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
```

## Pembagian Tugas

Pekerjaan dibagi per pasangan. Pembagian di dalam pasangan diatur sendiri oleh kedua anggotanya.

| Pasangan | Anggota | Bagian | File yang dikerjakan |
|----------|---------|--------|----------------------|
| 1 | Muhammad Albharaka Putrosandy (250810701100022)<br>Farras Arthada Siregar (250810701100111) | Fitur Admin: kelola menu, kelola akun kasir, riwayat dan laporan | service/MenuService.java, service/AkunService.java, service/LaporanService.java |
| 2 | M. Dzaky Syraj (250810701100051)<br>Nabil Al Fijar (250810701100031) | Fitur Kasir: pesanan, pembayaran, struk, riwayat kasir | service/PesananService.java, service/TransaksiService.java, payment/*, util/StrukPrinter.java |
| 3 | Mirdha Aulia Zahra (2408107010115)<br>Putroe Zalfa (250810701100024) | Finalisasi: simpan ke file, perbaikan bug, Javadoc, PPT | service/FileManager.java dan seluruh kode |

## Aturan Kerja Tim

- Satu pasangan mengerjakan file miliknya sendiri. Jangan mengubah file pasangan lain tanpa bilang.
- Nama class, nama method, dan parameter pada kerangka jangan diubah. Isi hanya bagian yang bertanda TODO.
- Setiap pasangan bekerja di branch sendiri, lalu `git pull` sebelum `git push`.
- Pesan commit memakai awalan: `feat:`, `fix:`, `docs:`, atau `refactor:`.
