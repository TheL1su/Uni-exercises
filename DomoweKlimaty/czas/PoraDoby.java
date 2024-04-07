//Filip Rutka
package zadanie4.czas;

public class PoraDoby {

    public PoraDoby() { //konstruktor domyslny
        poczatek = 0;
        koniec = 0;
    }
    public PoraDoby(int poczatekSekunda, int koniecSekunda){ //konstruktor z argumentami
        poczatek = poczatekSekunda;
        if(koniecSekunda > 86399) koniec = koniecSekunda - 86400;
        else koniec = koniecSekunda;
    }
    public void ustawPoczatek(int poczatekSekunda){ //funkcja ustawiajaca poczatek
        poczatek = poczatekSekunda;
    }
    public void ustawKoniec(int koniecSekunda){ //funkcja ustawiajaca koniec
        if(koniecSekunda > 86399) koniec = koniecSekunda - 86400;
        else koniec = koniecSekunda;
    }
    public int pobierzPoczatek(){ //funkcja zwracaja poczatek
        return poczatek;
    }
    public int pobierzKoniec(){ //funkcja zwracajaca koniec
        return koniec;
    } //funkcja zwracajaca koniec

    public boolean zawieraSie(int sekundaDnia){ //funkcja zwracajaca czy dana godzina zawiera sie w przedziale
        if(sekundaDnia == this.pobierzPoczatek()) return true; //poczatek przedzialu
        if(sekundaDnia == this.pobierzKoniec()) return false; //koniec przedzialu
        if(this.pobierzPoczatek() == this.pobierzKoniec()) return true; // cala doba
        if(this.pobierzPoczatek() < this.pobierzKoniec()){ // przedzial nie zawierajacy polnocy
            if(sekundaDnia < this.pobierzKoniec() && sekundaDnia >= this.pobierzPoczatek()) return true;
            else return false;
        }
        else{ //przedzial zawierajacy polnoc
            if(sekundaDnia >= this.pobierzPoczatek() || sekundaDnia < this.pobierzKoniec()) return true;
            else return false;
        }
    }
    public int dlugosc(){
        if(this.pobierzPoczatek() < this.pobierzKoniec())
            return pobierzKoniec() - pobierzPoczatek(); //przedzial nie zawiera polnocy
        else{ // przedzial zawiera polnoc
            return 86399 - pobierzPoczatek() + pobierzKoniec() + 1;
        }
    }
    int poczatek; // poczatek czasu
    int koniec; //koniec czasu
}
