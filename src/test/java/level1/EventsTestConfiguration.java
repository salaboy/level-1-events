package level1;

import level1.eventstore.InMemoryEventStore;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

@TestConfiguration
public class EventsTestConfiguration {
  @Bean
  @Primary
  public EventStore getEventStore(){
    return new InMemoryEventStore();
  }
}
