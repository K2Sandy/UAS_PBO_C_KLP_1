import model.Akun;
import service.AuthService;
import service.DataStore;
import service.FileManager;
import util.ConsoleHelper;
import util.LoginGagalException;

/**
 * Titik awal program KasirResto. Memuat data dari folder data/,
 * menampilkan layar login, lalu menjalankan menu sesuai peran akun
 * (Admin atau Kasir). Data disimpan otomatis saat program berhenti.
 */
public class Main {

    /** Class titik awal, tidak perlu dibuat objeknya. */
    private Main() {
    }

    /**
     * Menjalankan program.
     *
     * @param args argumen command line (tidak dipakai)
     */
    public static void main(String[] args) {
        DataStore.isiDataAwal();
        FileManager fileManager = new FileManager();
        fileManager.muatSemuaData();

        // Data disimpan setiap kali program berhenti, termasuk saat
        // ditutup paksa (Ctrl+C) atau input habis (Ctrl+D / Ctrl+Z),
        // supaya perubahan Admin tidak hilang.
        Runtime.getRuntime().addShutdownHook(new Thread(fileManager::simpanSemuaData));

        AuthService authService = new AuthService();
        boolean berjalan = true;

        while (berjalan) {
            ConsoleHelper.cetakJudul("KASIRRESTO");
            ConsoleHelper.cetak("1. Login");
            ConsoleHelper.cetak("0. Keluar");
            int pilihan = ConsoleHelper.bacaPilihan("Pilih: ", 0, 1);

            if (pilihan == 0) {
                berjalan = false;
            } else {
                String username = ConsoleHelper.bacaTeks("Username: ");
                String password = ConsoleHelper.bacaTeks("Password: ");
                try {
                    Akun akun = authService.login(username, password);
                    ConsoleHelper.cetak("Login berhasil. Selamat datang, "
                            + akun.getNamaLengkap() + " (" + akun.getPeran() + ")!");
                    akun.jalankanMenu();
                } catch (LoginGagalException e) {
                    ConsoleHelper.cetak(e.getMessage());
                }
            }
        }

        ConsoleHelper.cetak("Data disimpan. Terima kasih, program selesai.");
    }
}
