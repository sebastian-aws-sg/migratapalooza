      ******************************************************************
      * DCLGEN TABLE(NSCC.VDPM15_TMPLT_WORK)                           *
      *        LIBRARY(DB2T.DCLGEN.TEST.NSCC.COBCOPY(DPM1501))         *
      *        ACTION(REPLACE)                                         *
      *        LANGUAGE(COBOL)                                         *
      *        NAMES(D015-)                                            *
      *        QUOTE                                                   *
      *        COLSUFFIX(YES)                                          *
      *        INDVAR(YES)                                             *
      * ... IS THE DCLGEN COMMAND THAT MADE THE FOLLOWING STATEMENTS   *
      ******************************************************************
           EXEC SQL DECLARE NSCC.VDPM15_TMPLT_WORK TABLE
           ( MCA_TMPLT_ID                   INTEGER NOT NULL,
             MCA_TMPLT_NM                   VARCHAR(500) NOT NULL,
             MCA_TMPLT_SHORT_NM             CHAR(150) NOT NULL,
             MCA_TMPLT_GROUP_CD             CHAR(1) NOT NULL,
             MCA_TMPLT_TYPE_CD              CHAR(1) NOT NULL,
             DELR_CMPNY_ID                  CHAR(8) NOT NULL,
             CLNT_CMPNY_ID                  CHAR(8) NOT NULL,
             ATTRB_PRDCT_ID                 CHAR(8) NOT NULL,
             ATTRB_SUB_PRDCT_ID             CHAR(8) NOT NULL,
             ATTRB_REGN_ID                  CHAR(8) NOT NULL,
             MCA_PBLTN_DT                   DATE NOT NULL,
             MCA_END_DT                     DATE NOT NULL,
             MCA_STAT_IN                    CHAR(1) NOT NULL,
             MCA_EXE_TS                     TIMESTAMP NOT NULL,
             MCA_DELR_STAT_CD               CHAR(1) NOT NULL,
             MCA_CLNT_STAT_CD               CHAR(1) NOT NULL,
             MCA_ISDA_TMPLT_ID              INTEGER NOT NULL,
             MCA_CSTMZ_TMPLT_ID             INTEGER NOT NULL,
             MCA_TMPLT_RQSTR_ID             CHAR(10) NOT NULL,
             MCA_TMPLT_APRVR_ID             CHAR(10) NOT NULL,
             ROW_UPDT_TS                    TIMESTAMP NOT NULL,
             ROW_UPDT_USER_ID               CHAR(10) NOT NULL
           ) END-EXEC.
      ******************************************************************
      * COBOL DECLARATION FOR TABLE NSCC.VDPM15_TMPLT_WORK             *
      ******************************************************************
       01  DCLVDPM15-TMPLT-WORK.
      *                       MCA_TMPLT_ID
           10 D015-MCA-TMPLT-ID    PIC S9(9) USAGE COMP.
           10 D015-MCA-TMPLT-NM.
      *                       MCA_TMPLT_NM LENGTH
              49 D015-MCA-TMPLT-NM-LEN
                 PIC S9(4) USAGE COMP.
      *                       MCA_TMPLT_NM
              49 D015-MCA-TMPLT-NM-TEXT
                 PIC X(500).
      *                       MCA_TMPLT_SHORT_NM
           10 D015-MCA-TMPLT-SHORT-NM
              PIC X(150).
      *                       MCA_TMPLT_GROUP_CD
           10 D015-MCA-TMPLT-GROUP-CD
              PIC X(1).
      *                       MCA_TMPLT_TYPE_CD
           10 D015-MCA-TMPLT-TYPE-CD
              PIC X(1).
      *                       DELR_CMPNY_ID
           10 D015-DELR-CMPNY-ID   PIC X(8).
      *                       CLNT_CMPNY_ID
           10 D015-CLNT-CMPNY-ID   PIC X(8).
      *                       ATTRB_PRDCT_ID
           10 D015-ATTRB-PRDCT-ID  PIC X(8).
      *                       ATTRB_SUB_PRDCT_ID
           10 D015-ATTRB-SUB-PRDCT-ID
              PIC X(8).
      *                       ATTRB_REGN_ID
           10 D015-ATTRB-REGN-ID   PIC X(8).
      *                       MCA_PBLTN_DT
           10 D015-MCA-PBLTN-DT    PIC X(10).
      *                       MCA_END_DT
           10 D015-MCA-END-DT      PIC X(10).
      *                       MCA_STAT_IN
           10 D015-MCA-STAT-IN     PIC X(1).
      *                       MCA_EXE_TS
           10 D015-MCA-EXE-TS      PIC X(26).
      *                       MCA_DELR_STAT_CD
           10 D015-MCA-DELR-STAT-CD
              PIC X(1).
      *                       MCA_CLNT_STAT_CD
           10 D015-MCA-CLNT-STAT-CD
              PIC X(1).
      *                       MCA_ISDA_TMPLT_ID
           10 D015-MCA-ISDA-TMPLT-ID
              PIC S9(9) USAGE COMP.
      *                       MCA_CSTMZ_TMPLT_ID
           10 D015-MCA-CSTMZ-TMPLT-ID
              PIC S9(9) USAGE COMP.
      *                       MCA_TMPLT_RQSTR_ID
           10 D015-MCA-TMPLT-RQSTR-ID
              PIC X(10).
      *                       MCA_TMPLT_APRVR_ID
           10 D015-MCA-TMPLT-APRVR-ID
              PIC X(10).
      *                       ROW_UPDT_TS
           10 D015-ROW-UPDT-TS     PIC X(26).
      *                       ROW_UPDT_USER_ID
           10 D015-ROW-UPDT-USER-ID
              PIC X(10).
      ******************************************************************
      * INDICATOR VARIABLE STRUCTURE                                   *
      ******************************************************************
       01  IVDPM15-TMPLT-WORK.
           10 INDSTRUC           PIC S9(4) USAGE COMP OCCURS 22 TIMES.
      ******************************************************************
      * THE NUMBER OF COLUMNS DESCRIBED BY THIS DECLARATION IS 22      *
      ******************************************************************
