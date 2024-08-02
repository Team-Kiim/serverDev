package teamkiim.koffeechat.global.swagger.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Swagger 관련 설정 클래스
 */
@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI(){

        Info info = new Info()
                .title("KoffeeChat API Document")
                .description("CBNU SW 졸업작품 KoffeeChat 프로젝트의 API 명세서");

        return new OpenAPI()
                .info(info);
    }
}
