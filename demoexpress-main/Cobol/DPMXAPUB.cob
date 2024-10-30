       IDENTIFICATION DIVISION.
       PROGRAM-ID.   DPMXAPUB.
       AUTHOR.       COGNIZANT.
       DATE-WRITTEN. SEPTEMBER 2007.
      *                                                                *
      ******************************************************************
      *                                                                *
      *   THIS IS AN UNPUBLISHED PROGRAM OWNED BY ISCC                 *
      *   IN WHICH A COPYRIGHT SUBSISTS AS OF OCTOBER 2003.            *
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
      *    ENTRY DPMXAPUB
      *    NAME  DPMXAPUB(R)
      *
      ******************************************************************
      **         P R O G R A M   D O C U M E N T A T I O N            **
      ******************************************************************
      *                                                                *
      * SYSTEM:    MCA XPRESS APPLICATION                              *
      * PROGRAM:   DPMXAPUB                                            *
      *                                                                *
      * This stored procedure is used to Publish an ISDA Template      *
      * when publish button is clicked from MCA Template tab           *
      *                                                                *
      ******************************************************************
      *                                                                *
      * TABLES:                                                        *
      * -------                                                        *
      *                                                                *
      * D0006      - MCA TEMPLATE TABLE                     *
      * VDPM14AMCA_TMPLT      - MCA TEMPLATE HISTORY TABLE             *
      * D0003     - MCA ORG USER TABLE                     *
      * VDTM54_DEBUG_CNTRL    - DEBUG CONTROL TABLE                    *
      *                                                                *
      *----------------------------------------------------------------*
      *                                                                *
      * INCLUDES:                                                      *
      * ---------                                                      *
      *                                                                *
      * SQLCA                 - DB2 COMMAREA                           *
      * DPM1401               - MCA TEMPLATE TABLE                     *
      * DPM0301               - MCA ORG USER TABLE                     *
      * DTM5401               - DEBUG CONTROL TABLE                    *
      *----------------------------------------------------------------*
      *                                                                *
      * COPYBOOK:                                                      *
      * ---------                                                      *
      *                                                                *
      * DB2000IA                                                       *
      * DB20001B                                                       *
      * DB20001C                                                       *
      *                                                                *
      *----------------------------------------------------------------*
      *                                                                *
      *              M A I N T E N A N C E   H I S T O R Y             *
      *                                                                *
      * DATE CHANGED    VERSION     PROGRAMMER                         *
      * ------------    -------     --------------------               *
      *                                                                *
      * 09/04/2007        000       COGNIZANT                          *
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
       01  WS-PROGRAM                       PIC X(08) VALUE 'DPMXAPUB'.
       01  WS-TIMESTAMP                     PIC X(26) VALUE SPACES.
       01  WS-USER-ID                       PIC X(10).
       01  WS-PUBLISH-DATE                  PIC X(10).
       01  WS-TEMPLATE-STATUS               PIC X(01).
       01  WS-CUR-STAT                      PIC X(01).
       01  WS-DUMMY-ID                      PIC X(01).
       01  WS-TEMPLATE-ID                   PIC S9(9) USAGE COMP.
       01  WS-CONVERT-TEMPLATE-ID           PIC 9(18).
       01  FILLER REDEFINES WS-CONVERT-TEMPLATE-ID.
           05 WS-NUMERIC-TEMPLATE-ID        PIC 9(18).
       01  WS-SELECT-DATE                   PIC X(08) VALUE SPACES.
       01  FILLER REDEFINES WS-SELECT-DATE.
           05 WS-YY                         PIC 9(04).
           05 WS-YY-RED REDEFINES WS-YY     PIC X(04).
           05 WS-MM                         PIC 9(02).
              88 VALID-MONTH                VALUES 1 THRU 12.
              88 FEBRUARY                   VALUE  2.
              88 30-DAY-MONTH               VALUES 4 6 9 11.
              88 31-DAY-MONTH               VALUES 1 3 5 7 8 10 12.
           05 WS-DD                         PIC 9(02).
              88 VALID-DAY                  VALUES 1 THRU 31.
       01  WS-LEAP-YEAR                     PIC 9(04) VALUE 0.
       01  WS-INVLD-TEMPID                  PIC X(50) VALUE
           'Invalid Template ID passed'.
       01  WS-INVLD-REQID                  PIC X(50) VALUE
           'Approver or Reject ID Same As Requester ID.'.
       01  WS-INVLD-STATUS                  PIC X(50) VALUE
           'Invalid Approve Status passed'.
       01  WS-INVLD-STATUS1                 PIC X(50) VALUE
           'Approve Status conflict with Current Status.'.
       01  WS-INVLD-DATE                    PIC X(50) VALUE
           'Invalid Date passed'.
       01  WS-INVLD-USER                    PIC X(50) VALUE
           'Invalid User ID passed'.
       01  WS-INVLD-IND                     PIC X(50) VALUE
           'Invalid Test/Prod indicator passed'.
       01  WS-DASHES                        PIC X(40) VALUE ALL '='.
       01  WS-TS                            PIC X(26).
       01  WS-DISPLAY-SWITCH                PIC X(01)  VALUE 'N'.
           88 DISPLAY-PARAMETERS                       VALUE 'Y'.
           88 HIDE-PARAMETERS                          VALUE 'N'.
       01  WS-ERROR-AREA.
           05  WS-PARAGRAPH-NAME            PIC X(40).
           05  WS-TABLE-NAME                PIC X(40).
      *
      ** Working storage switches
      *
       01 WS-SWITCHES.
          05 WS-LEAP-SW                     PIC X(1)  VALUE 'N'.
             88 LEAP-YEAR                             VALUE 'Y'.
             88 NO-LEAP                               VALUE 'N'.

      *    INCLUDES

           EXEC SQL
              INCLUDE SQLCA
           END-EXEC
      *
           EXEC SQL
              INCLUDE DPM1401
           END-EXEC
      *
           EXEC SQL
              INCLUDE DPM0301
           END-EXEC
      *                                                                 00024910
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
      **PICTURE CLAUSE FOR OUTSQLCA - PIC X(179) - FOR LINKAGE SECTION
      *
       COPY  DB2000IB.
      *
       01  LS-SP-ERROR-AREA                 PIC X(80).
       01  LS-SP-RC                         PIC X(04).
       01  LS-TEMPLATE-ID                   PIC S9(9) USAGE COMP.
       01  LS-USER-ID                       PIC X(10).
       01  LS-TEMPLATE-STATUS               PIC X(01).
       01  LS-PUBLISH-DATE                  PIC X(10).
      *
       PROCEDURE DIVISION USING  OUTSQLCA,
                                 LS-SP-ERROR-AREA,
                                 LS-SP-RC,
                                 LS-TEMPLATE-ID,
                                 LS-USER-ID,
                                 LS-TEMPLATE-STATUS,
                                 LS-PUBLISH-DATE.

      *----------*
       0000-MAIN.
      *----------*

           PERFORM 1000-INITIALIZE
           PERFORM 2000-VALIDATE-INPUT
           PERFORM 3000-UPDATE-STATUS
           IF DISPLAY-PARAMETERS
              PERFORM 9100-DISPLAY-DATA
           END-IF
           PERFORM 9990-GOBACK
               .

      *---------------*
       1000-INITIALIZE.
      *---------------*

           MOVE '1000-INITIALIZE'           TO WS-PARAGRAPH-NAME
           MOVE 'SP00'                      TO LS-SP-RC
           DISPLAY WS-DASHES
           DISPLAY 'PROGRAM-NAME             :' WS-PROGRAM
           DISPLAY 'PARAGRAPH-NAME           :' WS-PARAGRAPH-NAME
           DISPLAY WS-DASHES
           MOVE SPACES                      TO OUTSQLCA
                                               LS-SP-ERROR-AREA
      * Convert Input Values Into Upper Case

           MOVE FUNCTION UPPER-CASE(LS-TEMPLATE-STATUS)
                                            TO WS-TEMPLATE-STATUS
           MOVE FUNCTION UPPER-CASE(LS-USER-ID)
                                            TO WS-USER-ID
           MOVE LS-TEMPLATE-ID              TO WS-CONVERT-TEMPLATE-ID
                                               WS-TEMPLATE-ID
           MOVE LS-PUBLISH-DATE             TO WS-PUBLISH-DATE

           EXEC SQL                                                     00051800
                SELECT ACTVT_DSPLY_IN                                   00051900
                  INTO :D054-ACTVT-DSPLY-IN                             00052010
                FROM VDTM54_DEBUG_CNTRL                                 00052040
                WHERE PRGM_ID = :WS-PROGRAM                             00052050
                WITH UR
           END-EXEC                                                     00052060
                                                                        00052070
           MOVE SQLCODE                     TO WS-SQLCODE               00052080
                                                                        00052090
           EVALUATE SQLCODE                                             00052091
              WHEN 0                                                    00052092
                  IF D054-ACTVT-DSPLY-IN = 'Y'                          00052094
                     SET DISPLAY-PARAMETERS TO TRUE                     00052095
                  END-IF                                                00052099

              WHEN 100                                                  00052092
                  CONTINUE
              WHEN OTHER                                                00052092
                  PERFORM 9000-SQL-ERROR
           END-EVALUATE                                                 00052102

           IF DISPLAY-PARAMETERS
              EXEC SQL
                  SET :WS-TS = CURRENT TIMESTAMP
              END-EXEC
              DISPLAY WS-DASHES
              DISPLAY 'DPMXAPUB STARTED AT      :' WS-TS
              DISPLAY WS-DASHES
           END-IF

               .

      *-------------------*
       2000-VALIDATE-INPUT.
      *-------------------*

           MOVE '2000-VALIDATE-INPUT'       TO WS-PARAGRAPH-NAME

           EXEC SQL
              SELECT  MCA_TMPLT_ID
                 INTO :WS-TEMPLATE-ID
              FROM D0006
                 WHERE MCA_TMPLT_ID = :WS-TEMPLATE-ID
                 WITH UR
           END-EXEC

           EVALUATE SQLCODE
              WHEN 0
                 CONTINUE

              WHEN 100
                 INITIALIZE SQLCODE
                 MOVE WS-INVLD-TEMPID       TO LS-SP-ERROR-AREA
                 MOVE 'SP50'                TO LS-SP-RC
                 PERFORM 9100-DISPLAY-DATA
                 PERFORM 9990-GOBACK

              WHEN OTHER
                 MOVE 'D0006'    TO WS-TABLE-NAME
                 PERFORM 9000-SQL-ERROR
           END-EVALUATE
           DISPLAY WS-DASHES
           DISPLAY 'PROGRAM-NAME             :' WS-PROGRAM
           DISPLAY 'PARAGRAPH-NAME           :' WS-PARAGRAPH-NAME
           DISPLAY WS-DASHES
           PERFORM 2010-VALIDATE-USER
           PERFORM 2020-VALIDATE-STATUS
               .

      *------------------*
       2010-VALIDATE-USER.
      *------------------*

           MOVE '2010-VALIDATE-USER'       TO WS-PARAGRAPH-NAME
           EXEC SQL
              SELECT DPM03.CMPNY_USER_ID
                     INTO :WS-USER-ID
                 FROM D0003 DPM03
                     WHERE DPM03.CMPNY_USER_ID = :WS-USER-ID
                     FETCH FIRST 1 ROW ONLY
                     WITH UR
           END-EXEC

           EVALUATE SQLCODE
              WHEN 0
                 DISPLAY WS-DASHES
                 DISPLAY 'PROGRAM-NAME             :' WS-PROGRAM
                 DISPLAY 'PARAGRAPH-NAME           :' WS-PARAGRAPH-NAME
                 DISPLAY WS-DASHES
              WHEN 100
                 INITIALIZE SQLCODE
                 MOVE WS-INVLD-USER         TO LS-SP-ERROR-AREA
                 MOVE 'SP50'                TO LS-SP-RC
                 PERFORM 9100-DISPLAY-DATA
                 PERFORM 9990-GOBACK
              WHEN OTHER
                 MOVE 'VDPM03_CMPNY_USE'   TO WS-TABLE-NAME
                 PERFORM 9000-SQL-ERROR
           END-EVALUATE
               .

      *--------------------*
       2020-VALIDATE-STATUS.
      *--------------------*

           MOVE '2020-VALIDATE-STATUS'     TO WS-PARAGRAPH-NAME
           EVALUATE WS-TEMPLATE-STATUS
              WHEN 'W'
                 MOVE 'I'                   TO WS-CUR-STAT
                 PERFORM 2025-STATUS-CHECK

              WHEN 'P'
                 MOVE 'W'                   TO WS-CUR-STAT
                 PERFORM 2025-STATUS-CHECK

              WHEN 'I'
                 MOVE 'W'                   TO WS-CUR-STAT
                 PERFORM 2025-STATUS-CHECK

              WHEN OTHER
                 MOVE WS-INVLD-STATUS       TO LS-SP-ERROR-AREA
                 MOVE 'SP50'                TO LS-SP-RC
                 PERFORM 9100-DISPLAY-DATA
                 PERFORM 9990-GOBACK
           END-EVALUATE
           DISPLAY WS-DASHES
           DISPLAY 'PROGRAM-NAME             :' WS-PROGRAM
           DISPLAY 'PARAGRAPH-NAME           :' WS-PARAGRAPH-NAME
           DISPLAY WS-DASHES
               .

      *------------------*
       2025-STATUS-CHECK.
      *------------------*

           MOVE '2025-STATUS-CHECK'       TO WS-PARAGRAPH-NAME

           EXEC SQL
              SELECT 'Y' INTO :WS-DUMMY-ID
              FROM D0006
                   WHERE MCA_TMPLT_ID = :WS-TEMPLATE-ID
                   AND   MCA_STAT_IN = :WS-CUR-STAT
           END-EXEC

           EVALUATE SQLCODE
              WHEN 0
                 CONTINUE
              WHEN +100
                 INITIALIZE SQLCODE
                 MOVE WS-INVLD-STATUS1      TO LS-SP-ERROR-AREA
                 MOVE 'SP02'                TO LS-SP-RC
                 PERFORM 9100-DISPLAY-DATA
                 PERFORM 9990-GOBACK

              WHEN OTHER
                 MOVE 'D0006'    TO WS-TABLE-NAME
                 PERFORM 9000-SQL-ERROR
           END-EVALUATE
              .
      *------------------*
       3000-UPDATE-STATUS.
      *------------------*

           MOVE '3000-UPDATE-STATUS'        TO WS-PARAGRAPH-NAME
           EVALUATE WS-TEMPLATE-STATUS
              WHEN 'W'
                 EXEC SQL
                      UPDATE D0006
                         SET MCA_STAT_IN = :WS-TEMPLATE-STATUS,
                             MCA_TMPLT_RQSTR_ID = :WS-USER-ID,
                             ROW_UPDT_USER_ID  = :WS-USER-ID,
                             ROW_UPDT_TS = CURRENT TIMESTAMP
                          WHERE MCA_TMPLT_ID = :WS-TEMPLATE-ID

                 END-EXEC

              WHEN 'P'
                 EXEC SQL
                      UPDATE D0006
                         SET MCA_STAT_IN = :WS-TEMPLATE-STATUS,
                             MCA_PBLTN_DT = :WS-PUBLISH-DATE,
                             MCA_TMPLT_APRVR_ID = :WS-USER-ID,
                             ROW_UPDT_USER_ID  = :WS-USER-ID,
                             ROW_UPDT_TS = CURRENT TIMESTAMP
                          WHERE MCA_TMPLT_ID = :WS-TEMPLATE-ID AND
                                MCA_TMPLT_RQSTR_ID <> :wS-USER-ID

                 END-EXEC
                 EVALUATE SQLCODE
                    WHEN 0
                       CONTINUE
                    WHEN 100
                       INITIALIZE SQLCODE
                       MOVE WS-INVLD-REQID  TO LS-SP-ERROR-AREA
                       MOVE 'SP03'          TO LS-SP-RC
                       PERFORM 9100-DISPLAY-DATA
                       PERFORM 9990-GOBACK
                 END-EVALUATE
      *
              WHEN 'I'
                 EXEC SQL
                      UPDATE D0006
                         SET MCA_STAT_IN = :WS-TEMPLATE-STATUS,
                             ROW_UPDT_USER_ID  = :WS-USER-ID,
                             MCA_TMPLT_RQSTR_ID = ' ',
                             MCA_TMPLT_APRVR_ID = ' ',
                             ROW_UPDT_TS = CURRENT TIMESTAMP
                          WHERE MCA_TMPLT_ID = :WS-TEMPLATE-ID AND
                             MCA_TMPLT_RQSTR_ID <> :wS-USER-ID
                 END-EXEC
                 EVALUATE SQLCODE
                    WHEN 0
                       CONTINUE
                    WHEN 100
                       INITIALIZE SQLCODE
                       MOVE WS-INVLD-REQID  TO LS-SP-ERROR-AREA
                       MOVE 'SP03'          TO LS-SP-RC
                       PERFORM 9100-DISPLAY-DATA
                       PERFORM 9990-GOBACK
                 END-EVALUATE
           END-EVALUATE
      *
           EVALUATE SQLCODE
              WHEN 0
                 MOVE 'SP00'                TO LS-SP-RC
                 DISPLAY WS-DASHES
                 DISPLAY 'PROGRAM-NAME             :' WS-PROGRAM
                 DISPLAY 'PARAGRAPH-NAME           :' WS-PARAGRAPH-NAME
                 DISPLAY WS-DASHES

              WHEN OTHER
                 MOVE 'D0006'    TO WS-TABLE-NAME
                 PERFORM 9000-SQL-ERROR
           END-EVALUATE
               .

      *--------------*
       9000-SQL-ERROR.
      *--------------*

           MOVE 'Database error has occurred. Please contact DTCC.'
                                            TO LS-SP-ERROR-AREA
           MOVE 'SP99'                      TO LS-SP-RC
           PERFORM 9100-DISPLAY-DATA
           MOVE SQLCODE                     TO WS-SQLCODE
           DISPLAY 'SQLCODE                  :' WS-SQLCODE

           EXEC SQL
                ROLLBACK
           END-EXEC

           PERFORM 9999-FORMAT-SQLCA
           PERFORM 9990-GOBACK
               .

      *-----------------*
       9100-DISPLAY-DATA.
      *-----------------*

           IF DISPLAY-PARAMETERS
              DISPLAY WS-DASHES
              DISPLAY 'PROGRAM-NAME             :' WS-PROGRAM
              DISPLAY 'PARAGRAPH-NAME           :' WS-PARAGRAPH-NAME
              DISPLAY 'TABLE-NAME               :' WS-TABLE-NAME
              DISPLAY 'SP-ERROR-AREA            :' LS-SP-ERROR-AREA
              DISPLAY 'SP-RC                    :' LS-SP-RC

              DISPLAY 'TEMPLATE ID        :' WS-CONVERT-TEMPLATE-ID
              DISPLAY WS-DASHES
              DISPLAY 'USER ID            :' LS-USER-ID
              DISPLAY WS-DASHES
              DISPLAY 'TEMPLATE STATUS    :' WS-TEMPLATE-STATUS
              DISPLAY WS-DASHES
           END-IF

               .

      *-----------*
       9990-GOBACK.
      *-----------*

           PERFORM 9999-FORMAT-SQLCA
           IF DISPLAY-PARAMETERS
              EXEC SQL
                  SET :WS-TS = CURRENT TIMESTAMP
              END-EXEC
              DISPLAY WS-DASHES
              DISPLAY 'OUTSQLCA FOR DPMXAPUB    :' OUTSQLCA
              DISPLAY WS-DASHES
              DISPLAY 'DPMXAPUB ENDED AT        :' WS-TS
              DISPLAY WS-DASHES
           END-IF
           GOBACK
               .

      *-----------------*
       9999-FORMAT-SQLCA.
      *-----------------*

           PERFORM DB2000I-FORMAT-SQLCA
              THRU DB2000I-FORMAT-SQLCA-EXIT
               .
      *
      **Move Statements To Format The OUTSQLCA Using DB2000IA & DB2000IB
      *
        COPY DB2000IC.
