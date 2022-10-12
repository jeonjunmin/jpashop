package jpabook.jpashop.domain;


import jpabook.jpashop.domain.item.Item;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.FetchType.*;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 해당 엔티티의 객체를 new로 생성할 수 없게 한다.
public class OrderItem {

    @Id @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "order_id") //Order의 order_id컬럼과 FK 매칭 선언
    private Order order;

    private int orderPrice; //주문가격
    private int count;      //주문수량

    //**생성 메서드**//
    public static OrderItem createOrderItem(Item item, int orderPrice,int count){
        OrderItem orderItem = new OrderItem();
        orderItem.setItem(item);
        orderItem.setOrderPrice(orderPrice);
        orderItem.setCount(count);

        item.removeStock(count);
        return orderItem;
    }

    //**비지니스 로직**//
    public void cancel(){
        //재고수량을 원복한다.
        getItem().addStock(count); //자기 클래스에 선언된 변수를 가져올때 getItem만 써도 된다. / 다른데서 해당 객체(OrderItem)가 선언되면 orderItem.addStock(count); 으로 사용 되어야 한다.
    }

    //**조회 로직**//

    /**
     * 주문상품 전체 가격 조회
     */
    public int getTotalPrice(){
        return getOrderPrice() * getCount();
    }

}
