       IDENTIFICATION DIVISION.
       PROGRAM-ID.    DPMXDDDC.
       AUTHOR.        COGNIZANT.

      ******************************************************************
      *THIS IS AN UNPUBLISHED PROGRAM OWNED BY ISCC                    *
      *IN WHICH A COPYRIGHT SUBSISTS AS OF JULY 2006.                  *
      *                                                                *
      ******************************************************************
      **LNKCTL**
      *    INCLUDE SYSLIB(DSNRLI)
      *    MODE AMODE(31) RMODE(ANY)
      *    ENTRY DPMXDDDC
      *    NAME  DPMXDDDC(R)
      *
      ******************************************************************
      **         P R O G R A M   D O C U M E N T A T I O N            **
      ******************************************************************
      * SYSTEM:    DERIV/SERV MCA XPRESS                               *
      * PROGRAM:   DPMXDDDC                                            *
      *                                                                *
      *                                                                *
      * THIS STORED PROCEDURE IS TO DELETE THE DOCUMENT IN THE         *
      * DATABASE.THIS STORED PROCEDURE IS UTILIZED FOR DELETING        *
      * PRE-EXISTING AND OTHER DOCUMENTS.                              *
      ******************************************************************
      * TABLES:                                                        *
      * -------                                                        *
      * NSCC.TDPM02_DELR_CMPNY - TABLE THAT CONTAINS INFORMATION ABOUT *
      * NON MCA-REGISTERED COMPANY.                                    *
      *                                                                *
      * NSCC.TDPM09_DOC_USER - TABLE THAT CONTAINS INFORMATION ABOUT   *
      * THE COMPANY THAT IS LINKED WITH A DOCUMENT                     *
      *                                                                *
      * NSCC.TDPM12_MCA_DOC - TABLE THAT HAS THE document uploaded     *
      * BY A COMPANY.                                                  *
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
      * DB2000IB                                                       *
      * DB2000IC                                                       *
      *----------------------------------------------------------------
      *              M A I N T E N A N C E   H I S T O R Y            *
      *                                                               *
      * DATE CHANGED    VERSION     PROGRAMMER                        *
      * ------------    -------     --------------------              *
      *                                                               *
      * 10/12/2007        01.00     COGNIZANT                         *
      *                             INITIAL IMPLEMENTATION            *
      *                                                               *
      *****************************************************************
       ENVIRONMENT DIVISION.
       DATA DIVISION.
       WORKING-STORAGE SECTION.
       01  WS-PROGRAM                      PIC X(08) VALUE 'DPMXDDDC'.
       01  WS-VARIABLES.
             05  WS-DASHES                 PIC X(40) VALUE ALL '='.
             05  WS-CMPNY-ID               PIC X(08).
             05  WS-CNT                    PIC S9(4) COMP VALUE 0.
             05  WS-EMPTY-CMP-DEL-ID       PIC X(50) VALUE
                 'Input deletion company id is empty'.
             05  WS-INVLD-CMP-DEL-ID     PIC X(50) VALUE
                 'Counterparty does not exist'.
             05  WS-EMPTY-DOC-ID           PIC X(50) VALUE
                 'Input doc id is empty'.
             05  WS-DELETED-SEL-RCD        PIC X(50) VALUE
                 'Selected Document is already deleted'.
             05  WS-EMPTY-USER-ID          PIC X(50) VALUE
                 'Input user id is empty'.
             05  WS-DOC-USR-REC-CNT-SW     PIC X(01) VALUE 'N'.
                 88  WS-DOC-USR-CNT-ONE    VALUE 'Y'.
             05  WS-DEL-DELR-CMPNY-SW      PIC X(01) VALUE 'N'.
                 88  WS-DEL-DELR-CMPNY     VALUE 'Y'.
      *
       01  WS-ERROR-AREA.
             05  WS-PARAGRAPH-NAME         PIC X(40).
             05  FILLER                    PIC X VALUE ','.
             05  WS-TABLE-NAME             PIC X(128).
             05  WS-SQLCODE                PIC 9(7).


       01  WS-TS                          PIC X(26).
       01  WS-DOC-ID                      PIC S9(18)V COMP-3.
       01  WS-DOC-VIEW-IN                 PIC X(01).
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
       01  LS-IN-MCA-TMPLT-ID              PIC S9(9) COMP.
       01  LS-IN-DOC-ID                    PIC S9(18)V COMP-3.
       01  LS-IN-UPDT-USER-ID              PIC X(10).

       PROCEDURE DIVISION USING  OUTSQLCA,
                                 LS-SP-ERROR-AREA,
                                 LS-SP-RC,
                                 LS-IN-DEL-CMP-ID,
                                 LS-IN-MCA-TMPLT-ID,
                                 LS-IN-DOC-ID,
                                 LS-IN-UPDT-USER-ID.
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
              DISPLAY 'DPMXDDDC STARTED AT      :' WS-TS
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

           IF LS-IN-DEL-CMP-ID > SPACES
              PERFORM 2110-SELECT-CMPNY-TYP
           ELSE
              MOVE WS-EMPTY-CMP-DEL-ID     TO LS-SP-ERROR-AREA
              MOVE 'SP50'                  TO LS-SP-RC
              PERFORM 9100-DISPLAY-DATA
              PERFORM 9990-GOBACK
           END-IF

           IF LS-IN-DOC-ID > ZEROES
              CONTINUE
           ELSE
              MOVE WS-EMPTY-DOC-ID         TO LS-SP-ERROR-AREA
              MOVE 'SP50'                  TO LS-SP-RC
              PERFORM 9100-DISPLAY-DATA
              PERFORM 9990-GOBACK
           END-IF

           IF LS-IN-UPDT-USER-ID > SPACES
              CONTINUE
           ELSE
              MOVE WS-EMPTY-USER-ID        TO LS-SP-ERROR-AREA
              MOVE 'SP50'                  TO LS-SP-RC
              PERFORM 9100-DISPLAY-DATA
              PERFORM 9990-GOBACK
           END-IF

           .
      *--------------------------*
       2110-SELECT-CMPNY-TYP.
      *------------------------*

           MOVE '2110-SELECT-CMPNY-TYP'
                                           TO WS-PARAGRAPH-NAME

           IF DISPLAY-PARAMETERS
              DISPLAY WS-PARAGRAPH-NAME
           END-IF

           EXEC SQL
              SELECT CMPNY_ID INTO :WS-CMPNY-ID
              FROM VDPM02_DELR_CMPNY
              WHERE CMPNY_ID     = :LS-IN-DEL-CMP-ID
