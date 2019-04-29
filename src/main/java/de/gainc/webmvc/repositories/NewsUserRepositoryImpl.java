//package de.gainc.webmvc.repositories;
//
//import de.gainc.webmvc.models.NewsUser;
//import java.time.LocalDate;
//import java.util.HashMap;
//import java.util.Map;
//import org.springframework.stereotype.Component;
//
//@Component
//public class NewsUserRepositoryImpl implements NewsUserRepository {
//
//  private Map<String, NewsUser> map = new HashMap<>();
//
//  public  NewsUserRepositoryImpl() {
//    map.put("buck", new NewsUser( LocalDate.now(),"Buck", "Rogers", "buck", "buck"));
//  }
//
//  @Override
//  public NewsUser findByUsername(String username) {
//    return map.get(username);
//  }
//
//  @Override
//  public NewsUser save(NewsUser user) {
//    map.put(user.getUsername(), user);
//    return user;
//  }
//}