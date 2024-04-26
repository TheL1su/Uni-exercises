//Filip Rutka
import java.util.Scanner;

public class Source {

    public static void pakowanie_rekur(int volume,int index_stack,int nr_elementu,int[] stack,int[] tab){
        if(volume == 0) return; //znalezlismy nasze elementy
        if(nr_elementu == tab.length){ // chcemy wrzucic element ktory jest juz poza tablica
            if(stack[0] == tab.length-1){ // chcielibysmy wrzucic element poza tablica na pierwsze miejsce
                stack[tab.length] = -1;
                return;
            }
            else{
                int rem = stack[index_stack-1]; //usuwamy ostatni element ze stosu
                stack[index_stack-1] = 0;
                pakowanie_rekur(volume + tab[rem] ,index_stack - 1, rem + 1 , stack , tab);
            }
        }
	    else{
            stack[index_stack] = nr_elementu; // dodajemy kolejny element na stos
            pakowanie_rekur(volume - tab[nr_elementu],index_stack + 1, nr_elementu + 1,stack,tab);
        }
    }
    public static void pakowanie_iter(int volume, int[] stack , int[] tab){
        int index_stack = 0;
        int vol = volume;
        int nr_elementu=0;
        while(true){
            while(nr_elementu < tab.length){ //wrzucamy na stack wszystko co mozemy
                stack[index_stack] = nr_elementu;
                vol -= tab[nr_elementu];
                if(vol == 0){ // jezeli odnalezlismy wage to wynik jest na stacku
                    return;
                }
                nr_elementu += 1;
                index_stack += 1;
            }
            if(nr_elementu == tab.length){
                if(stack[0] == tab.length-1){ // chcielibysmy wrzucic element poza tablica na pierwsze miejsce
                    stack[tab.length] = -1;
                    return;
                }
                else{ // mozemy wrzucic kolejny element
                    index_stack -= 1;
                    vol += tab[stack[index_stack]];
                    nr_elementu = stack[index_stack]+1;
                    stack[index_stack] = 0;
                }
            }

        }
    }
    public static Scanner scn = new Scanner(System.in);
    public static void main(String[] args) {
        int sets = scn.nextInt();
        while(sets > 0){
            int masa = scn.nextInt();
            int n = scn.nextInt();
            int[] t = new int[n];
            int[] Stack = new int[n+1];
            for(int i=0;i<n;i++){
                t[i] = scn.nextInt();
            }
            String Iter = "ITER: " + masa + " =";
            String Rec = "REC: " + masa + " =";
            pakowanie_iter(masa,Stack,t);
            if(Stack[t.length] == -1) System.out.println("BRAK ");
            else{
                int suma=0,index=0;
                while(suma != masa){
                    Rec += " " + t[Stack[index]];
                    suma += t[Stack[index]];
                    index += 1;
                }
                for(int p=0;p<=t.length;p++){
                    Stack[p] = 0;
                }
                pakowanie_iter(masa,Stack,t);
                suma=0;
                index=0;
                while(suma != masa){
                    Iter += " " + t[Stack[index]];
                    suma += t[Stack[index]];
                    index += 1;
                }
                System.out.println(Rec);
                System.out.println(Iter);
            }
            sets -= 1;
        }
    }
}
//        Test:
//        3
//        1
//        5
//        1 1 1 1 1
//        12
//        3
//        6 2 6
//        13
//        6
//        5 5 4 3 3 1
//        ODP:
//        REC: 1 = 1
//        ITER: 1 = 1
//        REC: 12 = 6 6
//        ITER: 12 = 6 6
//        REC: 13 = 5 5 3
//        ITER: 13 = 5 5 3
