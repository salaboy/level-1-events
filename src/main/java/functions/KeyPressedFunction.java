package functions;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.function.cloudevent.CloudEventMessageBuilder;
import org.springframework.cloud.function.web.util.HeaderUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.springframework.cloud.function.cloudevent.CloudEventMessageUtils.*;
import static org.springframework.cloud.function.cloudevent.CloudEventMessageUtils.SUBJECT;

@Component("keyPressed")
public class KeyPressedFunction implements Function<Message<KeyPressedEvent>, Message<LevelStatusEvent>> {

  private static final Logger LOGGER = Logger.getLogger(
    KeyPressedFunction.class.getName());

  @Value("${answer:42 is the answer}")
  private String levelAnswer;


  private List<KeyPressedEvent> events = new CopyOnWriteArrayList<>();

  @Override
  public Message<LevelStatusEvent> apply(Message<KeyPressedEvent> keyPressedEventMessage) {

    HttpHeaders httpHeaders = HeaderUtils.fromMessage(keyPressedEventMessage.getHeaders());

    LOGGER.log(Level.INFO, "Input CE Id:{0}", httpHeaders.getFirst(
      ID));
    LOGGER.log(Level.INFO, "Input CE Spec Version:{0}",
      httpHeaders.getFirst(SPECVERSION));
    LOGGER.log(Level.INFO, "Input CE Source:{0}",
      httpHeaders.getFirst(SOURCE));
    LOGGER.log(Level.INFO, "Input CE Subject:{0}",
      httpHeaders.getFirst(SUBJECT));

    // ADD LIST OF IGNORED KEYS SUCH AS SHIFT, always compare with lowercase
    // TODO: 
    /// - get session + get events from session
    /// - reproduce events, compare with answer (maybe with string contains)
    /// - return completed or keep trying events


    KeyPressedEvent keyPressedEvent = keyPressedEventMessage.getPayload();
    events.add(keyPressedEvent);

    Collections.sort(events);

    String currentAnswer = "";
    for (KeyPressedEvent event : events) {
      currentAnswer += event.getKey();
    }

    if (currentAnswer.toLowerCase().contains(levelAnswer.toLowerCase())) {
      LOGGER.log(Level.INFO, ">>>>>>>>>>CORRECT ANSWER<<<<<");
      //return new LevelStatusEvent(currentAnswer, true);
      return CloudEventMessageBuilder.withData(new LevelStatusEvent(currentAnswer, true))
        .setType("LevelCompletedEvent").setId(UUID.randomUUID().toString())
        .setSource(URI.create("https://level-1")).build();

    } else {
      LOGGER.log(Level.INFO, "KEEP TRYING Current answer is: " + currentAnswer + " , expected answer: " + levelAnswer);
      //return new LevelStatusEvent(currentAnswer, false);
      return CloudEventMessageBuilder.withData(new LevelStatusEvent(currentAnswer, false))
        .setType("LevelFailedEvent").setId(UUID.randomUUID().toString())
        .setSource(URI.create("https://level-1")).build();
    }

   
  }
}
