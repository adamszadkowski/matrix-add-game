package info.szadkowski.matrix.add.game.rest.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import info.szadkowski.matrix.add.game.core.listener.FullMatrixChangeListener;
import info.szadkowski.matrix.add.game.core.matrix.GameMatrix;
import info.szadkowski.matrix.add.game.core.visualizer.GameMatrixVisualizer;
import info.szadkowski.matrix.add.game.rest.model.Game;
import info.szadkowski.matrix.add.game.rest.model.GameId;
import info.szadkowski.matrix.add.game.rest.properties.GameProperties;
import info.szadkowski.matrix.add.game.rest.service.GameHolderFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Duration;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.empty;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

class GameControllerTest {
  private GameProperties gameProperties;
  private MockMvc mockMvc;

  @BeforeEach
  void setUp() throws Exception {
    gameProperties = new GameProperties();
    gameProperties.setSize(4);
    gameProperties.setExpirationTimeout(Duration.ofDays(1));
    gameProperties.setMaxGameCount(30);

    FullMatrixChangeListener changeListener = new PseudoRandomGenerator();
    GameController gameController = new GameController(gameProperties, new GameHolderFactory(gameProperties, changeListener));
    mockMvc = standaloneSetup(gameController)
            .setControllerAdvice(new ExceptionHandlingController())
            .build();
  }

  @Nested
  class NoGame {
    @Test
    void shouldCreateGameId() throws Exception {
      mockMvc.perform(get("/v1/game"))
              .andExpect(status().isOk())
              .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
              .andExpect(jsonPath("$.id").value(is(not(empty()))));
    }

    @Test
    void shouldReturnError() throws Exception {
      mockMvc.perform(get("/v1/game/notExistingId"))
              .andExpect(status().isNoContent())
              .andExpect(jsonPath("$.reason").value(is("Desired game id was not found")))
              .andExpect(jsonPath("$.message").value(is("Requested \"notExistingId\" was not found. Please GET /v1/game to create new.")))
              .andExpect(jsonPath("$.url").value(is("http://localhost/v1/game/notExistingId")));
    }
  }

  @Nested
  class ContentBased {
    private ObjectMapper mapper;

    @BeforeEach
    void setUp() throws Exception {
      mapper = new ObjectMapper();
    }

    @Nested
    class NewGame {
      private String gameId;

      @BeforeEach
      void setUp() throws Exception {
        gameId = getNewGameId();
      }

      @Nested
      class Movement {

        @BeforeEach
        void newlyCreatedGameShouldHaveTwoFieldsFilled() throws Exception {
          GameMatrixVisualizer visualizer = getVisualizerForUrl(String.format("/v1/game/%s", gameId));
          assertThat(visualizer.visualize()).isEqualTo(
                  "| 2 |   |   |   |\n" +
                  "|   |   |   |   |\n" +
                  "|   |   | 2 |   |\n" +
                  "|   |   |   |   |");
        }

        @Test
        void shouldBeAbleToMoveRight() throws Exception {
          GameMatrixVisualizer visualizer = getVisualizerForUrl(String.format("/v1/game/%s?move=RIGHT", gameId));
          assertThat(visualizer.visualize()).isEqualTo(
                  "| 2 |   |   | 2 |\n" +
                  "|   |   |   |   |\n" +
                  "|   |   |   | 2 |\n" +
                  "|   |   |   |   |");
        }

        @Test
        void shouldBeAbleToMoveDown() throws Exception {
          GameMatrixVisualizer visualizer = getVisualizerForUrl(String.format("/v1/game/%s?move=DOWN", gameId));
          assertThat(visualizer.visualize()).isEqualTo(
                  "| 2 |   |   |   |\n" +
                  "|   |   |   |   |\n" +
                  "|   |   |   |   |\n" +
                  "| 2 |   | 2 |   |");
        }

        @Test
        void shouldBeAbleToMoveLeft() throws Exception {
          GameMatrixVisualizer visualizer = getVisualizerForUrl(String.format("/v1/game/%s?move=LEFT", gameId));
          assertThat(visualizer.visualize()).isEqualTo(
                  "| 2 |   |   |   |\n" +
                  "|   |   |   |   |\n" +
                  "| 2 |   |   |   |\n" +
                  "|   |   |   |   |");
        }

        @Test
        void shouldBeAbleToMoveUp() throws Exception {
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

        GameMatrix gameMatrix = new GameMatrix(gameProperties.getSize());
        gameMatrix.setMatrix(mapper.readValue(s, Game.class).getGameMatrix());
        return new GameMatrixVisualizer(gameMatrix);
      }
    }

    @Nested
    class Expiration {

      @Test
      void givenZeroGamesAllowed_willFail() throws Exception {
        gameProperties.setMaxGameCount(0);
        mockMvc.perform(get("/v1/game"))
                .andExpect(status().isServiceUnavailable());
      }

      @Nested
      class SingleGameAllowed {

        @BeforeEach
        void setUp() throws Exception {
          gameProperties.setMaxGameCount(1);
        }

        @Test
        void givenLongExpireTime_willNotExpireInThatTime() throws Exception {
          gameProperties.setExpirationTimeout(Duration.ofDays(1));
          String id = getNewGameId();
          mockMvc.perform(get("/v1/game"))
                  .andExpect(status().isServiceUnavailable())
                  .andExpect(jsonPath("$.reason").value(is("Too many games")))
                  .andExpect(jsonPath("$.message").value(is("Cannot create new game")))
                  .andExpect(jsonPath("$.url").value(is("http://localhost/v1/game")));
          mockMvc.perform(get("/v1/game/" + id))
                  .andExpect(status().isOk());
        }

        @Test
        void givenShortExpireTime_willExpire() throws Exception {
          gameProperties.setExpirationTimeout(Duration.ofMillis(0));
          String id = getNewGameId();
          mockMvc.perform(get("/v1/game"))
                  .andExpect(status().isOk());
          mockMvc.perform(get("/v1/game/" + id))
                  .andExpect(status().isNoContent());
        }
      }
    }

    private String getNewGameId() throws Exception {
      String s = mockMvc.perform(get("/v1/game"))
              .andReturn().getResponse().getContentAsString();

      return mapper.readValue(s, GameId.class).getId();
    }
  }

  private static class PseudoRandomGenerator implements FullMatrixChangeListener {
    boolean isFirst = true;

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
