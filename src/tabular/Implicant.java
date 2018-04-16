package tabular;

import java.util.Arrays;

public class Implicant {
	private int[] grouped;
	private int min;
	private boolean ispaired = false;

	Implicant(int m, int[] g) {
		min = m;
		grouped = g;
		Arrays.sort(grouped);
	}

	Implicant(Implicant p) {
		min = p.getMin();
		grouped = p.getGrouped();
		Arrays.sort(grouped);
	}

	Implicant(int m){
		min = m;
	}

	int getMin() {
		return min;
	}

	int[] getGrouped() {
		return grouped.clone();
	}

	boolean isGrouped(Implicant p) {
		if (!isLog2(p.getMin() - min)) {
			return false;
		}
		for (int i = 0; i < grouped.length; i++) {
			if (grouped[i] != p.getGrouped()[i]) {
				return false;
			}
		}
		return true;
	}

	Implicant grouping(Implicant p) {
		int[] arr = new int[p.getGrouped().length + 1];
		for (int i = 0; i < arr.length - 1; i++) {
			arr[i] = p.getGrouped()[i];
		}
		arr[p.getGrouped().length] = p.getMin() - min;
		Implicant m = new Implicant(min,arr);
		return m;
	}

	private boolean isLog2(int diff) {
		return Integer.bitCount(diff) == 1;
	}
	boolean isEqual(Implicant p){
		if(p.getMin() != min){
			return false;
		}
		for(int i = 0; i < grouped.length; i++){
			if(p.getGrouped()[i] != grouped[i]){
				return false;
			}
		}
		return true;
	}

	void print() {
		System.out.print(min);
		System.out.print("[ ");
		for (int i = 0; i < grouped.length; i++) {
			System.out.print(grouped[i] + " ");
		}
		System.out.print("]");
	}
	void setPair(){
		ispaired = true; 
	}
	boolean getPair(){
		return ispaired;
	}
}
