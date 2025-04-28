package pl.marcinzygmunt.infrastucture.sorting;

import lombok.extern.slf4j.Slf4j;
import pl.marcinzygmunt.domain.model.Numbers;
import pl.marcinzygmunt.domain.port.SortingAlgorithm;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class InsertionSort implements SortingAlgorithm {
    @Override
    public Numbers sort(Numbers numbers) {
        List<Integer> list = new ArrayList<>(numbers.elements()); // kopia danych
        for (int i = 1; i < list.size(); i++) {
            int key = list.get(i);
            int j = i - 1;

            while (j >= 0 && list.get(j) > key) {
                list.set(j + 1, list.get(j));
                j = j - 1;
            }
            list.set(j + 1, key);
        }
        log.info("Sorted with InsertionSort !");
        return new Numbers(list);
    }
}

