package teoria.informacao.ga.main.interfaces;

import java.io.IOException;

public interface Compressor {

    String name();

    byte[] compress(byte[] data) throws IOException;

    byte[] decompress(byte[] data) throws IOException;

}
