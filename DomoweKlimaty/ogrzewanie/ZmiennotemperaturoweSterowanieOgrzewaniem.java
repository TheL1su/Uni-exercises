//Filip Rutka
package zadanie4.ogrzewanie;

import zadanie4.UzywajacyZegara;
import zadanie4.sensory.SensorNiedostepny;
import zadanie4.sensory.Termometr;
import zadanie4.sensory.Zegar;
import zadanie4.czas.PoraDoby;
import java.util.ArrayList;


import static zadanie4.czas.PrzelicznikCzasu.sekundaDnia;

public class ZmiennotemperaturoweSterowanieOgrzewaniem
        extends StalotemperaturoweSterowanieOgrzewaniem
        implements UzywajacyZegara {

    public ZmiennotemperaturoweSterowanieOgrzewaniem(){
        wspolczynnik = 0;
        docelowa = 0;
        minimalna = 0;
    }
    public double pobierzDocelowaTemperatura(){return docelowa;}
    public void ustawDocelowaTemperatura(double minimalnaTemperatura){
        docelowa = minimalnaTemperatura;
    }
    public double pobierzMinimalnaRoznica(){return minimalna;}
    public void ustawMinimalnaRoznica(double minimalnaRoznica){
        minimalna = minimalnaRoznica;
    }
    public Termometr pobierzTermometrWewnetrzny(){return wewnetrzny;}
    public void ustawTermometrZewnetrzny(Termometr termometrZewnetrzny){
        zewnetrzny = termometrZewnetrzny;
    }
    public Termometr pobierzTermometrZewnetrzny(){return zewnetrzny;}
    public void ustawTermometrWewnetrzny(Termometr termometrWewnetrzny){
        wewnetrzny = termometrWewnetrzny;
    }
    public double pobierzWspolczynnik(){return wspolczynnik;}
    public void ustawWspolczynnik(double wspolczynnik){
        this.wspolczynnik = wspolczynnik;
    }

    @Override
    public void sprawdzSensory(){
        int sekunda_dnia = sekundaDnia(this.zegar.pobierzCzas());
        double docelowa_teraz = this.docelowa;
        for (int i = 0; i < Lista_por.size(); i++) {
            PoraDoby przedzial = Lista_por.get(i);
            if(przedzial.zawieraSie(sekunda_dnia)) {
                docelowa_teraz = Lista_temperatur.get(i);
            }
        }
        try {
            float wewnetrzna = this.wewnetrzny.pobierzTemperature();
            float zewnetrzna = this.zewnetrzny.pobierzTemperature();

            if (wewnetrzna >= docelowa_teraz && ((docelowa_teraz - zewnetrzna) < minimalna)) {
                this.kociol.ustawZadanaTemperature(kociol.pobierzMinimalnaTemperatureWlaczenia() - 1);
            }
            else if (wewnetrzna < docelowa_teraz && (docelowa_teraz - zewnetrzna) < minimalna) {
                this.kociol.ustawZadanaTemperature((int) ((docelowa_teraz - wewnetrzna) * wspolczynnik + kociol.pobierzMinimalnaTemperatureWlaczenia()));
            } else {
                this.kociol.ustawZadanaTemperature((int) ((docelowa_teraz - zewnetrzna - minimalna) * wspolczynnik + this.kociol.pobierzMinimalnaTemperatureWlaczenia()));
            }
        }
        catch(SensorNiedostepny e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void ustawZegar(Zegar zegar) {
        this.zegar = zegar;
    }
    public void dodajOkresZezmienionaTemperatura(PoraDoby pora, double temperatura) throws NiejednoznaczaTemperatura{
        for (int i = 0; i < Lista_por.size(); i++) {
            PoraDoby przedzial = Lista_por.get(i); // pora juz dodana
            int koniec_pory, koniec_przedzialu;
            if (pora.pobierzKoniec() == 0) koniec_pory = 86399;
            else koniec_pory = pora.pobierzKoniec() - 1;
            if (przedzial.pobierzKoniec() == 0) koniec_przedzialu = 86399;
            else koniec_przedzialu = przedzial.pobierzKoniec() - 1;

            if (przedzial.zawieraSie(pora.pobierzPoczatek()) || przedzial.zawieraSie(koniec_pory)
                        || pora.zawieraSie(przedzial.pobierzKoniec()) || pora.zawieraSie(koniec_przedzialu)) {
                 throw new NiejednoznaczaTemperatura();
            }
        }
        Lista_por.add(pora);
        Lista_temperatur.add(temperatura);
    }

    ArrayList<PoraDoby> Lista_por = new ArrayList<PoraDoby>();
    ArrayList<Double> Lista_temperatur = new ArrayList<Double>();
    double wspolczynnik;
    double docelowa;
    double minimalna;
    Termometr wewnetrzny;
    Termometr zewnetrzny;
    Zegar zegar;
}
