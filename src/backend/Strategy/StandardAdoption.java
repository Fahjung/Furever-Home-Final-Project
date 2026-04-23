package backend.Strategy;

public class StandardAdoption extends AdoptionStrategy {
    @Override
    protected void processLogic() {
        System.out.println("Processing standard adoption paperwork...");
    }  
}