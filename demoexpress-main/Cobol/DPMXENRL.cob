000100 IDENTIFICATION DIVISION.
000200 PROGRAM-ID.   DPMXENRL.
000300 AUTHOR.       COGNIZANT.
000400 DATE-WRITTEN. SEPTEMBER 2007.
000500*
000600*
000700****************************************************************
000800*   THIS IS AN UNPUBLISHED PROGRAM OWNED BY ISCC               *
000900*   IN WHICH A COPYRIGHT SUBSISTS AS OF JULY 2006.             *
001000****************************************************************
001100****************************************************************
001200*                   COMPILATION INSTRUCTION                    *
001300*                  COMPILE DB2 VS COBOL 370                    *
001400****************************************************************
001500*    **LNKCTL**
001600*    INCLUDE SYSLIB(DSNRLI)
001700*    MODE AMODE(31) RMODE(ANY)
001800*    ENTRY DPMXENRL
001900*    NAME  DPMXENRL(R)
002000*
002100******************************************************************
002200**         P R O G R A M   D O C U M E N T A T I O N            **
002300******************************************************************
002400* SYSTEM:    MCA XPRESS APPLICATION                              *
002500* PROGRAM:   DPMXENRL                                            *
002600*                                                                *
002700* THIS STORED PROCEDURE INSERTS/UPDATES/DELETES THE MCA ENROLL   *
002700* TABLE BASED ON THE CLIENT/DEALER ENROLLMENT ACTION.            *
002700*                                                                *
002700* THIS WILL BE USED WHEN CLIENT ENROLLS A NEW DEALER OR          *
002700* RE-ENROLLS A DENIED DEALER OR WHEN DEALER APPROVES OR DENIES   *
002800* ENROLLMENT.                                                    *
002900*                                                                *
002900* CASES:                                                         *
002900* ------                                                         *
002900*    1) WHEN A CLIENT ENROLS FOR MCA, A RECORD IS INSERTED INTO  *
002900*       DPM06_MCA_ENRL WITH THE INPUT DELR_STAT_CD 'P'.          *
002900*       THE TRIGGER WILL INSERT THE COPY INTO DPM06AMCA_ENRL.    *
002900*                                                                *
0 2900*    2) WHEN A DEALER APPROVES FOR MCA, THE RECORD IN ENROL TABLE*
002900*       WITH 'P' STATUS WILL BE UPDATED WITH DELR_STAT_CD 'A'.   *
002900*       THE TRIGGER WILL INSERT THE COPY INTO DPM06AMCA_ENRL.    *
002900*                                                                *
002900*    3) WHEN A DEALER DENIES FOR MCA, THE RECORD IN ENROL TABLE  *
002900*       WITH 'P' STATUS WILL BE UPDATED WITH DELR_STAT_CD 'D'.   *
002900*       THE TRIGGER WILL INSERT THE COPY INTO DPM06AMCA_ENRL.    *
002900*       THEN IT DELETES THAT 'D' STATUS RECORD PHYSICALLY FROM   *
002900*       DPM06_MCA_ENRL.SO THE CLIENT CAN ENROLL AGAIN WITH DEALER*
003000******************************************************************
003100* TABLES:                                                        *
003200* -------                                                        *
003400* D0006   - TEMPLATE      TABLE FOR MCA               *
003400* VDPM06_MCA_ENRL    - ENROLLMENT    TABLE FOR MCA               *
003400* D0003  - COMPANY USER  TABLE FOR MCA               *
003400* D0005   - COMPANY       TABLE FOR MCA               *
      * VDTM54_DEBUG_CNTRL - DEBUG CONTROL TABLE                       *
