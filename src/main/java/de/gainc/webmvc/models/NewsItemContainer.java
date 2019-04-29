package de.gainc.webmvc.models;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;

@Data
public class NewsItemContainer {

   private List<NewsItem> articles = new ArrayList<>();
}