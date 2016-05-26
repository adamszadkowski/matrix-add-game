package info.szadkowski.matrix.add.game.rest.configuration;

import info.szadkowski.matrix.add.game.core.listener.FullMatrixChangeListener;
import info.szadkowski.matrix.add.game.rest.service.GameHolderFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GameConfiguration {
  @Value("${game.size:4}")
  private int size;

  @Bean
  public GameHolderFactory gameHolderFactory(FullMatrixChangeListener changeListener) {
    return new GameHolderFactory(changeListener, size);
  }

  @Bean
  public FullMatrixChangeListener changeListener() {
    return e -> e.getGameMatrix().set(0, 0, 2);
  }
}
