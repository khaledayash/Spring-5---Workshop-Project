package de.gainc.webmvc.controller;


import de.gainc.webmvc.models.NewsUser;
import de.gainc.webmvc.repositories.NewsUserRepository;
import java.time.LocalDate;
import javax.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/user")
public class SimpleRegistrationController {


  private NewsUserRepository newsUserRepository;
  private PasswordEncoder passwordEncoder;

  public SimpleRegistrationController(NewsUserRepository newsUserRepository, PasswordEncoder passwordEncoder) {
    this.newsUserRepository = newsUserRepository;
    this.passwordEncoder = passwordEncoder;
  }

  @GetMapping("/register")
  public String showRegistration(Model model) {
    model.addAttribute("newsUser", new NewsUser());
    return "register";
  }

  @PostMapping("/register")
  public String processRegistration(@Valid NewsUser user, BindingResult bindingResult, Model model) {
    if (bindingResult.hasErrors()) {
      model.addAttribute("newsUser", user);
      model.addAttribute("errors", bindingResult.getFieldErrors());
      return "register";
    }
    String pass = passwordEncoder.encode(user.getPassword());
    user.setPassword(pass);
    newsUserRepository.save(user);

    return  "redirect:/user/profile/" + user.getUsername();
  }
}