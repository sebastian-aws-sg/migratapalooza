package com.dtcc.dnv.otc.legacy;

import java.lang.reflect.Field;
import java.text.DecimalFormat;
import java.text.FieldPosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;


/**
 * Copyright © 2003 The Depository Trust & Clearing Company. All rights reserved.
 *
 * Depository Trust & Clearing Corporation (DTCC)
 * 55, Water Street,
 * New York, NY 10048
 * U.S.A
 * All Rights Reserved.
 *
 * This software may contain (in part or full) confidential/proprietary information of DTCC.
 * ("Confidential Information"). Disclosure of such Confidential
 * Information is prohibited and should be used only for its intended purpose
 * in accordance with rules and regulations of DTCC.
 * Form bean for a Struts application.
 *
 * @version 	1.0
 * @author     Dmitriy Larin
 * 
 * Rev  1.2     July 11, 2004        tk
 * Removed imports on OTCDNavigation, FormException and StringDictionary
 * Removed methods referencing the deleted imports
 * 
 * Rev 1.3      Oct 4, 2004 dlv
 * Removed unused imports
 */

public class HtmlUtilsDlv
{
   	
	public HtmlUtilsDlv()
	{
		
  	}
  	
	private static final Logger log = Logger.getLogger(HtmlUtilsDlv.class);
	
   private static String[] monthNames = {"Jan", "Feb", "Mar", "Apr", "May", "Jun",
                                         "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
    /**
     * This method is used to obtain the HttpServletRequest object.  The
     * HttpServletRequest is found in the Webplatform String Dictionary in
     * one of two places depending on which version is being used.
     *
     * @param pDictionary - a StringDictionary object that is passed by Webplatform.
     * @return HttpServletRequest object
     */


    public synchronized static String trim(String str) {
      if(str == null) {
        return "";
      }
      else {
        return str.trim();
      }
    }
    /**
     * This method returns a string object containing the current system date.
     * The date is formated to conform to the current Risk Web standards.
     */
    public synchronized static String getSystemDate()
    {
        SimpleDateFormat df = new SimpleDateFormat("MMM d, yyyy HH:mm z");
        return df.format(new java.util.Date());
    }

    public static String getCurrentDate()
    {
        //SimpleDateFormat df = new SimpleDateFormat("d-MMM-yyyy HH:mm a z");
        SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        return df.format(new java.util.Date());
    }

    public static String getCurrentDateBack15()
    {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE,-15);
		Date date = cal.getTime();
        SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        return df.format(date);
    }

    public synchronized static String sqlTsToDB2Ts(String sqlTs)
    {
        String db2Ts = sqlTs.replace(':','.').replace(' ','-');
        return db2Ts;
    }

    /**
     * This method takes a jdbc timestamp and converts it to a string in the
     * format required for the OTCD project.
     *
     * @param dt - jdbc date
     * @return String containing the formatted date
     */
    public synchronized static String formatDate(java.sql.Timestamp dt)
    {
        SimpleDateFormat df = new SimpleDateFormat("d-MMM-yyyy");
        return df.format(dt);
    }

    public synchronized static String padString(int len)
    {
       StringBuffer sb = new StringBuffer();
       sb.setLength(len);
       String str = sb.toString();
       return str.replace('\u0000', ' ');
    }


    public synchronized static String ISOToDisplayDt(String isoDate)
    {
       StringTokenizer st = new StringTokenizer(isoDate, "-/");
       String strCcyy = (String) st.nextElement();
       String strMth = (String) st.nextElement();
       String strDay = (String) st.nextElement();
//       if( strDay.length() == 1) strDay = "0" + strDay;
//       if( strMth.length() == 1) strMth = "0" + strMth;
       return strMth + "-" + strDay + "-" + strCcyy;
    }


    public synchronized static String DisplayDateToISODate(String displayDate)
    {
       StringTokenizer st = new StringTokenizer(displayDate, "-");
       String displayMth = (String) st.nextElement();
       String displayDay = (String) st.nextElement();
       String displayCcyy = (String) st.nextElement();
       int i = 0;
       for(i = 0; i < monthNames.length; i++)
       {
          if( displayMth.equalsIgnoreCase(monthNames[i]) )
              break;
       }

       String strMth = Integer.toString(++i);
       if( strMth.length() == 1 )
          strMth = "0" + strMth;

       return strMth + "-" + displayDay + "-" + displayCcyy;
    }

