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
        DLabel       $usable-memory-start      
        DLabel       $global-memory-block      
        DataZ        1                         
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
        Add                                    %% test1
        PushI        1                         
        StoreC                                 
        PushD        $global-memory-block      
        PushI        0                         
        Add                                    %% test1
        LoadC                                  
        JumpTrue     -print-boolean-1-true     
        PushD        $boolean-false-string     
        Jump         -print-boolean-1-join     
        Label        -print-boolean-1-true     
        PushD        $boolean-true-string      
        Label        -print-boolean-1-join     
        PushD        $print-format-boolean     
        Printf                                 
        PushD        $print-format-space       
        Printf                                 
        PushD        $global-memory-block      
        PushI        0                         
        Add                                    %% test1
        PushD        $global-memory-block      
        PushI        0                         
        Add                                    %% test1
        LoadC                                  
        BNegate                                
        StoreC                                 
        PushD        $global-memory-block      
        PushI        0                         
        Add                                    %% test1
        LoadC                                  
        JumpTrue     -print-boolean-2-true     
        PushD        $boolean-false-string     
        Jump         -print-boolean-2-join     
        Label        -print-boolean-2-true     
        PushD        $boolean-true-string      
        Label        -print-boolean-2-join     
        PushD        $print-format-boolean     
        Printf                                 
        PushD        $print-format-space       
        Printf                                 
        PushD        $global-memory-block      
        PushI        0                         
        Add                                    %% test1
        Label        -compare-3-arg1           
        PushI        4                         
        Label        -compare-3-arg2           
        PushI        5                         
        Label        -compare-3-sub            
        Subtract                               
        JumpNeg      -compare-3-false          
        Jump         -compare-3-true           
        Label        -compare-3-true           
        PushI        1                         
        Jump         -compare-3-join           
        Label        -compare-3-false          
        PushI        0                         
        Jump         -compare-3-join           
        Label        -compare-3-join           
        BNegate                                
        StoreC                                 
        PushD        $global-memory-block      
        PushI        0                         
        Add                                    %% test1
        LoadC                                  
        JumpTrue     -print-boolean-4-true     
        PushD        $boolean-false-string     
        Jump         -print-boolean-4-join     
        Label        -print-boolean-4-true     
        PushD        $boolean-true-string      
        Label        -print-boolean-4-join     
        PushD        $print-format-boolean     
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        Halt                                   
