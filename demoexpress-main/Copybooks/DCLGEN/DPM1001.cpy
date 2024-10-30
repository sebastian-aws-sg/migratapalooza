      ******************************************************************
      * DCLGEN TABLE(NSCC.VDPM10_MCA_LOCK)                             *
      *        LIBRARY(DB2T.DCLGEN.TEST.NSCC.COBCOPY(DPM1001))         *
      *        ACTION(REPLACE)                                         *
      *        LANGUAGE(COBOL)                                         *
      *        NAMES(D010-)                                            *
      *        QUOTE                                                   *
      *        COLSUFFIX(YES)                                          *
      *        INDVAR(YES)                                             *
      * ... IS THE DCLGEN COMMAND THAT MADE THE FOLLOWING STATEMENTS   *
      ******************************************************************
           EXEC SQL DECLARE NSCC.VDPM10_MCA_LOCK TABLE
           ( MCA_TMPLT_ID                   INTEGER NOT NULL,
             CMPNY_ID                       CHAR(8) NOT NULL,
             CMPNY_USER_ID                  CHAR(10) NOT NULL,
             ROW_UPDT_TS                    TIMESTAMP NOT NULL
           ) END-EXEC.
      ******************************************************************
      * COBOL DECLARATION FOR TABLE NSCC.VDPM10_MCA_LOCK               *
      ******************************************************************
       01  DCLVDPM10-MCA-LOCK.
      *                       MCA_TMPLT_ID
           10 D010-MCA-TMPLT-ID    PIC S9(9) USAGE COMP.
      *                       CMPNY_ID
           10 D010-CMPNY-ID        PIC X(8).
      *                       CMPNY_USER_ID
           10 D010-CMPNY-USER-ID   PIC X(10).
      *                       ROW_UPDT_TS
           10 D010-ROW-UPDT-TS     PIC X(26).
      ******************************************************************
      * INDICATOR VARIABLE STRUCTURE                                   *
      ******************************************************************
       01  IVDPM10-MCA-LOCK.
           10 INDSTRUC           PIC S9(4) USAGE COMP OCCURS 4 TIMES.
      ******************************************************************
      * THE NUMBER OF COLUMNS DESCRIBED BY THIS DECLARATION IS 4       *
      ******************************************************************
