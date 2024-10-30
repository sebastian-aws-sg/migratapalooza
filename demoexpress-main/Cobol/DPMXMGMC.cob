       IDENTIFICATION DIVISION.                                         00010000
       PROGRAM-ID.    DPMXMGMC.                                         00020000
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
      *    ENTRY DPMXMGMC                                               00130000
      *    NAME  DPMXMGMC(R)                                            00140000
      *                                                                 00150000
      ******************************************************************00160000
      **         P R O G R A M   D O C U M E N T A T I O N            **00170000
      ******************************************************************00180000
      * SYSTEM:    DERIV/SERV MCA XPRESS                               *00190000
      * PROGRAM:   DPMXMGMC                                            *00200000
      *                                                                *00210000
      * THIS STORED PROCEDURE RETURNS THE CATEGORY, TERMS AND          *00220000
      * CORRESPONDING AMENDMENTS FOR A GIVEN TEMPLATE ID               *00230000
      ******************************************************************00240000
      * TABLES:                                                        *00250000
      * -------                                                        *00260000
      * VDPM04_ATTRB_DTL          - COMMON TABLE CONTAINS MASTER       *00320000
      *                             INFORMATION FOR PRODUCT,SUB-PRODUCT*00330000
      *                             REGION, CATEGORY AND TERM          *00340000
      * VDPM07_MCA_CTGRY          - THIS TABLE CONTAINS THE CATEGORY   *00300000
      *                             INFORMATION FOR A TEMPLATE         *00310000
      * VDPM08_MCA_TERMS          - THIS TABLE CONTAINS THE TERM       *00300000
      *                             INFORMATION FOR A TEMPLATE         *00310000
      * VDPM12_MCA_DOC            - THIS TABLE CONTAINS THE DOCUMENT   *00300000
      *                             DETAILS FOR A TEMPLATE AND         *00310000
      *                             AMENDMENT                          *00310000
      * VDPM13_MCA_TEXT           - THIS TABLE CONTAINS THE TEXT VALUE *00300000
      *                             DETAILS FOR AMENDMENT              *00310000
      * D0006          - MASTER TABLE CONTAINS TEMPLATE     *00270000
      *                             DETAILS INCLUSIVE OF ISDA /        *00280000
      *                             CUSTOMIZED TEMPLATE                *00290000
      * VDPM15_TMPLT_WORK         - WORK TABLE CONTAINS TEMPLATE       *00270000
      *                             DETAILS INCLUSIVE OF ISDA /        *00280000
      *                             CUSTOMIZED TEMPLATE                *00290000
      * VDPM16_MCA_AMND           - MASTER TABLE CONTAINS THE AMENDMENT*00300000
      *                             INFORMATION FOR A TEMPLATE         *00310000
      * VDPM17_AMND_WORK          - WORK TABLE CONTAINS THE AMENDMENT  *00300000
      *                             INFORMATION FOR A TEMPLATE         *00310000
      * VDPM18_MCA_LINK           - LINK MASTER TABLE CONTAINS THE     *00300000
      *                             POINTER (COMMENT, DOCUMENT, TEXT)  *00310000
      *                             FOR EACH AMENDMENT                 *00310000
      * VDPM19_LINK_WORK          - LINK WORK TABLE CONTAINS THE       *00300000
      *                             POINTER (COMMENT, DOCUMENT, TEXT)  *00310000
      *                             FOR EACH AMENDMENT                 *00310000
      * VDTM54_DEBUG_CNTRL        - DEBUG CONTROL TABLE                *00300000
      *----------------------------------------------------------------*00350000
      * INCLUDES:                                                      *00360000
      * ---------                                                      *00370000
      * SQLCA    - DB2 COMMAREA                                        *00380000
      * DTM5401  - DCLGEN FOR DISPLAY CONTROL TABLE                    *00380000
      *---------------------------------------------------------------- 00610000
      * COPYBOOKS:                                                     *00580000
      * ---------                                                      *00590000
      * DB2000IA - DB2 STANDARD COPYBOOK WITH FORMATTED DISPLAY SQLCA  *00600000
      * DB2000IB - PICTURE CLAUSE FOR SQLCA                            *00600000
      * DB2000IC - COPYBOOK CONTAINING MOVE STATEMENTS TO FORMAT SQLCA *00600000
      *---------------------------------------------------------------- 00390000
      *---------------------------------------------------------------- 00400000
      *              M A I N T E N A N C E   H I S T O R Y            * 00410000
      *                                                               * 00420000
      *                                                               * 00430000
      * DATE CHANGED    VERSION     PROGRAMMER                        * 00440000
      * ------------    -------     --------------------              * 00450000
      *                                                               * 00460000
      * 08/28/2007        00.00     COGNIZANT                         * 00470000
      * INITIAL IMPLEMENTATION                                        * 00480000
      *                                                               * 00490000
      ***************************************************************** 00500000
       ENVIRONMENT DIVISION.                                            00510000
       DATA DIVISION.                                                   00520000
       WORKING-STORAGE SECTION.                                         00530000
                                                                        00530100
       01  WS-PROGRAM                      PIC X(08) VALUE 'DPMXMGMC'.  00540000
                                                                        00540100
       01  WS-VARIABLES.                                                00550000
             05  WS-TEMPLATE-ID            PIC S9(09) COMP              00560000
                                                     VALUE ZEROES.
             05  WS-CATEGORY-CD            PIC X(08) VALUE SPACES.      00560100
             05  WS-CATEGORY-SQ            PIC S9(04) COMP              00560100
                                                     VALUE ZEROES.      00560500
             05  WS-USER-IND               PIC X(01) VALUE SPACES.      00560200
                 88 LOCKED-USER            VALUE 'L'.
                 88 LOCK-ORG-USER          VALUE 'O'.
                 88 DEFAULT-USER           VALUE 'D'.
             05  WS-FUNC-IND               PIC X(01) VALUE SPACES.      00560200
                 88 MCA-USER               VALUE 'M'.
                 88 ADMIN-USER             VALUE 'A'.
             05  WS-TIMESTAMP              PIC X(26) VALUE SPACES.      00560200
             05  WS-TEMPLATE-CHK           PIC S9(9) COMP-3             00560400
                                                     VALUE ZEROES.      00560500
             05  WS-CATEGORY-CD-MIN        PIC X(08) VALUE SPACES.      00560100
             05  WS-CATEGORY-CD-MAX        PIC X(08) VALUE SPACES.      00560100

             05  WS-CATEGORY-SQ-MIN        PIC S9(04) COMP              00560100
                                                     VALUE ZEROES.      00560500
             05  WS-CATEGORY-SQ-MAX        PIC S9(04) COMP              00560100
                                                     VALUE ZEROES.      00560500
             05  WS-SEQ-MAX-VALUE          PIC S9(04) COMP              00560100
                                                     VALUE ZEROES.
             05  WS-DISPLAY-CONTROL-FLAG   PIC X(001) VALUE SPACES.     01190000
                 88 DISPLAY-ACTIVE         VALUE 'Y'.
                 88 DISPLAY-INACTIVE       VALUE 'N'.
             05  WS-INVALID-USER-IND       PIC X(50)                    00560100
                 VALUE 'INVALID USER INDICATOR'.                        00560100
             05  WS-INVALID-FUNC-IND       PIC X(50)                    00560100
                 VALUE 'INVALID FUNCTION INDICATOR'.                    00560100
             05  WS-INVALID-TMPLT-ID       PIC X(50)                    00560100
                 VALUE 'INVALID TEMPLATE ID'.                           00560100
       01  WS-ERROR-AREA.                                               00570000
             05  WS-PARAGRAPH-NAME         PIC X(40).                   00580000
             05  FILLER                    PIC X VALUE ','.             00590000
             05  WS-TABLE-NAME             PIC X(18).                   00600000
             05  WS-SQLCODE                PIC 9(7).                    00610000
                                                                        00620000
                                                                        00630000
      ***************************************************************** 00640000
      *                        SQL INCLUDES                            *00650000
      ******************************************************************00660000
           EXEC SQL                                                     00670000
                INCLUDE SQLCA                                           00680000
           END-EXEC.                                                    00690000
                                                                        00700000
           EXEC SQL                                                     00670000
                INCLUDE DTM5401                                         00680000
           END-EXEC.                                                    00690000
                                                                        00700000
      ******************************************************************00740000
      * DB2 STANDARD COPYBOOK WITH FORMATTED DISPLAY SQLCA              00750000
      * THIS MUST REMAIN AS THE LAST ENTRY IN WORKING STORAGE           00760000
      ******************************************************************00770000
       COPY  DB2000IA.                                                  00780000
                                                                        00790000
       LINKAGE SECTION.                                                 00800000
                                                                        00810000
      *PICTURE CLAUSE FOR OUTSQLCA - PIC X(179) - FOR LINKAGE SECTION   00820000
       COPY  DB2000IB.                                                  00830000
       01  LS-SP-ERROR-AREA                PIC X(80).                   00840000
       01  LS-SP-RC                        PIC X(04).                   00850000
       01  LS-TEMPLATE-ID                  PIC S9(09) COMP.             00860000
       01  LS-CATEGORY-CD                  PIC X(08).                   00860100
       01  LS-CATEGORY-SQ                  PIC S9(04) COMP.             00860100
       01  LS-FUNC-IND                     PIC X(01).                   00860200
       01  LS-USER-IND                     PIC X(01).                   00860200
                                                                        00870000
       PROCEDURE DIVISION USING  OUTSQLCA,                              00880000
                                 LS-SP-ERROR-AREA,                      00890000
                                 LS-SP-RC,                              00900000
                                 LS-TEMPLATE-ID,                        00910000
                                 LS-CATEGORY-CD,                        00910100
                                 LS-CATEGORY-SQ,                        00910100
                                 LS-FUNC-IND,                           00910200
                                 LS-USER-IND.                           00910200
      *---------*                                                       00920000
       1000-MAIN.                                                       00930000
      *---------*                                                       00940000
                                                                        00950000
           PERFORM 1000-INIT-AND-CHECK-PARMS                            00960000
                                                                        00970000
           PERFORM 2000-VALIDATE-INPUT                                  00980000
                                                                        00990000
           PERFORM 3000-PROCESS-PARA                                    00980000
                                                                        00990000
           PERFORM 9990-GOBACK                                          01000000
           .                                                            01010000
      *-------------------------*                                       01020000
       1000-INIT-AND-CHECK-PARMS.                                       01030000
      *-------------------------*                                       01040000
                                                                        01050000
           MOVE '1000-INIT-AND-CHECK-PARMS' TO WS-PARAGRAPH-NAME        01060000
                                                                        01420000
           PERFORM 8880-CHECK-DEBUG-TABLE
                                                                        01420000
           IF DISPLAY-ACTIVE
              EXEC SQL
                   SET :WS-TIMESTAMP = CURRENT TIMESTAMP
              END-EXEC
              DISPLAY "PROGRAM DPMXMGMC - START"
              DISPLAY "STARTING TIME " WS-TIMESTAMP
           END-IF
                                                                        01070000
           MOVE SPACES                      TO LS-SP-ERROR-AREA         01080000
                                               LS-SP-RC                 01090000
           MOVE LS-TEMPLATE-ID              TO WS-TEMPLATE-ID           01100000
           MOVE LS-CATEGORY-CD              TO WS-CATEGORY-CD           01100000
           MOVE LS-CATEGORY-SQ              TO WS-CATEGORY-SQ           01100000
           MOVE LS-USER-IND                 TO WS-USER-IND              01100000
           MOVE LS-FUNC-IND                 TO WS-FUNC-IND              01100000
                                                                        01420000
           IF DISPLAY-ACTIVE
              DISPLAY "WS-TEMPLATE-ID " WS-TEMPLATE-ID
              DISPLAY "WS-CATEGORY-CD " WS-CATEGORY-CD
              DISPLAY "WS-USER-IND    " WS-USER-IND
              DISPLAY "WS-FUNC-IND    " WS-FUNC-IND
           END-IF
           .                                                            01110000
      *--------------------*                                            01120000
       2000-VALIDATE-INPUT.                                             01130000
      *--------------------*                                            01140000
                                                                        01150000
           MOVE '2000-VALIDATE-INPUT'       TO WS-PARAGRAPH-NAME        01160000
                                                                        01170000
           IF WS-TEMPLATE-ID <= 0
              MOVE  WS-INVALID-TMPLT-ID     TO LS-SP-ERROR-AREA         01390000
              MOVE  'SP50'                  TO LS-SP-RC                 01400000
              PERFORM 9990-GOBACK                                       01430000
           END-IF

           IF WS-CATEGORY-CD > SPACES
              MOVE WS-CATEGORY-CD           TO WS-CATEGORY-CD-MIN
                                               WS-CATEGORY-CD-MAX
           ELSE
              IF DISPLAY-ACTIVE
                 DISPLAY "NO CATEGORY ID SPECIFIED"
              END-IF

              MOVE LOW-VALUES               TO WS-CATEGORY-CD-MIN
              MOVE HIGH-VALUES              TO WS-CATEGORY-CD-MAX
           END-IF

           IF WS-CATEGORY-SQ > 0
              MOVE WS-CATEGORY-SQ           TO WS-CATEGORY-SQ-MIN
                                               WS-CATEGORY-SQ-MAX
           ELSE
              IF DISPLAY-ACTIVE
                 DISPLAY "NO CATEGORY SQ SPECIFIED"
              END-IF

              MOVE 32667                    TO WS-SEQ-MAX-VALUE
              MOVE 0                        TO WS-CATEGORY-SQ-MIN
              MOVE WS-SEQ-MAX-VALUE         TO WS-CATEGORY-SQ-MAX
           END-IF
           .                                                            01110000
      *------------------*                                              01120000
       3000-PROCESS-PARA.                                               01130000
      *------------------*                                              01140000
                                                                        01150000
           MOVE '3000-PROCESS-PARA'         TO WS-PARAGRAPH-NAME        01160000

           IF DISPLAY-ACTIVE
              DISPLAY "FUNCTION INDICATOR" WS-FUNC-IND
           END-IF

           EVALUATE TRUE
               WHEN MCA-USER
                 PERFORM 3100-CHECK-MCA-USER-IND
               WHEN ADMIN-USER
                 PERFORM 3500-CHECK-ADMIN-USER-IND
               WHEN OTHER                                               01170000
                 MOVE  WS-INVALID-FUNC-IND  TO LS-SP-ERROR-AREA         01390000
                 MOVE  'SP50'               TO LS-SP-RC                 01400000
                 PERFORM 9990-GOBACK                                    01430000
           END-EVALUATE

           .                                                            01460000
      *------------------------*                                        01120000
       3100-CHECK-MCA-USER-IND.                                         01130000
      *------------------------*                                        01140000
                                                                        01150000
           MOVE '3100-CHECK-MCA-USER-IND'   TO WS-PARAGRAPH-NAME        01160000

           IF DISPLAY-ACTIVE
              DISPLAY "USER INDICATOR" WS-USER-IND
           END-IF
                                                                        01450000
           EVALUATE TRUE
               WHEN LOCKED-USER
                 PERFORM 3200-USER-SPEC-DETAILS
               WHEN LOCK-ORG-USER
                 PERFORM 3300-ORG-SPEC-DETAILS
               WHEN DEFAULT-USER
                 PERFORM 3400-DEF-SPEC-DETAILS
               WHEN OTHER
                 MOVE  WS-INVALID-USER-IND  TO LS-SP-ERROR-AREA         01390000
                 MOVE  'SP50'               TO LS-SP-RC                 01400000
                 PERFORM 9990-GOBACK                                    01430000
           END-EVALUATE
           .                                                            01460000
      *---------------------*                                           01120000
       3200-USER-SPEC-DETAILS.                                          01130000
      *---------------------*                                           01140000
                                                                        01150000
           MOVE '3200-USER-SPEC-DETAILS'    TO WS-PARAGRAPH-NAME        01160000

           EXEC SQL
                SELECT 1
                  INTO :WS-TEMPLATE-CHK
                  FROM D0006
                 WHERE MCA_TMPLT_ID = :WS-TEMPLATE-ID
           END-EXEC

           EVALUATE SQLCODE
              WHEN 0                                                    01700000
                 MOVE 'SP00'                TO LS-SP-RC
                 PERFORM 3210-MASTER-TEMPLATE-DETAILS
              WHEN 100                                                  01700000
                 PERFORM 3220-WORK-TEMPLATE-DETAILS
              WHEN OTHER                                                01750000
                 MOVE 'D0006'    TO WS-TABLE-NAME            01760000
                 PERFORM 9000-SQL-ERROR
           END-EVALUATE
           .
      *------------------------------*                                  01120000
       3210-MASTER-TEMPLATE-DETAILS.
      *------------------------------*                                  01120000

           MOVE '3210-MASTER-TEMPLATE-DETAILS'                          01160000
                                            TO WS-PARAGRAPH-NAME        01160000

           EXEC SQL
               DECLARE GMC_USR_FV_CSR CURSOR WITH HOLD WITH RETURN FOR
