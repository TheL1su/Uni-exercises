//Filip Rutka - gr 1
import java.util.Scanner;

class Train_list{
    public Train trains;
    Train_list(){
        trains = new Train("guard",null,null);
        trains.train_name = "guard";
        trains.first = null;
        trains.next_train = null;
    }
    public void New_train(String train_name,String wagon_name){
        Train iter = trains;
        if(trains.next_train == null){ // jezeli lista pociagow byla pusta
            Wagon new_wagon = new Wagon(wagon_name);
            Wagon head_wagon = new Wagon(new_wagon);
            trains.next_train = new Train(train_name,head_wagon,null);
        }
        else{ // lista pociagow zawiera pociagi
            if(this.train_finder(train_name) != null){
                System.out.println("Train" + " " + train_name + " " + "already exists");
                return;
            }
            Wagon new_wagon = new Wagon(wagon_name);
            Wagon head_wagon = new Wagon(new_wagon);
            trains.next_train = new Train(train_name,head_wagon,trains.next_train);
        }
    }
    public void insert_first(String train_name,String wagon_name){ // funkcja wstawiajaca wagon na 1 miejsce w pociagu
        Train found = train_finder(train_name);
        if(found == null) System.out.println("Train" + " " + train_name + " " + "does not exist"); // nie ma takiego pociagu
        else{ //jest taki pociag
            found.insert_first(wagon_name);
        }
    }
    public void insert_last(String train_name,String wagon_name){
        Train found = train_finder(train_name);
        if(found == null) System.out.println("Train" + " " + train_name + " " + "does not exist"); // nie ma takiego pociagu
        else{ //jest taki pociag
            found.insert_last(wagon_name);
        }
    }
    public void display(String train_name){
        Train found = train_finder(train_name);
        if(found == null) System.out.println("Train" + " " + train_name + " " + "does not exist"); // nie ma takiego pociagu
        else{
            found.display();
        }
    }
    public void reverse(String train_name){ // funckja odwracajaca kolejnosc wagonow
        Train found = train_finder(train_name);
        if(found == null) System.out.println("Train" + " " + train_name + " " + "does not exist");
        else{
            found.reverse();
        }
    }

    public Train train_finder(String train_name){
        Train iter = trains;
        while(iter.next_train != null){
            if(iter.train_name.equals(train_name)) return iter;
            iter = iter.next_train;
        }
        if(iter.train_name.equals(train_name)) return iter;
        return null;
    }
    public Train train_finder_prev(String train_name){
        Train iter = trains;
        if(iter.next_train == null) return null; //brak pociagow
        else{// mamy pare pociagow
            while(iter.next_train.next_train != null){
                if(iter.next_train.train_name.equals(train_name)) return iter; // znalezlismy poprzednika
                iter = iter.next_train;
            }
            if(iter.next_train.train_name.equals(train_name)) return iter; // ostatni pociag
            return null;
        }
    }

    public void display_trains(){ //Trains
        String out = "Trains: ";
        Train iter = trains;
        while(iter.next_train != null){
            iter = iter.next_train;
            out += iter.train_name + " ";
        }
        if(trains.next_train != null) out = out.substring(0,out.length()-1);
        System.out.println(out);
    }