003900*----------------------------------------------------------------*
004000* INCLUDES:                                                      *
004100* ---------                                                      *
004200* SQLCA    - DB2 COMMAREA                                        *
004900* DPM0101  - COMPANY      TABLE FOR MCA                          *
004900* DPM0301  - COMPANY USER TABLE FOR MCA                          *
004900* DPM0601  - ENROLLMENT   TABLE FOR MCA                          *
004900* DPM1401  - ENROLLMENT   TABLE FOR MCA                          *
004900* DPM5401  - DEBUG CNTRL  TABLE FOR MCA                          *
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
006300* 09/10/2007        001       COGNIZANT                          *
006400*                             INITIAL IMPLEMENTATION.            *
006200*                                                                *
006600******************************************************************
006700 ENVIRONMENT DIVISION.
006800 DATA DIVISION.
006900 WORKING-STORAGE SECTION.
007000 01  WS-SQLCODE                     PIC 9(08) VALUE 0.
010400 01  WS-DASHES                      PIC X(40) VALUE ALL '='.
007000 01  WS-PROGRAM                     PIC X(08) VALUE 'DPMXENRL'.
007600 01  WS-INVLD-TMPLT-ID              PIC X(50) VALUE
007700     'Invalid Template id'.
007800 01  WS-INVLD-CLNT-CMPNY-ID         PIC X(50) VALUE
007900     'Invalid Client Company id'.
008000 01  WS-INVLD-USER-ID               PIC X(50) VALUE
008100     'Invalid User id'.
008000 01  WS-EMPTY-USER-ID               PIC X(50) VALUE
008100     'User id is empty'.
008200 01  WS-INVLD-DELR-CMPNY-ID         PIC X(50) VALUE
008300     'Invalid Dealer Company id'.
007600 01  WS-EMPTY-TMPLT-ID              PIC X(50) VALUE
007700     'Template id is empty'.
007800 01  WS-EMPTY-CLNT-CMPNY-ID         PIC X(50) VALUE
007900     'Client Company id is empty'.
008200 01  WS-EMPTY-DELR-CMPNY-ID         PIC X(50) VALUE
008300     'Dealer Company id is empty'.
008200 01  WS-EMPTY-DELR-STAT-CD          PIC X(50) VALUE
008300     'Dealer Status code is empty'.
008200 01  WS-ENRL-PENDING-AL             PIC X(50) VALUE
008300     'Enrollment is already Pending'.
008200 01  WS-ENRL-NOT-FND                PIC X(50) VALUE
008300     'This Enrollment is already Denied'.
008200 01  WS-ENRL-APPROVED-AL            PIC X(50) VALUE
008300     'Enrollment is already Approved'.
010700 01  WS-ERROR-AREA.
010800     05 WS-PARAGRAPH-NAME           PIC X(40).
       01  WS-CLNT-STAT-IN                PIC X(1)  VALUE ' '.
       01  WS-TEMPLATE-ID-FLAG            PIC X(1)  VALUE 'N'.
019100 01  WS-DELR-CMPNY-FLAG             PIC X(1)  VALUE 'N'.
019100 01  WS-USER-FLAG                   PIC X(1)  VALUE 'N'.
019100 01  WS-USER-ID                     PIC X(10) VALUE ' '.
019100 01  WS-CURRENT-TS                  PIC X(26) VALUE SPACES.
019100 01  WS-ENRL-ACTION-SW              PIC X(1).
           88 APPROVE-ENRL-REQ            VALUE 'A'.
           88 DENY-ENRL-REQ               VALUE 'D'.
           88 CREATE-ENRL-REQ             VALUE 'P'.
       01  WS-TS                          PIC X(26).
       01  WS-DISPLAY-SWITCH              PIC X(01)  VALUE 'N'.
           88 DISPLAY-PARAMETERS                     VALUE 'Y'.
           88 HIDE-PARAMETERS                        VALUE 'N'.
