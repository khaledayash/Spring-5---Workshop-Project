package de.gainc.webmvc.controller;

import de.gainc.webmvc.exceptions.UnknownNewsUserException;
import de.gainc.webmvc.models.NewsUser;
import de.gainc.webmvc.repositories.NewsUserRepository;
import org.omg.CORBA.UnknownUserException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class NewsUserController {

  @ExceptionHandler(UnknownNewsUserException.class)
  public String userNotFound() {
    return "usernotfound";
  }


  private NewsUserRepository newsUserRepository;

  public NewsUserController(NewsUserRepository newsUserRepository) {
    this.newsUserRepository = newsUserRepository;
  }


  @GetMapping("/profile/{username}")
  public String showProfile(Model model, @PathVariable("username") String username) {

    NewsUser user = newsUserRepository.findByUsername(username);
    if (user == null) {
      throw new UnknownNewsUserException();
    }
    model.addAttribute("newsUser", user);

    return "profile";
  }
}
