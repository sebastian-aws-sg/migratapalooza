      ******************************************************************
      * DCLGEN TABLE(NSCC.VDPM13_MCA_TEXT)                             *
      *        LIBRARY(DB2T.DCLGEN.TEST.NSCC.COBCOPY(DPM1301))         *
      *        ACTION(REPLACE)                                         *
      *        LANGUAGE(COBOL)                                         *
      *        NAMES(D013-)                                            *
      *        QUOTE                                                   *
      *        COLSUFFIX(YES)                                          *
      *        INDVAR(YES)                                             *
      * ... IS THE DCLGEN COMMAND THAT MADE THE FOLLOWING STATEMENTS   *
      ******************************************************************
           EXEC SQL DECLARE NSCC.VDPM13_MCA_TEXT TABLE
           ( MCA_VALUE_ID                   DECIMAL(18, 0) NOT NULL,
             MCA_TEXT_DS                    VARCHAR(32000) NOT NULL
           ) END-EXEC.
      ******************************************************************
      * COBOL DECLARATION FOR TABLE NSCC.VDPM13_MCA_TEXT               *
      ******************************************************************
       01  DCLVDPM13-MCA-TEXT.
      *                       MCA_VALUE_ID
           10 D013-MCA-VALUE-ID    PIC S9(18)V USAGE COMP-3.
           10 D013-MCA-TEXT-DS.
      *                       MCA_TEXT_DS LENGTH
              49 D013-MCA-TEXT-DS-LEN
                 PIC S9(4) USAGE COMP.
      *                       MCA_TEXT_DS
              49 D013-MCA-TEXT-DS-TEXT
                 PIC X(32000).
      ******************************************************************
      * INDICATOR VARIABLE STRUCTURE                                   *
      ******************************************************************
       01  IVDPM13-MCA-TEXT.
           10 INDSTRUC           PIC S9(4) USAGE COMP OCCURS 2 TIMES.
      ******************************************************************
      * THE NUMBER OF COLUMNS DESCRIBED BY THIS DECLARATION IS 2       *
      ******************************************************************
