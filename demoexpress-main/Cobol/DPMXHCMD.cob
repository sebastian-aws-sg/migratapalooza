000100 IDENTIFICATION DIVISION.
000200 PROGRAM-ID.   DPMXHCMD.
000300 AUTHOR.       COGNIZANT.
000400 DATE-WRITTEN. SEPTEMEBER 2007.
000500*
000600*
000700****************************************************************
000800*   THIS IS AN UNPUBLISHED PROGRAM OWNED BY ISCC               *
000900*   IN WHICH A COPYRIGHT SUBSISTS AS OF JULY 2006.            *
001000****************************************************************
001100****************************************************************
001200*                   COMPILATION INSTRUCTION                    *
001300*                  COMPILE DB2 VS COBOL 370                    *
001400****************************************************************
001500*    **LNKCTL**
001600*    INCLUDE SYSLIB(DSNRLI)
001700*    MODE AMODE(31) RMODE(ANY)
001800*    ENTRY DPMXHCMD
001900*    NAME  DPMXHCMD(R)
002000*
002100******************************************************************
002200**         P R O G R A M   D O C U M E N T A T I O N            **
002300******************************************************************
002400* SYSTEM:    MCA XPRESS APPLICATION                              *
002500* PROGRAM:   DPMXHCMD                                            *
002600*                                                                *
002700* THIS STORED PROCEDURE SENDS THE LIST OF ALL MCA REGISTERED     *
002800* COMPANIES AND THEIR DETAILS. THIS WILL BE CALLED BY THE WEB    *
002900* WHEN THE SUPER USER LOGS IN MCA.                               *
003000******************************************************************
003100* TABLES:                                                        *
003200* -------                                                        *
003300* D0005      - COMPANY    TABLE FOR MCA
003900*----------------------------------------------------------------*
004000* INCLUDES:                                                      *
004100* ---------                                                      *
004200* SQLCA    - DB2 COMMAREA                                        *
004300* DPM0101  - COMPANY TABLE
      * DTM5401 - DEBUG CONTROL TABLE
005000*----------------------------------------------------------------*
305100* COPYBOOK:                                                      *
005200* ---------                                                      *
005300* DB2000IA                                                       *
005400* DB20001B                                                       *
005500* DB20001C                                                       *
005600*----------------------------------------------------------------*
005700*              M A I N T E N A N C E   H I S T O R Y             *
005800*                                                                *
005900*                                                                *
006000* DATE CHANGED    VERSION     PROGRAMMER                         *
006100* ------------    -------     --------------------               *
006200*                                                                *
006300* 08/30/2007        000       COGNIZANT                          *
006400*                             INITIAL IMPLEMENTATION.            *
006200*                                                                *
006600******************************************************************
006700 ENVIRONMENT DIVISION.
006800 DATA DIVISION.
006900 WORKING-STORAGE SECTION.
011300
007000 01  WS-SQLCODE                     PIC -ZZZ9.
007000 01  WS-PROGRAM                     PIC X(08) VALUE 'DPMXHCMD'.
010400 01  WS-DASHES                      PIC X(80) VALUE ALL '='.
010700 01  WS-ERROR-AREA.
010800     05 WS-PARAGRAPH-NAME           PIC X(40).
       01  WS-TS                          PIC X(26).
       01  WS-CURRENT-DT                  PIC X(10).
       01  WS-DISPLAY-SWITCH              PIC X(01) VALUE 'N'.
           88 DISPLAY-PARAMETERS                    VALUE 'Y'.
           88 HIDE-PARAMETERS                       VALUE 'N'.

012100**SQL COMMUNICATIONS AREA PASSED BACK IN OUTSQLCA
012200     EXEC SQL
012300        INCLUDE SQLCA
012400     END-EXEC
012500
012600     EXEC SQL
012700        INCLUDE DPM0101
012800     END-EXEC
013300
      * INCLUDE FOR VDTM54_DEBUG_CNTRL                                  00024910
           EXEC SQL                                                     00024920
                INCLUDE DTM5401                                         00024930
           END-EXEC.                                                    00024940
015000**DB2 STANDARD COPYBOOK WITH FORMATTED DISPLAY SQLCA
015100**THIS MUST REMAIN AS THE LAST ENTRY IN WORKING STORAGE
015200 COPY  DB2000IA.
015300
016000 LINKAGE SECTION.
016100**PICTURE CLAUSE FOR OUTSQLCA - PIC X(179) - FOR LINKAGE SECTION
016200 COPY  DB2000IB.
016300
016400 01  LS-SP-ERROR-AREA               PIC X(80).
016500 01  LS-SP-RC                       PIC X(04).
019200
019300 PROCEDURE DIVISION USING  OUTSQLCA,
019400                           LS-SP-ERROR-AREA,
019500                           LS-SP-RC.
020900*----------*
021000 0000-MAIN.
021100*----------*

021200     PERFORM 1000-INITIALIZE
021300
           PERFORM 2000-OPEN-CMPNY-LIST-CSR

           IF DISPLAY-PARAMETERS
              PERFORM 9100-DISPLAY-DATA
           END-IF

