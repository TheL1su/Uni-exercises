//Filip Rutka
package zadanie4.wentylacja;

import zadanie4.UzywajacyZegara;
import zadanie4.czas.PoraDoby;
import zadanie4.sensory.SensorNiedostepny;
import zadanie4.sensory.Zegar;

import java.util.ArrayList;

import static zadanie4.czas.PrzelicznikCzasu.sekundaDnia;


public class CzasoweSterowanieWentylacja extends SterowanieWentylacja
        implements UzywajacyZegara{
    public CzasoweSterowanieWentylacja() {}

    @Override
    public void sprawdzSensory() throws SensorNiedostepny {
        int sekunda_dnia = sekundaDnia(this.zegar.pobierzCzas());
        for(int i=0;i<Lista_por.size();i++){
            PoraDoby przedzial = Lista_por.get(i);
            if(przedzial.zawieraSie(sekunda_dnia)) {
                this.wentylator.ustawWlaczenie(true);
                return;
            }
        }
        this.wentylator.ustawWlaczenie(false);
    }

    @Override
    public void ustawZegar(Zegar zegar) {
        this.zegar = zegar;
    }
    public void dodajOkresWlaczenia(PoraDoby okresWlaczenia){
        this.Lista_por.add(okresWlaczenia);
    }

    PoraDoby[] pobierzOkresyWlaczenia(){
        if(Lista_por.size() > 0) {
            PoraDoby[] Lista = new PoraDoby[Lista_por.size()];
            for(int i=0;i<Lista_por.size();i++){
                Lista[i] = Lista_por.get(i);
            }
            return Lista;
        }
        return null;
    }
    void usunOkresWlaczenia(PoraDoby okres){
        int i=0;
        while( i < Lista_por.size()){
            if(Lista_por.get(i) == okres){
                Lista_por.remove(i);
                break;
            }
            i++;
        }
    }

    ArrayList<PoraDoby> Lista_por = new ArrayList<PoraDoby>();
    Zegar zegar;
}
