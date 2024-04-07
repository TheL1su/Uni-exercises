//Filip Rutka
package zadanie4.sensory;

import zadanie4.sensory.SensorNiedostepny;

public interface Termometr{
    float pobierzTemperature() throws SensorNiedostepny;
}
