       IDENTIFICATION DIVISION.
       PROGRAM-ID.    DPMXDDRO.
       AUTHOR.        COGNIZANT.

      ******************************************************************
      *THIS IS AN UNPUBLISHED PROGRAM OWNED BY ISCC                    *
      *IN WHICH A COPYRIGHT SUBSISTS AS OF JULY 2006.                  *
      *                                                                *
      ******************************************************************
      **LNKCTL**
      *    INCLUDE SYSLIB(DSNRLI)
      *    MODE AMODE(31) RMODE(ANY)
      *    ENTRY DPMXDDRO
      *    NAME  DPMXDDRO(R)
      *
      ******************************************************************
      **         P R O G R A M   D O C U M E N T A T I O N            **
      ******************************************************************
      * SYSTEM:    DERIV/SERV MCA XPRESS                               *
      * PROGRAM:   DPMXDDRO                                            *
      *                                                                *
      *                                                                *
      * THIS STORED PROCEDURE IS USED TO DELETE A COUNTERPARTY         *
      * AND REASSIGN OR DELETE THE DOCUMENTS IF ANY TO ANOTHER         *
      * COUNTER PARTY.                                                 *
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
      * NSCC.TDPM01_MCA_CMPNY - MASTER TABLE THAT HAS THE MCA          *
      * REGISTERED COMPANY DETAILS                                     *
      *                                                                *
      * NSCC.TDPM02_DELR_CMPNY - TABLE THAT HAS NON MCA REGISTERED     *
      * COMPANY DETAILS.                                               *
      *                                                                *
      * VDTM54_DEBUG_CNTRL    - DEBUG CONTROL TABLE                    *
      *                                                                *
      *----------------------------------------------------------------*
      * INCLUDES:
      * ---------
      * SQLCA    - DB2 COMMAREA
      * DPM0901  - DCLGEN FOR D0004
      * DPM1201  - DCLGEN FOR VDPM12_MCA_DOC
      * DPM1401  - DCLGEN FOR D0006
      * DPM0101  - DCLGEN FOR D0005
      * DPM0201  - DCLGEN FOR VDPM02_DELR_CMPNY
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
      *****************************************************************
       ENVIRONMENT DIVISION.
       DATA DIVISION.
       WORKING-STORAGE SECTION.
       01  WS-PROGRAM                      PIC X(08) VALUE 'DPMXDDRO'.
       01  WS-VARIABLES.
             05  WS-DASHES                 PIC X(40) VALUE ALL '='.
             05  WS-MCA-TMPLT-ID           PIC S9(9) COMP.
             05  WS-TMPLT-ID               PIC S9(9) COMP.
             05  WS-MCA-DOC-TYPE-CD        PIC X(01).
             05  WS-EMPTY-DEL-CMP-ID       PIC X(50) VALUE
                 'Delete company id is empty'.
             05  WS-INVLD-DEL-CMP-ID1      PIC X(50) VALUE
                 'Registered Counterparty cannot be deleted'.
             05  WS-INVLD-DEL-CMP-ID2      PIC X(50) VALUE
                 'Counterparty is already deleted'.
             05  WS-INVLD-NEW-CMP-ID      PIC X(50) VALUE
                 'Assigning Counterparty does not exist'.
             05  WS-EMPTY-LOG-CMP-ID       PIC X(50) VALUE
                 'Login company id is empty'.
             05  WS-EMPTY-USER-ID          PIC X(50) VALUE
                 'User id is empty'.
             05  WS-INVLD-OPR-TYPE         PIC X(50) VALUE
                 'Invalid operation flag'.
             05  WS-EMPTY-OPR-TYPE         PIC X(50) VALUE
                 'Operation flag is empty'.
             05  WS-INVLD-RAGN-ACT-IND     PIC X(50) VALUE
                 'Invalid Reassign Indicator Flag'.
             05  WS-EMPTY-RAGN-ACT-IND     PIC X(50) VALUE
                 'Reassign Indicator Flag is empty'.
             05  WS-EMP-NEW-CMP-ID         PIC X(50) VALUE
                 'New company id is empty'.
             05  WS-OPR-TYPE-IND-SW        PIC X(01).
                 88  WS-OPR-TYPE-DELETE    VALUE 'D'.
                 88  WS-DOC-TYPE-REASSIGN  VALUE 'R'.
             05  WS-RAGN-ACT-IND-SW        PIC X(01).
                 88  WS-RAGN-ACT-IND-INIT  VALUE 'I'.
                 88  WS-RAGN-ACT-IND-ALLOW VALUE 'A'.
             05  WS-REC-DELETED-SW         PIC X(01) VALUE 'N'.
                 88  WS-REC-NOT-DELETED    VALUE 'N'.
                 88  WS-REC-DELETED        VALUE 'Y'.
             05  WS-DUP-REC-EXISTS-SW     PIC X(01) VALUE 'N'.
                 88  WS-DUP-REC-NOT-EXISTS VALUE 'N'.
                 88  WS-DUP-REC-EXISTS     VALUE 'Y'.
             05  END-OF-DOC-USR-CSR-SW     PIC X(01) VALUE 'N'.
                 88  NOT-END-OF-DOC-USR-CSR VALUE 'N'.
                 88  END-OF-DOC-USR-CSR     VALUE 'Y'.
             05  END-OF-MCA-TMPLT-CSR-SW   PIC X(01) VALUE 'N'.
                 88  NOT-END-OF-MCA-TMPLT-CSR VALUE 'N'.
                 88  END-OF-MCA-TMPLT-CSR   VALUE 'Y'.
             05  END-OF-MCA-DOC-CSR-SW     PIC X(01) VALUE 'N'.
                 88  NOT-END-OF-MCA-DOC-CSR VALUE 'N'.
                 88  END-OF-MCA-DOC-CSR     VALUE 'Y'.
             05  ADDL-REC-FOUND-SW         PIC X(01) VALUE 'N'.
                 88  ADDL-REC-NOT-FOUND    VALUE 'N'.
                 88  ADDL-REC-FOUND        VALUE 'Y'.
             05  WS-IN-LOG-CMP-ID          PIC X(08).
             05  WS-IN-DEL-CMP-ID          PIC X(08).
             05  WS-IN-NEW-CMP-ID          PIC X(08).
             05  WS-MCA-VALUE-ID           PIC S9(18)V COMP-3.
             05  WS-CMPNY-ID               PIC X(08).
             05  WS-MCA-DOC-VIEW-IN        PIC X(01).
             05  WS-MCA-TMPLT-NM.
                 49 WS-TMPLT-NM-LEN        PIC S9(4) USAGE COMP.
                 49 WS-TMPLT-NM-TXT        PIC X(500).
             05  WS-MCA-TMPLT-SHORT-NM     PIC X(150).
             05  WS-MCA-DOC-DS             PIC X(216).
             05  WS-MCA-EXE-TS             PIC X(26).
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
012800************************************************************
012900*              D E C L A R E  C U R S O R S                *
013000************************************************************
013100
013200*  DECLARE CURSOR FOR DELETING DOCUMENT LINKED WITH A USER
013300
013400     EXEC SQL
013500         DECLARE DOC_USR_CSR CURSOR WITH HOLD FOR
013600            SELECT DPM09.MCA_VALUE_ID , DPM09.CMPNY_ID ,
013600                   DPM09.MCA_DOC_VIEW_IN,
013600                   DPM12.MCA_TMPLT_ID , DPM12.MCA_DOC_TYPE_CD,
013600                   DPM12.MCA_DOC_DS,
                         VALUE(DPM14.MCA_TMPLT_NM,' '),
                         VALUE(DPM14.MCA_TMPLT_SHORT_NM,' '),
                        VALUE(DPM14.MCA_EXE_TS,
                      '1900-01-01-01.01.01.000000')
