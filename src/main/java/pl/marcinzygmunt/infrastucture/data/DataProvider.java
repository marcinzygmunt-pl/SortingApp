package pl.marcinzygmunt.infrastucture.data;

import pl.marcinzygmunt.domain.model.Numbers;

import java.util.Optional;

public interface DataProvider {
    Optional<Numbers> fetchData();
}