011300
012100**SQL COMMUNICATIONS AREA PASSED BACK IN OUTSQLCA
012200     EXEC SQL
012300        INCLUDE SQLCA
012400     END-EXEC
014500
014600     EXEC SQL
014700        INCLUDE DPM0601
014800     END-EXEC
014900
014600     EXEC SQL
014700        INCLUDE DPM1401
014800     END-EXEC
014900
014600     EXEC SQL
014700        INCLUDE DPM0301
014800     END-EXEC
014900
014600     EXEC SQL
014700        INCLUDE DPM0101
014800     END-EXEC
014900
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
016600 01  LS-MCA-TMPLT-ID                PIC S9(9) COMP.
016700 01  LS-CLNT-CMPNY-ID               PIC X(08).
016700 01  LS-DELR-CMPNY-ID               PIC X(08).
016700 01  LS-DELR-STAT-CD                PIC X(1).
016800 01  LS-USER-ID                     PIC X(10).
016800 01  LS-ENRL-TS                     PIC X(26).
019200
019300 PROCEDURE DIVISION USING  OUTSQLCA,
019400                           LS-SP-ERROR-AREA,
019500                           LS-SP-RC,
019600                           LS-MCA-TMPLT-ID,
019700                           LS-CLNT-CMPNY-ID,
019800                           LS-DELR-CMPNY-ID,
                                 LS-DELR-STAT-CD,
                                 LS-USER-ID,
                                 LS-ENRL-TS.

020900*----------*
021000 0000-MAIN.
021100*----------*

021200     PERFORM 1000-INITIALIZE.
021300
021400     PERFORM 2000-VALIDATE-INPUT

021400     PERFORM 3000-PROCESS-ENRL-REQ

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
025400*    CONVERT ALL THE INPUT LS-DELR-STAT-CD INTO UPPER-CASE
027100     MOVE FUNCTION UPPER-CASE(LS-USER-ID)
027200                                     TO WS-USER-ID
027100     MOVE FUNCTION UPPER-CASE(LS-DELR-STAT-CD)
027200                                     TO WS-ENRL-ACTION-SW
                                              LS-DELR-STAT-CD
           EXEC SQL
              SET :WS-CURRENT-TS = CURRENT TIMESTAMP
           END-EXEC

           EXEC SQL                                                     00051800
                SELECT ACTVT_DSPLY_IN                                   00051900
                  INTO :D054-ACTVT-DSPLY-IN                             00052010
                FROM VDTM54_DEBUG_CNTRL                                 00052040
                WHERE PRGM_ID = :WS-PROGRAM                             00052050
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
              DISPLAY 'DPMXENRL STARTED AT      :' WS-CURRENT-TS
              DISPLAY WS-DASHES
           END-IF

           .
030700*------------------------*
030800 2000-VALIDATE-INPUT.
030900*------------------------*
031100
031000     MOVE '2000-VALIDATE-INPUT'      TO WS-PARAGRAPH-NAME
031100
031200     IF LS-MCA-TMPLT-ID = ZEROES
031500        MOVE WS-EMPTY-TMPLT-ID       TO LS-SP-ERROR-AREA
031600        MOVE 'SP50'                  TO LS-SP-RC
              PERFORM 9100-DISPLAY-DATA
031800        PERFORM 9990-GOBACK
           ELSE
031300        PERFORM 2100-VALIDATE-TEMPLATE-ID
031900     END-IF
032000
032900     IF LS-CLNT-CMPNY-ID > SPACES
033000        PERFORM 2200-VALIDATE-CLNT-CMPNY-ID
031400     ELSE
031500        MOVE WS-EMPTY-CLNT-CMPNY-ID  TO LS-SP-ERROR-AREA
031600        MOVE 'SP50'                  TO LS-SP-RC
              PERFORM 9100-DISPLAY-DATA
031800        PERFORM 9990-GOBACK
031900     END-IF
033200
033300     IF LS-DELR-CMPNY-ID > SPACES
033400        PERFORM 2300-VALIDATE-DELR-CMPNY-ID
031400     ELSE
031500        MOVE WS-EMPTY-DELR-CMPNY-ID  TO LS-SP-ERROR-AREA
031600        MOVE 'SP50'                  TO LS-SP-RC
              PERFORM 9100-DISPLAY-DATA
031800        PERFORM 9990-GOBACK
040600     END-IF

           EVALUATE TRUE
              WHEN APPROVE-ENRL-REQ
              WHEN DENY-ENRL-REQ
                 MOVE LS-DELR-CMPNY-ID        TO D003-CMPNY-ID
              WHEN CREATE-ENRL-REQ
                 MOVE LS-CLNT-CMPNY-ID        TO D003-CMPNY-ID
