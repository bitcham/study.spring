package jpabook.jpashop.service;

import jakarta.persistence.EntityManager;
import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.exception.NotEnoughStockException;
import jpabook.jpashop.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class OrderServiceTest {

   @Autowired
   EntityManager em;
   @Autowired
   OrderService orderService;
    @Autowired
    OrderRepository orderRepository;

    @Test
    public void orderItem() throws Exception {
        // given
        Member member = getMember();

        Item item = getBook("JPA", 10000, 10);

        int orderCount = 2;

        // when
        Long orderId = orderService.order(member.getId(), item.getId(), orderCount);

        // then
        Order findOrder = orderRepository.findOne(orderId);

        assertThat(findOrder.getOrderStatus()).isEqualTo(OrderStatus.ORDER);
        assertThat(findOrder.getOrderItems().size()).isEqualTo(1);
        assertThat(findOrder.getTotalPrice()).isEqualTo(10000 * orderCount);
        assertThat(item.getStockQuantity()).isEqualTo(8);
    }

    @Test
    public void cancelOrder() throws Exception {
        // given
        Member member = getMember();
        Item item = getBook("JPA", 10000, 10);

        // when
        int orderCount = 11;

        // then
        assertThatThrownBy(() -> orderService.order(member.getId(), item.getId(), orderCount))
                .isInstanceOf(NotEnoughStockException.class);

    }

    @Test
    public void overstock() throws Exception {
        // given
        Member member = getMember();
        Item item = getBook("JPA", 10000, 10);
        int orderCount = 2;

        Long orderId = orderService.order(member.getId(), item.getId(), orderCount);

        // when
        orderService.cancelOrder(orderId);

        // then
        Order findOrder = orderRepository.findOne(orderId);

        assertThat(findOrder.getOrderStatus()).isEqualTo(OrderStatus.CANCEL);
        assertThat(item.getStockQuantity()).isEqualTo(10);
    }


    private Member getMember() {
        Member member = new Member();
        member.setName("hello");
        member.setAddress(new Address("seoul", "gangnam", "123-123"));
        em.persist(member);
        return member;
    }

    private Book getBook(String name, int price, int stockQuantity) {
        Book book = new Book();
        book.setName(name);
        book.setPrice(price);
        book.setStockQuantity(stockQuantity);
        em.persist(book);
        return book;
    }
}