//Filip Rutka
package zadanie4.wentylacja;


import zadanie4.UzywajacyZegara;
import zadanie4.czas.PoraDoby;
import zadanie4.sensory.SensorNiedostepny;
import zadanie4.sensory.Zegar;
import zadanie4.sensory.Higrometr;

import java.util.ArrayList;


import static zadanie4.czas.PrzelicznikCzasu.sekundaDnia;

public class WilgotnoscioweSterowanieWentylacja
        extends SterowanieWentylacja
        implements UzywajacyZegara {

    @Override
    public void sprawdzSensory() throws SensorNiedostepny {
        int sekunda_dnia = sekundaDnia(this.zegar.pobierzCzas());
        System.out.println();
        System.out.println("Godzina: " + (sekunda_dnia/3600) +  ":" + ((sekunda_dnia%3600)/60) + " aktualna wilgotnosc: " +
                this.higrometr.pobierzWilgotnosc() + " maksymalna wilgotnosc: " + maksymalna);

        System.out.println("Stan wentylatora: " + wentylator.jestWlaczony());
        System.out.println("lista wlaczen start");
        for(int i=0;i<Lista_wlaczen.size();i++){
            System.out.print("("+ (Lista_wlaczen.get(i).pobierzPoczatek()/3600) + ":" + (Lista_wlaczen.get(i).pobierzPoczatek()%3600)/60 + "," + (Lista_wlaczen.get(i).pobierzKoniec()/3600) + ":" + (Lista_wlaczen.get(i).pobierzKoniec()%3600)/60 + ") ");
        }
        System.out.println("lista wlaczen stop");

        if(Lista_wlaczen.isEmpty()){ //Lista wlaczen byla pusta
            PoraDoby kolejna = new PoraDoby(sekunda_dnia,sekunda_dnia + 15*60);
            Lista_wlaczen.add(kolejna);
            wentylator.ustawWlaczenie(true);
            System.out.println("Stan wentylatora: " + wentylator.jestWlaczony());
            return;
        }

        if(this.higrometr.pobierzWilgotnosc() > maksymalna){ //za duza wilgotnosc, wlaczamy wentylator
            PoraDoby last = Lista_wlaczen.get(Lista_wlaczen.size()-1); //lista wlaczen nie byla pusta
            if(last.zawieraSie(sekunda_dnia)){ //jezeli sekunda zawiera sie w przedziale w ktorym powinien byc wlaczony wentylator
                PoraDoby nowa = new PoraDoby(last.pobierzPoczatek(),sekunda_dnia + 15*60);
                Lista_wlaczen.remove(Lista_wlaczen.size()-1);
                Lista_wlaczen.add(nowa);
                wentylator.ustawWlaczenie(true);
                System.out.println("Stan wentylatora: " + wentylator.jestWlaczony());
            }
            else {
                PoraDoby kolejna = new PoraDoby(sekunda_dnia, sekunda_dnia + 15 * 60);
                Lista_wlaczen.add(kolejna);
                wentylator.ustawWlaczenie(true);
                System.out.println("Stan wentylatora: " + wentylator.jestWlaczony());
            }
            return;
        }
        PoraDoby last = Lista_wlaczen.get(Lista_wlaczen.size()-1); //lista wlaczen nie byla pusta
        if(last.zawieraSie(sekunda_dnia)) {
            System.out.println("hmmmm");
            return;
        }

        int trzy_godziny_temu,laczny_czas = 0;

        if(sekunda_dnia - 3*3600 < 0) trzy_godziny_temu = 86400 + (sekunda_dnia - 3*3600);
        else trzy_godziny_temu = sekunda_dnia - 3*3600;

        PoraDoby trzy_godzinny_przedzial = new PoraDoby(trzy_godziny_temu,sekunda_dnia);
        System.out.println("Trzy h: " + "("+ (trzy_godzinny_przedzial.pobierzPoczatek()/3600) + ":" + (trzy_godzinny_przedzial.pobierzPoczatek()%3600)/60 + "," + (trzy_godzinny_przedzial.pobierzKoniec()/3600) + ":" + (trzy_godzinny_przedzial.pobierzKoniec()%3600)/60 + ") ");

        for (int i = 0; i < Lista_wlaczen.size(); i++) {
            PoraDoby przedzial = Lista_wlaczen.get(i);
            if (!trzy_godzinny_przedzial.zawieraSie(przedzial.pobierzPoczatek()) && trzy_godzinny_przedzial.zawieraSie(przedzial.pobierzKoniec())) {
                PoraDoby pomocniczy = new PoraDoby(trzy_godziny_temu, przedzial.pobierzKoniec());
                laczny_czas += pomocniczy.dlugosc();
                System.out.println(laczny_czas);
            }
            if (trzy_godzinny_przedzial.zawieraSie(przedzial.pobierzPoczatek())
                    && trzy_godzinny_przedzial.zawieraSie(przedzial.pobierzKoniec()-1)) {
                laczny_czas += przedzial.dlugosc();
                System.out.println(laczny_czas);
            }
        }
        if (laczny_czas < 3600 ) {
            System.out.println("laczny czas: " + laczny_czas);
            wentylator.ustawWlaczenie(true);
            PoraDoby nowa = new PoraDoby(sekunda_dnia, sekunda_dnia + 15*60);
            Lista_wlaczen.add(nowa);
            System.out.println("Stan wentylatora: " + wentylator.jestWlaczony());
            return;
        }
        wentylator.ustawWlaczenie(false);
    }
    double getMaksymalnaPozadanaWilgotnosc(){ return maksymalna; }
    public void ustawMaksymalnaPozadanaWilgotnosc(double maksymalnaPozadanaWilgotnosc){
        this.maksymalna = maksymalnaPozadanaWilgotnosc;
    }
    public void ustawHigrometr(Higrometr higrometr){
        try {
            if(higrometr == null) throw new SensorNiedostepny();
            this.higrometr = higrometr;
        }
        catch(SensorNiedostepny e){System.out.println(e.getMessage());}
    }

    @Override
    public void ustawZegar(Zegar zegar) {
        this.zegar = zegar;
    }

    ArrayList<PoraDoby> Lista_wlaczen = new ArrayList<PoraDoby>();
    double maksymalna;
    Higrometr higrometr;
    Zegar zegar;
}
