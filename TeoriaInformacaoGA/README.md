# Compactador de arquivos

Este projeto tem como objetivo elaborar uma solução computacional que codifique(compacte) e decodifique(descompacte) arquivos.
A idéia é desenvolver uma solução de compactação(sem perda –lossless) empregando uma combinação(livre) de abordagens de codificação a nível de **símbolo**, mas que  também trate a ocorrência da **repetição simples de símbolos** ao longo do texto

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
$ java -jar target/trabalho.ga-1-spring-boot.jar -c alice29.txt -log -sci
Compressing file: alice29.txt
Original file size: 152089
Compression step executed: LZW
        new size: 74148
        compression rate (to previous step): 51.246967%
        compression rate (to original file): 51.246967%
Compression step executed: Huffman
        new size: 69804
        compression rate (to previous step): 5.858551%
        compression rate (to original file): 54.10319%
  Original size: 152089
Compressed size: 69804 ( -54.10319% )
File compressed: alice29.txt.cmpt
```

### Descompactar arquivo
```
$java -jar target/trabalho.ga-1-spring-boot.jar -d alice29.txt.cmpt -log -sci
Decompressing file: alice29.txt.cmpt
File decompressed: alice29.txt
```
