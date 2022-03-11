package level1.eventstore;

import level1.KeyPressed;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;

@RedisHash("gameevents")
public class GameEvents {
  @Id
  private String sessionId;
  private List<KeyPressed> eventObjects = new CopyOnWriteArrayList<>();

  public GameEvents() {
  }


  public GameEvents(String sessionId) {
    this.sessionId = sessionId;
  }


  public String getSessionId() {
    return sessionId;
  }

  public void setSessionId(String sessionId) {
    this.sessionId = sessionId;
  }


  public void addEvent(KeyPressed event) {
    eventObjects.add(event);
    Collections.sort(eventObjects);
  }

  public String getEvents() {
    String answer = "";
    for (KeyPressed kp : eventObjects) {
      answer += kp.getKey();
    }
    return answer;
  }
}
