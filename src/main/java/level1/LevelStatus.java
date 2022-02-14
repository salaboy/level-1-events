package level1;

import com.fasterxml.jackson.annotation.JsonClassDescription;

@JsonClassDescription
public class LevelStatus {
  private String currentAnswer;
  private String levelName;
  private boolean completed;
  private String gameSessionId;

  public LevelStatus() {
  }

  public LevelStatus(String gameSessionId, String currentAnswer, boolean completed) {
    this.gameSessionId = gameSessionId;
    this.levelName = "level-1";
    this.currentAnswer = currentAnswer;
    this.completed = completed;

  }

  public LevelStatus(String gameSessionId, String currentAnswer, String levelName, boolean completed) {
    this.gameSessionId = gameSessionId;
    this.currentAnswer = currentAnswer;
    this.levelName = levelName;
    this.completed = completed;
  }

  public String getGameSessionId() {
    return gameSessionId;
  }

  public void setGameSessionId(String gameSessionId) {
    this.gameSessionId = gameSessionId;
  }

  public String getCurrentAnswer() {
    return currentAnswer;
  }

  public void setCurrentAnswer(String currentAnswer) {
    this.currentAnswer = currentAnswer;
  }

  public String getLevelName() {
    return levelName;
  }

  public void setLevelName(String levelName) {
    this.levelName = levelName;
  }

  public boolean isCompleted() {
    return completed;
  }

  public void setCompleted(boolean completed) {
    this.completed = completed;
  }
}
