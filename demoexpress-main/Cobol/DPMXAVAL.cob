       IDENTIFICATION DIVISION.
       PROGRAM-ID.   DPMXAVAL.
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
      *    ENTRY DPMXAVAL
      *    NAME  DPMXAVAL(R)
      *
      ******************************************************************
      **         P R O G R A M   D O C U M E N T A T I O N            **
      ******************************************************************
      *                                                                *
      * SYSTEM:    MCA XPRESS APPLICATION                              *
      * PROGRAM:   DPMXAVAL                                            *
      *                                                                *
      * This stored procedure is used to retrieve all alert messages   *
      * for Admin and other user                                       *
      *                                                                *
      ******************************************************************
      *                                                                *
      * TABLES:                                                        *
      * -------                                                        *
      *                                                                *
      * D0002   - MCA ALERT INFORMATION TABLE              *
      * VDTM54_DEBUG_CNTRL  - DEBUG CONTROL TABLE                      *
      *                                                                *
      *----------------------------------------------------------------*
      *                                                                *
      * INCLUDES:                                                      *
      * ---------                                                      *
      *                                                                *
      * SQLCA               - DB2 COMMAREA                             *
      * DPM0501             - MCA ALERT INFORMATION TABLE              *
      * DTM5401             - DEBUG CONTROL TABLE                      *
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
      * 09/19/2007        000       COGNIZANT                          *
      *                             INITIAL IMPLEMENTATION FOR         *
      *                             MCA XPRESS.                        *
      *                                                                *
      ******************************************************************
      *                                                                *
       ENVIRONMENT DIVISION.
       DATA DIVISION.
       WORKING-STORAGE SECTION.
       01  WS-SQLCODE                       PIC -ZZZ9.
       01  WS-PROGRAM                       PIC X(08) VALUE 'DPMXAVAL'.
       01  WS-INVLD-USERTYPE                PIC X(50) VALUE
           'Invalid User Type passed'.
       01  WS-DASHES                        PIC X(40) VALUE ALL '='.
       01  WS-TS                            PIC X(26).
       01  WS-DISPLAY-SWITCH                PIC X(01)  VALUE 'N'.
           88 DISPLAY-PARAMETERS                       VALUE 'Y'.
           88 HIDE-PARAMETERS                          VALUE 'N'.
       01  WS-USER-TYPE                     PIC X(01) VALUE ' '.
           88  WS-ADMIN-ENTRY               VALUE 'A'.
           88  WS-USER-ENTRY                VALUE 'U'.
       01  WS-USER-VALID                    PIC X(01) VALUE 'N'.
           88  WS-VALID-ENTRY               VALUE 'Y'.
           88  WS-INVALID-ENTRY             VALUE 'N'.
       01  WS-ERROR-AREA.
           05  WS-PARAGRAPH-NAME            PIC X(40).
           05  WS-TABLE-NAME                PIC X(40).
      *
      **SQL COMMUNICATIONS AREA PASSED BACK IN OUTSQLCA
      *
           EXEC SQL
              INCLUDE SQLCA
           END-EXEC
      *
           EXEC SQL
              INCLUDE DTM0501
           END-EXEC
      *
      *                                                                 00024910
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
       01  LS-USER-TYPE                     PIC X(01).
      *
       PROCEDURE DIVISION USING  OUTSQLCA,
                                 LS-SP-ERROR-AREA,
                                 LS-SP-RC,
                                 LS-USER-TYPE.

      *----------*
       0000-MAIN.
      *----------*

           PERFORM 1000-INITIALIZE
           PERFORM 2000-VALIDATE-INPUT
           PERFORM 3000-LIST-ALERTS
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
      * CONVERT ALL THE INPUT INTO UPPER-CASE
           MOVE FUNCTION UPPER-CASE(LS-USER-TYPE)
                                            TO LS-USER-TYPE
           MOVE LS-USER-TYPE                TO WS-USER-TYPE

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
              DISPLAY 'DPMXAVAL STARTED AT      :' WS-TS
              DISPLAY WS-DASHES
           END-IF

           .

      *------------------------*
       2000-VALIDATE-INPUT.
      *------------------------*

           MOVE '2000-VALIDATE-INPUT'       TO WS-PARAGRAPH-NAME

           EVALUATE TRUE
              WHEN WS-ADMIN-ENTRY
              WHEN WS-USER-ENTRY
                 SET WS-VALID-ENTRY         TO TRUE
           END-EVALUATE
      *
           IF WS-INVALID-ENTRY
              MOVE WS-INVLD-USERTYPE        TO LS-SP-ERROR-AREA
              MOVE 'SP50'                   TO LS-SP-RC
              PERFORM 9100-DISPLAY-DATA
              PERFORM 9990-GOBACK
           ELSE
              CONTINUE
           END-IF
           .

      *------------------------*
       3000-LIST-ALERTS.
      *------------------------*

           MOVE '3000-LIST-ALERTS'          TO WS-PARAGRAPH-NAME
           EVALUATE TRUE
              WHEN WS-ADMIN-ENTRY
              IF DISPLAY-PARAMETERS
                 DISPLAY 'DPMXAVAL_CSR1 CURSOR'
              END-IF

              EXEC SQL
                 DECLARE DPMXAVAL_CSR1 CURSOR WITH HOLD WITH RETURN FOR
  1                 SELECT DPM05.MCA_ALERT_ID AS ALERT_ID,
  2                       DPM05.ALERT_INFO_SUB_DS AS ALERT_SUBJECT,
  3                       DPM05.ROW_UPDT_TS  AS ALERT_UPDATED_TIMESTAMP,
  4                       DPM03.CMPNY_USER_NM AS ALERT_UPDATED_NAME,
  5                       DPM05.ROW_UPDT_USER_ID AS ALERT_UPDATED_ID
                      FROM D0002 DPM05,
								DPM03
                  WHERE DPM03.CMPNY_USER_ID = DPM05.ROW_UPDT_USER_ID
                        ORDER BY DPM05.ROW_UPDT_TS DESC
                        FETCH FIRST 100 ROWS ONLY
                        WITH UR
              END-EXEC
      *
              EXEC SQL
                 OPEN DPMXAVAL_CSR1
              END-EXEC

              WHEN WS-USER-ENTRY
              IF DISPLAY-PARAMETERS
                 DISPLAY 'DPMXAVAL_CSR2 CURSOR'
              END-IF
              EXEC SQL
                 DECLARE DPMXAVAL_CSR2 CURSOR WITH HOLD WITH RETURN FOR
   1                SELECT DPM05.MCA_ALERT_ID AS ALERT_ID,
   2                      DPM05.ALERT_INFO_SUB_DS AS ALERT_SUBJECT,
   3                      DPM05.ROW_UPDT_TS  AS ALERT_UPDATED_TIMESTAMP,
   4                      DPM03.CMPNY_USER_NM AS ALERT_UPDATED-NAME,
   5                      DPM05.ROW_UPDT_USER_ID AS ALERT_UPDATED_ID
                      FROM D0002 DPM05,
                           D0003 DPM03
                     WHERE DPM03.CMPNY_USER_ID = DPM05.ROW_UPDT_USER_ID
                     ORDER BY DPM05.ROW_UPDT_TS DESC
                     FETCH FIRST 5 ROWS ONLY
                      WITH UR
              END-EXEC
      *
              EXEC SQL
                 OPEN DPMXAVAL_CSR2
              END-EXEC

           END-EVALUATE

           EVALUATE SQLCODE
              WHEN 0
                 MOVE 'SP00'                TO LS-SP-RC

              WHEN OTHER
                 MOVE 'DPMXAVAL_CSR'        TO WS-TABLE-NAME
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
           DISPLAY 'SQLCODE                  :' WS-SQLCODE
           PERFORM 9999-FORMAT-SQLCA
           PERFORM 9990-GOBACK
           .

      *------------------------*
       9100-DISPLAY-DATA.
      *------------------------*

           IF DISPLAY-PARAMETERS
              DISPLAY WS-DASHES
              DISPLAY 'PROGRAM-NAME             :' WS-PROGRAM
              DISPLAY 'PARAGRAPH-NAME           :' WS-PARAGRAPH-NAME
              DISPLAY 'CURSOR-NAME              :' WS-TABLE-NAME
              DISPLAY 'SP-ERROR-AREA            :' LS-SP-ERROR-AREA
              DISPLAY 'SP-RC                    :' LS-SP-RC
              DISPLAY 'USER TYPE                :' WS-USER-TYPE
              DISPLAY WS-DASHES
           END-IF

           .

      *------------------------*
       9990-GOBACK.
      *------------------------*

           PERFORM 9999-FORMAT-SQLCA
           IF DISPLAY-PARAMETERS
              EXEC SQL
                  SET :WS-TS = CURRENT TIMESTAMP
              END-EXEC
              DISPLAY WS-DASHES
              DISPLAY 'OUTSQLCA FOR DPMXAVAL    :' OUTSQLCA
              DISPLAY WS-DASHES
              DISPLAY 'DPMXAVAL ENDED AT        :' WS-TS
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