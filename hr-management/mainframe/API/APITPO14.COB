      *-----------------------------------------------------------------
      *  APITPO14 - TECHNICAL PROGRAM TO ACCESS DATA NEEDED BY COBCIO14.
      *-----------------------------------------------------------------
      *                                                                 
      *--------------------PART OF MYTELCO HR APPLICATION-----------
      *                                                                 
      *-----------------------------------------------------------------
       IDENTIFICATION DIVISION.                                         
       PROGRAM-ID.   APITP014.                                          
       AUTHOR.       CAST SOFTWARE                                      
       DATE-WRITTEN. FEBRUARY 2012.                                       
                                                                        
       EJECT                                                            
       ENVIRONMENT DIVISION.                                            
       DATA DIVISION.                                                   
                                                                        
       WORKING-STORAGE SECTION.                                         
                                                                        
           COPY DFHBMSCA.                                               
                                                                        
           COPY DFHAID.  
           
           COPY LEAVEM.

           EXEC SQL
             INCLUDE LEAVE
           END-EXEC.

           EXEC SQL 
               INCLUDE SQLCA  
           END-EXEC.                       
                                                                        
       01  LEAVE-MASTER-RECORD.                                      
           10 WS-EMP-NO               PIC X(6).
           10 WS-LYEARMONTH           PIC X(6).
           10 WS-LAVAILABLE           PIC S9(4) USAGE COMP.
           10 WS-LTAKEN               PIC S9(4) USAGE COMP. 
           10 WS-LBALANCE             PIC S9(4) USAGE COMP.            
           10 WS-WORKINGDAYS          PIC S9(4) USAGE COMP.

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
           MOVE DATA-IN TO LEAVE-MASTER-RECORD.
           
           EXEC SQL                                                
               SELECT                                                
                   LAVAILABLE                              
               INTO          
                   :WS-LAVAILABLE                                        
               FROM 
                   LEAVE                                              
               WHERE 
                   LYEARMONTH = :WS-LYEARMONTH
               AND EMPNO      = :WS-EMP-NO
           END-EXEC.                                               
                                                                        
           EVALUATE SQLCODE                                        
               WHEN 0                                              
                   MOVE LEAVE-MASTER-RECORD TO DATA-OUT                                       
                   MOVE 0                   TO RESP-CODE
               WHEN OTHER                
                   MOVE SPACES              TO DATA-OUT                                        
                   MOVE 1                   TO RESP-CODE
           END-EVALUATE.                                           
