package pl.pisze_czytam;

import java.util.ArrayList;
import java.util.Scanner;

class Main {
    private static ArrayList<Butelka> zbiorButelek = new ArrayList<Butelka>();

    static class Butelka {
        private double pojemnosc;
        private double wypelnienie;
        private static int numerButelki = 0;

        Butelka(double pojemnosc, double wypelnienie) {
            if (wypelnienie > pojemnosc) {
                System.out.println("Nie możesz wlać do butelki więcej, niż wynosi jej pojemność. Wypełniam butelkę, reszta płynu przepada.");
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
        // TODO: wstęp - info ogólne + gdy chce przejść dalej (do głównego menu), trzeba wcisnąć "enter".
        stworzButelke(scanner);
        System.out.println("Dziękujemy za zabawę butelkami. Do widzenia!");
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
            // TODO: switch na poszczególne komendy (3, 4 do napisania; 5 do sprawdzenia) + do uporządkowanie do metod, gdy liczba spoza zakresu -> nieprawidłowa komenda, powtórz
            switch (komenda) {
                case 1:
                    stworzButelke(scanner);
                    break;
                case 2:
                    sprawdzButelke(scanner);
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
                    for (int i = 0; i < zbiorButelek.size(); i++) {
                        wydrukujInfoButelki(i);
                        System.out.println("-------------------------");
                    }
                    scanner.nextLine();
                    System.out.println(" ");
                    scanner.nextLine();
                    spytajCoDalej(scanner);
                    break;
                default:
                    System.out.println("Nie wiem, co robić. Wyraź się jaśniej.");
                    scanner.next();
                    System.out.println(" ");
                    scanner.nextLine();
                    spytajCoDalej(scanner);
            }
        } else {
            System.out.println("Nieprawidłowa komenda.");
            scanner.next();
            spytajCoDalej(scanner);
        }
    }

    private static void stworzButelke(Scanner scanner) {
        double pojemnosc;
        do {
            System.out.println("Aby stworzyć butelkę, podaj jej pojemność.");
            // TODO: catch InputMismatchException w przypadku, gdy ktoś wpisze dwa przecinki
            pojemnosc = scanner.nextDouble();
            if (pojemnosc < 0) {
                System.out.println("Butelka mnie może mieć mniej niż 0.");
            }
        } while (pojemnosc < 0);
        scanner.nextLine();


        System.out.println("Ile litrów chcesz do niej wlać na początek?");
        // TODO: catch InputMismatchException w przypadku, gdy ktoś wpisze dwa przecinki
        double wypelnienie = scanner.nextDouble();
        if (wypelnienie < 0) {
            System.out.println("Nie możesz wlać mniej niż zero litrów. Butelka będzie pusta, dopóki w nią czegoś nie wlejesz.");
            wypelnienie = 0;
        } else if (wypelnienie > pojemnosc) {
            wypelnienie = pojemnosc;
        }

        Butelka butelka = new Butelka(pojemnosc, wypelnienie);
        System.out.println("Stworzyłeś pierwszą butelkę o pojemności " + pojemnosc + " l z " + wypelnienie + " l płynu. "
                + "Będziesz mógł dolać jeszcze " + (pojemnosc - wypelnienie) + " l.");
        System.out.println("Gdy będziesz chciał potem z niej skorzystać, przy wyborze butelki wpisz " + butelka.getNumerButelki() + ".");
        zbiorButelek.add(butelka);
        scanner.nextLine();
        System.out.println("Naciśnij enter, by przejść dalej.");
        scanner.nextLine();
        spytajCoDalej(scanner);
    }

    private static void sprawdzButelke(Scanner scanner) {
        if (zbiorButelek.size() == 0) {
            System.out.println("Nie masz żadnych butelek. Nie wiem, co chcesz sprawdzać.");
            spytajCoDalej(scanner);
        } else if (zbiorButelek.size() == 1) {
            System.out.println("Masz jedną butelkę. Jej pojemność to: " + zbiorButelek.get(0).getPojemnosc() + " l i jest w niej " + zbiorButelek.get(0).getWypelnienie() + " l płynu.");
        } else {
            System.out.println("Której butelki pojemność bądź zawartość chcesz sprawdzić? Wpisz numer od 1 do " + zbiorButelek.size() + ".");
            if (scanner.hasNextInt()) {
                int nrButelki = scanner.nextInt();
                if (nrButelki <= 0 || nrButelki > zbiorButelek.size()) {
                    System.out.println("Nie wiem, o którą butelkę Ci chodzi. Żadna butelka z dostępnych nie ma takiego numeru.");
                } else {
                    // TODO: pytanie o to, czego chce się dowiedzieć użytkownik (pojemność, wypełnienie, ile można dolać czy chce wiedzieć wszystko
                    // TODO cd: + być może rozbicie metody "wydrukujInfoButelki" - w jej ramach poszczególne metody drukujące jeden wers)
                    int indeksButelki = nrButelki - 1;
                    wydrukujInfoButelki(indeksButelki);
                    scanner.nextLine();
                    System.out.println(" ");
                    scanner.nextLine();
                    spytajCoDalej(scanner);
                }
            }
        }
    }

    private static void wydrukujInfoButelki(int indeks) {
        System.out.println("BUTELKA " + (indeks + 1));
        System.out.println("Pojemność: " + zbiorButelek.get(indeks).getPojemnosc());
        System.out.println("Aktualna zawartość: " + zbiorButelek.get(indeks).getWypelnienie());
        System.out.println("Możesz jeszcze dolać: " + (zbiorButelek.get(indeks).getPojemnosc() - zbiorButelek.get(indeks).getWypelnienie()) + "l.");
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
            //TODO: numer zamiast komendy
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
