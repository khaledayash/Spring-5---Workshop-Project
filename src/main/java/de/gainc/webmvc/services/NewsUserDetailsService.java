package de.gainc.webmvc.services;


import de.gainc.webmvc.models.NewsUser;
import de.gainc.webmvc.repositories.NewsUserRepository;
import java.util.ArrayList;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class NewsUserDetailsService implements UserDetailsService {

  private NewsUserRepository userRepository;

  public NewsUserDetailsService(NewsUserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
   public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    NewsUser newsUser = userRepository.findByUsername(username);
     if (newsUser == null ){
       throw new  UsernameNotFoundException("Unknown user");
    } return new User(newsUser.getUsername(), newsUser.getPassword(),  new ArrayList<>());
  }
}