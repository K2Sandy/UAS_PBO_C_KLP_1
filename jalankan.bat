@echo off
REM Compile lalu jalankan KasirResto (Windows).
REM Pakai: klik dua kali file ini, atau ketik jalankan.bat di folder utama repo.
cd /d "%~dp0"
echo Meng-compile...
javac -encoding UTF-8 -d out -sourcepath src src\Main.java
if errorlevel 1 (
    echo Compile gagal. Periksa pesan error di atas.
    pause
    exit /b 1
)
java -cp out Main
pause