1                SELECT  DPM07A.ATTRB_CTGRY_ID
2                       ,DPM07A.CTGRY_DESC
3                       ,DPM07A.CTGRY_STAT_CD
4                       ,DPM07A.CTGRY_SQ
5                       ,DPM07A.ATTRB_TERM_ID
6                       ,DPM07A.TERM_DESC
7                       ,DPM07A.TERM_STAT_CD
8                       ,DPM07A.TERM_SQ
9                       ,DPM07A.MCA_AMND_ID
10                      ,COALESCE(DPM07A.AMND_STAT_CD,' ')
11                      ,COALESCE(DPM07A.MCA_VALUE_ID,0)
12                      ,CASE WHEN DPM07A.MCA_VALUE_TYPE_CD = 'T'
                              THEN DPM13.MCA_TEXT_DS
                              WHEN DPM07A.MCA_VALUE_TYPE_CD = 'D'
                              THEN DPM12.MCA_DOC_DS
                              ELSE ' '
                         END
13                      ,COALESCE(DPM07A.MCA_VALUE_TYPE_CD,' ')
                 FROM

                (SELECT  DPM07.ATTRB_CTGRY_ID
                        ,DPM04.ATTRB_VALUE_DS AS CTGRY_DESC
                        ,DPM07.CTGRY_STAT_CD
                        ,DPM07.CTGRY_SQ
                        ,DPM08.ATTRB_TERM_ID
                        ,DPM04A.ATTRB_VALUE_DS AS TERM_DESC
                        ,DPM08.TERM_STAT_CD
                        ,DPM08.TERM_SQ
                        ,DPM16.MCA_AMND_ID
                        ,COALESCE(DPM19.AMND_STAT_CD,DPM18.AMND_STAT_CD)
                                              AS AMND_STAT_CD
                        ,COALESCE(DPM19.MCA_VALUE_ID,DPM18.MCA_VALUE_ID)
                                              AS MCA_VALUE_ID
                        ,COALESCE(DPM19.MCA_VALUE_TYPE_CD,
                         DPM18.MCA_VALUE_TYPE_CD) AS MCA_VALUE_TYPE_CD
                    FROM VDPM07_MCA_CTGRY                  DPM07
              INNER JOIN VDPM04_ATTRB_DTL                  DPM04
                 ON DPM07.ATTRB_CTGRY_ID = DPM04.ATTRB_ID
                AND DPM04.ATTRB_TYPE_ID  = 'C'
              INNER JOIN VDPM08_MCA_TERMS                  DPM08
                 ON DPM07.MCA_TMPLT_ID   = DPM08.MCA_TMPLT_ID
                AND DPM07.ATTRB_CTGRY_ID = DPM08.ATTRB_CTGRY_ID
                AND DPM07.CTGRY_SQ       = DPM08.CTGRY_SQ
                AND DPM08.ATTRB_TERM_ID  > ' '
                AND DPM08.TERM_SQ        > 0
              INNER JOIN VDPM04_ATTRB_DTL                  DPM04A
                 ON DPM08.ATTRB_TERM_ID  = DPM04A.ATTRB_ID
                AND DPM04A.ATTRB_TYPE_ID = 'T'
              INNER JOIN VDPM16_MCA_AMND                   DPM16
                 ON DPM08.MCA_TMPLT_ID   = DPM16.MCA_TMPLT_ID
                AND DPM08.ATTRB_CTGRY_ID = DPM16.ATTRB_CTGRY_ID
                AND DPM08.CTGRY_SQ       = DPM16.CTGRY_SQ
                AND DPM08.ATTRB_TERM_ID  = DPM16.ATTRB_TERM_ID
                AND DPM08.TERM_SQ        = DPM16.TERM_SQ
                AND DPM16.MCA_AMND_ID    > 0
              LEFT OUTER JOIN VDPM19_LINK_WORK             DPM19
                ON DPM19.MCA_AMND_ID     = DPM16.MCA_AMND_ID
               AND DPM19.MCA_VALUE_ID    > 0
               AND DPM19.MCA_VALUE_TYPE_CD IN ('T','D','C')
               AND DPM19.MCA_ACCS_STAT_CD = 'U'
              LEFT OUTER JOIN VDPM18_MCA_LINK              DPM18
                ON DPM18.MCA_AMND_ID     = DPM16.MCA_AMND_ID
               AND DPM18.MCA_VALUE_ID    > 0
               AND DPM18.MCA_VALUE_TYPE_CD IN ('T','D','C')
                   WHERE DPM07.MCA_TMPLT_ID = :WS-TEMPLATE-ID
                     AND DPM07.ATTRB_CTGRY_ID BETWEEN
                         :WS-CATEGORY-CD-MIN
                     AND :WS-CATEGORY-CD-MAX
                     AND DPM07.CTGRY_SQ BETWEEN
                         :WS-CATEGORY-SQ-MIN
                     AND :WS-CATEGORY-SQ-MAX)              DPM07A
              LEFT OUTER JOIN VDPM13_MCA_TEXT              DPM13
                ON DPM07A.MCA_VALUE_ID   = DPM13.MCA_VALUE_ID
              LEFT OUTER JOIN VDPM12_MCA_DOC               DPM12
                ON DPM07A.MCA_VALUE_ID   = DPM12.MCA_VALUE_ID

               ORDER BY DPM07A.CTGRY_SQ ASC,
                        DPM07A.TERM_SQ  ASC
               WITH UR
           END-EXEC

           EXEC SQL
               OPEN GMC_USR_FV_CSR
           END-EXEC

           EVALUATE SQLCODE                                             01690000
              WHEN 0                                                    01700000
                 MOVE 'SP00'                TO LS-SP-RC
              WHEN OTHER                                                01750000
                 MOVE 'GMC_USR_FV_CSR'      TO WS-TABLE-NAME            01760000
                 PERFORM 9000-SQL-ERROR
           END-EVALUATE                                                 01820000
           .                                                            01460000
      *------------------------------*                                  01120000
       3220-WORK-TEMPLATE-DETAILS.
      *------------------------------*                                  01120000

           MOVE '3220-WORK-TEMPLATE-DETAILS'                            01160000
                                            TO WS-PARAGRAPH-NAME        01160000

           EXEC SQL
               DECLARE GMC_USR_W_FV_CSR CURSOR WITH HOLD WITH RETURN FOR
