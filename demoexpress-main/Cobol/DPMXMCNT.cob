       IDENTIFICATION DIVISION.
       PROGRAM-ID.    DPMXMCNT.
       AUTHOR.        COGNIZANT.

      ******************************************************************
      *THIS IS AN UNPUBLISHED PROGRAM OWNED BY ISCC                    *
      *IN WHICH A COPYRIGHT SUBSISTS AS OF JULY 2006.                  *
      *                                                                *
      ******************************************************************
      **LNKCTL**
      *    INCLUDE SYSLIB(DSNRLI)
      *    MODE AMODE(31) RMODE(ANY)
      *    ENTRY DPMXMCNT
      *    NAME  DPMXMCNT(R)
      *
      ******************************************************************
      **         P R O G R A M   D O C U M E N T A T I O N            **
      ******************************************************************
      * SYSTEM:    DERIV/SERV MCA XPRESS                               *
      * PROGRAM:   DPMXMCNT                                            *
      *                                                                *
      * THIS STORED PROCEDURE IS USED  TO RETURN THE NAME OF THE LATEST*
      * SAVED GENERIC VERSION OF MCA TEMPLATE PERTAINING TO A DEALER   *
      * THIS STORED PROCEDURE IS CALLED FROM THE WEB  WHEN THE DEALER  *
      * USER SAVES A TEMPLATE IN THE STEP 1 OF THE MCA WIZARD.         *
      ******************************************************************
      * TABLES:                                                        *
      * -------                                                        *
      * D0006 - MASTER TABLE CONTAINS TEMPLATE LEVEL        *
      * DETAILS INCLUSIVE OF ISDA/ CUSTOMIZED TEMPLATE                 *
      *                                                                *
      * VDTM54_DEBUG_CNTRL    - DEBUG CONTROL TABLE                    *
      *----------------------------------------------------------------*
      * INCLUDES:
      * ---------
      * SQLCA    - DB2 COMMAREA
      * DPM1401              - DCLGEN copy book for D0006   *
      * DPM1501              - DCLGEN COPY BOOK FOR VDPM15_TMPLT_WORK  *
      * DTM5401              - DCLGEN copy book for VDTM54_DEBUG_CNTRL *
      *                                                                *
      *----------------------------------------------------------------*
      * COPYBOOK:                                                      *
      * ---------                                                      *
      *                                                                *
      * DB2000IA                                                       *
      * DB2000IB                                                       *
      * DB2000IC                                                       *
      *----------------------------------------------------------------
      *----------------------------------------------------------------
      *              M A I N T E N A N C E   H I S T O R Y            *
      *                                                               *
      *                                                               *
      * DATE CHANGED    VERSION     PROGRAMMER                        *
      * ------------    -------     --------------------              *
      *                                                               *
      * 09/10/2007        00.00     COGNIZANT                         *
      * INITIAL IMPLEMENTATION                                        *
      *                                                               *
      *****************************************************************
       ENVIRONMENT DIVISION.
       DATA DIVISION.
       WORKING-STORAGE SECTION.
       01  WS-PROGRAM                      PIC X(08) VALUE 'DPMXMCNT'.
       01  WS-VARIABLES.
             05  WS-GEN-CNT                PIC S9(4) COMP.
             05  WS-TMPLT-CHK              PIC S9(9) COMP-3             00560400
                                                     VALUE ZEROES.      00560500
             05  WS-TMPLT-ID               PIC S9(9) USAGE COMP.
             05  WS-DLR-ID                 PIC X(8).
             05  WS-DASHES                 PIC X(40) VALUE ALL '='.
             05  WS-INVLD-DLR-ID           PIC X(40) VALUE
                           'INVALID DEALER ID PASSED'.
       01  WS-TS                          PIC X(26).
       01  WS-DISPLAY-SWITCH              PIC X(01) VALUE 'N'.
           88 DISPLAY-PARAMETERS                    VALUE 'Y'.
           88 HIDE-PARAMETERS                       VALUE 'N'.
       01  WS-TBL-SW                      PIC X(01) VALUE ' '.
           88 WS-WORK                               VALUE 'W'.
           88 WS-MSTR                               VALUE 'M'.
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
                INCLUDE DPM1401
           END-EXEC.

           EXEC SQL
                INCLUDE DPM1501
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
       01  LS-TMPLT-ID                     PIC S9(9) USAGE COMP.
       01  LS-DLR-ID                       PIC X(8).
       01  LS-GEN-CNT                      PIC S9(4) USAGE COMP.
       01  LS-TMPLT-NM.
           49 LS-TMPLT-NM-LEN              PIC S9(4) USAGE COMP.
           49 LS-TMPLT-NM-TXT              PIC X(500).

       PROCEDURE DIVISION USING  OUTSQLCA,
                                 LS-SP-ERROR-AREA,
                                 LS-SP-RC,
                                 LS-TMPLT-ID,
                                 LS-DLR-ID,
                                 LS-GEN-CNT,
                                 LS-TMPLT-NM.
      *---------*
       0000-MAIN.
      *---------*

           PERFORM 1000-INITIALIZE
           PERFORM 2000-CHECK-TEMPLATE
           PERFORM 3000-PROCESS
           IF DISPLAY-PARAMETERS
              PERFORM 9100-DISPLAY-DATA
           END-IF
           PERFORM 9990-GOBACK
           .
      *-------------------------*
       1000-INITIALIZE.
      *-------------------------*
           MOVE '1000-INITIALIZE'           TO WS-PARAGRAPH-NAME

           MOVE SPACES                      TO LS-SP-ERROR-AREA
                                               OUTSQLCA
           MOVE 'SP00'                      TO LS-SP-RC
      *CONVERT THE INPUT VALUES INTO UPPER-CASE
           MOVE FUNCTION UPPER-CASE(LS-DLR-ID)
                                            TO LS-DLR-ID
           MOVE LS-DLR-ID                   TO WS-DLR-ID
           MOVE LS-TMPLT-ID                 TO WS-TMPLT-ID
                                                                        00051700
           EXEC SQL                                                     00051800
                SELECT ACTVT_DSPLY_IN                                   00051900
                  INTO :D054-ACTVT-DSPLY-IN                             00052010
                FROM   VDTM54_DEBUG_CNTRL                               00052040
                WHERE PRGM_ID = :WS-PROGRAM                             00052050
                WITH UR
           END-EXEC                                                     00052060
                                                                        00052070
           MOVE SQLCODE                     TO WS-SQLCODE               00052080
                                                                        00052090
           EVALUATE SQLCODE                                             00052091
              WHEN 0                                                    00052092
                  IF D054-ACTVT-DSPLY-IN = 'Y'                          00052094
                     SET DISPLAY-PARAMETERS TO TRUE                     00052095
                  END-IF                                                00052099
              WHEN 100                                                  00052092
                  CONTINUE
              WHEN OTHER                                                00052092
                  PERFORM 9000-SQL-ERROR
           END-EVALUATE                                                 00052102

           IF DISPLAY-PARAMETERS
              EXEC SQL
                  SET :WS-TS = CURRENT TIMESTAMP
              END-EXEC
              DISPLAY WS-DASHES
              DISPLAY 'DPMXMCNT STARTED AT      :' WS-TS
              DISPLAY WS-DASHES
           END-IF
           .
      *----------------------*
       2000-CHECK-TEMPLATE.
      *----------------------*
           MOVE '2000-CHECK-TEMPLATE'       TO WS-PARAGRAPH-NAME        01160000

           PERFORM 2100-VALIDATE-DLR-ID

           EXEC SQL
                SELECT 1
                  INTO :WS-TMPLT-CHK
                  FROM VDPM15_TMPLT_WORK
                 WHERE MCA_TMPLT_ID = :WS-TMPLT-ID
                 WITH UR
           END-EXEC

           EVALUATE SQLCODE
              WHEN 0                                                    01700000
                 SET WS-WORK                TO TRUE
              WHEN 100                                                  01700000
                 SET WS-MSTR                TO TRUE
              WHEN OTHER                                                01750000
                 PERFORM 9000-SQL-ERROR                                 01810000
           END-EVALUATE
           .
      *----------------------*
       2100-VALIDATE-DLR-ID.
      *----------------------*
           MOVE '2100-VALIDATE-DLR-ID'      TO WS-PARAGRAPH-NAME
           IF LS-DLR-ID = SPACES
              MOVE  WS-INVLD-DLR-ID         TO LS-SP-ERROR-AREA
              MOVE  'SP50'                  TO LS-SP-RC
              PERFORM 9990-GOBACK
           END-IF

           EXEC SQL
                SELECT DPM14.DELR_CMPNY_ID
                  INTO :D014-DELR-CMPNY-ID
                  FROM   D0006  DPM14
                 WHERE  DPM14.DELR_CMPNY_ID = :WS-DLR-ID
                FETCH FIRST 1 ROWS ONLY
                WITH UR
           END-EXEC

           EVALUATE SQLCODE
              WHEN 0
                 CONTINUE
              WHEN 100
                 MOVE  0                    TO LS-GEN-CNT
              WHEN OTHER
                 PERFORM 9000-SQL-ERROR
           END-EVALUATE .

      *---------------*
       3000-PROCESS.
      *---------------*
           IF WS-WORK
              PERFORM 3110-GET-GENERIC-CNT
      *       PERFORM 3200-GET-TMPLT-NM
           ELSE
              PERFORM 3120-GET-GENERIC-CNT
      *       PERFORM 3200-GET-TMPLT-NM
           END-IF .
      *--------------------*
       3110-GET-GENERIC-CNT.
      *--------------------*
           MOVE '3110-GET-GENERIC-CNT'      TO WS-PARAGRAPH-NAME

           EXEC SQL
                SELECT COUNT(DPM14.MCA_TMPLT_ID)
                  INTO :WS-GEN-CNT
                  FROM  VDPM15_TMPLT_WORK DPM15
                       ,D0006       DPM14
                 WHERE DPM15.MCA_TMPLT_ID      = :WS-TMPLT-ID
                   AND DPM14.MCA_ISDA_TMPLT_ID = DPM15.MCA_ISDA_TMPLT_ID
                   AND DPM14.DELR_CMPNY_ID     = :WS-DLR-ID
                   AND DPM14.MCA_TMPLT_TYPE_CD = 'D'
                WITH UR
           END-EXEC

           EVALUATE SQLCODE
              WHEN 0
                 MOVE WS-GEN-CNT            TO LS-GEN-CNT
              WHEN OTHER
                 PERFORM 9000-SQL-ERROR
           END-EVALUATE .
      *--------------------*
       3120-GET-GENERIC-CNT.
      *--------------------*
           MOVE '3120-GET-GENERIC-CNT'      TO WS-PARAGRAPH-NAME

           EXEC SQL
                SELECT COUNT(DPM14.MCA_TMPLT_ID)
                  INTO :WS-GEN-CNT
                  FROM  D0006       DPM15
                       ,D0006       DPM14
                 WHERE DPM15.MCA_TMPLT_ID      = :WS-TMPLT-ID
                   AND DPM14.MCA_ISDA_TMPLT_ID = DPM15.MCA_ISDA_TMPLT_ID
                   AND DPM14.DELR_CMPNY_ID     = :WS-DLR-ID
                   AND DPM14.MCA_TMPLT_TYPE_CD = 'D'
                WITH UR
           END-EXEC

           EVALUATE SQLCODE
              WHEN 0
                 MOVE WS-GEN-CNT            TO LS-GEN-CNT
              WHEN 100
                 MOVE 0                     TO LS-GEN-CNT
              WHEN OTHER
                 PERFORM 9000-SQL-ERROR
           END-EVALUATE .
      *--------------------*
      *3200-GET-TMPLT-NM.
      *--------------------*
      *    MOVE '3200-GET-TMPLT-NM'         TO WS-PARAGRAPH-NAME
      *
      *    EXEC SQL
      *       SELECT MCA_TMPLT_NM
      *       INTO :LS-TMPLT-NM
      *       FROM   D0006
      *       WHERE  MCA_ISDA_TMPLT_ID = :WS-TMPLT-ID
      *         AND  MCA_TMPLT_ID      = MCA_ISDA_TMPLT_ID
      *       WITH UR
      *    END-EXEC
      *
      *    EVALUATE SQLCODE
      *       WHEN 0
      *          CONTINUE
      *       WHEN 100
      *          CONTINUE
      *       WHEN OTHER
      *          PERFORM 9000-SQL-ERROR
      *    END-EVALUATE .
      *---------------------*
       9000-SQL-ERROR.
      *------------------------*

           MOVE 'DATABASE ERROR HAS OCCURRED. PLEASE CONTACT DTCC.'
                                            TO LS-SP-ERROR-AREA
           MOVE 'SP99'                      TO LS-SP-RC

           MOVE SQLCODE                     TO WS-SQLCODE
           DISPLAY 'PARAGRAPH-NAME          :' WS-PARAGRAPH-NAME
           DISPLAY 'SQLCODE                :' WS-SQLCODE
           PERFORM 9100-DISPLAY-DATA
           PERFORM 9999-FORMAT-SQLCA
           EXEC SQL
               ROLLBACK
           END-EXEC

           PERFORM 9990-GOBACK
           .
      *------------------------*
       9100-DISPLAY-DATA.
      *------------------------*

           DISPLAY WS-DASHES
           DISPLAY 'PROGRAM-NAME             :' WS-PROGRAM
           DISPLAY 'PARAGRAPH-NAME           :' WS-PARAGRAPH-NAME
           DISPLAY 'SP-ERROR-AREA            :' LS-SP-ERROR-AREA
           DISPLAY 'SP-RC                    :' LS-SP-RC
           DISPLAY 'TEMPLATE-ID              :' LS-TMPLT-ID
           DISPLAY 'DEALER-ID                :' LS-DLR-ID
           DISPLAY 'GENERIC-COUNT            :' LS-GEN-CNT
           DISPLAY 'TEMPLATE-NAME            :' LS-TMPLT-NM

           .
      *------------------------*
       9990-GOBACK.
      *------------------------*

            PERFORM 9999-FORMAT-SQLCA
            IF DISPLAY-PARAMETERS
               EXEC SQL
                   SET :WS-TS = CURRENT TIMESTAMP
               END-EXEC
               DISPLAY WS-DASHES
               DISPLAY 'OUTSQLCA FOR DPMXMCNT    :' OUTSQLCA
               DISPLAY WS-DASHES
               DISPLAY 'DPMXMCNT ENDED AT        :' WS-TS
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