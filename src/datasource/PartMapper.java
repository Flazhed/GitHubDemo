package datasource;

import domain.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

//=== Encapsulates SQL-statements
//=== and mapping between objects and tables
// hau 2011
public class PartMapper {

    // load a tuple from parts
    public Part getPart(int pno, Connection con) {
        Part p = null;
        String SQLString1 = // get part
                "select * "
                + "from parts "
                + "where pno = ?";

        PreparedStatement statement = null;

        try {
            //=== get part
            statement = con.prepareStatement(SQLString1);
            statement.setInt(1, pno);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                p = new Part(pno,
                        rs.getString(2),
                        rs.getInt(3),
                        rs.getDouble(4),
                        rs.getInt(5));
            }
        } catch (Exception e) {
            System.out.println("Fail in PartsMapper - getPart");
            System.out.println(e.getMessage());
        }

        return p;
    }

    //== Update a tuple in part
    public boolean updatePart(Part p, Connection con) {
        int rowsInserted = 0;
        String SQLString
                = "update parts "
                + "set pname = ?, QOH = ?, price = ?, olevel = ? "
                + "where pno = ?";

        PreparedStatement statement = null;

        try {
            //== update tuple
            statement = con.prepareStatement(SQLString);
            statement.setString(1, p.getPname());
            statement.setInt(2, p.getQOH());
            statement.setDouble(3, p.getPrice());
            statement.setInt(4, p.getOlevel());
            statement.setInt(5, p.getPno());
            rowsInserted = statement.executeUpdate();
        } catch (Exception e) {
            System.out.println("Fail in PartsMapper - updatePart");
            System.out.println(e.getMessage());
        }

        return rowsInserted == 1;
    }

    //== Save a new part
    public boolean savePart(Part p, Connection con) throws MyException {

        int rowsInserted = 0;
        String SQLString
                = "insert into parts "
                + "values (?,?,?,?,?)";
        PreparedStatement statement = null;

        try {
            //== insert tuple
            statement = con.prepareStatement(SQLString);
            statement.setInt(1, p.getPno());
            statement.setString(2, p.getPname());
            statement.setInt(3, p.getQOH());
            statement.setDouble(4, p.getPrice());
            statement.setInt(5, p.getOlevel());
            rowsInserted = statement.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Fail in Partmapper - saveNewPart");
            System.out.println(e.getMessage());
            throw new MyException(p.getPno());

        } finally // must close statement
        {
            try {
                statement.close();
            } catch (SQLException e) {
                System.out.println("Fail in Partmapper - newPart");
                System.out.println(e.getMessage());
            }
        }

        return rowsInserted == 1;
    }

    public boolean deletePart(Part p, Connection con) {
        int rowsAffected = 0;
        String SQLString
                = "DELETE from parts "
                + "where pno = ?";
        PreparedStatement statement = null;

        try {
            //== insert tuple
            statement = con.prepareStatement(SQLString);
            statement.setInt(1, p.getPno());
            rowsAffected = statement.executeUpdate();
        } catch (Exception e) {
            System.out.println("Fail in Partmapper1 - deletePart");
            System.out.println(e.getMessage());
        } finally // must close statement
        {
            try {
                statement.close();
            } catch (SQLException e) {
                System.out.println("Fail in Partmapper2 - deletePart");
                System.out.println(e.getMessage());
            }
        }
        return rowsAffected == 1;
    }

}
