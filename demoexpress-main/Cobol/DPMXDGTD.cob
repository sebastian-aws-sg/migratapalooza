       IDENTIFICATION DIVISION.
       PROGRAM-ID.    DPMXDGTD.
       AUTHOR.        COGNIZANT.

      ******************************************************************
      *THIS IS AN UNPUBLISHED PROGRAM OWNED BY ISCC                    *
      *IN WHICH A COPYRIGHT SUBSISTS AS OF JULY 2006.                  *
      *                                                                *
      ******************************************************************
      **LNKCTL**
      *    INCLUDE SYSLIB(DSNRLI)
      *    MODE AMODE(31) RMODE(ANY)
      *    ENTRY DPMXDGTD
      *    NAME  DPMXDGTD(R)
      *
      ******************************************************************
      **         P R O G R A M   D O C U M E N T A T I O N            **
      ******************************************************************
      * SYSTEM:    DERIV/SERV MCA XPRESS                               *
      * PROGRAM:   DPMXDGTD                                            *
      *                                                                *
      *                                                                *
      * THIS STORED PROCEDURE RETREIVES THE DOCUMENT LIBRARY           *
      * INFORMATION PERTAINING TO A COMPANY IN MANAGE DOCUMENTS.       *
      * THIS STORED PROCEDURE IS UTILIZED BY PRE-EXISTING / OTHERS /   *
      * SEARCH TAB OF MANAGE DOCUMENTS.                                *
      ******************************************************************
      * TABLES:                                                        *
      * -------                                                        *
      * NSCC.TDPM09_DOC_USER - TABLE THAT CONTAINS INFORMATION ABOUT   *
      * THE COMPANY THAT IS LINKED WITH A DOCUMENT                     *
      *                                                                *
      * NSCC.TDPM12_MCA_DOC - TABLE THAT HAS THE document uploaded     *
      * by a company.                                                  *
      *                                                                *
      * NSCC.TDPM14_MCA_TMPLT - MASTER TABLE THAT HAS THE TEMPLATE     *
      * INFORMATION.                                                   *
      *                                                                *
      * NSCC.TDPM01_MCA_CMPNY- MASTER TABLE THAT HAS THE COMPANY       *
      * INFORMATION.                                                   *
      *                                                                *
      * NSCC.TDPM02_DELR_CMPNY-TABLE THAT HAS THE COMPANY INFORMATION  *
      * ADDED BY THE INFORMATION.                                      *
      *                                                                *
      * NSCC.TDPM03_CMPNY_USER-TABLE THAT HAS THE user INFORMATION     *
      * PERTAINING TO A COMPANY.                                       *
      *                                                                *
      * VDTM54_DEBUG_CNTRL    - DEBUG CONTROL TABLE                    *
      *                                                                *
      *                                                                *
      *----------------------------------------------------------------*
      * INCLUDES:
      * ---------
      * SQLCA    - DB2 COMMAREA
      * DPM0901  - DCLGEN FOR D0004
      * DPM1201  - DCLGEN FOR VDPM12_MCA_DOC
      * DPM1401  - DCLGEN FOR VDPM14_MCA_DOC
      * DPM0101  - DCLGEN FOR D0005
      * DPM0201  - DCLGEN FOR VDPM02_DELR_CMPNY
      * DPM0301  - DCLGEN FOR D0003
      * DTM5401  - DCLGEN FOR VDTM54_DEBUG_CNTRL
      *----------------------------------------------------------------
      *----------------------------------------------------------------*
      * COPYBOOK:                                                      *
      * ---------                                                      *
      * DB2000IA                                                       *
      * DB2000IB                                                       *
      * DB2000IC                                                       *
      *----------------------------------------------------------------
      *              M A I N T E N A N C E   H I S T O R Y            *
                                                                     *
      *                                                               *
      * DATE CHANGED    VERSION     PROGRAMMER                        *
      * ------------    -------     --------------------              *
      *                                                               *
      * 09/26/2007        01.00     COGNIZANT                         *
      *                             INITIAL IMPLEMENTATION            *
      *                                                               *
      * 01/20/2008        02.00     COGNIZANT                         *
      *                             TUNED THE DPMXDGTD_CSR1 CURSOR    *
      *                             FOR BETTER PERFORMANCE.           *
      *                                                               *
      *****************************************************************
       ENVIRONMENT DIVISION.
       DATA DIVISION.
       WORKING-STORAGE SECTION.
       01  WS-PROGRAM                      PIC X(08) VALUE 'DPMXDGTD'.
       01  WS-VARIABLES.
             05  WS-DASHES                 PIC X(40) VALUE ALL '='.
             05  WS-EMPTY-DOC-TYPE-CD      PIC X(50) VALUE
                 'Input document type is empty'.
             05  WS-INVLD-DOC-TYPE-CD      PIC X(50) VALUE
                 'Input document type is invalid'.
             05  WS-EMPTY-LOG-CMP-ID       PIC X(50) VALUE
                 'Login company id is empty'.
             05  WS-DOC-TYPE-IND-SW        PIC X(01).
                 88  WS-DOC-TYPE-PRE-EXIST VALUE 'P'.
                 88  WS-DOC-TYPE-OTHER     VALUE 'O'.
                 88  WS-DOC-TYPE-SPACES    VALUE ' '.
             05  WS-DONE-SWITCH            PIC X(01)   VALUE 'N'.
                 88 WS-DONE                            VALUE 'Y'.
             05  WS-CONTAINS-AST-SWITCH    PIC X(01)   VALUE 'N'.
                 88 WS-CONTAINS-AST                    VALUE 'Y'.
                 88 WS-NOT-CONTAINS-AST                VALUE 'N'.
             05  WS-IN-LOG-CMP-ID          PIC X(08).
             05  WS-IN-SEL-CMP-ID          PIC X(08).
             05  WS-CHK-DESCLN             PIC S9(4)    COMP VALUE 0.
             05  WS-CNT                    PIC S9(4)    COMP VALUE 0.
             05 WS-IN-DOC-DS-GRP-ALL       PIC X(217).
             05 WS-IN-DOC-DS-GRP           PIC X(217).
             05 WS-IN-DOC-DS-GRP-R REDEFINES WS-IN-DOC-DS-GRP.
                10 WS-IN-DOC-DS-PER        PIC X(1).
                10 WS-IN-DOC-DS-STR        PIC X(216).
             05 WS-IN-DOC-DS               PIC X(216).
             05 WS-VALUE-ID-MIN            PIC S9(18)V USAGE COMP-3     00560100
                                                    VALUE ZEROES.
             05 WS-VALUE-ID-MAX            PIC S9(18)V USAGE COMP-3     00560100
                                               VALUE 999999999999999999.
      *
       01  WS-ERROR-AREA.
             05  WS-PARAGRAPH-NAME         PIC X(40).
             05  FILLER                    PIC X VALUE ','.
             05  WS-TABLE-NAME             PIC X(128).
             05  WS-SQLCODE                PIC 9(7).

       01  WS-TS                          PIC X(26).
       01  WS-DISPLAY-SWITCH              PIC X(01) VALUE 'N'.
           88 DISPLAY-PARAMETERS                    VALUE 'Y'.
           88 HIDE-PARAMETERS                       VALUE 'N'.



      *****************************************************************
      *                        SQL INCLUDES                            *
      ******************************************************************
           EXEC SQL
                INCLUDE SQLCA
           END-EXEC.

           EXEC SQL
                INCLUDE DPM0101
           END-EXEC.

           EXEC SQL
                INCLUDE DPM0201
           END-EXEC.

           EXEC SQL
                INCLUDE DPM0301
           END-EXEC.

           EXEC SQL
                INCLUDE DPM0901
           END-EXEC.

           EXEC SQL
                INCLUDE DPM1201
           END-EXEC.

           EXEC SQL
                INCLUDE DPM1401
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
       01  LS-IN-LOG-CMP-ID                PIC X(08).
       01  LS-MCA-DOC-TYPE-CD              PIC X(01).
       01  LS-IN-DOC-DS                    PIC X(216).
       01  LS-IN-SEL-CMP-ID                PIC X(08).

       PROCEDURE DIVISION USING  OUTSQLCA,
                                 LS-SP-ERROR-AREA,
                                 LS-SP-RC,
                                 LS-IN-LOG-CMP-ID,
                                 LS-MCA-DOC-TYPE-CD,
                                 LS-IN-DOC-DS,
                                 LS-IN-SEL-CMP-ID.
      *---------*
       0000-MAIN.
      *---------*

           PERFORM 1000-INITIALIZE

           PERFORM 2000-VALIDATE-INPUT

           PERFORM 3000-SELECT-DOCUMENT-DTLS

           IF DISPLAY-PARAMETERS
              PERFORM 9100-DISPLAY-DATA
           END-IF


           PERFORM 9990-GOBACK
           .
      *------------------------*
       1000-INITIALIZE.
      *------------------------*

           MOVE '1000-INITIALIZE'          TO WS-PARAGRAPH-NAME
           MOVE SPACES                     TO OUTSQLCA, LS-SP-ERROR-AREA
           MOVE 'SP00'                     TO LS-SP-RC
           INITIALIZE WS-IN-DOC-DS-STR
      *    CONVERT THE INPUT VALUES INTO UPPER-CASE
           MOVE FUNCTION UPPER-CASE(LS-MCA-DOC-TYPE-CD)
                                           TO LS-MCA-DOC-TYPE-CD
           MOVE FUNCTION UPPER-CASE(LS-IN-DOC-DS)
                                           TO WS-IN-DOC-DS
                                                                        00051700
           EXEC SQL                                                     00051800
                SELECT ACTVT_DSPLY_IN                                   00051900
                  INTO :D054-ACTVT-DSPLY-IN                             00052010
                FROM   VDTM54_DEBUG_CNTRL                               00052040
                WHERE PRGM_ID = :WS-PROGRAM                             00052050
                WITH UR                                                 00052050
           END-EXEC                                                     00052060
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
              DISPLAY 'DPMXDGTD STARTED AT      :' WS-TS
              DISPLAY WS-DASHES
           END-IF

           .
      *------------------------*
       2000-VALIDATE-INPUT.
      *------------------------*

           MOVE '2000-VALIDATE-INPUT'      TO WS-PARAGRAPH-NAME

           IF DISPLAY-PARAMETERS
              DISPLAY WS-PARAGRAPH-NAME
           END-IF


           IF LS-MCA-DOC-TYPE-CD   > SPACES
              MOVE LS-MCA-DOC-TYPE-CD       TO  WS-DOC-TYPE-IND-SW
              IF  WS-DOC-TYPE-PRE-EXIST OR WS-DOC-TYPE-OTHER
                   OR WS-DOC-TYPE-SPACES
                  CONTINUE
              ELSE
                  MOVE WS-INVLD-DOC-TYPE-CD TO LS-SP-ERROR-AREA
                  MOVE 'SP50'               TO LS-SP-RC
                  PERFORM 9100-DISPLAY-DATA
                  PERFORM 9990-GOBACK
              END-IF
           END-IF

           IF LS-IN-LOG-CMP-ID > SPACES
              CONTINUE
           ELSE
              MOVE WS-EMPTY-LOG-CMP-ID     TO LS-SP-ERROR-AREA
              MOVE 'SP50'                  TO LS-SP-RC
              PERFORM 9100-DISPLAY-DATA
              PERFORM 9990-GOBACK
           END-IF

           IF WS-IN-DOC-DS  > SPACES
               PERFORM 2100-PROCESS-DOC-DS
           ELSE
               MOVE ALL '%'              TO WS-IN-DOC-DS-GRP-ALL
           END-IF
           .
      *------------------------*
       2100-PROCESS-DOC-DS.
      *------------------------*

           MOVE '2100-PROCESS-DOC-DS'      TO WS-PARAGRAPH-NAME

           IF DISPLAY-PARAMETERS
              DISPLAY WS-PARAGRAPH-NAME
           END-IF

           PERFORM VARYING WS-CNT FROM 100 BY -1 UNTIL WS-DONE
                                               OR WS-CNT = 0
              IF WS-IN-DOC-DS(WS-CNT:1) > ' '
                 SET WS-DONE             TO TRUE
                 MOVE WS-CNT             TO WS-CHK-DESCLN
              END-IF
           END-PERFORM


           PERFORM VARYING WS-CNT FROM 1 BY 1 UNTIL WS-CONTAINS-AST OR
                                        WS-CNT > WS-CHK-DESCLN
              IF WS-IN-DOC-DS(WS-CNT:1) = '*'
                 SET WS-CONTAINS-AST  TO TRUE
              END-IF
           END-PERFORM

           INSPECT WS-IN-DOC-DS REPLACING ALL '*'  BY '%'
           INSPECT WS-IN-DOC-DS REPLACING ALL ' '  BY '%'

           MOVE WS-IN-DOC-DS(1:WS-CHK-DESCLN)
                                         TO WS-IN-DOC-DS-STR
           MOVE ALL '%' TO WS-IN-DOC-DS-STR(WS-CHK-DESCLN + 1:)
      *
           IF WS-IN-DOC-DS-STR(1:1) = '%' OR WS-CHK-DESCLN =  1
              OR WS-NOT-CONTAINS-AST
               MOVE '%'                 TO WS-IN-DOC-DS-PER
               MOVE WS-IN-DOC-DS-GRP-R  TO WS-IN-DOC-DS-GRP-ALL
           ELSE
               MOVE WS-IN-DOC-DS-STR     TO WS-IN-DOC-DS-GRP
               MOVE WS-IN-DOC-DS-GRP     TO WS-IN-DOC-DS-GRP-ALL
               MOVE '%' TO WS-IN-DOC-DS-GRP-ALL(217:1)
           END-IF

           .
      *---------------------*
       3000-SELECT-DOCUMENT-DTLS.
      *---------------------*

           MOVE '3000-SELECT-DOCUMENT-DTLS'
                                           TO WS-PARAGRAPH-NAME

           IF DISPLAY-PARAMETERS
              DISPLAY WS-PARAGRAPH-NAME
           END-IF

           IF LS-MCA-DOC-TYPE-CD = 'P'
              PERFORM 3010-SELECT-PRE-EXIST-RCDS
           ELSE
              IF LS-MCA-DOC-TYPE-CD = 'O'
                 PERFORM 3020-SELECT-OTHER-RCDS
              ELSE
                 PERFORM 3030-SELECT-BOTH-RCDS
              END-IF
           END-IF

           .
      *---------------------*
       3010-SELECT-PRE-EXIST-RCDS.
      *---------------------*

           MOVE '3010-SELECT-PRE-EXIST-RCDS'
                                           TO WS-PARAGRAPH-NAME

           IF DISPLAY-PARAMETERS
              DISPLAY WS-PARAGRAPH-NAME
           END-IF

           EXEC SQL                                                     07090062
               DECLARE DPMXDGTD_CSR1 CURSOR WITH HOLD WITH RETURN FOR   07100062
 1                SELECT CMPNY1.CMPNY_ID
 2                      ,CMPNY1.CMPNY_NM AS COUNTER_PARTY
 3                      ,CMPNY1.ORGANIZATION_IND
 4                      ,CMPNY2.TMPLT_ID
 5                      ,CMPNY2.TEMPLATE_NAME
 6                      ,CMPNY2.TEMPLATE_SHORT_NAME
 7                      ,CMPNY2.EXECUTION_DATE
 8                      ,CMPNY2.VIEW_INDICATOR AS VIEW_INDICATOR
 9                      ,CMPNY2.ROW_UPDT_USER_ID
 10                     ,CMPNY2.CMPNY_USER_NM  AS UPLOADED_BY
 11                     ,CMPNY2.ROW_UPDT_TS AS UPLOADED_ON
 12                     ,CMPNY2.DOC_ID       AS DOC_ID
 13                     ,CMPNY2.MCA_DOC_DS   AS DOCUMENT_DESC
 14                     ,CMPNY2.MCA_DOC_TYPE_CD AS DOCUMENT_TYPE
                         FROM (SELECT CMPNY_ID , CMPNY_NM
                              ,'R' AS ORGANIZATION_IND
                              FROM D0005
                              UNION ALL
                              SELECT CMPNY_ID , CMPNY_NM
                              ,'U' AS ORGANIZATION_IND
                              FROM VDPM02_DELR_CMPNY
                              WHERE
                              CRT_CMPNY_ID  = :LS-IN-LOG-CMP-ID)
                              CMPNY1
                             ,(SELECT DPM09.CMPNY_ID
                              ,DPM09.MCA_DOC_VIEW_IN AS VIEW_INDICATOR
                              ,DPM12.ROW_UPDT_USER_ID
                              ,DPM03.CMPNY_USER_NM
                              ,DPM12.ROW_UPDT_TS
                              ,DPM12.MCA_VALUE_ID  AS DOC_ID
                              ,DPM12.MCA_DOC_DS
                              ,DPM12.MCA_DOC_TYPE_CD
                              ,VALUE(DPM14.MCA_TMPLT_ID,0) AS TMPLT_ID
                              ,VALUE(DPM14.MCA_TMPLT_NM,' ')
                               AS TEMPLATE_NAME
                              ,VALUE(DPM14.MCA_TMPLT_SHORT_NM,' ')
                               AS TEMPLATE_SHORT_NAME
                              ,VALUE(DPM14.MCA_EXE_TS,
                               '1900-01-01-01.01.01.000000')
                               AS EXECUTION_DATE
                              FROM D0004 DPM09
                                  ,D0003 DPM03
                                  ,VDPM12_MCA_DOC    DPM12
                              LEFT OUTER JOIN
                              D0006 DPM14
                              ON DPM12.MCA_TMPLT_ID=DPM14.MCA_TMPLT_ID
                              WHERE (DPM09.CMPNY_ID <> :LS-IN-LOG-CMP-ID
                              AND DPM09.MCA_DOC_VIEW_IN = 'Y')
                              AND DPM09.MCA_VALUE_ID IN
                              (SELECT MCA_VALUE_ID
                              FROM D0004
                              WHERE (MCA_VALUE_ID BETWEEN
                              :WS-VALUE-ID-MIN AND :WS-VALUE-ID-MAX)
                              AND   CMPNY_ID = :LS-IN-LOG-CMP-ID
                              GROUP BY MCA_VALUE_ID)
                              AND DPM12.DOC_DEL_CD = ' '
                              AND DPM12.MCA_DOC_TYPE_CD =
                                 :LS-MCA-DOC-TYPE-CD
                            AND DPM09.MCA_VALUE_ID = DPM12.MCA_VALUE_ID
                            AND (DPM03.CMPNY_ID  = DPM12.CMPNY_ID
                                 AND DPM03.CMPNY_USER_ID =
                                 DPM12.ROW_UPDT_USER_ID)
                            AND (' '            = :LS-IN-SEL-CMP-ID
                            OR DPM09.CMPNY_ID   = :LS-IN-SEL-CMP-ID)
                            AND (DPM12.MCA_DOC_TYPE_CD
                                 = :LS-MCA-DOC-TYPE-CD AND
                            ((UCASE(DPM14.MCA_TMPLT_NM) LIKE
                            :WS-IN-DOC-DS-GRP-ALL)
                            OR (UCASE(DPM14.MCA_TMPLT_SHORT_NM) LIKE
                            :WS-IN-DOC-DS-GRP-ALL)))
                            UNION ALL
                              SELECT DPM09.CMPNY_ID
                              ,DPM09.MCA_DOC_VIEW_IN AS VIEW_INDICATOR
                              ,DPM12.ROW_UPDT_USER_ID
                              ,DPM03.CMPNY_USER_NM
                              ,DPM12.ROW_UPDT_TS
                              ,DPM12.MCA_VALUE_ID AS DOC_ID
                              ,DPM12.MCA_DOC_DS
                              ,DPM12.MCA_DOC_TYPE_CD
                              ,VALUE(DPM14.MCA_TMPLT_ID,0) AS TMPLT_ID
                              ,VALUE(DPM14.MCA_TMPLT_NM,' ')
                               AS TEMPLATE_NAME
                              ,VALUE(DPM14.MCA_TMPLT_SHORT_NM,' ')
                               AS TEMPLATE_SHORT_NAME
                              ,VALUE(DPM14.MCA_EXE_TS,
                               '1900-01-01-01.01.01.000000')
                               AS EXECUTION_DATE
                              FROM D0004 DPM09
                             ,D0003 DPM03
                             ,VDPM12_MCA_DOC    DPM12
                              LEFT OUTER JOIN
                              D0006 DPM14
                              ON DPM12.MCA_TMPLT_ID=DPM14.MCA_TMPLT_ID
                           WHERE DPM09.MCA_DOC_VIEW_IN = 'N'
                             AND DPM12.CMPNY_ID  = :LS-IN-LOG-CMP-ID
                             AND DPM12.DOC_DEL_CD = ' '
                             AND DPM12.MCA_DOC_TYPE_CD =
                                 :LS-MCA-DOC-TYPE-CD
                            AND DPM09.MCA_VALUE_ID = DPM12.MCA_VALUE_ID
                            AND (DPM03.CMPNY_ID  = DPM12.CMPNY_ID
                                 AND DPM03.CMPNY_USER_ID =
                                 DPM12.ROW_UPDT_USER_ID)
                            AND (' '            = :LS-IN-SEL-CMP-ID
                            OR DPM09.CMPNY_ID   = :LS-IN-SEL-CMP-ID)
                            AND (DPM12.MCA_DOC_TYPE_CD
                                 = :LS-MCA-DOC-TYPE-CD AND
                            ((UCASE(DPM14.MCA_TMPLT_NM) LIKE
                            :WS-IN-DOC-DS-GRP-ALL)
                            OR (UCASE(DPM14.MCA_TMPLT_SHORT_NM) LIKE
                            :WS-IN-DOC-DS-GRP-ALL))))
                             CMPNY2
                          WHERE CMPNY1.CMPNY_ID  = CMPNY2.CMPNY_ID
                          ORDER BY CMPNY1.CMPNY_NM,
                          CMPNY2.ROW_UPDT_TS DESC
                  WITH UR
           END-EXEC

           EXEC SQL
              OPEN DPMXDGTD_CSR1
           END-EXEC

           EVALUATE SQLCODE
              WHEN 0
                 CONTINUE
              WHEN OTHER
                 PERFORM 9000-SQL-ERROR
           END-EVALUATE
           .
      *---------------------*
       3020-SELECT-OTHER-RCDS.
      *---------------------*

           MOVE '3020-SELECT-OTHER-RCDS'
                                           TO WS-PARAGRAPH-NAME

           IF DISPLAY-PARAMETERS
              DISPLAY WS-PARAGRAPH-NAME
           END-IF

           EXEC SQL                                                     07090062
               DECLARE DPMXDGTD_CSR2 CURSOR WITH HOLD WITH RETURN FOR   07100062
 1                SELECT CMPNY1.CMPNY_ID
 2                      ,CMPNY1.CMPNY_NM AS COUNTER_PARTY
 3                      ,CMPNY1.ORGANIZATION_IND
 4                      ,CMPNY2.TMPLT_ID
 5                      ,CMPNY2.TEMPLATE_NAME
 6                      ,CMPNY2.TEMPLATE_SHORT_NAME
 7                      ,CMPNY2.EXECUTION_DATE
 8                      ,CMPNY2.VIEW_INDICATOR AS VIEW_INDICATOR
 9                      ,CMPNY2.ROW_UPDT_USER_ID
 10                     ,CMPNY2.CMPNY_USER_NM  AS UPLOADED_BY
 11                     ,CMPNY2.ROW_UPDT_TS AS UPLOADED_ON
 12                     ,CMPNY2.DOC_ID       AS DOC_ID
 13                     ,CMPNY2.MCA_DOC_DS   AS DOCUMENT_DESC
 14                     ,CMPNY2.MCA_DOC_TYPE_CD AS DOCUMENT_TYPE
                         FROM (SELECT CMPNY_ID , CMPNY_NM
                              ,'R' AS ORGANIZATION_IND
                              FROM D0005
                              UNION ALL
                              SELECT CMPNY_ID , CMPNY_NM
                              ,'U' AS ORGANIZATION_IND
                              FROM VDPM02_DELR_CMPNY
                              WHERE
                              CRT_CMPNY_ID  = :LS-IN-LOG-CMP-ID)
                              CMPNY1
                             ,(SELECT DPM09.CMPNY_ID
                              ,DPM09.MCA_DOC_VIEW_IN AS VIEW_INDICATOR
                              ,DPM12.ROW_UPDT_USER_ID
                              ,DPM03.CMPNY_USER_NM
                              ,DPM12.ROW_UPDT_TS
                              ,DPM12.MCA_VALUE_ID  AS DOC_ID
                              ,DPM12.MCA_DOC_DS
                              ,DPM12.MCA_DOC_TYPE_CD
                              ,VALUE(DPM14.MCA_TMPLT_ID,0) AS TMPLT_ID
                              ,VALUE(DPM14.MCA_TMPLT_NM,' ')
                               AS TEMPLATE_NAME
                              ,VALUE(DPM14.MCA_TMPLT_SHORT_NM,' ')
                               AS TEMPLATE_SHORT_NAME
                              ,VALUE(DPM14.MCA_EXE_TS,
                               '1900-01-01-01.01.01.000000')
                               AS EXECUTION_DATE
                              FROM D0004 DPM09
                                  ,D0003 DPM03
                                  ,VDPM12_MCA_DOC    DPM12
                              LEFT OUTER JOIN
                              D0006 DPM14
                              ON DPM12.MCA_TMPLT_ID=DPM14.MCA_TMPLT_ID
                              WHERE (DPM09.CMPNY_ID <> :LS-IN-LOG-CMP-ID
                              AND DPM09.MCA_DOC_VIEW_IN = 'Y')
                              AND DPM09.MCA_VALUE_ID IN
                              (SELECT MCA_VALUE_ID
                              FROM D0004
                              WHERE (MCA_VALUE_ID BETWEEN
                              :WS-VALUE-ID-MIN AND :WS-VALUE-ID-MAX)
                              AND   CMPNY_ID = :LS-IN-LOG-CMP-ID
                              GROUP BY MCA_VALUE_ID)
                              AND DPM12.DOC_DEL_CD = ' '
                              AND DPM12.MCA_DOC_TYPE_CD =
                                 :LS-MCA-DOC-TYPE-CD
                            AND DPM09.MCA_VALUE_ID = DPM12.MCA_VALUE_ID
                            AND (DPM03.CMPNY_ID  = DPM12.CMPNY_ID
                                 AND DPM03.CMPNY_USER_ID =
                                 DPM12.ROW_UPDT_USER_ID)
                            AND (' '            = :LS-IN-SEL-CMP-ID
                            OR DPM09.CMPNY_ID   = :LS-IN-SEL-CMP-ID)
                            AND (UCASE(DPM12.MCA_DOC_DS) LIKE
                            :WS-IN-DOC-DS-GRP-ALL AND
                            DPM12.MCA_DOC_TYPE_CD = 'O')
                            UNION ALL
                              SELECT DPM09.CMPNY_ID
                              ,DPM09.MCA_DOC_VIEW_IN AS VIEW_INDICATOR
                              ,DPM12.ROW_UPDT_USER_ID
                              ,DPM03.CMPNY_USER_NM
                              ,DPM12.ROW_UPDT_TS
                              ,DPM12.MCA_VALUE_ID AS DOC_ID
                              ,DPM12.MCA_DOC_DS
                              ,DPM12.MCA_DOC_TYPE_CD
                              ,VALUE(DPM14.MCA_TMPLT_ID,0) AS TMPLT_ID
                              ,VALUE(DPM14.MCA_TMPLT_NM,' ')
                               AS TEMPLATE_NAME
                              ,VALUE(DPM14.MCA_TMPLT_SHORT_NM,' ')
                               AS TEMPLATE_SHORT_NAME
                              ,VALUE(DPM14.MCA_EXE_TS,
                               '1900-01-01-01.01.01.000000')
                               AS EXECUTION_DATE
                              FROM D0004 DPM09
                             ,D0003 DPM03
                             ,VDPM12_MCA_DOC    DPM12
                              LEFT OUTER JOIN
                              D0006 DPM14
                              ON DPM12.MCA_TMPLT_ID=DPM14.MCA_TMPLT_ID
                             WHERE DPM09.MCA_DOC_VIEW_IN = 'N'
                             AND DPM12.CMPNY_ID  = :LS-IN-LOG-CMP-ID
                             AND DPM12.DOC_DEL_CD = ' '
                              AND DPM12.MCA_DOC_TYPE_CD =
                                 :LS-MCA-DOC-TYPE-CD
                            AND DPM09.MCA_VALUE_ID = DPM12.MCA_VALUE_ID
                            AND (DPM03.CMPNY_ID  = DPM12.CMPNY_ID
                                 AND DPM03.CMPNY_USER_ID =
                                 DPM12.ROW_UPDT_USER_ID)
                            AND (' '            = :LS-IN-SEL-CMP-ID
                            OR DPM09.CMPNY_ID   = :LS-IN-SEL-CMP-ID)
                            AND (UCASE(DPM12.MCA_DOC_DS) LIKE
                            :WS-IN-DOC-DS-GRP-ALL AND
                            DPM12.MCA_DOC_TYPE_CD =
                              :LS-MCA-DOC-TYPE-CD))
                             CMPNY2
                          WHERE CMPNY1.CMPNY_ID  = CMPNY2.CMPNY_ID
                          ORDER BY CMPNY1.CMPNY_NM,
                          CMPNY2.ROW_UPDT_TS DESC
                  WITH UR
           END-EXEC

           EXEC SQL
              OPEN DPMXDGTD_CSR2
           END-EXEC

           EVALUATE SQLCODE
              WHEN 0
                 CONTINUE
              WHEN OTHER
                 PERFORM 9000-SQL-ERROR
           END-EVALUATE
           .
      *---------------------*
       3030-SELECT-BOTH-RCDS.
      *---------------------*

           MOVE '3030-SELECT-BOTH-RCDS'
                                           TO WS-PARAGRAPH-NAME

           IF DISPLAY-PARAMETERS
              DISPLAY WS-PARAGRAPH-NAME
           END-IF

           EXEC SQL                                                     07090062
               DECLARE DPMXDGTD_CSR3 CURSOR WITH HOLD WITH RETURN FOR   07100062
 1                SELECT CMPNY1.CMPNY_ID
 2                      ,CMPNY1.CMPNY_NM AS COUNTER_PARTY
 3                      ,CMPNY1.ORGANIZATION_IND
 4                      ,CMPNY2.TMPLT_ID
 5                      ,CMPNY2.TEMPLATE_NAME
 6                      ,CMPNY2.TEMPLATE_SHORT_NAME
 7                      ,CMPNY2.EXECUTION_DATE
 8                      ,CMPNY2.VIEW_INDICATOR AS VIEW_INDICATOR
 9                      ,CMPNY2.ROW_UPDT_USER_ID
 10                     ,CMPNY2.CMPNY_USER_NM  AS UPLOADED_BY
 11                     ,CMPNY2.ROW_UPDT_TS AS UPLOADED_ON
 12                     ,CMPNY2.DOC_ID       AS DOC_ID
 13                     ,CMPNY2.MCA_DOC_DS   AS DOCUMENT_DESC
 14                     ,CMPNY2.MCA_DOC_TYPE_CD AS DOCUMENT_TYPE
                         FROM (SELECT CMPNY_ID , CMPNY_NM
                              ,'R' AS ORGANIZATION_IND
                              FROM D0005
                              UNION ALL
                              SELECT CMPNY_ID , CMPNY_NM
                              ,'U' AS ORGANIZATION_IND
                              FROM VDPM02_DELR_CMPNY
                              WHERE
                              CRT_CMPNY_ID  = :LS-IN-LOG-CMP-ID)
                              CMPNY1
                             ,(SELECT DPM09.CMPNY_ID
                              ,DPM09.MCA_DOC_VIEW_IN AS VIEW_INDICATOR
                              ,DPM12.ROW_UPDT_USER_ID
                              ,DPM03.CMPNY_USER_NM
                              ,DPM12.ROW_UPDT_TS
                              ,DPM12.MCA_VALUE_ID  AS DOC_ID
                              ,DPM12.MCA_DOC_DS
                              ,DPM12.MCA_DOC_TYPE_CD
                              ,VALUE(DPM14.MCA_TMPLT_ID,0) AS TMPLT_ID
                              ,VALUE(DPM14.MCA_TMPLT_NM,' ')
                               AS TEMPLATE_NAME
                              ,VALUE(DPM14.MCA_TMPLT_SHORT_NM,' ')
                               AS TEMPLATE_SHORT_NAME
                              ,VALUE(DPM14.MCA_EXE_TS,
                               '1900-01-01-01.01.01.000000')
                               AS EXECUTION_DATE
                              FROM D0004 DPM09
                                  ,D0003 DPM03
                                  ,VDPM12_MCA_DOC    DPM12
                              LEFT OUTER JOIN
                              D0006 DPM14
                              ON DPM12.MCA_TMPLT_ID=DPM14.MCA_TMPLT_ID
                              WHERE (DPM09.CMPNY_ID <> :LS-IN-LOG-CMP-ID
                              AND DPM09.MCA_DOC_VIEW_IN = 'Y')
                              AND DPM09.MCA_VALUE_ID IN
                              (SELECT MCA_VALUE_ID
                              FROM D0004
                              WHERE (MCA_VALUE_ID BETWEEN
                              :WS-VALUE-ID-MIN AND :WS-VALUE-ID-MAX)
                              AND   CMPNY_ID = :LS-IN-LOG-CMP-ID
                              GROUP BY MCA_VALUE_ID)
                              AND DPM12.DOC_DEL_CD = ' '
                              AND (' '            = :LS-MCA-DOC-TYPE-CD
                              OR DPM12.MCA_DOC_TYPE_CD =
                                 :LS-MCA-DOC-TYPE-CD)
                            AND DPM09.MCA_VALUE_ID = DPM12.MCA_VALUE_ID
                            AND (DPM03.CMPNY_ID  = DPM12.CMPNY_ID
                                 AND DPM03.CMPNY_USER_ID =
                                 DPM12.ROW_UPDT_USER_ID)
                            AND (' '            = :LS-IN-SEL-CMP-ID
                            OR DPM09.CMPNY_ID   = :LS-IN-SEL-CMP-ID)
                            AND ((UCASE(DPM12.MCA_DOC_DS) LIKE
                            :WS-IN-DOC-DS-GRP-ALL AND
                            DPM12.MCA_DOC_TYPE_CD = 'O')
                            OR (DPM12.MCA_DOC_TYPE_CD
                                 = 'P' AND
                            ((UCASE(DPM14.MCA_TMPLT_NM) LIKE
                            :WS-IN-DOC-DS-GRP-ALL)
                            OR (UCASE(DPM14.MCA_TMPLT_SHORT_NM) LIKE
                            :WS-IN-DOC-DS-GRP-ALL))))
                            UNION ALL
                              SELECT DPM09.CMPNY_ID
                              ,DPM09.MCA_DOC_VIEW_IN AS VIEW_INDICATOR
                              ,DPM12.ROW_UPDT_USER_ID
                              ,DPM03.CMPNY_USER_NM
                              ,DPM12.ROW_UPDT_TS
                              ,DPM12.MCA_VALUE_ID AS DOC_ID
                              ,DPM12.MCA_DOC_DS
                              ,DPM12.MCA_DOC_TYPE_CD
                              ,VALUE(DPM14.MCA_TMPLT_ID,0) AS TMPLT_ID
                              ,VALUE(DPM14.MCA_TMPLT_NM,' ')
                               AS TEMPLATE_NAME
                              ,VALUE(DPM14.MCA_TMPLT_SHORT_NM,' ')
                               AS TEMPLATE_SHORT_NAME
                              ,VALUE(DPM14.MCA_EXE_TS,
                               '1900-01-01-01.01.01.000000')
                               AS EXECUTION_DATE
                              FROM D0004 DPM09
                             ,D0003 DPM03
                             ,VDPM12_MCA_DOC    DPM12
                              LEFT OUTER JOIN
                              D0006 DPM14
                              ON DPM12.MCA_TMPLT_ID=DPM14.MCA_TMPLT_ID
                             WHERE DPM09.MCA_DOC_VIEW_IN = 'N'
                             AND DPM12.CMPNY_ID  = :LS-IN-LOG-CMP-ID
                             AND DPM12.DOC_DEL_CD = ' '
                             AND ' '             = :LS-MCA-DOC-TYPE-CD
                            AND DPM09.MCA_VALUE_ID = DPM12.MCA_VALUE_ID
                            AND (DPM03.CMPNY_ID  = DPM12.CMPNY_ID
                                 AND DPM03.CMPNY_USER_ID =
                                 DPM12.ROW_UPDT_USER_ID)
                            AND (' '            = :LS-IN-SEL-CMP-ID
                            OR DPM09.CMPNY_ID   = :LS-IN-SEL-CMP-ID)
                            AND ((UCASE(DPM12.MCA_DOC_DS) LIKE
                            :WS-IN-DOC-DS-GRP-ALL AND
                            DPM12.MCA_DOC_TYPE_CD = 'O')
                            OR (DPM12.MCA_DOC_TYPE_CD
                                 = 'P' AND
                            ((UCASE(DPM14.MCA_TMPLT_NM) LIKE
                            :WS-IN-DOC-DS-GRP-ALL)
                            OR (UCASE(DPM14.MCA_TMPLT_SHORT_NM) LIKE
                            :WS-IN-DOC-DS-GRP-ALL)))))
                             CMPNY2
                          WHERE CMPNY1.CMPNY_ID  = CMPNY2.CMPNY_ID
                          ORDER BY CMPNY1.CMPNY_NM,
                          CMPNY2.ROW_UPDT_TS DESC
                  WITH UR
           END-EXEC

           EXEC SQL
              OPEN DPMXDGTD_CSR3
           END-EXEC

           EVALUATE SQLCODE
              WHEN 0
                 CONTINUE
              WHEN OTHER
                 PERFORM 9000-SQL-ERROR
           END-EVALUATE
           .
      *---------------------*
