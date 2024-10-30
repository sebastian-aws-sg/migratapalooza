       IDENTIFICATION DIVISION.
       PROGRAM-ID.    DPMXDODO.
       AUTHOR.        COGNIZANT.

      ******************************************************************
      *THIS IS AN UNPUBLISHED PROGRAM OWNED BY ISCC                    *
      *IN WHICH A COPYRIGHT SUBSISTS AS OF JULY 2006.                  *
      *                                                                *
      ******************************************************************
      **LNKCTL**
      *    INCLUDE SYSLIB(DSNRLI)
      *    MODE AMODE(31) RMODE(ANY)
      *    ENTRY DPMXDODO
      *    NAME  DPMXDODO(R)
      *
      ******************************************************************
      **         P R O G R A M   D O C U M E N T A T I O N            **
      ******************************************************************
      * SYSTEM:    DERIV/SERV MCA XPRESS                               *
      * PROGRAM:   DPMXDODO                                            *
      *                                                                *
      *                                                                *
      * THIS STORED PROCEDURE ENABLES A COMPANY TO ADD /               *
      * RE-NAME A COMPANY WHO HAVE NOT REGISTERED WITH MCA-XPRESS.     *
      ******************************************************************
      * TABLES:                                                        *
      * -------                                                        *
      * NSCC.TDPM01_MCA_CMPNY- MASTER TABLE THAT HAS THE CLIENT/DEALER *
      * COMPANY DETAILS.                                               *
      *                                                                *
      * NSCC.TDPM02_DELR_CMPNY - TABLE THAT HAS THE CLIENT/DEALER    *
      * DETAILS ADDED BY THE DEALER / CLIENT.                          *
      *                                                                *
      * NSCC.TDPM03_CMPNY_USER - TABLE THAT HAS THE USER INFORMATION   *
      * PERTAINING TO A DEALER/CLIENT FIRM.                            *
      *                                                                *
      * VDTM54_DEBUG_CNTRL    - DEBUG CONTROL TABLE                    *
      *                                                                *
      *----------------------------------------------------------------*
      * INCLUDES:
      * ---------
      * SQLCA    - DB2 COMMAREA
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
      * 09/11/2007        01.00     COGNIZANT                         *
      *                             INITIAL IMPLEMENTATION            *
      *                                                               *
      *****************************************************************
       ENVIRONMENT DIVISION.
       DATA DIVISION.
       WORKING-STORAGE SECTION.
       01  WS-PROGRAM                      PIC X(08) VALUE 'DPMXDODO'.
       01  WS-VARIABLES.
             05  WS-DASHES                 PIC X(40) VALUE ALL '='.
             05  WS-CMPNY-SEQ              PIC S9(9) COMP.
             05  WS-NUMERIC-CMPNY-SEQ      PIC 9(9).
             05  FILLER REDEFINES WS-NUMERIC-CMPNY-SEQ.
                 10 WS-NUMERIC-CMPNY-SEQ-C PIC X(9).
             05  WS-CHAR-CMPNY-SEQ.
                  10 WS-CMPNY-SEQ-HASH     PIC X(01) VALUE '$'.
                  10 WS-CMPNY-SEQ-NUM      PIC X(07).
             05  WS-EMPTY-SEL-CMP-ID       PIC X(50) VALUE
                 'Selected company is empty'.
             05  WS-EMPTY-CMP-NM           PIC X(50) VALUE
                 'Counterparty Name cannot be empty'.
             05  WS-INVLD-CMP-NM           PIC X(50) VALUE
                 'Counterparty Name already exists'.
             05  WS-INVLD-EDT-CMP-ID1      PIC X(50) VALUE
                 'Registered counterparty cannot be renamed'.
             05  WS-INVLD-DEL-CMP-ID2      PIC X(50) VALUE
                 'Counterparty is already deleted'.
             05  WS-EMPTY-OPR-FLG          PIC X(50) VALUE
                 'Input operation flag is empty'.
             05  WS-INVLD-OPR-FLG          PIC X(50) VALUE
                 'Input operation flag is invalid'.
             05  WS-EMPTY-LOG-CMP-ID       PIC X(50) VALUE
                 'Login company id is empty'.
             05  WS-EMPTY-LOG-USR-ID       PIC X(50) VALUE
                 'Login user id is empty'.
             05  WS-CMPNY-ID               PIC X(08).
             05  WS-PROCESS-IND-SW         PIC X(01).
                 88  WS-PROCESS-ADD        VALUE 'A'.
                 88  WS-PROCESS-RENAME     VALUE 'R'.
             05  WS-IN-NEW-CMP-NM          PIC X(255).
      *
       01  WS-ERROR-AREA.
             05  WS-PARAGRAPH-NAME         PIC X(40).
             05  FILLER                    PIC X VALUE ','.
             05  WS-TABLE-NAME             PIC X(128).
             05  WS-SQLCODE                PIC 9(7).

       01  WS-TS                           PIC X(26).
       01  WS-DISPLAY-SWITCH               PIC X(01) VALUE 'N'.
           88 DISPLAY-PARAMETERS                     VALUE 'Y'.
           88 HIDE-PARAMETERS                        VALUE 'N'.



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
       01  LS-IN-SEL-CMP-ID                PIC X(08).
       01  LS-IN-NEW-CMP-NM                PIC X(255).
       01  LS-IN-OPR-FLAG                  PIC X(01).
       01  LS-IN-LOG-CMP-ID                PIC X(08).
       01  LS-IN-LOG-USR-ID                PIC X(10).
       01  LS-OUT-CMP-ID                   PIC X(08).

       PROCEDURE DIVISION USING  OUTSQLCA,
                                 LS-SP-ERROR-AREA,
                                 LS-SP-RC,
                                 LS-IN-SEL-CMP-ID,
                                 LS-IN-NEW-CMP-NM,
                                 LS-IN-OPR-FLAG,
                                 LS-IN-LOG-CMP-ID,
                                 LS-IN-LOG-USR-ID,
                                 LS-OUT-CMP-ID.
      *---------*
       0000-MAIN.
      *---------*

           PERFORM 1000-INITIALIZE

           PERFORM 2000-VALIDATE-INPUT

           PERFORM 3000-PROCESS-DELR-CMPNY

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
           MOVE +0                         TO WS-CMPNY-SEQ
      *    CONVERT THE INPUT VALUES INTO UPPER-CASE
           MOVE FUNCTION UPPER-CASE(LS-IN-NEW-CMP-NM)
                                           TO WS-IN-NEW-CMP-NM
           MOVE FUNCTION UPPER-CASE(LS-IN-OPR-FLAG)
                                           TO  LS-IN-OPR-FLAG
           MOVE LS-IN-OPR-FLAG             TO  WS-PROCESS-IND-SW
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
              DISPLAY 'DPMXDODO STARTED AT      :' WS-TS
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


           IF WS-PROCESS-RENAME
              IF LS-IN-SEL-CMP-ID = SPACES
                  MOVE WS-EMPTY-SEL-CMP-ID TO LS-SP-ERROR-AREA
                  MOVE 'SP50'              TO LS-SP-RC
                  PERFORM 9100-DISPLAY-DATA
                  PERFORM 9990-GOBACK
              ELSE
                  PERFORM 2100-VAL-SEL-CMP-ID
              END-IF
           END-IF


           IF LS-IN-NEW-CMP-NM   > SPACES
              PERFORM 2200-VALIDATE-NEW-CMP-NM
           ELSE
              MOVE WS-EMPTY-CMP-NM     TO LS-SP-ERROR-AREA
              MOVE 'SP01'                  TO LS-SP-RC
              PERFORM 9100-DISPLAY-DATA
              PERFORM 9990-GOBACK
           END-IF

           IF LS-IN-OPR-FLAG  > SPACES
              IF  WS-PROCESS-ADD  OR  WS-PROCESS-RENAME
                  CONTINUE
              ELSE
                  MOVE WS-INVLD-OPR-FLG    TO LS-SP-ERROR-AREA
                  MOVE 'SP50'              TO LS-SP-RC
                  PERFORM 9100-DISPLAY-DATA
                  PERFORM 9990-GOBACK
              END-IF
           ELSE
              MOVE WS-EMPTY-OPR-FLG       TO LS-SP-ERROR-AREA
              MOVE 'SP50'                  TO LS-SP-RC
              PERFORM 9100-DISPLAY-DATA
              PERFORM 9990-GOBACK
           END-IF

           IF LS-IN-LOG-CMP-ID > SPACES
              CONTINUE
           ELSE
              MOVE WS-EMPTY-LOG-CMP-ID     TO LS-SP-ERROR-AREA
              MOVE 'SP50'                  TO LS-SP-RC
              PERFORM 9100-DISPLAY-DATA
              PERFORM 9990-GOBACK
           END-IF

           IF LS-IN-LOG-USR-ID  > SPACES
              CONTINUE
           ELSE
              MOVE WS-EMPTY-LOG-USR-ID     TO LS-SP-ERROR-AREA
              MOVE 'SP50'                  TO LS-SP-RC
              PERFORM 9100-DISPLAY-DATA
              PERFORM 9990-GOBACK
           END-IF
           .
      *---------------------*
       2100-VAL-SEL-CMP-ID.
      *---------------------*

           MOVE '2100-VAL-SEL-CMP-ID'    TO WS-PARAGRAPH-NAME

           IF DISPLAY-PARAMETERS
              DISPLAY WS-PARAGRAPH-NAME
           END-IF


           EXEC SQL

              SELECT  CMPNY_ID INTO :WS-CMPNY-ID
                FROM  D0005
                WHERE CMPNY_ID  = :LS-IN-SEL-CMP-ID
                WITH UR
           END-EXEC

           EVALUATE SQLCODE
              WHEN 0
                 MOVE WS-INVLD-EDT-CMP-ID1 TO LS-SP-ERROR-AREA
                 MOVE 'SP01'               TO LS-SP-RC
                 PERFORM 9100-DISPLAY-DATA
                 PERFORM 9990-GOBACK
              WHEN 100
                 EXEC SQL
                    SELECT CMPNY_ID INTO :WS-CMPNY-ID
                      FROM VDPM02_DELR_CMPNY
                      WHERE CMPNY_ID = :LS-IN-SEL-CMP-ID
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

      *----------------------------*
       2200-VALIDATE-NEW-CMP-NM.
      *----------------------------*

           MOVE '2200-VALIDATE-NEW-CMP-NM'
                                           TO WS-PARAGRAPH-NAME

           IF DISPLAY-PARAMETERS
              DISPLAY WS-PARAGRAPH-NAME
           END-IF


           EXEC SQL

              SELECT  CMPNY_ID INTO :WS-CMPNY-ID
                FROM  D0005
                WHERE UCASE(CMPNY_NM) = :WS-IN-NEW-CMP-NM
                WITH UR
           END-EXEC

           EVALUATE SQLCODE
              WHEN 0
                 MOVE WS-INVLD-CMP-NM    TO LS-SP-ERROR-AREA
                 MOVE 'SP02'             TO LS-SP-RC
                 PERFORM 9100-DISPLAY-DATA
                 PERFORM 9990-GOBACK
              WHEN 100
                 EXEC SQL
                      SELECT CMPNY_ID INTO :WS-CMPNY-ID
                      FROM VDPM02_DELR_CMPNY
                      WHERE UCASE(CMPNY_NM)  = :WS-IN-NEW-CMP-NM
                        AND CRT_CMPNY_ID     = :LS-IN-LOG-CMP-ID
                      WITH UR
                 END-EXEC
                 EVALUATE SQLCODE
                   WHEN 0
                      MOVE WS-INVLD-CMP-NM    TO LS-SP-ERROR-AREA
                      MOVE 'SP02'             TO LS-SP-RC
                      PERFORM 9100-DISPLAY-DATA
                      PERFORM 9990-GOBACK
                   WHEN 100
                      CONTINUE
                   WHEN OTHER
                      PERFORM 9000-SQL-ERROR
                 END-EVALUATE
              WHEN OTHER
                 PERFORM 9000-SQL-ERROR
           END-EVALUATE
           .
      *---------------------*
       3000-PROCESS-DELR-CMPNY.
      *---------------------*

           MOVE '3000-PROCESS-DELR-CMPNY'  TO WS-PARAGRAPH-NAME

           IF DISPLAY-PARAMETERS
              DISPLAY WS-PARAGRAPH-NAME
           END-IF


