package de.gainc.webmvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.containsString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import de.gainc.webmvc.controller.SimpleRegistrationController;
import de.gainc.webmvc.models.NewsUser;
import de.gainc.webmvc.repositories.NewsUserRepository;
import java.time.LocalDate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@WebMvcTest(SimpleRegistrationController.class)
public class SimpleRegisterationControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private NewsUserRepository newsUserRepository;


  @Test
  public void testShowRegistrationForm() throws Exception {
    mockMvc.perform(get("/user/register")).andExpect(model().attributeExists("newsUser")).andExpect(status().isOk())
        .andExpect(content().string(containsString("Register")));
  }

  @Test
  public void testFormProssesing() throws Exception {
    NewsUser checkUser = new NewsUser(LocalDate.of(1970, 1, 1), "test", "test", "user", "pass");
    mockMvc.perform(post("/user/register")
        .with(csrf())
        .param("birthday", "1970-01-01")
        .param("firstname", "test")
        .param("lastname", "test")
        .param("username", "user").param("password", "pass")).andExpect(redirectedUrl("/user/profile/user"));
    ArgumentCaptor<NewsUser> argument = ArgumentCaptor.forClass(NewsUser.class);
    verify(newsUserRepository, times(1))
        .save(argument.capture());
    NewsUser savedUser = argument.getValue();

    assertThat(savedUser.getUsername()).isEqualTo("user");
    assertThat(savedUser.getFirstname()).isEqualTo("test");
    assertThat(savedUser.getLastname()).isEqualTo("test");
    assertThat(savedUser.getBirthday()).isEqualTo(LocalDate.of(1970, 1, 1));
    assertThat(new BCryptPasswordEncoder().matches("pass", savedUser.getPassword())).isTrue();
  }

  @Test
  public void testFormValidation() throws Exception {
    NewsUser checkUser = new NewsUser(LocalDate.of(1970, 1, 1), "a", "b", "use", "pass");
    mockMvc.perform(post("/user/register")
        .with(csrf())
        .param("birthday", "1970-01-01")
        .param("firstname", "a")
        .param("lastname", "b")
        .param("username", "use")
        .param("password", "pass"))
        .andExpect(view().name("register"))
        .andExpect(content().string(containsString("Muss mind. 4 Zeichen lang sein.")));
    verify(newsUserRepository, times(0)).save(checkUser);
  }
}