1                SELECT  DPM07.ATTRB_CTGRY_ID
2                       ,DPM04.ATTRB_VALUE_DS
3                       ,DPM07.CTGRY_STAT_CD
4                       ,DPM07.CTGRY_SQ
5                       ,DPM08.ATTRB_TERM_ID
6                       ,DPM04A.ATTRB_VALUE_DS
7                       ,DPM08.TERM_STAT_CD
8                       ,DPM08.TERM_SQ
9                       ,DPM17.MCA_AMND_ID
10                      ,COALESCE(DPM19.AMND_STAT_CD,' ')
11                      ,COALESCE(DPM19.MCA_VALUE_ID,0)
12                      ,CASE WHEN DPM19.MCA_VALUE_TYPE_CD = 'T'
                              THEN DPM13.MCA_TEXT_DS
                              WHEN DPM19.MCA_VALUE_TYPE_CD = 'D'
                              THEN DPM12.MCA_DOC_DS
                              ELSE ' '
                         END
13                      ,COALESCE(DPM19.MCA_VALUE_TYPE_CD,' ')
                    FROM VDPM07_MCA_CTGRY                  DPM07
              INNER JOIN VDPM04_ATTRB_DTL                  DPM04
                 ON DPM07.ATTRB_CTGRY_ID = DPM04.ATTRB_ID
                AND DPM04.ATTRB_TYPE_ID  = 'C'
              INNER JOIN VDPM08_MCA_TERMS                  DPM08
                 ON DPM07.MCA_TMPLT_ID   = DPM08.MCA_TMPLT_ID
                AND DPM07.ATTRB_CTGRY_ID = DPM08.ATTRB_CTGRY_ID
                AND DPM07.CTGRY_SQ       = DPM08.CTGRY_SQ
                AND DPM08.ATTRB_TERM_ID  > ' '
                AND DPM08.TERM_SQ        > 0
              INNER JOIN VDPM04_ATTRB_DTL                  DPM04A
                 ON DPM08.ATTRB_TERM_ID  = DPM04A.ATTRB_ID
                AND DPM04A.ATTRB_TYPE_ID = 'T'
              INNER JOIN VDPM17_AMND_WORK                  DPM17
                 ON DPM08.MCA_TMPLT_ID   = DPM17.MCA_TMPLT_ID
                AND DPM08.ATTRB_CTGRY_ID = DPM17.ATTRB_CTGRY_ID
                AND DPM08.CTGRY_SQ       = DPM17.CTGRY_SQ
                AND DPM08.ATTRB_TERM_ID  = DPM17.ATTRB_TERM_ID
                AND DPM08.TERM_SQ        = DPM17.TERM_SQ
                AND DPM17.MCA_AMND_ID    > 0
              INNER JOIN VDPM19_LINK_WORK                  DPM19
                ON DPM19.MCA_AMND_ID     = DPM17.MCA_AMND_ID
               AND DPM19.MCA_VALUE_ID    > 0
               AND DPM19.MCA_VALUE_TYPE_CD IN ('T','D','C')
              LEFT OUTER JOIN VDPM13_MCA_TEXT              DPM13
                ON DPM19.MCA_VALUE_ID    = DPM13.MCA_VALUE_ID
              LEFT OUTER JOIN VDPM12_MCA_DOC               DPM12
                ON DPM19.MCA_VALUE_ID    = DPM12.MCA_VALUE_ID
                   WHERE DPM07.MCA_TMPLT_ID = :WS-TEMPLATE-ID
                     AND DPM07.ATTRB_CTGRY_ID BETWEEN
                         :WS-CATEGORY-CD-MIN
                     AND :WS-CATEGORY-CD-MAX
                     AND DPM07.CTGRY_SQ BETWEEN
                         :WS-CATEGORY-SQ-MIN
                     AND :WS-CATEGORY-SQ-MAX

               ORDER BY DPM07.CTGRY_SQ ASC,
                        DPM08.TERM_SQ  ASC
               WITH UR
           END-EXEC

           EXEC SQL
               OPEN GMC_USR_W_FV_CSR
           END-EXEC

           EVALUATE SQLCODE                                             01690000
              WHEN 0                                                    01700000
                 MOVE 'SP00'                TO LS-SP-RC
              WHEN OTHER                                                01750000
                 MOVE 'GMC_USR_W_FV_CSR'    TO WS-TABLE-NAME            01760000
                 PERFORM 9000-SQL-ERROR
           END-EVALUATE                                                 01820000
           .
      *---------------------*                                           01120000
       3300-ORG-SPEC-DETAILS.                                           01130000
      *---------------------*                                           01140000
                                                                        01150000
           MOVE '3300-ORG-SPEC-DETAILS'     TO WS-PARAGRAPH-NAME        01160000

           EXEC SQL
               DECLARE GMC_ORG_FV_CSR CURSOR WITH HOLD WITH RETURN FOR