045000     MOVE LS-IN-SEL-CMP-ID           TO D002-CMPNY-ID
045000     MOVE LS-IN-LOG-CMP-ID           TO D002-CRT-CMPNY-ID
045000     MOVE LS-IN-NEW-CMP-NM           TO D002-CMPNY-NM
045000     MOVE LS-IN-LOG-USR-ID           TO D002-ROW-UPDT-USER-ID

           IF WS-PROCESS-ADD                                            07090062
               PERFORM 3100-ISRT-DELR-CMPNY-TABLE                       07100062
           ELSE
               IF WS-PROCESS-RENAME                                     07090062
                   PERFORM 3200-UPDT-DELR-CMPNY-TABLE                   07100062
               END-IF
           END-IF
           .
044500*--------------------------*
044600 3100-ISRT-DELR-CMPNY-TABLE.
044700*--------------------------*

044800     MOVE '3100-ISRT-DELR-CMPNY-TABLE'
044800                                     TO WS-PARAGRAPH-NAME

           IF DISPLAY-PARAMETERS
              DISPLAY WS-PARAGRAPH-NAME
           END-IF

044800     PERFORM 3110-CMPNY-SEQ-GEN
045100
045200     EXEC SQL
045300        INSERT INTO VDPM02_DELR_CMPNY
                 (CMPNY_ID
                 ,CRT_CMPNY_ID
                 ,CMPNY_NM
                 ,ROW_UPDT_TS
                 ,ROW_UPDT_USER_ID)
                 VALUES
                 (:D002-CMPNY-ID
                 ,:D002-CRT-CMPNY-ID
                 ,:D002-CMPNY-NM
                 ,CURRENT TIMESTAMP
                 ,:D002-ROW-UPDT-USER-ID)
