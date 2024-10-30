       IDENTIFICATION DIVISION.
       PROGRAM-ID.    DPMXMPSP.
       AUTHOR.        COGNIZANT.

      ******************************************************************
      *THIS IS AN UNPUBLISHED PROGRAM OWNED BY ISCC                    *
      *IN WHICH A COPYRIGHT SUBSISTS AS OF JULY 2006.                  *
      *                                                                *
      ******************************************************************
      **LNKCTL**
      *    INCLUDE SYSLIB(DSNRLI)
      *    MODE AMODE(31) RMODE(ANY)
      *    ENTRY DPMXMPSP
      *    NAME  DPMXMPSP(R)
      *
      ******************************************************************
      **         P R O G R A M   D O C U M E N T A T I O N            **
      ******************************************************************
      * SYSTEM:    DERIV/SERV MCA XPRESS                               *
      * PROGRAM:   DPMXMPSP                                            *
      *                                                                *
      * THIS STORED PROCEDURE RETURNS THE AVAILABLE PROD AND SUB PROD  *
      * INFORMATION TO THE WEB WHEN THE INDUSTRY PUBLISHED UNDER THE   *
      * MCA TAB OR THE MCA WIZARD                                      *
      * (BY DEALER) IS CLICKED BY THE USER                             *
      ******************************************************************
      * TABLES:                                                        *
      * -------                                                        *
      *      TDPM04_ATTRB_DTL - CONTAINS DETAILED DESCRIPTION OF THE   *
      * ABBREVIATED CODE FOR VARIOUS PROD / SUB PROD / REGION /        *
      * CATEGORY / TERM                                                *
      * VDTM54_DEBUG_CNTRL    - DEBUG CONTROL TABLE                    *
      *----------------------------------------------------------------*
      * INCLUDES:                                                     *
      * ---------                                                     *
      * SQLCA    - DB2 COMMAREA                                       *
      * DPM0401                                                       *
      * DTM5401                                                       *
      *----------------------------------------------------------------
      * COPYBOOK:                                                     *
      * ---------                                                     *
      * DB2000IA                                                      *
      * DB2000IB                                                      *
      * DB2000IC                                                      *
      *----------------------------------------------------------------
      *              M A I N T E N A N C E   H I S T O R Y            *
      *                                                               *
      *                                                               *
      * DATE CHANGED    VERSION     PROGRAMMER                        *
      * ------------    -------     --------------------              *
      *                                                               *
      * 09/11/2007        00.00     COGNIZANT                         *
      * INITIAL IMPLEMENTATION                                        *
      *                                                               *
      *****************************************************************
       ENVIRONMENT DIVISION.
       DATA DIVISION.
       WORKING-STORAGE SECTION.
       01  WS-PROGRAM                      PIC X(08) VALUE 'DPMXMPSP'.
       01  WS-DASHES                       PIC X(40) VALUE ALL '='.
       01  WS-ERROR-AREA.
             05  WS-PARAGRAPH-NAME         PIC X(40).
             05  FILLER                    PIC X VALUE ','.
             05  WS-TABLE-NAME             PIC X(18).
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
           PERFORM 2000-RETRIEVE-PROD
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
              DISPLAY 'DPMXMPSP STARTED AT      :' WS-TS
              DISPLAY WS-DASHES
           END-IF
           .
      *---------------------*
       2000-RETRIEVE-PROD.
      *---------------------*

           MOVE '2000-RETRIEVE-PROD'        TO WS-PARAGRAPH-NAME


           EXEC SQL                                                     07090062
               DECLARE DPMXMPSP_CSR  CURSOR WITH HOLD WITH RETURN FOR   07100062
    1             SELECT ATTRB.PROD_CD
    2                   ,ATTRB.PROD_DESC
    3                   ,ATTRB.PROD_TYPE
    4                   ,ATTRB.SPROD_CD
    5                   ,ATTRB.SPROD_DESC
    6                   ,ATTRB.SPROD_TYPE
    7                   ,COALESCE(DPM14.MCA_TMPLT_ID,0)
                    FROM
                    (SELECT  DPM04A.ATTRB_ID           PROD_CD
                            ,DPM04A.ATTRB_VALUE_DS     PROD_DESC
                            ,DPM04A.ATTRB_TYPE_ID      PROD_TYPE
                            ,DPM04B.ATTRB_ID           SPROD_CD
                            ,DPM04B.ATTRB_VALUE_DS     SPROD_DESC
                            ,DPM04B.ATTRB_TYPE_ID      SPROD_TYPE
                       FROM  VDPM04_ATTRB_DTL    DPM04A
                            ,VDPM04_ATTRB_DTL    DPM04B
                      WHERE  DPM04A.ATTRB_TYPE_ID  = 'P'
                        AND  DPM04B.ATTRB_TYPE_ID  = 'S') ATTRB
                     LEFT OUTER JOIN D0006  DPM14
                      ON ATTRB.PROD_CD     = DPM14.ATTRB_PRDCT_ID
                     AND ATTRB.SPROD_CD    = DPM14.ATTRB_SUB_PRDCT_ID
                     AND DPM14.MCA_STAT_IN = 'P'
                  ORDER BY ATTRB.PROD_CD  ASC
                          ,ATTRB.SPROD_CD ASC
                     WITH UR
           END-EXEC

           EXEC SQL
              OPEN DPMXMPSP_CSR
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
               DISPLAY 'OUTSQLCA FOR DPMXMPSP    :' OUTSQLCA
               DISPLAY WS-DASHES
               DISPLAY 'DPMXMPSP ENDED AT        :' WS-TS
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