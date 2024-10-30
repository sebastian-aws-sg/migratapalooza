       IDENTIFICATION DIVISION.
       PROGRAM-ID.   DPMXHEXP.
       AUTHOR.       COGNIZANT.
       DATE-WRITTEN. SEPTEMBER 2007.
      *
      *
      ****************************************************************
      *   THIS IS AN UNPUBLISHED PROGRAM OWNED BY ISCC               *
      *   IN WHICH A COPYRIGHT SUBSISTS AS OF JULY 2006.             *
      ****************************************************************
      ****************************************************************
      *                   COMPILATION INSTRUCTION                    *
      *                  COMPILE DB2 VS COBOL 370                    *
      ****************************************************************
      *    **LNKCTL**
      *    INCLUDE SYSLIB(DSNRLI)
      *    MODE AMODE(31) RMODE(ANY)
      *    ENTRY DPMXHEXP
      *    NAME  DPMXHEXP(R)
      *
      ******************************************************************
      **         P R O G R A M   D O C U M E N T A T I O N            **
      ******************************************************************
      * SYSTEM:    MCA XPRESS APPLICATION                              *
      * PROGRAM:   DPMXHEXP                                            *
      *                                                                *
      *  THIS STORED PROCEDURE IS CALLED WHEN A CLIENT COMPANY USER    *
      * OR DEALER COMPANY USER CLICKS THE PENDING MCA TAB/EXECUTED MCA *
      * TAB OR A ADMIN USER LOGS INTO ADMIN HOME PAGE.                 *
      ******************************************************************
      * TABLES:                                                        *
      * -------                                                        *
      * D0005      - COMPANY       TABLE FOR MCA
      * D0003     - COMPANY USER  TABLE FOR MCA
      * D0004       - DOCUMENT USER TABLE FOR MCA
      * VDPM10_MCA_LOCK       - LOCK TABLE    TABLE FOR MCA
      * VDPM12_MCA_DOC        - DOCUMENT      TABLE FOR MCA
      * D0006      - TEMPLATE      TABLE FOR MCA
      * VDPM15_TMPLT_WORK     - TEMPLATE WORK TABLE FOR MCA
      * VDTM54_DEBUG_CNTRL    - DEBUG CONTROL TABLE FOR MCA
      *----------------------------------------------------------------*
      * INCLUDES:                                                      *
      * ---------                                                      *
      * SQLCA    - DB2 COMMAREA                                        *
      * DPM0101  - COMPANY       TABLE FOR MCA
      * DPM0301  - COMPANY USER  TABLE FOR MCA
      * DPM0901  - DOCUMENT USER TABLE FOR MCA
      * DPM1001  - LOCK TABLE    TABLE FOR MCA
      * DPM1201  - DOCUMENT      TABLE FOR MCA
      * DPM1401  - TEMPLATE      TABLE FOR MCA
      * DPM1501  - TEMPLATE WORK TABLE FOR MCA
      * DTM5401  - DEBUG CONTROL TABLE FOR MCA
      *----------------------------------------------------------------*
      * COPYBOOK:                                                      *
      * ---------                                                      *
      * DB2000IA                                                       *
      * DB20001B                                                       *
      * DB20001C                                                       *
      *----------------------------------------------------------------*
      *              M A I N T E N A N C E   H I S T O R Y             *
      *                                                                *
      *                                                                *
      * DATE CHANGED    VERSION     PROGRAMMER                         *
      * ------------    -------     --------------------               *
      *                                                                *
      * 10/03/2007        001       COGNIZANT                          *
      *                             INITIAL IMPLEMENTATION.            *
      *                                                                *
      * 01/20/2008        002       COGNIZANT                          *
      *                             TUNED THE EXE_MCA_CSR CURSOR       *
      *                             FOR BETTER PERFORMANCE.            *
      *                                                                *
      ******************************************************************
       ENVIRONMENT DIVISION.
       DATA DIVISION.
       WORKING-STORAGE SECTION.

       01  WS-SQLCODE                     PIC -ZZZ9.
       01  WS-PROGRAM                     PIC X(08) VALUE 'DPMXHEXP'.
       01  WS-INVLD-CMPNY-ID              PIC X(50) VALUE
           'Invalid Company Id'.
       01  WS-EMPTY-CMPNY-ID              PIC X(50) VALUE
           'Company Id is empty'.
       01  WS-EMPTY-TAB-IND               PIC X(50) VALUE
           'Tab indicator is empty'.
       01  WS-EMPTY-USER-ID               PIC X(50) VALUE
           'User id is empty'.
       01  WS-INVLD-TAB-IND               PIC X(50) VALUE
           'Invalid Tab indicator'.
       01  WS-DASHES                      PIC X(80) VALUE ALL '='.
       01  WS-ERROR-AREA.
           05 WS-PARAGRAPH-NAME           PIC X(40).
       01  WS-TAB-IND-SW                  PIC X(01).
           88 ADMIN-HOMEPAGE-TAB          VALUE 'A'.
           88 PENDING-MCA-TAB             VALUE 'P'.
           88 EXECUTED-MCA-TAB            VALUE 'E'.
       01  WS-CLIENT-ID                   PIC X(8).
       01  WS-DEALER-ID                   PIC X(8).
       01  WS-CMPNY-ID-HIGH               PIC X(8)  VALUE HIGH-VALUES.
       01  WS-CMPNY-ID-LOW                PIC X(8)  VALUE LOW-VALUES.
       01  WS-TS                          PIC X(26).
       01  WS-DISPLAY-SWITCH              PIC X(01) VALUE 'N'.
           88 DISPLAY-PARAMETERS                    VALUE 'Y'.
           88 HIDE-PARAMETERS                       VALUE 'N'.

      **SQL COMMUNICATIONS AREA PASSED BACK IN OUTSQLCA
           EXEC SQL
              INCLUDE SQLCA
           END-EXEC

           EXEC SQL
              INCLUDE DPM0101
           END-EXEC

           EXEC SQL
              INCLUDE DPM0301
           END-EXEC

           EXEC SQL
              INCLUDE DPM0901
           END-EXEC

           EXEC SQL
              INCLUDE DPM1001
           END-EXEC

           EXEC SQL
              INCLUDE DPM1201
           END-EXEC

           EXEC SQL
              INCLUDE DPM1401
           END-EXEC

           EXEC SQL
              INCLUDE DPM1501
           END-EXEC

      * INCLUDE FOR VDTM54_DEBUG_CNTRL                                  00024910
           EXEC SQL                                                     00024920
                INCLUDE DTM5401                                         00024930
           END-EXEC.                                                    00024940
      **DB2 STANDARD COPYBOOK WITH FORMATTED DISPLAY SQLCA
      **THIS MUST REMAIN AS THE LAST ENTRY IN WORKING STORAGE
       COPY  DB2000IA.

       LINKAGE SECTION.
      **PICTURE CLAUSE FOR OUTSQLCA - PIC X(179) - FOR LINKAGE SECTION
       COPY  DB2000IB.

       01  LS-SP-ERROR-AREA               PIC X(80).
       01  LS-SP-RC                       PIC X(04).
       01  LS-CMPNY-ID                    PIC X(08).
       01  LS-TAB-IND                     PIC X(01).
       01  LS-USER-ID                     PIC X(10).

       PROCEDURE DIVISION USING  OUTSQLCA,
                                 LS-SP-ERROR-AREA,
                                 LS-SP-RC,
                                 LS-CMPNY-ID,
                                 LS-TAB-IND,
                                 LS-USER-ID.
      *----------*
       0000-MAIN.
      *----------*

           PERFORM 1000-INITIALIZE

           PERFORM 2000-VALIDATE-INPUT

           PERFORM 3000-PROCESS-REQ

           PERFORM 9990-GOBACK

           .
      *------------------------*
       1000-INITIALIZE.
      *------------------------*

           MOVE '1000-INITIALIZE'                   TO WS-PARAGRAPH-NAME
           MOVE SPACES                              TO OUTSQLCA
                                                      ,LS-SP-ERROR-AREA
           MOVE 'SP00'                              TO LS-SP-RC
           MOVE LS-TAB-IND                          TO WS-TAB-IND-SW
           MOVE FUNCTION UPPER-CASE(LS-USER-ID)     TO LS-USER-ID

                                                                        00051700
           EXEC SQL                                                     00051800
                SELECT ACTVT_DSPLY_IN                                   00051900
                  INTO :D054-ACTVT-DSPLY-IN                             00052010
                FROM VDTM54_DEBUG_CNTRL                                 00052040
                WHERE PRGM_ID = :WS-PROGRAM                             00052050
           END-EXEC                                                     00052060
                                                                        00052070
           MOVE SQLCODE                     TO WS-SQLCODE               00052080
                                                                        00052090
           EVALUATE SQLCODE                                             00052091
              WHEN 0                                                    00052092
                 IF D054-ACTVT-DSPLY-IN = 'Y'                           00052094
                    SET DISPLAY-PARAMETERS TO TRUE                      00052095
                    EXEC SQL
                       SET :WS-TS = CURRENT TIMESTAMP
                    END-EXEC
                 END-IF                                                 00052099
              WHEN 100                                                  00052092
                 CONTINUE
              WHEN OTHER                                                00052092
                 PERFORM 9000-SQL-ERROR
           END-EVALUATE                                                 00052102

           IF DISPLAY-PARAMETERS
              DISPLAY WS-DASHES
              DISPLAY 'DPMXHEXP STARTED AT      :' WS-TS
              DISPLAY WS-DASHES
           END-IF

           .
      *------------------------*
       2000-VALIDATE-INPUT.
      *------------------------*

           MOVE '2000-VALIDATE-INPUT'               TO WS-PARAGRAPH-NAME

           IF LS-CMPNY-ID = SPACES
              MOVE WS-EMPTY-CMPNY-ID                TO LS-SP-ERROR-AREA
              MOVE 'SP50'                           TO LS-SP-RC
              PERFORM 9100-DISPLAY-DATA
              PERFORM 9990-GOBACK
           ELSE
              PERFORM 2100-VALIDATE-CMPNY-ID
           END-IF

           IF LS-TAB-IND = SPACES
              MOVE WS-EMPTY-TAB-IND                 TO LS-SP-ERROR-AREA
              MOVE 'SP50'                           TO LS-SP-RC
              PERFORM 9100-DISPLAY-DATA
              PERFORM 9990-GOBACK
           END-IF

           .
      *--------------------------*
       2100-VALIDATE-CMPNY-ID.
      *--------------------------*

           MOVE '2100-VALIDATE-CMPNY-ID'            TO WS-PARAGRAPH-NAME
           MOVE LS-CMPNY-ID                         TO D001-CMPNY-ID

           EXEC SQL
              SELECT CMPNY_TYPE_CD
                INTO :D001-CMPNY-TYPE-CD
                FROM D0005
                WHERE CMPNY_ID = :D001-CMPNY-ID
           END-EXEC

           EVALUATE SQLCODE

              WHEN ZEROES
                 CONTINUE
              WHEN +100
                 MOVE 'SP50'                        TO LS-SP-RC
                 MOVE WS-INVLD-CMPNY-ID             TO LS-SP-ERROR-AREA
                 PERFORM 9100-DISPLAY-DATA
                 PERFORM 9990-GOBACK
              WHEN OTHER
                 PERFORM 9000-SQL-ERROR
           END-EVALUATE

           .
      *--------------------------*
       3000-PROCESS-REQ.
      *--------------------------*

           MOVE '3000-PROCESS-REQ'                  TO WS-PARAGRAPH-NAME

           EVALUATE TRUE
             WHEN PENDING-MCA-TAB
                PERFORM 3100-SET-DEALER-CLIENT-ID
                PERFORM 5000-PENDING-MCA-CSR
             WHEN EXECUTED-MCA-TAB
                PERFORM 6000-EXECUTED-MCA-CSR
             WHEN ADMIN-HOMEPAGE-TAB
                PERFORM 4000-ADMIN-HOMEPAGE-TAB-CSR
             WHEN OTHER
                MOVE WS-INVLD-TAB-IND               TO LS-SP-ERROR-AREA
                MOVE 'SP50'                         TO LS-SP-RC
                PERFORM 9100-DISPLAY-DATA
                PERFORM 9990-GOBACK
           END-EVALUATE

           IF DISPLAY-PARAMETERS
              PERFORM 9100-DISPLAY-DATA
           END-IF

           .
      *--------------------------*
       3100-SET-DEALER-CLIENT-ID.
      *--------------------------*

           MOVE '3100-SET-DEALER-CLIENT-ID'         TO WS-PARAGRAPH-NAME

           IF D001-CMPNY-TYPE-CD = 'D'
              MOVE LS-CMPNY-ID                      TO WS-DEALER-ID
           ELSE
              MOVE SPACES                           TO WS-DEALER-ID
           END-IF
           MOVE LS-CMPNY-ID                         TO WS-CLIENT-ID

           .
      *----------------------------*
       4000-ADMIN-HOMEPAGE-TAB-CSR.
      *----------------------------*

           MOVE '4000-ADMIN-HOMEPAGE-TAB-CSR'       TO WS-PARAGRAPH-NAME
           IF DISPLAY-PARAMETERS
              DISPLAY 'OPEN ADMIN-HOMEPAGE-TAB-CSR CURSOR'
           END-IF
      *    THE LIST CURSOR FOR ADMIN HOME PAGE.

           EXEC SQL
              DECLARE ADMIN_HOME_PG_CSR CURSOR WITH HOLD WITH RETURN FOR
                 SELECT
