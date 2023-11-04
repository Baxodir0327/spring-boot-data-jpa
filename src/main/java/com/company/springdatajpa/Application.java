package com.company.springdatajpa;

import com.company.springdatajpa.entity.Post;
import com.company.springdatajpa.repository.PostRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.net.URL;
import java.util.List;
import java.util.Optional;

@SpringBootApplication
@EnableJpaAuditing
public class Application {
    private final SessionUser sessionUser;

    public Application(SessionUser sessionUser) {
        this.sessionUser = sessionUser;
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

  /*  @Bean*/
    CommandLineRunner runner(PostRepository postRepository, ObjectMapper objectMapper) {
        return args -> {
            URL url = new URL("https://jsonplaceholder.typicode.com/posts");
            List<Post> posts = objectMapper.readValue(url, new TypeReference<>() {});

            postRepository.saveAll(posts);
        };
    }

    @Bean
    public AuditorAware<Long> auditorAware() {
        return () -> Optional.ofNullable(sessionUser.getId());
    }
}
