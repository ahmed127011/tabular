package tabular;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

public class expressions {
	private LinkedList<PrimeImplicant> prime;
	private int[] minterms;
	private LinkedList<Integer>[] cover;// countion primeImplicant covering every minterms
	private LinkedList<Integer>[] combinations; // combination of output
	private int bits;
	public expressions(LinkedList<PrimeImplicant> pr, int[] minterms,int bits) {
		this.prime = pr;
		this.bits = bits;
		this.minterms = minterms;
		this.cover = new LinkedList[minterms.length];
		for (int i = 0; i < minterms.length; i++) {
			this.cover[i] = new LinkedList<>();
		}

	}

	private void setcover() {
		for (int i = 0; i < prime.size(); i++)// go through prime implicant
		{
			for (int j = 0; j < prime.get(i).getcover().length; j++)// go through the cover using prime implicant									
			{
				for (int k = 0; k < minterms.length; k++)// compare the coverd
															// minterms with all
															// minterms
				{
					if (prime.get(i).getcover()[j] == minterms[k]) {
						cover[k].add(i);
						break;
					}
				}

			}
		}
	}

	private void combine() {
		int n = 1;
		// n stands for number of combination of output
		for (int i = 0; i < cover.length; i++) {
			n *= cover[i].size();
		}
		// init the combintaion
		this.combinations = new LinkedList[n];
		for (int i = 0; i < n; i++) {
			this.combinations[i] = new LinkedList<>();
		}
		int flag = 1;
		for (int i = 0; i < cover.length; i++) {
			int q = 0;
			flag *= cover[i].size();
			if (cover[i].size() > 1) {

				for (int j = 0; j < cover[i].size(); j++) {
					for (int k = 0; k < n / flag; k++, q++) {
						boolean exists=false;
						for(int haha=0;haha<combinations[q].size();haha++)	
						{
							if(combinations[q].get(haha)==cover[i].get(j))
							{
								exists=true;
								break;
							}
						}
						if(exists==false)
						combinations[q].add(cover[i].get(j));

					}
					if (j == cover[i].size() - 1 && q != n) {
						j = -1;
					}

				}

			} else {
				for (int j = 0; j < n; j++) {
					combinations[j].add(cover[i].get(0));
				}
			}

		}
		for (int i = 0; i < combinations.length; i++) {
			
			for (int j = 0; j < combinations[i].size(); j++) {
				System.out.print(combinations[i].get(j) + " ");
			}
			System.out.println();
		}

	}

	private void deleteElliShbhB3d() {
		for (int i = 0; i < combinations.length; i++) {
			combinations[i].sort(null);
			for (int j = i - 1; j >= 0; j--) {
				if ( combinations[j] == null)
					continue;
				if (combinations[i].size() != combinations[j].size())
					continue;
				boolean equal=true ;
				for (int k = 0; k < combinations[i].size(); k++) {
					if (combinations[i].get(k) != combinations[j].get(k)) {
						equal=false;
						break;
					}
				}
				if (equal==true)
					combinations[j] = null;
			}
		}
		System.out.println("-----------------------------------------");
for (int i = 0; i < combinations.length; i++) {
			if(combinations[i]==null)
				continue;
			for (int j = 0; j < combinations[i].size(); j++) {
				System.out.print(combinations[i].get(j) + " ");
			}
			System.out.println();
		}
	}

	private void deleteelKbera() {
		for (int i = 0; i < combinations.length; i++) {
			if (combinations[i] == null)
				continue;
			for (int j = 0; j <combinations.length; j++) {
				if (combinations[j] == null||i==j)
					continue;
				if (combinations[j].size() > combinations[i].size()) {
					int same = 0;
					for (int k = 0; k < combinations[i].size(); k++) {
						if (combinations[j].contains(combinations[i].get(k))) {
							same++;
						}
					}
					if (same == combinations[i].size())
						combinations[j] = null;

				}
			}
		}
		System.out.println("-----------------------------------------");
		for (int i = 0; i < combinations.length; i++) {
					if(combinations[i]==null)
						continue;
					for (int j = 0; j < combinations[i].size(); j++) {
						System.out.print(combinations[i].get(j)+1 + " ");
					}
					System.out.println();
				}
		
	}
	private void FindMinimumCost()
	{
		int min=prime.size();
		for(int i=0;i<combinations.length;i++)
		{
			if(combinations[i]==null)
				continue;
			if(combinations[i].size()<min)
				min=combinations[i].size();
		}
		for(int i=0;i<combinations.length;i++)
		{
			if(combinations[i]==null)
				continue;
			if(combinations[i].size()!=min)
				combinations[i]=null;
		}

		System.out.println("-----------------------------------------");
		for (int i = 0; i < combinations.length; i++) {
			if(combinations[i]==null)
						continue;
					for (int j = 0; j < combinations[i].size(); j++) {
						System.out.print(combinations[i].get(j)+1 + " ");
					}
					System.out.println();
				}
	}
	public void zabatha() {
		setcover();
		combine();
		deleteElliShbhB3d();
		deleteelKbera();
		FindMinimumCost();
		for(int i = 0; i < combinations.length; i++ ){
			if(combinations[i] != null){
				for(int j = 0; j < combinations[i].size(); j++){
					for(int z = j+1; z < combinations[i].size(); z++){
						if(combinations[i].get(z) == combinations[i].get(j)){
							combinations[i].remove(z);
							z--;
						}
					}
				}
			}
		}
	}
	String getOutput(){
		StringBuilder str = new StringBuilder();
		for(int i = 0; i < combinations.length; i++ ){
			if(combinations[i] != null){
				for(int j = 0; j < combinations[i].size(); j++){
					str.append(prime.get(combinations[i].get(j)).toString(bits));
					if(j < combinations[i].size() - 1){
						str.append(" + ");
					}
				}
				str.append('\n');
			}
		}
		return str.toString();
	}
	
	public void printOutput(){
		for(int i = 0; i < combinations.length; i++ ){
			if(combinations[i] != null){
				for(int j = 0; j < combinations[i].size(); j++){
					System.out.print(prime.get(combinations[i].get(j)).toString(bits));
					if(j < combinations[i].size() - 1){
						System.out.print(" + ");
					}
				}
				System.out.println();
			}
		}
	}

}
