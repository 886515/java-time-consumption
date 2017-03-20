class BubbleSort {
    
	static void sort(int[] array) {
		Timer ins = Timer.getInstance();
		ins.startTiming("BubbleSort.sort");
		
		final int SIZE = array.length;
    	int temp;
    	
    	for(int i = 0; i < SIZE; i++) {
    		for(int j = 0; j < SIZE - 1; j++) {
    			if(array[j] > array[j + 1]) {
    				temp = array[j];
    				array[j] = array[j + 1];
    				array[j + 1] = temp;
    			}
    		}
    	}
    	
    	ins.stopTiming("BubbleSort.sort");
    }
	
}
