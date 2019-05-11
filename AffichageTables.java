package bases.postgres;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Scanner;

public class AffichageTables {       
	private static final String DB_CONNECTION = "jdbc:postgresql://localhost:5432/mybd";        
	private static final String DB_USER = "serge";
	private static final String DB_PASSWORD = "toto";

   public static void main(String...args) throws SQLException{
	 int choix;  
	 while((choix=afficherMenu())!=6) {
	   switch(choix) {
	     case 1 : afficherTable("client");
	              break;
	     case 2 : afficherTable("compte");
	              break;
	     case 3 : afficherTable("ecriture");
                  break;
         case 4 : afficherTable("associer");
                  break;
         case 5 : afficherTable("produit");
                  break;
         default : System.out.println("Cette option n'existe pas");
                  break;
	   }
	 }
   }
   
   public static void afficherTable(String tableName) throws SQLException  {
	   
	   try(Connection con = DriverManager.getConnection(DB_CONNECTION, DB_USER,DB_PASSWORD);
		   PreparedStatement stm = con.prepareStatement("select * from "+tableName);
		   ResultSet rs = stm.executeQuery();){
		   ResultSetMetaData rsmd = rs.getMetaData();
           int columnsNumber = rsmd.getColumnCount();
          
		   while(rs.next()) {
			 for(int i = 1; i<=columnsNumber; i++) {   
			   String valeur = rs.getString(i);
		       System.out.print(" "+valeur+" :");
		      }
			   System.out.println();
	       }
      }
   }
   
   public static int afficherMenu() {
	   Scanner reader = new Scanner(System.in);
	   System.out.println("============================================================");
	   System.out.println("|                           MENU                           |");
	   System.out.println("============================================================");
	   System.out.println("1. Client ");
	   System.out.println("2. Compte");
	   System.out.println("3. Ecriture");
	   System.out.println("4. Associer");
	   System.out.println("5. Produit");
       System.out.println("6. Quitter");
       System.out.print("Votre choix (pensez à validez) >");
       return reader.nextInt();
   }
}
