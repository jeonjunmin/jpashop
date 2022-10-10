package jpabook.jpashop.domain.item;

import jpabook.jpashop.Exception.NotEnoughStockException;
import jpabook.jpashop.domain.Category;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE) //상속 받은 Entity를 현재 Item 테이블에 다 모은다는 뜻
@DiscriminatorColumn(name="dtype") //상속 받은 Entity 구분값, 상속받은 각 Entity는 DiscriminatorValue로 정의되어 있음 
@Getter @Setter
public abstract class Item {
    @Id
    @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String name;
    private int price;
    private int stockQuantity;

    @ManyToMany(mappedBy = "items")
    private List<Category> categories = new ArrayList<>();


    /**
     * 비지니스 로직
     * #엔티티가 가지고 있는 값을 변경하는 비지니스 로직은 해당 엔티티 안에 구현하는게 좋다.
     */
    //스톡 증가
    public void addStock(int quantity) {
        this.stockQuantity += quantity;
    }

    //스톡 감소
    public void removeStock(int quantity) {
        int restStock = this.stockQuantity = quantity;
        if(restStock < 0){
            throw new NotEnoughStockException("need more stock");
        }
        this.stockQuantity = restStock;
    }



}
