       IDENTIFICATION DIVISION.
       PROGRAM-ID.    DPMXMALL.
       AUTHOR.        COGNIZANT.

      ******************************************************************
      *THIS IS AN UNPUBLISHED PROGRAM OWNED BY ISCC                    *
      *IN WHICH A COPYRIGHT SUBSISTS AS OF JULY 2006.                  *
      *                                                                *
      ******************************************************************
      **LNKCTL**
      *    INCLUDE SYSLIB(DSNRLI)
      *    MODE AMODE(31) RMODE(ANY)
      *    ENTRY DPMXMALL
      *    NAME  DPMXMALL(R)
      *
      ******************************************************************
      **         P R O G R A M   D O C U M E N T A T I O N            **
      ******************************************************************
      * SYSTEM:    DERIV/SERV MCA XPRESS                               *
      * PROGRAM:   DPMXMALL                                            *
      *                                                                *
      * THIS STORED PROCEDURE IS USED TO FETCH ALL THE REQUIRED        *
      * TEMPLATES FOR A SELECTED PROD / SUB PROD / REGION / DEALER ID  *
      * THIS STORED PROCEDURE IS CALLED FROM THE WEB TO GET THE VALUES *
      * OF THE TEMPLATES FOR THE DROP DOWN IN THE MCA WIZARD OR        *
      * INDUSTRY PUBLISHED VIEW                                        *
      ******************************************************************
      * TABLES:                                                        *
      * -------                                                        *
      * NSCC.D0006 - MASTER TABLE CONTAINS TEMPLATE LEVEL   *
      * DETAILS INCLUSIVE OF ISDA/ CUSTOMIZED TEMPLATE                 *
      *                                                                *
      * VDTM54_DEBUG_CNTRL    - DEBUG CONTROL TABLE                    *
      *----------------------------------------------------------------*
      * INCLUDES:
      * ---------
      * SQLCA    - DB2 COMMAREA
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
      *                                                               *
      * DATE CHANGED    VERSION     PROGRAMMER                        *
      * ------------    -------     --------------------              *
      *                                                               *
      * 09/04/2007        00.00     COGNIZANT                         *
      * INITIAL IMPLEMENTATION                                        *
      *                                                               *
      *****************************************************************
       ENVIRONMENT DIVISION.
       DATA DIVISION.
       WORKING-STORAGE SECTION.
       01  WS-PROGRAM                      PIC X(08) VALUE 'DPMXMALL'.
       01  WS-VARIABLES.
             05  WS-CNT                    PIC S9(4) COMP VALUE 0.
             05  WS-ACT-CNT                PIC S9(4) COMP VALUE 0.
             05  WS-DASHES                 PIC X(40) VALUE ALL '='.
             05  WS-PROD-CD                PIC X(08) VALUE SPACES.
             05  WS-SUB-PROD-CD            PIC X(08) VALUE SPACES.
             05  WS-REGION-CD              PIC X(08) VALUE SPACES.
             05  WS-DEALER-ID              PIC X(08) VALUE SPACES.
             05  WS-INVLD-ACTN-TYP         PIC X(40) VALUE
                           'INVALID ACTION TYPE PASSED'.
             05  WS-DLR-REQ                PIC X(40) VALUE
                           'DEALER ID REQUIRED AS INPUT'.
       01  WS-TS                          PIC X(26).
       01  WS-ERROR-AREA.
             05  WS-PARAGRAPH-NAME         PIC X(40).
             05  FILLER                    PIC X VALUE ','.
             05  WS-TABLE-NAME             PIC X(18).
             05  WS-SQLCODE                PIC 9(7).
       01  WS-SWITCHES.
             05 WS-DONE-SWITCH             PIC X(1)  VALUE 'N'.
                88 WS-DONE                           VALUE 'Y'.
             05 WS-BYTE                    PIC X(01).
                88 VALID-LETTER            VALUES 'A' THRU 'Z'.
                88 VALID-DIGIT             VALUES '0' THRU '9'.
             05 WS-DISPLAY-SWITCH          PIC X(01) VALUE 'N'.
                88 DISPLAY-PARAMETERS                VALUE 'Y'.
                88 HIDE-PARAMETERS                   VALUE 'N'.
             05 WS-TMPLT-TYP-SW            PIC X(01).
                88  WS-ISDA                VALUE 'I'.
                88  WS-DEALER-GENERIC      VALUE 'D'.

      *****************************************************************
      *                        SQL INCLUDES                            *
      ******************************************************************
           EXEC SQL
                INCLUDE SQLCA
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
       01  LS-PROD-CD                      PIC X(08).
       01  LS-SUB-PROD-CD                  PIC X(08).
       01  LS-REGION-CD                    PIC X(08).
       01  LS-DEALER-ID                    PIC X(08).
       01  LS-TMPLT-TYP                    PIC X(01).

       PROCEDURE DIVISION USING  OUTSQLCA,
                                 LS-SP-ERROR-AREA,
                                 LS-SP-RC,
                                 LS-PROD-CD,
                                 LS-SUB-PROD-CD,
                                 LS-REGION-CD,
                                 LS-DEALER-ID,
                                 LS-TMPLT-TYP.
      *---------*
       0000-MAIN.
      *---------*

           PERFORM 1000-INITIALIZE

           PERFORM 2000-VALIDATE-INPUT
           PERFORM 3000-RETRIEVE-TMPLT-NM

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
           MOVE FUNCTION UPPER-CASE(LS-PROD-CD)
                                           TO LS-PROD-CD
           MOVE FUNCTION UPPER-CASE(LS-SUB-PROD-CD)
                                           TO LS-SUB-PROD-CD
           MOVE FUNCTION UPPER-CASE(LS-REGION-CD)
                                           TO LS-REGION-CD
           MOVE FUNCTION UPPER-CASE(LS-DEALER-ID)
                                           TO LS-DEALER-ID
           MOVE FUNCTION UPPER-CASE(LS-TMPLT-TYP)
                                           TO LS-TMPLT-TYP
           MOVE LS-TMPLT-TYP               TO WS-TMPLT-TYP-SW
           MOVE LS-PROD-CD                 TO WS-PROD-CD
           MOVE LS-SUB-PROD-CD             TO WS-SUB-PROD-CD
           MOVE LS-REGION-CD               TO WS-REGION-CD
           MOVE LS-DEALER-ID               TO WS-DEALER-ID
           EXEC SQL
               SET :WS-TS = CURRENT TIMESTAMP
           END-EXEC
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
              DISPLAY WS-DASHES
              DISPLAY 'DPMXMALL STARTED AT      :' WS-TS
              DISPLAY WS-DASHES
           END-IF
           .
      *--------------------------*
       2000-VALIDATE-INPUT.
      *--------------------------*
           MOVE '2000-VALIDATE-INPUT'        TO WS-PARAGRAPH-NAME
           EVALUATE TRUE
              WHEN WS-ISDA
                 IF LS-DEALER-ID = SPACES
                    CONTINUE
                 END-IF
              WHEN WS-DEALER-GENERIC
                 IF LS-DEALER-ID = SPACES
                    MOVE WS-DLR-REQ         TO LS-SP-ERROR-AREA
                    MOVE 'SP50'             TO LS-SP-RC
                    PERFORM 9990-GOBACK
                 END-IF
              WHEN OTHER
                 MOVE  WS-INVLD-ACTN-TYP    TO LS-SP-ERROR-AREA
                 MOVE  'SP50'               TO LS-SP-RC
                 PERFORM 9990-GOBACK
           END-EVALUATE .
      *-----------------------*
       3000-RETRIEVE-TMPLT-NM.
      *-----------------------*

           MOVE '3000-RETRIEVE-TMPLT-NM'    TO WS-PARAGRAPH-NAME

           EXEC SQL                                                     07090062
               DECLARE DPMXMALL_CSR1 CURSOR WITH HOLD WITH RETURN FOR   07100062
     1            SELECT MCA_TMPLT_ID,
     2                   MCA_TMPLT_NM
                  FROM   D0006
                  WHERE  ATTRB_PRDCT_ID      = :WS-PROD-CD
                  AND    ATTRB_SUB_PRDCT_ID  = :WS-SUB-PROD-CD
                  AND    ATTRB_REGN_ID       = :WS-REGION-CD
                  AND    MCA_TMPLT_TYPE_CD   = 'I'
                  AND    MCA_STAT_IN         = 'P'
                  ORDER BY MCA_TMPLT_ID
                  WITH UR
           END-EXEC

           EXEC SQL                                                     07090062
               DECLARE DPMXMALL_CSR2 CURSOR WITH HOLD WITH RETURN FOR   07100062
     1            SELECT MCA_TMPLT_ID,
     2                   MCA_TMPLT_NM
                  FROM   D0006
                  WHERE  ATTRB_PRDCT_ID      = :WS-PROD-CD
                  AND    ATTRB_SUB_PRDCT_ID  = :WS-SUB-PROD-CD
                  AND    ATTRB_REGN_ID       = :WS-REGION-CD
                  AND  ((MCA_TMPLT_TYPE_CD   = 'I'
                  AND    MCA_STAT_IN         = 'P')
                  OR
                        (DELR_CMPNY_ID       = :WS-DEALER-ID
                  AND    MCA_TMPLT_TYPE_CD  IN ('D','C','E'))
                         )
                  ORDER BY MCA_TMPLT_TYPE_CD DESC
                          ,MCA_PBLTN_DT      DESC
                          ,MCA_TMPLT_NM      ASC
                  WITH UR
           END-EXEC

           EVALUATE TRUE
              WHEN WS-ISDA
                 EXEC SQL
                    OPEN DPMXMALL_CSR1
                 END-EXEC
              WHEN OTHER
                 EXEC SQL
                    OPEN DPMXMALL_CSR2
                 END-EXEC
           END-EVALUATE

           EVALUATE SQLCODE
              WHEN 0
                 MOVE 'SP00'                TO LS-SP-RC
              WHEN OTHER
                 PERFORM 9000-SQL-ERROR
           END-EVALUATE .

      *---------------------*
       9000-SQL-ERROR.
      *------------------------*

           PERFORM 9100-DISPLAY-DATA
           MOVE 'DATABASE ERROR HAS OCCURRED. PLEASE CONTACT DTCC.'
                                            TO LS-SP-ERROR-AREA
           MOVE 'SP99'                      TO LS-SP-RC
           MOVE SQLCODE                     TO WS-SQLCODE
           DISPLAY 'PARAGRAPH-NAME          :' WS-PARAGRAPH-NAME
           DISPLAY 'SQLCODE                :' WS-SQLCODE
           PERFORM 9999-FORMAT-SQLCA
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
           DISPLAY 'PRODUCT-CD               :' LS-PROD-CD
           DISPLAY 'SUB-PRODUCT-CD           :' LS-SUB-PROD-CD
           DISPLAY 'REGION-CD                :' LS-REGION-CD
           DISPLAY 'DEALER-ID                :' LS-DEALER-ID
           DISPLAY 'TEMPLATE-TYPE            :' LS-TMPLT-TYP

           EXEC SQL
               SET :WS-TS = CURRENT TIMESTAMP
           END-EXEC
           .
      *------------------------*
       9990-GOBACK.
      *------------------------*

            PERFORM 9999-FORMAT-SQLCA
            IF DISPLAY-PARAMETERS
               DISPLAY WS-DASHES
               DISPLAY 'OUTSQLCA FOR DPMXMALL    :' OUTSQLCA
               DISPLAY WS-DASHES
               DISPLAY 'DPMXMALL ENDED AT        :' WS-TS
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