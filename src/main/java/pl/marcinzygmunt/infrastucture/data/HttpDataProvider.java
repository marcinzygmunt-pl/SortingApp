package pl.marcinzygmunt.infrastucture.data;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import pl.marcinzygmunt.domain.model.Numbers;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

@Slf4j
public class HttpDataProvider implements DataProvider {
        private final String url;

        public HttpDataProvider(String url) {
            this.url = url;
        }

        @Override
        public Optional<Numbers> fetchData() {
            try {
                URL link = new URL(url);
                HttpURLConnection conn = (HttpURLConnection) link.openConnection();
                conn.setRequestMethod("GET");
                conn.connect();

                if (conn.getResponseCode() != 200) {
                    return Optional.empty();
                }

                Scanner sc = new Scanner(link.openStream());
                StringBuilder inline = new StringBuilder();
                while (sc.hasNext()) {
                    inline.append(sc.nextLine());
                }
                sc.close();

                ObjectMapper mapper = new ObjectMapper();
                JsonNode root = mapper.readTree(inline.toString());
                JsonNode elementsNode = root.get("elements");

                if (elementsNode == null || !elementsNode.isArray()) {
                    return Optional.empty();
                }

                List<Integer> elements = new ArrayList<>();
                elementsNode.forEach(node -> elements.add(node.asInt()));
                log.info("Data fetched from HTTP!");
                return Optional.of(new Numbers(elements));

            } catch (Exception e) {
                log.error("Error fetching data");
                return Optional.empty();
            }
        }
}
