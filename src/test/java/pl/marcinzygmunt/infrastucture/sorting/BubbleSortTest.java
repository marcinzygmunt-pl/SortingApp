package pl.marcinzygmunt.infrastucture.sorting;

import org.junit.jupiter.api.Test;
import pl.marcinzygmunt.domain.model.Numbers;
import pl.marcinzygmunt.domain.port.SortingAlgorithm;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BubbleSortTest {

    @Test
    void sort_shouldSortWithBubbleAlgorithm() {
        SortingAlgorithm sorter = new BubbleSort();
        Numbers input = new Numbers(List.of(5, 3, 8, 1, -2, 0));
        Numbers expected = new Numbers(List.of(-2, 0, 1, 3, 5, 8));

       Numbers result = sorter.sort(input);

        assertEquals(expected, result, "Błąd sortowania BubbleSort!");
    }
}