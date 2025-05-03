package hello.advanced.trace.threadlocal;

import hello.advanced.trace.threadlocal.code.ThreadLocalService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class ThreadLocalServiceTest {

    private ThreadLocalService service = new ThreadLocalService();

    @Test
    void field(){
        log.info("main start");
        Runnable UserA = () ->{
            log.info("UserA start");
            service.logic("userA");
            log.info("UserA end");
        };

        Runnable UserB = () ->{
            log.info("UserB start");
            service.logic("userB");
            log.info("UserB end");
        };

        Thread threadA = new Thread(UserA);
        threadA.setName("thread-A");
        Thread threadB = new Thread(UserB);
        threadB.setName("thread-B");
        threadA.start();
        sleep(100);
        threadB.start();
        sleep(3000);
        log.info("main end");
    }

    private void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