    public void union(String name1, String name2){ // laczenie dwoch pociagow
        Train first = train_finder(name1);
        Train second_prev = train_finder_prev(name2); // pociag przed tym ktory bedziemy usuwac

        if(first == null || second_prev == null) { // nie znaleziono ktoregos z pociagow
            if(first == null) System.out.println("Train" + " " + name1 + " " + "does not exist");
            if(second_prev == null) System.out.println("Train" + " " + name2 + " " + "does not exist");
        }

        else{
            Wagon first_in_first = first.first.next; // pierwszy wagon pierwszego pociagu
            Wagon last_in_first = first.first.next.prev; // ostatni wagon drugiego pociagu
            Wagon first_in_second = second_prev.next_train.first.next; //pierwszy wagon drugiego pociagu
            Wagon last_in_second = second_prev.next_train.first.next.prev; //ostatni wagon drugiego pociagu
            first_in_first.prev = last_in_second; //pierwszy z ostatnim
            last_in_second.next = first_in_first; //ostatni z pierwszym
            last_in_first.next = first_in_second; //ostatni z pierwszego z pierwszym drugiego
            first_in_second.prev = last_in_first; //pierwszy ostatniego z ostatnim pierwszego

            second_prev.next_train = second_prev.next_train.next_train; //usuwamy pociag
        }
    }
    public void delfirst(String name1,String name2){
        Train first_prev = train_finder_prev(name1);
        Train duplicate = train_finder(name2);

        if(first_prev == null || duplicate != null){
            if(first_prev == null) System.out.println("Train" + " " + name1 + " " + "does not exist");
            if(duplicate != null) System.out.println("Train" + " " + name2 + " " + "already exists");
        }

        else{
            Train first = first_prev.next_train;
            this.New_train(name2,first.first.next.name);


            if(first.first.next == first.first.next.prev){// tylko jeden wagon
                if(first_prev != trains) first_prev.next_train = first.next_train;
                else{
                    this.trains.next_train.next_train = first.next_train;
                }
            }

            else{ // wiecej wagonow
                Wagon first_wagon = first.first.next; // pierwszy wagon ktory bedziemy usuwac
                Wagon first_next = first_wagon.next;
                Wagon last = first_wagon.prev;
                if(first_next.next == first_wagon){ // mamy odwrocony pociag
                    first_next.next = first_next.prev;
                    first_next.prev = last;
                    last.next = first_next;
                }
                else{ // pociag nie jest odwrocony
                    first_next.prev = last;
                    last.next = first_next;
                }
                first.first.next = first_next;
            }
        }
    }
    public void dellast(String name1,String name2) {
        Train first_prev = train_finder_prev(name1);
        Train duplicate = train_finder(name2);

        if (first_prev == null || duplicate != null) {
            if (first_prev == null) System.out.println("Train" + " " + name1 + " " + "does not exist");
            if (duplicate != null) System.out.println("Train" + " " + name2 + " " + "already exists");
        }
        else {
            Train first = first_prev.next_train;
            this.New_train(name2, first.first.next.prev.name);

            if(first.first.next == first.first.next.prev){// tylko jeden wagon
                if(first_prev != trains) first_prev.next_train = first.next_train;
                else{
                    this.trains.next_train.next_train = first.next_train;
                }
            }

            else {
                Wagon last = first.first.next.prev; // ostatni wagon
                Wagon last_prev = last.prev;
                Wagon first_wagon = first.first.next;

                if (first_wagon.next.next == first_wagon) { // mamy odwrocony pociag
                    last_prev.prev = last_prev.next;
                    last_prev.next = first_wagon;
                    first_wagon.prev = last_prev;
                }
                else {
                    first_wagon.prev = last_prev;
                    last_prev.next = first_wagon;
                }
            }
        }
    }
}



class Train {

    Train(String train_name, Wagon first_wagon, Train next) {
        this.train_name = train_name;
        this.first = first_wagon;
        this.next_train = next;
    }

    public void insert_first(String name) {
        if (first.next == first.next.prev) { // pociag posiadal jeden wagon, nie wazne czy jest odwrocony czy nie
            Wagon wagon = new Wagon(name, first.next, first.next);
            Wagon first = this.first.next;
            first.next = wagon;
            first.prev = wagon;
            this.first.next = wagon;
        }
        else {// wiecej wagonow
            Wagon first_wagon = this.first.next;
            Wagon last = first_wagon.prev;
            Wagon wagon = new Wagon(name, last, first_wagon);
            first_wagon.prev = wagon;
            last.next = wagon;
            this.first.next = wagon;
        }
    }

