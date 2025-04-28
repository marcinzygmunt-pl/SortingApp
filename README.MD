# SortingApp

## Opis projektu
SortingApp to aplikacja Java umożliwiająca:
- Pobieranie listy liczb całkowitych z różnych źródeł danych,
- Sortowanie pobranych danych przy użyciu wybranego algorytmu,
- Pomiar czasu trwania operacji sortowania (oddzielony od logiki sortowania).

## Funkcjonalności
- Obsługa danych z pliku lub zdalnego zasobu HTTP,
- Wybór algorytmu sortowania Bubble Sorting / Insertion Sorting,
- Pomiar czasu operacji sortowania,
- Logowanie z użyciem SLF4J,
- Modularna architektura DDD.

## Wymagania
- Java 17+
- Maven 3.8+

## Uruchamianie aplikacji
Budowanie i uruchamianie:
```bash
mvn clean install
java -jar target/sorting-app.jar