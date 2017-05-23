package login;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import dbConnection.dbConnection;

/**
 * Created by pawel on 15.04.2017.
 */
public class LoginModel {
    Connection connection;

    public LoginModel(){
        try {
            this.connection = dbConnection.getConnection();
        } catch(SQLException e){
            e.printStackTrace();
        }

        if (this.connection == null){
            System.exit(1);
        }
    }

    public boolean isDatabaseConnected(){
        return this.connection != null;
    }

    public boolean isLogin(String user, String pass, String option) throws Exception
    {
        PreparedStatement pr = null;
        ResultSet result = null;

        String sql = "SELECT * FROM login where username=? and password = ? and role = ?";

        try {
            pr = this.connection.prepareStatement(sql);
            pr.setString(1, user);
            pr.setString(2, pass);
            pr.setString(3, option);
            result = pr.executeQuery();

            boolean b1;
            if (result.next()){
                return true;
            } else  return false;
        }
        catch (SQLException e){
            return false;
        }
        finally {
            pr.close();
            result.close();
        }
    }



}
