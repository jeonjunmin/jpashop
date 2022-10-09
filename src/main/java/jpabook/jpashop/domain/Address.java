package jpabook.jpashop.domain;

import lombok.Getter;

import javax.persistence.Embeddable;

@Embeddable //내장 타입 설정, 참조 할때 사용
@Getter
public class Address {

    private String city;
    private String street;
    private String zipcode;

    protected  Address() {

    }

    public Address(String city, String street, String zipcode) {
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }
}
