       IDENTIFICATION DIVISION.
       PROGRAM-ID.    DPMXMSAV.
       AUTHOR.        COGNIZANT.

      ******************************************************************
      *THIS IS AN UNPUBLISHED PROGRAM OWNED BY ISCC                    *
      *IN WHICH A COPYRIGHT SUBSISTS AS OF JULY 2006.                  *
      *                                                                *
      ******************************************************************
      **LNKCTL**
      *    INCLUDE SYSLIB(DSNRLI)
      *    MODE AMODE(31) RMODE(ANY)
      *    ENTRY DPMXMSAV
      *    NAME  DPMXMSAV(R)
      *
      ******************************************************************
      **         P R O G R A M   D O C U M E N T A T I O N            **
      ******************************************************************
      * SYSTEM:    DERIV/SERV MCA XPRESS                               *
      * PROGRAM:   DPMXMSAV                                            *
      *                                                                *
      * THIS STORED PROCEDURE USED TO SAVE THE AMENDMENT DETAILS FOR   *
      * A TEMPLATE                                                     *
      ******************************************************************
      * TABLES:                                                        *
      * -------                                                        *
      * VDPM07_MCA_CTGRY          - THIS TABLE CONTAINS THE CATEGORY   *
      *                             INFORMATION FOR A TEMPLATE         *
      * VDPM08_MCA_TERMS          - THIS TABLE CONTAINS THE TERM       *00300000
      *                             INFORMATION FOR A TEMPLATE         *00310000
      * VDPM10_MCA_LOCK           - CONTAINS THE LOCK INFORMATION FOR  *
      *                             A PARTICULAR TEMPLATE              *
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
      * DPM0701  - DCLGEN COPYBOOK FOR VDPM07_MCA_CTGRY  TABLE         *00380000
      * DPM0801  - DCLGEN COPYBOOK FOR VDPM08_MCA_TERMS  TABLE         *00380000
      * DPM1001  - DCLGEN COPYBOOK FOR VDPM10_MCA_LOCK   TABLE         *00380000
      * DPM1401  - DCLGEN COPYBOOK FOR D0006  TABLE         *00380000
      * DPM1501  - DCLGEN COPYBOOK FOR VDPM15_TMPLT_WORK TABLE         *00380000
      * DPM1601  - DCLGEN COPYBOOK FOR VDPM16_MCA_AMND   TABLE         *00380000
      * DPM1801  - DCLGEN COPYBOOK FOR VDPM18_MCA_LINK   TABLE         *00380000
      * DPM1901  - DCLGEN COPYBOOK FOR VDPM19_LINK_WORK  TABLE         *00380000
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
      * 09/06/2007        00.00     COGNIZANT                         *
      * INITIAL IMPLEMENTATION                                        *
      *                                                               *
      *****************************************************************
       ENVIRONMENT DIVISION.
       DATA DIVISION.
       WORKING-STORAGE SECTION.
       01  WS-PROGRAM                      PIC X(08) VALUE 'DPMXMSAV'.
       01  WS-LOCK-PROGRAM                 PIC X(08) VALUE 'DPMXMLOK'.
       01  WS-VARIABLES.
             05  WS-NEW-TEMPLATE-ID        PIC S9(09) COMP
                                           VALUE ZEROES.
             05  WS-OLD-TEMPLATE-ID        PIC S9(09) COMP
                                           VALUE ZEROES.
             05  WS-OUT-TEMPLATE-ID        PIC S9(09) COMP
                                           VALUE ZEROES.
             05  WS-OUT-TEMPLATE-TYPE      PIC X(01) VALUE SPACES.
      *      05  WS-TEMPLATE-NAME          PIC X(150) VALUE SPACES.
             05  WS-TEMPLATE-NAME.
                 49 WS-TEMPLATE-NAME-LEN   PIC S9(04) COMP.
                 49 WS-TEMPLATE-NAME-TEXT  PIC X(498).
             05  WS-TEMPLATE-NAME-UPPER    PIC X(500) VALUE SPACES.
             05  WS-TEMPLATE-TYPE          PIC X(01) VALUE SPACES.
                 88 ISDA-TMPLT             VALUE 'I'.
                 88 DEALER-CUSTOMIZED      VALUE 'D'.
                 88 CLIENT-CUSTOMIZED      VALUE 'C'.
                 88 EXECUTED-TMPLT         VALUE 'E'.
                 88 WORKING-TMPLT          VALUE 'W'.
                 88 REEXEC-WORKING         VALUE 'R'.
             05  WS-NEW-TEMPLATE-TYPE      PIC X(01) VALUE SPACES.
             05  WS-CMPNY-CD               PIC X(08) VALUE SPACES.
             05  WS-CURRENT-TIMESTAMP      PIC X(26) VALUE SPACES.
             05  WS-TMPLT-CHECK            PIC S9(09) COMP
                                                     VALUE ZEROES.
             05  WS-USER-ID                PIC X(10) VALUE SPACES.
             05  WS-SAVE-IND               PIC X(01) VALUE SPACES.
                 88 CHECK-TMPLT            VALUE 'C'.
                 88 SAVE-TMPLT             VALUE 'S'.
                 88 UNLOCK-TMPLT           VALUE 'U'.
                 88 DELETE-TMPLT           VALUE 'D'.
             05  WS-EOF-RECORD-VALUE       PIC X(1)  VALUE 'N'.
                 88 EOF-RECORD             VALUE 'Y'.
                 88 NOT-EOF-RECORD         VALUE 'N'.
             05  WS-SAVE-DC-TEMPLATE       PIC X(1)  VALUE SPACES.
                 88 NOT-IN-MSTR-TABLE      VALUE 'N'.
                 88 TMPLT-ID-SAME          VALUE 'S'.
                 88 TMPLT-ID-DIFF          VALUE 'D'.
             05  WS-DELETE-LINK            PIC X(1)  VALUE 'N'.
                 88 LINK-DELETED           VALUE 'Y'.
                 88 LINK-NOT-DELETED       VALUE 'N'.
             05  WS-CREATE-LINK            PIC X(1)  VALUE 'N'.
                 88 LINK-CREATE            VALUE 'Y'.
                 88 LINK-NOT-CREATE        VALUE 'N'.
             05  WS-TMPLT-STAT             PIC X(1)  VALUE 'N'.
                 88 MAIN-TMPLT             VALUE 'Y'.
                 88 WORK-TMPLT             VALUE 'N'.
             05  WS-TMPLT-TYPES            PIC X(1)  VALUE 'N'.
                 88 WORK-TMPLT-TYPE        VALUE 'Y'.
                 88 MAIN-TMPLT-TYPE        VALUE 'N'.
             05  WS-TMPLT-UPDATE-FLAG      PIC X(1)  VALUE 'N'.
                 88 TMPLT-UPDATE           VALUE 'Y'.
                 88 TMPLT-NOT-UPDATE       VALUE 'N'.
             05  WS-AMND-PENDING-FLAG      PIC X(1)  VALUE 'N'.
                 88 AMND-PENDING           VALUE 'Y'.
                 88 NO-AMND-PENDING        VALUE 'N'.
             05  WS-USER-STATUS            PIC X(1)  VALUE 'N'.
                 88 LOCKED-USER            VALUE 'Y'.
                 88 OTHER-USER             VALUE 'N'.
             05  WS-OVER-WRITE-STAT        PIC X(1)  VALUE 'N'.
                 88 OVER-WRITE-LOCKED      VALUE 'Y'.
                 88 OVER-WRITE-OTHER       VALUE 'N'.
             05  WS-TEMPLATE-LOCKED        PIC X(1)  VALUE SPACES.
                 88 TEMPLATE-LOCKED        VALUE 'Y'.
                 88 TEMPLATE-NOT-LOCKED    VALUE 'N'.
             05  WS-TEMP-SEQUENCE-NO       PIC S9(9) COMP
                                           VALUE ZEROES.
             05  WS-AMNDT-SEQUENCE-NO      PIC S9(18) COMP-3
                                           VALUE ZEROES.
             05  WS-PREV-AMND-ID           PIC S9(18) COMP-3
                                           VALUE ZEROES.
             05  WS-PREV-VALUE-ID          PIC S9(18) COMP-3
                                           VALUE ZEROES.
             05  WS-PREV-VALUE-TYPE-CD     PIC X(01)
                                           VALUE SPACES.
             05  WS-AMND-CHECK             PIC S9(04) COMP
                                           VALUE ZEROES.
             05  WS-LINK-CHECK             PIC S9(18) COMP-3
                                           VALUE ZEROES.
             05  WS-EOF-LINK-RECORD-VALUE  PIC X(1)  VALUE 'N'.
                 88 EOF-LINK-RECORD        VALUE 'Y'.
                 88 NOT-EOF-LINK-RECORD    VALUE 'N'.
                                                                        00850000
             05  WS-DISPLAY-CONTROL-FLAG   PIC X(001) VALUE SPACES.     01190000
                 88 DISPLAY-ACTIVE         VALUE 'Y'.
                 88 DISPLAY-INACTIVE       VALUE 'N'.
                                                                        00850000
       01  WS-ERROR-MSG.                                                00860000
             05  WS-INVALID-TMPLT-TYPE     PIC X(50)                    00560100
                 VALUE 'INVALID TEMPLATE TYPE'.                         00560100
             05  WS-TMPLT-LOCKED           PIC X(50)                    00560100
                 VALUE 'Template is Locked. '.                          00560100
             05  WS-INVALID-SAVE-IND       PIC X(50)                    00560100
                 VALUE 'INVALID SAVE INDICATOR '.                       00560100
             05  WS-TMPLT-EXIST            PIC X(50)                    00560100
                 VALUE 'Template already exists. '.                     00560100
             05  WS-CANNOT-SAVE            PIC X(50)                    00560100
                 VALUE 'Cannot save as ISDA,CP FINAL and Executed templa00560100
      -          'te'.
             05  WS-NO-WORK-VALUE          PIC X(50)                    00560100
                 VALUE 'There are no changes to save'.                  00560100
             05  WS-DATABASE-ERROR         PIC X(50)                    00560100
                 VALUE 'Database Error Occurred. Please Contact DTCC'.  00560100

       01  WS-ERROR-AREA.
             05  WS-PARAGRAPH-NAME         PIC X(40).
             05  FILLER                    PIC X VALUE ','.
             05  WS-TABLE-NAME             PIC X(18).
             05  WS-SQLCODE                PIC 9(7).

       01  WS-LOCK-AREA.
             05  WS-LOCK-TEMPLATE-ID       PIC S9(9) COMP.
             05  WS-LOCK-CMPNY-ID          PIC X(8).
             05  WS-LOCK-IN-USER-ID        PIC X(10).
             05  WS-LOCK-ACTION-IND        PIC X(01).
             05  WS-LOCK-CMPNY-CD          PIC X(8).
             05  WS-LOCK-OUT-USER-ID       PIC X(10).
             05  WS-LOCK-USER-NM           PIC X(200).
             05  WS-LOCK-OUT-SQLCODE       PIC +(9)9.
             05  WS-LOCK-ERROR-AREA        PIC X(80).
             05  WS-LOCK-RC                PIC X(04).


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
                INCLUDE DPM1001
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
       01  LS-OLD-TEMPLATE-ID              PIC S9(09) COMP.
       01  LS-NEW-TEMPLATE-ID              PIC S9(09) COMP.
      *01  LS-TEMPLATE-NAME                PIC X(150).
       01  LS-TEMPLATE-NAME.
           49 LS-TEMPLATE-NAME-LEN         PIC S9(04) COMP.
           49 LS-TEMPLATE-NAME-TEXT        PIC X(498).
       01  LS-TEMPLATE-TYPE                PIC X(01).
       01  LS-CMPNY-CD                     PIC X(08).
       01  LS-USER-ID                      PIC X(10).
       01  LS-SAVE-IND                     PIC X(01).

       PROCEDURE DIVISION USING  OUTSQLCA,
                                 LS-SP-ERROR-AREA,
                                 LS-SP-RC,
                                 LS-OLD-TEMPLATE-ID,
                                 LS-NEW-TEMPLATE-ID,
                                 LS-TEMPLATE-NAME,
                                 LS-TEMPLATE-TYPE,
                                 LS-CMPNY-CD,
                                 LS-USER-ID,
                                 LS-SAVE-IND.

      *---------*
       1000-MAIN.
      *---------*

           PERFORM 1000-INIT-AND-CHECK-PARMS

           PERFORM 2000-SAVE-TEMPLATE

           PERFORM 9990-GOBACK
           .
      *-------------------------*
       1000-INIT-AND-CHECK-PARMS.
      *-------------------------*

           MOVE '1000-INIT-AND-CHECK-PARMS' TO WS-PARAGRAPH-NAME

           MOVE SPACES                      TO LS-SP-ERROR-AREA
                                               LS-SP-RC
           INITIALIZE WS-TEMPLATE-NAME
                      WS-PREV-AMND-ID
                      WS-PREV-VALUE-ID
                      WS-PREV-VALUE-TYPE-CD
           MOVE LS-OLD-TEMPLATE-ID          TO WS-OLD-TEMPLATE-ID
           MOVE LS-NEW-TEMPLATE-ID          TO WS-NEW-TEMPLATE-ID
      *    MOVE LS-TEMPLATE-NAME            TO WS-TEMPLATE-NAME
           MOVE LS-TEMPLATE-NAME-LEN        TO WS-TEMPLATE-NAME-LEN
           MOVE LS-TEMPLATE-NAME-TEXT(1:WS-TEMPLATE-NAME-LEN)
                                            TO WS-TEMPLATE-NAME-TEXT
           MOVE LS-TEMPLATE-TYPE            TO WS-TEMPLATE-TYPE
           MOVE LS-CMPNY-CD                 TO WS-CMPNY-CD
           MOVE LS-USER-ID                  TO WS-USER-ID
           MOVE LS-SAVE-IND                 TO WS-SAVE-IND

           PERFORM 9400-CHECK-DEBUG-TABLE

           IF DISPLAY-ACTIVE
              DISPLAY "****************************"
              DISPLAY "ENTERING TO PROGRAM DPMXMSAV"
              DISPLAY "****************************"
              EXEC SQL
                   SET :WS-CURRENT-TIMESTAMP = CURRENT TIMESTAMP
              END-EXEC

              DISPLAY 'STARTING TIME: '        WS-CURRENT-TIMESTAMP
              DISPLAY 'WS-OLD-TEMPLATE-ID '    WS-OLD-TEMPLATE-ID
              DISPLAY 'WS-NEW-TEMPLATE-ID '    WS-NEW-TEMPLATE-ID
              DISPLAY 'WS-TEMPLATE-NAME-LEN '  WS-TEMPLATE-NAME-LEN
              DISPLAY 'WS-TEMPLATE-NAME-TEXT ' WS-TEMPLATE-NAME-TEXT
              DISPLAY 'WS-TEMPLATE-TYPE '      WS-TEMPLATE-TYPE
              DISPLAY 'WS-CMPNY-CD '           WS-CMPNY-CD
              DISPLAY 'WS-USER-ID '            WS-USER-ID
              DISPLAY 'WS-SAVE-IND '           WS-SAVE-IND
           END-IF
           .
      *-------------------*
       2000-SAVE-TEMPLATE.
      *-------------------*

           MOVE '2000-SAVE-TEMPLATE'        TO WS-PARAGRAPH-NAME

           EVALUATE TRUE
              WHEN CHECK-TMPLT
              WHEN SAVE-TMPLT
              WHEN UNLOCK-TMPLT
                PERFORM 2025-CHECK-TEMPLATE-TYPE
              WHEN DELETE-TMPLT
                PERFORM 2050-DELETE-WORK-VALUES
              WHEN OTHER
                MOVE  WS-INVALID-SAVE-IND  TO LS-SP-ERROR-AREA
                MOVE  'SP50'               TO LS-SP-RC
                PERFORM 9990-GOBACK
           END-EVALUATE
           .
      *-------------------------*
       2025-CHECK-TEMPLATE-TYPE.
      *-------------------------*

           MOVE '2025-CHECK-TEMPLATE-TYPE'  TO WS-PARAGRAPH-NAME

           IF DISPLAY-ACTIVE
              DISPLAY 'WS-TEMPLATE-TYPE ' WS-TEMPLATE-TYPE
           END-IF

           EVALUATE TRUE
              WHEN ISDA-TMPLT
                PERFORM 2100-SAVE-ISDA-TEMPLATE
              WHEN DEALER-CUSTOMIZED
              WHEN CLIENT-CUSTOMIZED
              WHEN EXECUTED-TMPLT
                PERFORM 2200-SAVE-DEALER-TEMPLATE
              WHEN WORKING-TMPLT
              WHEN REEXEC-WORKING
                PERFORM 2300-SAVE-WORK-TEMPLATE
              WHEN OTHER
                 MOVE  WS-INVALID-TMPLT-TYPE
                                            TO LS-SP-ERROR-AREA
                 MOVE  'SP50'               TO LS-SP-RC
                 PERFORM 9990-GOBACK
           END-EVALUATE
           .
      *------------------------*
       2050-DELETE-WORK-VALUES.
      *------------------------*

           MOVE '2050-DELETE-WORK-VALUES'   TO WS-PARAGRAPH-NAME

           IF DISPLAY-ACTIVE
              DISPLAY WS-PARAGRAPH-NAME
              DISPLAY 'WS-OLD-TEMPLATE-ID ' WS-OLD-TEMPLATE-ID
           END-IF

           PERFORM 2250-DELETE-WORK

           IF WORK-TMPLT
              PERFORM 2400-DELETE-CTGRY-TERMS
           END-IF

           IF ISDA-TMPLT
              PERFORM 2450-UPDATE-TMPLT-STATUS
           END-IF

           PERFORM 9200-UNLOCK-OLD-TEMPLATE
           .
      *------------------------*
       2100-SAVE-ISDA-TEMPLATE.
      *------------------------*

           MOVE '2100-SAVE-ISDA-TEMPLATE'   TO WS-PARAGRAPH-NAME

           IF DISPLAY-ACTIVE
              DISPLAY WS-PARAGRAPH-NAME
           END-IF

           PERFORM 2110-SAVE-AMEND-DETAILS

           PERFORM 2110A-CHECK-WORK-DETAILS

           PERFORM 2111-DELETE-MSTR-LINK

           PERFORM 2220B-SAVE-NEW-LINKS

           PERFORM 9350-UPDATE-TEMPLATE

           PERFORM 2250-DELETE-WORK

           IF UNLOCK-TMPLT
              PERFORM 9200-UNLOCK-OLD-TEMPLATE
           END-IF
           .
      *------------------------*
       2110-SAVE-AMEND-DETAILS.
      *------------------------*

           MOVE '2110-SAVE-AMEND-DETAILS'   TO WS-PARAGRAPH-NAME

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
                           WHERE MCA_TMPLT_ID = :WS-OLD-TEMPLATE-ID)

           END-EXEC

           EVALUATE SQLCODE
              WHEN 0
                 MOVE 'SP00'                TO LS-SP-RC
              WHEN 100
                 CONTINUE
              WHEN OTHER
                 MOVE 'VDPM16_MCA_AMND'     TO WS-TABLE-NAME
                 PERFORM 9500-SQL-ERROR
           END-EVALUATE
           .
      *------------------------*
       2110A-CHECK-WORK-DETAILS.
      *------------------------*

           MOVE '2110A-CHECK-WORK-DETAILS'  TO WS-PARAGRAPH-NAME

           IF DISPLAY-ACTIVE
              DISPLAY WS-PARAGRAPH-NAME
           END-IF

           EXEC SQL
                SELECT 1
                  INTO :WS-LINK-CHECK
                  FROM VDPM16_MCA_AMND         DPM16
                      ,VDPM19_LINK_WORK        DPM19
                 WHERE DPM16.MCA_TMPLT_ID = :WS-OLD-TEMPLATE-ID
                   AND DPM16.MCA_AMND_ID  = DPM19.MCA_AMND_ID
                   AND DPM19.MCA_ACCS_STAT_CD = 'U'
                   FETCH FIRST ROW ONLY
           END-EXEC

           EVALUATE SQLCODE
              WHEN 0
                 MOVE 'SP00'                TO LS-SP-RC
              WHEN 100
                 MOVE 'SP05'                TO LS-SP-RC
                 MOVE WS-NO-WORK-VALUE      TO LS-SP-ERROR-AREA
                 MOVE WS-OLD-TEMPLATE-ID    TO WS-NEW-TEMPLATE-ID
                 PERFORM 9990-GOBACK
              WHEN OTHER
                 MOVE 'VDPM18_MCA_LINK'     TO WS-TABLE-NAME
                 PERFORM 9500-SQL-ERROR
           END-EVALUATE
           .
      *----------------------*
       2111-DELETE-MSTR-LINK.
      *----------------------*

           MOVE '2111-DELETE-MSTR-LINK'     TO WS-PARAGRAPH-NAME

           IF DISPLAY-ACTIVE
              DISPLAY WS-PARAGRAPH-NAME
           END-IF

           EXEC SQL
                 DELETE FROM VDPM18_MCA_LINK DPM18
                  WHERE DPM18.MCA_AMND_ID IN
                       (SELECT DPM19.MCA_AMND_ID
                          FROM VDPM16_MCA_AMND    DPM16
                              ,VDPM19_LINK_WORK   DPM19
                         WHERE DPM16.MCA_TMPLT_ID = :WS-OLD-TEMPLATE-ID
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
                 PERFORM 9500-SQL-ERROR
           END-EVALUATE

           IF DISPLAY-ACTIVE
              DISPLAY "DELETE SQLCODE " SQLCODE
           END-IF
           .
      *--------------------------*
       2200-SAVE-DEALER-TEMPLATE.
      *--------------------------*

           MOVE '2200-SAVE-DEALER-TEMPLATE' TO WS-PARAGRAPH-NAME

           PERFORM 2210-GET-TEMPLATE-DETAILS

           PERFORM 2211-CHECK-WORK-VALUE

           IF DISPLAY-ACTIVE
              DISPLAY 'WS-SAVE-DC-TEMPLATE' WS-SAVE-DC-TEMPLATE
              DISPLAY 'WS-TMPLT-TYPES ' WS-TMPLT-TYPES
              DISPLAY 'WS-USER-STATUS ' WS-USER-STATUS
              DISPLAY 'WS-OVER-WRITE-STATUS ' WS-OVER-WRITE-STAT
           END-IF

           IF (WORK-TMPLT-TYPE OR TMPLT-ID-SAME OR TMPLT-ID-DIFF OR
              NOT-IN-MSTR-TABLE) AND  LOCKED-USER
              PERFORM 2205-SAVE-D-WORK-TEMPLATE
           ELSE
              PERFORM 2205A-SAVE-D-MAIN-TEMPLATE
           END-IF

           IF UNLOCK-TMPLT
              PERFORM 9200-UNLOCK-OLD-TEMPLATE
           END-IF
           .
      *-------------------------*
       2205-SAVE-D-WORK-TEMPLATE.
      *-------------------------*

           MOVE '2205-SAVE-D-WORK-TEMPLATE' TO WS-PARAGRAPH-NAME

           EVALUATE TRUE
               WHEN NOT-IN-MSTR-TABLE
                 IF WORK-TMPLT
                    PERFORM 2220-SAVE-NEW-TEMPLATE
                 ELSE
                    SET OVER-WRITE-LOCKED   TO TRUE
                    PERFORM 2225-SAVE-AS-NEW-TEMPLATE
                 END-IF

               WHEN TMPLT-ID-SAME
                 PERFORM 2230-UPDATE-EXIST-TEMPLATE

               WHEN TMPLT-ID-DIFF
                 SET OVER-WRITE-LOCKED      TO TRUE
                 PERFORM 2240-SAVE-EXIST-TEMPLATE

               WHEN OTHER
                 MOVE  WS-ERROR-AREA        TO LS-SP-ERROR-AREA
                 PERFORM 9990-GOBACK
           END-EVALUATE

           IF LOCKED-USER
              PERFORM 2250-DELETE-WORK
           END-IF

           .
      *--------------------------*
       2205A-SAVE-D-MAIN-TEMPLATE.
      *--------------------------*

           MOVE '2205A-SAVE-D-MAIN-TEMPLATE'
                                            TO WS-PARAGRAPH-NAME

           EVALUATE TRUE
               WHEN NOT-IN-MSTR-TABLE
                 PERFORM 9100-SAVE-GENERIC-TEMPLATE
               WHEN TMPLT-ID-SAME
                 MOVE  'SP02'               TO LS-SP-RC
                 MOVE WS-TMPLT-LOCKED       TO LS-SP-ERROR-AREA
                 MOVE WS-OLD-TEMPLATE-ID    TO WS-NEW-TEMPLATE-ID
                 PERFORM 9990-GOBACK

               WHEN TMPLT-ID-DIFF
                 SET OVER-WRITE-OTHER       TO TRUE
                 PERFORM 2240-SAVE-EXIST-TEMPLATE

               WHEN OTHER
                 MOVE  WS-ERROR-AREA        TO LS-SP-ERROR-AREA
                 PERFORM 9990-GOBACK
           END-EVALUATE

           IF LOCKED-USER
              PERFORM 2250-DELETE-WORK
           END-IF

           .
      *-------------------------*
       2210-GET-TEMPLATE-DETAILS.
      *-------------------------*

           MOVE '2210-GET-TEMPLATE-DETAILS' TO WS-PARAGRAPH-NAME

           IF DISPLAY-ACTIVE
              DISPLAY WS-PARAGRAPH-NAME
           END-IF

           PERFORM 2210A-CHECK-TEMPLATE-NAME

           IF WS-NEW-TEMPLATE-ID > 0
              PERFORM 2210B-CHECK-LOCK
           END-IF

           PERFORM 2210C-CHECK-OLD-TMPLT-LOCK
           .
      *-------------------------*
       2210A-CHECK-TEMPLATE-NAME.
      *-------------------------*

           MOVE '2210A-CHECK-TEMPLATE-NAME' TO WS-PARAGRAPH-NAME

      *    MOVE FUNCTION UPPER-CASE(WS-TEMPLATE-NAME)
      *                                     TO WS-TEMPLATE-NAME-UPPER
           MOVE FUNCTION UPPER-CASE(WS-TEMPLATE-NAME-TEXT)
                                            TO WS-TEMPLATE-NAME-UPPER
           PERFORM 2210AA-GET-PROD-SPROD

           IF DISPLAY-ACTIVE
              DISPLAY WS-PARAGRAPH-NAME
              DISPLAY "TEMPLATE NAME UPPER " WS-TEMPLATE-NAME-UPPER
           END-IF

           EXEC SQL
                SELECT MCA_TMPLT_ID
                      ,MCA_TMPLT_TYPE_CD
                  INTO :D014-MCA-TMPLT-ID
                      ,:D014-MCA-TMPLT-TYPE-CD
                  FROM D0006
                 WHERE UPPER(MCA_TMPLT_NM) = :WS-TEMPLATE-NAME-UPPER
                   AND DELR_CMPNY_ID       = :WS-CMPNY-CD
                   AND ATTRB_PRDCT_ID      = :D014-ATTRB-PRDCT-ID
                   AND ATTRB_SUB_PRDCT_ID  = :D014-ATTRB-SUB-PRDCT-ID
                   AND ATTRB_REGN_ID       = :D014-ATTRB-REGN-ID
                FETCH FIRST ROW ONLY
                WITH UR
           END-EXEC

           EVALUATE SQLCODE
              WHEN 0
                 IF DISPLAY-ACTIVE
                    DISPLAY 'NEW TEMPLATE ID'    D014-MCA-TMPLT-ID
                    DISPLAY 'TEMPLATE TYPE CODE' D014-MCA-TMPLT-TYPE-CD
                 END-IF
                 IF D014-MCA-TMPLT-TYPE-CD = 'D'
                    MOVE D014-MCA-TMPLT-ID  TO WS-NEW-TEMPLATE-ID
                 ELSE
                    MOVE  'SP01'            TO LS-SP-RC
                    MOVE WS-CANNOT-SAVE     TO LS-SP-ERROR-AREA
                    MOVE WS-OLD-TEMPLATE-ID TO WS-NEW-TEMPLATE-ID
                    PERFORM 9990-GOBACK
                 END-IF
              WHEN 100
                 SET NOT-IN-MSTR-TABLE      TO TRUE
              WHEN OTHER
                 MOVE 'D0006'    TO WS-TABLE-NAME
                 PERFORM 9500-SQL-ERROR
           END-EVALUATE

           IF DISPLAY-ACTIVE
              DISPLAY "INSIDE 2210A-CHECK-TEMPLATE-NAME PARA"
              DISPLAY "WS-SAVE-DC-TEMPLATE " WS-SAVE-DC-TEMPLATE
              DISPLAY "WS-NEW-TEMPLATE-ID "  WS-NEW-TEMPLATE-ID
           END-IF
           .
      *---------------------*
       2210AA-GET-PROD-SPROD.
      *---------------------*

           MOVE '2210AA-GET-PROD-SPROD'     TO WS-PARAGRAPH-NAME

           PERFORM 9375-CHECK-TEMPLATE-ID

           IF MAIN-TMPLT
              EXEC SQL
                   SELECT  ATTRB_PRDCT_ID
                          ,ATTRB_SUB_PRDCT_ID
                          ,ATTRB_REGN_ID
                     INTO :D014-ATTRB-PRDCT-ID
                         ,:D014-ATTRB-SUB-PRDCT-ID
                         ,:D014-ATTRB-REGN-ID
                     FROM D0006
                    WHERE MCA_TMPLT_ID = :WS-OLD-TEMPLATE-ID
              END-EXEC
           ELSE
              EXEC SQL
                   SELECT  ATTRB_PRDCT_ID
                          ,ATTRB_SUB_PRDCT_ID
                          ,ATTRB_REGN_ID
                     INTO :D014-ATTRB-PRDCT-ID
                         ,:D014-ATTRB-SUB-PRDCT-ID
                         ,:D014-ATTRB-REGN-ID
                     FROM VDPM15_TMPLT_WORK
                    WHERE MCA_TMPLT_ID = :WS-OLD-TEMPLATE-ID
              END-EXEC
           END-IF

           EVALUATE SQLCODE
              WHEN 0
                 CONTINUE
              WHEN OTHER
                 IF MAIN-TMPLT
                    MOVE 'D0006' TO WS-TABLE-NAME
                 ELSE
                    MOVE 'VDPM15_TMPLT_WORK'
                                            TO WS-TABLE-NAME
                 END-IF
                 PERFORM 9500-SQL-ERROR
           END-EVALUATE
           .
      *-----------------*
       2210B-CHECK-LOCK.
      *-----------------*

           MOVE '2210B-CHECK-LOCK'          TO WS-PARAGRAPH-NAME

           IF DISPLAY-ACTIVE
              DISPLAY WS-PARAGRAPH-NAME
           END-IF

           EXEC SQL
                SELECT CMPNY_ID
                      ,CMPNY_USER_ID
                  INTO :D010-CMPNY-ID
                      ,:D010-CMPNY-USER-ID
                  FROM VDPM10_MCA_LOCK
                 WHERE MCA_TMPLT_ID = :WS-NEW-TEMPLATE-ID
           END-EXEC

           EVALUATE SQLCODE
              WHEN 0
                 IF D010-CMPNY-ID      = WS-CMPNY-CD AND
                    D010-CMPNY-USER-ID = WS-USER-ID
                    PERFORM 2210BA-SET-SAVE-IND
                 ELSE
                    MOVE  'SP02'            TO LS-SP-RC
                    MOVE WS-TMPLT-LOCKED    TO LS-SP-ERROR-AREA
                    MOVE WS-OLD-TEMPLATE-ID TO WS-NEW-TEMPLATE-ID
                    PERFORM 9990-GOBACK
                 END-IF
                 SET TEMPLATE-LOCKED        TO TRUE
              WHEN 100
                 SET TEMPLATE-NOT-LOCKED    TO TRUE
              WHEN OTHER
                 MOVE 'VDPM10_MCA_LOCK'     TO WS-TABLE-NAME
                 PERFORM 9500-SQL-ERROR
           END-EVALUATE

           IF DISPLAY-ACTIVE
              DISPLAY "INSIDE 2210B-CHECK-LOCK PARA"
              DISPLAY "WS-SAVE-DC-TEMPLATE " WS-SAVE-DC-TEMPLATE
           END-IF

           IF WS-SAVE-DC-TEMPLATE NOT = 'N'
              IF WS-OLD-TEMPLATE-ID = WS-NEW-TEMPLATE-ID
                 SET TMPLT-ID-SAME          TO TRUE
                 IF NOT UNLOCK-TMPLT
                    SET SAVE-TMPLT          TO TRUE
                 END-IF
              ELSE
                 IF SAVE-TMPLT OR UNLOCK-TMPLT
                    SET TMPLT-ID-DIFF       TO TRUE
                 ELSE
                    MOVE  WS-TMPLT-EXIST    TO LS-SP-ERROR-AREA
                    MOVE  'SP04'            TO LS-SP-RC
                    MOVE WS-OLD-TEMPLATE-ID TO WS-NEW-TEMPLATE-ID
                    PERFORM 9990-GOBACK
                 END-IF
              END-IF
           END-IF
           .
      *---------------------------*
       2210C-CHECK-OLD-TMPLT-LOCK.
      *---------------------------*

           MOVE '2210C-CHECK-OLD-TMPLT-LOCK'
                                            TO WS-PARAGRAPH-NAME

           IF DISPLAY-ACTIVE
              DISPLAY WS-PARAGRAPH-NAME
           END-IF

           SET LOCKED-USER                  TO TRUE

           EXEC SQL
                SELECT CMPNY_ID
                      ,CMPNY_USER_ID
                  INTO :D010-CMPNY-ID
                      ,:D010-CMPNY-USER-ID
                  FROM VDPM10_MCA_LOCK
                 WHERE MCA_TMPLT_ID = :WS-OLD-TEMPLATE-ID
           END-EXEC

           EVALUATE SQLCODE
              WHEN 0
                 IF D010-CMPNY-ID      = WS-CMPNY-CD AND
                    D010-CMPNY-USER-ID = WS-USER-ID
                    SET LOCKED-USER         TO TRUE
                 ELSE
                    SET OTHER-USER          TO TRUE
                 END-IF
              WHEN 100
                 CONTINUE
              WHEN OTHER
                 MOVE 'VDPM10_MCA_LOCK'     TO WS-TABLE-NAME
                 PERFORM 9500-SQL-ERROR
           END-EVALUATE
           .
      *--------------------*
       2210BA-SET-SAVE-IND.
      *--------------------*

           MOVE '2210BA-SET-SAVE-IND'       TO WS-PARAGRAPH-NAME

           IF WS-OLD-TEMPLATE-ID = WS-NEW-TEMPLATE-ID
              IF NOT UNLOCK-TMPLT
                 SET SAVE-TMPLT          TO TRUE
              END-IF
           ELSE
              MOVE  'SP02'               TO LS-SP-RC
              MOVE WS-TMPLT-LOCKED       TO LS-SP-ERROR-AREA
              MOVE WS-OLD-TEMPLATE-ID    TO WS-NEW-TEMPLATE-ID
              PERFORM 9990-GOBACK
           END-IF
           .
      *----------------------*
       2211-CHECK-WORK-VALUE.
      *----------------------*

           MOVE '2211-CHECK-WORK-VALUE'     TO WS-PARAGRAPH-NAME

           IF WORK-TMPLT
              SET WORK-TMPLT-TYPE           TO TRUE
           ELSE
              PERFORM 2211A-CHECK-LINK-WORK
           END-IF
           .
      *----------------------*
       2211A-CHECK-LINK-WORK.
      *----------------------*

           MOVE '2211A-CHECK-LINK-WORK'     TO WS-PARAGRAPH-NAME

           EXEC SQL
                SELECT 1
                  INTO :WS-TMPLT-CHECK
                  FROM VDPM19_LINK_WORK   DPM19
                      ,VDPM16_MCA_AMND    DPM16
                 WHERE DPM19.MCA_AMND_ID  = DPM16.MCA_AMND_ID
                   AND DPM16.MCA_TMPLT_ID = :WS-OLD-TEMPLATE-ID
                 FETCH FIRST ROW ONLY
           END-EXEC

           EVALUATE SQLCODE
              WHEN 0
                 SET WORK-TMPLT-TYPE        TO TRUE
              WHEN 100
                 SET MAIN-TMPLT-TYPE        TO TRUE
              WHEN OTHER
                 MOVE 'VDPM19_LINK_WORK'    TO WS-TABLE-NAME
                 PERFORM 9500-SQL-ERROR
           END-EVALUATE
           .
      *------------------------*
       2220-SAVE-NEW-TEMPLATE.
      *------------------------*

           MOVE '2220-SAVE-NEW-TEMPLATE'    TO WS-PARAGRAPH-NAME
      *    MOVE WS-TEMPLATE-NAME            TO D014-MCA-TMPLT-NM
           MOVE WS-TEMPLATE-NAME-LEN        TO D014-MCA-TMPLT-NM-LEN
           MOVE WS-TEMPLATE-NAME-TEXT       TO D014-MCA-TMPLT-NM-TEXT
           MOVE WS-CMPNY-CD                 TO D014-DELR-CMPNY-ID

           IF DISPLAY-ACTIVE
              DISPLAY WS-PARAGRAPH-NAME
           END-IF

           EXEC SQL
                INSERT INTO D0006
                        (SELECT MCA_TMPLT_ID
                               ,:D014-MCA-TMPLT-NM
                               ,MCA_TMPLT_SHORT_NM
                               ,MCA_TMPLT_GROUP_CD
                               ,MCA_TMPLT_TYPE_CD
                               ,:D014-DELR-CMPNY-ID
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
                               ,CURRENT TIMESTAMP
                               ,ROW_UPDT_USER_ID
                         FROM VDPM15_TMPLT_WORK
                       WHERE MCA_TMPLT_ID = :WS-OLD-TEMPLATE-ID)
           END-EXEC

           EVALUATE SQLCODE
              WHEN 0
                 MOVE 'SP00'                TO LS-SP-RC
                 PERFORM 2220A-SAVE-NEW-AMND
                 SET SAVE-TMPLT             TO TRUE
              WHEN OTHER
                 MOVE 'D0006'    TO WS-TABLE-NAME
                 PERFORM 9500-SQL-ERROR
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
                           WHERE MCA_TMPLT_ID = :WS-OLD-TEMPLATE-ID)

           END-EXEC

           EVALUATE SQLCODE
              WHEN 0
                 MOVE 'SP00'                TO LS-SP-RC
                 PERFORM 2220B-SAVE-NEW-LINKS
              WHEN OTHER
                 MOVE 'VDPM16_MCA_AMND'     TO WS-TABLE-NAME
                 PERFORM 9500-SQL-ERROR
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
                           WHERE DPM16.MCA_TMPLT_ID =
                                                     :WS-OLD-TEMPLATE-ID
                             AND DPM16.MCA_AMND_ID = DPM19.MCA_AMND_ID
                             AND DPM19.MCA_ACCS_STAT_CD = 'U')
           END-EXEC

           EVALUATE SQLCODE
              WHEN 0
                 MOVE 'SP00'                TO LS-SP-RC
              WHEN OTHER
                 MOVE 'VDPM18_MCA_LINK'     TO WS-TABLE-NAME
                 PERFORM 9500-SQL-ERROR
           END-EVALUATE
           .
      *--------------------------*
       2225-SAVE-AS-NEW-TEMPLATE.
      *--------------------------*

           MOVE '2225-SAVE-AS-NEW-TEMPLATE' TO WS-PARAGRAPH-NAME

           IF DISPLAY-ACTIVE
              DISPLAY WS-PARAGRAPH-NAME
           END-IF

           PERFORM 9110-GET-TMPLT-SEQ

           PERFORM 9120-INSERT-NEW-TMPLT

           SET LINK-NOT-CREATE              TO TRUE
           PERFORM 9130-CREATE-STATIC-GRID

           PERFORM 2240C-GET-OLD-TMPLT-VALUES

           .
      *--------------------------*
       2230-UPDATE-EXIST-TEMPLATE.
      *--------------------------*

           MOVE '2230-UPDATE-EXIST-TEMPLATE'
                                            TO WS-PARAGRAPH-NAME

           IF DISPLAY-ACTIVE
              DISPLAY WS-PARAGRAPH-NAME
           END-IF

           IF SAVE-TMPLT OR UNLOCK-TMPLT
              PERFORM 2230A-COPY-LINKS
           ELSE
              MOVE  WS-INVALID-SAVE-IND     TO LS-SP-ERROR-AREA
              MOVE  'SP50'                  TO LS-SP-RC
              PERFORM 9990-GOBACK
           END-IF

           .
      *-----------------*
       2230A-COPY-LINKS.
      *-----------------*

           MOVE '2230A-COPY-LINKS'          TO WS-PARAGRAPH-NAME

           IF DISPLAY-ACTIVE
              DISPLAY WS-PARAGRAPH-NAME
           END-IF

           PERFORM 2230B-OPEN-LINK-CURSOR

           SET NOT-EOF-RECORD               TO TRUE
           INITIALIZE WS-PREV-AMND-ID

           PERFORM UNTIL EOF-RECORD

              PERFORM 2230C-FETCH-LINK-CURSOR

              EVALUATE SQLCODE
                 WHEN 0
                    MOVE 'SP00'             TO LS-SP-RC
                    PERFORM 2230D-UPDATE-LINKS
                 WHEN 100
                    SET EOF-RECORD          TO TRUE
                 WHEN OTHER
                    MOVE 'AMND_CSR'         TO WS-TABLE-NAME
                    PERFORM 9500-SQL-ERROR
              END-EVALUATE
           END-PERFORM

           IF TEMPLATE-NOT-LOCKED
              PERFORM 9300-LOCK-NEW-TEMPLATE
           END-IF
           .
      *----------------------*
       2230B-OPEN-LINK-CURSOR.
      *----------------------*

           MOVE '2230B-OPEN-LINK-CURSOR'    TO WS-PARAGRAPH-NAME

           IF DISPLAY-ACTIVE
              DISPLAY WS-PARAGRAPH-NAME
           END-IF

           EXEC SQL
                DECLARE AMND_CSR CURSOR FOR
                   SELECT DPM19.MCA_AMND_ID
                         ,DPM19.MCA_VALUE_ID
                         ,DPM19.MCA_VALUE_TYPE_CD
                         ,DPM19.AMND_STAT_CD
                         ,DPM19.ROW_UPDT_TS
                         ,DPM19.ROW_UPDT_USER_ID
                     FROM VDPM19_LINK_WORK   DPM19
                         ,VDPM16_MCA_AMND    DPM16
                    WHERE DPM19.MCA_AMND_ID  = DPM16.MCA_AMND_ID
                      AND DPM19.MCA_ACCS_STAT_CD = 'U'
                      AND DPM16.MCA_TMPLT_ID = :WS-OLD-TEMPLATE-ID
                  WITH UR
           END-EXEC

           EXEC SQL
                OPEN AMND_CSR
           END-EXEC

           EVALUATE SQLCODE
              WHEN 0
                 MOVE 'SP00'                TO LS-SP-RC
              WHEN OTHER
                 MOVE 'AMND_CSR'            TO WS-TABLE-NAME
                 PERFORM 9500-SQL-ERROR
           END-EVALUATE
           .
      *-----------------------*
       2230C-FETCH-LINK-CURSOR.
      *-----------------------*

           MOVE '2230C-FETCH-LINK-CURSOR'   TO WS-PARAGRAPH-NAME

           IF DISPLAY-ACTIVE
              DISPLAY WS-PARAGRAPH-NAME
           END-IF

           EXEC SQL
                FETCH AMND_CSR
                 INTO :D019-MCA-AMND-ID
                     ,:D019-MCA-VALUE-ID
                     ,:D019-MCA-VALUE-TYPE-CD
                     ,:D019-AMND-STAT-CD
                     ,:D019-ROW-UPDT-TS
                     ,:D019-ROW-UPDT-USER-ID
           END-EXEC
           .
      *-------------------*
       2230D-UPDATE-LINKS.
      *-------------------*

           MOVE '2230D-UPDATE-LINKS'        TO WS-PARAGRAPH-NAME

           IF DISPLAY-ACTIVE
              DISPLAY WS-PARAGRAPH-NAME
              DISPLAY 'D019-MCA-AMND-ID :' D019-MCA-AMND-ID
              DISPLAY 'WS-PREV-AMND-ID  :' WS-PREV-AMND-ID
           END-IF

           IF D019-MCA-AMND-ID NOT = WS-PREV-AMND-ID
              PERFORM 2230DA-DELETE-LINKS
           END-IF

           PERFORM 2230E-INSERT-LINKS
           .
      *-------------------*
       2230DA-DELETE-LINKS.
      *-------------------*

           MOVE '2230DA-DELETE-LINKS'       TO WS-PARAGRAPH-NAME

           IF DISPLAY-ACTIVE
              DISPLAY WS-PARAGRAPH-NAME
           END-IF

           EXEC SQL
                DELETE FROM VDPM18_MCA_LINK
                 WHERE MCA_AMND_ID = :D019-MCA-AMND-ID
           END-EXEC

           IF DISPLAY-ACTIVE
              DISPLAY 'DELETE SQLCODE ' SQLCODE
           END-IF

           EVALUATE SQLCODE
              WHEN 0
                 MOVE 'SP00'                TO LS-SP-RC
                 MOVE D019-MCA-AMND-ID      TO WS-PREV-AMND-ID
              WHEN OTHER
                 MOVE 'VDPM18_MCA_LINK'     TO WS-TABLE-NAME
                 PERFORM 9500-SQL-ERROR
           END-EVALUATE
           .
      *------------------*
       2230E-INSERT-LINKS.
      *------------------*

           MOVE '2230E-INSERT-LINKS'        TO WS-PARAGRAPH-NAME

           IF DISPLAY-ACTIVE
              DISPLAY WS-PARAGRAPH-NAME
           END-IF

           MOVE WS-USER-ID                 TO D019-ROW-UPDT-USER-ID

           EXEC SQL
                INSERT INTO VDPM18_MCA_LINK
                                 (MCA_AMND_ID
                                 ,MCA_VALUE_ID
                                 ,MCA_VALUE_TYPE_CD
                                 ,AMND_STAT_CD
                                 ,ROW_UPDT_TS
                                 ,ROW_UPDT_USER_ID)
                         VALUES (:D019-MCA-AMND-ID
                                ,:D019-MCA-VALUE-ID
                                ,:D019-MCA-VALUE-TYPE-CD
                                ,:D019-AMND-STAT-CD
                                ,CURRENT_TIMESTAMP
                                ,:D019-ROW-UPDT-USER-ID)
           END-EXEC

           EVALUATE SQLCODE
              WHEN 0
                 MOVE 'SP00'                TO LS-SP-RC
              WHEN OTHER
                 MOVE 'VDPM18_MCA_LINK'     TO WS-TABLE-NAME
                 PERFORM 9500-SQL-ERROR
           END-EVALUATE
           .
      *-------------------------*
       2240-SAVE-EXIST-TEMPLATE.
      *-------------------------*

           MOVE '2240-SAVE-EXIST-TEMPLATE'  TO WS-PARAGRAPH-NAME

           IF DISPLAY-ACTIVE
              DISPLAY WS-PARAGRAPH-NAME
           END-IF

           PERFORM 2240A-DELETE-NEW-TMPLT-LINK

           PERFORM 2240B-UPDATE-NEW-TMPLT-LINK

           .
      *---------------------------*
       2240A-DELETE-NEW-TMPLT-LINK.
      *---------------------------*

           MOVE '2240A-DELETE-NEW-TMPLT-LINK'
                                            TO WS-PARAGRAPH-NAME

           IF DISPLAY-ACTIVE
              DISPLAY WS-PARAGRAPH-NAME
           END-IF

           MOVE WS-NEW-TEMPLATE-ID          TO D016-MCA-TMPLT-ID

           EXEC SQL
                DELETE FROM VDPM18_MCA_LINK DPM18A
                 WHERE DPM18A.MCA_AMND_ID IN
                              (SELECT DPM16.MCA_AMND_ID
                                 FROM VDPM16_MCA_AMND       DPM16
                                     ,VDPM18_MCA_LINK       DPM18
                                WHERE DPM16.MCA_TMPLT_ID =
                                                      :D016-MCA-TMPLT-ID
                                  AND DPM16.MCA_AMND_ID  =
                                                    DPM18.MCA_AMND_ID)
           END-EXEC

           EVALUATE SQLCODE
              WHEN 0
                 MOVE 'SP00'                TO LS-SP-RC
              WHEN OTHER
                 MOVE 'VDPM18_MCA_LINK'     TO WS-TABLE-NAME
                 PERFORM 9500-SQL-ERROR
           END-EVALUATE
           .
      *---------------------------*
       2240B-UPDATE-NEW-TMPLT-LINK.
      *---------------------------*

           MOVE '2240B-UPDATE-NEW-TMPLT-LINK'
                                            TO WS-PARAGRAPH-NAME

           IF DISPLAY-ACTIVE
              DISPLAY WS-PARAGRAPH-NAME
           END-IF

           PERFORM 2240C-GET-OLD-TMPLT-VALUES

           .
      *----------------------------*
       2240C-GET-OLD-TMPLT-VALUES.
      *----------------------------*

           MOVE '2240C-GET-OLD-TMPLT-VALUES'
                                            TO WS-PARAGRAPH-NAME

           IF DISPLAY-ACTIVE
              DISPLAY WS-PARAGRAPH-NAME
              DISPLAY "WS-TMPLT-STAT " WS-TMPLT-STAT
              DISPLAY "WS-OVER-WRITE-STATUS " WS-OVER-WRITE-STAT
           END-IF

           IF MAIN-TMPLT
              PERFORM 2240CA-OPEN-OLD-TMPLT-MAIN
              PERFORM 2240CB-FETCH-OLD-TMPLT-MAIN
              PERFORM 2240CD-CLOSE-OLD-TMPLT-MAIN
           ELSE
              PERFORM 2240DA-OPEN-OLD-TMPLT-WORK
              PERFORM 2240DB-FETCH-OLD-TMPLT-WORK
              PERFORM 2240DD-CLOSE-OLD-TMPLT-WORK
           END-IF

           IF OVER-WRITE-LOCKED
              PERFORM 9200-UNLOCK-OLD-TEMPLATE
           END-IF

           PERFORM 9300-LOCK-NEW-TEMPLATE
           .
      *---------------------------*
       2240CA-OPEN-OLD-TMPLT-MAIN.
      *---------------------------*

           MOVE '2240CA-OPEN-OLD-TMPLT-MAIN'
                                            TO WS-PARAGRAPH-NAME

           IF DISPLAY-ACTIVE
              DISPLAY WS-PARAGRAPH-NAME
           END-IF

           IF OVER-WRITE-LOCKED
              EXEC SQL
                   DECLARE OLD_TMPLT_M_CSR CURSOR FOR
                    SELECT DPM16.ATTRB_CTGRY_ID
                          ,DPM16.CTGRY_SQ
                          ,DPM16.ATTRB_TERM_ID
                          ,DPM16.TERM_SQ
                          ,COALESCE(DPM19.MCA_VALUE_ID,
                                    DPM18.MCA_VALUE_ID)
                          ,COALESCE(DPM19.MCA_VALUE_TYPE_CD,
                                    DPM18.MCA_VALUE_TYPE_CD)
                          ,COALESCE(DPM19.AMND_STAT_CD,
                                    DPM18.AMND_STAT_CD)
                          ,COALESCE(DPM19.ROW_UPDT_TS,
                                    DPM18.ROW_UPDT_TS)
                          ,COALESCE(DPM19.ROW_UPDT_USER_ID,                ID)
                                    DPM18.ROW_UPDT_USER_ID)                ID)
                      FROM            VDPM16_MCA_AMND         DPM16
                      LEFT OUTER JOIN VDPM19_LINK_WORK        DPM19
                        ON DPM16.MCA_AMND_ID = DPM19.MCA_AMND_ID
                      LEFT OUTER JOIN VDPM18_MCA_LINK         DPM18
                        ON DPM16.MCA_AMND_ID = DPM18.MCA_AMND_ID
                     WHERE DPM16.MCA_TMPLT_ID = :WS-OLD-TEMPLATE-ID
                     WITH UR
              END-EXEC

              EXEC SQL
                   OPEN OLD_TMPLT_M_CSR
              END-EXEC

              EVALUATE SQLCODE
                 WHEN 0
                    MOVE 'SP00'                TO LS-SP-RC
                 WHEN OTHER
                    MOVE 'OLD_TMPLT_CSR'       TO WS-TABLE-NAME
                    PERFORM 9500-SQL-ERROR
              END-EVALUATE
           ELSE
              EXEC SQL
                   DECLARE OLD_TMPLT_O_CSR CURSOR FOR
                    SELECT DPM16.ATTRB_CTGRY_ID
                          ,DPM16.CTGRY_SQ
                          ,DPM16.ATTRB_TERM_ID
                          ,DPM16.TERM_SQ
                          ,DPM18.MCA_VALUE_ID
                          ,DPM18.MCA_VALUE_TYPE_CD
                          ,DPM18.AMND_STAT_CD
                          ,DPM18.ROW_UPDT_TS
                          ,DPM18.ROW_UPDT_USER_ID                          ID)
                      FROM       VDPM16_MCA_AMND         DPM16
                      INNER JOIN VDPM18_MCA_LINK         DPM18
                        ON DPM16.MCA_AMND_ID = DPM18.MCA_AMND_ID
                     WHERE DPM16.MCA_TMPLT_ID = :WS-OLD-TEMPLATE-ID
                     WITH UR
              END-EXEC

              EXEC SQL
                   OPEN OLD_TMPLT_O_CSR
              END-EXEC

              EVALUATE SQLCODE
                 WHEN 0
                    MOVE 'SP00'                TO LS-SP-RC
                 WHEN OTHER
                    MOVE 'OLD_TMPLT_O_CSR'     TO WS-TABLE-NAME
                    PERFORM 9500-SQL-ERROR
              END-EVALUATE
           END-IF
           .
      *-----------------------------*
       2240CB-FETCH-OLD-TMPLT-MAIN.
      *-----------------------------*

           MOVE '2240CB-FETCH-OLD-TMPLT-MAIN'
                                            TO WS-PARAGRAPH-NAME

           IF DISPLAY-ACTIVE
              DISPLAY WS-PARAGRAPH-NAME
           END-IF

           SET NOT-EOF-RECORD               TO TRUE

           PERFORM UNTIL EOF-RECORD

              IF OVER-WRITE-LOCKED
                 EXEC SQL
                      FETCH OLD_TMPLT_M_CSR
                       INTO :D016-ATTRB-CTGRY-ID
                           ,:D016-CTGRY-SQ
                           ,:D016-ATTRB-TERM-ID
                           ,:D016-TERM-SQ
                           ,:D018-MCA-VALUE-ID
                           ,:D018-MCA-VALUE-TYPE-CD
                           ,:D018-AMND-STAT-CD
                           ,:D018-ROW-UPDT-TS
                           ,:D018-ROW-UPDT-USER-ID
                 END-EXEC
              ELSE
                 EXEC SQL
                      FETCH OLD_TMPLT_O_CSR
                       INTO :D016-ATTRB-CTGRY-ID
                           ,:D016-CTGRY-SQ
                           ,:D016-ATTRB-TERM-ID
                           ,:D016-TERM-SQ
                           ,:D018-MCA-VALUE-ID
                           ,:D018-MCA-VALUE-TYPE-CD
                           ,:D018-AMND-STAT-CD
                           ,:D018-ROW-UPDT-TS
                           ,:D018-ROW-UPDT-USER-ID
                 END-EXEC
              END-IF

              EVALUATE SQLCODE
                 WHEN 0
                    MOVE 'SP00'             TO LS-SP-RC
                    PERFORM 2240E-INSERT-NEW-TMPLT-LINK
                 WHEN 100
                    SET EOF-RECORD          TO TRUE
                 WHEN OTHER
                    IF OVER-WRITE-LOCKED
                       MOVE 'OLD_TMPLT_M_CSR'  TO WS-TABLE-NAME
                    ELSE
                       MOVE 'OLD_TMPLT_O_CSR'  TO WS-TABLE-NAME
                    END-IF
                    PERFORM 9500-SQL-ERROR
              END-EVALUATE
           END-PERFORM
           .
      *-----------------------------*
       2240CD-CLOSE-OLD-TMPLT-MAIN.
      *-----------------------------*

           MOVE '2240CD-CLOSE-OLD-TMPLT-MAIN'
                                            TO WS-PARAGRAPH-NAME

           IF OVER-WRITE-LOCKED
              EXEC SQL
                   CLOSE OLD_TMPLT_M_CSR
              END-EXEC
           ELSE
              EXEC SQL
                   CLOSE OLD_TMPLT_O_CSR
              END-EXEC
           END-IF

           EVALUATE SQLCODE
              WHEN 0
                 MOVE 'SP00'                TO LS-SP-RC
              WHEN OTHER
                 IF OVER-WRITE-LOCKED
                    MOVE 'OLD_TMPLT_M_CSR'  TO WS-TABLE-NAME
                 ELSE
                    MOVE 'OLD_TMPLT_M_CSR'  TO WS-TABLE-NAME
                 END-IF
                 PERFORM 9500-SQL-ERROR
           END-EVALUATE
           .
      *---------------------------*
       2240DA-OPEN-OLD-TMPLT-WORK.
      *---------------------------*

           MOVE '2240CA-OPEN-OLD-TMPLT-WORK'
                                            TO WS-PARAGRAPH-NAME

           IF DISPLAY-ACTIVE
              DISPLAY WS-PARAGRAPH-NAME
           END-IF

           EXEC SQL
                DECLARE OLD_TMPLT_W_CSR CURSOR FOR
                 SELECT DPM17.ATTRB_CTGRY_ID
                       ,DPM17.CTGRY_SQ
                       ,DPM17.ATTRB_TERM_ID
                       ,DPM17.TERM_SQ
                       ,DPM19.MCA_VALUE_ID
                       ,DPM19.MCA_VALUE_TYPE_CD
                       ,DPM19.AMND_STAT_CD
                       ,DPM19.ROW_UPDT_TS
                       ,DPM19.ROW_UPDT_USER_ID                          ID)
                   FROM VDPM17_AMND_WORK        DPM17
                       ,VDPM19_LINK_WORK        DPM19
                  WHERE DPM17.MCA_TMPLT_ID = :WS-OLD-TEMPLATE-ID
                    AND DPM17.MCA_AMND_ID = DPM19.MCA_AMND_ID
                  WITH UR
           END-EXEC

           EXEC SQL
                OPEN OLD_TMPLT_W_CSR
           END-EXEC

           EVALUATE SQLCODE
              WHEN 0
                 MOVE 'SP00'                TO LS-SP-RC
              WHEN OTHER
                 MOVE 'OLD_TMPLT_CSR'       TO WS-TABLE-NAME
                 PERFORM 9500-SQL-ERROR
           END-EVALUATE
           .
      *-----------------------------*
       2240DB-FETCH-OLD-TMPLT-WORK.
      *-----------------------------*

           MOVE '2240DB-FETCH-OLD-TMPLT-WORK'
                                            TO WS-PARAGRAPH-NAME

           IF DISPLAY-ACTIVE
              DISPLAY WS-PARAGRAPH-NAME
           END-IF

           SET NOT-EOF-RECORD               TO TRUE

           PERFORM UNTIL EOF-RECORD

              EXEC SQL
                   FETCH OLD_TMPLT_W_CSR
                    INTO :D016-ATTRB-CTGRY-ID
                        ,:D016-CTGRY-SQ
                        ,:D016-ATTRB-TERM-ID
                        ,:D016-TERM-SQ
                        ,:D018-MCA-VALUE-ID
                        ,:D018-MCA-VALUE-TYPE-CD
                        ,:D018-AMND-STAT-CD
                        ,:D018-ROW-UPDT-TS
                        ,:D018-ROW-UPDT-USER-ID
              END-EXEC

              EVALUATE SQLCODE
                 WHEN 0
                    MOVE 'SP00'             TO LS-SP-RC
                    PERFORM 2240E-INSERT-NEW-TMPLT-LINK
                 WHEN 100
                    SET EOF-RECORD          TO TRUE
                 WHEN OTHER
                    MOVE 'OLD_TMPLT_CSR'    TO WS-TABLE-NAME
                    PERFORM 9500-SQL-ERROR
              END-EVALUATE
           END-PERFORM
           .
      *-----------------------------*
       2240DD-CLOSE-OLD-TMPLT-WORK.
      *-----------------------------*

           MOVE '2240CD-CLOSE-OLD-TMPLT-WORK'
                                            TO WS-PARAGRAPH-NAME

           EXEC SQL
                CLOSE OLD_TMPLT_W_CSR
           END-EXEC

           EVALUATE SQLCODE
              WHEN 0
                 MOVE 'SP00'                TO LS-SP-RC
              WHEN OTHER
                 MOVE 'OLD_TMPLT_CSR'       TO WS-TABLE-NAME
                 PERFORM 9500-SQL-ERROR
           END-EVALUATE
           .

      *-----------------------------*
       2240E-INSERT-NEW-TMPLT-LINK.
      *-----------------------------*

           MOVE '2240E-INSERT-NEW-TMPLT-LINK'
                                            TO WS-PARAGRAPH-NAME

           IF DISPLAY-ACTIVE
              DISPLAY WS-PARAGRAPH-NAME
              DISPLAY "WS-NEW-TEMPLATE-ID "    WS-NEW-TEMPLATE-ID
              DISPLAY "D016-ATTRB-CTGRY-ID "   D016-ATTRB-CTGRY-ID
              DISPLAY "D016-CTGRY-SQ "         D016-CTGRY-SQ
              DISPLAY "D016-ATTRB-TERM-ID "    D016-ATTRB-TERM-ID
              DISPLAY "D016-TERM-SQ "          D016-TERM-SQ
           END-IF

           EXEC SQL
                SELECT MCA_AMND_ID
                  INTO :D016-MCA-AMND-ID
                  FROM VDPM16_MCA_AMND
                 WHERE MCA_TMPLT_ID   = :WS-NEW-TEMPLATE-ID
                   AND ATTRB_CTGRY_ID = :D016-ATTRB-CTGRY-ID
                   AND CTGRY_SQ       = :D016-CTGRY-SQ
                   AND ATTRB_TERM_ID  = :D016-ATTRB-TERM-ID
                   AND TERM_SQ        = :D016-TERM-SQ
           END-EXEC

           EVALUATE SQLCODE
              WHEN 0
                 MOVE 'SP00'                TO LS-SP-RC
              WHEN OTHER
                 MOVE 'VDPM16_MCA_AMND'     TO WS-TABLE-NAME
                 PERFORM 9500-SQL-ERROR
           END-EVALUATE

           MOVE WS-USER-ID                 TO D018-ROW-UPDT-USER-ID

           IF DISPLAY-ACTIVE
              DISPLAY "D016-MCA-AMND-ID "        D016-MCA-AMND-ID
              DISPLAY "D018-MCA-VALUE-ID "       D018-MCA-VALUE-ID
              DISPLAY "D018-MCA-VALUE-TYPE-CD "  D018-MCA-VALUE-TYPE-CD
              DISPLAY "D018-AMND-STAT-CD "       D018-AMND-STAT-CD
           END-IF

           IF WS-PREV-AMND-ID       = D016-MCA-AMND-ID  AND
              WS-PREV-VALUE-ID      = D018-MCA-VALUE-ID AND
              WS-PREV-VALUE-TYPE-CD = D018-MCA-VALUE-TYPE-CD
              CONTINUE
           ELSE
              EXEC SQL
                   INSERT INTO VDPM18_MCA_LINK
                            (MCA_AMND_ID
                            ,MCA_VALUE_ID
                            ,MCA_VALUE_TYPE_CD
                            ,AMND_STAT_CD
                            ,ROW_UPDT_TS
                            ,ROW_UPDT_USER_ID)
                     VALUES (:D016-MCA-AMND-ID
                            ,:D018-MCA-VALUE-ID
                            ,:D018-MCA-VALUE-TYPE-CD
                            ,:D018-AMND-STAT-CD
                            ,CURRENT TIMESTAMP
                            ,:D018-ROW-UPDT-USER-ID)
              END-EXEC

              EVALUATE SQLCODE
                 WHEN 0
                    MOVE 'SP00'                TO LS-SP-RC
                    MOVE D016-MCA-AMND-ID      TO WS-PREV-AMND-ID
                    MOVE D018-MCA-VALUE-ID     TO WS-PREV-VALUE-ID
                    MOVE D018-MCA-VALUE-TYPE-CD TO WS-PREV-VALUE-TYPE-CD
                 WHEN OTHER
                    MOVE 'VDPM18_MCA_LINK'     TO WS-TABLE-NAME
                    PERFORM 9500-SQL-ERROR
              END-EVALUATE
           END-IF
           .
      *------------------*
       2250-DELETE-WORK.
      *------------------*

           MOVE '2250-DELETE-WORK'          TO WS-PARAGRAPH-NAME

           IF DISPLAY-ACTIVE
              DISPLAY WS-PARAGRAPH-NAME
           END-IF

           PERFORM 2250A-DELETE-LINKS

           PERFORM 2250B-DELETE-AMND

           IF WORKING-TMPLT OR REEXEC-WORKING
              SET MAIN-TMPLT                TO TRUE
           ELSE
              PERFORM 2250C-DELETE-TEMPLATE
           END-IF

           .
      *-------------------*
       2250A-DELETE-LINKS.
      *-------------------*

           MOVE '2250A-DELETE-LINKS'        TO WS-PARAGRAPH-NAME

           IF DISPLAY-ACTIVE
              DISPLAY WS-PARAGRAPH-NAME
           END-IF

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
                   AND DPM19A.MCA_ACCS_STAT_CD = 'U'
           END-EXEC

           EVALUATE SQLCODE
              WHEN 0
                 MOVE 'SP00'                TO LS-SP-RC
                 IF WORKING-TMPLT OR REEXEC-WORKING
                    PERFORM 2250AA-INSERT-WORK
                 END-IF
              WHEN 100
                 CONTINUE
              WHEN OTHER
                 MOVE 'VDPM19_LINK_WORK'    TO WS-TABLE-NAME
                 PERFORM 9500-SQL-ERROR
           END-EVALUATE

           IF ISDA-TMPLT
              EXEC SQL
                   DELETE FROM VDPM19_LINK_WORK DPM19A
                    WHERE DPM19A.MCA_AMND_ID IN
                                 (SELECT DPM17.MCA_AMND_ID
                                    FROM VDPM17_AMND_WORK      DPM17
                                        ,VDPM19_LINK_WORK      DPM19
                                   WHERE DPM17.MCA_TMPLT_ID =
                                                   :WS-OLD-TEMPLATE-ID  ID
                                     AND DPM17.MCA_AMND_ID  =
                                                    DPM19.MCA_AMND_ID
                                     AND DPM19.MCA_ACCS_STAT_CD = 'U')
              END-EXEC

              EVALUATE SQLCODE
                 WHEN 0
                    MOVE 'SP00'                TO LS-SP-RC
                 WHEN 100
                    CONTINUE
                 WHEN OTHER
                    MOVE 'VDPM19_LINK_WORK'    TO WS-TABLE-NAME
                    PERFORM 9500-SQL-ERROR
              END-EVALUATE
           END-IF
           .
      *-------------------*
       2250AA-INSERT-WORK.
      *-------------------*

           MOVE '2250AA-INSERT-WORK'        TO WS-PARAGRAPH-NAME
           MOVE 'U'                         TO D019-MCA-ACCS-STAT-CD

           EXEC SQL
                INSERT INTO VDPM19_LINK_WORK
                              (SELECT DPM19.MCA_AMND_ID
                                     ,DPM19.MCA_VALUE_ID
                                     ,DPM19.MCA_VALUE_TYPE_CD
                                     ,:D019-MCA-ACCS-STAT-CD
                                     ,DPM19.AMND_STAT_CD
                                     ,DPM19.ROW_UPDT_TS
                                     ,DPM19.ROW_UPDT_USER_ID
                                 FROM VDPM16_MCA_AMND       DPM16
                                     ,VDPM19_LINK_WORK      DPM19
                                WHERE DPM16.MCA_TMPLT_ID =
                                                    :WS-OLD-TEMPLATE-ID
                                  AND DPM16.MCA_AMND_ID  =
                                                    DPM19.MCA_AMND_ID
                                  AND DPM19.MCA_ACCS_STAT_CD = 'O')
           END-EXEC

           EVALUATE SQLCODE
              WHEN 0
                 MOVE 'SP00'                TO LS-SP-RC
              WHEN 100
                 CONTINUE
              WHEN OTHER
                 MOVE 'VDPM19_LINK_WORK'    TO WS-TABLE-NAME
                 PERFORM 9500-SQL-ERROR
           END-EVALUATE
           .
      *-------------------*
       2250B-DELETE-AMND.
      *-------------------*

           MOVE '2250B-DELETE-AMND'         TO WS-PARAGRAPH-NAME

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
                 PERFORM 9500-SQL-ERROR
           END-EVALUATE
           .
      *----------------------*
       2250C-DELETE-TEMPLATE.
      *----------------------*

           MOVE '2250C-DELETE-TEMPLATE'     TO WS-PARAGRAPH-NAME

           SET MAIN-TMPLT                   TO TRUE

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
                 SET WORK-TMPLT             TO TRUE
              WHEN 100
                 SET MAIN-TMPLT             TO TRUE
              WHEN OTHER
                 MOVE 'VDPM15_TMPLT_WORK'   TO WS-TABLE-NAME
                 PERFORM 9500-SQL-ERROR
           END-EVALUATE
           .
      *-----------------------*
       2300-SAVE-WORK-TEMPLATE.
      *-----------------------*

           MOVE '2300-SAVE-WORK-TEMPLATE'   TO WS-PARAGRAPH-NAME

           IF DISPLAY-ACTIVE
              DISPLAY WS-PARAGRAPH-NAME
           END-IF

           PERFORM 2310-UPDATE-WORK-TEMPLT-LINKS

           PERFORM 9350-UPDATE-TEMPLATE

           IF UNLOCK-TMPLT
              PERFORM 9200-UNLOCK-OLD-TEMPLATE
           END-IF
           .
      *-----------------------------*
       2310-UPDATE-WORK-TEMPLT-LINKS.
      *-----------------------------*

           MOVE '2310-UPDATE-WORK-TEMPLT-LINKS'
                                            TO WS-PARAGRAPH-NAME

           IF DISPLAY-ACTIVE
              DISPLAY WS-PARAGRAPH-NAME
           END-IF

           PERFORM 2310A-DELETE-WORK-TEMPLT-LINKS

           PERFORM 2310B-INSERT-WORK-TEMPLT-LINKS

           .
      *-----------------------------*
       2310A-DELETE-WORK-TEMPLT-LINKS.
      *-----------------------------*

           MOVE '2310A-DELETE-WORK-TEMPLT-LINKS'
                                            TO WS-PARAGRAPH-NAME

           IF DISPLAY-ACTIVE
              DISPLAY WS-PARAGRAPH-NAME
           END-IF

           EXEC SQL
                DELETE FROM VDPM19_LINK_WORK DPM19A
                 WHERE DPM19A.MCA_AMND_ID IN
                              (SELECT DPM19.MCA_AMND_ID
                                 FROM VDPM16_MCA_AMND       DPM16
                                     ,VDPM19_LINK_WORK      DPM19
                                WHERE DPM16.MCA_TMPLT_ID   =
                                                     :WS-OLD-TEMPLATE-ID
                                  AND DPM16.MCA_AMND_ID    =
                                                      DPM19.MCA_AMND_ID
                                  AND DPM19.MCA_ACCS_STAT_CD = 'O')
                   AND DPM19A.MCA_ACCS_STAT_CD = 'O'

           END-EXEC

           EVALUATE SQLCODE
              WHEN 0
                 MOVE 'SP00'                TO LS-SP-RC
              WHEN 100
                 CONTINUE
              WHEN OTHER
                 MOVE 'VDPM19_LINK_WORK'    TO WS-TABLE-NAME
                 PERFORM 9500-SQL-ERROR
           END-EVALUATE
           .
      *-----------------------------*
       2310B-INSERT-WORK-TEMPLT-LINKS.
      *-----------------------------*

           MOVE '2310B-INSERT-WORK-TEMPLT-LINKS'
                                            TO WS-PARAGRAPH-NAME

           IF DISPLAY-ACTIVE
              DISPLAY WS-PARAGRAPH-NAME
           END-IF

           MOVE 'O'                         TO D019-MCA-ACCS-STAT-CD

           EXEC SQL
                INSERT INTO VDPM19_LINK_WORK
                                 (MCA_AMND_ID
                                 ,MCA_VALUE_ID
                                 ,MCA_VALUE_TYPE_CD
                                 ,MCA_ACCS_STAT_CD
                                 ,AMND_STAT_CD
                                 ,ROW_UPDT_TS
                                 ,ROW_UPDT_USER_ID)
                         (SELECT  DPM19.MCA_AMND_ID
                                 ,DPM19.MCA_VALUE_ID
                                 ,DPM19.MCA_VALUE_TYPE_CD
                                 ,:D019-MCA-ACCS-STAT-CD
                                 ,DPM19.AMND_STAT_CD
                                 ,CURRENT TIMESTAMP
                                 ,DPM19.ROW_UPDT_USER_ID
                            FROM VDPM16_MCA_AMND       DPM16
                                ,VDPM19_LINK_WORK      DPM19
                           WHERE DPM16.MCA_TMPLT_ID =                   D
                                                    :WS-OLD-TEMPLATE-ID D
                             AND DPM16.MCA_AMND_ID  = DPM19.MCA_AMND_ID
                             AND DPM19.MCA_ACCS_STAT_CD = 'U')
           END-EXEC

           EVALUATE SQLCODE
              WHEN 0
                 MOVE 'SP00'                TO LS-SP-RC
              WHEN OTHER
                 MOVE 'VDPM19_LINK_WORK'    TO WS-TABLE-NAME
                 PERFORM 9500-SQL-ERROR
           END-EVALUATE
           .
      *-----------------------*
       2400-DELETE-CTGRY-TERMS.
      *-----------------------*

           MOVE '2400-DELETE-CTGRY-TERMS'   TO WS-PARAGRAPH-NAME

           EXEC SQL
                DELETE FROM VDPM08_MCA_TERMS
                 WHERE MCA_TMPLT_ID = :WS-OLD-TEMPLATE-ID
           END-EXEC

           EVALUATE SQLCODE
              WHEN 0
                 MOVE 'SP00'                TO LS-SP-RC
              WHEN OTHER
                 MOVE 'VDPM08_MCA_TERMS'    TO WS-TABLE-NAME
                 PERFORM 9500-SQL-ERROR
           END-EVALUATE

           EXEC SQL
                DELETE FROM VDPM07_MCA_CTGRY
                 WHERE MCA_TMPLT_ID = :WS-OLD-TEMPLATE-ID
           END-EXEC

           EVALUATE SQLCODE
              WHEN 0
                 MOVE 'SP00'                TO LS-SP-RC
              WHEN OTHER
                 MOVE 'VDPM07_MCA_CTGRY'    TO WS-TABLE-NAME
                 PERFORM 9500-SQL-ERROR
           END-EVALUATE
           .
      *------------------------*
       2450-UPDATE-TMPLT-STATUS.
      *------------------------*

           MOVE '2450-UPDATE-TMPLT-STATUS'  TO WS-PARAGRAPH-NAME

           PERFORM 2475-CHECK-AMND

           IF TMPLT-UPDATE
              EXEC SQL
                   UPDATE D0006
                      SET MCA_STAT_IN      = 'N'
                         ,ROW_UPDT_TS      = CURRENT TIMESTAMP
                         ,ROW_UPDT_USER_ID = :WS-USER-ID
                    WHERE MCA_TMPLT_ID     = :WS-OLD-TEMPLATE-ID
              END-EXEC

              EVALUATE SQLCODE
                 WHEN 0
                    CONTINUE
                 WHEN OTHER
                    MOVE 'D0006' TO WS-TABLE-NAME
                    PERFORM 9500-SQL-ERROR
              END-EVALUATE
           END-IF
           .

      *----------------*
       2475-CHECK-AMND.
      *----------------*

           MOVE '2475-CHECK-AMND'           TO WS-PARAGRAPH-NAME

           EXEC SQL
                SELECT MCA_AMND_ID
                  INTO :D016-MCA-AMND-ID
                  FROM VDPM16_MCA_AMND
                 WHERE MCA_TMPLT_ID = :WS-OLD-TEMPLATE-ID
                 FETCH FIRST ROW ONLY
           END-EXEC

           EVALUATE SQLCODE
              WHEN 0
                 SET TMPLT-NOT-UPDATE       TO TRUE
              WHEN 100
                 SET TMPLT-UPDATE           TO TRUE
              WHEN OTHER
                 MOVE 'VDPM16_MCA_AMND'     TO WS-TABLE-NAME
                 PERFORM 9500-SQL-ERROR
           END-EVALUATE
           .

      *--------------------------*
       9100-SAVE-GENERIC-TEMPLATE.
      *--------------------------*

           MOVE '9100-SAVE-GENERIC-TEMPLATE'
                                            TO WS-PARAGRAPH-NAME
           PERFORM 9110-GET-TMPLT-SEQ

           PERFORM 9120-INSERT-NEW-TMPLT

           SET LINK-CREATE                  TO TRUE
           PERFORM 9130-CREATE-STATIC-GRID

           IF LOCKED-USER
              PERFORM 9200-UNLOCK-OLD-TEMPLATE
           END-IF

           PERFORM 9300-LOCK-NEW-TEMPLATE
           .

      *----------------------*
       9110-GET-TMPLT-SEQ.
      *----------------------*

           MOVE '9110-GET-TMPLT-SEQ'       TO WS-PARAGRAPH-NAME

           IF DISPLAY-ACTIVE
              DISPLAY WS-PARAGRAPH-NAME
           END-IF

           INITIALIZE WS-TEMP-SEQUENCE-NO

           EXEC SQL
                SELECT NEXT VALUE FOR DPM.SQDPM141
                  INTO :WS-TEMP-SEQUENCE-NO
                  FROM SYSIBM.SYSDUMMY1
           END-EXEC

           EVALUATE SQLCODE
              WHEN 0
                 MOVE 'SP00'                TO LS-SP-RC

                 IF DISPLAY-ACTIVE
                    DISPLAY "WS-TEMP-SEQUENCE-NO " WS-TEMP-SEQUENCE-NO
                 END-IF

              WHEN OTHER
                 MOVE 'TMPLT_SEQ'           TO WS-TABLE-NAME
                 PERFORM 9500-SQL-ERROR
           END-EVALUATE
           .

      *----------------------*
       9120-INSERT-NEW-TMPLT.
      *----------------------*

           MOVE '9120-INSERT-NEW-TMPLT'    TO WS-PARAGRAPH-NAME

           IF DISPLAY-ACTIVE
              DISPLAY WS-PARAGRAPH-NAME
           END-IF

           MOVE WS-TEMP-SEQUENCE-NO         TO D014-MCA-TMPLT-ID
                                               WS-OUT-TEMPLATE-ID
                                               WS-NEW-TEMPLATE-ID
      *    MOVE WS-TEMPLATE-NAME            TO D014-MCA-TMPLT-NM
           MOVE WS-TEMPLATE-NAME-LEN        TO D014-MCA-TMPLT-NM-LEN
           MOVE WS-TEMPLATE-NAME-TEXT       TO D014-MCA-TMPLT-NM-TEXT

           MOVE WS-USER-ID                  TO D014-ROW-UPDT-USER-ID

           MOVE 'D'                         TO D014-MCA-TMPLT-TYPE-CD
                                               WS-NEW-TEMPLATE-TYPE

           MOVE WS-OLD-TEMPLATE-ID          TO D014-MCA-CSTMZ-TMPLT-ID
           MOVE SPACES                      TO D014-MCA-DELR-STAT-CD
                                               D014-MCA-CLNT-STAT-CD
                                               D014-CLNT-CMPNY-ID

           MOVE WS-CMPNY-CD                 TO D014-DELR-CMPNY-ID

           EXEC SQL
                INSERT INTO D0006
                        (SELECT :D014-MCA-TMPLT-ID
                               ,:D014-MCA-TMPLT-NM
                               ,MCA_TMPLT_SHORT_NM
                               ,MCA_TMPLT_GROUP_CD
                               ,:D014-MCA-TMPLT-TYPE-CD
                               ,:D014-DELR-CMPNY-ID
                               ,:D014-CLNT-CMPNY-ID
                               ,ATTRB_PRDCT_ID
                               ,ATTRB_SUB_PRDCT_ID
                               ,ATTRB_REGN_ID
                               ,MCA_PBLTN_DT
                               ,MCA_END_DT
                               ,MCA_STAT_IN
                               ,MCA_EXE_TS
                               ,:D014-MCA-DELR-STAT-CD
                               ,:D014-MCA-CLNT-STAT-CD
                               ,MCA_ISDA_TMPLT_ID
                               ,:D014-MCA-CSTMZ-TMPLT-ID
                               ,MCA_TMPLT_RQSTR_ID
                               ,MCA_TMPLT_APRVR_ID
                               ,CURRENT TIMESTAMP
                               ,:D014-ROW-UPDT-USER-ID
                         FROM D0006
                       WHERE MCA_TMPLT_ID = :WS-OLD-TEMPLATE-ID)
           END-EXEC

           EVALUATE SQLCODE
              WHEN 0
                 MOVE 'SP00'                TO LS-SP-RC
              WHEN OTHER
                 MOVE 'D0006'    TO WS-TABLE-NAME
                 PERFORM 9500-SQL-ERROR
           END-EVALUATE
           .
      *-----------------------*
       9130-CREATE-STATIC-GRID.
      *-----------------------*

           MOVE '9130-CREATE-STATIC-GRID'  TO WS-PARAGRAPH-NAME

           PERFORM 9131-COPY-CATEGORY-DETAILS

           PERFORM 9132-COPY-TERM-DETAILS

           PERFORM 9133-COPY-AMNDT-DETAILS
           .
      *--------------------------*
       9131-COPY-CATEGORY-DETAILS.
      *--------------------------*

           MOVE '9131-COPY-CATEGORY-DETAILS'
                                            TO WS-PARAGRAPH-NAME

           IF DISPLAY-ACTIVE
              DISPLAY WS-PARAGRAPH-NAME
           END-IF

           SET NOT-EOF-RECORD               TO TRUE

           PERFORM 9131A-DECLARE-CATGRY-CURSOR

           PERFORM 9131B-OPEN-CATGRY-CURSOR

           PERFORM UNTIL EOF-RECORD

              PERFORM 9131C-FETCH-CATGRY-CURSOR

              EVALUATE SQLCODE
                  WHEN 0
                    PERFORM 9131D-INSERT-CATGRY
                  WHEN 100
                    SET EOF-RECORD          TO TRUE
                    PERFORM 9131E-CLOSE-CATGRY-CURSOR
                  WHEN OTHER
                    MOVE 'VDPM07_MCA_CTGRY' TO WS-TABLE-NAME
                    PERFORM 9500-SQL-ERROR
              END-EVALUATE
           END-PERFORM
           .
      *---------------------------*
       9131A-DECLARE-CATGRY-CURSOR.
      *---------------------------*

           MOVE '9131A-DECLARE-CATGRY-CURSOR'
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
                  WHERE MCA_TMPLT_ID = :WS-OLD-TEMPLATE-ID
                 WITH UR
           END-EXEC
           .
      *--------------------------*
       9131B-OPEN-CATGRY-CURSOR.
      *--------------------------*

           MOVE '9131B-OPEN-CATGRY-CURSOR'  TO WS-PARAGRAPH-NAME

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
                 PERFORM 9500-SQL-ERROR
           END-EVALUATE
           .
      *--------------------------*
       9131C-FETCH-CATGRY-CURSOR.
      *--------------------------*

           MOVE '9131C-FETCH-CATGRY-CURSOR' TO WS-PARAGRAPH-NAME

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
       9131D-INSERT-CATGRY.
      *---------------------*

           MOVE '9131D-INSERT-CATGRY'       TO WS-PARAGRAPH-NAME

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
                 PERFORM 9500-SQL-ERROR
           END-EVALUATE
           .

      *--------------------------*
       9131E-CLOSE-CATGRY-CURSOR.
      *--------------------------*

           MOVE '9131E-CLOSE-CATGRY-CURSOR' TO WS-PARAGRAPH-NAME

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
                 PERFORM 9500-SQL-ERROR
           END-EVALUATE
           .
      *------------------------*
       9132-COPY-TERM-DETAILS.
      *------------------------*

           MOVE '9132-COPY-TERM-DETAILS'    TO WS-PARAGRAPH-NAME

           IF DISPLAY-ACTIVE
              DISPLAY WS-PARAGRAPH-NAME
           END-IF


           SET NOT-EOF-RECORD               TO TRUE

           PERFORM 9132A-DECLARE-TERM-CURSOR

           PERFORM 9132B-OPEN-TERM-CURSOR

           PERFORM UNTIL EOF-RECORD

              PERFORM 9132C-FETCH-TERM-CURSOR

              EVALUATE SQLCODE
                  WHEN 0
                    PERFORM 9132D-INSERT-TERM
                  WHEN 100
                    SET EOF-RECORD          TO TRUE
                    PERFORM 9132E-CLOSE-TERM-CURSOR
                  WHEN OTHER
                    MOVE 'VDPM08_MCA_TERMS' TO WS-TABLE-NAME
                    PERFORM 9500-SQL-ERROR
              END-EVALUATE
           END-PERFORM
           .
      *---------------------------*
       9132A-DECLARE-TERM-CURSOR.
      *---------------------------*

           MOVE '9132A-DECLARE-TERM-CURSOR' TO WS-PARAGRAPH-NAME

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
                  WHERE MCA_TMPLT_ID = :WS-OLD-TEMPLATE-ID
                 WITH UR
           END-EXEC
           .
      *------------------------*
       9132B-OPEN-TERM-CURSOR.
      *------------------------*

           MOVE '9132B-OPEN-TERM-CURSOR'    TO WS-PARAGRAPH-NAME

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
                 PERFORM 9500-SQL-ERROR
           END-EVALUATE
           .
      *--------------------------*
       9132C-FETCH-TERM-CURSOR.
      *--------------------------*

           MOVE '9132C-FETCH-TERM-CURSOR'   TO WS-PARAGRAPH-NAME

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
       9132D-INSERT-TERM.
      *---------------------*

           MOVE '9132D-INSERT-TERM'         TO WS-PARAGRAPH-NAME

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
                 PERFORM 9500-SQL-ERROR
           END-EVALUATE
           .
      *--------------------------*
       9132E-CLOSE-TERM-CURSOR.
      *--------------------------*

           MOVE '9132E-CLOSE-TERM-CURSOR'   TO WS-PARAGRAPH-NAME

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
                 PERFORM 9500-SQL-ERROR
           END-EVALUATE
           .
      *------------------------*
       9133-COPY-AMNDT-DETAILS.
      *------------------------*

           MOVE '9133-COPY-AMNDT-DETAILS'   TO WS-PARAGRAPH-NAME

           IF DISPLAY-ACTIVE
              DISPLAY WS-PARAGRAPH-NAME
              DISPLAY 'WS-CREATE-LINK ' WS-CREATE-LINK
           END-IF

           SET NOT-EOF-RECORD               TO TRUE

           PERFORM 9133A-DECLARE-AMNDT-CURSOR

           PERFORM 9133B-OPEN-AMNDT-CURSOR

           PERFORM UNTIL EOF-RECORD

              PERFORM 9133C-FETCH-AMNDT-CURSOR

              EVALUATE SQLCODE
                  WHEN 0
                    MOVE D016-MCA-AMND-ID   TO D018-MCA-AMND-ID
                    PERFORM 9133D-GET-NEXT-AMNDT-SEQ
                    PERFORM 9133E-INSERT-AMNDT
                    IF LINK-CREATE
                       PERFORM 9133F-GET-LINK-DETAILS
                    END-IF
                  WHEN 100
                    SET EOF-RECORD          TO TRUE
                    PERFORM 9133G-CLOSE-AMNDT-CURSOR
                  WHEN OTHER
                    MOVE 'VDPM16_MCA_AMND'  TO WS-TABLE-NAME
                    PERFORM 9500-SQL-ERROR
              END-EVALUATE
           END-PERFORM
           .
      *---------------------------*
       9133A-DECLARE-AMNDT-CURSOR.
      *---------------------------*

           MOVE '9133A-DECLARE-AMNDT-CURSOR'
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
                  WHERE MCA_TMPLT_ID = :WS-OLD-TEMPLATE-ID
                 WITH UR
           END-EXEC
           .
      *------------------------*
       9133B-OPEN-AMNDT-CURSOR.
      *------------------------*

           MOVE '9133B-OPEN-AMNDT-CURSOR'   TO WS-PARAGRAPH-NAME

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
                 PERFORM 9500-SQL-ERROR
           END-EVALUATE
           .
      *--------------------------*
       9133C-FETCH-AMNDT-CURSOR.
      *--------------------------*

           MOVE '9133C-FETCH-AMNDT-CURSOR'  TO WS-PARAGRAPH-NAME

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
       9133D-GET-NEXT-AMNDT-SEQ.
      *--------------------------*

           MOVE '9133D-GET-NEXT-AMNDT-SEQ'  TO WS-PARAGRAPH-NAME

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
                 PERFORM 9500-SQL-ERROR
           END-EVALUATE
           .
      *---------------------*
       9133E-INSERT-AMNDT.
      *---------------------*

           MOVE '9133E-INSERT-AMNDT'        TO WS-PARAGRAPH-NAME

           IF DISPLAY-ACTIVE
              DISPLAY WS-PARAGRAPH-NAME
           END-IF

           MOVE WS-AMNDT-SEQUENCE-NO        TO D016-MCA-AMND-ID

           MOVE WS-TEMP-SEQUENCE-NO         TO D016-MCA-TMPLT-ID

           MOVE WS-USER-ID                  TO D016-ROW-UPDT-USER-ID

           EXEC SQL
                INSERT INTO VDPM16_MCA_AMND
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
                 MOVE 'VDPM16_MCA_AMND'     TO WS-TABLE-NAME
                 PERFORM 9500-SQL-ERROR
           END-EVALUATE
           .
      *-----------------------*
       9133F-GET-LINK-DETAILS.
      *-----------------------*

           MOVE '9133F-GET-LINK-DETAILS'    TO WS-PARAGRAPH-NAME

           IF DISPLAY-ACTIVE
              DISPLAY WS-PARAGRAPH-NAME
           END-IF

           SET NOT-EOF-LINK-RECORD          TO TRUE

           PERFORM 9133FA-DECLARE-LINK-CURSOR

           PERFORM 9133FB-OPEN-LINK-CURSOR

           PERFORM UNTIL EOF-LINK-RECORD

              PERFORM 9133FC-FETCH-LINK-CURSOR

              EVALUATE SQLCODE
                  WHEN 0
                    PERFORM 9133FD-INSERT-LINK-WRK
                  WHEN 100
                    SET EOF-LINK-RECORD     TO TRUE
                    PERFORM 9133FE-CLOSE-LINK-CURSOR
                  WHEN OTHER
                    MOVE 'VDPM16_MCA_AMND'  TO WS-TABLE-NAME
                    PERFORM 9500-SQL-ERROR
              END-EVALUATE
           END-PERFORM
           .
      *---------------------------*
       9133FA-DECLARE-LINK-CURSOR.
      *---------------------------*

           MOVE '9133FA-DECLARE-LINK-CURSOR'
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
       9133FB-OPEN-LINK-CURSOR.
      *------------------------*

           MOVE '9133FB-OPEN-LINK-CURSOR'   TO WS-PARAGRAPH-NAME

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
                 PERFORM 9500-SQL-ERROR
           END-EVALUATE
           .
      *--------------------------*
       9133FC-FETCH-LINK-CURSOR.
      *--------------------------*

           MOVE '9133FC-FETCH-LINK-CURSOR'  TO WS-PARAGRAPH-NAME

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
       9133FD-INSERT-LINK-WRK.
      *----------------------*

           MOVE '9133FD-INSERT-LINK-WRK'    TO WS-PARAGRAPH-NAME

           IF DISPLAY-ACTIVE
              DISPLAY WS-PARAGRAPH-NAME
           END-IF

           MOVE WS-AMNDT-SEQUENCE-NO        TO D018-MCA-AMND-ID

           MOVE WS-USER-ID                  TO D018-ROW-UPDT-USER-ID

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

           EVALUATE SQLCODE
              WHEN 0
                 MOVE 'SP00'                TO LS-SP-RC
              WHEN OTHER
                 MOVE 'VDPM19_LINK_WORK'    TO WS-TABLE-NAME
                 PERFORM 9500-SQL-ERROR
           END-EVALUATE
           .
      *--------------------------*
       9133FE-CLOSE-LINK-CURSOR.
      *--------------------------*

           MOVE '9133FE-CLOSE-LINK-CURSOR'  TO WS-PARAGRAPH-NAME

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
                 PERFORM 9500-SQL-ERROR
           END-EVALUATE
           .
      *--------------------------*
       9133G-CLOSE-AMNDT-CURSOR.
      *--------------------------*

           MOVE '9133G-CLOSE-AMNDT-CURSOR'  TO WS-PARAGRAPH-NAME

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
                 PERFORM 9500-SQL-ERROR
           END-EVALUATE
           .
      *--------------------------*
       9200-UNLOCK-OLD-TEMPLATE.
      *--------------------------*

           MOVE '9200-UNLOCK-OLD-TEMPLATE'  TO WS-PARAGRAPH-NAME

           INITIALIZE WS-LOCK-AREA

           MOVE WS-OLD-TEMPLATE-ID          TO WS-LOCK-TEMPLATE-ID
           MOVE 'U'                         TO WS-LOCK-ACTION-IND

           IF DISPLAY-ACTIVE
              DISPLAY "INPUT TO THE LOCK PROGRAM"
              DISPLAY "TEMPLATE ID "        WS-LOCK-TEMPLATE-ID
              DISPLAY "WS-LOCK-ACTION-IND " WS-LOCK-ACTION-IND
           END-IF

           CALL WS-LOCK-PROGRAM USING WS-LOCK-TEMPLATE-ID
                                      WS-LOCK-CMPNY-ID
                                      WS-LOCK-IN-USER-ID
                                      WS-LOCK-ACTION-IND
                                      WS-LOCK-CMPNY-CD
                                      WS-LOCK-OUT-USER-ID
                                      WS-LOCK-USER-NM
                                      WS-LOCK-OUT-SQLCODE
                                      WS-LOCK-ERROR-AREA
                                      WS-LOCK-RC

           MOVE WS-LOCK-OUT-SQLCODE         TO SQLCODE

           IF DISPLAY-ACTIVE
              DISPLAY "OUTPUT OF THE LOCK PROGRAM"
              DISPLAY "WS-LOCK-OUT-SQLCODE " WS-LOCK-OUT-SQLCODE
           END-IF

           EVALUATE SQLCODE
              WHEN 0
                 MOVE 'SP00'                TO LS-SP-RC
              WHEN 100
                 CONTINUE
              WHEN OTHER
                 MOVE 'LOCK PROGRAM'        TO WS-TABLE-NAME
                 PERFORM 9500-SQL-ERROR
           END-EVALUATE

           .
      *-----------------------*
       9300-LOCK-NEW-TEMPLATE.
      *-----------------------*

           MOVE '9300-LOCK-NEW-TEMPLATE'    TO WS-PARAGRAPH-NAME

           INITIALIZE WS-LOCK-AREA

           MOVE WS-NEW-TEMPLATE-ID          TO WS-LOCK-TEMPLATE-ID
           MOVE 'L'                         TO WS-LOCK-ACTION-IND
           MOVE WS-CMPNY-CD                 TO WS-LOCK-CMPNY-ID
           MOVE WS-USER-ID                  TO WS-LOCK-IN-USER-ID

           IF DISPLAY-ACTIVE
              DISPLAY "INPUT TO THE LOCK PROGRAM"
              DISPLAY "TEMPLATE ID "        WS-LOCK-TEMPLATE-ID
              DISPLAY "WS-LOCK-ACTION-IND " WS-LOCK-ACTION-IND
              DISPLAY "WS-LOCK-CMPNY-ID "   WS-LOCK-CMPNY-ID
              DISPLAY "WS-LOCK-IN-USER-ID " WS-LOCK-IN-USER-ID
           END-IF

           CALL WS-LOCK-PROGRAM USING WS-LOCK-TEMPLATE-ID
                                      WS-LOCK-CMPNY-ID
                                      WS-LOCK-IN-USER-ID
                                      WS-LOCK-ACTION-IND
                                      WS-LOCK-CMPNY-CD
                                      WS-LOCK-OUT-USER-ID
                                      WS-LOCK-USER-NM
                                      WS-LOCK-OUT-SQLCODE
                                      WS-LOCK-ERROR-AREA
                                      WS-LOCK-RC

           MOVE WS-LOCK-OUT-SQLCODE         TO SQLCODE

           IF DISPLAY-ACTIVE
              DISPLAY "OUTPUT OF THE LOCK PROGRAM"
              DISPLAY "WS-LOCK-OUT-SQLCODE " WS-LOCK-OUT-SQLCODE
           END-IF

           EVALUATE SQLCODE
              WHEN 0
                 MOVE 'SP00'                TO LS-SP-RC
              WHEN 100
                 CONTINUE
              WHEN OTHER
                 MOVE 'LOCK PROGRAM'        TO WS-TABLE-NAME
                 PERFORM 9500-SQL-ERROR
           END-EVALUATE
           .
      *---------------------*
       9350-UPDATE-TEMPLATE.
      *---------------------*

           MOVE '9350-UPDATE-TEMPLATE'      TO WS-PARAGRAPH-NAME

           IF DISPLAY-ACTIVE
              DISPLAY WS-PARAGRAPH-NAME
           END-IF

           MOVE WS-USER-ID                  TO D014-ROW-UPDT-USER-ID
           MOVE 'I'                         TO D014-MCA-STAT-IN

           IF WORKING-TMPLT OR REEXEC-WORKING
              PERFORM 9375-CHECK-TEMPLATE-ID
              IF MAIN-TMPLT
                 PERFORM 9380-CREATE-WORK-TEMPLATE
              ELSE
                 PERFORM 9380A-UPDATE-WORK-TEMPLATE
              END-IF
           ELSE
              EXEC SQL
                   UPDATE D0006
                      SET MCA_STAT_IN      = :D014-MCA-STAT-IN
                         ,ROW_UPDT_TS      = CURRENT TIMESTAMP
                         ,ROW_UPDT_USER_ID = :D014-ROW-UPDT-USER-ID
                    WHERE MCA_TMPLT_ID     = :WS-OLD-TEMPLATE-ID
              END-EXEC

              EVALUATE SQLCODE
                 WHEN 0
                    MOVE 'SP00'             TO LS-SP-RC
                 WHEN OTHER
                    MOVE 'D0006' TO WS-TABLE-NAME
                    PERFORM 9500-SQL-ERROR
              END-EVALUATE
           END-IF
           .
      *-----------------------*
       9375-CHECK-TEMPLATE-ID.
      *-----------------------*

           MOVE '9375-CHECK-TEMPLATE-ID'    TO WS-PARAGRAPH-NAME

           EXEC SQL
                SELECT MCA_TMPLT_ID
                      ,MCA_CLNT_STAT_CD
                  INTO :D016-MCA-TMPLT-ID
                      ,:D015-MCA-CLNT-STAT-CD
                  FROM VDPM15_TMPLT_WORK
                 WHERE MCA_TMPLT_ID = :WS-OLD-TEMPLATE-ID
           END-EXEC

           EVALUATE SQLCODE
              WHEN 0
                 SET WORK-TMPLT             TO TRUE
              WHEN 100
                 SET MAIN-TMPLT             TO TRUE
              WHEN OTHER
                 MOVE 'VDPM15_TMPLT_WORK'   TO WS-TABLE-NAME
                 PERFORM 9500-SQL-ERROR
           END-EVALUATE
           .
      *--------------------------*                                       1850000
       9380-CREATE-WORK-TEMPLATE.                                        1860000
      *--------------------------*                                       1870000
                                                                         1880000
           MOVE '9380-CREATE-WORK-TEMPLATE' TO WS-PARAGRAPH-NAME         1890000
                                                                         1880000
           PERFORM 9381-GET-CLNT-STAT-CD

           IF D015-MCA-CLNT-STAT-CD = 'A'
              PERFORM 9382-CHECK-PENDING-AMND
              IF AMND-PENDING
                 MOVE 'D'                   TO D015-MCA-CLNT-STAT-CD     1890000
              END-IF
           END-IF

           EXEC SQL
                INSERT INTO TDPM15_TMPLT_WORK
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
                               ,:D015-MCA-CLNT-STAT-CD
                               ,MCA_ISDA_TMPLT_ID
                               ,MCA_CSTMZ_TMPLT_ID
                               ,MCA_TMPLT_RQSTR_ID
                               ,MCA_TMPLT_APRVR_ID
                               ,CURRENT TIMESTAMP
                               ,:D014-ROW-UPDT-USER-ID
                         FROM D0006
                       WHERE MCA_TMPLT_ID = :WS-OLD-TEMPLATE-ID)
           END-EXEC

           EVALUATE SQLCODE
              WHEN 0
                 MOVE 'SP00'                TO LS-SP-RC
              WHEN OTHER
                 MOVE 'VDPM15_TMPLT_WORK'   TO WS-TABLE-NAME
                 PERFORM 9500-SQL-ERROR
           END-EVALUATE
           .
      *--------------------------*
       9380A-UPDATE-WORK-TEMPLATE.
      *--------------------------*

           MOVE '9380A-UPDATE-WORK-TEMPLATE' TO WS-PARAGRAPH-NAME

           IF D015-MCA-CLNT-STAT-CD = 'A'
              PERFORM 9382-CHECK-PENDING-AMND
              IF AMND-PENDING
                 MOVE 'D'                    TO D015-MCA-CLNT-STAT-CD
              END-IF
           END-IF

           EXEC SQL
                UPDATE VDPM15_TMPLT_WORK
                   SET MCA_CLNT_STAT_CD = :D015-MCA-CLNT-STAT-CD
                      ,ROW_UPDT_TS      = CURRENT TIMESTAMP
                      ,ROW_UPDT_USER_ID = :D014-ROW-UPDT-USER-ID
                 WHERE MCA_TMPLT_ID     = :WS-OLD-TEMPLATE-ID
           END-EXEC

           EVALUATE SQLCODE
              WHEN 0
                 MOVE 'SP00'                 TO LS-SP-RC
              WHEN OTHER
                 MOVE 'VDPM15_TMPLT_WORK'
                                             TO WS-TABLE-NAME
                 PERFORM 9500-SQL-ERROR
           END-EVALUATE
           .
      *----------------------*                                          01850000
       9381-GET-CLNT-STAT-CD.                                           01860000
      *----------------------*                                          01870000
                                                                        01880000
           MOVE '9381-GET-CLNT-STAT-CD'     TO WS-PARAGRAPH-NAME        01890000
                                                                        00051700
           EXEC SQL                                                     00051800
                SELECT MCA_CLNT_STAT_CD                                 00051900
                  INTO :D015-MCA-CLNT-STAT-CD                           00052010
                FROM   D0006                                 00052040
                WHERE MCA_TMPLT_ID = :WS-OLD-TEMPLATE-ID                00052050
                WITH UR
           END-EXEC                                                     00052060
                                                                        00052070
           EVALUATE SQLCODE                                             00052091
              WHEN 0                                                    00052092
                  CONTINUE
              WHEN OTHER                                                00052092
                  MOVE 'D0006'    TO WS-TABLE-NAME            0176000
                  PERFORM 9500-SQL-ERROR                                 0181000
           END-EVALUATE                                                 00052102
           .                                                            00051700
      *-----------------------*                                         01850000
       9382-CHECK-PENDING-AMND.                                         01860000
      *-----------------------*                                         01870000
                                                                        01880000
           MOVE '9382-CHECK-PENDING-AMND'   TO WS-PARAGRAPH-NAME        01890000
                                                                        00051700
           EXEC SQL                                                     00051800
                SELECT 1                                                00051900
                  INTO :WS-AMND-CHECK                                   00052010
                  FROM VDPM16_MCA_AMND       DPM16                      00052040
                      ,VDPM19_LINK_WORK      DPM19
                 WHERE DPM16.MCA_TMPLT_ID      = :WS-OLD-TEMPLATE-ID
                   AND DPM16.MCA_AMND_ID       = DPM19.MCA_AMND_ID
                   AND DPM19.MCA_VALUE_TYPE_CD > 'C'
                   AND DPM19.MCA_ACCS_STAT_CD  = 'O'
                   AND DPM19.AMND_STAT_CD      = 'P'
                FETCH FIRST ROW ONLY
                WITH UR
           END-EXEC                                                     00052060
                                                                        00052070
           EVALUATE SQLCODE                                             00052091
              WHEN 0                                                    00052092
                  SET AMND-PENDING           TO TRUE
              WHEN 100                                                  00052092
                  SET NO-AMND-PENDING        TO TRUE
              WHEN OTHER                                                00052092
                  MOVE 'VDPM19_LINK_WORK'    TO WS-TABLE-NAME            0176000
                  PERFORM 9500-SQL-ERROR                                 0181000
           END-EVALUATE                                                 00052102
           .
      *-----------------------*                                         01850000
       9400-CHECK-DEBUG-TABLE.                                          01860000
      *-----------------------*                                         01870000
                                                                        01880000
           MOVE '9400-CHECK-DEBUG-TABLE'    TO WS-PARAGRAPH-NAME        01890000
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
                  PERFORM 9500-SQL-ERROR                                 0181000
           END-EVALUATE                                                 00052102
           .
      *---------------*                                                 01850000
       9500-SQL-ERROR.                                                  01860000
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
              DISPLAY "WS-OLD-TEMPLATE-ID " WS-OLD-TEMPLATE-ID
              DISPLAY "WS-NEW-TEMPLATE-ID " WS-NEW-TEMPLATE-ID
           END-IF

           IF WS-NEW-TEMPLATE-ID = 0
              MOVE WS-OLD-TEMPLATE-ID      TO WS-NEW-TEMPLATE-ID
           END-IF

           MOVE WS-NEW-TEMPLATE-ID         TO LS-NEW-TEMPLATE-ID
           MOVE WS-NEW-TEMPLATE-TYPE       TO LS-TEMPLATE-TYPE

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
              DISPLAY 'LS-NEW-TEMPLATE-ID '  LS-NEW-TEMPLATE-ID
              DISPLAY 'ENDING TIME: '        WS-CURRENT-TIMESTAMP
           END-IF
           .
           COPY DB2000IC.
