package pl.marcinzygmunt.infrastucture.data;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import pl.marcinzygmunt.domain.model.Numbers;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
public class FileDataProvider implements DataProvider {
    private final String filePath;

    public FileDataProvider(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public Optional<Numbers> fetchData() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(new File(filePath));
            JsonNode elementsNode = root.get("elements");

            if (elementsNode == null || !elementsNode.isArray()) {
                return Optional.empty();
            }

            List<Integer> elements = new ArrayList<>();
            elementsNode.forEach(node -> elements.add(node.asInt()));
            log.info("Data fetched from file!");
            return Optional.of(new Numbers(elements));

        } catch (Exception e) {
            log.error("Error fetching data");
            return Optional.empty();
        }
    }
}
