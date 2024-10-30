      *-----------------------------------------------------------------
      *  APIACC - TECHNICAL PROGRAM TO ACCESS DATA NEEDED BY COBACC.
      *-----------------------------------------------------------------
      *                                                                 
      *--------------------PART OF MYTELCO HR APPLICATION-----------
      *                                                                 
      *-----------------------------------------------------------------
       IDENTIFICATION DIVISION.                                         
       PROGRAM-ID.   APIACC.                                          
       AUTHOR.       CAST SOFTWARE                                      
       DATE-WRITTEN. FEBRUARY 2012.                                       
                                                                        
       EJECT                                                            
       ENVIRONMENT DIVISION.                                            
       DATA DIVISION.                                                   
                                                                        
       WORKING-STORAGE SECTION.                                         
                                                                        
           EXEC SQL 
              INCLUDE ACCOUNT                             
           END-EXEC.                                               

           EXEC SQL 
               INCLUDE SQLCA  
           END-EXEC.                       
                                                                        
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
           MOVE DATA-IN TO DCL-ACCOUNT.
           
      *     EXEC SQL                                                
      *            SELECT                                                
      *              ACC_TOTAL                                            
      *            INTO
      *              :W-ACC-TOTAL
      *            FROM ACCOUNT                                           
      *            WHERE CUST_NO = :W-CUST-NO AND ACC_NO = :W-ACC-NO                        
      *            FETCH FIRST ROW ONLY
      *     END-EXEC.                                               
       COPY CPACCPRO.
       
           EVALUATE SQLCODE                                        
               WHEN 0                                              
                   MOVE DCL-ACCOUNT             TO DATA-OUT                                       
                   MOVE 0                       TO RESP-CODE
               WHEN OTHER                
                   MOVE SPACES                  TO DATA-OUT                                        
                   MOVE 1                       TO RESP-CODE
           END-EVALUATE.                                           
