package jednowarstwowa;

public class Obserwacje {

    private String atrybutDecyzyjny;
    private double[] atrybuty;
    private String zaklasyfikowano;

    public Obserwacje(String atrybutDecyzyjny, double[] atrybuty) {
        zaklasyfikowano = ""+0;
        this.atrybutDecyzyjny = atrybutDecyzyjny;

        this.atrybuty = normalizuj(atrybuty);
    }

    public Obserwacje(double[] atrybuty){
        this.atrybuty = atrybuty;
    }

    public String getAtrybutDecyzyjny() {
        return atrybutDecyzyjny;
    }

    public double[] getAtrybuty() {
        return atrybuty;
    }

    public void setZaklasyfikowano(String zaklasyfikowano) {
        this.zaklasyfikowano = zaklasyfikowano;
    }

    public boolean czyPrawidlowoZaklasyfikowano(){
        return zaklasyfikowano.equals(atrybutDecyzyjny);
    }

    public String getZaklasyfikowano() {
        return zaklasyfikowano;
    }

    public int liczbaAtrybutow(){
        //liczba atrybutow bez decyzyjnego
        return atrybuty.length;
    }

    public double[] normalizuj(double[] atrybuty){
        double dlugosc=0;
        for (int i = 0; i < atrybuty.length; i++) {
            dlugosc+=Math.pow(atrybuty[i],2);
        }
        dlugosc = Math.sqrt(dlugosc);
        for (int i = 0; i < atrybuty.length; i++) {
            atrybuty[i]= atrybuty[i]/dlugosc;
        }
        return atrybuty;
    }


}