    public synchronized static String DisplayDtToISODt(String displayDate,boolean isDefaultLow)
    {
       if(displayDate == null
          || displayDate.trim().length() == 0) {
         if(isDefaultLow){
           return "01/01/0001";
         }
         else {
           return "12/31/2999";
         }
       }
       return displayDate;
    }

    public static String DisplayDtToISOTs(String displayDate, boolean isDefaultLow)
    {
       String date = DisplayDtToISODt(displayDate,isDefaultLow);
       return date+"-00.00.00.000000" ;
    }

    public static void main(String[] args)
    {
       String str = DisplayDateToISODate(args[0]);
       //System.out.println("DISPLAY DATE  : " + args[0]);
       //System.out.println("ISO DATE      : " + str);
    }
    public synchronized static String removeCommas(String num)
    {
       if(num == null || num.trim().length()==0) {
         return "";
       }
       StringTokenizer st = new StringTokenizer(num,",");
       String num2= "";
       while(st.hasMoreElements()) {
         num2 = num2 + (String) st.nextElement();
       }
       if(num2.trim().length()==0)
         num2=num;
       return num2;
    }
//    public static synchronized String logError(String errCode, String errMsg) {
//    	return (new Timestamp(System.currentTimeMillis())).toString();
//    }
//    public static synchronized String logError(String errCode, String[] errMsg) {
//    	return (new Timestamp(System.currentTimeMillis())).toString();
//    } 



    public static String logError(String errMessageNbr, String[] params)
    {
       return logError(errMessageNbr, params, new Throwable(" ") );
    }



    public static String logError(String errMessageNbr, String[] params, Throwable t)
    {
       String errorMessageTemplate = null;

       try
       {
          Class constants = Class.forName("com.dtcc.otcd.common.OTCDConstants");
          Field errorField = constants.getDeclaredField("ERROR_" + errMessageNbr);
          errorMessageTemplate = (String) errorField.get(null);
       }
       catch(ClassNotFoundException cnf)
       {
       	log.error("ClassNotFoundException from HtmlUtilsDlv:logError ", cnf);  
       }
       catch(IllegalArgumentException iae)
       {
       	log.error("IllegalArgumentException from HtmlUtilsDlv:logError ", iae);
       }
       catch(IllegalAccessException iace)
       {
       	log.error("IllegalAccessException from HtmlUtilsDlv:logError ", iace);
       }
       catch(NoSuchFieldException nsfe)
       {
       	log.error("NoSuchFieldException from HtmlUtilsDlv:logError ", nsfe);
       }


       if( errorMessageTemplate.indexOf("%%") != -1 && params != null)
       {
          int placeHolder = 0;
          for(int i = 0; i < params.length; i++)
          {
             placeHolder = errorMessageTemplate.indexOf("%%");
             if(placeHolder != -1)
                errorMessageTemplate = errorMessageTemplate.substring(0, placeHolder) +
                                       params[i] + errorMessageTemplate.substring(placeHolder + 2);
          }
       }

       Date dt = new GregorianCalendar().getTime();
       SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy 'at' kk:mm:s.S z");
       String dateTime = sdf.format(dt);

       String msg = "Error Date and Time: " + dateTime + "\n" +
                    "Error Message: " + errorMessageTemplate + "\n";


       // DLV 12/8/2003. New logging procedure is implemented
       // using log4j. Custom functions going out of the scope 
       // eventually. 
       //OTCDLogger.log(msg, t);

       return dateTime + "\n" + errorMessageTemplate;
    }

    public static synchronized String formatUsCurr(String num) {
      if(num == null || num.trim().length()==0)
        return "";
      else
        num = num.trim();
      String num1 = "";
      String num2 = "";
      String num3 = "";
      java.util.StringTokenizer st = new java.util.StringTokenizer(num,".");
      if(st.hasMoreElements()) {
        num1 = (String)st.nextElement();
      }
      if(st.hasMoreElements()) {
        num2 = (String)st.nextElement();
      }
      //
      boolean done = true;
      for(int mm=0; mm < num1.length() && done; mm++) {
        if(num1.charAt(mm) == '0') {
          num1 = num1.substring(1, num1.length());
          mm--;
        }
        else
          done=false;
      }
      //
      int size = num1.length();
      int kk=1;
      int ii=size-1;
      for(; ii >=0 ; ii--) {
        num3= (""+num1.charAt(ii))+num3;
        if(kk==3 && ii>0) {
          kk=0;
          num3=","+num3;
        }
        kk++;
      }
      return (""+num3+"."+num2);
    }