1                     ' '                          AS COMPANY_ID
2                    ,' '                          AS COMPANY_NAME
3                    ,DPM14.MCA_TMPLT_ID           AS TEMPLATE_ID
4                    ,DPM14.MCA_TMPLT_SHORT_NM     AS TEMPLATE_NAME
5                    ,DPM14.ROW_UPDT_TS            AS LAST_MODIFIED_ON
6                    ,COALESCE(DPM03_1.CMPNY_USER_NM,' ')
                                                   AS LAST_MODIFIED_NM
7                    ,CASE
                        WHEN DPM14.MCA_STAT_IN = 'P'
                          THEN 'Posted'
                        WHEN DPM14.MCA_STAT_IN = 'I'
                          THEN 'In-Progress'
                        WHEN DPM14.MCA_STAT_IN = 'W'
                          THEN 'Pending approval'
                        ELSE   'New'
                      END                          AS MCA_STATUS_MSG
8                    ,CASE
                        WHEN (VALUE(DPM10.MCA_TMPLT_ID,0)) = 0
                        THEN 'N'
                        ELSE 'Y'
                      END                          AS LOCK_IND
9                    ,COALESCE(DPM03_2.CMPNY_USER_NM,' ')
                                                   AS LOCK_USER_NAME
10                   ,COALESCE(DPM10.CMPNY_USER_ID,' ')
                                                   AS LOCK_USER_ID