014100            FROM  D0004 DPM09
014100                 ,VDPM12_MCA_DOC  DPM12
                        LEFT OUTER JOIN
                        D0006 DPM14
                        ON DPM12.MCA_TMPLT_ID = DPM14.MCA_TMPLT_ID
014200            WHERE  DPM09.CMPNY_ID   = :WS-IN-DEL-CMP-ID
014200            AND  (DPM12.CMPNY_ID    = :WS-IN-LOG-CMP-ID
014200                  AND DPM09.MCA_VALUE_ID = DPM12.MCA_VALUE_ID)
014200            FOR UPDATE
014800     END-EXEC.
013100
013200*  DECLARE CURSOR FOR CHECKING THE EXISTANCE OF A TEMPLATE
013300
           EXEC SQL
013500         DECLARE MCA_TMPLT_CSR CURSOR FOR
                  SELECT MCA_TMPLT_ID
                    FROM D0006
                    WHERE MCA_TMPLT_NM   = :WS-MCA-TMPLT-NM
                    AND MCA_TMPLT_SHORT_NM = :WS-MCA-TMPLT-SHORT-NM
                    AND MCA_TMPLT_TYPE_CD = :WS-MCA-DOC-TYPE-CD
                    AND DELR_CMPNY_ID = :WS-IN-LOG-CMP-ID
                    AND CLNT_CMPNY_ID = :WS-IN-NEW-CMP-ID
                    AND MCA_EXE_TS = :WS-MCA-EXE-TS
                    FETCH FIRST ROW ONLY
                    WITH UR
           END-EXEC.
013100
013200*  DECLARE CURSOR FOR CHECKING THE EXISTANCE OF A DOCUMENT
013300
           EXEC SQL