031400        WHEN OTHER
031500           MOVE WS-EMPTY-DELR-STAT-CD   TO LS-SP-ERROR-AREA
031600           MOVE 'SP50'                  TO LS-SP-RC
                 PERFORM 9100-DISPLAY-DATA
031800           PERFORM 9990-GOBACK
033500     END-EVALUATE
04170
033300     IF LS-USER-ID > SPACES
033400        PERFORM 2400-VALIDATE-USER-ID
031400     ELSE
031500        MOVE WS-EMPTY-USER-ID           TO LS-SP-ERROR-AREA
031600        MOVE 'SP50'                     TO LS-SP-RC
              PERFORM 9100-DISPLAY-DATA
031800        PERFORM 9990-GOBACK
033500     END-IF

           IF LS-ENRL-TS = SPACES
044800        MOVE WS-CURRENT-TS           TO LS-ENRL-TS
           END-IF

041700     .
041900*----------------------------*
042000 2100-VALIDATE-TEMPLATE-ID.
042100*----------------------------*
042200
031000     MOVE '2100-VALIDATE-TEMPLATE-ID'
031000                                     TO WS-PARAGRAPH-NAME
           EXEC SQL
              SELECT  'Y'
                INTO :WS-TEMPLATE-ID-FLAG
                FROM  D0006
                WHERE MCA_TMPLT_ID      = :LS-MCA-TMPLT-ID
                  AND MCA_TMPLT_TYPE_CD = 'I'
                  AND MCA_STAT_IN       = 'P'
                WITH UR
           END-EXEC

           EVALUATE SQLCODE
              WHEN 0
                 CONTINUE
              WHEN +100
                 MOVE WS-INVLD-TMPLT-ID    TO LS-SP-ERROR-AREA
                 MOVE 'SP50'               TO LS-SP-RC
                 PERFORM 9990-GOBACK
              WHEN OTHER
                 PERFORM 9000-SQL-ERROR
           END-EVALUATE
044200
044400     .
044500*--------------------------*
044600 2200-VALIDATE-CLNT-CMPNY-ID.
044700*--------------------------*

044800     MOVE '2200-VALIDATE-CLNT-CMPNY-ID'
044800                                     TO WS-PARAGRAPH-NAME
045000     MOVE LS-CLNT-CMPNY-ID           TO D001-CMPNY-ID
045100
045200     EXEC SQL
045300        SELECT CMPNY_STAT_IN INTO :D001-CMPNY-STAT-IN
045400          FROM D0005
045500          WHERE CMPNY_ID = :D001-CMPNY-ID
045600     END-EXEC
045700
045800     EVALUATE SQLCODE
045900
046000        WHEN ZEROES
                 CONTINUE
046000        WHEN +100
046300           MOVE 'SP50'                 TO LS-SP-RC
                 MOVE WS-INVLD-CLNT-CMPNY-ID TO LS-SP-ERROR-AREA
046400           PERFORM 9990-GOBACK
046700        WHEN OTHER
046800           PERFORM 9000-SQL-ERROR
047000     END-EVALUATE

047100     .
044500*--------------------------*
044600 2300-VALIDATE-DELR-CMPNY-ID.
044700*--------------------------*

044800     MOVE '2300-VALIDATE-DELR-CMPNY-ID'
044800                                       TO WS-PARAGRAPH-NAME
045000     MOVE LS-DELR-CMPNY-ID             TO D001-CMPNY-ID
045100
045200     EXEC SQL
045300        SELECT 'Y' INTO :WS-DELR-CMPNY-FLAG
045400          FROM D0005
045500          WHERE CMPNY_ID      = :D001-CMPNY-ID
                  AND CMPNY_TYPE_CD = 'D'
                  AND CMPNY_STAT_IN = :D001-CMPNY-STAT-IN
