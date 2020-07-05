package eu.acme.demo.config;

import com.fasterxml.classmate.TypeResolver;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.info.BuildProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;
import java.util.List;
import java.util.Set;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    private static final List<Parameter> headerParameters = Collections.emptyList();

    private static final String HEADER = "header";
    private static final String STRING = "string";

    private static final List<Parameter> HEADER_PARAMETERS = Lists.newArrayList(
            new ParameterBuilder().name("Authorization")
                    .modelRef(new ModelRef(STRING))
                    .parameterType(HEADER)
                    .required(true)
                    .description(
                            "Basic Authorization Required")
                    .build()
    );

    private BuildProperties buildProperties;

    private static final Set<String> PROTOCOLS = Sets.newHashSet("http", "https");

    @Autowired
    public SwaggerConfig(BuildProperties buildProperties) {
        this.buildProperties = buildProperties;
    }

    @Bean
    public Docket api() {

        TypeResolver typeResolver = new TypeResolver();

        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("Demo Order Api")
                .select()
                .apis(
                        RequestHandlerSelectors.basePackage(
                                "eu.acme.demo.web")
                )
                .build()
                .globalOperationParameters(headerParameters)
                .produces(Collections.singleton("application/json"))
                .consumes(Collections.singleton("application/json"))
                .protocols(PROTOCOLS)
                .globalOperationParameters(HEADER_PARAMETERS)
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        return new ApiInfo(
                "Demo order REST API",
                "Demo order REST API",
                buildProperties.getVersion(),
                "",
                new Contact("Ioannis Mitrolios", "https://www.google.com",
                        "i.mitrolios@gmail.com"),
                "Apache 2.0", "http://www.apache.org/licenses/LICENSE-2.0.html", Collections.emptyList());
    }
}