013500         DECLARE MCA_DOC_CSR CURSOR FOR
                  SELECT DPM12.MCA_VALUE_ID
                         ,DPM09.CMPNY_ID
                         ,DPM09.MCA_DOC_VIEW_IN
                    FROM D0004 DPM09,
                          VDPM12_MCA_DOC DPM12
                    WHERE UCASE(DPM12.MCA_DOC_DS) = :WS-MCA-DOC-DS
                    AND DPM12.MCA_DOC_TYPE_CD = :WS-MCA-DOC-TYPE-CD
                    AND DPM12.CMPNY_ID = :WS-IN-LOG-CMP-ID
                    AND DPM12.DOC_DEL_CD = ' '
                    AND (DPM09.MCA_VALUE_ID = DPM12.MCA_VALUE_ID
                       AND DPM09.CMPNY_ID = :WS-IN-NEW-CMP-ID)
                    FETCH FIRST ROW ONLY
                    WITH UR
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
       01  LS-IN-DEL-CMP-ID                PIC X(08).
       01  LS-IN-NEW-CMP-ID                PIC X(08).
       01  LS-IN-LOG-CMP-ID                PIC X(08).
       01  LS-IN-OPR-IND-CD                PIC X(01).
       01  LS-IN-USER-ID                   PIC X(10).
       01  LS-IO-RAGN-ACT-IND              PIC X(01).

       PROCEDURE DIVISION USING  OUTSQLCA,
                                 LS-SP-ERROR-AREA,
                                 LS-SP-RC,
                                 LS-IN-DEL-CMP-ID,
                                 LS-IN-NEW-CMP-ID,
                                 LS-IN-LOG-CMP-ID,
                                 LS-IN-OPR-IND-CD,
                                 LS-IN-USER-ID,
                                 LS-IO-RAGN-ACT-IND.
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
           MOVE SPACES                     TO OUTSQLCA, LS-SP-ERROR-AREA
           MOVE 'SP00'                     TO LS-SP-RC
      *    CONVERT THE INPUT VALUES INTO UPPER-CASE
           MOVE FUNCTION UPPER-CASE(LS-IN-OPR-IND-CD)
                                           TO LS-IN-OPR-IND-CD
           MOVE FUNCTION UPPER-CASE(LS-IO-RAGN-ACT-IND)
                                           TO LS-IO-RAGN-ACT-IND
           MOVE LS-IN-DEL-CMP-ID           TO WS-IN-DEL-CMP-ID
           MOVE LS-IN-LOG-CMP-ID           TO WS-IN-LOG-CMP-ID
           MOVE LS-IN-NEW-CMP-ID           TO WS-IN-NEW-CMP-ID
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
              DISPLAY 'DPMXDDRO STARTED AT      :' WS-TS
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


           IF WS-IN-DEL-CMP-ID   > SPACES
              PERFORM 2100-VAL-DEL-CMP-ID
           ELSE
              MOVE WS-EMPTY-DEL-CMP-ID      TO LS-SP-ERROR-AREA
              MOVE 'SP50'                   TO LS-SP-RC
              PERFORM 9100-DISPLAY-DATA
              PERFORM 9990-GOBACK
           END-IF

           IF LS-IN-OPR-IND-CD   > SPACES
              MOVE LS-IN-OPR-IND-CD       TO  WS-OPR-TYPE-IND-SW
              IF  WS-OPR-TYPE-DELETE OR WS-DOC-TYPE-REASSIGN
                  CONTINUE
              ELSE
                  MOVE WS-INVLD-OPR-TYPE TO LS-SP-ERROR-AREA
                  MOVE 'SP50'               TO LS-SP-RC
                  PERFORM 9100-DISPLAY-DATA
                  PERFORM 9990-GOBACK
              END-IF
           ELSE
              MOVE WS-EMPTY-OPR-TYPE TO LS-SP-ERROR-AREA
              MOVE 'SP50'                   TO LS-SP-RC
              PERFORM 9100-DISPLAY-DATA
              PERFORM 9990-GOBACK
           END-IF

           IF WS-IN-LOG-CMP-ID > SPACES
              CONTINUE
           ELSE
              MOVE WS-EMPTY-LOG-CMP-ID     TO LS-SP-ERROR-AREA
              MOVE 'SP50'                  TO LS-SP-RC
              PERFORM 9100-DISPLAY-DATA
              PERFORM 9990-GOBACK
           END-IF

           IF WS-DOC-TYPE-REASSIGN
               IF LS-IN-NEW-CMP-ID  = SPACES
                  MOVE WS-EMP-NEW-CMP-ID    TO LS-SP-ERROR-AREA
                  MOVE 'SP50'               TO LS-SP-RC
                  PERFORM 9100-DISPLAY-DATA
                  PERFORM 9990-GOBACK
               ELSE
                  PERFORM 2200-VAL-NEW-CMP-ID
               END-IF

               IF LS-IO-RAGN-ACT-IND > SPACES
                  MOVE LS-IO-RAGN-ACT-IND    TO  WS-RAGN-ACT-IND-SW
                  IF WS-RAGN-ACT-IND-INIT OR  WS-RAGN-ACT-IND-ALLOW
                      CONTINUE
                  ELSE
                      MOVE WS-INVLD-RAGN-ACT-IND
                                             TO LS-SP-ERROR-AREA
                      MOVE 'SP50'            TO LS-SP-RC
                      PERFORM 9100-DISPLAY-DATA
                      PERFORM 9990-GOBACK
                  END-IF
               ELSE
                  MOVE WS-EMPTY-RAGN-ACT-IND    TO LS-SP-ERROR-AREA
                  MOVE 'SP50'                    TO LS-SP-RC
                  PERFORM 9100-DISPLAY-DATA
                  PERFORM 9990-GOBACK
               END-IF
           END-IF

           IF LS-IN-USER-ID > SPACES
              CONTINUE
           ELSE
              MOVE WS-EMPTY-USER-ID        TO LS-SP-ERROR-AREA
              MOVE 'SP50'                  TO LS-SP-RC
              PERFORM 9100-DISPLAY-DATA
              PERFORM 9990-GOBACK
           END-IF

           .
      *---------------------*
       2100-VAL-DEL-CMP-ID.
      *---------------------*

           MOVE '2100-VAL-DEL-CMP-ID'    TO WS-PARAGRAPH-NAME

           IF DISPLAY-PARAMETERS
              DISPLAY WS-PARAGRAPH-NAME
           END-IF


           EXEC SQL

              SELECT  CMPNY_ID INTO :WS-CMPNY-ID
                FROM  D0005
                WHERE CMPNY_ID  = :WS-IN-DEL-CMP-ID
                WITH UR
           END-EXEC

           EVALUATE SQLCODE
              WHEN 0
                 MOVE WS-INVLD-DEL-CMP-ID1 TO LS-SP-ERROR-AREA
                 MOVE 'SP01'               TO LS-SP-RC
                 PERFORM 9990-GOBACK
              WHEN 100
                 EXEC SQL
                    SELECT CMPNY_ID INTO :WS-CMPNY-ID
                      FROM VDPM02_DELR_CMPNY
                      WHERE CMPNY_ID = :WS-IN-DEL-CMP-ID
                      WITH UR
                 END-EXEC
                 EVALUATE SQLCODE
                   WHEN 0
                      CONTINUE
                   WHEN 100
                      MOVE WS-INVLD-DEL-CMP-ID2 TO LS-SP-ERROR-AREA
                      MOVE 'SP01'               TO LS-SP-RC
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
       2200-VAL-NEW-CMP-ID.
      *---------------------*

           MOVE '2200-VAL-NEW-CMP-ID'    TO WS-PARAGRAPH-NAME

           IF DISPLAY-PARAMETERS
              DISPLAY WS-PARAGRAPH-NAME
           END-IF


           EXEC SQL

              SELECT  CMPNY_ID INTO :WS-CMPNY-ID
                FROM  D0005
                WHERE CMPNY_ID  = :LS-IN-NEW-CMP-ID
                WITH UR
           END-EXEC

           EVALUATE SQLCODE
              WHEN 0
                 CONTINUE
              WHEN 100
                 EXEC SQL
                    SELECT CMPNY_ID INTO :WS-CMPNY-ID
                      FROM VDPM02_DELR_CMPNY
                      WHERE CMPNY_ID = :LS-IN-NEW-CMP-ID
                      WITH UR
                 END-EXEC
                 EVALUATE SQLCODE
                   WHEN 0
                      CONTINUE
                   WHEN 100
                      MOVE WS-INVLD-NEW-CMP-ID  TO LS-SP-ERROR-AREA
                      MOVE 'SP02'               TO LS-SP-RC
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

           MOVE '3000-PROCESS-DOCUMENT'    TO WS-PARAGRAPH-NAME

           IF DISPLAY-PARAMETERS
              DISPLAY WS-PARAGRAPH-NAME
           END-IF

           IF WS-RAGN-ACT-IND-ALLOW  OR WS-OPR-TYPE-DELETE
               CONTINUE
           ELSE
               PERFORM 3100-OPEN-DOC-USR-CSR                            07100062
               PERFORM UNTIL END-OF-DOC-USR-CSR OR WS-DUP-REC-EXISTS
                   PERFORM 3200-FETCH-DOC-USR-CSR
                   IF NOT-END-OF-DOC-USR-CSR
                      PERFORM 3300-RECORD-EXISTS
                   END-IF
               END-PERFORM
               PERFORM 3600-CLOSE-DOC-USR-CSR                           07100062
           END-IF                                                       07100062

           IF WS-DUP-REC-NOT-EXISTS  OR                                 07100062
              WS-RAGN-ACT-IND-ALLOW  OR
              WS-OPR-TYPE-DELETE
              SET NOT-END-OF-DOC-USR-CSR   TO TRUE                      07100062
              PERFORM 3100-OPEN-DOC-USR-CSR                             07100062
              PERFORM  UNTIL END-OF-DOC-USR-CSR
                 PERFORM 3200-FETCH-DOC-USR-CSR
                 IF NOT-END-OF-DOC-USR-CSR
                    PERFORM 3400-PROC-DOC-USR-CSR
                 END-IF
              END-PERFORM
              IF WS-REC-NOT-DELETED                                     07100062
                 PERFORM 3500-DELETE-DELR-CMP-RCD
              END-IF                                                    07100062
              PERFORM 3600-CLOSE-DOC-USR-CSR                            07100062
              MOVE 'C'               TO LS-IO-RAGN-ACT-IND
           END-IF                                                       07100062
           .
