/*
 * Created on Aug 19, 2005
 *
 *  
 */
package com.mytelco.server;

import java.rmi.RemoteException;

import javax.ejb.CreateException;
import javax.ejb.EJBHome;

/**
 * @author myTelco 
 * 
 * 
 */
public interface LogonSessionHome extends EJBHome {
    LogonSession create() throws CreateException, RemoteException;

}