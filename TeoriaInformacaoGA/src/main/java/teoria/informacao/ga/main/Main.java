package teoria.informacao.ga.main;

import teoria.informacao.ga.main.compressors.CompressorHandler;
import teoria.informacao.ga.main.compressors.huffman.Huffman;
import teoria.informacao.ga.main.compressors.lzw.Lzw;
import teoria.informacao.ga.main.utils.Utils;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {

        if (args.length == 1 && (args[0].equals("-h") || args[0].equals("--help"))) {
            Utils.showHelp();
            return;
        }

        if (args.length < 2) {
            System.out.println("\nMODE and FILENAME required!\n");
            Utils.showHelp();
        }

        String mode = args[0];
        String inputFile = args[1];
        boolean log = false;
        boolean showCompressInfo = false;

        if (args.length > 2) {
            log = hasParam(args, "-log");
            showCompressInfo = hasParam(args, "-sci") || hasParam(args, "--show-compress-info");
        }

        CompressorHandler compressor = CompressorHandler.builder()
                .compressor(new Lzw())
                .compressor(new Huffman())
                .log(log)
                .showCompressInfo(showCompressInfo)
                .build();

        compressor.run(mode, inputFile);


    }

    private static boolean hasParam(String[] args, String s) {
        for (int i = 2; i < args.length; i++) {
            if (args[i].equals(s)) {
                return true;
            }
        }
        return false;
    }

}