049000     END-EXEC
049100
049200     EVALUATE SQLCODE
049300        WHEN 0
049400           SET WS-DEL-DELR-CMPNY     TO TRUE
049300        WHEN 100
                 EXEC SQL
                      SELECT CMPNY_ID INTO :WS-CMPNY-ID
                      FROM D0005
                      WHERE CMPNY_ID         = :LS-IN-DEL-CMP-ID
                      WITH UR
                 END-EXEC
                 EVALUATE SQLCODE
                   WHEN 0
                      CONTINUE
                   WHEN 100
                      MOVE WS-INVLD-CMP-DEL-ID  TO LS-SP-ERROR-AREA
                      MOVE 'SP01'               TO LS-SP-RC
                      PERFORM 9100-DISPLAY-DATA
                      PERFORM 9990-GOBACK
                   WHEN OTHER
                      PERFORM 9000-SQL-ERROR
                 END-EVALUATE
049500        WHEN OTHER
046800           PERFORM 9000-SQL-ERROR
050400     END-EVALUATE
050500
050600     .
045100
      *---------------------*
       3000-PROCESS-DOCUMENT.
      *---------------------*

           MOVE '3000-PROCESS-DOCUMENT'
                                           TO WS-PARAGRAPH-NAME

           IF DISPLAY-PARAMETERS
              DISPLAY WS-PARAGRAPH-NAME
           END-IF


           PERFORM 3100-DELETE-DOC-USR                                  07090062

           IF WS-DOC-USR-CNT-ONE OR WS-DEL-DELR-CMPNY                   07090062
              PERFORM 3200-UPDATE-DOC-RCD                               07090062
              IF LS-IN-MCA-TMPLT-ID > ZEROES                            07090062
                  PERFORM 3300-DELETE-TEMPLT-REC                        07090062
              END-IF                                                    07090062
           END-IF                                                       07090062
           .
      *--------------------------*
       3100-DELETE-DOC-USR.
      *------------------------*

           MOVE '3100-DELETE-DOC-USR'
                                           TO WS-PARAGRAPH-NAME

           IF DISPLAY-PARAMETERS
              DISPLAY WS-PARAGRAPH-NAME
           END-IF

           PERFORM 3110-VAL-DOC-USR-RCD

           PERFORM 3120-COUNT-DOC-USR

           IF WS-DEL-DELR-CMPNY  OR  WS-DOC-USR-CNT-ONE
               PERFORM 3130-DEL-ALL-DOC-USR
           ELSE
               PERFORM 3140-UPD-DOC-USR-RCD
           END-IF
