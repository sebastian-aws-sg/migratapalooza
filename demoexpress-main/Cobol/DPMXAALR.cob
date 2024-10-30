       IDENTIFICATION DIVISION.
       PROGRAM-ID.   DPMXAALR.
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
      *    ENTRY DTMXAALR
      *    NAME  DTMXAALR(R)
      *
      ******************************************************************
      **         P R O G R A M   D O C U M E N T A T I O N            **
      ******************************************************************
      *                                                                *
      * SYSTEM:    MCA XPRESS APPLICATION                              *
      * PROGRAM:   DTMXAALR                                            *
      *                                                                *
      * This stored procedure is used to create an Alert Message       *
      *                                                                *
      ******************************************************************
      *                                                                *
      * TABLES:                                                        *
      * -------                                                        *
      *                                                                *
      * D0002   - MCA ALERT INFORMATION TABLE              *
      * VDPM13_MCA_TEXT     - MCA TEXT TABLE                           *
      * D0003   - MCA ORG USER TABLE                       *
      * VDTM54_DEBUG_CNTRL  - DEBUG CONTROL TABLE                      *
      *                                                                *
      *----------------------------------------------------------------*
      *                                                                *
      * INCLUDES:                                                      *
      * ---------                                                      *
      *                                                                *
      * SQLCA               - DB2 COMMAREA                             *
      * DPM0501             - MCA ALERT INFORMATION TABLE              *
      * DPM1301             - MCA TEXT TABLE                           *
      * DPM0301             - MCA ORG USER TABLE                       *
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
      * 09/14/2007        000       COGNIZANT                          *
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
       01  WS-PROGRAM                       PIC X(08) VALUE 'DPMXAALR'.
       01  WS-INVLD-USERID                  PIC X(50) VALUE
           'Invalid User ID passed'.
       01  WS-INVLD-SUBJCT                  PIC X(50) VALUE
           'Message Sub or Detail cannot be Blank'.
       01  WS-DASHES                        PIC X(40) VALUE ALL '='.
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
      *
      **PICTURE CLAUSE FOR OUTSQLCA - PIC X(179) - FOR LINKAGE SECTION
      *
       COPY  DB2000IB.
      *
       01  LS-SP-ERROR-AREA                 PIC X(80).
       01  LS-SP-RC                         PIC X(04).
       01  LS-ALERT-ID                      PIC S9(9) USAGE COMP.
       01  LS-USER-ID                       PIC X(10).
       01  LS-ALERT-SUB                     PIC X(150).
       01  LS-ALERT-MSG.
           49 LS-ALERT-MSG-LEN              PIC S9(4) USAGE COMP.
           49 LS-ALERT-MSG-DT               PIC X(32000).

      *
       PROCEDURE DIVISION USING  OUTSQLCA,
                                 LS-SP-ERROR-AREA,
                                 LS-SP-RC,
                                 LS-ALERT-ID,
                                 LS-ALERT-SUB,
                                 LS-ALERT-MSG,
                                 LS-USER-ID.

      *----------*
       0000-MAIN.
      *----------*

           PERFORM 1000-INITIALIZE
           PERFORM 2000-VALIDATE-INPUT
           PERFORM 3000-CREATE-ALERT
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
           MOVE 0                           TO LS-ALERT-ID
      * CONVERT ALL THE INPUT INTO UPPER-CASE
           MOVE FUNCTION UPPER-CASE(LS-USER-ID)
                                            TO LS-USER-ID

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
              DISPLAY 'DPMXAALR STARTED AT      :' WS-TS
              DISPLAY WS-DASHES
           END-IF

           .

      *------------------------*
       2000-VALIDATE-INPUT.
      *------------------------*

           MOVE '2000-VALIDATE-INPUT'       TO WS-PARAGRAPH-NAME

           EXEC SQL
              SELECT  CMPNY_USER_ID
                INTO :LS-USER-ID
                FROM D0003
               WHERE  CMPNY_USER_ID = :LS-USER-ID
               FETCH  FIRST 1 ROW ONLY
                WITH  UR
           END-EXEC

           EVALUATE SQLCODE
              WHEN 0
                 CONTINUE

              WHEN 100
                 INITIALIZE SQLCODE
                 MOVE WS-INVLD-USERID       TO LS-SP-ERROR-AREA
                 MOVE 'SP50'                TO LS-SP-RC
                 PERFORM 9100-DISPLAY-DATA
                 PERFORM 9990-GOBACK

              WHEN OTHER
                 MOVE 'D0003'   TO WS-TABLE-NAME
                 PERFORM 9000-SQL-ERROR
           END-EVALUATE

           EVALUATE TRUE
              WHEN LS-ALERT-SUB = SPACES OR LOW-VALUES
              WHEN LS-ALERT-MSG-LEN = ZERO
                 MOVE WS-INVLD-SUBJCT       TO LS-SP-ERROR-AREA
                 MOVE 'SP01'                TO LS-SP-RC
                 PERFORM 9100-DISPLAY-DATA
                 PERFORM 9990-GOBACK
           END-EVALUATE
           .

      *------------------------*
       3000-CREATE-ALERT.
      *------------------------*

           MOVE '3000-CREATE-ALERT'         TO WS-PARAGRAPH-NAME

           EXEC SQL
                SELECT NEXT VALUE FOR DPM.SQDPM111
                  INTO :D013-MCA-VALUE-ID
                  FROM SYSIBM.SYSDUMMY1
                  WITH UR
           END-EXEC

           EVALUATE SQLCODE
              WHEN 0
                 MOVE 'SP00'                TO LS-SP-RC

              WHEN OTHER
                 MOVE 'VDPM13_MCA_TEXT'     TO WS-TABLE-NAME
                 PERFORM 9000-SQL-ERROR
           END-EVALUATE

           MOVE SPACES                      TO D013-MCA-TEXT-DS-TEXT
           MOVE LS-ALERT-MSG-DT             TO D013-MCA-TEXT-DS-TEXT
           MOVE LENGTH OF LS-ALERT-MSG-DT   TO D013-MCA-TEXT-DS-LEN

           EXEC SQL                                                     000933
              INSERT INTO VDPM13_MCA_TEXT                               000934
               VALUES (:D013-MCA-VALUE-ID,                              000934
                       :D013-MCA-TEXT-DS)                               000935
           END-EXEC                                                     000944

           EVALUATE SQLCODE
              WHEN 0
                 MOVE 'SP00'                TO LS-SP-RC

              WHEN OTHER
                 MOVE 'VDPM13_MCA_TEXT'     TO WS-TABLE-NAME
                 PERFORM 9000-SQL-ERROR
           END-EVALUATE

           EXEC SQL
                INSERT INTO D0002
                   VALUES (:D013-MCA-VALUE-ID
                          ,:LS-ALERT-SUB
                          ,CURRENT TIMESTAMP
                          ,:LS-USER-ID)

           END-EXEC

           EVALUATE SQLCODE
              WHEN 0
                 MOVE 'SP00'                TO LS-SP-RC
                 MOVE D013-MCA-VALUE-ID     TO LS-ALERT-ID

              WHEN OTHER
                 MOVE 'D0002'   TO WS-TABLE-NAME
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

           EXEC SQL
                ROLLBACK
           END-EXEC

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
              DISPLAY 'TABLE NAME               :' WS-TABLE-NAME
              DISPLAY 'SP-ERROR-AREA            :' LS-SP-ERROR-AREA
              DISPLAY 'SP-RC                    :' LS-SP-RC
              DISPLAY 'USER ID                  :' LS-USER-ID
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
              DISPLAY 'OUTSQLCA FOR DPMXAALR    :' OUTSQLCA
              DISPLAY WS-DASHES
              DISPLAY 'DPMXAALR ENDED AT        :' WS-TS
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