1                SELECT  DPM07A.ATTRB_CTGRY_ID
2                       ,DPM07A.CTGRY_DESC
3                       ,DPM07A.CTGRY_STAT_CD
4                       ,DPM07A.CTGRY_SQ
5                       ,DPM07A.ATTRB_TERM_ID
6                       ,DPM07A.TERM_DESC
7                       ,DPM07A.TERM_STAT_CD
8                       ,DPM07A.TERM_SQ
9                       ,DPM07A.MCA_AMND_ID
10                      ,COALESCE(DPM07A.AMND_STAT_CD,' ')
11                      ,COALESCE(DPM07A.MCA_VALUE_ID,0)
12                      ,CASE WHEN DPM07A.MCA_VALUE_TYPE_CD = 'T'
                              THEN DPM13.MCA_TEXT_DS
                              WHEN DPM07A.MCA_VALUE_TYPE_CD = 'D'
                              THEN DPM12.MCA_DOC_DS
                              ELSE ' '
                         END
13                      ,COALESCE(DPM07A.MCA_VALUE_TYPE_CD,' ')
                 FROM

                (SELECT  DPM07.ATTRB_CTGRY_ID
                        ,DPM04.ATTRB_VALUE_DS AS CTGRY_DESC
                        ,DPM07.CTGRY_STAT_CD
                        ,DPM07.CTGRY_SQ
                        ,DPM08.ATTRB_TERM_ID
                        ,DPM04A.ATTRB_VALUE_DS AS TERM_DESC
                        ,DPM08.TERM_STAT_CD
                        ,DPM08.TERM_SQ
                        ,DPM16.MCA_AMND_ID
                        ,COALESCE(DPM19.AMND_STAT_CD,DPM18.AMND_STAT_CD)
                                              AS AMND_STAT_CD
                        ,COALESCE(DPM19.MCA_VALUE_ID,DPM18.MCA_VALUE_ID)
                                              AS MCA_VALUE_ID
                        ,COALESCE(DPM19.MCA_VALUE_TYPE_CD,
                         DPM18.MCA_VALUE_TYPE_CD) AS MCA_VALUE_TYPE_CD
                    FROM VDPM07_MCA_CTGRY                  DPM07
              INNER JOIN VDPM04_ATTRB_DTL                  DPM04
                 ON DPM07.ATTRB_CTGRY_ID = DPM04.ATTRB_ID
                AND DPM04.ATTRB_TYPE_ID = 'C'
              INNER JOIN VDPM08_MCA_TERMS                  DPM08
                 ON DPM07.MCA_TMPLT_ID   = DPM08.MCA_TMPLT_ID
                AND DPM07.ATTRB_CTGRY_ID = DPM08.ATTRB_CTGRY_ID
                AND DPM07.CTGRY_SQ       = DPM08.CTGRY_SQ
                AND DPM08.ATTRB_TERM_ID  > ' '
                AND DPM08.TERM_SQ        > 0
              INNER JOIN VDPM04_ATTRB_DTL                  DPM04A
                 ON DPM08.ATTRB_TERM_ID  = DPM04A.ATTRB_ID
                AND DPM04A.ATTRB_TYPE_ID = 'T'
              INNER JOIN VDPM16_MCA_AMND                   DPM16
                 ON DPM08.MCA_TMPLT_ID   = DPM16.MCA_TMPLT_ID
                AND DPM08.ATTRB_CTGRY_ID = DPM16.ATTRB_CTGRY_ID
                AND DPM08.CTGRY_SQ       = DPM16.CTGRY_SQ
                AND DPM08.ATTRB_TERM_ID  = DPM16.ATTRB_TERM_ID
                AND DPM08.TERM_SQ        = DPM16.TERM_SQ
                AND DPM16.MCA_AMND_ID    > 0
              LEFT OUTER JOIN VDPM19_LINK_WORK             DPM19
                ON DPM19.MCA_AMND_ID     = DPM16.MCA_AMND_ID
               AND DPM19.MCA_VALUE_ID    > 0
               AND DPM19.MCA_VALUE_TYPE_CD IN ('T','D','C')
               AND DPM19.MCA_ACCS_STAT_CD = 'O'
              LEFT OUTER JOIN VDPM18_MCA_LINK              DPM18
                ON DPM18.MCA_AMND_ID      = DPM16.MCA_AMND_ID
               AND DPM18.MCA_VALUE_ID     > 0
               AND DPM18.MCA_VALUE_TYPE_CD IN ('T','D','C')
                   WHERE DPM07.MCA_TMPLT_ID = :WS-TEMPLATE-ID
                     AND DPM07.ATTRB_CTGRY_ID BETWEEN
                         :WS-CATEGORY-CD-MIN
                     AND :WS-CATEGORY-CD-MAX
                     AND DPM07.CTGRY_SQ BETWEEN
                         :WS-CATEGORY-SQ-MIN
                     AND :WS-CATEGORY-SQ-MAX)              DPM07A

              LEFT OUTER JOIN VDPM13_MCA_TEXT              DPM13
                ON DPM07A.MCA_VALUE_ID  = DPM13.MCA_VALUE_ID
              LEFT OUTER JOIN VDPM12_MCA_DOC               DPM12
                ON DPM07A.MCA_VALUE_ID  = DPM12.MCA_VALUE_ID

               ORDER BY DPM07A.CTGRY_SQ ASC,
                        DPM07A.TERM_SQ  ASC
               WITH UR
           END-EXEC

           EXEC SQL
               OPEN GMC_ORG_FV_CSR
           END-EXEC

           EVALUATE SQLCODE                                             01690000
              WHEN 0                                                    01700000
                 MOVE 'SP00'                TO LS-SP-RC
              WHEN OTHER                                                01750000
                 MOVE 'GMC_ORG_FV_CSR'      TO WS-TABLE-NAME            01760000
                 PERFORM 9000-SQL-ERROR
           END-EVALUATE                                                 01820000
           .                                                            01460000
      *---------------------*                                           01120000
       3400-DEF-SPEC-DETAILS.                                           01130000
      *---------------------*                                           01140000
                                                                        01150000
           MOVE '3400-DEF-SPEC-DETAILS'     TO WS-PARAGRAPH-NAME        01160000

           EXEC SQL
               DECLARE GMC_DEF_FV_CSR CURSOR WITH HOLD WITH RETURN FOR
