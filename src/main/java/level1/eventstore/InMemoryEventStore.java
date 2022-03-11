package level1.eventstore;

import level1.EventStore;
import level1.KeyPressed;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/*
 * This implementation only works In-Memory, hence it will not support multiple replicas
 */
public class InMemoryEventStore implements EventStore {

  private Map<String, GameEvents> eventsPerSession = new ConcurrentHashMap<>();
  
  public GameEvents registerEventForSession(String gameSessionId, KeyPressed keyPressedEvent) {
    if (eventsPerSession.get(gameSessionId) == null) {
      eventsPerSession.put(gameSessionId, new GameEvents());
    }
    GameEvents gameEvents = eventsPerSession.get(gameSessionId);
    gameEvents.addEvent(keyPressedEvent);
    return gameEvents;
  }

  @Override
  public GameEvents getEventsForSession(String gameSessionId) {
    return eventsPerSession.get(gameSessionId);
  }
}
