       IDENTIFICATION DIVISION.                                         00010000
       PROGRAM-ID.    DPMXMSTG.                                         00020001
       AUTHOR.        COGNIZANT.                                        00030000
                                                                        00040000
      ******************************************************************00050000
      *THIS IS AN UNPUBLISHED PROGRAM OWNED BY ISCC                    *00060000
      *IN WHICH A COPYRIGHT SUBSISTS AS OF JULY 2006.                  *00070000
      *                                                                *00080000
      ******************************************************************00090000
      **LNKCTL**                                                        00100000
      *    INCLUDE SYSLIB(DSNRLI)                                       00110000
      *    MODE AMODE(31) RMODE(ANY)                                    00120000
      *    ENTRY DPMXMSTG                                               00130001
      *    NAME  DPMXMSTG(R)                                            00140001
      *                                                                 00150000
      ******************************************************************00160000
      **         P R O G R A M   D O C U M E N T A T I O N            **00170000
      ******************************************************************00180000
      * SYSTEM:    DERIV/SERV MCA XPRESS                               *00190000
      * PROGRAM:   DPMXMSTG                                            *00200001
      *                                                                *00210000
      * THIS STORED PROCEDURE RETURNS THE STATIC GRID OF THE MCA       *00220000
      * TEMPLATE FOR A GIVEN TEMPLATE ID                               *00230000
      ******************************************************************00240000
      * TABLES:                                                        *00250000
      * -------                                                        *00260000
      * VDPM04_ATTRB_DTL          - COMMON TABLE CONTAINS MASTER       *00540000
      *                             INFORMATION FOR PRODUCT,SUB-PRODUCT*00550000
      *                             REGION, CATEGORY AND TERM          *00560000
      * D0006          - MASTER TABLE CONTAINS TEMPLATE     *00270000
      *                             DETAILS INCLUSIVE OF ISDA /        *00280000
      *                             CUSTOMIZED TEMPLATE                *00290000
      * VDTM54_DEBUG_CNTRL        - DEBUG CONTROL TABLE                *00270000
      *----------------------------------------------------------------*00570000
      * INCLUDES:                                                      *00580000
      * ---------                                                      *00590000
      * SQLCA    - DB2 COMMAREA                                        *00600000
      * DPM1401  - DCLGEN FOR D0006 TABLE                   *00600000
      * DPM1601  - DCLGEN FOR VDPM16_MCA_AMND                          *00600000
      * DTM5401  - DCLGEN FOR DISPLAY CONTROL TABLE                    *00600000
      *---------------------------------------------------------------- 00610000
      * COPYBOOKS:                                                     *00580000
      * ---------                                                      *00590000
      * DB2000IA - DB2 STANDARD COPYBOOK WITH FORMATTED DISPLAY SQLCA  *00600000
      * DB2000IB - PICTURE CLAUSE FOR SQLCA                            *00600000
      * DB2000IC - COPYBOOK CONTAINING MOVE STATEMENTS TO FORMAT SQLCA *00600000
      *---------------------------------------------------------------- 00610000
      *---------------------------------------------------------------- 00620000
      *              M A I N T E N A N C E   H I S T O R Y            * 00630000
      *                                                               * 00640000
      *                                                               * 00650000
      * DATE CHANGED    VERSION     PROGRAMMER                        * 00660000
      * ------------    -------     --------------------              * 00670000
      *                                                               * 00680000
      * 08/30/2007        00.00     COGNIZANT                         * 00690000
      * INITIAL IMPLEMENTATION                                        * 00700000
      *                                                               * 00710000
      ***************************************************************** 00720000
       ENVIRONMENT DIVISION.                                            00730000
       DATA DIVISION.                                                   00740000
       WORKING-STORAGE SECTION.                                         00750000
                                                                        00760000
       01  WS-PROGRAM                      PIC X(008) VALUE 'DPMXMSTG'. 00770001
                                                                        00780000
       01  WS-VARIABLES.                                                00790000
             05  WS-TEMPLATE-ID            PIC S9(09) COMP              00800000
                                                      VALUE ZEROES.
             05  WS-USR-WORK-COUNT         PIC S9(04) COMP              00800000
                                                      VALUE ZEROES.
             05  WS-ORG-WORK-COUNT         PIC S9(04) COMP              00800000
                                                      VALUE ZEROES.
             05  WS-TERM-CNT               PIC S9(04) COMP              00800000
                                                      VALUE ZEROES.
             05  WS-AMND-CNT               PIC S9(04) COMP              00800000
                                                      VALUE ZEROES.
             05  WS-EXIST-CHECK            PIC S9(04) COMP              00800000
                                                      VALUE ZEROES.
             05  WS-AMND-STAT-COUNT        PIC S9(04) COMP              00800000
                                                      VALUE ZEROES.
             05  WS-COMPANY-ID             PIC X(008) VALUE SPACES.     00830000
             05  WS-CLIENT-CMPNY-ID        PIC X(008) VALUE SPACES.     00830000
             05  WS-DEALER-CMPNY-NM        PIC X(255) VALUE SPACES.     00830000
             05  WS-CLIENT-CMPNY-NM        PIC X(255) VALUE SPACES.     00830000
             05  WS-USER-ID                PIC X(010) VALUE SPACES.     00840000
             05  WS-TMPLT-LCK-ST           PIC X(001) VALUE 'N'.        00840000
                 88 TMPLT-LCKD                        VALUE 'Y'.        00850000
                 88 TMPLT-NOT-LCKD                    VALUE 'N'.        00850000
             05  WS-SUB-APP-ENABLE         PIC X(001) VALUE 'N'.        00840000
                 88 SUB-FOR-APP-ENABLE                VALUE 'Y'.        00850000
                 88 SUB-FOR-APP-DISABLE               VALUE 'N'.        00850000
             05  WS-SUB-CP-ENABLE          PIC X(001) VALUE 'N'.        00840000
                 88 SUB-FOR-CP-ENABLE                 VALUE 'Y'.        00850000
                 88 SUB-FOR-CP-DISABLE                VALUE 'N'.        00850000
             05  WS-SUB-EXEC-ENABLE        PIC X(001) VALUE 'N'.        00840000
                 88 SUB-FOR-EXEC-ENABLE               VALUE 'Y'.        00850000
                 88 SUB-FOR-EXEC-DISABLE              VALUE 'N'.        00850000
             05  WS-PROP-LANG-FLAG         PIC X(001) VALUE 'N'.        00840000
                 88 PROP-LANG-ADDED                   VALUE 'Y'.        00850000
                 88 PROP-LANG-NOT-ADDED               VALUE 'N'.        00850000
             05  WS-RENOG-ENABLE-FLAG      PIC X(001) VALUE 'N'.        00840000
                 88 RENOG-ENABLE                      VALUE 'Y'.        00850000
                 88 RENOG-DISABLE                     VALUE 'N'.        00850000
             05  WS-PENDING-AMND           PIC X(001) VALUE 'N'.        00840000
                 88 PENDING-AMND                      VALUE 'Y'.        00850000
                 88 NO-PENDING-AMND                   VALUE 'N'.        00850000
             05  WS-SUBMIT-APP-ENABLE      PIC X(001) VALUE 'N'.        00840000
             05  WS-SUBMIT-CP-ENABLE       PIC X(001) VALUE 'N'.        00840000
             05  WS-SUBMIT-EXEC-ENABLE     PIC X(001) VALUE 'N'.        00840000
             05  WS-PROP-LANG-STATUS       PIC X(001) VALUE 'N'.        00840000
             05  WS-RENOG-ENABLE           PIC X(001) VALUE 'N'.        00840000
             05  WS-TMPLT-LCK-STAT         PIC X(001) VALUE 'N'.        00840000
             05  WS-L-ORG-CD               PIC X(008) VALUE SPACES.     00840000
             05  WS-L-ORG-USR-CD           PIC X(010) VALUE SPACES.     00840000
             05  WS-L-ORG-USR-NM           PIC X(200) VALUE SPACES.     00840000
             05  WS-LCK-ORG-CD             PIC X(008) VALUE SPACES.     00840000
             05  WS-LCK-ORG-USR-CD         PIC X(010) VALUE SPACES.     00840000
             05  WS-LCK-ORG-USR-NM         PIC X(200) VALUE SPACES.     00840000
             05  WS-DEF-FLAG               PIC X(001) VALUE SPACES.     00840000
             05  WS-USER-IND               PIC X(001) VALUE SPACES.     01190000
             05  WS-TIMESTAMP              PIC X(026) VALUE SPACES.     01190000
             05  WS-WORK-VALUE             PIC X(001) VALUE SPACES.     01190000
                 88 WORK-VALUE             VALUE 'Y'.
                 88 NO-WORK-VALUE          VALUE 'N'.
             05  WS-SAVE-ENABLE            PIC X(001) VALUE SPACES.     01190000
             05  WS-ISDA-TMPLT-NM          PIC X(500) VALUE SPACES.     01190000
             05  WS-ISDA-TEMPLATE-ID       PIC S9(09) COMP              01190000
                                                      VALUE ZEROES.
             05  WS-TMPLT-ID-CHK           PIC S9(09) COMP              01190000
                                                      VALUE ZEROES.
             05  WS-CHECK-AMND-ID          PIC S9(18) COMP-3            01190000
                                                      VALUE ZEROES.
             05  WS-WRK-TEMPLATE-TYPE      PIC X(01)  VALUE SPACES.     01190000
                 88 ISDA-TMPLT             VALUE 'I'.
                 88 WORKING-TMPLT          VALUE 'W'.
                 88 REEXEC-WORKING         VALUE 'R'.
             05  D014-MCA-PBLTN-DT-I       PIC S9(04) COMP              01190000
                                                      VALUE ZEROES.
             05  WS-TEMPLATE-ID-TYPE       PIC X(001) VALUE SPACES.     01190000
                 88 MAIN-TEMPLATE-ID       VALUE 'Y'.
                 88 WORK-TEMPLATE-ID       VALUE 'N'.
                                                                        00850000
             05  WS-DISPLAY-CONTROL-FLAG   PIC X(001) VALUE SPACES.     01190000
                 88 DISPLAY-ACTIVE         VALUE 'Y'.
                 88 DISPLAY-INACTIVE       VALUE 'N'.
                                                                        00850000
       01  WS-ERROR-MSG.                                                00860000
             05  WS-INVALID-TMPLT-ID       PIC X(50)                    00560100
                 VALUE 'INVALID TEMPLATE ID'.                           00560100
             05  WS-NO-RECORD-FOUND        PIC X(50)                    00560100
                 VALUE 'NO RECORD FOUND FOR THE INPUT TEMPLATE ID'.     00560100
             05  WS-DATABASE-ERROR         PIC X(50)                    00560100
                 VALUE 'DATABASE ERROR OCCURRED. PLEASE CONTACT DTCC'.  00560100
                                                                        00850000
       01  WS-ERROR-AREA.                                               00860000
             05  WS-PARAGRAPH-NAME         PIC X(40).                   00870000
             05  FILLER                    PIC X VALUE ','.             00880000
             05  WS-TABLE-NAME             PIC X(18).                   00890000
             05  WS-SQLCODE                PIC 9(7).                    00900000
                                                                        00910000
                                                                        00920000
      ***************************************************************** 00930000
      *                        SQL INCLUDES                            *00940000
      ******************************************************************00950000
           EXEC SQL                                                     00960000
                INCLUDE SQLCA                                           00970000
           END-EXEC.                                                    00980000
                                                                        00990000
           EXEC SQL                                                     01000000
                INCLUDE DPM1401                                         01010000
           END-EXEC.                                                    01020000
                                                                        00990000
           EXEC SQL                                                     01000000
                INCLUDE DPM1601                                         01010000
           END-EXEC.                                                    01020000
                                                                        00990000
           EXEC SQL                                                     01000000
                INCLUDE DTM5401                                         01010000
           END-EXEC.                                                    01020000

      ******************************************************************01030000
      * DB2 STANDARD COPYBOOK WITH FORMATTED DISPLAY SQLCA              01040000
      * THIS MUST REMAIN AS THE LAST ENTRY IN WORKING STORAGE           01050000
      ******************************************************************01060000
       COPY  DB2000IA.                                                  01070000
                                                                        01080000
       LINKAGE SECTION.                                                 01090000
                                                                        01100000
      *PICTURE CLAUSE FOR OUTSQLCA - PIC X(179) - FOR LINKAGE SECTION   01110000
       COPY  DB2000IB.                                                  01120000
       01  LS-SP-ERROR-AREA                PIC X(80).                   01130000
       01  LS-SP-RC                        PIC X(04).                   01140000
       01  LS-TEMPLATE-ID                  PIC S9(09) COMP.             01150000
       01  LS-COMPANY-ID                   PIC X(08).                   01180000
       01  LS-USER-ID                      PIC X(10).                   01190000
       01  LS-DEF-FLAG                     PIC X(01).                   01190000
                                                                        01200000
       PROCEDURE DIVISION USING  OUTSQLCA,                              01210000
                                 LS-SP-ERROR-AREA,                      01220000
                                 LS-SP-RC,                              01230000
                                 LS-TEMPLATE-ID,                        01240000
                                 LS-COMPANY-ID,                         01270000
                                 LS-USER-ID,                            01280000
                                 LS-DEF-FLAG.                           01280000
      *---------*                                                       01290000
       1000-MAIN.                                                       01300000
      *---------*                                                       01310000
                                                                        01320000
           PERFORM 1000-INIT-AND-CHECK-PARMS                            01330000
                                                                        01340000
           PERFORM 2000-CHECK-FLAG                                      01350000
                                                                        01360000
           PERFORM 9990-GOBACK                                          01370000
           .                                                            01380000
      *-------------------------*                                       01390000
       1000-INIT-AND-CHECK-PARMS.                                       01400000
      *-------------------------*                                       01410000

           MOVE '1000-INIT-AND-CHECK-PARMS' TO WS-PARAGRAPH-NAME        01430000
                                                                        01420000
           PERFORM 8880-CHECK-DEBUG-TABLE

           IF DISPLAY-ACTIVE
              EXEC SQL
                   SET :WS-TIMESTAMP = CURRENT TIMESTAMP
              END-EXEC
              DISPLAY "PROGRAM DPMXMSTG - START"
              DISPLAY "STARTING TIME " WS-TIMESTAMP
           END-IF
                                                                        01440000
           MOVE SPACES                      TO LS-SP-ERROR-AREA         01450000
                                               LS-SP-RC                 01460000
           INITIALIZE DCLVDPM14-MCA-TMPLT
                      WS-COMPANY-ID
                      WS-USER-ID
                      WS-DEF-FLAG
                      WS-ISDA-TMPLT-NM
                      WS-SUBMIT-APP-ENABLE
                      WS-SUBMIT-CP-ENABLE
                      WS-SUBMIT-EXEC-ENABLE
                      WS-PROP-LANG-STATUS
                      WS-SAVE-ENABLE
                      WS-DEALER-CMPNY-NM
                      WS-CLIENT-CMPNY-NM
                      WS-RENOG-ENABLE


           MOVE LS-TEMPLATE-ID              TO D014-MCA-TMPLT-ID        01470000
           MOVE LS-COMPANY-ID               TO WS-COMPANY-ID            01500000
           MOVE LS-USER-ID                  TO WS-USER-ID               01510000
           MOVE LS-DEF-FLAG                 TO WS-DEF-FLAG              01510000
           SET NO-WORK-VALUE                TO TRUE                     01510000

           IF DISPLAY-ACTIVE
              DISPLAY "LS-TEMPLATE-ID"  LS-TEMPLATE-ID
              DISPLAY "WS-COMPANY-ID"   WS-COMPANY-ID
              DISPLAY "WS-USER-ID"      WS-USER-ID
              DISPLAY "WS-DEF-FLAG"     WS-DEF-FLAG
           END-IF
           .                                                            01520000
      *---------------------*                                           01530000
       2000-CHECK-FLAG.                                                 01540000
      *---------------------*                                           01550000
                                                                        01560000
           MOVE '2000-CHECK-FLAG'           TO WS-PARAGRAPH-NAME        01570000

           IF DISPLAY-ACTIVE
              DISPLAY "WS-DEF-FLAG " WS-DEF-FLAG
           END-IF


           IF WS-DEF-FLAG = 'Y'
              PERFORM 2100-GET-DEFAULT-TEMPLATE
           ELSE
              PERFORM 2200-CHECK-LOCK
           END-IF
           .                                                            01520000
      *--------------------------*                                      01530000
       2100-GET-DEFAULT-TEMPLATE.                                       01540000
      *--------------------------*                                      01550000
                                                                        01560000
           MOVE '2100-GET-DEFAULT-TEMPLATE' TO WS-PARAGRAPH-NAME        01570000

           MOVE WS-TMPLT-LCK-ST             TO WS-TMPLT-LCK-STAT        01890000
           MOVE WS-L-ORG-CD                 TO WS-LCK-ORG-CD            01890000
           MOVE WS-L-ORG-USR-CD             TO WS-LCK-ORG-USR-CD        01890000
           MOVE WS-L-ORG-USR-NM             TO WS-LCK-ORG-USR-NM        01890000
           MOVE 'D'                         TO WS-USER-IND              01890000

           PERFORM 2110-GET-MAX-PUBLN-DATE
                                                                        01900000
           EXEC SQL
               DECLARE GMC_DEF_CSR CURSOR WITH HOLD WITH RETURN FOR
