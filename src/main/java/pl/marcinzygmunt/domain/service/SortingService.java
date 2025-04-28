package pl.marcinzygmunt.domain.service;

import pl.marcinzygmunt.domain.model.Numbers;
import pl.marcinzygmunt.domain.port.SortingAlgorithm;

public class SortingService {

    private final SortingAlgorithm algorithm;

    public SortingService(SortingAlgorithm algorithm) {
        this.algorithm = algorithm;
    }

    public Numbers sort(Numbers numbers) {
        return algorithm.sort(numbers);
    }
}

