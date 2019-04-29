package de.gainc.webmvc.api;

import de.gainc.webmvc.models.NewsItem;
import de.gainc.webmvc.models.NewsItemContainer;
import de.gainc.webmvc.services.NewsService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.cache.annotation.CacheResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@Slf4j
public class NewsServiceImpl implements NewsService {

  private String apikey;
  private RestTemplate restTemplate;

  public NewsServiceImpl(@Value("${newsapi.key}") String apikey,
      RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
    this.apikey = apikey;
  }

  private static final String api = "https://newsapi.org/v2/top-headlines?"
      + "country=de&"
      + "apiKey={apikey}&"
      + "pageSize={count}&"
      + "page=0";

  @Override
  @CacheResult(cacheName = "newscache")
  public List<NewsItem> findNews(int count) {
    Map<String, String> param = new HashMap<>();
    param.put("count", String.valueOf(count));
    param.put("apikey", apikey);
    log.info("calling news Api");
    return restTemplate.getForObject(api, NewsItemContainer.class, param).getArticles();
  }

}
