/*
 * Created on Aug 19, 2005
 *
 *
 */
package com.mytelco.server;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import javax.naming.NamingException;

import com.mytelco.common.CommonUtility;
import com.mytelco.common.LoginInfo;

/**
 * @author myTelco 
 *
 *
 */
public class LogonSessionBean implements SessionBean {

    public boolean authenticate(LoginInfo user) throws RemoteException {
        String userId = user.getId();
        String password = user.getPassword();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = CommonUtility.getConnection(CommonUtility.getCentralDBName());
            String strSql = "SELECT count(*) cnt FROM SUBSIDIARY WHERE SUBID = ? AND SUBPASSOWRD = ? ";
            preparedStatement = connection.prepareStatement(strSql);
            preparedStatement.setString(1, userId);
            preparedStatement.setString(2, password);
            System.out.println(" Query being Fired is ; " + strSql);
            ResultSet rs = preparedStatement.executeQuery();
            System.out.println("result returned !");

            if (rs.next()) {
                int count = rs.getInt("cnt");
                System.out.println("count = " + count);
                if (count > 0) {
                    return true;
                }

            }
            rs.close();
        } catch (NamingException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        CommonUtility.closePreparedStatement(preparedStatement);
        CommonUtility.closeConnection(connection);
        return false;
    }

    public void ejbCreate() throws RemoteException{
    }

    public void setSessionContext(SessionContext ctx) throws RemoteException{
    }

    public void ejbRemove() throws RemoteException{
    }

    public void ejbActivate() throws RemoteException{
    }

    public void ejbPassivate() throws RemoteException{
    }

    public void ejbLoad() throws RemoteException{
    }

    public void ejbStore() throws RemoteException{
    }

}