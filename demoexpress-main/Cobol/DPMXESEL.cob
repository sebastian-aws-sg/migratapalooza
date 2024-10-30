000100 IDENTIFICATION DIVISION.
000200 PROGRAM-ID.   DPMXESEL.
000300 AUTHOR.       COGNIZANT.
000400 DATE-WRITTEN. SEPTEMBER 2007.
000500*
000600*
000700****************************************************************
000800*   THIS IS AN UNPUBLISHED PROGRAM OWNED BY ISCC               *
000900*   IN WHICH A COPYRIGHT SUBSISTS AS OF OCTOBER 2003.          *
001000****************************************************************
001100****************************************************************
001200*                   COMPILATION INSTRUCTION                    *
001300*                  COMPILE DB2 VS COBOL 370                    *
001400****************************************************************
001500*    **LNKCTL**
001600*    INCLUDE SYSLIB(DSNRLI)
001700*    MODE AMODE(31) RMODE(ANY)
001800*    ENTRY DPMXESEL
001900*    NAME  DPMXESEL(R)
002000*
002100******************************************************************
002200**         P R O G R A M   D O C U M E N T A T I O N            **
002300******************************************************************
002400* SYSTEM:    MCA XPRESS APPLICATION                              *
002500* PROGRAM:   DPMXESEL                                            *
002600*                                                                *
002700* THIS STORED PROCEDURE SENDS THE LATEST TEMPLATE FROM EACH      *
002800* TEMPLATE FORM LIKE INTERDEALER AND CLIENT-DEALER FORM          *
002900* (IF AVAILABLE) FOR EACH PRODUCT-SUB-PRODUCT AND REGION         *
002900* COMBINATION. THE TEMPLATES CAN EITHER BE EXECUTED OR THE       *
002900* PENDING TEMPLATES OR THE ISDA BASE TEMPLATES.THE MINIMUM       *
002900* CRITERIA FOR THE TEMPLATE IS THAT IT SHOULD BE PUBLISHED.      *
003000******************************************************************
003100* TABLES:                                                        *
003200* -------                                                        *
003300* D0005   - COMPANY TABLE FOR MCA                     *
003400* VDPM04_ATTRB_DTL   - ATTRIBUTE DETAIL TABLE FOR MCA            *
003400* VDPM06_MCA_ENRL    - ENROLLMENT TABLE FOR MCA                  *
003400* D0006   - TEMPLATE TABLE FOR MCA                    *
003400* VDTM54_DEBUG_CNTRL - DEBUG CONTROL TABLE
003900*----------------------------------------------------------------*
004000* INCLUDES:                                                      *
004100* ---------                                                      *
004200* SQLCA    - DB2 COMMAREA                                        *
004300* DPM0101  - COMPANY TABLE                                       *
004400* DPM0401  - ATTRIBUTE DETAIL TABLE                              *
004400* DPM0601  - ENROLLMENT TABLE                                    *
004400* DPM1401  - TEMPLATE TABLE FOR MCA                              *
004400* DTM5401  - DEBUG CONTROL TABLE                                 *
005000*----------------------------------------------------------------*
305100* COPYBOOK:                                                      *
005200* ---------                                                      *
005300* DB2000IA                                                       *
005400* DB20001B                                                       *
005500* DB20001C                                                       *
005600*----------------------------------------------------------------*
005700*              M A I N T E N A N C E   H I S T O R Y             *
005800*                                                                *
005900*                                                                *
006000* DATE CHANGED    VERSION     PROGRAMMER                         *
006100* ------------    -------     --------------------               *
006200*                                                                *
006300* 09/04/2007        001       COGNIZANT                          *
006400*                             INITIAL IMPLEMENTATION.            *
006200*                                                                *
006600******************************************************************
006700 ENVIRONMENT DIVISION.
006800 DATA DIVISION.
006900 WORKING-STORAGE SECTION.
011300
007000 01  WS-SQLCODE                     PIC -ZZZ9.
007000 01  WS-PROGRAM                     PIC X(08) VALUE 'DPMXESEL'.
007600 01  WS-INVLD-CLNT-CMPNY-CD         PIC X(50) VALUE
007700     'Invalid Client Company Code'.
007600 01  WS-EMPTY-CMPNY-CLNT-CD         PIC X(50) VALUE
007700     'Client Company Code Required'.
007600 01  WS-INVLD-DELR-CMPNY-CD         PIC X(50) VALUE
007700     'Invalid Dealer Company Code'.
007600 01  WS-EMPTY-CMPNY-DELR-CD         PIC X(50) VALUE
007700     'Dealer Company Code Required'.
010400 01  WS-DASHES                      PIC X(80) VALUE ALL '='.
010700 01  WS-ERROR-AREA.
010800     05 WS-PARAGRAPH-NAME           PIC X(40).
019100 01  WS-CMPNY-ACCT-IN               PIC X(01) VALUE 'N'.
019100 01  WS-CMPNY-CD                    PIC X(08) VALUE ' '.
019100 01  WS-CMPNY-CD-FLAG               PIC X(01) VALUE 'N'.
019100 01  WS-CURRENT-TS                  PIC X(26) VALUE ' '.
       01  WS-DISPLAY-SWITCH              PIC X(01) VALUE 'N'.
           88 DISPLAY-PARAMETERS                    VALUE 'Y'.
           88 HIDE-PARAMETERS                       VALUE 'N'.

