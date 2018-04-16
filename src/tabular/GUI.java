package tabular;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.LinkedList;

import javax.swing.JOptionPane;

import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class GUI extends Application {
	public int minterms[];
	public int[] dontcare;
	
	int[] parseArray(String str){
		LinkedList<Integer> l = new LinkedList<Integer>();
		for(int i = 0; i < str.length(); i++) {
			if(str.charAt(i) >= '0' && str.charAt(i) <= '9') {
				int j = i;
				while(j < str.length() && str.charAt(j)>= '0' && str.charAt(j) <= '9'){
					j++;
				}
				l.add(Integer.parseInt(str.substring(i, j)));
				i = j;
			}
		}
		int[] arr = new int[l.size()];
		for(int i = 0; i < l.size(); i++){
			arr[i] = l.get(i);
		}
		return arr;
	}
	@Override
	public void start(Stage arg0) throws Exception {
		TextField screen1 = new TextField();
		screen1.setEditable(true);
		screen1.setPrefWidth(200);
		
		TextField screen2 = new TextField();
		screen2.setEditable(true);
		screen2.setPrefWidth(200);

		TextField var = new TextField();
		var.setEditable(true);
		var.setPrefWidth(100);
		
		TextArea screen3 = new TextArea();
		screen3.setEditable(false);
		screen3.setPrefWidth(300);
		screen3.setPrefHeight(200);
		screen3.textFormatterProperty();
		
		FileChooser fileChooser = new FileChooser();
		Button f = new Button("solve in file");
		
		Button from = new Button("read from file");
		
		from.setOnAction(e -> {
			File file = fileChooser.showOpenDialog(arg0);
			PrintStream out;
			BufferedReader br;
			int bit = 1;
			try {
				br = new BufferedReader(new FileReader(file));
				try {
					String m = br.readLine();
					minterms = parseArray(m);
					String d = br.readLine();
					dontcare = parseArray(d);
					String v = br.readLine();
					int variable = Integer.parseInt(v);
					bit = variable;
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			int[] arr = new int[minterms.length+dontcare.length];
			for(int i = 0; i < minterms.length;i++){
				arr[i] = minterms[i];
			}
			for(int i = minterms.length; i < dontcare.length;i++){
				arr[i] = dontcare[i];
			}
			Arrays.sort(arr);
			Arrays.sort(minterms);
			Arrays.sort(dontcare);
			Table t = new Table(minterms,dontcare);
			t.joinFirst();
			for(int i = 0; i < bit + 1; i++){
				System.out.println("the join" + i);
				t.join();
			}
			t.delete();
			t.setcover();
			LinkedList<PrimeImplicant> p = new LinkedList<PrimeImplicant>();
			p = t.getPrime();
			System.out.println(bit);
			for(int i = 0; i < p.size(); i++){
				System.out.print(p.get(i).toString(bit));
				if(i != p.size() - 1){
					System.out.print(" + ");
				}
			}
			System.out.println();
			MUI exp = new MUI(p,minterms,bit);
			exp.zabatha();
			screen3.appendText(exp.getOutput());
		});
		
		f.setOnAction(e ->{
			File file = fileChooser.showSaveDialog(arg0);
			PrintStream out;
			try {
				out = new PrintStream(new FileOutputStream(file));
				System.setOut(out);
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			String m = screen1.getText();
			minterms = parseArray(m);
			String d = screen2.getText();
			dontcare = parseArray(d);
			String v = var.getText();
			int variable = Integer.parseInt(v);
			int[] arr = new int[minterms.length+dontcare.length];
			for(int i = 0; i < minterms.length;i++){
				arr[i] = minterms[i];
			}
			for(int i = minterms.length; i < dontcare.length;i++){
				arr[i] = dontcare[i];
			}
			Arrays.sort(arr);
			Arrays.sort(minterms);
			Arrays.sort(dontcare);
			int bit = variable;
			Table t = new Table(minterms,dontcare);
			t.joinFirst();
			for(int i = 0; i < bit + 1; i++){
				System.out.println("the join" + i);
				t.join();
			}
			t.delete();
			t.setcover();
			LinkedList<PrimeImplicant> p = new LinkedList<PrimeImplicant>();
			p = t.getPrime();
			System.out.println(bit);
			for(int i = 0; i < p.size(); i++){
				System.out.print(p.get(i).toString(bit));
				if(i != p.size() - 1){
					System.out.print(" + ");
				}
			}
			System.out.println();
			MUI exp = new MUI(p,minterms,bit);
			exp.zabatha();
			screen3.appendText(exp.getOutput());
		});
		
		Button cal = new Button("calculate expression");
		cal.setOnAction(e -> {
			String m = screen1.getText();
			minterms = parseArray(m);
			String d = screen2.getText();
			dontcare = parseArray(d);
			int[] arr = new int[minterms.length+dontcare.length];
			for(int i = 0; i < minterms.length;i++){
				arr[i] = minterms[i];
			}
			for(int i = minterms.length; i < dontcare.length;i++){
				arr[i] = dontcare[i];
			}
			Arrays.sort(arr);
			Arrays.sort(minterms);
			Arrays.sort(dontcare);
			String v = var.getText();
			int variable = Integer.parseInt(v);
			int bit = variable;
			Table t = new Table(minterms,dontcare);
			t.joinFirst();
			for(int i = 0; i < bit + 1; i++){
				System.out.println("the join" + i);
				t.join();
			}
			t.delete();
			t.setcover();
			LinkedList<PrimeImplicant> p = new LinkedList<PrimeImplicant>();
			p = t.getPrime();
			for(int i = 0; i < p.size(); i++){
				System.out.print(p.get(i).toString(bit));
				if(i != p.size() - 1){
					System.out.print(" + ");
				}
			}
			System.out.println();
			MUI exp = new MUI(p,minterms,bit);
			exp.zabatha();
			screen3.setText(exp.getOutput());
		});
		Label m = new Label("The minterms");
		Label d = new Label("The dontcare");
		Label r = new Label("The result");
		Label v = new Label("the num of variables");
		
		GridPane grid = new GridPane();
		GridPane.setConstraints(m, 0, 0);
		GridPane.setConstraints(screen1, 1, 0);
		GridPane.setColumnSpan(screen1, 5);
		
		GridPane.setConstraints(d, 0, 1);
		GridPane.setConstraints(screen2, 1, 1);
		GridPane.setColumnSpan(screen2, 5);
		
		GridPane.setConstraints(v, 0, 2);
		GridPane.setConstraints(var, 1, 2);
		GridPane.setColumnSpan(var, 5);
		
		GridPane.setConstraints(r, 0, 3);
		GridPane.setConstraints(screen3, 1, 3);
		GridPane.setColumnSpan(screen3, 5);
		
		GridPane.setConstraints(cal, 0, 4);
		GridPane.setColumnSpan(cal, 2);
		
		GridPane.setConstraints(f, 0, 5);
		GridPane.setColumnSpan(f, 2);
		
		GridPane.setConstraints(from, 0, 6);
		GridPane.setColumnSpan(from, 2);
		
		grid.getChildren().addAll(screen1,screen2,screen3,cal,f,m,d,r,var,v,from);
		grid.setPadding(new Insets(10,10,10,10));
		grid.setVgap(10);
		grid.setHgap(10);
		
		arg0.setTitle("Tabular");
		arg0.setScene(new Scene(grid,400,300));
		arg0.show();
	}
	public static void main (String[] argu){
		launch(argu);
	}

}
