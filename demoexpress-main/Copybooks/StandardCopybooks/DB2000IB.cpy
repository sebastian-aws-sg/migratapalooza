      ******************************************************************
      ******************************************************************
      * THIS COPYBOOK IS PART OF THE DER SYSTEM                         
      ******************************************************************
      *                                                                *
      *       SYSTEM: DATABASE ADMINISTRATION                          *
      *    SUBSYSTEM: DTC STORED PROCEDURE SQLCA FORMAT                *
      *                                                                *
      *      INCLUDE: DB2000IB                                         *
      *       AUTHOR: MICHAEL MCCARTHY                                 *
      *         DATE: JUNE 30, 1998                                    *
      *                                                                *
      *    ABSTRACT:  THIS INCLUDE MEMBER CONTAINS THE LINKAGE SECTION *
      * DEFINITION OF THE OUTSQLCA.  THIS MEMBER EXISTS SO THAT IF THE *
      * SQLCA EVER CHANGES, IT CAN BE CHANGED HERE AND ALL PROGRAMS    *
      * WILL ONLY NEED TO BE RECOMPILED, AND NOT MODIFIED.  THIS COPY  *
      * MEMBER MUST BE THE FIRST LINE UNDER THE LINKAGE SECTION.       *
      * SEE THE DOCUMENTATION IN DB2000IC FOR USAGE INSTRUCTIONS.      *
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
      *         LINKAGE SECTION OUTPUT DISPLAY SQLCA STRUCTURE         *
      ******************************************************************
       01  OUTSQLCA                    PIC X(179).                      
