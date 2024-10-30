000100 IDENTIFICATION DIVISION.
000200 PROGRAM-ID.   DPMXDDLD.
000300 AUTHOR.       COGNIZANT.
000400 DATE-WRITTEN. JANUARY 2008.
000500*
000600*
000700****************************************************************
000800*   THIS IS AN UNPUBLISHED PROGRAM OWNED BY ISCC               *
000900*   IN WHICH A COPYRIGHT SUBSISTS AS OF OCTOBER 2003.          *
001000****************************************************************
001100****************************************************************
001200*                   COMPILATION INSTRUCTION                    *
001300*                  COMPILE DB2 VS COBOL 370                    *
001400****************************************************************
001500*    **LNKCTL**
001600*    INCLUDE SYSLIB(DSNRLI)
001700*    MODE AMODE(31) RMODE(ANY)
001800*    ENTRY DPMXDDLD
001900*    NAME  DPMXDDLD(R)
002000*
002100******************************************************************
002200**         P R O G R A M   D O C U M E N T A T I O N            **
002300******************************************************************
002400* SYSTEM:    MCA XPRESS APPLICATION                              *
002500* PROGRAM:   DPMXDDLD                                            *
002600*                                                                *
002700* THIS STORED PROCEDURE IS CALLED TO RETREIVE A DOCUMENT FOR     *
002800* A PARTICULAR DOCUMENT ID.                                      *
003000******************************************************************
003100* TABLES:                                                        *
003200* -------                                                        *
003300* VDPM12_MCA_DOC        - DOCUMENT TABLE FOR MCA                 *
      *                                                                *
      * VDTM54_DEBUG_CNTRL    - DEBUG CONTROL TABLE                    *
      *                                                                *
003900*----------------------------------------------------------------*
004000* INCLUDES:                                                      *
004100* ---------                                                      *
004200* SQLCA    - DB2 COMMAREA                                        *
      * DPM1201  - DCLGEN FOR VDPM12_MCA_DOC
      * DTM5401  - DCLGEN FOR VDTM54_DEBUG_CNTRL
005000*----------------------------------------------------------------*
305100* COPYBOOK:                                                      *
005200* ---------                                                      *
005300* DB2000IA                                                       *
005400* DB2000IB                                                       *
005500* DB2000IC                                                       *
005600*----------------------------------------------------------------*
005700*              M A I N T E N A N C E   H I S T O R Y             *
005800*                                                                *
005900*                                                                *
006000* DATE CHANGED    VERSION     PROGRAMMER                         *
006100* ------------    -------     --------------------               *
006200*                                                                *
006300* 01/17/2008        01.0      COGNIZANT                          *
006400*                             INITIAL IMPLEMENTATION.            *
006200*                                                                *
006600******************************************************************
006700 ENVIRONMENT DIVISION.
006800 DATA DIVISION.
006900 WORKING-STORAGE SECTION.
011300
007000 01  WS-SQLCODE                     PIC -ZZZ9.
007000 01  WS-PROGRAM                     PIC X(08) VALUE 'DPMXDDLD'.
       01  WS-VARIABLES.
007600       05  WS-EMPTY-DOC-ID          PIC X(50) VALUE
007700           'Input document code is empty'.
             05  WS-EMPTY-DOC-TYPE-CD     PIC X(50) VALUE
                 'Input document type is empty'.
             05  WS-INVLD-DOC-TYPE-CD     PIC X(50) VALUE
                 'Input document type is invalid'.
             05  WS-DELETED-SEL-RCD        PIC X(50) VALUE
                 'Selected Document is already deleted'.
010400       05  WS-DASHES                PIC X(40) VALUE ALL '='.
             05  WS-DOC-TYPE-IND-SW       PIC X(01).
                 88  WS-DOC-TYPE-PRE-EXIST VALUE 'P'.
                 88  WS-DOC-TYPE-OTHER     VALUE 'O'.
                 88  WS-DOC-TYPE-IMAGE     VALUE 'I'.

       01  WS-TS                          PIC X(26).
       01  WS-DOC-ID                      PIC S9(18)V COMP-3.
       01  WS-DOC-VIEW-IN                 PIC X(01).
       01  WS-DISPLAY-SWITCH              PIC X(01) VALUE 'N'.
           88 DISPLAY-PARAMETERS                    VALUE 'Y'.
           88 HIDE-PARAMETERS                       VALUE 'N'.

010700 01  WS-ERROR-AREA.
010800     05 WS-PARAGRAPH-NAME           PIC X(40).

012100**SQL COMMUNICATIONS AREA PASSED BACK IN OUTSQLCA
012200     EXEC SQL
012300        INCLUDE SQLCA
012400     END-EXEC
012500
012600     EXEC SQL
012700        INCLUDE DPM1201
012800     END-EXEC

      * INCLUDE FOR VDTM54_DEBUG_CNTRL                                  00024910
           EXEC SQL                                                     00024920
                INCLUDE DTM5401                                         00024930
           END-EXEC.                                                    00024940
