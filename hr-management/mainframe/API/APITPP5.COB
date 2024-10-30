      *-----------------------------------------------------------------
      *  APITPP5 - TECHNICAL PROGRAM TO ACCESS DATA NEEDED BY COBCIOP4.
      *-----------------------------------------------------------------
      *                                                                 
      *--------------------PART OF MYTELCO HR APPLICATION-----------
      *                                                                 
      *-----------------------------------------------------------------
       IDENTIFICATION DIVISION.                                         
       PROGRAM-ID.   APITPP5.                                          
       AUTHOR.       CAST SOFTWARE                                      
       DATE-WRITTEN. FEBRUARY 2012.                                       
                                                                        
       EJECT                                                            
       ENVIRONMENT DIVISION.                                            
       DATA DIVISION.                                                   
                                                                        
       WORKING-STORAGE SECTION.                                         
                                                                        
           COPY DFHBMSCA.                                               
                                                                        
           COPY DFHAID.  
           
           COPY PRJACTM.

           EXEC SQL
              INCLUDE EMPRJACT
           END-EXEC.

           EXEC SQL 
               INCLUDE SQLCA  
           END-EXEC.                       
                                                                        
       01  PROJECT-MASTER-RECORD.                                      
      *        ** key field                                             
           03  EMP-NO                      PIC X(6).                    
      *    03  PROJ-NO                     PIC X(6) VALUE SPACES.                    
      *    03  ACT-NO                      PIC S9(2).                    
      *    03  EMPTIME                     PIC S9(5).                   
      *        ** format (yymmdd)                                       
           03  ACT-DATE-STARTED            PIC 9(6) VALUE 0.                    
      *        ** format (yymmdd)                                        
           03  ACT-DATE-ENDED              PIC 9(6) VALUE 0.                    
                                                                        
           03  PROJ-NAME                   PIC X(24) VALUE SPACES.
           03  DEPTNO                      PIC X(3) VALUE SPACES.
           03  PROJ-EMP                    PIC X(6) VALUE SPACES.
           03  PROJ-STARTDATE              PIC 9(6) VALUE 0.
           03  PROJ-ENDDATE                PIC 9(6) VALUE 0.

       01  DEPT-MASTER-RECORD.                                          
      *        ** key field                                             
           03  DEPT-CODE                   PIC X(4).                    
           03  DEPT-DESCRIPTION            PIC X(30).                   
      *        ** format (yyddd) packed                                 
           03  DEPT-DATE-MAINTAINED        PIC 9(5) COMP-3.             
           03  FILLER                      PIC X(43).                   

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
               WHEN OTHER
                   MOVE 99 TO RESP-CODE
           END-EVALUATE.
           GOBACK.           
       CMD-01.
           MOVE DATA-IN TO PROJECT-MASTER-RECORD.
           
           EXEC SQL                                                
                  SELECT                                                
                      ACT_NO                                        
                    , EMPTIME                                         
                    , ESTARTDATE                                        
                    , EENDDATE                                        
                  INTO          
                      :ACT-NO                                        
                    , :EMPTIME                                    
                    , :ESTARTDATE                                   
                    , :EENDDATE                                   
                  FROM 
                       EMP_PROJ_ACT                                              
                  WHERE 
                       PROJ_NO = :WS-PROJ-NO
                   AND EMPNO   = :WS-EMPNO
           END-EXEC.                                               
                                                                        
           EVALUATE SQLCODE                                        
               WHEN 0                                              
                   MOVE PROJECT-MASTER-RECORD   TO DATA-OUT                                       
                   MOVE 0                       TO RESP-CODE
               WHEN 100                                              
                   MOVE PROJECT-MASTER-RECORD   TO DATA-OUT                                       
                   MOVE 100                     TO RESP-CODE
               WHEN OTHER                
                   MOVE SPACES                  TO DATA-OUT                                        
                   MOVE 1                       TO RESP-CODE
           END-EVALUATE.                                           
       CMD-02.
           MOVE DATA-IN TO PROJECT-MASTER-RECORD.
           
           EXEC SQL                                                
                UPDATE                                           
                    EMP_PROJ_ACT                                 
                SET 
                    ACT_NO         = :ACT-NO       
                  , EMPTIME        = :EMPTIME     
                  , ESTARTDATE     = :ESTARTDATE       
                  , EENDDATE       = :EENDDATE      
                WHERE
                (
                      EMP_PROJ_ACT.EMPNO   = :WS-EMPNO
                  AND EMP_PROJ_ACT.PROJ_NO = :WS-PROJ-NO
                )
           END-EXEC.                                               
                                                                        
           EVALUATE SQLCODE                                        
               WHEN 0                                              
                   MOVE PROJECT-MASTER-RECORD   TO DATA-OUT                                       
                   MOVE 0                       TO RESP-CODE
               WHEN OTHER                
                   MOVE SPACES                  TO DATA-OUT                                        
                   MOVE 1                       TO RESP-CODE
           END-EVALUATE.                                           