    public void insert_last(String name) {
        if (first.next == first.next.prev) {//jeden wagon
            Wagon wagon = new Wagon(name, first.next, first.next);
            first.next.next = wagon;
            first.next.prev = wagon;
        }
        else {
            Wagon first_wagon = this.first.next;
            Wagon last = first_wagon.prev;
            Wagon wagon = new Wagon(name, last, first_wagon);
            first_wagon.prev = wagon;
            last.next = wagon;
        }
    }

    public void display() {
        String out = this.train_name + ": " + this.first.next.name + " ";
        Wagon iter = this.first.next;
        Wagon prev = iter;
        iter = iter.next;
        while (iter != this.first.next) {
            out += iter.name + " ";
            if (iter.next == prev) {
                prev = iter;
                iter = iter.prev;
            }
            else if (iter.prev == prev) {
                prev = iter;
                iter = iter.next;
            }
            //System.out.println(out);
        }
        out = out.substring(0,out.length()-1);
        System.out.println(out);
    }

    public void reverse(){
        Wagon last = this.first.next.prev;
        Wagon first = this.first.next;
        first.prev = first.next;
        first.next = last;
        last.next = last.prev;
        last.prev = first;
        this.first.next = last; // przepinamy wskaznik na ostatni wagon
    }

    public String train_name;
    public Wagon first;
    public Train next_train;

}
class Wagon{
    Wagon(Wagon first){
        this.name = "";
        this.prev = null;
        this.next = first;
    }
    Wagon(String name){
        this.name = name;
        this.prev = this;
        this.next = this;
    }
    Wagon(String name, Wagon prev, Wagon next){
        this.name = name;
        this.prev = prev;
        this.next = next;
    }
    public String name;
    public Wagon prev;
    public Wagon next;
}

public class Source {
    public static Scanner scn = new Scanner(System.in);

    public static void main(String[] args){
        int sets = scn.nextInt();
        while(sets > 0){
            int operations = scn.nextInt();
            scn.nextLine();
            Train_list chillwagon = new Train_list();
            for(int i=0;i<operations;i++){
                String line = scn.nextLine();
                String[] words = line.split(" ");
                //System.out.println(words[0]);
                if(words.length == 1) chillwagon.display_trains();
                if(words.length == 2){
                    if(words[0].equals("Display")) chillwagon.display(words[1]);
                    if(words[0].equals("Reverse")) chillwagon.reverse(words[1]);
                }
                if(words.length == 3){
                    if(words[0].equals("InsertFirst")) chillwagon.insert_first(words[1],words[2]);
                    if(words[0].equals("InsertLast")) chillwagon.insert_last(words[1],words[2]);
                    if(words[0].equals("Union")) chillwagon.union(words[1],words[2]);
                    if(words[0].equals("DelFirst")) chillwagon.delfirst(words[1],words[2]);
                    if(words[0].equals("DelLast")) chillwagon.dellast(words[1],words[2]);
                    if(words[0].equals("New")) chillwagon.New_train(words[1],words[2]);
                }
            }
            sets -= 1;
        }
    }
}
/*
3
5
New T1 W1
New T2 W2
Union T1 T2
DelFirst T1 T2
Trains
5
New T1 W1
DelFirst T1 T2
Reverse T2
Display T2
Trains
21
New T1 W7
InsertLast T1 W6
InsertLast T1 W5
InsertFirst T1 W8
InsertLast T1 W4
Reverse T1
New T2 W3
InsertLast T2 W2
InsertLast T2 W1
InsertLast T2 W0
Reverse T2
Union T1 T2
Display T1
DelFirst T1 T3
DelLast T1 T4
Display T3
Display T1
Union T3 T1
Union T3 T4
Display T3
Trains

Trains: T2 T1

T2: W1
Trains: T2

T1: W4 W5 W6 W7 W8 W0 W1 W2 W3
T3: W4
T1: W5 W6 W7 W8 W0 W1 W2
T3: W4 W5 W6 W7 W8 W0 W1 W2 W3
Trains: T3
*/