044500*--------------------------*
044600 3100-OPEN-DOC-USR-CSR.
044700*--------------------------*

044800     MOVE '3100-OPEN-DOC-USR-CSR'
044800                                     TO WS-PARAGRAPH-NAME

           IF DISPLAY-PARAMETERS
              DISPLAY WS-PARAGRAPH-NAME
           END-IF

048800     EXEC SQL
048900        OPEN DOC_USR_CSR
049000     END-EXEC
049100
049200     EVALUATE SQLCODE
049300        WHEN 0
049400           CONTINUE
049500        WHEN OTHER
046800           PERFORM 9000-SQL-ERROR
050400     END-EVALUATE
050500
050600     .
045100
050700*************************************************************
050800*    FETCH DOC-USR-CSR CURSOR                               *
050900*************************************************************
051000 3200-FETCH-DOC-USR-CSR.
045100
044800     MOVE '3200-FETCH-DOC-USR-CSR'
044800                                     TO WS-PARAGRAPH-NAME

           IF DISPLAY-PARAMETERS
              DISPLAY WS-PARAGRAPH-NAME
           END-IF

051100
051200     EXEC SQL
051300        FETCH DOC_USR_CSR INTO
051400           :WS-MCA-VALUE-ID , :WS-CMPNY-ID , :WS-MCA-DOC-VIEW-IN,
051400           :WS-MCA-TMPLT-ID, :WS-MCA-DOC-TYPE-CD,
051400           :WS-MCA-DOC-DS,
051400           :WS-MCA-TMPLT-NM, :WS-MCA-TMPLT-SHORT-NM,
051400           :WS-MCA-EXE-TS
051900     END-EXEC
052000
052100     EVALUATE SQLCODE
052200
052300        WHEN 0
052300           CONTINUE
053800
053900        WHEN +100
054600           SET END-OF-DOC-USR-CSR TO TRUE
054700
054800        WHEN OTHER
046800           PERFORM 9000-SQL-ERROR
055700
055800     END-EVALUATE
055900
056000     .
      *------------------------*
       3300-RECORD-EXISTS.
      *------------------------*

           MOVE '3300-RECORD-EXISTS'      TO WS-PARAGRAPH-NAME

           IF DISPLAY-PARAMETERS
              DISPLAY WS-PARAGRAPH-NAME
           END-IF


           IF WS-MCA-DOC-TYPE-CD= 'P'
              PERFORM 3310-MCA-TMPLT-RECORD-EXISTS
           ELSE
              IF WS-MCA-DOC-TYPE-CD = 'O'
                 MOVE FUNCTION UPPER-CASE(WS-MCA-DOC-DS)
                                           TO WS-MCA-DOC-DS
                 PERFORM 3320-DOCUMENT-RECORD-EXISTS
              END-IF
           END-IF
           .

      *------------------------*
       3310-MCA-TMPLT-RECORD-EXISTS.
      *------------------------*

           MOVE '3310-MCA-TMPLT-RECORD-EXISTS'
                                          TO WS-PARAGRAPH-NAME

           IF DISPLAY-PARAMETERS
              DISPLAY WS-PARAGRAPH-NAME
           END-IF

           PERFORM 3311-OPEN-MCA-TMPLT-CSR                              07100062
           PERFORM 3312-FETCH-MCA-TMPLT-CSR
               UNTIL END-OF-MCA-TMPLT-CSR OR WS-DUP-REC-EXISTS
           PERFORM 3313-CLOSE-MCA-TMPLT-CSR                             07100062
           .