045600     END-EXEC
045700
045800     EVALUATE SQLCODE
046000        WHEN ZEROES
                 CONTINUE
046000        WHEN +100
046300           MOVE 'SP50'                 TO LS-SP-RC
                 MOVE WS-INVLD-DELR-CMPNY-ID TO LS-SP-ERROR-AREA
046400           PERFORM 9990-GOBACK
046700        WHEN OTHER
046800           PERFORM 9000-SQL-ERROR
047000     END-EVALUATE
047100     .
044500*--------------------------*
044600 2400-VALIDATE-USER-ID.
044700*--------------------------*

044800     MOVE '2400-VALIDATE-USER-ID'    TO WS-PARAGRAPH-NAME
045000     MOVE WS-USER-ID                 TO D003-CMPNY-USER-ID
045100
045200     EXEC SQL
045300        SELECT 'Y' INTO :WS-USER-FLAG
045400          FROM D0003
045500          WHERE CMPNY_USER_ID = :D003-CMPNY-USER-ID
                  AND CMPNY_ID      = :D003-CMPNY-ID
045600     END-EXEC
045700
045800     EVALUATE SQLCODE
046000        WHEN ZEROES
                 CONTINUE
046000        WHEN +100
046200           MOVE WS-INVLD-USER-ID     TO LS-SP-ERROR-AREA
046300           MOVE 'SP50'               TO LS-SP-RC
046400           PERFORM 9990-GOBACK
046700        WHEN OTHER
046800           PERFORM 9000-SQL-ERROR
047000     END-EVALUATE
047100     .
044500*--------------------------*
044600 3000-PROCESS-ENRL-REQ.
044700*--------------------------*

044800     MOVE '3000-PROCESS-ENRL-REQ'    TO WS-PARAGRAPH-NAME
045000     MOVE LS-DELR-CMPNY-ID           TO D006-DELR-CMPNY-ID
045000     MOVE LS-CLNT-CMPNY-ID           TO D006-CLNT-CMPNY-ID
044800     MOVE WS-CURRENT-TS              TO D006-ROW-UPDT-TS
044800     MOVE LS-USER-ID                 TO D006-ROW-UPDT-USER-ID
044800     MOVE ZEROES                     TO D006-ASGD-TMPLT-ID
044800     MOVE LS-MCA-TMPLT-ID            TO D006-RQST-TMPLT-ID
044800     MOVE LS-ENRL-TS                 TO D006-ENRL-TS
045100
045200     EXEC SQL
045300        SELECT DELR_STAT_CD INTO :D006-DELR-STAT-CD
045400          FROM VDPM06_MCA_ENRL
045500          WHERE DELR_CMPNY_ID = :D006-DELR-CMPNY-ID
045500            AND CLNT_CMPNY_ID = :D006-CLNT-CMPNY-ID
                  AND RQST_TMPLT_ID = :D006-RQST-TMPLT-ID
045600     END-EXEC
045700
045800     EVALUATE SQLCODE
046000        WHEN ZEROES
                 EVALUATE D006-DELR-STAT-CD
                    WHEN 'A'
                       MOVE 'SP01'                TO LS-SP-RC
                       MOVE WS-ENRL-APPROVED-AL   TO LS-SP-ERROR-AREA
046400                 PERFORM 9990-GOBACK
                    WHEN 'P'
                       IF CREATE-ENRL-REQ
                          MOVE 'SP02'             TO LS-SP-RC
                          MOVE WS-ENRL-PENDING-AL TO LS-SP-ERROR-AREA
046400                    PERFORM 9990-GOBACK
                       END-IF
                 END-EVALUATE
                 PERFORM 3100-UPDT-ENROLLMENT
046000        WHEN +100
                 EVALUATE TRUE
                    WHEN DENY-ENRL-REQ
                    WHEN APPROVE-ENRL-REQ
                       MOVE 'SP03'             TO LS-SP-RC
                       MOVE WS-ENRL-NOT-FND    TO LS-SP-ERROR-AREA
                       PERFORM 9990-GOBACK
                    WHEN CREATE-ENRL-REQ
                       CONTINUE
                 END-EVALUATE
                 PERFORM 3300-ISRT-ENROLLMENT
