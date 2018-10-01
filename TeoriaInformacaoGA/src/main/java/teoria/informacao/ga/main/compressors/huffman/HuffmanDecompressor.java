package teoria.informacao.ga.main.compressors.huffman;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import nayuki.huffmancoding.BitInputStream;
import nayuki.huffmancoding.CanonicalCode;
import nayuki.huffmancoding.CodeTree;
import nayuki.huffmancoding.HuffmanDecompress;

public class HuffmanDecompressor {

    public byte[] decompress(byte[] data) throws IOException {
        InputStream in = new ByteArrayInputStream(data);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        BitInputStream bitIn = new BitInputStream(in);

        CanonicalCode canonCode = readCode(bitIn);
        CodeTree code = canonCode.toCodeTree();
        HuffmanDecompress.decompress(code, bitIn, out);
        return out.toByteArray();

    }


    static CanonicalCode readCode(BitInputStream in) throws IOException {
        int[] codeLengths = new int[257];

        for (int i = 0; i < codeLengths.length; ++i) {
            int val = 0;

            for (int j = 0; j < 8; ++j) {
                val = val << 1 | in.readNoEof();
            }

            codeLengths[i] = val;
        }

        return new CanonicalCode(codeLengths);
    }

}
