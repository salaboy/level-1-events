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
import java.util.Date;
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

    KeyPressed keyPressedEvent = new KeyPressed();

    keyPressedEvent.setKey("a");
    keyPressedEvent.setPosition(0);
    keyPressedEvent.setTimestamp(new Date());

    HttpHeaders ceHeaders = new HttpHeaders();
    ceHeaders.add(SPECVERSION, "1.0");
    ceHeaders.add(ID, UUID.randomUUID()
        .toString());
    ceHeaders.add("Ce-Type", "KeyPressedEvent");
    ceHeaders.add(SOURCE, "http://localhost:8080/");
    ceHeaders.add(SUBJECT, "game");

    ResponseEntity<LevelStatus> response = this.rest.exchange(
        RequestEntity.post(new URI("/"))
            .contentType(MediaType.APPLICATION_JSON)
            .headers(ceHeaders)
            .body(keyPressedEvent),
      LevelStatus.class);

    assertThat(response.getStatusCode()
        .value(), equalTo(200));
    LevelStatus levelStatusEvent = response.getBody();
    HttpHeaders headers = response.getHeaders();
    assertThat(headers.get(TYPE), notNullValue());
    assertThat(headers.get(TYPE), equalTo("LevelFailedEvent"));
    assertThat(levelStatusEvent, notNullValue());
//    LevelStatus levelStatusEvent = objectMapper.readValue(body,
//        LevelStatus.class);
    assertThat(levelStatusEvent, notNullValue());
    assertThat(levelStatusEvent.isCompleted(), equalTo(false));
    assertThat(levelStatusEvent.getLevelName(), equalTo("level-1"));
    assertThat(levelStatusEvent.getCurrentAnswer(), equalTo("a"));
   
  }
}
