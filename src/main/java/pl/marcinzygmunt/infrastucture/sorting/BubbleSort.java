package pl.marcinzygmunt.infrastucture.sorting;

import lombok.extern.slf4j.Slf4j;
import pl.marcinzygmunt.domain.model.Numbers;
import pl.marcinzygmunt.domain.port.SortingAlgorithm;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class BubbleSort implements SortingAlgorithm {

    @Override
    public Numbers sort(Numbers numbers) {
        List<Integer> list = new ArrayList<>(numbers.elements());
        int n = list.size();
        boolean swapped;
        do {
            swapped = false;
            for (int i = 1; i < n; i++) {
                if (list.get(i - 1) > list.get(i)) {
                    int temp = list.get(i);
                    list.set(i, list.get(i - 1));
                    list.set(i - 1, temp);
                    swapped = true;
                }
            }
            n--;
        } while (swapped);
        log.info("Sorted with BubbleSort !");
        return new Numbers(list);
    }
}
