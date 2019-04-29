package de.gainc.webmvc.hatous;

import de.gainc.webmvc.controller.UserApiController;
import de.gainc.webmvc.hatous.NewsUserResource;
import de.gainc.webmvc.models.NewsUser;
import de.gainc.webmvc.repositories.NewsUserRepository;
import java.util.List;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.web.bind.annotation.GetMapping;

public class NewsUserResourceAssembler extends ResourceAssemblerSupport<NewsUser, NewsUserResource> {

  private NewsUserRepository newsUserRepository;

  public NewsUserResourceAssembler(Class<?> controllerClass,
      Class<NewsUserResource> resourceType, NewsUserRepository newsUserRepository) {
    super(controllerClass, resourceType);
    this.newsUserRepository = newsUserRepository;
  }

  public NewsUserResourceAssembler() {
    super(UserApiController.class, NewsUserResource.class);
  }

  @Override
  protected NewsUserResource instantiateResource(NewsUser u) {
    return new NewsUserResource(
        u.getFirstname(),
        u.getLastname(),
        u.getBirthday(),
        u.getUsername());
  }

  @Override
  public NewsUserResource toResource(NewsUser user) {
    return createResourceWithId(user.getUsername(), user);
  }



}
