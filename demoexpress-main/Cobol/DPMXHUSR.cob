000100 IDENTIFICATION DIVISION.
000200 PROGRAM-ID.   DPMXHUSR.
000300 AUTHOR.       COGNIZANT.
000400 DATE-WRITTEN. AUGUST 2007.
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
001800*    ENTRY DPMXHUSR
001900*    NAME  DPMXHUSR(R)
002000*
002100******************************************************************
002200**         P R O G R A M   D O C U M E N T A T I O N            **
002300******************************************************************
002400* SYSTEM:    MCA XPRESS APPLICATION                              *
002500* PROGRAM:   DPMXHUSR                                            *
002600*                                                                *
002700* THIS STORED PROCEDURE IS CALLED WHEN A USER LOGS INTO MCA      *
002800* XPRESS APPLICATION. IT QUERIES FOR THE USER INFO IN THE TABLE  *
002900* D0003.                                             *
002600*                                                                *
002900* IF USER INFO IS ALREADY PRESENT IN THE TABLE, THEN COMPARES THE*
002900* TABLE FIELDS WITH THE INPUT PARAMETERS. FOR ANY CHANGE IN THE  *
002900* FIELD, UPDATES THE TABLE WITH THE INPUT PARAMETER FOR THAT USER*
002600*                                                                *
002900* ELSE INSERTS A NEW RECORD IN THE TABLE FOR THAT USER.          *
003000******************************************************************
003100* TABLES:                                                        *
003200* -------                                                        *
003300* D0005   - COMPANY TABLE FOR MCA
003300* D0003  - COMPANY USER TABLE FOR MCA
      * VDTM54_DEBUG_CNTRL - DEBUG CONTROL TABLE
003900*----------------------------------------------------------------*
004000* INCLUDES:                                                      *
004100* ---------                                                      *
004200* SQLCA              - DB2 COMMAREA                              *
004900* DPM0101            - COMPANY TABLE FOR MCA                     *
004900* DPM0301            - COMPANY USER TABLE FOR MCA                *
004900* DTM5401            - VDTM54_DEBUG_CNTRL                *
005000*----------------------------------------------------------------*
005100* COPYBOOK:                                                      *
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
006300* 09/02/2007        001       COGNIZANT                          *
006400*                             INITIAL IMPLEMENTATION FOR         *
006200*                             MCA XPRESS.                        *
006600******************************************************************
006700 ENVIRONMENT DIVISION.
006800 DATA DIVISION.
006900 WORKING-STORAGE SECTION.
007000 01  WS-SQLCODE                     PIC -ZZ9.
007000 01  WS-PROGRAM                     PIC X(08) VALUE 'DPMXHUSR'.
007600 01  WS-INVLD-CMPNY-ID              PIC X(50) VALUE
007700     'Invalid Company ID'.
007600 01  WS-EMPTY-CMPNY-ID              PIC X(50) VALUE
007700     'Company Id is empty'.
007800 01  WS-EMPTY-USER-ID               PIC X(50) VALUE
007900     'UMG User Id is empty'.
007800 01  WS-EMPTY-EMAIL-ID              PIC X(50) VALUE
007900     'User Email Id is empty'.
007600 01  WS-EMPTY-USER-NAME             PIC X(50) VALUE
007700     'User Name is empty'.
010400 01  WS-DASHES                      PIC X(80) VALUE ALL '='.
010400 01  WS-USER-EMAIL                  PIC X(100) VALUE SPACES.
010400 01  DPM03-USER-EMAIL               PIC X(100) VALUE SPACES.
010700 01  WS-ERROR-AREA.
010800     05  WS-PARAGRAPH-NAME          PIC X(40).
019100 01  WS-CMPNY-ID-FLAG               PIC X(01) VALUE 'N'.
019100 01  WS-UPDATE-FLAG-SW              PIC X(01) VALUE 'N'.
           88 WS-UPDATE-REQD              VALUE 'Y'.
           88 WS-UPDATE-REQD-NO           VALUE 'N'.
       01  WS-TS                          PIC X(26).
       01  WS-USER.
           05 WS-USER-ID-SEQ              PIC S9(9) COMP.
           05 WS-USER-ID-SEQ-PIC          PIC 9(9).
           05 FILLER REDEFINES WS-USER-ID-SEQ-PIC.
              10 WS-USER-ID-SEQ-CHAR      PIC X(9).
       01  WS-DISPLAY-SWITCH              PIC X(01) VALUE 'N'.
           88 DISPLAY-PARAMETERS                    VALUE 'Y'.
           88 HIDE-PARAMETERS                       VALUE 'N'.
