package de.gainc.webmvc.controller;

import static org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.fromMethodCall;
import static org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.on;

import de.gainc.webmvc.hatous.NewsUserResource;
import de.gainc.webmvc.hatous.NewsUserResourceAssembler;
import de.gainc.webmvc.models.NewsUser;
import de.gainc.webmvc.repositories.NewsUserRepository;
import java.util.List;
import javax.persistence.EntityNotFoundException;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponents;

@RestController
@RequestMapping("/api")
public class UserApiController {

  private NewsUserRepository newsUserRepository;
  private PasswordEncoder passwordEncoder;


  public UserApiController(NewsUserRepository newsUserRepository, PasswordEncoder passwordEncoder) {
    this.newsUserRepository = newsUserRepository;
    this.passwordEncoder = passwordEncoder;
  }

//  @GetMapping
//  public List<NewsUser> findAll() {
//    return newsUserRepository.findAll();
//  }
@GetMapping
public Resources<NewsUserResource> findAll() {
  List<NewsUser> users = newsUserRepository.findAll();
  List<NewsUserResource> resources = new
      NewsUserResourceAssembler().toResources(users);
  return new Resources<>(resources);
}

//  @GetMapping("/{id}")
//  public ResponseEntity<NewsUser> findById(@PathVariable("id") String id) {
//    return newsUserRepository.findById(id)
//        .map(u -> ResponseEntity.ok(u))
//        .orElse(ResponseEntity.notFound().build());
//  }

  @GetMapping("/{id}")
  public ResponseEntity<NewsUserResource> findById(@PathVariable("id") String id) {
    return newsUserRepository.findById(id)
        .map(u -> new NewsUserResourceAssembler().toResource(u))
        .map(r -> ResponseEntity.ok(r))
        .orElse(ResponseEntity.notFound().build());
  }
//  @PostMapping
//  public NewsUser save(@RequestBody NewsUser user) {
//    String pass = passwordEncoder.encode(user.getPassword());
//    user.setPassword(pass);
//    return newsUserRepository.save(user);
//  }

//  @PostMapping
//  public ResponseEntity<NewsUser> save(@RequestBody NewsUser user) {
//    String pass = passwordEncoder.encode(user.getPassword());
//    user.setPassword(pass);
//    user = newsUserRepository.save(user);
//    UriComponents uri =
//        fromMethodCall(on(UserApiController.class)
//            .findById(user.getUsername())).build();
//    return ResponseEntity.created(uri.toUri()).build();
//  }
@PostMapping
public ResponseEntity<?> save(@RequestBody NewsUser user) {
  String pass = passwordEncoder.encode(user.getPassword());
  user.setPassword(pass);
  NewsUser savedUser = newsUserRepository.save(user);
  UriComponents uri =
      fromMethodCall(on(UserApiController.class)
          .findById(savedUser.getUsername())).build();
  return ResponseEntity.created(uri.toUri()).build();
}


}
