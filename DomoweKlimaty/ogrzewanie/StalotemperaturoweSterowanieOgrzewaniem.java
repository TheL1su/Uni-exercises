//Filip Rutka
package zadanie4.ogrzewanie;

import zadanie4.sensory.SensorNiedostepny;
import zadanie4.sensory.Termometr;

public class StalotemperaturoweSterowanieOgrzewaniem extends SterowanieOgrzewaniem{

    public StalotemperaturoweSterowanieOgrzewaniem(){ //konstruktor domyslny
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
        try {
            float wewnetrzna = this.wewnetrzny.pobierzTemperature();
            float zewnetrzna = this.zewnetrzny.pobierzTemperature();
            if (wewnetrzna >= docelowa && ((docelowa - zewnetrzna) < minimalna)) {
                this.kociol.ustawZadanaTemperature(this.kociol.pobierzMinimalnaTemperatureWlaczenia() - 1);
            }
            else if (wewnetrzna < docelowa && (docelowa - zewnetrzna) < minimalna) {
                this.kociol.ustawZadanaTemperature((int) ((docelowa - wewnetrzna) * wspolczynnik + this.kociol.pobierzMinimalnaTemperatureWlaczenia()));
            } else {
                this.kociol.ustawZadanaTemperature((int) ((docelowa - zewnetrzna - minimalna) * wspolczynnik + this.kociol.pobierzMinimalnaTemperatureWlaczenia()));
            }
        }
        catch(SensorNiedostepny e){
            System.out.println(e.getMessage());
        }
    }

    double wspolczynnik;
    double docelowa;
    double minimalna;
    Termometr wewnetrzny;
    Termometr zewnetrzny;
}