011300
012100**SQL COMMUNICATIONS AREA PASSED BACK IN OUTSQLCA
012200     EXEC SQL
012300        INCLUDE SQLCA
012400     END-EXEC
012500
012600     EXEC SQL
012700        INCLUDE DPM0101
012800     END-EXEC
012500
012600     EXEC SQL
012700        INCLUDE DPM0301
012800     END-EXEC
012900
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
016600 01  LS-CMPNY-ID                    PIC X(08).
016600 01  LS-USER-ID                     PIC X(10).
016700 01  LS-USER-NAME                   PIC X(200).
016700 01  LS-USER-EMAIL                  PIC X(100).
016700 01  LS-UMG-USER-ID                 PIC X(50).
016700 01  LS-USER-PHONE-NB               PIC X(20).
019200
019300 PROCEDURE DIVISION USING  OUTSQLCA,
019400                           LS-SP-ERROR-AREA,
019500                           LS-SP-RC,
019600                           LS-CMPNY-ID,
019700                           LS-USER-ID,
019800                           LS-USER-NAME,
019900                           LS-USER-EMAIL,
019900                           LS-UMG-USER-ID,
019900                           LS-USER-PHONE-NB.
020900*----------*
021000 0000-MAIN.
021100*----------*
           DISPLAY 'LS-CMPNY-ID:' LS-CMPNY-ID
021200     PERFORM 1000-INITIALIZE.
021300
021400     PERFORM 2000-VALIDATE-INPUT.

021400     PERFORM 3000-GET-USER-INFO.

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
025300     MOVE FUNCTION UPPER-CASE(LS-USER-EMAIL)
                                           TO WS-USER-EMAIL
                                                                        00051700
           EXEC SQL                                                     00051800
                SELECT ACTVT_DSPLY_IN                                   00051900
                  INTO :D054-ACTVT-DSPLY-IN                             00052010
                FROM   VDTM54_DEBUG_CNTRL                               00052040
                WHERE PRGM_ID = :WS-PROGRAM                             00052050
                WITH UR
           END-EXEC                                                     00052060
                                                                        00052070
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
              DISPLAY 'DPMXHUSR STARTED AT      :' WS-TS
              DISPLAY WS-DASHES
           END-IF

030600     .
030700*------------------------*
030800 2000-VALIDATE-INPUT.
030900*------------------------*
031100
031000     MOVE '2000-VALIDATE-INPUT'               TO WS-PARAGRAPH-NAME
031100
031200     IF LS-CMPNY-ID > SPACES
031300        PERFORM 2100-VALIDATE-CMPNY-ID
031400     ELSE
031500        MOVE WS-EMPTY-CMPNY-ID                TO LS-SP-ERROR-AREA
031600        MOVE 'SP50'                           TO LS-SP-RC
031700        PERFORM 9100-DISPLAY-DATA
031800        PERFORM 9990-GOBACK
031900     END-IF
032000
032100     IF LS-UMG-USER-ID = SPACES
032300        MOVE WS-EMPTY-USER-ID                 TO LS-SP-ERROR-AREA
032400        MOVE 'SP50'                           TO LS-SP-RC
032500        PERFORM 9100-DISPLAY-DATA
032600        PERFORM 9990-GOBACK
032700     END-IF
032800
032900     IF LS-USER-NAME = SPACES
032300        MOVE WS-EMPTY-USER-NAME               TO LS-SP-ERROR-AREA
032400        MOVE 'SP50'                           TO LS-SP-RC
032500        PERFORM 9100-DISPLAY-DATA
032600        PERFORM 9990-GOBACK
           END-IF
032900
032900     IF LS-USER-EMAIL = SPACES
032300        MOVE WS-EMPTY-EMAIL-ID                TO LS-SP-ERROR-AREA
032400        MOVE 'SP50'                           TO LS-SP-RC
032500        PERFORM 9100-DISPLAY-DATA
032600        PERFORM 9990-GOBACK
033100     END-IF
033200
041700     .
041900*----------------------------*
042000 2100-VALIDATE-CMPNY-ID.
042100*----------------------------*
042200
031000     MOVE '2100-VALIDATE-CMPNY-ID'          TO WS-PARAGRAPH-NAME

045200     EXEC SQL
045300        SELECT 'Y' INTO :WS-CMPNY-ID-FLAG
045400          FROM D0005
045500          WHERE CMPNY_ID = :LS-CMPNY-ID
                WITH UR
045600     END-EXEC
045700
045800     EVALUATE SQLCODE
046000        WHEN ZEROES
                 CONTINUE
046000        WHEN +100
046200           MOVE WS-INVLD-CMPNY-ID             TO LS-SP-ERROR-AREA
046300           MOVE 'SP50'                        TO LS-SP-RC
046400           PERFORM 9990-GOBACK
046700        WHEN OTHER
046800           PERFORM 9000-SQL-ERROR
047000     END-EVALUATE
044400     .
101300*------------------------*
101400 3000-GET-USER-INFO.
101500*------------------------*
101600*   QUERY THE USER TABLE TO FIND THE USER INFO.
101300*   IF EXISTS, THEN COMPARE THE INPUT PARAMETERS WITH DATABASE
      *      AND UPDATE THE DATABASE FOR ANY CHANGE IN USER INFO.
      *   ELSE INSERT A RECORD FOR THE USER WITH THE SEQ GENERATED
      *      USER ID.

