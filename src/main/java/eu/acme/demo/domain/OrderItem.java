package eu.acme.demo.domain;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "order_items")
@Data
public class OrderItem extends AuditableEntity {

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @Column(name = "unit_price", columnDefinition = "DECIMAL(9,2)", nullable = false)
    private BigDecimal unitPrice;

    @Column(name = "units", nullable = false)
    private int units;

    @Column(name = "total_price", columnDefinition = "DECIMAL(9,2)", nullable = false)
    private BigDecimal totalPrice;

}
