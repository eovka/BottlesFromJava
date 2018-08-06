package pl.pisze_czytam;

import java.util.ArrayList;
import java.util.Scanner;

class Main {
    private static ArrayList<Butelka> zbiorButelek = new ArrayList<Butelka>();

    static class Butelka {
        private double pojemnosc;
        private double wypelnienie;
        private static int numerButelki = 1;

        Butelka(double pojemnosc, double wypelnienie) {
            if (wypelnienie > pojemnosc) {
                System.out.println("Nie możesz wlać do butelki więcej, niż wynosi jej pojemność. Butelka jest pełna.");
                wypelnienie = pojemnosc;
            }
            this.pojemnosc = pojemnosc;
            this.wypelnienie = wypelnienie;
            numerButelki++;
        }

        double getPojemnosc() {
            return pojemnosc;
        }
        double getWypelnienie() {
            return wypelnienie;
        }
        int getNumerButelki() {
            return numerButelki;
        }

        // TODO: przesunąć wydruki poza funkcję, by były bardziej uniwersalne przy przelej?
        void wlej(double wlewane) {
            if (wlewane < 0) {
                System.out.println("Nie możesz wlać mniej niż zero litrów. Jeśli chcesz coś wylać z butelki, użyj funkcji \"wylej\".");
                return;
            }
            if (wlewane + wypelnienie <= pojemnosc) {
                wypelnienie += wlewane;
                double zostalo = pojemnosc - wypelnienie;
                System.out.println("Przelano " + wlewane + " l. Możesz dolać jeszcze " + zostalo + " l.");
            } else {
                System.out.println("Nie można wlać aż tyle. Dolano " + (pojemnosc - wypelnienie) + " l do pełna.");
                wypelnienie = pojemnosc;
            }
        }

        void wylej(double wylewane) {
            if (wylewane < 0) {
                System.out.println("Nie możesz wylać mniej niż zero litrów. Jeśli chcesz  wlać coś do butelki, użyj funkcji \"wlej\".");
                return;
            }
            if (wylewane > wypelnienie) {
                System.out.println("Nie można wylać " + wylewane + " l. Wylewam całą zawartość butelki, czyli " + wypelnienie + " l.");
                wypelnienie = 0;
            } else {
                wypelnienie -= wylewane;
                System.out.println("Wylano " + wylewane + " l. W butelce zostało " + wypelnienie + " l.");
            }
        }
        // TODO: do sprawdzenia, czy nie trzeba rozwinąć / zmienić info dla użytkownika / zmienić samą funkcję
        void przelej(Butelka butelkaZ, Butelka butelkaDo, double przelewane) {
            butelkaZ.wylej(przelewane);
            butelkaDo.wlej(przelewane);
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        stworzButelke(scanner);
        System.out.println("Naciśnij enter, by przejść dalej.");
        scanner.nextLine();
        spytajCoDalej(scanner);

        scanner.close();
    }

    private static void spytajCoDalej(Scanner scanner) {
        System.out.println("Co chcesz zrobić teraz? Wpisz odpowiednią cyfrę.");
        System.out.println("1 - Stwórz jeszcze jedną butelkę.");
        System.out.println("2 - Sprawdź pojemność lub zawartość wybranej butelki.");
        System.out.println("3 - Wlej coś do butelki.");
        System.out.println("4 - Wylej coś z butelki.");
        System.out.println("5 - Przelej coś z jednej butelki do drugiej.");
        System.out.println("6 - Zobacz zestawienie wszystkich swoich butelek.");
        if (scanner.hasNextInt()) {
            int komenda = scanner.nextInt();
            // TODO: switch na poszczególne komendy -> przejście do metod (liczba spoza zakresu - nieprawidłowa komenda, powtórz
            switch (komenda) {
                case 1:
                    stworzButelke(scanner);
                    break;
                case 5:
                    switch (zbiorButelek.size()) {
                        case 0:
                            System.out.println("Nie wiem, skąd i dokąd chcesz przelewać płyn - nie masz ani jednej butelki.");
                            break;
                        case 1:
                            System.out.println("Nic nie przelejesz - masz tylko jedną butelkę. \nMożesz spróbować coś do niej wlać lub z niej wylać.");
                            break;
                        default:
                            przelej(scanner);
                    }
                    break;
                case 6:
                    for (Butelka butelka : zbiorButelek) {
                        System.out.println("BUTELKA " + butelka.getNumerButelki());
                        System.out.println("Pojemność: " + butelka.getPojemnosc());
                        System.out.println("Aktualna zawartość: " + butelka.getWypelnienie());
                        System.out.println("-------------------------");
                    }
                    break;
                default:
                    System.out.println("Nie wiem, co robić. Wyraź się jaśniej.");
                    scanner.next();
                    spytajCoDalej(scanner);
            }
        } else {
            System.out.println("Nieprawidłowa komenda.");
            scanner.next();
            spytajCoDalej(scanner);
        }
    }

    private static void stworzButelke(Scanner input) {
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
        System.out.println("Stworzyłeś pierwszą butelkę o pojemności " + pojemnosc + " l z " + wypelnienie + " l płynu. "
                + "Będziesz mógł dolać jeszcze " + (pojemnosc - wypelnienie) + " l.");
        System.out.println("Gdy będziesz chciał potem z niej skorzystać, przy wyborze butelki wpisz " + butelka.getNumerButelki() + ".");
        input.nextLine();
        zbiorButelek.add(butelka);
    }

    private static void przelej(Scanner scanner) {
        System.out.println("Z której butelki chcesz coś przelać?");
        // TODO funkcja ktoraButelka - zwrotka obiekt Butelki
        String butelkaZniej = scanner.nextLine();
        int numerButelki1 = 0;
        if (butelkaZniej.contains("butelka ")) {
            String[] tnijButelke = butelkaZniej.split("butelka ");
            numerButelki1 = Integer.parseInt(tnijButelke[1]);
        } else {
            System.out.println("Aby wybrać butelkę, wpisz poprawną komendę, \"butelka + numer\", np. \"butelka 2\".");
            // TODO: powtarzaj póki ktoś nie wpisze dobrze / daj możliwość wyjścia z komendy.
        }
        if (numerButelki1 >= zbiorButelek.size()) {
            System.out.println("Nie ma takiej butelki.");
            // TODO: powtórz pytanie o numer butelki, ewentualnie daj możliwość wyjścia.
        }
        Butelka butelkaZ = zbiorButelek.get(numerButelki1 - 1);

        System.out.println("Ile chcesz przelać?");
        double przelewane = 0.0;
        if (scanner.hasNextDouble()) {
            przelewane = scanner.nextDouble();
        }
        // TODO: drugi raz funkcja ktora butelka - zwrotka butelka "do"
        // TODO: dokończyć przelewanie.
    }
}
