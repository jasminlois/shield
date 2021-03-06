/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import db.DBConnector;
import entity.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Franco
 */
public class UserDAO {
    
    public User login(String username, String password){ //Checks for login success; Returns null for failures and User object for successes
        try{
            //Create Connection
            DBConnector db = new DBConnector();
            Connection cn = db.getConnection();
            
            //Prepare the statement which will use a stored procedure
            PreparedStatement pstmt = cn.prepareStatement("CALL `shield`.`6474`(?, ?);");
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            
            //Run statement and advance to first line
            ResultSet rs = pstmt.executeQuery();
            rs.next();
            
            User user = null; //Create return variable
        
            if(rs.getRow() != 0){ 
                int success = rs.getInt(1);
                if(success == 1){
                    int id = rs.getInt(2);
                    int classID = rs.getInt(3);
                    String uname = rs.getString(4);
                    String nameTitle = rs.getString(5);
                    String nameFirst = rs.getString(6);
                    String nameOther = rs.getString(7);
                    String nameLast = rs.getString(8);
                    user = new User(id, classID, uname, nameTitle, nameFirst, nameOther, nameLast);
                }
            }
            
            //Close connection
            cn.close();
            return user;
            
        } catch(SQLException ex){
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
}