044500*--------------------------*
044600 3311-OPEN-MCA-TMPLT-CSR.
044700*--------------------------*

044800     MOVE '3311-OPEN-MCA-TMPLT-CSR'
044800                                     TO WS-PARAGRAPH-NAME

           IF DISPLAY-PARAMETERS
              DISPLAY WS-PARAGRAPH-NAME
           END-IF

048800     EXEC SQL
048900        OPEN MCA_TMPLT_CSR
049000     END-EXEC
049100
049200     EVALUATE SQLCODE
049300        WHEN 0
049400           CONTINUE
049500        WHEN OTHER
046800           PERFORM 9000-SQL-ERROR
050400     END-EVALUATE
050500
050600     .
045100
050700*************************************************************
050800*    FETCH MCA-TMPLT-CSR CURSOR                             *
050900*************************************************************
051000 3312-FETCH-MCA-TMPLT-CSR.
045100
044800     MOVE '3312-FETCH-MCA-TMPLT-CSR'
044800                                     TO WS-PARAGRAPH-NAME

           IF DISPLAY-PARAMETERS
              DISPLAY WS-PARAGRAPH-NAME
           END-IF

051100
051200     EXEC SQL
051300        FETCH MCA_TMPLT_CSR  INTO
051400           :WS-TMPLT-ID
051900     END-EXEC
052000
052100     EVALUATE SQLCODE
052200
052300        WHEN 0
                 MOVE 'D'               TO LS-IO-RAGN-ACT-IND
                 SET  WS-DUP-REC-EXISTS TO TRUE
