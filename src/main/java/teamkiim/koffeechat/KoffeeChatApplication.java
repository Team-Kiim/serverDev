package teamkiim.koffeechat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;

//@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
@SpringBootApplication
@EnableJpaAuditing
@EnableAsync
public class KoffeeChatApplication {

    public static void main(String[] args) {
        SpringApplication.run(KoffeeChatApplication.class, args);
    }

}
