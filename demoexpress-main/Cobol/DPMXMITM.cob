       IDENTIFICATION DIVISION.
       PROGRAM-ID.    DPMXMITM.
       AUTHOR.        COGNIZANT.

      ******************************************************************
      *THIS IS AN UNPUBLISHED PROGRAM OWNED BY ISCC                    *
      *IN WHICH A COPYRIGHT SUBSISTS AS OF JULY 2006.                  *
      *                                                                *
      ******************************************************************
      **LNKCTL**
      *    INCLUDE SYSLIB(DSNRLI)
      *    MODE AMODE(31) RMODE(ANY)
      *    ENTRY DPMXMITM
      *    NAME  DPMXMITM(R)
      *
      ******************************************************************
      **         P R O G R A M   D O C U M E N T A T I O N            **
      ******************************************************************
      * SYSTEM:    DERIV/SERV MCA XPRESS                               *
      * PROGRAM:   DPMXMITM                                            *
      *                                                                *
      * THIS STORED PROCEDURE IS USED  TO POPULATE THE ISDA TERM VALUE *
      * FOR A PARTICULAR AMENDED VALUE                                 *
      * THIS STORED PROCEDURE IS CALLED FROM THE WEB  WHEN THE VIEW    *
      * INDUSTRY PUBLISHED TERM IS CLICKED BY THE USER FOR AN AMENDED  *
      * TERM                                                           *
      ******************************************************************
      * TABLES:                                                        *
      * -------                                                        *
      *      D0006 - MASTER TABLE CONTAINS TEMPLATE LEVEL   *
      * DETAILS INCLUSIVE OF ISDA/ CUSTOMIZED TEMPLATE                 *
      *                                                                *
      * VDPM04_MCA_ATTRB_DTL - CONTAINS DETAILED DESCRIPTION OF THE    *
      * ABBREVIATED CODE FOR VARIOUS PROD / SUB PROD / REGION /        *
      * CATEGORY / TERM                                                *
      *                                                                *
      * VDPM18_MCA_LINK-THIS IS A LINK TABLE THAT CONNECTS THE POINTERS*
      * OF THE AMENDMENT / DOCUMENT / COMMENTS TABLE TO THE TEXT TABLE *
      *                                                                *
      * VDPM16_MCA_AMND   - THIS TABLE CONTAINS THE  DETAILS ABOUT THE *
      * VALUE OF TERM PERTAINING TO A CATEGORY WHICH IS INCLUSIVE OF   *
      * BOTH THE ISDA TERM VALUES AS WELL AS AMENDED TERM VALUES THAT  *
      * IS DIFFERENT FROM ISDA TERM VALUE                              *
      *                                                                *
      * VDPM13_MCA_TEXT                                                *
      *                                                                *
      * VDTM54_DEBUG_CNTRL    - DEBUG CONTROL TABLE                    *
      *----------------------------------------------------------------*
      * INCLUDES:                                                      *
      * ---------                                                      *
      * SQLCA    - DB2 COMMAREA                                        *
      * DPM1401                                                        *
      * DPM1501                                                        *
      * DPM0401                                                        *
      * DPM1801                                                        *
      * DPM1901                                                        *
      * DPM1601                                                        *
      * DPM1701                                                        *
      * DPM1301                                                        *
      * DPM5401                                                        *
      *----------------------------------------------------------------
      * COPYBOOK:                                                      *
      * ---------                                                      *
      * DB2000IA                                                      *
      * DB2000IB                                                      *
      * DB2000IC                                                      *
      *----------------------------------------------------------------
      *              M A I N T E N A N C E   H I S T O R Y            *
      *                                                               *
      *                                                               *
      * DATE CHANGED    VERSION     PROGRAMMER                        *
      * ------------    -------     --------------------              *
      *                                                               *
      * 09/10/2007        00.00     COGNIZANT                         *
      * INITIAL IMPLEMENTATION                                        *
      *                                                               *
      *****************************************************************
       ENVIRONMENT DIVISION.
       DATA DIVISION.
       WORKING-STORAGE SECTION.
       01  WS-PROGRAM                      PIC X(08) VALUE 'DPMXMITM'.
       01  WS-VARIABLES.
             05  WS-TEMPLATE-CHK           PIC S9(9) COMP-3             00560400
                                                     VALUE ZEROES.      00560500
             05  WS-AMND-CHK               PIC S9(9) COMP-3             00560400
                                                     VALUE ZEROES.      00560500
             05  WS-DASHES                 PIC X(40) VALUE ALL '='.
             05  WS-CTGRY-NM               PIC X(150).
             05  WS-AMND-ID                PIC S9(18)V USAGE COMP-3.
             05  WS-AMND-ID1               PIC S9(18)V USAGE COMP-3.
             05  WS-TERM-NM                PIC X(150).
             05  WS-INVLD-AMND-ID          PIC X(40) VALUE
                           'AMENDMENT ID DOES NOT EXIST'.
             05  WS-INVLD-ACTN-VL          PIC X(40) VALUE
                           'INVALID USER TYPE'.
             05  WS-INVLD-TERM-VL          PIC X(40) VALUE
                           'INVALID TEMPLATE TYPE'.
             05  WS-INVALID-USER-IND     PIC X(40) VALUE
                           'INVALID USER INDICATOR'.
       01  WS-TS                          PIC X(26).
       01  WS-ERROR-AREA.
             05  WS-PARAGRAPH-NAME         PIC X(40).
             05  FILLER                    PIC X VALUE ','.
             05  WS-TABLE-NAME             PIC X(18).
             05  WS-SQLCODE                PIC 9(7).
       01  WS-SWITCHES.
             05  WS-TMPLT-TYP-SW           PIC X(01).
                 88  WS-ISDA               VALUE 'I'.
                 88  WS-OTHER              VALUE 'C'.
             05  WS-FUNC-TYP-SW            PIC X(01).
                 88  WS-ADMIN              VALUE 'A'.
                 88  WS-MCAWIZ             VALUE 'M'.
             05  WS-USER-IND               PIC X(01).
                 88  WS-LCK-USR            VALUE 'L'.
                 88  WS-ORG-USR            VALUE 'O'.
                 88  WS-DEF-USR            VALUE 'D'.
             05  WS-DISPLAY-SWITCH         PIC X(01) VALUE 'N'.
                 88 DISPLAY-PARAMETERS               VALUE 'Y'.
                 88 HIDE-PARAMETERS                  VALUE 'N'.

      *****************************************************************
      *                        SQL INCLUDES                            *
      ******************************************************************
           EXEC SQL
                INCLUDE SQLCA
           END-EXEC.

           EXEC SQL
                INCLUDE DPM1401
           END-EXEC.

           EXEC SQL
                INCLUDE DPM1501
           END-EXEC.

           EXEC SQL
                INCLUDE DPM0401
           END-EXEC.

           EXEC SQL
                INCLUDE DPM1801
           END-EXEC.

           EXEC SQL
                INCLUDE DPM1901
           END-EXEC.

           EXEC SQL
                INCLUDE DPM1601
           END-EXEC.

           EXEC SQL
                INCLUDE DPM1701
           END-EXEC.

           EXEC SQL
                INCLUDE DPM1301
           END-EXEC.
      * INCLUDE FOR VDTM54_DEBUG_CNTRL                                  00024910
           EXEC SQL                                                     00024920
                INCLUDE DTM5401                                         00024930
           END-EXEC.                                                    00024940
      ******************************************************************
      * DB2 STANDARD COPYBOOK WITH FORMATTED DISPLAY SQLCA
      * THIS MUST REMAIN AS THE LAST ENTRY IN WORKING STORAGE
      ******************************************************************
       COPY  DB2000IA.

       LINKAGE SECTION.

      *PICTURE CLAUSE FOR OUTSQLCA - PIC X(179) - FOR LINKAGE SECTION
       COPY  DB2000IB.
       01  LS-SP-ERROR-AREA                PIC X(80).
       01  LS-SP-RC                        PIC X(04).
       01  LS-AMND-ID                      PIC S9(18)V USAGE COMP-3.
       01  LS-FUNC-TYP                     PIC X(01).
       01  LS-TMPLT-TYP                    PIC X(01).
       01  LS-USER-IND                     PIC X(01).

       PROCEDURE DIVISION USING  OUTSQLCA,
                                 LS-SP-ERROR-AREA,
                                 LS-SP-RC,
                                 LS-AMND-ID,
                                 LS-FUNC-TYP,
                                 LS-TMPLT-TYP,
                                 LS-USER-IND.
      *---------*
       0000-MAIN.
      *---------*

           PERFORM 1000-INITIALIZE

           PERFORM 2000-VALIDATE-INPUT

           PERFORM 3000-PROCESS

           IF DISPLAY-PARAMETERS
              PERFORM 9100-DISPLAY-DATA
           END-IF
           PERFORM 9990-GOBACK
           .
      *-------------------------*
       1000-INITIALIZE.
      *-------------------------*

           MOVE '1000-INITIALIZE'           TO WS-PARAGRAPH-NAME

           MOVE SPACES                      TO LS-SP-ERROR-AREA
                                               OUTSQLCA
           MOVE 'SP00'                      TO LS-SP-RC
           MOVE FUNCTION UPPER-CASE(LS-FUNC-TYP)
                                            TO LS-FUNC-TYP
           MOVE FUNCTION UPPER-CASE(LS-TMPLT-TYP)
                                            TO LS-TMPLT-TYP
           MOVE FUNCTION UPPER-CASE(LS-USER-IND)
                                            TO LS-USER-IND
           MOVE LS-AMND-ID                  TO WS-AMND-ID
           MOVE LS-FUNC-TYP                 TO WS-FUNC-TYP-SW
           MOVE LS-TMPLT-TYP                TO WS-TMPLT-TYP-SW
           MOVE LS-USER-IND                 TO WS-USER-IND
                                                                        00051700
           EXEC SQL                                                     00051800
                SELECT ACTVT_DSPLY_IN                                   00051900
                  INTO :D054-ACTVT-DSPLY-IN                             00052010
                FROM   VDTM54_DEBUG_CNTRL                               00052040
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
              DISPLAY 'DPMXMITM STARTED AT      :' WS-TS
              DISPLAY WS-DASHES
           END-IF
           .
      *------------------------*
       2000-VALIDATE-INPUT.
      *------------------------*
           PERFORM  2100-VALIDATE-FUNC-TYP
           PERFORM  2200-VALIDATE-TMPLT-TYP
           PERFORM  2300-VALIDATE-USR-IND.
      *--------------------------*
       2100-VALIDATE-FUNC-TYP.
      *--------------------------*
           MOVE '2100-VALIDATE-FUNC-TYP'    TO WS-PARAGRAPH-NAME
           EVALUATE TRUE
              WHEN WS-ADMIN
              WHEN WS-MCAWIZ
                 CONTINUE
              WHEN OTHER
                 MOVE  WS-INVLD-ACTN-VL     TO LS-SP-ERROR-AREA
                 MOVE  'SP50'               TO LS-SP-RC
                 PERFORM 9990-GOBACK
           END-EVALUATE .
      *--------------------------*
       2200-VALIDATE-TMPLT-TYP.
      *--------------------------*
           MOVE '2200-VALIDATE-TMPLT-TYP'   TO WS-PARAGRAPH-NAME
           EVALUATE TRUE
              WHEN WS-ISDA
              WHEN WS-OTHER
                 CONTINUE
              WHEN OTHER
                 MOVE  WS-INVLD-TERM-VL     TO LS-SP-ERROR-AREA
                 MOVE  'SP50'               TO LS-SP-RC
                 PERFORM 9990-GOBACK
           END-EVALUATE .
      *------------------------*                                        01120000
       2300-VALIDATE-USR-IND.                                           01130000
      *------------------------*                                        01140000
                                                                        01150000
           MOVE '2300-VALIDATE-USR-IND'     TO WS-PARAGRAPH-NAME        01160000
                                                                        01450000
           IF WS-ISDA
              CONTINUE
           ELSE
             EVALUATE TRUE
                WHEN WS-LCK-USR
                WHEN WS-ORG-USR
                WHEN WS-DEF-USR
                   CONTINUE
                WHEN OTHER
                   MOVE WS-INVALID-USER-IND
                                           TO LS-SP-ERROR-AREA          00
                   MOVE 'SP50'             TO LS-SP-RC                  00
                   PERFORM 9990-GOBACK                                  00
             END-EVALUATE
           END-IF
           .                                                            01460000
      *---------------------*                                           01120000
       3000-PROCESS.
      *---------------------*                                           01140000
           MOVE '3000-PROCESS'              TO WS-PARAGRAPH-NAME        01160000
                                                                        01450000
           EVALUATE TRUE
               WHEN WS-ADMIN
                  IF WS-LCK-USR
                    PERFORM 3050-AMEND-ID-CHECK
                  ELSE
                    PERFORM 3315-GET-HDR
                    PERFORM 3500-DEF-SPEC-DETAILS
                  END-IF
               WHEN WS-MCAWIZ
                  EVALUATE TRUE
                     WHEN WS-ISDA
                        PERFORM 3100-AMND-CHECK
                     WHEN WS-OTHER
                        PERFORM 3200-CHECK-USER
                  END-EVALUATE
           END-EVALUATE

           .                                                            01460000
      *-------------------*
       3050-AMEND-ID-CHECK.
      *-------------------*
           MOVE '3050-AMEND-ID-CHECK'       TO WS-PARAGRAPH-NAME        01160000

           EXEC SQL
                SELECT 1
                  INTO :WS-AMND-ID1
                  FROM VDPM16_MCA_AMND
                 WHERE MCA_AMND_ID    = :WS-AMND-ID
                 WITH UR
           END-EXEC

           EVALUATE SQLCODE
              WHEN 0                                                    01700000
                 PERFORM 3051-GET-MASTER-VALUES
              WHEN 100                                                  01700000
                 PERFORM 3051A-CHECK-WORK
              WHEN OTHER                                                01750000
                 PERFORM 9000-SQL-ERROR                                 01810000
           END-EVALUATE
           .
      *----------------------*
       3051-GET-MASTER-VALUES.
      *----------------------*
           MOVE '3051-GET-MASTER-VALUES'   TO WS-PARAGRAPH-NAME         01160000

           PERFORM 3315-GET-HDR
           PERFORM 3320-TERM-MSTR
           .

      *-------------------*
       3051A-CHECK-WORK.
      *-------------------*
           MOVE '3051A-CHECK-WORK'         TO WS-PARAGRAPH-NAME         01160000

           EXEC SQL
                SELECT 1
                  INTO :WS-AMND-ID1
                  FROM VDPM17_AMND_WORK
                 WHERE MCA_AMND_ID    = :WS-AMND-ID
                 WITH UR
           END-EXEC

           EVALUATE SQLCODE
              WHEN 0                                                    01700000
                 PERFORM 3051B-WORK-TEMPLATE-DETAILS
              WHEN 100                                                  01700000
                 INITIALIZE SQLCODE
                 MOVE 'SP50'               TO LS-SP-RC
                 MOVE WS-INVLD-AMND-ID     TO LS-SP-ERROR-AREA
              WHEN OTHER                                                01750000
                 PERFORM 9000-SQL-ERROR                                 01810000
           END-EVALUATE
           .
      *----------------------------*
       3051B-WORK-TEMPLATE-DETAILS.
      *----------------------------*
           MOVE '3051B-WORK-TEMPLATE-DETAILS'
                                            TO WS-PARAGRAPH-NAME
           EXEC SQL
               DECLARE ITM_A_OTHER_WRK CURSOR WITH HOLD WITH RETURN FOR 07100062
                  SELECT
  1                     DPM15.MCA_TMPLT_ID
  2                    ,DPM15.MCA_TMPLT_TYPE_CD
  3                    ,DPM15.MCA_TMPLT_NM
  4                    ,DPM17.ATTRB_CTGRY_ID
  5                    ,DPM17.CTGRY_SQ
  6                    ,DPM04A.ATTRB_VALUE_DS
  7                    ,DPM17.ATTRB_TERM_ID
  8                    ,DPM17.TERM_SQ
  9                    ,DPM04B.ATTRB_VALUE_DS
  10                   ,DPM19.MCA_AMND_ID
  11                   ,DPM19.MCA_VALUE_ID
  12                   ,CASE WHEN DPM19.MCA_VALUE_TYPE_CD = 'T'
                             THEN DPM13.MCA_TEXT_DS
                             WHEN DPM19.MCA_VALUE_TYPE_CD = 'D'
                             THEN ' '
                        END
  13                   ,DPM19.MCA_VALUE_TYPE_CD
                  FROM       D0006     DPM15
                  INNER JOIN VDPM17_AMND_WORK     DPM17
                         ON DPM15.MCA_TMPLT_ID   = DPM17.MCA_TMPLT_ID
                  INNER JOIN VDPM04_ATTRB_DTL     DPM04A
                         ON DPM17.ATTRB_CTGRY_ID = DPM04A.ATTRB_ID
                        AND DPM04A.ATTRB_TYPE_ID = 'C'
                  INNER JOIN VDPM04_ATTRB_DTL     DPM04B
                         ON DPM17.ATTRB_TERM_ID  = DPM04B.ATTRB_ID
                        AND DPM04B.ATTRB_TYPE_ID   = 'T'
                  INNER JOIN VDPM19_LINK_WORK     DPM19
                       ON DPM19.MCA_AMND_ID       = DPM17.MCA_AMND_ID
                      AND DPM19.MCA_VALUE_ID      > 0
                      AND DPM19.MCA_VALUE_TYPE_CD IN ('T','D')
                      AND DPM19.MCA_ACCS_STAT_CD  = 'U'
                  LEFT OUTER JOIN VDPM13_MCA_TEXT DPM13
                     ON  DPM19.MCA_VALUE_ID   = DPM13.MCA_VALUE_ID
      *           LEFT OUTER JOIN VDPM12_MCA_DOC  DPM12
      *              ON  DPM19.MCA_VALUE_ID   = DPM12.MCA_VALUE_ID
                  WHERE  DPM17.MCA_AMND_ID    = :WS-AMND-ID
               WITH UR
           END-EXEC

           EXEC SQL
              OPEN ITM_A_OTHER_WRK
           END-EXEC

           EVALUATE SQLCODE
              WHEN 0
                 MOVE 'SP00'                TO LS-SP-RC
              WHEN OTHER
                 PERFORM 9000-SQL-ERROR
           END-EVALUATE .
           .
      *-------------------*
       3100-AMND-CHECK.
      *-------------------*
           MOVE '3100-AMND-CHECK'           TO WS-PARAGRAPH-NAME        01160000

           EXEC SQL
                SELECT 1
                  INTO :WS-AMND-CHK
                  FROM VDPM17_AMND_WORK
                 WHERE MCA_AMND_ID  = :WS-AMND-ID
                 WITH UR
           END-EXEC

           EVALUATE SQLCODE
              WHEN 0                                                    01700000
                 PERFORM 3110-GET-ISDA-AMND-ID
                 PERFORM 3111-WORK-TEMPLATE-DETAILS
              WHEN 100                                                  01700000
                 INITIALIZE SQLCODE
                 PERFORM 3120-GET-ISDA-AMND-ID
                 PERFORM 3121-MASTER-TEMPLATE-DETAILS
              WHEN OTHER                                                01750000
                 PERFORM 9000-SQL-ERROR                                 01810000
           END-EVALUATE
           .
      *--------------------------*
       3110-GET-ISDA-AMND-ID.
      *--------------------------*
           MOVE '3110-GET-ISDA-AMND-ID'
                                            TO WS-PARAGRAPH-NAME
           EXEC SQL
              SELECT MCA_ISDA_AMND_ID
              INTO :D017-MCA-ISDA-AMND-ID
              FROM VDPM17_AMND_WORK
              WHERE MCA_AMND_ID = :WS-AMND-ID
           END-EXEC
           EVALUATE SQLCODE
              WHEN 0                                                    01700000
                 CONTINUE
              WHEN OTHER                                                01750000
                 PERFORM 9000-SQL-ERROR                                 01810000
           END-EVALUATE .
      *--------------------------*
       3111-WORK-TEMPLATE-DETAILS.
      *--------------------------*
           MOVE '3111-WORK-TEMPLATE-DETAILS'
                                            TO WS-PARAGRAPH-NAME
           EXEC SQL                                                     07090062
               DECLARE ITM_ISDA_WRK  CURSOR WITH HOLD WITH RETURN FOR   07100062
                  SELECT
  1                     DPM14.MCA_TMPLT_ID
  2                    ,DPM14.MCA_TMPLT_TYPE_CD
  3                    ,DPM14.MCA_TMPLT_NM
  4                    ,DPM16.ATTRB_CTGRY_ID
  5                    ,DPM16.CTGRY_SQ
  6                    ,DPM04A.ATTRB_VALUE_DS
  7                    ,DPM16.ATTRB_TERM_ID
  8                    ,DPM16.TERM_SQ
  9                    ,DPM04B.ATTRB_VALUE_DS
  10                   ,DPM16.MCA_AMND_ID
  11                   ,DPM18.MCA_VALUE_ID
  12                   ,CASE WHEN DPM18.MCA_VALUE_TYPE_CD = 'T'
                             THEN DPM13.MCA_TEXT_DS
                             WHEN DPM18.MCA_VALUE_TYPE_CD = 'D'
                             THEN ' '
                        END
  13                   ,DPM18.MCA_VALUE_TYPE_CD
                  FROM   VDPM16_MCA_AMND          DPM16
             INNER JOIN  D0006         DPM14
                     ON   DPM14.MCA_TMPLT_ID   = DPM16.MCA_TMPLT_ID
             INNER JOIN  VDPM18_MCA_LINK          DPM18
                     ON   DPM18.MCA_AMND_ID    = DPM16.MCA_AMND_ID
                    AND   DPM18.MCA_VALUE_ID   > 0
                    AND   DPM18.MCA_VALUE_TYPE_CD IN('T','D')
             INNER JOIN  VDPM04_ATTRB_DTL         DPM04A
                     ON   DPM04A.ATTRB_ID      = DPM16.ATTRB_CTGRY_ID
                    AND   DPM04A.ATTRB_TYPE_ID = 'C'
             INNER JOIN  VDPM04_ATTRB_DTL         DPM04B
                     ON   DPM04B.ATTRB_ID      = DPM16.ATTRB_TERM_ID
                    AND   DPM04B.ATTRB_TYPE_ID = 'T'
             LEFT OUTER JOIN  VDPM13_MCA_TEXT     DPM13
                     ON   DPM18.MCA_VALUE_ID   = DPM13.MCA_VALUE_ID
      *      LEFT OUTER JOIN  VDPM12_MCA_DOC      DPM12
      *              ON   DPM18.MCA_VALUE_ID   = DPM12.MCA_VALUE_ID
                  WHERE   DPM16.MCA_AMND_ID    = :D017-MCA-ISDA-AMND-ID
               WITH UR
           END-EXEC

           EXEC SQL
              OPEN ITM_ISDA_WRK
           END-EXEC
           EVALUATE SQLCODE
              WHEN 0
                 MOVE 'SP00'                TO LS-SP-RC
              WHEN OTHER
                 PERFORM 9000-SQL-ERROR
           END-EVALUATE .
      *--------------------------*
       3120-GET-ISDA-AMND-ID.
      *--------------------------*
           MOVE '3120-GET-ISDA-AMND-ID'
                                            TO WS-PARAGRAPH-NAME
           EXEC SQL
              SELECT MCA_ISDA_AMND_ID
              INTO :D016-MCA-ISDA-AMND-ID
              FROM VDPM16_MCA_AMND
              WHERE MCA_AMND_ID = :WS-AMND-ID
           END-EXEC
           EVALUATE SQLCODE
              WHEN 0                                                    01700000
                 CONTINUE
              WHEN OTHER                                                01750000
                 PERFORM 9000-SQL-ERROR                                 01810000
           END-EVALUATE .
      *-------------------------------*
       3121-MASTER-TEMPLATE-DETAILS.
      *-------------------------------*
           MOVE '3121-MASTER-TEMPLATE-DETAILS'
                                            TO WS-PARAGRAPH-NAME
           EXEC SQL                                                     07090062
               DECLARE ITM_ISDA_MSTR CURSOR WITH HOLD WITH RETURN FOR   07100062
                  SELECT
  1                     DPM14.MCA_TMPLT_ID
  2                    ,DPM14.MCA_TMPLT_TYPE_CD
  3                    ,DPM14.MCA_TMPLT_NM
  4                    ,DPM16.ATTRB_CTGRY_ID
  5                    ,DPM16.CTGRY_SQ
  6                    ,DPM04A.ATTRB_VALUE_DS    AS CTGRY_DESC
  7                    ,DPM16.ATTRB_TERM_ID
  8                    ,DPM16.TERM_SQ
  9                    ,DPM04B.ATTRB_VALUE_DS    AS TERM_DESC
  10                   ,:WS-AMND-ID
  11                   ,DPM18.MCA_VALUE_ID
  12                   ,CASE WHEN DPM18.MCA_VALUE_TYPE_CD = 'T'
                             THEN DPM13.MCA_TEXT_DS
                             WHEN DPM18.MCA_VALUE_TYPE_CD = 'D'
                             THEN ' '
                        END
  13                   ,DPM18.MCA_VALUE_TYPE_CD
                  FROM   VDPM16_MCA_AMND          DPM16
             INNER JOIN  D0006         DPM14
                     ON   DPM14.MCA_TMPLT_ID   = DPM16.MCA_TMPLT_ID
             INNER JOIN  VDPM18_MCA_LINK          DPM18
                     ON   DPM18.MCA_AMND_ID    = DPM16.MCA_AMND_ID
                    AND   DPM18.MCA_VALUE_ID   > 0
                    AND   DPM18.MCA_VALUE_TYPE_CD IN ('T','D')
             INNER JOIN  VDPM04_ATTRB_DTL         DPM04A
                     ON   DPM04A.ATTRB_ID      = DPM16.ATTRB_CTGRY_ID
                    AND   DPM04A.ATTRB_TYPE_ID = 'C'
             INNER JOIN  VDPM04_ATTRB_DTL         DPM04B
                     ON   DPM04B.ATTRB_ID      = DPM16.ATTRB_TERM_ID
                    AND   DPM04B.ATTRB_TYPE_ID = 'T'
             LEFT OUTER JOIN  VDPM13_MCA_TEXT     DPM13
                     ON   DPM18.MCA_VALUE_ID   = DPM13.MCA_VALUE_ID
      *      LEFT OUTER JOIN  VDPM12_MCA_DOC      DPM12
      *              ON   DPM18.MCA_VALUE_ID   = DPM12.MCA_VALUE_ID
                  WHERE   DPM16.MCA_AMND_ID    = :D016-MCA-ISDA-AMND-ID
               WITH UR
           END-EXEC

           EXEC SQL
              OPEN ITM_ISDA_MSTR
           END-EXEC
           EVALUATE SQLCODE
              WHEN 0
                 MOVE 'SP00'                TO LS-SP-RC
              WHEN OTHER
                 PERFORM 9000-SQL-ERROR
           END-EVALUATE .
      *------------------*                                              01120000
       3200-CHECK-USER.
      *------------------*                                              01140000
           MOVE '3200-CHECK-USER'           TO WS-PARAGRAPH-NAME        01160000
                                                                        01450000
           EVALUATE TRUE
               WHEN WS-LCK-USR
                 PERFORM 3300-CHECK-TEMPLATE
               WHEN WS-ORG-USR
                 PERFORM 3315-GET-HDR
                 PERFORM 3400-ORG-SPEC-DETAILS
               WHEN WS-DEF-USR
                 PERFORM 3315-GET-HDR
                 PERFORM 3500-DEF-SPEC-DETAILS
           END-EVALUATE

           .                                                            01460000
      *---------------------*                                           01120000
       3300-CHECK-TEMPLATE.
      *---------------------*                                           01140000

           MOVE '3300-CHECK-TEMPLATE'       TO WS-PARAGRAPH-NAME        01160000

           EXEC SQL
                SELECT 1
                  INTO :WS-AMND-CHK
                  FROM VDPM16_MCA_AMND
                 WHERE MCA_AMND_ID = :WS-AMND-ID
                 WITH UR
           END-EXEC

           EVALUATE SQLCODE
              WHEN 0                                                    01700000
                 PERFORM 3315-GET-HDR
                 PERFORM 3320-TERM-MSTR
              WHEN 100                                                  01700000
                 PERFORM 3311-TERM-WORK
              WHEN OTHER                                                01750000
                 PERFORM 9000-SQL-ERROR                                 01810000
           END-EVALUATE
           .
      *-----------------*
       3311-TERM-WORK.
      *-----------------*
           MOVE '3311-TERM-WORK'             TO WS-PARAGRAPH-NAME
           EXEC SQL                                                     07090062
               DECLARE ITM_OTHER_WRK CURSOR WITH HOLD WITH RETURN FOR   07100062
                  SELECT
  1                     DPM15.MCA_TMPLT_ID
  2                    ,DPM15.MCA_TMPLT_TYPE_CD
  3                    ,DPM15.MCA_TMPLT_NM
  4                    ,DPM17.ATTRB_CTGRY_ID
  5                    ,DPM17.CTGRY_SQ
  6                    ,DPM04A.ATTRB_VALUE_DS
  7                    ,DPM17.ATTRB_TERM_ID
  8                    ,DPM17.TERM_SQ
  9                    ,DPM04B.ATTRB_VALUE_DS
  10                   ,DPM19.MCA_AMND_ID
  11                   ,DPM19.MCA_VALUE_ID
  12                   ,CASE WHEN DPM19.MCA_VALUE_TYPE_CD = 'T'
                             THEN DPM13.MCA_TEXT_DS
                             WHEN DPM19.MCA_VALUE_TYPE_CD = 'D'
                             THEN ' '
                        END
  13                   ,COALESCE(DPM19.MCA_VALUE_TYPE_CD,' ') AS VAL_TYP
                  FROM       VDPM15_TMPLT_WORK    DPM15
                  INNER JOIN VDPM17_AMND_WORK     DPM17
                         ON DPM15.MCA_TMPLT_ID   = DPM17.MCA_TMPLT_ID
                        AND DPM17.ATTRB_CTGRY_ID > ' '
                        AND DPM17.CTGRY_SQ       > 0
                        AND DPM17.ATTRB_TERM_ID  > ' '
                        AND DPM17.TERM_SQ        > 0
                  INNER JOIN VDPM04_ATTRB_DTL     DPM04A
                         ON DPM17.ATTRB_CTGRY_ID = DPM04A.ATTRB_ID
                        AND DPM04A.ATTRB_TYPE_ID = 'C'
                  INNER JOIN VDPM04_ATTRB_DTL     DPM04B
                         ON DPM17.ATTRB_TERM_ID  = DPM04B.ATTRB_ID
                        AND DPM04B.ATTRB_TYPE_ID = 'T'
                  INNER JOIN VDPM19_LINK_WORK     DPM19
                       ON DPM19.MCA_AMND_ID       = DPM17.MCA_AMND_ID
                      AND DPM19.MCA_VALUE_ID      > 0
                      AND DPM19.MCA_VALUE_TYPE_CD IN ('T','D')
                      AND DPM19.MCA_ACCS_STAT_CD  = 'U'
                  LEFT OUTER JOIN VDPM13_MCA_TEXT DPM13
                     ON  DPM19.MCA_VALUE_ID   = DPM13.MCA_VALUE_ID
      *           LEFT OUTER JOIN VDPM12_MCA_DOC  DPM12
      *              ON  DPM19.MCA_VALUE_ID   = DPM12.MCA_VALUE_ID
                  WHERE  DPM17.MCA_AMND_ID    = :WS-AMND-ID
               WITH UR
           END-EXEC

           EXEC SQL
              OPEN ITM_OTHER_WRK
           END-EXEC
           EVALUATE SQLCODE
              WHEN 0
                 MOVE 'SP00'                TO LS-SP-RC
              WHEN OTHER
                 PERFORM 9000-SQL-ERROR
           END-EVALUATE .
      *-----------------*
       3315-GET-HDR.
      *-----------------*
           MOVE '3315-GET-HDR'               TO WS-PARAGRAPH-NAME
           EXEC SQL
              SELECT
 1                  DPM14.MCA_TMPLT_ID
 2                 ,DPM14.MCA_TMPLT_TYPE_CD
 3                 ,DPM14.MCA_TMPLT_NM
 4                 ,DPM16.ATTRB_CTGRY_ID
 5                 ,DPM16.CTGRY_SQ
 6                 ,DPM04A.ATTRB_VALUE_DS
 7                 ,DPM16.ATTRB_TERM_ID
 8                 ,DPM16.TERM_SQ
 9                 ,DPM04B.ATTRB_VALUE_DS
              INTO
                    :D014-MCA-TMPLT-ID
                   ,:D014-MCA-TMPLT-TYPE-CD
                   ,:D014-MCA-TMPLT-NM
                   ,:D016-ATTRB-CTGRY-ID
                   ,:D016-CTGRY-SQ
                   ,:WS-CTGRY-NM
                   ,:D016-ATTRB-TERM-ID
                   ,:D016-TERM-SQ
                   ,:WS-TERM-NM
              FROM       D0006     DPM14
              INNER JOIN VDPM16_MCA_AMND      DPM16
                     ON DPM14.MCA_TMPLT_ID   = DPM16.MCA_TMPLT_ID
              INNER JOIN VDPM04_ATTRB_DTL     DPM04A
                     ON DPM16.ATTRB_CTGRY_ID = DPM04A.ATTRB_ID
                    AND DPM04A.ATTRB_TYPE_ID = 'C'
              INNER JOIN VDPM04_ATTRB_DTL     DPM04B
                     ON DPM16.ATTRB_TERM_ID  = DPM04B.ATTRB_ID
                    AND DPM04B.ATTRB_TYPE_ID = 'T'
                  WHERE DPM16.MCA_AMND_ID    = :WS-AMND-ID
                  WITH UR
           END-EXEC
           EVALUATE SQLCODE
              WHEN 0
                 CONTINUE
              WHEN 100
                 DISPLAY 'INVALID AMEND ID:' LS-AMND-ID
                 PERFORM 9990-GOBACK
              WHEN OTHER
                 MOVE SQLCODE TO WS-SQLCODE
                 DISPLAY 'DPMXITM SQLCODE:', WS-SQLCODE
                 PERFORM 9000-SQL-ERROR
           END-EVALUATE .
      *-----------------*
       3320-TERM-MSTR.
      *-----------------*
           MOVE '3320-TERM-MSTR'             TO WS-PARAGRAPH-NAME
           EXEC SQL                                                     07090062
               DECLARE ITM_OTHER_MSTR CURSOR WITH HOLD WITH RETURN FOR  07100062
                  SELECT
  1                  :D014-MCA-TMPLT-ID               AS TMPLT_ID
  2                 ,:D014-MCA-TMPLT-TYPE-CD          AS TMPLT_TYP_CD
  3                 ,:D014-MCA-TMPLT-NM               AS TMPLT_NM
  4                 ,:D016-ATTRB-CTGRY-ID             AS CTGRY_ID
  5                 ,:D016-CTGRY-SQ                   AS CTGRY_SQ
  6                 ,:WS-CTGRY-NM                     AS CTGRY_NM
  7                 ,:D016-ATTRB-TERM-ID              AS TERM_ID
  8                 ,:D016-TERM-SQ                    AS TERM_SQ
  9                 ,:WS-TERM-NM                      AS TERM_NM
  10                ,COALESCE(ITM.MCA_AMND_ID,0)      AS AMND_ID
  11                ,COALESCE(ITM.MCA_VALUE_ID,0)     AS VALUE_ID
  12                ,CASE WHEN ITM.MCA_VALUE_TYPE_CD = 'T'
                          THEN DPM13.MCA_TEXT_DS
                          WHEN ITM.MCA_VALUE_TYPE_CD = 'D'
                          THEN ' '
                     END
  13                ,COALESCE(ITM.MCA_VALUE_TYPE_CD,' ') AS VALUE_TYP
             FROM
                (SELECT DPM16.MCA_AMND_ID
                       ,COALESCE(DPM19.MCA_VALUE_ID,DPM18.MCA_VALUE_ID)
                                                AS MCA_VALUE_ID
                       ,COALESCE(DPM19.MCA_VALUE_TYPE_CD,
                          DPM18.MCA_VALUE_TYPE_CD) AS MCA_VALUE_TYPE_CD
                 FROM            VDPM16_MCA_AMND    DPM16
                 LEFT OUTER JOIN VDPM19_LINK_WORK   DPM19
                         ON DPM19.MCA_AMND_ID       = DPM16.MCA_AMND_ID
                        AND DPM19.MCA_VALUE_ID      > 0
                        AND DPM19.MCA_VALUE_TYPE_CD IN ('T','D')
                        AND DPM19.MCA_ACCS_STAT_CD  = 'U'
                 LEFT OUTER JOIN VDPM18_MCA_LINK    DPM18
                         ON DPM18.MCA_AMND_ID       = DPM16.MCA_AMND_ID
                        AND DPM18.MCA_VALUE_ID      > 0
                        AND DPM18.MCA_VALUE_TYPE_CD IN ('T','D')
                      WHERE DPM16.MCA_AMND_ID = :WS-AMND-ID)  ITM
                 LEFT OUTER JOIN VDPM13_MCA_TEXT    DPM13
                     ON  ITM.MCA_VALUE_ID   = DPM13.MCA_VALUE_ID
      *          LEFT OUTER JOIN VDPM12_MCA_DOC     DPM12
      *              ON  ITM.MCA_VALUE_ID   = DPM12.MCA_VALUE_ID
               WITH UR
           END-EXEC

           EXEC SQL
              OPEN ITM_OTHER_MSTR
           END-EXEC

           EVALUATE SQLCODE
              WHEN 0
                 MOVE 'SP00'                TO LS-SP-RC
              WHEN OTHER
                 PERFORM 9000-SQL-ERROR
           END-EVALUATE .

      *----------------------*
       3400-ORG-SPEC-DETAILS.
      *----------------------*
           MOVE '3400-ORG-SPEC-DETAILS'      TO WS-PARAGRAPH-NAME
           EXEC SQL                                                     07090062
               DECLARE ITM_ORG_USER   CURSOR WITH HOLD WITH RETURN FOR  07100062
                  SELECT
  1                     :D014-MCA-TMPLT-ID               AS TMPLT_ID
  2                    ,:D014-MCA-TMPLT-TYPE-CD          AS TMPLT_TYP_CD
  3                    ,:D014-MCA-TMPLT-NM               AS TMPLT_NM
  4                    ,:D016-ATTRB-CTGRY-ID             AS CTGRY_ID
  5                    ,:D016-CTGRY-SQ                   AS CTGRY_SQ
  6                    ,:WS-CTGRY-NM                     AS CTGRY_NM
  7                    ,:D016-ATTRB-TERM-ID              AS TERM_ID
  8                    ,:D016-TERM-SQ                    AS TERM_SQ
  9                    ,:WS-TERM-NM                      AS TERM_NM
  10                   ,COALESCE(ITM.MCA_AMND_ID,0)      AS AMND_ID
  11                   ,COALESCE(ITM.MCA_VALUE_ID,0)     AS VALUE_ID
  12                   ,CASE WHEN ITM.MCA_VALUE_TYPE_CD = 'T'
                             THEN DPM13.MCA_TEXT_DS
                             WHEN ITM.MCA_VALUE_TYPE_CD = 'D'
                             THEN ' '
                        END
  13                   ,COALESCE(ITM.MCA_VALUE_TYPE_CD,' ') AS VALUE_TYP
             FROM
                (SELECT DPM16.MCA_AMND_ID
                       ,COALESCE(DPM19.MCA_VALUE_ID,DPM18.MCA_VALUE_ID)
                                                AS MCA_VALUE_ID
                       ,COALESCE(DPM19.MCA_VALUE_TYPE_CD,
                          DPM18.MCA_VALUE_TYPE_CD) AS MCA_VALUE_TYPE_CD
                 FROM            VDPM16_MCA_AMND    DPM16
                 LEFT OUTER JOIN VDPM19_LINK_WORK   DPM19
                         ON DPM19.MCA_AMND_ID       = DPM16.MCA_AMND_ID
                        AND DPM19.MCA_VALUE_ID      > 0
                        AND DPM19.MCA_VALUE_TYPE_CD IN ('T','D')
                        AND DPM19.MCA_ACCS_STAT_CD  = 'O'
                 LEFT OUTER JOIN VDPM18_MCA_LINK    DPM18
                         ON DPM18.MCA_AMND_ID       = DPM16.MCA_AMND_ID
                        AND DPM18.MCA_VALUE_ID      > 0
                        AND DPM18.MCA_VALUE_TYPE_CD IN ('T','D')
                      WHERE DPM16.MCA_AMND_ID = :WS-AMND-ID)  ITM
             LEFT OUTER JOIN VDPM13_MCA_TEXT    DPM13
                ON  ITM.MCA_VALUE_ID   = DPM13.MCA_VALUE_ID
      *      LEFT OUTER JOIN VDPM12_MCA_DOC     DPM12
      *         ON  ITM.MCA_VALUE_ID   = DPM12.MCA_VALUE_ID
             WITH UR
           END-EXEC

           EXEC SQL
              OPEN ITM_ORG_USER
           END-EXEC
           EVALUATE SQLCODE
              WHEN 0
                 MOVE 'SP00'                TO LS-SP-RC
              WHEN OTHER
                 PERFORM 9000-SQL-ERROR
           END-EVALUATE .
      *--------------------------*
       3500-DEF-SPEC-DETAILS.
      *--------------------------*
           MOVE '3500-DEF-SPEC-DETAILS'     TO WS-PARAGRAPH-NAME
           EXEC SQL                                                     07090062
               DECLARE ITM_DEF_USER  CURSOR WITH HOLD WITH RETURN FOR   07100062
                  SELECT
  1                 :D014-MCA-TMPLT-ID               AS TMPLT_ID
  2                ,:D014-MCA-TMPLT-TYPE-CD          AS TMPLT_TYP_CD
  3                ,:D014-MCA-TMPLT-NM               AS TMPLT_NM
  4                ,:D016-ATTRB-CTGRY-ID             AS CTGRY_ID
  5                ,:D016-CTGRY-SQ                   AS CTGRY_SQ
  6                ,:WS-CTGRY-NM                     AS CTGRY_NM
  7                ,:D016-ATTRB-TERM-ID              AS TERM_ID
  8                ,:D016-TERM-SQ                    AS TERM_SQ
  9                ,:WS-TERM-NM                      AS TERM_NM
  10               ,DPM16.MCA_AMND_ID                AS AMND_ID
  11               ,DPM18.MCA_VALUE_ID               AS VALUE_ID
  12               ,CASE WHEN DPM18.MCA_VALUE_TYPE_CD = 'T'
                         THEN DPM13.MCA_TEXT_DS
                         WHEN DPM18.MCA_VALUE_TYPE_CD = 'D'
                         THEN ' '
                    END
  13               ,DPM18.MCA_VALUE_TYPE_CD          AS VALUE_TYP
                    FROM    VDPM16_MCA_AMND       DPM16
                 INNER JOIN VDPM18_MCA_LINK       DPM18
                    ON DPM16.MCA_AMND_ID    = DPM18.MCA_AMND_ID
                   AND DPM18.MCA_VALUE_ID   > 0
                   AND DPM18.MCA_VALUE_TYPE_CD IN ('T','D')
                 LEFT OUTER JOIN VDPM13_MCA_TEXT    DPM13
                   ON DPM18.MCA_VALUE_ID   = DPM13.MCA_VALUE_ID
      *          LEFT OUTER JOIN VDPM12_MCA_DOC     DPM12
      *            ON DPM18.MCA_VALUE_ID   = DPM12.MCA_VALUE_ID
                  WHERE  DPM16.MCA_AMND_ID    = :WS-AMND-ID
               WITH UR
           END-EXEC

           EXEC SQL
              OPEN ITM_DEF_USER
           END-EXEC
           EVALUATE SQLCODE
              WHEN 0
                 MOVE 'SP00'                TO LS-SP-RC
              WHEN OTHER
                 PERFORM 9000-SQL-ERROR
           END-EVALUATE .
      *---------------------*
       9000-SQL-ERROR.
      *------------------------*

           PERFORM 9100-DISPLAY-DATA
           MOVE 'DATABASE ERROR HAS OCCURRED. PLEASE CONTACT DTCC.'
                                            TO LS-SP-ERROR-AREA
           MOVE 'SP99'                      TO LS-SP-RC
           MOVE SQLCODE                     TO WS-SQLCODE
           DISPLAY 'PARAGRAPH-NAME          :' WS-PARAGRAPH-NAME
           DISPLAY 'SQLCODE                :' WS-SQLCODE
           PERFORM 9999-FORMAT-SQLCA
           PERFORM 9990-GOBACK
           .
      *------------------------*
       9100-DISPLAY-DATA.
      *------------------------*

           DISPLAY WS-DASHES
           DISPLAY 'PROGRAM-NAME             :' WS-PROGRAM
           DISPLAY 'PARAGRAPH-NAME           :' WS-PARAGRAPH-NAME
           DISPLAY 'SP-ERROR-AREA            :' LS-SP-ERROR-AREA
           DISPLAY 'SP-RC                    :' LS-SP-RC
           DISPLAY 'AMENDMENT-ID             :' LS-AMND-ID
           DISPLAY 'FUNCTION-TYPE            :' LS-FUNC-TYP
           DISPLAY 'TEMPLATE-TYPE            :' LS-TMPLT-TYP
           DISPLAY 'USER-INDICATOR           :' LS-USER-IND

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
               DISPLAY 'OUTSQLCA FOR DPMXMITM    :' OUTSQLCA
               DISPLAY WS-DASHES
               DISPLAY 'DPMXMITM ENDED AT        :' WS-TS
               DISPLAY WS-DASHES
            END-IF
            GOBACK
           .
      *------------------*
       9999-FORMAT-SQLCA.
      *------------------*
           PERFORM DB2000I-FORMAT-SQLCA
              THRU DB2000I-FORMAT-SQLCA-EXIT

           .

      **MOVE STATEMENTS TO FORMAT THE OUTSQLCA USING DB2000IA & DB2000IB
        COPY DB2000IC.
