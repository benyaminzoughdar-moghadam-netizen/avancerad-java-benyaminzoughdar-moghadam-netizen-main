package se.gritacademy;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class MainClient {

    private static final String BASE_URL = "http://127.0.0.1:8080/ads";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            printMenu();
            System.out.print("Välj: ");
            String choice = scanner.nextLine().trim();

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
                    System.out.println("Ogiltigt val.");
                    break;
            }
        }
    }

    private static void printMenu() {
        System.out.println("\n===== Fulköpings Köp & Sälj =====");
        System.out.println("1. Lista alla annonser");
        System.out.println("2. Visa annons");
        System.out.println("3. Skapa annons");
        System.out.println("4. Ändra pris");
        System.out.println("5. Radera annons");
        System.out.println("0. Avsluta");
    }

    // 1) GET /ads
    private static void listAds() {
        HttpResponse res = request("GET", BASE_URL, null);
        printResponse(res);
    }

    // 2) GET /ads/{id}
    private static void showAd(Scanner scanner) {
        Long id = readLong(scanner, "Ange annonsens ID: ");
        if (id == null) return;

        HttpResponse res = request("GET", BASE_URL + "/" + id, null);
        printResponse(res);
    }

    // 3) POST /ads
    private static void createAd(Scanner scanner) {
        System.out.print("Ämnesrad: ");
        String subject = scanner.nextLine().trim();

        System.out.print("Säljare namn: ");
        String sellerName = scanner.nextLine().trim();

        System.out.print("Säljare kontakt (email eller telefon): ");
        String sellerContact = scanner.nextLine().trim();

        System.out.print("Beskrivning: ");
        String description = scanner.nextLine().trim();

        Double price = readDouble(scanner, "Pris: ");
        if (price == null) return;

        // JSON som matchar en vanlig Ad-modell
        String json = "{"
                + "\"subject\":\"" + escapeJson(subject) + "\","
                + "\"sellerName\":\"" + escapeJson(sellerName) + "\","
                + "\"sellerContact\":\"" + escapeJson(sellerContact) + "\","
                + "\"description\":\"" + escapeJson(description) + "\","
                + "\"price\":" + price
                + "}";

        HttpResponse res = request("POST", BASE_URL, json);
        printResponse(res);
    }

    // 4) PUT /ads/{id}  (uppdatera pris)
    private static void updatePrice(Scanner scanner) {
        Long id = readLong(scanner, "Ange annonsens ID: ");
        if (id == null) return;

        Double newPrice = readDouble(scanner, "Nytt pris: ");
        if (newPrice == null) return;

        // Skickar bara priset (servern kan välja att uppdatera bara detta)
        String json = "{"
                + "\"price\":" + newPrice
                + "}";

        HttpResponse res = request("PUT", BASE_URL + "/" + id, json);
        printResponse(res);
    }

    // 5) DELETE /ads/{id}
    private static void deleteAd(Scanner scanner) {
        Long id = readLong(scanner, "Ange annonsens ID: ");
        if (id == null) return;

        HttpResponse res = request("DELETE", BASE_URL + "/" + id, null);
        printResponse(res);
    }

    // ---------------- HTTP helper ----------------

    private static HttpResponse request(String method, String urlStr, String bodyJson) {
        try {
            URL url = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod(method);
            conn.setRequestProperty("Accept", "application/json");

            if (bodyJson != null) {
                conn.setDoOutput(true);
                conn.setRequestProperty("Content-Type", "application/json; charset=utf-8");
                byte[] bytes = bodyJson.getBytes(StandardCharsets.UTF_8);
                OutputStream os = conn.getOutputStream();
                os.write(bytes);
                os.flush();
                os.close();
            }

            int status = conn.getResponseCode();

            BufferedReader reader;
            if (status >= 200 && status < 300) {
                reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));
            } else {
                // Om servern skickar fel, läs errorStream om den finns
                if (conn.getErrorStream() != null) {
                    reader = new BufferedReader(new InputStreamReader(conn.getErrorStream(), StandardCharsets.UTF_8));
                } else {
                    reader = null;
                }
            }

            StringBuilder sb = new StringBuilder();
            if (reader != null) {
                String line;
                while ((line = reader.readLine()) != null) {
                    sb.append(line).append("\n");
                }
                reader.close();
            }

            conn.disconnect();
            return new HttpResponse(status, sb.toString().trim());

        } catch (Exception e) {
            return new HttpResponse(-1, "Kunde inte ansluta till servern: " + e.getMessage());
        }
    }

    private static void printResponse(HttpResponse res) {
        if (res.status == -1) {
            System.out.println(res.body);
            return;
        }
        System.out.println("HTTP status: " + res.status);
        if (res.body == null || res.body.trim().isEmpty()) {
            System.out.println("(tomt svar)");
        } else {
            System.out.println(res.body);
        }
    }

    // ---------------- Input helpers ----------------

    private static Long readLong(Scanner scanner, String prompt) {
        System.out.print(prompt);
        String s = scanner.nextLine().trim();
        try {
            return Long.parseLong(s);
        } catch (Exception e) {
            System.out.println("Ogiltigt nummer.");
            return null;
        }
    }

    private static Double readDouble(Scanner scanner, String prompt) {
        System.out.print(prompt);
        String s = scanner.nextLine().trim().replace(",", ".");
        try {
            return Double.parseDouble(s);
        } catch (Exception e) {
            System.out.println("Ogiltigt pris.");
            return null;
        }
    }

    private static String escapeJson(String s) {
        return s.replace("\\", "\\\\").replace("\"", "\\\"");
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