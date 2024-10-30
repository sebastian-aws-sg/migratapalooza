       IDENTIFICATION DIVISION.
       PROGRAM-ID.    DPMXDADC.
       AUTHOR.        COGNIZANT.

      ******************************************************************
      *THIS IS AN UNPUBLISHED PROGRAM OWNED BY ISCC                    *
      *IN WHICH A COPYRIGHT SUBSISTS AS OF JULY 2006.                  *
      *                                                                *
      ******************************************************************
      **LNKCTL**
      *    INCLUDE SYSLIB(DSNRLI)
      *    MODE AMODE(31) RMODE(ANY)
      *    ENTRY DPMXDADC
      *    NAME  DPMXDADC(R)
      *
      ******************************************************************
      **         P R O G R A M   D O C U M E N T A T I O N            **
      ******************************************************************
      * SYSTEM:    DERIV/SERV MCA XPRESS                               *
      * PROGRAM:   DPMXDADC                                            *
      *                                                                *
      *                                                                *
      * THIS STORED PROCEDURE IS TO UPLOAD THE DOCUMENT IN THE         *
      * DATABASE.THIS STORED PROCEDURE IS UTILIZED FOR UPLOADING       *
      * PRE-EXISTING AND OTHER DOCUMENTS.                              *
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
      * VDTM54_DEBUG_CNTRL    - DEBUG CONTROL TABLE                    *
      *                                                                *
      *----------------------------------------------------------------*
      * INCLUDES:
      * ---------
      * SQLCA    - DB2 COMMAREA
      * DPM0101  - DCLGEN FOR D0005
      * DPM0201  - DCLGEN FOR VDPM02_DELR_CMPNY
      * DPM0901  - DCLGEN FOR D0004
      * DPM1201  - DCLGEN FOR VDPM12_MCA_DOC
      * DPM1401  - DCLGEN FOR D0006
      * DTM5401  - DCLGEN FOR VDTM54_DEBUG_CNTRL
      *----------------------------------------------------------------
      *----------------------------------------------------------------*
      * COPYBOOK:                                                      *
      * ---------                                                      *
      * DB2000IA                                                       *
      * DERDTEDT                                                       *
      * DB2000IB                                                       *
      * DB2000IC                                                       *
      *----------------------------------------------------------------
      *              M A I N T E N A N C E   H I S T O R Y            *
                                                                     *
      *                                                               *
      * DATE CHANGED    VERSION     PROGRAMMER                        *
      * ------------    -------     --------------------              *
      *                                                               *
      * 10/11/2007        01.00     COGNIZANT                         *
      *                             INITIAL IMPLEMENTATION            *
      *                                                               *
      * 01/20/2008        02.00     COGNIZANT                         *
      *                             MODIFIED TO TAKE THE LENGTH OF    *
      *                             DOCUMENT PASSED AS A INPUT        *
      *                             PARAMETER RATHER THAN CALCULATING *
      *                             THE LENGTH OF THE DOCUMENT.       *
      *                                                               *
      *****************************************************************
       ENVIRONMENT DIVISION.
       DATA DIVISION.
       WORKING-STORAGE SECTION.
       01  WS-PROGRAM                      PIC X(08) VALUE 'DPMXDADC'.
       01  WS-VARIABLES.
             05  WS-DASHES                 PIC X(40) VALUE ALL '='.
             05  WS-CMPNY-TYPE-CD          PIC X(01).
             05  WS-CMPNY-ID               PIC X(08).
             05  WS-DOC-VIEW-IN            PIC X(01).
             05  WS-CMPNY-NM               PIC X(255).
             05  WS-DELR-CMPNY-ID          PIC X(08) VALUE SPACES.
             05  WS-CLNT-CMPNY-ID          PIC X(08) VALUE SPACES.
             05  WS-IN-MCA-EXE-TS.
                 10 WS-IN-MCA-EXE-DATE.
                     15 WS-IN-MCA-EXE-YYYY PIC X(04).
                     15 WS-IN-MCA-EXE-FIL1 PIC X(01) VALUE '-'.
                     15 WS-IN-MCA-EXE-MM   PIC X(02).
                     15 WS-IN-MCA-EXE-FIL2 PIC X(01) VALUE '-'.
                     15 WS-IN-MCA-EXE-DD   PIC X(02).
                 10 WS-IN-MCA-EXE-REM      PIC X(16).
             05  WS-CALL-PROGRAM           PIC X(08) VALUE SPACES.
                 88 DERC01B0               VALUE 'DERC01B0'.
                 88 DPMXDUPL               VALUE 'DPMXDUPL'.
             05  WS-EMPTY-EXEC-DATE        PIC X(50) VALUE
                 'Execution date cannot be empty'.
             05  WS-INVLD-EXEC-DATE        PIC X(50) VALUE
                 'Execution date is invalid'.
             05  WS-EMPTY-DOC-VIEW-IN      PIC X(50) VALUE
                 'Input doc view indicator is empty'.
             05  WS-INVLD-DOC-VIEW-IN      PIC X(50) VALUE
                 'Input doc view indicator is invalid'.
             05  WS-EMPTY-DOC-TYPE-CD      PIC X(50) VALUE
                 'Input document type is empty'.
             05  WS-INVLD-DOC-TYPE-CD      PIC X(50) VALUE
                 'Input document type is invalid'.
             05  WS-DUP-DOCUMENT           PIC X(28) VALUE
                 'Document already exists for '.
             05  WS-EMPTY-DOC-DS           PIC X(50) VALUE
                 'File is mandatory'.
             05  WS-INVLD-ASGN-CMP-ID      PIC X(50) VALUE
                 'Assigned Counterparty does not exist'.
             05  WS-DOC-TYPE-IND-SW        PIC X(01).
                 88  WS-DOC-TYPE-PRE-EXIST VALUE 'P'.
                 88  WS-DOC-TYPE-OTHER     VALUE 'O'.
             05  WS-DOC-VIEW-IND-SW        PIC X(01).
                 88  WS-DOC-VIEW-IND-YES   VALUE 'Y'.
                 88  WS-DOC-VIEW-IND-NO    VALUE 'N'.
             05  WS-MCA-DOC-VIEW-IN        PIC X(01).
             05  WS-MCA-CMPNY-ID           PIC X(08).
             05 WS-IN-DOC-DS               PIC X(216).
             05 WS-TMPLT-ID                PIC S9(9) COMP  VALUE 0.
             05 WS-TEMPLT-SEQ              PIC S9(9) COMP.
             05 WS-VALUE-ID                PIC S9(18)V COMP-3.
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

      *
       01 WS-PASS-AREA.
          05 WS-IN-CMPNY-ID              PIC X(8).
          05 WS-IN-MCA-TMPLT-ID          PIC S9(9) COMP.
          05 WS-IN-MCA-DOC-DS            PIC X(216).
          05 WS-IN-DOC-TYPE-CD           PIC X(1).
          05 WS-IN-ROW-UPDT-USER-ID      PIC X(10).
          05 WS-IN-DOC-OBJ-TX            USAGE IS SQL
                                         TYPE IS BLOB(2097152).
          05 WS-OUT-DOC-ID               PIC S9(18)V COMP-3.
          05 WS-OUT-SQLCODE              PIC +(9)9.
