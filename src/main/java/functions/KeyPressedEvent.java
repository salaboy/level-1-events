package functions;

import java.util.Date;

public class KeyPressedEvent implements Comparable<KeyPressedEvent> {

  private String key;
  private int position;
  private Date timestamp;

  public KeyPressedEvent() {
  }

  public KeyPressedEvent(String key, int position, Date timestamp) {
    this.key = key;
    this.position = position;
    this.timestamp = timestamp;
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

  @Override
  public int compareTo(KeyPressedEvent input) {
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
    return "Input{" +
      "key='" + key + '\'' +
      ", position=" + position +
      ", timestamp=" + timestamp +
      '}';
  }
}
