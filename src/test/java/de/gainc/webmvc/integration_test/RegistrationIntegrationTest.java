package de.gainc.webmvc.integration_test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import de.gainc.webmvc.models.NewsUser;
import de.gainc.webmvc.repositories.NewsUserRepository;
import java.time.LocalDate;
import java.util.concurrent.TimeUnit;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RegistrationIntegrationTest {

  @LocalServerPort
  private int port;

  private WebDriver driver;

  @Autowired
  private NewsUserRepository userRepository;


  @Before
  public void setup() {
    System.setProperty("webdriver.chrome.driver", "target/drivers/chromedriver-windows-32bit.exe");
    ChromeOptions options = new ChromeOptions();
    options.addArguments("start-maximized");
    options.addArguments("disable-infobars");
    options.addArguments("--disable-extensions");
    options.addArguments("--disable-gpu");
    options.addArguments("--disable-dev-shm-usage");
    options.addArguments("--no-sandbox");
    options.addArguments("--headless");
    driver = new ChromeDriver(options);
    driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
  }

  @After
  public void tearDown() {
    if (driver != null) {
      driver.quit();
    }
  }

  @Test
  public void testRegistration() {
    driver.get("http://localhost:" + port + "/user/register");
    driver.findElement(By.id("firstname")).sendKeys("test");
    driver.findElement(By.id("lastname")).sendKeys("test");
    driver.findElement(By.id("birthday")).sendKeys("01.01.1970");
    driver.findElement(By.id("username")).sendKeys("test");
    driver.findElement(By.id("password")).sendKeys("test");
    driver.findElement(By.tagName("button")).click();
    NewsUser expected = new NewsUser(LocalDate.of(1970, 1, 1), "test", "test", "test", "test");
    ArgumentCaptor<NewsUser> argument =
        ArgumentCaptor.forClass(NewsUser.class);
    verify(userRepository, times(1))
        .save(argument.capture());
    NewsUser savedUser = argument.getValue();
    assertThat(savedUser.getUsername()).isEqualTo("test");
    assertThat(savedUser.getFirstname()).isEqualTo("test");
    assertThat(savedUser.getLastname()).isEqualTo("test");
    assertThat(savedUser.getBirthday()).isEqualTo(LocalDate.of(1970, 1, 1));
    assertThat(new BCryptPasswordEncoder()
        .matches("test", savedUser.getPassword()))
        .isTrue();
  }


}