package hello.springtx.order;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    @Transactional
    public void order(Order order) throws NotEnoughMoneyException {
        log.info("order called");
        orderRepository.save(order);

        log.info("Pay process entered");
        if(order.getUsername().equals("ex")){
            log.info("Pay process exception");
            throw new RuntimeException("system error");
        } else if (order.getUsername().equals("lack of money")) {
            log.info("Pay process lack of money");
            order.setPayStatus("waiting");
            throw new NotEnoughMoneyException("lack of money");
        } else {
            log.info("Pay process success");
            order.setPayStatus("success");
        }
        log.info("Pay process end");
    }
}