1                SELECT  DPM07.ATTRB_CTGRY_ID
2                       ,DPM04.ATTRB_VALUE_DS
3                       ,DPM07.CTGRY_STAT_CD
4                       ,DPM07.CTGRY_SQ
5                       ,DPM08.ATTRB_TERM_ID
6                       ,DPM04A.ATTRB_VALUE_DS
7                       ,DPM08.TERM_STAT_CD
8                       ,DPM08.TERM_SQ
9                       ,DPM16.MCA_AMND_ID
10                      ,DPM18.AMND_STAT_CD
11                      ,DPM18.MCA_VALUE_ID
12                      ,CASE WHEN DPM18.MCA_VALUE_TYPE_CD = 'T'
                              THEN DPM13.MCA_TEXT_DS
                              WHEN DPM18.MCA_VALUE_TYPE_CD = 'D'
                              THEN DPM12.MCA_DOC_DS
                              ELSE ' '
                         END
13                      ,DPM18.MCA_VALUE_TYPE_CD
                    FROM VDPM07_MCA_CTGRY                  DPM07
              INNER JOIN VDPM04_ATTRB_DTL                  DPM04
                 ON DPM07.ATTRB_CTGRY_ID = DPM04.ATTRB_ID
                AND DPM04.ATTRB_TYPE_ID  = 'C'
              INNER JOIN VDPM08_MCA_TERMS                  DPM08
                 ON DPM07.MCA_TMPLT_ID   = DPM08.MCA_TMPLT_ID
                AND DPM07.ATTRB_CTGRY_ID = DPM08.ATTRB_CTGRY_ID
                AND DPM07.CTGRY_SQ       = DPM08.CTGRY_SQ
                AND DPM08.ATTRB_TERM_ID  > ' '
                AND DPM08.TERM_SQ        > 0
              INNER JOIN VDPM04_ATTRB_DTL                  DPM04A
                 ON DPM08.ATTRB_TERM_ID  = DPM04A.ATTRB_ID
                AND DPM04A.ATTRB_TYPE_ID = 'T'
              INNER JOIN VDPM16_MCA_AMND                   DPM16
                 ON DPM08.MCA_TMPLT_ID   = DPM16.MCA_TMPLT_ID
                AND DPM08.ATTRB_CTGRY_ID = DPM16.ATTRB_CTGRY_ID
                AND DPM08.CTGRY_SQ       = DPM16.CTGRY_SQ
                AND DPM08.ATTRB_TERM_ID  = DPM16.ATTRB_TERM_ID
                AND DPM08.TERM_SQ        = DPM16.TERM_SQ
                AND DPM16.MCA_AMND_ID    > 0
              INNER JOIN VDPM18_MCA_LINK                   DPM18
                ON DPM18.MCA_AMND_ID     = DPM16.MCA_AMND_ID
               AND DPM18.MCA_VALUE_ID    > 0
               AND DPM18.MCA_VALUE_TYPE_CD IN ('T','D','C')
              LEFT OUTER JOIN VDPM13_MCA_TEXT              DPM13
                ON DPM18.MCA_VALUE_ID    = DPM13.MCA_VALUE_ID
              LEFT OUTER JOIN VDPM12_MCA_DOC               DPM12
                ON DPM18.MCA_VALUE_ID    = DPM12.MCA_VALUE_ID
                   WHERE DPM07.MCA_TMPLT_ID = :WS-TEMPLATE-ID
                     AND DPM07.ATTRB_CTGRY_ID BETWEEN
                         :WS-CATEGORY-CD-MIN
                     AND :WS-CATEGORY-CD-MAX
                     AND DPM07.CTGRY_SQ BETWEEN
                         :WS-CATEGORY-SQ-MIN
                     AND :WS-CATEGORY-SQ-MAX

               ORDER BY DPM07.CTGRY_SQ ASC,
                        DPM08.TERM_SQ  ASC
               WITH UR
           END-EXEC

           EXEC SQL
               OPEN GMC_DEF_FV_CSR
           END-EXEC

           EVALUATE SQLCODE                                             01690000
              WHEN 0                                                    01700000
                 MOVE 'SP00'                TO LS-SP-RC
              WHEN OTHER                                                01750000
                 MOVE 'GMC_DEF_FV_CSR'      TO WS-TABLE-NAME            01760000
                 PERFORM 9000-SQL-ERROR
           END-EVALUATE                                                 01820000
           .                                                            01460000
      *-------------------------*                                       01120000
       3500-CHECK-ADMIN-USER-IND.                                       01130000
      *-------------------------*                                       01140000
                                                                        01150000
           MOVE '3500-CHECK-ADMIN-USER-IND' TO WS-PARAGRAPH-NAME        01160000

           IF DISPLAY-ACTIVE
              DISPLAY "USER INDICATOR" WS-USER-IND
           END-IF
                                                                        01450000
           EVALUATE TRUE
               WHEN LOCKED-USER
                 PERFORM 3510-USER-SPEC-DETAILS
               WHEN LOCK-ORG-USER
               WHEN DEFAULT-USER
                 PERFORM 3520-DEF-SPEC-DETAILS
               WHEN OTHER
                 MOVE  WS-INVALID-USER-IND  TO LS-SP-ERROR-AREA         01390000
                 MOVE  'SP50'               TO LS-SP-RC                 01400000
                 PERFORM 9990-GOBACK                                    01430000
           END-EVALUATE
           .                                                            01460000
      *---------------------*
       3510-USER-SPEC-DETAILS.
      *---------------------*

           MOVE '3510-USER-SPEC-DETAILS'    TO WS-PARAGRAPH-NAME        01160000

           EXEC SQL
               DECLARE GMC_AMUSR_FV_CSR CURSOR WITH HOLD WITH RETURN FOR