045700     END-EXEC
045700
045800     EVALUATE SQLCODE
046000        WHEN ZEROES
                 MOVE D002-CMPNY-ID  TO   LS-OUT-CMP-ID
046700        WHEN OTHER
046800           PERFORM 9000-SQL-ERROR
047000     END-EVALUATE
045700
047100     .
044500*--------------------------*
044600 3110-CMPNY-SEQ-GEN.
470000*--------------------------*

044800     MOVE '3110-CMPNY-SEQ-GEN'       TO WS-PARAGRAPH-NAME


           IF DISPLAY-PARAMETERS
              DISPLAY WS-PARAGRAPH-NAME
           END-IF

           EXEC SQL
               SET :WS-CMPNY-SEQ = (NEXT VALUE FOR DPM.SQDPM021)
           END-EXEC
045700
045800     EVALUATE SQLCODE
046000        WHEN ZEROES
                 MOVE WS-CMPNY-SEQ         TO WS-NUMERIC-CMPNY-SEQ
                 MOVE WS-NUMERIC-CMPNY-SEQ-C(3:7)
                                            TO WS-CMPNY-SEQ-NUM
                 MOVE WS-CHAR-CMPNY-SEQ     TO D002-CMPNY-ID
046700        WHEN OTHER
046800           PERFORM 9000-SQL-ERROR
047000     END-EVALUATE
045700
047100     .
044500*--------------------------*
044600 3200-UPDT-DELR-CMPNY-TABLE.
044700*--------------------------*

