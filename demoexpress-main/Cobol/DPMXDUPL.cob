       IDENTIFICATION DIVISION.
       PROGRAM-ID.    DPMXDUPL.
       AUTHOR.        COGNIZANT.

      ******************************************************************
      * THIS IS AN UNPUBLISHED PROGRAM OWNED BY ISCC                   *
      * IN WHICH A COPYRIGHT SUBSISTS AS OF JULY 2006.                 *
      *                                                                *
      ******************************************************************
      ******************************************************************
      **         P R O G R A M   D O C U M E N T A T I O N            **
      ******************************************************************
      * SYSTEM:    MCA XPRESS                                          *
      * PROGRAM:   DPMXDUPL                                            *
      *                                                                *
      * THIS BATCH PROGRAM IS USED TO UPLOAD THE DOCUMENT IN THE       *
      * IN DOCUMENT TABLE.                                             *
      *                                                                *
      ******************************************************************
      * TABLES:                                                        *
      * -------                                                        *
      * TDPM12_MCA_DOC - TABLE THAT STORES THE ACTUAL DOCUMENT         *
      *                                                                *
      *----------------------------------------------------------------*
      * INCLUDES:                                                      *
      * ---------                                                      *
      * SQLCA   - DB2 COMMAREA                                         *
      * DPM1201 - DOCUMENT TABLE DCLGEN                                *
      *----------------------------------------------------------------*
      *              M A I N T E N A N C E   H I S T O R Y            *
      *                                                               *
      *                                                               *
      * DATE CREATED    VERSION     PROGRAMMER                        *
      * ------------    -------     --------------------              *
      *                                                               *
      * 09/21/2007          1.0     COGNIZANT                         *
      *                             INITIAL IMPLEMENTATION            *
      *                                                               *
      *****************************************************************
       ENVIRONMENT DIVISION.
       DATA DIVISION.
       WORKING-STORAGE SECTION.
       01 WS-PROGRAM                        PIC X(08) VALUE 'DPMXDUPL'.
       01 WS-TABLE-NAME                     PIC X(30) VALUE SPACES.
       01 WS-PARAGRAPH-NAME                 PIC X(30) VALUE SPACES.
       01 WS-DOC-DEL-CD                     PIC X(01) VALUE SPACES.
       01 WS-IN-BLOB                        USAGE IS SQL
                                            TYPE IS BLOB(2097152).
       01 WS-VARIABLES.
          05 WS-SQLCODE                     PIC +(9)9.
          05 WS-DOC-SEQ                     PIC S9(18)V COMP-3.
       01 WS-PASS-AREA.
037800    05 WS-IN-CMPNY-ID                 PIC X(8).
037900    05 WS-IN-MCA-TMPLT-ID             PIC S9(9) COMP.
037900    05 WS-IN-MCA-DOC-DS               PIC X(100).
038000    05 WS-IN-DOC-TYPE-CD              PIC X(1).
038000    05 WS-IN-ROW-UPDT-USER-ID         PIC X(10).
024200 01 WS-SWITCHES.
024300    05 WS-DB-PROCESS-SW               PIC X(01).
024400       88 WS-DB-SUCCESS               VALUE 'S'.
024400       88 WS-DB-ERROR                 VALUE 'E'.
      *****************************************************************
      *                        SQL INCLUDES                            *
      ******************************************************************
           EXEC SQL
                INCLUDE SQLCA
           END-EXEC.

           EXEC SQL
                INCLUDE DPM1201
           END-EXEC.
037200
037300 LINKAGE SECTION.
037400
037500 01 LS-PASS-AREA.
037800    05 LS-IN-CMPNY-ID              PIC X(8).
037900    05 LS-IN-MCA-TMPLT-ID          PIC S9(9) COMP.
037900    05 LS-IN-MCA-DOC-DS            PIC X(216).
038000    05 LS-IN-DOC-TYPE-CD           PIC X(1).
038000    05 LS-IN-ROW-UPDT-USER-ID      PIC X(10).
238000    05 LS-IN-DOC-OBJ-TX            USAGE IS SQL
238000                                   TYPE IS BLOB(2097152).
038000    05 LS-OUT-DOC-ID               PIC S9(18)V COMP-3.
038000    05 LS-OUT-SQLCODE              PIC +(9)9.
038100
      *----------*
       PROCEDURE DIVISION USING LS-PASS-AREA.
      *----------*
       0000-MAIN.
      *----------*

           PERFORM 1000-INITIALIZE
           PERFORM 2000-PROCESS-BLOB
           PERFORM 9990-GOBACK
           .
