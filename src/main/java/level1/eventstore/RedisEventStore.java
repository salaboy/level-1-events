package level1.eventstore;

import level1.EventStore;
import level1.KeyPressed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

// Review: https://engineering.salesforce.com/lessons-learned-using-spring-data-redis-f3121f89bff9
@Component
public class RedisEventStore implements EventStore {

  @Autowired
  private GameEventsRepository gameEventsRepository;
  
  @Override
  public GameEvents registerEventForSession(String gameSessionId, KeyPressed keyPressedEvent) {
    GameEvents gameEvents = null;
    Optional<GameEvents> byId = gameEventsRepository.findById(gameSessionId);
    if(!byId.isPresent()){
      gameEvents = new GameEvents(gameSessionId);
    }else{
      gameEvents = byId.get();
    }
    gameEvents.addEvent(keyPressedEvent);

    return gameEventsRepository.save(gameEvents);
  }

  @Override
  public GameEvents getEventsForSession(String gameSessionId) {
    Optional<GameEvents> byId = gameEventsRepository.findById(gameSessionId);
    if(!byId.isPresent()){
      return new GameEvents(gameSessionId);  
    }else{
      return byId.get();
    }
  }
}