024500     PERFORM 9990-GOBACK
024700
024600     .
024800*------------------------*
024900 1000-INITIALIZE.
025000*------------------------*
025100
025200     MOVE '1000-INITIALIZE'          TO WS-PARAGRAPH-NAME
025300     MOVE SPACES                     TO OUTSQLCA, LS-SP-ERROR-AREA
025300     MOVE 'SP00'                     TO LS-SP-RC

                                                                        00051700
           EXEC SQL                                                     00051800
                SELECT ACTVT_DSPLY_IN                                   00051900
                  INTO :D054-ACTVT-DSPLY-IN                             00052010
                FROM   VDTM54_DEBUG_CNTRL                               00052040
                WHERE PRGM_ID = :WS-PROGRAM                             00052050
           END-EXEC                                                     00052060
                                                                        00052070
           MOVE SQLCODE                     TO WS-SQLCODE               00052080
                                                                        00052090
           EVALUATE SQLCODE                                             00052091
              WHEN 0                                                    00052092
                  IF D054-ACTVT-DSPLY-IN = 'Y'                          00052094
                     SET DISPLAY-PARAMETERS TO TRUE                     00052095
                     EXEC SQL
                         SET :WS-TS = CURRENT TIMESTAMP
                            ,:WS-CURRENT-DT = CURRENT DATE
                     END-EXEC
                  END-IF                                                00052099
              WHEN 100                                                  00052092
                  CONTINUE
              WHEN OTHER                                                00052092
                  PERFORM 9000-SQL-ERROR
           END-EVALUATE                                                 00052102

           IF DISPLAY-PARAMETERS
              DISPLAY WS-DASHES
              DISPLAY 'DPMXHCMD STARTED AT      :' WS-TS
              DISPLAY WS-DASHES
           END-IF
030600     .
      *------------------------*
       2000-OPEN-CMPNY-LIST-CSR.
      *------------------------*

           MOVE '2000-OPEN-CMPNY-LIST-CSR'       TO WS-PARAGRAPH-NAME
           IF DISPLAY-PARAMETERS
              DISPLAY 'OPEN CMPNY_LIST_CSR CURSOR'
           END-IF
      *    THE MCAX REGISTERED COMPANIES LIST CURSOR WILL BE OPENED.

           EXEC SQL
              DECLARE CMPNY_LIST_CSR CURSOR WITH HOLD WITH RETURN FOR
                 SELECT
1                     DPM01.CMPNY_ID
2                    ,DPM01.CMPNY_TYPE_CD
3                    ,DPM01.CMPNY_GROUP_CD
4                    ,DPM01.CMPNY_NM
5                    ,DPM01.CMPNY_STAT_IN
6                    ,DPM01.CMPNY_PRMRY_CNTCT_NM
7                    ,DPM01.CMPNY_SCNDY_CNTCT_NM
8                    ,DPM01.CMPNY_PRMRY_PHONE_NB
9                    ,DPM01.CMPNY_SCNDY_PHONE_NB
10                   ,DPM01.CMPNY_PRMRY_EMAIL_ID
11                   ,DPM01.CMPNY_SCNDY_EMAIL_ID
12                   ,CHAR(DPM01.EFFV_START_DT)
13                   ,CHAR(DPM01.EFFV_END_DT)
                 FROM D0005       DPM01
                 WHERE CURRENT DATE >= DPM01.EFFV_START_DT
                   AND CURRENT DATE <= DPM01.EFFV_END_DT
                   AND DPM01.CMPNY_TYPE_CD IN ('D','C')
              ORDER BY DPM01.CMPNY_NM
              WITH UR
           END-EXEC

           EXEC SQL
              OPEN CMPNY_LIST_CSR
           END-EXEC

           EVALUATE SQLCODE
              WHEN 0
                 CONTINUE
              WHEN OTHER
                 PERFORM 9000-SQL-ERROR
           END-EVALUATE

           .
      *------------------------*
       9000-SQL-ERROR.
      *------------------------*

           PERFORM 9100-DISPLAY-DATA
           MOVE 'Database error has occurred. Please contact DTCC.'
                                            TO LS-SP-ERROR-AREA
           MOVE 'SP99'                      TO LS-SP-RC
           MOVE SQLCODE                     TO WS-SQLCODE
           DISPLAY 'SQLCODE                 :' WS-SQLCODE
           DISPLAY 'PARAGRAPH-NAME          :' WS-PARAGRAPH-NAME
           PERFORM 9990-GOBACK

           .
      *------------------------*
       9100-DISPLAY-DATA.
      *------------------------*

           IF DISPLAY-PARAMETERS
              DISPLAY WS-DASHES
              DISPLAY 'PARAGRAPH-NAME           :' WS-PARAGRAPH-NAME
              DISPLAY 'SP-ERROR-AREA            :' LS-SP-ERROR-AREA
              DISPLAY 'SP-RC                    :' LS-SP-RC
           END-IF


           .
      *------------------------*
       9990-GOBACK.
      *------------------------*

            PERFORM 9999-FORMAT-SQLCA
            IF DISPLAY-PARAMETERS
               DISPLAY WS-DASHES
               DISPLAY 'OUTSQLCA FOR DPMXHCMD    :' OUTSQLCA
               DISPLAY WS-DASHES
               EXEC SQL
                  SET :WS-TS = CURRENT TIMESTAMP
               END-EXEC
               DISPLAY 'DPMXHCMD ENDED AT        :' WS-TS
               DISPLAY WS-DASHES
            END-IF
            GOBACK
           .
      *------------------------*
       9999-FORMAT-SQLCA.
      *------------------------*
           PERFORM DB2000I-FORMAT-SQLCA
              THRU DB2000I-FORMAT-SQLCA-EXIT
           .
      **MOVE STATEMENTS TO FORMAT THE OUTSQLCA USING DB2000IA & DB2000IB
        COPY DB2000IC.