11                   ,COALESCE(DPM10.CMPNY_ID,' ') AS LOCK_CMPNY_ID
12                   ,' '                          AS MCA_TMPLT_TYPE
13                   ,'Y'                          AS MCA_REG_CMPNY_IND
14                   ,' '                          AS DOC_ID
15                   ,' '                          AS DOC_NAME
16                   ,CASE
                         WHEN DPM14.MCA_STAT_IN = 'P'
                           THEN CHAR(DATE(DPM14.ROW_UPDT_TS))
                         ELSE '01/01/1900'
                      END                          AS DATE_POSTED
17                   ,CHAR(DPM14.MCA_PBLTN_DT)    AS MCA_AGREEMENT_DATE
18                   ,COALESCE(DPM03_1.CMPNY_USER_ID,' ')
                                                   AS LAST_MODIFIED_ID
19                   ,ISDA.MCA_TMPLT_NM            AS LONG_NAME
                 FROM D0006 DPM14
                         LEFT OUTER JOIN
                      VDPM10_MCA_LOCK       DPM10
                         ON
                      DPM14.MCA_TMPLT_ID      = DPM10.MCA_TMPLT_ID
                         LEFT OUTER JOIN
                      D0003 DPM03_2
                         ON
                      DPM10.CMPNY_USER_ID     = DPM03_2.CMPNY_USER_ID
                  AND DPM10.CMPNY_ID          = DPM03_2.CMPNY_ID
                         LEFT OUTER JOIN
                      D0003 DPM03_1
                         ON
                      DPM14.ROW_UPDT_USER_ID  = DPM03_1.CMPNY_USER_ID
                  AND DPM03_1.CMPNY_ID        = :LS-CMPNY-ID
                     ,D0006 ISDA
              WHERE DPM14.MCA_TMPLT_TYPE_CD  = 'I'
                AND DPM14.MCA_ISDA_TMPLT_ID  = ISDA.MCA_TMPLT_ID
              ORDER BY MCA_STATUS_MSG ASC, DPM14.ROW_UPDT_TS DESC
           END-EXEC

           EXEC SQL
              OPEN ADMIN_HOME_PG_CSR
           END-EXEC

           EVALUATE SQLCODE
              WHEN ZEROES
                 CONTINUE
              WHEN OTHER
                 PERFORM 9000-SQL-ERROR
           END-EVALUATE

           .
      *----------------------------*
       5000-PENDING-MCA-CSR.
      *----------------------------*

           MOVE '5000-PENDING-MCA-CSR'              TO WS-PARAGRAPH-NAME

           IF DISPLAY-PARAMETERS
              DISPLAY 'OPEN PENDING-MCA-CSR CURSOR'
           END-IF

      *    THE PENDING MCA TAB CURSOR FOR DEALER

           EXEC SQL
              DECLARE PEND_MCA_CSR CURSOR WITH HOLD WITH RETURN FOR
                 SELECT
