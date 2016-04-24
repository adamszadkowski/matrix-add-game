package info.szadkowski.matrix.add.game.rest.controller;

import info.szadkowski.matrix.add.game.rest.model.GameId;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(value = "/v1", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class GameController {
  @RequestMapping(value = "/game", method = RequestMethod.GET)
  public GameId createNewGameId() {
    return new GameId(UUID.randomUUID().toString());
  }

  @RequestMapping(value = "/game/{id}", method = RequestMethod.GET)
  public String getGameMatrix(@PathVariable("id") String gameId) {
    throw new GameNotFoundException("Requested \"" + gameId + "\" was not found. Please GET /v1/game to create new.");
//    return "";
  }

  @ResponseStatus(value = HttpStatus.NO_CONTENT, reason = "Desired game id was not found")
  public static class GameNotFoundException extends RuntimeException {
    public GameNotFoundException(String message) {
      super(message);
    }
  }
}
