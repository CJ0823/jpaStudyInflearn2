package jpabook.jpashop.repository;

import jpabook.jpashop.domain.item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemRepository {

  private final EntityManager em;

  public void save(Item item) {
    if (item.getId() == null) {
      em.persist(item);
    } else {
      em.merge(item); //사용 금지
      //merge는 준영속 상태의 객체를 업데이트 해준다.
      //파라미터로 넘어온 item 객체의 id값을 기반으로 DB에서 실제 영속성 엔티티를 불러오고 거기에
      //파라미터로 넘어온 객체의 필드 값들을 업데이트 해주는 방식이다.
      //그런데! 모든 필드값들을 업데이트 하는것이 기본 전략이고, 사용자가 직접 입력한 필드값이 없다면 null로 처리해버린다.
      //그래서 실무에서 위험할 수 있으므로 em.merge는 사용하지 않고, 영속성 엔티티를 직접 불러와서 set하여
      //JPA가 제공하는 변경 감지(dirty checking) 기능을 이용한다.
    }
  }

  public Item findOne(Long id) {
    return em.find(Item.class, id);
  }

  public List<Item> findAll() {
    return em.createQuery("select i from Item i", Item.class)
            .getResultList();
  }
}
