       IDENTIFICATION DIVISION.
       PROGRAM-ID.   DPMXMLOK.
       AUTHOR.       COGNIZANT.
       DATE-WRITTEN. OCTOBER 2007.
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
      *    ENTRY DPMXMLOK
      *    NAME  DPMXMLOK(R)
      *
      ******************************************************************
      **         P R O G R A M   D O C U M E N T A T I O N            **
      ******************************************************************
      *                                                                *
      * SYSTEM:    MCA XPRESS APPLICATION                              *
      * PROGRAM:   DPMXMLOK                                            *
      *                                                                *
      * This common batch  procedure is used  lock / unlock / view     *
      * locking information of a template for a particular user        *
      *                                                                *
      ******************************************************************
      *                                                                *
      * TABLES:                                                        *
      * -------                                                        *
      *                                                                *
      * D0006    - MCA TEMPLATE TABLE                       *
      * D0003   - MCA ORG USER TABLE                       *
      * VDPM10_MCA_LOCK     - MCA LOCK TABLE                           *
      * VDTM54_DEBUG_CNTRL  - DEBUG CONTROL TABLE                      *
      *                                                                *
      *----------------------------------------------------------------*
      *                                                                *
      * INCLUDES:                                                      *
      * ---------                                                      *
      * SQLCA                                                          *
      * DPM1401                                                        *
      * DPM0301                                                        *
      * DPM1001                                                        *
      * DTM5401                                                        *
      *----------------------------------------------------------------*
      *              M A I N T E N A N C E   H I S T O R Y             *
      *                                                                *
      * DATE CHANGED    VERSION     PROGRAMMER                         *
      * ------------    -------     --------------------               *
      *                                                                *
      * 09/10/2007        000       COGNIZANT                          *
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
       01  WS-PROGRAM                       PIC X(08) VALUE 'DPMXMLOK'.
       01  WS-TIMESTAMP                     PIC X(26) VALUE SPACES.
       01  WS-TS                            PIC X(26).
       01  WS-USER-ID                       PIC X(10).
       01  WS-TEMPLATE-ID                   PIC S9(09) COMP
                                                VALUE ZEROES.
       01  WS-CMPNY-ID                      PIC X(08).
       01  WS-INVLD-TEMPID                  PIC X(50) VALUE
           'Invalid Template ID passed'.
       01  WS-INVLD-USER                    PIC X(50) VALUE
           'Invalid User ID passed'.
       01  WS-INVLD-IND                     PIC X(50) VALUE
           'Invalid action indicator'.
       01  WS-DUP-LCK                       PIC X(50) VALUE
           'Already locked by another User'.
       01  WS-DUP-UNLCK                     PIC X(50) VALUE
           'Template is not currently locked'.
       01  WS-SPC-ID                        PIC X(50) VALUE
           'Company ID / User ID is Spaces'.
       01  WS-DASHES                        PIC X(40) VALUE ALL '='.
       01  WS-ERROR-AREA.
           05  WS-PARAGRAPH-NAME            PIC X(40).
           05  WS-TABLE-NAME                PIC X(40).
       01  WS-SWITCHES.
             05 WS-ACTION-SW               PIC X(01).
                88  WS-LOCK                VALUE 'L'.
                88  WS-UNLOCK              VALUE 'U'.
                88  WS-VIEW                VALUE 'V'.
       01  WS-DISPLAY-SWITCH               PIC X(01) VALUE 'N'.
           88 DISPLAY-PARAMETERS                     VALUE 'Y'.
           88 HIDE-PARAMETERS                        VALUE 'N'.
      *
      **SQL COMMUNICATIONS AREA PASSED BACK IN OUTSQLCA
      *
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
      *
           EXEC SQL
              INCLUDE DPM1001
           END-EXEC
      * INCLUDE FOR VDTM54_DEBUG_CNTRL                                  00024910
           EXEC SQL                                                     00024920
                INCLUDE DTM5401                                         00024930
           END-EXEC.                                                    00024940
      *
       LINKAGE SECTION.
      *
       01  LS-TEMPLATE-ID                   PIC S9(9) COMP.
       01  LS-CMPNY-ID                      PIC X(8).
       01  LS-USER-ID                       PIC X(10).
       01  LS-ACTION-IND                    PIC X(01).
       01  LS-LCK-CMPNY-CD                  PIC X(8).
       01  LS-LCK-USER-ID                   PIC X(10).
       01  LS-LCK-USER-NM                   PIC X(200).
       01  LS-OUT-SQLCODE                   PIC +(9)9.
       01  LS-ERROR-AREA                    PIC X(80).
       01  LS-RC                            PIC X(04).
      *
       PROCEDURE DIVISION USING  LS-TEMPLATE-ID,
                                 LS-CMPNY-ID,
                                 LS-USER-ID,
                                 LS-ACTION-IND,
                                 LS-LCK-CMPNY-CD,
                                 LS-LCK-USER-ID,
                                 LS-LCK-USER-NM,
                                 LS-OUT-SQLCODE,
                                 LS-ERROR-AREA,
                                 LS-RC.
      *----------*
       0000-MAIN.
      *----------*

           PERFORM 1000-INITIALIZE
           PERFORM 2000-VALIDATE-INPUT
           PERFORM 3000-LOCK-PROCESS
           IF DISPLAY-PARAMETERS
              PERFORM 9100-DISPLAY-DATA
           END-IF
           PERFORM 9990-GOBACK
           .

      *------------------*
       1000-INITIALIZE.
      *------------------*
           MOVE '1000-INITIALIZE'           TO WS-PARAGRAPH-NAME
           MOVE SPACES                      TO LS-ERROR-AREA
           MOVE 'SP00'                      TO LS-RC
           INITIALIZE   WS-ACTION-SW
                        LS-OUT-SQLCODE
                        WS-TEMPLATE-ID
           MOVE LS-TEMPLATE-ID              TO WS-TEMPLATE-ID
      * CONVERT ALL THE INPUT INTO UPPER-CASE
           MOVE FUNCTION UPPER-CASE(LS-CMPNY-ID)
                                            TO WS-CMPNY-ID
           MOVE FUNCTION UPPER-CASE(LS-USER-ID)
                                            TO WS-USER-ID
           MOVE FUNCTION UPPER-CASE(LS-ACTION-IND)
                                            TO LS-ACTION-IND
           MOVE LS-ACTION-IND               TO WS-ACTION-SW

           EXEC SQL                                                     00051800
                SELECT ACTVT_DSPLY_IN                                   00051900
                  INTO :D054-ACTVT-DSPLY-IN                             00052010
                FROM   VDTM54_DEBUG_CNTRL                               00052040
                WHERE PRGM_ID = :WS-PROGRAM                             00052050
           END-EXEC                                                     00052060
                                                                        00052070
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
              DISPLAY 'DPMXMLOK STARTED AT      :' WS-TS
              DISPLAY WS-DASHES
           END-IF
           .
      *------------------------*
       2000-VALIDATE-INPUT.
      *------------------------*
           PERFORM 2100-VALIDATE-CMPNY-ID
           PERFORM 2200-VALIDATE-ACTN-IND
           .

      *------------------------*
       2100-VALIDATE-CMPNY-ID.
      *------------------------*
           MOVE '2100-VALIDATE-CMPNY-ID'    TO WS-PARAGRAPH-NAME

           IF  WS-CMPNY-ID <= SPACES
           AND WS-USER-ID <= SPACES
           AND WS-LOCK
              MOVE 'SP50'                   TO LS-RC
              MOVE WS-SPC-ID                TO LS-ERROR-AREA
              PERFORM 9990-GOBACK
           END-IF
           .
      *------------------------*
       2200-VALIDATE-ACTN-IND.
      *------------------------*
           MOVE '2200-VALIDATE-ACTN-IND'    TO WS-PARAGRAPH-NAME
           EVALUATE TRUE
              WHEN WS-LOCK
              WHEN WS-UNLOCK
              WHEN WS-VIEW
                 CONTINUE
              WHEN OTHER
                 MOVE WS-INVLD-IND          TO LS-ERROR-AREA
                 MOVE 'SP50'                TO LS-RC
                 PERFORM 9990-GOBACK
           END-EVALUATE .

      *------------------------*
       3000-LOCK-PROCESS.
      *------------------------*
           MOVE '3000-LOCK-PROCESS'         TO WS-PARAGRAPH-NAME
           EVALUATE TRUE
              WHEN WS-LOCK
                 PERFORM 3110-VALIDATE-LOCK
              WHEN WS-UNLOCK
                 PERFORM 3200-UNLOCK
              WHEN WS-VIEW
                 PERFORM 3300-VIEW-LOCK
           END-EVALUATE
           .

      *------------------------*
       3110-VALIDATE-LOCK.
      *------------------------*
           MOVE '3110-VALIDATE-LOCK'        TO WS-PARAGRAPH-NAME
           EXEC SQL
              SELECT MCA_TMPLT_ID
                    ,CMPNY_ID
                    ,CMPNY_USER_ID
              INTO :D010-MCA-TMPLT-ID
                  ,:D010-CMPNY-ID
                  ,:D010-CMPNY-USER-ID
              FROM VDPM10_MCA_LOCK
              WHERE MCA_TMPLT_ID = :WS-TEMPLATE-ID
           END-EXEC

           EVALUATE SQLCODE
              WHEN 0
                 IF D010-CMPNY-ID       = WS-CMPNY-ID AND
                    D010-CMPNY-USER-ID  = WS-USER-ID
                    CONTINUE
                 ELSE
                    MOVE 'SP01'             TO  LS-RC
                    MOVE WS-DUP-LCK         TO  LS-ERROR-AREA
                    PERFORM 9990-GOBACK
                 END-IF
              WHEN 100
                 PERFORM 3120-LOCK
              WHEN OTHER
                 MOVE WS-INVLD-IND          TO LS-ERROR-AREA
                 MOVE 'SP50'                TO LS-RC
                 PERFORM 9990-GOBACK
           END-EVALUATE .

      *------------------------*
       3120-LOCK.
      *------------------------*

           MOVE '3100-LOCK'                 TO WS-PARAGRAPH-NAME

           EXEC SQL
              INSERT INTO VDPM10_MCA_LOCK
              ( MCA_TMPLT_ID,
                CMPNY_ID,
                CMPNY_USER_ID,
                ROW_UPDT_TS)
              VALUES(:WS-TEMPLATE-ID,
                     :WS-CMPNY-ID,
                     :WS-USER-ID,
                     CURRENT TIMESTAMP)

           END-EXEC

           EVALUATE SQLCODE
              WHEN 0
                 MOVE SQLCODE              TO  LS-OUT-SQLCODE
              WHEN OTHER
                 MOVE 'VDPM10_MCA_LOCK'    TO  WS-TABLE-NAME
                 PERFORM 9000-SQL-ERROR
                 PERFORM 9990-GOBACK
           END-EVALUATE
           .

      *------------------------*
       3200-UNLOCK.
      *------------------------*

           MOVE '3200-UNLOCK'              TO WS-PARAGRAPH-NAME

           EXEC SQL
              DELETE FROM VDPM10_MCA_LOCK

               WHERE MCA_TMPLT_ID = :WS-TEMPLATE-ID

           END-EXEC

           EVALUATE SQLCODE
              WHEN 0
                 MOVE SQLCODE              TO  LS-OUT-SQLCODE
              WHEN 100
                 MOVE SQLCODE              TO  LS-OUT-SQLCODE
                 MOVE 'SP02'               TO  LS-RC
                 MOVE WS-DUP-UNLCK         TO  LS-ERROR-AREA
              WHEN OTHER
                 MOVE 'VDPM10_MCA_LOCK'    TO  WS-TABLE-NAME
                 PERFORM 9000-SQL-ERROR
                 PERFORM 9990-GOBACK
           END-EVALUATE
           .

      *------------------------*
       3300-VIEW-LOCK.
      *------------------------*

           MOVE '3300-VIEW-LOCK'           TO WS-PARAGRAPH-NAME

           EXEC SQL
               SELECT
  1                 DPM10.CMPNY_ID,
  2                 DPM10.CMPNY_USER_ID,
  3                 DPM03.CMPNY_USER_NM
               INTO
                   :D010-CMPNY-ID,
                   :D010-CMPNY-USER-ID,
                   :D003-CMPNY-USER-NM
               FROM VDPM10_MCA_LOCK         DPM10,
                    D0003       DPM03
               WHERE DPM10.MCA_TMPLT_ID   = :WS-TEMPLATE-ID
                 AND (DPM03.CMPNY_ID = DPM10.CMPNY_ID
                  AND DPM03.CMPNY_USER_ID = DPM10.CMPNY_USER_ID)
               WITH UR
           END-EXEC
      *
           EVALUATE SQLCODE
              WHEN 0
                 MOVE  SQLCODE             TO LS-OUT-SQLCODE
                 MOVE  D010-CMPNY-ID       TO LS-LCK-CMPNY-CD
                 MOVE  D010-CMPNY-USER-ID  TO LS-LCK-USER-ID
                 MOVE  D003-CMPNY-USER-NM  TO LS-LCK-USER-NM
              WHEN OTHER
                 MOVE 'VDPM10_MCA_LOCK'    TO  WS-TABLE-NAME
                 PERFORM 9000-SQL-ERROR
                 PERFORM 9990-GOBACK
           END-EVALUATE
           .

      *------------------------*
       9000-SQL-ERROR.
      *------------------------*

           MOVE SQLCODE                    TO WS-SQLCODE
                                              LS-OUT-SQLCODE
           DISPLAY ' *** SQL ERROR *** '
           DISPLAY 'PROGRAM   NAME = ' WS-PROGRAM
           DISPLAY 'PARAGRAPH NAME = ' WS-PARAGRAPH-NAME
           DISPLAY 'TABLE     NAME = ' WS-TABLE-NAME
           DISPLAY 'SQLCODE        = ' WS-SQLCODE

           EXEC SQL
                ROLLBACK
           END-EXEC
           .
      *------------------------*
       9100-DISPLAY-DATA.
      *------------------------*

           DISPLAY WS-DASHES
           DISPLAY 'PROGRAM-NAME             :' WS-PROGRAM
           DISPLAY 'PARAGRAPH-NAME           :' WS-PARAGRAPH-NAME
           DISPLAY 'SP-ERROR-AREA            :' LS-ERROR-AREA
           DISPLAY 'SP-RC                    :' LS-RC

           .
      *------------------------*
       9990-GOBACK.
      *------------------------*
           IF DISPLAY-PARAMETERS
              EXEC SQL
                  SET :WS-TS = CURRENT TIMESTAMP
              END-EXEC
              DISPLAY WS-DASHES
              DISPLAY 'DPMXMLOK ENDED AT        :' WS-TS
              DISPLAY WS-DASHES
           END-IF
           GOBACK

           .