053800
053900        WHEN +100
054600           SET END-OF-MCA-TMPLT-CSR TO TRUE
054700
054800        WHEN OTHER
046800           PERFORM 9000-SQL-ERROR
055700
055800     END-EVALUATE
055900
056000     .

044500*--------------------------*
044600 3313-CLOSE-MCA-TMPLT-CSR.
044700*--------------------------*

044800     MOVE '3313-CLOSE-MCA-TMPLT-CSR'
044800                                     TO WS-PARAGRAPH-NAME

           IF DISPLAY-PARAMETERS
              DISPLAY WS-PARAGRAPH-NAME
           END-IF

048800     EXEC SQL
048900        CLOSE MCA_TMPLT_CSR
049000     END-EXEC
049100
049200     EVALUATE SQLCODE
049300        WHEN 0
049400           CONTINUE
049500        WHEN OTHER
046800           PERFORM 9000-SQL-ERROR
050400     END-EVALUATE
050500
050600     .
045100
      *------------------------*
       3320-DOCUMENT-RECORD-EXISTS.
      *------------------------*

           MOVE '3320-DOCUMENT-RECORD-EXISTS'
                                          TO WS-PARAGRAPH-NAME

           IF DISPLAY-PARAMETERS
              DISPLAY WS-PARAGRAPH-NAME
           END-IF


           PERFORM 3321-OPEN-MCA-DOC-CSR                                07100062
           PERFORM 3322-FETCH-MCA-DOC-CSR
               UNTIL END-OF-MCA-DOC-CSR OR WS-DUP-REC-EXISTS
           PERFORM 3323-CLOSE-MCA-DOC-CSR                               07100062
050600     .
045100
044500*--------------------------*
044600 3321-OPEN-MCA-DOC-CSR.
044700*--------------------------*

044800     MOVE '3321-OPEN-MCA-DOC-CSR'
044800                                     TO WS-PARAGRAPH-NAME

           IF DISPLAY-PARAMETERS
              DISPLAY WS-PARAGRAPH-NAME
           END-IF

048800     EXEC SQL
048900        OPEN MCA_DOC_CSR
049000     END-EXEC
049100
049200     EVALUATE SQLCODE
049300        WHEN 0
049400           CONTINUE
049500        WHEN OTHER
046800           PERFORM 9000-SQL-ERROR
050400     END-EVALUATE
050500
050600     .
045100
050700*************************************************************
050800*    FETCH MCA-DOC-CSR CURSOR                               *
050900*************************************************************
051000 3322-FETCH-MCA-DOC-CSR.
045100
044800     MOVE '3322-FETCH-MCA-DOC-CSR'
044800                                     TO WS-PARAGRAPH-NAME

           IF DISPLAY-PARAMETERS
              DISPLAY WS-PARAGRAPH-NAME
           END-IF

051100
051200     EXEC SQL
051300        FETCH MCA_DOC_CSR INTO
051400           :WS-MCA-VALUE-ID,
051400           :WS-CMPNY-ID,
051400           :WS-MCA-DOC-VIEW-IN
051900     END-EXEC
052000
052100     EVALUATE SQLCODE
052200
052300        WHEN 0
                 MOVE 'D'               TO LS-IO-RAGN-ACT-IND
                 SET  WS-DUP-REC-EXISTS TO TRUE
053800
053900        WHEN +100
054600           SET END-OF-MCA-DOC-CSR TO TRUE
054700
054800        WHEN OTHER
046800           PERFORM 9000-SQL-ERROR
055700
055800     END-EVALUATE
055900
056000     .

044500*--------------------------*
044600 3323-CLOSE-MCA-DOC-CSR.
044700*--------------------------*

044800     MOVE '3323-CLOSE-MCA-DOC-CSR'
044800                                     TO WS-PARAGRAPH-NAME

           IF DISPLAY-PARAMETERS
              DISPLAY WS-PARAGRAPH-NAME
           END-IF

048800     EXEC SQL
048900        CLOSE MCA_DOC_CSR
049000     END-EXEC
049100
049200     EVALUATE SQLCODE
049300        WHEN 0
049400           CONTINUE
049500        WHEN OTHER
046800           PERFORM 9000-SQL-ERROR
050400     END-EVALUATE
050500
050600     .
050700*************************************************************
050800*    PROCESS DOC-USR-CSR CURSOR                             *
050900*************************************************************
051000 3400-PROC-DOC-USR-CSR.
045100
044800     MOVE '3400-PROC-DOC-USR-CSR'
044800                                     TO WS-PARAGRAPH-NAME

           IF DISPLAY-PARAMETERS
              DISPLAY WS-PARAGRAPH-NAME
           END-IF