1                 SELECT DPM14.MCA_TMPLT_ID
2                       ,DPM14.MCA_TMPLT_NM
3                       ,DPM14.MCA_TMPLT_SHORT_NM
4                       ,DPM14.MCA_TMPLT_TYPE_CD
5                       ,DPM14.DELR_CMPNY_ID
6                       ,DPM14.CLNT_CMPNY_ID
7                       ,DPM14.MCA_DELR_STAT_CD
8                       ,DPM14.MCA_CLNT_STAT_CD
9                       ,DPM14.ATTRB_PRDCT_ID
10                      ,DPM04.ATTRB_VALUE_DS
11                      ,DPM14.ATTRB_SUB_PRDCT_ID
12                      ,DPM04A.ATTRB_VALUE_DS
13                      ,DPM14.ATTRB_REGN_ID
14                      ,DPM04B.ATTRB_VALUE_DS
15                      ,DPM14.MCA_STAT_IN
16                      ,:WS-TMPLT-LCK-STAT
17                      ,:WS-LCK-ORG-CD
18                      ,:WS-LCK-ORG-USR-CD
19                      ,:WS-LCK-ORG-USR-NM
20                      ,:WS-USER-IND
21                      ,:WS-SAVE-ENABLE
22                      ,:WS-ISDA-TMPLT-NM
23                      ,DPM14.MCA_TMPLT_RQSTR_ID
24                      ,:WS-SUBMIT-APP-ENABLE
25                      ,:WS-SUBMIT-CP-ENABLE
26                      ,:WS-SUBMIT-EXEC-ENABLE
27                      ,:WS-PROP-LANG-STATUS
28                      ,:WS-DEALER-CMPNY-NM
29                      ,:WS-CLIENT-CMPNY-NM
30                      ,:WS-RENOG-ENABLE
                    FROM D0006                    DPM14
              INNER JOIN VDPM04_ATTRB_DTL                    DPM04
                 ON DPM14.ATTRB_PRDCT_ID = DPM04.ATTRB_ID
                AND DPM04.ATTRB_TYPE_ID = 'P'
              INNER JOIN VDPM04_ATTRB_DTL                    DPM04A
                 ON DPM14.ATTRB_SUB_PRDCT_ID = DPM04A.ATTRB_ID
                AND DPM04A.ATTRB_TYPE_ID = 'S'
              INNER JOIN VDPM04_ATTRB_DTL                    DPM04B
                 ON DPM14.ATTRB_REGN_ID = DPM04B.ATTRB_ID
                AND DPM04B.ATTRB_TYPE_ID = 'R'
                  WHERE DPM14.MCA_TMPLT_ID = :D014-MCA-TMPLT-ID
                  WITH UR
           END-EXEC
                                                                        02010000
           EXEC SQL
                OPEN GMC_DEF_CSR
           END-EXEC

           EVALUATE SQLCODE                                             01690000
              WHEN 0                                                    01700000
                 MOVE 'SP00'                TO LS-SP-RC
              WHEN OTHER                                                01750000
                 MOVE 'D0006'    TO WS-TABLE-NAME            01760000
                 PERFORM 9000-SQL-ERROR                                 01810000
           END-EVALUATE                                                 01820000
           .                                                            01520000
      *----------------------*                                          01530000
       2110-GET-MAX-PUBLN-DATE.                                         01540000
      *----------------------*                                          01550000
                                                                        01560000
           MOVE '2110-GET-MAX-PUBLN-DATE'   TO WS-PARAGRAPH-NAME        01570000
                                                                        01560000
           EXEC SQL
                SELECT MAX(MCA_PBLTN_DT)
                  INTO :D014-MCA-PBLTN-DT :D014-MCA-PBLTN-DT-I
                  FROM D0006
                 WHERE MCA_TMPLT_TYPE_CD ='I'
                   AND MCA_STAT_IN       ='P'
           END-EXEC

           EVALUATE SQLCODE                                             01690000
              WHEN 0                                                    01700000
                 IF D014-MCA-PBLTN-DT-I NOT = 0
                    MOVE '12/12/9999'       TO D014-MCA-PBLTN-DT
                 ELSE
                    PERFORM 2110A-GET-LATEST-PUBL-TMPLT
                 END-IF
              WHEN 100                                                  01700000
                 MOVE 'SP50'                TO LS-SP-RC
                 MOVE WS-NO-RECORD-FOUND    TO LS-SP-ERROR-AREA
                 PERFORM 9990-GOBACK                                    01810000
              WHEN OTHER                                                01750000
                 MOVE 'D0006'    TO WS-TABLE-NAME            01760000
                 PERFORM 9000-SQL-ERROR                                 01810000
           END-EVALUATE                                                 01820000
           .
      *---------------------------*                                     01530000
       2110A-GET-LATEST-PUBL-TMPLT.                                     01540000
      *---------------------------*                                     01550000
                                                                        01560000
           MOVE '2110A-GET-LATEST-PUBL-TMPLT'                           01570000
                                            TO WS-PARAGRAPH-NAME        01570000

           EXEC SQL
               SELECT MAX(MCA_TMPLT_ID)
                 INTO :D014-MCA-TMPLT-ID
                 FROM D0006
                WHERE MCA_TMPLT_TYPE_CD ='I'
                  AND MCA_STAT_IN       ='P'
                  AND MCA_PBLTN_DT      = :D014-MCA-PBLTN-DT
           END-EXEC

           EVALUATE SQLCODE                                             01690000
              WHEN 0                                                    01700000
                 CONTINUE
              WHEN 100                                                  01700000
                 CONTINUE
              WHEN OTHER                                                01750000
                 MOVE 'D0006'    TO WS-TABLE-NAME            01760000
                 PERFORM 9000-SQL-ERROR                                 01810000
           END-EVALUATE                                                 01820000
           .                                                            01520000
      *---------------------*                                           01530000
       2200-CHECK-LOCK.                                                 01540000
      *---------------------*                                           01550000
                                                                        01560000
           MOVE '2200-CHECK-LOCK'           TO WS-PARAGRAPH-NAME        01570000
                                                                        01580000
           SET MAIN-TEMPLATE-ID             TO TRUE

           IF D014-MCA-TMPLT-ID <= 0
              MOVE 'SP50'                   TO LS-SP-RC
              MOVE  WS-INVALID-TMPLT-ID     TO LS-SP-ERROR-AREA         01770000
              PERFORM 9990-GOBACK                                       01810000
           END-IF

           PERFORM 2210-CHECK-WORK
                                                                        01580000
           PERFORM 2210A-SET-ENABLE-FLAG

           PERFORM 2210B-GET-D-CP-NAME

           EXEC SQL                                                     01590000
                SELECT  DPM10.CMPNY_ID                                  01600000
                       ,DPM10.CMPNY_USER_ID                             01610000
                       ,DPM03.CMPNY_USER_NM                             01610000
                  INTO  :WS-L-ORG-CD                                    01620000
                       ,:WS-L-ORG-USR-CD                                01630000
                       ,:WS-L-ORG-USR-NM                                01630000
                  FROM  VDPM10_MCA_LOCK         DPM10                   01640000
                       ,D0003       DPM03                   01640000
                 WHERE  DPM10.MCA_TMPLT_ID  = :D014-MCA-TMPLT-ID        01650000
                   AND  DPM10.CMPNY_ID      = DPM03.CMPNY_ID            01650000
                   AND  DPM10.CMPNY_USER_ID = DPM03.CMPNY_USER_ID       01650000
                WITH UR                                                 01660000
           END-EXEC                                                     01670000
                                                                        01680000
           EVALUATE SQLCODE                                             01690000
              WHEN 0                                                    01700000
                 SET TMPLT-LCKD             TO TRUE

                 IF WS-COMPANY-ID = WS-L-ORG-CD AND
                    WS-USER-ID = WS-L-ORG-USR-CD
                    MOVE 'L'                TO WS-USER-IND
                    IF WS-WRK-TEMPLATE-TYPE = 'W' OR 'R'
                       PERFORM 2214-CHECK-USR-WORK
                    ELSE
                       PERFORM 2215-CHECK-WORK-VALUES
                    END-IF
                 ELSE
                    IF WS-COMPANY-ID = WS-L-ORG-CD
                       MOVE 'O'             TO WS-USER-IND
                    ELSE
                       MOVE 'D'             TO WS-USER-IND
                    END-IF
                 END-IF

              WHEN 100                                                  01730000
                 MOVE 'D'                   TO WS-USER-IND
                 SET TMPLT-NOT-LCKD         TO TRUE
              WHEN OTHER                                                01750000
                 MOVE 'VDPM10_MCA_LOCK'     TO WS-TABLE-NAME            01760000
                 PERFORM 9000-SQL-ERROR                                 01810000
           END-EVALUATE                                                 01820000

           MOVE WS-WORK-VALUE               TO WS-SAVE-ENABLE

           IF DISPLAY-ACTIVE
              DISPLAY "LOCK-USER-IND "       WS-USER-IND
              DISPLAY "WS-TEMPLATE-ID-TYPE " WS-TEMPLATE-ID-TYPE
              DISPLAY "WS-WORK-VALUE "       WS-WORK-VALUE
           END-IF

           IF WORK-TEMPLATE-ID
              PERFORM 2216-GET-WORK-ISDA-TMPLT-NAME
              PERFORM 2220-GET-WORK-TEMP-LAYOUT                         01740000
           ELSE
              PERFORM 2217-GET-MAIN-ISDA-TMPLT-NAME
              PERFORM 2230-GET-MAIN-TEMP-LAYOUT                         01740000
           END-IF
           .                                                            01830000
      *----------------*                                                01850000
       2210-CHECK-WORK.                                                 01860000
      *----------------*                                                01870000
                                                                        01880000
           MOVE '2210-CHECK-WORK'           TO WS-PARAGRAPH-NAME        01890000

           EXEC SQL
                SELECT MCA_TMPLT_TYPE_CD
                      ,MCA_ISDA_TMPLT_ID
                      ,CLNT_CMPNY_ID
                      ,MCA_DELR_STAT_CD
                      ,MCA_CLNT_STAT_CD
                  INTO :WS-WRK-TEMPLATE-TYPE
                      ,:WS-ISDA-TEMPLATE-ID
                      ,:WS-CLIENT-CMPNY-ID
                      ,:D014-MCA-DELR-STAT-CD
                      ,:D014-MCA-CLNT-STAT-CD
                  FROM D0006
                 WHERE MCA_TMPLT_ID = :D014-MCA-TMPLT-ID
           END-EXEC

           EVALUATE SQLCODE                                             01690000
              WHEN 0                                                    01700000
                 SET MAIN-TEMPLATE-ID       TO TRUE
              WHEN 100                                                  01700000
                 SET WORK-TEMPLATE-ID       TO TRUE
              WHEN OTHER                                                01750000
                 MOVE 'D0006'    TO WS-TABLE-NAME            01760000
                 PERFORM 9000-SQL-ERROR                                 01810000
           END-EVALUATE                                                 01820000
           .                                                            01830000
      *----------------------*                                          01850000
       2210A-SET-ENABLE-FLAG.                                           01860000
      *----------------------*                                          01870000
                                                                        01880000
           MOVE '2210A-SET-ENABLE-FLAG'     TO WS-PARAGRAPH-NAME        01890000
                                                                        01580000
           IF ISDA-TMPLT
              PERFORM 2210AA-SUB-APP-ENABLE
           END-IF

           IF WORKING-TMPLT OR REEXEC-WORKING
              PERFORM 2210AC-CHECK-AMND-STAT
              PERFORM 2210AD-CHECK-SUB-CP-ENABLE
           END-IF

           IF WS-WRK-TEMPLATE-TYPE = 'E'
              PERFORM 2210C-SET-RENOG-ENABLE
           END-IF

           MOVE WS-SUB-APP-ENABLE           TO WS-SUBMIT-APP-ENABLE
           MOVE WS-SUB-CP-ENABLE            TO WS-SUBMIT-CP-ENABLE
           MOVE WS-SUB-EXEC-ENABLE          TO WS-SUBMIT-EXEC-ENABLE
           MOVE WS-PROP-LANG-FLAG           TO WS-PROP-LANG-STATUS
           MOVE WS-RENOG-ENABLE-FLAG        TO WS-RENOG-ENABLE
           .                                                            01830000
      *---------------------*                                           01850000
       2210AA-SUB-APP-ENABLE.                                           01860000
      *---------------------*                                           01870000
                                                                        01880000
           MOVE '2210AA-SUB-APP-ENABLE'     TO WS-PARAGRAPH-NAME        01890000

           PERFORM 2210AAA-CHECK-TERM-COUNT

           EXEC SQL
                SELECT DPM16.MCA_AMND_ID
                  INTO :D016-MCA-AMND-ID
                  FROM VDPM16_MCA_AMND   DPM16
                      ,VDPM07_MCA_CTGRY  DPM07
                 WHERE DPM16.MCA_TMPLT_ID   = DPM07.MCA_TMPLT_ID
                   AND DPM16.ATTRB_CTGRY_ID = DPM07.ATTRB_CTGRY_ID
                   AND DPM16.CTGRY_SQ       = DPM07.CTGRY_SQ
                   AND DPM07.CTGRY_STAT_CD  = 'P'
                   AND DPM07.MCA_TMPLT_ID   = :D014-MCA-TMPLT-ID
                FETCH FIRST ROW ONLY
           END-EXEC

           EVALUATE SQLCODE                                             01690000
              WHEN 0                                                    01700000
                 SET PROP-LANG-ADDED        TO TRUE
              WHEN 100                                                  01700000
                 SET PROP-LANG-NOT-ADDED    TO TRUE
              WHEN OTHER                                                01750000
                 MOVE 'VDPM08_MCA_TERMS'    TO WS-TABLE-NAME            01760000
                 PERFORM 9000-SQL-ERROR                                 01810000
           END-EVALUATE                                                 01820000
           .                                                            01830000
      *----------------------*                                          01850000
       2210AAA-CHECK-TERM-COUNT.                                        01860000
      *----------------------*                                          01870000
                                                                        01880000
           MOVE '2210AAA-CHECK-TERM-COUNT'  TO WS-PARAGRAPH-NAME        01890000

           EXEC SQL
                SELECT COUNT(ATTRB_TERM_ID)
                  INTO :WS-TERM-CNT
                  FROM VDPM08_MCA_TERMS
                 WHERE MCA_TMPLT_ID = :D014-MCA-TMPLT-ID
           END-EXEC

           EVALUATE SQLCODE                                             01690000
              WHEN 0                                                    01700000
                 PERFORM 2210AAB-CHECK-AMND-COUNT
              WHEN OTHER                                                01750000
                 MOVE 'VDPM08_MCA_TERMS'    TO WS-TABLE-NAME            01760000
                 PERFORM 9000-SQL-ERROR                                 01810000
           END-EVALUATE                                                 01820000
           .                                                            01830000
      *----------------------*                                          01850000
       2210AAB-CHECK-AMND-COUNT.                                        01860000
      *----------------------*                                          01870000
                                                                        01880000
           MOVE '2210AAB-CHECK-AMND-COUNT'  TO WS-PARAGRAPH-NAME        01890000

           EXEC SQL
                SELECT COUNT(MCA_AMND_ID)
                  INTO :WS-AMND-CNT
                  FROM VDPM16_MCA_AMND
                 WHERE MCA_TMPLT_ID = :D014-MCA-TMPLT-ID
           END-EXEC

           EVALUATE SQLCODE                                             01690000
              WHEN 0                                                    01700000
                 IF WS-TERM-CNT = WS-AMND-CNT
                    SET SUB-FOR-APP-ENABLE  TO TRUE
                 ELSE
                    SET SUB-FOR-APP-DISABLE TO TRUE
                 END-IF
              WHEN OTHER                                                01750000
                 MOVE 'VDPM16_MCA_AMND'     TO WS-TABLE-NAME            01760000
                 PERFORM 9000-SQL-ERROR                                 01810000
           END-EVALUATE                                                 01820000
           .                                                            01830000
      *----------------------*                                          01850000
       2210AC-CHECK-AMND-STAT.                                          01860000
      *----------------------*                                          01870000
                                                                        01880000
           MOVE '2210AC-CHECK-AMND-STAT'    TO WS-PARAGRAPH-NAME        01890000

           EXEC SQL
                SELECT 1
                  INTO :WS-AMND-STAT-COUNT
                  FROM VDPM19_LINK_WORK DPM19
                      ,VDPM16_MCA_AMND  DPM16
                 WHERE DPM16.MCA_TMPLT_ID = :D014-MCA-TMPLT-ID
                   AND DPM16.MCA_AMND_ID  = DPM19.MCA_AMND_ID
                   AND DPM19.AMND_STAT_CD = 'P'
                 FETCH FIRST ROW ONLY
           END-EXEC

           EVALUATE SQLCODE                                             01690000
              WHEN 0                                                    01700000
                 SET SUB-FOR-EXEC-DISABLE TO TRUE
                 SET PENDING-AMND         TO TRUE
              WHEN 100                                                  01700000
                 PERFORM 2210ACA-CHECK-LINK
              WHEN OTHER                                                01750000
                 MOVE 'VDPM16_MCA_AMND'     TO WS-TABLE-NAME            01760000
                 PERFORM 9000-SQL-ERROR                                 01810000
           END-EVALUATE                                                 01820000
           .                                                            01830000
      *-------------------*                                             01850000
       2210ACA-CHECK-LINK.                                              01860000
      *-------------------*                                             01870000
                                                                        01880000
           MOVE '2210ACA-CHECK-LINK'        TO WS-PARAGRAPH-NAME        01890000

           EXEC SQL
                SELECT 1
                  INTO :WS-AMND-STAT-COUNT
                  FROM VDPM18_MCA_LINK  DPM18
                      ,VDPM16_MCA_AMND  DPM16
                 WHERE DPM16.MCA_TMPLT_ID = :D014-MCA-TMPLT-ID
                   AND DPM16.MCA_AMND_ID  = DPM18.MCA_AMND_ID
                   AND DPM18.MCA_AMND_ID NOT IN
                         (SELECT DPM19.MCA_AMND_ID
                            FROM NSCC.VDPM19_LINK_WORK DPM19
                                ,NSCC.VDPM16_MCA_AMND  DPM16
                           WHERE DPM16.MCA_TMPLT_ID = :D014-MCA-TMPLT-ID
                             AND DPM16.MCA_AMND_ID  = DPM19.MCA_AMND_ID)
                   AND DPM18.AMND_STAT_CD = 'P'
                 FETCH FIRST ROW ONLY
           END-EXEC

           EVALUATE SQLCODE                                             01690000
              WHEN 0                                                    01700000
                 SET SUB-FOR-EXEC-DISABLE TO TRUE
                 SET PENDING-AMND         TO TRUE
              WHEN 100                                                  01700000
                 SET SUB-FOR-EXEC-ENABLE  TO TRUE
                 SET NO-PENDING-AMND      TO TRUE
              WHEN OTHER                                                01750000
                 MOVE 'VDPM16_MCA_AMND'     TO WS-TABLE-NAME            01760000
                 PERFORM 9000-SQL-ERROR                                 01810000
           END-EVALUATE                                                 01820000
                                                                        01890000
           .                                                            01830000
      *--------------------------*                                      01850000
       2210AD-CHECK-SUB-CP-ENABLE.                                      01860000
      *--------------------------*                                      01870000
                                                                        01880000
           MOVE '2210AD-CHECK-SUB-CP-ENABLE'                            01890000
                                            TO WS-PARAGRAPH-NAME        01890000

           EXEC SQL
                SELECT COUNT(MCA_TMPLT_ID)
                  INTO :WS-EXIST-CHECK
                  FROM VDPM14AMCA_TMPLT
                 WHERE MCA_TMPLT_ID = :D014-MCA-TMPLT-ID
           END-EXEC

           EVALUATE SQLCODE                                             01690000
              WHEN 0                                                    01700000
                 IF WS-EXIST-CHECK >= 1 AND WORKING-TMPLT
                    SET SUB-FOR-CP-ENABLE   TO TRUE
                 END-IF

                 IF REEXEC-WORKING
                    IF D014-MCA-CLNT-STAT-CD = ' ' AND
                       NO-PENDING-AMND
                       SET SUB-FOR-CP-DISABLE  TO TRUE
                    ELSE
                       SET SUB-FOR-CP-ENABLE   TO TRUE
                    END-IF
                 END-IF

      *          IF SUB-FOR-CP-DISABLE
      *             PERFORM 2210AE-CHK-WORK-FOR-ENABLE
      *          END-IF

              WHEN OTHER                                                01750000
                 MOVE 'VDPM14AMCA_TMPLT'    TO WS-TABLE-NAME            01760000
                 PERFORM 9000-SQL-ERROR                                 01810000
           END-EVALUATE                                                 01820000

           .                                                            01830000
      *--------------------------*                                      01850000
      *2210AE-CHK-WORK-FOR-ENABLE.                                      01860000
      *--------------------------*                                      01870000
      *                                                                 01880000
      *    MOVE '2210AE-CHK-WORK-FOR-ENABLE'                            01890000
      *                                     TO WS-PARAGRAPH-NAME        01890000
      *    EXEC SQL
      *         SELECT COUNT(MCA_TMPLT_ID)
      *           INTO :WS-EXIST-CHECK
      *           FROM VDPM15_TMPLT_WORK
      *          WHERE MCA_TMPLT_ID = :D014-MCA-TMPLT-ID
      *    END-EXEC
      *
      *    EVALUATE SQLCODE                                             01690000
      *       WHEN 0                                                    01700000
      *          IF WS-EXIST-CHECK > 0
      *             SET SUB-FOR-CP-ENABLE   TO TRUE
      *          ELSE
      *             SET SUB-FOR-CP-DISABLE  TO TRUE
      *          END-IF
      **      WHEN OTHER                                                01750000
      *          MOVE 'VDPM15_TMPLT_WORK'   TO WS-TABLE-NAME            01760000
      *          PERFORM 9000-SQL-ERROR                                 01810000
      *    END-EVALUATE                                                 01820000
      *    .                                                            01830000
      *-------------------*                                             01850000
       2210B-GET-D-CP-NAME.                                             01860000
      *-------------------*                                             01870000
                                                                        01880000
           MOVE '2210B-GET-D-CP-NAME'       TO WS-PARAGRAPH-NAME        01890000

           IF MAIN-TEMPLATE-ID
              PERFORM 2210BA-GET-FROM-TMPLT
           ELSE
              PERFORM 2210BB-GET-FROM-TMPLT-WORK
           END-IF
           .                                                            01830000
      *---------------------*                                           01850000
       2210BA-GET-FROM-TMPLT.                                           01860000
      *---------------------*                                           01870000
                                                                        01880000
           MOVE '2210BA-GET-FROM-TMPLT'     TO WS-PARAGRAPH-NAME        01890000

           EXEC SQL
                SELECT  DPM01A.CMPNY_NM
                       ,DPM02A.CMPNY_NM
                  INTO :WS-DEALER-CMPNY-NM
                      ,:WS-CLIENT-CMPNY-NM
                  FROM      D0006      DPM14
                 INNER JOIN D0005      DPM01A
                         ON DPM14.DELR_CMPNY_ID = DPM01A.CMPNY_ID
                 INNER JOIN D0005      DPM02A
                         ON DPM14.CLNT_CMPNY_ID = DPM02A.CMPNY_ID
                 WHERE DPM14.MCA_TMPLT_ID = :D014-MCA-TMPLT-ID
                 WITH UR
           END-EXEC

           EVALUATE SQLCODE                                             01690000
              WHEN 0                                                    01700000
              WHEN 100                                                  01700000
                 CONTINUE
              WHEN OTHER                                                01750000
                 MOVE 'D0006'    TO WS-TABLE-NAME            01760000
                 PERFORM 9000-SQL-ERROR                                 01810000
           END-EVALUATE                                                 01820000
           .                                                            01830000
      *--------------------------*                                      01850000
       2210BB-GET-FROM-TMPLT-WORK.                                      01860000
      *--------------------------*                                      01870000
                                                                        01880000
           MOVE '2210BB-GET-FROM-TMPLT-WORK'                            01890000
                                            TO WS-PARAGRAPH-NAME        01890000

           EXEC SQL
                SELECT  DPM01A.CMPNY_NM
                       ,DPM02A.CMPNY_NM
                  INTO :WS-DEALER-CMPNY-NM
                      ,:WS-CLIENT-CMPNY-NM
                  FROM      VDPM15_TMPLT_WORK     DPM15
                 INNER JOIN D0005      DPM01A
                         ON DPM15.DELR_CMPNY_ID = DPM01A.CMPNY_ID
                 INNER JOIN D0005      DPM02A
                         ON DPM15.CLNT_CMPNY_ID = DPM02A.CMPNY_ID
                 WHERE DPM15.MCA_TMPLT_ID = :D014-MCA-TMPLT-ID
                 WITH UR
           END-EXEC

           EVALUATE SQLCODE                                             01690000
              WHEN 0                                                    01700000
              WHEN 100                                                  01700000
                 CONTINUE
              WHEN OTHER                                                01750000
                 MOVE 'VDPM15_TMPLT_WORK'   TO WS-TABLE-NAME            01760000
                 PERFORM 9000-SQL-ERROR                                 01810000
           END-EVALUATE                                                 01820000
           .                                                            01830000
      *----------------------*                                          01850000
       2210C-SET-RENOG-ENABLE.                                          01860000
      *----------------------*                                          01870000
                                                                        01880000
           MOVE '2210C-SET-RENOG-ENABLE'    TO WS-PARAGRAPH-NAME        01890000

           EXEC SQL
                SELECT MCA_TMPLT_ID
                  INTO :WS-TMPLT-ID-CHK
                  FROM D0006
                 WHERE MCA_ISDA_TMPLT_ID = :WS-ISDA-TEMPLATE-ID
                   AND MCA_TMPLT_TYPE_CD IN ('W','R')
                   AND DELR_CMPNY_ID     = :WS-COMPANY-ID
                   AND CLNT_CMPNY_ID     = :WS-CLIENT-CMPNY-ID
                FETCH FIRST ROW ONLY
                WITH UR
           END-EXEC

           EVALUATE SQLCODE                                             01690000
              WHEN 0                                                    01700000
                 SET RENOG-DISABLE          TO TRUE
              WHEN 100                                                  01700000
                 SET RENOG-ENABLE           TO TRUE
              WHEN OTHER                                                01750000
                 MOVE 'D0006'    TO WS-TABLE-NAME            01760000
                 PERFORM 9000-SQL-ERROR                                 01810000
           END-EVALUATE                                                 01820000

           .                                                            01830000
      *-------------------*                                             01850000
       2214-CHECK-USR-WORK.                                             01860000
      *-------------------*                                             01870000
                                                                        01880000
           MOVE '2214-CHECK-USR-WORK'       TO WS-PARAGRAPH-NAME        01890000

           EXEC SQL
                SELECT  DPM19A.MCA_AMND_ID
                  INTO :WS-CHECK-AMND-ID
                  FROM

                    (SELECT DPM19.MCA_AMND_ID
                           ,DPM19.MCA_VALUE_ID
                           ,DPM19.MCA_VALUE_TYPE_CD
                           ,DPM19.AMND_STAT_CD
                       FROM VDPM16_MCA_AMND       DPM16
                           ,VDPM19_LINK_WORK      DPM19
                      WHERE DPM16.MCA_TMPLT_ID      = :D014-MCA-TMPLT-ID
                        AND DPM16.MCA_AMND_ID       = DPM19.MCA_AMND_ID
                        AND DPM19.MCA_VALUE_ID      > 0
                        AND DPM19.MCA_VALUE_TYPE_CD = 'T'
                        AND DPM19.MCA_ACCS_STAT_CD  = 'U') DPM19A

                   ,(SELECT DPM19.MCA_AMND_ID
                           ,DPM19.MCA_VALUE_ID
                           ,DPM19.MCA_VALUE_TYPE_CD
                           ,DPM19.AMND_STAT_CD
                      FROM  VDPM16_MCA_AMND       DPM16
                          , VDPM19_LINK_WORK      DPM19
                     WHERE  DPM16.MCA_TMPLT_ID     = :D014-MCA-TMPLT-ID
                       AND  DPM16.MCA_AMND_ID      = DPM19.MCA_AMND_ID
                       AND  DPM19.MCA_VALUE_ID      > 0
                       AND  DPM19.MCA_VALUE_TYPE_CD = 'T'
                       AND  DPM19.MCA_ACCS_STAT_CD = 'O') DPM19B

                 WHERE  DPM19A.MCA_AMND_ID         = DPM19B.MCA_AMND_ID
                   AND (DPM19A.MCA_VALUE_ID       <> DPM19B.MCA_VALUE_ID
                    OR  DPM19A.MCA_VALUE_TYPE_CD  <>
                                                DPM19B.MCA_VALUE_TYPE_CD
                    OR DPM19A.AMND_STAT_CD       <> DPM19B.AMND_STAT_CD)
                   FETCH FIRST ROW ONLY
           END-EXEC

           EVALUATE SQLCODE                                             01690000
              WHEN 0                                                    01700000
                 SET WORK-VALUE             TO TRUE
              WHEN 100                                                  01700000
                 PERFORM 2214A-CHECK-USR-WORK-DOC
              WHEN OTHER                                                01750000
                 MOVE 'VDPM19_LINK_WORK'    TO WS-TABLE-NAME            01760000
                 PERFORM 9000-SQL-ERROR                                 01810000
           END-EVALUATE                                                 01820000
           .                                                            01830000
      *------------------------*                                        01850000
       2214A-CHECK-USR-WORK-DOC.                                        01860000
      *------------------------*                                        01870000
                                                                        01880000
           MOVE '2214A-CHECK-USR-WORK-DOC'  TO WS-PARAGRAPH-NAME        01890000

           EXEC SQL
                SELECT  DPM19A.MCA_AMND_ID
                  INTO :WS-CHECK-AMND-ID
                  FROM

                    (SELECT DPM19.MCA_AMND_ID
                           ,DPM19.MCA_VALUE_ID
                           ,DPM19.MCA_VALUE_TYPE_CD
                           ,DPM19.AMND_STAT_CD
                       FROM VDPM16_MCA_AMND       DPM16
                           ,VDPM19_LINK_WORK      DPM19
                      WHERE DPM16.MCA_TMPLT_ID      = :D014-MCA-TMPLT-ID
                        AND DPM16.MCA_AMND_ID       = DPM19.MCA_AMND_ID
                        AND DPM19.MCA_VALUE_ID      > 0
                        AND DPM19.MCA_VALUE_TYPE_CD ='D'
                        AND DPM19.MCA_ACCS_STAT_CD  = 'U') DPM19A

                   ,(SELECT DPM19.MCA_AMND_ID
                           ,DPM19.MCA_VALUE_ID
                           ,DPM19.MCA_VALUE_TYPE_CD
                           ,DPM19.AMND_STAT_CD
                      FROM  VDPM16_MCA_AMND       DPM16
                          , VDPM19_LINK_WORK      DPM19
                     WHERE  DPM16.MCA_TMPLT_ID     = :D014-MCA-TMPLT-ID
                       AND  DPM16.MCA_AMND_ID      = DPM19.MCA_AMND_ID
                       AND  DPM19.MCA_VALUE_ID      > 0
                       AND  DPM19.MCA_VALUE_TYPE_CD ='D'
                       AND  DPM19.MCA_ACCS_STAT_CD = 'O') DPM19B

                 WHERE  DPM19A.MCA_AMND_ID         = DPM19B.MCA_AMND_ID
                   AND (DPM19A.MCA_VALUE_ID       <> DPM19B.MCA_VALUE_ID
                    OR  DPM19A.MCA_VALUE_TYPE_CD  <>
                                                DPM19B.MCA_VALUE_TYPE_CD
                    OR DPM19A.AMND_STAT_CD       <> DPM19B.AMND_STAT_CD)
                   FETCH FIRST ROW ONLY
           END-EXEC

           EVALUATE SQLCODE                                             01690000
              WHEN 0                                                    01700000
                 SET WORK-VALUE             TO TRUE
              WHEN 100                                                  01700000
                 PERFORM 2214B-CHECK-USR-WORK-CMNT
              WHEN OTHER                                                01750000
                 MOVE 'VDPM19_LINK_WORK'    TO WS-TABLE-NAME            01760000
                 PERFORM 9000-SQL-ERROR                                 01810000
           END-EVALUATE                                                 01820000
           .                                                            01830000
      *-------------------------*                                       01850000
       2214B-CHECK-USR-WORK-CMNT.                                       01860000
      *-------------------------*                                       01870000
                                                                        01880000
           MOVE '2214B-CHECK-USR-WORK-CMNT' TO WS-PARAGRAPH-NAME        01890000

           EXEC SQL
                SELECT COUNT(DPM19.MCA_AMND_ID)
                   INTO :WS-USR-WORK-COUNT
                   FROM NSCC.VDPM16_MCA_AMND  DPM16
                       ,NSCC.VDPM19_LINK_WORK DPM19
                  WHERE DPM16.MCA_TMPLT_ID      = :D014-MCA-TMPLT-ID
                    AND DPM16.MCA_AMND_ID       = DPM19.MCA_AMND_ID
                    AND DPM19.MCA_VALUE_ID      > 0
                    AND DPM19.MCA_VALUE_TYPE_CD = 'C'
                    AND DPM19.MCA_ACCS_STAT_CD  = 'U'
           END-EXEC

           EVALUATE SQLCODE                                             01690000
              WHEN 0                                                    01700000
                 IF WS-USR-WORK-COUNT = 0
                    SET NO-WORK-VALUE       TO TRUE
                 ELSE
                    PERFORM 2214C-CHECK-ORG-WORK-CMNT
                 END-IF
              WHEN OTHER                                                01750000
                 MOVE 'VDPM19_LINK_WORK'    TO WS-TABLE-NAME            01760000
                 PERFORM 9000-SQL-ERROR                                 01810000
           END-EVALUATE                                                 01820000
           .                                                            01830000
      *-------------------------*                                       01850000
       2214C-CHECK-ORG-WORK-CMNT.                                       01860000
      *-------------------------*                                       01870000
                                                                        01880000
           MOVE '2214C-CHECK-ORG-WORK-CMNT' TO WS-PARAGRAPH-NAME        01890000

           EXEC SQL
                SELECT COUNT(DPM19.MCA_AMND_ID)
                   INTO :WS-ORG-WORK-COUNT
                   FROM NSCC.VDPM16_MCA_AMND  DPM16
                       ,NSCC.VDPM19_LINK_WORK DPM19
                  WHERE DPM16.MCA_TMPLT_ID      = :D014-MCA-TMPLT-ID
                    AND DPM16.MCA_AMND_ID       = DPM19.MCA_AMND_ID
                    AND DPM19.MCA_VALUE_ID      > 0
                    AND DPM19.MCA_VALUE_TYPE_CD = 'C'
                    AND DPM19.MCA_ACCS_STAT_CD  = 'O'
           END-EXEC

           EVALUATE SQLCODE                                             01690000
              WHEN 0                                                    01700000
                 IF WS-USR-WORK-COUNT = WS-ORG-WORK-COUNT
                    SET NO-WORK-VALUE       TO TRUE
                 ELSE
                    SET WORK-VALUE          TO TRUE
                 END-IF
              WHEN OTHER                                                01750000
                 MOVE 'VDPM19_LINK_WORK'    TO WS-TABLE-NAME            01760000
                 PERFORM 9000-SQL-ERROR                                 01810000
           END-EVALUATE                                                 01820000

           .                                                            01830000
      *-----------------------*                                         01850000
       2215-CHECK-WORK-VALUES.                                          01860000
      *-----------------------*                                         01870000
                                                                        01880000
           MOVE '2215-CHECK-WORK-VALUES'    TO WS-PARAGRAPH-NAME        01890000

           IF MAIN-TEMPLATE-ID
              EXEC SQL
                   SELECT DPM17A.MCA_AMND_ID
                     INTO :WS-CHECK-AMND-ID
                     FROM
                  (SELECT COALESCE(DPM17.MCA_AMND_ID,DPM16.MCA_AMND_ID)
                                                        AS MCA_AMND_ID
                     FROM      D0006         DPM14
                  LEFT OUTER JOIN VDPM17_AMND_WORK DPM17
                          ON DPM14.MCA_TMPLT_ID = DPM17.MCA_TMPLT_ID
                  LEFT OUTER JOIN VDPM16_MCA_AMND          DPM16
                          ON DPM14.MCA_TMPLT_ID = DPM16.MCA_TMPLT_ID
                       WHERE DPM14.MCA_TMPLT_ID = :D014-MCA-TMPLT-ID)
                                                        DPM17A
                  INNER JOIN      VDPM19_LINK_WORK         DPM19
                          ON DPM17A.MCA_AMND_ID = DPM19.MCA_AMND_ID
                  FETCH FIRST ROW ONLY
              END-EXEC
           ELSE
              EXEC SQL
                   SELECT DPM17.MCA_AMND_ID
                     INTO :WS-CHECK-AMND-ID
                     FROM VDPM15_TMPLT_WORK        DPM15
               INNER JOIN VDPM17_AMND_WORK         DPM17
                       ON DPM15.MCA_TMPLT_ID = DPM17.MCA_TMPLT_ID
               INNER JOIN NSCC.VDPM19_LINK_WORK    DPM19
                       ON DPM17.MCA_AMND_ID  = DPM19.MCA_AMND_ID
                    WHERE DPM15.MCA_TMPLT_ID = :D014-MCA-TMPLT-ID
                  FETCH FIRST ROW ONLY
              END-EXEC
           END-IF

           EVALUATE SQLCODE                                             01690000
              WHEN 0                                                    01700000
                 SET WORK-VALUE             TO TRUE
              WHEN 100                                                  01700000
                 SET NO-WORK-VALUE          TO TRUE
              WHEN OTHER                                                01750000
                 IF MAIN-TEMPLATE-ID
                    MOVE 'D0006' TO WS-TABLE-NAME            01760000
                 ELSE
                    MOVE 'VDPM15_TMPLT_WORK' TO WS-TABLE-NAME           01760000
                 END-IF
                 PERFORM 9000-SQL-ERROR                                 01810000
           END-EVALUATE                                                 01820000
           .                                                            01830000
      *-----------------------------*                                   01530000
       2216-GET-WORK-ISDA-TMPLT-NAME.                                   01540000
      *-----------------------------*                                   01550000
                                                                        01560000
           MOVE '2216-GET-WORK-ISDA-TMPLT-NAME'                         01570000
                                            TO WS-PARAGRAPH-NAME        01570000

           EXEC SQL
                SELECT  DPM14.MCA_TMPLT_NM
      *           INTO  :WS-ISDA-TMPLT-NM
                  INTO  :D014-MCA-TMPLT-NM
                  FROM  VDPM15_TMPLT_WORK DPM15
                       ,D0006       DPM14
                 WHERE  DPM14.MCA_TMPLT_ID = DPM15.MCA_ISDA_TMPLT_ID
                   AND  DPM15.MCA_TMPLT_ID = :D014-MCA-TMPLT-ID
           END-EXEC

           EVALUATE SQLCODE                                             01690000
              WHEN 0                                                    01700000
                 MOVE 'SP00'                TO LS-SP-RC
                 MOVE D014-MCA-TMPLT-NM-TEXT
                                            TO WS-ISDA-TMPLT-NM
              WHEN 100                                                  01700000
                 MOVE 'SP50'                TO LS-SP-RC
                 MOVE WS-INVALID-TMPLT-ID   TO LS-SP-ERROR-AREA
                 PERFORM 9990-GOBACK                                    01810000
              WHEN OTHER                                                01750000
                 MOVE 'D0006'    TO WS-TABLE-NAME            01760000
                 PERFORM 9000-SQL-ERROR                                 01810000
           END-EVALUATE                                                 01820000
           .                                                            01520000
      *-----------------------------*                                   01530000
       2217-GET-MAIN-ISDA-TMPLT-NAME.                                   01540000
      *-----------------------------*                                   01550000
                                                                        01560000
           MOVE '2217-GET-MAIN-ISDA-TMPLT-NAME'                         01570000
                                            TO WS-PARAGRAPH-NAME        01570000

           EXEC SQL
                SELECT  DPM15.MCA_TMPLT_NM
      *           INTO  :WS-ISDA-TMPLT-NM
                  INTO  :D014-MCA-TMPLT-NM
                  FROM  D0006 DPM14
                       ,D0006 DPM15
                 WHERE  DPM15.MCA_TMPLT_ID = DPM14.MCA_ISDA_TMPLT_ID
                   AND  DPM14.MCA_TMPLT_ID = :D014-MCA-TMPLT-ID
           END-EXEC

           EVALUATE SQLCODE                                             01690000
              WHEN 0                                                    01700000
                 MOVE 'SP00'                TO LS-SP-RC
                 MOVE D014-MCA-TMPLT-NM-TEXT
                                            TO WS-ISDA-TMPLT-NM
              WHEN 100                                                  01700000
                 MOVE 'SP50'                TO LS-SP-RC
                 MOVE WS-INVALID-TMPLT-ID   TO LS-SP-ERROR-AREA
                 PERFORM 9990-GOBACK                                    01810000
              WHEN OTHER                                                01750000
                 MOVE 'D0006'    TO WS-TABLE-NAME            01760000
                 PERFORM 9000-SQL-ERROR                                 01810000
           END-EVALUATE                                                 01820000
           .                                                            01520000
      *-------------------------*                                       01850000
       2220-GET-WORK-TEMP-LAYOUT.                                       01860000
      *-------------------------*                                       01870000
                                                                        01880000
           MOVE '2220-GET-WORK-TEMP-LAYOUT' TO WS-PARAGRAPH-NAME        01890000
           MOVE WS-TMPLT-LCK-ST             TO WS-TMPLT-LCK-STAT        01890000
           MOVE WS-L-ORG-CD                 TO WS-LCK-ORG-CD            01890000
           MOVE WS-L-ORG-USR-CD             TO WS-LCK-ORG-USR-CD        01890000
           MOVE WS-L-ORG-USR-NM             TO WS-LCK-ORG-USR-NM        01890000
                                                                        01900000
           IF DISPLAY-ACTIVE
              DISPLAY "D014-MCA-TMPLT-ID " D014-MCA-TMPLT-ID
           END-IF
                                                                        01900000
           EXEC SQL
               DECLARE DTMXMSTG_W_CSR CURSOR WITH HOLD WITH RETURN FOR
