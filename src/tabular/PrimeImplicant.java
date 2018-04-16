package tabular;

import java.math.BigInteger;
import java.util.Arrays;

public class PrimeImplicant {
	private int[] prime;
	private int min;
	private int[] cover;
	private int counter = 0;

	PrimeImplicant(int[]cover){
		this.cover=cover;
	}

	PrimeImplicant(int mi, int[] m) {
		prime = m;
		min = mi;
		Arrays.sort(prime);
	}

	PrimeImplicant(int mi) {
		min = mi;
		prime = new int[0];
		//Arrays.sort(prime);
	}

	PrimeImplicant(Implicant e) {
		min = e.getMin();
		prime = e.getGrouped();
		Arrays.sort(prime);
	}

	int[] getprime() {
		return prime.clone();
	}

	int getMin() {
		return min;
	}

	void coverage(int varible) {
		int combination = (int) Math.pow(2, prime.length);
		cover = new int[combination];
		loadCoverage(0);
		Arrays.sort(cover);
		System.out.print(toString(varible)+ " => ");
		for(int i = 0; i < cover.length; i++){
			System.out.print(cover[i] + " ");
		}
		System.out.println();
	}
	private void loadCoverage(int c){
		if(c >= prime.length){
			cover[counter] += min;
			//System.out.println(cover[counter]);
			counter++;
			return;
		}
		int temp = cover[counter];
		cover[counter] += prime[c];
		loadCoverage(c+1);
		cover[counter] = temp;
		cover[counter] += 0;
		loadCoverage(c+1);
	}

	void print() {
		System.out.print(min);
		if (prime != null) {
			System.out.print("[");
			for (int i = 0; i < prime.length; i++) {
				System.out.print(prime[i] + " ");
			}
			System.out.print("]");
		}
	}
	boolean isEqual(PrimeImplicant p){
		if(p.getMin() != min){
			return false;
		}
		for(int i = 0; i < prime.length; i++){
			if(p.getprime()[i] != prime[i]){
				return false;
			}
		}
		return true;
	}
	public int[] getcover() {
		// TODO Auto-generated method stub
		return cover;
	}
	private int log2(int n){
		return (int)(Math.log10(n)/Math.log10(2));
	}
	public String toString(int variablesNum){
		StringBuilder str = new StringBuilder();
		if(variablesNum == prime.length){
			return "1";
		}
		if(prime.length == 0){
			str.append(String.format("%0"+variablesNum+"d", new BigInteger(Integer.toBinaryString(min))));
			String s = "";
			for(int i = 0; i < str.toString().length(); i++){
				if(str.toString().charAt(i) == '1'){
					s += String.format("%c", 'A'+i);
				}
				else{
					s += String.format("%c'", 'A'+i);
				}
			}
			return s;
		}
		char[] arr = new char[variablesNum];
		int c = prime.length - 1;
		String m = Integer.toBinaryString(min);
		String s = "";
		String x = Integer.toBinaryString((int)Math.pow(2, variablesNum)-1);
		if(x.length() != m.length()){
			for(int i = 0; i < (x.length() - m.length()); i++){
				s += "0";
			}
		}
		s += m;
		for(int i = 0; i < prime.length ; i++){
			int del = log2(prime[i]);
			arr[arr.length - 1 - del] = '#';
		}
		for(int i = 0; i < arr.length ; i++){
			if(arr[i] != '#'){
				arr[i] = (char)('A' + i);
			}
		}
		for(int i = 0; i < arr.length ; i++){
			if(arr[i] != '#'){
				if(s.charAt(i) == '0'){
					str.append(arr[i]);
					str.append('\'');
				}
				else{
					str.append(arr[i]);
				}
			}
		}
		return str.toString();
	}
}
