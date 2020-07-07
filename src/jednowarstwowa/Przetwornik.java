package jednowarstwowa;

import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;

import static java.nio.file.FileVisitResult.CONTINUE;

public class Przetwornik {
    private List<String> klasyObiektow;
    private List<Obserwacje> listaObserwacji;

    public Przetwornik(String sciezkaPliku) {
        klasyObiektow = new ArrayList<>();
        listaObserwacji = new ArrayList<>();

        zaladujDane(sciezkaPliku);

    }

    private void zaladujDane(String sciezkaPliku) {
        Path katalog = Paths.get(sciezkaPliku);


        try {
            Files.walkFileTree(katalog, new SimpleFileVisitor<Path>() {
                double[] tablicaZnakow;

                @Override
                public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attr){
                    if (dir.getFileName().toString().equals("Trening") || dir.getFileName().toString().equals("Test")){
                        return CONTINUE;
                    }
                    klasyObiektow.add(dir.getFileName().toString());

                    return CONTINUE;
                }

                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attr) {
                    tablicaZnakow = new double[26];

                    BufferedReader fileReader;
                    try {
                        fileReader = new BufferedReader(new FileReader(new File(file.toString())));
                        String linia;
                        while ((linia = fileReader.readLine()) != null) {
                            String znormalizowany = Normalizer.normalize(linia.trim().toLowerCase(),Normalizer.Form.NFKD);
                            znormalizowany = znormalizowany.replaceAll("[^\\p{ASCII}]", "");
                            
                            char[] charTab = znormalizowany.toCharArray();

                            for (char charakter : charTab) {
                                int ascii = (int) charakter;
                                if (ascii>=97 && ascii<=122){
                                    tablicaZnakow[ascii-97] += 1;
                                }
                            }

                            //System.out.println(znormalizowany);
                        }
                        listaObserwacji.add(new Obserwacje(file.getParent().getFileName().toString(),tablicaZnakow));
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    return CONTINUE;
                }


                @Override
                public FileVisitResult visitFileFailed(Path file_path, IOException exc) {
                    System.err.println(file_path);
                    System.err.println(exc.getMessage());
                    exc.printStackTrace();
                    return CONTINUE;
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Obserwacje> getListaObserwacji() {
        return listaObserwacji;
    }

    public List<String> getKlasyObiektow() {
        return klasyObiektow;
    }
}
