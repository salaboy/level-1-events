package level1;

import com.fasterxml.jackson.annotation.JsonClassDescription;

@JsonClassDescription
public class LevelStatus {
  private String currentAnswer;
  private int levelId;
  private boolean completed;
  private String gameSessionId;

  public LevelStatus() {
  }

  public LevelStatus(String gameSessionId, String currentAnswer, boolean completed) {
    this.gameSessionId = gameSessionId;
    this.levelId = 1;
    this.currentAnswer = currentAnswer;
    this.completed = completed;

  }

  public LevelStatus(String gameSessionId, String currentAnswer, int levelId, boolean completed) {
    this.gameSessionId = gameSessionId;
    this.currentAnswer = currentAnswer;
    this.levelId = levelId;
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

  public int getLevelId() {
    return levelId;
  }

  public void setLevelId(int levelId) {
    this.levelId = levelId;
  }

  public boolean isCompleted() {
    return completed;
  }

  public void setCompleted(boolean completed) {
    this.completed = completed;
  }
}
