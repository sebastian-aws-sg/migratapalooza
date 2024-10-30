/*
 * Created on Aug 19, 2005
 *
 *  
 */
package com.mytelco.server;
import java.rmi.RemoteException;

import javax.ejb.EJBObject;

import com.mytelco.common.LoginInfo;

/**
 * @author myTelco 
 *
 *  
 */
public interface LogonSession extends EJBObject {

    public boolean authenticate(LoginInfo user) throws RemoteException;
    
}
