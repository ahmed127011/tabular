package tabular;

public class test {
	public static void main(String[] argu){
		String s = "0 ,3 ,6 ,5 ,9";
		s.replaceAll(" ", "");
		System.out.println(s);
		String[] str = s.split(",");
		for(int i = 0; i < str.length; i++ ){
			System.out.println(str[i]);
		}
	}
}