101400 9000-SQL-ERROR.
101500*------------------------*
101600
101800     PERFORM 9100-DISPLAY-DATA
087800     MOVE 'Database error has occurred. Please contact DTCC.'
087800                                      TO LS-SP-ERROR-AREA
           MOVE 'SP99'                      TO LS-SP-RC
           MOVE SQLCODE                     TO WS-SQLCODE
           DISPLAY 'PARAGRAPH-NAME           :' WS-PARAGRAPH-NAME
102700     DISPLAY 'SQLCODE                :' WS-SQLCODE
101900     PERFORM 9999-FORMAT-SQLCA
102000     PERFORM 9990-GOBACK
102100     .
102200*------------------------*
102300 9100-DISPLAY-DATA.
102400*------------------------*
102500
           IF DISPLAY-PARAMETERS
102600         DISPLAY WS-DASHES
102700         DISPLAY 'PROGRAM-NAME             :' WS-PROGRAM
102700         DISPLAY 'PARAGRAPH-NAME           :' WS-PARAGRAPH-NAME
102800         DISPLAY 'SP-ERROR-AREA            :' LS-SP-ERROR-AREA
102900         DISPLAY 'SP-RC                    :' LS-SP-RC
102900         DISPLAY 'LS-IN-LOG-CMP-ID         :' LS-IN-LOG-CMP-ID
102900         DISPLAY 'LS-MCA-DOC-TYPE-CD       :' LS-MCA-DOC-TYPE-CD
102900         DISPLAY 'LS-IN-DOC-DS             :' LS-IN-DOC-DS
102900         DISPLAY 'LS-IN-SEL-CMP-ID         :' LS-IN-SEL-CMP-ID
102600         DISPLAY WS-DASHES
106900     END-IF
107000     .
      *------------------------*
       9990-GOBACK.
      *------------------------*

            PERFORM 9999-FORMAT-SQLCA
            IF DISPLAY-PARAMETERS
               DISPLAY WS-DASHES
               DISPLAY 'OUTSQLCA FOR DPMXDGTD    :' OUTSQLCA
               DISPLAY WS-DASHES
               EXEC SQL
                   SET :WS-TS = CURRENT TIMESTAMP
               END-EXEC
               DISPLAY 'DPMXDGTD ENDED AT        :' WS-TS
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