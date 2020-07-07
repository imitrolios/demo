package eu.acme.demo.handlers;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@ApiModel("errorResponseDto")
@Data
public class ErrorResponseDto {

    @ApiModelProperty(
            accessMode = ApiModelProperty.AccessMode.READ_WRITE,
            name = "status",
            notes = "HTTP status code",
            dataType = "Number",
            example = "500")
    private Integer status;

    @ApiModelProperty(
            accessMode = ApiModelProperty.AccessMode.READ_WRITE,
            name = "error",
            notes = "Error type",
            dataType = "String",
            example = "ValidationException")
    private String error;

    @ApiModelProperty(
            accessMode = ApiModelProperty.AccessMode.READ_WRITE,
            name = "message",
            notes = "Error message",
            dataType = "String",
            example = "Error")
    private String message;

    @ApiModelProperty(
            accessMode = ApiModelProperty.AccessMode.READ_WRITE,
            name = "path",
            notes = "Request path",
            dataType = "String",
            example = "/orders")
    private String path;

    @ApiModelProperty(
            accessMode = ApiModelProperty.AccessMode.READ_WRITE,
            name = "timestamp",
            notes = "The date time when the error was returned",
            dataType = "String",
            example = "2020-07-06T10:43:27")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime timestamp;
}
