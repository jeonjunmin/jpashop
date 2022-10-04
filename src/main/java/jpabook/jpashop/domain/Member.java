package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Member {
    @Id @GeneratedValue
    @Column(name = "member_id")
    private long id;

    private String name;

    @Embedded //내장 타입 설정, 참조 할때 사용
    private Address address;

    @OneToMany(mappedBy = "member") // oder객체(orders테이블)과 1:다 관계 , (mappedBy = "member")는 order객체의 member에를 FK의 주체로 삼는다는 뜻
    private List<Order> orders = new ArrayList<>();

}
