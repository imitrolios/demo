package eu.acme.demo.api;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.common.collect.Lists;
import eu.acme.demo.domain.Order;
import eu.acme.demo.domain.enums.OrderStatus;
import eu.acme.demo.handlers.ErrorResponseDto;
import eu.acme.demo.repository.OrderItemRepository;
import eu.acme.demo.repository.OrderRepository;
import eu.acme.demo.web.dto.OrderDto;
import eu.acme.demo.web.dto.OrderItemDto;
import eu.acme.demo.web.dto.OrderLiteDto;
import eu.acme.demo.web.dto.OrderRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class OrderAPITests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Test
    void testOrderAPI() throws Exception {

        OrderRequest orderRequest = createOrderRequestForTest();

        ObjectMapper om = new ObjectMapper();
        String orderRequestAsString = om.writeValueAsString(orderRequest);
        MvcResult orderResult = this.mockMvc.perform(post("http://api.okto-demo.eu/orders").
                content(orderRequestAsString)
                .contentType("application/json")
                .accept("application/json"))
                .andExpect(status().isCreated())
                .andReturn();

        OrderDto orderDto = om.readValue(orderResult.getResponse().getContentAsString(), OrderDto.class);

        assertThat(orderResult.getResponse().getContentType(), equalTo(MediaType.APPLICATION_JSON_VALUE));
        assertThat(orderResult.getResponse().getStatus(), equalTo(HttpStatus.CREATED.value()));
        assertThat(orderDto.getClientReferenceCode(), equalTo("testCode"));
        assertThat(orderDto.getId(), notNullValue());
        assertThat(orderDto.getOrderItems(), notNullValue());
        assertFalse(orderDto.getOrderItems().isEmpty(), "order items list is empty");
        assertThat(orderDto.getStatus(), notNullValue());
    }

    @Test
    void testOrderDoubleSubmission() throws Exception {
        OrderRequest orderRequest = createOrderRequestForTest();

        ObjectMapper om = new ObjectMapper();
        String orderRequestAsString = om.writeValueAsString(orderRequest);
        this.mockMvc.perform(post("http://api.okto-demo.eu/orders").
                content(orderRequestAsString)
                .contentType("application/json")
                .accept("application/json"))
                .andExpect(status().isCreated())
                .andReturn();

        MvcResult orderResult = this.mockMvc.perform(post("http://api.okto-demo.eu/orders").
                content(orderRequestAsString)
                .contentType("application/json")
                .accept("application/json"))
                .andExpect(status().is4xxClientError())
                .andReturn();

        // deserialize java.time.LocalDateTime
        om.registerModule(new JavaTimeModule());
        ErrorResponseDto errorResponseDto = om.readValue(orderResult.getResponse().getContentAsString(), ErrorResponseDto.class);

        assertThat(orderResult.getResponse().getContentType(), equalTo(MediaType.APPLICATION_JSON_VALUE));
        assertThat(orderResult.getResponse().getStatus(), equalTo(HttpStatus.BAD_REQUEST.value()));
        assertThat(errorResponseDto.getStatus(), equalTo(400));
        assertThat(errorResponseDto.getError(), notNullValue());
        assertThat(errorResponseDto.getMessage(), notNullValue());
    }

    @Test
    void testFetchAllOrders() throws Exception {

        orderRepository.saveAndFlush(createOrderForTest());
        orderRepository.saveAndFlush(createOrderForTest());

        MvcResult mvcResult = this.mockMvc.perform(get("http://api.okto-demo.eu/orders").
                contentType("application/json")
                .accept("application/json"))
                .andExpect(status().isOk())
                .andReturn();

        ObjectMapper om = new ObjectMapper();
        List<OrderLiteDto> orderLiteDtos = om.readValue(mvcResult.getResponse().getContentAsString(),
                new TypeReference<List<OrderLiteDto>>() {
                });

        assertThat(mvcResult.getResponse().getContentType(), equalTo(MediaType.APPLICATION_JSON_VALUE));
        assertThat(mvcResult.getResponse().getStatus(), equalTo(HttpStatus.OK.value()));
        assertThat(orderLiteDtos.size(), equalTo(2));
        assertThat(orderLiteDtos.get(0).getId(), notNullValue());
        assertThat(orderLiteDtos.get(1).getId(), notNullValue());
    }

    @Test
    void testFetchCertainOrder() throws Exception {
        Order order = createOrderForTest();
        orderRepository.saveAndFlush(order);

        MvcResult mvcResult = this.mockMvc.perform(get("http://api.okto-demo.eu/orders/" + order.getId())
                .contentType("application/json")
                .accept("application/json"))
                .andExpect(status().isOk())
                .andReturn();

        ObjectMapper om = new ObjectMapper();
        OrderDto orderDto = om.readValue(mvcResult.getResponse().getContentAsString(), OrderDto.class);

        assertThat(mvcResult.getResponse().getContentType(), equalTo(MediaType.APPLICATION_JSON_VALUE));
        assertThat(mvcResult.getResponse().getStatus(), equalTo(HttpStatus.OK.value()));
        assertThat(orderDto, notNullValue());
        assertThat(orderDto.getId(), notNullValue());
        assertThat(orderDto.getClientReferenceCode(), equalTo("testCode"));
    }

    @Test
    void testFetchCertainOrderThatDoesNotExistReturns400() throws Exception {
        Order order = createOrderForTest();
        orderRepository.saveAndFlush(order);

        MvcResult mvcResult = this.mockMvc.perform(get("http://api.okto-demo.eu/orders/" + UUID.randomUUID())
                .contentType("application/json")
                .accept("application/json"))
                .andExpect(status().is4xxClientError())
                .andReturn();

        ObjectMapper om = new ObjectMapper();
        om.registerModule(new JavaTimeModule());
        ErrorResponseDto errorResponseDto = om.readValue(mvcResult.getResponse().getContentAsString(), ErrorResponseDto.class);

        assertThat(mvcResult.getResponse().getContentType(), equalTo(MediaType.APPLICATION_JSON_VALUE));
        assertThat(mvcResult.getResponse().getStatus(), equalTo(HttpStatus.BAD_REQUEST.value()));
        assertThat(errorResponseDto.getStatus(), equalTo(400));
        assertThat(errorResponseDto.getError(), notNullValue());
        assertThat(errorResponseDto.getMessage(), notNullValue());
    }

    private Order createOrderForTest() {
        Order order = new Order();
        order.setDescription("test order");
        order.setStatus(OrderStatus.UNDER_PROCESS);
        order.setClientReferenceCode("testCode");
        order.setItemCount(5);
        order.setItemTotalAmount(BigDecimal.valueOf(50.00));
        order.setCreatedDate(Instant.now());

        return order;
    }

    private OrderRequest createOrderRequestForTest() {
        OrderRequest orderRequest = new OrderRequest();
        orderRequest.setDescription("test order");
        orderRequest.setStatus(OrderStatus.UNDER_PROCESS);
        orderRequest.setClientReferenceCode("testCode");
        orderRequest.setItemCount(5);
        orderRequest.setItemTotalAmount(BigDecimal.valueOf(50.00));

        OrderItemDto orderItemDto = new OrderItemDto();
        orderItemDto.setUnits(6);
        orderItemDto.setUnitPrice(BigDecimal.valueOf(5.00));
        orderItemDto.setTotalPrice(BigDecimal.valueOf(30));
        List<OrderItemDto> orderItemDtos = Lists.newArrayList(orderItemDto);

        orderRequest.setOrderItemDtos(orderItemDtos);
        return orderRequest;
    }

    @AfterEach
    private void truncateTablesInDb() {
        orderItemRepository.deleteAll();
        orderRepository.deleteAll();
    }
}

