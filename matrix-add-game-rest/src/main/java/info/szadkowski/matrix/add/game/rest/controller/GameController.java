package info.szadkowski.matrix.add.game.rest.controller;

import info.szadkowski.matrix.add.game.rest.model.Game;
import info.szadkowski.matrix.add.game.rest.model.GameId;
import info.szadkowski.matrix.add.game.rest.model.MoveDirection;
import info.szadkowski.matrix.add.game.rest.service.GameHolder;
import info.szadkowski.matrix.add.game.rest.service.GameHolderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@RestController
@RequestMapping(value = "/v1", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class GameController {
  private final ConcurrentMap<String, GameHolder> cache = new ConcurrentHashMap<>();

  private final GameHolderFactory gameHolderFactory;

  @Autowired
  public GameController(GameHolderFactory gameHolderFactory) {
    this.gameHolderFactory = gameHolderFactory;
  }

  @RequestMapping(value = "/game", method = RequestMethod.GET)
  public GameId createNewGameId() {
    String id = UUID.randomUUID().toString();
    GameHolder gameHolder = gameHolderFactory.create();
    cache.put(id, gameHolder);
    for (int i = 0; i < 2; i++)
      gameHolder.generateRandom();
    return new GameId(id);
  }

  @RequestMapping(value = "/game/{id}", method = RequestMethod.GET)
  public Game getGameMatrix(@PathVariable("id") String gameId,
                            @RequestParam(value = "move", required = false, defaultValue = "NONE") MoveDirection move) {
    GameHolder gameHolder = cache.get(gameId);
    if (gameHolder == null)
      throw new GameNotFoundException("Requested \"" + gameId + "\" was not found. Please GET /v1/game to create new.");

    move.move(gameHolder);

    return new Game(gameHolder.getMatrix());
  }

  @ResponseStatus(value = HttpStatus.NO_CONTENT, reason = "Desired game id was not found")
  public static class GameNotFoundException extends RuntimeException {
    public GameNotFoundException(String message) {
      super(message);
    }
  }
}
