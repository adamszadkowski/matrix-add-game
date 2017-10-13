package info.szadkowski.matrix.add.game.rest.configuration;

import info.szadkowski.matrix.add.game.core.listener.FullMatrixChangeListener;
import info.szadkowski.matrix.add.game.core.listener.RandomNumberAppender;
import info.szadkowski.matrix.add.game.rest.properties.GameProperties;
import info.szadkowski.matrix.add.game.rest.service.GameHolderFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.support.DefaultConversionService;

import java.time.Duration;

@Configuration
public class GameConfiguration {
  @Bean
  public ConversionService conversionService() {
    DefaultConversionService conversionService = new DefaultConversionService();
    conversionService.addConverter(new Converter<String, Duration>() {
      @Override
      public Duration convert(String s) {
        if (s == null) return null;
        return Duration.parse(s);
      }
    });
    return conversionService;
  }

  @Bean
  public GameProperties gameProperties() {
    return new GameProperties();
  }

  @Bean
  public FullMatrixChangeListener changeListener() {
    return new RandomNumberAppender();
  }

  @Bean
  public GameHolderFactory gameHolderFactory(GameProperties gameProperties,
                                             FullMatrixChangeListener changeListener) {
    return new GameHolderFactory(gameProperties, changeListener);
  }
}
