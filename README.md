# Thread Factoring 

## Description 
Implement the `getInstance` method of [`com.epam.rd.autotasks.ThreadUnion`](src/main/java/com/epam/rd/autotasks/ThreadUnion.java).

It should return a `ThreadUnion` instance.

`ThreadUnion` is a named `ThreadFactory` that creates threads, monitors their execution results, and allows to call the operation of their group shutdown.

Methods description:

| Method | Description |
| --- | --- |
| `Thread newThread (Runnable runnable)` | Creates and registers a new thread. The name of the thread should be "&lt;_thread-union-name_&gt;-worker-_n_", where _n_ is a number of this thread. A `ThreadUnion` must monitor the execution of a created thread - refer to the `results()` method. | 
| `int totalSize()` | Returns the total number of threads created within a `ThreadUnion`. |
| `int activeSize()` | Returns the total number of currently active threads created within a `ThreadUnion`. |
| `void shutdown()` | Interrupts all created threads and prevents creating more threads via the `newThread` method. |
| `boolean isShutdown()` | Returns true if shutdown has been called. |
| `void awaitTermination()` | Waits until all the created threads are finished. |
| `boolean isFinished()` | Checks if a `ThreadUnion` was shutdown and all the created threads have finished.|
| `List<FinishedThreadResult> results()` | Returns a list of results of finished threads. No results must be returned for the threads that are not finished yet. A result must contain a thread name, a timestamp when it finished execution and a `Throwable` if it was thrown. |

 Note that your `ThreadUnion` implementation should be thread-safe and support concurrent thread generating.  
