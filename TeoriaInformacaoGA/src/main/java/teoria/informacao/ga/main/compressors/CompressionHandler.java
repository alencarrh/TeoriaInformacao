package teoria.informacao.ga.main.compressors;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import lombok.Builder;
import lombok.Getter;
import lombok.Singular;
import teoria.informacao.ga.main.interfaces.Compressor;
import teoria.informacao.ga.main.utils.Utils;

@Getter
@Builder
public class CompressionHandler {

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
        long startTime, stopTime, allTime = 0;
        logCompressInfo("Original file size: " + initialSize);
        for (Compressor compressor : getCompressors()) {
            startTime = System.nanoTime();

            data = compressor.compress(data);

            stopTime = System.nanoTime();

            currentSize = data.length;
            logCompressStep(initialSize, previousSize, currentSize, startTime, stopTime, compressor);
            allTime += time(startTime, stopTime);
            previousSize = currentSize;
        }

        logCompressFinalStatus(data, initialSize, allTime);

        String resultFile = filename + ".cmpt";
        log("File compressed:", resultFile);
        Utils.saveFile(resultFile, data);
    }

    private void logCompressFinalStatus(final byte[] data, final int initialSize, final long allTime) {
        logCompressInfo("\nFinal status:");
        logCompressInfo("\tExecution time:", allTime + "ms");
        logCompressInfo("\tOriginal size:", initialSize);
        logCompressInfo("\tCompressed size:", data.length);
        logCompressInfo("\tSpace saving:", spaceSaving(data.length, initialSize) + "%");
        logCompressInfo("\tCompression ratio:", compressionRatio(data.length, initialSize));
    }

    private void logCompressStep(final int initialSize, final int previousSize, final int currentSize,
        final long startTime, final long stopTime, final Compressor compressor) {
        logCompressInfo("Compression step executed:", compressor.name());
        logCompressInfo("\tnew size:", currentSize);
        logCompressInfo("\texecution time:", time(startTime, stopTime) + "ms");
        logCompressInfo("\tspace saving:", spaceSaving(currentSize, previousSize) + "%");
        logCompressInfo("\tcompression ratio (to previous step):", compressionRatio(currentSize, previousSize));
        logCompressInfo("\tcompression ratio (to original file):", compressionRatio(currentSize, initialSize));
    }

    private void decompress(String filename) throws IOException {
        log("Decompressing file:", filename);
        byte[] data = Utils.readFile(filename);
        long startTime, stopTime, allTime = 0;
        for (Compressor compressor : getCompressorsReversed()) {
            startTime = System.nanoTime();

            data = compressor.decompress(data);

            stopTime = System.nanoTime();
            logCompressInfo("Decompression step executed:", compressor.name(), "-", time(startTime, stopTime) + "ms");
            allTime += time(startTime, stopTime);
        }
        String resultFile = filename.replace(".cmpt", "");
        log("File decompressed:", resultFile, "-", allTime + "ms");
        Utils.saveFile(resultFile, data);
    }


    private float spaceSaving(final float newSize, final float previousSize) {
        return 100 - ((newSize / previousSize) * 100);
    }


    private float compressionRatio(float newSize, float previousSize) {
        return previousSize / newSize;
    }

    private long time(final long startTime, final long stopTime) {
        return TimeUnit.MILLISECONDS.convert(stopTime - startTime, TimeUnit.NANOSECONDS);
    }


    private void logCompressInfo(final Object... messages) {
        if (showCompressInfo) {
            log(messages);
        }
    }

    private void log(final Object... messages) {
        if (log) {
            for (Object message : messages) {
                System.out.print(message);
                System.out.print(" ");
            }
            System.out.println();
        }
    }


}