1                 SELECT DPM15.MCA_TMPLT_ID
2                       ,DPM15.MCA_TMPLT_NM
3                       ,DPM15.MCA_TMPLT_SHORT_NM
4                       ,DPM15.MCA_TMPLT_TYPE_CD
5                       ,DPM15.DELR_CMPNY_ID
6                       ,DPM15.CLNT_CMPNY_ID
7                       ,DPM15.MCA_DELR_STAT_CD
8                       ,DPM15.MCA_CLNT_STAT_CD
9                       ,DPM15.ATTRB_PRDCT_ID
10                      ,DPM04.ATTRB_VALUE_DS
11                      ,DPM15.ATTRB_SUB_PRDCT_ID
12                      ,DPM04A.ATTRB_VALUE_DS
13                      ,DPM15.ATTRB_REGN_ID
14                      ,DPM04B.ATTRB_VALUE_DS
15                      ,DPM15.MCA_STAT_IN
16                      ,:WS-TMPLT-LCK-STAT
17                      ,:WS-LCK-ORG-CD
18                      ,:WS-LCK-ORG-USR-CD
19                      ,:WS-LCK-ORG-USR-NM
20                      ,:WS-USER-IND
21                      ,:WS-SAVE-ENABLE
22                      ,:WS-ISDA-TMPLT-NM
23                      ,DPM15.MCA_TMPLT_RQSTR_ID
24                      ,:WS-SUBMIT-APP-ENABLE
25                      ,:WS-SUBMIT-CP-ENABLE
26                      ,:WS-SUBMIT-EXEC-ENABLE
27                      ,:WS-PROP-LANG-STATUS
28                      ,:WS-DEALER-CMPNY-NM
29                      ,:WS-CLIENT-CMPNY-NM
30                      ,:WS-RENOG-ENABLE
                    FROM VDPM15_TMPLT_WORK                   DPM15
              INNER JOIN VDPM04_ATTRB_DTL                    DPM04
                 ON DPM15.ATTRB_PRDCT_ID  = DPM04.ATTRB_ID
                AND DPM04.ATTRB_TYPE_ID   = 'P'
              INNER JOIN VDPM04_ATTRB_DTL                    DPM04A
                 ON DPM15.ATTRB_SUB_PRDCT_ID = DPM04A.ATTRB_ID
                AND DPM04A.ATTRB_TYPE_ID  = 'S'
              INNER JOIN VDPM04_ATTRB_DTL                    DPM04B
                 ON DPM15.ATTRB_REGN_ID   = DPM04B.ATTRB_ID
                AND DPM04B.ATTRB_TYPE_ID  = 'R'
                   WHERE DPM15.MCA_TMPLT_ID = :D014-MCA-TMPLT-ID
                  WITH UR
           END-EXEC
                                                                        02010000
           EXEC SQL
                OPEN DTMXMSTG_W_CSR
           END-EXEC

           EVALUATE SQLCODE                                             01690000
              WHEN 0                                                    01700000
                 MOVE 'SP00'                TO LS-SP-RC
              WHEN OTHER                                                01750000
                 MOVE 'D0006'    TO WS-TABLE-NAME            01760000
                 PERFORM 9000-SQL-ERROR                                 01810000
           END-EVALUATE                                                 01820000
           .                                                            01830000
      *-------------------------*                                       01850000
       2230-GET-MAIN-TEMP-LAYOUT.                                       01860000
      *-------------------------*                                       01870000
                                                                        01880000
           MOVE '2230-GET-MAIN-TEMP-LAYOUT' TO WS-PARAGRAPH-NAME        01890000
           MOVE WS-TMPLT-LCK-ST             TO WS-TMPLT-LCK-STAT        01890000
           MOVE WS-L-ORG-CD                 TO WS-LCK-ORG-CD            01890000
           MOVE WS-L-ORG-USR-CD             TO WS-LCK-ORG-USR-CD        01890000
           MOVE WS-L-ORG-USR-NM             TO WS-LCK-ORG-USR-NM        01890000
                                                                        01900000
           EXEC SQL
               DECLARE DTMXMSTG_M_CSR CURSOR WITH HOLD WITH RETURN FOR