013300
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
038000 01  LS-IN-DOC-ID                   PIC S9(18)V COMP-3.
038000 01  LS-IN-SEL-CMP-ID               PIC X(08).
038000 01  LS-IN-DOC-TYPE-CD              PIC X(01).
019200
019300 PROCEDURE DIVISION USING  OUTSQLCA,
019400                           LS-SP-ERROR-AREA,
019500                           LS-SP-RC,
019600                           LS-IN-DOC-ID,
019600                           LS-IN-SEL-CMP-ID,
019600                           LS-IN-DOC-TYPE-CD.
020900*----------*
021000 0000-MAIN.
021100*----------*

021200     PERFORM 1000-INITIALIZE
021300
021400     PERFORM 2000-VALIDATE-INPUT

           IF  WS-DOC-TYPE-PRE-EXIST OR WS-DOC-TYPE-OTHER
               PERFORM 3000-OPEN-DOC-LIST-CSR
           ELSE
               PERFORM 4000-OPEN-IMG-LIST-CSR
           END-IF

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

      *    CONVERT THE INPUT VALUES INTO UPPER-CASE
           MOVE FUNCTION UPPER-CASE(LS-IN-DOC-TYPE-CD)
                                           TO LS-IN-DOC-TYPE-CD
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
              DISPLAY 'DPMXDDLD STARTED AT      :' WS-TS
              DISPLAY WS-DASHES
           END-IF

030600     .
030700*------------------------*
030800 2000-VALIDATE-INPUT.
030900*------------------------*
031100
031000     MOVE '2000-VALIDATE-INPUT'               TO WS-PARAGRAPH-NAME

           IF DISPLAY-PARAMETERS
              DISPLAY WS-PARAGRAPH-NAME
           END-IF

040600
           IF LS-IN-DOC-TYPE-CD   > SPACES
              MOVE LS-IN-DOC-TYPE-CD       TO  WS-DOC-TYPE-IND-SW
              IF  WS-DOC-TYPE-PRE-EXIST OR WS-DOC-TYPE-OTHER
                   OR WS-DOC-TYPE-IMAGE
                  CONTINUE
              ELSE
                  MOVE WS-INVLD-DOC-TYPE-CD TO LS-SP-ERROR-AREA
                  MOVE 'SP50'               TO LS-SP-RC
                  PERFORM 9990-GOBACK
              END-IF
           ELSE
               MOVE WS-EMPTY-DOC-TYPE-CD    TO LS-SP-ERROR-AREA
               MOVE 'SP50'                  TO LS-SP-RC
               PERFORM 9990-GOBACK
           END-IF
040600
           IF WS-DOC-TYPE-PRE-EXIST OR WS-DOC-TYPE-OTHER
031200        IF LS-IN-DOC-ID = ZEROES
 31500           MOVE WS-EMPTY-DOC-ID               TO LS-SP-ERROR-AREA
031600           MOVE 'SP50'                        TO LS-SP-RC
031700           PERFORM 9100-DISPLAY-DATA
031800           PERFORM 9990-GOBACK
031900        END-IF
031900     END-IF
031100
041700     .
054400*------------------------*
054500 3000-OPEN-DOC-LIST-CSR.
054501*------------------------*
054527
054502     MOVE '3000-OPEN-DOC-LIST-CSR'            TO WS-PARAGRAPH-NAME

           IF DISPLAY-PARAMETERS
              DISPLAY WS-PARAGRAPH-NAME
           END-IF

           PERFORM 3100-VAL-DOC-ID

      *    THE DOCUMENT CURSOR WILL BE OPENED.

054528     EXEC SQL
054529        DECLARE DOC_LIST_CSR CURSOR WITH HOLD WITH RETURN FOR
                 SELECT
 1                    MCA_VALUE_ID AS DOC_ID
 2                   ,MCA_DOC_DS   AS DOC_NAME
 3                   ,MCA_DOC_TYPE_CD AS DOC_TYPE_CD
 4                   ,MCA_DOC_OBJ_TX AS DOCUMENT
                     FROM VDPM12_MCA_DOC
              WHERE  MCA_VALUE_ID    = :LS-IN-DOC-ID
              AND    DOC_DEL_CD      = ' '
              WITH UR
064804     END-EXEC

064806     EXEC SQL
064807        OPEN DOC_LIST_CSR
064808     END-EXEC
054505
054506     EVALUATE SQLCODE
054507        WHEN 0
054508           CONTINUE
054509        WHEN OTHER
                 PERFORM 9000-SQL-ERROR
054521     END-EVALUATE

