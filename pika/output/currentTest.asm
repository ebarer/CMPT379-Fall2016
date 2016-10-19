        Jump         $$main                    
        DLabel       $eat-location-zero        
        DataZ        8                         
        DLabel       $print-format-integer     
        DataC        37                        %% "%d"
        DataC        100                       
        DataC        0                         
        DLabel       $print-format-floating    
        DataC        37                        %% "%g"
        DataC        103                       
        DataC        0                         
        DLabel       $print-format-rational    
        DataC        37                        %% "%d_%d/%d"
        DataC        100                       
        DataC        95                        
        DataC        37                        
        DataC        100                       
        DataC        47                        
        DataC        37                        
        DataC        100                       
        DataC        0                         
        DLabel       $print-format-boolean     
        DataC        37                        %% "%s"
        DataC        115                       
        DataC        0                         
        DLabel       $print-format-character   
        DataC        37                        %% "%c"
        DataC        99                        
        DataC        0                         
        DLabel       $print-format-string      
        DataC        37                        %% "%s"
        DataC        115                       
        DataC        0                         
        DLabel       $print-format-newline     
        DataC        10                        %% "\n"
        DataC        0                         
        DLabel       $print-format-tab         
        DataC        9                         %% "\t"
        DataC        0                         
        DLabel       $print-format-space       
        DataC        32                        %% " "
        DataC        0                         
        DLabel       $boolean-true-string      
        DataC        116                       %% "true"
        DataC        114                       
        DataC        117                       
        DataC        101                       
        DataC        0                         
        DLabel       $boolean-false-string     
        DataC        102                       %% "false"
        DataC        97                        
        DataC        108                       
        DataC        115                       
        DataC        101                       
        DataC        0                         
        DLabel       $errors-general-message   
        DataC        82                        %% "Runtime error: %s\n"
        DataC        117                       
        DataC        110                       
        DataC        116                       
        DataC        105                       
        DataC        109                       
        DataC        101                       
        DataC        32                        
        DataC        101                       
        DataC        114                       
        DataC        114                       
        DataC        111                       
        DataC        114                       
        DataC        58                        
        DataC        32                        
        DataC        37                        
        DataC        115                       
        DataC        10                        
        DataC        0                         
        DLabel       $errors-divide-by-zero    
        DataC        100                       %% "division by zero"
        DataC        105                       
        DataC        118                       
        DataC        105                       
        DataC        115                       
        DataC        105                       
        DataC        111                       
        DataC        110                       
        DataC        32                        
        DataC        98                        
        DataC        121                       
        DataC        32                        
        DataC        122                       
        DataC        101                       
        DataC        114                       
        DataC        111                       
        DataC        0                         
        DLabel       $rational-temp-numerator-1 
        DataI        0                         
        DLabel       $rational-temp-denominator-1 
        DataI        0                         
        DLabel       $rational-temp-numerator-2 
        DataI        0                         
        DLabel       $rational-temp-denominator-2 
        DataI        0                         
        DLabel       $usable-memory-start      
        DLabel       $global-memory-block      
        DataZ        25                        
        Label        $$general-runtime-error   
        PushD        $errors-general-message   
        Printf                                 
        Halt                                   
        Label        $$divide-by-zero          
        PushD        $errors-divide-by-zero    
        Jump         $$general-runtime-error   
        Label        $$main                    
        PushD        $global-memory-block      
        PushI        0                         
        Add                                    %% intConvert
        PushI        15096                     
        PushI        127                       
        BTAnd                                  
        StoreC                                 
        PushD        $global-memory-block      
        PushI        0                         
        Add                                    %% intConvert
        LoadC                                  
        PushD        $print-format-character   
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        PushD        $global-memory-block      
        PushI        1                         
        Add                                    %% myRat1
        PushD        $rational-temp-numerator-1 
        PushI        3                         
        StoreI                                 
        PushD        $rational-temp-denominator-1 
        PushI        8                         
        StoreI                                 
        Duplicate                              
        PushI        4                         
        Add                                    
        PushD        $rational-temp-denominator-1 
        LoadI                                  
        StoreI                                 
        PushD        $rational-temp-numerator-1 
        LoadI                                  
        StoreI                                 
        PushD        $global-memory-block      
        PushI        9                         
        Add                                    %% myRat2
        PushD        $rational-temp-numerator-1 
        PushI        5                         
        StoreI                                 
        PushD        $rational-temp-denominator-1 
        PushI        10                        
        StoreI                                 
        Duplicate                              
        PushI        4                         
        Add                                    
        PushD        $rational-temp-denominator-1 
        LoadI                                  
        StoreI                                 
        PushD        $rational-temp-numerator-1 
        LoadI                                  
        StoreI                                 
        PushD        $global-memory-block      
        PushI        1                         
        Add                                    %% myRat1
        Duplicate                              
        PushI        4                         
        Add                                    
        LoadI                                  
        PushD        $rational-temp-denominator-1 
        Exchange                               
        StoreI                                 
        LoadI                                  
        PushD        $rational-temp-numerator-1 
        Exchange                               
        StoreI                                 
        PushD        $rational-temp-denominator-1 
        LoadI                                  
        PushD        $rational-temp-numerator-1 
        LoadI                                  
        PushD        $rational-temp-denominator-1 
        LoadI                                  
        Remainder                              
        Duplicate                              
        JumpFalse    -print-rational-1-skip-fraction 
        PushD        $rational-temp-numerator-1 
        LoadI                                  
        PushD        $rational-temp-denominator-1 
        LoadI                                  
        Divide                                 
        PushD        $print-format-rational    
        Printf                                 
        PushD        $print-format-space       
        Printf                                 
        PushD        $global-memory-block      
        PushI        9                         
        Add                                    %% myRat2
        Duplicate                              
        PushI        4                         
        Add                                    
        LoadI                                  
        PushD        $rational-temp-denominator-1 
        Exchange                               
        StoreI                                 
        LoadI                                  
        PushD        $rational-temp-numerator-1 
        Exchange                               
        StoreI                                 
        PushD        $rational-temp-denominator-1 
        LoadI                                  
        PushD        $rational-temp-numerator-1 
        LoadI                                  
        PushD        $rational-temp-denominator-1 
        LoadI                                  
        Remainder                              
        Duplicate                              
        JumpFalse    -print-rational-2-skip-fraction 
        PushD        $rational-temp-numerator-1 
        LoadI                                  
        PushD        $rational-temp-denominator-1 
        LoadI                                  
        Divide                                 
        PushD        $print-format-rational    
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        PushD        $global-memory-block      
        PushI        17                        
        Add                                    %% test
        PushD        $global-memory-block      
        PushI        1                         
        Add                                    %% myRat1
        Duplicate                              
        PushI        4                         
        Add                                    
        LoadI                                  
        PushD        $rational-temp-denominator-1 
        Exchange                               
        StoreI                                 
        LoadI                                  
        PushD        $rational-temp-numerator-1 
        Exchange                               
        StoreI                                 
        PushD        $global-memory-block      
        PushI        9                         
        Add                                    %% myRat2
        Duplicate                              
        PushI        4                         
        Add                                    
        LoadI                                  
        PushD        $rational-temp-denominator-1 
        Exchange                               
        StoreI                                 
        LoadI                                  
        PushD        $rational-temp-numerator-1 
        Exchange                               
        StoreI                                 
        Add                                    
        Duplicate                              
        PushI        4                         
        Add                                    
        PushD        $rational-temp-denominator-1 
        LoadI                                  
        StoreI                                 
        PushD        $rational-temp-numerator-1 
        LoadI                                  
        StoreI                                 
        PushD        $global-memory-block      
        PushI        17                        
        Add                                    %% test
        Duplicate                              
        PushI        4                         
        Add                                    
        LoadI                                  
        PushD        $rational-temp-denominator-1 
        Exchange                               
        StoreI                                 
        LoadI                                  
        PushD        $rational-temp-numerator-1 
        Exchange                               
        StoreI                                 
        PushD        $rational-temp-denominator-1 
        LoadI                                  
        PushD        $rational-temp-numerator-1 
        LoadI                                  
        PushD        $rational-temp-denominator-1 
        LoadI                                  
        Remainder                              
        Duplicate                              
        JumpFalse    -print-rational-3-skip-fraction 
        PushD        $rational-temp-numerator-1 
        LoadI                                  
        PushD        $rational-temp-denominator-1 
        LoadI                                  
        Divide                                 
        PushD        $print-format-rational    
        Printf                                 
        Halt                                   