052300     IF WS-OPR-TYPE-DELETE
052300         PERFORM 3410-DELETE-DOC-USR-RCD
052300         PERFORM 3420-SELECT-ADDL-RCD
052300         IF ADDL-REC-FOUND
052300            PERFORM 3410-DELETE-DOC-USR-RCD
052300         END-IF
052300         IF WS-MCA-DOC-TYPE-CD        = 'P'
052300            PERFORM 3430-DELETE-MCA-TMPLT-RCD
052300         END-IF
052300         PERFORM 3440-UPDATE-DOC-RCD
052300     ELSE
052300         IF WS-DOC-TYPE-REASSIGN
052300            PERFORM 3410-DELETE-DOC-USR-RCD
052300            PERFORM 3450-INSERT-DOC-USR-RCD
052300            IF WS-MCA-DOC-TYPE-CD        = 'P'
052300               PERFORM 3460-UPDATE-MCA-TMPLT-RCD
052300            END-IF
052300         END-IF
052300     END-IF
053800
056000     .
044500*--------------------------*
044600 3410-DELETE-DOC-USR-RCD.
044700*--------------------------*

044800     MOVE '3410-DELETE-DOC-USR-RCD'
044800                                     TO WS-PARAGRAPH-NAME

           IF DISPLAY-PARAMETERS
              DISPLAY WS-PARAGRAPH-NAME
           END-IF

048800     EXEC SQL
048900        DELETE FROM D0004
048900        WHERE MCA_VALUE_ID = :WS-MCA-VALUE-ID
048900        AND   CMPNY_ID     = :WS-CMPNY-ID
049000     END-EXEC
049100
049200     EVALUATE SQLCODE
049300        WHEN 0
                 CONTINUE
049500        WHEN OTHER
046800           PERFORM 9000-SQL-ERROR
050400     END-EVALUATE
050500
050600     .
045100
044500*--------------------------*
044600 3420-SELECT-ADDL-RCD.
044700*--------------------------*

044800     MOVE '3420-SELECT-ADDL-RCD'
044800                                     TO WS-PARAGRAPH-NAME

           IF DISPLAY-PARAMETERS
              DISPLAY WS-PARAGRAPH-NAME
           END-IF

048800     EXEC SQL
048900       SELECT CMPNY_ID INTO :WS-CMPNY-ID
048900       FROM D0004
048900       WHERE MCA_VALUE_ID = :WS-MCA-VALUE-ID
049000     END-EXEC

           EVALUATE SQLCODE
              WHEN 0
                 SET ADDL-REC-FOUND TO TRUE
              WHEN 100
                 INITIALIZE SQLCODE
              WHEN OTHER
                 PERFORM 9000-SQL-ERROR
           END-EVALUATE
050600     .
045100
044500*--------------------------*
044600 3430-DELETE-MCA-TMPLT-RCD.
044700*--------------------------*

044800     MOVE '3430-DELETE-MCA-TMPLT-RCD'
044800                                     TO WS-PARAGRAPH-NAME

           IF DISPLAY-PARAMETERS
              DISPLAY WS-PARAGRAPH-NAME
           END-IF

048800     EXEC SQL
048900        DELETE FROM D0006
048900        WHERE MCA_TMPLT_ID = :WS-MCA-TMPLT-ID
049000     END-EXEC
049100
049200     EVALUATE SQLCODE
049300        WHEN 0
                 CONTINUE
049500        WHEN OTHER
046800           PERFORM 9000-SQL-ERROR
050400     END-EVALUATE
050500
050600     .
045100
044500*--------------------------*
044600 3440-UPDATE-DOC-RCD.
044700*--------------------------*

044800     MOVE '3440-UPDATE-DOC-RCD'
044800                                     TO WS-PARAGRAPH-NAME

           IF DISPLAY-PARAMETERS
              DISPLAY WS-PARAGRAPH-NAME
           END-IF

048800     EXEC SQL
048900        UPDATE VDPM12_MCA_DOC
048900        SET DOC_DEL_CD = 'D'
048900            ,ROW_UPDT_USER_ID = :LS-IN-USER-ID
048900            ,ROW_UPDT_TS      = CURRENT TIMESTAMP
048900        WHERE MCA_VALUE_ID    = :WS-MCA-VALUE-ID
049000     END-EXEC
049100
049200     EVALUATE SQLCODE
049300        WHEN 0
049400           CONTINUE
049500        WHEN OTHER
046800           PERFORM 9000-SQL-ERROR
050400     END-EVALUATE
050500
050600     .
045100
044500*--------------------------*
044600 3450-INSERT-DOC-USR-RCD.
044700*--------------------------*

