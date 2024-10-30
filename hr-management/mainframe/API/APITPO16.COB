      *-----------------------------------------------------------------
      *  APITPO16 - TECHNICAL PROGRAM TO ACCESS DATA NEEDED BY COBCIO16.
      *-----------------------------------------------------------------
      *                                                                 
      *--------------------PART OF MYTELCO HR APPLICATION-----------
      *                                                                 
      *-----------------------------------------------------------------
       IDENTIFICATION DIVISION.                                         
       PROGRAM-ID.   APITP016.                                          
       AUTHOR.       CAST SOFTWARE                                      
       DATE-WRITTEN. FEBRUARY 2012.                                       
                                                                        
       EJECT                                                            
       ENVIRONMENT DIVISION.                                            
       DATA DIVISION.                                                   
                                                                        
       WORKING-STORAGE SECTION.                                         
                                                                        
           COPY DFHBMSCA.                                               
                                                                        
           COPY DFHAID.  
           
           COPY DEVELOPM.

           EXEC SQL
               INCLUDE DEVELOPER
           END-EXEC.

           EXEC SQL 
               INCLUDE SQLCA  
           END-EXEC.                       
                                                                        
       01  DEVELOPER-MASTER-RECORD.                                      
           10 WS-DEVELOPER-ID         PIC X(6).
           10 WS-HARDWARE-ID          PIC X(10).
           10 WS-SKILL-LEVEL          PIC S9(7)V9(2) USAGE COMP-3.
           10 WS-SALARY               PIC S9(7)V9(2) USAGE COMP-3. 
           10 WS-EXPERIENCE           PIC S9(7)V9(2) USAGE COMP-3.            

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
           MOVE DATA-IN TO DEVELOPER-MASTER-RECORD.
           
           EXEC SQL                                                
                  SELECT                                                
                      SALARY                              
                  INTO          
                      :WS-SALARY
                  FROM 
                       DEVELOPER
                  WHERE 
                       DEVELOPER_ID = :WS-DEVELOPER-ID
                   AND HARDWARE_ID  = :WS-HARDWARE-ID
           END-EXEC.                                               
                                                                        
           EVALUATE SQLCODE                                        
               WHEN 0                                              
                   MOVE DEVELOPER-MASTER-RECORD TO DATA-OUT                                       
                   MOVE 0                       TO RESP-CODE
               WHEN OTHER                
                   MOVE SPACES                  TO DATA-OUT                                        
                   MOVE 1                       TO RESP-CODE
           END-EVALUATE.                                           
