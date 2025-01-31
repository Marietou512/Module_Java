package test;

import lib.Json;
class Student{
	int id; String nom; boolean passe;


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getNom() {
		return nom;
	}


	public void setNom(String nom) {
		this.nom = nom;
	}


	public boolean isPasse() {
		return passe;
	}


	public void setPasse(boolean passe) {
		this.passe = passe;
	}
	
	
}

public class RunMeBaby {

	private static class TestCase {
		public String  getString() { return "xoxo"; }
		public Integer getInteger() { return 42; }
		public Integer getNull() { return null; }
		
	}

	public static void main(String[] args)  {
		//System.out.println(new Json().write(new TestCase()));
		// {"string":"xoxo","integer":42,"null":null}
		int[] test= new int[4];
		test[0]=4; test[1]=7; test[2]=9; test[3]=4;
	
		int x=44;
		Object[] tab1= {"Mira","Cissé"};
		Object[] tab2= {true};
		Object[] tab= {2, tab1, 5.3, tab2};
	
		System.out.println(new Json().write(x));
		System.out.println(new Json().write("xoxo"));
		System.out.println(new Json().write(tab));

	}

}