1                     DPM14.CLNT_CMPNY_ID          AS COMPANY_ID
2                    ,DPM01.CMPNY_NM               AS COMPANY_NAME
3                    ,DPM14.MCA_TMPLT_ID AS TEMPLATE_ID
4                    ,DPM14.MCA_TMPLT_SHORT_NM     AS TEMPLATE_NAME
5                    ,CASE
                        WHEN COALESCE(TMPLT_WORK.DELR_STAT_CD,
                                      DPM14.MCA_DELR_STAT_CD) = 'P'
                        THEN COALESCE(TMPLT_WORK.LAST_UPDATED_TS,
                                      DPM14.ROW_UPDT_TS)
                        ELSE DPM14.ROW_UPDT_TS
                      END                          AS LAST_UPDATED
6                    ,CASE
                        WHEN COALESCE(TMPLT_WORK.DELR_STAT_CD,
                                      DPM14.MCA_DELR_STAT_CD) = 'P'
                        THEN COALESCE(TMPLT_WORK.LAST_MOD_NM,
                                      DPM03_2.CMPNY_USER_NM)
                        ELSE DPM03_2.CMPNY_USER_NM
                      END                          AS LAST_MODIFIED_NM
7                    ,CASE
                        WHEN DPM14.MCA_DELR_STAT_CD = 'P'
                          THEN
                           CASE
                             WHEN COALESCE(TMPLT_WORK.DELR_STAT_CD,
                                           DPM14.MCA_DELR_STAT_CD) = 'P'
                              AND COALESCE(TMPLT_WORK.CLNT_STAT_CD,
                                           DPM14.MCA_CLNT_STAT_CD) = 'D'
                              THEN 'Pending Dealer Review'
                             WHEN COALESCE(TMPLT_WORK.DELR_STAT_CD,
                                           DPM14.MCA_DELR_STAT_CD) = 'P'
                              AND COALESCE(TMPLT_WORK.CLNT_STAT_CD,
                                           DPM14.MCA_CLNT_STAT_CD) = ' '
                              THEN 'Pending Dealer Review'
                             WHEN COALESCE(TMPLT_WORK.DELR_STAT_CD,
                                           DPM14.MCA_DELR_STAT_CD) = 'D'
                              AND COALESCE(TMPLT_WORK.CLNT_STAT_CD,
                                           DPM14.MCA_CLNT_STAT_CD) = 'P'
                              THEN 'Pending Counterparty Review'
                             WHEN COALESCE(TMPLT_WORK.DELR_STAT_CD,
                                           DPM14.MCA_DELR_STAT_CD) = 'P'
                              AND COALESCE(TMPLT_WORK.CLNT_STAT_CD,
                                           DPM14.MCA_CLNT_STAT_CD) = 'A'
                              THEN 'Pending Execution'
                           END
                        WHEN DPM14.MCA_DELR_STAT_CD = 'D'
                           THEN 'Pending Counterparty Review'
                      END                          AS MCA_STATUS_MSG
8                    ,CASE
                       WHEN DPM14.MCA_DELR_STAT_CD = 'P'
                         THEN CASE
                                WHEN(COALESCE(DPM10.MCA_TMPLT_ID,0)) = 0
                                 THEN 'N'
                                ELSE 'Y'
                              END
                       ELSE 'N'
                      END                          AS LOCK_IND
9                    ,CASE
                       WHEN DPM14.MCA_DELR_STAT_CD = 'P'
                         THEN COALESCE(DPM03_1.CMPNY_USER_NM,' ')
                       ELSE ' '
                      END                          AS LOCK_USER_NAME
10                   ,CASE
                       WHEN DPM14.MCA_DELR_STAT_CD = 'P'
                         THEN COALESCE(DPM10.CMPNY_USER_ID,' ')
                       ELSE ' '
                      END                          AS LOCK_USER_ID