050600     .
045100
      *---------------------*
       3110-VAL-DOC-USR-RCD.
      *---------------------*

           MOVE '3110-VAL-DOC-USR-RCD'      TO WS-PARAGRAPH-NAME

           IF DISPLAY-PARAMETERS
              DISPLAY WS-PARAGRAPH-NAME
           END-IF


           EXEC SQL

              SELECT  MCA_VALUE_ID , MCA_DOC_VIEW_IN
                   INTO :WS-DOC-ID , :WS-DOC-VIEW-IN
                FROM  D0004
                WHERE MCA_VALUE_ID     = :LS-IN-DOC-ID
                AND   CMPNY_ID         = :LS-IN-DEL-CMP-ID
                WITH UR
           END-EXEC

           EVALUATE SQLCODE
              WHEN 0
                 IF WS-DOC-VIEW-IN = 'D'
                    MOVE WS-DELETED-SEL-RCD
                                         TO LS-SP-ERROR-AREA
                    MOVE 'SP02'          TO LS-SP-RC
                    PERFORM 9990-GOBACK
                 END-IF
              WHEN 100
                 MOVE WS-DELETED-SEL-RCD TO LS-SP-ERROR-AREA
                 MOVE 'SP02'              TO LS-SP-RC
                 PERFORM 9100-DISPLAY-DATA
                 PERFORM 9990-GOBACK
              WHEN OTHER
                 PERFORM 9000-SQL-ERROR
           END-EVALUATE
           .

      *---------------------*
       3120-COUNT-DOC-USR.
      *---------------------*

           MOVE '3120-COUNT-DOC-USR'    TO WS-PARAGRAPH-NAME

           IF DISPLAY-PARAMETERS
              DISPLAY WS-PARAGRAPH-NAME
           END-IF


           EXEC SQL

              SELECT  COUNT(MCA_VALUE_ID) INTO :WS-CNT
                FROM  D0004
                WHERE MCA_VALUE_ID     = :LS-IN-DOC-ID
                AND   MCA_DOC_VIEW_IN  IN ('N', 'D')
                WITH UR
           END-EXEC

           EVALUATE SQLCODE
              WHEN 0
                 IF WS-CNT = 1
                    SET WS-DOC-USR-CNT-ONE  TO TRUE
                 END-IF
              WHEN OTHER
                 PERFORM 9000-SQL-ERROR
           END-EVALUATE
           .

      *--------------------------*
       3130-DEL-ALL-DOC-USR.
      *------------------------*

           MOVE '3130-DEL-ALL-DOC-USR'     TO WS-PARAGRAPH-NAME

           IF DISPLAY-PARAMETERS
              DISPLAY WS-PARAGRAPH-NAME
           END-IF

           EXEC SQL
              DELETE FROM D0004
              WHERE MCA_VALUE_ID = :LS-IN-DOC-ID
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
      *--------------------------*
       3140-UPD-DOC-USR-RCD.
      *------------------------*

           MOVE '3140-UPD-DOC-USR-RCD'     TO WS-PARAGRAPH-NAME

           IF DISPLAY-PARAMETERS
              DISPLAY WS-PARAGRAPH-NAME
           END-IF


           EXEC SQL
048900        UPDATE D0004
048900        SET MCA_DOC_VIEW_IN   = 'D'
048900            ,ROW_UPDT_USER_ID = :LS-IN-UPDT-USER-ID
048900            ,ROW_UPDT_TS      = CURRENT TIMESTAMP
              WHERE MCA_VALUE_ID    = :LS-IN-DOC-ID
              AND   CMPNY_ID        = :LS-IN-DEL-CMP-ID
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
044600 3200-UPDATE-DOC-RCD.
044700*--------------------------*

044800     MOVE '3200-UPDATE-DOC-RCD'
044800                                     TO WS-PARAGRAPH-NAME

           IF DISPLAY-PARAMETERS
              DISPLAY WS-PARAGRAPH-NAME
           END-IF

048800     EXEC SQL
048900        UPDATE VDPM12_MCA_DOC
048900        SET DOC_DEL_CD = 'D'
048900            ,ROW_UPDT_USER_ID = :LS-IN-UPDT-USER-ID
048900            ,ROW_UPDT_TS      = CURRENT TIMESTAMP
048900        WHERE MCA_VALUE_ID    = :LS-IN-DOC-ID
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
       3300-DELETE-TEMPLT-REC.
      *---------------------*

           MOVE '3300-DELETE-TEMPLT-REC'
                                           TO WS-PARAGRAPH-NAME

           IF DISPLAY-PARAMETERS
              DISPLAY WS-PARAGRAPH-NAME
           END-IF

           EXEC SQL
              DELETE FROM D0006
              WHERE MCA_TMPLT_ID = :LS-IN-MCA-TMPLT-ID
           END-EXEC

           EVALUATE SQLCODE
              WHEN 0
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
102900         DISPLAY 'LS-IN-DEL-CMP-ID     :' LS-IN-DEL-CMP-ID
102900         DISPLAY 'LS-IN-MCA-TMPLT-ID       :' LS-IN-MCA-TMPLT-ID
102900         DISPLAY 'LS-IN-DOC-ID             :' LS-IN-DOC-ID
102900         DISPLAY 'LS-IN-UPDT-USER-ID       :' LS-IN-UPDT-USER-ID
           END-IF
107000     .
      *------------------------*
       9990-GOBACK.
      *------------------------*

            PERFORM 9999-FORMAT-SQLCA
            IF DISPLAY-PARAMETERS
               DISPLAY WS-DASHES
               DISPLAY 'OUTSQLCA FOR DPMXDDDC    :' OUTSQLCA
               DISPLAY WS-DASHES
               EXEC SQL
                   SET :WS-TS = CURRENT TIMESTAMP
               END-EXEC
               DISPLAY 'DPMXDDDC ENDED AT        :' WS-TS
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