      ******************************************************************
      * DCLGEN TABLE(NSCC.VDPM19_LINK_WORK)                            *
      *        LIBRARY(DB2T.DCLGEN.TEST.NSCC.COBCOPY(DPM1901))         *
      *        LANGUAGE(COBOL)                                         *
      *        NAMES(D019-)                                            *
      *        QUOTE                                                   *
      *        COLSUFFIX(YES)                                          *
      *        INDVAR(YES)                                             *
      * ... IS THE DCLGEN COMMAND THAT MADE THE FOLLOWING STATEMENTS   *
      ******************************************************************
           EXEC SQL DECLARE NSCC.VDPM19_LINK_WORK TABLE
           ( MCA_AMND_ID                    DECIMAL(18, 0) NOT NULL,
             MCA_VALUE_ID                   DECIMAL(18, 0) NOT NULL,
             MCA_VALUE_TYPE_CD              CHAR(1) NOT NULL,
             MCA_ACCS_STAT_CD               CHAR(1) NOT NULL,
             AMND_STAT_CD                   CHAR(1) NOT NULL,
             ROW_UPDT_TS                    TIMESTAMP NOT NULL,
             ROW_UPDT_USER_ID               CHAR(10) NOT NULL
           ) END-EXEC.
      ******************************************************************
      * COBOL DECLARATION FOR TABLE NSCC.VDPM19_LINK_WORK              *
      ******************************************************************
       01  DCLVDPM19-LINK-WORK.
      *                       MCA_AMND_ID
           10 D019-MCA-AMND-ID     PIC S9(18)V USAGE COMP-3.
      *                       MCA_VALUE_ID
           10 D019-MCA-VALUE-ID    PIC S9(18)V USAGE COMP-3.
      *                       MCA_VALUE_TYPE_CD
           10 D019-MCA-VALUE-TYPE-CD
              PIC X(1).
      *                       MCA_ACCS_STAT_CD
           10 D019-MCA-ACCS-STAT-CD
              PIC X(1).
      *                       AMND_STAT_CD
           10 D019-AMND-STAT-CD    PIC X(1).
      *                       ROW_UPDT_TS
           10 D019-ROW-UPDT-TS     PIC X(26).
      *                       ROW_UPDT_USER_ID
           10 D019-ROW-UPDT-USER-ID
              PIC X(10).
      ******************************************************************
      * INDICATOR VARIABLE STRUCTURE                                   *
      ******************************************************************
       01  IVDPM19-LINK-WORK.
           10 INDSTRUC           PIC S9(4) USAGE COMP OCCURS 7 TIMES.
      ******************************************************************
      * THE NUMBER OF COLUMNS DESCRIBED BY THIS DECLARATION IS 7       *
      ******************************************************************
