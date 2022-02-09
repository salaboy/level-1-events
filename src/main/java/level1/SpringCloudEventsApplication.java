package level1;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.function.cloudevent.CloudEventMessageBuilder;
import org.springframework.cloud.function.web.util.HeaderUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.messaging.Message;
import static org.springframework.cloud.function.cloudevent.CloudEventMessageUtils.*;

import java.net.URI;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;

@SpringBootApplication
public class SpringCloudEventsApplication {

  private static final Logger LOGGER = Logger.getLogger(
      SpringCloudEventsApplication.class.getName());

  public static void main(String[] args) {
    SpringApplication.run(SpringCloudEventsApplication.class, args);
  }

  @Value("${answer:42 is the answer}")
  private String levelAnswer;


  private List<KeyPressed> events = new CopyOnWriteArrayList<>();

  @Bean
  public Function<Message<KeyPressed>, Message<LevelStatus>> KeyPressedEvent() {
    return m -> {
      HttpHeaders httpHeaders = HeaderUtils.fromMessage(m.getHeaders());

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


      KeyPressed keyPressedEvent = m.getPayload();
      events.add(keyPressedEvent);

      Collections.sort(events);

      String currentAnswer = "";
      for(KeyPressed event: events){
        currentAnswer += event.getKey();
      }

      if(currentAnswer.toLowerCase().contains(levelAnswer.toLowerCase())){
        LOGGER.log(Level.INFO, ">>>>>>>>>>CORRECT ANSWER<<<<<");
        return CloudEventMessageBuilder.withData(new LevelStatus(currentAnswer, true))
          .setType("LevelCompletedEvent").setId(UUID.randomUUID().toString())
				.setSource(URI.create("https://level-1")).build();

      }else{
        LOGGER.log(Level.INFO, "KEEP TRYING Current answer is: " + currentAnswer + " , expected answer: " + levelAnswer);
        return CloudEventMessageBuilder.withData(new LevelStatus(currentAnswer, false))
          .setType("LevelFailedEvent").setId(UUID.randomUUID().toString())
          .setSource(URI.create("https://level-1")).build();
      }

    };
  }


  /**
   * Health checks
   * 
   * @return
   */
  @Bean
  public Function<String, String> health() {
    return probe -> {
      if ("readiness".equals(probe)) {
        return "ready";
      } else if ("liveness".equals(probe)) {
        return "live";
      } else {
        return "OK";
      }
    };
  }
  
}
