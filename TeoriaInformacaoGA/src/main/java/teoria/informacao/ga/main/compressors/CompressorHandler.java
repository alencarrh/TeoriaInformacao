package teoria.informacao.ga.main.compressors;

import lombok.Builder;
import lombok.Getter;
import lombok.Singular;
import teoria.informacao.ga.main.interfaces.Compressor;
import teoria.informacao.ga.main.utils.Utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Getter
@Builder
public class CompressorHandler {

    @Singular
    private final List<Compressor> compressors;
    private final boolean log;
    private final boolean showCompressInfo;


    public void run(String mode, String filename) throws IOException {
        switch (mode) {
            case "-c":
                compress(filename);
                return;
            case "-d":
                decompress(filename);
                return;
            default:
                System.out.println("Invalid MODE");
                Utils.showHelp();
        }
    }

    private List<Compressor> getCompressorsReversed() {
        List<Compressor> compressorsReversed = new ArrayList<>(compressors);
        Collections.reverse(compressorsReversed);
        return compressorsReversed;
    }

    private void compress(String filename) throws IOException {
        log("Compressing file: " + filename);
        byte[] data = Utils.readFile(filename);

        int initialSize = data.length;
        int previousSize = initialSize;
        int currentSize;
        logCompressInfo("Original file size: " + initialSize);
        for (Compressor compressor : getCompressors()) {
            data = compressor.compress(data);
            currentSize = data.length;

            logCompressInfo("Compression step executed:", compressor.name());
            logCompressInfo("\tnew size:", currentSize);
            logCompressInfo("\tcompression rate (to previous step):", calculateRate(currentSize, previousSize) + "%");
            logCompressInfo("\tcompression rate (to original file):", calculateRate(currentSize, initialSize) + "%");

            previousSize = currentSize;
        }
        logCompressInfo("  Original size:", initialSize);
        logCompressInfo("Compressed size:", data.length, "(", "-" + calculateRate(data.length, initialSize) + "% )");

        String resultFile = filename + ".cmpt";
        log("File compressed:", resultFile);
        Utils.saveFile(resultFile, data);
    }

    private void decompress(String filename) throws IOException {
        log("Decompressing file:", filename);
        byte[] data = Utils.readFile(filename);
        for (Compressor compressor : getCompressorsReversed()) {
            data = compressor.decompress(data);
        }
        String resultFile = filename.replace(".cmpt", "");
        log("File decompressed:", resultFile);
        Utils.saveFile(resultFile, data);
    }

    private void logCompressInfo(Object... messages) {
        if (showCompressInfo) {
            log(messages);
        }
    }

    private void log(Object... messages) {
        if (log) {
            for (Object message : messages) {
                System.out.print(message);
                System.out.print(" ");
            }
            System.out.println();
        }
    }

    private float calculateRate(float newSize, float previousSize) {
        return 100 - ((newSize / previousSize) * 100);
    }


}
