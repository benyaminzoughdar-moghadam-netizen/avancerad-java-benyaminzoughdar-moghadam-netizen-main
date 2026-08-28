# Fulköpings Köp & Sälj

## Beskrivning

Detta projekt är en enkel köp- och sälj-tjänst.

Projektet består av:

* **Server:** Java Spring Boot REST API
* **Client:** Java konsolapplikation som skickar HTTP-requests

Servern lagrar annonser i minnet (HashMap).

---

## Funktioner

Klienten kan:

1. Lista alla annonser
2. Visa en annons via ID
3. Skapa en ny annons
4. Ändra pris
5. Radera annons

---

## REST API

| Method | Endpoint        | Funktion       |
| ------ | --------------- | -------------- |
| GET    | /ads            | Lista annonser |
| GET    | /ads/{id}       | Visa annons    |
| POST   | /ads            | Skapa annons   |
| PUT    | /ads/{id}/price | Uppdatera pris |
| DELETE | /ads/{id}       | Radera annons  |

---

## Starta server

Kör:

ServerApplication.java

Servern startar på:

http://localhost:8080

---

## Starta klient

Kör:

MainClient.java

---

## Exempel JSON

```json
{
  "subject": "Cykel",
  "sellerName": "Beny",
  "sellerContact": "beny@gmail.com",
  "description": "Mountainbike",
  "price": 500
}
```
