       IDENTIFICATION DIVISION.
       PROGRAM-ID.    DPMXDORG.
       AUTHOR.        COGNIZANT.

      ******************************************************************
      *THIS IS AN UNPUBLISHED PROGRAM OWNED BY ISCC                    *
      *IN WHICH A COPYRIGHT SUBSISTS AS OF JULY 2006.                  *
      *                                                                *
      ******************************************************************
      **LNKCTL**
      *    INCLUDE SYSLIB(DSNRLI)
      *    MODE AMODE(31) RMODE(ANY)
      *    ENTRY DPMXDORG
      *    NAME  DPMXDORG(R)
      *
      ******************************************************************
      **         P R O G R A M   D O C U M E N T A T I O N            **
      ******************************************************************
      * SYSTEM:    DERIV/SERV MCA XPRESS                               *
      * PROGRAM:   DPMXDORG                                            *
      *                                                                *
      * FOR A GIVEN DEALER/CLIENT ID, THIS STORED PROCEDURE RETURNS    *
      * THE OTHER DEALER/CLIENT ID' WHO HAVE REGISTERED WITH MCA       *
      * XPRESS AND ALSO OTHER DEALER/CLIENT ID ADDED BY THE GIVEN      *
      * DEALER / CLIENT ID.                                            *
      ******************************************************************
      * TABLES:                                                        *
      * -------                                                        *
      * NSCC.TDPM01_MCA_CMPNY- MASTER TABLE THAT HAS THE CLIENT/DEALER *
      * COMPANY DETAILS.                                               *
      *                                                                *
      * NSCC.TDPM02_DELR_CMPNY - TABLE THAT HAS THE CLIENT/DEALER      *
      * DETAILS ADDED BY THE DEALER / CLIENT.                          *
      *                                                                *
      * NSCC.TDPM09_DOC_USER   - TABLE THAT HAS THE CLIENT/DEALER      *
      * LINKED WITH A DOCUMENT.                                        *
      *                                                                *
      * VDTM54_DEBUG_CNTRL    - DEBUG CONTROL TABLE                    *
      *                                                                *
      *----------------------------------------------------------------*
      * INCLUDES:
      * ---------
      * SQLCA    - DB2 COMMAREA
      * DPM0901  - DCLGEN FOR D0004
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
      *                                                               *
      *                                                               *
      * DATE CHANGED    VERSION     PROGRAMMER                        *
      * ------------    -------     --------------------              *
      *                                                               *
      * 09/05/2007         1.0      COGNIZANT                         *
      *                             initial implementation            *
      *                                                               *
      *****************************************************************
       ENVIRONMENT DIVISION.
       DATA DIVISION.
       WORKING-STORAGE SECTION.
       01  WS-PROGRAM                      PIC X(08) VALUE 'DPMXDORG'.
       01  WS-VARIABLES.
             05  WS-DASHES                 PIC X(40) VALUE ALL '='.
             05  WS-CMPNY-STAT-IN          PIC X(01).
             05  WS-CMPNY-TYPE-CD          PIC X(01).
             05  WS-CMPNY-ID               PIC X(08).
             05  WS-EMPTY-CMP-ID           PIC X(50) VALUE
                 'Input company id is empty'.
             05  WS-INVLD-CMP-ID           PIC X(50) VALUE
                 'Input company id is invalid'.
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

      * INCLUDE FOR VDTM54_DEBUG_CNTRL                                  00024910
           EXEC SQL                                                     00024920
                INCLUDE DTM5401                                         00024930
           END-EXEC.                                                    00024940