11                   ,CASE
                       WHEN DPM14.MCA_DELR_STAT_CD = 'P'
                         THEN COALESCE(DPM10.CMPNY_ID,' ')
                       ELSE ' '
                      END                          AS LOCK_CMPNY_ID
12                   ,' '                          AS MCA_TMPLT_TYPE
13                   ,'Y'                          AS MCA_REG_CMPNY_IND
14                   ,' '                          AS DOC_ID
15                   ,' '                          AS DOC_NAME
16                   ,' '                          AS DATE_POSTED
17                   ,' '                          AS MCA_AGREEMENT_DATE
18                   ,CASE
                        WHEN COALESCE(TMPLT_WORK.DELR_STAT_CD,
                                      DPM14.MCA_DELR_STAT_CD) = 'P'
                        THEN COALESCE(TMPLT_WORK.LAST_MOD_ID,
                                      DPM14.ROW_UPDT_USER_ID)
                        ELSE DPM14.ROW_UPDT_USER_ID
                      END                          AS LAST_MODIFIED_ID
19                   ,ISDA.MCA_TMPLT_NM            AS LONG_NAME
                 FROM D0006       DPM14
                        LEFT OUTER JOIN
                      VDPM10_MCA_LOCK        DPM10
                         ON
                      DPM14.MCA_TMPLT_ID       = DPM10.MCA_TMPLT_ID
                        LEFT OUTER JOIN
                      D0003      DPM03_1
                         ON
                        DPM10.CMPNY_USER_ID    = DPM03_1.CMPNY_USER_ID
                    AND DPM10.CMPNY_ID         = DPM03_1.CMPNY_ID
                        LEFT OUTER JOIN
                     (SELECT
                         DPM15.MCA_TMPLT_ID        AS TMPLT_WORK_ID
                        ,DPM15.ROW_UPDT_TS         AS LAST_UPDATED_TS
                        ,DPM03_3.CMPNY_USER_NM     AS LAST_MOD_NM
                        ,DPM03_3.CMPNY_USER_ID     AS LAST_MOD_ID
                        ,DPM15.MCA_DELR_STAT_CD    AS DELR_STAT_CD
                        ,DPM15.MCA_CLNT_STAT_CD    AS CLNT_STAT_CD
                         FROM VDPM15_TMPLT_WORK DPM15
                             ,D0003 DPM03_3
                             ,D0005  DPM01_2
                         WHERE DPM15.ROW_UPDT_USER_ID =
                                                   DPM03_3.CMPNY_USER_ID
                           AND DPM15.CLNT_CMPNY_ID = DPM01_2.CMPNY_ID
                      )
                      TMPLT_WORK
                         ON
                        DPM14.MCA_TMPLT_ID   = TMPLT_WORK.TMPLT_WORK_ID
                     ,D0003      DPM03_2
                     ,D0005       DPM01
                     ,D0006       ISDA
                 WHERE  DPM14.DELR_CMPNY_ID  = :WS-DEALER-ID
                    AND DPM14.MCA_DELR_STAT_CD IN ('P','D')
                    AND DPM14.MCA_CLNT_STAT_CD IN ('P','D','A',' ')
                    AND DPM14.ROW_UPDT_USER_ID = DPM03_2.CMPNY_USER_ID
                    AND DPM14.CLNT_CMPNY_ID    = DPM01.CMPNY_ID
                    AND (DPM14.CLNT_CMPNY_ID  <> DPM14.DELR_CMPNY_ID
                         OR DPM14.MCA_DELR_STAT_CD = 'P')
                    AND DPM14.MCA_ISDA_TMPLT_ID = ISDA.MCA_TMPLT_ID
                              UNION ALL
                 SELECT
1                     DPM14.DELR_CMPNY_ID          AS COMPANY_ID
2                    ,DPM01.CMPNY_NM               AS COMPANY_NAME
3                    ,DPM14.MCA_TMPLT_ID AS TEMPLATE_ID
4                    ,DPM14.MCA_TMPLT_SHORT_NM     AS TEMPLATE_NAME
5                    ,CASE
                        WHEN COALESCE(TMPLT_WORK.CLNT_STAT_CD,
                                      DPM14.MCA_CLNT_STAT_CD) = 'P'
                        THEN COALESCE(TMPLT_WORK.LAST_UPDATED_TS,
                                      DPM14.ROW_UPDT_TS)
                        ELSE DPM14.ROW_UPDT_TS
                      END                          AS LAST_UPDATED
6                    ,CASE
                        WHEN COALESCE(TMPLT_WORK.CLNT_STAT_CD,
                                      DPM14.MCA_CLNT_STAT_CD) = 'P'
                        THEN COALESCE(TMPLT_WORK.LAST_MOD_NM,
                                      DPM03_2.CMPNY_USER_NM)
                        ELSE DPM03_2.CMPNY_USER_NM
                      END                          AS LAST_MODIFIED_NM