1                SELECT  DPM19A.ATTRB_CTGRY_ID
2                       ,DPM19A.CTGRY_DESC
3                       ,DPM19A.CTGRY_STAT_CD
4                       ,DPM19A.CTGRY_SQ
5                       ,DPM19A.ATTRB_TERM_ID
6                       ,DPM19A.TERM_DESC
7                       ,DPM19A.TERM_STAT_CD
8                       ,DPM19A.TERM_SQ
9                       ,COALESCE(DPM19A.MCA_AMND_ID,0)
10                      ,COALESCE(DPM19A.AMND_STAT_CD,' ')
11                      ,COALESCE(DPM19A.MCA_VALUE_ID,0)
12                      ,CASE WHEN DPM19A.MCA_VALUE_TYPE_CD = 'T'
                              THEN DPM13.MCA_TEXT_DS
                              WHEN DPM19A.MCA_VALUE_TYPE_CD = 'D'
                              THEN DPM12.MCA_DOC_DS
                              ELSE ' '
                         END
13                      ,COALESCE(DPM19A.MCA_VALUE_TYPE_CD,' ')
                 FROM
                (SELECT  DPM17A.ATTRB_CTGRY_ID
                        ,DPM17A.CTGRY_DESC
                        ,DPM17A.CTGRY_STAT_CD
                        ,DPM17A.CTGRY_SQ
                        ,DPM17A.ATTRB_TERM_ID
                        ,DPM17A.TERM_DESC
                        ,DPM17A.TERM_STAT_CD
                        ,DPM17A.TERM_SQ
                        ,DPM17A.MCA_AMND_ID
                        ,COALESCE(DPM19.AMND_STAT_CD,DPM18.AMND_STAT_CD)
                                              AS AMND_STAT_CD
                        ,COALESCE(DPM19.MCA_VALUE_ID,DPM18.MCA_VALUE_ID)
                                              AS MCA_VALUE_ID
                        ,COALESCE(DPM19.MCA_VALUE_TYPE_CD,
                         DPM18.MCA_VALUE_TYPE_CD) AS MCA_VALUE_TYPE_CD
                 FROM
                (SELECT  DPM07.ATTRB_CTGRY_ID
                        ,DPM04.ATTRB_VALUE_DS AS CTGRY_DESC
                        ,DPM07.CTGRY_STAT_CD
                        ,DPM07.CTGRY_SQ
                        ,DPM08.ATTRB_TERM_ID
                        ,DPM04A.ATTRB_VALUE_DS AS TERM_DESC
                        ,DPM08.TERM_STAT_CD
                        ,DPM08.TERM_SQ
                        ,COALESCE(DPM17.MCA_AMND_ID,DPM16.MCA_AMND_ID)
                                                       AS MCA_AMND_ID
                    FROM NSCC.VDPM07_MCA_CTGRY             DPM07
              INNER JOIN NSCC.VDPM04_ATTRB_DTL             DPM04
                 ON DPM07.ATTRB_CTGRY_ID   = DPM04.ATTRB_ID
                AND DPM04.ATTRB_TYPE_ID    = 'C'
              INNER JOIN NSCC.VDPM08_MCA_TERMS             DPM08
                 ON DPM07.MCA_TMPLT_ID     = DPM08.MCA_TMPLT_ID
                AND DPM07.ATTRB_CTGRY_ID   = DPM08.ATTRB_CTGRY_ID
                AND DPM07.CTGRY_SQ         = DPM08.CTGRY_SQ
                AND DPM08.ATTRB_TERM_ID    > ' '
                AND DPM08.TERM_SQ          > 0
              INNER JOIN NSCC.VDPM04_ATTRB_DTL             DPM04A
                 ON DPM08.ATTRB_TERM_ID    = DPM04A.ATTRB_ID
                AND DPM04A.ATTRB_TYPE_ID   = 'T'
              LEFT OUTER JOIN NSCC.VDPM17_AMND_WORK        DPM17
                 ON DPM08.MCA_TMPLT_ID     = DPM17.MCA_TMPLT_ID
                AND DPM08.ATTRB_CTGRY_ID   = DPM17.ATTRB_CTGRY_ID
                AND DPM08.CTGRY_SQ         = DPM17.CTGRY_SQ
                AND DPM08.ATTRB_TERM_ID    = DPM17.ATTRB_TERM_ID
                AND DPM08.TERM_SQ          = DPM17.TERM_SQ
                AND DPM17.MCA_AMND_ID      > 0
              LEFT OUTER JOIN NSCC.VDPM16_MCA_AMND         DPM16
                 ON DPM08.MCA_TMPLT_ID     = DPM16.MCA_TMPLT_ID
                AND DPM08.ATTRB_CTGRY_ID   = DPM16.ATTRB_CTGRY_ID
                AND DPM08.CTGRY_SQ         = DPM16.CTGRY_SQ
                AND DPM08.ATTRB_TERM_ID    = DPM16.ATTRB_TERM_ID
                AND DPM08.TERM_SQ          = DPM16.TERM_SQ
                AND DPM16.MCA_AMND_ID      > 0
                   WHERE DPM07.MCA_TMPLT_ID = :WS-TEMPLATE-ID
                     AND DPM07.ATTRB_CTGRY_ID BETWEEN
                         :WS-CATEGORY-CD-MIN
                     AND :WS-CATEGORY-CD-MAX
                     AND DPM07.CTGRY_SQ BETWEEN
                         :WS-CATEGORY-SQ-MIN
                     AND :WS-CATEGORY-SQ-MAX)              DPM17A
              LEFT OUTER JOIN NSCC.VDPM19_LINK_WORK        DPM19
                ON DPM19.MCA_AMND_ID       = DPM17A.MCA_AMND_ID
               AND DPM19.MCA_VALUE_ID      > 0
               AND DPM19.MCA_VALUE_TYPE_CD IN ('T', 'D', 'C')
               AND DPM19.MCA_ACCS_STAT_CD  = 'U'
              LEFT OUTER JOIN NSCC.VDPM18_MCA_LINK         DPM18
                ON DPM18.MCA_AMND_ID       = DPM17A.MCA_AMND_ID
               AND DPM18.MCA_VALUE_ID      > 0
               AND DPM18.MCA_VALUE_TYPE_CD IN ('T', 'D', 'C'))
                                                           DPM19A
              LEFT OUTER JOIN NSCC.VDPM13_MCA_TEXT         DPM13
                ON DPM19A.MCA_VALUE_ID     = DPM13.MCA_VALUE_ID
              LEFT OUTER JOIN NSCC.VDPM12_MCA_DOC          DPM12
                ON DPM19A.MCA_VALUE_ID     = DPM12.MCA_VALUE_ID

               ORDER BY DPM19A.CTGRY_SQ ASC,
                        DPM19A.TERM_SQ  ASC
               WITH UR
           END-EXEC

           EXEC SQL
               OPEN GMC_AMUSR_FV_CSR
           END-EXEC

           EVALUATE SQLCODE                                             01690000
              WHEN 0                                                    01700000
                 MOVE 'SP00'                TO LS-SP-RC
              WHEN OTHER                                                01750000
                 MOVE 'GMC_AUSR_FV_CSR'     TO WS-TABLE-NAME            01760000
                 PERFORM 9000-SQL-ERROR
           END-EVALUATE                                                 01820000
           .                                                            01460000
      *---------------------*                                           01120000
       3520-DEF-SPEC-DETAILS.                                           01130000
      *---------------------*                                           01140000
                                                                        01150000
           MOVE '3520-DEF-SPEC-DETAILS'     TO WS-PARAGRAPH-NAME        01160000

           EXEC SQL
               DECLARE GMC_ADEF_FV_CSR CURSOR WITH HOLD WITH RETURN FOR
