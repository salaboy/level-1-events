package level1;

public class LevelStatusEvent {
  private String currentAnswer;
  private String levelName;
  private boolean completed;

  public LevelStatusEvent() {
  }

  public LevelStatusEvent(String currentAnswer, boolean completed) {
    this.levelName = "level-1";
    this.currentAnswer = currentAnswer;
    this.completed = completed;
  }

  public LevelStatusEvent(String currentAnswer, String levelName, boolean completed) {
    this.currentAnswer = currentAnswer;
    this.levelName = levelName;
    this.completed = completed;
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
