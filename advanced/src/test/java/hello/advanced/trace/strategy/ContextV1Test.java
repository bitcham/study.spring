package hello.advanced.trace.strategy;

import hello.advanced.trace.strategy.code.strategy.ContextV1;
import hello.advanced.trace.strategy.code.strategy.StrategyLogic1;
import hello.advanced.trace.strategy.code.strategy.StrategyLogic2;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class ContextV1Test {

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
        StrategyLogic1 strategyLogic1 = new StrategyLogic1();
        StrategyLogic2 strategyLogic2 = new StrategyLogic2();
        ContextV1 context1 = new ContextV1(strategyLogic1);
        ContextV1 context2 = new ContextV1(strategyLogic2);
        context1.execute();
        context2.execute();
    }

    @Test
    void templateMethodV2() {
        ContextV1 context1 = new ContextV1(() -> log.info("business logic1"));
        ContextV1 context2 = new ContextV1(() -> log.info("business logic2"));
        context1.execute();
        context2.execute();
    }




}