046700        WHEN OTHER
046800           PERFORM 9000-SQL-ERROR
047000     END-EVALUATE
047100     .
044500*--------------------------*
044600 3100-UPDT-ENROLLMENT.
044700*--------------------------*

044800     MOVE '3100-UPDT-ENROLLMENT'          TO WS-PARAGRAPH-NAME
044800     MOVE LS-DELR-STAT-CD                 TO D006-DELR-STAT-CD

           IF APPROVE-ENRL-REQ
044800        MOVE LS-USER-ID                   TO D006-APRVR-USER-ID
                                                   D006-ROW-UPDT-USER-ID
044800        MOVE WS-CURRENT-TS                TO D006-APRVL-TS
                                                   D006-ROW-UPDT-TS
           ELSE
044800        MOVE SPACES                       TO D006-APRVR-USER-ID
044800        MOVE LS-USER-ID                   TO D006-ROW-UPDT-USER-ID
044800        MOVE '1900-01-01-01.01.01.000001' TO D006-APRVL-TS
044800        MOVE WS-CURRENT-TS                TO D006-ROW-UPDT-TS
           END-IF

045200     EXEC SQL
045300        UPDATE VDPM06_MCA_ENRL
045400          SET DELR_STAT_CD          = :D006-DELR-STAT-CD
045400             ,ROW_UPDT_TS           = :D006-ROW-UPDT-TS
045400             ,ROW_UPDT_USER_ID      = :D006-ROW-UPDT-USER-ID
                   ,APRVL_TS              = :D006-APRVL-TS
                   ,APRVR_USER_ID         = :D006-APRVR-USER-ID
045500          WHERE DELR_CMPNY_ID       = :D006-DELR-CMPNY-ID
045500           AND  CLNT_CMPNY_ID       = :D006-CLNT-CMPNY-ID
                 AND  RQST_TMPLT_ID       = :D006-RQST-TMPLT-ID
045600     END-EXEC
045700
045800     EVALUATE SQLCODE
046000        WHEN ZEROES
                 IF DENY-ENRL-REQ
                    PERFORM 3200-DLET-ENROLLMENT
                 END-IF
046700        WHEN +100
                 MOVE 'SP03'                  TO LS-SP-RC
                 MOVE WS-ENRL-NOT-FND         TO LS-SP-ERROR-AREA
046400           PERFORM 9990-GOBACK
046700        WHEN OTHER
046800           PERFORM 9000-SQL-ERROR
047000     END-EVALUATE
045700
047100     .
044500*--------------------------*
044600 3200-DLET-ENROLLMENT.
044700*--------------------------*

044800     MOVE '3200-DLET-ENROLLMENT'        TO WS-PARAGRAPH-NAME
044800     MOVE LS-DELR-STAT-CD               TO D006-DELR-STAT-CD

045200     EXEC SQL
045300        DELETE FROM VDPM06_MCA_ENRL
045400          WHERE DELR_STAT_CD        = :D006-DELR-STAT-CD
045500            AND DELR_CMPNY_ID       = :D006-DELR-CMPNY-ID
045500            AND CLNT_CMPNY_ID       = :D006-CLNT-CMPNY-ID
                  AND RQST_TMPLT_ID       = :D006-RQST-TMPLT-ID
045600     END-EXEC
045700
045800     EVALUATE SQLCODE
046000        WHEN ZEROES
                 CONTINUE
046000        WHEN +100
                 MOVE 'SP03'                  TO LS-SP-RC
                 MOVE WS-ENRL-NOT-FND         TO LS-SP-ERROR-AREA
046400           PERFORM 9990-GOBACK
046700        WHEN OTHER
046800           PERFORM 9000-SQL-ERROR
047000     END-EVALUATE
045700
047100     .
044500*--------------------------*
044600 3300-ISRT-ENROLLMENT.
044700*--------------------------*