1                SELECT  DPM07.ATTRB_CTGRY_ID
2                       ,DPM04.ATTRB_VALUE_DS
3                       ,DPM07.CTGRY_STAT_CD
4                       ,DPM07.CTGRY_SQ
5                       ,DPM08.ATTRB_TERM_ID
6                       ,DPM04A.ATTRB_VALUE_DS
7                       ,DPM08.TERM_STAT_CD
8                       ,DPM08.TERM_SQ
9                       ,COALESCE(DPM16.MCA_AMND_ID,0)
10                      ,COALESCE(DPM18.AMND_STAT_CD,' ')
11                      ,COALESCE(DPM18.MCA_VALUE_ID,0)
12                      ,CASE WHEN DPM18.MCA_VALUE_TYPE_CD = 'T'
                              THEN DPM13.MCA_TEXT_DS
                              WHEN DPM18.MCA_VALUE_TYPE_CD = 'D'
                              THEN DPM12.MCA_DOC_DS
                              ELSE ' '
                         END
13                      ,COALESCE(DPM18.MCA_VALUE_TYPE_CD,' ')
                    FROM VDPM07_MCA_CTGRY                  DPM07
              INNER JOIN VDPM04_ATTRB_DTL                  DPM04
                 ON DPM07.ATTRB_CTGRY_ID    = DPM04.ATTRB_ID
                AND DPM04.ATTRB_TYPE_ID     = 'C'
              INNER JOIN VDPM08_MCA_TERMS                  DPM08
                 ON DPM07.MCA_TMPLT_ID      = DPM08.MCA_TMPLT_ID
                AND DPM07.ATTRB_CTGRY_ID    = DPM08.ATTRB_CTGRY_ID
                AND DPM07.CTGRY_SQ          = DPM08.CTGRY_SQ
                AND DPM08.ATTRB_TERM_ID     > ' '
                AND DPM08.TERM_SQ           > 0
              INNER JOIN VDPM04_ATTRB_DTL                  DPM04A
                 ON DPM08.ATTRB_TERM_ID     = DPM04A.ATTRB_ID
                AND DPM04A.ATTRB_TYPE_ID    = 'T'
              LEFT OUTER JOIN VDPM16_MCA_AMND              DPM16
                 ON DPM08.MCA_TMPLT_ID      = DPM16.MCA_TMPLT_ID
                AND DPM08.ATTRB_CTGRY_ID    = DPM16.ATTRB_CTGRY_ID
                AND DPM08.CTGRY_SQ          = DPM16.CTGRY_SQ
                AND DPM08.ATTRB_TERM_ID     = DPM16.ATTRB_TERM_ID
                AND DPM08.TERM_SQ           = DPM16.TERM_SQ
                AND DPM16.MCA_AMND_ID       > 0
              LEFT OUTER JOIN VDPM18_MCA_LINK              DPM18
                ON DPM18.MCA_AMND_ID        = DPM16.MCA_AMND_ID
               AND DPM18.MCA_VALUE_ID       > 0
               AND DPM18.MCA_VALUE_TYPE_CD  IN ('T','D','C')
              LEFT OUTER JOIN VDPM13_MCA_TEXT              DPM13
                ON DPM18.MCA_VALUE_ID       = DPM13.MCA_VALUE_ID
              LEFT OUTER JOIN VDPM12_MCA_DOC               DPM12
                ON DPM18.MCA_VALUE_ID       = DPM12.MCA_VALUE_ID
                   WHERE DPM07.MCA_TMPLT_ID = :WS-TEMPLATE-ID
                     AND DPM07.ATTRB_CTGRY_ID BETWEEN
                         :WS-CATEGORY-CD-MIN
                     AND :WS-CATEGORY-CD-MAX
                     AND DPM07.CTGRY_SQ BETWEEN
                         :WS-CATEGORY-SQ-MIN
                     AND :WS-CATEGORY-SQ-MAX

               ORDER BY DPM07.CTGRY_SQ ASC,
                        DPM08.TERM_SQ  ASC
               WITH UR
           END-EXEC

           EXEC SQL
               OPEN GMC_ADEF_FV_CSR
           END-EXEC

           EVALUATE SQLCODE                                             01690000
              WHEN 0                                                    01700000
                 MOVE 'SP00'                TO LS-SP-RC
              WHEN OTHER                                                01750000
                 MOVE 'GMC_ADEF_FV_CSR'     TO WS-TABLE-NAME            01760000
                 PERFORM 9000-SQL-ERROR
           END-EVALUATE                                                 01820000
           .                                                            01460000
      *-----------------------*                                         01850000
       8880-CHECK-DEBUG-TABLE.                                          01860000
      *-----------------------*                                         01870000
                                                                        01880000
           MOVE '8880-CHECK-DEBUG-TABLE'    TO WS-PARAGRAPH-NAME        01890000
                                                                        00051700
           EXEC SQL                                                     00051800
                SELECT ACTVT_DSPLY_IN                                   00051900
                  INTO :D054-ACTVT-DSPLY-IN                             00052010
                  FROM   VDTM54_DEBUG_CNTRL                             00052040
                 WHERE PRGM_ID = :WS-PROGRAM                            00052050
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
                 PERFORM 9000-SQL-ERROR
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
      *------------*                                                    01470000
       9990-GOBACK.                                                     01480000
      *------------*                                                    01490000
           PERFORM 9995-DISPLAY-DATA                                    01500000
           PERFORM 9999-FORMAT-SQLCA                                    01510000
           GOBACK                                                       01520000
           .                                                            01530000
      *------------------*                                              01540000
       9995-DISPLAY-DATA.                                               01550000
      *------------------*                                              01560000
           IF DISPLAY-ACTIVE
              DISPLAY 'LS-SP-RC             :' LS-SP-RC                 01570000
              DISPLAY 'LS-SP-ERROR-AREA     :' LS-SP-ERROR-AREA         01580000
           END-IF
           .                                                            01590000
      *------------------*                                              01600000
       9999-FORMAT-SQLCA.                                               01610000
      *------------------*                                              01620000
           PERFORM DB2000I-FORMAT-SQLCA                                 01630000
              THRU DB2000I-FORMAT-SQLCA-EXIT                            01640000
                                                                        01420000
           IF DISPLAY-ACTIVE
              EXEC SQL
                   SET :WS-TIMESTAMP = CURRENT TIMESTAMP
              END-EXEC
              DISPLAY "PROGRAM DPMXMGMC - END"
              DISPLAY "ENDING TIME " WS-TIMESTAMP
           END-IF
           .                                                            01660000
                                                                        01670000
      **MOVE STATEMENTS TO FORMAT THE OUTSQLCA USING DB2000IA & DB2000IB01680000
        COPY DB2000IC.                                                  01690000