044800     MOVE '3200-UPDT-DELR-CMPNY-TABLE'
044800                                     TO WS-PARAGRAPH-NAME

           IF DISPLAY-PARAMETERS
              DISPLAY WS-PARAGRAPH-NAME
           END-IF


045200     EXEC SQL
045300        UPDATE VDPM02_DELR_CMPNY
045400          SET CMPNY_NM            = :D002-CMPNY-NM
045400             ,ROW_UPDT_TS         = CURRENT TIMESTAMP
045400             ,ROW_UPDT_USER_ID    = :D002-ROW-UPDT-USER-ID
045500          WHERE CMPNY_ID          = :D002-CMPNY-ID
045600     END-EXEC
045700
045800     EVALUATE SQLCODE
046000        WHEN ZEROES
                 MOVE D002-CMPNY-ID  TO   LS-OUT-CMP-ID
046700        WHEN OTHER
046800           PERFORM 9000-SQL-ERROR
047000     END-EVALUATE
045700
047100     .
      *---------------------*
101400 9000-SQL-ERROR.
101500*------------------------*
101600
101800     PERFORM 9100-DISPLAY-DATA
087800     MOVE 'Database error has occurred. Please contact DTCC.'
087800                                      TO LS-SP-ERROR-AREA
           MOVE 'SP99'                      TO LS-SP-RC
           MOVE SQLCODE                     TO WS-SQLCODE
           DISPLAY 'PARAGRAPH-NAME          :' WS-PARAGRAPH-NAME
102700     DISPLAY 'SQLCODE                 :' WS-SQLCODE
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
102900         DISPLAY 'LS-IN-SEL-CMP-ID         :' LS-IN-SEL-CMP-ID
102900         DISPLAY 'LS-IN-NEW-CMP-NM         :' LS-IN-NEW-CMP-NM
102900         DISPLAY 'LS-IN-OPR-FLAG           :' LS-IN-OPR-FLAG
102900         DISPLAY 'LS-IN-LOG-CMP-ID         :' LS-IN-LOG-CMP-ID
102900         DISPLAY 'LS-IN-LOG-USR-ID         :' LS-IN-LOG-USR-ID
102900         DISPLAY 'LS-OUT-CMP-ID            :' LS-OUT-CMP-ID
102600         DISPLAY WS-DASHES
106900     END-IF
107000     .
      *------------------------*
       9990-GOBACK.
      *------------------------*

            PERFORM 9999-FORMAT-SQLCA
            IF DISPLAY-PARAMETERS
               DISPLAY WS-DASHES
               DISPLAY 'OUTSQLCA FOR DPMXDODO    :' OUTSQLCA
               DISPLAY WS-DASHES
               EXEC SQL
                   SET :WS-TS = CURRENT TIMESTAMP
               END-EXEC
               DISPLAY 'DPMXDODO ENDED AT        :' WS-TS
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