038100


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

      *** DATE EDIT ROUTINE   ***
       COPY DERDTEDT.

       LINKAGE SECTION.

      *PICTURE CLAUSE FOR OUTSQLCA - PIC X(179) - FOR LINKAGE SECTION
       COPY  DB2000IB.
       01  LS-SP-ERROR-AREA.
           49 LS-SP-ERROR-AREA-LEN         PIC S9(4) USAGE COMP.
           49 LS-SP-ERROR-AREA-TXT         PIC X(300).
       01  LS-SP-RC                        PIC X(04).
       01  LS-IN-UPL-CMP-ID                PIC X(08).
       01  LS-IN-ASGN-CMP-ID               PIC X(08).
       01  LS-IN-MCA-TMPLT-ID              PIC S9(9) COMP.
       01  LS-IN-MCA-TMPLT-NM.
           49 LS-IN-TMPLT-NM-LEN           PIC S9(4) USAGE COMP.
           49 LS-IN-TMPLT-NM-TXT           PIC X(500).
       01  LS-IN-MCA-SHORT-NM              PIC X(150).
       01  LS-IN-ATTRB-PRDCT-ID            PIC X(08).
       01  LS-IN-ATTRB-SUB-PRDCT-ID        PIC X(08).
       01  LS-IN-ATTRB-REGN-ID             PIC X(08).
       01  LS-IN-MCA-DOC-DS                PIC X(216).
       01  LS-IN-MCA-DOC-TYPE-CD           PIC X(01).
       01  LS-IN-MCA-DOC-VIEW-IN           PIC X(01).
       01  LS-IN-MCA-EXE-TS                PIC X(26).
       01  LS-IN-MCA-USER-ID               PIC X(10).
       01  LS-IN-MCA-DOC-OBJ-TX            USAGE IS SQL
                                           TYPE IS BLOB(2097152).

       PROCEDURE DIVISION USING  OUTSQLCA,
                                 LS-SP-ERROR-AREA,
                                 LS-SP-RC,
                                 LS-IN-UPL-CMP-ID,
                                 LS-IN-ASGN-CMP-ID,
                                 LS-IN-MCA-TMPLT-ID,
                                 LS-IN-MCA-TMPLT-NM,
                                 LS-IN-MCA-SHORT-NM,
                                 LS-IN-ATTRB-PRDCT-ID,
                                 LS-IN-ATTRB-SUB-PRDCT-ID,
                                 LS-IN-ATTRB-REGN-ID,
                                 LS-IN-MCA-DOC-DS,
                                 LS-IN-MCA-DOC-TYPE-CD,
                                 LS-IN-MCA-DOC-VIEW-IN,
                                 LS-IN-MCA-EXE-TS,
                                 LS-IN-MCA-USER-ID,
                                 LS-IN-MCA-DOC-OBJ-TX.
      *---------*
       0000-MAIN.
      *---------*

           PERFORM 1000-INITIALIZE

           PERFORM 2000-VALIDATE-INPUT

           PERFORM 3000-PROCESS-DOCUMENT

           IF DISPLAY-PARAMETERS
              PERFORM 9100-DISPLAY-DATA
           END-IF


           PERFORM 9990-GOBACK
           .
      *------------------------*
       1000-INITIALIZE.
      *------------------------*

           MOVE '1000-INITIALIZE'          TO WS-PARAGRAPH-NAME
           MOVE SPACES                     TO OUTSQLCA,
                                              LS-SP-ERROR-AREA-TXT
           MOVE 'SP00'                     TO LS-SP-RC
      *    CONVERT THE INPUT VALUES INTO UPPER-CASE
           MOVE FUNCTION UPPER-CASE(LS-IN-MCA-DOC-TYPE-CD)
                                           TO LS-IN-MCA-DOC-TYPE-CD
           MOVE FUNCTION UPPER-CASE(LS-IN-MCA-DOC-DS)
                                           TO WS-IN-DOC-DS
           MOVE FUNCTION UPPER-CASE(LS-IN-MCA-DOC-VIEW-IN)
                                           TO LS-IN-MCA-DOC-VIEW-IN
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
              DISPLAY 'DPMXDADC STARTED AT      :' WS-TS
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

           IF LS-IN-MCA-DOC-TYPE-CD > SPACES
              MOVE LS-IN-MCA-DOC-TYPE-CD   TO  WS-DOC-TYPE-IND-SW
              IF  WS-DOC-TYPE-PRE-EXIST OR WS-DOC-TYPE-OTHER
                  CONTINUE
              ELSE
                  MOVE LENGTH OF WS-INVLD-DOC-TYPE-CD
                                     TO   LS-SP-ERROR-AREA-LEN
                  MOVE WS-INVLD-DOC-TYPE-CD TO LS-SP-ERROR-AREA-TXT
                  MOVE 'SP50'               TO LS-SP-RC
                  PERFORM 9100-DISPLAY-DATA
                  PERFORM 9990-GOBACK
              END-IF
           ELSE
              MOVE LENGTH OF WS-EMPTY-DOC-TYPE-CD
                                            TO LS-SP-ERROR-AREA-LEN
              MOVE WS-EMPTY-DOC-TYPE-CD     TO LS-SP-ERROR-AREA-TXT
              MOVE 'SP50'                   TO LS-SP-RC
              PERFORM 9100-DISPLAY-DATA
              PERFORM 9990-GOBACK
           END-IF

           IF LS-IN-ASGN-CMP-ID  > SPACES
              PERFORM 2400-VAL-ASGN-CMP-ID
           END-IF

           IF LS-IN-MCA-DOC-VIEW-IN  > SPACES
              MOVE LS-IN-MCA-DOC-VIEW-IN   TO  WS-DOC-VIEW-IND-SW
              IF  WS-DOC-VIEW-IND-YES OR WS-DOC-VIEW-IND-NO
                  CONTINUE
              ELSE
                  MOVE LENGTH OF WS-INVLD-DOC-VIEW-IN
                                            TO LS-SP-ERROR-AREA-LEN
                  MOVE WS-INVLD-DOC-VIEW-IN TO LS-SP-ERROR-AREA-TXT
                  MOVE 'SP50'               TO LS-SP-RC
                  PERFORM 9100-DISPLAY-DATA
                  PERFORM 9990-GOBACK
              END-IF
           ELSE
              MOVE LENGTH OF WS-EMPTY-DOC-VIEW-IN
                                            TO LS-SP-ERROR-AREA-LEN
              MOVE WS-EMPTY-DOC-VIEW-IN     TO LS-SP-ERROR-AREA-TXT
              MOVE 'SP50'                   TO LS-SP-RC
              PERFORM 9100-DISPLAY-DATA
              PERFORM 9990-GOBACK
           END-IF

           IF WS-DOC-TYPE-PRE-EXIST
              IF LS-IN-MCA-EXE-TS  > SPACES
                 MOVE LS-IN-MCA-EXE-TS     TO WS-IN-MCA-EXE-TS
                 PERFORM 2100-VALIDATE-DATES
                 IF DERC01C0-88-NO-ERROR
                    PERFORM 2200-RECORD-EXISTS
                 ELSE
                    PERFORM 9100-DISPLAY-DATA
                    PERFORM 9990-GOBACK
                 END-IF
              ELSE
                 MOVE LENGTH OF WS-EMPTY-EXEC-DATE
                                            TO LS-SP-ERROR-AREA-LEN
                 MOVE WS-EMPTY-EXEC-DATE   TO LS-SP-ERROR-AREA-TXT
                 MOVE 'SP01'               TO LS-SP-RC
                 PERFORM 9100-DISPLAY-DATA
                 PERFORM 9990-GOBACK
              END-IF
           ELSE
              IF WS-DOC-TYPE-OTHER
                  IF WS-IN-DOC-DS > SPACES
                     PERFORM 2300-VALIDATE-DOC-DS
                  ELSE
                     MOVE LENGTH OF WS-EMPTY-DOC-DS
                                            TO LS-SP-ERROR-AREA-LEN
                     MOVE WS-EMPTY-DOC-DS TO LS-SP-ERROR-AREA-TXT
                     MOVE 'SP03'          TO LS-SP-RC
                     PERFORM 9100-DISPLAY-DATA
                     PERFORM 9990-GOBACK
                  END-IF
              END-IF
           END-IF

           .
      *-------------------------*
       2100-VALIDATE-DATES.
      *-------------------------*
           MOVE '2100-VALIDATE-DATES'       TO WS-PARAGRAPH-NAME

           IF DISPLAY-PARAMETERS
              DISPLAY WS-PARAGRAPH-NAME
           END-IF

           MOVE WS-IN-MCA-EXE-DATE          TO DERC01C0-DATE
           MOVE 'DERC01B0'                  TO WS-CALL-PROGRAM
           SET DERC01C0-DATE-FORMAT-ISO     TO TRUE
           CALL WS-CALL-PROGRAM USING DERC01C0-DATE-EDIT-AREA

           IF DERC01C0-88-ERROR
               MOVE LENGTH OF WS-INVLD-EXEC-DATE
                                              TO LS-SP-ERROR-AREA-LEN
               MOVE WS-INVLD-EXEC-DATE        TO LS-SP-ERROR-AREA-TXT
               MOVE 'SP01'                    TO LS-SP-RC
               PERFORM 9100-DISPLAY-DATA
               PERFORM 9990-GOBACK
           END-IF
           .
      *------------------------*
       2200-RECORD-EXISTS.
      *------------------------*

           MOVE '2200-RECORD-EXISTS'      TO WS-PARAGRAPH-NAME

           IF DISPLAY-PARAMETERS
              DISPLAY WS-PARAGRAPH-NAME
           END-IF


           EXEC SQL

              SELECT  MCA_TMPLT_ID INTO :WS-TMPLT-ID
                FROM  D0006
                WHERE MCA_ISDA_TMPLT_ID = :LS-IN-MCA-TMPLT-ID
                AND   MCA_TMPLT_TYPE_CD = :LS-IN-MCA-DOC-TYPE-CD
                AND   DELR_CMPNY_ID = :LS-IN-UPL-CMP-ID
                AND   CLNT_CMPNY_ID = :LS-IN-ASGN-CMP-ID
                AND   MCA_EXE_TS = :LS-IN-MCA-EXE-TS
                WITH UR
           END-EXEC

           EVALUATE SQLCODE
              WHEN 0
                 STRING
                   WS-DUP-DOCUMENT
                   LS-IN-MCA-SHORT-NM
                    DELIMITED BY SIZE
                    INTO LS-SP-ERROR-AREA-TXT
                 END-STRING
                 MOVE LENGTH OF LS-SP-ERROR-AREA-TXT
                                           TO LS-SP-ERROR-AREA-LEN
                 MOVE 'SP02'               TO LS-SP-RC
                 PERFORM 9100-DISPLAY-DATA
                 PERFORM 9990-GOBACK
              WHEN 100
                 INITIALIZE SQLCODE
              WHEN OTHER
                 PERFORM 9000-SQL-ERROR
           END-EVALUATE
           .

      *------------------------*
       2300-VALIDATE-DOC-DS.
      *------------------------*

           MOVE '2300-VALIDATE-DOC-DS'    TO WS-PARAGRAPH-NAME

           IF DISPLAY-PARAMETERS
              DISPLAY WS-PARAGRAPH-NAME
           END-IF


           EXEC SQL

              SELECT  DPM12.MCA_VALUE_ID
                     ,DPM09.CMPNY_ID
                     ,DPM09.MCA_DOC_VIEW_IN
                    INTO :WS-VALUE-ID
                        ,:WS-CMPNY-ID
                        ,:WS-DOC-VIEW-IN
                FROM  D0004 DPM09,
                      VDPM12_MCA_DOC  DPM12
                WHERE UCASE(DPM12.MCA_DOC_DS) = :WS-IN-DOC-DS
                AND   DPM12.MCA_DOC_TYPE_CD = :LS-IN-MCA-DOC-TYPE-CD
                AND   DPM12.CMPNY_ID = :LS-IN-UPL-CMP-ID
                AND   DPM12.DOC_DEL_CD = ' '
                AND  (DPM09.MCA_VALUE_ID  = DPM12.MCA_VALUE_ID
                   AND DPM09.CMPNY_ID     = :LS-IN-ASGN-CMP-ID)
                WITH UR
           END-EXEC

           EVALUATE SQLCODE
              WHEN 0
                 IF WS-DOC-VIEW-IN = 'D'
                    CONTINUE
                 ELSE
                    PERFORM 2320-RETREIVE-ASGN-CMP-NM
                    STRING
                      WS-DUP-DOCUMENT
                      WS-CMPNY-NM
                       DELIMITED BY SIZE
                       INTO LS-SP-ERROR-AREA-TXT
                    END-STRING
                    MOVE LENGTH OF LS-SP-ERROR-AREA-TXT
                                           TO LS-SP-ERROR-AREA-LEN
                    MOVE 'SP02'            TO LS-SP-RC
                    PERFORM 9990-GOBACK
                 END-IF
              WHEN 100
                 INITIALIZE SQLCODE
              WHEN OTHER
                 PERFORM 9000-SQL-ERROR
           END-EVALUATE
           .
      *----------------------------*
       2320-RETREIVE-ASGN-CMP-NM.
      *----------------------------*

           MOVE '2320-RETREIVE-ASGN-CMP-NM'
                                           TO WS-PARAGRAPH-NAME

           IF DISPLAY-PARAMETERS
              DISPLAY WS-PARAGRAPH-NAME
           END-IF


           EXEC SQL

              SELECT  CMPNY_NM INTO :WS-CMPNY-NM
                FROM  D0005
                WHERE CMPNY_ID  = :LS-IN-ASGN-CMP-ID
                WITH UR
           END-EXEC

           EVALUATE SQLCODE
              WHEN 0
                 CONTINUE
              WHEN 100
                 EXEC SQL
                      SELECT CMPNY_NM INTO :WS-CMPNY-NM
                      FROM VDPM02_DELR_CMPNY
                      WHERE CMPNY_ID = :LS-IN-ASGN-CMP-ID
                      WITH UR
                 END-EXEC
                 EVALUATE SQLCODE
                   WHEN 0
                      CONTINUE
                   WHEN OTHER
                      PERFORM 9000-SQL-ERROR
                 END-EVALUATE
              WHEN OTHER
                 PERFORM 9000-SQL-ERROR
           END-EVALUATE
           .

      *---------------------*
       2400-VAL-ASGN-CMP-ID.
      *---------------------*

           MOVE '2400-VAL-ASGN-CMP-ID'   TO WS-PARAGRAPH-NAME

           IF DISPLAY-PARAMETERS
              DISPLAY WS-PARAGRAPH-NAME
           END-IF


           EXEC SQL

              SELECT  CMPNY_ID INTO :WS-CMPNY-ID
                FROM  D0005
                WHERE CMPNY_ID  = :LS-IN-ASGN-CMP-ID
                WITH UR
           END-EXEC

           EVALUATE SQLCODE
              WHEN 0
                 CONTINUE
              WHEN 100
                 EXEC SQL
                    SELECT CMPNY_ID INTO :WS-CMPNY-ID
                      FROM VDPM02_DELR_CMPNY
                      WHERE CMPNY_ID = :LS-IN-ASGN-CMP-ID
                      WITH UR
                 END-EXEC
                 EVALUATE SQLCODE
                   WHEN 0
                      CONTINUE
                   WHEN 100
                      MOVE LENGTH OF WS-INVLD-ASGN-CMP-ID
                                           TO LS-SP-ERROR-AREA-LEN
                      MOVE WS-INVLD-ASGN-CMP-ID TO LS-SP-ERROR-AREA-TXT
                      MOVE 'SP03'               TO LS-SP-RC
                      PERFORM 9100-DISPLAY-DATA
                      PERFORM 9990-GOBACK
                   WHEN OTHER
                      PERFORM 9000-SQL-ERROR
                 END-EVALUATE
              WHEN OTHER
                 PERFORM 9000-SQL-ERROR
           END-EVALUATE
           .

      *---------------------*
       3000-PROCESS-DOCUMENT.
      *---------------------*

           MOVE '3000-PROCESS-DOCUMENT'
                                           TO WS-PARAGRAPH-NAME

           IF DISPLAY-PARAMETERS
              DISPLAY WS-PARAGRAPH-NAME
           END-IF

           IF WS-DOC-TYPE-PRE-EXIST                                     07090062
               MOVE LS-IN-UPL-CMP-ID       TO WS-DELR-CMPNY-ID          07090062
               MOVE LS-IN-ASGN-CMP-ID      TO WS-CLNT-CMPNY-ID          07090062
               PERFORM 3100-INSERT-PRE-EXIST-TEMPLATE                   07090062
           END-IF                                                       07090062

           PERFORM 3200-INSERT-MCA-DOC                                  07090062
           PERFORM 3300-PROCESS-MCA-DOC-USER                            07090062
           .
      *---------------------*
       3100-INSERT-PRE-EXIST-TEMPLATE.
      *---------------------*

           MOVE '3100-INSERT-PRE-EXIST-TEMPLATE'
                                           TO WS-PARAGRAPH-NAME

           IF DISPLAY-PARAMETERS
              DISPLAY WS-PARAGRAPH-NAME
           END-IF

           PERFORM 3110-TEMPLT-SEQ-GEN

           MOVE LS-IN-TMPLT-NM-LEN   TO   D014-MCA-TMPLT-NM-LEN
           MOVE LS-IN-TMPLT-NM-TXT(1:D014-MCA-TMPLT-NM-LEN)
                                     TO   D014-MCA-TMPLT-NM-TEXT

           EXEC SQL
              INSERT INTO D0006
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
                 VALUES
                 (:WS-TMPLT-ID
                 ,:D014-MCA-TMPLT-NM
                 ,:LS-IN-MCA-SHORT-NM
                 ,' '
                 ,:LS-IN-MCA-DOC-TYPE-CD
                 ,:WS-DELR-CMPNY-ID
                 ,:WS-CLNT-CMPNY-ID
                 ,:LS-IN-ATTRB-PRDCT-ID
                 ,:LS-IN-ATTRB-SUB-PRDCT-ID
                 ,:LS-IN-ATTRB-REGN-ID
                 ,'01/01/1900'
                 ,'01/01/1900'
                 ,' '
                 ,:LS-IN-MCA-EXE-TS
                 ,' '
                 ,' '
                 ,:LS-IN-MCA-TMPLT-ID
                 ,:LS-IN-MCA-TMPLT-ID
                 ,' '
                 ,' '
                 ,CURRENT TIMESTAMP
                 ,:LS-IN-MCA-USER-ID)
           END-EXEC

           EVALUATE SQLCODE
              WHEN ZEROES
                 CONTINUE
              WHEN OTHER
                 PERFORM 9000-SQL-ERROR
           END-EVALUATE

           .
      *--------------------------*
       3110-TEMPLT-SEQ-GEN.
      *--------------------------*

           MOVE '3110-TEMPLT-SEQ-GEN'      TO WS-PARAGRAPH-NAME

           IF DISPLAY-PARAMETERS
              DISPLAY WS-PARAGRAPH-NAME
           END-IF


           EXEC SQL
               SET :WS-TEMPLT-SEQ = (NEXT VALUE FOR DPM.SQDPM141)
           END-EXEC

           EVALUATE SQLCODE
              WHEN ZEROES
                 MOVE WS-TEMPLT-SEQ      TO WS-TMPLT-ID
              WHEN OTHER
                 PERFORM 9000-SQL-ERROR
           END-EVALUATE

           .
      *---------------------*
       3200-INSERT-MCA-DOC.
      *---------------------*

           MOVE '3200-INSERT-MCA-DOC'
                                           TO WS-PARAGRAPH-NAME

           IF DISPLAY-PARAMETERS
              DISPLAY WS-PARAGRAPH-NAME
           END-IF

           MOVE LS-IN-UPL-CMP-ID           TO WS-IN-CMPNY-ID            07090062
           MOVE WS-TMPLT-ID                TO WS-IN-MCA-TMPLT-ID        07090062
           MOVE LS-IN-MCA-DOC-DS           TO WS-IN-MCA-DOC-DS          07090062
           MOVE LS-IN-MCA-DOC-TYPE-CD      TO WS-IN-DOC-TYPE-CD         07090062
           MOVE LS-IN-MCA-USER-ID          TO WS-IN-ROW-UPDT-USER-ID    07090062
           MOVE LS-IN-MCA-DOC-OBJ-TX-LENGTH                             07090062
                                           TO WS-IN-DOC-OBJ-TX-LENGTH
           MOVE LS-IN-MCA-DOC-OBJ-TX-DATA(1:WS-IN-DOC-OBJ-TX-LENGTH)    07090062
                                           TO WS-IN-DOC-OBJ-TX-DATA     07090062
           MOVE 'DPMXDUPL'                 TO WS-CALL-PROGRAM
           CALL WS-CALL-PROGRAM USING WS-PASS-AREA

           MOVE WS-OUT-SQLCODE             TO SQLCODE

           EVALUATE SQLCODE
              WHEN 0
                 MOVE 'SP00'                TO LS-SP-RC
              WHEN OTHER
                 MOVE 'TDPM12_MCA_DOC'      TO WS-TABLE-NAME
                 PERFORM 9000-SQL-ERROR
           END-EVALUATE
           .
      *---------------------*
       3300-PROCESS-MCA-DOC-USER.
      *---------------------*

           MOVE '3300-PROCESS-MCA-DOC-USER'
                                           TO WS-PARAGRAPH-NAME

           IF DISPLAY-PARAMETERS
              DISPLAY WS-PARAGRAPH-NAME
           END-IF

           MOVE 'N'                        TO WS-MCA-DOC-VIEW-IN        07090062
           IF LS-IN-UPL-CMP-ID =  LS-IN-ASGN-CMP-ID                     07090062
               MOVE LS-IN-UPL-CMP-ID       TO WS-MCA-CMPNY-ID           07090062
           ELSE                                                         07090062
               IF (LS-IN-UPL-CMP-ID NOT = LS-IN-ASGN-CMP-ID             07090062
               AND WS-DOC-VIEW-IND-NO)                                  07090062
                  MOVE LS-IN-ASGN-CMP-ID                                07090062
                                       TO WS-MCA-CMPNY-ID               07090062
               ELSE                                                     07090062
                  MOVE 'Y'             TO WS-MCA-DOC-VIEW-IN            07090062
                   MOVE LS-IN-UPL-CMP-ID                                07090062
                                       TO WS-MCA-CMPNY-ID               07090062
               END-IF                                                   07090062
           END-IF                                                       07090062
           PERFORM 3310-INSERT-MCA-DOC-USER                             07090062
           IF  WS-DOC-VIEW-IND-YES
               MOVE 'Y'                    TO WS-MCA-DOC-VIEW-IN        07090062
               MOVE LS-IN-ASGN-CMP-ID TO WS-MCA-CMPNY-ID                07090062
               PERFORM 3310-INSERT-MCA-DOC-USER                         07090062
           END-IF                                                       07090062
           .
      *---------------------*
       3310-INSERT-MCA-DOC-USER.
      *---------------------*

           MOVE '3310-INSERT-MCA-DOC-USER'
                                           TO WS-PARAGRAPH-NAME

           IF DISPLAY-PARAMETERS
              DISPLAY WS-PARAGRAPH-NAME
           END-IF


           EXEC SQL
              INSERT INTO D0004
                 (MCA_VALUE_ID
                 ,CMPNY_ID
                 ,MCA_DOC_VIEW_IN
                 ,ROW_UPDT_TS
                 ,ROW_UPDT_USER_ID)
                 VALUES
                 (:WS-OUT-DOC-ID
                 ,:WS-MCA-CMPNY-ID
                 ,:WS-MCA-DOC-VIEW-IN
                 ,CURRENT TIMESTAMP
                 ,:LS-IN-MCA-USER-ID)
           END-EXEC

           EVALUATE SQLCODE
              WHEN ZEROES
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
087800     MOVE 49                          TO LS-SP-ERROR-AREA-LEN
087800     MOVE 'Database error has occurred. Please contact DTCC.'
087800                                      TO LS-SP-ERROR-AREA-TXT
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
102800         DISPLAY 'SP-ERROR-AREA :' LS-SP-ERROR-AREA-TXT
102800                                   (1:LS-SP-ERROR-AREA-LEN)
102900         DISPLAY 'SP-RC                    :' LS-SP-RC
102900         DISPLAY 'LS-IN-UPL-CMP-ID         :' LS-IN-UPL-CMP-ID
102900         DISPLAY 'LS-IN-ASGN-CMP-ID        :' LS-IN-ASGN-CMP-ID
102900         DISPLAY 'LS-IN-MCA-TMPLT-ID       :' LS-IN-MCA-TMPLT-ID
102900         DISPLAY 'LS-IN-MCA-TMPLT-NM       :' LS-IN-MCA-TMPLT-NM
102900         DISPLAY 'LS-IN-MCA-SHORT-NM       :' LS-IN-MCA-SHORT-NM
102900         DISPLAY 'LS-IN-MCA-DOC-DS         :' LS-IN-MCA-DOC-DS
102900         DISPLAY 'LS-IN-MCA-DOC-TYPE-CD   :' LS-IN-MCA-DOC-TYPE-CD
102900         DISPLAY 'LS-IN-MCA-DOC-VIEW-IN  :'  LS-IN-MCA-DOC-VIEW-IN
102900         DISPLAY 'LS-IN-MCA-EXE-TS         :' LS-IN-MCA-EXE-TS
102900         DISPLAY 'LS-IN-MCA-USER-ID        :' LS-IN-MCA-USER-ID
102600         DISPLAY WS-DASHES
103000
107000     .
      *------------------------*
       9990-GOBACK.
      *------------------------*

            PERFORM 9999-FORMAT-SQLCA
            IF DISPLAY-PARAMETERS
               DISPLAY WS-DASHES
               DISPLAY 'OUTSQLCA FOR DPMXDADC    :' OUTSQLCA
               DISPLAY WS-DASHES
               EXEC SQL
                   SET :WS-TS = CURRENT TIMESTAMP
               END-EXEC
               DISPLAY 'DPMXDADC ENDED AT        :' WS-TS
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