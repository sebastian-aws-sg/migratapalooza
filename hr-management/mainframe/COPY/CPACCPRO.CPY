      * This copybook contains the access to the database
           EXEC SQL                                                
                  SELECT                                                
                    ACC_TOTAL                                            
                  INTO
                    :W-ACC-TOTAL
                  FROM ACCOUNT                                           
                  WHERE CUST_NO = :W-CUST-NO AND ACC_NO = :W-ACC-NO                        
                  FETCH FIRST ROW ONLY
           END-EXEC.                                               
