package level1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.function.cloudevent.CloudEventMessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.util.Arrays;
import java.util.UUID;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component("KeyPressedEvent")
public class KeyPressedFunction implements Function<Message<KeyPressed>, Message<LevelStatus>> {

  private static final Logger LOGGER = Logger.getLogger(
    KeyPressedFunction.class.getName());

  @Value("${answer:42 is the answer}")
  private String levelAnswer;

  @Autowired
  private EventStore store;

  @Override
  public Message<LevelStatus> apply(Message<KeyPressed> keyPressedEventMessage) {

//    HttpHeaders httpHeaders = HeaderUtils.fromMessage(keyPressedEventMessage.getHeaders());

//    LOGGER.log(Level.INFO, "Input CE Id:{0}", httpHeaders.getFirst(
//      ID));
//    LOGGER.log(Level.INFO, "Input CE Spec Version:{0}",
//      httpHeaders.getFirst(SPECVERSION));
//    LOGGER.log(Level.INFO, "Input CE Source:{0}",
//      httpHeaders.getFirst(SOURCE));
//    LOGGER.log(Level.INFO, "Input CE Subject:{0}",
//      httpHeaders.getFirst(SUBJECT));
//    LOGGER.log(Level.INFO, "Input CE Type:{0}",
//      httpHeaders.getFirst(TYPE));


    KeyPressed keyPressedEvent = keyPressedEventMessage.getPayload();
    String gameSessionId = keyPressedEvent.getSessionId();

    LOGGER.log(Level.INFO, "Input GameSessionId :{0} - Key: {1} - TS: {2} ", Arrays.asList(gameSessionId, keyPressedEvent.getKey(), keyPressedEvent.getTimestamp())
      );
    
    String currentAnswer = store.registerEventForSession(gameSessionId, keyPressedEvent).getEvents();

    if (currentAnswer.toLowerCase().contains(levelAnswer.toLowerCase())) {
      LOGGER.log(Level.INFO, ">>>>>>>>>>CORRECT ANSWER<<<<<");
      return CloudEventMessageBuilder.withData(new LevelStatus(gameSessionId, currentAnswer, true))
        .setType("LevelCompletedEvent").setId(UUID.randomUUID().toString())
        .setSource(URI.create("https://level-1")).build();

    } else {
      LOGGER.log(Level.INFO, "KEEP TRYING Current answer is: " + currentAnswer + " , expected answer: " + levelAnswer);
      return CloudEventMessageBuilder.withData(new LevelStatus(gameSessionId, currentAnswer, false))
        .setType("LevelFailedEvent").setId(UUID.randomUUID().toString())
        .setSource(URI.create("https://level-1")).build();
    }


  }
}
