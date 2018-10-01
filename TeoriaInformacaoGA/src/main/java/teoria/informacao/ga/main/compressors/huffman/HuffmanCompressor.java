package teoria.informacao.ga.main.compressors.huffman;

import nayuki.huffmancoding.BitOutputStream;
import nayuki.huffmancoding.CanonicalCode;
import nayuki.huffmancoding.CodeTree;
import nayuki.huffmancoding.FrequencyTable;
import nayuki.huffmancoding.HuffmanCompress;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class HuffmanCompressor {

    public byte[] compress(byte[] data) throws IOException {
        FrequencyTable freqs = new FrequencyTable(new int[257]);
        for (byte x : data) {
            freqs.increment(x & 0xFF);
        }
        freqs.increment(256);  // EOF symbol gets a frequency of 1

        CodeTree code = freqs.buildCodeTree();
        CanonicalCode canonCode = new CanonicalCode(code, 257);
        code = canonCode.toCodeTree();

        InputStream in = new ByteArrayInputStream(data);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        BitOutputStream bitOut = new BitOutputStream(out);

        writeCode(bitOut, canonCode);
        HuffmanCompress.compress(code, in, bitOut);

        bitOut.close();
        return out.toByteArray();
    }

    static void writeCode(BitOutputStream out, CanonicalCode canonCode) throws IOException {
        for (int i = 0; i < canonCode.getSymbolLimit(); ++i) {
            int val = canonCode.getCodeLength(i);
            if (val >= 256) {
                throw new RuntimeException("The code for a symbol is too long");
            }

            for (int j = 7; j >= 0; --j) {
                out.write(val >>> j & 1);
            }
        }

    }

}
