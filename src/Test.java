import java.util.Random;

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
	
	static int[] populateArray() {
		final int SIZE = 10000;
		int[] array = new int[SIZE];
		
		for(int i = 0; i < SIZE; i++) {
			array[i] = getRandomInt();
		}
		
		return array;
	}
	
	static int getRandomInt() {		
		final int MIN = 1;
		final int MAX = 99999;
		Random random = new Random();
		return random.nextInt((MAX - MIN) + 1) + MIN;
	}

}
