/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import datasource.PartMapper;
import domain.MyException;
import domain.Part;
import java.sql.Connection;
import java.sql.DriverManager;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author jekm
 */
public class PartmMapperTest {

    PartMapper pm;
    Connection con;
    String pw = "cphsl159";
    String id = "cphsl159";

    public PartmMapperTest() {
    }

    @Before
    public void setUp() throws Exception {
        getConnection();
        Fixture.setUp(con);
        pm = new PartMapper();
    }

    @After
    public void tearDown() {
        releaseConnection();
    }

    @Test
    public void testGetPartMatch() {

        Part p = pm.getPart(10506, con);
        //== return value correct ?
        assertTrue("GetPartMatch failed1", p != null);
        assertTrue("GetPartMatch failed2", p.getPno() == 10506);

    }

    @Test
    public void testGetPartNoMatch() {

        Part p = pm.getPart(10555, con);
        //== return value correct?
        assertTrue("GetPartNoMatc failed1", p == null);

    }

    @Test
    public void testUpdatePartMatch() {
        Part p = new Part(10506, "Star Wars", 222, 33.33, 444);
        boolean updateOK = pm.updatePart(p, con);
        //== return value correct?
        assertTrue("UpdatePartMatch failed1", updateOK);
        Part p2 = pm.getPart(10506, con);
        //== end state correct?
        assertTrue("UpdatePartMatch failed2", p2.getQOH() == 222);
    }

    @Test
    public void testUpdatePartNoMatch() {

        Part p = new Part(10555, "Star Wars", 222, 33.33, 444);
        boolean updateOK = pm.updatePart(p, con);
        //== return value correct?
        assertFalse("UpdatePartNoMatch failed1", updateOK);
    }
    
    
    @Test(expected=MyException.class)
    public void saveNewPartWithExc() throws MyException{
        
        Part p1 = new Part(10506, "Harry Potter, Den Gode", 111, 20, 333);
        pm.savePart(p1, con);
    }
    
    @Test
    public void saveNewPartWithoutExp() throws MyException{
    Part p1 = pm.getPart(19999, con);
        assertTrue("saveNewPart failed1", p1==null);
        p1 = new Part(19999, "Harry Potter, Den Gode", 111, 20, 333);
        pm.savePart(p1, con);
        Part p2 = pm.getPart(19999, con);
        boolean testp1p2 = (p1.getPno()==p2.getPno() && p1.getPname().equals(p2.getPname()));
        assertTrue("saveNewPart failed2", testp1p2);
        
    }
    
    @Test
    public void deletePart(){
        Part p1 = pm.getPart(10506, con);
        assertTrue("detlePart failed1", (p1.getPno()==10506));
        assertTrue("deltePart2", pm.deletePart(p1, con));
        p1 = pm.getPart(10506, con);
        assertTrue("detlePart failed3", (p1==null));
        
    }

    //=== Connection specifics
    private void getConnection() {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            con = DriverManager.getConnection(
                    "jdbc:oracle:thin:@datdb.cphbusiness.dk:1521:dat", id, pw);
        } catch (Exception e) {
            System.out.println("fail in getConnection()");
            System.out.println(e);
        }
    }

    //=== Connection specifics
    public void releaseConnection() {
        try {
            con.close();
        } catch (Exception e) {
            System.err.println(e);
        }
    }
}
