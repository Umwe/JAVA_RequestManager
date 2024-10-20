# JAVA_RequestManager
The RequestManager.java file is a utility class designed to manage and handle multiple concurrent requests safely using Java’s ExecutorService. The class ensures that incoming tasks (requests) are processed in separate threads without causing the system to freeze or crash. It also handles graceful shutdown and returns the results of each request.


# Request Manager

## Overview
The `RequestManager` class is a multithreaded utility designed to handle concurrent request processing in Java. It uses an `ExecutorService` to manage a fixed pool of threads, ensuring that multiple tasks can be processed simultaneously without overloading the system. This class is ideal for scenarios where numerous requests need to be handled concurrently, such as server-side processing or background task management.

## Features
- Utilizes a fixed thread pool to efficiently manage multiple concurrent requests.
- Processes tasks asynchronously, preventing system freezing and crashes.
- Implements a graceful shutdown to ensure all running tasks are completed before termination.
- Provides results for each request in the form of a `Future` object.

## Usage

1. Clone the repository or copy the `RequestManager.java` file into your project.
   
   ```bash
   git clone https://github.com/umwe/request-manager.git

2.Create an instance of RequestManager with the desired number of threads:

    RequestManager manager = new RequestManager(5); // Example with 5 threads

3.Define a list of Callable<String> tasks representing requests, and submit them for execution:

      List<Callable<String>> requests = new ArrayList<>();
      for (int i = 1; i <= 10; i++) {
          final int requestId = i;
          requests.add(() -> {
              // Simulate request processing
              Thread.sleep(1000);  // Simulate some work
              return "Request " + requestId + " processed.";
          });
      }

4.Handle the requests using handleRequests:
      manager.handleRequests(requests);

5.Once all tasks are processed, ensure the executor service is shut down properly:


    