013300

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
       01  LS-IN-CMP-CD                    PIC X(08).
       01  LS-IN-DEL-CMP-CD                PIC X(08).

       PROCEDURE DIVISION USING  OUTSQLCA,
                                 LS-SP-ERROR-AREA,
                                 LS-SP-RC,
                                 LS-IN-CMP-CD,
                                 LS-IN-DEL-CMP-CD.
      *---------*
       0000-MAIN.
      *---------*

           PERFORM 1000-INITIALIZE

           PERFORM 2000-VALIDATE-INPUT

           PERFORM 3000-RETRIEVE-CMP-NM

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
                                                                        00052070
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
      *------------------------*
       2000-VALIDATE-INPUT.
      *------------------------*

           MOVE '2000-VALIDATE-INPUT'      TO WS-PARAGRAPH-NAME

           IF DISPLAY-PARAMETERS
              DISPLAY WS-PARAGRAPH-NAME
           END-IF

           IF LS-IN-CMP-CD > SPACES
              PERFORM 2100-VALIDATE-CMP-ID
           ELSE
              MOVE WS-EMPTY-CMP-ID         TO LS-SP-ERROR-AREA
              MOVE 'SP50'                  TO LS-SP-RC
              PERFORM 9100-DISPLAY-DATA
              PERFORM 9990-GOBACK
           END-IF
           .
      *----------------------------*
       2100-VALIDATE-CMP-ID.
      *----------------------------*

           MOVE '2100-VALIDATE-CMP-ID'
                                           TO WS-PARAGRAPH-NAME


           IF DISPLAY-PARAMETERS
              DISPLAY WS-PARAGRAPH-NAME
           END-IF

           EXEC SQL

              SELECT  CMPNY_ID,
                      CMPNY_STAT_IN,
                      CMPNY_TYPE_CD
                    INTO :WS-CMPNY-ID
                        ,:WS-CMPNY-STAT-IN
                        ,:WS-CMPNY-TYPE-CD
                FROM  D0005
                WHERE CMPNY_ID = :LS-IN-CMP-CD
                WITH UR
           END-EXEC

           EVALUATE SQLCODE
              WHEN 0
                 CONTINUE
              WHEN 100
                 MOVE WS-INVLD-CMP-ID    TO LS-SP-ERROR-AREA
                 MOVE 'SP50'             TO LS-SP-RC
                 PERFORM 9100-DISPLAY-DATA
                 PERFORM 9990-GOBACK
              WHEN OTHER
                 PERFORM 9000-SQL-ERROR
           END-EVALUATE
           .
      *---------------------*
       3000-RETRIEVE-CMP-NM.
      *---------------------*

           MOVE '3000-RETRIEVE-CMP-NM'      TO WS-PARAGRAPH-NAME

           IF DISPLAY-PARAMETERS
              DISPLAY WS-PARAGRAPH-NAME
           END-IF

           EXEC SQL                                                     07090062
               DECLARE DPMXDORG_CSR1 CURSOR WITH HOLD WITH RETURN FOR   07100062
1                 SELECT DISTINCT(DPM01.CMPNY_ID)
2                       ,DPM01.CMPNY_NM
3                       ,'R' AS ORGANIZATION_IND
                        ,CASE
                           WHEN VALUE(DPM09.CMPNY_ID,'N') = 'N'
                             THEN 'N'
                           ELSE 'Y'
4                        END AS DOCUMENT_IND
                         FROM D0005 DPM01
                         LEFT OUTER JOIN
                         D0004 DPM09
                         ON DPM01.CMPNY_ID = DPM09.CMPNY_ID
                         WHERE (DPM01.CMPNY_STAT_IN = :WS-CMPNY-STAT-IN
                            AND DPM01.CMPNY_TYPE_CD IN ('C','D'))
               UNION ALL
1                 SELECT DISTINCT(DPM02.CMPNY_ID)
2                        ,DPM02.CMPNY_NM
3                        ,'U' AS ORGANIZATION_IND
                        ,CASE
                           WHEN VALUE(DPM09.CMPNY_ID,'N') = 'N'
                             THEN 'N'
                           ELSE 'Y'
4                        END AS DOCUMENT_IND
                         FROM VDPM02_DELR_CMPNY DPM02
                         LEFT OUTER JOIN
                         D0004 DPM09
                         ON DPM02.CMPNY_ID = DPM09.CMPNY_ID
                         WHERE (DPM02.CRT_CMPNY_ID = :LS-IN-CMP-CD
                            AND DPM02.CMPNY_ID    <> :LS-IN-DEL-CMP-CD)
                  ORDER BY CMPNY_NM
                  WITH UR
           END-EXEC

           EXEC SQL
              OPEN DPMXDORG_CSR1
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
102700     DISPLAY 'SQLCODE                  :' WS-SQLCODE
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
102900         DISPLAY 'LS-IN-CMP-CD:' LS-IN-CMP-CD
102900         DISPLAY 'LS-IN-DEL-CMP-CD         :' LS-IN-DEL-CMP-CD
102600         DISPLAY WS-DASHES
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