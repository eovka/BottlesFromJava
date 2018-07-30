package pl.pisze_czytam;

import java.util.Scanner;

class Main {
    static class Butelka {
        private double pojemnosc;
        private double wypełnienie;
        private static int numerButelki = 1;
        private String nazwaButelki = "butelka " + numerButelki;

        Butelka(double pojemnosc, double wypełnienie) {
            if (wypełnienie > pojemnosc) {
                System.out.println("Nie możesz wlać do butelki więcej, niż wynosi jej pojemność. Butelka jest pełna.");
                wypełnienie = pojemnosc;
            }
            this.pojemnosc = pojemnosc;
            this.wypełnienie = wypełnienie;
            numerButelki++;
        }

        double getPojemnosc() {
            return pojemnosc;
        }

        double getWypełnienie() {
            return wypełnienie;
        }
        int getNumerButelki() {
            return numerButelki;
        }
        String getNazwaButelki() {
            return nazwaButelki;
        }

        void wlej(double wlewane) {
            if (wlewane < 0) {
                System.out.println("Nie możesz wlać mniej niż zero litrów. Jeśli chcesz coś wylać z butelki, użyj funkcji \"wylej\".");
                return;
            }
            if (wlewane + wypełnienie <= pojemnosc) {
                wypełnienie += wlewane;
                double zostalo = pojemnosc - wypełnienie;
                System.out.println("Przelano " + wlewane + " l. Możesz dolać jeszcze " + zostalo + " l.");
            } else {
                System.out.println("Nie można wlać aż tyle. Dolano " + (pojemnosc - wypełnienie) + " l do pełna.");
                wypełnienie = pojemnosc;
            }
        }

        void wylej(double wylewane) {
            if (wylewane < 0) {
                System.out.println("Nie możesz wylać mniej niż zero litrów. Jeśli chcesz  wlać coś do butelki, użyj funkcji \"wlej\".");
                return;
            }
            if (wylewane > wypełnienie) {
                System.out.println("Nie można wylać " + wylewane + " l. Wylewam całą zawartość butelki, czyli " + wypełnienie + " l.");
                wypełnienie = 0;
            } else {
                wypełnienie -= wylewane;
                System.out.println("Wylano " + wylewane + " l. W butelce zostało " + wypełnienie + " l.");
            }
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Butelka butelka1 = stworzButelke(scanner);
        // TODO: oddzielna funkcja "co zrobić dalej?"
        System.out.println("Naciśnij enter, by przejść dalej.");
        scanner.nextLine();
        System.out.println("Co chcesz zrobić teraz? Wpisz odpowiednią cyfrę.");
        System.out.println("1 - Stwórz jeszcze jedną butelkę.");
        System.out.println("2 - Sprawdź pojemność lub zawartość butelki.");
        System.out.println("3 - Wlej coś do butelki.");
        System.out.println("4 - Wylej coś z butelki.");
        System.out.println("5 - Przelej coś z jednej butelki do drugiej.");
        if (scanner.hasNextInt()) {
            int komenda = scanner.nextInt();
            // TODO: switch na poszczególne komendy -> przejście do metod (liczba spoza zakresu - nieprawidłowa komenda, powtórz
        } else {
            System.out.println("Nieprawidłowa komenda.");
            // TODO: powtórz zapytanie
        }
        scanner.close();
    }

    private static Butelka stworzButelke(Scanner input) {
        double pojemnosc;
        do {
            System.out.println("Aby stworzyć butelkę, podaj jej pojemność.");
            pojemnosc = input.nextDouble();
             if (pojemnosc < 0) {
                 System.out.println("Butelka mnie może mieć mniej niż 0.");
             }
        } while (pojemnosc < 0);
        input.nextLine();

            System.out.println("Ile litrów chcesz do niej wlać na początek?");
            double wypelnienie = input.nextDouble();
            if (wypelnienie < 0) {
                System.out.println("Nie możesz wlać mniej niż zero litrów. Butelka będzie pusta, dopóki w nią czegoś nie wlejesz.");
                wypelnienie = 0;
            }

        Butelka butelka = new Butelka(pojemnosc, wypelnienie);
        String nazwaButelki = butelka.getNazwaButelki();
        System.out.println("Stworzyłeś pierwszą butelkę o pojemności " + pojemnosc + " l z " + wypelnienie + " l płynu. "
                + "Będziesz mógł dolać jeszcze " + (pojemnosc - wypelnienie) + " l.");
        System.out.println("Gdy będziesz chciał potem z niej skorzystać, wpisz \"" + nazwaButelki + "\".");
        input.nextLine();
        return butelka;
    }
}
