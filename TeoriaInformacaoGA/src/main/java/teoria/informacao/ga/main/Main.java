package teoria.informacao.ga.main;


import nayuki.huffmancoding.BitOutputStream;
import nayuki.huffmancoding.CanonicalCode;
import nayuki.huffmancoding.CodeTree;
import nayuki.huffmancoding.FrequencyTable;
import nayuki.huffmancoding.HuffmanCompress;
import org.apache.commons.io.FileUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

public class Main {


    public static void main(String[] args) throws IOException {

        String inputFile = "tradutores";
        byte[] input = Files.readAllBytes(new File(inputFile + ".txt").toPath());
        byte[] result = compress(input);

        FileUtils.writeByteArrayToFile(new File(inputFile + ".cmpt"), result);

        System.out.println(input.length);
        System.out.println(result.length);
        print(input);
        print(result);
    }

    private static void print(byte[] arr) {
        for (byte b : arr) {
            System.out.print(String.format("%8s", Integer.toBinaryString(b)).replace(" ", "0"));
        }
        System.out.println();
    }

    private static byte[] compress(byte[] b) throws IOException {

        FrequencyTable freqs = new FrequencyTable(new int[257]);
        for (byte x : b)
            freqs.increment(x & 0xFF);
        freqs.increment(256);  // EOF symbol gets a frequency of 1
        CodeTree code = freqs.buildCodeTree();
        CanonicalCode canonCode = new CanonicalCode(code, 257);
        code = canonCode.toCodeTree();

        InputStream in = new ByteArrayInputStream(b);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        BitOutputStream bitOut = new BitOutputStream(out);

//        HuffmanCompress.writeCodeLengthTable(bitOut, canonCode);
        HuffmanCompress.compress(code, in, bitOut);
        bitOut.close();
        return out.toByteArray();
    }

}
