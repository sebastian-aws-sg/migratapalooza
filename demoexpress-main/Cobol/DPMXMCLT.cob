       IDENTIFICATION DIVISION.
       PROGRAM-ID.   DPMXMCLT.
       AUTHOR.       COGNIZANT.
       DATE-WRITTEN. SEPTEMBER 2007.
      *                                                                *
      ******************************************************************
      *                                                                *
      *   THIS IS AN UNPUBLISHED PROGRAM OWNED BY ISCC                 *
      *   IN WHICH A COPYRIGHT SUBSISTS AS OF JULY 2006.               *
      *                                                                *
      ******************************************************************
      ******************************************************************
      *                                                                *
      *                   COMPILATION INSTRUCTION                      *
      *                  COMPILE DB2 VS COBOL 370                      *
      *                                                                *
      ******************************************************************
      *
      *    **LNKCTL**
      *    INCLUDE SYSLIB(DSNRLI)
      *    MODE AMODE(31) RMODE(ANY)
      *    ENTRY DPMXMCLT
      *    NAME  DPMXMCLT(R)
      *
      ******************************************************************
      **         P R O G R A M   D O C U M E N T A T I O N            **
      ******************************************************************
      *                                                                *
      * SYSTEM:    MCA XPRESS APPLICATION                              *
      * PROGRAM:   DPMXMCLT                                            *
      *                                                                *
      * This stored procedure provides the list of Clients who have    *
      * enrolled with the Dealer for a specific Template.              *
      *                                                                *
      ******************************************************************
      *                                                                *
      * TABLES:                                                        *
      * -------                                                        *
      *                                                                *
      * TDPM06_MCA_ENRL      - MCA ENROLLMENT TABLE                    *
      * TDPM01_MCA_CMPNY     - MCA COMPANY TABLE                       *
      * TDPM14_MCA_TMPLT     - MCA TEMPLATE TABLE                      *
      *                                                                *
      *----------------------------------------------------------------*
      *                                                                *
      * INCLUDES:                                                      *
      * ---------                                                      *
      *                                                                *
      * SQLCA                - DB2 COMMAREA                            *
      * DPM0601              - MCA ENROLLMENT TABLE                    *
      * DPM0101              - MCA COMPANY TABLE                       *
      * DPM1401              - MCA TEMPLATE TABLE                      *
      * DTM5401              - DCLGEN copy book for VDTM54_DEBUG_CNTRL *
      *                                                                *
      *----------------------------------------------------------------*
      *                                                                *
      * COPYBOOK:                                                      *
      * ---------                                                      *
      *                                                                *
      * DB2000IA                                                       *
      * DB2000IB                                                       *
      * DB2000IC                                                       *
      *                                                                *
      *----------------------------------------------------------------*
      *                                                                *
      *              M A I N T E N A N C E   H I S T O R Y             *
      *                                                                *
      * DATE CHANGED    VERSION     PROGRAMMER                         *
      * ------------    -------     --------------------               *
      *                                                                *
      * 09/26/2007        000       COGNIZANT                          *
      *                             INITIAL IMPLEMENTATION FOR         *
      *                             MCA XPRESS.                        *
      *                                                                *
      ******************************************************************
      *                                                                *
       ENVIRONMENT DIVISION.
       DATA DIVISION.
       WORKING-STORAGE SECTION.
      *
       01  WS-SQLCODE                       PIC -ZZZ9.
       01  WS-PROGRAM                       PIC X(08) VALUE 'DPMXMCLT'.
       01  WS-TEMPLATE-ID                   PIC S9(9)V USAGE COMP-3.
       01  WS-CONVERT-TEMPLATE-ID           PIC X(9).
       01  FILLER REDEFINES WS-CONVERT-TEMPLATE-ID.
           05 WS-NUMERIC-TEMPLATE-ID        PIC 9(9).
       01  WS-DEALER-ID                     PIC X(08) VALUE SPACES.
       01  WS-ERROR-AREA.
           05  WS-PARAGRAPH-NAME            PIC X(40).
           05  WS-TABLE-NAME                PIC X(40).
       01  WS-SPACES-TEMPID                 PIC X(50) VALUE
           'Template Id is Zeroes, Send Valid Value'.
       01  WS-SPACES-USERID                 PIC X(50) VALUE
           'User Id is Spaces, Send Valid Value'.
       01  WS-DASHES                        PIC X(40) VALUE ALL '='.
       01  WS-TS                            PIC X(26).
       01  WS-DISPLAY-SWITCH                PIC X(01) VALUE 'N'.
           88 DISPLAY-PARAMETERS                      VALUE 'Y'.
           88 HIDE-PARAMETERS                         VALUE 'N'.
      *
      **SQL COMMUNICATIONS AREA PASSED BACK IN OUTSQLCA
      *
           EXEC SQL
              INCLUDE SQLCA
           END-EXEC

           EXEC SQL
              INCLUDE DPM0601
           END-EXEC

           EXEC SQL
              INCLUDE DPM0101
           END-EXEC

           EXEC SQL
              INCLUDE DPM1401
           END-EXEC

      * INCLUDE FOR VDTM54_DEBUG_CNTRL                                  00024910
           EXEC SQL                                                     00024920
                INCLUDE DTM5401                                         00024930
           END-EXEC.                                                    00024940

      *
      **DB2 STANDARD COPYBOOK WITH FORMATTED DISPLAY SQLCA
      **THIS MUST REMAIN AS THE LAST ENTRY IN WORKING STORAGE
      *
       COPY  DB2000IA.
      *
       LINKAGE SECTION.
      *
      **PICTURE CLAUSE FOR OUTSQLCA - PIC X(179) - FOR LINKAGE SECTION
      *
       COPY  DB2000IB.
      *
       01  LS-SP-ERROR-AREA                 PIC X(80).
       01  LS-SP-RC                         PIC X(04).
       01  LS-TEMPLATE-ID                   PIC S9(9) USAGE COMP.
       01  LS-DEALER-ID                     PIC X(08).
      *
       PROCEDURE DIVISION USING  OUTSQLCA,
                                 LS-SP-ERROR-AREA,
                                 LS-SP-RC,
                                 LS-TEMPLATE-ID,
                                 LS-DEALER-ID.

      *----------*
       0000-MAIN.
      *----------*

           PERFORM 1000-INITIALIZE
           PERFORM 2000-VALIDATE-INPUT
           PERFORM 3000-ENROLLED-NOT-ASSIGNED
           IF DISPLAY-PARAMETERS
              PERFORM 9100-DISPLAY-DATA
           END-IF

           PERFORM 9990-GOBACK
           .

      *------------------------*
       1000-INITIALIZE.
      *------------------------*

           MOVE '1000-INITIALIZE'           TO WS-PARAGRAPH-NAME
           MOVE SPACES                      TO OUTSQLCA
                                               LS-SP-ERROR-AREA
           MOVE 'SP00'                      TO LS-SP-RC
           MOVE LS-TEMPLATE-ID              TO WS-CONVERT-TEMPLATE-ID
           MOVE WS-NUMERIC-TEMPLATE-ID      TO WS-TEMPLATE-ID
           MOVE LS-DEALER-ID                TO WS-DEALER-ID
                                                                        00051700
           EXEC SQL                                                     00051800
                SELECT ACTVT_DSPLY_IN                                   00051900
                  INTO :D054-ACTVT-DSPLY-IN                             00052010
                FROM VDTM54_DEBUG_CNTRL                                 00052040
                WHERE PRGM_ID = :WS-PROGRAM                             00052050
                WITH UR                                                 00052050
           END-EXEC                                                     00052060
                                                                        00052070
           MOVE SQLCODE                     TO WS-SQLCODE               00052080
                                                                        00052090
           EVALUATE SQLCODE                                             00052091
              WHEN 0                                                    00052092
                  IF D054-ACTVT-DSPLY-IN = 'Y'                          00052094
                     SET DISPLAY-PARAMETERS TO TRUE                     00052095
                  END-IF                                                00052099
              WHEN +100                                                 00052092
                  CONTINUE
              WHEN OTHER                                                00052092
                  PERFORM 9000-SQL-ERROR
           END-EVALUATE                                                 00052102

           IF DISPLAY-PARAMETERS
              DISPLAY WS-DASHES
              DISPLAY 'DPMXMCLT STARTED AT      :' WS-TS
              DISPLAY WS-DASHES
           END-IF

           .
           .

      *------------------------*
       2000-VALIDATE-INPUT.
      *------------------------*

           MOVE '2000-VALIDATE-INPUT'       TO WS-PARAGRAPH-NAME

           EVALUATE WS-TEMPLATE-ID
              WHEN ZERO
                 MOVE 'SP50'                TO LS-SP-RC
                 MOVE WS-SPACES-TEMPID      TO LS-SP-ERROR-AREA
                 PERFORM 9100-DISPLAY-DATA
                 PERFORM 9990-GOBACK
            END-EVALUATE

           EVALUATE WS-DEALER-ID
              WHEN SPACES
                 MOVE 'SP50'                TO LS-SP-RC
                 MOVE WS-SPACES-USERID      TO LS-SP-ERROR-AREA
                 PERFORM 9100-DISPLAY-DATA
                 PERFORM 9990-GOBACK
            END-EVALUATE

           .

      *----------------------------*
       3000-ENROLLED-NOT-ASSIGNED.
      *----------------------------*

           MOVE '3000-ENROLLED-NOT-ASSIGNED' TO WS-PARAGRAPH-NAME

           EXEC SQL
              DECLARE DPMXMCLT_CSR CURSOR WITH HOLD WITH RETURN FOR
     1         SELECT DPM06.CLNT_CMPNY_ID AS "CLIENT COMPANY CODE",
     2                DPM01.CMPNY_NM AS "CLIENT COMPANY NAME"
                FROM  VDPM06_MCA_ENRL  DPM06,
                      D0005 DPM01,
                      D0006 DPM14
                WHERE DPM06.RQST_TMPLT_ID  = DPM14.MCA_ISDA_TMPLT_ID
                  AND DPM14.MCA_TMPLT_ID   = :WS-TEMPLATE-ID
                  AND DPM06.DELR_CMPNY_ID  = :WS-DEALER-ID
                  AND DPM06.DELR_STAT_CD   = 'A'
                  AND DPM06.ASGD_TMPLT_ID  = 0
                  AND DPM01.CMPNY_ID       = DPM06.CLNT_CMPNY_ID
             ORDER BY DPM01.CMPNY_NM ASC
                   WITH UR
           END-EXEC
      *
           EXEC SQL
              OPEN DPMXMCLT_CSR
           END-EXEC

           EVALUATE SQLCODE
              WHEN 0
                 MOVE 'SP00'                TO LS-SP-RC
              WHEN OTHER
                 MOVE 'DPMXMCLT_CSR'        TO WS-TABLE-NAME
                 PERFORM 9000-SQL-ERROR
           END-EVALUATE
           .

      *------------------------*
       9000-SQL-ERROR.
      *------------------------*

           MOVE 'Database error has occurred. Please contact DTCC.'
                                            TO LS-SP-ERROR-AREA
           MOVE 'SP99'                      TO LS-SP-RC

           PERFORM 9100-DISPLAY-DATA
           MOVE SQLCODE                     TO WS-SQLCODE
           DISPLAY 'PARAGRAPH-NAME          :' WS-PARAGRAPH-NAME
           DISPLAY 'SQLCODE                  :' WS-SQLCODE

           PERFORM 9999-FORMAT-SQLCA
           PERFORM 9990-GOBACK
           .

      *------------------------*
       9100-DISPLAY-DATA.
      *------------------------*

           DISPLAY WS-DASHES
           DISPLAY 'PROGRAM-NAME             :' WS-PROGRAM
           DISPLAY 'PARAGRAPH-NAME           :' WS-PARAGRAPH-NAME
           DISPLAY 'TABLE/CURSOR NAME        :' WS-TABLE-NAME
           DISPLAY 'SP-ERROR-AREA            :' LS-SP-ERROR-AREA
           DISPLAY 'SP-RC                    :' LS-SP-RC

           EVALUATE LS-SP-RC
              WHEN 'SP50'
                 DISPLAY 'TEMPLATE ID        :' LS-TEMPLATE-ID
                 DISPLAY WS-DASHES
                 DISPLAY 'DEALER ID          :' LS-DEALER-ID
                 DISPLAY WS-DASHES
              WHEN 'SP99'
                 DISPLAY "SQLERRMC   : " SQLERRMC
                 DISPLAY "SQLSTATE   : " SQLSTATE
                 DISPLAY "SQLCODE    : " SQLCODE
                 DISPLAY "SQLERRD(1) : " SQLERRD(1)
                 DISPLAY "SQLERRD(2) : " SQLERRD(2)
                 DISPLAY "SQLERRD(3) : " SQLERRD(3)
                 DISPLAY "SQLERRD(4) : " SQLERRD(4)
                 DISPLAY "SQLERRD(5) : " SQLERRD(5)
                 DISPLAY "SQLERRD(6) : " SQLERRD(6)
              EXEC SQL
                   ROLLBACK
              END-EXEC
              WHEN OTHER
                 DISPLAY WS-DASHES
           END-EVALUATE

           EXEC SQL
               SET :WS-TS = CURRENT TIMESTAMP
           END-EXEC
           .

      *------------------------*
       9990-GOBACK.
      *------------------------*

           PERFORM 9999-FORMAT-SQLCA
           GOBACK
           .

      *------------------------*
       9999-FORMAT-SQLCA.
      *------------------------*

           PERFORM DB2000I-FORMAT-SQLCA
              THRU DB2000I-FORMAT-SQLCA-EXIT
           .
      *
      **MOVE STATEMENTS TO FORMAT THE OUTSQLCA USING DB2000IA & DB2000IB
      *
        COPY DB2000IC.
