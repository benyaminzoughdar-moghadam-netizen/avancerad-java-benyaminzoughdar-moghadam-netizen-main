package se.gritacademy;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class MainClient {

    private static final String BASE_URL = "http://localhost:8080/ads";

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

                case "1":
                    listAds();
                    break;

                case "2":
                    showAd(scanner);
                    break;

                case "3":
                    createAd(scanner);
                    break;

                case "4":
                    updatePrice(scanner);
                    break;

                case "5":
                    deleteAd(scanner);
                    break;

                case "0":
                    System.out.println("Hejdå!");
                    return;

                default:
                    System.out.println("Ogiltigt val");
            }
        }
    }

    private static void listAds() {
        HttpResponse response = request("GET", BASE_URL, null);
        printResponse(response);
    }

    private static void showAd(Scanner scanner) {

        System.out.print("Ange annonsens ID: ");
        String id = scanner.nextLine();

        HttpResponse response = request("GET", BASE_URL + "/" + id, null);
        printResponse(response);
    }

    private static void createAd(Scanner scanner) {

        System.out.print("Ämnesrad: ");
        String subject = scanner.nextLine().trim();

        System.out.print("Säljare namn: ");
        String sellerName = scanner.nextLine().trim();

        System.out.print("Säljare kontakt (email eller telefon): ");
        String sellerContact = scanner.nextLine().trim();

        System.out.print("Beskrivning: ");
        String description = scanner.nextLine().trim();

        System.out.print("Pris: ");
        String priceInput = scanner.nextLine().trim().replace(",", ".");

        double price;

        try {
            price = Double.parseDouble(priceInput);
        } catch (Exception e) {
            System.out.println("Ogiltigt pris. Skriv t.ex. 500 eller 499.99");
            return;
        }

        String json =
                "{"
                        + "\"subject\":\"" + subject + "\","
                        + "\"sellerName\":\"" + sellerName + "\","
                        + "\"sellerContact\":\"" + sellerContact + "\","
                        + "\"description\":\"" + description + "\","
                        + "\"price\":" + price
                        + "}";

        HttpResponse response = request("POST", BASE_URL, json);
        printResponse(response);
    }

    private static void updatePrice(Scanner scanner) {

        System.out.print("Ange annonsens ID: ");
        String id = scanner.nextLine();

        System.out.print("Nytt pris: ");
        String priceInput = scanner.nextLine().trim().replace(",", ".");

        double price;

        try {
            price = Double.parseDouble(priceInput);
        } catch (Exception e) {
            System.out.println("Ogiltigt pris.");
            return;
        }

        HttpResponse response = request("PUT", BASE_URL + "/" + id + "/price", String.valueOf(price));
        printResponse(response);
    }

    private static void deleteAd(Scanner scanner) {

        System.out.print("Ange annonsens ID: ");
        String id = scanner.nextLine();

        HttpResponse response = request("DELETE", BASE_URL + "/" + id, null);
        printResponse(response);
    }

    private static HttpResponse request(String method, String urlString, String body) {

        try {

            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod(method);
            connection.setRequestProperty("Accept", "application/json");

            if (body != null) {

                connection.setDoOutput(true);
                connection.setRequestProperty("Content-Type", "application/json");

                OutputStream os = connection.getOutputStream();
                os.write(body.getBytes());
                os.flush();
                os.close();
            }

            int status = connection.getResponseCode();

            InputStream inputStream;

            if (status >= 200 && status < 300) {
                inputStream = connection.getInputStream();
            } else {
                inputStream = connection.getErrorStream();
            }

            String responseBody = "";

            if (inputStream != null) {

                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;

                while ((line = reader.readLine()) != null) {
                    responseBody += line + "\n";
                }

                reader.close();
            }

            return new HttpResponse(status, responseBody);

        } catch (Exception e) {

            return new HttpResponse(-1, e.getMessage());
        }
    }

    private static void printResponse(HttpResponse response) {

        System.out.println("HTTP status: " + response.status);

        if (response.body == null || response.body.trim().isEmpty()) {
            System.out.println("(tomt svar)");
        } else {
            System.out.println(response.body);
        }
    }

    private static class HttpResponse {

        int status;
        String body;

        HttpResponse(int status, String body) {
            this.status = status;
            this.body = body;
        }
    }
}