031000     MOVE '3000-GET-USER-INFO'              TO WS-PARAGRAPH-NAME

045200     EXEC SQL
045300        SELECT CMPNY_ID
                    ,CMPNY_USER_ID
                    ,CMPNY_USER_NM
                    ,CMPNY_USER_EMAIL_ID
                    ,CMPNY_USER_PHONE_NB
045300          INTO
045300               :D003-CMPNY-ID
                    ,:D003-CMPNY-USER-ID
                    ,:D003-CMPNY-USER-NM
                    ,:D003-CMPNY-USER-EMAIL-ID
                    ,:D003-CMPNY-USER-PHONE-NB
045400          FROM D0003
045500          WHERE CMPNY_ID    = :LS-CMPNY-ID
                  AND UMG_USER_ID = :LS-UMG-USER-ID
                WITH UR
045600     END-EXEC

           MOVE SQLCODE TO WS-SQLCODE
           DISPLAY 'SQLCODE 3000-:' WS-SQLCODE

045800     EVALUATE SQLCODE

046000        WHEN ZEROES
                 IF D003-CMPNY-USER-NM NOT = LS-USER-NAME
                    MOVE LS-USER-NAME             TO D003-CMPNY-USER-NM
                    SET WS-UPDATE-REQD            TO TRUE
                 END-IF

                 MOVE FUNCTION UPPER-CASE(D003-CMPNY-USER-EMAIL-ID)
                                                  TO DPM03-USER-EMAIL
                 IF DPM03-USER-EMAIL NOT = WS-USER-EMAIL
                    MOVE LS-USER-EMAIL            TO
                                               D003-CMPNY-USER-EMAIL-ID
                    SET WS-UPDATE-REQD            TO TRUE
                 END-IF

                 IF D003-CMPNY-USER-PHONE-NB NOT = LS-USER-PHONE-NB
                    MOVE LS-USER-PHONE-NB         TO
                                               D003-CMPNY-USER-PHONE-NB
                    SET WS-UPDATE-REQD            TO TRUE
                 END-IF

                 IF WS-UPDATE-REQD
                    PERFORM 3100-UPDATE-USER-TABLE
                 END-IF
                 MOVE D003-CMPNY-USER-ID          TO LS-USER-ID
046000        WHEN +100
046100           PERFORM 3200-INSERT-USER-TABLE
                 MOVE D003-CMPNY-USER-ID          TO LS-USER-ID
046700        WHEN OTHER
046800           PERFORM 9000-SQL-ERROR
047000     END-EVALUATE

           .
101300*------------------------*
101400 3100-UPDATE-USER-TABLE.
101500*------------------------*
101600
           MOVE '3100-UPDATE-USER-TABLE'    TO WS-PARAGRAPH-NAME

           EXEC SQL
              UPDATE D0003
                 SET CMPNY_USER_NM       = :D003-CMPNY-USER-NM
                    ,CMPNY_USER_EMAIL_ID = :D003-CMPNY-USER-EMAIL-ID
                    ,ROW_UPDT_TS         = CURRENT TIMESTAMP
                    ,CMPNY_USER_PHONE_NB = :D003-CMPNY-USER-PHONE-NB
                 WHERE CMPNY_ID          = :D003-CMPNY-ID
                   AND CMPNY_USER_ID     = :D003-CMPNY-USER-ID
           END-EXEC

045800     EVALUATE SQLCODE
046000        WHEN ZEROES
                 CONTINUE
046700        WHEN OTHER
046800           PERFORM 9000-SQL-ERROR
047000     END-EVALUATE

           .
101300*------------------------*
101400 3200-INSERT-USER-TABLE.
101500*------------------------*
101600
           MOVE '3200-INSERT-USER-TABLE'    TO WS-PARAGRAPH-NAME
           PERFORM 3300-CREATE-SEQ-USER-ID
           MOVE WS-USER-ID-SEQ-CHAR         TO D003-CMPNY-USER-ID
           MOVE LS-CMPNY-ID                 TO D003-CMPNY-ID
           MOVE LS-USER-NAME                TO D003-CMPNY-USER-NM
           MOVE LS-USER-EMAIL               TO D003-CMPNY-USER-EMAIL-ID
           MOVE LS-UMG-USER-ID              TO D003-UMG-USER-ID
           MOVE LS-USER-PHONE-NB            TO D003-CMPNY-USER-PHONE-NB

           EXEC SQL
              INSERT INTO D0003
                                (CMPNY_ID
                                ,CMPNY_USER_ID
                                ,CMPNY_USER_NM
                                ,CMPNY_USER_EMAIL_ID
                                ,ROW_UPDT_TS
                                ,UMG_USER_ID
                                ,CMPNY_USER_PHONE_NB)
                          VALUES(:D003-CMPNY-ID
                                ,:D003-CMPNY-USER-ID
                                ,:D003-CMPNY-USER-NM
                                ,:D003-CMPNY-USER-EMAIL-ID
                                ,CURRENT TIMESTAMP
                                ,:D003-UMG-USER-ID
                                ,:D003-CMPNY-USER-PHONE-NB)
           END-EXEC

