//Filip Rutka
package zadanie4.sensory;

import java.util.*;

import zadanie4.SprawdzajacySensory;

public abstract class SensorObserwowanyImpl implements SensorObserwowany{
    public SensorObserwowanyImpl(){
        this.ListaSensorow = new ArrayList<SprawdzajacySensory>();
    } //konstruktor

    @Override
    public void dodajObserwartoraSensorow(SprawdzajacySensory sprawdzajacy) {
        this.ListaSensorow.add(sprawdzajacy);
    }

    @Override
    public void wyczyscObserwatorowSensorow() {
        if(!this.ListaSensorow.isEmpty()) this.ListaSensorow.clear();
    }

    protected void powiadom(){
        for (SprawdzajacySensory sprawdzajacySensory : this.ListaSensorow) {
            try {
                sprawdzajacySensory.sprawdzSensory();
            } catch (SensorNiedostepny e) {
                e.printStackTrace();
            }
        }
    }
    public ArrayList<SprawdzajacySensory> ListaSensorow;
}
