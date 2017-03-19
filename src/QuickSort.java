
public class QuickSort {

	public static void sort(int[] array, int low, int high) {
		if(array == null || array.length == 0) {
			return;
		}
		
		if(low >= high) {
			return;
		}
		
		int temp;
		
		int middle = low + (high - low) / 2;
		int pivot = array[middle];
		
		int i = low;
		int j = high;
		
		while(i <= j) {
			while(array[i] < pivot) {
				i++;
			}
			
			while(array[j] > pivot) {
				j--;
			}
			
			if(i <= j) {
				temp = array[i];
				array[i] = array[j];
				array[j] = temp;
				
				i++;
				j--;
			}
		}
		
		if(low < j) {
			sort(array, low, j);
		}
		
		if(high > i) {
			sort(array, i, high);
		}
	}

}