012100**SQL COMMUNICATIONS AREA PASSED BACK IN OUTSQLCA
012200     EXEC SQL
012300        INCLUDE SQLCA
012400     END-EXEC
012500
012600     EXEC SQL
012700        INCLUDE DPM0101
012800     END-EXEC
012900
013000     EXEC SQL
013100        INCLUDE DPM0401
013200     END-EXEC
013300
013000     EXEC SQL
013100        INCLUDE DPM0601
013200     END-EXEC
013300
013000     EXEC SQL
013100        INCLUDE DPM1401
013200     END-EXEC
013300
      * INCLUDE FOR VDTM54_DEBUG_CNTRL                                  00024910
           EXEC SQL                                                     00024920
                INCLUDE DTM5401                                         00024930
           END-EXEC.                                                    00024940
015000**DB2 STANDARD COPYBOOK WITH FORMATTED DISPLAY SQLCA
015100**THIS MUST REMAIN AS THE LAST ENTRY IN WORKING STORAGE
015200 COPY  DB2000IA.
015300
016000 LINKAGE SECTION.
016100**PICTURE CLAUSE FOR OUTSQLCA - PIC X(179) - FOR LINKAGE SECTION
016200 COPY  DB2000IB.
016300
016400 01  LS-SP-ERROR-AREA               PIC X(80).
016500 01  LS-SP-RC                       PIC X(04).
016600 01  LS-CLNT-CMPNY-CD               PIC x(08).
016600 01  LS-DELR-CMPNY-CD               PIC X(08).
019200
019300 PROCEDURE DIVISION USING  OUTSQLCA,
019400                           LS-SP-ERROR-AREA,
019500                           LS-SP-RC,
019600                           LS-CLNT-CMPNY-CD,
019600                           LS-DELR-CMPNY-CD.
020900*----------*
021000 0000-MAIN.
021100*----------*

021200     PERFORM 1000-INITIALIZE
021400     PERFORM 2000-VALIDATE-INPUT
021400     PERFORM 3000-OPEN-ATTRB-LIST-CSR
           PERFORM 4000-OPEN-TMPLT-LIST-CSR

           IF DISPLAY-PARAMETERS
              PERFORM 9100-DISPLAY-DATA
           END-IF

024500     PERFORM 9990-GOBACK
024600     .
024800*------------------------*
024900 1000-INITIALIZE.
025000*------------------------*
025100
025200     MOVE '1000-INITIALIZE'                   TO WS-PARAGRAPH-NAME
025300     MOVE SPACES                              TO OUTSQLCA
025300                                                 LS-SP-ERROR-AREA
025300     MOVE 'SP00'                              TO LS-SP-RC


           EXEC SQL                                                     00051800
                SELECT ACTVT_DSPLY_IN                                   00051900
                  INTO :D054-ACTVT-DSPLY-IN                             00052010
                FROM   VDTM54_DEBUG_CNTRL                               00052040
                WHERE PRGM_ID = :WS-PROGRAM                             00052050
           END-EXEC                                                     00052060
                                                                        00052070
           MOVE SQLCODE                     TO WS-SQLCODE               00052080
                                                                        00052090
           EVALUATE SQLCODE                                             00052091
              WHEN 0                                                    00052092
                  IF D054-ACTVT-DSPLY-IN = 'Y'                          00052094
                     SET DISPLAY-PARAMETERS TO TRUE                     00052095
                     EXEC SQL
                        SET :WS-CURRENT-TS = CURRENT TIMESTAMP
                     END-EXEC
                  END-IF                                                00052099
              WHEN 100                                                  00052092
                  CONTINUE
              WHEN OTHER                                                00052092
                  PERFORM 9000-SQL-ERROR
           END-EVALUATE                                                 00052102

           IF DISPLAY-PARAMETERS
              DISPLAY WS-DASHES
              DISPLAY 'DPMXESEL STARTED AT      :' WS-CURRENT-TS
              DISPLAY WS-DASHES
           END-IF
