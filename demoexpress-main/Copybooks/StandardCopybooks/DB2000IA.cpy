      ******************************************************************
      ******************************************************************
      * THIS COPYBOOK IS PART OF THE DER SYSTEM                         
      ******************************************************************
      *                                                                *
      *       SYSTEM: DATABASE ADMINISTRATION                          *
      *    SUBSYSTEM: DTC STORED PROCEDURE SQLCA FORMAT                *
      *                                                                *
      *      INCLUDE: DB2000IA                                         *
      *       AUTHOR: MICHAEL MCCARTHY                                 *
      *         DATE: JUNE 30, 1998                                    *
      *                                                                *
      *    ABSTRACT:  THIS INCLUDE MEMBER CONTAINS FIELDS FOR USE      *
      * WITH THE DTC STORED PROCEDURE SQLCA FORMATER CALLED BY INCLUDE *
      * DB2000IC.  THIS INCLUDE CONTAINS A DISPLAY FORMAT SQLCA.  THIS *
      * MEMBER MUST BE COPIED AS THE LAST WORKING-STORAGE ENTRY.  SEE  *
      * THE DOCUMENTATION IN DB2000IC FOR USAGE INSTRUCTIONS.          *
      *                                                                *
      *----------------------------------------------------------------*
      *-------------------  R E V I S I O N  L O G  -------------------*
      *----------------------------------------------------------------*
      * DATE        REVISED BY              REQUESTED BY               *
      * ----------  --------------------    --------------------       *
      * 99/99/9999  XXXXXXXXXXXXXXXXXXXX    XXXXXXXXXXXXXXXXXXXX       *
      *                                                                *
      * DESCRIPTION: XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX *
      * XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX *
      * XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX *
      * XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX *
      *                                                                *
      *----------------------------------------------------------------*
      * DATE        REVISED BY              REQUESTED BY               *
      * ----------  --------------------    --------------------       *
      * 99/99/9999  XXXXXXXXXXXXXXXXXXXX    XXXXXXXXXXXXXXXXXXXX       *
      *                                                                *
      * DESCRIPTION: XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX *
      * XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX *
      * XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX *
      * XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX *
      *                                                                *
      ******************************************************************
      *                                                                 
      ******************************************************************
      *            179 BYTE DISPLAY FORMAT SQLCA STRUCTURE             *
      ******************************************************************
       01  DB2000I-SQLCA.                                               
           05  DB2000I-SQLCAID         PIC X(08).                       
           05  DB2000I-SQLCABC         PIC ++++++++9.                   
           05  DB2000I-SQLCODE         PIC ++++++++9.                   
           05  DB2000I-SQLERRML        PIC ++++9.                       
           05  DB2000I-SQLERRMC        PIC X(70).                       
           05  DB2000I-SQLERRP         PIC X(08).                       
           05  DB2000I-SQLERRD-1       PIC ++++++++9.                   
           05  DB2000I-SQLERRD-2       PIC ++++++++9.                   
           05  DB2000I-SQLERRD-3       PIC ++++++++9.                   
           05  DB2000I-SQLERRD-4       PIC ++++++++9.                   
           05  DB2000I-SQLERRD-5       PIC ++++++++9.                   
           05  DB2000I-SQLERRD-6       PIC ++++++++9.                   
           05  DB2000I-SQLWARN         PIC X(11).                       
           05  DB2000I-SQLSTATE        PIC X(05).                       
                                                                                
