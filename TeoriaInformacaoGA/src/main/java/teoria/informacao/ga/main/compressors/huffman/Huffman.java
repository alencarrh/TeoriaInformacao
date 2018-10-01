package teoria.informacao.ga.main.compressors.huffman;

import teoria.informacao.ga.main.interfaces.Compressor;

import java.io.IOException;

public class Huffman implements Compressor {

    private HuffmanCompressor compressor;
    private HuffmanDecompressor decompressor;

    public Huffman() {
        this.compressor = new HuffmanCompressor();
        this.decompressor = new HuffmanDecompressor();
    }

    @Override
    public String name() {
        return "Huffman";
    }

    @Override
    public byte[] compress(byte[] data) throws IOException {
        return compressor.compress(data);
    }

    @Override
    public byte[] decompress(byte[] data) throws IOException {
        return decompressor.decompress(data);
    }
}
