package eu.acme.demo.web.dto;

import eu.acme.demo.domain.enums.OrderStatus;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class OrderRequest {

    private String clientReferenceCode;
    private List<OrderItemDto> orderItemDtos;
    private String description;
    private BigDecimal itemTotalAmount;
    private int itemCount;
    private OrderStatus status;

}
