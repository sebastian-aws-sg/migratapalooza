      *-----------------------------------------------------------------
      *  APITPO19 - TECHNICAL PROGRAM TO ACCESS DATA NEEDED BY COBCIO19.
      *-----------------------------------------------------------------
      *                                                                 
      *--------------------PART OF MYTELCO HR APPLICATION-----------
      *                                                                 
      *-----------------------------------------------------------------
       IDENTIFICATION DIVISION.                                         
       PROGRAM-ID.   APITP019.                                          
       AUTHOR.       CAST SOFTWARE                                      
       DATE-WRITTEN. FEBRUARY 2012.                                       
                                                                        
       EJECT                                                            
       ENVIRONMENT DIVISION.                                            
       DATA DIVISION.                                                   
                                                                        
       WORKING-STORAGE SECTION.                                         
                                                                        
           COPY DFHBMSCA.                                               
                                                                        
           COPY DFHAID.  
           
           COPY ACTIVM.

           EXEC SQL
              INCLUDE ACTIVITY
           END-EXEC.

           EXEC SQL 
               INCLUDE SQLCA  
           END-EXEC.                       
                                                                        
       01  ACTIVITY-MASTER-RECORD.                                      
           10 WS-ACT-NO               PIC S9(4) USAGE COMP.
           10 WS-ACT-KEYWORD          PIC X(6).
           10 WS-ACT-DESC             PIC X(20).

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
               WHEN OTHER
                   MOVE 99 TO RESP-CODE
           END-EVALUATE.
           GOBACK.           
       CMD-01.
           MOVE DATA-IN TO ACTIVITY-MASTER-RECORD.
           
           EXEC SQL                                                
                  SELECT                                                
                      ACT_KEYWORD
                      ACT_DESC
                  INTO          
                      :WS-ACT-KEYWORD
                      :WS-ACT-DESC
                  FROM 
                       ACTIVITY
                  WHERE 
                       ACT_NO      = :WS-ACT-NO
           END-EXEC.                                               
                                                                        
           EVALUATE SQLCODE                                        
               WHEN 0                                              
                   MOVE ACTIVITY-MASTER-RECORD  TO DATA-OUT                                       
                   MOVE 0                       TO RESP-CODE
               WHEN OTHER                
                   MOVE SPACES                  TO DATA-OUT                                        
                   MOVE 1                       TO RESP-CODE
           END-EVALUATE.                                           
