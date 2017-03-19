# java-time-consumption

> :hourglass: A Java class used to generate a log file with information on the runtime of blocks of code.

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
		
		ins.startTiming("bubbleSort(array)");
		BubbleSort.sort(array);
		ins.stopTiming("bubbleSort(array)");
		
		ins.startTiming("quickSort(array)");
		QuickSort.sort(array, 0, array.length - 1);
		ins.stopTiming("quickSort(array)");
		
		ins.stopTiming("main");
		
		ins.comment("One last comment before the dump!");
		
		ins.dump("my.log");
	}
}
```

This generates a sample log file like:

```
STARTTIMING: main
COMMENT: I like to comment in my logs
| STARTTIMING: populateArray()
| STOPTIMING: populateArray() 7ms
| STARTTIMING: bubbleSort(array)
| STOPTIMING: bubbleSort(array) 167ms
| STARTTIMING: quickSort(array)
| STOPTIMING: quickSort(array) 2ms
STOPTIMING: main 177ms
TOTAL TIME: 177ms
COMMENT: One last comment before the dump!
```

## API

### `void startTiming(String comment)`
Start timing a method, or block of code

### `void stopTiming(String comment)`
Stop timing a method, or block of code

### void comment(String comment)
Place an additional comment in the output of the instrumentation log file


### void dump(String filename)
Writes formatted/indented pairs of startTiming/stopTiming statements to a human readable log file. If you provide `null` as filename then a logfile will be created with the timestamp `instrumentationddyyMMhhmmss.log`. You should call this method ONCE at the end of a program that uses the instrumentation class.

### void activate(Boolean onOff)
Activates/deactivates instrumentation. You should call this ONCE at the beginning of any program that uses the instrumentation class. Pass in false and the calls to all instrumentation methods will return immediately with no effect. The class behaves as if `activate(false)` was called by default.
