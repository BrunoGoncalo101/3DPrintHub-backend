package pt.utp.estg.pp.printhub.config;

import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI apiInfo() {
        return new OpenAPI()
                .info(new Info()
                        .title("3D Print Hub API")
                        .description("API REST da loja online de impressão 3D: catálogo, pedidos de impressão personalizada, carrinho e encomendas.")
                        .version("1.0"));
    }
}
