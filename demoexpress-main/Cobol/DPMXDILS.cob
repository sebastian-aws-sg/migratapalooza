       IDENTIFICATION DIVISION.
       PROGRAM-ID.    DPMXDILS.
       AUTHOR.        COGNIZANT.

      ******************************************************************
      *THIS IS AN UNPUBLISHED PROGRAM OWNED BY ISCC                    *
      *IN WHICH A COPYRIGHT SUBSISTS AS OF JULY 2006.                  *
      *                                                                *
      ******************************************************************
      **LNKCTL**
      *    INCLUDE SYSLIB(DSNRLI)
      *    MODE AMODE(31) RMODE(ANY)
      *    ENTRY DPMXDILS
      *    NAME  DPMXDILS(R)
      *
      ******************************************************************
      **         P R O G R A M   D O C U M E N T A T I O N            **
      ******************************************************************
      * SYSTEM:    DERIV/SERV MCA XPRESS                               *
      * PROGRAM:   DPMXDILS                                            *
      *                                                                *
      *                                                                *
      * THIS STORED PROCEDURE RETREIVES THE AVAILABLE ISDA MCA         *
      * TEMPLATES TO ENABLE THE USER TO CHOOSE FOR PRE-EXISTING MCA'S. *
      ******************************************************************
      * TABLES:                                                        *
      * -------                                                        *
      * NSCC.TDPM14_MCA_TMPLT - MASTER TABLE THAT HAS THE TEMPLATE     *
      * INFORMATION.                                                   *
      *                                                                *
      * NSCC.TDPM04_ATTRB_DTL - ATTRIBUTE TABLE THAT HAS THE DESC FOR  *
      * VARIOUS CODES.                                                 *
      *                                                                *
      * VDTM54_DEBUG_CNTRL    - DEBUG CONTROL TABLE                    *
      *                                                                *
      *                                                                *
      *----------------------------------------------------------------*
      * INCLUDES:
      * ---------
      * SQLCA    - DB2 COMMAREA
      * DPM1401  - DCLGEN FOR VDPM14_MCA_DOC
      * DPM0401  - DCLGEN FOR VDPM04_ATTRB_DTL
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
       01  WS-PROGRAM                      PIC X(08) VALUE 'DPMXDILS'.
       01  WS-DASHES                       PIC X(40) VALUE ALL '='.
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

       PROCEDURE DIVISION USING  OUTSQLCA,
                                 LS-SP-ERROR-AREA,
                                 LS-SP-RC.
      *---------*
       0000-MAIN.
      *---------*

           PERFORM 1000-INITIALIZE

           PERFORM 2000-SELECT-ISDA-MCA-DTLS

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
              DISPLAY 'DPMXDORG STARTED AT      :' WS-TS
              DISPLAY WS-DASHES
           END-IF

           .
      *---------------------*
       2000-SELECT-ISDA-MCA-DTLS.
      *---------------------*

           MOVE '2000-SELECT-ISDA-MCA-DTLS'
                                           TO WS-PARAGRAPH-NAME

           IF DISPLAY-PARAMETERS
              DISPLAY WS-PARAGRAPH-NAME
           END-IF

           EXEC SQL                                                     07090062
               DECLARE DPMXDILS_CSR1 CURSOR WITH HOLD WITH RETURN FOR   07100062
  1              SELECT  DPM14.MCA_TMPLT_ID
  2                     ,DPM14.MCA_TMPLT_NM
  3                     ,DPM14.MCA_TMPLT_SHORT_NM
  4                     ,COALESCE(DPM14.ATTRB_REGN_ID,' ') AS REGION_ID
  5                     ,COALESCE(DPM041.ATTRB_VALUE_DS,' ') AS REGION
  6                     ,COALESCE(DPM14.ATTRB_PRDCT_ID,' ') AS PROD_ID
  7                     ,COALESCE(DPM042.ATTRB_VALUE_DS,' ') AS PRODUCT
  8                     ,COALESCE(DPM14.ATTRB_SUB_PRDCT_ID,' ') AS
                         SUB_PRODUCT_ID
  9                     ,COALESCE(DPM043.ATTRB_VALUE_DS,' ') AS
                         SUB_PRODUCT
                         FROM D0006 DPM14
                                 LEFT OUTER JOIN
                              VDPM04_ATTRB_DTL DPM041
                                 ON
                             (DPM14.ATTRB_REGN_ID = DPM041.ATTRB_ID
                          AND DPM041.ATTRB_TYPE_ID = 'R')
                                 LEFT OUTER JOIN
                              VDPM04_ATTRB_DTL DPM042
                                 ON
                             (DPM14.ATTRB_PRDCT_ID = DPM042.ATTRB_ID
                          AND DPM042.ATTRB_TYPE_ID = 'P')
                                 LEFT OUTER JOIN
                              VDPM04_ATTRB_DTL DPM043
                                 ON
                             (DPM14.ATTRB_SUB_PRDCT_ID = DPM043.ATTRB_ID
                          AND DPM043.ATTRB_TYPE_ID = 'S')
                         WHERE ((DPM14.MCA_TMPLT_TYPE_CD = 'I'
                                AND DPM14.MCA_STAT_IN = 'P')
                              OR (DPM14.MCA_TMPLT_TYPE_CD = 'P'
                                AND DPM14.DELR_CMPNY_ID= ' '
                                AND DPM14.CLNT_CMPNY_ID= ' '))
                  ORDER BY DPM14.MCA_TMPLT_ID
                  WITH UR
           END-EXEC

           EXEC SQL
              OPEN DPMXDILS_CSR1
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
           END-IF
107000     .
      *------------------------*
       9990-GOBACK.
      *------------------------*

            PERFORM 9999-FORMAT-SQLCA
            IF DISPLAY-PARAMETERS
               DISPLAY WS-DASHES
               DISPLAY 'OUTSQLCA FOR DPMXDORG    :' OUTSQLCA
               DISPLAY WS-DASHES
               EXEC SQL
                   SET :WS-TS = CURRENT TIMESTAMP
               END-EXEC
               DISPLAY 'DPMXDORG ENDED AT        :' WS-TS
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