package info.szadkowski.matrix.add.game.rest.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.bechte.junit.runners.context.HierarchicalContextRunner;
import info.szadkowski.matrix.add.game.core.listener.FullMatrixChangeListener;
import info.szadkowski.matrix.add.game.core.listener.MatrixChangeEvent;
import info.szadkowski.matrix.add.game.core.matrix.GameMatrix;
import info.szadkowski.matrix.add.game.core.visualizer.GameMatrixVisualizer;
import info.szadkowski.matrix.add.game.rest.model.Game;
import info.szadkowski.matrix.add.game.rest.model.GameId;
import info.szadkowski.matrix.add.game.rest.service.GameHolderFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.empty;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@RunWith(HierarchicalContextRunner.class)
public class GameControllerTest {
  private static final int GAME_MATRIX_SIZE = 4;

  private MockMvc mockMvc;

  @Before
  public void setUp() throws Exception {
    FullMatrixChangeListener changeListener = new PseudoRandomGenerator();
    mockMvc = standaloneSetup(new GameController(new GameHolderFactory(changeListener, GAME_MATRIX_SIZE)))
            .setControllerAdvice(new ExceptionHandlingController())
            .build();
  }

  public class NoGame {
    @Test
    public void shouldCreateGameId() throws Exception {
      mockMvc.perform(get("/v1/game"))
              .andExpect(status().isOk())
              .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
              .andExpect(jsonPath("$.id").value(is(not(empty()))));
    }

    @Test
    public void shouldReturnError() throws Exception {
      mockMvc.perform(get("/v1/game/notExistingId"))
              .andExpect(status().isNoContent())
              .andExpect(jsonPath("$.reason").value(is("Desired game id was not found")))
              .andExpect(jsonPath("$.message").value(is("Requested \"notExistingId\" was not found. Please GET /v1/game to create new.")))
              .andExpect(jsonPath("$.url").value(is("http://localhost/v1/game/notExistingId")));
    }
  }

  public class NewGame {
    private ObjectMapper mapper;
    private String gameId;

    @Before
    public void setUp() throws Exception {
      mapper = new ObjectMapper();

      String s = mockMvc.perform(get("/v1/game"))
              .andReturn().getResponse().getContentAsString();

      gameId = mapper.readValue(s, GameId.class).getId();
    }

    public class Movement {
      @Before
      public void newlyCreatedGameShouldHaveTwoFieldsFilled() throws Exception {
        GameMatrixVisualizer visualizer = getVisualizerForUrl(String.format("/v1/game/%s", gameId));
        assertThat(visualizer.visualize()).isEqualTo(
                "| 2 |   |   |   |\n" +
                "|   |   |   |   |\n" +
                "|   |   | 2 |   |\n" +
                "|   |   |   |   |");
      }

      @Test
      public void shouldBeAbleToMoveRight() throws Exception {
        GameMatrixVisualizer visualizer = getVisualizerForUrl(String.format("/v1/game/%s?move=RIGHT", gameId));
        assertThat(visualizer.visualize()).isEqualTo(
                "| 2 |   |   | 2 |\n" +
                "|   |   |   |   |\n" +
                "|   |   |   | 2 |\n" +
                "|   |   |   |   |");
      }

      @Test
      public void shouldBeAbleToMoveDown() throws Exception {
        GameMatrixVisualizer visualizer = getVisualizerForUrl(String.format("/v1/game/%s?move=DOWN", gameId));
        assertThat(visualizer.visualize()).isEqualTo(
                "| 2 |   |   |   |\n" +
                "|   |   |   |   |\n" +
                "|   |   |   |   |\n" +
                "| 2 |   | 2 |   |");
      }

      @Test
      public void shouldBeAbleToMoveLeft() throws Exception {
        GameMatrixVisualizer visualizer = getVisualizerForUrl(String.format("/v1/game/%s?move=LEFT", gameId));
        assertThat(visualizer.visualize()).isEqualTo(
                "| 2 |   |   |   |\n" +
                "|   |   |   |   |\n" +
                "| 2 |   |   |   |\n" +
                "|   |   |   |   |");
      }

      @Test
      public void shouldBeAbleToMoveUp() throws Exception {
        GameMatrixVisualizer visualizer = getVisualizerForUrl(String.format("/v1/game/%s?move=UP", gameId));
        assertThat(visualizer.visualize()).isEqualTo(
                "| 2 |   | 2 |   |\n" +
                "|   |   |   |   |\n" +
                "|   |   |   |   |\n" +
                "|   |   |   |   |");
      }
    }

    private GameMatrixVisualizer getVisualizerForUrl(String urlTemplate) throws Exception {
      String s = mockMvc.perform(get(urlTemplate))
              .andExpect(status().isOk())
              .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
              .andReturn().getResponse().getContentAsString();

      GameMatrix gameMatrix = new GameMatrix(GAME_MATRIX_SIZE);
      gameMatrix.setMatrix(mapper.readValue(s, Game.class).getGameMatrix());
      return new GameMatrixVisualizer(gameMatrix);
    }
  }

  private static class PseudoRandomGenerator implements FullMatrixChangeListener {
    public boolean isFirst = true;

    @Override
    public void update(MatrixChangeEvent e) {
      if (isFirst) {
        e.getGameMatrix().set(2, 2, 2);
        isFirst = false;
      } else {
        e.getGameMatrix().set(0, 0, 2);
      }
    }
  }
}