054522     .
      *---------------------*
       3100-VAL-DOC-ID.
      *---------------------*

           MOVE '3100-VAL-DOC-ID'        TO WS-PARAGRAPH-NAME

           IF DISPLAY-PARAMETERS
              DISPLAY WS-PARAGRAPH-NAME
           END-IF


           EXEC SQL

              SELECT  DPM09.MCA_VALUE_ID ,
                      DPM09.MCA_DOC_VIEW_IN
                   INTO :WS-DOC-ID , :WS-DOC-VIEW-IN
                FROM  D0004  DPM09
                WHERE MCA_VALUE_ID     = :LS-IN-DOC-ID
                AND   CMPNY_ID         = :LS-IN-SEL-CMP-ID
                WITH UR
           END-EXEC

           EVALUATE SQLCODE
              WHEN 0
                 IF WS-DOC-VIEW-IN = 'D'
                    MOVE WS-DELETED-SEL-RCD
                                         TO LS-SP-ERROR-AREA
                    MOVE 'SP01'          TO LS-SP-RC
                    PERFORM 9990-GOBACK
                 END-IF
              WHEN 100
                 MOVE WS-DELETED-SEL-RCD TO LS-SP-ERROR-AREA
                 MOVE 'SP01'              TO LS-SP-RC
                 PERFORM 9990-GOBACK
              WHEN OTHER
                 PERFORM 9000-SQL-ERROR
           END-EVALUATE
           .

054400*------------------------*
054500 4000-OPEN-IMG-LIST-CSR.
054501*------------------------*
054527
054502     MOVE '4000-OPEN-IMG-LIST-CSR'            TO WS-PARAGRAPH-NAME

           IF DISPLAY-PARAMETERS
              DISPLAY WS-PARAGRAPH-NAME
           END-IF

      *    THE IMAGE CURSOR WILL BE OPENED.

054528     EXEC SQL
054529        DECLARE IMG_LIST_CSR CURSOR WITH HOLD WITH RETURN FOR
054530           SELECT
 1                    MCA_VALUE_ID AS DOC_ID
 2                   ,MCA_DOC_DS   AS DOC_NAME
 3                   ,MCA_DOC_TYPE_CD AS DOC_TYPE_CD
 4                   ,MCA_DOC_OBJ_TX AS DOCUMENT
                     FROM VDPM12_MCA_DOC
060300        WHERE MCA_DOC_TYPE_CD = :LS-IN-DOC-TYPE-CD
              WITH UR
064804     END-EXEC

064806     EXEC SQL
064807        OPEN IMG_LIST_CSR
064808     END-EXEC
054505
054506     EVALUATE SQLCODE
054507        WHEN 0
054508           CONTINUE
054509        WHEN OTHER
                 PERFORM 9000-SQL-ERROR
054521     END-EVALUATE

054522     .
101300*------------------------*
101400 9000-SQL-ERROR.
101500*------------------------*
101600
101800     PERFORM 9100-DISPLAY-DATA
087800     MOVE 'Database error has occurred. Please contact DTCC.'
087800                                      TO LS-SP-ERROR-AREA
           MOVE 'SP99'                      TO LS-SP-RC
           MOVE SQLCODE                     TO WS-SQLCODE
           DISPLAY 'PARAGRAPH-NAME           :' WS-PARAGRAPH-NAME
102700     DISPLAY 'SQLCODE                 :' WS-SQLCODE
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
102900         DISPLAY 'LS-IN-DOC-ID             :' LS-IN-DOC-ID
102900         DISPLAY 'LS-IN-SEL-CMP-ID     :' LS-IN-SEL-CMP-ID
102900         DISPLAY 'LS-IN-DOC-TYPE-CD        :' LS-IN-DOC-TYPE-CD
102600         DISPLAY WS-DASHES
106900     END-IF
106800
107000     .
107100*------------------------*
107200 9990-GOBACK.
107300*------------------------*

101900      PERFORM 9999-FORMAT-SQLCA
            IF DISPLAY-PARAMETERS
               DISPLAY WS-DASHES
               DISPLAY 'OUTSQLCA FOR DPMXDDLD    :' OUTSQLCA
               DISPLAY WS-DASHES
               EXEC SQL
                   SET :WS-TS = CURRENT TIMESTAMP
               END-EXEC
               DISPLAY 'DPMXDDLD ENDED AT        :' WS-TS
               DISPLAY WS-DASHES
            END-IF
107400      GOBACK
107500     .
107600*------------------------*
107700 9999-FORMAT-SQLCA.
107800*------------------------*
107900     PERFORM DB2000I-FORMAT-SQLCA
108000        THRU DB2000I-FORMAT-SQLCA-EXIT
108100     .
108200**MOVE STATEMENTS TO FORMAT THE OUTSQLCA USING DB2000IA & DB2000IB
108300  COPY DB2000IC.
