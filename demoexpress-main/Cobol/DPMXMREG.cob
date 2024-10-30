       IDENTIFICATION DIVISION.
       PROGRAM-ID.    DPMXMREG.
       AUTHOR.        COGNIZANT.

      ******************************************************************
      *THIS IS AN UNPUBLISHED PROGRAM OWNED BY ISCC                    *
      *IN WHICH A COPYRIGHT SUBSISTS AS OF JULY 2006.                  *
      *                                                                *
      ******************************************************************
      **LNKCTL**
      *    INCLUDE SYSLIB(DSNRLI)
      *    MODE AMODE(31) RMODE(ANY)
      *    ENTRY DPMXMREG
      *    NAME  DPMXMREG(R)
      *
      ******************************************************************
      **         P R O G R A M   D O C U M E N T A T I O N            **
      ******************************************************************
      * SYSTEM:    DERIV/SERV MCA XPRESS                               *
      * PROGRAM:   DPMXMREG                                            *
      *                                                                *
      * THIS STORED PROCEDURE RETURNS THE LIST OF REGIONS AVAILABLE FOR*
      * THE SPECIFIC PROD / SUB PROD                                   *
      * THIS STORED PROCEDURE IS CALLED FROM THE WEB TO GET THE VALUES *
      * OF THE REGION THE DROP DOWN IN THE MCA WIZARD / INDUSTRY       *
      * PUBLISHED VIEW                                                 *
      ******************************************************************
      * TABLES:                                                        *
      * -------                                                        *
      *      D0006 - MASTER TABLE CONTAINS TEMPLATE LEVEL   *
      * DETAILS INCLUSIVE OF ISDA/ CUSTOMIZED TEMPLATE                 *
      *                                                                *
      *      VDPM04_ATTRB_DTL                                          *
      * VDTM54_DEBUG_CNTRL    - DEBUG CONTROL TABLE                    *
      *----------------------------------------------------------------*
      * INCLUDES:
      * ---------
      * SQLCA                - DB2 COMMAREA                            *
      * DPM1401              - DCLGEN copy book for D0006   *
      * DPM0401              - DCLGEN COPY BOOK FOR VDPM04_ATTRB_DTL   *
      * DTM5401              - DCLGEN copy book for VDTM54_DEBUG_CNTRL *
      *                                                                *
      *----------------------------------------------------------------
      * COPYBOOK:                                                      *
      * ---------                                                      *
      *                                                                *
      * DB2000IA                                                       *
      * DB2000IB                                                       *
      * DB2000IC                                                       *
      *                                                                *
      *----------------------------------------------------------------
      *              M A I N T E N A N C E   H I S T O R Y            *
      *                                                               *
      *                                                               *
      * DATE CHANGED    VERSION     PROGRAMMER                        *
      * ------------    -------     --------------------              *
      *                                                               *
      * 09/01/2007        00.00     COGNIZANT                         *
      * INITIAL IMPLEMENTATION                                        *
      *                                                               *
      *****************************************************************
       ENVIRONMENT DIVISION.
       DATA DIVISION.
       WORKING-STORAGE SECTION.
       01  WS-PROGRAM                      PIC X(08) VALUE 'DPMXMREG'.
       01  WS-VARIABLES.
             05  WS-DASHES                 PIC X(40) VALUE ALL '='.
       01  WS-ERROR-AREA.
             05  WS-PARAGRAPH-NAME         PIC X(40).
             05  FILLER                    PIC X VALUE ','.
             05  WS-TABLE-NAME             PIC X(18).
             05  WS-SQLCODE                PIC 9(7).
       01  WS-TS                           PIC X(26).
       01  WS-PROD-CD                      PIC X(08) VALUE SPACES.
       01  WS-SUB-PROD-CD                  PIC X(08) VALUE SPACES.
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
                INCLUDE DPM1401
           END-EXEC.

           EXEC SQL
                INCLUDE DPM0401
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

       PROCEDURE DIVISION USING  OUTSQLCA,
                                 LS-SP-ERROR-AREA,
                                 LS-SP-RC,
                                 LS-PROD-CD,
                                 LS-SUB-PROD-CD.
      *---------*
       0000-MAIN.
      *---------*

           PERFORM 1000-INITIALIZE
           PERFORM 2000-RETRIEVE-REG-NM
           IF DISPLAY-PARAMETERS
              PERFORM 9100-DISPLAY-DATA
           END-IF
           PERFORM 9990-GOBACK
           .
      *------------------*
       1000-INITIALIZE.
      *------------------*

           MOVE '1000-INITIALIZE'           TO WS-PARAGRAPH-NAME
           MOVE SPACES                      TO LS-SP-ERROR-AREA
                                               OUTSQLCA
           MOVE 'SP00'                      TO LS-SP-RC
      *CONVERT THE INPUT VALUES INTO UPPER-CASE
           MOVE FUNCTION UPPER-CASE(LS-PROD-CD)
                                           TO LS-PROD-CD
           MOVE FUNCTION UPPER-CASE(LS-SUB-PROD-CD)
                                           TO LS-SUB-PROD-CD

           MOVE LS-PROD-CD                 TO WS-PROD-CD
           MOVE LS-SUB-PROD-CD             TO WS-SUB-PROD-CD

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
              DISPLAY 'DPMXMREG STARTED AT      :' WS-TS
              DISPLAY WS-DASHES
           END-IF
           .
      *---------------------*
       2000-RETRIEVE-REG-NM.
      *---------------------*

           MOVE '2000-RETRIEVE-REG-NM'      TO WS-PARAGRAPH-NAME

           IF WS-PROD-CD     > SPACES AND
              WS-SUB-PROD-CD > SPACES
              PERFORM 2100-GET-REG-TMPLT
           ELSE
              PERFORM 2200-GET-ALL-REGION
           END-IF
           .
      *-------------------*
       2100-GET-REG-TMPLT.
      *-------------------*

           MOVE '2100-GET-REG-TMPLT'        TO WS-PARAGRAPH-NAME

           EXEC SQL                                                     07090062
               DECLARE DPMXMREG_CSR CURSOR WITH HOLD WITH RETURN FOR    07100062
     1            SELECT DPM04.ATTRB_ID ,
     2                   DPM04.ATTRB_VALUE_DS
                  FROM   VDPM04_ATTRB_DTL    DPM04
                        ,D0006    DPM14
                  WHERE  DPM04.ATTRB_ID            = DPM14.ATTRB_REGN_ID
                    AND  DPM14.ATTRB_PRDCT_ID      = :WS-PROD-CD
                    AND  DPM14.ATTRB_SUB_PRDCT_ID  = :WS-SUB-PROD-CD
                    AND  DPM14.MCA_TMPLT_TYPE_CD   =  'I'
                    AND  DPM14.MCA_STAT_IN         =  'P'
                    AND  DPM04.ATTRB_TYPE_ID       =  'R'
                  ORDER BY MCA_PBLTN_DT DESC
                  FETCH FIRST ROW ONLY
                  WITH UR
           END-EXEC

           EXEC SQL
              OPEN DPMXMREG_CSR
           END-EXEC

           EVALUATE SQLCODE
              WHEN 0
                 MOVE 'SP00'                TO LS-SP-RC
              WHEN OTHER
                 PERFORM 9000-SQL-ERROR
           END-EVALUATE
           .
      *--------------------*
       2200-GET-ALL-REGION.
      *--------------------*

           MOVE '2200-GET-ALL-REGION'       TO WS-PARAGRAPH-NAME

           EXEC SQL                                                     07090062
               DECLARE ALL_REG_CSR CURSOR WITH HOLD WITH RETURN FOR     07100062
     1            SELECT ATTRB_ID
     2                  ,ATTRB_VALUE_DS
                    FROM VDPM04_ATTRB_DTL
                   WHERE ATTRB_TYPE_ID = 'R'
                   ORDER BY ATTRB_ID
                  WITH UR
           END-EXEC

           EXEC SQL
              OPEN ALL_REG_CSR
           END-EXEC

           EVALUATE SQLCODE
              WHEN 0
                 MOVE 'SP00'                TO LS-SP-RC
              WHEN OTHER
                 PERFORM 9000-SQL-ERROR
           END-EVALUATE
           .
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
           DISPLAY 'PRODUCT-CD               :' LS-PROD-CD
           DISPLAY 'SUB-PRODUCT-CD           :' LS-SUB-PROD-CD
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
               DISPLAY 'OUTSQLCA FOR DPMXMREG    :' OUTSQLCA
               DISPLAY WS-DASHES
               DISPLAY 'DPMXMREG ENDED AT        :' WS-TS
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