044800     MOVE '3300-ISRT-ENROLLMENT'        TO WS-PARAGRAPH-NAME
044800     MOVE LS-DELR-STAT-CD               TO D006-DELR-STAT-CD
044800     MOVE '1900-01-01-01.01.01.000001'  TO D006-APRVL-TS
044800     MOVE SPACES                        TO D006-APRVR-USER-ID
045100
045200     EXEC SQL
045300        INSERT INTO VDPM06_MCA_ENRL
                 (DELR_CMPNY_ID
                 ,CLNT_CMPNY_ID
                 ,RQST_TMPLT_ID
                 ,ASGD_TMPLT_ID
                 ,ENRL_TS
                 ,DELR_STAT_CD
                 ,ROW_UPDT_TS
                 ,ROW_UPDT_USER_ID
                 ,APRVR_USER_ID
                 ,APRVL_TS)
                 VALUES
                 (:D006-DELR-CMPNY-ID
                 ,:D006-CLNT-CMPNY-ID
                 ,:D006-RQST-TMPLT-ID
                 ,:D006-ASGD-TMPLT-ID
                 ,:D006-ENRL-TS
                 ,:D006-DELR-STAT-CD
                 ,:D006-ROW-UPDT-TS
                 ,:D006-ROW-UPDT-USER-ID
                 ,:D006-APRVR-USER-ID
                 ,:D006-APRVL-TS)
           END-EXEC
045700
045800     EVALUATE SQLCODE
046000        WHEN ZEROES
                 CONTINUE
046000        WHEN -803
                 INITIALIZE SQLCODE
                 MOVE 'SP02'                        TO LS-SP-RC
                 MOVE WS-ENRL-PENDING-AL            TO LS-SP-ERROR-AREA
046400           PERFORM 9990-GOBACK
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
087800                                     TO LS-SP-ERROR-AREA
           MOVE 'SP99'                     TO LS-SP-RC
           MOVE SQLCODE                    TO WS-SQLCODE
           DISPLAY 'PARAGRAPH-NAME         :' WS-PARAGRAPH-NAME
102700     DISPLAY 'SQLCODE                :' WS-SQLCODE
102000     PERFORM 9990-GOBACK
102100     .
102200*------------------------*
102300 9100-DISPLAY-DATA.
102400*------------------------*
102500
           IF DISPLAY-PARAMETERS
102600        DISPLAY WS-DASHES
102700        DISPLAY 'PROGRAM-NAME             :' WS-PROGRAM
102700        DISPLAY 'PARAGRAPH-NAME           :' WS-PARAGRAPH-NAME
102800        DISPLAY 'SP-ERROR-AREA            :' LS-SP-ERROR-AREA
102900        DISPLAY 'SP-RC                    :' LS-SP-RC
103400        DISPLAY 'MCA TEMPLATE ID          :' LS-MCA-TMPLT-ID
103700        DISPLAY 'CLIENT COMPANY ID        :' LS-CLNT-CMPNY-ID
104000        DISPLAY 'DEALER COMPANY ID        :' LS-DELR-CMPNY-ID
104300        DISPLAY 'DEALER STATUS CODE       :' LS-DELR-STAT-CD
104600        DISPLAY 'CLIENT USER ID           :' LS-USER-ID
104600        DISPLAY 'ENROLLMENT TS            :' LS-ENRL-TS
           END-IF

106800
107000     .
107100*------------------------*
107200 9990-GOBACK.
107300*------------------------*

101900     PERFORM 9999-FORMAT-SQLCA
           IF DISPLAY-PARAMETERS
              DISPLAY WS-DASHES
              DISPLAY 'OUTSQLCA FOR DPMXENRL    :' OUTSQLCA
              DISPLAY WS-DASHES
              EXEC SQL
                  SET :WS-CURRENT-TS = CURRENT TIMESTAMP
              END-EXEC
              DISPLAY 'DPMXENRL ENDED AT        :' WS-CURRENT-TS
              DISPLAY WS-DASHES
           END-IF
107400     GOBACK
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