7                    ,CASE
                        WHEN DPM14.MCA_CLNT_STAT_CD = 'P'
                           THEN
                           CASE
                             WHEN COALESCE(TMPLT_WORK.DELR_STAT_CD,
                                       DPM14.MCA_DELR_STAT_CD) = 'P'
                              AND COALESCE(TMPLT_WORK.CLNT_STAT_CD,
                                       DPM14.MCA_CLNT_STAT_CD) = 'D'
                              THEN 'Pending Dealer Review'
                             WHEN COALESCE(TMPLT_WORK.DELR_STAT_CD,
                                       DPM14.MCA_DELR_STAT_CD) = 'P'
                              AND COALESCE(TMPLT_WORK.CLNT_STAT_CD,
                                       DPM14.MCA_CLNT_STAT_CD) = ' '
                              THEN 'Pending Dealer Review'
                             WHEN COALESCE(TMPLT_WORK.DELR_STAT_CD,
                                       DPM14.MCA_DELR_STAT_CD) = 'D'
                              AND COALESCE(TMPLT_WORK.CLNT_STAT_CD,
                                       DPM14.MCA_CLNT_STAT_CD) = 'P'
                              THEN 'Pending Counterparty Review'
                             WHEN COALESCE(TMPLT_WORK.DELR_STAT_CD,
                                       DPM14.MCA_DELR_STAT_CD) = 'P'
                              AND COALESCE(TMPLT_WORK.CLNT_STAT_CD,
                                       DPM14.MCA_CLNT_STAT_CD) = 'A'
                              THEN 'Pending Execution'
                           END
                        WHEN DPM14.MCA_CLNT_STAT_CD = 'D'
                           THEN 'Pending Dealer Review'
                        WHEN DPM14.MCA_CLNT_STAT_CD = 'A'
                           THEN 'Pending Execution'
                      END                          AS MCA_STATUS_MSG
8                    ,CASE
                       WHEN DPM14.MCA_CLNT_STAT_CD = 'P'
                         THEN CASE
                                WHEN(COALESCE(DPM10.MCA_TMPLT_ID,0)) = 0
                                 THEN 'N'
                                ELSE 'Y'
                              END
                       ELSE 'N'
                      END                          AS LOCK_IND
9                    ,CASE
                       WHEN DPM14.MCA_CLNT_STAT_CD = 'P'
                         THEN COALESCE(DPM03_1.CMPNY_USER_NM,' ')
                       ELSE ' '
                      END                          AS LOCK_USER_NAME
10                   ,CASE
                       WHEN DPM14.MCA_CLNT_STAT_CD = 'P'
                         THEN COALESCE(DPM10.CMPNY_USER_ID,' ')
                       ELSE ' '
                      END                          AS LOCK_USER_ID
11                   ,CASE
                       WHEN DPM14.MCA_CLNT_STAT_CD = 'P'
                         THEN COALESCE(DPM10.CMPNY_ID,' ')
                       ELSE ' '
                      END                          AS LOCK_CMPNY_ID
12                   ,' '                          AS MCA_TMPLT_TYPE
13                   ,'Y'                          AS MCA_REG_CMPNY_IND
14                   ,' '                          AS DOC_ID
15                   ,' '                          AS DOC_NAME
16                   ,' '                          AS DATE_POSTED
17                   ,' '                          AS MCA_AGREEMENT_DATE
18                   ,CASE
                        WHEN COALESCE(TMPLT_WORK.CLNT_STAT_CD,
                                      DPM14.MCA_CLNT_STAT_CD) = 'P'
                        THEN COALESCE(TMPLT_WORK.LAST_MOD_ID,
                                      DPM14.ROW_UPDT_USER_ID)
                        ELSE DPM14.ROW_UPDT_USER_ID
                      END                          AS LAST_MODIFIED_ID
