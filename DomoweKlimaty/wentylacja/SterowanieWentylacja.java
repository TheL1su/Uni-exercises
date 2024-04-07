//Filip Rutka
package zadanie4.wentylacja;

import zadanie4.SprawdzajacySensory;
import zadanie4.efektory.SterownikWentylatora;

public abstract class SterowanieWentylacja implements SprawdzajacySensory {
    public SterowanieWentylacja(){};

    public SterownikWentylatora jakiSterownikWentylatora() {
        return wentylator;
    }

    public void ustawSterownikWentylatora(SterownikWentylatora wentylator){
        this.wentylator = wentylator;
    }

    SterownikWentylatora wentylator;
}
