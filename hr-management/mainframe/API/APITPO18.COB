      *-----------------------------------------------------------------
      *  APITPO18 - TECHNICAL PROGRAM TO ACCESS DATA NEEDED BY COBCIO18.
      *-----------------------------------------------------------------
      *                                                                 
      *--------------------PART OF MYTELCO HR APPLICATION-----------
      *                                                                 
      *-----------------------------------------------------------------
       IDENTIFICATION DIVISION.                                         
       PROGRAM-ID.   APITP018.                                          
       AUTHOR.       CAST SOFTWARE                                      
       DATE-WRITTEN. FEBRUARY 2012.                                       
                                                                        
       EJECT                                                            
       ENVIRONMENT DIVISION.                                            
       DATA DIVISION.                                                   
                                                                        
       WORKING-STORAGE SECTION.                                         
                                                                        
           COPY DFHBMSCA.                                               
                                                                        
           COPY DFHAID.  
           
           COPY SUBSIDM.

           EXEC SQL
               INCLUDE SUBSIDIARY
           END-EXEC.

           EXEC SQL 
               INCLUDE SQLCA  
           END-EXEC.                       
                                                                        
       01  SUBSID-MASTER-RECORD.                                      
           10 WS-SUBID                PIC X(4).
           10 WS-SUBNAME              PIC X(10).
           10 WS-SUBPASSWORD          PIC X(16).

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
           MOVE DATA-IN TO SUBSID-MASTER-RECORD.
           
           EXEC SQL                                                
                  SELECT                                                
                    SUBNAME                             
                  INTO          
                      :WS-SUBNAME
                  FROM 
                       SUBSIDIARY
                  WHERE 
                       SUBID = :WS-SUBID
           END-EXEC.                                               
                                                                        
           EVALUATE SQLCODE                                        
               WHEN 0                                              
                   MOVE SUBSID-MASTER-RECORD    TO DATA-OUT                                       
                   MOVE 0                       TO RESP-CODE
               WHEN OTHER                
                   MOVE SPACES                  TO DATA-OUT                                        
                   MOVE 1                       TO RESP-CODE
           END-EVALUATE.                                           
