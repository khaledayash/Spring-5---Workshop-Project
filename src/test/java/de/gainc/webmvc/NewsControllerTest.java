package de.gainc.webmvc;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import de.gainc.webmvc.controller.NewsController;
import de.gainc.webmvc.models.NewsItem;
import de.gainc.webmvc.services.NewsService;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@WebMvcTest(NewsController.class)

public class  NewsControllerTest {

  @Autowired
  private MockMvc mockMvc;
  @MockBean
  private  NewsService newsService;

  @Test
  @WithMockUser
  public void testNewsController() throws Exception {
    List<NewsItem> mockItems = IntStream.range(0, 12).mapToObj(i -> new NewsItem("Title " + i, "Desc")).
        collect(Collectors.toList());
    when(newsService.findNews(12)).thenReturn(mockItems);

    mockMvc.perform(get("/news?count=12")).andExpect(status().isOk())
        .andExpect(model().attribute("news", hasItems(mockItems.toArray())))
        .andExpect(content().string(containsString("Title 0")));
  }

}