044800     MOVE '3450-INSERT-DOC-USR-RCD'
044800                                     TO WS-PARAGRAPH-NAME

           IF DISPLAY-PARAMETERS
              DISPLAY WS-PARAGRAPH-NAME
           END-IF

048800     EXEC SQL
048900        INSERT INTO D0004
048900          (MCA_VALUE_ID
048900          ,CMPNY_ID
048900          ,MCA_DOC_VIEW_IN
048900          ,ROW_UPDT_TS
048900          ,ROW_UPDT_USER_ID)
048900        VALUES
048900          (:WS-MCA-VALUE-ID
048900          ,:LS-IN-NEW-CMP-ID
048900          ,:WS-MCA-DOC-VIEW-IN
048900          ,CURRENT TIMESTAMP
048900          ,:LS-IN-USER-ID)
049000     END-EXEC
049100
049200     EVALUATE SQLCODE
049300        WHEN 0
049400           CONTINUE
049500        WHEN OTHER
046800           PERFORM 9000-SQL-ERROR
050400     END-EVALUATE
050500
050600     .
045100
044500*--------------------------*
044600 3460-UPDATE-MCA-TMPLT-RCD.
044700*--------------------------*

044800     MOVE '3460-UPDATE-MCA-TMPLT-RCD'
044800                                     TO WS-PARAGRAPH-NAME

           IF DISPLAY-PARAMETERS
              DISPLAY WS-PARAGRAPH-NAME
           END-IF

048800     EXEC SQL
048900        UPDATE D0006
048900        SET CLNT_CMPNY_ID     = :LS-IN-NEW-CMP-ID
048900            ,ROW_UPDT_USER_ID = :LS-IN-USER-ID
048900            ,ROW_UPDT_TS      = CURRENT TIMESTAMP
048900        WHERE MCA_TMPLT_ID    = :WS-MCA-TMPLT-ID
049000     END-EXEC
049100
049200     EVALUATE SQLCODE
049300        WHEN 0
                 CONTINUE
049500        WHEN OTHER
046800           PERFORM 9000-SQL-ERROR
050400     END-EVALUATE
050500
050600     .
045100
044500*--------------------------*
044600 3500-DELETE-DELR-CMP-RCD.
044700*--------------------------*

044800     MOVE '3500-DELETE-DELR-CMP-RCD'
044800                                     TO WS-PARAGRAPH-NAME

           IF DISPLAY-PARAMETERS
              DISPLAY WS-PARAGRAPH-NAME
           END-IF

048800     EXEC SQL
048900        DELETE FROM VDPM02_DELR_CMPNY
048900        WHERE CMPNY_ID     = :WS-IN-DEL-CMP-ID
049000     END-EXEC
049100
049200     EVALUATE SQLCODE
049300        WHEN 0
049400           CONTINUE
049500        WHEN OTHER
046800           PERFORM 9000-SQL-ERROR
050400     END-EVALUATE
050500
050600     .
045100
044500*--------------------------*
044600 3600-CLOSE-DOC-USR-CSR.
044700*--------------------------*

044800     MOVE '3600-CLOSE-DOC-USR-CSR'
044800                                     TO WS-PARAGRAPH-NAME

           IF DISPLAY-PARAMETERS
              DISPLAY WS-PARAGRAPH-NAME
           END-IF

048800     EXEC SQL
048900        CLOSE DOC_USR_CSR
049000     END-EXEC
049100
049200     EVALUATE SQLCODE
049300        WHEN 0
049400           CONTINUE
049500        WHEN OTHER
046800           PERFORM 9000-SQL-ERROR
050400     END-EVALUATE
050500
050600     .
045100
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
102900         DISPLAY 'LS-IN-DEL-CMP-ID         :' LS-IN-DEL-CMP-ID
102900         DISPLAY 'LS-IN-NEW-CMP-ID         :' LS-IN-NEW-CMP-ID
102900         DISPLAY 'LS-IN-LOG-CMP-ID         :' LS-IN-LOG-CMP-ID
102900         DISPLAY 'LS-IN-OPR-IND-CD         :' LS-IN-OPR-IND-CD
102900         DISPLAY 'LS-IN-USER-ID            :' LS-IN-USER-ID
102900         DISPLAY 'LS-IO-RAGN-ACT-IND       :' LS-IO-RAGN-ACT-IND
102600         DISPLAY WS-DASHES
106900     END-IF
107000     .
      *------------------------*
       9990-GOBACK.
      *------------------------*

            PERFORM 9999-FORMAT-SQLCA
            IF DISPLAY-PARAMETERS
               DISPLAY WS-DASHES
               DISPLAY 'OUTSQLCA FOR DPMXDDRO    :' OUTSQLCA
               DISPLAY WS-DASHES
               EXEC SQL
                   SET :WS-TS = CURRENT TIMESTAMP
               END-EXEC
               DISPLAY 'DPMXDDRO ENDED AT        :' WS-TS
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