package de.gainc.webmvc.integration_test;

import de.gainc.webmvc.repositories.NewsUserRepository;
import org.mockito.AdditionalAnswers;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class MockRepositoryConfiguration {
@Primary
@Bean
NewsUserRepository newsUserRepositoryMock(final NewsUserRepository real) {
// workaround for https://github.com/spring-projects/spring-boot/issues/7033
return Mockito.mock(NewsUserRepository.class,
AdditionalAnswers.delegatesTo(real));
}
}
