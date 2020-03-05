package hu.blzsaa.simplewebshop.it;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.TEXT_HTML;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class SwaggerIT {
  @Autowired private MockMvc mockMvc;

  @Test
  void shouldPublishSwaggerUi() throws Exception {
    this.mockMvc
        .perform(get("/swagger-ui.html"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(TEXT_HTML));
  }

  @Test
  void shouldPublishSwaggerAsJson() throws Exception {
    this.mockMvc
        .perform(get("/v2/api-docs"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(APPLICATION_JSON))
        .andExpect(jsonPath("$.swagger").value("2.0"));
  }
}
