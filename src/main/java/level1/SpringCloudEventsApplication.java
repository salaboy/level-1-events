package level1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.function.Function;

@SpringBootApplication
public class SpringCloudEventsApplication {

//  private static final Logger LOGGER = Logger.getLogger(
//      SpringCloudEventsApplication.class.getName());

  public static void main(String[] args) {
    SpringApplication.run(SpringCloudEventsApplication.class, args);
  }

//  @Value("${answer:42 is the answer}")
//  private String levelAnswer;
//  
//  
//  private List<KeyPressedEvent> events = new CopyOnWriteArrayList<>();
//
//  @Bean
//  public Function<Message<KeyPressedEvent>, Message<LevelStatusEvent>> keyPressed() {
//    return m -> {
//      HttpHeaders httpHeaders = HeaderUtils.fromMessage(m.getHeaders());
//      
//      LOGGER.log(Level.INFO, "Input CE Id:{0}", httpHeaders.getFirst(
//          ID));
//      LOGGER.log(Level.INFO, "Input CE Spec Version:{0}",
//          httpHeaders.getFirst(SPECVERSION));
//      LOGGER.log(Level.INFO, "Input CE Source:{0}",
//          httpHeaders.getFirst(SOURCE));
//      LOGGER.log(Level.INFO, "Input CE Subject:{0}",
//          httpHeaders.getFirst(SUBJECT));
//
//      // ADD LIST OF IGNORED KEYS SUCH AS SHIFT, always compare with lowercase
//      // TODO: 
//      /// - get session + get events from session
//      /// - reproduce events, compare with answer (maybe with string contains)
//      /// - return completed or keep trying events
//      
//      
//      KeyPressedEvent keyPressedEvent = m.getPayload();
//      events.add(keyPressedEvent);
//
//      Collections.sort(events);
//
//      String currentAnswer = "";
//      for(KeyPressedEvent event: events){
//        currentAnswer += event.getKey();
//      }
//      
//      if(currentAnswer.toLowerCase().contains(levelAnswer.toLowerCase())){
//        LOGGER.log(Level.INFO, ">>>>>>>>>>CORRECT ANSWER<<<<<");
//        //return new LevelStatusEvent(currentAnswer, true);
//        return CloudEventMessageBuilder.withData(new LevelStatusEvent(currentAnswer, true))
//          .setType("LevelCompletedEvent").setId(UUID.randomUUID().toString())
//				.setSource(URI.create("https://level-1")).build();
//   
//      }else{
//        LOGGER.log(Level.INFO, "KEEP TRYING Current answer is: " + currentAnswer + " , expected answer: " + levelAnswer);
//        //return new LevelStatusEvent(currentAnswer, false);
//        return CloudEventMessageBuilder.withData(new LevelStatusEvent(currentAnswer, false))
//          .setType("LevelFailedEvent").setId(UUID.randomUUID().toString())
//          .setSource(URI.create("https://level-1")).build();
//      }
//      
//    };
//  }

//  // How does this  work for more than one function ?? 
//  @Bean
//  public CloudEventHeaderEnricher attributesProvider() {
//    return attributes -> attributes
//        .setSpecVersion("1.0")
//        .setId(UUID.randomUUID()
//            .toString())
//        .setSource("level-1")
//        .setType("LevelStatus");
//      // I should be able to set an extension here
//  }

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
