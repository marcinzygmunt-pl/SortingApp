package pl.marcinzygmunt.infrastucture.data;

import org.junit.jupiter.api.Test;
import pl.marcinzygmunt.domain.model.Numbers;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class FileDataProviderTest {

        @Test
        void fetchData_shouldLoadNumbersFromClasspathFile() {
            FileDataProvider provider = new FileDataProvider("lista.json");

            Optional<Numbers> result = provider.fetchData();

            assertTrue(result.isPresent(), "Dane powinny zostać poprawnie wczytane");
            Numbers numbers = result.get();
            assertNotNull(numbers.elements(), "Lista elementów nie powinna być null");
            assertFalse(numbers.elements().isEmpty(), "Lista elementów nie powinna być pusta");

            assertEquals(Integer.valueOf(5), numbers.elements().get(0));
        }

        @Test
        void shouldReturnEmptyWhenFileDoesNotExist() {
            FileDataProvider provider = new FileDataProvider("brak_pliku.json");

            Optional<Numbers> result = provider.fetchData();

            assertTrue(result.isEmpty(), "Brakujący plik powinien zwrócić pusty Optional");
        }

}