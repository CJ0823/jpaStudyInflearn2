package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@Setter
public class Order {

  @Id
  @GeneratedValue
  @Column(name = "order_id")
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "member_id")
  private Member member;

  @OneToMany(mappedBy = "order")
  private List<OrderItem> orderItems = new ArrayList<>();

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "delivery_id")
  private Delivery delivery;

  private LocalDateTime orderDate; //주문시간

  @Enumerated(value = EnumType.STRING)
  private OrderStatus orderStatus; //주문상태 ORDER, CANCEL

  public void changeMember(Member member) {
    this.setMember(member);
    member.getOrders().add(this);
  }

  public void changeDelivery(Delivery delivery) {
    this.setDelivery(delivery);
    delivery.setOrder(this);
  }

}
