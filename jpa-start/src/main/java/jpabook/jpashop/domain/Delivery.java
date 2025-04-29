package jpabook.jpashop.domain;

import jakarta.persistence.*;

import static jakarta.persistence.FetchType.*;

@Entity
public class Delivery {

    @Id
    @GeneratedValue
    private Long id;

    @Embedded
    private Address address;

    @OneToOne(mappedBy = "delivery", fetch = LAZY)
    private Order order;

    private DeliveryStatus status;


}
