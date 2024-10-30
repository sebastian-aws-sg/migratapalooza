       IDENTIFICATION DIVISION.
       PROGRAM-ID.   DPMXAADL.
       AUTHOR.       COGNIZANT.
       DATE-WRITTEN. SEPTEMBER 2007.
      *
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
      *    ENTRY DPMXAADL
      *    NAME  DPMXAADL(R)
      *
      ******************************************************************
      **         P R O G R A M   D O C U M E N T A T I O N            **
      ******************************************************************
      *                                                                *
      * SYSTEM:    MCA XPRESS APPLICATION                              *
      * PROGRAM:   DPMXAADL                                            *
      *                                                                *
      * This stored procedure is used to retrieve Alert detail for an  *
      * Alert ID                                                       *
      *                                                                *
      ******************************************************************
      *                                                                *
      * TABLES:                                                        *
      * -------                                                        *
      *                                                                *
      * D0002  - MCA ALERT INFORMATION TABLE               *
      * VDPM13_MCA_TEXT    - MCA TEXT TABLE                            *
      * VDTM54_DEBUG_CNTRL - DEBUG CONTROL TABLE                       *
      *                                                                *
      *----------------------------------------------------------------*
      *                                                                *
      * INCLUDES:                                                      *
      * ---------                                                      *
      *                                                                *
      * SQLCA              - DB2 COMMAREA                              *
      * DPM0501            - MCA ALERT INFORMATION TABLE               *
      * DPM1301            - MCA TEXT TABLE                            *
      * DTM5401            - DEBUG CONTROL TABLE                       *
      *----------------------------------------------------------------*
      *                                                                *
      * COPYBOOK:                                                      *
      * ---------                                                      *
      *                                                                *
      * DB2000IA                                                       *
      * DB20001B                                                       *
      * DB20001C                                                       *
      *----------------------------------------------------------------*
      *                                                                *
      *              M A I N T E N A N C E   H I S T O R Y             *
      *                                                                *
      * DATE CHANGED    VERSION     PROGRAMMER                         *
      * ------------    -------     --------------------               *
      * 09/27/2007        000       COGNIZANT                          *
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
       01  WS-PROGRAM                       PIC X(08) VALUE 'DPMXAADL'.
       01  WS-ALERT-ID                      PIC S9(9) USAGE COMP.
       01  WS-INVLD-ALERTID                 PIC X(50) VALUE
           'Invalid Alert ID passed'.
       01  WS-DASHES                        PIC X(40) VALUE ALL '='.
       01  WS-SUB                           PIC S9(4) COMP.
       01  WS-TS                            PIC X(26).
       01  WS-DISPLAY-SWITCH                PIC X(01)  VALUE 'N'.
           88 DISPLAY-PARAMETERS                       VALUE 'Y'.
           88 HIDE-PARAMETERS                          VALUE 'N'.
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
              INCLUDE DPM0501
           END-EXEC
      *
           EXEC SQL
              INCLUDE DPM1301
           END-EXEC
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
       01  LS-ALERT-ID                      PIC S9(9) USAGE COMP.
      *
       PROCEDURE DIVISION USING  OUTSQLCA,
                                 LS-SP-ERROR-AREA,
                                 LS-SP-RC,
                                 LS-ALERT-ID.
      *----------*
       0000-MAIN.
      *----------*

           PERFORM 1000-INITIALIZE
           PERFORM 2000-VALIDATE-INPUT
           PERFORM 3000-LIST-ALERT-DETAILS
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
           MOVE LS-ALERT-ID                 TO WS-ALERT-ID

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
              DISPLAY 'DPMXAADL STARTED AT      :' WS-TS
              DISPLAY WS-DASHES
           END-IF
           .

      *------------------------*
       2000-VALIDATE-INPUT.
      *------------------------*

           MOVE '2000-VALIDATE-INPUT'       TO WS-PARAGRAPH-NAME
           DISPLAY WS-ALERT-ID

           EXEC SQL
              SELECT  MCA_ALERT_ID
                INTO :WS-ALERT-ID
                FROM D0002
               WHERE  MCA_ALERT_ID = :WS-ALERT-ID
                WITH  UR
           END-EXEC

           EVALUATE SQLCODE
              WHEN 0
                 CONTINUE

              WHEN 100
                 INITIALIZE SQLCODE
                 MOVE WS-INVLD-ALERTID      TO LS-SP-ERROR-AREA
                 MOVE 'SP50'                TO LS-SP-RC
                 PERFORM 9100-DISPLAY-DATA
                 PERFORM 9990-GOBACK

              WHEN OTHER
                 MOVE 'VDPM05_MCA_ALR_INFO' TO WS-TABLE-NAME
                 PERFORM 9000-SQL-ERROR
           END-EVALUATE
           .

      *------------------------*
       3000-LIST-ALERT-DETAILS.
      *------------------------*

           MOVE '3000-LIST-ALERT-DETAILS'   TO WS-PARAGRAPH-NAME
           IF DISPLAY-PARAMETERS
              DISPLAY 'DPMXAADL_CSR CURSOR'
           END-IF

           EXEC SQL
              DECLARE DPMXAADL_CSR CURSOR WITH HOLD WITH RETURN FOR
  1              SELECT DPM05.ALERT_INFO_SUB_DS AS ALERT_SUBJECT,
  2                     DPM13.MCA_TEXT_DS AS ALERT_DETAIL,
  3                     DPM05.ROW_UPDT_USER_ID AS ROW-UPDATED-USER,
  4                     DPM03.CMPNY_USER_NM AS USER-NAME,
  5                     DPM05.ROW_UPDT_TS AS ROW-UPDATED-TIMESTAMP
                   FROM D0002   DPM05,
                        VDPM13_MCA_TEXT     DPM13,
                        D0003   DPM03
                  WHERE DPM05.MCA_ALERT_ID  = :WS-ALERT-ID
                    AND DPM05.MCA_ALERT_ID  = DPM13.MCA_VALUE_ID
                    AND DPM03.CMPNY_USER_ID = DPM05.ROW_UPDT_USER_ID
                   WITH UR
           END-EXEC
      *
           EXEC SQL
              OPEN DPMXAADL_CSR
           END-EXEC

           EVALUATE SQLCODE
              WHEN 0
                 MOVE 'SP00'                TO LS-SP-RC

              WHEN OTHER
                 MOVE 'DPMXAADL_CSR'        TO WS-TABLE-NAME
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
              DISPLAY 'TABLE/CURSOR NAME        :' WS-TABLE-NAME
              DISPLAY 'SP-ERROR-AREA            :' LS-SP-ERROR-AREA
              DISPLAY 'SP-RC                    :' LS-SP-RC
              DISPLAY 'ALERT ID                 :' WS-ALERT-ID
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
              DISPLAY 'OUTSQLCA FOR DPMXAADL    :' OUTSQLCA
              DISPLAY WS-DASHES
              DISPLAY 'DPMXAADL ENDED AT        :' WS-TS
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