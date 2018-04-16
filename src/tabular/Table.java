package tabular;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;

public class Table {
	private Integer[] table1;
	private Integer[] minterms;
	private LinkedList<Implicant>[] table;
	private LinkedList<Implicant> imp = new LinkedList<Implicant>();
	private LinkedList<Implicant>[] implicant;
	private LinkedList<PrimeImplicant> prime = new LinkedList<PrimeImplicant>();
	private int bit = 0;
	private int litral;

	Table(int[] minterms, int[] dontcare) {
		this.minterms = new Integer[minterms.length];
		if (dontcare != null) {
			table1 = new Integer[minterms.length + dontcare.length];
		} else {
			table1 = new Integer[minterms.length];
		}
		bit = 0;
		for (int i = 0; i < minterms.length; i++) {
			table1[i] = minterms[i];
			this.minterms[i] = minterms[i];
		}
		int c = 0;
		for (int i = minterms.length; dontcare != null && c < dontcare.length; i++) {
			table1[i] = dontcare[c++];
		}
		Arrays.sort(table1);
		Arrays.sort(this.minterms);
		if (table1[table1.length - 1] != 0 && table1[table1.length - 1] != 1) {
			bit = (int) Math.ceil((Math.log10(table1[table1.length - 1]) / Math.log10(2)));
		} else {
			bit = 1;
		}
		litral = bit;
		table = new LinkedList[bit + 1];
		for (int i = 0; i < bit + 1; i++) {
			table[i] = new LinkedList<Implicant>();
		}
		for (int i = 0; i < table1.length; i++) {
			table[Integer.bitCount(table1[i])].add(new Implicant(table1[i]));
		}
		for (int i = 0; i < bit + 1; i++) {
			for (int j = 0; j < table[i].size(); j++) {
				System.out.println(table[i].get(j).getMin());
			}
			System.out.println("===================");
		}
	}

	/**
	 * knowing wether the diff is equal to 1,2,4,8,.....
	 * 
	 * @param diff
	 * @return
	 */
	private boolean isLog2(int diff) {
		return Integer.bitCount(diff) == 1;
	}

	LinkedList<PrimeImplicant> getPrime() {
		return prime;
	}

	/**
	 * the implicant which dosen't grouped withe any other implicant. is prime
	 * implicant and adding it to prime implicant list
	 */
	private void findPrimeImplicant() {
		for (int i = 0; i < implicant.length; i++) {
			for (int j = 0; j < implicant[i].size(); j++) {
				if (!implicant[i].get(j).getPair()) {
					prime.add(new PrimeImplicant(implicant[i].get(j)));
				}
			}
		}
	}

	public void joinFirst() {
		for (int i = 0; i < table.length; i++) {
			for (int j = 0; j < table[i].size(); j++) {
				for (int z = 0; i + 1 < table.length && z < table[i + 1].size(); z++) {
					if (table[i + 1].get(z).getMin() > table[i].get(j).getMin()
							&& isLog2(table[i + 1].get(z).getMin() - table[i].get(j).getMin())) {
						int[] arr = new int[1];
						arr[0] = table[i + 1].get(z).getMin() - table[i].get(j).getMin();
						Implicant p = new Implicant(table[i].get(j).getMin(), arr);
						imp.add(p);
						table[i].get(j).setPair();
						table[i + 1].get(z).setPair();
					}
				}
				if (!table[i].get(j).getPair()) {
					PrimeImplicant p = new PrimeImplicant(table[i].get(j).getMin());
					prime.add(p);
				}
			}
		}
		// create();
		implicant = new LinkedList[bit];
		for (int i = 0; i < bit; i++) {
			implicant[i] = new LinkedList<Implicant>();
		}
		for (int i = 0; i < imp.size(); i++) {
			imp.get(i).print();
			System.out.println();
			implicant[Integer.bitCount(imp.get(i).getMin())].add(imp.get(i));
		}
		bit--;
		System.out.println("-----------------------");
	}

