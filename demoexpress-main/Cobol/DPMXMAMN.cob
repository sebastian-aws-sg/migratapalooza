       IDENTIFICATION DIVISION.
       PROGRAM-ID.    DPMXMAMN.
       AUTHOR.        COGNIZANT.

      ******************************************************************
      *THIS IS AN UNPUBLISHED PROGRAM OWNED BY ISCC                    *
      *IN WHICH A COPYRIGHT SUBSISTS AS OF JULY 2006.                  *
      *                                                                *
      ******************************************************************
      **LNKCTL**
      *    INCLUDE SYSLIB(DSNRLI)
      *    MODE AMODE(31) RMODE(ANY)
      *    ENTRY DPMXMAMN
      *    NAME  DPMXMAMN(R)
      *
      ******************************************************************
      **         P R O G R A M   D O C U M E N T A T I O N            **
      ******************************************************************
      * SYSTEM:    DERIV/SERV MCA XPRESS                               *
      * PROGRAM:   DPMXMAMN                                            *
      *                                                                *
      * THIS STORED PROCEDURE USED TO POST AN AMENDMENT FOR A TEMPLATE *
      * CATEGORY TERM.                                                 *
      ******************************************************************
      * TABLES:                                                        *
      * -------                                                        *
      * D0006          - MASTER TABLE CONTAINS TEMPLATE     *
      *                             DETAILS INCLUSIVE OF ISDA /        *
      *                             CUSTOMIZED TEMPLATE                *
      * VDPM15_TMPLT_WORK         - WORK TABLE CONTAINS TEMPLATE       *
      *                             DETAILS                            *
      * VDPM07_MCA_CTGRY          - THIS TABLE CONTAINS THE CATEGORY   *
      *                             INFORMATION FOR A TEMPLATE         *
      * VDPM08_MCA_TERMS          - THIS TABLE CONTAINS THE TERM       *00300000
      *                             INFORMATION FOR A TEMPLATE         *00310000
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
      * VDPM13_MCA_TEXT           - THIS TABLE CONTAINS THE TEXT VALUE *00300000
      *                             DETAILS FOR AMENDMENT              *00310000
      * VDPM12_MCA_DOC            - THIS TABLE CONTAINS THE DOCUMENT   *00300000
      *                             DETAILS FOR AMENDMENT              *00310000
      * VDPM11_MCA_CMNT           - THIS TABLE CONTAINS THE COMMENT    *00300000
      *                             DETAILS FOR AMENDMENT              *00310000
      * VDTM54_DEBUG_CNTRL        - DEBUG CONTROL TABLE                *00300000
      *----------------------------------------------------------------*
      *----------------------------------------------------------------*00350000
      * INCLUDES:                                                      *00360000
      * ---------                                                      *00370000
      * SQLCA    - DB2 COMMAREA                                        *00380000
      * DPM0701  - DCLGEN COPYBOOK FOR VDPM07_MCA_CTGRY TABLE          *00380000
      * DPM0801  - DCLGEN COPYBOOK FOR VDPM08_MCA_TERMS TABLE          *00380000
      * DPM1101  - DCLGEN COPYBOOK FOR VDPM11_MCA_CMNT  TABLE          *00380000
      * DPM1201  - DCLGEN COPYBOOK FOR VDPM12_MCA_DOC   TABLE          *00380000
      * DPM1301  - DCLGEN COPYBOOK FOR VDPM13_MCA_TEXT  TABLE          *00380000
      * DPM1401  - DCLGEN COPYBOOK FOR D0006 TABLE          *00380000
      * DPM1601  - DCLGEN COPYBOOK FOR VDPM16_MCA_AMND  TABLE          *00380000
      * DPM1701  - DCLGEN COPYBOOK FOR VDPM17_AMND_WORK TABLE          *00380000
      * DPM1801  - DCLGEN COPYBOOK FOR VDPM18_MCA_LINK  TABLE          *00380000
      * DPM1901  - DCLGEN COPYBOOK FOR VDPM19_LINK_WORK TABLE          *00380000
      * DTM5401  - DCLGEN FOR DISPLAY CONTROL TABLE                    *00380000
      *----------------------------------------------------------------*00610000
      * COPYBOOKS:                                                     *00580000
      * ---------                                                      *00590000
      * DB2000IA - DB2 STANDARD COPYBOOK WITH FORMATTED DISPLAY SQLCA  *00600000
      * DB2000IB - COPYBOOK CONTAINING PICTURE CLAUSE FOR SQLCA        *00600000
      * DB2000IC - COPYBOOK CONTAINING MOVE STATEMENTS TO FORMAT SQLCA *00600000
      *---------------------------------------------------------------- 00390000
      *----------------------------------------------------------------
      *              M A I N T E N A N C E   H I S T O R Y            *
      *                                                               *
      *                                                               *
      * DATE CHANGED    VERSION     PROGRAMMER                        *
      * ------------    -------     --------------------              *
      *                                                               *
      * 09/06/2007        00.00     COGNIZANT                         *
      * INITIAL IMPLEMENTATION                                        *
      *                                                               *
      *****************************************************************
       ENVIRONMENT DIVISION.
       DATA DIVISION.
       WORKING-STORAGE SECTION.
       01  WS-PROGRAM                      PIC X(08) VALUE 'DPMXMAMN'.
       01  WS-UPL-PROGRAM                  PIC X(08) VALUE 'DPMXDUPL'.
       01  WS-VARIABLES.
             05  WS-TEMPLATE-ID            PIC S9(09) COMP
                                                     VALUE ZEROES.
             05  WS-TEMPLATE-TYPE          PIC X(01) VALUE SPACES.
                 88 ISDA-TMPLT             VALUE 'I'.
                 88 DEALER-CUSTOMIZED      VALUE 'D'.
                 88 CLIENT-CUSTOMIZED      VALUE 'C'.
                 88 EXECUTED-TMPLT         VALUE 'E'.
                 88 WORKING-TMPLT          VALUE 'W'.
                 88 REEXEC-WORKING         VALUE 'R'.
             05  WS-CATEGORY-ID            PIC X(08) VALUE SPACES.
             05  WS-CATEGORY-SQ            PIC S9(04) COMP
                                                     VALUE ZEROES.
             05  WS-TERM-ID                PIC X(08) VALUE SPACES.
             05  WS-TERM-SQ                PIC S9(04) COMP
                                                     VALUE ZEROES.
             05  WS-USER-ID                PIC X(10) VALUE SPACES.
             05  WS-AMEND-IND              PIC X(01) VALUE SPACES.
             05  WS-VALUE-IND              PIC X(01) VALUE SPACES.
                 88 TEXT-INSERT            VALUE 'T'.
                 88 DOC-INSERT             VALUE 'D'.
                 88 COMMENT-INSERT         VALUE 'C'.
             05  WS-DOC-DEL-IND            PIC X(01) VALUE SPACES.
                 88 DELETE-DOC             VALUE 'D'.
                 88 NOT-DELETE-DOC         VALUE 'N'.
             05  WS-VALUE.
                 49 WS-TEXT-LEN            PIC S9(04) COMP.
                 49 WS-TEXT-VAL            PIC X(31998).
             05  WS-AMND-STAT-CD           PIC X(01) VALUE SPACES.
                 88 AGREE-AMDNT            VALUE 'A'.
                 88 POST-AMND              VALUE 'P'.
             05  WS-FUNC-FLAG              PIC X(01) VALUE SPACES.
                 88 MCA-WIZARD             VALUE 'M'.
                 88 ADMIN                  VALUE 'A'.
             05  WS-CRT-FLAG               PIC X(01) VALUE SPACES.
                 88 CREATE                 VALUE 'Y'.
                 88 NOT-CREATE             VALUE 'N'.
             05  WS-CRT-W-FLAG             PIC X(01) VALUE SPACES.
                 88 CREATE-WORK            VALUE 'C'.
                 88 NOT-CREATE-WORK        VALUE 'N'.
             05  WS-OUT-TEMPLATE-ID        PIC S9(9) COMP
                                                     VALUE ZEROES.
             05  WS-OUT-TEMPLATE-TYPE      PIC X(01) VALUE SPACES.
             05  WS-OUT-VALUE-ID           PIC S9(18) COMP-3
                                                     VALUE ZEROES.
             05  WS-VALUE-ID               PIC S9(18) COMP-3
                                                     VALUE ZEROES.
             05  WS-CURRENT-TS             PIC X(26) VALUE SPACES.
             05  WS-VALUE-FLAG             PIC X(01) VALUE 'Y'.
                 88 AMND-EXISTING          VALUE 'Y'.
                 88 AMND-NEW               VALUE 'N'.
             05  WS-AMND-ID                PIC S9(18) COMP-3
                                           VALUE ZEROES.
             05  WS-CHECK-AMND-ID          PIC S9(18) COMP-3
                                           VALUE ZEROES.
             05  WS-EOF-RECORD-VALUE       PIC X(1)  VALUE 'N'.
                 88 EOF-RECORD             VALUE 'Y'.
                 88 NOT-EOF-RECORD         VALUE 'N'.
             05  WS-EOF-LINK-RECORD-VALUE  PIC X(1)  VALUE 'N'.
                 88 EOF-LINK-RECORD        VALUE 'Y'.
                 88 NOT-EOF-LINK-RECORD    VALUE 'N'.
             05  WS-TEMP-SEQUENCE-NO       PIC S9(9) COMP
                                           VALUE ZEROES.
             05  WS-AMNDT-SEQUENCE-NO      PIC S9(18) COMP-3
                                           VALUE ZEROES.
             05  WS-TDC-SEQUENCE-NO        PIC S9(18) COMP-3
                                           VALUE ZEROES.
                                                                        00850000
             05  WS-DISPLAY-CONTROL-FLAG   PIC X(001) VALUE SPACES.     01190000
                 88 DISPLAY-ACTIVE         VALUE 'Y'.
                 88 DISPLAY-INACTIVE       VALUE 'N'.
                                                                        00850000
       01  WS-ERROR-MSG.                                                00860000
             05  WS-INVALID-TMPLT-TYPE     PIC X(50)                    00560100
                 VALUE 'INVALID TEMPLATE TYPE'.                         00560100
             05  WS-INVALID-FUNC-FLAG      PIC X(50)                    00560100
                 VALUE 'INVALID FUNCTIONAL FLAG'.                       00560100
             05  WS-INVALID-AMND-ID        PIC X(50)                    00560100
                 VALUE 'INVALID AMENDMENT ID'.                          00560100
             05  WS-INVALID-CTGRY-TERM     PIC X(50)                    00560100
                 VALUE 'INVALID CATEGORY AND TERM DETAILS PASSED'.      00560100
             05  WS-DATABASE-ERROR         PIC X(50)                    00560100
                 VALUE 'DATABASE ERROR OCCURRED. PLEASE CONTACT DTCC'.  00560100

       01 WS-PASS-AREA.
             05 WS-IN-CMPNY-ID             PIC X(8).
             05 WS-IN-MCA-TMPLT-ID         PIC S9(09) COMP.
             05 WS-IN-MCA-DOC-DS           PIC X(216).
             05 WS-IN-DOC-TYPE-CD          PIC X(1).
             05 WS-IN-ROW-UPDT-USER-ID     PIC X(10).
             05 WS-IN-DOC-OBJ-TX           USAGE IS SQL
                                           TYPE IS BLOB(2097152).
             05 WS-O-DOC-ID                PIC S9(18)V COMP-3.
             05 WS-OUT-SQLCODE             PIC +(9)9.

       01  WS-DOCUMENT  USAGE IS SQL TYPE IS BLOB(2097152).

       01  WS-ERROR-AREA.
             05  WS-PARAGRAPH-NAME         PIC X(40).
             05  FILLER                    PIC X VALUE ','.
             05  WS-TABLE-NAME             PIC X(18).
             05  WS-SQLCODE                PIC 9(7).

      *****************************************************************
      *                        SQL INCLUDES                            *
      ******************************************************************
           EXEC SQL
                INCLUDE SQLCA
           END-EXEC.

           EXEC SQL
                INCLUDE DPM0701
           END-EXEC.

           EXEC SQL
                INCLUDE DPM0801
           END-EXEC.

           EXEC SQL
                INCLUDE DPM1101
           END-EXEC.

           EXEC SQL
                INCLUDE DPM1201
           END-EXEC.

           EXEC SQL
                INCLUDE DPM1301
           END-EXEC.

           EXEC SQL
                INCLUDE DPM1401
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
       01  LS-TEMPLATE-TYPE                PIC X(01).
       01  LS-CATEGORY-ID                  PIC X(08).
       01  LS-CATEGORY-SQ                  PIC S9(04) COMP.
       01  LS-TERM-ID                      PIC X(08).
       01  LS-TERM-SQ                      PIC S9(04) COMP.
       01  LS-USER-ID                      PIC X(10).
       01  LS-AMND-STAT-CD                 PIC X(01).
       01  LS-FUNC-FLAG                    PIC X(01).
       01  LS-VALUE-IND                    PIC X(01).
       01  LS-VALUE.
           49 LS-TEXT-LEN                  PIC S9(04) COMP.
           49 LS-TEXT-VAL                  PIC X(31998).
       01  LS-AMND-ID                      PIC S9(18) COMP-3.
       01  LS-VALUE-ID                     PIC S9(18) COMP-3.
       01  LS-DOC-DEL-IND                  PIC X(01).
       01  LS-DOCUMENT USAGE IS SQL TYPE IS BLOB(2097152).

       PROCEDURE DIVISION USING  OUTSQLCA,
                                 LS-SP-ERROR-AREA,
                                 LS-SP-RC,
                                 LS-TEMPLATE-ID,
                                 LS-TEMPLATE-TYPE,
                                 LS-CATEGORY-ID,
                                 LS-CATEGORY-SQ,
                                 LS-TERM-ID,
                                 LS-TERM-SQ,
                                 LS-USER-ID,
                                 LS-AMND-STAT-CD,
                                 LS-FUNC-FLAG,
                                 LS-VALUE-IND,
                                 LS-VALUE,
                                 LS-AMND-ID,
                                 LS-VALUE-ID,
                                 LS-DOC-DEL-IND,
                                 LS-DOCUMENT.

      *---------*
       1000-MAIN.
      *---------*

           PERFORM 1000-INIT-AND-CHECK-PARMS

           PERFORM 2000-UPDATE-AMNDT-VALUE

           PERFORM 9990-GOBACK
           .
      *-------------------------*
       1000-INIT-AND-CHECK-PARMS.
      *-------------------------*

           MOVE '1000-INIT-AND-CHECK-PARMS' TO WS-PARAGRAPH-NAME

           MOVE SPACES                      TO LS-SP-ERROR-AREA
                                               LS-SP-RC
           INITIALIZE WS-VARIABLES
                      DCLVDPM07-MCA-CTGRY
                      DCLVDPM08-MCA-TERMS
                      DCLVDPM11-MCA-CMNT
                      DCLVDPM12-MCA-DOC
                      DCLVDPM13-MCA-TEXT
                      DCLVDPM14-MCA-TMPLT
                      DCLVDPM16-MCA-AMND
                      DCLVDPM17-AMND-WORK
                      DCLVDPM18-MCA-LINK
                      DCLVDPM19-LINK-WORK
                      WS-DOCUMENT

           PERFORM 9520-CHECK-DEBUG-TABLE

           MOVE LS-TEMPLATE-ID              TO WS-TEMPLATE-ID
           MOVE LS-TEMPLATE-TYPE            TO WS-TEMPLATE-TYPE
           MOVE LS-CATEGORY-ID              TO WS-CATEGORY-ID
           MOVE LS-CATEGORY-SQ              TO WS-CATEGORY-SQ
           MOVE LS-TERM-ID                  TO WS-TERM-ID
           MOVE LS-TERM-SQ                  TO WS-TERM-SQ
           MOVE LS-USER-ID                  TO WS-USER-ID
           MOVE LS-AMND-ID                  TO WS-AMND-ID
           MOVE LS-AMND-STAT-CD             TO WS-AMND-STAT-CD
           MOVE LS-FUNC-FLAG                TO WS-FUNC-FLAG
           MOVE LS-VALUE-IND                TO WS-VALUE-IND
           MOVE LS-VALUE                    TO WS-VALUE
           MOVE LS-DOC-DEL-IND              TO WS-DOC-DEL-IND
           MOVE LS-DOCUMENT-LENGTH          TO WS-DOCUMENT-LENGTH
           MOVE LS-DOCUMENT-DATA(1:LS-DOCUMENT-LENGTH)
                                            TO WS-DOCUMENT-DATA

           MOVE LS-TEMPLATE-ID              TO WS-OUT-TEMPLATE-ID
           MOVE LS-TEMPLATE-TYPE            TO WS-OUT-TEMPLATE-TYPE
           MOVE LS-VALUE-ID                 TO WS-VALUE-ID

           IF DISPLAY-ACTIVE

              EXEC SQL
                   SET :WS-CURRENT-TS = CURRENT TIMESTAMP
              END-EXEC

              DISPLAY 'PROGRAM DPMXMAMN STARTING ' WS-CURRENT-TS
              DISPLAY "WS-TEMPLATE-ID "   WS-TEMPLATE-ID
              DISPLAY "WS-TEMPLATE-TYPE " WS-TEMPLATE-TYPE
              DISPLAY "WS-CATEGORY-ID "   WS-CATEGORY-ID
              DISPLAY "WS-CATEGORY-SQ "   WS-CATEGORY-SQ
              DISPLAY "WS-TERM-ID "       WS-TERM-ID
              DISPLAY "WS-TERM-SQ "       WS-TERM-SQ
              DISPLAY "WS-USER-ID "       WS-USER-ID
              DISPLAY "WS-AMND-ID "       WS-AMND-ID
              DISPLAY "WS-AMND-STAT-CD "  WS-AMND-STAT-CD
              DISPLAY "WS-FUNC-FLAG "     WS-FUNC-FLAG
              DISPLAY "WS-VALUE-IND "     WS-VALUE-IND
              DISPLAY "WS-TEXT-LENGTH "   WS-TEXT-LEN
              DISPLAY "WS-VALUE-ID  "     WS-VALUE-ID
              DISPLAY "WS-DOC-DEL-IND "   WS-DOC-DEL-IND
           END-IF
           .
      *------------------------*
       2000-UPDATE-AMNDT-VALUE.
      *------------------------*

           MOVE '2000-UPDATE-AMNDT-VALUE'   TO WS-PARAGRAPH-NAME

           IF DISPLAY-ACTIVE
              DISPLAY "WS-FUNC-FLAG " WS-FUNC-FLAG
              DISPLAY WS-PARAGRAPH-NAME
           END-IF

           EVALUATE TRUE
               WHEN MCA-WIZARD
                 PERFORM 2100-CHECK-TEMPLATE-TYPE
               WHEN ADMIN
                 PERFORM 3000-CREATE-ADMIN-TEMPLATE
               WHEN OTHER
                 MOVE  WS-INVALID-FUNC-FLAG TO LS-SP-ERROR-AREA
                 MOVE  'SP50'               TO LS-SP-RC
                 PERFORM 9990-GOBACK
           END-EVALUATE
           .
      *-------------------------*
       2100-CHECK-TEMPLATE-TYPE.
      *-------------------------*

           MOVE '2100-CHECK-TEMPLATE-TYPE'  TO WS-PARAGRAPH-NAME

           IF DISPLAY-ACTIVE
              DISPLAY "WS-TEMPLATE-TYPE " WS-TEMPLATE-TYPE
              DISPLAY WS-PARAGRAPH-NAME
           END-IF

           EVALUATE TRUE
              WHEN ISDA-TMPLT
              WHEN CLIENT-CUSTOMIZED
              WHEN EXECUTED-TMPLT
                PERFORM 2200-CREATE-NEW-TEMPLATE
              WHEN DEALER-CUSTOMIZED
                PERFORM 2250-UPDATE-VALUES
              WHEN WORKING-TMPLT
              WHEN REEXEC-WORKING
                PERFORM 2300-UPDT-EXIST-DC-TEMPLATE
              WHEN OTHER
                 MOVE  WS-INVALID-TMPLT-TYPE
                                            TO LS-SP-ERROR-AREA
                 MOVE  'SP50'               TO LS-SP-RC
                 PERFORM 9990-GOBACK
           END-EVALUATE
           .
      *-------------------------*
       2200-CREATE-NEW-TEMPLATE.
      *-------------------------*

           MOVE '2200-CREATE-NEW-TEMPLATE'  TO WS-PARAGRAPH-NAME

           PERFORM 2205-CHECK-INPUT-TEMPLATE

           IF DISPLAY-ACTIVE
              DISPLAY "WS-CRT-FLAG " WS-CRT-FLAG
              DISPLAY WS-PARAGRAPH-NAME
           END-IF

           IF CREATE
              PERFORM 2210-GET-TEMPLATE-DETAILS

              PERFORM 2220-GET-NEXT-TEMPLATE-SEQ

              PERFORM 2230-INSERT-NEW-TEMPLATE

              PERFORM 2240-CREATE-STATIC-GRID
           END-IF

           PERFORM 2250-UPDATE-VALUES

           .
      *-------------------------*
       2205-CHECK-INPUT-TEMPLATE.
      *-------------------------*

           MOVE '2205-CHECK-INPUT-TEMPLATE' TO WS-PARAGRAPH-NAME

           IF DISPLAY-ACTIVE
              DISPLAY WS-PARAGRAPH-NAME
           END-IF

           EXEC SQL
                SELECT MCA_TMPLT_ID
                  INTO :D014-MCA-TMPLT-ID
                  FROM D0006
                 WHERE MCA_TMPLT_ID = :WS-TEMPLATE-ID
                WITH UR
           END-EXEC

           EVALUATE SQLCODE
              WHEN 0
                 SET CREATE                 TO TRUE
              WHEN 100
                 SET NOT-CREATE             TO TRUE
              WHEN OTHER
                 MOVE 'TDPM14_MCA_TMPLT'    TO WS-TABLE-NAME
                 PERFORM 9550-SQL-ERROR
           END-EVALUATE

           .
      *-------------------------*
       2210-GET-TEMPLATE-DETAILS.
      *-------------------------*

           MOVE '2210-GET-TEMPLATE-DETAILS' TO WS-PARAGRAPH-NAME

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
                 MOVE 'TDPM14_MCA_TMPLT'    TO WS-TABLE-NAME
                 PERFORM 9550-SQL-ERROR
           END-EVALUATE

           .
      *--------------------------*
       2220-GET-NEXT-TEMPLATE-SEQ.
      *--------------------------*

           MOVE '2220-GET-NEXT-TEMPLATE-SEQ'
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
                 PERFORM 9550-SQL-ERROR
           END-EVALUATE
           .
      *------------------------*
       2230-INSERT-NEW-TEMPLATE.
      *------------------------*

           MOVE '2230-INSERT-NEW-TEMPLATE'  TO WS-PARAGRAPH-NAME

           IF DISPLAY-ACTIVE
              DISPLAY WS-PARAGRAPH-NAME
           END-IF

           MOVE WS-TEMP-SEQUENCE-NO         TO D014-MCA-TMPLT-ID
                                               WS-OUT-TEMPLATE-ID

           MOVE WS-USER-ID                  TO D014-ROW-UPDT-USER-ID

           MOVE 'D'                         TO D014-MCA-TMPLT-TYPE-CD
                                               WS-OUT-TEMPLATE-TYPE

           MOVE WS-TEMPLATE-ID              TO D014-MCA-CSTMZ-TMPLT-ID

           EXEC SQL
                INSERT INTO TDPM15_TMPLT_WORK
                         (MCA_TMPLT_ID
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
                         ,ROW_UPDT_USER_ID)
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
                 MOVE 'TDPM15_TMPLT_WORK'   TO WS-TABLE-NAME
                 PERFORM 9550-SQL-ERROR
           END-EVALUATE
           .
      *------------------------*
       2240-CREATE-STATIC-GRID.
      *------------------------*

           MOVE '2240-CREATE-STATIC-GRID'   TO WS-PARAGRAPH-NAME

           IF DISPLAY-ACTIVE
              DISPLAY WS-PARAGRAPH-NAME
           END-IF

           PERFORM 9200-COPY-CATEGORY-DETAILS

           PERFORM 9300-COPY-TERM-DETAILS

           PERFORM 2243-COPY-AMNDT-DETAILS
           .
      *------------------------*
       2243-COPY-AMNDT-DETAILS.
      *------------------------*

           MOVE '2243-COPY-AMNDT-DETAILS'   TO WS-PARAGRAPH-NAME

           IF DISPLAY-ACTIVE
              DISPLAY WS-PARAGRAPH-NAME
           END-IF

           SET NOT-EOF-RECORD               TO TRUE

           PERFORM 2243A-DECLARE-AMNDT-CURSOR

           PERFORM 2243B-OPEN-AMNDT-CURSOR

           PERFORM UNTIL EOF-RECORD

              PERFORM 2243C-FETCH-AMNDT-CURSOR

              EVALUATE SQLCODE
                  WHEN 0
                    MOVE D016-MCA-AMND-ID   TO D018-MCA-AMND-ID
                    PERFORM 2243D-GET-NEXT-AMNDT-SEQ
                    PERFORM 2243E-INSERT-AMNDT
                    PERFORM 2243F-GET-LINK-DETAILS
                  WHEN 100
                    SET EOF-RECORD          TO TRUE
                    PERFORM 2243G-CLOSE-AMNDT-CURSOR
                  WHEN OTHER
                    MOVE 'TDPM16_MCA_AMND'  TO WS-TABLE-NAME
                    PERFORM 9550-SQL-ERROR
              END-EVALUATE
           END-PERFORM
           .
      *---------------------------*
       2243A-DECLARE-AMNDT-CURSOR.
      *---------------------------*

           MOVE '2243A-DECLARE-AMNDT-CURSOR'
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
       2243B-OPEN-AMNDT-CURSOR.
      *------------------------*

           MOVE '2243B-OPEN-AMNDT-CURSOR'   TO WS-PARAGRAPH-NAME

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
                 PERFORM 9550-SQL-ERROR
           END-EVALUATE
           .
      *--------------------------*
       2243C-FETCH-AMNDT-CURSOR.
      *--------------------------*

           MOVE '2243C-FETCH-AMNDT-CURSOR'  TO WS-PARAGRAPH-NAME

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
       2243D-GET-NEXT-AMNDT-SEQ.
      *--------------------------*

           MOVE '2243D-GET-NEXT-AMNDT-SEQ'  TO WS-PARAGRAPH-NAME

           IF DISPLAY-ACTIVE
              DISPLAY WS-PARAGRAPH-NAME
           END-IF

           INITIALIZE WS-AMNDT-SEQUENCE-NO

           EXEC SQL
                SET :WS-AMNDT-SEQUENCE-NO = NEXT VALUE FOR DPM.SQDPM171
           END-EXEC

           EVALUATE SQLCODE
              WHEN 0
                 MOVE 'SP00'                TO LS-SP-RC

                 IF DISPLAY-ACTIVE
                    DISPLAY "WS-AMNDT-SEQUENCE-NO" WS-AMNDT-SEQUENCE-NO
                 END-IF

              WHEN OTHER
                 MOVE 'AMNDT_SEQ'           TO WS-TABLE-NAME
                 PERFORM 9550-SQL-ERROR
           END-EVALUATE
           .
      *---------------------*
       2243E-INSERT-AMNDT.
      *---------------------*

           MOVE '2243E-INSERT-AMNDT'        TO WS-PARAGRAPH-NAME

           IF DISPLAY-ACTIVE
              DISPLAY WS-PARAGRAPH-NAME
           END-IF

           MOVE WS-AMNDT-SEQUENCE-NO        TO D016-MCA-AMND-ID

           MOVE WS-TEMP-SEQUENCE-NO         TO D016-MCA-TMPLT-ID

           MOVE WS-USER-ID                  TO D016-ROW-UPDT-USER-ID

           EXEC SQL
                INSERT INTO TDPM17_AMND_WORK
                              (MCA_AMND_ID
                              ,MCA_TMPLT_ID
                              ,ATTRB_CTGRY_ID
                              ,CTGRY_SQ
                              ,ATTRB_TERM_ID
                              ,TERM_SQ
                              ,MCA_ISDA_AMND_ID
                              ,ROW_UPDT_TS
                              ,ROW_UPDT_USER_ID)
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

           EVALUATE SQLCODE
              WHEN 0
                 MOVE 'SP00'                TO LS-SP-RC
              WHEN OTHER
                 MOVE 'VDPM17_AMND_WORK'    TO WS-TABLE-NAME
                 PERFORM 9550-SQL-ERROR
           END-EVALUATE
           .
      *-----------------------*
       2243F-GET-LINK-DETAILS.
      *-----------------------*

           MOVE '2243F-GET-LINK-DETAILS'    TO WS-PARAGRAPH-NAME

           IF DISPLAY-ACTIVE
              DISPLAY WS-PARAGRAPH-NAME
           END-IF

           SET NOT-EOF-LINK-RECORD          TO TRUE

           PERFORM 2243FA-DECLARE-LINK-CURSOR

           PERFORM 2243FB-OPEN-LINK-CURSOR

           PERFORM UNTIL EOF-LINK-RECORD

              PERFORM 2243FC-FETCH-LINK-CURSOR

              EVALUATE SQLCODE
                  WHEN 0
                    PERFORM 2243FD-INSERT-LINK-WRK
                  WHEN 100
                    SET EOF-LINK-RECORD     TO TRUE
                    PERFORM 2243FE-CLOSE-LINK-CURSOR
                  WHEN OTHER
                    MOVE 'TDPM16_MCA_AMND'  TO WS-TABLE-NAME
                    PERFORM 9550-SQL-ERROR
              END-EVALUATE
           END-PERFORM
           .
      *---------------------------*
       2243FA-DECLARE-LINK-CURSOR.
      *---------------------------*

           MOVE '2243FA-DECLARE-LINK-CURSOR'
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
                    AND MCA_VALUE_TYPE_CD <> 'C'
                 WITH UR
           END-EXEC
           .
      *------------------------*
       2243FB-OPEN-LINK-CURSOR.
      *------------------------*

           MOVE '2243FB-OPEN-LINK-CURSOR'   TO WS-PARAGRAPH-NAME

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
                 PERFORM 9550-SQL-ERROR
           END-EVALUATE
           .
      *--------------------------*
       2243FC-FETCH-LINK-CURSOR.
      *--------------------------*

           MOVE '2243FC-FETCH-LINK-CURSOR'  TO WS-PARAGRAPH-NAME

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
       2243FD-INSERT-LINK-WRK.
      *----------------------*

           MOVE '2243FD-INSERT-LINK-WRK'    TO WS-PARAGRAPH-NAME

           IF DISPLAY-ACTIVE
              DISPLAY WS-PARAGRAPH-NAME
           END-IF

           MOVE WS-AMNDT-SEQUENCE-NO        TO D018-MCA-AMND-ID


           MOVE 'U'                         TO D019-MCA-ACCS-STAT-CD
           IF D018-AMND-STAT-CD = 'A'
              MOVE 'P'                      TO D018-AMND-STAT-CD
           END-IF

           MOVE WS-USER-ID                  TO D018-ROW-UPDT-USER-ID

           EXEC SQL
                INSERT INTO TDPM19_LINK_WORK
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

           EVALUATE SQLCODE
              WHEN 0
                 MOVE 'SP00'                TO LS-SP-RC
              WHEN OTHER
                 MOVE 'VDPM19_LINK_WORK'    TO WS-TABLE-NAME
                 PERFORM 9550-SQL-ERROR
           END-EVALUATE
           .
      *--------------------------*
       2243FE-CLOSE-LINK-CURSOR.
      *--------------------------*

           MOVE '2243FE-CLOSE-LINK-CURSOR'  TO WS-PARAGRAPH-NAME

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
                 PERFORM 9550-SQL-ERROR
           END-EVALUATE
           .
      *--------------------------*
       2243G-CLOSE-AMNDT-CURSOR.
      *--------------------------*

           MOVE '2243G-CLOSE-AMNDT-CURSOR'  TO WS-PARAGRAPH-NAME

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
                 PERFORM 9550-SQL-ERROR
           END-EVALUATE
           .
      *-------------------*
       2250-UPDATE-VALUES.
      *-------------------*

           MOVE '2250-UPDATE-VALUES'        TO WS-PARAGRAPH-NAME

           IF WS-CRT-FLAG = 'Y'
              MOVE WS-TEMP-SEQUENCE-NO      TO WS-TEMPLATE-ID
           END-IF

           IF DISPLAY-ACTIVE
              DISPLAY WS-PARAGRAPH-NAME
              DISPLAY 'WS-TEMPLATE-ID '   WS-TEMPLATE-ID
              DISPLAY 'WS-CATEGORY-ID '   WS-CATEGORY-ID
              DISPLAY 'WS-CATEGORY-SQ '   WS-CATEGORY-SQ
              DISPLAY 'WS-TERM-ID '       WS-TERM-ID
              DISPLAY 'WS-TERM-SQ '       WS-TERM-SQ
           END-IF

           EXEC SQL
                SELECT MCA_AMND_ID
                  INTO :D019-MCA-AMND-ID
                  FROM VDPM17_AMND_WORK
                 WHERE MCA_TMPLT_ID   = :WS-TEMPLATE-ID
                   AND ATTRB_CTGRY_ID = :WS-CATEGORY-ID
                   AND CTGRY_SQ       = :WS-CATEGORY-SQ
                   AND ATTRB_TERM_ID  = :WS-TERM-ID
                   AND TERM_SQ        = :WS-TERM-SQ
                 WITH UR
           END-EXEC

           EVALUATE SQLCODE
              WHEN 0
                 MOVE 'SP00'                TO LS-SP-RC
                 MOVE D019-MCA-AMND-ID      TO WS-AMND-ID
                 SET AMND-EXISTING          TO TRUE
              WHEN 100
                 PERFORM 2301-CHECK-LINK-WORK
                 IF CREATE-WORK
                    PERFORM 9500-GET-LINK-DETAILS
                 END-IF
              WHEN OTHER
                 MOVE 'VDPM17_AMND_WORK'    TO WS-TABLE-NAME
                 PERFORM 9550-SQL-ERROR
           END-EVALUATE

           PERFORM 9000-INSERT-VALUES
           .
      *---------------------------*
       2300-UPDT-EXIST-DC-TEMPLATE.
      *---------------------------*

           IF DISPLAY-ACTIVE
              DISPLAY "2300-UPDT-EXIST-DC-TEMPLATE"
           END-IF

           IF AGREE-AMDNT
              PERFORM 2300A-GET-TMPLT-DETAILS
           END-IF

           PERFORM 2301-CHECK-LINK-WORK

           IF CREATE-WORK
              PERFORM 9500-GET-LINK-DETAILS
           END-IF

           IF AGREE-AMDNT
              PERFORM 2310-UPDATE-AMND-STATUS
           ELSE
              SET AMND-EXISTING                TO TRUE
              PERFORM 9000-INSERT-VALUES
           END-IF

           .
      *-----------------------*
       2300A-GET-TMPLT-DETAILS.
      *-----------------------*

           MOVE '2300A-GET-TMPLT-DETAILS'   TO WS-PARAGRAPH-NAME
           MOVE WS-AMND-ID                  TO D019-MCA-AMND-ID

           EXEC SQL
                SELECT  MCA_TMPLT_ID
                       ,ATTRB_CTGRY_ID
                       ,CTGRY_SQ
                       ,ATTRB_TERM_ID
                       ,TERM_SQ
                  INTO :WS-TEMPLATE-ID
                      ,:WS-CATEGORY-ID
                      ,:WS-CATEGORY-SQ
                      ,:WS-TERM-ID
                      ,:WS-TERM-SQ
                  FROM VDPM16_MCA_AMND
                 WHERE MCA_AMND_ID = :D019-MCA-AMND-ID
           END-EXEC

           EVALUATE SQLCODE
              WHEN 0
                 MOVE 'SP00'                TO LS-SP-RC
              WHEN OTHER
                 MOVE 'VDPM16_MCA_AMND'     TO WS-TABLE-NAME
                 PERFORM 9550-SQL-ERROR
           END-EVALUATE
           .
      *---------------------*
       2301-CHECK-LINK-WORK.
      *---------------------*

           MOVE '2301-CHECK-LINK-WORK'      TO WS-PARAGRAPH-NAME

           EXEC SQL
                SELECT DPM19.MCA_AMND_ID
                      ,DPM19.AMND_STAT_CD
                  INTO :D019-MCA-AMND-ID
                      ,:D018-AMND-STAT-CD
                  FROM VDPM19_LINK_WORK DPM19
                      ,VDPM16_MCA_AMND  DPM16
                 WHERE DPM16.MCA_AMND_ID    = DPM19.MCA_AMND_ID
                   AND DPM16.MCA_TMPLT_ID   = :WS-TEMPLATE-ID
                   AND DPM16.ATTRB_CTGRY_ID = :WS-CATEGORY-ID
                   AND DPM16.CTGRY_SQ       = :WS-CATEGORY-SQ
                   AND DPM16.ATTRB_TERM_ID  = :WS-TERM-ID
                   AND DPM16.TERM_SQ        = :WS-TERM-SQ
                 ORDER BY DPM19.AMND_STAT_CD ASC
                 FETCH FIRST ROW ONLY
           END-EXEC

           EVALUATE SQLCODE
              WHEN 0
                 MOVE 'SP00'                TO LS-SP-RC
                 MOVE D019-MCA-AMND-ID      TO WS-AMND-ID
                 SET NOT-CREATE-WORK        TO TRUE
                 SET AMND-EXISTING          TO TRUE
              WHEN 100
                 SET CREATE-WORK            TO TRUE
                 IF NOT AGREE-AMDNT
                    PERFORM 2302-SELECT-AMND-ID
                 END-IF
              WHEN OTHER
                 MOVE 'VDPM17_AMND_WORK'    TO WS-TABLE-NAME
                 PERFORM 9550-SQL-ERROR
           END-EVALUATE

           .
      *--------------------*
       2302-SELECT-AMND-ID.
      *--------------------*

           MOVE '2302-SELECT-AMND-ID'       TO WS-PARAGRAPH-NAME

           EXEC SQL
                SELECT MCA_AMND_ID
                  INTO :D019-MCA-AMND-ID
                  FROM VDPM16_MCA_AMND
                 WHERE MCA_TMPLT_ID   = :WS-TEMPLATE-ID
                   AND ATTRB_CTGRY_ID = :WS-CATEGORY-ID
                   AND CTGRY_SQ       = :WS-CATEGORY-SQ
                   AND ATTRB_TERM_ID  = :WS-TERM-ID
                   AND TERM_SQ        = :WS-TERM-SQ
           END-EXEC

           EVALUATE SQLCODE
              WHEN 0
                 MOVE 'SP00'                TO LS-SP-RC
                 MOVE D019-MCA-AMND-ID      TO WS-AMND-ID
                 SET AMND-EXISTING          TO TRUE
              WHEN 100
                 MOVE 'SP50'                TO LS-SP-RC
                 MOVE  WS-INVALID-AMND-ID   TO LS-SP-ERROR-AREA
                 PERFORM 9990-GOBACK
              WHEN OTHER
                 MOVE 'VDPM17_AMND_WORK'    TO WS-TABLE-NAME
                 PERFORM 9550-SQL-ERROR
           END-EVALUATE
           .

      *------------------------*
       2310-UPDATE-AMND-STATUS.
      *------------------------*

           MOVE '2310-UPDATE-AMND-STATUS'   TO WS-PARAGRAPH-NAME

           IF DISPLAY-ACTIVE
              DISPLAY WS-PARAGRAPH-NAME
           END-IF

           MOVE WS-AMND-ID                  TO D019-MCA-AMND-ID
           MOVE WS-AMND-STAT-CD             TO D019-AMND-STAT-CD
           MOVE WS-USER-ID                  TO D019-ROW-UPDT-USER-ID

           EXEC SQL
                UPDATE TDPM19_LINK_WORK
                   SET AMND_STAT_CD     = :D019-AMND-STAT-CD
                      ,ROW_UPDT_TS      = CURRENT TIMESTAMP
                      ,ROW_UPDT_USER_ID = :D019-ROW-UPDT-USER-ID
                 WHERE MCA_AMND_ID      = :D019-MCA-AMND-ID
                   AND MCA_ACCS_STAT_CD = 'U'
           END-EXEC

           EVALUATE SQLCODE
              WHEN 0
                 MOVE 'SP00'                TO LS-SP-RC
              WHEN OTHER
                 MOVE 'VDPM19_LINK_WORK'    TO WS-TABLE-NAME
                 PERFORM 9550-SQL-ERROR
           END-EVALUATE
           .

      *---------------------------*
       3000-CREATE-ADMIN-TEMPLATE.
      *---------------------------*

           MOVE '3000-CREATE-ADMIN-TEMPLATE'
                                            TO WS-PARAGRAPH-NAME

           PERFORM 3200-CREATE-AMND-DETAILS

           .
      *------------------------*
       3200-CREATE-AMND-DETAILS.
      *------------------------*

           MOVE '3200-CREATE-AMND-DETAILS'  TO WS-PARAGRAPH-NAME

           IF DISPLAY-ACTIVE
              DISPLAY WS-PARAGRAPH-NAME
           END-IF

           PERFORM 3210-GET-AMND-ID

           MOVE D016-MCA-AMND-ID            TO WS-AMND-ID

           PERFORM 9000-INSERT-VALUES

           .
      *-----------------*
       3210-GET-AMND-ID.
      *-----------------*

           MOVE '3210-GET-AMND-ID'          TO WS-PARAGRAPH-NAME

           IF DISPLAY-ACTIVE
              DISPLAY WS-PARAGRAPH-NAME
           END-IF

           EXEC SQL
                SELECT MCA_AMND_ID
                  INTO :D016-MCA-AMND-ID
                  FROM VDPM16_MCA_AMND
                 WHERE MCA_TMPLT_ID   = :WS-TEMPLATE-ID
                   AND ATTRB_CTGRY_ID = :WS-CATEGORY-ID
                   AND CTGRY_SQ       = :WS-CATEGORY-SQ
                   AND ATTRB_TERM_ID  = :WS-TERM-ID
                   AND TERM_SQ        = :WS-TERM-SQ
                 WITH UR
           END-EXEC

           EVALUATE SQLCODE
              WHEN 0
                 MOVE 'SP00'                TO LS-SP-RC
                 MOVE D016-MCA-AMND-ID      TO D019-MCA-AMND-ID
                 SET AMND-EXISTING          TO TRUE
                 PERFORM 3210B-CHECK-LINK-WORK
              WHEN 100
                 PERFORM 3210A-CHECK-AMND-WORK
              WHEN OTHER
                 MOVE 'VDPM16_MCA_AMND'     TO WS-TABLE-NAME
                 PERFORM 9550-SQL-ERROR
           END-EVALUATE
           .

      *---------------------*
       3210A-CHECK-AMND-WORK.
      *---------------------*

           MOVE '3210A-CHECK-AMND-WORK'     TO WS-PARAGRAPH-NAME

           EXEC SQL
                SELECT MCA_AMND_ID
                  INTO :D016-MCA-AMND-ID
                  FROM VDPM17_AMND_WORK
                 WHERE MCA_TMPLT_ID   = :WS-TEMPLATE-ID
                   AND ATTRB_CTGRY_ID = :WS-CATEGORY-ID
                   AND CTGRY_SQ       = :WS-CATEGORY-SQ
                   AND ATTRB_TERM_ID  = :WS-TERM-ID
                   AND TERM_SQ        = :WS-TERM-SQ
                 WITH UR
           END-EXEC

           EVALUATE SQLCODE
              WHEN 0
                 MOVE 'SP00'                TO LS-SP-RC
                 MOVE D016-MCA-AMND-ID      TO D019-MCA-AMND-ID
                 SET AMND-EXISTING          TO TRUE
              WHEN 100
                 PERFORM 3211-CREATE-AMND-ID
                 SET AMND-NEW               TO TRUE
              WHEN OTHER
                 MOVE 'VDPM17_AMND_WORK'    TO WS-TABLE-NAME
                 PERFORM 9550-SQL-ERROR
           END-EVALUATE
           .

      *---------------------*
       3210B-CHECK-LINK-WORK.
      *---------------------*

           MOVE '3210A-CHECK-LINK-WORK'     TO WS-PARAGRAPH-NAME

           EXEC SQL
                SELECT MCA_AMND_ID
                  INTO :WS-CHECK-AMND-ID
                  FROM VDPM19_LINK_WORK
                 WHERE MCA_AMND_ID    = :D016-MCA-AMND-ID
                 FETCH FIRST ROW ONLY
                 WITH UR
           END-EXEC

           EVALUATE SQLCODE
              WHEN 0
                 CONTINUE
              WHEN 100
                 MOVE D016-MCA-AMND-ID      TO WS-AMND-ID
                 PERFORM 9500-GET-LINK-DETAILS
              WHEN OTHER
                 MOVE 'VDPM19_LINK_WORK'    TO WS-TABLE-NAME
                 PERFORM 9550-SQL-ERROR
           END-EVALUATE
           .

      *-------------------*
       3211-CREATE-AMND-ID.
      *-------------------*

           MOVE '3211-CREATE-AMND-ID'       TO WS-PARAGRAPH-NAME

           IF DISPLAY-ACTIVE
              DISPLAY WS-PARAGRAPH-NAME
           END-IF

           IF WS-TEMPLATE-ID  = ZEROES OR
              WS-CATEGORY-ID <= SPACES OR
              WS-CATEGORY-SQ  = ZEROES OR
              WS-TERM-ID     <= SPACES OR
              WS-TERM-SQ      = ZEROES
              MOVE  WS-INVALID-CTGRY-TERM   TO LS-SP-ERROR-AREA
              MOVE  'SP50'                  TO LS-SP-RC
              PERFORM 9990-GOBACK
           END-IF

           INITIALIZE WS-AMNDT-SEQUENCE-NO

           EXEC SQL
                SET :WS-AMNDT-SEQUENCE-NO = NEXT VALUE FOR DPM.SQDPM171
           END-EXEC

           EVALUATE SQLCODE
              WHEN 0
                 MOVE 'SP00'                TO LS-SP-RC

                 IF DISPLAY-ACTIVE
                    DISPLAY "WS-AMNDT-SEQUENCE-NO" WS-AMNDT-SEQUENCE-NO
                 END-IF

              WHEN OTHER
                 MOVE 'AMNDT_SEQ'           TO WS-TABLE-NAME
                 PERFORM 9550-SQL-ERROR
           END-EVALUATE

           PERFORM 3212-INSERT-AMND
           .
      *-----------------*
       3212-INSERT-AMND.
      *-----------------*

           MOVE '3212-INSERT-AMND'          TO WS-PARAGRAPH-NAME

           IF DISPLAY-ACTIVE
              DISPLAY WS-PARAGRAPH-NAME
           END-IF

           MOVE WS-AMNDT-SEQUENCE-NO        TO D016-MCA-AMND-ID
                                               D019-MCA-AMND-ID

           MOVE WS-TEMPLATE-ID              TO D016-MCA-TMPLT-ID
           MOVE WS-CATEGORY-ID              TO D016-ATTRB-CTGRY-ID
           MOVE WS-TERM-ID                  TO D016-ATTRB-TERM-ID
           MOVE WS-CATEGORY-SQ              TO D016-CTGRY-SQ
           MOVE WS-TERM-SQ                  TO D016-TERM-SQ
           MOVE WS-AMNDT-SEQUENCE-NO        TO D016-MCA-ISDA-AMND-ID
           MOVE WS-USER-ID                  TO D016-ROW-UPDT-USER-ID

           EXEC SQL
                INSERT INTO TDPM17_AMND_WORK
                              (MCA_AMND_ID
                              ,MCA_TMPLT_ID
                              ,ATTRB_CTGRY_ID
                              ,CTGRY_SQ
                              ,ATTRB_TERM_ID
                              ,TERM_SQ
                              ,MCA_ISDA_AMND_ID
                              ,ROW_UPDT_TS
                              ,ROW_UPDT_USER_ID)
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

           EVALUATE SQLCODE
              WHEN 0
                 MOVE 'SP00'                TO LS-SP-RC
              WHEN OTHER
                 MOVE 'TDPM17_AMND_WORK'    TO WS-TABLE-NAME
                 PERFORM 9550-SQL-ERROR
           END-EVALUATE
           .
      *-----------------*
       9000-INSERT-VALUES.
      *-----------------*

           MOVE '9000-INSERT-VALUES'        TO WS-PARAGRAPH-NAME

           IF DISPLAY-ACTIVE
              DISPLAY WS-PARAGRAPH-NAME
           END-IF

           EVALUATE TRUE
               WHEN TEXT-INSERT
                 PERFORM 9010-TEXT-INSERT
               WHEN DOC-INSERT
                 IF DELETE-DOC
                    MOVE WS-VALUE-ID       TO WS-OUT-VALUE-ID
                    PERFORM 9020A-DOC-UPDATE
                 ELSE
                    PERFORM 9020-DOC-INSERT
                 END-IF
               WHEN COMMENT-INSERT
                 PERFORM 9030-COMMENT-INSERT
               WHEN OTHER
                 MOVE 'TEXT INDICATOR'      TO WS-TABLE-NAME
                 MOVE  'SP10'               TO LS-SP-RC
                 PERFORM 9990-GOBACK
           END-EVALUATE
           .
      *-----------------*
       9010-TEXT-INSERT.
      *-----------------*

           MOVE '9010-TEXT-INSERT'          TO WS-PARAGRAPH-NAME

           IF DISPLAY-ACTIVE
              DISPLAY WS-PARAGRAPH-NAME
           END-IF

           INITIALIZE WS-TDC-SEQUENCE-NO

           PERFORM 9100-GET-NEXT-SEQUENCE

           MOVE WS-TDC-SEQUENCE-NO          TO D013-MCA-VALUE-ID
           MOVE WS-TEXT-VAL                 TO D013-MCA-TEXT-DS-TEXT
           MOVE WS-TEXT-LEN                 TO D013-MCA-TEXT-DS-LEN

           EXEC SQL
                INSERT INTO TDPM13_MCA_TEXT
                             (MCA_VALUE_ID
                             ,MCA_TEXT_DS)
                       VALUES(:D013-MCA-VALUE-ID
                             ,:D013-MCA-TEXT-DS)
           END-EXEC

           EVALUATE SQLCODE
              WHEN 0
                 MOVE 'SP00'                TO LS-SP-RC
                 IF DISPLAY-ACTIVE
                    DISPLAY " WS-VALUE-FLAG " WS-VALUE-FLAG
                 END-IF
                 IF AMND-EXISTING
                    PERFORM 9010A-TEXT-UPDATE
                 ELSE
                    PERFORM 9010B-TEXT-INSERT
                 END-IF
              WHEN OTHER
                 MOVE 'VDPM13_MCA_TEXT'     TO WS-TABLE-NAME
                 PERFORM 9550-SQL-ERROR
           END-EVALUATE

           .
      *------------------*
       9010A-TEXT-UPDATE.
      *------------------*

           MOVE '9010A-TEXT-UPDATE'         TO WS-PARAGRAPH-NAME

           IF DISPLAY-ACTIVE
              DISPLAY WS-PARAGRAPH-NAME
              DISPLAY "WS-TEMPLATE-TYPE "  WS-TEMPLATE-TYPE
              DISPLAY "WS-VALUE-ID " WS-VALUE-ID
              DISPLAY "D019-MCA-AMND-ID " D019-MCA-AMND-ID
           END-IF

           MOVE WS-USER-ID                  TO D019-ROW-UPDT-USER-ID

           IF ISDA-TMPLT OR CLIENT-CUSTOMIZED OR EXECUTED-TMPLT
              EXEC SQL
                   DELETE FROM TDPM19_LINK_WORK
                    WHERE MCA_VALUE_ID     = :WS-VALUE-ID
                      AND MCA_AMND_ID      = :D019-MCA-AMND-ID
                      AND MCA_VALUE_TYPE_CD = 'T'
              END-EXEC
           ELSE
              EXEC SQL
                   DELETE FROM TDPM19_LINK_WORK
                    WHERE MCA_VALUE_ID     = :WS-VALUE-ID
                      AND MCA_AMND_ID      = :D019-MCA-AMND-ID
                      AND MCA_VALUE_TYPE_CD = 'T'
                      AND MCA_ACCS_STAT_CD = 'U'
              END-EXEC
           END-IF

           EVALUATE SQLCODE
              WHEN 0
              WHEN 100
                 MOVE 'SP00'                TO LS-SP-RC
                 IF DISPLAY-ACTIVE
                    DISPLAY "DELETE TEXT SQLCODE " SQLCODE
                 END-IF
                 PERFORM 9010B-TEXT-INSERT
              WHEN OTHER
                 MOVE 'VDPM19_LINK_WORK'    TO WS-TABLE-NAME
                 PERFORM 9550-SQL-ERROR
           END-EVALUATE
           .

      *------------------*
       9010B-TEXT-INSERT.
      *------------------*

           MOVE '9010B-TEXT-INSERT'         TO WS-PARAGRAPH-NAME

           IF DISPLAY-ACTIVE
              DISPLAY WS-PARAGRAPH-NAME
           END-IF

           MOVE D013-MCA-VALUE-ID           TO D019-MCA-VALUE-ID
                                               WS-OUT-VALUE-ID
           MOVE 'T'                         TO D019-MCA-VALUE-TYPE-CD
           MOVE 'U'                         TO D019-MCA-ACCS-STAT-CD

           IF WS-OUT-TEMPLATE-TYPE = 'I'
              MOVE 'I'                      TO D019-AMND-STAT-CD
           ELSE
              IF WS-VALUE-IND NOT = 'C'
                 MOVE 'P'                   TO D019-AMND-STAT-CD
              END-IF
           END-IF

           MOVE WS-USER-ID                  TO D019-ROW-UPDT-USER-ID

           EXEC SQL
                INSERT INTO TDPM19_LINK_WORK
                              (MCA_AMND_ID
                              ,MCA_VALUE_ID
                              ,MCA_VALUE_TYPE_CD
                              ,MCA_ACCS_STAT_CD
                              ,AMND_STAT_CD
                              ,ROW_UPDT_TS
                              ,ROW_UPDT_USER_ID)
                       VALUES (:D019-MCA-AMND-ID
                              ,:D019-MCA-VALUE-ID
                              ,:D019-MCA-VALUE-TYPE-CD
                              ,:D019-MCA-ACCS-STAT-CD
                              ,:D019-AMND-STAT-CD
                              ,CURRENT TIMESTAMP
                              ,:D019-ROW-UPDT-USER-ID)
           END-EXEC

           EVALUATE SQLCODE
              WHEN 0
                 MOVE 'SP00'                TO LS-SP-RC
                 IF DISPLAY-ACTIVE
                    DISPLAY "INSERT TEXT IS SUCEESFUL"
                    DISPLAY "D019-MCA-AMND-ID "  D019-MCA-AMND-ID
                    DISPLAY "D019-MCA-VALUE-ID " D019-MCA-VALUE-ID
                    DISPLAY "D019-MCA-VALUE-TYPE-CD "
                                                 D019-MCA-VALUE-TYPE-CD
                    DISPLAY "D019-MCA-ACCS-STAT-CD "
                                                 D019-MCA-ACCS-STAT-CD
                 END-IF
              WHEN OTHER
                 MOVE 'TDPM19_LINK_WORK'    TO WS-TABLE-NAME
                 PERFORM 9550-SQL-ERROR
           END-EVALUATE

           .
      *-----------------*
       9020-DOC-INSERT.
      *-----------------*

           MOVE '9020-DOC-INSERT'           TO WS-PARAGRAPH-NAME

           IF DISPLAY-ACTIVE
              DISPLAY WS-PARAGRAPH-NAME
           END-IF

           INITIALIZE WS-PASS-AREA

           MOVE WS-TEXT-VAL(1:WS-TEXT-LEN)  TO WS-IN-MCA-DOC-DS
           MOVE 'I'                         TO WS-IN-DOC-TYPE-CD
           MOVE WS-USER-ID                  TO WS-IN-ROW-UPDT-USER-ID
           MOVE WS-DOCUMENT                 TO WS-IN-DOC-OBJ-TX
      *    MOVE LS-DOCUMENT                 TO WS-IN-DOC-OBJ-TX

           IF DISPLAY-ACTIVE
              DISPLAY "INPUT TO THE UPLOAD PROGRAM"
              DISPLAY "DOCUMENT NAME "     WS-IN-MCA-DOC-DS
              DISPLAY "WS-IN-DOC-TYPE-CD " WS-IN-DOC-TYPE-CD
              DISPLAY "WS-USER-ID "        WS-USER-ID
           END-IF

           CALL WS-UPL-PROGRAM USING WS-PASS-AREA

           IF DISPLAY-ACTIVE
              DISPLAY "OUTPUT OF THE UPLOAD PROGRAM"
              DISPLAY "WS-O-DOC-ID "    WS-O-DOC-ID
              DISPLAY "WS-OUT-SQLCODE " WS-OUT-SQLCODE
           END-IF
           MOVE WS-OUT-SQLCODE              TO SQLCODE

           EVALUATE SQLCODE
              WHEN 0
                 MOVE 'SP00'                TO LS-SP-RC
                 MOVE WS-O-DOC-ID           TO WS-OUT-VALUE-ID
              WHEN OTHER
                 MOVE 'DOC_SEQ'             TO WS-TABLE-NAME
                 PERFORM 9550-SQL-ERROR
           END-EVALUATE

           IF AMND-EXISTING
              PERFORM 9020A-DOC-UPDATE
           ELSE
              PERFORM 9020B-DOC-INSERT
           END-IF

           .
      *------------------*
       9020A-DOC-UPDATE.
      *------------------*

           MOVE '9020A-DOC-UPDATE'          TO WS-PARAGRAPH-NAME

           MOVE WS-USER-ID                  TO D019-ROW-UPDT-USER-ID
           MOVE WS-OUT-VALUE-ID             TO D019-MCA-VALUE-ID

           IF DISPLAY-ACTIVE
              DISPLAY WS-PARAGRAPH-NAME
              DISPLAY "WS-TEMPLATE-TYPE "  WS-TEMPLATE-TYPE
              DISPLAY "WS-VALUE-ID " WS-VALUE-ID
              DISPLAY "D019-MCA-AMND-ID " D019-MCA-AMND-ID
           END-IF

           IF ISDA-TMPLT OR CLIENT-CUSTOMIZED OR EXECUTED-TMPLT
              EXEC SQL
                   DELETE FROM TDPM19_LINK_WORK
                    WHERE MCA_VALUE_ID     = :WS-VALUE-ID
                      AND MCA_AMND_ID      = :D019-MCA-AMND-ID
                      AND MCA_VALUE_TYPE_CD = 'D'
              END-EXEC
           ELSE
              EXEC SQL
                   DELETE FROM TDPM19_LINK_WORK
                    WHERE MCA_VALUE_ID     = :WS-VALUE-ID
                      AND MCA_AMND_ID      = :D019-MCA-AMND-ID
                      AND MCA_VALUE_TYPE_CD = 'D'
                      AND MCA_ACCS_STAT_CD = 'U'
              END-EXEC
           END-IF

           EVALUATE SQLCODE
              WHEN 0
              WHEN 100
                 MOVE 'SP00'                TO LS-SP-RC

                 IF DISPLAY-ACTIVE
                    DISPLAY "DELETE DOC SQLCODE " SQLCODE
                 END-IF

                 IF NOT DELETE-DOC
                    PERFORM 9020B-DOC-INSERT
                 END-IF

              WHEN OTHER
                 MOVE 'VDPM19_LINK_WORK'    TO WS-TABLE-NAME
                 PERFORM 9550-SQL-ERROR
           END-EVALUATE
           .

      *-----------------*
       9020B-DOC-INSERT.
      *-----------------*

           MOVE '9020B-DOC-INSERT'          TO WS-PARAGRAPH-NAME

           IF DISPLAY-ACTIVE
              DISPLAY WS-PARAGRAPH-NAME
           END-IF

           MOVE WS-OUT-VALUE-ID             TO D019-MCA-VALUE-ID
           MOVE 'D'                         TO D019-MCA-VALUE-TYPE-CD
           MOVE 'U'                         TO D019-MCA-ACCS-STAT-CD

           IF WS-OUT-TEMPLATE-TYPE = 'I'
              MOVE 'I'                      TO D019-AMND-STAT-CD
           ELSE
              MOVE 'P'                      TO D019-AMND-STAT-CD
           END-IF

           MOVE WS-USER-ID                  TO D019-ROW-UPDT-USER-ID

           EXEC SQL
                INSERT INTO TDPM19_LINK_WORK
                              (MCA_AMND_ID
                              ,MCA_VALUE_ID
                              ,MCA_VALUE_TYPE_CD
                              ,MCA_ACCS_STAT_CD
                              ,AMND_STAT_CD
                              ,ROW_UPDT_TS
                              ,ROW_UPDT_USER_ID)
                       VALUES (:D019-MCA-AMND-ID
                              ,:D019-MCA-VALUE-ID
                              ,:D019-MCA-VALUE-TYPE-CD
                              ,:D019-MCA-ACCS-STAT-CD
                              ,:D019-AMND-STAT-CD
                              ,CURRENT TIMESTAMP
                              ,:D019-ROW-UPDT-USER-ID)
           END-EXEC

           EVALUATE SQLCODE
              WHEN 0
                 MOVE 'SP00'                TO LS-SP-RC
                 IF DISPLAY-ACTIVE
                    DISPLAY "DOCUMENT INSERT IS SUCCESSFUL"
                    DISPLAY "D019-MCA-AMND-ID "  D019-MCA-AMND-ID
                    DISPLAY "D019-MCA-VALUE-ID " D019-MCA-VALUE-ID
                    DISPLAY "D019-MCA-VALUE-TYPE-CD "
                                                 D019-MCA-VALUE-TYPE-CD
                    DISPLAY "D019-MCA-ACCS-STAT-CD "
                                                 D019-MCA-ACCS-STAT-CD
                 END-IF
              WHEN OTHER
                 MOVE 'TDPM19_LINK_WORK'    TO WS-TABLE-NAME
                 PERFORM 9550-SQL-ERROR
           END-EVALUATE

           .
      *--------------------*
       9030-COMMENT-INSERT.
      *--------------------*

           MOVE '9030-COMMENT-INSERT'       TO WS-PARAGRAPH-NAME

           IF DISPLAY-ACTIVE
              DISPLAY WS-PARAGRAPH-NAME
           END-IF

           INITIALIZE WS-TDC-SEQUENCE-NO

           PERFORM 9100-GET-NEXT-SEQUENCE

           MOVE WS-USER-ID                  TO D011-ROW-UPDT-USER-ID

           MOVE WS-TDC-SEQUENCE-NO          TO D011-MCA-VALUE-ID
                                               WS-OUT-VALUE-ID
           MOVE WS-TEXT-VAL                 TO D011-CMNT-TX-TEXT
           MOVE WS-TEXT-LEN                 TO D011-CMNT-TX-LEN

           EXEC SQL
                INSERT INTO TDPM11_MCA_CMNT
                             (MCA_VALUE_ID
                             ,DELR_CMPNY_ID
                             ,CLNT_CMPNY_ID
                             ,ROW_UPDT_TS
                             ,ROW_UPDT_USER_ID
                             ,CMNT_TX)
                       VALUES(:D011-MCA-VALUE-ID
                             ,:D011-DELR-CMPNY-ID
                             ,:D011-CLNT-CMPNY-ID
                             ,CURRENT TIMESTAMP
                             ,:D011-ROW-UPDT-USER-ID
                             ,:D011-CMNT-TX)
           END-EXEC

           EVALUATE SQLCODE
              WHEN 0
                 MOVE 'SP00'                TO LS-SP-RC
                 PERFORM 9030A-COMMENT-UPDATE
              WHEN OTHER
                 MOVE 'TDPM11_MCA_CMNT'     TO WS-TABLE-NAME
                 PERFORM 9550-SQL-ERROR
           END-EVALUATE

           .
      *---------------------*
       9030A-COMMENT-UPDATE.
      *---------------------*

           MOVE '9030A-COMMENT-UPDATE'      TO WS-PARAGRAPH-NAME

           IF DISPLAY-ACTIVE
              DISPLAY WS-PARAGRAPH-NAME
              DISPLAY "AMENDMENT STATUS CODE " D018-AMND-STAT-CD
           END-IF
           MOVE 'C'                         TO D019-MCA-VALUE-TYPE-CD
           MOVE 'U'                         TO D019-MCA-ACCS-STAT-CD
           MOVE WS-USER-ID                  TO D019-ROW-UPDT-USER-ID
           MOVE D018-AMND-STAT-CD           TO D019-AMND-STAT-CD

           EXEC SQL
                INSERT INTO TDPM19_LINK_WORK
                              (MCA_AMND_ID
                              ,MCA_VALUE_ID
                              ,MCA_VALUE_TYPE_CD
                              ,MCA_ACCS_STAT_CD
                              ,AMND_STAT_CD
                              ,ROW_UPDT_TS
                              ,ROW_UPDT_USER_ID)
                       VALUES (:D019-MCA-AMND-ID
                              ,:D011-MCA-VALUE-ID
                              ,:D019-MCA-VALUE-TYPE-CD
                              ,:D019-MCA-ACCS-STAT-CD
                              ,:D019-AMND-STAT-CD
                              ,CURRENT TIMESTAMP
                              ,:D019-ROW-UPDT-USER-ID)
           END-EXEC

           EVALUATE SQLCODE
              WHEN 0
                 MOVE 'SP00'                TO LS-SP-RC
              WHEN OTHER
                 MOVE 'TDPM19_LINK_WORK'    TO WS-TABLE-NAME
                 PERFORM 9550-SQL-ERROR
           END-EVALUATE
           .
      *----------------------*
       9100-GET-NEXT-SEQUENCE.
      *----------------------*

           MOVE '9100-GET-NEXT-SEQUENCE'    TO WS-PARAGRAPH-NAME

           IF DISPLAY-ACTIVE
              DISPLAY WS-PARAGRAPH-NAME
           END-IF

           EXEC SQL
                SET :WS-TDC-SEQUENCE-NO = NEXT VALUE FOR DPM.SQDPM111
           END-EXEC

           EVALUATE SQLCODE
              WHEN 0
                 MOVE 'SP00'                TO LS-SP-RC

                 IF DISPLAY-ACTIVE
                    DISPLAY "WS-TDC-SEQUENCE-NO " WS-TDC-SEQUENCE-NO
                 END-IF

              WHEN OTHER
                 MOVE 'TXT_SEQ'             TO WS-TABLE-NAME
                 PERFORM 9550-SQL-ERROR
           END-EVALUATE
           .
      *--------------------------*
       9200-COPY-CATEGORY-DETAILS.
      *--------------------------*

           MOVE '9200-COPY-CATEGORY-DETAILS'
                                            TO WS-PARAGRAPH-NAME

           IF DISPLAY-ACTIVE
              DISPLAY WS-PARAGRAPH-NAME
           END-IF
           SET NOT-EOF-RECORD               TO TRUE

           PERFORM 9200A-DECLARE-CATGRY-CURSOR

           PERFORM 9200B-OPEN-CATGRY-CURSOR

           PERFORM UNTIL EOF-RECORD

              PERFORM 9200C-FETCH-CATGRY-CURSOR

              EVALUATE SQLCODE
                  WHEN 0
                    PERFORM 9200D-INSERT-CATGRY
                  WHEN 100
                    SET EOF-RECORD          TO TRUE
                    PERFORM 9200E-CLOSE-CATGRY-CURSOR
                  WHEN OTHER
                    MOVE 'VDPM07_MCA_CTGRY' TO WS-TABLE-NAME
                    PERFORM 9550-SQL-ERROR
              END-EVALUATE
           END-PERFORM
           .
      *---------------------------*
       9200A-DECLARE-CATGRY-CURSOR.
      *---------------------------*

           MOVE '9200A-DECLARE-CATGRY-CURSOR'
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
       9200B-OPEN-CATGRY-CURSOR.
      *--------------------------*

           MOVE '9200B-OPEN-CATGRY-CURSOR'  TO WS-PARAGRAPH-NAME

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
                 PERFORM 9550-SQL-ERROR
           END-EVALUATE
           .
      *--------------------------*
       9200C-FETCH-CATGRY-CURSOR.
      *--------------------------*

           MOVE '9200C-FETCH-CATGRY-CURSOR' TO WS-PARAGRAPH-NAME

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
       9200D-INSERT-CATGRY.
      *---------------------*

           MOVE '9200D-INSERT-CATGRY'       TO WS-PARAGRAPH-NAME

           IF DISPLAY-ACTIVE
              DISPLAY WS-PARAGRAPH-NAME
           END-IF

           MOVE WS-TEMP-SEQUENCE-NO         TO D007-MCA-TMPLT-ID

           MOVE WS-USER-ID                  TO D007-ROW-UPDT-USER-ID

           EXEC SQL
                INSERT INTO TDPM07_MCA_CTGRY
                              (MCA_TMPLT_ID
                              ,ATTRB_CTGRY_ID
                              ,CTGRY_SQ
                              ,CTGRY_STAT_CD
                              ,ROW_UPDT_TS
                              ,ROW_UPDT_USER_ID)
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
                 PERFORM 9550-SQL-ERROR
           END-EVALUATE
           .

      *--------------------------*
       9200E-CLOSE-CATGRY-CURSOR.
      *--------------------------*

           MOVE '9200E-CLOSE-CATGRY-CURSOR' TO WS-PARAGRAPH-NAME

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
                 PERFORM 9550-SQL-ERROR
           END-EVALUATE
           .
      *------------------------*
       9300-COPY-TERM-DETAILS.
      *------------------------*

           MOVE '9300-COPY-TERM-DETAILS'    TO WS-PARAGRAPH-NAME

           IF DISPLAY-ACTIVE
              DISPLAY WS-PARAGRAPH-NAME
           END-IF


           SET NOT-EOF-RECORD               TO TRUE

           PERFORM 9300A-DECLARE-TERM-CURSOR

           PERFORM 9300B-OPEN-TERM-CURSOR

           PERFORM UNTIL EOF-RECORD

              PERFORM 9300C-FETCH-TERM-CURSOR

              EVALUATE SQLCODE
                  WHEN 0
                    PERFORM 9300D-INSERT-TERM
                  WHEN 100
                    SET EOF-RECORD          TO TRUE
                    PERFORM 9300E-CLOSE-TERM-CURSOR
                  WHEN OTHER
                    MOVE 'VDPM08_MCA_TERMS' TO WS-TABLE-NAME
                    PERFORM 9550-SQL-ERROR
              END-EVALUATE
           END-PERFORM
           .
      *---------------------------*
       9300A-DECLARE-TERM-CURSOR.
      *---------------------------*

           MOVE '9300A-DECLARE-TERM-CURSOR' TO WS-PARAGRAPH-NAME

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
       9300B-OPEN-TERM-CURSOR.
      *------------------------*

           MOVE '9300B-OPEN-TERM-CURSOR'    TO WS-PARAGRAPH-NAME

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
                 PERFORM 9550-SQL-ERROR
           END-EVALUATE
           .
      *--------------------------*
       9300C-FETCH-TERM-CURSOR.
      *--------------------------*

           MOVE '9300C-FETCH-TERM-CURSOR'   TO WS-PARAGRAPH-NAME

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
       9300D-INSERT-TERM.
      *---------------------*

           MOVE '9300D-INSERT-TERM'         TO WS-PARAGRAPH-NAME

           IF DISPLAY-ACTIVE
              DISPLAY WS-PARAGRAPH-NAME
           END-IF

           MOVE WS-TEMP-SEQUENCE-NO         TO D008-MCA-TMPLT-ID

           MOVE WS-USER-ID                  TO D008-ROW-UPDT-USER-ID

           EXEC SQL
                INSERT INTO TDPM08_MCA_TERMS
                              (MCA_TMPLT_ID
                              ,ATTRB_CTGRY_ID
                              ,CTGRY_SQ
                              ,ATTRB_TERM_ID
                              ,TERM_SQ
                              ,TERM_STAT_CD
                              ,ROW_UPDT_TS
                              ,ROW_UPDT_USER_ID)
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
                 PERFORM 9550-SQL-ERROR
           END-EVALUATE
           .
      *--------------------------*
       9300E-CLOSE-TERM-CURSOR.
      *--------------------------*

           MOVE '9300E-CLOSE-TERM-CURSOR'   TO WS-PARAGRAPH-NAME

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
                 PERFORM 9550-SQL-ERROR
           END-EVALUATE
           .
      *---------------------*
       9500-GET-LINK-DETAILS.
      *---------------------*

           MOVE '9500-GET-LINK-DETAILS'     TO WS-PARAGRAPH-NAME

           IF DISPLAY-ACTIVE
              DISPLAY WS-PARAGRAPH-NAME
           END-IF

           SET NOT-EOF-LINK-RECORD          TO TRUE

           PERFORM 9505A-DECLARE-LINK-CURSOR

           PERFORM 9505B-OPEN-LINK-CURSOR

           PERFORM UNTIL EOF-LINK-RECORD

              PERFORM 9505C-FETCH-LINK-CURSOR

              EVALUATE SQLCODE
                  WHEN 0
                    PERFORM 9505D-INSERT-LINK-WRK
                  WHEN 100
                    SET EOF-LINK-RECORD     TO TRUE
                    PERFORM 9505E-CLOSE-LINK-CURSOR
                  WHEN OTHER
                    MOVE 'TDPM16_MCA_AMND'  TO WS-TABLE-NAME
                    PERFORM 9550-SQL-ERROR
              END-EVALUATE
           END-PERFORM
           .
      *--------------------------*
       9505A-DECLARE-LINK-CURSOR.
      *--------------------------*

           MOVE '9505A-DECLARE-LINK-CURSOR' TO WS-PARAGRAPH-NAME

           IF DISPLAY-ACTIVE
              DISPLAY WS-PARAGRAPH-NAME
           END-IF

           EXEC SQL
                DECLARE DC_LINK_CSR CURSOR FOR
                 SELECT  MCA_VALUE_ID
                        ,MCA_VALUE_TYPE_CD
                        ,AMND_STAT_CD
                   FROM VDPM18_MCA_LINK
                  WHERE MCA_AMND_ID     = :WS-AMND-ID
                 WITH UR
           END-EXEC
           .
      *-----------------------*
       9505B-OPEN-LINK-CURSOR.
      *-----------------------*

           MOVE '9505B-OPEN-LINK-CURSOR'    TO WS-PARAGRAPH-NAME

           IF DISPLAY-ACTIVE
              DISPLAY WS-PARAGRAPH-NAME
           END-IF

           EXEC SQL
                OPEN DC_LINK_CSR
           END-EXEC

           EVALUATE SQLCODE
              WHEN 0
                 MOVE 'SP00'                TO LS-SP-RC
              WHEN OTHER
                 MOVE 'DC_LINK_CSR'         TO WS-TABLE-NAME
                 PERFORM 9550-SQL-ERROR
           END-EVALUATE
           .
      *--------------------------*
       9505C-FETCH-LINK-CURSOR.
      *--------------------------*

           MOVE '9505C-FETCH-LINK-CURSOR'   TO WS-PARAGRAPH-NAME

           IF DISPLAY-ACTIVE
              DISPLAY WS-PARAGRAPH-NAME
           END-IF

           EXEC SQL
                FETCH DC_LINK_CSR
                 INTO  :D018-MCA-VALUE-ID
                      ,:D018-MCA-VALUE-TYPE-CD
                      ,:D018-AMND-STAT-CD
           END-EXEC
           .

      *---------------------*
       9505D-INSERT-LINK-WRK.
      *---------------------*

           MOVE '9505D-INSERT-LINK-WRK'     TO WS-PARAGRAPH-NAME

           IF DISPLAY-ACTIVE
              DISPLAY WS-PARAGRAPH-NAME
           END-IF

      *    MOVE WS-AMNDT-SEQUENCE-NO        TO D019-MCA-AMND-ID
           MOVE WS-AMND-ID                  TO D019-MCA-AMND-ID


           MOVE 'U'                         TO D019-MCA-ACCS-STAT-CD

           MOVE WS-USER-ID                  TO D019-ROW-UPDT-USER-ID

           EXEC SQL
                INSERT INTO TDPM19_LINK_WORK
                              (MCA_AMND_ID
                              ,MCA_VALUE_ID
                              ,MCA_VALUE_TYPE_CD
                              ,MCA_ACCS_STAT_CD
                              ,AMND_STAT_CD
                              ,ROW_UPDT_TS
                              ,ROW_UPDT_USER_ID)
                       VALUES (:D019-MCA-AMND-ID
                              ,:D018-MCA-VALUE-ID
                              ,:D018-MCA-VALUE-TYPE-CD
                              ,:D019-MCA-ACCS-STAT-CD
                              ,:D018-AMND-STAT-CD
                              ,CURRENT TIMESTAMP
                              ,:D019-ROW-UPDT-USER-ID)
           END-EXEC

           EVALUATE SQLCODE
              WHEN 0
                 MOVE 'SP00'                TO LS-SP-RC
              WHEN OTHER
                 MOVE 'VDPM19_LINK_WORK'    TO WS-TABLE-NAME
                 PERFORM 9550-SQL-ERROR
           END-EVALUATE

      *    MOVE WS-AMNDT-SEQUENCE-NO        TO D019-MCA-AMND-ID
           MOVE WS-AMND-ID                  TO D019-MCA-AMND-ID

           IF WORKING-TMPLT OR REEXEC-WORKING
              MOVE 'O'                      TO D019-MCA-ACCS-STAT-CD

              MOVE WS-USER-ID               TO D019-ROW-UPDT-USER-ID

              EXEC SQL
                   INSERT INTO TDPM19_LINK_WORK
                                  (MCA_AMND_ID
                                  ,MCA_VALUE_ID
                                  ,MCA_VALUE_TYPE_CD
                                  ,MCA_ACCS_STAT_CD
                                  ,AMND_STAT_CD
                                  ,ROW_UPDT_TS
                                  ,ROW_UPDT_USER_ID)
                          VALUES  (:D019-MCA-AMND-ID
                                  ,:D018-MCA-VALUE-ID
                                  ,:D018-MCA-VALUE-TYPE-CD
                                  ,:D019-MCA-ACCS-STAT-CD
                                  ,:D018-AMND-STAT-CD
                                  ,CURRENT TIMESTAMP
                                  ,:D019-ROW-UPDT-USER-ID)
              END-EXEC

              EVALUATE SQLCODE
                 WHEN 0
                    MOVE 'SP00'             TO LS-SP-RC
                 WHEN OTHER
                    MOVE 'VDPM19_LINK_WORK' TO WS-TABLE-NAME
                    PERFORM 9550-SQL-ERROR
              END-EVALUATE
           END-IF
           .
      *------------------------*
       9505E-CLOSE-LINK-CURSOR.
      *------------------------*

           MOVE '9505E-CLOSE-LINK-CURSOR'   TO WS-PARAGRAPH-NAME

           IF DISPLAY-ACTIVE
              DISPLAY WS-PARAGRAPH-NAME
           END-IF

           EXEC SQL
                CLOSE DC_LINK_CSR
           END-EXEC

           EVALUATE SQLCODE
              WHEN 0
                 MOVE 'SP00'                TO LS-SP-RC
              WHEN OTHER
                 MOVE 'VDPM19_LINK_WORK'    TO WS-TABLE-NAME
                 PERFORM 9550-SQL-ERROR
           END-EVALUATE
           .
      *-----------------------*                                         01850000
       9520-CHECK-DEBUG-TABLE.                                          01860000
      *-----------------------*                                         01870000
                                                                        01880000
           MOVE '9520-CHECK-DEBUG-TABLE'    TO WS-PARAGRAPH-NAME        01890000
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
                  PERFORM 9550-SQL-ERROR                                 0181000
           END-EVALUATE                                                 00052102
           .
      *---------------*                                                 01850000
       9550-SQL-ERROR.                                                  01860000
      *---------------*                                                 01870000
                                                                        01880000
           MOVE '9550-SQL-ERROR'            TO WS-PARAGRAPH-NAME        01890000
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
           PERFORM 9991-POPULATE-OUTPUT

           IF DISPLAY-ACTIVE
              PERFORM 9995-DISPLAY-DATA
           END-IF

           PERFORM 9999-FORMAT-SQLCA
           GOBACK
           .
      *-------------------*
       9991-POPULATE-OUTPUT.
      *-------------------*

           MOVE '9991-POPULATE-OUTPUT'      TO WS-PARAGRAPH-NAME

           IF LS-SP-RC = 'SP99'
              EXEC SQL
                   ROLLBACK
              END-EXEC
              DISPLAY "WS-ERROR-AREA " WS-ERROR-AREA
           ELSE
              MOVE WS-OUT-TEMPLATE-ID       TO LS-TEMPLATE-ID
              MOVE WS-OUT-TEMPLATE-TYPE     TO LS-TEMPLATE-TYPE
              MOVE WS-AMND-ID               TO LS-AMND-ID
              MOVE WS-OUT-VALUE-ID          TO LS-VALUE-ID
           END-IF
           .
      *------------------*
       9995-DISPLAY-DATA.
      *------------------*
           DISPLAY 'WS-OUT-TEMPLATE-ID   :' WS-OUT-TEMPLATE-ID
           DISPLAY 'WS-OUT-TEMPLATE-TYPE :' WS-OUT-TEMPLATE-TYPE
           DISPLAY 'WS-AMND-ID           :' WS-AMND-ID
           DISPLAY 'WS-OUT-VALUE-ID      :' WS-OUT-VALUE-ID
           DISPLAY 'LS-SP-RC             :' LS-SP-RC
           DISPLAY 'LS-SP-ERROR-AREA     :' LS-SP-ERROR-AREA
           .
      *------------------*
       9999-FORMAT-SQLCA.
      *------------------*
           PERFORM DB2000I-FORMAT-SQLCA
              THRU DB2000I-FORMAT-SQLCA-EXIT

           IF DISPLAY-ACTIVE
              EXEC SQL
                   SET :WS-CURRENT-TS = CURRENT TIMESTAMP
              END-EXEC
              DISPLAY "PROGRAM DPMXMAMN - END TIMESTAMP " WS-CURRENT-TS
           END-IF
           .

      **MOVE STATEMENTS TO FORMAT THE OUTSQLCA USING DB2000IA & DB2000IB
        COPY DB2000IC.
