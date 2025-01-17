package io.enfuse.pipeline.geodeprocessor;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {TestConfiguration.class, GeodeProcessorApplication.class})
@ActiveProfiles("test")
public class GeodeProcessorApplicationTests {

  @Test
  public void contextLoads() {}
}
