package Inteca.rekrutacja.FamilyApp;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.net.URL;

@SpringBootApplication
public class FamilyAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(FamilyAppApplication.class, args);
	}

	public static JsonNode getJson(URL url) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.readTree(url);
	}

}