19                   ,ISDA.MCA_TMPLT_NM            AS LONG_NAME
                 FROM D0006     DPM14
                        LEFT OUTER JOIN
                      VDPM10_MCA_LOCK        DPM10
                         ON
                      DPM14.MCA_TMPLT_ID       = DPM10.MCA_TMPLT_ID
                        LEFT OUTER JOIN
                      D0003 DPM03_1
                         ON
                        DPM10.CMPNY_USER_ID    = DPM03_1.CMPNY_USER_ID
                    AND DPM10.CMPNY_ID         = DPM03_1.CMPNY_ID
                        LEFT OUTER JOIN
                     (SELECT
                         DPM15.MCA_TMPLT_ID        AS TMPLT_WORK_ID
                        ,DPM15.ROW_UPDT_TS         AS LAST_UPDATED_TS
                        ,DPM03_3.CMPNY_USER_NM     AS LAST_MOD_NM
                        ,DPM03_3.CMPNY_USER_ID     AS LAST_MOD_ID
                        ,DPM15.MCA_DELR_STAT_CD    AS DELR_STAT_CD
                        ,DPM15.MCA_CLNT_STAT_CD    AS CLNT_STAT_CD
                         FROM VDPM15_TMPLT_WORK DPM15
                             ,D0003 DPM03_3
                             ,D0005  DPM01_2
                         WHERE DPM15.ROW_UPDT_USER_ID =
                                                   DPM03_3.CMPNY_USER_ID
                           AND DPM15.CLNT_CMPNY_ID = DPM01_2.CMPNY_ID
                      )
                      TMPLT_WORK
                         ON
                        DPM14.MCA_TMPLT_ID    = TMPLT_WORK.TMPLT_WORK_ID
                     ,D0003 DPM03_2
                     ,D0005 DPM01
                     ,D0006 ISDA
                 WHERE  DPM14.CLNT_CMPNY_ID = :WS-CLIENT-ID
                    AND DPM14.MCA_DELR_STAT_CD IN ('P','D')
                    AND DPM14.MCA_CLNT_STAT_CD IN ('P','D','A')
                    AND DPM14.ROW_UPDT_USER_ID =   DPM03_2.CMPNY_USER_ID
                    AND DPM14.DELR_CMPNY_ID = DPM01.CMPNY_ID
                    AND (DPM14.CLNT_CMPNY_ID  <> DPM14.DELR_CMPNY_ID
                         OR DPM14.MCA_CLNT_STAT_CD = 'P')
                    AND DPM14.MCA_ISDA_TMPLT_ID = ISDA.MCA_TMPLT_ID
              ORDER BY COMPANY_NAME, LAST_UPDATED DESC
           END-EXEC

           EXEC SQL
              OPEN PEND_MCA_CSR
           END-EXEC

           EVALUATE SQLCODE
              WHEN ZEROES
                 CONTINUE
              WHEN OTHER
                 PERFORM 9000-SQL-ERROR
           END-EVALUATE

           .
      *----------------------------*
       6000-EXECUTED-MCA-CSR.
      *----------------------------*

           MOVE '6000-EXECUTED-MCA-CSR'             TO WS-PARAGRAPH-NAME
           IF DISPLAY-PARAMETERS
              DISPLAY 'OPEN EXECUTED-MCA-CSR CURSOR'
           END-IF
      *    THE EXECUTED MCA TAB CURSOR FOR DEALER

           EXEC SQL
              DECLARE EXE_MCA_CSR CURSOR WITH HOLD WITH RETURN FOR
                 SELECT
 1                    COALESCE(DPM09.CMPNY_ID,DPM14.CLNT_CMPNY_ID)
                                                     AS COMPANY_ID
 2                   ,CASE
                        WHEN DPM14.MCA_TMPLT_TYPE_CD = 'P'
                        THEN COALESCE(DPM01_2.CMPNY_NM,DPM02_2.CMPNY_NM)
                        ELSE COALESCE(DPM01_1.CMPNY_NM,DPM02_1.CMPNY_NM)
                      END                         AS COMPANY_NAME
 3                   ,DPM14.MCA_TMPLT_ID          AS TEMPLATE_ID
 4                   ,DPM14.MCA_TMPLT_SHORT_NM    AS TEMPLATE_NAME
 5                   ,DPM14.MCA_EXE_TS            AS EXEC_TIMESTAMP
 6                   ,DPM03_1.CMPNY_USER_NM       AS FINAL_SUBMIT_NM
 7                   ,'Executed'                  AS MCA_STATUS_MSG
 8                   ,'N'                         AS LOCK_IND
 9                   ,' '                         AS LOCK_USER_NAME
 10                  ,' '                         AS LOCK_USER_ID
 11                  ,' '                         AS LOCK_CMPNY_ID
 12                  ,CASE
                         WHEN DPM14.MCA_TMPLT_TYPE_CD = 'P'
                            THEN 'Pre-Existing'
                         ELSE 'New'
                      END                         AS MCA_TMPLT_TYPE
 13                  ,CASE
                        WHEN COALESCE(DPM01_1.CMPNY_ID,'N') = 'N'
                        THEN CASE
                               WHEN COALESCE(DPM01_2.CMPNY_ID,'N') = 'N'
                               THEN 'N'
                               ELSE 'Y'
                              END
                        ELSE 'Y'
                      END                         AS MCA_REG_CMPNY_IND
 14                  ,COALESCE(DPM12.MCA_VALUE_ID,0) AS DOC_ID
 15                  ,COALESCE(DPM12.MCA_DOC_DS,' ') AS DOC_NAME
 16                  ,' '                         AS DATE_POSTED
 17                  ,' '                         AS MCA_AGREEMENT_DATE
 18                  ,DPM03_1.CMPNY_USER_ID       AS FINAL_SUBMIT_ID
 19                  ,ISDA.MCA_TMPLT_NM           AS LONG_NAME
            FROM D0006 DPM14
                    LEFT OUTER JOIN
                 VDPM12_MCA_DOC   DPM12
                    ON
                 DPM14.MCA_TMPLT_ID            = DPM12.MCA_TMPLT_ID
             AND DPM12.MCA_DOC_TYPE_CD         = 'P'
             AND DPM12.DOC_DEL_CD              = ' '
             AND DPM12.CMPNY_ID                = :LS-CMPNY-ID
                    LEFT OUTER JOIN
                 D0004 DPM09
                    ON
                 DPM12.MCA_VALUE_ID            = DPM09.MCA_VALUE_ID
             AND DPM09.MCA_DOC_VIEW_IN         = 'N'
                    LEFT OUTER JOIN
                 D0005  DPM01_1
                    ON
                 DPM14.CLNT_CMPNY_ID           = DPM01_1.CMPNY_ID
                    LEFT OUTER JOIN
                 VDPM02_DELR_CMPNY DPM02_1
                    ON
                 DPM14.CLNT_CMPNY_ID           = DPM02_1.CMPNY_ID
                 AND DPM02_1.CRT_CMPNY_ID  BETWEEN :WS-CMPNY-ID-LOW
                                               AND :WS-CMPNY-ID-HIGH
                    LEFT OUTER JOIN
                 D0005  DPM01_2
                    ON
                 DPM09.CMPNY_ID                = DPM01_2.CMPNY_ID
                    LEFT OUTER JOIN
                 VDPM02_DELR_CMPNY DPM02_2
                    ON
                 DPM09.CMPNY_ID                = DPM02_2.CMPNY_ID
                 AND DPM02_2.CRT_CMPNY_ID  BETWEEN :WS-CMPNY-ID-LOW
                                               AND :WS-CMPNY-ID-HIGH
                ,D0003 DPM03_1
                ,D0006 ISDA
             WHERE  DPM14.DELR_CMPNY_ID        = :LS-CMPNY-ID
                AND DPM14.CLNT_CMPNY_ID  BETWEEN :WS-CMPNY-ID-LOW
                                             AND :WS-CMPNY-ID-HIGH
                AND DPM03_1.CMPNY_ID           = :LS-CMPNY-ID
                AND DPM14.DELR_CMPNY_ID        = DPM03_1.CMPNY_ID
                AND DPM14.ROW_UPDT_USER_ID     = DPM03_1.CMPNY_USER_ID
                AND DPM14.MCA_TMPLT_TYPE_CD IN ('P','E')
                AND DPM14.MCA_ISDA_TMPLT_ID    = ISDA.MCA_TMPLT_ID
                              UNION ALL
                SELECT
 1                    DPM14.DELR_CMPNY_ID         AS COMPANY_ID
 2                   ,DPM01.CMPNY_NM              AS COMPANY_NAME
 3                   ,DPM14.MCA_TMPLT_ID          AS TEMPLATE_ID
 4                   ,DPM14.MCA_TMPLT_SHORT_NM    AS TEMPLATE_NAME
 5                   ,DPM14.MCA_EXE_TS            AS EXEC_TIMESTAMP
 6                   ,DPM03.CMPNY_USER_NM         AS FINAL_SUBMIT_NM
 7                   ,'Executed'                  AS MCA_STATUS_MSG
 8                   ,'N'                         AS LOCK_IND
 9                   ,' '                         AS LOCK_USER_NAME
 10                  ,' '                         AS LOCK_USER_ID
 11                  ,' '                         AS LOCK_CMPNY_ID
 12                  ,'New'                       AS MCA_TMPLT_TYPE
 13                  ,'Y'                         AS MCA_REG_CMPNY_IND
 14                  ,0                           AS DOC_ID
 15                  ,' '                         AS DOC_NAME
 16                  ,' '                         AS DATE_POSTED
 17                  ,' '                         AS MCA_AGREEMENT_DATE
 18                  ,DPM03.CMPNY_USER_ID         AS FINAL_SUBMIT_ID
 19                  ,ISDA.MCA_TMPLT_NM           AS LONG_NAME
            FROM D0006  DPM14
                    LEFT OUTER JOIN
                 D0005  DPM01
                    ON
                 DPM14.DELR_CMPNY_ID           = DPM01.CMPNY_ID
                ,D0003 DPM03
                ,D0006 ISDA
             WHERE  DPM14.CLNT_CMPNY_ID        = :LS-CMPNY-ID
                AND DPM14.DELR_CMPNY_ID  BETWEEN :WS-CMPNY-ID-LOW
                                             AND :WS-CMPNY-ID-HIGH
                AND DPM14.DELR_CMPNY_ID        = DPM03.CMPNY_ID
                AND DPM14.ROW_UPDT_USER_ID     = DPM03.CMPNY_USER_ID
                AND DPM14.MCA_TMPLT_TYPE_CD    = 'E'
                AND DPM14.CLNT_CMPNY_ID       <> DPM14.DELR_CMPNY_ID
                AND DPM14.MCA_ISDA_TMPLT_ID    = ISDA.MCA_TMPLT_ID
              ORDER BY COMPANY_NAME, EXEC_TIMESTAMP DESC
           END-EXEC

           EXEC SQL
              OPEN EXE_MCA_CSR
           END-EXEC

           EVALUATE SQLCODE
              WHEN ZEROES
                 CONTINUE
              WHEN OTHER
                 PERFORM 9000-SQL-ERROR
           END-EVALUATE

           .
      *------------------------*
       9000-SQL-ERROR.
      *------------------------*

           PERFORM 9100-DISPLAY-DATA
           MOVE 'Database error has occurred. Please contact DTCC.'
                                            TO LS-SP-ERROR-AREA
           MOVE 'SP99'                      TO LS-SP-RC
           MOVE SQLCODE                     TO WS-SQLCODE
           DISPLAY 'PARAGRAPH-NAME          :' WS-PARAGRAPH-NAME
           DISPLAY 'SQLCODE                 :' WS-SQLCODE
           PERFORM 9990-GOBACK

           .
      *------------------------*
       9100-DISPLAY-DATA.
      *------------------------*

           IF DISPLAY-PARAMETERS
              DISPLAY WS-DASHES
              DISPLAY 'PROGRAM-NAME             :' WS-PROGRAM
              DISPLAY 'PARAGRAPH-NAME           :' WS-PARAGRAPH-NAME
              DISPLAY 'SP-ERROR-AREA            :' LS-SP-ERROR-AREA
              DISPLAY 'SP-RC                    :' LS-SP-RC
              DISPLAY 'LS-CMPNY-ID              :' LS-CMPNY-ID
              DISPLAY 'LS-TAB-IND               :' LS-TAB-IND
              DISPLAY 'LS-USER-ID               :' LS-USER-ID
           END-IF

           .
      *------------------------*
       9990-GOBACK.
      *------------------------*

            PERFORM 9999-FORMAT-SQLCA


            IF DISPLAY-PARAMETERS
               DISPLAY WS-DASHES
               DISPLAY 'OUTSQLCA FOR DPMXHEXP    :' OUTSQLCA
               DISPLAY WS-DASHES
               EXEC SQL
                  SET :WS-TS = CURRENT TIMESTAMP
               END-EXEC
               DISPLAY 'DPMXHEXP ENDED AT        :' WS-TS
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
      **MOVE STATEMENTS TO FORMAT THE OUTSQLCA USING DB2000IA & DB2000IB
        COPY DB2000IC.