    public static synchronized String blankToNbsp(String val) {
      if(val==null || val.trim().length()==0) {
        //return "n/a";
        return "&nbsp;";
      }
      else {
        return val;
      }
    }
/*
    public static synchronized String escapeSpecChars(String val) {
      String[] escList = OTCDConstants.escapeList;
      int index = 0;
      for(int ii=0; ii< escList.length ; ii++) {
        index = val.indexOf(escList[ii],index);
        while(index >= 0 )  {
          String temp = val.substring (0,index-1) + "\\" + val.substring (index, val.length()-1);
           val = temp;
        }
      }
      return null;
    }
*/
    public static String getCurrDateFormatted(String fmt)
    {
       SimpleDateFormat sdf = null;
       //
       if( fmt.equalsIgnoreCase("US") )
          sdf = new SimpleDateFormat("MM/dd/yyyy");
       else
          sdf = new SimpleDateFormat("yyyy-MM-dd");
       //
       return sdf.format( new Date() );
    }


    public static String getCurrTimeFormatted()
    {
       SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
       return sdf.format( new Date() );
    }    
    
    public static String usToISODate(String date)
    {
       if( date == null || date.trim().equals("") )
          return date;
       StringTokenizer st = new StringTokenizer(date, "/");
       String mm = (String) st.nextElement();
       String dd = (String) st.nextElement();
       String ccyy = (String) st.nextElement();
       String dt = ccyy + "-" + mm + "-" + dd;
       return dt.trim();
    }
    
    public static String iSODateToUs(String date)
    {
       if( date == null || date.trim().equals("") )
          return "";
       if( date.indexOf("-") < 0)
       		return date ;
       //
       
       StringTokenizer st = new StringTokenizer(date, "-");
       String ccyy = (String) st.nextElement();
       String mm = (String) st.nextElement();
       String dd = (String) st.nextElement();
       String dt = mm + "/" + dd + "/"+ ccyy;
       return dt.trim();
    }    

    public static String usToMMYY(String date)
    {
       if( date.trim().equals("") )
          return date;
       StringTokenizer st = new StringTokenizer(date, "/");
       String mm = (String) st.nextElement();
       //String dd = (String) st.nextElement();
       String ccyy = (String) st.nextElement();
       String dt = "" + mm + ccyy.substring(2);
       return dt.trim();
    }

    
    public static String deEditAmount(String amt, String tok)
    {
       StringTokenizer st = new StringTokenizer(amt, tok);

       String amtDeedited = "";
       while( st.hasMoreElements() )
          amtDeedited += st.nextElement();

       return amtDeedited;
    }

    public static String editAmount(String amt, int numDec)
    {
       String decimalPattern = null;
       String intPartPattern = "###,###,###,##0";
       String decPartPattern2 = ".00";
       String decPartPattern5 = ".00000";


       if (numDec == 0)
          decimalPattern = intPartPattern;
       else if( numDec == 2)
          decimalPattern = intPartPattern + decPartPattern2;
       else
          decimalPattern = intPartPattern + decPartPattern5;

       double d = 0;
       try
       {
          d = new Double( amt.trim() ).doubleValue();
       }
       catch(NumberFormatException nfe) { }

       DecimalFormat df = new DecimalFormat(decimalPattern);

       StringBuffer sb = new StringBuffer();
       df.format( d, sb, new FieldPosition(0) );

       return sb.toString();
    }

	 public static String getNameFromList(String list, String firmId, String seperator)
	 {
       String firmName = "   ";

       StringTokenizer st = new StringTokenizer(list, seperator);
       while( st.hasMoreElements() )
       {
          String ele = (String) st.nextElement();

          StringTokenizer eleTkn = new StringTokenizer(ele, "|");
          String id = (String) eleTkn.nextElement();
          String name = (String) eleTkn.nextElement();

          if( id.trim().equalsIgnoreCase(firmId) )
          {
             firmName = name;
             break;
          }
       }

       return firmName;
	 	
	 }


