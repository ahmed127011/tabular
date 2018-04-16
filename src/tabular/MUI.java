package tabular;

import java.util.LinkedList;

public class MUI {
	private LinkedList<PrimeImplicant> prime;
	private int[] minterms;
	public LinkedList<Integer> remainedMinterms;
	private LinkedList<Integer>[] cover;
	private LinkedList<Integer>[] combinations;
	private LinkedList<Integer> essprime;
	private int bit = 0;

	public MUI(LinkedList<PrimeImplicant> pr, int[] minterms, int bit) {
		this.bit = bit;
		this.prime = pr;
		this.minterms = minterms;
		this.essprime=new LinkedList<>();
		this.remainedMinterms=new LinkedList<Integer>();
		for(int i=0;i<minterms.length;i++)
		{
			this.remainedMinterms.add(minterms[i]);
		}
		this.cover = new LinkedList[minterms.length];
		for (int i = 0; i < minterms.length; i++) {
			this.cover[i] = new LinkedList<>();
			this.cover[i].add(minterms[i]);
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
	private void findEssential()
	{
		for (int i=0;i<cover.length;i++)
		{
			if(cover[i].size()==2)
			{
				
				int p=cover[i].get(1);
				essprime.add(p);
				
				int [] mtrms=prime.get(p).getcover();
				for(int j=0;j<mtrms.length;j++)
				{
					remainedMinterms.removeFirstOccurrence(mtrms[j]);
				}
			}
		}
		System.out.println("++++++++++++++++++++++");
		if(remainedMinterms.size() != 0){
			for(int i=0;i<remainedMinterms.size();i++)
				System.out.print(remainedMinterms.get(i)+" ");
		}
		else{
			System.out.print("every prime implicant is essential");
		}
		System.out.println("\n++++++++++++++++++++++");
		for(int i=0;i<essprime.size();i++)
			System.out.println(prime.get(essprime.get(i)).toString(bit));
		System.out.println("++++++++++++++++++++++");
	}

	private void combine() {
		int n = 1;
		for (int i = 0; i < cover.length; i++) {
			if(remainedMinterms.contains(cover[i].get(0))==false){
				continue;}
			System.out.print(cover[i].get(0)+ " is covered by => ");
			for(int j = 1; j <cover[i].size(); j++){
				System.out.print(prime.get(cover[i].get(j)).toString(bit)+"   ");
			}
			System.out.println();
			n *= cover[i].size()-1;
		}
		
		this.combinations = new LinkedList[n];
		for (int i = 0; i < n; i++) {
			this.combinations[i] = new LinkedList<>();
		}
		
		int flag = 1;
		for (int i = 0; i < cover.length; i++) {
			if(remainedMinterms.contains(cover[i].get(0))==false){
				continue;}
			int q = 0;
			flag *= cover[i].size()-1;
			if (cover[i].size() > 2) {

				for (int j = 1; j < cover[i].size(); j++) {
					for (int k = 0; k < n / flag; k++, q++) {
						
						if(combinations[q].contains(cover[i].get(j))==false)
						{
							
						combinations[q].add(cover[i].get(j));
					}}
					if (j == cover[i].size()-1  && q != n) {
						j = 0;
					}

				}

			} else {
				for (int j = 0; j < n; j++) {
					combinations[j].add(cover[i].get(1));
				}
			}

		}
		System.out.println("===========First combinations==============");
		for (int i = 0; i < combinations.length; i++) {
			
			for (int j = 0; j < combinations[i].size(); j++) {
				int p = combinations[i].get(j);
				System.out.print(prime.get(p).toString(bit));
				if(j < combinations[i].size() - 1){
					System.out.print(" + ");
				}
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
		System.out.println("-----------------DElete Repeated-------------------");
for (int i = 0; i < combinations.length; i++) {
			if(combinations[i]==null)
				continue;
			for (int j = 0; j < combinations[i].size(); j++) {
				System.out.print(prime.get(combinations[i].get(j)).toString(bit) + " ");
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
		System.out.println("==========Delete El kbera (X+XY+X)==============");
		for (int i = 0; i < combinations.length; i++) {
					if(combinations[i]==null)
						continue;
					for (int j = 0; j < combinations[i].size(); j++) {
						System.out.print(prime.get(combinations[i].get(j)).toString(bit) + " ");
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

		System.out.println("==============Find the Shortest expressions=========");
		for (int i = 0; i < combinations.length; i++) {
			if(combinations[i]==null)
						continue;
					for (int j = 0; j < combinations[i].size(); j++) {
						System.out.print(prime.get(combinations[i].get(j)).toString(bit) + " ");
					}
					System.out.println();
				}
	}
	private void addTheEssential()
	{
		for (int i=0;i<combinations.length;i++)
		{
			if(combinations[i]==null)
				continue;
			for (int j=0;j<essprime.size();j++)
				combinations[i].add(essprime.get(j));
			combinations[i].sort(null);
		}
		System.out.println("===================final combinations isa=====================");
	}

	public void zabatha() {
		setcover();
		findEssential();
		combine();
		deleteElliShbhB3d();
		deleteelKbera();
		FindMinimumCost();
		addTheEssential();
		for(int i = 0; i < combinations.length; i++){
			if(combinations[i] != null){
				for(int j = 0; j < combinations[i].size();j++){
					for(int z = j+1; z < combinations[i].size(); z++){
						if(combinations[i].get(z) == combinations[i].get(j)){
							combinations[i].remove(z);
							z--;
						}
					}
				}
			}
		}
		for(int i = 0; i < combinations.length; i++){
			if(combinations[i] != null){
				for(int j = 0; j < combinations[i].size();j++){
					int p = combinations[i].get(j);
					System.out.print(prime.get(p).toString(bit));
					if(j < combinations[i].size()-1){
						System.out.print(" + ");
					}
				}
				System.out.println();
			}
		}
	}
	String getOutput(){
		StringBuilder str = new StringBuilder();
		for(int i = 0; i < combinations.length; i++ ){
			if(combinations[i] != null){
				for(int j = 0; j < combinations[i].size(); j++){
					str.append(prime.get(combinations[i].get(j)).toString(bit));
					if(j < combinations[i].size() - 1){
						str.append(" + ");
					}
				}
				str.append('\n');
			}
		}
		return str.toString();
	}

}
