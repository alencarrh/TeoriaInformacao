package teoria.informacao.ga.main.utils;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public final class Utils {


    public static byte[] readFile(String filename) throws IOException {
        return Files.readAllBytes(new File(filename).toPath());
    }

    public static void saveFile(String filename, byte[] data) throws IOException {
        FileUtils.writeByteArrayToFile(new File(filename), data);
    }

    public static void showHelp() {
        System.out.println("params: MODE filename [-log] [(-sci | --show-compress-info)] [(-h | --help)]");
        System.out.println("\tMODE: -c or -d");
        System.out.println("\t -c: to compress");
        System.out.println("\t -d: to decompress");

    }
}
