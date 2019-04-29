package de.gainc.webmvc.controller;

import de.gainc.webmvc.models.NewsItem;
import de.gainc.webmvc.services.NewsService;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class NewsController {

  private NewsService service;

  public NewsController(NewsService service) {
    this.service = service;
  }

  @GetMapping("/news")
  public String showRecentNews(Model model, @RequestParam(name = "count", defaultValue = "5") int count) {
    List<NewsItem> news = service.findNews(count);
    model.addAttribute("news", news);
    return "news";
  }
}