030600     .
030700*------------------------*
030800 2000-VALIDATE-INPUT.
030900*------------------------*
031100
031000     MOVE '2000-VALIDATE-INPUT'               TO WS-PARAGRAPH-NAME
040600
031200     IF LS-CLNT-CMPNY-CD = SPACES
031500        MOVE WS-EMPTY-CMPNY-CLNT-CD           TO LS-SP-ERROR-AREA
031600        MOVE 'SP50'                           TO LS-SP-RC
031700        PERFORM 9100-DISPLAY-DATA
031800        PERFORM 9990-GOBACK
031900     END-IF

031200     IF LS-DELR-CMPNY-CD = SPACES
031500        MOVE WS-EMPTY-CMPNY-DELR-CD           TO LS-SP-ERROR-AREA
031600        MOVE 'SP50'                           TO LS-SP-RC
031700        PERFORM 9100-DISPLAY-DATA
031800        PERFORM 9990-GOBACK
031900     END-IF
031100
041700     .
      *------------------------*
       3000-OPEN-ATTRB-LIST-CSR.
      *------------------------*

           MOVE '3000-OPEN-ATTRB-LIST-CSR'        TO WS-PARAGRAPH-NAME

           IF DISPLAY-PARAMETERS
              DISPLAY 'OPEN ATTRB_LIST_CSR CURSOR'
           END-IF

      *    THE DEALER CURSOR WILL BE OPENED.

           EXEC SQL
              DECLARE ATTRB_LIST_CSR CURSOR WITH HOLD WITH RETURN FOR
                 SELECT
1                   PROD.ATTRB_ID
                      || ' ' || SUBPROD.ATTRB_ID
                             AS ATTRB_ID
2                  ,RTRIM(LTRIM(SUBPROD.ATTRB_VALUE_DS))
                      || ' ' || RTRIM(LTRIM(PROD.ATTRB_VALUE_DS))
                             AS ATTRB_VALUE_DS
3                  ,'P' AS ATTRB_TYPE_ID
4                  ,RTRIM(LTRIM(PROD.ATTRB_VALUE_DS))
                      || ' ' || RTRIM(LTRIM(SUBPROD.ATTRB_VALUE_DS))
                             AS SORT_DESC
                 FROM  VDPM04_ATTRB_DTL PROD
                      ,VDPM04_ATTRB_DTL SUBPROD
                 WHERE PROD.ATTRB_TYPE_ID       = 'P'
                   AND SUBPROD.ATTRB_TYPE_ID    = 'S'
                 UNION ALL
                 SELECT
1                       ATTRB_ID
2                      ,ATTRB_VALUE_DS
3                      ,ATTRB_TYPE_ID
4                      ,ATTRB_VALUE_DS AS SORT_DESC
                    FROM VDPM04_ATTRB_DTL DPM04
                    WHERE DPM04.ATTRB_TYPE_ID = 'R'
                      ORDER BY ATTRB_ID , SORT_DESC
                    WITH UR
           END-EXEC

           EXEC SQL
              OPEN ATTRB_LIST_CSR
           END-EXEC

           EVALUATE SQLCODE
              WHEN 0
                 CONTINUE
              WHEN OTHER
                 PERFORM 9000-SQL-ERROR
