package jednowarstwowa;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String sciezkaTrening = "data\\Trening";
        Przetwornik trening = new Przetwornik(sciezkaTrening);
        List<Obserwacje> treningoweObserwacje = trening.getListaObserwacji();
        List<String> klasyObiektow = trening.getKlasyObiektow();

        String sciezkaTest = "data\\Test";
        Przetwornik test = new Przetwornik(sciezkaTest);
        List<Obserwacje> testoweObserwacje = test.getListaObserwacji();

        List<Perceptron> perceptrony = new ArrayList<>();

        for (String s : klasyObiektow) {
            Perceptron perceptron = new Perceptron(26,0,s,100);
            perceptron.uczSie(treningoweObserwacje);
            perceptrony.add(perceptron);
        }

       /* for (Obserwacje obserwacje : testoweObserwacje) {
            for (Perceptron perceptron : perceptrony) {
                    perceptron.klasyfikuj(obserwacje);
                    if (!obserwacje.getZaklasyfikowano().equals("0")){
                        break;
                    }
            }
        }*/

        for (Perceptron perceptron : perceptrony) {
            double TP = 0;
            double TN = 0;
            double FP = 0;
            double FN = 0;
            System.out.println(perceptron.getWykrywaj()+": ");
            for (Obserwacje obserwacje : testoweObserwacje) {
                perceptron.klasyfikuj(obserwacje);
                if (obserwacje.czyPrawidlowoZaklasyfikowano()){
                    TP++;
                }
                if (!perceptron.getWykrywaj().equals(obserwacje.getAtrybutDecyzyjny())){
                    TN++;
                }
                if (perceptron.getWykrywaj().equals(obserwacje.getZaklasyfikowano()) && !perceptron.getWykrywaj().equals(obserwacje.getAtrybutDecyzyjny())){
                    FP++;
                }
                if (!perceptron.getWykrywaj().equals(obserwacje.getZaklasyfikowano()) && perceptron.getWykrywaj().equals(obserwacje.getAtrybutDecyzyjny())){
                    FN++;
                }

            }
            System.out.println("Macierz omylek: ");
            //System.out.println("TP: "+TP+" TN: "+TN+" FP: "+FP+" FN: "+FN);
            System.out.println("| "+TP+" | "+FP+" |\n| "+FN+" | "+TN+"|");

            double dokladnosc = (TP+TN)/testoweObserwacje.size();
            double precyzja = TP/(TP+FP);
            double pelnosc = TP/(TP+FN);
            double fmiara = (2*precyzja*pelnosc)/(precyzja+pelnosc);
            System.out.println("Liczba zaklasyfikowanych prawidlowo: "+(TP+TN)+ "/" + testoweObserwacje.size());
            System.out.format("Dokladnosc: %.2f%%%n",dokladnosc*100);
            System.out.format("Precyzja: %.2f%%%n",precyzja*100);
            System.out.format("Pełnosc: %.2f%%%n",pelnosc*100);
            System.out.format("Fmiara: %.2f%%%n",fmiara*100);
            System.out.println();

        }

        System.out.println();

       /* for (Obserwacje obserwacje : testoweObserwacje) {
            if (obserwacje.czyPrawidlowoZaklasyfikowano()){
                System.out.println(obserwacje.getAtrybutDecyzyjny()+"=="+obserwacje.getZaklasyfikowano());
            }else{
                System.out.println("\t\t\t"+obserwacje.getAtrybutDecyzyjny()+"!="+obserwacje.getZaklasyfikowano());
            }
        }*/
//-------------------------------------------------------------------

        //wprowadzanie swojego tekstu
        System.out.println("Wpisz: \n\tWprowadz: aby wprowadzic sciezke do pliku\n\tEXIT: aby zakonczyc dzialanie programu");
        Scanner scanner = new Scanner(System.in);
        boolean dziala=true;
        while(dziala){
            String napis = scanner.nextLine();
            if (napis.toLowerCase().equals("wprowadz")){
                System.out.println("Podaj sciezke do pliku: ");
                String path = scanner.nextLine();
                Przetwornik przetwornik = new Przetwornik(path);
                List<Obserwacje> obserwacje = przetwornik.getListaObserwacji();
                for (Obserwacje obserwacja : obserwacje) {
                    for (Perceptron perceptron : perceptrony) {
                        perceptron.klasyfikuj(obserwacje);
                        if (!obserwacja.getZaklasyfikowano().equals("0")){
                            break;
                        }
                    }
                    System.out.println("Zaklasyfikowano do: " +obserwacja.getZaklasyfikowano());
                }



            }else if (napis.toLowerCase().equals("exit")){
                dziala = false;
                System.out.println("Zamykanie programu....");
            }else{
                System.out.println("Wpisz: \n\tWprowadz: aby wprowadzic sciezke do pliku\n\tEXIT: aby zakonczyc dzialanie programu");
            }
        }

        //------------------------------------------------------------------

        scanner.close();

    }
}
