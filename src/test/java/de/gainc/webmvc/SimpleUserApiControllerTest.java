package de.gainc.webmvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import de.gainc.webmvc.controller.UserApiController;
import de.gainc.webmvc.models.NewsUser;
import de.gainc.webmvc.repositories.NewsUserRepository;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@WebMvcTest(UserApiController.class)
public class SimpleUserApiControllerTest {

  @Autowired
  private MockMvc mockMvc;
  @MockBean
  private NewsUserRepository userRepository;
  private NewsUser testUser;



  @Before
  public void setup() {
    testUser = new NewsUser(
        LocalDate.of(1970, 1, 1),
        "testfirst", "testlast",
        "testuser",
        "testpass");
    when(userRepository.findAll())
        .thenReturn(Arrays.asList(testUser));
    when(userRepository.findById(testUser.getUsername()))
        .thenReturn(Optional.of(testUser));
    when(userRepository.save(any()))
        .thenReturn(testUser);
  }
  @Test
      public void testRestApi() throws Exception{
//    mockMvc.perform(get("/api"))
//        .andExpect(status().isOk())
//        .andExpect(content()
//            .contentType("application/json;charset=UTF-8"))
//        .andExpect(jsonPath("$[0].username")
//            .value(testUser.getUsername()))
//        .andExpect(jsonPath("$[0].firstname")
//            .value(testUser.getFirstname()))
//        .andExpect(jsonPath("$[0].lastname")
//            .value(testUser.getLastname()))
//        .andExpect(jsonPath("$[0].password")
//            .value(testUser.getPassword()))
//        .andExpect(jsonPath("$[0].birthday")
//            .value(testUser.getBirthday().toString()));
//    verify(userRepository, times(1)).findAll();

    mockMvc.perform(get("/api"))
        .andExpect(status().isOk())
        .andExpect(content()
            .contentType("application/hal+json;charset=UTF-8"))
        .andExpect(jsonPath("$._embedded.newsUserResourceList[0].username")
            .value(testUser.getUsername()))
        .andExpect(jsonPath("$._embedded.newsUserResourceList[0].firstname")
            .value(testUser.getFirstname()))
        .andExpect(jsonPath("$._embedded.newsUserResourceList[0].lastname")
            .value(testUser.getLastname()))
        .andExpect(jsonPath("$._embedded.newsUserResourceList[0].birthday")
            .value(testUser.getBirthday().toString()))
        .andExpect(jsonPath("$._embedded.newsUserResourceList[0]._links.self.href")
            .value("http://localhost/api/" + testUser.getUsername()));
    verify(userRepository, times(1)).findAll();


    mockMvc.perform(get("/api/unknown"))
        .andExpect(status().isNotFound());
    verify(userRepository, times(1)).findById("unknown");


  }


  @Test
  public void testJSON() throws Exception{
    mockMvc.perform(get("/api/testuser"))
        .andExpect(status().isOk())
        .andExpect(content()
            .contentType("application/hal+json;charset=UTF-8"))
        .andExpect(jsonPath("$.username")
            .value(testUser.getUsername()))
        .andExpect(jsonPath("$.firstname")
            .value(testUser.getFirstname()))
        .andExpect(jsonPath("$.lastname")
            .value(testUser.getLastname()))
        .andExpect(jsonPath("$.birthday")
            .value(testUser.getBirthday().toString()));
    verify(userRepository, times(1)).findById("testuser");
  }
  @Test
  public void testSave() throws Exception{
//    String json = "{\"username\":\"testuser\"," +
//        "\"firstname\":\"testfirst\"," +
//        "\"lastname\":\"testlast\"," +
//        "\"password\":\"testpass\"," +
//        "\"birthday\":\"1970-01-01\"" +
//        "}";
//    mockMvc.perform(post("/api")
//        .contentType(MediaType.APPLICATION_JSON)
//        .content(json))
//        .andExpect(status().isOk());
//
//    ArgumentCaptor<NewsUser> argument =
//        ArgumentCaptor.forClass(NewsUser.class);
//    verify(userRepository, times(1))
//        .save(argument.capture());
//    NewsUser savedUser = argument.getValue();
//    assertThat(savedUser.getUsername()).isEqualTo("testuser");
//    assertThat(savedUser.getFirstname()).isEqualTo("testfirst");
//    assertThat(savedUser.getLastname()).isEqualTo("testlast");
//    assertThat(savedUser.getBirthday()).isEqualTo(LocalDate.of(1970, 1, 1));
//    assertThat(new BCryptPasswordEncoder()
//        .matches("testpass", savedUser.getPassword()))
//        .isTrue();

    String json = "{\"username\":\"testuser\"," +
        "\"firstname\":\"testfirst\"," +
        "\"lastname\":\"testlast\"," +
        "\"password\":\"testpass\"," +
        "\"birthday\":\"1970-01-01\"" +
        "}";
    mockMvc.perform(post("/api")
        .contentType(MediaType.APPLICATION_JSON)
        .content(json))
        .andExpect(status().isCreated())
        .andExpect(header()
            .string("Location",
                "http://localhost/api/testuser"));
  }





}