package info.szadkowski.matrix.add.game.rest.controller;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.empty;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

public class GameControllerTest {
  private MockMvc mockMvc;

  @Before
  public void setUp() throws Exception {
    mockMvc = standaloneSetup(new GameController())
            .setControllerAdvice(new ExceptionHandlingController())
            .build();
  }

  @Test
  public void shouldCreateGameId() throws Exception {
    mockMvc.perform(get("/v1/game"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(jsonPath("$.id").value(is(not(empty()))));
  }

  @Test
  public void givenNotExistingGameId_willReturnError() throws Exception {
    mockMvc.perform(get("/v1/game/notExistingId"))
            .andExpect(status().isNoContent())
            .andExpect(jsonPath("$.reason").value(is("Desired game id was not found")))
            .andExpect(jsonPath("$.message").value(is("Requested \"notExistingId\" was not found. Please GET /v1/game to create new.")))
            .andExpect(jsonPath("$.url").value(is("http://localhost/v1/game/notExistingId")));
  }
}
