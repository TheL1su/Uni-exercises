//Filip Rutka
package zadanie4.ogrzewanie;
import zadanie4.czas.PoraDoby;

public class NiejednoznaczaTemperatura extends java.lang.Exception {
    NiejednoznaczaTemperatura(){};
    NiejednoznaczaTemperatura(PoraDoby konflikt, java.lang.String message){
        pora_bledu = konflikt;
        this.message = message;
    }
    public PoraDoby pobierzKonflikt(){
        return pora_bledu;
    }

    PoraDoby pora_bledu;
    String message;
}
