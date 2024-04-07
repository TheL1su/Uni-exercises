//Filip Rutka
package zadanie4.sensory;

import zadanie4.efektory.SterownikKotla;
import zadanie4.efektory.SterownikWentylatora;

public class SensorNiedostepny extends java.lang.Exception {
    public SensorNiedostepny() {}

    public SensorNiedostepny(java.lang.String message){
        System.out.println(message);
    }
}
