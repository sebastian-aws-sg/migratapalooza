       IDENTIFICATION DIVISION.
       PROGRAM-ID.    DPMXMSTU.
       AUTHOR.        COGNIZANT.

      ******************************************************************
      *THIS IS AN UNPUBLISHED PROGRAM OWNED BY ISCC                    *
      *IN WHICH A COPYRIGHT SUBSISTS AS OF JULY 2006.                  *
      *                                                                *
      ******************************************************************
      **LNKCTL**
      *    INCLUDE SYSLIB(DSNRLI)
      *    MODE AMODE(31) RMODE(ANY)
      *    ENTRY DPMXMSTU
      *    NAME  DPMXMSTU(R)
      *
      ******************************************************************
      **         P R O G R A M   D O C U M E N T A T I O N            **
      ******************************************************************
      * SYSTEM:    DERIV/SERV MCA XPRESS                               *
      * PROGRAM:   DPMXMSTU                                            *
      *                                                                *
      * THIS STORED PROCEDURE USED TO CHANGE THE TEMPLATE STATUS AND   *
      * UPDATE THE VALUES FOR EACH AMENDMENT WHEN THE USER APPLIES     *
      * A TEMPLATE TO A CP/SUBMIT TO THE CP/SUBMIT FOR THE EXECUTION   *
      ******************************************************************
      * TABLES:                                                        *
      * -------                                                        *
      * VDPM06_MCA_ENRL           - TABLE CONTAINS THE MCA ENROLLMENT  *
      *                             INFORMATION                        *
      * D0006          - MASTER TABLE CONTAINS TEMPLATE     *
      *                             DETAILS INCLUSIVE OF ISDA /        *
      *                             CUSTOMIZED TEMPLATE                *
      * VDPM15_TMPLT-WORK         - WORK TABLE CONTAINS TEMPLATE       *
      *                             DETAILS                            *
      * VDPM16_MCA_AMND           - MASTER TABLE CONTAINS THE AMENDMENT*
      *                             INFORMATION FOR A TEMPLATE         *
      * VDPM17_AMND_WORK          - WORK TABLE CONTAINS THE AMENDMENT  *
      *                             INFORMATION FOR A TEMPLATE         *
      * VDPM18_MCA_LINK           - LINK MASTER TABLE CONTAINS THE     *
      *                             POINTER (COMMENT, DOCUMENT, TEXT)  *
      *                             FOR EACH AMENDMENT                 *
      * VDPM19_LINK_WORK          - LINK WORK TABLE CONTAINS THE       *
      *                             POINTER (COMMENT, DOCUMENT, TEXT)  *
      *                             FOR EACH AMENDMENT                 *
      * VDTM54_DEBUG_CNTRL        - DEBUG CONTROL TABLE                *
      *----------------------------------------------------------------*
      * INCLUDES:                                                      *00360000
      * ---------                                                      *00370000
      * SQLCA    - DB2 COMMAREA                                        *00380000
      * DPM0601  - DCLGEN COPYBOOK FOR VDPM06_MCA_ENRL  TABLE          *00380000
      * DPM0701  - DCLGEN COPYBOOK FOR VDPM07_MCA_CTGRY TABLE          *00380000
      * DPM0801  - DCLGEN COPYBOOK FOR VDPM08_MCA_TERMS TABLE          *00380000
      * DPM1001  - DCLGEN COPYBOOK FOR VDPM10_MCA_LOCK  TABLE          *00380000
      * DPM1401  - DCLGEN COPYBOOK FOR D0006 TABLE          *00380000
      * DPM1601  - DCLGEN COPYBOOK FOR VDPM16_MCA_AMND  TABLE          *00380000
      * DPM1801  - DCLGEN COPYBOOK FOR VDPM18_MCA_LINK  TABLE          *00380000
      * DPM1901  - DCLGEN COPYBOOK FOR VDPM19_LINK_WORK TABLE          *00380000
      * DTM5401  - DCLGEN FOR DISPLAY CONTROL TABLE                    *00380000
      *----------------------------------------------------------------*00610000
      * COPYBOOKS:                                                     *00580000
      * ---------                                                      *00590000
      * DB2000IA - DB2 STANDARD COPYBOOK WITH FORMATTED DISPLAY SQLCA  *00600000
      * DB2000IB - COPYBOOK CONTAINING PICTURE CLAUSE FOR SQLCA        *00600000
      * DB2000IC - COPYBOOK CONTAINING MOVE STATEMENTS TO FORMAT SQLCA *00600000
      *----------------------------------------------------------------
      *----------------------------------------------------------------
      *              M A I N T E N A N C E   H I S T O R Y            *
      *                                                               *
      *                                                               *
      * DATE CHANGED    VERSION     PROGRAMMER                        *
      * ------------    -------     --------------------              *
      *                                                               *
      * 09/21/2007        00.00     COGNIZANT                         *
      *                             INITIAL IMPLEMENTATION            *
      *                                                               *
      * 01/20/2008        01.00     COGNIZANT                         *
      *                             ADDED THE DISPLAY STATMENT        *
      *                                                               *
      *****************************************************************
       ENVIRONMENT DIVISION.
       DATA DIVISION.
       WORKING-STORAGE SECTION.
       01  WS-PROGRAM                      PIC X(08) VALUE 'DPMXMSTU'.
       01  WS-VARIABLES.
             05  WS-TEMPLATE-ID            PIC S9(09) COMP
                                                     VALUE ZEROES.
             05  WS-OLD-TEMPLATE-ID        PIC S9(09) COMP
                                                     VALUE ZEROES.
      *      05  WS-TEMPLATE-NAME          PIC X(150) VALUE SPACES.
             05  WS-TEMPLATE-NAME.
                 49 WS-TEMPLATE-NAME-LEN   PIC S9(04) COMP.
                 49 WS-TEMPLATE-NAME-TEXT  PIC X(498).
             05  WS-TEMPLATE-TYPE          PIC X(01) VALUE SPACES.
                 88 DEALER-CUSTOMIZED      VALUE 'D'.
                 88 CLIENT-CUSTOMIZED      VALUE 'C'.
                 88 EXECUTED-TMPLT         VALUE 'E'.
                 88 WORKING-TMPLT          VALUE 'W'.
                 88 REEXEC-WORKING         VALUE 'R'.
             05  WS-OUT-TEMPLATE-ID        PIC S9(09) COMP
                                                     VALUE ZEROES.
             05  WS-OUT-TEMPLATE-NAME.
                 49 WS-OUT-TEMPLATE-NAME-LEN
                                           PIC S9(04) COMP.
                 49 WS-OUT-TEMPLATE-NAME-TEXT
                                           PIC X(498).
             05  WS-OUT-TEMPLATE-TYPE      PIC X(01) VALUE SPACES.
             05  WS-DELR-CMPNY-ID          PIC X(08) VALUE SPACES.
             05  WS-CLNT-CMPNY-ID          PIC X(08) VALUE SPACES.
             05  WS-DLR-STAT-CD            PIC X(01) VALUE SPACES.
             05  WS-CLNT-STAT-CD           PIC X(01) VALUE SPACES.
             05  WS-USER-ID                PIC X(10) VALUE SPACES.
             05  WS-EXEC-TS                PIC X(26) VALUE SPACES.
             05  WS-EXEC-FLAG              PIC X(01) VALUE SPACES.
                 88 EXECUTE-MCA            VALUE 'E'.
                 88 REEXECUTE-MCA          VALUE 'R'.
             05  WS-CURRENT-TIMESTAMP      PIC X(26) VALUE SPACES.
             05  WS-TEMP-SEQUENCE-NO       PIC S9(9) COMP
                                                     VALUE ZEROES.
             05  WS-AMNDT-SEQUENCE-NO      PIC S9(18) COMP-3
                                                     VALUE ZEROES.
             05  WS-LOCK-CHECK             PIC S9(04) COMP
                                                     VALUE ZEROES.
             05  WS-ISDA-TMPLT-ID          PIC S9(04) COMP
                                                     VALUE ZEROES.
             05  WS-TMPLT-ID-CHK           PIC S9(04) COMP
                                                     VALUE ZEROES.
             05  WS-EOF-RECORD-VALUE       PIC X(1)  VALUE 'N'.
                 88 EOF-RECORD             VALUE 'Y'.
                 88 NOT-EOF-RECORD         VALUE 'N'.
             05  WS-EOF-LINK-RECORD-VALUE  PIC X(1)  VALUE 'N'.
                 88 EOF-LINK-RECORD        VALUE 'Y'.
                 88 NOT-EOF-LINK-RECORD    VALUE 'N'.
                                                                        00850000
             05  WS-DISPLAY-CONTROL-FLAG   PIC X(001) VALUE SPACES.     01190000
                 88 DISPLAY-ACTIVE         VALUE 'Y'.
                 88 DISPLAY-INACTIVE       VALUE 'N'.

       01  WS-ERROR-AREA.
             05  WS-PARAGRAPH-NAME         PIC X(40).
             05  FILLER                    PIC X VALUE ','.
             05  WS-TABLE-NAME             PIC X(18).
             05  WS-SQLCODE                PIC 9(7).
                                                                        00850000
       01  WS-ERROR-MSG.                                                00860000
             05  WS-INVALID-TMPLT-TYPE     PIC X(50)                    00560100
                 VALUE 'INVALID TEMPLATE TYPE'.                         00560100
             05  WS-INVALID-CLIENT-STAT-CD PIC X(50)                    00560100
                 VALUE 'INVALID CLIENT STATUS CODE'.                    00560100
             05  WS-MCA-ALREADY-EXECUTED   PIC X(50)                    00560100
                 VALUE 'MCA is already Executed'.                       00560100
             05  WS-INVALID-DEALER-STAT-CD PIC X(50)                    00560100
                 VALUE 'INVALID DEALER STATUS CODE'.                    00560100
             05  WS-ALREADY-APPLIED        PIC X(50)                    00560100
                 VALUE 'MCA is already assigned to this counterparty'.  00560100
             05  WS-MCA-WORKED-BY-OTHER    PIC X(50)                    00560100
                 VALUE 'MCA is Currently being worked by another user'. 00560100
             05  WS-ALREADY-RENOG-DONE     PIC X(50)                    00560100
                 VALUE 'A Renegotiated MCA is already created'.         00560100
             05  WS-ALREADY-SUB-TO-CP      PIC X(50)                    00560100
                 VALUE 'MCA is Already Submitted to the Counterparty'.  00560100
             05  WS-DATABASE-ERROR         PIC X(50)                    00560100
                 VALUE 'DATABASE ERROR OCCURRED. PLEASE CONTACT DTCC'.  00560100

       01  WS-SWITCHES.
             05 WS-TMPLT-ID-TYPE           PIC X(1)  VALUE 'Y'.
                88 MAIN-TMPLT                        VALUE 'Y'.
                88 WORK-TMPLT                        VALUE 'N'.

      *****************************************************************
      *                        SQL INCLUDES                            *
      ******************************************************************
           EXEC SQL
                INCLUDE SQLCA
           END-EXEC.

           EXEC SQL
                INCLUDE DPM0601
           END-EXEC.

           EXEC SQL
                INCLUDE DPM0701
           END-EXEC.

           EXEC SQL
                INCLUDE DPM0801
           END-EXEC.

           EXEC SQL
                INCLUDE DPM1001
           END-EXEC.

           EXEC SQL
                INCLUDE DPM1401
           END-EXEC.

           EXEC SQL
                INCLUDE DPM1601
           END-EXEC.

           EXEC SQL
                INCLUDE DPM1801
           END-EXEC.

           EXEC SQL
                INCLUDE DPM1901
           END-EXEC.

           EXEC SQL
                INCLUDE DTM5401
           END-EXEC.

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
       01  LS-TEMPLATE-ID                  PIC S9(09) COMP.
      *01  LS-TEMPLATE-NAME                PIC X(150).
       01  LS-TEMPLATE-NAME.
           49 LS-TEMPLATE-NAME-LEN         PIC S9(04) COMP.
           49 LS-TEMPLATE-NAME-TEXT        PIC X(498).
       01  LS-TEMPLATE-TYPE                PIC X(01).
       01  LS-DELR-CMPNY-ID                PIC X(08).
       01  LS-CLNT-CMPNY-ID                PIC X(08).
       01  LS-DLR-STAT-CD                  PIC X(01).
       01  LS-CLNT-STAT-CD                 PIC X(01).
       01  LS-USER-ID                      PIC X(10).
       01  LS-EXEC-TS                      PIC X(26).
       01  LS-EXEC-FLAG                    PIC X(01).

       PROCEDURE DIVISION USING  OUTSQLCA,
                                 LS-SP-ERROR-AREA,
                                 LS-SP-RC,
                                 LS-TEMPLATE-ID,
                                 LS-TEMPLATE-NAME,
                                 LS-TEMPLATE-TYPE,
                                 LS-DELR-CMPNY-ID,
                                 LS-CLNT-CMPNY-ID,
                                 LS-DLR-STAT-CD,
                                 LS-CLNT-STAT-CD,
                                 LS-USER-ID,
                                 LS-EXEC-TS,
                                 LS-EXEC-FLAG.
      *---------*
       0000-MAIN.
      *---------*

           PERFORM 1000-INIT-AND-CHECK-PARMS

           PERFORM 2000-UPDATE-TEMPLATE-STATUS

           PERFORM 9990-GOBACK
           .
      *-------------------------*
       1000-INIT-AND-CHECK-PARMS.
      *-------------------------*

           MOVE '1000-INIT-AND-CHECK-PARMS' TO WS-PARAGRAPH-NAME

           MOVE SPACES                      TO LS-SP-ERROR-AREA
                                               LS-SP-RC
           INITIALIZE WS-TEMPLATE-NAME

           MOVE LS-TEMPLATE-ID              TO WS-TEMPLATE-ID
      *    MOVE LS-TEMPLATE-NAME            TO WS-TEMPLATE-NAME
           MOVE LS-TEMPLATE-NAME-LEN        TO WS-TEMPLATE-NAME-LEN
           MOVE LS-TEMPLATE-NAME-TEXT(1:WS-TEMPLATE-NAME-LEN)
                                            TO WS-TEMPLATE-NAME-TEXT
           MOVE LS-TEMPLATE-TYPE            TO WS-TEMPLATE-TYPE
           MOVE LS-DELR-CMPNY-ID            TO WS-DELR-CMPNY-ID
           MOVE LS-CLNT-CMPNY-ID            TO WS-CLNT-CMPNY-ID
           MOVE LS-DLR-STAT-CD              TO WS-DLR-STAT-CD
           MOVE LS-CLNT-STAT-CD             TO WS-CLNT-STAT-CD
           MOVE LS-USER-ID                  TO WS-USER-ID
           MOVE LS-EXEC-TS                  TO WS-EXEC-TS
           MOVE LS-EXEC-FLAG                TO WS-EXEC-FLAG

           MOVE LS-TEMPLATE-ID              TO WS-OUT-TEMPLATE-ID
      *    MOVE LS-TEMPLATE-NAME            TO WS-OUT-TEMPLATE-NAME
           MOVE LS-TEMPLATE-NAME-LEN        TO WS-OUT-TEMPLATE-NAME-LEN
           MOVE LS-TEMPLATE-NAME-TEXT       TO WS-OUT-TEMPLATE-NAME-TEXT
           MOVE LS-TEMPLATE-TYPE            TO WS-OUT-TEMPLATE-TYPE

           PERFORM 9600-CHECK-DEBUG-TABLE

           IF DISPLAY-ACTIVE
              EXEC SQL
                   SET :WS-CURRENT-TIMESTAMP = CURRENT TIMESTAMP
              END-EXEC

              DISPLAY "PROGRAM DPMXMSTU STARTED : " WS-CURRENT-TIMESTAMP
              DISPLAY "WS-TEMPLATE-ID    " WS-TEMPLATE-ID
              DISPLAY "WS-TEMPLATE-NAME-LEN "  WS-TEMPLATE-NAME-LEN
              DISPLAY "WS-TEMPLATE-NAME-TEXT " WS-TEMPLATE-NAME-TEXT
              DISPLAY "WS-TEMPLATE-TYPE  " WS-TEMPLATE-TYPE
              DISPLAY "WS-DELR-CMPNY-ID  " WS-DELR-CMPNY-ID
              DISPLAY "WS-CLNT-CMPNY-ID  " WS-CLNT-CMPNY-ID
              DISPLAY "WS-DLR-STAT-CD    " WS-DLR-STAT-CD
              DISPLAY "WS-CLNT-STAT-CD   " WS-CLNT-STAT-CD
              DISPLAY "WS-USER-ID        " WS-USER-ID
              DISPLAY "WS-EXEC-TS        " WS-EXEC-TS
              DISPLAY "WS-EXEC-FLAG      " WS-EXEC-FLAG
           END-IF
           .
      *---------------------------*
       2000-UPDATE-TEMPLATE-STATUS.
      *---------------------------*

           MOVE '2000-UPDATE-TEMPLATE-STATUS'
                                            TO WS-PARAGRAPH-NAME
           IF DISPLAY-ACTIVE
              DISPLAY WS-PARAGRAPH-NAME
           END-IF

           EVALUATE TRUE
              WHEN DEALER-CUSTOMIZED
                PERFORM 9000-CREATE-CP-FINAL
              WHEN CLIENT-CUSTOMIZED
              WHEN EXECUTED-TMPLT
                PERFORM 2200-APP-TO-CLIENT-OR-EXEC
              WHEN WORKING-TMPLT
              WHEN REEXEC-WORKING
                PERFORM 2300-SUBMIT-TO-CP
              WHEN OTHER
                 MOVE  WS-INVALID-TMPLT-TYPE
                                            TO LS-SP-ERROR-AREA
                 MOVE  'SP50'               TO LS-SP-RC
                 PERFORM 9990-GOBACK
           END-EVALUATE
           .
      *--------------------------*
       2200-APP-TO-CLIENT-OR-EXEC.
      *--------------------------*

           MOVE '2200-APP-TO-CLIENT-OR-EXEC'
                                            TO WS-PARAGRAPH-NAME

           IF REEXECUTE-MCA
              PERFORM 2201-CHECK-RENOG-STAT
              PERFORM 2230-CREATE-WORKING-TEMPLATE
           ELSE
              PERFORM 2205-APPLY-TO-CLIENT
           END-IF
           .
      *----------------------*
       2201-CHECK-RENOG-STAT.
      *----------------------*

           MOVE '2201-CHECK-RENOG-STAT'     TO WS-PARAGRAPH-NAME

           IF DISPLAY-ACTIVE
              DISPLAY WS-PARAGRAPH-NAME
           END-IF

           INITIALIZE WS-ISDA-TMPLT-ID

           EXEC SQL
                SELECT MCA_ISDA_TMPLT_ID
                  INTO :WS-ISDA-TMPLT-ID
                  FROM D0006
                 WHERE MCA_TMPLT_ID = :WS-TEMPLATE-ID
           END-EXEC

           EVALUATE SQLCODE
              WHEN 0
                 CONTINUE
              WHEN OTHER
                 MOVE 'D0006'    TO WS-TABLE-NAME
                 PERFORM 9700-SQL-ERROR
           END-EVALUATE

           EXEC SQL
                SELECT MCA_TMPLT_ID
                  INTO :WS-TMPLT-ID-CHK
                  FROM D0006
                 WHERE MCA_ISDA_TMPLT_ID = :WS-ISDA-TMPLT-ID
                   AND MCA_TMPLT_TYPE_CD = 'R'
                   AND DELR_CMPNY_ID     = :WS-DELR-CMPNY-ID
                   AND CLNT_CMPNY_ID     = :WS-CLNT-CMPNY-ID
                FETCH FIRST ROW ONLY
                WITH UR
           END-EXEC

           EVALUATE SQLCODE
              WHEN 0
                 MOVE  WS-ALREADY-RENOG-DONE
                                            TO LS-SP-ERROR-AREA
                 MOVE  'SP04'               TO LS-SP-RC
                 PERFORM 9990-GOBACK
              WHEN 100
                 CONTINUE
              WHEN OTHER
                 MOVE 'D0006'    TO WS-TABLE-NAME
                 PERFORM 9700-SQL-ERROR
           END-EVALUATE
           .
      *---------------------*
       2205-APPLY-TO-CLIENT.
      *---------------------*

           MOVE '2205-APPLY-TO-CLIENT'      TO WS-PARAGRAPH-NAME

           IF DISPLAY-ACTIVE
              DISPLAY WS-PARAGRAPH-NAME
           END-IF

           PERFORM 2210-CHECK-TEMPLATE

           IF MAIN-TMPLT AND CLIENT-CUSTOMIZED
              MOVE  WS-ALREADY-SUB-TO-CP    TO LS-SP-ERROR-AREA
              MOVE  'SP05'                  TO LS-SP-RC
              PERFORM 9990-GOBACK
           END-IF

           IF EXECUTED-TMPLT AND EXECUTE-MCA
              MOVE  WS-MCA-ALREADY-EXECUTED TO LS-SP-ERROR-AREA
              MOVE  'SP03'                  TO LS-SP-RC
              PERFORM 9990-GOBACK
           END-IF

           IF WORK-TMPLT
              PERFORM 2220-SAVE-NEW-TEMPLATE
              PERFORM 2230-CREATE-WORKING-TEMPLATE
              MOVE WS-TEMPLATE-ID           TO WS-OLD-TEMPLATE-ID
              PERFORM 9500-DELETE-WORK
           ELSE
              PERFORM 9000-CREATE-CP-FINAL
           END-IF
           .
      *--------------------*
       2210-CHECK-TEMPLATE.
      *--------------------*

           MOVE '2210-CHECK-TEMPLATE'       TO WS-PARAGRAPH-NAME

           IF DISPLAY-ACTIVE
              DISPLAY WS-PARAGRAPH-NAME
           END-IF

           EXEC SQL
                SELECT MCA_TMPLT_ID
                  INTO :D014-MCA-TMPLT-ID
                  FROM D0006
                 WHERE MCA_TMPLT_ID = :WS-TEMPLATE-ID
           END-EXEC

           EVALUATE SQLCODE
              WHEN 0
                 SET MAIN-TMPLT             TO TRUE
              WHEN 100
                 SET WORK-TMPLT             TO TRUE
              WHEN OTHER
                 MOVE 'D0006'    TO WS-TABLE-NAME
                 PERFORM 9700-SQL-ERROR
           END-EVALUATE
           .
      *------------------------*
       2220-SAVE-NEW-TEMPLATE.
      *------------------------*

           MOVE '2220-SAVE-NEW-TEMPLATE'    TO WS-PARAGRAPH-NAME

           IF DISPLAY-ACTIVE
              DISPLAY WS-PARAGRAPH-NAME
           END-IF

           PERFORM 2221-CHECK-ENRL-STAT

      *    MOVE WS-TEMPLATE-NAME            TO D014-MCA-TMPLT-NM
           MOVE WS-TEMPLATE-NAME-LEN        TO D014-MCA-TMPLT-NM-LEN
           MOVE WS-TEMPLATE-NAME-TEXT       TO D014-MCA-TMPLT-NM-TEXT

           IF DISPLAY-ACTIVE
              DISPLAY WS-PARAGRAPH-NAME
           END-IF

           EXEC SQL
                INSERT INTO D0006
                        (SELECT MCA_TMPLT_ID
                               ,MCA_TMPLT_NM
                               ,MCA_TMPLT_SHORT_NM
                               ,MCA_TMPLT_GROUP_CD
                               ,MCA_TMPLT_TYPE_CD
                               ,DELR_CMPNY_ID
                               ,CLNT_CMPNY_ID
                               ,ATTRB_PRDCT_ID
                               ,ATTRB_SUB_PRDCT_ID
                               ,ATTRB_REGN_ID
                               ,MCA_PBLTN_DT
                               ,MCA_END_DT
                               ,MCA_STAT_IN
                               ,MCA_EXE_TS
                               ,MCA_DELR_STAT_CD
                               ,MCA_CLNT_STAT_CD
                               ,MCA_ISDA_TMPLT_ID
                               ,MCA_CSTMZ_TMPLT_ID
                               ,MCA_TMPLT_RQSTR_ID
                               ,MCA_TMPLT_APRVR_ID
                               ,ROW_UPDT_TS
                               ,ROW_UPDT_USER_ID
                         FROM VDPM15_TMPLT_WORK
                       WHERE MCA_TMPLT_ID = :WS-TEMPLATE-ID)
           END-EXEC

           EVALUATE SQLCODE
              WHEN 0
                 MOVE 'SP00'                TO LS-SP-RC
                 PERFORM 2220A-SAVE-NEW-AMND
              WHEN 100
                 CONTINUE
              WHEN OTHER
                 MOVE 'D0006'    TO WS-TABLE-NAME
                 PERFORM 9700-SQL-ERROR
           END-EVALUATE
           .
      *--------------------*
       2220A-SAVE-NEW-AMND.
      *--------------------*

           MOVE '2220A-SAVE-NEW-AMND'       TO WS-PARAGRAPH-NAME

           IF DISPLAY-ACTIVE
              DISPLAY WS-PARAGRAPH-NAME
           END-IF

           EXEC SQL
                INSERT INTO VDPM16_MCA_AMND
                         (SELECT  MCA_AMND_ID
                                 ,MCA_TMPLT_ID
                                 ,ATTRB_CTGRY_ID
                                 ,CTGRY_SQ
                                 ,ATTRB_TERM_ID
                                 ,TERM_SQ
                                 ,MCA_ISDA_AMND_ID
                                 ,ROW_UPDT_TS
                                 ,ROW_UPDT_USER_ID
                            FROM VDPM17_AMND_WORK
                           WHERE MCA_TMPLT_ID = :WS-TEMPLATE-ID)

           END-EXEC

           EVALUATE SQLCODE
              WHEN 0
                 MOVE 'SP00'                TO LS-SP-RC
                 PERFORM 2220B-SAVE-NEW-LINKS
              WHEN OTHER
                 MOVE 'VDPM16_MCA_AMND'     TO WS-TABLE-NAME
                 PERFORM 9700-SQL-ERROR
           END-EVALUATE
           .
      *---------------------*
       2220B-SAVE-NEW-LINKS.
      *---------------------*

           MOVE '2220B-SAVE-NEW-LINKS'      TO WS-PARAGRAPH-NAME

           IF DISPLAY-ACTIVE
              DISPLAY WS-PARAGRAPH-NAME
           END-IF

           EXEC SQL
                INSERT INTO VDPM18_MCA_LINK
                                 (MCA_AMND_ID
                                 ,MCA_VALUE_ID
                                 ,MCA_VALUE_TYPE_CD
                                 ,AMND_STAT_CD
                                 ,ROW_UPDT_TS
                                 ,ROW_UPDT_USER_ID)
                         (SELECT  DPM19.MCA_AMND_ID
                                 ,DPM19.MCA_VALUE_ID
                                 ,DPM19.MCA_VALUE_TYPE_CD
                                 ,DPM19.AMND_STAT_CD
                                 ,DPM19.ROW_UPDT_TS
                                 ,DPM19.ROW_UPDT_USER_ID
                            FROM VDPM16_MCA_AMND         DPM16
                                ,VDPM19_LINK_WORK        DPM19
                           WHERE DPM16.MCA_TMPLT_ID  = :WS-TEMPLATE-ID
                             AND DPM16.MCA_AMND_ID   = DPM19.MCA_AMND_ID
                             AND DPM19.MCA_ACCS_STAT_CD = 'U')
           END-EXEC

           EVALUATE SQLCODE
              WHEN 0
                 MOVE 'SP00'                TO LS-SP-RC
              WHEN OTHER
                 MOVE 'VDPM18_MCA_LINK'     TO WS-TABLE-NAME
                 PERFORM 9700-SQL-ERROR
           END-EVALUATE
           .
      *---------------------*
       2221-CHECK-ENRL-STAT.
      *---------------------*

           MOVE '2221-CHECK-ENRL-STAT'      TO WS-PARAGRAPH-NAME

           IF DISPLAY-ACTIVE
              DISPLAY WS-PARAGRAPH-NAME
           END-IF

           PERFORM 2221A-GET-ISDA-WORK-TMPLT-ID
           MOVE D014-MCA-ISDA-TMPLT-ID      TO D006-RQST-TMPLT-ID

           MOVE WS-DELR-CMPNY-ID            TO D006-DELR-CMPNY-ID
           MOVE WS-CLNT-CMPNY-ID            TO D006-CLNT-CMPNY-ID

           EXEC SQL
                SELECT ASGD_TMPLT_ID
                  INTO :D006-ASGD-TMPLT-ID
                  FROM VDPM06_MCA_ENRL
                 WHERE DELR_CMPNY_ID = :D006-DELR-CMPNY-ID
                   AND CLNT_CMPNY_ID = :D006-CLNT-CMPNY-ID
                   AND RQST_TMPLT_ID = :D006-RQST-TMPLT-ID
           END-EXEC

           EVALUATE SQLCODE
              WHEN 0
                 IF D006-ASGD-TMPLT-ID > 0
                    MOVE 'SP01'             TO LS-SP-RC
                    MOVE WS-ALREADY-APPLIED TO LS-SP-ERROR-AREA
                    PERFORM 9990-GOBACK
                 ELSE
                    CONTINUE
                 END-IF
              WHEN OTHER
                 MOVE 'VDPM06_MCA_ENRL'     TO WS-TABLE-NAME
                 PERFORM 9700-SQL-ERROR
           END-EVALUATE
           .
      *----------------------------*
       2221A-GET-ISDA-WORK-TMPLT-ID.
      *----------------------------*

           MOVE '2221A-GET-ISDA-WORK-TMPLT-ID'
                                            TO WS-PARAGRAPH-NAME

           IF DISPLAY-ACTIVE
              DISPLAY WS-PARAGRAPH-NAME
           END-IF

           EXEC SQL
                SELECT MCA_ISDA_TMPLT_ID
                  INTO :D014-MCA-ISDA-TMPLT-ID
                  FROM VDPM15_TMPLT_WORK
                 WHERE MCA_TMPLT_ID = :WS-TEMPLATE-ID
           END-EXEC

           EVALUATE SQLCODE
              WHEN 0
                 CONTINUE
              WHEN OTHER
                 MOVE 'D0006'    TO WS-TABLE-NAME
                 PERFORM 9700-SQL-ERROR
           END-EVALUATE
           .

      *-----------------------------*
       2230-CREATE-WORKING-TEMPLATE.
      *-----------------------------*

           MOVE '2230-CREATE-WORKING-TEMPLATE'
                                            TO WS-PARAGRAPH-NAME
           IF DISPLAY-ACTIVE
              DISPLAY WS-PARAGRAPH-NAME
           END-IF

           PERFORM 9050-GET-TEMPLATE-DETAILS

           PERFORM 9100-GET-NEXT-TEMPLATE-SEQ

           PERFORM 2231-INSERT-NEW-TEMPLATE

           PERFORM 9300-CREATE-STATIC-GRID

           IF NOT REEXECUTE-MCA
              PERFORM 2232-UPDATE-ENRL-STAT
           END-IF
           .

      *-------------------------*
       2231-INSERT-NEW-TEMPLATE.
      *-------------------------*

           MOVE '2231-INSERT-NEW-TEMPLATE'  TO WS-PARAGRAPH-NAME

           IF DISPLAY-ACTIVE
              DISPLAY WS-PARAGRAPH-NAME
           END-IF

           MOVE WS-TEMP-SEQUENCE-NO         TO D014-MCA-TMPLT-ID
                                               WS-OUT-TEMPLATE-ID
      *    MOVE WS-TEMPLATE-NAME            TO D014-MCA-TMPLT-NM
           MOVE WS-TEMPLATE-NAME-LEN        TO D014-MCA-TMPLT-NM-LEN
           MOVE WS-TEMPLATE-NAME-TEXT       TO D014-MCA-TMPLT-NM-TEXT

           MOVE WS-USER-ID                  TO D014-ROW-UPDT-USER-ID

           MOVE WS-TEMPLATE-ID              TO D014-MCA-CSTMZ-TMPLT-ID

           IF REEXECUTE-MCA
              MOVE 'R'                      TO D014-MCA-TMPLT-TYPE-CD
           ELSE
              MOVE 'W'                      TO D014-MCA-TMPLT-TYPE-CD
           END-IF

           MOVE D014-MCA-TMPLT-TYPE-CD      TO WS-OUT-TEMPLATE-TYPE
           MOVE WS-DELR-CMPNY-ID            TO D014-DELR-CMPNY-ID
           MOVE WS-CLNT-CMPNY-ID            TO D014-CLNT-CMPNY-ID
           MOVE WS-DLR-STAT-CD              TO D014-MCA-DELR-STAT-CD
           MOVE WS-CLNT-STAT-CD             TO D014-MCA-CLNT-STAT-CD

           EXEC SQL
                INSERT INTO D0006
                  VALUES (:D014-MCA-TMPLT-ID
                         ,:D014-MCA-TMPLT-NM
                         ,:D014-MCA-TMPLT-SHORT-NM
                         ,:D014-MCA-TMPLT-GROUP-CD
                         ,:D014-MCA-TMPLT-TYPE-CD
                         ,:D014-DELR-CMPNY-ID
                         ,:D014-CLNT-CMPNY-ID
                         ,:D014-ATTRB-PRDCT-ID
                         ,:D014-ATTRB-SUB-PRDCT-ID
                         ,:D014-ATTRB-REGN-ID
                         ,:D014-MCA-PBLTN-DT
                         ,:D014-MCA-END-DT
                         ,:D014-MCA-STAT-IN
                         ,:D014-MCA-EXE-TS
                         ,:D014-MCA-DELR-STAT-CD
                         ,:D014-MCA-CLNT-STAT-CD
                         ,:D014-MCA-ISDA-TMPLT-ID
                         ,:D014-MCA-CSTMZ-TMPLT-ID
                         ,:D014-MCA-TMPLT-RQSTR-ID
                         ,:D014-MCA-TMPLT-APRVR-ID
                         ,CURRENT_TIMESTAMP
                         ,:D014-ROW-UPDT-USER-ID)
           END-EXEC

           EVALUATE SQLCODE
              WHEN 0
                 MOVE 'SP00'                TO LS-SP-RC
              WHEN OTHER
                 MOVE 'D0006'    TO WS-TABLE-NAME
                 PERFORM 9700-SQL-ERROR
           END-EVALUATE
           .

      *---------------------*
       2232-UPDATE-ENRL-STAT.
      *---------------------*

           MOVE '2232-UPDATE-ENRL-STAT'     TO WS-PARAGRAPH-NAME

           IF DISPLAY-ACTIVE
              DISPLAY WS-PARAGRAPH-NAME
           END-IF
           MOVE D014-MCA-ISDA-TMPLT-ID      TO D006-RQST-TMPLT-ID
           MOVE WS-DELR-CMPNY-ID            TO D006-DELR-CMPNY-ID
           MOVE WS-CLNT-CMPNY-ID            TO D006-CLNT-CMPNY-ID

           EXEC SQL
                UPDATE VDPM06_MCA_ENRL
                   SET ASGD_TMPLT_ID = :WS-OUT-TEMPLATE-ID
                      ,ROW_UPDT_TS   = CURRENT TIMESTAMP
                 WHERE DELR_CMPNY_ID = :D006-DELR-CMPNY-ID
                   AND CLNT_CMPNY_ID = :D006-CLNT-CMPNY-ID
                   AND RQST_TMPLT_ID = :D006-RQST-TMPLT-ID
           END-EXEC

           EVALUATE SQLCODE
              WHEN 0
                 MOVE 'SP00'                TO LS-SP-RC
              WHEN OTHER
                 MOVE 'VDPM06_MCA_ENRL'     TO WS-TABLE-NAME
                 PERFORM 9700-SQL-ERROR
           END-EVALUATE
           .

      *------------------*
       2300-SUBMIT-TO-CP.
      *------------------*

           MOVE '2300-SUBMIT-TO-CP'         TO WS-PARAGRAPH-NAME

           IF DISPLAY-ACTIVE
              DISPLAY WS-PARAGRAPH-NAME
           END-IF

           PERFORM 2301-CHECK-TMPLT-LOCK-STAT


           IF EXECUTE-MCA
              PERFORM 2305-EXECUTE-MCA
           ELSE
              PERFORM 2310-UPDATE-LINKS
              PERFORM 2320-UPDATE-TEMPLATE
           END-IF
           .
      *--------------------------*
       2301-CHECK-TMPLT-LOCK-STAT.
      *--------------------------*

           MOVE '2301-CHECK-TMPLT-LOCK-STAT'TO WS-PARAGRAPH-NAME

           IF DISPLAY-ACTIVE
              DISPLAY WS-PARAGRAPH-NAME
              DISPLAY 'WS-TEMPLATE-ID ' WS-TEMPLATE-ID
           END-IF

           EXEC SQL
                SELECT 1
                  INTO :WS-LOCK-CHECK
                  FROM VDPM10_MCA_LOCK
                 WHERE MCA_TMPLT_ID = :WS-TEMPLATE-ID
           END-EXEC

           EVALUATE SQLCODE
              WHEN 0
                 MOVE 'SP02'                TO LS-SP-RC
                 MOVE WS-MCA-WORKED-BY-OTHER
                                            TO LS-SP-ERROR-AREA
                 PERFORM 9990-GOBACK
              WHEN 100
                 CONTINUE
              WHEN OTHER
                 MOVE 'VDPM10_MCA_LOCK'     TO WS-TABLE-NAME
                 PERFORM 9700-SQL-ERROR
           END-EVALUATE
           .
      *------------------*
       2305-EXECUTE-MCA.
      *------------------*

           MOVE '2305-EXECUTE-MCA'          TO WS-PARAGRAPH-NAME

           IF DISPLAY-ACTIVE
              DISPLAY WS-PARAGRAPH-NAME
           END-IF

           PERFORM 2305A-CHECK-CLIENT-STATUS

           MOVE WS-TEMPLATE-ID              TO D014-MCA-TMPLT-ID
                                               WS-OUT-TEMPLATE-ID
           MOVE 'E'                         TO D014-MCA-TMPLT-TYPE-CD
                                               WS-OUT-TEMPLATE-TYPE
      *    MOVE WS-TEMPLATE-NAME            TO D014-MCA-TMPLT-NM
           MOVE WS-TEMPLATE-NAME-LEN        TO D014-MCA-TMPLT-NM-LEN
           MOVE WS-TEMPLATE-NAME-TEXT       TO D014-MCA-TMPLT-NM-TEXT
           MOVE WS-EXEC-TS                  TO D014-MCA-EXE-TS
           MOVE WS-DLR-STAT-CD              TO D014-MCA-DELR-STAT-CD
           MOVE WS-USER-ID                  TO D014-ROW-UPDT-USER-ID

           EXEC SQL
                UPDATE D0006
                   SET MCA_TMPLT_NM      = :D014-MCA-TMPLT-NM
                      ,MCA_TMPLT_TYPE_CD = :D014-MCA-TMPLT-TYPE-CD
                      ,MCA_EXE_TS        = :D014-MCA-EXE-TS
                      ,MCA_DELR_STAT_CD  = :D014-MCA-DELR-STAT-CD
                      ,ROW_UPDT_TS       = CURRENT TIMESTAMP
                      ,ROW_UPDT_USER_ID  = :D014-ROW-UPDT-USER-ID
                 WHERE MCA_TMPLT_ID      = :D014-MCA-TMPLT-ID
           END-EXEC

           EVALUATE SQLCODE
              WHEN 0
                 MOVE 'SP00'                TO LS-SP-RC
              WHEN OTHER
                 MOVE 'D0006'    TO WS-TABLE-NAME
                 PERFORM 9700-SQL-ERROR
           END-EVALUATE
           .
      *-------------------------*
       2305A-CHECK-CLIENT-STATUS.
      *-------------------------*

           MOVE '2305A-CHECK-CLIENT-STATUS' TO WS-PARAGRAPH-NAME

           IF DISPLAY-ACTIVE
              DISPLAY WS-PARAGRAPH-NAME
           END-IF

           IF WS-DLR-STAT-CD NOT = 'A'
              MOVE  WS-INVALID-DEALER-STAT-CD
                                            TO LS-SP-ERROR-AREA
              MOVE  'SP50'                  TO LS-SP-RC
              PERFORM 9990-GOBACK
           END-IF

           EXEC SQL
                SELECT MCA_CLNT_STAT_CD
                      ,MCA_DELR_STAT_CD
                  INTO :D014-MCA-CLNT-STAT-CD
                      ,:D014-MCA-DELR-STAT-CD
                  FROM D0006
                 WHERE MCA_TMPLT_ID = :WS-TEMPLATE-ID
           END-EXEC

           EVALUATE SQLCODE
              WHEN 0
                 MOVE 'SP00'                TO LS-SP-RC

                 IF D014-MCA-CLNT-STAT-CD NOT = 'A'
                    MOVE  WS-MCA-WORKED-BY-OTHER
                                            TO LS-SP-ERROR-AREA
                    MOVE  'SP02'            TO LS-SP-RC
                    PERFORM 9990-GOBACK
                 END-IF

                 IF D014-MCA-DELR-STAT-CD = 'A'
                    MOVE  WS-MCA-ALREADY-EXECUTED
                                            TO LS-SP-ERROR-AREA
                    MOVE  'SP03'            TO LS-SP-RC
                    PERFORM 9990-GOBACK
                 END-IF

              WHEN OTHER
                 MOVE 'D0006'    TO WS-TABLE-NAME
                 PERFORM 9700-SQL-ERROR
           END-EVALUATE

           EXEC SQL
                SELECT MCA_CLNT_STAT_CD
                  INTO :D014-MCA-CLNT-STAT-CD
                  FROM VDPM15_TMPLT_WORK
                 WHERE MCA_TMPLT_ID = :WS-TEMPLATE-ID
           END-EXEC

           EVALUATE SQLCODE
              WHEN 0
                 IF D014-MCA-CLNT-STAT-CD = 'D'
                    MOVE  WS-MCA-WORKED-BY-OTHER
                                           TO LS-SP-ERROR-AREA
                    MOVE  'SP02'           TO LS-SP-RC
                    PERFORM 9990-GOBACK
                 END-IF
              WHEN 100
                 CONTINUE
              WHEN OTHER
                 MOVE 'VDPM15_TMPLT_WORK'  TO WS-TABLE-NAME
                 PERFORM 9700-SQL-ERROR
           END-EVALUATE

           PERFORM 2310-UPDATE-LINKS
           .
      *------------------*
       2310-UPDATE-LINKS.
      *------------------*

           MOVE '2310-UPDATE-LINKS'         TO WS-PARAGRAPH-NAME

           IF DISPLAY-ACTIVE
              DISPLAY WS-PARAGRAPH-NAME
           END-IF

           IF NOT EXECUTE-MCA
              PERFORM 2310A-CHECK-STATUS-CODE
           END-IF

           PERFORM 2311-DELETE-LINKS

           PERFORM 2312-INSERT-LINKS

           MOVE WS-TEMPLATE-ID              TO WS-OLD-TEMPLATE-ID

           PERFORM 9500-DELETE-WORK

           .
      *-----------------------*
       2310A-CHECK-STATUS-CODE.
      *-----------------------*

           MOVE '2310A-CHECK-STATUS-CODE'   TO WS-PARAGRAPH-NAME

           IF DISPLAY-ACTIVE
              DISPLAY WS-PARAGRAPH-NAME
           END-IF

           EXEC SQL
                SELECT MCA_DELR_STAT_CD
                      ,MCA_CLNT_STAT_CD
                  INTO :D014-MCA-DELR-STAT-CD
                      ,:D014-MCA-CLNT-STAT-CD
                  FROM D0006
                 WHERE MCA_TMPLT_ID = :WS-TEMPLATE-ID
           END-EXEC

           EVALUATE SQLCODE
              WHEN 0
                IF D014-MCA-DELR-STAT-CD = WS-DLR-STAT-CD AND
                   D014-MCA-CLNT-STAT-CD = WS-CLNT-STAT-CD
                   MOVE  WS-ALREADY-SUB-TO-CP
                                            TO LS-SP-ERROR-AREA
                   MOVE  'SP05'             TO LS-SP-RC
                   PERFORM 9990-GOBACK
                END-IF

                IF D014-MCA-DELR-STAT-CD = WS-DLR-STAT-CD OR
                   D014-MCA-CLNT-STAT-CD = WS-CLNT-STAT-CD
                   MOVE  WS-MCA-WORKED-BY-OTHER
                                            TO LS-SP-ERROR-AREA
                   MOVE  'SP02'             TO LS-SP-RC
                   PERFORM 9990-GOBACK
                END-IF
              WHEN OTHER
                 MOVE 'D0006'    TO WS-TABLE-NAME
                 PERFORM 9700-SQL-ERROR
           END-EVALUATE
           .
      *------------------*
       2311-DELETE-LINKS.
      *------------------*

           MOVE '2311-DELETE-LINKS'         TO WS-PARAGRAPH-NAME

           IF DISPLAY-ACTIVE
              DISPLAY WS-PARAGRAPH-NAME
           END-IF

           MOVE WS-TEMPLATE-ID              TO D016-MCA-TMPLT-ID

           EXEC SQL
                DELETE FROM VDPM18_MCA_LINK                 DPM18
                 WHERE DPM18.MCA_AMND_ID IN
                              (SELECT DPM19.MCA_AMND_ID
                                 FROM VDPM16_MCA_AMND       DPM16
                                     ,VDPM19_LINK_WORK      DPM19
                                WHERE DPM16.MCA_TMPLT_ID =
                                                      :D016-MCA-TMPLT-ID
                                  AND DPM16.MCA_AMND_ID  =
                                                    DPM19.MCA_AMND_ID)
           END-EXEC

           EVALUATE SQLCODE
              WHEN 0
                 MOVE 'SP00'                TO LS-SP-RC
              WHEN 100
                 CONTINUE
              WHEN OTHER
                 MOVE 'VDPM18_MCA_LINK'     TO WS-TABLE-NAME
                 PERFORM 9700-SQL-ERROR
           END-EVALUATE
           .
      *------------------*
       2312-INSERT-LINKS.
      *------------------*

           MOVE '2312-INSERT-LINKS'         TO WS-PARAGRAPH-NAME

           IF DISPLAY-ACTIVE
              DISPLAY WS-PARAGRAPH-NAME
           END-IF

           EXEC SQL
                INSERT INTO VDPM18_MCA_LINK
                                 (MCA_AMND_ID
                                 ,MCA_VALUE_ID
                                 ,MCA_VALUE_TYPE_CD
                                 ,AMND_STAT_CD
                                 ,ROW_UPDT_TS
                                 ,ROW_UPDT_USER_ID)
                         (SELECT  DPM19.MCA_AMND_ID
                                 ,DPM19.MCA_VALUE_ID
                                 ,DPM19.MCA_VALUE_TYPE_CD
                                 ,DPM19.AMND_STAT_CD
                                 ,DPM19.ROW_UPDT_TS
                                 ,DPM19.ROW_UPDT_USER_ID
                            FROM VDPM16_MCA_AMND       DPM16
                                ,VDPM19_LINK_WORK DPM19
                           WHERE DPM16.MCA_TMPLT_ID = :WS-TEMPLATE-ID
                             AND DPM16.MCA_AMND_ID  = DPM19.MCA_AMND_ID
                             AND DPM19.MCA_ACCS_STAT_CD = 'U')
           END-EXEC

           EVALUATE SQLCODE
              WHEN 0
                 MOVE 'SP00'                TO LS-SP-RC
              WHEN 100
                 CONTINUE
              WHEN OTHER
                 MOVE 'VDPM18_MCA_LINK'     TO WS-TABLE-NAME
                 PERFORM 9700-SQL-ERROR
           END-EVALUATE
           .
      *--------------------*
       2320-UPDATE-TEMPLATE.
      *--------------------*

           MOVE '2320-UPDATE-TEMPLATE'      TO WS-PARAGRAPH-NAME

           IF DISPLAY-ACTIVE
              DISPLAY WS-PARAGRAPH-NAME
           END-IF

           MOVE WS-DLR-STAT-CD              TO D014-MCA-DELR-STAT-CD
           MOVE WS-CLNT-STAT-CD             TO D014-MCA-CLNT-STAT-CD
           MOVE WS-USER-ID                  TO D014-ROW-UPDT-USER-ID

           EXEC SQL
                UPDATE D0006
                   SET MCA_DELR_STAT_CD = :D014-MCA-DELR-STAT-CD
                      ,MCA_CLNT_STAT_CD = :D014-MCA-CLNT-STAT-CD
                      ,ROW_UPDT_TS      = CURRENT TIMESTAMP
                      ,ROW_UPDT_USER_ID = :D014-ROW-UPDT-USER-ID
                 WHERE MCA_TMPLT_ID     = :WS-TEMPLATE-ID
           END-EXEC

           EVALUATE SQLCODE
              WHEN 0
                 MOVE 'SP00'                TO LS-SP-RC
              WHEN OTHER
                 MOVE 'D0006'    TO WS-TABLE-NAME
                 PERFORM 9700-SQL-ERROR
           END-EVALUATE
           .
      *---------------------*
       9000-CREATE-CP-FINAL.
      *---------------------*

           MOVE '9000-CREATE-CP-FINAL'      TO WS-PARAGRAPH-NAME

           IF DISPLAY-ACTIVE
              DISPLAY WS-PARAGRAPH-NAME
           END-IF

           PERFORM 9050-GET-TEMPLATE-DETAILS

           PERFORM 9100-GET-NEXT-TEMPLATE-SEQ

           PERFORM 9200-INSERT-NEW-TEMPLATE

           PERFORM 9300-CREATE-STATIC-GRID
           .

      *-------------------------*
       9050-GET-TEMPLATE-DETAILS.
      *-------------------------*

           MOVE '9050-GET-TEMPLATE-DETAILS' TO WS-PARAGRAPH-NAME

           IF DISPLAY-ACTIVE
              DISPLAY WS-PARAGRAPH-NAME
           END-IF

           MOVE WS-USER-ID                  TO D014-ROW-UPDT-USER-ID

           EXEC SQL
                SELECT MCA_TMPLT_ID
                      ,MCA_TMPLT_NM
                      ,MCA_TMPLT_SHORT_NM
                      ,MCA_TMPLT_GROUP_CD
                      ,MCA_TMPLT_TYPE_CD
                      ,DELR_CMPNY_ID
                      ,CLNT_CMPNY_ID
                      ,ATTRB_PRDCT_ID
                      ,ATTRB_SUB_PRDCT_ID
                      ,ATTRB_REGN_ID
                      ,MCA_PBLTN_DT
                      ,MCA_END_DT
                      ,MCA_STAT_IN
                      ,MCA_EXE_TS
                      ,MCA_DELR_STAT_CD
                      ,MCA_CLNT_STAT_CD
                      ,MCA_ISDA_TMPLT_ID
                      ,MCA_CSTMZ_TMPLT_ID
                      ,MCA_TMPLT_RQSTR_ID
                      ,MCA_TMPLT_APRVR_ID
                      ,ROW_UPDT_TS
                      ,ROW_UPDT_USER_ID
                  INTO
                      :D014-MCA-TMPLT-ID
                     ,:D014-MCA-TMPLT-NM
                     ,:D014-MCA-TMPLT-SHORT-NM
                     ,:D014-MCA-TMPLT-GROUP-CD
                     ,:D014-MCA-TMPLT-TYPE-CD
                     ,:D014-DELR-CMPNY-ID
                     ,:D014-CLNT-CMPNY-ID
                     ,:D014-ATTRB-PRDCT-ID
                     ,:D014-ATTRB-SUB-PRDCT-ID
                     ,:D014-ATTRB-REGN-ID
                     ,:D014-MCA-PBLTN-DT
                     ,:D014-MCA-END-DT
                     ,:D014-MCA-STAT-IN
                     ,:D014-MCA-EXE-TS
                     ,:D014-MCA-DELR-STAT-CD
                     ,:D014-MCA-CLNT-STAT-CD
                     ,:D014-MCA-ISDA-TMPLT-ID
                     ,:D014-MCA-CSTMZ-TMPLT-ID
                     ,:D014-MCA-TMPLT-RQSTR-ID
                     ,:D014-MCA-TMPLT-APRVR-ID
                     ,:D014-ROW-UPDT-TS
                     ,:D014-ROW-UPDT-USER-ID
                  FROM D0006
                 WHERE MCA_TMPLT_ID = :WS-TEMPLATE-ID
                WITH UR
           END-EXEC

           EVALUATE SQLCODE
              WHEN 0
                 MOVE 'SP00'                TO LS-SP-RC
              WHEN OTHER
                 MOVE 'D0006'    TO WS-TABLE-NAME
                 PERFORM 9700-SQL-ERROR
           END-EVALUATE

           .
      *--------------------------*
       9100-GET-NEXT-TEMPLATE-SEQ.
      *--------------------------*

           MOVE '9100-GET-NEXT-TEMPLATE-SEQ'
                                            TO WS-PARAGRAPH-NAME

           IF DISPLAY-ACTIVE
              DISPLAY WS-PARAGRAPH-NAME
           END-IF

           INITIALIZE WS-TEMP-SEQUENCE-NO

           EXEC SQL
                SET :WS-TEMP-SEQUENCE-NO = NEXT VALUE FOR DPM.SQDPM141
           END-EXEC

           EVALUATE SQLCODE
              WHEN 0
                 MOVE 'SP00'                TO LS-SP-RC

                 IF DISPLAY-ACTIVE
                    DISPLAY "WS-TEMP-SEQUENCE-NO " WS-TEMP-SEQUENCE-NO
                 END-IF

              WHEN OTHER
                 MOVE 'TMPLT_SEQ'           TO WS-TABLE-NAME
                 PERFORM 9700-SQL-ERROR
           END-EVALUATE
           .
      *------------------------*
       9200-INSERT-NEW-TEMPLATE.
      *------------------------*

           MOVE '9200-INSERT-NEW-TEMPLATE'  TO WS-PARAGRAPH-NAME

           IF DISPLAY-ACTIVE
              DISPLAY WS-PARAGRAPH-NAME
           END-IF

           MOVE WS-TEMP-SEQUENCE-NO         TO D014-MCA-TMPLT-ID
                                               WS-OUT-TEMPLATE-ID
      *    MOVE WS-TEMPLATE-NAME            TO D014-MCA-TMPLT-NM
           MOVE WS-TEMPLATE-NAME-LEN        TO D014-MCA-TMPLT-NM-LEN
           MOVE WS-TEMPLATE-NAME-TEXT       TO D014-MCA-TMPLT-NM-TEXT

           MOVE 'C'                         TO D014-MCA-TMPLT-TYPE-CD
                                               WS-OUT-TEMPLATE-TYPE

           MOVE WS-USER-ID                  TO D014-ROW-UPDT-USER-ID
           MOVE SPACES                      TO D014-MCA-DELR-STAT-CD
                                               D014-MCA-CLNT-STAT-CD

           MOVE WS-TEMPLATE-ID              TO D014-MCA-CSTMZ-TMPLT-ID
           MOVE WS-DELR-CMPNY-ID            TO D014-DELR-CMPNY-ID
           MOVE WS-CLNT-CMPNY-ID            TO D014-CLNT-CMPNY-ID

           EXEC SQL
                INSERT INTO VDPM15_TMPLT_WORK
                  VALUES (:D014-MCA-TMPLT-ID
                         ,:D014-MCA-TMPLT-NM
                         ,:D014-MCA-TMPLT-SHORT-NM
                         ,:D014-MCA-TMPLT-GROUP-CD
                         ,:D014-MCA-TMPLT-TYPE-CD
                         ,:D014-DELR-CMPNY-ID
                         ,:D014-CLNT-CMPNY-ID
                         ,:D014-ATTRB-PRDCT-ID
                         ,:D014-ATTRB-SUB-PRDCT-ID
                         ,:D014-ATTRB-REGN-ID
                         ,:D014-MCA-PBLTN-DT
                         ,:D014-MCA-END-DT
                         ,:D014-MCA-STAT-IN
                         ,:D014-MCA-EXE-TS
                         ,:D014-MCA-DELR-STAT-CD
                         ,:D014-MCA-CLNT-STAT-CD
                         ,:D014-MCA-ISDA-TMPLT-ID
                         ,:D014-MCA-CSTMZ-TMPLT-ID
                         ,:D014-MCA-TMPLT-RQSTR-ID
                         ,:D014-MCA-TMPLT-APRVR-ID
                         ,CURRENT TIMESTAMP
                         ,:D014-ROW-UPDT-USER-ID)
           END-EXEC

           EVALUATE SQLCODE
              WHEN 0
                 MOVE 'SP00'                TO LS-SP-RC
              WHEN OTHER
                 MOVE 'VDPM15_TMPLT_WORK'   TO WS-TABLE-NAME
                 PERFORM 9700-SQL-ERROR
           END-EVALUATE
           .
      *------------------------*
       9300-CREATE-STATIC-GRID.
      *------------------------*

           MOVE '9300-CREATE-STATIC-GRID'   TO WS-PARAGRAPH-NAME

           IF DISPLAY-ACTIVE
              DISPLAY WS-PARAGRAPH-NAME
           END-IF

           PERFORM 9310-COPY-CATEGORY-DETAILS

           PERFORM 9320-COPY-TERM-DETAILS

           PERFORM 9330-COPY-AMNDT-DETAILS

           .
      *--------------------------*
       9310-COPY-CATEGORY-DETAILS.
      *--------------------------*

           MOVE '9310-COPY-CATEGORY-DETAILS'
                                            TO WS-PARAGRAPH-NAME

           IF DISPLAY-ACTIVE
              DISPLAY WS-PARAGRAPH-NAME
           END-IF

           SET NOT-EOF-RECORD               TO TRUE

           PERFORM 9310A-DECLARE-CATGRY-CURSOR

           PERFORM 9310B-OPEN-CATGRY-CURSOR

           PERFORM UNTIL EOF-RECORD

              PERFORM 9310C-FETCH-CATGRY-CURSOR

              EVALUATE SQLCODE
                  WHEN 0
                    PERFORM 9310D-INSERT-CATGRY
                  WHEN 100
                    SET EOF-RECORD          TO TRUE
                    PERFORM 9310E-CLOSE-CATGRY-CURSOR
                  WHEN OTHER
                    MOVE 'VDPM07_MCA_CTGRY' TO WS-TABLE-NAME
                    PERFORM 9700-SQL-ERROR
              END-EVALUATE
           END-PERFORM
           .
      *---------------------------*
       9310A-DECLARE-CATGRY-CURSOR.
      *---------------------------*

           MOVE '9310A-DECLARE-CATGRY-CURSOR'
                                            TO WS-PARAGRAPH-NAME

           IF DISPLAY-ACTIVE
              DISPLAY WS-PARAGRAPH-NAME
           END-IF

           EXEC SQL
                DECLARE CTGRY_CSR CURSOR FOR
                 SELECT  ATTRB_CTGRY_ID
                        ,CTGRY_SQ
                        ,CTGRY_STAT_CD
                   FROM VDPM07_MCA_CTGRY
                  WHERE MCA_TMPLT_ID = :WS-TEMPLATE-ID
                 WITH UR
           END-EXEC
           .
      *--------------------------*
       9310B-OPEN-CATGRY-CURSOR.
      *--------------------------*

           MOVE '9310B-OPEN-CATGRY-CURSOR'  TO WS-PARAGRAPH-NAME

           IF DISPLAY-ACTIVE
              DISPLAY WS-PARAGRAPH-NAME
           END-IF

           EXEC SQL
                OPEN CTGRY_CSR
           END-EXEC

           EVALUATE SQLCODE
              WHEN 0
                 MOVE 'SP00'                TO LS-SP-RC
              WHEN OTHER
                 MOVE 'CTGRY_CSR'           TO WS-TABLE-NAME
                 PERFORM 9700-SQL-ERROR
           END-EVALUATE
           .
      *--------------------------*
       9310C-FETCH-CATGRY-CURSOR.
      *--------------------------*

           MOVE '9310C-FETCH-CATGRY-CURSOR' TO WS-PARAGRAPH-NAME

           IF DISPLAY-ACTIVE
              DISPLAY WS-PARAGRAPH-NAME
           END-IF

           EXEC SQL
                FETCH CTGRY_CSR
                 INTO  :D007-ATTRB-CTGRY-ID
                      ,:D007-CTGRY-SQ
                      ,:D007-CTGRY-STAT-CD
           END-EXEC
           .
      *---------------------*
       9310D-INSERT-CATGRY.
      *---------------------*

           MOVE '9310D-INSERT-CATGRY'       TO WS-PARAGRAPH-NAME

           IF DISPLAY-ACTIVE
              DISPLAY WS-PARAGRAPH-NAME
           END-IF

           MOVE WS-TEMP-SEQUENCE-NO         TO D007-MCA-TMPLT-ID

           MOVE WS-USER-ID                  TO D007-ROW-UPDT-USER-ID

           EXEC SQL
                INSERT INTO VDPM07_MCA_CTGRY
                       VALUES (:D007-MCA-TMPLT-ID
                              ,:D007-ATTRB-CTGRY-ID
                              ,:D007-CTGRY-SQ
                              ,:D007-CTGRY-STAT-CD
                              ,CURRENT TIMESTAMP
                              ,:D007-ROW-UPDT-USER-ID)
           END-EXEC

           EVALUATE SQLCODE
              WHEN 0
                 MOVE 'SP00'                TO LS-SP-RC
              WHEN OTHER
                 MOVE 'VDPM07_MCA_CTGRY'    TO WS-TABLE-NAME
                 PERFORM 9700-SQL-ERROR
           END-EVALUATE
           .

      *--------------------------*
       9310E-CLOSE-CATGRY-CURSOR.
      *--------------------------*

           MOVE '9310E-CLOSE-CATGRY-CURSOR' TO WS-PARAGRAPH-NAME

           IF DISPLAY-ACTIVE
              DISPLAY WS-PARAGRAPH-NAME
           END-IF

           EXEC SQL
                CLOSE CTGRY_CSR
           END-EXEC

           EVALUATE SQLCODE
              WHEN 0
                 MOVE 'SP00'                TO LS-SP-RC
              WHEN OTHER
                 MOVE 'CTGRY_CSR'           TO WS-TABLE-NAME
                 PERFORM 9700-SQL-ERROR
           END-EVALUATE
           .
      *------------------------*
       9320-COPY-TERM-DETAILS.
      *------------------------*

           MOVE '9320-COPY-TERM-DETAILS'    TO WS-PARAGRAPH-NAME

           IF DISPLAY-ACTIVE
              DISPLAY WS-PARAGRAPH-NAME
           END-IF


           SET NOT-EOF-RECORD               TO TRUE

           PERFORM 9320A-DECLARE-TERM-CURSOR

           PERFORM 9320B-OPEN-TERM-CURSOR

           PERFORM UNTIL EOF-RECORD

              PERFORM 9320C-FETCH-TERM-CURSOR

              EVALUATE SQLCODE
                  WHEN 0
                    PERFORM 9320D-INSERT-TERM
                  WHEN 100
                    SET EOF-RECORD          TO TRUE
                    PERFORM 9320E-CLOSE-TERM-CURSOR
                  WHEN OTHER
                    MOVE 'VDPM08_MCA_TERMS' TO WS-TABLE-NAME
                    PERFORM 9700-SQL-ERROR
              END-EVALUATE
           END-PERFORM
           .
      *---------------------------*
       9320A-DECLARE-TERM-CURSOR.
      *---------------------------*

           MOVE '9320A-DECLARE-TERM-CURSOR' TO WS-PARAGRAPH-NAME

           IF DISPLAY-ACTIVE
              DISPLAY WS-PARAGRAPH-NAME
           END-IF

           EXEC SQL
                DECLARE TERM_CSR CURSOR FOR
                 SELECT  ATTRB_CTGRY_ID
                        ,CTGRY_SQ
                        ,ATTRB_TERM_ID
                        ,TERM_SQ
                        ,TERM_STAT_CD
                   FROM VDPM08_MCA_TERMS
                  WHERE MCA_TMPLT_ID = :WS-TEMPLATE-ID
                 WITH UR
           END-EXEC
           .
      *------------------------*
       9320B-OPEN-TERM-CURSOR.
      *------------------------*

           MOVE '9320B-OPEN-TERM-CURSOR'    TO WS-PARAGRAPH-NAME

           IF DISPLAY-ACTIVE
              DISPLAY WS-PARAGRAPH-NAME
           END-IF

           EXEC SQL
                OPEN TERM_CSR
           END-EXEC

           EVALUATE SQLCODE
              WHEN 0
                 MOVE 'SP00'                TO LS-SP-RC
              WHEN OTHER
                 MOVE 'TERM_CSR'            TO WS-TABLE-NAME
                 PERFORM 9700-SQL-ERROR
           END-EVALUATE
           .
      *--------------------------*
       9320C-FETCH-TERM-CURSOR.
      *--------------------------*

           MOVE '9320C-FETCH-TERM-CURSOR'   TO WS-PARAGRAPH-NAME

           IF DISPLAY-ACTIVE
              DISPLAY WS-PARAGRAPH-NAME
           END-IF

           EXEC SQL
                FETCH TERM_CSR
                 INTO  :D008-ATTRB-CTGRY-ID
                      ,:D008-CTGRY-SQ
                      ,:D008-ATTRB-TERM-ID
                      ,:D008-TERM-SQ
                      ,:D008-TERM-STAT-CD
           END-EXEC
           .
      *---------------------*
       9320D-INSERT-TERM.
      *---------------------*

           MOVE '9320D-INSERT-TERM'         TO WS-PARAGRAPH-NAME

           IF DISPLAY-ACTIVE
              DISPLAY WS-PARAGRAPH-NAME
           END-IF

           MOVE WS-TEMP-SEQUENCE-NO         TO D008-MCA-TMPLT-ID

           MOVE WS-USER-ID                  TO D008-ROW-UPDT-USER-ID

           EXEC SQL
                INSERT INTO VDPM08_MCA_TERMS
                       VALUES (:D008-MCA-TMPLT-ID
                              ,:D008-ATTRB-CTGRY-ID
                              ,:D008-CTGRY-SQ
                              ,:D008-ATTRB-TERM-ID
                              ,:D008-TERM-SQ
                              ,:D008-TERM-STAT-CD
                              ,CURRENT TIMESTAMP
                              ,:D008-ROW-UPDT-USER-ID)
           END-EXEC

           EVALUATE SQLCODE
              WHEN 0
                 MOVE 'SP00'                TO LS-SP-RC
              WHEN OTHER
                 MOVE 'VDPM08_MCA_TERMS'    TO WS-TABLE-NAME
                 PERFORM 9700-SQL-ERROR
           END-EVALUATE
           .
      *--------------------------*
       9320E-CLOSE-TERM-CURSOR.
      *--------------------------*

           MOVE '9320E-CLOSE-TERM-CURSOR'   TO WS-PARAGRAPH-NAME

           IF DISPLAY-ACTIVE
              DISPLAY WS-PARAGRAPH-NAME
           END-IF

           EXEC SQL
                CLOSE TERM_CSR
           END-EXEC

           EVALUATE SQLCODE
              WHEN 0
                 MOVE 'SP00'                TO LS-SP-RC
              WHEN OTHER
                 MOVE 'TERM_CSR'            TO WS-TABLE-NAME
                 PERFORM 9700-SQL-ERROR
           END-EVALUATE
           .
      *------------------------*
       9330-COPY-AMNDT-DETAILS.
      *------------------------*

           MOVE '9330-COPY-AMNDT-DETAILS'   TO WS-PARAGRAPH-NAME

           IF DISPLAY-ACTIVE
              DISPLAY WS-PARAGRAPH-NAME
           END-IF

           SET NOT-EOF-RECORD               TO TRUE

           PERFORM 9330A-DECLARE-AMNDT-CURSOR

           PERFORM 9330B-OPEN-AMNDT-CURSOR

           PERFORM UNTIL EOF-RECORD

              PERFORM 9330C-FETCH-AMNDT-CURSOR

              EVALUATE SQLCODE
                  WHEN 0
                    MOVE D016-MCA-AMND-ID   TO D018-MCA-AMND-ID
                    PERFORM 9330D-GET-NEXT-AMNDT-SEQ
                    PERFORM 9330E-INSERT-AMNDT
                    PERFORM 9330F-GET-LINK-DETAILS
                  WHEN 100
                    SET EOF-RECORD          TO TRUE
                    PERFORM 9330G-CLOSE-AMNDT-CURSOR
                  WHEN OTHER
                    MOVE 'TDTM79_MCA_AMND'  TO WS-TABLE-NAME
                    PERFORM 9700-SQL-ERROR
              END-EVALUATE
           END-PERFORM
           .
      *---------------------------*
       9330A-DECLARE-AMNDT-CURSOR.
      *---------------------------*

           MOVE '9330A-DECLARE-AMNDT-CURSOR'
                                            TO WS-PARAGRAPH-NAME

           IF DISPLAY-ACTIVE
              DISPLAY WS-PARAGRAPH-NAME
           END-IF

           EXEC SQL
                DECLARE AMNDT_CSR CURSOR FOR
                 SELECT  MCA_AMND_ID
                        ,ATTRB_CTGRY_ID
                        ,CTGRY_SQ
                        ,ATTRB_TERM_ID
                        ,TERM_SQ
                        ,MCA_ISDA_AMND_ID
                   FROM VDPM16_MCA_AMND
                  WHERE MCA_TMPLT_ID = :WS-TEMPLATE-ID
                 WITH UR
           END-EXEC
           .
      *------------------------*
       9330B-OPEN-AMNDT-CURSOR.
      *------------------------*

           MOVE '9330B-OPEN-AMNDT-CURSOR'   TO WS-PARAGRAPH-NAME

           IF DISPLAY-ACTIVE
              DISPLAY WS-PARAGRAPH-NAME
           END-IF

           EXEC SQL
                OPEN AMNDT_CSR
           END-EXEC

           EVALUATE SQLCODE
              WHEN 0
                 MOVE 'SP00'                TO LS-SP-RC
              WHEN OTHER
                 MOVE 'AMNDT_CSR'           TO WS-TABLE-NAME
                 PERFORM 9700-SQL-ERROR
           END-EVALUATE
           .
      *--------------------------*
       9330C-FETCH-AMNDT-CURSOR.
      *--------------------------*

           MOVE '9330C-FETCH-AMNDT-CURSOR'  TO WS-PARAGRAPH-NAME

           IF DISPLAY-ACTIVE
              DISPLAY WS-PARAGRAPH-NAME
           END-IF

           EXEC SQL
                FETCH AMNDT_CSR
                 INTO  :D016-MCA-AMND-ID
                      ,:D016-ATTRB-CTGRY-ID
                      ,:D016-CTGRY-SQ
                      ,:D016-ATTRB-TERM-ID
                      ,:D016-TERM-SQ
                      ,:D016-MCA-ISDA-AMND-ID
           END-EXEC
           .
      *--------------------------*
       9330D-GET-NEXT-AMNDT-SEQ.
      *--------------------------*

           MOVE '9330D-GET-NEXT-AMNDT-SEQ'  TO WS-PARAGRAPH-NAME

           IF DISPLAY-ACTIVE
              DISPLAY WS-PARAGRAPH-NAME
           END-IF

           INITIALIZE WS-AMNDT-SEQUENCE-NO

           EXEC SQL
                SELECT NEXT VALUE FOR DPM.SQDPM171
                  INTO :WS-AMNDT-SEQUENCE-NO
                  FROM SYSIBM.SYSDUMMY1
           END-EXEC

           EVALUATE SQLCODE
              WHEN 0
                 MOVE 'SP00'                TO LS-SP-RC

                 IF DISPLAY-ACTIVE
                    DISPLAY "WS-AMNDT-SEQUENCE-NO" WS-AMNDT-SEQUENCE-NO
                 END-IF

              WHEN OTHER
                 MOVE 'AMNDT_SEQ'           TO WS-TABLE-NAME
                 PERFORM 9700-SQL-ERROR
           END-EVALUATE
           .
      *---------------------*
       9330E-INSERT-AMNDT.
      *---------------------*

           MOVE '9330E-INSERT-AMNDT'        TO WS-PARAGRAPH-NAME

           IF DISPLAY-ACTIVE
              DISPLAY WS-PARAGRAPH-NAME
           END-IF

           MOVE WS-AMNDT-SEQUENCE-NO        TO D016-MCA-AMND-ID
           MOVE WS-TEMP-SEQUENCE-NO         TO D016-MCA-TMPLT-ID

           MOVE WS-USER-ID                  TO D016-ROW-UPDT-USER-ID

           IF WS-OUT-TEMPLATE-TYPE = 'W' OR 'R'
              EXEC SQL
                   INSERT INTO VDPM16_MCA_AMND
                          VALUES (:D016-MCA-AMND-ID
                                 ,:D016-MCA-TMPLT-ID
                                 ,:D016-ATTRB-CTGRY-ID
                                 ,:D016-CTGRY-SQ
                                 ,:D016-ATTRB-TERM-ID
                                 ,:D016-TERM-SQ
                                 ,:D016-MCA-ISDA-AMND-ID
                                 , CURRENT TIMESTAMP
                                 ,:D016-ROW-UPDT-USER-ID)
              END-EXEC
           ELSE
              EXEC SQL
                   INSERT INTO VDPM17_AMND_WORK
                          VALUES (:D016-MCA-AMND-ID
                                 ,:D016-MCA-TMPLT-ID
                                 ,:D016-ATTRB-CTGRY-ID
                                 ,:D016-CTGRY-SQ
                                 ,:D016-ATTRB-TERM-ID
                                 ,:D016-TERM-SQ
                                 ,:D016-MCA-ISDA-AMND-ID
                                 , CURRENT TIMESTAMP
                                 ,:D016-ROW-UPDT-USER-ID)
              END-EXEC
           END-IF

           EVALUATE SQLCODE
              WHEN 0
                 MOVE 'SP00'                TO LS-SP-RC
              WHEN OTHER
                 IF WS-OUT-TEMPLATE-TYPE = 'W' OR 'R'
                    MOVE 'VDPM16_MCA_AMND'  TO WS-TABLE-NAME
                 ELSE
                    MOVE 'VDPM17_AMND_WORK' TO WS-TABLE-NAME
                 END-IF
                 PERFORM 9700-SQL-ERROR
           END-EVALUATE
           .
      *-----------------------*
       9330F-GET-LINK-DETAILS.
      *-----------------------*

           MOVE '9330F-GET-LINK-DETAILS'    TO WS-PARAGRAPH-NAME

           IF DISPLAY-ACTIVE
              DISPLAY WS-PARAGRAPH-NAME
           END-IF

           SET NOT-EOF-LINK-RECORD          TO TRUE

           PERFORM 9330FA-DECLARE-LINK-CURSOR

           PERFORM 9330FB-OPEN-LINK-CURSOR

           PERFORM UNTIL EOF-LINK-RECORD

              PERFORM 9330FC-FETCH-LINK-CURSOR

              EVALUATE SQLCODE
                  WHEN 0
                    PERFORM 9330FD-INSERT-LINK-WRK
                  WHEN 100
                    SET EOF-LINK-RECORD     TO TRUE
                    PERFORM 9330FE-CLOSE-LINK-CURSOR
                  WHEN OTHER
                    MOVE 'VDPM16_MCA_AMND'  TO WS-TABLE-NAME
                    PERFORM 9700-SQL-ERROR
              END-EVALUATE
           END-PERFORM
           .
      *---------------------------*
       9330FA-DECLARE-LINK-CURSOR.
      *---------------------------*

           MOVE '9330FA-DECLARE-LINK-CURSOR'
                                            TO WS-PARAGRAPH-NAME

           IF DISPLAY-ACTIVE
              DISPLAY WS-PARAGRAPH-NAME
           END-IF

           EXEC SQL
                DECLARE IC_LINK_CSR CURSOR FOR
                 SELECT  MCA_VALUE_ID
                        ,MCA_VALUE_TYPE_CD
                        ,AMND_STAT_CD
                   FROM VDPM18_MCA_LINK
                  WHERE MCA_AMND_ID     = :D018-MCA-AMND-ID
                 WITH UR
           END-EXEC
           .
      *------------------------*
       9330FB-OPEN-LINK-CURSOR.
      *------------------------*

           MOVE '9330FB-OPEN-LINK-CURSOR'   TO WS-PARAGRAPH-NAME

           IF DISPLAY-ACTIVE
              DISPLAY WS-PARAGRAPH-NAME
           END-IF

           EXEC SQL
                OPEN IC_LINK_CSR
           END-EXEC

           EVALUATE SQLCODE
              WHEN 0
                 MOVE 'SP00'                TO LS-SP-RC
              WHEN OTHER
                 MOVE 'IC_LINK_CSR'         TO WS-TABLE-NAME
                 PERFORM 9700-SQL-ERROR
           END-EVALUATE
           .
      *--------------------------*
       9330FC-FETCH-LINK-CURSOR.
      *--------------------------*

           MOVE '9330FC-FETCH-LINK-CURSOR'  TO WS-PARAGRAPH-NAME

           IF DISPLAY-ACTIVE
              DISPLAY WS-PARAGRAPH-NAME
           END-IF

           EXEC SQL
                FETCH IC_LINK_CSR
                 INTO  :D018-MCA-VALUE-ID
                      ,:D018-MCA-VALUE-TYPE-CD
                      ,:D018-AMND-STAT-CD
           END-EXEC
           .
      *----------------------*
       9330FD-INSERT-LINK-WRK.
      *----------------------*

           MOVE '9330FD-INSERT-LINK-WRK'    TO WS-PARAGRAPH-NAME

           IF DISPLAY-ACTIVE
              DISPLAY WS-PARAGRAPH-NAME
           END-IF

           MOVE WS-AMNDT-SEQUENCE-NO        TO D018-MCA-AMND-ID

           MOVE 'U'                         TO D019-MCA-ACCS-STAT-CD

           MOVE WS-USER-ID                  TO D018-ROW-UPDT-USER-ID

           IF WS-OUT-TEMPLATE-TYPE = 'W' OR 'R'
              EXEC SQL
                   INSERT INTO VDPM18_MCA_LINK
                                 (MCA_AMND_ID
                                 ,MCA_VALUE_ID
                                 ,MCA_VALUE_TYPE_CD
                                 ,AMND_STAT_CD
                                 ,ROW_UPDT_TS
                                 ,ROW_UPDT_USER_ID)
                          VALUES (:D018-MCA-AMND-ID
                                 ,:D018-MCA-VALUE-ID
                                 ,:D018-MCA-VALUE-TYPE-CD
                                 ,:D018-AMND-STAT-CD
                                 ,CURRENT TIMESTAMP
                                 ,:D018-ROW-UPDT-USER-ID)
              END-EXEC
           ELSE
              EXEC SQL
                   INSERT INTO VDPM19_LINK_WORK
                                 (MCA_AMND_ID
                                 ,MCA_VALUE_ID
                                 ,MCA_VALUE_TYPE_CD
                                 ,MCA_ACCS_STAT_CD
                                 ,AMND_STAT_CD
                                 ,ROW_UPDT_TS
                                 ,ROW_UPDT_USER_ID)
                          VALUES (:D018-MCA-AMND-ID
                                 ,:D018-MCA-VALUE-ID
                                 ,:D018-MCA-VALUE-TYPE-CD
                                 ,:D019-MCA-ACCS-STAT-CD
                                 ,:D018-AMND-STAT-CD
                                 ,CURRENT TIMESTAMP
                                 ,:D018-ROW-UPDT-USER-ID)
              END-EXEC
           END-IF

           EVALUATE SQLCODE
              WHEN 0
                 MOVE 'SP00'                TO LS-SP-RC
              WHEN OTHER
                 IF WS-OUT-TEMPLATE-TYPE = 'W' OR 'R'
                    MOVE 'VDPM18_MCA_LINK'  TO WS-TABLE-NAME
                 ELSE
                    MOVE 'VDPM19_LINK_WORK' TO WS-TABLE-NAME
                 END-IF
                 PERFORM 9700-SQL-ERROR
           END-EVALUATE
           .
      *--------------------------*
       9330FE-CLOSE-LINK-CURSOR.
      *--------------------------*

           MOVE '9330FE-CLOSE-LINK-CURSOR'  TO WS-PARAGRAPH-NAME

           IF DISPLAY-ACTIVE
              DISPLAY WS-PARAGRAPH-NAME
           END-IF

           EXEC SQL
                CLOSE IC_LINK_CSR
           END-EXEC

           EVALUATE SQLCODE
              WHEN 0
                 MOVE 'SP00'                TO LS-SP-RC
              WHEN OTHER
                 MOVE 'VDPM19_LINK_WORK'    TO WS-TABLE-NAME
                 PERFORM 9700-SQL-ERROR
           END-EVALUATE
           .
      *--------------------------*
       9330G-CLOSE-AMNDT-CURSOR.
      *--------------------------*

           MOVE '9330G-CLOSE-AMNDT-CURSOR'  TO WS-PARAGRAPH-NAME

           IF DISPLAY-ACTIVE
              DISPLAY WS-PARAGRAPH-NAME
           END-IF

           EXEC SQL
                CLOSE AMNDT_CSR
           END-EXEC

           EVALUATE SQLCODE
              WHEN 0
                 MOVE 'SP00'                TO LS-SP-RC
              WHEN OTHER
                 MOVE 'AMNDT_CSR'           TO WS-TABLE-NAME
                 PERFORM 9700-SQL-ERROR
           END-EVALUATE
           .
      *------------------*
       9500-DELETE-WORK.
      *------------------*

           MOVE '9500-DELETE-WORK'          TO WS-PARAGRAPH-NAME

           IF DISPLAY-ACTIVE
              DISPLAY WS-PARAGRAPH-NAME
           END-IF

           PERFORM 9500A-DELETE-LINKS

           PERFORM 9500B-DELETE-AMND

           PERFORM 9500C-DELETE-TEMPLATE

           .
      *-------------------*
       9500A-DELETE-LINKS.
      *-------------------*

           MOVE '9500A-DELETE-LINKS'        TO WS-PARAGRAPH-NAME

           IF DISPLAY-ACTIVE
              DISPLAY WS-PARAGRAPH-NAME
           END-IF

           IF WORKING-TMPLT OR REEXEC-WORKING

              EXEC SQL
                   DELETE FROM VDPM19_LINK_WORK DPM19A
                    WHERE DPM19A.MCA_AMND_ID IN
                              (SELECT DPM16.MCA_AMND_ID
                                 FROM VDPM16_MCA_AMND       DPM16
                                     ,VDPM19_LINK_WORK      DPM19
                                WHERE DPM16.MCA_TMPLT_ID =
                                               :WS-OLD-TEMPLATE-ID
                                  AND DPM16.MCA_AMND_ID  =
                                                 DPM19.MCA_AMND_ID)
              END-EXEC
           ELSE
              EXEC SQL
                   DELETE FROM VDPM19_LINK_WORK DPM19A
                    WHERE DPM19A.MCA_AMND_ID IN
                              (SELECT DPM16.MCA_AMND_ID
                                 FROM VDPM16_MCA_AMND       DPM16
                                     ,VDPM19_LINK_WORK      DPM19
                                WHERE DPM16.MCA_TMPLT_ID =
                                               :WS-OLD-TEMPLATE-ID
                                  AND DPM16.MCA_AMND_ID  =
                                                 DPM19.MCA_AMND_ID
                                  AND DPM19.MCA_ACCS_STAT_CD = 'U')
              END-EXEC
           END-IF

           EVALUATE SQLCODE
              WHEN 0
                 MOVE 'SP00'                TO LS-SP-RC
              WHEN 100
                 CONTINUE
              WHEN OTHER
                 MOVE 'VDPM19_LINK_WORK'    TO WS-TABLE-NAME
                 PERFORM 9700-SQL-ERROR
           END-EVALUATE
           .
      *-------------------*
       9500B-DELETE-AMND.
      *-------------------*

           MOVE '9500B-DELETE-AMND'         TO WS-PARAGRAPH-NAME

           IF DISPLAY-ACTIVE
              DISPLAY WS-PARAGRAPH-NAME
           END-IF

           EXEC SQL
                DELETE FROM VDPM17_AMND_WORK
                 WHERE MCA_TMPLT_ID  = :WS-OLD-TEMPLATE-ID
           END-EXEC

           EVALUATE SQLCODE
              WHEN 0
                 MOVE 'SP00'                TO LS-SP-RC
              WHEN 100
                 CONTINUE
              WHEN OTHER
                 MOVE 'VDPM17_AMND_WORK'    TO WS-TABLE-NAME
                 PERFORM 9700-SQL-ERROR
           END-EVALUATE
           .
      *----------------------*
       9500C-DELETE-TEMPLATE.
      *----------------------*

           MOVE '9500C-DELETE-TEMPLATE'     TO WS-PARAGRAPH-NAME

           IF DISPLAY-ACTIVE
              DISPLAY WS-PARAGRAPH-NAME
           END-IF

           EXEC SQL
                DELETE FROM VDPM15_TMPLT_WORK
                 WHERE MCA_TMPLT_ID  = :WS-OLD-TEMPLATE-ID
           END-EXEC

           EVALUATE SQLCODE
              WHEN 0
                 MOVE 'SP00'                TO LS-SP-RC
              WHEN 100
                 CONTINUE
              WHEN OTHER
                 MOVE 'VDPM15_TMPLT_WORK'   TO WS-TABLE-NAME
                 PERFORM 9700-SQL-ERROR
           END-EVALUATE
           .
      *-----------------------*                                         01850000
       9600-CHECK-DEBUG-TABLE.                                          01860000
      *-----------------------*                                         01870000
                                                                        01880000
           MOVE '9600-CHECK-DEBUG-TABLE'    TO WS-PARAGRAPH-NAME        01890000
                                                                        00051700
           EXEC SQL                                                     00051800
                SELECT ACTVT_DSPLY_IN                                   00051900
                  INTO :D054-ACTVT-DSPLY-IN                             00052010
                FROM   VDTM54_DEBUG_CNTRL                               00052040
                WHERE PRGM_ID = :WS-PROGRAM                             00052050
                WITH UR
           END-EXEC                                                     00052060
                                                                        00052070
           EVALUATE SQLCODE                                             00052091
              WHEN 0                                                    00052092
                  IF D054-ACTVT-DSPLY-IN = 'Y'                          00052094
                     SET DISPLAY-ACTIVE    TO TRUE                      00052095
                  END-IF                                                00052099
              WHEN 100                                                  00052092
                  CONTINUE
              WHEN OTHER                                                00052092
                  MOVE 'VDTM54_DEBUG_CNTRL'  TO WS-TABLE-NAME            0176000
                  PERFORM 9700-SQL-ERROR                                 0181000
           END-EVALUATE                                                 00052102
           .
      *---------------*                                                 01850000
       9700-SQL-ERROR.                                                  01860000
      *---------------*                                                 01870000
                                                                        01880000
           MOVE  WS-DATABASE-ERROR          TO LS-SP-ERROR-AREA         01770000
           MOVE  'SP99'                     TO LS-SP-RC                 01780000
           MOVE SQLCODE                     TO WS-SQLCODE               01790000
           DISPLAY 'SQLCODE:'               WS-SQLCODE                  01800000
           DISPLAY 'PARAGRAPH-NAME:'        WS-PARAGRAPH-NAME           01800000
           DISPLAY 'WS-ERROR-AREA '         WS-ERROR-AREA               01800000
           PERFORM 9990-GOBACK                                          01810000
           .                                                            01460000
      *------------*
       9990-GOBACK.
      *------------*
           PERFORM 9995-DISPLAY-DATA
           PERFORM 9999-FORMAT-SQLCA
           GOBACK
           .
      *------------------*
       9995-DISPLAY-DATA.
      *------------------*
           IF DISPLAY-ACTIVE
              DISPLAY 'LS-SP-RC             :' LS-SP-RC
              DISPLAY 'LS-SP-ERROR-AREA     :' LS-SP-ERROR-AREA
           END-IF

           MOVE WS-OUT-TEMPLATE-ID         TO LS-TEMPLATE-ID
           MOVE WS-OUT-TEMPLATE-NAME-LEN   TO LS-TEMPLATE-NAME-LEN
           MOVE WS-OUT-TEMPLATE-NAME-TEXT  TO LS-TEMPLATE-NAME-TEXT
           MOVE WS-OUT-TEMPLATE-TYPE       TO LS-TEMPLATE-TYPE

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
      *------------------*
       9999-FORMAT-SQLCA.
      *------------------*
           PERFORM DB2000I-FORMAT-SQLCA
              THRU DB2000I-FORMAT-SQLCA-EXIT

           IF DISPLAY-ACTIVE
              EXEC SQL
                   SET :WS-CURRENT-TIMESTAMP = CURRENT TIMESTAMP
              END-EXEC

              DISPLAY "PROGRAM DPMXMSTU ENDED : " WS-CURRENT-TIMESTAMP
              DISPLAY "LS-TEMPLATE-ID OUT "   LS-TEMPLATE-ID
              DISPLAY "LS-TEMPLATE-NAME OUT " LS-TEMPLATE-NAME-TEXT
              DISPLAY "LS-TEMPLATE-TYPE OUT " LS-TEMPLATE-TYPE
           END-IF
           .

      **MOVE STATEMENTS TO FORMAT THE OUTSQLCA USING DB2000IA & DB2000IB
        COPY DB2000IC.
