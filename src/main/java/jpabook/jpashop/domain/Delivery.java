package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class Delivery {
    @Id
    @GeneratedValue
    @Column(name = "delivery_id")
    private long id;

    @OneToOne(mappedBy ="delivery" ) // Order Entity의 delivery가 주인(FK라는 뜻)
    private Order order;

    @Embedded
    private Address address;

    @Enumerated(EnumType.STRING) //데이터가 enum에서 정의한 string타입으로 들어가게 한다.
    private DeliveryStatus status; //READY ,COMP

}