1                 SELECT DPM14.MCA_TMPLT_ID
2                       ,DPM14.MCA_TMPLT_NM
3                       ,DPM14.MCA_TMPLT_SHORT_NM
4                       ,DPM14.MCA_TMPLT_TYPE_CD
5                       ,DPM14.DELR_CMPNY_ID
6                       ,DPM14.CLNT_CMPNY_ID
7                       ,DPM14.MCA_DELR_STAT_CD
8                       ,DPM14.MCA_CLNT_STAT_CD
9                       ,DPM14.ATTRB_PRDCT_ID
10                      ,DPM04.ATTRB_VALUE_DS
11                      ,DPM14.ATTRB_SUB_PRDCT_ID
12                      ,DPM04A.ATTRB_VALUE_DS
13                      ,DPM14.ATTRB_REGN_ID
14                      ,DPM04B.ATTRB_VALUE_DS
15                      ,DPM14.MCA_STAT_IN
16                      ,:WS-TMPLT-LCK-STAT
17                      ,:WS-LCK-ORG-CD
18                      ,:WS-LCK-ORG-USR-CD
19                      ,:WS-LCK-ORG-USR-NM
20                      ,:WS-USER-IND
21                      ,:WS-SAVE-ENABLE
22                      ,:WS-ISDA-TMPLT-NM
23                      ,DPM14.MCA_TMPLT_RQSTR_ID
24                      ,:WS-SUBMIT-APP-ENABLE
25                      ,:WS-SUBMIT-CP-ENABLE
26                      ,:WS-SUBMIT-EXEC-ENABLE
27                      ,:WS-PROP-LANG-STATUS
28                      ,:WS-DEALER-CMPNY-NM
29                      ,:WS-CLIENT-CMPNY-NM
30                      ,:WS-RENOG-ENABLE
                    FROM D0006                    DPM14
              INNER JOIN VDPM04_ATTRB_DTL                    DPM04
                 ON DPM14.ATTRB_PRDCT_ID  = DPM04.ATTRB_ID
                AND DPM04.ATTRB_TYPE_ID   = 'P'
              INNER JOIN VDPM04_ATTRB_DTL                    DPM04A
                 ON DPM14.ATTRB_SUB_PRDCT_ID = DPM04A.ATTRB_ID
                AND DPM04A.ATTRB_TYPE_ID  = 'S'
              INNER JOIN VDPM04_ATTRB_DTL                    DPM04B
                 ON DPM14.ATTRB_REGN_ID   = DPM04B.ATTRB_ID
                AND DPM04B.ATTRB_TYPE_ID  = 'R'
                   WHERE DPM14.MCA_TMPLT_ID = :D014-MCA-TMPLT-ID
                  WITH UR
           END-EXEC
                                                                        02010000
           EXEC SQL
                OPEN DTMXMSTG_M_CSR
           END-EXEC

           EVALUATE SQLCODE                                             01690000
              WHEN 0                                                    01700000
                 MOVE 'SP00'                TO LS-SP-RC
              WHEN OTHER                                                01750000
                 MOVE 'D0006'    TO WS-TABLE-NAME            01760000
                 PERFORM 9000-SQL-ERROR                                 01810000
           END-EVALUATE                                                 01820000
           .                                                            02020000
      *-----------------------*                                         01850000
       8880-CHECK-DEBUG-TABLE.                                          01860000
      *-----------------------*                                         01870000
                                                                        01880000
           MOVE '8880-CHECK-DEBUG-TABLE'    TO WS-PARAGRAPH-NAME        01890000
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
                  PERFORM 9000-SQL-ERROR                                 0181000
           END-EVALUATE                                                 00052102
           .
      *---------------*                                                 01850000
       9000-SQL-ERROR.                                                  01860000
      *---------------*                                                 01870000
                                                                        01880000
           MOVE  'DATABASE ERROR HAS OCCURRED. PLEASE CONTACT DTCC.'    01770000
                                            TO LS-SP-ERROR-AREA         01770000
           MOVE  'SP99'                     TO LS-SP-RC                 01780000
           MOVE SQLCODE                     TO WS-SQLCODE               01790000
           DISPLAY 'SQLCODE:'               WS-SQLCODE                  01800000
           DISPLAY 'PARAGRAPH-NAME:'        WS-PARAGRAPH-NAME           01800000
           DISPLAY 'WS-ERROR-AREA '         WS-ERROR-AREA               01800000
           PERFORM 9990-GOBACK                                          01810000
           .                                                            01460000
      *------------*                                                    02580000
       9990-GOBACK.                                                     02590000
      *------------*                                                    02600000
           PERFORM 9995-DISPLAY-DATA                                    02610000
           PERFORM 9999-FORMAT-SQLCA                                    02620000
           GOBACK                                                       02630000
           .                                                            02640000
      *------------------*                                              02650000
       9995-DISPLAY-DATA.                                               02660000
      *------------------*                                              02670000
           IF DISPLAY-ACTIVE
              DISPLAY 'LS-SP-RC             :' LS-SP-RC                 02680000
              DISPLAY 'LS-SP-ERROR-AREA     :' LS-SP-ERROR-AREA         02690000
           END-IF

           IF LS-SP-RC = 'SP99'
              DISPLAY 'WS-ERROR-AREA ' WS-ERROR-AREA
           END-IF
           .                                                            02700000
      *------------------*                                              02710000
       9999-FORMAT-SQLCA.                                               02720000
      *------------------*                                              02730000
           PERFORM DB2000I-FORMAT-SQLCA                                 02740000
              THRU DB2000I-FORMAT-SQLCA-EXIT                            02750000
                                                                        01420000
           IF DISPLAY-ACTIVE
              EXEC SQL
                   SET :WS-TIMESTAMP = CURRENT TIMESTAMP
              END-EXEC
              DISPLAY "PROGRAM DPMXMSTG - END"
              DISPLAY "ENDING TIME " WS-TIMESTAMP
           END-IF
           .                                                            02770000
                                                                        02760000
      **MOVE STATEMENTS TO FORMAT THE OUTSQLCA USING DB2000IA & DB2000IB02790000
        COPY DB2000IC.                                                  02800000
