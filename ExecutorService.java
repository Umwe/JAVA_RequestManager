import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.Callable;
import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class RequestManager {

    // Define a fixed thread pool size
    private ExecutorService executor;

    // Constructor to initialize the thread pool
    public RequestManager(int numOfThreads) {
        // Initialize the thread pool with a fixed number of threads
        executor = Executors.newFixedThreadPool(numOfThreads);
    }

    // Method to submit tasks to be handled by threads
    public void handleRequests(List<Callable<String>> requests) {
        try {
            // Submit all requests for processing
            List<Future<String>> futures = executor.invokeAll(requests);

            // Retrieve and process the results from the completed tasks
            for (Future<String> future : futures) {
                try {
                    // Print the result of each completed request
                    System.out.println("Result: " + future.get());
                } catch (Exception e) {
                    System.err.println("Error processing request: " + e.getMessage());
                }
            }

        } catch (InterruptedException e) {
            System.err.println("Execution was interrupted: " + e.getMessage());
        }
    }

    // Method to shutdown the ExecutorService safely
    public void shutdown() {
        try {
            // Initiate a graceful shutdown
            executor.shutdown();
            // Wait for existing tasks to terminate within the specified timeout
            if (!executor.awaitTermination(60, TimeUnit.SECONDS)) {
                executor.shutdownNow(); // Force shutdown if tasks did not complete
                if (!executor.awaitTermination(60, TimeUnit.SECONDS)) {
                    System.err.println("Executor did not terminate.");
                }
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }

    // Main method to test the RequestManager
    public static void main(String[] args) {
        RequestManager manager = new RequestManager(5);  // Create manager with 5 threads

        // Create a list of requests (tasks)
        List<Callable<String>> requests = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            final int requestId = i;
            requests.add(() -> {
                // Simulate request processing
                Thread.sleep(1000);  // Simulate some work
                return "Request " + requestId + " processed.";
            });
        }

        // Handle requests concurrently
        manager.handleRequests(requests);

        // Shutdown the manager
        manager.shutdown();
    }
}
