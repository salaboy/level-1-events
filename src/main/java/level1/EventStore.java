package level1;

import level1.eventstore.GameEvents;

public interface EventStore {

  GameEvents getEventsForSession(String gameSessionId);
  GameEvents registerEventForSession(String gameSessionId, KeyPressed keyPressedEvent);
  
}
