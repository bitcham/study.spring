package hello.advanced.trace.strategy.code.strategy;

public class StrategyLogic1 implements Strategy{

    @Override
    public void call() {
        System.out.println("business logic1 execution");
    }
}
