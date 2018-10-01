# Compactador de arquivos

Este projeto tem como objetivo elaborar uma solução computacional que codifique(compacte) e decodifique(descompacte) arquivos.
A idéia é desenvolver uma solução de compactação(sem perda –lossless) empregando uma combinação(livre) de abordagens de codificação a nível de **símbolo**, mas que  também trate a ocorrência da **repetição simples de símbolos** ao longo do texto


## Abordagem / Funcionamento

A compressão é controlada via a classe `CompressionHandler.java`. Está classe é incializada da seguinte forma:
```
 CompressionHandler compressor = CompressionHandler.builder()
      .compressor(new Lzw())
      .compressor(new Huffman())
      .log(log)
      .showCompressInfo(showCompressInfo)
      .build();
```

Com esta inicialização a compressão é feita utilizando dos algoritmos ***[LZW](https://github.com/lambdacasserole/lizard)*** e ***[Huffman](https://github.com/nayuki/Reference-Huffman-coding)*** respectivamente.

* *A ordem da aplicação dos algoritmos de compressão/compactação é feita pela ordem dos `.compressor` adicionados na inicialização da classe `CompressionHandler`.*
* *A descompressão/descompactação dos arquivos é feita na ordem inversa dos `.compressor` adicionados na inicialização da classe `CompressionHandler`.*

### Limitações

#### LWZ
>Lizard won't produce archive files out-of-the-box. It's a lower-level tool for dealing with LZW compression directly. **It is not optimized for speed, memory efficiency or performance.** This is a naïve implementation and oh boy is it slow. Seriously, I literally use hexadecimal strings to build dictionary entries (yeah, I know). Note also that the code dictionary is cleared as soon as it becomes full. This creates a way-less-than-optimal compression ratio.
> -- <cite>[Lizard code][1]</cite>

#### Huffman
> This project is a clear implementation of Huffman coding, suitable as a reference for educational purposes. It is provided separately in Java, Python, C++, and is open source. The code can be used for study, and as a solid basis for modification and extension. **Consequently, the codebase optimizes for readability and avoids fancy logic, and does not target the best speed/memory/performance**. Home page with detailed description: https://www.nayuki.io/page/reference-huffman-coding
> -- <cite>[Reference-Huffman-coding][2]</cite>

[1]:https://github.com/lambdacasserole/lizard
[2]:https://github.com/nayuki/Reference-Huffman-coding

#### São pré-requisitos deste projeto:
* **Maven 3** *[como instalar](https://www.mkyong.com/maven/how-to-install-maven-in-windows/)*
* **Java 8**
* **[Lombok](https://projectlombok.org/)** Habilitado na sua IDE (*se utilizar uma IDE*)

### Setup inicial
```
git clone https://github.com/alencarrh/TeoriaInformacao
cd /TeoriaInformacao/TeoriaInformacaoGA
```

### Build
```
mvn clean install
```

### Executar
```
$ java -jar target/trabalho.ga-1-spring-boot.jar -h
params: MODE filename [-log] [(-sci | --show-compress-info)] [(-h | --help)]
        MODE: -c or -d
         -c: to compress
         -d: to decompress
```

### Compactar arquivo
```
$ java -jar target/trabalho.ga-1-spring-boot.jar -c cantrbry/alice29.txt -log -sci
Compressing file: cantrbry/alice29.txt
Original file size: 152089
Compression step executed: LZW
        new size: 74148
        execution time: 299ms
        space saving: 51.246967%
        compression ratio (to previous step): 2.0511544
        compression ratio (to original file): 2.0511544
Compression step executed: Huffman
        new size: 69804
        execution time: 83ms
        space saving: 5.858551%
        compression ratio (to previous step): 1.0622314
        compression ratio (to original file): 2.1788006

Final status:
        Execution time: 382ms
        Original size: 152089
        Compressed size: 69804
        Space saving: 54.10319%
        Compression ratio: 2.1788006
File compressed: cantrbry/alice29.txt.cmpt

```

### Descompactar arquivo
```
$ java -jar target/trabalho.ga-1-spring-boot.jar -d cantrbry/alice29.txt.cmpt -log -sci
Decompressing file: cantrbry/alice29.txt.cmpt
File decompressed: cantrbry/alice29.txt
```
