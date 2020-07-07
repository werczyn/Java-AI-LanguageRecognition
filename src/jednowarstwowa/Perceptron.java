package jednowarstwowa;

import jednowarstwowa.Obserwacje;

import java.util.List;

public class Perceptron {

    private double [] wagi;
    private double prog;
    private String wykrywaj;
    private int licznik=0;
    private int alfa;

    public Perceptron(int liczbaAtrybutow, double prog, String wykrywaj,int alfa) {
        this.wagi = new double[liczbaAtrybutow];
        this.alfa = alfa;
        this.wykrywaj = wykrywaj;

        for (int i=0; i<wagi.length; i++){
            wagi[i]=Math.random();
        }

        this.prog = prog;
    }

    public void klasyfikuj(List<Obserwacje> obserwacje){
        for (Obserwacje obserwacja : obserwacje) {
            klasyfikuj(obserwacja);
        }
    }

    public void klasyfikuj(Obserwacje obserwacja){
        double[] atrybuty = obserwacja.getAtrybuty();
        double net = 0;
        //liczenie wartosci net
        for (int i = 0; i < atrybuty.length; i++) {
            net += atrybuty[i]*wagi[i];
        }
        //obliczanie wyjscia
        if (net >= prog){
            obserwacja.setZaklasyfikowano(wykrywaj);
        }else{
            obserwacja.setZaklasyfikowano(""+0);
        }
    }


    public void uczSie(List<Obserwacje> obserwacje) {
        klasyfikuj(obserwacje);
        for (Obserwacje obserwacja : obserwacje) {
            if (!obserwacja.czyPrawidlowoZaklasyfikowano()){
                int y;
                int d;
                if (obserwacja.getAtrybutDecyzyjny().equals(wykrywaj)){
                    d = 1;
                }else{
                    d=0;
                }
                if (obserwacja.getZaklasyfikowano().equals(wykrywaj)){
                    y=1;
                }else {
                    y=0;
                }

                int mnoznik = d - y;
                for (int i = 0; i < obserwacja.getAtrybuty().length; i++) {
                    wagi[i]=wagi[i]+(obserwacja.getAtrybuty()[i]*mnoznik*alfa);
                }
            }
        }
        String[] tablicaDecyzji = new String[obserwacje.size()];
        String[] tablicaZaklasyfikowanych = new String[obserwacje.size()];

        for (int i = 0; i < tablicaDecyzji.length; i++) {
            tablicaDecyzji[i] = obserwacje.get(i).getAtrybutDecyzyjny();
            tablicaZaklasyfikowanych[i] = obserwacje.get(i).getZaklasyfikowano();
        }

        for (int i = 0; i < tablicaDecyzji.length; i++) {
            if (tablicaDecyzji[i] != tablicaZaklasyfikowanych[i] && licznik<5000){
                licznik++;
                uczSie(obserwacje);
            }
        }

    }

    public void showWektorWag(){
        for (double v : wagi) {
            System.out.println(v);
        }
    }

    public String getWykrywaj() {
        return wykrywaj;
    }
}
