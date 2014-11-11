package datasource;

import java.sql.Connection;
import domain.*;

// Encapsulates the Data Source Layer
// Encapsulates connection handling 
// Implemented as a Singleton to provide global access from
// Domain classes and to ensure the use of max one connection
// hau
public class DBFacade {

	  private PartMapper pm; 
	  private Connection con;
	  
	  //== Singleton start
	  private static DBFacade instance;
	 
	  private DBFacade() {
		  pm 	= new PartMapper();
		  con 	= DBConnector.getInstance().getConnection();  
                  
	  }
	  public static DBFacade getInstance()
	  {
		  if(instance == null)
			  instance = new DBFacade();
		  return instance;
	  }
	  //== Singleton end

	  
	  public Part getpart(int ono) 
	  {
		  return pm.getPart(ono, con);	      
	  }
	  
          public boolean updatePart(Part p){
              
              return pm.updatePart(p, con);
          }
}
