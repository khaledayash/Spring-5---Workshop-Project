package de.gainc.webmvc.services;

import de.gainc.webmvc.models.NewsItem;
import java.util.List;

public interface NewsService {

  List<NewsItem> findNews(int count);
}