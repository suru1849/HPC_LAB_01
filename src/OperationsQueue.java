import java.util.List;
import java.util.concurrent.locks.ReentrantLock;
import java.util.ArrayList;;
public class OperationsQueue {
    // * List
    private final List<Integer> operations = new ArrayList<>();

    // * Reentrant lock
    ReentrantLock rel;

    OperationsQueue(ReentrantLock rel)
    {
        this.rel = rel;
    }

    public void addSimulation(int totalSimulation) {

        // Add 50 random numbers in the operations list. The number will be range from -100 to 100. It cannot be zero.
        for (int i = 0; i < totalSimulation; i++) {
            int random = (int) (Math.random() * 200) - 100;
            if (random != 0) {
                operations.add(random);
                // *
                System.out.println(i + ". New operation added: " + random);
            }
            // add small delay to simulate the time taken for a new customer to arrive
            try {
                Thread.sleep((int) (Math.random() * 80));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        operations.add(-9999);
    }
    public void add(int amount) {
        operations.add(amount);
    }

    public int getNextItem(String name) {
        // add a small delay to simulate the time taken to get the next operation.
        // * if operations list is empty then it will be an infinite loop.
        // System.out.println(name + " - hapaning");
        
        boolean ans = rel.tryLock();
        int x = -9999;
        
        // * ans is True if resource is not held by any other thread
        if(ans){

           System.out.println("Start - "+name);
            try {
                if(!operations.isEmpty())
                {
                  x = operations.remove(0);
                }
              Thread.sleep((int) (Math.random() * 80));
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                System.out.println(name+" completed");
                rel.unlock();
            }

        }
        else
        {
            System.out.println("waiting for - " + name);
        }

        // while(operations.isEmpty()) {
        //     try {
        //         Thread.sleep(100);
        //     } catch (InterruptedException e) {
        //         e.printStackTrace();
        //     }
        // }
        // * remove the index = 0 and give operation[0]
        return x;
    }
}