047900*************************************************************
048000*     INITIALIZE                                            *
048100*************************************************************
       1000-INITIALIZE.

           INITIALIZE                           WS-SQLCODE
                                                WS-DOC-SEQ
                                                WS-PASS-AREA
                                                WS-IN-BLOB.
048100*************************************************************
048200 2000-PROCESS-BLOB.
048100*************************************************************
048300
           MOVE LS-IN-CMPNY-ID             TO WS-IN-CMPNY-ID
           MOVE LS-IN-MCA-TMPLT-ID         TO WS-IN-MCA-TMPLT-ID
           MOVE LS-IN-MCA-DOC-DS           TO WS-IN-MCA-DOC-DS
           MOVE LS-IN-DOC-TYPE-CD          TO WS-IN-DOC-TYPE-CD
           MOVE LS-IN-ROW-UPDT-USER-ID     TO WS-IN-ROW-UPDT-USER-ID
           MOVE LS-IN-DOC-OBJ-TX-LENGTH    TO WS-IN-BLOB-LENGTH
           MOVE LS-IN-DOC-OBJ-TX-DATA(1:LS-IN-DOC-OBJ-TX-LENGTH)
                                           TO WS-IN-BLOB-DATA
           PERFORM 2100-DOC-SEQ-GEN

           EXEC SQL
              INSERT INTO NSCC.VDPM12_MCA_DOC
                    (MCA_VALUE_ID
                    ,MCA_TMPLT_ID
                    ,MCA_DOC_DS
                    ,MCA_DOC_TYPE_CD
                    ,CMPNY_ID
                    ,DOC_DEL_CD
                    ,ROW_UPDT_TS
                    ,ROW_UPDT_USER_ID
                    ,MCA_DOC_OBJ_TX)
              VALUES (:WS-DOC-SEQ
                     ,:WS-IN-MCA-TMPLT-ID
                     ,:WS-IN-MCA-DOC-DS
                     ,:WS-IN-DOC-TYPE-CD
                     ,:WS-IN-CMPNY-ID
                     ,:WS-DOC-DEL-CD
                     ,CURRENT TIMESTAMP
                     ,:WS-IN-ROW-UPDT-USER-ID
                     ,:WS-IN-BLOB)
           END-EXEC
049100
049200     EVALUATE SQLCODE
049300        WHEN 0
049400           MOVE SQLCODE              TO  LS-OUT-SQLCODE
049400           MOVE WS-DOC-SEQ           TO  LS-OUT-DOC-ID
049500        WHEN OTHER
049800           MOVE '2000-PROCESS-BLOB'
049800                                     TO  WS-PARAGRAPH-NAME
049400           MOVE 'VDPM12_MCA_DOC'     TO  WS-TABLE-NAME
050300           PERFORM 9000-SQL-ERROR
049400           MOVE SQLCODE              TO  LS-OUT-SQLCODE
049400           PERFORM 9990-GOBACK
050400     END-EVALUATE
050500
050600     .
044500*--------------------------*
044600 2100-DOC-SEQ-GEN.
470000*--------------------------*

044800     MOVE '2100-DOC-SEQ-GEN'         TO WS-PARAGRAPH-NAME

           INITIALIZE WS-DOC-SEQ

           EXEC SQL
               SET :WS-DOC-SEQ = (NEXT VALUE FOR DPM.SQDPM111)
           END-EXEC
045700
045800     EVALUATE SQLCODE
046000        WHEN ZEROES
                 DISPLAY 'WS-DOC-SEQ = ' WS-DOC-SEQ
      *          CONTINUE
046700        WHEN OTHER
049800           MOVE '2100-DOC-SEQ-GEN'
049800                                     TO WS-PARAGRAPH-NAME
049400           MOVE SPACE                TO WS-TABLE-NAME
050300           PERFORM 9000-SQL-ERROR
049400           MOVE SQLCODE              TO  LS-OUT-SQLCODE
049400           PERFORM 9990-GOBACK
047000     END-EVALUATE
045700
047100     .
      *------------------------*
       9000-SQL-ERROR.
      *------------------------*

           MOVE SQLCODE                 TO WS-SQLCODE
           DISPLAY ' *** SQL ERROR *** '
           DISPLAY 'PROGRAM   NAME = ' WS-PROGRAM
           DISPLAY 'PARAGRAPH NAME = ' WS-PARAGRAPH-NAME
           DISPLAY 'TABLE     NAME = ' WS-TABLE-NAME
           DISPLAY 'SQLCODE        = ' WS-SQLCODE
           .
      ******************************************************************
      *END PROCESSING
      ******************************************************************
       9990-GOBACK.
            GOBACK
           .