    public static String formatAmount(String amt)
    {
       String strDoubleAmt = amt.substring(0, 12) + "." + amt.substring(12);
       double d = 0.0D;
       try
       {
          d = Double.parseDouble(strDoubleAmt);
       }
       catch(NumberFormatException nfe)
       {
          d = 0.0D;
       }

       DecimalFormat df = new DecimalFormat("###,###,###,##0.00000");
       StringBuffer sb = df.format(d, new StringBuffer(), new FieldPosition(0) );

       return sb.toString();
    }
    
	//Fills the left side of a string with characters to make it a certain length.
	//If the string is already that length, the string just returned without any type of formatting
	public static String leftStringFill (String string, int length, char charFill)
	  {
	       int stringLength = string.length();
	
	
	       if (stringLength < length)
	       {
	            int diff =  length - stringLength;
	            for (int i = 0; i < diff; i++)
	            {
	             string = charFill + string;
	            }
	       }
	       else
	       {
	         //The incoming string is the same size as the desired length or longer,
	         //don't change the string and return it
	       }
	   return string;
	   }
	
	////////////////////////////////////////////
	//Fills the right side of a string with characters to make it a certain length.
	//If the string is already that length, the string just returned without any type of formatting
	public static String rightStringFill(String string, int length, char charFill)
		   {
		       int stringLength = string.length();
		       StringBuffer newString = new StringBuffer();
		       newString.append(string);
		
		
		       if (stringLength < length)
		       {
		            int diff =  length - stringLength;
		            for (int i = 0; i < diff; i++)
		            {
		              newString.append(charFill);
		            }
		       }
		       else
		       {
		         //The incoming string is the same size as the desired length or longer,
		         //don't change the string and return it
		       }
		   return newString.toString();
		   }
	
