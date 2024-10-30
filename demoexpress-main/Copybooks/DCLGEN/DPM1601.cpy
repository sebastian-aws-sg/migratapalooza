      ******************************************************************
      * DCLGEN TABLE(NSCC.VDPM16_MCA_AMND)                             *
      *        LIBRARY(DB2T.DCLGEN.TEST.NSCC.COBCOPY(DPM1601))         *
      *        LANGUAGE(COBOL)                                         *
      *        NAMES(D016-)                                            *
      *        QUOTE                                                   *
      *        COLSUFFIX(YES)                                          *
      *        INDVAR(YES)                                             *
      * ... IS THE DCLGEN COMMAND THAT MADE THE FOLLOWING STATEMENTS   *
      ******************************************************************
           EXEC SQL DECLARE NSCC.VDPM16_MCA_AMND TABLE
           ( MCA_AMND_ID                    DECIMAL(18, 0) NOT NULL,
             MCA_TMPLT_ID                   INTEGER NOT NULL,
             ATTRB_CTGRY_ID                 CHAR(8) NOT NULL,
             CTGRY_SQ                       SMALLINT NOT NULL,
             ATTRB_TERM_ID                  CHAR(8) NOT NULL,
             TERM_SQ                        SMALLINT NOT NULL,
             MCA_ISDA_AMND_ID               DECIMAL(18, 0) NOT NULL,
             ROW_UPDT_TS                    TIMESTAMP NOT NULL,
             ROW_UPDT_USER_ID               CHAR(10) NOT NULL
           ) END-EXEC.
      ******************************************************************
      * COBOL DECLARATION FOR TABLE NSCC.VDPM16_MCA_AMND               *
      ******************************************************************
       01  DCLVDPM16-MCA-AMND.
      *                       MCA_AMND_ID
           10 D016-MCA-AMND-ID     PIC S9(18)V USAGE COMP-3.
      *                       MCA_TMPLT_ID
           10 D016-MCA-TMPLT-ID    PIC S9(9) USAGE COMP.
      *                       ATTRB_CTGRY_ID
           10 D016-ATTRB-CTGRY-ID  PIC X(8).
      *                       CTGRY_SQ
           10 D016-CTGRY-SQ        PIC S9(4) USAGE COMP.
      *                       ATTRB_TERM_ID
           10 D016-ATTRB-TERM-ID   PIC X(8).
      *                       TERM_SQ
           10 D016-TERM-SQ         PIC S9(4) USAGE COMP.
      *                       MCA_ISDA_AMND_ID
           10 D016-MCA-ISDA-AMND-ID
              PIC S9(18)V USAGE COMP-3.
      *                       ROW_UPDT_TS
           10 D016-ROW-UPDT-TS     PIC X(26).
      *                       ROW_UPDT_USER_ID
           10 D016-ROW-UPDT-USER-ID
              PIC X(10).
      ******************************************************************
      * INDICATOR VARIABLE STRUCTURE                                   *
      ******************************************************************
       01  IVDPM16-MCA-AMND.
           10 INDSTRUC           PIC S9(4) USAGE COMP OCCURS 9 TIMES.
      ******************************************************************
      * THE NUMBER OF COLUMNS DESCRIBED BY THIS DECLARATION IS 9       *
      ******************************************************************
