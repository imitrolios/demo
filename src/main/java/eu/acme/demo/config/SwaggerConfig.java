package eu.acme.demo.config;

import com.fasterxml.classmate.TypeResolver;
import com.google.common.collect.Sets;
import eu.acme.demo.handlers.ErrorResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.info.BuildProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    private static final List<Parameter> headerParameters = Collections.emptyList();

    private static final ModelRef ERROR_RESPONSE_MODEL_REF = new ModelRef("errorResponseDto");

    private static final List<ResponseMessage> RESPONSE_MESSAGES = Arrays.asList(
            new ResponseMessageBuilder()
                    .code(HttpServletResponse.SC_BAD_REQUEST)
                    .message("Bad request")
                    .responseModel(ERROR_RESPONSE_MODEL_REF)
                    .build(),
            new ResponseMessageBuilder()
                    .code(HttpServletResponse.SC_NOT_FOUND)
                    .message("Not found")
                    .responseModel(ERROR_RESPONSE_MODEL_REF)
                    .build(),
            new ResponseMessageBuilder()
                    .code(HttpServletResponse.SC_INTERNAL_SERVER_ERROR)
                    .message("Internal server error")
                    .responseModel(ERROR_RESPONSE_MODEL_REF)
                    .build()
    );

    private static final String HEADER = "header";
    private static final String STRING = "string";

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
                .globalResponseMessage(RequestMethod.GET, RESPONSE_MESSAGES)
                .globalResponseMessage(RequestMethod.POST, RESPONSE_MESSAGES)
                .globalResponseMessage(RequestMethod.PUT, RESPONSE_MESSAGES)
                .globalResponseMessage(RequestMethod.DELETE, RESPONSE_MESSAGES)
                .additionalModels(typeResolver.resolve(ErrorResponseDto.class))
                .produces(Collections.singleton("application/json"))
                .consumes(Collections.singleton("application/json"))
                .protocols(PROTOCOLS)
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
