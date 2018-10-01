package teoria.informacao.ga.main.compressors.lzw;

import java.io.IOException;

import com.sauljohnson.lizard.LzwCompressor;

import teoria.informacao.ga.main.interfaces.Compressor;

public class Lzw implements Compressor {

    private LzwCompressor compressor;

    public Lzw() {
        this.compressor = new LzwCompressor();
    }

    @Override
    public String name() {
        return "LZW";
    }

    @Override
    public byte[] compress(byte[] data) throws IOException {
        return this.compressor.compress(data);
    }

    @Override
    public byte[] decompress(byte[] data) throws IOException {
        return compressor.decompress(data);
    }
}
