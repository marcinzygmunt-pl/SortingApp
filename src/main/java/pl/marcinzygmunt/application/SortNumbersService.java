package pl.marcinzygmunt.application;

import pl.marcinzygmunt.domain.model.Numbers;
import pl.marcinzygmunt.domain.service.SortingService;

public class SortNumbersService implements SortNumbers {

        private final SortingService sortingService;

        public SortNumbersService(SortingService sortingService) {
            this.sortingService = sortingService;
        }

        @Override
        public Numbers execute(Numbers numbers) {
            return sortingService.sort(numbers);
        }
}
