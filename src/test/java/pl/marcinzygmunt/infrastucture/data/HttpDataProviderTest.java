package pl.marcinzygmunt.infrastucture.data;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.model.HttpRequest;
import org.mockserver.model.HttpResponse;
import pl.marcinzygmunt.domain.model.Numbers;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class HttpDataProviderTest {

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
                        .withBody("{ \"elements\": [4, 7, 1, -3, 2] }")
        );
    }

    @AfterAll
    static void stopServer() {
        mockServer.stop();
    }

    @Test
    void fetchData_shouldFetchNumbersFromHttp() {
        HttpDataProvider provider = new HttpDataProvider("http://localhost:1080/dane/lista.json");
        Optional<Numbers> result = provider.fetchData();
        assertTrue(result.isPresent());
        assertEquals(5, result.get().elements().size());
        assertEquals(4, result.get().elements().get(0));
    }
}
