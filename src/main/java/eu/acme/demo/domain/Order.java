package eu.acme.demo.domain;

import eu.acme.demo.domain.enums.OrderStatus;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "orders")
@Data
public class Order extends AuditableEntity {

    @Column(name = "ref_code", length = 30, nullable = false)
    private String clientReferenceCode;

    @Column(name = "description")
    private String description;

    @Column(name = "total_amount", columnDefinition = "DECIMAL(9,2)", nullable = false)
    private BigDecimal itemTotalAmount;

    @Column(name = "item_count", nullable = false)
    private int itemCount;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 20, nullable = false)
    private OrderStatus status;

}
