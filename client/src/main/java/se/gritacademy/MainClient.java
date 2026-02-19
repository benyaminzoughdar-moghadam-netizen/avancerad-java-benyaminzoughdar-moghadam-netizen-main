package se.gritacademy;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class MainClient {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n===== Fulköpings Köp & Sälj =====");
            System.out.println("1. Lista alla annonser");
            System.out.println("0. Avsluta");
            System.out.print("Välj: ");

            String choice = scanner.nextLine();

            if ("1".equals(choice)) {
                listAdsFromServer();
            } else if ("0".equals(choice)) {
                System.out.println("Hejdå!");
                break;
            } else {
                System.out.println("Ogiltigt val.");
            }
        }
    }

    private static void listAdsFromServer() {
        try {
            URL url = new URL("http://localhost:8080/ads");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            int status = conn.getResponseCode();
            System.out.println("HTTP status: " + status);

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(conn.getInputStream())
            );

            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

            reader.close();
            conn.disconnect();

        } catch (Exception e) {
            System.out.println("Kunde inte ansluta till servern.");
        }
    }
}
// commit: client talks to server
