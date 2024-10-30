/*
 * Created on Oct 17, 2005
 *
 *  
 */
package com.mytelco.common;

import java.util.Date;

/**
 * @author myTelco 
 * 
 * 
 */
public class MsgInTrayData {

    private String empNo;

    private Date received;

    private String source;

    private String subject;

    private String noteText;

    /**
     * @return Returns the Employee Number.
     */
    public String getempNo() {
        return empNo;
    }

    /**
     * @param empNo
     *            The Employee Number to set.
     */
    public void setempNo(String empNo) {
        this.empNo = empNo;
    }

    
    public String getnoteText() {
        return noteText;
    }

    public void setnoteText(String noteText) {
        this.noteText = noteText;
    }

    
    public String getsource() {
        return source;
    }

    public void setsource(String source) {
        this.source = source;
    }

    
    public String getsubject() {
        return subject;
    }

    public void setsubject(String subject) {
        this.subject = subject;
    }

    
    public Date getreceived() {
        return received;
    }

    public void setreceived(Date received) {
        this.received = received;
    }

}