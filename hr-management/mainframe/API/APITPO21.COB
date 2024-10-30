      *-----------------------------------------------------------------
      *  APITPO21 - TECHNICAL PROGRAM TO ACCESS DATA NEEDED BY COBCIO21.
      *-----------------------------------------------------------------
      *                                                                 
      *--------------------PART OF MYTELCO HR APPLICATION-----------
      *                                                                 
      *-----------------------------------------------------------------
       IDENTIFICATION DIVISION.                                         
       PROGRAM-ID.   APITP021.                                          
       AUTHOR.       CAST SOFTWARE                                      
       DATE-WRITTEN. FEBRUARY 2012.                                       
                                                                        
       EJECT                                                            
       ENVIRONMENT DIVISION.                                            
       DATA DIVISION.                                                   
                                                                        
       WORKING-STORAGE SECTION.                                         
                                                                        
           COPY DFHBMSCA.                                               
                                                                        
           COPY DFHAID.  
           
           COPY RESUMEM.

           EXEC SQL
              INCLUDE EMPRESUM
           END-EXEC.

           EXEC SQL 
               INCLUDE SQLCA  
           END-EXEC.                       
                                                                        
       01  RESUME-MASTER-RECORD.                                      
           10 WS-EMPNO                PIC X(6).
           10 WS-RESUME-FORMAT        PIC X(10).
           10 WS-RESUME               USAGE IS SQL TYPE IS CLOB.

       LINKAGE SECTION.
       01 CMD-CODE  PIC 99.
       01 RESP-CODE PIC 99.
       01 DATA-IN   PIC X(512).
       01 DATA-OUT  PIC X(2048)
      *-----------------------------------------------------------------
      * PROCEDURE DIVISION.
      *-----------------------------------------------------------------
       PROCEDURE DIVISION USING CMD-CODE RESP-CODE DATA-IN DATA-OUT.               
      * MAIN PROCESS
       MAIN.
           EVALUATE CMD-CODE
               WHEN 1
                   PERFORM CMD-01
               WHEN 2
                   PERFORM CMD-02
               WHEN 3
                   PERFORM CMD-03
               WHEN 4
                   PERFORM CMD-04
               WHEN 5
                   PERFORM CMD-05
               WHEN OTHER
                   MOVE 99 TO RESP-CODE
           END-EVALUATE.
           GOBACK.           
       CMD-01.
           MOVE DATA-IN TO RESUME-MASTER-RECORD.
           
           EXEC SQL                                                
                   SELECT                                                
                    RESUME                              
                   INTO          
                      :WS-RESUME
                   FROM 
                       EMP_RESUME
                   WHERE 
                       EMPNO = :WS-EMPNO
           END-EXEC.                                               
                                                                        
           EVALUATE SQLCODE                                        
               WHEN 0                                              
                   MOVE RESUME-MASTER-RECORD    TO DATA-OUT                                       
                   MOVE 0                       TO RESP-CODE
               WHEN OTHER                
                   MOVE SPACES                  TO DATA-OUT                                        
                   MOVE 1                       TO RESP-CODE
           END-EVALUATE.                                           
       CMD-02.
      * OK
           EXEC SQL
                   SELECT COUNT(*)
                   FROM
                       V32_APTRSCLT
                   WHERE
                       RESTART_ID = 1 AND 
                       RECORD_TYPE = 2 AND 
                       AGENT_ID = 3
           END-EXEC
           
           EVALUATE SQLCODE                                        
               WHEN 0                                              
                   MOVE SPACES                  TO DATA-OUT                                       
                   MOVE 0                       TO RESP-CODE
               WHEN OTHER                
                   MOVE SPACES                  TO DATA-OUT                                        
                   MOVE 1                       TO RESP-CODE
           END-EVALUATE.                                           

       CMD-03.
      * KO
           EXEC SQL
                   SELECT COUNT(*)
                   FROM
                       V32_APTRSCLT
                   WHERE
                       RECORD_TYPE = 2 AND 
                       AGENT_ID = 3
           END-EXEC
           
           EVALUATE SQLCODE                                        
               WHEN 0                                              
                   MOVE SPACES                  TO DATA-OUT                                       
                   MOVE 0                       TO RESP-CODE
               WHEN OTHER                
                   MOVE SPACES                  TO DATA-OUT                                        
                   MOVE 1                       TO RESP-CODE
           END-EVALUATE.       
           
       CMD-04.
      * KO
           EXEC SQL
                   SELECT COUNT(*)
                   FROM
                       V32_APTRSCLT
                   WHERE
                       AGENT_ID = 3
           END-EXEC
           
           EVALUATE SQLCODE                                        
               WHEN 0                                              
                   MOVE SPACES                  TO DATA-OUT                                       
                   MOVE 0                       TO RESP-CODE
               WHEN OTHER                
                   MOVE SPACES                  TO DATA-OUT                                        
                   MOVE 1                       TO RESP-CODE
           END-EVALUATE.                                           

       CMD-05.
      * KO
           EXEC SQL
                   SELECT COUNT(*)
                   FROM
                       V32_APTRSCLT
                   WHERE
                       CHAR(RESTART_ID) = '1' AND 
                       RECORD_TYPE = 2 AND 
                       AGENT_ID = 3
           END-EXEC
           
           EVALUATE SQLCODE                                        
               WHEN 0                                              
                   MOVE SPACES                  TO DATA-OUT                                       
                   MOVE 0                       TO RESP-CODE
               WHEN OTHER                
                   MOVE SPACES                  TO DATA-OUT                                        
                   MOVE 1                       TO RESP-CODE
           END-EVALUATE.                                           
