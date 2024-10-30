       IDENTIFICATION DIVISION.
       PROGRAM-ID.    DPMXMCMT.
       AUTHOR.        COGNIZANT.

      ******************************************************************
      *THIS IS AN UNPUBLISHED PROGRAM OWNED BY ISCC                    *
      *IN WHICH A COPYRIGHT SUBSISTS AS OF JULY 2006.                  *
      *                                                                *
      ******************************************************************
      **LNKCTL**
      *    INCLUDE SYSLIB(DSNRLI)
      *    MODE AMODE(31) RMODE(ANY)
      *    ENTRY DPMXMCMT
      *    NAME  DPMXMCMT(R)
      *
      ******************************************************************
      **         P R O G R A M   D O C U M E N T A T I O N            **
      ******************************************************************
      * SYSTEM:    DERIV/SERV MCA XPRESS                               *
      * PROGRAM:   DPMXMCMT                                            *
      *                                                                *
      * THIS STORED PROCEDURE IS USED  TO POPULATE THE POST / SHOW THE *
      * HISTORY OF COMMENTS FOR A PARTICULAR TERM VALUE.               *
      * THIS STORED PROCEDURE IS CALLED FROM THE WEB  WHEN THE POST /  *
      * VIEW COMMENT IS CLICKED BY THE USER FOR AN AMENDED TERM        *
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
      * VDPM16_MCA_ AMND  - THIS TABLE CONTAINS THE  DETAILS ABOUT THE *
      * VALUE OF TERM PERTAINING TO A CATEGORY WHICH IS INCLUSIVE OF   *
      * BOTH THE ISDA TERM VALUES AS WELL AS AMENDED TERM VALUES THAT  *
      * IS DIFFERENT FROM ISDA TERM VALUE                              *
      *                                                                *
      * VDPM11_MCA_CMNT - THIS TABLE CONTAINS INFORMATION OF THE       *
      * COMMENTS THAT HAS BEEN RAISED BY BOTH THE CLIENT / DEALER      *
      *                                                                *
      * VDPM02_DELR_CMPNY - CONTAINS INFORMATION ABOUT THE COMPANY WHO *
      * HAVE NOT REGISTERED WITH MCA-XPRESS BUT THE CLIENT / DEALER    *
      * WOULD LIKE TO MAINTAIN THE DOCUMENTS IN THE MCA-XPRESS         *
      *                                                                *
      * VDPM03_CMPNY_USR - CONTAINS INFORMATION OF A USER PERTAINING TO*
      * A REGISTERED MCA-XPRESS FIRM                                   *
      *                                                                *
      * VDTM54_DEBUG_CNTRL    - DEBUG CONTROL TABLE                    *
      *----------------------------------------------------------------*
      * INCLUDES:                                                      *
      * ---------                                                      *
      * SQLCA    - DB2 COMMAREA                                        *
      * DPM0301                                                        *
      * DPM1101                                                        *
      * DPM1401                                                        *
      * DPM1501                                                        *
      * DPM1601                                                        *
      * DPM1701                                                        *
      * DPM1801                                                        *
      * DPM1901                                                        *
      * DTM5401                                                        *
      *----------------------------------------------------------------*
      * COPYBOOKS:                                                     *
      * ---------                                                      *
      * DB2000IA
      * DB2000IB                                                       *
      * DB2000IC
      *----------------------------------------------------------------
      *              M A I N T E N A N C E   H I S T O R Y            *
      *                                                               *
      *                                                               *
      * DATE CHANGED    VERSION     PROGRAMMER                        *
      * ------------    -------     --------------------              *
      *                                                               *
      * 09/11/2007        00.00     COGNIZANT                         *
      * INITIAL IMPLEMENTATION                                        *
      *                                                               *
      *****************************************************************
       ENVIRONMENT DIVISION.
       DATA DIVISION.
       WORKING-STORAGE SECTION.
       01  WS-PROGRAM                      PIC X(08) VALUE 'DPMXMCMT'.
       01  WS-VARIABLES.
             05  WS-CNT                    PIC S9(4) COMP VALUE 0.
             05  WS-DASHES                 PIC X(40) VALUE ALL '='.
             05  WS-CTGRY-NM               PIC X(150).
             05  WS-TERM-NM                PIC X(150).
             05  WS-AMND-CHK               PIC S9(9) COMP-3             00560400
                                                     VALUE ZEROES.      00560500
             05  WS-AMND-ID                PIC S9(18)V USAGE COMP-3.
             05  WS-INVLD-CMT-IND          PIC X(50) VALUE
                           'INVALID COMMENT INDICATOR PASSED'.
             05  WS-INVALID-USER-IND       PIC X(50) VALUE
                           'INVALID USER INDICATOR PASSED'.
       01  WS-TS                          PIC X(26).
       01  WS-ERROR-AREA.
             05  WS-PARAGRAPH-NAME         PIC X(40).
             05  FILLER                    PIC X VALUE ','.
             05  WS-TABLE-NAME             PIC X(18).
             05  WS-SQLCODE                PIC 9(7).

       01  WS-SWITCHES.
             05  WS-USER-IND               PIC X(01).
                 88  WS-LCK-USR            VALUE 'L'.
                 88  WS-ORG-USR            VALUE 'O'.
                 88  WS-DEF-USR            VALUE 'D'.
             05  WS-CMT-IND                PIC X(01).
                 88  WS-VIEW               VALUE 'V'.
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
                INCLUDE DPM0301
           END-EXEC.

           EXEC SQL
                INCLUDE DPM1101
           END-EXEC.

           EXEC SQL
                INCLUDE DPM1401
           END-EXEC.

           EXEC SQL
                INCLUDE DPM1501
           END-EXEC.

           EXEC SQL
                INCLUDE DPM1601
           END-EXEC.

           EXEC SQL
                INCLUDE DPM1701
           END-EXEC.

           EXEC SQL
                INCLUDE DPM1801
           END-EXEC.

           EXEC SQL
                INCLUDE DPM1901
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
       01  LS-CMT-IND                      PIC X(1).
       01  LS-USER-IND                     PIC X(1).

       PROCEDURE DIVISION USING  OUTSQLCA,
                                 LS-SP-ERROR-AREA,
                                 LS-SP-RC,
                                 LS-AMND-ID,
                                 LS-CMT-IND,
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
      *-----------------*
       1000-INITIALIZE.
      *-----------------*

           MOVE '1000-INITIALIZE'           TO WS-PARAGRAPH-NAME
           MOVE SPACES                      TO LS-SP-ERROR-AREA
           MOVE 'SP00'                      TO LS-SP-RC
           MOVE FUNCTION UPPER-CASE(LS-CMT-IND)
                                            TO LS-CMT-IND
           MOVE FUNCTION UPPER-CASE(LS-USER-IND)
                                            TO LS-USER-IND
           MOVE LS-AMND-ID                  TO WS-AMND-ID
           MOVE LS-USER-IND                 TO WS-USER-IND
           MOVE LS-CMT-IND                  TO WS-CMT-IND
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
              DISPLAY 'DPMXMCMT STARTED AT      :' WS-TS
              DISPLAY WS-DASHES
           END-IF
           .
      *------------------------*
       2000-VALIDATE-INPUT.
      *------------------------*
           PERFORM 2100-VALIDATE-CMT-IND
           PERFORM 2200-VALIDATE-USR-IND  .
      *----------------------*
       2100-VALIDATE-CMT-IND.
      *----------------------*
           MOVE '2100-VALIDATE-CMT-IND'     TO WS-PARAGRAPH-NAME
           EVALUATE TRUE
              WHEN WS-VIEW
                 CONTINUE
              WHEN OTHER
                 MOVE  WS-INVLD-CMT-IND     TO LS-SP-ERROR-AREA
                 MOVE  'SP50'               TO LS-SP-RC
                 PERFORM 9990-GOBACK
           END-EVALUATE .
      *------------------------*                                        01120000
       2200-VALIDATE-USR-IND.                                           01130000
      *------------------------*                                        01140000
                                                                        01150000
           MOVE '2200-VALIDATE-USR-IND'     TO WS-PARAGRAPH-NAME        01160000
                                                                        01450000
           EVALUATE TRUE
               WHEN WS-LCK-USR
               WHEN WS-ORG-USR
               WHEN WS-DEF-USR
                 CONTINUE
               WHEN OTHER
                 MOVE  WS-INVALID-USER-IND  TO LS-SP-ERROR-AREA         01390000
                 MOVE  'SP50'               TO LS-SP-RC                 01400000
                 PERFORM 9990-GOBACK                                    01430000
           END-EVALUATE

           .                                                            01460000
      *---------------*                                                 01120000
       3000-PROCESS.                                                    01130000
      *---------------*                                                 01140000
                                                                        01150000
           MOVE '3000-PROCESS'              TO WS-PARAGRAPH-NAME        01160000
                                                                        01450000
           EVALUATE TRUE
               WHEN WS-LCK-USR
                 PERFORM 3050-CHECK-TEMPLATE
               WHEN WS-ORG-USR
                 PERFORM 3100-RETRIEVE-HDR
                 PERFORM 3200-ORG-SPEC-DETAILS
               WHEN WS-DEF-USR
                 PERFORM 3100-RETRIEVE-HDR
                 PERFORM 3300-DEF-SPEC-DETAILS
           END-EVALUATE

           .                                                            01460000
      *---------------------*                                           01120000
       3050-CHECK-TEMPLATE.
      *---------------------*                                           01140000

           MOVE '3050-CHECK-TEMPLATE'       TO WS-PARAGRAPH-NAME        01160000

           EXEC SQL
                SELECT 1
                  INTO :WS-AMND-CHK
                  FROM VDPM17_AMND_WORK
                 WHERE MCA_AMND_ID  = :WS-AMND-ID
                 WITH UR
           END-EXEC

           EVALUATE SQLCODE
              WHEN 0                                                    01700000
                 PERFORM 3120-RETRIEVE-HDR
                 PERFORM 3121-WORK-TEMPLATE-DETAILS
              WHEN 100                                                  01700000
                 PERFORM 3100-RETRIEVE-HDR
                 PERFORM 3110-MASTER-TEMPLATE-DETAILS
              WHEN OTHER                                                01750000
                 PERFORM 9000-SQL-ERROR                                 01810000
           END-EVALUATE
           .
      *-------------------*
       3100-RETRIEVE-HDR.
      *-------------------*
           MOVE '3100-RETRIEVE-HDR'         TO WS-PARAGRAPH-NAME
           EXEC SQL
  1             SELECT  DPM14.MCA_TMPLT_ID
  2                    ,DPM14.MCA_TMPLT_NM
  3                    ,DPM16.ATTRB_CTGRY_ID
  4                    ,DPM16.CTGRY_SQ
  5                    ,DPM04A.ATTRB_VALUE_DS   AS CTGRY_DESC
  6                    ,DPM16.ATTRB_TERM_ID
  7                    ,DPM16.TERM_SQ
  8                    ,DPM04B.ATTRB_VALUE_DS   AS TERM_DESC
                  INTO :D014-MCA-TMPLT-ID
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
                        AND DPM04A.ATTRB_TYPE_ID  = 'C'
                  INNER JOIN VDPM04_ATTRB_DTL     DPM04B
                         ON DPM16.ATTRB_TERM_ID  = DPM04B.ATTRB_ID
                        AND DPM04B.ATTRB_TYPE_ID   = 'T'
                  WHERE     DPM16.MCA_AMND_ID    = :WS-AMND-ID
                  WITH UR
           END-EXEC
           EVALUATE SQLCODE
              WHEN 0
                 CONTINUE
              WHEN OTHER
                 MOVE SQLCODE TO WS-SQLCODE
                 DISPLAY 'DPMXCMT SQLCODE:', WS-SQLCODE
                 PERFORM 9000-SQL-ERROR
           END-EVALUATE .
      *-----------------------------*
       3110-MASTER-TEMPLATE-DETAILS.
      *-----------------------------*

           MOVE '3110-MASTER-TEMPLATE-DETAILS'
                                            TO WS-PARAGRAPH-NAME

           EXEC SQL                                                     07090062
               DECLARE CMT_T1MSTR_CSR CURSOR WITH HOLD WITH RETURN FOR  07100062
                SELECT
  1               :D016-ATTRB-CTGRY-ID                  AS CTGRY_ID
  2              ,:D016-CTGRY-SQ                        AS CTGRY_SQ
  3              ,:WS-CTGRY-NM                          AS CTGRY_NM
  4              ,:D016-ATTRB-TERM-ID                   AS TERM_ID
  5              ,:D016-TERM-SQ                         AS TERM_SQ
  6              ,:WS-TERM-NM                           AS TERM_NM
  7              ,COALESCE(CMT.MCA_VALUE_ID,0)          AS VALUE_ID
  8              ,COALESCE(DPM11.ROW_UPDT_USER_ID,' ')  AS UPDT_USR_ID
  9              ,COALESCE(DPM03.CMPNY_USER_NM,' ')     AS CMPNY_NM
  10             ,COALESCE(CHAR(DPM11.ROW_UPDT_TS),' ') AS UPDT_TS
  11             ,COALESCE(DPM11.CMNT_TX,' ')           AS CMNT_TX
             FROM
                (SELECT DPM16.MCA_AMND_ID
                       ,COALESCE(DPM19.MCA_VALUE_ID,DPM18.MCA_VALUE_ID)
                                                AS MCA_VALUE_ID
                       ,COALESCE(DPM19.MCA_VALUE_TYPE_CD,
                        DPM18.MCA_VALUE_TYPE_CD) AS MCA_VALUE_TYPE_CD
                 FROM            VDPM16_MCA_AMND    DPM16
                 LEFT OUTER JOIN VDPM19_LINK_WORK   DPM19
                         ON DPM19.MCA_AMND_ID       = DPM16.MCA_AMND_ID
                        AND DPM19.MCA_VALUE_TYPE_CD = 'C'
                        AND DPM19.MCA_ACCS_STAT_CD  = 'U'
                 LEFT OUTER JOIN VDPM18_MCA_LINK    DPM18
                         ON DPM18.MCA_AMND_ID       = DPM16.MCA_AMND_ID
                        AND DPM18.MCA_VALUE_TYPE_CD = 'C'
                      WHERE DPM16.MCA_AMND_ID = :WS-AMND-ID)  CMT
                 LEFT OUTER JOIN VDPM11_MCA_CMNT        DPM11
                     ON CMT.MCA_VALUE_ID          = DPM11.MCA_VALUE_ID
             LEFT OUTER JOIN D0003      DPM03
                     ON DPM11.ROW_UPDT_USER_ID  = DPM03.CMPNY_USER_ID
             ORDER BY DPM11.ROW_UPDT_TS DESC
             WITH UR
           END-EXEC

           EXEC SQL
              OPEN CMT_T1MSTR_CSR
           END-EXEC

           EVALUATE SQLCODE
              WHEN 0
              WHEN 100
                 MOVE 'SP00'                TO LS-SP-RC
              WHEN OTHER
                 PERFORM 9000-SQL-ERROR
           END-EVALUATE

           .
      *-------------------*
       3120-RETRIEVE-HDR.
      *-------------------*
           MOVE '3120-RETRIEVE-HDR'         TO WS-PARAGRAPH-NAME
           EXEC SQL
   1            SELECT  DPM15.MCA_TMPLT_ID
   2                   ,DPM15.MCA_TMPLT_NM
   3                   ,DPM17.ATTRB_CTGRY_ID
   4                   ,DPM17.CTGRY_SQ
   5                   ,DPM04A.ATTRB_VALUE_DS   AS CTGRY_DESC
   6                   ,DPM17.ATTRB_TERM_ID
   7                   ,DPM17.TERM_SQ
   8                   ,DPM04B.ATTRB_VALUE_DS   AS TERM_DESC
                  INTO :D015-MCA-TMPLT-ID
                      ,:D015-MCA-TMPLT-NM
                      ,:D017-ATTRB-CTGRY-ID
                      ,:D017-CTGRY-SQ
                      ,:WS-CTGRY-NM
                      ,:D017-ATTRB-TERM-ID
                      ,:D017-TERM-SQ
                      ,:WS-TERM-NM
                  FROM       VDPM15_TMPLT_WORK    DPM15
                  INNER JOIN VDPM17_AMND_WORK     DPM17
                         ON DPM15.MCA_TMPLT_ID   = DPM17.MCA_TMPLT_ID
                  INNER JOIN VDPM04_ATTRB_DTL     DPM04A
                         ON DPM17.ATTRB_CTGRY_ID = DPM04A.ATTRB_ID
                        AND DPM04A.ATTRB_TYPE_ID  = 'C'
                  INNER JOIN VDPM04_ATTRB_DTL     DPM04B
                         ON DPM17.ATTRB_TERM_ID  = DPM04B.ATTRB_ID
                        AND DPM04B.ATTRB_TYPE_ID   = 'T'
                  WHERE     DPM17.MCA_AMND_ID    = :WS-AMND-ID
                  WITH UR
           END-EXEC
           EVALUATE SQLCODE
              WHEN 0
                 CONTINUE
              WHEN OTHER
                 MOVE SQLCODE TO WS-SQLCODE
                 DISPLAY 'DPMXCMT SQLCODE:', WS-SQLCODE
                 PERFORM 9000-SQL-ERROR
           END-EVALUATE .
      *-----------------------------*
       3121-WORK-TEMPLATE-DETAILS.
      *-----------------------------*

           MOVE '3121-WORK-TEMPLATE-DETAILS'
                                            TO WS-PARAGRAPH-NAME

           EXEC SQL                                                     07090062
              DECLARE CMT_T1WORK_CSR CURSOR WITH HOLD WITH RETURN FOR   07100062
                SELECT
  1              :D017-ATTRB-CTGRY-ID                   AS CTGRY_ID
  2             ,:D017-CTGRY-SQ                         AS CTGRY_SQ
  3             ,:WS-CTGRY-NM                           AS CTGRY_NM
  4             ,:D017-ATTRB-TERM-ID                    AS TERM_ID
  5             ,:D017-TERM-SQ                          AS TERM_SQ
  6             ,:WS-TERM-NM                            AS TERM_NM
  7             ,COALESCE(DPM11.MCA_VALUE_ID,0)         AS VALUE_ID
  8             ,COALESCE(DPM11.ROW_UPDT_USER_ID,' ')   AS UPDT_USR_ID
  9             ,COALESCE(DPM03.CMPNY_USER_NM,' ')      AS CMPNY_NM
  10            ,COALESCE(CHAR(DPM11.ROW_UPDT_TS),' ')  AS UPDT_TS
  11            ,COALESCE(DPM11.CMNT_TX,' ')            AS CMNT_TX
                FROM            VDPM17_AMND_WORK    DPM17
                LEFT OUTER JOIN VDPM19_LINK_WORK    DPM19
                       ON DPM19.MCA_AMND_ID       = DPM17.MCA_AMND_ID
                      AND DPM19.MCA_VALUE_TYPE_CD = 'C'
                LEFT OUTER JOIN VDPM11_MCA_CMNT     DPM11
                       ON DPM19.MCA_VALUE_ID      = DPM11.MCA_VALUE_ID
                LEFT OUTER JOIN D0003   DPM03
                       ON DPM11.ROW_UPDT_USER_ID  = DPM03.CMPNY_USER_ID
                WHERE DPM17.MCA_AMND_ID = :WS-AMND-ID
                ORDER BY DPM11.ROW_UPDT_TS DESC
                WITH UR
           END-EXEC

           EXEC SQL
              OPEN CMT_T1WORK_CSR
           END-EXEC

           EVALUATE SQLCODE
              WHEN 0
              WHEN 100
                 MOVE 'SP00'                TO LS-SP-RC
              WHEN OTHER
                 PERFORM 9000-SQL-ERROR
           END-EVALUATE
           .
                                                                        01150000
      *---------------------*                                           01120000
       3200-ORG-SPEC-DETAILS.                                           01130000
      *---------------------*                                           01140000
                                                                        01150000
           MOVE '3200-ORG-SPEC-DETAILS'     TO WS-PARAGRAPH-NAME        01160000

           EXEC SQL
               DECLARE CMT_T2_CSR CURSOR WITH HOLD WITH RETURN FOR      07100062
                SELECT
  1               :D016-ATTRB-CTGRY-ID                  AS CTGRY_ID
  2              ,:D016-CTGRY-SQ                        AS CTGRY_SQ
  3              ,:WS-CTGRY-NM                          AS CTGRY_NM
  4              ,:D016-ATTRB-TERM-ID                   AS TERM_ID
  5              ,:D016-TERM-SQ                         AS TERM_SQ
  6              ,:WS-TERM-NM                           AS TERM_NM
  7              ,COALESCE(CMT.MCA_VALUE_ID,0)          AS VALUE_ID
  8              ,COALESCE(DPM11.ROW_UPDT_USER_ID,' ')  AS UPDT_USR_ID
  9              ,COALESCE(DPM03.CMPNY_USER_NM,' ')     AS CMPNY_NM
  10             ,COALESCE(CHAR(DPM11.ROW_UPDT_TS),' ') AS UPDT_TS
  11             ,COALESCE(DPM11.CMNT_TX,' ')           AS CMNT_TX
             FROM
                (SELECT DPM16.MCA_AMND_ID
                       ,COALESCE(DPM19.MCA_VALUE_ID,DPM18.MCA_VALUE_ID)
                                                AS MCA_VALUE_ID
                       ,COALESCE(DPM19.MCA_VALUE_TYPE_CD,
                        DPM18.MCA_VALUE_TYPE_CD) AS MCA_VALUE_TYPE_CD
                 FROM            VDPM16_MCA_AMND    DPM16
                 LEFT OUTER JOIN VDPM19_LINK_WORK   DPM19
                         ON DPM19.MCA_AMND_ID       = DPM16.MCA_AMND_ID
                        AND DPM19.MCA_VALUE_TYPE_CD = 'C'
                        AND DPM19.MCA_ACCS_STAT_CD  = 'O'
                 LEFT OUTER JOIN VDPM18_MCA_LINK    DPM18
                         ON DPM18.MCA_AMND_ID       = DPM16.MCA_AMND_ID
                        AND DPM18.MCA_VALUE_TYPE_CD = 'C'
                      WHERE DPM16.MCA_AMND_ID = :WS-AMND-ID) CMT
             LEFT OUTER JOIN VDPM11_MCA_CMNT        DPM11
                     ON CMT.MCA_VALUE_ID          = DPM11.MCA_VALUE_ID
                    AND CMT.MCA_VALUE_TYPE_CD     = 'C'
             LEFT OUTER JOIN D0003      DPM03
                     ON DPM11.ROW_UPDT_USER_ID  = DPM03.CMPNY_USER_ID
             ORDER BY DPM11.ROW_UPDT_TS DESC
             WITH UR
           END-EXEC

           EXEC SQL
              OPEN CMT_T2_CSR
           END-EXEC

           EVALUATE SQLCODE
              WHEN 0
              WHEN 100
                 MOVE 'SP00'                TO LS-SP-RC
              WHEN OTHER
                 PERFORM 9000-SQL-ERROR
           END-EVALUATE
           .                                                            01460000

      *---------------------*                                           01120000
       3300-DEF-SPEC-DETAILS.                                           01130000
      *---------------------*                                           01140000
                                                                        01150000
           MOVE '3300-DEF-SPEC-DETAILS'     TO WS-PARAGRAPH-NAME        01160000

           EXEC SQL                                                     01150000
              DECLARE CMT_T3_CSR CURSOR WITH HOLD WITH RETURN FOR       07100062
                SELECT
  1              :D016-ATTRB-CTGRY-ID                  AS CTGRY_ID
  2             ,:D016-CTGRY-SQ                        AS CTGRY_SQ
  3             ,:WS-CTGRY-NM                          AS CTGRY_NM
  4             ,:D016-ATTRB-TERM-ID                   AS TERM_ID
  5             ,:D016-TERM-SQ                         AS TERM_SQ
  6             ,:WS-TERM-NM                           AS TERM_NM
  7             ,COALESCE(DPM18.MCA_VALUE_ID,0)        AS VALUE_ID
  8             ,COALESCE(DPM11.ROW_UPDT_USER_ID,' ')  AS UPDT_USR_I
  9             ,COALESCE(DPM03.CMPNY_USER_NM,' ')     AS CMPNY_NM
  10            ,COALESCE(CHAR(DPM11.ROW_UPDT_TS),' ') AS UPDT_TS
  11            ,COALESCE(DPM11.CMNT_TX,' ')           AS CMNT_TX
                FROM            VDPM16_MCA_AMND     DPM16
                LEFT OUTER JOIN VDPM18_MCA_LINK     DPM18
                       ON DPM18.MCA_AMND_ID       = DPM16.MCA_AMND_ID
                      AND DPM18.MCA_VALUE_TYPE_CD = 'C'
                LEFT OUTER JOIN VDPM11_MCA_CMNT     DPM11
                       ON DPM18.MCA_VALUE_ID      = DPM11.MCA_VALUE_ID
                LEFT OUTER JOIN D0003   DPM03
                       ON DPM11.ROW_UPDT_USER_ID  = DPM03.CMPNY_USER_ID
                WHERE DPM16.MCA_AMND_ID = :WS-AMND-ID
                ORDER BY DPM11.ROW_UPDT_TS DESC
                WITH UR
           END-EXEC

           EXEC SQL
              OPEN CMT_T3_CSR
           END-EXEC

           EVALUATE SQLCODE
              WHEN 0
              WHEN 100
                 MOVE 'SP00'                TO LS-SP-RC
              WHEN OTHER
                 PERFORM 9000-SQL-ERROR
           END-EVALUATE

           .                                                            01460000

      *---------------------*
       9000-SQL-ERROR.
      *------------------------*

           MOVE 'DATABASE ERROR HAS OCCURRED. PLEASE CONTACT DTCC.'
                                            TO LS-SP-ERROR-AREA
           MOVE 'SP99'                      TO LS-SP-RC
           MOVE SQLCODE                     TO WS-SQLCODE
           DISPLAY 'PARAGRAPH-NAME          :' WS-PARAGRAPH-NAME
           DISPLAY 'SQLCODE                :' WS-SQLCODE
           PERFORM 9100-DISPLAY-DATA
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
           DISPLAY 'COMMENT-INDICATOR        :' LS-CMT-IND
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
               DISPLAY 'OUTSQLCA FOR DPMXMCMT    :' OUTSQLCA
               DISPLAY WS-DASHES
               DISPLAY 'DPMXMCMT ENDED AT        :' WS-TS
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