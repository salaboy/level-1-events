package level1;

import com.fasterxml.jackson.annotation.JsonClassDescription;

import java.util.Date;

@JsonClassDescription
public class KeyPressed implements Comparable<KeyPressed> {

  private String key;
  private int position;
  private Date timestamp;
  private String sessionId;

  public KeyPressed() {
  }
  
  public String getKey() {
    return key;
  }

  public void setKey(String key) {
    this.key = key;
  }

  public int getPosition() {
    return position;
  }

  public void setPosition(int position) {
    this.position = position;
  }

  public Date getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(Date timestamp) {
    this.timestamp = timestamp;
  }

  public String getSessionId() {
    return sessionId;
  }
  
  public void setSessionId(String sessionId) {
    this.sessionId = sessionId;
  }

  @Override
  public int compareTo(KeyPressed input) {
    if (this.getTimestamp() == null) {
      return -1;
    }
    if (input == null || input.getTimestamp() == null) {
      return 1;
    }
    if (this.getTimestamp().before(input.getTimestamp())) {
      return -1;
    } else if (this.getTimestamp().after(input.getTimestamp())) {
      return 1;
    } else {
      return 0;
    }
  }

  @Override
  public String toString() {
    return "KeyPressed{" +
      "key='" + key + '\'' +
      ", position=" + position +
      ", timestamp=" + timestamp +
      ", sessionId='" + sessionId + '\'' +
      '}';
  }
}
