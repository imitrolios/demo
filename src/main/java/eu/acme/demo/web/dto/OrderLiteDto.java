package eu.acme.demo.web.dto;

import eu.acme.demo.domain.enums.OrderStatus;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class OrderLiteDto {
    private UUID id;
    private OrderStatus status;
    private String description;
    /**
     * reference code used by client system to track order
     */
    private String clientReferenceCode;
    private BigDecimal totalAmount;
    private int itemCount;
}
