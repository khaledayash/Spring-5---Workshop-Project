package de.gainc.webmvc.models;

import java.time.LocalDate;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
public class NewsUser {

  @Past
  @NotNull
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private LocalDate birthday;
  @NotBlank
  private String firstname;
  @NotBlank
  private String lastname;
  @Id
  @Size(min = 4, message = "Muss mind. 4 Zeichen lang sein.")
  private String username;
  @Size(min = 4, message = "Muss mind. 4 Zeichen lang sein.")
  private String password;
}