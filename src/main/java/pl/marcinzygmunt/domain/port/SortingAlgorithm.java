package pl.marcinzygmunt.domain.port;

import pl.marcinzygmunt.domain.model.Numbers;

public interface SortingAlgorithm {
    Numbers sort(Numbers numbers);
}
