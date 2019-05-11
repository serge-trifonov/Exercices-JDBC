package bases.postgres;

import java.sql.*;
import java.util.*;

public class Requets {
	private static final String DB_CONNECTION = "jdbc:postgresql://localhost:5432/mybd";        
	private static final String DB_USER = "serge";
	private static final String DB_PASSWORD = "toto";
	private static Scanner READER = new Scanner(System.in);
	
	public static void main(String... args) {
		int choix;
		while((choix=afficherMenu())!=3) {
			   switch(choix) {
			   case 1 :String query = saisieRequete("select");
			   			if(query!=null) {
					       executeSelect(query);
			   			}
			   			break;
			   case 2 :query = saisieRequete("update", "delete", "insert");
		       			if(query!=null) {
		       				executeUpdate(query);
		       			}
		       			break;
		       default : System.out.println("Votre choix n'est pas valide");
			   }
		}
	}
	
	public static int afficherMenu() {   
		System.out.println("============================================================");
		System.out.println("|                           MENU                           |");
		System.out.println("============================================================");
		System.out.println("1. Exécuter une requête de type SELECT.");
		System.out.println("2. Exécuter une requête de type INSERT, DELETE ou UPDATE.");
		System.out.println("3. Quitter le programme.");
		System.out.print(" Votre choix (pensez à validez) >");
		return READER.nextInt();
   }
	
   public static String saisieRequete(String... str) {
	   	System.out.print("Veillez saisir votre requete : ");
	   	READER.nextLine();
	   	String query = READER.nextLine().trim().toLowerCase();
	   	boolean valide=false;
	   	for(String s: str) {
	   			if(query.startsWith(s)) valide=true;
	   	}
	   return valide?query:null;
   }
   
   public static void executeSelect(String query) {
	   try(Connection con = DriverManager.getConnection(DB_CONNECTION, DB_USER,DB_PASSWORD);
		   PreparedStatement stm = con.prepareStatement(query);
		   ResultSet rs = stm.executeQuery();){
			   ResultSetMetaData rsmd = rs.getMetaData();
	           int columnsNumber = rsmd.getColumnCount();
	           int[] maxString = new int[columnsNumber];
	           String[] tete = new String[columnsNumber];
	           for(int i = 1; i<=columnsNumber; i++) { 
	        	   tete[i-1]=rsmd.getColumnName(i);
	        	   maxString[i-1]=tete[i-1].length()>rsmd.getColumnDisplaySize(i)?tete[i-1].length():rsmd.getColumnDisplaySize(i);
	           }
	           int longueur=columnsNumber+1;
	    	   for(int x:maxString) {
	    		   longueur+=x;
	    	   }
	    	   afficheChar(longueur,'-');
	    	   System.out.println();
	    	   for(int i =0; i<columnsNumber; i++) {
	    		   System.out.print("|"+tete[i]);
	    		   afficheChar(maxString[i]-tete[i].length(),' ');
	    	   }
	    	   System.out.println("|");
	    	   afficheChar(longueur,'-');
	    	   System.out.println();
	           while(rs.next()) {
				 for(int i = 1; i<=columnsNumber; i++) {   
					 System.out.print("|"+rs.getString(i));
					 int nbChar=rs.getString(i)==null?4:rs.getString(i).length();
		    		 afficheChar(maxString[i-1]-nbChar,' ');
			      }
				 System.out.println("|");
	           }
	      }
	   catch(SQLException e) {
		   System.out.println("Il y a un problème avec votre requete");
	   }
   }
   
   public static void executeUpdate(String query) {
	   try(Connection con = DriverManager.getConnection(DB_CONNECTION, DB_USER,DB_PASSWORD);
		   PreparedStatement prstm = con.prepareStatement(query);)
		   {
		   int affRows =prstm.executeUpdate();
		   System.out.println((affRows>0)?affRows+"lignes ont été affactés":"aucune ligne n'a été affecté");
	   }
	   catch(SQLException e) {
		   System.out.println("Il y a un problème avec votre requete");
	   }
   }
   
   public static void afficheChar(int nb, char c) {
	   for(int i=0; i<nb; i++) System.out.print(c);
   }
}