		public static String escapeIt(String inputString)
		{
			char[] oldChar = {'"', '\'', '\\','\t','\n','\b','\r'};
			String[] newChars = {"&#034;", "&#039;", "&#092;", "&#009;", "&#010;", "&#032;", "&#013;"}; 
	
			
			StringBuffer temp = new StringBuffer(inputString);
			
			for(int i = 0; i < temp.length(); i++)
			{
				for(int j = 0; j < oldChar.length; j++)
				{
					if( temp.charAt(i) == oldChar[j] )
					{
						temp.deleteCharAt(i);
						temp.insert(i, newChars[j]);
					}
				}	
			}		
			
			return new String(temp);
		}

/*
		public static String generateMasterConfTransTypeScript
		( DropDownBoxFields ddBoxFields )
		{
			//
			Vector vOptions = null;
			vOptions = ddBoxFields.getMasterConfirmTypes();	
			//
			String productA = "";
			String regionA  = "";
			//
         	StringBuffer kMasterConfTransType = new StringBuffer("kMasterConfTransTypeOptions = new Object() ;");
	        //----------------------- Create "All" values menu ---------------------------------------
	    	kMasterConfTransType.append("kMasterConfTransTypeOptions[\"All\"] = \n"); 
	    	kMasterConfTransType.append("[ \n");
			for(int i = 0;  i < vOptions.size(); i++) {
				DropDownBoxVO vo = (DropDownBoxVO) vOptions.get(i);
				//
				String product     = vo.getProductType();
				String region      = vo.getRegionType();
				String value       = vo.getValue();
				String description = vo.getDescription();
				String shortDesc   = vo.getShortDescription();
				String precision   = vo.getPrecision() ;
                String selected    = value.equalsIgnoreCase("All")?"true":"false";
				String defaultSelectedFlag = value.equalsIgnoreCase("All")?"true":"false";
				//Add heading - option group / product - region
				if(!productA.equalsIgnoreCase(product) || !regionA.equalsIgnoreCase(region))
				{
					productA = product ;
					regionA  = region;
					if(i != 0) kMasterConfTransType.append(",\n");
		       	    kMasterConfTransType.append("{ text:\"--- "+SearchTranslator.getInstance().getProductType(product)+" - "+region+" ---\",value:\"\", isDefaultSelectedFlag:false, isSelectedFlag:false } \n");      
				}
               	kMasterConfTransType.append(",\n");
		        kMasterConfTransType.append("{ text:\""+description+"\",value:\""+value+"\",isDefaultSelectedFlag:"+defaultSelectedFlag+",isSelectedFlag:"+selected+" }");  //"All" has to be a default
			} 
		    kMasterConfTransType.append("\n ] ; \n");
            //
	        //----------------------- Create "All" values menu ---------------------------------------
			productA = "";
			regionA  = "";
            //
	    	kMasterConfTransType.append("kMasterConfTransTypeOptions[\"CreditDefaultSwapShort\"] = \n"); 
	    	kMasterConfTransType.append("[ \n");
			for(int i = 0;  i < vOptions.size(); i++) {
				DropDownBoxVO vo = (DropDownBoxVO) vOptions.get(i);
				//
				String product     = vo.getProductType();
				String region      = vo.getRegionType();
				String value       = vo.getValue();
				String description = vo.getDescription();
				String shortDesc   = vo.getShortDescription();
				String precision   = vo.getPrecision() ;
                String selected    = value.equalsIgnoreCase("All")?"true":"false";
				String defaultSelectedFlag = value.equalsIgnoreCase("All")?"true":"false";
				
				if(    product == null
				    || product.equalsIgnoreCase("CreditDefaultSwapShort")
				    || product.equalsIgnoreCase("All") ) {
						//Add heading - option group / product - region
						if(!productA.equalsIgnoreCase(product) || !regionA.equalsIgnoreCase(region)) 
						{
								productA = product ;
								regionA  = region;
						       	if(i != 0) kMasterConfTransType.append(",\n");
						       	kMasterConfTransType.append("{ text:\"--- "+SearchTranslator.getInstance().getProductType(product)+" - "+region+" ---\",value:\"\", isDefaultSelectedFlag:false, isSelectedFlag:false } \n");      
					    }
	                	kMasterConfTransType.append(",\n");
				        kMasterConfTransType.append("{ text:\""+description+"\",value:\""+value+"\",isDefaultSelectedFlag:"+defaultSelectedFlag+",isSelectedFlag:"+selected+" }");  //"All" has to be a default
				}
			} 
		    kMasterConfTransType.append("\n ] ; \n");
            //
	        //----------------------- Create "All" values menu ---------------------------------------
			productA = "";
			regionA  = "";

	    	kMasterConfTransType.append("kMasterConfTransTypeOptions[\"CreditDefaultSwapIndex\"] = \n"); 
	    	kMasterConfTransType.append("[ \n");
			for(int i = 0;  i < vOptions.size(); i++) {
				DropDownBoxVO vo = (DropDownBoxVO) vOptions.get(i);
				//
				String product     = vo.getProductType();
				String region      = vo.getRegionType();
				String value       = vo.getValue();
				String description = vo.getDescription();
				String shortDesc   = vo.getShortDescription();
				String precision   = vo.getPrecision() ;
                String selected    = value.equalsIgnoreCase("All")?"true":"false";
				String defaultSelectedFlag = value.equalsIgnoreCase("All")?"true":"false";
				
				if(    product == null
				    || product.equalsIgnoreCase("CreditDefaultSwapIndex")
				    || product.equalsIgnoreCase("All") ) {
						//Add heading - option group / product - region
						if(!productA.equalsIgnoreCase(product) || !regionA.equalsIgnoreCase(region)) 
						{
								productA = product ;
								regionA  = region;
                                if(i != 0) kMasterConfTransType.append(",\n");								
						       	kMasterConfTransType.append("{ text:\"--- "+SearchTranslator.getInstance().getProductType( product )+" - "+region+" ---\",value:\"\", isDefaultSelectedFlag:false, isSelectedFlag:false } \n");      
						}
	                	kMasterConfTransType.append(",\n");
				        kMasterConfTransType.append("{ text:\""+description+"\",value:\""+value+"\",isDefaultSelectedFlag:"+defaultSelectedFlag+",isSelectedFlag:"+selected+" }"); 
				}
			} 
		    kMasterConfTransType.append("\n ] ; \n");
			return kMasterConfTransType.toString();	
		}	
*/
}