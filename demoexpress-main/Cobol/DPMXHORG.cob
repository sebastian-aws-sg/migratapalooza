       IDENTIFICATION DIVISION.
       PROGRAM-ID.   DPMXHORG.
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
      *    ENTRY DPMXHORG
      *    NAME  DPMXHORG(R)
      *
      ******************************************************************
      **         P R O G R A M   D O C U M E N T A T I O N            **
      ******************************************************************
      *                                                                *
      * SYSTEM:    MCA XPRESS APPLICATION                              *
      * PROGRAM:   DPMXHORG                                            *
      *                                                                *
      * This stored procedure returns the company detials (Counterparty*
      * /Dealer) for a given COMPANY ID.                               *
      *                                                                *
      ******************************************************************
      *                                                                *
      * TABLES:                                                        *
      * -------                                                        *
      *                                                                *
      * TDPM01_MCA_CMPNY     - THIS TABLE HOLDS COMPANY INFO.          *
      * TDPM02_DELR_CMPNY    - THIS TABLE HOLDS COMPANY NAME.          *
      * VDTM54_DEBUG_CNTRL   - DEBUG CONTROL TABLE                     *
      *                                                                *
      *----------------------------------------------------------------*
      *                                                                *
      * INCLUDES:                                                      *
      * ---------                                                      *
      *                                                                *
      * SQLCA                - DB2 COMMAREA                            *
      * DPM0101              - COMPANY INFO TABLE                      *
      * DPM0201              - COMPANY NAME TABLE                      *
      * DTM5401              - VDTM54_DEBUG_CNTRL TABLE                *
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
      * 09/17/2007        000       COGNIZANT                          *
      *                             INITIAL IMPLEMENTATION FOR         *
      *                             MCA XPRESS.                        *
      *                                                                *
      * 01/20/2008        001       COGNIZANT                          *
      *                             REMOVED UNION OF DPM02 TABLE FROM*
      *                             DPMXHORG_CSR CURSOR.               *
      *                                                                *
      ******************************************************************
      *                                                                *
       ENVIRONMENT DIVISION.
       DATA DIVISION.
       WORKING-STORAGE SECTION.
      *
       01  WS-SQLCODE                       PIC -ZZZ9.
       01  WS-PROGRAM                       PIC X(08)  VALUE 'DPMXHORG'.
       01  WS-CMPNY-ID                      PIC X(08)  VALUE SPACES.
       01  WS-RETURN-ID.
           05  WS-PARAGRAPH-NAME            PIC X(40).
           05  WS-TABLE-NAME                PIC X(40).
       01  WS-SPACES-CMPNYID                PIC X(50) VALUE
           'Company Id is empty'.
       01  WS-INVLD-CMPNYID                 PIC X(50) VALUE
           'Invalid Company ID'.
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
              INCLUDE DPM0101
           END-EXEC

           EXEC SQL
              INCLUDE DPM0201
           END-EXEC

      * INCLUDE FOR VDTM54_DEBUG_CNTRL                                  00024910
           EXEC SQL                                                     00024920
                INCLUDE DTM5401                                         00024930
           END-EXEC.                                                    00024940

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
       01  LS-CMPNY-ID                      PIC X(08).
      *
       PROCEDURE DIVISION USING  OUTSQLCA,
                                 LS-SP-ERROR-AREA,
                                 LS-SP-RC,
                                 LS-CMPNY-ID.

      *----------*
       0000-MAIN.
      *----------*

           PERFORM 1000-INITIALIZE
           PERFORM 2000-VALIDATE-INPUT
           PERFORM 3000-COMPANY-DETAILS
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
           MOVE LS-CMPNY-ID                 TO WS-CMPNY-ID
                                                                        00051700
           EXEC SQL                                                     00051800
                SELECT ACTVT_DSPLY_IN                                   00051900
                  INTO :D054-ACTVT-DSPLY-IN                             00052010
                FROM   VDTM54_DEBUG_CNTRL                               00052040
                WHERE PRGM_ID = :WS-PROGRAM                             00052050
                WITH UR
           END-EXEC                                                     00052060
                                                                        00052070
           EVALUATE SQLCODE                                             00052091
              WHEN 0                                                    00052092
                  IF D054-ACTVT-DSPLY-IN = 'Y'                          00052094
                     SET DISPLAY-PARAMETERS TO TRUE                     00052095
                     EXEC SQL
                         SET :WS-TS = CURRENT TIMESTAMP
                     END-EXEC
                  END-IF                                                00052099
              WHEN 100                                                  00052092
                  CONTINUE
              WHEN OTHER                                                00052092
                  PERFORM 9000-SQL-ERROR
           END-EVALUATE                                                 00052102

           IF DISPLAY-PARAMETERS
              DISPLAY WS-DASHES
              DISPLAY 'DPMXHORG STARTED AT      :' WS-TS
              DISPLAY WS-DASHES
           END-IF
           .

      *------------------------*
       2000-VALIDATE-INPUT.
      *------------------------*

           MOVE '2000-VALIDATE-INPUT'       TO WS-PARAGRAPH-NAME

           EVALUATE WS-CMPNY-ID
              WHEN SPACES
                 MOVE 'SP50'                TO LS-SP-RC
                 MOVE WS-SPACES-CMPNYID     TO LS-SP-ERROR-AREA
                 PERFORM 9100-DISPLAY-DATA
                 PERFORM 9990-GOBACK
              WHEN OTHER
                 CONTINUE
            END-EVALUATE
           .

      *------------------------*
       3000-COMPANY-DETAILS.
      *------------------------*

           MOVE '3000-COMPANY-DETAILS'       TO WS-PARAGRAPH-NAME

           EXEC SQL
              DECLARE DPMXHORG_CSR CURSOR WITH HOLD WITH RETURN FOR
     1         SELECT DPM01.CMPNY_NM,
     2                DPM01.CMPNY_PRMRY_CNTCT_NM,
     3                DPM01.CMPNY_SCNDY_CNTCT_NM,
     4                DPM01.CMPNY_PRMRY_PHONE_NB,
     5                DPM01.CMPNY_SCNDY_PHONE_NB,
     6                DPM01.CMPNY_PRMRY_EMAIL_ID,
     7                DPM01.CMPNY_SCNDY_EMAIL_ID
                  FROM D0005  DPM01
                      WHERE DPM01.CMPNY_ID = :WS-CMPNY-ID
               WITH UR
           END-EXEC
      *
           EXEC SQL
               OPEN DPMXHORG_CSR
           END-EXEC

           EVALUATE SQLCODE
              WHEN 0
                 MOVE 'SP00'                TO LS-SP-RC
              WHEN +100
                 MOVE WS-INVLD-CMPNYID      TO LS-SP-ERROR-AREA
                 MOVE 'SP50'                TO LS-SP-RC
                 PERFORM 9100-DISPLAY-DATA
                 PERFORM 9990-GOBACK
              WHEN OTHER
                 MOVE 'DPMXHORG_CSR'        TO WS-TABLE-NAME
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
           DISPLAY 'PARAGRAPH-NAME           :' WS-PARAGRAPH-NAME
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
           DISPLAY 'COMPANY  ID              :' WS-CMPNY-ID
           DISPLAY WS-DASHES

           IF LS-SP-RC = 'SP99'
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
           END-IF

           .

      *------------------------*
       9990-GOBACK.
      *------------------------*

           PERFORM 9999-FORMAT-SQLCA
           IF DISPLAY-PARAMETERS
              DISPLAY WS-DASHES
              DISPLAY 'OUTSQLCA FOR DPMXHORG    :' OUTSQLCA
              DISPLAY WS-DASHES
              EXEC SQL
                 SET :WS-TS = CURRENT TIMESTAMP
              END-EXEC
              DISPLAY 'DPMXHORG ENDED AT        :' WS-TS
              DISPLAY WS-DASHES
           END-IF
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