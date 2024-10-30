package com.mytelco.server;

import java.io.Serializable;

public class EmpPrjActPKey implements Serializable {
    public String employeeNo;
    public String projectNo;
    public String activityNo;

    public EmpPrjActPKey(){}

    public EmpPrjActPKey(String employeeNo,String projectNo, String activityNo){
         this.employeeNo = employeeNo;	
         this.activityNo = activityNo;
         this.projectNo  = projectNo;
    }
    
    public String getEmployeeNo(){
    	return employeeNo;
    }
  
    public String getProjectNo(){
    	return projectNo;
    }
    
    public String getActivityNo(){
    	return activityNo;
    }

    public boolean equals(Object obj){
    	if(obj == null || !(obj instanceof EmpPrjActPKey)) {
    		return false;
    	}
    	else{
			EmpPrjActPKey other = (EmpPrjActPKey) obj;
    		if (this.activityNo.equals(other.activityNo) && 
    				this.employeeNo.equals(other.employeeNo) && 
    				this.projectNo.equals(other.projectNo) == true){
    			return true;
    		}
    		else{
    			return false;
    		}
		}
    }

    public String toString(){
    	return employeeNo + ":" + projectNo + ":" + activityNo;
    }

    public int hashCode(){
    	return employeeNo.hashCode()^projectNo.hashCode()^activityNo.hashCode();
    }

}