045800     EVALUATE SQLCODE
045900
046000        WHEN ZEROES
                 CONTINUE
046700        WHEN OTHER
046800           PERFORM 9000-SQL-ERROR
046900
047000     END-EVALUATE

           .
101300*------------------------*
101400 3300-CREATE-SEQ-USER-ID.
101500*------------------------*
      *    CREATE USER ID USING SEQUENCE OBJECT DPM.SQDPM021

044800     MOVE '3300-CREATE-SEQ-USER-ID'   TO WS-PARAGRAPH-NAME

           EXEC SQL
               SET :WS-USER-ID-SEQ = (NEXT VALUE FOR DPM.SQDPM021)
           END-EXEC
045700
045800     EVALUATE SQLCODE
046000        WHEN ZEROES
                 MOVE WS-USER-ID-SEQ        TO WS-USER-ID-SEQ-PIC
046700        WHEN +100
046800           CONTINUE
046700        WHEN OTHER
046800           PERFORM 9000-SQL-ERROR
047000     END-EVALUATE
045700
047100     .
101300*------------------------*
101400 9000-SQL-ERROR.
101500*------------------------*
101600
101800     PERFORM 9100-DISPLAY-DATA
087800     MOVE 'Database error has occurred. Please contact DTCC.'
087800                                      TO LS-SP-ERROR-AREA
           MOVE SQLCODE                     TO WS-SQLCODE
           MOVE 'SP99'                      TO LS-SP-RC
102700     DISPLAY 'SQLCODE                 :' WS-SQLCODE
           DISPLAY 'PARAGRAPH-NAME          :' WS-PARAGRAPH-NAME
101900     PERFORM 9999-FORMAT-SQLCA
102000     PERFORM 9990-GOBACK
102100     .
102200*------------------------*
102300 9100-DISPLAY-DATA.
102400*------------------------*
102500
           IF DISPLAY-PARAMETERS
102600        DISPLAY WS-DASHES
102700        DISPLAY 'PARAGRAPH-NAME           :' WS-PARAGRAPH-NAME
102800        DISPLAY 'SP-ERROR-AREA            :' LS-SP-ERROR-AREA
102900        DISPLAY 'SP-RC                    :' LS-SP-RC
103400        DISPLAY 'COMPANY ID               :' LS-CMPNY-ID
103700        DISPLAY 'USER ID                  :' LS-USER-ID
104000        DISPLAY 'USER NAME                :' LS-USER-NAME
104300        DISPLAY 'USER EMAIL ID            :' LS-USER-EMAIL
104300        DISPLAY 'UMG  USER  ID            :' LS-UMG-USER-ID
104300        DISPLAY 'USER PHONE NB            :' LS-USER-PHONE-NB
              DISPLAY 'WS-USER-ID-SEQ           :' WS-USER-ID-SEQ
              DISPLAY 'WS-USER-ID-SEQ-CHAR      :' WS-USER-ID-SEQ-CHAR
              DISPLAY 'GENERATED USER ID        :' D003-CMPNY-USER-ID
           END-IF
106800
107000     .
107100*------------------------*
107200 9990-GOBACK.
107300*------------------------*

101900      PERFORM 9999-FORMAT-SQLCA
            IF DISPLAY-PARAMETERS
               DISPLAY WS-DASHES
               DISPLAY 'OUTSQLCA FOR DPMXHUSR    :' OUTSQLCA
               DISPLAY WS-DASHES
               EXEC SQL
                   SET :WS-TS = CURRENT TIMESTAMP
               END-EXEC
               DISPLAY 'DPMXHUSR ENDED AT        :' WS-TS
               DISPLAY WS-DASHES
            END-IF
107400      GOBACK
107500     .
107600*------------------------*
107700 9999-FORMAT-SQLCA.
107800*------------------------*
107900     PERFORM DB2000I-FORMAT-SQLCA
108000        THRU DB2000I-FORMAT-SQLCA-EXIT
108100        .
108200**MOVE STATEMENTS TO FORMAT THE OUTSQLCA USING DB2000IA & DB2000IB
108300  COPY DB2000IC.
