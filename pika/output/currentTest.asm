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
        DataZ        8                         
        DLabel       -stringConstant-3-i       
        DataC        45                        %% "-"
        DataC        0                         
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
        Add                                    %% i
        PushI        0                         
        StoreI                                 
        Label        -while-stmt-5-loop        
        Label        -compare-1-arg1           
        PushD        $global-memory-block      
        PushI        0                         
        Add                                    %% i
        LoadI                                  
        Label        -compare-1-arg2           
        PushI        5                         
        Label        -compare-1-sub            
        Subtract                               
        JumpPos      -compare-1-true           
        Jump         -compare-1-false          
        Label        -compare-1-true           
        PushI        1                         
        Jump         -compare-1-join           
        Label        -compare-1-false          
        PushI        0                         
        Jump         -compare-1-join           
        Label        -compare-1-join           
        JumpFalse    -while-stmt-5-join        
        PushD        $global-memory-block      
        PushI        4                         
        Add                                    %% j
        PushI        0                         
        StoreI                                 
        Label        -while-stmt-4-loop        
        Label        -compare-2-arg1           
        PushD        $global-memory-block      
        PushI        4                         
        Add                                    %% j
        LoadI                                  
        Label        -compare-2-arg2           
        PushI        3                         
        Label        -compare-2-sub            
        Subtract                               
        JumpNeg      -compare-2-true           
        Jump         -compare-2-false          
        Label        -compare-2-true           
        PushI        1                         
        Jump         -compare-2-join           
        Label        -compare-2-false          
        PushI        0                         
        Jump         -compare-2-join           
        Label        -compare-2-join           
        JumpFalse    -while-stmt-4-join        
        PushD        $global-memory-block      
        PushI        0                         
        Add                                    %% i
        LoadI                                  
        PushD        $print-format-integer     
        Printf                                 
        PushD        $print-format-space       
        Printf                                 
        PushD        -stringConstant-3-i       
        PushD        $print-format-string      
        Printf                                 
        PushD        $print-format-space       
        Printf                                 
        PushD        $global-memory-block      
        PushI        4                         
        Add                                    %% j
        LoadI                                  
        PushD        $print-format-integer     
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        PushD        $global-memory-block      
        PushI        4                         
        Add                                    %% j
        PushD        $global-memory-block      
        PushI        4                         
        Add                                    %% j
        LoadI                                  
        PushI        1                         
        Add                                    
        StoreI                                 
        Jump         -while-stmt-4-loop        
        Label        -while-stmt-4-join        
        PushD        $global-memory-block      
        PushI        0                         
        Add                                    %% i
        PushD        $global-memory-block      
        PushI        0                         
        Add                                    %% i
        LoadI                                  
        PushI        1                         
        Add                                    
        StoreI                                 
        Jump         -while-stmt-5-loop        
        Label        -while-stmt-5-join        
        Halt                                   
