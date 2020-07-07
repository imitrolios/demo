package eu.acme.demo.mappers;

import eu.acme.demo.domain.Order;
import eu.acme.demo.domain.enums.OrderStatus;
import eu.acme.demo.web.dto.OrderLiteDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class OrderMapperTests {
    @Autowired
    private OrderMapper orderMapper;

    @Test
    public void when_order_mapper_should_return_orderLiteDto() {

        Order order = new Order();

        order.setClientReferenceCode("testCode");
        order.setStatus(OrderStatus.UNDER_PROCESS);
        order.setDescription("testDescription");

        OrderLiteDto orderLiteDto = orderMapper.toOrderLiteDto(order);

        assertThat(orderLiteDto).isNotNull();
        assertThat(orderLiteDto.getClientReferenceCode()).isEqualTo("testCode");
        assertThat(orderLiteDto.getStatus()).isEqualTo(OrderStatus.UNDER_PROCESS);
        assertThat(orderLiteDto.getDescription()).isEqualTo("testDescription");
    }

}
