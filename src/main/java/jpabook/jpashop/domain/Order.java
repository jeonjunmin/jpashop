package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Table;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="orders")
@Getter @Setter
public class Order {

    @Id @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @ManyToMany // Member객체(member테이블)와 다:1 관계
    @JoinColumn(name = "member_id") // Member객체(member테이블)의 member_id와 FK 설정
    private Member member;

    @OneToMany(mappedBy = "order") //OderItem 객체의 order가 FK를 가지고 있으므로 주인임을 나타낸다., 두 Entity가 서로를 가지고 있을때 주인관계를 나타내기 위해 사용한다.
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne //1:1관계일때 사용
    @JoinColumn(name = "delivery_id") //Delivery의 delivery_id 컬럼과 FK 매칭 선언
    private Delivery delivery;

    private LocalDateTime orderDate;

    private OrderStatus status; //주문상태 [ORDER, CANCEL]
}
