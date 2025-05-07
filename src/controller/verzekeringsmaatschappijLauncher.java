package controller;
/*
* Student: Sebastiaan Schneider
* Studentnummer: 500612720
*
* Dit programma berekent de premie voor ingevoerde auto's, geeft vervolgens een
* overzicht weer en vertelde de gebruiker hoeveel van de premies onder de 1000 euro
* vallen.
*/

import java.util.Scanner;

public class verzekeringsmaatschappijLauncher {
    public static void main(String[] args) {
        System.out.println("Dit is de uitwerking van Sebastiaan Schneider");

        // magic numbers
        final int MIN_WAARDE = 1000;
        final int MIN_JAREN = 0;
        final int MAX_JAREN = 40;

        // Scanner om input mee te krijgen
        Scanner input = new Scanner(System.in);

        // vraag om hoeveel auto's er worden ingevoerd en initialisatie van array's
        System.out.print("Hoeveel autopolissen wil je registreren? ");
        int aantalPolissen = input.nextInt();
        String[] naamLijst = new String[aantalPolissen];
        int[] waardeLijst = new int[aantalPolissen];
        int[] jarenLijst = new int[aantalPolissen];
        double[] premieLijst = new double[aantalPolissen];

        // voor elk van de auto's wordt de naam van de eigenaar, waarde en aantal blabla
        // schadevrije jaren gevraagd, die vervolgens in de array's worden opgeslagen
        for (int i = 0; i < aantalPolissen; i++) {
            System.out.println("Autopolis " + (i + 1));
            System.out.print("    Naam polishouder: ");
            String naam = input.next();
            naamLijst[i] = naam;

            // do-while loop met check voor geldige waardes
            int waarde;
            do {
                System.out.print("    Waarde van de auto in euro’s: ");
                waarde = input.nextInt();
                waardeLijst[i] = waarde;

                if (waarde < MIN_WAARDE) {
                    System.out.println("    De waarde moet minimaal " + MIN_WAARDE +
                            " euro zijn!");
                }
            } while (waarde < MIN_WAARDE);

            // do-while loop met check voor geldige waardes
            int jaren;
            do {
                System.out.print("    Aantal schadevrije jaren: ");
                jaren = input.nextInt();
                jarenLijst[i] = jaren;

                if (jaren < 0) {
                    System.out.println("Het aantal schadevrije jaren moet minimaal " +
                            MIN_JAREN + " zijn!");
                } else if (jaren > 40) {
                    System.out.println("Het aantal schadevrije jaren mag maximaal " +
                            MAX_JAREN + " zijn!");
                }
            } while (jaren < 0 || jaren > 40);

            // berekening van de premie via externe methode
            double premie = berekenJaarpremie(waarde, jaren);
            premieLijst[i] = premie;
        }

        // print van het overzicht
        System.out.println("Overzicht van de autopolissen");
        System.out.println("polishouder     waarde  aantal jaren    jaarpremie");
        for (int i = 0; i < aantalPolissen; i++) {
            System.out.printf("%-14s %7d %13d %13.2f\n", naamLijst[i], waardeLijst[i], jarenLijst[i], premieLijst[i]);
        }

        // berekening aantal premies onder de 1000 euro via externe methode
        int aantalOnder = bepaalLageJaarPremies(premieLijst, MIN_WAARDE);
        System.out.println("\nAantal premies lager dan 1000 euro: " + aantalOnder);
    }

    // methode om de premie te berekenen
    public static double berekenJaarpremie(int mpWaarde, int mpAantalSchadevrijeJaren) {
        // magic number voor korting als vermenigvuldiging
        final double STANDAAR_PREMIE = 0.1; // 10%
        final double KORTING_PER_JAAR = 0.05; // 5%
        final double MAX_KORTING = 0.70; // 70%

        // berekening totale korting op basis van schadevrije jaren met max van 70%
        double kortingProcent = mpAantalSchadevrijeJaren * KORTING_PER_JAAR;
        if (kortingProcent > MAX_KORTING) {
            kortingProcent = MAX_KORTING;
        }
        // omrekening om de double als vermenuigvuldiging te kunnen gebruiken
        double korting = 1 - kortingProcent;

        // berekening premie
        double premie = mpWaarde * (STANDAAR_PREMIE * korting);

        // afronding op 2 decimalen
        return (double) Math.round(premie * 100.0) / 100.0;
    }

    // methode om het aantal premies onder de 1000 euro te berekenen
    public static int bepaalLageJaarPremies(double[] mpJaarPremies, int mpGrenswaardeLaag) {
        int aantalOnder = 0;

        // itereert over de premie array en checkt elke waarde of die onder de 1000 is
        for (double premie : mpJaarPremies) {
            if (premie < mpGrenswaardeLaag) {
                aantalOnder++;
            }
        }
        return aantalOnder;
    }
}