054521     END-EVALUATE

           .
      *------------------------*
       4000-OPEN-TMPLT-LIST-CSR.
      *------------------------*

           MOVE '4000-OPEN-TMPLT-LIST-CSR'        TO WS-PARAGRAPH-NAME

           IF DISPLAY-PARAMETERS
              DISPLAY 'OPEN TMPLT_LIST_CSR CURSOR'
           END-IF
      *    THE TEMPLATE LIST CURSOR WILL BE OPENED.

           EXEC SQL
             DECLARE TMPLT_LIST_CSR CURSOR WITH HOLD WITH RETURN FOR
                SELECT
 1                  ISDA.ATTRB_PRDCT_ID ||' '|| ISDA.ATTRB_SUB_PRDCT_ID
                                               AS PRDCT_ID
 2                 ,ISDA.ATTRB_REGN_ID         AS REGN_ID
 3                 ,ISDA.MCA_TMPLT_ID
 4                 ,ISDA.MCA_TMPLT_NM
 5                 ,ISDA.MCA_TMPLT_GROUP_CD    AS TMPLT_GROUP_CD
 6                 ,CASE COALESCE(DPM06.DELR_STAT_CD,'PU')
                      WHEN 'A'
                        THEN
                           CASE COALESCE(DPM14.MCA_DELR_STAT_CD,'P')
                              WHEN 'A'
                                 THEN 'EXECUTED'
                              WHEN ' '
                                 THEN 'PUBLISHED'
                              ELSE 'PENDING'
                           END
                      WHEN 'P'
                        THEN 'PENDING'
                      ELSE 'PUBLISHED'
                    END AS MCA_STATUS_MSG
 7                 ,CASE COALESCE(DPM14.MCA_DELR_STAT_CD,'P')
                      WHEN 'A'
                         THEN CHAR(DATE(DPM14.MCA_EXE_TS),USA)
                      ELSE '01/01/1900'
                    END AS EXECUTED_DATE
 8                 ,COALESCE(DPM14.MCA_TMPLT_TYPE_CD
                            ,ISDA.MCA_TMPLT_TYPE_CD) AS TMPLT_TYPE_CD
 9                 ,CHAR(DATE(ISDA.MCA_PBLTN_DT)) AS PUBLN_DT
 10                ,COALESCE(DPM14.ROW_UPDT_TS
                                           ,'1900-01-01-00.00.00.00000')
                                                     AS UPDT_TS
              FROM
 1               (SELECT ATTRB_PRDCT_ID
 2                      ,ATTRB_SUB_PRDCT_ID
 3                      ,ATTRB_REGN_ID
 4                      ,MCA_TMPLT_TYPE_CD
 5                      ,MCA_TMPLT_ID
 6                      ,MCA_TMPLT_NM
 7                      ,MCA_TMPLT_GROUP_CD
 8                      ,MCA_PBLTN_DT
 9                      ,ROW_UPDT_TS
                         FROM D0006
                     WHERE MCA_TMPLT_TYPE_CD = 'I'
                     AND MCA_STAT_IN       = 'P') ISDA
               LEFT OUTER JOIN VDPM06_MCA_ENRL       DPM06
                          ON
               ISDA.MCA_TMPLT_ID         = DPM06.RQST_TMPLT_ID
               AND ((DPM06.DELR_CMPNY_ID = :LS-DELR-CMPNY-CD AND
                     DPM06.CLNT_CMPNY_ID = :LS-CLNT-CMPNY-CD)
                                        OR
                    (DPM06.DELR_CMPNY_ID = :LS-CLNT-CMPNY-CD AND
                     DPM06.CLNT_CMPNY_ID = :LS-DELR-CMPNY-CD))
               LEFT OUTER JOIN D0006 DPM14
                 ON DPM06.ASGD_TMPLT_ID  = DPM14.MCA_TMPLT_ID AND
                    DPM14.MCA_TMPLT_TYPE_CD <> 'E'
                UNION ALL
              SELECT
 1              DPM14.ATTRB_PRDCT_ID ||' '|| DPM14.ATTRB_SUB_PRDCT_ID
                                                AS PRDCT_ID
 2             ,DPM14.ATTRB_REGN_ID             AS REGN_ID
 3             ,DPM14.MCA_TMPLT_ID
 4             ,ISDA.MCA_TMPLT_NM
 5             ,DPM14.MCA_TMPLT_GROUP_CD        AS TMPLT_GROUP_CD
 6             ,CASE DPM14.MCA_TMPLT_TYPE_CD
                  WHEN 'E'
                     THEN 'EXECUTED'
                  ELSE 'PENDING'
                  END AS MCA_STATUS_MSG
 7             ,CASE DPM14.MCA_TMPLT_TYPE_CD
                  WHEN 'E'
                     THEN CHAR(DATE(DPM14.MCA_EXE_TS),USA)
                  ELSE '01/01/1900'
                  END AS EXECUTED_DATE
 8             ,DPM14.MCA_TMPLT_TYPE_CD         AS TMPLT_TYPE_CD
 9             ,CHAR(DATE(DPM14.MCA_PBLTN_DT))  AS PUBLN_DT
 10            ,COALESCE(DPM14.ROW_UPDT_TS,'1900-01-01-00.00.00.00000')
                                                AS UPDT_TS
              FROM  D0006 DPM14
                   ,D0006 ISDA
               WHERE DPM14.MCA_ISDA_TMPLT_ID    = ISDA.MCA_TMPLT_ID
               AND ((DPM14.DELR_CMPNY_ID        = :LS-DELR-CMPNY-CD
                     AND DPM14.CLNT_CMPNY_ID    = :LS-CLNT-CMPNY-CD
                     AND ((DPM14.MCA_TMPLT_TYPE_CD =  'R' AND
                     DPM14.MCA_CLNT_STAT_CD > ' ')  OR
                        (DPM14.MCA_TMPLT_TYPE_CD = 'E'))
                                                OR
                    (DPM14.DELR_CMPNY_ID        = :LS-CLNT-CMPNY-CD
                     AND DPM14.CLNT_CMPNY_ID    = :LS-DELR-CMPNY-CD
                     AND DPM14.MCA_TMPLT_TYPE_CD IN('R','E'))
                   ))
             ORDER BY PRDCT_ID,REGN_ID,TMPLT_GROUP_CD,UPDT_TS DESC,
                                                      PUBLN_DT DESC
              WITH UR
           END-EXEC

           EXEC SQL
              OPEN TMPLT_LIST_CSR
           END-EXEC

           EVALUATE SQLCODE
              WHEN 0
                 CONTINUE
              WHEN OTHER
                 PERFORM 9000-SQL-ERROR
