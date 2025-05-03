package hello.advanced.trace.template;

import hello.advanced.trace.template.code.AbstractTemplate;
import hello.advanced.trace.template.code.SubClassLogic1;
import hello.advanced.trace.template.code.SubClassLogic2;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class TemplateMethodTest {

    @Test
    void templateMethodV0() {
        logic1();
        logic2();
    }

    private void logic1(){
        long startTime = System.currentTimeMillis();
        log.info("logic1 start");
        long endTime = System.currentTimeMillis();
        long resultTime = endTime - startTime;
        log.info("logic1 end time={}ms", resultTime);
    }

    private void logic2(){
        long startTime = System.currentTimeMillis();
        log.info("logic2 start");
        long endTime = System.currentTimeMillis();
        long resultTime = endTime - startTime;
        log.info("logic2 end time={}ms", resultTime);
    }

    @Test
    void templateMethodV1() {
        AbstractTemplate template = new SubClassLogic1();
        template.execute();

        AbstractTemplate template2 = new SubClassLogic2();
        template2.execute();
    }

    @Test
    void templateMethodV2() {
        AbstractTemplate template = new AbstractTemplate(){
            @Override
            protected void call() {
                log.info("business logic1");
            }
        };

        AbstractTemplate template2 = new AbstractTemplate(){
            @Override
            protected void call() {
                log.info("business logic2");
            }
        };
        template.execute();
        template2.execute();
    }
}
