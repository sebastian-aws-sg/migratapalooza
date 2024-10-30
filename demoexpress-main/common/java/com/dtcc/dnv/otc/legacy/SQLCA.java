package com.dtcc.dnv.otc.legacy;

//import java.io.BufferedReader;
//import java.io.FileReader;
//import java.io.IOException;


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
 * @author     Sarosh Pasricha
 */

public class SQLCA
{

   private String sqlCa, sqlcAid, sqlcAbc, sqlErrmc, sqlErrp, sqlState;
   private int sqlErrml, sqlCode;
   private int[] sqlErrd;
   private char[] sqlWarn;

   private static final int sqlAidStrtPos = 0;
   private static final int sqlAbcStrtPos = 8;
   private static final int sqlcodeStrtPos = 17;
   private static final int sqlErrmlStrtPos = 26;
   private static final int sqlErrmcStrtPos = 31;
   private static final int sqlErrpStrtPos = 101;
   private static final int sqlErrdStrtPos = 109;
   private static final int sqlWarnStrtPos = 163;
   private static final int sqlStateStrtPos = 174;


   /**
    * Private Constructor.
    */
   private SQLCA()
   {
      super();

      sqlcAid = sqlcAbc = sqlErrmc = sqlErrp = sqlState = null;
      sqlCode = sqlErrml = 0;
      sqlErrd = new int[6];
      sqlWarn = new char[11];
   }

   /**
    * Public Constructor.
    * @param sqlCa
    */
   public SQLCA(String sqlCa)
   {
      this();

      this.sqlCa = sqlCa;

      parseSQLCa();
   }

   /**
    * Parses the SQLCA that was passed to the public Constructor.
    */
   private void parseSQLCa()
   {
      sqlcAid = sqlCa.substring(sqlAidStrtPos, 8);
      sqlcAbc = sqlCa.substring(sqlAbcStrtPos, 17);

      try
      {
         String strSqlCode = sqlCa.substring(sqlcodeStrtPos, 26).trim();
         sqlCode = Integer.parseInt( (strSqlCode.substring(0,1).equals("+"))? strSqlCode.substring(1): strSqlCode );

         String sLen = sqlCa.substring(sqlErrmlStrtPos, 31).trim();
         sLen = (sLen.substring(0,1).equals("+"))? sLen.substring(1): sLen;
         sqlErrml = Integer.parseInt(sLen);
      }
      catch(NumberFormatException nfe)
      {
         sqlErrml = 70;
      }

      sqlErrmc = sqlCa.substring(sqlErrmcStrtPos, 101);
      sqlErrp = sqlCa.substring(sqlErrpStrtPos, 109);

      int pos = sqlErrdStrtPos;
      for(int i = 0; i < 6; i++)
      {
         try
         {
            String strSqlErrd = sqlCa.substring(pos, pos + 9).trim();
            strSqlErrd = (strSqlErrd.substring(0,1).equals("+"))? strSqlErrd.substring(1): strSqlErrd;
            sqlErrd[i] = Integer.parseInt( strSqlErrd );
            pos += 9;
         }
         catch(NumberFormatException nfe)
         {
            sqlErrd[i] = 0;
         }

      }

      pos = sqlWarnStrtPos;
      for(int i = 0; i < 11; i++)
      {
         sqlWarn[i] = sqlCa.charAt(pos);
         pos++;
      }

      sqlState = sqlCa.substring(174);
   }


   /**
    * Getter for the original SQLCA that was passed to the public constructor.
    * @return
    */
   public String getSqlCa()
   {
      return sqlCa;
   }


   /**
    * Getter for SQLCABC.
    * @return
    */
   public String getSqlcAbc()
   {
      return sqlcAbc;
   }


   /**
    * Getter for SQLCAID.
    * @return
    */
   public String getSqlcAid()
   {
      return sqlcAid;
   }


   /**
    * Getter for the SQLCODE.
    * Returns it as an integer.
    * @return
    */
   public int getSqlCode()
   {
      return sqlCode;
   }


   /**
    * Return the SQLCOE as a String.
    * @return
    */
   public String getSqlCodeAsString()
   {
      return (sqlCode > 0)? "+" + Integer.toString(sqlCode): Integer.toString(sqlCode);
   }


   /**
    * Getter for the SQLERRD array of integers.
    * Length of this array is 6.
    * @return
    */
   public int[] getSqlErrd()
   {
      return sqlErrd;
   }


   /**
    * Getter for individual elements of the SQLERRD array.
    * The parameter must be between 0 and 5.
    * @param i
    * @return
    */
   public int getSqlErrd(int i)
   {
      return sqlErrd[i];
   }


   /**
    * Getter for the SQLERRMC - the SQL error message.
    * @return
    */
   public String getSqlErrmc()
   {
      return sqlErrmc;
   }


   /**
    * Getter for SQLERRML - the length of the SQL error message in SQLERRMC.
    * @return
    */
   public int getSqlErrml()
   {
      return sqlErrml;
   }


   /**
    * Getter for SQLERRP.
    * If an error occured, this field will contain the name of the DB2 module
    * that detected the error.
    * @return
    */
   public String getSqlErrp()
   {
      return sqlErrp;
   }


   /**
    * Getter for SQLSTATE.
    * Contains a return code for the outcome of the execution of the most
    * recent SQL statement.  The SQLSTATE values are documented in <b>DB2 Messages and Codes</b>.
    * @return
    */
   public String getSqlState()
   {
      return sqlState;
   }


   /**
    * Getter for the entire SQLWARN array.
    * This array of chars has a length of 11.
    * @return
    */
   public char[] getSqlWarn()
   {
      return sqlWarn;
   }


   /**
    * Getter for an individual element of the SQLWARN array.
    * @param i
    * @return
    */
   public char getSqlWarn(int i)
   {
      return sqlWarn[i];
   }


   /**
    * Getter for SQLWARNA
    * @return
    */
   public char getSqlWarnA()
   {
      return sqlWarn[11];
   }


   /**
    * Formats the parsed SQLCA for display.
    * @return
    */
   public String toString()
   {
      String strSqlCa = "SQLCAID   : " + sqlcAid + "\n" +
                        "SQLCABC   : " + sqlcAbc + "\n" +
                        "SQLCODE   : " + getSqlCodeAsString() + "\n" +
                        "SQLERRML  : " + sqlErrml + "\n" +
                        "SQLERRMC  : " + sqlErrmc + "\n" +
                        "SQLERRP   : " + sqlErrp + "\n";

      for(int i = 0; i < 6; i++)
          strSqlCa += "SQLERRD[" + i + "]: " + Integer.toString(sqlErrd[i]) + "\n";

      for(int i = 0; i < 10; i++)
         strSqlCa += "SQWARN[" + i + "] : " + new Character(sqlWarn[i]).toString() + "\n";

      strSqlCa += "SQLWARNA  : " + sqlWarn[10] + "\n" +
                  "SQLSTATE  : " + sqlState;

      return strSqlCa;
   }
}