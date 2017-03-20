# java-time-consumption

> A Java class used to generate a log file with information on the runtime of blocks of code.

## What it is

The `Timer` class is used to monitor the time that it takes for methods and blocks of code take to run. This class is an example of computer softwar instrumentation.

### Instrumentation

Computer software instrumentation is used to help analyze performance, diagnose errors, and log information about software. Instrumentation is used to monitor specific components of code, which, in our case, is methods and blocks of code. Instrumentation is usually managed by an overarching tool. The `Timer` class has been created using the [singleton design principle](https://www.tutorialspoint.com/design_pattern/singleton_pattern.htm), meaning that there can only ever be one instance of the class. This makes sure that the class behaves as expected and will not need to be re-instantiated.

## Purpose

The purpose of this class is to help determine which components of a software system require the most time to execute. This class is part of an experiment to determine the bottlenecks in computer software and how best to analyze software performance. Using this class, we can effectively determine which section of code require the most time to execute. With this information, we can pinpoint exactly where we should work to improve the speed at which the software executes. This can lead to software which is less prone to timeouts and exceptions when dealing with larger data sets.

## How it works

To be able to run multiple timers at once, I had to come up with a way of keeping track of the start and end points of different blocks of code. This ended up being a relatively simple solution, which involved the use of two `Stack` objects. These `Stack`s follow the last-in-first-out \(LIFO\) principle charcteristic of a `Stack`. The reason for using `Stack`s is so that we can have the first timer started, be the last timer we end, just as it will be in code execution.

### Variables

#### `String buffer`

This variable holds the lines that will be printed to the `.log` file. This variable is initially empty, but is filled as timers are started and stopped.

#### `Stack<String> runningTimers`

This variable holds the `comment` passed to the `startTiming` method. the `comment` is added to the `Stack` when a timer is started and removed when a timer is finished. This variable is used to determine whether we can end a particular timer, as well as how much we need to indent a particular `comment`.

#### `Stack<Long> startTimes`

This variable holds the various start times associated with the timers in `runningTimers`. These times are gathered using the `System.currentTimeMillis()` method, which gets the current time in milliseconds. When a timer is stopped, we get the difference between the current time in milliseconds when stopped and the initial start time to determine how many milliseconds the method or block of code took to execute.

### API

#### `void startTiming(String comment)`

Start timing a method, or block of code

#### `void stopTiming(String comment)`

Stop timing a method, or block of code

#### `void comment(String comment)`

Place an additional comment in the output of the instrumentation log file

#### `void dump(String filename)`

Writes formatted/indented pairs of startTiming/stopTiming statements to a human readable log file. If you provide `null` as filename then a logfile will be created with the timestamp `instrumentationddyyMMhhmmss.log`. You should call this method ONCE at the end of a program that uses the instrumentation class.

#### `void activate(Boolean onOff)`

Activates/deactivates instrumentation. You should call this ONCE at the beginning of any program that uses the instrumentation class. Pass in false and the calls to all instrumentation methods will return immediately with no effect. The class behaves as if `activate(false)` was called by default.

## Test Case

This is the test case used to ensure that the `Timer` class works effectively. As you may notice, there are no timers around the `BubbleSort.sort` method and the `QuickSort.sort` method. This is because these methods have timers integrated into them.

The results of this test are reproducibel. The `populateArray` always creates an array of size 10,000 filled with random number between 1 and 99,999, inclusive. The `BubbleSort.sort` algorithm is called on the generated array, followed by the `QuickSort.sort`. Testing the time to execute the two sorting algorithms on the same array reduces the number of moving parts.

```java
public class Test {

    public static void main(String[] args) {
        Timer ins = Timer.getInstance();
        ins.activate(true);

        ins.startTiming("main");

        ins.comment("I like to comment in my logs");

        ins.startTiming("populateArray()");
        int[] array = populateArray();
        ins.stopTiming("populateArray()");

        BubbleSort.sort(array);

        QuickSort.sort(array, 0, array.length - 1);

        ins.stopTiming("main");
        ins.comment("One last comment before the dump!");
        ins.dump("my.log");
    }
}
```

### Analysis of Test Case log

Whean creating a log for our test case there is some overhead. There is the space needed to store the buffer that will eventually be written to the log file, the space for the `runningTimers` and `startTimes` variables, and the time needed to access those variables, as well as write `buffer` to file.

When examining the performance of the sorting algorithms we can make predictions based off of their documented time complecity. Bubble sort has O\(n^2\), while quicksort has O\(n\*log\(n\)\). we can assume quicksort will take less time to execute.

The log for our test case shows that the class works as expected. Starting from the top, we have the start of the main timer for the `Test` class. There is then a comment. As expected, the comment is indented once as there is one timer running. Both the `populateArray` and `BubbleSort.sort` methods are started and stopped, while the `QuickSort.sort` method is started once for the initial call, then is started many times, recursively \(this is what the `...` is meant to indicate\). As the timing is built into the sorting methods, and `QuickSort.sort` is recursive, we see a very large time for its execution, when in reality, `BubbleSort.sort` is the more time consuming of the two algorithms. This is due to the repeated calls to start and stop timers for the `QuickSort.sort` algorithm. The log file finishes by stopping the main timer, then executing then printing the total time it took to run all of the timers \(i.e. the time it toook to run `main`\), finally followed by the final comment.

```
STARTTIMING: main
| COMMENT: I like to comment in my logs
| STARTTIMING: populateArray()
| STOPTIMING: populateArray() 7ms
| STARTTIMING: BubbleSort.sort
| STOPTIMING: BubbleSort.sort 165ms
| STARTTIMING: QuickSort.sort
...
| STOPTIMING: QuickSort.sort 5508ms
STOPTIMING: main 5683ms
TOTAL TIME: 5683ms
COMMENT: One last comment before the dump!
```