054521     END-EVALUATE

054522     .
101300*------------------------*
101400 9000-SQL-ERROR.
101500*------------------------*
101600
101800     PERFORM 9100-DISPLAY-DATA
087800     MOVE 'Database error has occurred. Please contact DTCC.'
087800                                      TO LS-SP-ERROR-AREA
           MOVE 'SP99'                      TO LS-SP-RC
           MOVE SQLCODE                     TO WS-SQLCODE
102700     DISPLAY 'SQLCODE                 :' WS-SQLCODE
           DISPLAY 'PARAGRAPH-NAME          :' WS-PARAGRAPH-NAME
101900     PERFORM 9999-FORMAT-SQLCA
102000     PERFORM 9990-GOBACK
102100     .
102200*------------------------*
102300 9100-DISPLAY-DATA.
102400*------------------------*
102500
           IF DISPLAY-PARAMETERS
102600        DISPLAY WS-DASHES
102700        DISPLAY 'PROGRAM-NAME             :' WS-PROGRAM
102700        DISPLAY 'PARAGRAPH-NAME           :' WS-PARAGRAPH-NAME
102800        DISPLAY 'SP-ERROR-AREA            :' LS-SP-ERROR-AREA
102900        DISPLAY 'SP-RC                    :' LS-SP-RC
103400        DISPLAY 'LS-CLNT-CMPNY-CD         :' LS-CLNT-CMPNY-CD
103400        DISPLAY 'LS-DELR-CMPNY-CD         :' LS-DELR-CMPNY-CD
           END-IF
106800
107000     .
107100*------------------------*
107200 9990-GOBACK.
107300*------------------------*

101900      PERFORM 9999-FORMAT-SQLCA
            IF DISPLAY-PARAMETERS
               DISPLAY WS-DASHES
               DISPLAY 'OUTSQLCA FOR DPMXESEL    :' OUTSQLCA
               DISPLAY WS-DASHES
               EXEC SQL
                  SET :WS-CURRENT-TS = CURRENT TIMESTAMP
               END-EXEC
               DISPLAY 'DPMXESEL ENDED AT        :' WS-CURRENT-TS
               DISPLAY WS-DASHES
            END-IF
107400      GOBACK
107500     .
107600*------------------------*
107700 9999-FORMAT-SQLCA.
107800*------------------------*
107900     PERFORM DB2000I-FORMAT-SQLCA
108000        THRU DB2000I-FORMAT-SQLCA-EXIT
108100        .
108200**MOVE STATEMENTS TO FORMAT THE OUTSQLCA USING DB2000IA & DB2000IB
108300  COPY DB2000IC.
