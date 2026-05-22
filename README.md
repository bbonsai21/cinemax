# CineMax - EN

A screening and booking management application for small cinemas, written entirely in Java.

## Repository Structure
**authors.txt**  (not included in the GitHub repo for privacy reasons)

**README.md**

**src/**        source code

**bin/**        .jar and native executables

**data/**       CSV data files

**doc/**        manuals and JavaDoc

**lib/**        external libraries (if any)

## Requirements

### Compiling to .jar
- JDK 25+

### Compiling to native executable
- GraalVM JDK 25+ with Native Image

### Running the .jar
- JRE 25+

### Running the native executable
- No external dependencies

---

## Compiling to .jar

### Linux

```bash
javac -sourcepath src -d bin/build src/cinemax/CineMax.java
jar cfm bin/CineMax.jar manifest.txt -C bin/build .
```

### Windows

```batch
javac -sourcepath src -d bin\build src\cinemax\CineMax.java
jar cfm bin\CineMax.jar manifest.txt -C bin\build .
```

The `manifest.txt` file in the repository root must contain:

```
Manifest-Version: 1.0
Main-Class: cinemax.CineMax

```

The final blank line is required.

---

## Compiling to a Native Executable (GraalVM Native Image)

### Prerequisites

Install GraalVM JDK 25, which includes `native-image`.
Verify that `native-image` is in your PATH:

```bash
native-image --version
```

You must also have compiled the .jar file as described above.

### Linux

```bash
native-image -jar bin/CineMax.jar --no-fallback -o bin/CineMax
```

This creates the file `bin/CineMax` (ELF, native Linux executable).

### Windows

```batch
native-image -jar bin\CineMax.jar --no-fallback -o bin\CineMax
```

Produces the file `bin\CineMax.exe`.

---

## Execution

As an alternative to manual compilation, you can download **ELF** and **.exe** executables directly from the GitHub *Releases* page. In that case, simply run the downloaded file by replacing the paths below with the path to the downloaded executable.

### .jar

```bash
java -jar bin/CineMax.jar
```

### Native executable — Linux

```bash
./bin/CineMax
```

### Native executable — Windows

```batch
bin\CineMax.exe
```

# CineMax - IT

Un'applicazione di gestione proiezioni e prenotazioni per piccoli cinema, scritta interamente in Java.

## Struttura del repository

**authors.txt**  (not incluso nel Github per ragioni di privacy)

**README.txt**

**src/**        codice sorgente

**bin/**        .jar ed eseguibili nativi

**data/**       dati CSV e file

**doc/**        manuali e documentazione javadoc

**lib/**        librerie esterne (se presenti)

## Requisiti

### Compilazione a .jar
- JDK 25+

### Compilazione a eseguibile nativo
- GraalVM JDK 25+ con Native Image

### Esecuzione del .jar
- JRE 25+

### Esecuzione dell'eseguibile nativo
- Nessuna dipendenza esterna

---

## Compilazione a .jar

### Linux

```bash
javac -sourcepath src -d bin/build src/cinemax/CineMax.java
jar cfm bin/CineMax.jar manifest.txt -C bin/build .
```

### Windows

```batch
javac -sourcepath src -d bin\build src\cinemax\CineMax.java
jar cfm bin\CineMax.jar manifest.txt -C bin\build .
```

Il file `manifest.txt` nella root del repository deve contenere:

```
Manifest-Version: 1.0
Main-Class: cinemax.CineMax

```

La riga vuota finale è obbligatoria.

---

## Compilazione a eseguibile nativo (GraalVM Native Image)

### Prerequisiti

Installare GraalVM JDK 25, che include `native-image`.
Verificare che `native-image` sia nel PATH:

```bash
native-image --version
```

Serve, inoltre, aver compilato il .jar come descritto sopra.

### Linux

```bash
native-image -jar bin/CineMax.jar --no-fallback -o bin/CineMax
```

Produce il file `bin/CineMax` (ELF, eseguibile nativo Linux).

### Windows

```batch
native-image -jar bin\CineMax.jar --no-fallback -o bin\CineMax
```

Produce il file `bin\CineMax.exe`.

---

## Esecuzione

In alternativa alla compilazione manuale, è possibile scaricare eseguibili **ELF** e **.exe** direttamente dalla pagina *Releases* Github. In quel caso basterà eseguire il file scaricato sostituendo ai path sotto quello dell'eseguibile scaricato.

### .jar

```bash
java -jar bin/CineMax.jar
```

### Eseguibile nativo — Linux

```bash
./bin/CineMax
```

### Eseguibile nativo — Windows

```batch
bin\CineMax.exe
```