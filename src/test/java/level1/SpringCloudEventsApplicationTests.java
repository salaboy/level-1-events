package level1;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.cloud.function.cloudevent.CloudEventMessageUtils.*;

@SpringBootTest(classes = SpringCloudEventsApplication.class,
    webEnvironment = WebEnvironment.RANDOM_PORT)
public class SpringCloudEventsApplicationTests {
  
  @Autowired
  private TestRestTemplate rest;

  @Autowired
  ObjectMapper objectMapper = new ObjectMapper();

  @Test
  public void testKeyPressedJsonInput() throws Exception {

    KeyPressedEvent keyPressedEvent = new KeyPressedEvent();

    keyPressedEvent.setKey("a");

    HttpHeaders ceHeaders = new HttpHeaders();
    ceHeaders.add(SPECVERSION, "1.0");
    ceHeaders.add(ID, UUID.randomUUID()
        .toString());
    ceHeaders.add(TYPE, "keyPressed");
    ceHeaders.add(SOURCE, "http://localhost:8080/keypressed");
    ceHeaders.add(SUBJECT, "game");

    ResponseEntity<String> response = this.rest.exchange(
        RequestEntity.post(new URI("/keyPressed"))
            .contentType(MediaType.APPLICATION_JSON)
            .headers(ceHeaders)
            .body(keyPressedEvent),
        String.class);

    assertThat(response.getStatusCode()
        .value(), equalTo(200));
    String body = response.getBody();
    assertThat(body, notNullValue());
    LevelStatusEvent levelStatusEvent = objectMapper.readValue(body,
        LevelStatusEvent.class);
    assertThat(levelStatusEvent, notNullValue());
    assertThat(levelStatusEvent.isCompleted(), equalTo(false));
    assertThat(levelStatusEvent.getLevelName(), equalTo("level-1"));
    assertThat(levelStatusEvent.getCurrentAnswer(), equalTo("a"));
   
  }
}