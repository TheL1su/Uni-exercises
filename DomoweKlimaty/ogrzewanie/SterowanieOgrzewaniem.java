//Filip Rutka
package zadanie4.ogrzewanie;

import zadanie4.SprawdzajacySensory;
import zadanie4.efektory.SterownikKotla;

public abstract class SterowanieOgrzewaniem implements SprawdzajacySensory {
    public SterowanieOgrzewaniem() {}
    public SterownikKotla pobierzSterownikKotla(){ return kociol;}
    public void ustawSterownikPieca(SterownikKotla kociol){ this.kociol = kociol;}

    SterownikKotla kociol;
}
