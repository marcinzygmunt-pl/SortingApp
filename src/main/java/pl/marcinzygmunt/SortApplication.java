package pl.marcinzygmunt;

import lombok.extern.slf4j.Slf4j;
import pl.marcinzygmunt.application.SortNumbersService;
import pl.marcinzygmunt.domain.model.Numbers;
import pl.marcinzygmunt.domain.port.SortingAlgorithm;
import pl.marcinzygmunt.domain.service.SortingService;
import pl.marcinzygmunt.infrastucture.data.DataProvider;
import pl.marcinzygmunt.infrastucture.data.FileDataProvider;
import pl.marcinzygmunt.infrastucture.data.HttpDataProvider;
import pl.marcinzygmunt.infrastucture.sorting.BubbleSort;
import pl.marcinzygmunt.infrastucture.sorting.InsertionSort;
import pl.marcinzygmunt.infrastucture.util.SortingTimer;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Slf4j
public class SortApplication {

    private static final String FILE_PATH = "lista.json";
    private static final String HTTP_URL = "https://somedomain.org/data/lista.json";
    private static final SortingAlgorithm SORTING_ALGORITHM = new BubbleSort();
    //private static final SortingAlgorithm SORTING_ALGORITHM = new InsertionSort();


    public static void main(String[] args) {
        List<DataProvider> providers = Arrays.asList(
                new FileDataProvider(FILE_PATH),
                new HttpDataProvider(HTTP_URL)
        );

        Optional<Numbers> numbers = providers.stream()
                .map(DataProvider::fetchData)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .findFirst();

        if (numbers.isEmpty()) {
            log.info("No data to process.");
            return;
        }

        SortingTimer timer = new SortingTimer();
        SortNumbersService sortService = new SortNumbersService(new SortingService(SORTING_ALGORITHM));

        timer.start();
        Numbers sorted = sortService.execute(numbers.get());
        timer.stop();

        log.info("Sorted data: {}", sorted.elements());
        log.info("Sorting time: {} ms ({} ns)",timer.getElapsedTimeMillis(), timer.getElapsedTimeNanos());
    }
}