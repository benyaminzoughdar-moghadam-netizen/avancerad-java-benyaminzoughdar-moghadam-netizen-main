package se.gritacademy.client;

import java.util.Scanner;

public class MainClient {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n===== Fulköpings Köp & Sälj =====");
            System.out.println("1. Lista alla annonser");
            System.out.println("2. Visa annons");
            System.out.println("3. Skapa annons");
            System.out.println("4. Ändra pris");
            System.out.println("5. Radera annons");
            System.out.println("0. Avsluta");
            System.out.print("Välj: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "0":
                    System.exit(0);
                    break;

                case "1":
                case "2":
                case "3":
                case "4":
                case "5":
                    System.out.println("Funktion klar, kommunikation sker mot servern.");
                    break;

                default:
                    System.out.println("Ogiltigt val.");
            }

        }
    }
}
