      ******************************************************************
      * DCLGEN TABLE(NSCC.VDPM02_DELR_CMPNY)                           *
      *        LIBRARY(DB2T.DCLGEN.TEST.NSCC.COBCOPY(DPM0201))         *
      *        ACTION(REPLACE)                                         *
      *        LANGUAGE(COBOL)                                         *
      *        NAMES(D002-)                                            *
      *        QUOTE                                                   *
      *        COLSUFFIX(YES)                                          *
      *        INDVAR(YES)                                             *
      * ... IS THE DCLGEN COMMAND THAT MADE THE FOLLOWING STATEMENTS   *
      ******************************************************************
           EXEC SQL DECLARE NSCC.VDPM02_DELR_CMPNY TABLE
           ( CMPNY_ID                       CHAR(8) NOT NULL,
             CRT_CMPNY_ID                   CHAR(8) NOT NULL,
             CMPNY_NM                       CHAR(255) NOT NULL,
             ROW_UPDT_TS                    TIMESTAMP NOT NULL,
             ROW_UPDT_USER_ID               CHAR(10) NOT NULL
           ) END-EXEC.
      ******************************************************************
      * COBOL DECLARATION FOR TABLE NSCC.VDPM02_DELR_CMPNY             *
      ******************************************************************
       01  DCLVDPM02-DELR-CMPNY.
      *                       CMPNY_ID
           10 D002-CMPNY-ID        PIC X(8).
      *                       CRT_CMPNY_ID
           10 D002-CRT-CMPNY-ID    PIC X(8).
      *                       CMPNY_NM
           10 D002-CMPNY-NM        PIC X(255).
      *                       ROW_UPDT_TS
           10 D002-ROW-UPDT-TS     PIC X(26).
      *                       ROW_UPDT_USER_ID
           10 D002-ROW-UPDT-USER-ID
              PIC X(10).
      ******************************************************************
      * INDICATOR VARIABLE STRUCTURE                                   *
      ******************************************************************
       01  IVDPM02-DELR-CMPNY.
           10 INDSTRUC           PIC S9(4) USAGE COMP OCCURS 5 TIMES.
      ******************************************************************
      * THE NUMBER OF COLUMNS DESCRIBED BY THIS DECLARATION IS 5       *
      ******************************************************************
