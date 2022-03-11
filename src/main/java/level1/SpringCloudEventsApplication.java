package level1;

import level1.eventstore.GameEventsRepository;
import level1.eventstore.RedisConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

import java.util.function.Function;


@SpringBootApplication(scanBasePackages = "level1")
@Import({RedisConfiguration.class})
public class SpringCloudEventsApplication {

//  private static final Logger LOGGER = Logger.getLogger(
//      SpringCloudEventsApplication.class.getName());
  
  public static void main(String[] args) {
    SpringApplication.run(SpringCloudEventsApplication.class, args);
  }

  @Autowired
  private EventStore store;
  
//  @Value("${answer:42 is the answer}")
//  private String levelAnswer;
//
//
//  private List<KeyPressed> events = new CopyOnWriteArrayList<>();
//
//  @Bean
//  public Function<Message<KeyPressed>, Message<LevelStatus>> KeyPressedEvent() {
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
//      KeyPressed keyPressedEvent = m.getPayload();
//      events.add(keyPressedEvent);
//
//      Collections.sort(events);
//
//      String currentAnswer = "";
//      for(KeyPressed event: events){
//        currentAnswer += event.getKey();
//      }
//
//      if(currentAnswer.toLowerCase().contains(levelAnswer.toLowerCase())){
//        LOGGER.log(Level.INFO, ">>>>>>>>>>CORRECT ANSWER<<<<<");
//        return CloudEventMessageBuilder.withData(new LevelStatus(currentAnswer, true))
//          .setType("LevelCompletedEvent").setId(UUID.randomUUID().toString())
//				.setSource(URI.create("https://level-1")).build();
//
//      }else{
//        LOGGER.log(Level.INFO, "KEEP TRYING Current answer is: " + currentAnswer + " , expected answer: " + levelAnswer);
//        return CloudEventMessageBuilder.withData(new LevelStatus(currentAnswer, false))
//          .setType("LevelFailedEvent").setId(UUID.randomUUID().toString())
//          .setSource(URI.create("https://level-1")).build();
//      }
//
//    };
 // }
  
  @Value("${answer:42}")
  private String levelAnswer;

  @Value("${question:What is the Answer to Life, the Universe and Everything?}")
  private String levelQuestion;
  
  @Bean
  public Function<String, String> question() {
    return question -> levelQuestion;
  }
  
  @Bean
  public Function<String, String> answerBySession() {
    return sessionId -> {
      return store.getEventsForSession(sessionId).getEvents();
    };
  }

  @Bean
  public Function<String, String> answer() {
    return question -> levelAnswer;
  }
  
}
