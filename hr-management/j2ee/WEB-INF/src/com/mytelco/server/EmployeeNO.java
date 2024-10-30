package com.mytelco.server;

import java.io.Serializable;

public class EmployeeNO implements Serializable {
	StringBuffer mystring = new StringBuffer();
    public String empNo ;
    public EmployeeNO(String empNo){
         this.empNo = empNo;	
    }
    public EmployeeNO(){}
    public String toString(){
    	return empNo.toString();
    }
    public int hashCode(){
    	return empNo.hashCode();
    }
    public boolean equals(Object emp){
    	return ((EmployeeNO)emp).empNo.equals(empNo);
    }
}
