package jpabook.jpashop.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Table;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Entity
@Table(name="orders")
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 해당 엔티티의 객체를 new로 생성할 수 없게 한다.
public class Order {

    @Id @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = LAZY) // Member객체(member테이블)와 다:1 관계
    @JoinColumn(name = "member_id") // Member객체(member테이블)의 member_id와 FK 설정
    private Member member;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL) //OderItem 객체의 order가 FK를 가지고 있으므로 주인임을 나타낸다., 두 Entity가 서로를 가지고 있을때 주인관계를 나타내기 위해 사용한다.
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne(fetch = LAZY, cascade = CascadeType.ALL) //1:1관계일때 사용
    @JoinColumn(name = "delivery_id") //Delivery의 delivery_id 컬럼과 FK 매칭 선언
    private Delivery delivery;

    private LocalDateTime orderDate;

    private OrderStatus status; //주문상태 [ORDER, CANCEL]

    //**연관관계 메서드**// --양방향일때 사용하는 메서드
    public void setMember(Member member){
        this.member = member;
        member.getOrders().add(this);
    }

    public void addOrderItem(OrderItem orderItem){
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    public void setDelivery(Delivery delivery){
        this.delivery = delivery;
        delivery.setOrder(this);
    }

    //**생성 메서드**//
    public static Order createOrder(Member member, Delivery delivery, OrderItem... orderItems) {  //...은 해당 객체를 여러개 넣을 수 있다는 뜻이다.
        Order order = new Order();
        order.setMember(member);
        order.setDelivery(delivery);
        for(OrderItem orderItem : orderItems ) {
            order.addOrderItem(orderItem);
        }
        order.setStatus(OrderStatus.ORDER);
        order.setOrderDate(LocalDateTime.now());
        return order;
    }

    //**비지니스 로직**//
    /**
     * 주문 취소
     */
    public void cancel() {
        if(delivery.getStatus() == DeliveryStatus.COMP){
            throw new IllegalStateException("이미 배송완료된 상품은 취소가 불가능합니다.");
        }
        this.setStatus(OrderStatus.CANCEL);
        for (OrderItem orderItem : orderItems){
            orderItem.cancel();
        }
    }


    //**조회 로직**//
    /**
     * 전체 주문 가격 조회
     */
    public int getTotalPrice() {
        int totalPrice = 0;
        for(OrderItem orderItem : orderItems){
            totalPrice += orderItem.getTotalPrice();
        }
        return totalPrice;
    }


}
