      ******************************************************************
      * DCLGEN TABLE(NSCC.VDPM07_MCA_CTGRY)                            *
      *        LIBRARY(DB2T.DCLGEN.TEST.NSCC.COBCOPY(DPM0701))         *
      *        LANGUAGE(COBOL)                                         *
      *        NAMES(D007-)                                            *
      *        QUOTE                                                   *
      *        COLSUFFIX(YES)                                          *
      *        INDVAR(YES)                                             *
      * ... IS THE DCLGEN COMMAND THAT MADE THE FOLLOWING STATEMENTS   *
      ******************************************************************
           EXEC SQL DECLARE NSCC.VDPM07_MCA_CTGRY TABLE
           ( MCA_TMPLT_ID                   INTEGER NOT NULL,
             ATTRB_CTGRY_ID                 CHAR(8) NOT NULL,
             CTGRY_SQ                       SMALLINT NOT NULL,
             CTGRY_STAT_CD                  CHAR(1) NOT NULL,
             ROW_UPDT_TS                    TIMESTAMP NOT NULL,
             ROW_UPDT_USER_ID               CHAR(10) NOT NULL
           ) END-EXEC.
      ******************************************************************
      * COBOL DECLARATION FOR TABLE NSCC.VDPM07_MCA_CTGRY              *
      ******************************************************************
       01  DCLVDPM07-MCA-CTGRY.
      *                       MCA_TMPLT_ID
           10 D007-MCA-TMPLT-ID    PIC S9(9) USAGE COMP.
      *                       ATTRB_CTGRY_ID
           10 D007-ATTRB-CTGRY-ID  PIC X(8).
      *                       CTGRY_SQ
           10 D007-CTGRY-SQ        PIC S9(4) USAGE COMP.
      *                       CTGRY_STAT_CD
           10 D007-CTGRY-STAT-CD   PIC X(1).
      *                       ROW_UPDT_TS
           10 D007-ROW-UPDT-TS     PIC X(26).
      *                       ROW_UPDT_USER_ID
           10 D007-ROW-UPDT-USER-ID
              PIC X(10).
      ******************************************************************
      * INDICATOR VARIABLE STRUCTURE                                   *
      ******************************************************************
       01  IVDPM07-MCA-CTGRY.
           10 INDSTRUC           PIC S9(4) USAGE COMP OCCURS 6 TIMES.
      ******************************************************************
      * THE NUMBER OF COLUMNS DESCRIBED BY THIS DECLARATION IS 6       *
      ******************************************************************
