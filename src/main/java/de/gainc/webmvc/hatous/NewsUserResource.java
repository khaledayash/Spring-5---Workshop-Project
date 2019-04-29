package de.gainc.webmvc.hatous;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.hateoas.ResourceSupport;

@AllArgsConstructor
public class NewsUserResource extends ResourceSupport {

  @Getter
  private String firstname;
  @Getter
  private String lastname;
  @Getter
  private LocalDate birthday;
  @Getter
  private String username;
}