	public void join() {
		imp = new LinkedList<Implicant>();
		for (int i = 0; i < implicant.length - 1; i++) {
			for (int j = 0; j < implicant[i].size(); j++) {
				for (int z = 0; z < implicant[i + 1].size(); z++) {
					if (implicant[i].get(j).isGrouped(implicant[i + 1].get(z))) {
						Implicant p;
						p = implicant[i].get(j).grouping(implicant[i + 1].get(z));
						imp.add(p);
						implicant[i].get(j).setPair();
						implicant[i + 1].get(z).setPair();
					}
				}
			}
		}
		findPrimeImplicant();
		create();
	}

	void create() {
		// System.out.println("+++++++++++++++++++++++++++++++++++");
		implicant = new LinkedList[bit];
		for (int i = 0; i < bit; i++) {
			implicant[i] = new LinkedList<Implicant>();
		}
		for (int i = 0; i < imp.size(); i++) {
			imp.get(i).print();
			System.out.println();
			implicant[Integer.bitCount(imp.get(i).getMin())].add(imp.get(i));
		}
		System.out.println("+++++++++++++++++++++++++++++++++++");
		// deleteoccuer();
		/**
		 * System.out.println("+++++++++++++++++++++++++++++++++++"); for (int i
		 * = 0; i < bit; i++) { for (int j = 0; j < implicant[i].size(); j++) {
		 * implicant[i].get(j).print(); System.out.println(); }
		 * System.out.println("==============="); }
		 */
	}

	/**
	 * to delete the repeated implicant after grouping
	 */
	void delete() {
		for (int i = 0; i < prime.size(); i++) {
			for (int j = i + 1; j < prime.size(); j++) {
				if (prime.get(i).isEqual(prime.get(j))) {
					prime.remove(j);
					j--;
				}
			}
		}
	}

	/**
	 * void deleteoccuer() { for (int i = 0; i < implicant.length; i++) { for
	 * (int j = 0; j < implicant[i].size(); j++) { for (int z = j + 1; z <
	 * implicant[i].size(); z++) { if
	 * (implicant[i].get(j).isEqual(implicant[i].get(z))) {
	 * implicant[i].remove(z); } } } } }
	 */
	void setcover() {
		for (int i = 0; i < prime.size(); i++) {
			prime.get(i).coverage(litral);
		}
	}

	void print() {
		for (int i = 0; i < imp.size(); i++) {
			imp.get(i).print();
			System.out.println();
		}
	}

	void printPrime() {
		System.out.println(prime.size());
		for (int i = 0; i < prime.size(); i++) {
			prime.get(i).print();
			System.out.println();
		}
	}

	void printImplicant() {
		System.out.println(implicant.length);
		for (int i = 0; i < implicant.length; i++) {
			for (int j = 0; j < implicant[i].size(); j++) {
				implicant[i].get(j).print();
			}
		}
	}

	public static void main(String[] argu) {
		int[] min = { 0, 1, 4, 5, 6, 7, 9, 11, 15 };
		int[] dont = { 10, 14 };
		Arrays.sort(min);
		Table t = new Table(min, dont);
		System.out.println();
		System.out.println("Grouping 1");
		t.joinFirst();
		for (int i = 1; i < 3; i++) {
			System.out.println("Grouping" + (i + 1));
			t.join();
		}
		t.print();
		System.out.println("____________________________");
		// t.printPrime();
		t.delete();
		t.printPrime();
		t.setcover();
		LinkedList<PrimeImplicant> p = new LinkedList<PrimeImplicant>();
		// p = t.getPrime();
		for (int i = 0; i < t.getPrime().size(); i++) {
			p.add(t.getPrime().get(i));
		}
		System.out.println("===================================");
		System.out.println("the prime Implicant");
		for (int i = 0; i < p.size(); i++) {
			System.out.println(p.get(i).toString(5) + " ");

		}
		System.out.println("===================================");
		expressions exp = new expressions(p, min, 4);
		MUI m = new MUI(p, min, 4);
		m.zabatha();

	}
}
