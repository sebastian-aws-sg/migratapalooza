/*
 * Created on Aug 18, 2005
 *
 *
 */
package com.mytelco.common;

import java.io.Serializable;

/**
 * DOCUMENT ME!
 *
 * @author $author$
 * @version $Revision$
 */
public class LoginInfo extends Object implements Serializable{
    /**
     *  userid: Stores an unique id to identify your user
     */
    private String id = null;
    private String password = null;



    /** Creates new LoginInfo object */
    public LoginInfo() {
    }

    /**
     * Creates a new LoginInfo object.
     *
     * @param as_user DOCUMENT ME!
     */
    public LoginInfo(String aId, String aPassword) {
        setId(aId);
        setPassword(aPassword);

    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public String getId() {
        return id;
    }

    /**
     * DOCUMENT ME!
     *
     * @param as_user DOCUMENT ME!
     */
    public void setId(String aId) {
        id = aId;
    }



    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public String getPassword() {
        return password;
    }

    /**
     * DOCUMENT ME!
     *
     * @param password DOCUMENT ME!
     */
    public void setPassword(String password) {
        this.password = password;
    }
}
