package pl.marcinzygmunt;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.model.HttpRequest;
import org.mockserver.model.HttpResponse;
import pl.marcinzygmunt.domain.model.Numbers;
import pl.marcinzygmunt.domain.service.SortingService;
import pl.marcinzygmunt.infrastucture.data.DataProvider;
import pl.marcinzygmunt.infrastucture.data.FileDataProvider;
import pl.marcinzygmunt.infrastucture.data.HttpDataProvider;
import pl.marcinzygmunt.infrastucture.sorting.BubbleSort;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SortApplicationIT {

    private static ClientAndServer mockServer;

    @BeforeAll
    static void startServer() {
        mockServer = ClientAndServer.startClientAndServer(1080);

        mockServer.when(
                HttpRequest.request()
                        .withMethod("GET")
                        .withPath("/dane/lista.json")
        ).respond(
                HttpResponse.response()
                        .withStatusCode(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{ \"elements\": [9, 5, 7, 3, 1] }")
        );
    }

    @AfterAll
    static void stopServer() {
        mockServer.stop();
    }

    @Test
    void application_shouldFetchFromHttpSortAndMeasureTime() {

        List<DataProvider> providers = List.of(
                new FileDataProvider("non_existing_file.json"), // nie istnieje => fallback na HTTP
                new HttpDataProvider("http://localhost:1080/dane/lista.json")
        );

        Optional<Numbers> numbers = providers.stream()
                .map(DataProvider::fetchData)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .findFirst();


        assertTrue(numbers.isPresent(), "Dane powinny być dostępne");
        assertEquals(List.of(9, 5, 7, 3, 1), numbers.get().elements(), "Dane powinny być poprawnie pobrane");

        SortingService sortingService = new SortingService(new BubbleSort());

        long startTime = System.nanoTime();
        Numbers sorted = sortingService.sort(numbers.get());
        long endTime = System.nanoTime();
        long durationMs = (endTime - startTime) / 1_000_000;

        assertEquals(List.of(1, 3, 5, 7, 9), sorted.elements(), "Dane powinny być poprawnie posortowane rosnąco");
        assertTrue(durationMs >= 0, "Czas sortowania powinien być dodatni");

    }

    @Test
    void application_shouldFetchFromFileSortAndMeasureTime() {
        List<DataProvider> providers = List.of(
                new HttpDataProvider("http://localhost:1080/non_existing/lista.json"), // 404 => fallback na file
                new FileDataProvider("lista.json") // istnieje

        );

        Optional<Numbers> numbers = providers.stream()
                .map(DataProvider::fetchData)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .findFirst();


        assertTrue(numbers.isPresent(), "Dane powinny być dostępne");
        assertEquals(List.of(5, 3, 8, 1, -2, 0), numbers.get().elements(), "Dane powinny być poprawnie pobrane");

        SortingService sortingService = new SortingService(new BubbleSort());

        long startTime = System.nanoTime();
        Numbers sorted = sortingService.sort(numbers.get());
        long endTime = System.nanoTime();
        long durationMs = (endTime - startTime) / 1_000_000;

        assertEquals(List.of(-2, 0, 1, 3, 5, 8), sorted.elements(), "Dane powinny być poprawnie posortowane rosnąco");
        assertTrue(durationMs >= 0, "Czas sortowania powinien być dodatni");

    }
}