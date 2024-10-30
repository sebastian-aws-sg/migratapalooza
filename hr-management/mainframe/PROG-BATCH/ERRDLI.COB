      * EXAMPLE FOR THE QR THAT SEARCHES FOR PROGRAMS
      * NOT CHECKING STATUS CODE GE OR GB AFTER DLI CALLS
      * USING GNP AND GN FUNCTIONS.
       IDENTIFICATION DIVISION.
       PROGRAM-ID. TEST.
       DATA DIVISION.
       WORKING-STORAGE SECTION.
       01 W-RC PIC XX.
           88 OK VALUE 'GE'.
       LINKAGE SECTION.
       01 DEPTPCB.                                                       
          02  DBD-NAME      PIC X(8).                                   
          02  SEG-LEVEL     PIC X(2).                                   
          02  STATUS-CODE   PIC X(2).                                   
          02  PROC-OPTIONS  PIC X(4).                                   
          02  RESERVE-DLI   PIC S9(9) USAGE IS BINARY.                  
          02  SEG-NAME-FB   PIC X(8).                                   
          02  KEY-LENGTH    PIC S9(9) USAGE IS BINARY.                  
          02  NO-SENSE-SEG  PIC S9(9) USAGE IS BINARY.                  
          02  KEY-AREA      PIC X(3).                                    
       PROCEDURE DIVISION.
       MAIN-PARAG.
           PERFORM OK-1.
           PERFORM OK-2.
           PERFORM OK-3.
           PERFORM OK-4.
           PERFORM KO-1.
           GOBACK.
       OK-1.
      * STATUS IS DIRECTLY CHECKED
           CALL 'CBLTDLI' USING WS-GNP DEPTPCB WS-RATE-CHILD.

           IF STATUS-CODE = 'GE'
               DISPLAY "ERROR HANDLING"
               MOVE 'Y' TO WS-EOJ
           END-IF
       OK-2.
      * STATUS IS DIRECTLY CHECKED
           CALL 'CBLTDLI' USING WS-GNP DEPTPCB WS-RATE-CHILD.

           EVALUATE STATUS-CODE
               WHEN 'GE'
                   DISPLAY "ERROR HANDLING"
                   MOVE 'Y' TO WS-EOJ
               WHEN OTHER
                   DISPLAY "OTHER CASES MANAGED HERE"
           END-EVALUATE
       OK-3.
      * STATUS IS CHECKED THROUGH AN INTERMEDIATE VARIABLE
           CALL 'CBLTDLI' USING WS-GN DEPTPCB WS-RATE-CHILD.
           
           MOVE STATUS-CODE TO W-RC.

           IF W-RC = 'GB'
               DISPLAY "ERROR HANDLING"
               MOVE 'Y' TO WS-EOJ
           END-IF 
       OK-4.
      * STATUS IS CHECKED THROUGH A 88 LEVEL ATTACHED TO AN INTERMEDIATE VARIABLE
           CALL 'CBLTDLI' USING WS-GN DEPTPCB WS-RATE-CHILD.
           
           MOVE STATUS-CODE TO W-RC.

           IF OK
               DISPLAY "ERROR HANDLING"
               MOVE 'Y' TO WS-EOJ
           END-IF 
       KO-1.
      * NO STATUS CHECK
           CALL 'CBLTDLI' USING WS-GNP DEPTPCB WS-RATE-CHILD.
           MOVE "OK" TO W-OK.
           DISPLAY "NO ERROR HANDLING".

          