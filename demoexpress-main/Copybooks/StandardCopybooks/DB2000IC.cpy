      ******************************************************************
      * THIS COPYBOOK IS PART OF THE DER SYSTEM                         
      ******************************************************************
      *                                                                *
      *    SYSTEM:    DATABASE ADMINISTRATION                          *
      *    SUBSYSTEM: DTC STORED PROCEDURE SQLCA FORMAT                *
      *                                                                *
      *    INCLUDE:   DB2000IC                                         *
      *    AUTHOR:    MICHAEL MCCARTHY                                 *
      *    DATE:      JUNE 30, 1998                                    *
      *                                                                *
      *    ABSTRACT:  THIS INCLUDE MEMBER CONTAINS CODE NEEDED AT      *
      * THE END OF THE PROCEDURE DIVISION IN STORED PROCEDURES TO      *
      * FORMAT THE OUTSQLCA RETURNED TO AN ASCII CLIENT.               *
      * IT IS USED IN CONJUNCTION WITH INCLUDES DB2000IA AND DB2000IB. *
      *                                                                *
      * THE PROGRAMMER MUST INCLUDE THIS AT THE BOTTOM OF THE          *
      * PROCEDURE DIVISION, WITH DB2000IA INCLUDED AT THE BOTTOM OF    *
      * THE WORKING-STORAGE SECTION, AND DB2000IB INCLUDED AT THE TOP  *
      * OF THE LINKAGE SECTION.  AS IN THE FOLLOWING EXAMPLE:          *
      *                                                                *
      *  WORKING-STORAGE SECTION.                                      *
      *      ...                                                       *
      *  COPY DB2000IA                                               . *
      *                                                                *
      *  LINKAGE SECTION.                                              *
      *  COPY DB2000IB                                               . *
      *  01  LINK-VAR-1    PIC X(10).                                  *
      *                                                                *
      *  PROCEDURE DIVISION USING OUTSQLCA, LINK-VAR-1.                *
      *      ...                                                       *
      *      EXEC SQL ... END-EXEC.                                    *
      *      PERFORM DB2000I-FORMAT-SQLCA                              *
      *         THRU DB2000I-FORMAT-SQLCA-EXIT.                        *
      *      ...                                                       *
      *      GOBACK.                                                   *
      *  COPY DB2000IC.                                                *
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
                                                                        
       DB2000I-FORMAT-SQLCA.                                            
      ******************************************************************
      **   COPY THE SQLCA INTO AN ALL DISPLAY FORMAT.  THIS WAY WE    **
      **   WILL BE ABLE TO TRANSFER THE ENTIRE SQLCA TO A REMOTE DB2  **
      **   CLIENT (ASCII PLATFORM), AND NOT LOSE THE ABILITY TO XLATE **
      **   THE NUMERIC FIELDS.  THE CLIENT WILL REFORMAT THIS TO A    **
      **   VALID SQLCA STRUCTURE FOR PROCESSING.                      **
      ******************************************************************
           MOVE SQLCAID       TO DB2000I-SQLCAID.                       
           MOVE SQLCABC       TO DB2000I-SQLCABC.                       
           MOVE SQLCODE       TO DB2000I-SQLCODE.                       
           MOVE SQLERRML      TO DB2000I-SQLERRML.                      
           MOVE SQLERRMC      TO DB2000I-SQLERRMC.                      
           MOVE SQLERRP       TO DB2000I-SQLERRP.                       
           MOVE SQLERRD(1)    TO DB2000I-SQLERRD-1.                     
           MOVE SQLERRD(2)    TO DB2000I-SQLERRD-2.                     
           MOVE SQLERRD(3)    TO DB2000I-SQLERRD-3.                     
           MOVE SQLERRD(4)    TO DB2000I-SQLERRD-4.                     
           MOVE SQLERRD(5)    TO DB2000I-SQLERRD-5.                     
           MOVE SQLERRD(6)    TO DB2000I-SQLERRD-6.                     
           MOVE SQLWARN       TO DB2000I-SQLWARN.                       
           MOVE SQLSTATE      TO DB2000I-SQLSTATE                       
           MOVE DB2000I-SQLCA TO OUTSQLCA.                              
       DB2000I-FORMAT-SQLCA-EXIT.                                       
           EXIT.                                                        
                                                                        
                                                                        
