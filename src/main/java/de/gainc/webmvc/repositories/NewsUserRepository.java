package de.gainc.webmvc.repositories;

import de.gainc.webmvc.models.NewsUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsUserRepository extends JpaRepository<NewsUser, String> {
NewsUser findByUsername(String username);
//NewsUser save(NewsUser user);
}