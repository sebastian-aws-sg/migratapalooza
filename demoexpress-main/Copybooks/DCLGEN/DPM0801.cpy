      ******************************************************************
      * DCLGEN TABLE(NSCC.VDPM08_MCA_TERMS)                            *
      *        LIBRARY(DB2T.DCLGEN.TEST.NSCC.COBCOPY(DPM0801))         *
      *        ACTION(REPLACE)                                         *
      *        LANGUAGE(COBOL)                                         *
      *        NAMES(D008-)                                            *
      *        QUOTE                                                   *
      *        COLSUFFIX(YES)                                          *
      *        INDVAR(YES)                                             *
      * ... IS THE DCLGEN COMMAND THAT MADE THE FOLLOWING STATEMENTS   *
      ******************************************************************
           EXEC SQL DECLARE NSCC.VDPM08_MCA_TERMS TABLE
           ( MCA_TMPLT_ID                   INTEGER NOT NULL,
             ATTRB_CTGRY_ID                 CHAR(8) NOT NULL,
             CTGRY_SQ                       SMALLINT NOT NULL,
             ATTRB_TERM_ID                  CHAR(8) NOT NULL,
             TERM_SQ                        SMALLINT NOT NULL,
             TERM_STAT_CD                   CHAR(1) NOT NULL,
             ROW_UPDT_TS                    TIMESTAMP NOT NULL,
             ROW_UPDT_USER_ID               CHAR(10) NOT NULL
           ) END-EXEC.
      ******************************************************************
      * COBOL DECLARATION FOR TABLE NSCC.VDPM08_MCA_TERMS              *
      ******************************************************************
       01  DCLVDPM08-MCA-TERMS.
      *                       MCA_TMPLT_ID
           10 D008-MCA-TMPLT-ID    PIC S9(9) USAGE COMP.
      *                       ATTRB_CTGRY_ID
           10 D008-ATTRB-CTGRY-ID  PIC X(8).
      *                       CTGRY_SQ
           10 D008-CTGRY-SQ        PIC S9(4) USAGE COMP.
      *                       ATTRB_TERM_ID
           10 D008-ATTRB-TERM-ID   PIC X(8).
      *                       TERM_SQ
           10 D008-TERM-SQ         PIC S9(4) USAGE COMP.
      *                       TERM_STAT_CD
           10 D008-TERM-STAT-CD    PIC X(1).
      *                       ROW_UPDT_TS
           10 D008-ROW-UPDT-TS     PIC X(26).
      *                       ROW_UPDT_USER_ID
           10 D008-ROW-UPDT-USER-ID
              PIC X(10).
      ******************************************************************
      * INDICATOR VARIABLE STRUCTURE                                   *
      ******************************************************************
       01  IVDPM08-MCA-TERMS.
           10 INDSTRUC           PIC S9(4) USAGE COMP OCCURS 8 TIMES.
      ******************************************************************
      * THE NUMBER OF COLUMNS DESCRIBED BY THIS DECLARATION IS 8       *
      ******************************************************************
