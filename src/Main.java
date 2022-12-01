import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {
    private static final File savegames = new File("C:\\Games\\savegames");

    public static void main(String[] args) {
        GameProgress player1 = new GameProgress(98, 12, 56, 36);
        GameProgress player2 = new GameProgress(88, 15, 48, 56);
        GameProgress player3 = new GameProgress(91, 14, 56, 42);

        savegame(player1, "save1");
        savegame(player2, "save2");
        savegame(player3, "save3");

        zipFiles(savegames.getPath() + "/save1.dat", "saveZip1");
        zipFiles(savegames.getPath() + "/save2.dat", "saveZip2");
        zipFiles(savegames.getPath() + "/save3.dat", "saveZip3");

        deleteFiles(savegames.getPath() + "/save1.dat");
        deleteFiles(savegames.getPath() + "/save2.dat");
        deleteFiles(savegames.getPath() + "/save3.dat");
    }

    public static void savegame(GameProgress gameProgress, String fileName) {
        try (
                FileOutputStream fos = new FileOutputStream(savegames.toPath() + "/" + fileName + ".dat");
                ObjectOutputStream oos = new ObjectOutputStream(fos)
        ) {
            oos.writeObject(gameProgress);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void zipFiles(String path, String filename) {
        try (ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(savegames.toPath() + "/" + filename + ".zip"));
             FileInputStream fis = new FileInputStream(path)) {
            ZipEntry entry = new ZipEntry("zipEntry");
            zout.putNextEntry(entry);
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            zout.write(buffer);
            zout.closeEntry();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void deleteFiles(String path) {
        new File(path).delete();
    }
}

