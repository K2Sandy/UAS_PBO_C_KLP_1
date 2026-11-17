#!/bin/sh
# Compile lalu jalankan KasirResto (Linux / macOS / Git Bash).
# Pakai: sh jalankan.sh   (jalankan dari folder utama repo)
cd "$(dirname "$0")" || exit 1
echo "Meng-compile..."
javac -encoding UTF-8 -d out -sourcepath src src/Main.java || {
    echo "Compile gagal. Periksa pesan error di atas."
    exit 1
}
java -cp out Main
