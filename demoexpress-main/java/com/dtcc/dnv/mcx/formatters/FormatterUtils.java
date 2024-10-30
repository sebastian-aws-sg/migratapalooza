package com.dtcc.dnv.mcx.formatters;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.dtcc.dnv.mcx.beans.TermBean;
import com.dtcc.dnv.mcx.util.MessageLogger;

/**
 * Copyright © 2003 The Depository Trust & Clearing Company. All rights
 * reserved.
 * 
 * Depository Trust & Clearing Corporation (DTCC) 55, Water Street, New York, NY
 * 10048 U.S.A All Rights Reserved.
 * 
 * This software may contain (in part or full) confidential/proprietary
 * information of DTCC. ("Confidential Information"). Disclosure of such
 * Confidential Information is prohibited and should be used only for its
 * intended purpose in accordance with rules and regulations of DTCC. Form bean
 * for a Struts application.
 * 
 * @version 1.0
 * @author Kevin Lake
 *  
 */
public class FormatterUtils {

	private static final MessageLogger log = MessageLogger.getMessageLogger(FormatterUtils.class.getName());

    /**
     * Method format date as a string by mask.
     * 
     * @param amount
     * @return String
     */
    public static String formatOutputDate(String date, String inmask, String outmask) {
        if (inmask == null || inmask.length() == 0)
            inmask = "yyyy-MM-dd";
        if (outmask == null || outmask.length() == 0)
            outmask = "dd-MMM-yyyy";

        if (date == null || date.trim().length() < 4)
            return "";
        else {
            Date dt = getDate(date, inmask);
            if (dt != null)
                return getStringFromDate(dt, outmask);
            else
                return "";
        }
    }

    /**
     * Formats the Database returned TimeStamp to a format 'MM/dd/yy hh:mm:ss PM EDT'
     * @param dateTime
     * @return
     */
    public static String formatOutputDateTimeStamp(Timestamp dateTime)
    {
    	return DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.LONG).format(dateTime);
    }
    

    /**
     * Method to format String from Date by mask
     * 
     * @param date
     * @param mask
     * @return
     */
    public static String getStringFromDate(Date date, String mask) {

        Format formatter = new SimpleDateFormat(mask);
        return formatter.format(date);
    }

    /**
     * Method to format dates from String by mask
     * 
     * @param sDate
     * @param mask
     * @return
     */
    public static Date getDate(String sDate, String mask) {

        try {
            DateFormat df = new SimpleDateFormat(mask);
            Date dt = df.parse(sDate);
            return dt;
        } catch (ParseException e) {
            return null;
        }

    }

    /**
     * @param number
     * @return
     */
    public static String formatNumber(String number) {
        try {
            if (number == null || number.trim().length() == 0)
                return "";
            else if (Double.parseDouble(number.trim()) == 0d) {
                return "";
            } else {
                return number.trim();
            }
        } catch (NumberFormatException e) {
            log.error(e);
        }
        return number;
    }
    
    
    /**
     * 
     * Method to replace spaces with &nbsp; 
     * @param cmpnyName
     * @return
     */
    public static String formatName(String cmpnyName) {
        final String SPACE = "&nbsp;";
        cmpnyName = cmpnyName.trim().replaceAll(" ",SPACE);
        return cmpnyName;
    }
    /**
     *  method to disable the hyperlink in the posted term value
     * @param termVal
     * @return
     */
    public static String formatTermValue(String termVal){
        String newVal = termVal.replaceAll("href=","style=\"cursor:default\" ");
        return newVal;
        
    }

    /**
	 * Modify the Amendment Text for the Image Source with its docId
	 * @param termBean
	 * @param docId
	 */
	public static void modifyImageSource(TermBean termBean)
	{
		String amendmentValue = termBean.getTermVal();
		
		int startloc = 0;
		int endloc = 0;
		String originalFilePath = null;
		String replaceFilePath = null;
		startloc = amendmentValue.indexOf("src=") + 4;
		if(amendmentValue.indexOf("/mcx/action/getImageDoc?docId=") > 0){			
			endloc = amendmentValue.indexOf(">", startloc);
		}else if(amendmentValue.indexOf(".", startloc) > 0){			
			endloc = amendmentValue.indexOf(".", startloc)+5;		
		}else 
			return;
		
		 originalFilePath = amendmentValue.substring(startloc, endloc);
		 replaceFilePath = "'/mcx/action/getImageDoc?docId=" + termBean.getDocId() + "'";
		amendmentValue = amendmentValue.replaceFirst(originalFilePath, replaceFilePath);
		termBean.setTermVal(amendmentValue);
		termBean.setDocName(originalFilePath);
	}

}
