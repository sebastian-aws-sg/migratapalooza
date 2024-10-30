      ******************************************************************
      * DCLGEN TABLE(NSCC.VDPM06_MCA_ENRL)                             *
      *        LIBRARY(DB2T.DCLGEN.TEST.NSCC.COBCOPY(DPM0601))         *
      *        ACTION(REPLACE)                                         *
      *        LANGUAGE(COBOL)                                         *
      *        NAMES(D006-)                                            *
      *        QUOTE                                                   *
      *        COLSUFFIX(YES)                                          *
      *        INDVAR(YES)                                             *
      * ... IS THE DCLGEN COMMAND THAT MADE THE FOLLOWING STATEMENTS   *
      ******************************************************************
           EXEC SQL DECLARE NSCC.VDPM06_MCA_ENRL TABLE
           ( DELR_CMPNY_ID                  CHAR(8) NOT NULL,
             CLNT_CMPNY_ID                  CHAR(8) NOT NULL,
             RQST_TMPLT_ID                  INTEGER NOT NULL,
             ASGD_TMPLT_ID                  INTEGER NOT NULL,
             ENRL_TS                        TIMESTAMP NOT NULL,
             DELR_STAT_CD                   CHAR(1) NOT NULL,
             ROW_UPDT_TS                    TIMESTAMP NOT NULL,
             ROW_UPDT_USER_ID               CHAR(10) NOT NULL,
             APRVR_USER_ID                  CHAR(10) NOT NULL,
             APRVL_TS                       TIMESTAMP NOT NULL
           ) END-EXEC.
      ******************************************************************
      * COBOL DECLARATION FOR TABLE NSCC.VDPM06_MCA_ENRL               *
      ******************************************************************
       01  DCLVDPM06-MCA-ENRL.
      *                       DELR_CMPNY_ID
           10 D006-DELR-CMPNY-ID   PIC X(8).
      *                       CLNT_CMPNY_ID
           10 D006-CLNT-CMPNY-ID   PIC X(8).
      *                       RQST_TMPLT_ID
           10 D006-RQST-TMPLT-ID   PIC S9(9) USAGE COMP.
      *                       ASGD_TMPLT_ID
           10 D006-ASGD-TMPLT-ID   PIC S9(9) USAGE COMP.
      *                       ENRL_TS
           10 D006-ENRL-TS         PIC X(26).
      *                       DELR_STAT_CD
           10 D006-DELR-STAT-CD    PIC X(1).
      *                       ROW_UPDT_TS
           10 D006-ROW-UPDT-TS     PIC X(26).
      *                       ROW_UPDT_USER_ID
           10 D006-ROW-UPDT-USER-ID
              PIC X(10).
      *                       APRVR_USER_ID
           10 D006-APRVR-USER-ID   PIC X(10).
      *                       APRVL_TS
           10 D006-APRVL-TS        PIC X(26).
      ******************************************************************
      * INDICATOR VARIABLE STRUCTURE                                   *
      ******************************************************************
       01  IVDPM06-MCA-ENRL.
           10 INDSTRUC           PIC S9(4) USAGE COMP OCCURS 10 TIMES.
      ******************************************************************
      * THE NUMBER OF COLUMNS DESCRIBED BY THIS DECLARATION IS 10      *
      ******************************************************************
