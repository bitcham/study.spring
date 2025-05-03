package hello.advanced.trace.strategy.code.strategy;

public class StrategyLogic2 implements Strategy{

    @Override
    public void call() {
        System.out.println("business logic2 execution");
    }
}
