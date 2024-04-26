//Filip Rutka gr-1

import java.util.Scanner;

public class Source {
    public static Scanner scn = new Scanner(System.in);

    static void swap(int[] t,int p,int q ) { //funkcja zamieniaja dwa elementy w tablicy
        int temp = t[p];
        t[p] = t[q];
        t[q] = temp;
    }
    static int Selection(int[] t, int l,int r) { //Selection sort z wykladu
        for(int g=l; g<r-1;g++) {
            int index = g;
            for (int j = g + 1; j < r; j++) {
                if (t[j] < t[index]) index = j;
            }
            swap(t,index,g);
        }
        return (l + r)/2;
    }
    static int partition(int[] t, int l, int r,int M) { //partiotion w wersji lomuto
        int i = (l - 1);
        for(int j = l; j < r; j++) {
            if (t[j] <= M) {
                i++;
                swap(t, i, j);
            }
        }
        swap(t, i + 1, r);
        return i+1;
    }

    public static int Select(int[] t, int l, int r, int k) { //glowna funkcja liczaca magiczne piatki

        int ilosc = (r - l + 1) / 5; //wylicz ile bedzie podzialow tablicy na zbiory 5 elementowe
        int index_median = 0; //indeks oznaczajacy gdzie powinna zostac wstawiona kolejna mediana
        for (int i = 0; i < ilosc; i++) { //posortuj zbiory 5 elementowe
            swap(t,l+ index_median, Selection(t, l + i * 5, l + i * 5 + 4));
            index_median += 1;
        }
        if ((r - l + 1) % 5 > 0) { //posortuj koncowke tablicy ktora niekoniecznie ma 5 elementow
            swap(t,l+ index_median, Selection(t, l + ilosc * 5, r));
            index_median += 1;
        }
        int M;
        if(index_median==1) M = t[l]; //mediana jest tylko jedna
        else M = Select(t,r-index_median,r,index_median/2); //rekurencyjne wywolanie magicznych piatek aby znalezc mediane median

        int place = l + k - 1; // zmienna z informacja o indeksie k-tego elementu
        int i = l;
        while(i < r && t[i] != M) { //znajdz element o wartosci M (mediana median) i wrzuc go na koniec aby byl pivotem w partition
            i++;
        }
        swap(t,i,r);
        int pivot = partition(t,l,r,M); //indeks pivota (mediany median)

        if(pivot == place) return t[pivot]; //pivot to nasz szukany element k <= |S1| + |S2|
        if(pivot > place) return Select(t, l, pivot - 1, k); //szukany element po lewej stronie od pivota k <= |S1|
        return Select(t,pivot+1,r,place - pivot); // szukany element po prawej stronie od pivota k - |S1| - |S2|
    }
    public static void main(String[] args) {
        int sets = scn.nextInt();
        scn.nextLine();
        while (sets > 0) {
            int n = scn.nextInt();
            int[] tab = new int[n];
            for(int i=0;i<n;i++){
                tab[i] = scn.nextInt();
            }
            int ilosc_szukanych = scn.nextInt();
            int[] szukane = new int[ilosc_szukanych];
            for(int i=0;i<ilosc_szukanych;i++){
                szukane[i] = scn.nextInt();
            }
            for(int i=0;i<ilosc_szukanych;i++) {
                if(szukane[i] < 1 || szukane[i] > tab.length) System.out.println(szukane[i] + " brak");
                else System.out.println(szukane[i] + " " + Select(tab, 0, tab.length - 1, szukane[i]));
            }
            sets -= 1;
        }
    }
}
//TEST IN:
//        3
//        6
//        0 0 0 0 0 0
//        2
//        5 6
//        9
//        1 2 6 8 12 15 19 3 5
//        3
//        3 6 9
//        15
//        1 2 3 4 5 6 7 8 9 10 11 12 13 14 15
//        2
//        0 16
//TEST OUT:
//        5 0
//        6 0
//        3 1
//        6 1
//        9 3
//        0 brak
//        16 brak