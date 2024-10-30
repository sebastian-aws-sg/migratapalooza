       IDENTIFICATION DIVISION.
       PROGRAM-ID.   DPMXHAPE.
       AUTHOR.       COGNIZANT.
       DATE-WRITTEN. SEPTEMBER 2007.
      *                                                                *
      ******************************************************************
      *                                                                *
      *   THIS IS AN UNPUBLISHED PROGRAM OWNED BY ISCC                 *
      *   IN WHICH A COPYRIGHT SUBSISTS AS OF JULY 2006.               *
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
      *    ENTRY DPMXHAPE
      *    NAME  DPMXHAPE(R)
      *
      ******************************************************************
      **         P R O G R A M   D O C U M E N T A T I O N            **
      ******************************************************************
      *                                                                *
      * SYSTEM:    MCA XPRESS APPLICATION                              *
      * PROGRAM:   DPMXHAPE                                            *
      *                                                                *
      * This stored procedure provides the detials for Pending Enroll- *
      * ment Approval Tab and Approved Firms Tab of Dealer HomePage    *
      * for given Company Dealer Id and Tab Indicator.                 *
      *                                                                *
      ******************************************************************
      *                                                                *
      * TABLES:                                                        *
      * -------                                                        *
      *                                                                *
      * D0006     - THIS TABLE HOLDS CLIENT, DEALER, MCA    *
      *                        TEMPLATE INFORMATION.                   *
      * D0005     - THIS TABLE HOLDS COMPANY INFO.          *
      * D0003    - THIS TABLE HOLDS USER INFO.             *
      * VDPM10_MCA_LOCK      - LOCK TABLE TABLE FOR MCA.               *
      * VDPM06_MCA_ENRL      - THIS TABLE HOLDS CLIENT, DEALER, MCA    *
      *                        ENROLLEMNT INFO.                        *
      * VDTM54_DEBUG_CNTRL   - DEBUG CONTROL TABLE                     *
      *----------------------------------------------------------------*
      *                                                                *
      * INCLUDES:                                                      *
      * ---------                                                      *
      *                                                                *
      * SQLCA                - DB2 COMMAREA                            *
      * DPM1401              - DCLGEN copy book for D0006   *
      * DPM0101              - DCLGEN copy book for D0005   *
      * DPM0301              - DCLGEN copy book for D0003  *
      * DPM0601              - DCLGEN copy book for VDPM06_MCA_ENRL    *
      * DPM1001              - DCLGEN copy book for VDPM10_MCA_LOCK    *
      * DPM1501              - DCLGEN copy book for VDPM15_TMPLT_WORK  *
      * DTM5401              - DCLGEN copy book for VDTM54_DEBUG_CNTRL *
      *                                                                *
      *----------------------------------------------------------------*
      *                                                                *
      * COPYBOOK:                                                      *
      * ---------                                                      *
      *                                                                *
      * DB2000IA                                                       *
      * DB2000IB                                                       *
      * DB2000IC                                                       *
      *                                                                *
      *----------------------------------------------------------------*
      *                                                                *
      *              M A I N T E N A N C E   H I S T O R Y             *
      *                                                                *
      * DATE CHANGED    VERSION     PROGRAMMER                         *
      * ------------    -------     --------------------               *
      *                                                                *
      * 09/20/2007        000       COGNIZANT                          *
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
       01  WS-PROGRAM                       PIC X(08)  VALUE 'DPMXHAPE'.
       01  WS-DEALER-ID                     PIC X(08)  VALUE SPACES.
       01  WS-TAB-INDICATOR                 PIC X(01)  VALUE SPACES.
           88  WS-PENDING                   VALUE 'P'.
           88  WS-APPROVED                  VALUE 'A'.
       01  WS-RETURN-ID.
           05  WS-PARAGRAPH-NAME            PIC X(40).
           05  WS-TABLE-NAME                PIC X(40).
       01  WS-SPACES-CMPNYID                PIC X(50) VALUE
           'Company Id is Spaces, Send Valid Value'.
       01  WS-SPACES-TABIND                 PIC X(50) VALUE
           'Tab Indicator is Spaces, Send Valid Value'.
       01  WS-INVLD-TABIND                  PIC X(50) VALUE
           'Invalid Tab Indicator Passed'.
       01  WS-DASHES                        PIC X(40) VALUE ALL '='.
       01  WS-TS                            PIC X(26).
       01  WS-DISPLAY-SWITCH                PIC X(01) VALUE 'N'.
           88 DISPLAY-PARAMETERS                      VALUE 'Y'.
           88 HIDE-PARAMETERS                         VALUE 'N'.
      *
      **SQL COMMUNICATIONS AREA PASSED BACK IN OUTSQLCA
      *
           EXEC SQL
              INCLUDE SQLCA
           END-EXEC

           EXEC SQL
              INCLUDE DPM1401
           END-EXEC

           EXEC SQL
              INCLUDE DPM0101
           END-EXEC

           EXEC SQL
              INCLUDE DPM0301
           END-EXEC

           EXEC SQL
              INCLUDE DPM0601
           END-EXEC

           EXEC SQL
              INCLUDE DPM1001
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
       01  LS-CMPNY-ID                      PIC X(08).
       01  LS-TAB-INDICATOR                 PIC X(01).
      *
       PROCEDURE DIVISION USING  OUTSQLCA,
                                 LS-SP-ERROR-AREA,
                                 LS-SP-RC,
                                 LS-CMPNY-ID,
                                 LS-TAB-INDICATOR.

      *----------*
       0000-MAIN.
      *----------*

           PERFORM 1000-INITIALIZE
           PERFORM 2000-VALIDATE-INPUT
           PERFORM 3000-CLIENT-DETAILS
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
           MOVE LS-CMPNY-ID                 TO WS-DEALER-ID
           MOVE LS-TAB-INDICATOR            TO WS-TAB-INDICATOR
                                                                        00051700
                                                                        00051700
           EXEC SQL                                                     00051800
                SELECT ACTVT_DSPLY_IN                                   00051900
                  INTO :D054-ACTVT-DSPLY-IN                             00052010
                FROM VDTM54_DEBUG_CNTRL                                 00052040
                WHERE PRGM_ID = :WS-PROGRAM                             00052050
                WITH UR                                                 00052050
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
              WHEN +100                                                 00052092
                  CONTINUE
              WHEN OTHER                                                00052092
                  PERFORM 9000-SQL-ERROR
           END-EVALUATE                                                 00052102

           IF DISPLAY-PARAMETERS
              DISPLAY WS-DASHES
              DISPLAY 'DPMXHAPE STARTED AT      :' WS-TS
              DISPLAY WS-DASHES
           END-IF

           .

      *------------------------*
       2000-VALIDATE-INPUT.
      *------------------------*

           MOVE '2000-VALIDATE-INPUT'       TO WS-PARAGRAPH-NAME

           EVALUATE WS-DEALER-ID
               WHEN SPACES
                    MOVE 'SP50'                TO LS-SP-RC
                    MOVE WS-SPACES-CMPNYID     TO LS-SP-ERROR-AREA
                    PERFORM 9100-DISPLAY-DATA
                    PERFORM 9990-GOBACK
               WHEN OTHER
                    CONTINUE
           END-EVALUATE

           EVALUATE WS-TAB-INDICATOR
               WHEN SPACES
                    MOVE 'SP50'                TO LS-SP-RC
                    MOVE WS-SPACES-TABIND      TO LS-SP-ERROR-AREA
                    PERFORM 9100-DISPLAY-DATA
                    PERFORM 9990-GOBACK
               WHEN OTHER
                    PERFORM 2400-VALIDATE-TABIND
           END-EVALUATE
           .

      *------------------------*
       2400-VALIDATE-TABIND.
      *------------------------*

           MOVE '2400-VALIDATE-TABIND'      TO WS-PARAGRAPH-NAME

           EVALUATE TRUE
               WHEN WS-PENDING
               WHEN WS-APPROVED
                    MOVE 'SP00'                TO LS-SP-RC
               WHEN OTHER
                    MOVE WS-INVLD-TABIND       TO LS-SP-ERROR-AREA
                    MOVE 'SP50'                TO LS-SP-RC
                    PERFORM 9100-DISPLAY-DATA
                    PERFORM 9990-GOBACK
           END-EVALUATE
           .

      *------------------------*
       3000-CLIENT-DETAILS.
      *------------------------*

           MOVE '3000-CLIENT-DETAILS'        TO WS-PARAGRAPH-NAME

           EVALUATE TRUE
               WHEN WS-PENDING
                    PERFORM 3200-CLIENTS-ENROLLED
               WHEN WS-APPROVED
                    PERFORM 3400-CLIENTS-APPROVED
           END-EVALUATE
           .

      *------------------------*
       3200-CLIENTS-ENROLLED.
      *------------------------*

           MOVE '3200-CLIENTS-ENROLLED'      TO WS-PARAGRAPH-NAME

           EXEC SQL
             DECLARE DPMXHAPE_ENRLCSR CURSOR WITH HOLD WITH RETURN FOR
             SELECT
     1       VALUE(DPM14.MCA_TMPLT_ID,ISDA.MCA_TMPLT_ID)MCA_TMPLT_ID
     2              ,ISDA.MCA_TMPLT_SHORT_NM
     3              ,DPM06.CLNT_CMPNY_ID
     4              ,DPM01.CMPNY_NM
     5              ,DPM06.ENRL_TS
     6              ,DPM03.CMPNY_USER_NM
     7              ,CHAR (YEAR (ISDA.MCA_PBLTN_DT))
     8              ,' ' AS MCA_STATUS_MSG
     9              ,DPM03.CMPNY_USER_ID
    10              ,' ' AS LOCK_USER_ID
    11              ,' ' AS LOCK_CMPNY_ID
    12              ,' ' AS LOCK_USER_NAME
    13              ,' ' AS LOCK_IND
    14              ,ISDA.MCA_TMPLT_NM
              FROM  VDPM06_MCA_ENRL             DPM06
              JOIN  (SELECT  MCA_TMPLT_ID
                            ,MCA_TMPLT_SHORT_NM
                            ,MCA_PBLTN_DT
                            ,MCA_TMPLT_NM
                       FROM  D0006
                      WHERE  MCA_TMPLT_TYPE_CD = 'I'
                        AND  MCA_STAT_IN = 'P') ISDA
                ON  ISDA.MCA_TMPLT_ID = DPM06.RQST_TMPLT_ID
              LEFT OUTER JOIN
                    D0006            DPM14
                ON  DPM06.ASGD_TMPLT_ID    = DPM14.MCA_TMPLT_ID
              LEFT OUTER JOIN  VDPM10_MCA_LOCK             DPM10
                ON  DPM14.MCA_TMPLT_ID     = DPM10.MCA_TMPLT_ID
              LEFT OUTER JOIN  D0003           DPM03_1
                ON  DPM10.CMPNY_USER_ID    = DPM03_1.CMPNY_USER_ID
               AND  DPM10.CMPNY_ID         = DPM03_1.CMPNY_ID
              JOIN  D0005            DPM01
                ON  DPM06.CLNT_CMPNY_ID    = DPM01.CMPNY_ID
              JOIN  D0003           DPM03
                ON  DPM06.CLNT_CMPNY_ID    = DPM03.CMPNY_ID
               AND  DPM06.ROW_UPDT_USER_ID = DPM03.CMPNY_USER_ID
             WHERE  DPM06.DELR_STAT_CD     = 'P'
               AND  DPM06.DELR_CMPNY_ID    = :WS-DEALER-ID
             ORDER BY DPM06.ENRL_TS DESC, DPM01.CMPNY_NM ASC
              WITH UR
           END-EXEC
      *
           EXEC SQL
               OPEN DPMXHAPE_ENRLCSR
           END-EXEC

           EVALUATE SQLCODE
              WHEN 0
                 MOVE 'SP00'                TO LS-SP-RC
              WHEN OTHER
                 MOVE 'DPMXHAPE_ENRLCSR'    TO WS-TABLE-NAME
                 PERFORM 9000-SQL-ERROR
           END-EVALUATE
           .

      *------------------------*
       3400-CLIENTS-APPROVED.
      *------------------------*

           MOVE '3400-CLIENTS-APPROVED'      TO WS-PARAGRAPH-NAME

           EXEC SQL
             DECLARE DPMXHAPE_APRDCSR CURSOR WITH HOLD WITH RETURN FOR
             SELECT
     1         VALUE(DPM14.MCA_TMPLT_ID,ISDA.MCA_TMPLT_ID)MCA_TMPLT_ID
     2             ,ISDA.MCA_TMPLT_SHORT_NM
     3             ,DPM06.CLNT_CMPNY_ID
     4             ,DPM01.CMPNY_NM AS COMPANY_NAME
     5             ,DPM06.APRVL_TS      AS APPROVED_TS
     6             ,DPM03.CMPNY_USER_NM
     7             ,CHAR(YEAR(ISDA.MCA_PBLTN_DT))
     8             ,CASE
                      WHEN DPM06.ASGD_TMPLT_ID = 0
                        THEN 'MCA Set up required'
                      WHEN DPM14.MCA_CLNT_STAT_CD  = 'A'
                        AND DPM14.MCA_DELR_STAT_CD = 'A'
                        THEN 'Executed'
                      WHEN DPM14.MCA_CLNT_STAT_CD  = ' '
                        AND DPM14.MCA_DELR_STAT_CD = ' '
                        THEN '        '
                      WHEN DPM14.MCA_DELR_STAT_CD = 'P'
                        THEN
                           CASE
                             WHEN COALESCE(DPM15.MCA_DELR_STAT_CD,
                                           DPM14.MCA_DELR_STAT_CD) = 'P'
                              AND COALESCE(DPM15.MCA_CLNT_STAT_CD,
                                           DPM14.MCA_CLNT_STAT_CD) = 'D'
                              THEN 'Pending Dealer Review'
                             WHEN COALESCE(DPM15.MCA_DELR_STAT_CD,
                                           DPM14.MCA_DELR_STAT_CD) = 'P'
                              AND COALESCE(DPM15.MCA_CLNT_STAT_CD,
                                           DPM14.MCA_CLNT_STAT_CD) = ' '
                              THEN 'Pending Dealer Review'
                             WHEN COALESCE(DPM15.MCA_DELR_STAT_CD,
                                           DPM14.MCA_DELR_STAT_CD) = 'D'
                              AND COALESCE(DPM15.MCA_CLNT_STAT_CD,
                                           DPM14.MCA_CLNT_STAT_CD) = 'P'
                              THEN 'Pending Counterparty Review'
                             WHEN COALESCE(DPM15.MCA_DELR_STAT_CD,
                                           DPM14.MCA_DELR_STAT_CD) = 'P'
                              AND COALESCE(DPM15.MCA_CLNT_STAT_CD,
                                           DPM14.MCA_CLNT_STAT_CD) = 'A'
                              THEN 'Pending Execution'
                           END
                        WHEN DPM14.MCA_DELR_STAT_CD = 'D'
                           THEN 'Pending Counterparty Review'
     8              END                          AS MCA_STATUS_MSG
     9             ,DPM03.CMPNY_USER_ID
    10             ,CASE
                      WHEN DPM14.MCA_DELR_STAT_CD = 'P'
                        THEN COALESCE(DPM10.CMPNY_USER_ID,' ')
                      ELSE ' '
                    END                          AS LOCK_USER_ID
    11             ,CASE
                      WHEN DPM14.MCA_DELR_STAT_CD = 'P'
                         THEN COALESCE(DPM10.CMPNY_ID,' ')
                      ELSE ' '
                    END                          AS LOCK_CMPNY_ID
    12             ,CASE
                      WHEN DPM14.MCA_DELR_STAT_CD = 'P'
                         THEN COALESCE(DPM03_1.CMPNY_USER_NM,' ')
                      ELSE ' '
                    END                          AS LOCK_USER_NAME
    13             ,CASE
                      WHEN DPM14.MCA_DELR_STAT_CD = 'P'
                        THEN CASE
                               WHEN(COALESCE(DPM10.MCA_TMPLT_ID,0)) = 0
                                 THEN 'N'
                                ELSE 'Y'
                             END
                      ELSE 'N'
                   END                            AS LOCK_IND
    14             ,ISDA.MCA_TMPLT_NM
             FROM  VDPM06_MCA_ENRL          DPM06
             JOIN  (SELECT  MCA_TMPLT_ID
                           ,MCA_TMPLT_SHORT_NM
                           ,MCA_PBLTN_DT
                           ,MCA_TMPLT_NM
                      FROM D0006
                     WHERE MCA_TMPLT_TYPE_CD = 'I'
                       AND MCA_STAT_IN = 'P') ISDA
               ON  ISDA.MCA_TMPLT_ID = DPM06.RQST_TMPLT_ID
             LEFT  OUTER JOIN
                   D0006         DPM14
               ON  DPM06.ASGD_TMPLT_ID    = DPM14.MCA_TMPLT_ID
             LEFT OUTER JOIN  VDPM10_MCA_LOCK             DPM10
               ON  DPM14.MCA_TMPLT_ID     = DPM10.MCA_TMPLT_ID
             LEFT OUTER JOIN  D0003           DPM03_1
               ON  DPM10.CMPNY_USER_ID    = DPM03_1.CMPNY_USER_ID
              AND  DPM10.CMPNY_ID         = DPM03_1.CMPNY_ID
             LEFT OUTER JOIN VDPM15_TMPLT_WORK DPM15
               ON  DPM14.MCA_TMPLT_ID     = DPM15.MCA_TMPLT_ID
             JOIN  D0005         DPM01
               ON  DPM06.CLNT_CMPNY_ID    = DPM01.CMPNY_ID
             JOIN  D0003        DPM03
               ON  DPM06.DELR_CMPNY_ID    = DPM03.CMPNY_ID
              AND  DPM06.APRVR_USER_ID    = DPM03.CMPNY_USER_ID
            WHERE  DPM06.DELR_STAT_CD     = 'A'
              AND  DPM06.DELR_CMPNY_ID    = :WS-DEALER-ID
                    UNION ALL
             SELECT
     1         VALUE(DPM14.MCA_TMPLT_ID,ISDA.MCA_TMPLT_ID)MCA_TMPLT_ID
     2             ,ISDA.MCA_TMPLT_SHORT_NM
     3             ,DPM14.CLNT_CMPNY_ID
     4             ,DPM01.CMPNY_NM     AS COMPANY_NAME
     5             ,DPM14.ROW_UPDT_TS  AS APPROVED_TS
     6             ,DPM03.CMPNY_USER_NM
     7             ,CHAR(YEAR(ISDA.MCA_PBLTN_DT))
     8             ,CASE
                      WHEN DPM14.MCA_CLNT_STAT_CD  = 'A'
                        AND DPM14.MCA_DELR_STAT_CD = 'A'
                        THEN 'Executed'
                      WHEN DPM14.MCA_CLNT_STAT_CD  = ' '
                        AND DPM14.MCA_DELR_STAT_CD = ' '
                        THEN '        '
                      WHEN DPM14.MCA_DELR_STAT_CD = 'P'
                        THEN
                           CASE
                             WHEN COALESCE(DPM15.MCA_DELR_STAT_CD,
                                           DPM14.MCA_DELR_STAT_CD) = 'P'
                              AND COALESCE(DPM15.MCA_CLNT_STAT_CD,
                                           DPM14.MCA_CLNT_STAT_CD) = 'D'
                              THEN 'Pending Dealer Review'
                             WHEN COALESCE(DPM15.MCA_DELR_STAT_CD,
                                           DPM14.MCA_DELR_STAT_CD) = 'P'
                              AND COALESCE(DPM15.MCA_CLNT_STAT_CD,
                                           DPM14.MCA_CLNT_STAT_CD) = ' '
                              THEN 'Pending Dealer Review'
                             WHEN COALESCE(DPM15.MCA_DELR_STAT_CD,
                                           DPM14.MCA_DELR_STAT_CD) = 'D'
                              AND COALESCE(DPM15.MCA_CLNT_STAT_CD,
                                           DPM14.MCA_CLNT_STAT_CD) = 'P'
                              THEN 'Pending Counterparty Review'
                             WHEN COALESCE(DPM15.MCA_DELR_STAT_CD,
                                           DPM14.MCA_DELR_STAT_CD) = 'P'
                              AND COALESCE(DPM15.MCA_CLNT_STAT_CD,
                                           DPM14.MCA_CLNT_STAT_CD) = 'A'
                              THEN 'Pending Execution'
                           END
                        WHEN DPM14.MCA_DELR_STAT_CD = 'D'
                           THEN 'Pending Counterparty Review'
     8              END                          AS MCA_STATUS_MSG
     9             ,DPM03.CMPNY_USER_ID
    10             ,CASE
                      WHEN DPM14.MCA_DELR_STAT_CD = 'P'
                        THEN COALESCE(DPM10.CMPNY_USER_ID,' ')
                      ELSE ' '
                    END                          AS LOCK_USER_ID
    11             ,CASE
                      WHEN DPM14.MCA_DELR_STAT_CD = 'P'
                         THEN COALESCE(DPM10.CMPNY_ID,' ')
                      ELSE ' '
                    END                          AS LOCK_CMPNY_ID
    12             ,CASE
                      WHEN DPM14.MCA_DELR_STAT_CD = 'P'
                         THEN COALESCE(DPM03_1.CMPNY_USER_NM,' ')
                      ELSE ' '
                    END                          AS LOCK_USER_NAME
    13             ,CASE
                      WHEN DPM14.MCA_DELR_STAT_CD = 'P'
                        THEN CASE
                               WHEN(COALESCE(DPM10.MCA_TMPLT_ID,0)) = 0
                                 THEN 'N'
                                ELSE 'Y'
                             END
                      ELSE 'N'
                   END                            AS LOCK_IND
    14             ,ISDA.MCA_TMPLT_NM
             FROM  D0006         DPM14
             JOIN  (SELECT  MCA_TMPLT_ID
                           ,MCA_TMPLT_SHORT_NM
                           ,MCA_PBLTN_DT
                           ,MCA_TMPLT_NM
                      FROM D0006
                    WHERE MCA_TMPLT_TYPE_CD = 'I'
                       AND MCA_STAT_IN      = 'P') ISDA
               ON  ISDA.MCA_TMPLT_ID        = DPM14.MCA_TMPLT_ID
             LEFT OUTER JOIN  VDPM10_MCA_LOCK      DPM10
               ON  DPM14.MCA_TMPLT_ID       = DPM10.MCA_TMPLT_ID
             LEFT OUTER JOIN  D0003    DPM03_1
               ON  DPM10.CMPNY_USER_ID      = DPM03_1.CMPNY_USER_ID
              AND  DPM10.CMPNY_ID           = DPM03_1.CMPNY_ID
             LEFT OUTER JOIN VDPM15_TMPLT_WORK DPM15
               ON  DPM14.MCA_TMPLT_ID       = DPM15.MCA_TMPLT_ID
             JOIN  D0005           DPM01
               ON  DPM14.CLNT_CMPNY_ID      = DPM01.CMPNY_ID
             JOIN  D0003          DPM03
               ON  DPM14.DELR_CMPNY_ID      = DPM03.CMPNY_ID
              AND  DPM14.ROW_UPDT_USER_ID   = DPM03.CMPNY_USER_ID
            WHERE  DPM14.MCA_TMPLT_TYPE_CD  = 'A'
              AND  DPM14.DELR_CMPNY_ID      = :WS-DEALER-ID
            ORDER  BY COMPANY_NAME ASC,  APPROVED_TS DESC
             WITH UR
           END-EXEC
      *
           EXEC SQL
               OPEN DPMXHAPE_APRDCSR
           END-EXEC

           EVALUATE SQLCODE
              WHEN 0
                 MOVE 'SP00'                TO LS-SP-RC
              WHEN OTHER
                 MOVE 'DPMXHAPE_APRDCSR'    TO WS-TABLE-NAME
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
           DISPLAY 'PARAGRAPH-NAME          :' WS-PARAGRAPH-NAME
           DISPLAY 'SQLCODE                 :' WS-SQLCODE

           PERFORM 9999-FORMAT-SQLCA
           PERFORM 9990-GOBACK
           .

      *------------------------*
       9100-DISPLAY-DATA.
      *------------------------*

           DISPLAY WS-DASHES
           DISPLAY 'PROGRAM-NAME             :' WS-PROGRAM
           DISPLAY 'PARAGRAPH-NAME           :' WS-PARAGRAPH-NAME
           DISPLAY 'TABLE/CURSOR NAME        :' WS-TABLE-NAME
           DISPLAY 'SP-ERROR-AREA            :' LS-SP-ERROR-AREA
           DISPLAY 'SP-RC                    :' LS-SP-RC

           IF LS-SP-RC = 'SP99'
              DISPLAY "SQLERRMC   : " SQLERRMC
              DISPLAY "SQLSTATE   : " SQLSTATE
              DISPLAY "SQLCODE    : " SQLCODE
              DISPLAY "SQLERRD(1) : " SQLERRD(1)
              DISPLAY "SQLERRD(2) : " SQLERRD(2)
              DISPLAY "SQLERRD(3) : " SQLERRD(3)
              DISPLAY "SQLERRD(4) : " SQLERRD(4)
              DISPLAY "SQLERRD(5) : " SQLERRD(5)
              DISPLAY "SQLERRD(6) : " SQLERRD(6)
              EXEC SQL
                   ROLLBACK
              END-EXEC
           END-IF

           .

      *------------------------*
       9990-GOBACK.
      *------------------------*

           PERFORM 9999-FORMAT-SQLCA
           IF DISPLAY-PARAMETERS
              DISPLAY WS-DASHES
              DISPLAY 'OUTSQLCA FOR DPMXHAPE    :' OUTSQLCA
              DISPLAY WS-DASHES
              EXEC SQL
                 SET :WS-TS = CURRENT TIMESTAMP
              END-EXEC
              DISPLAY 'DPMXHAPE ENDED AT        :' WS-TS
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