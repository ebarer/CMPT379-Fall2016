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
        DataZ        4                         
        DLabel       -stringConstant-2-        
        DataC        116                       %% "true statement"
        DataC        114                       
        DataC        117                       
        DataC        101                       
        DataC        32                        
        DataC        115                       
        DataC        116                       
        DataC        97                        
        DataC        116                       
        DataC        101                       
        DataC        109                       
        DataC        101                       
        DataC        110                       
        DataC        116                       
        DataC        0                         
        DLabel       -stringConstant-4-        
        DataC        101                       %% "elseif statement"
        DataC        108                       
        DataC        115                       
        DataC        101                       
        DataC        105                       
        DataC        102                       
        DataC        32                        
        DataC        115                       
        DataC        116                       
        DataC        97                        
        DataC        116                       
        DataC        101                       
        DataC        109                       
        DataC        101                       
        DataC        110                       
        DataC        116                       
        DataC        0                         
        DLabel       -stringConstant-5-        
        DataC        102                       %% "false statement"
        DataC        97                        
        DataC        108                       
        DataC        115                       
        DataC        101                       
        DataC        32                        
        DataC        115                       
        DataC        116                       
        DataC        97                        
        DataC        116                       
        DataC        101                       
        DataC        109                       
        DataC        101                       
        DataC        110                       
        DataC        116                       
        DataC        0                         
        Label        $$general-runtime-error   
        PushD        $errors-general-message   
        Printf                                 
        Halt                                   
        Label        $$divide-by-zero          
        PushD        $errors-divide-by-zero    
        Jump         $$general-runtime-error   
        Label        $$main                    
        Label        -if-stmt-7-               
        Label        -compare-1-arg1           
        PushI        4                         
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
        JumpFalse    -if-stmt-7-else           
        PushD        $global-memory-block      
        PushI        0                         
        Add                                    %% test
        PushI        5                         
        StoreI                                 
        PushD        -stringConstant-2-        
        PushD        $print-format-string      
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        Jump         -if-stmt-7-join           
        Label        -if-stmt-7-else           
        Label        -if-stmt-6-               
        Label        -compare-3-arg1           
        PushI        9                         
        Label        -compare-3-arg2           
        PushI        10                        
        Label        -compare-3-sub            
        Subtract                               
        JumpPos      -compare-3-true           
        Jump         -compare-3-false          
        Label        -compare-3-true           
        PushI        1                         
        Jump         -compare-3-join           
        Label        -compare-3-false          
        PushI        0                         
        Jump         -compare-3-join           
        Label        -compare-3-join           
        JumpFalse    -if-stmt-6-else           
        PushD        -stringConstant-4-        
        PushD        $print-format-string      
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        Jump         -if-stmt-6-join           
        Label        -if-stmt-6-else           
        PushD        -stringConstant-5-        
        PushD        $print-format-string      
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        Label        -if-stmt-6-join           
        Label        -if-stmt-7-join           
        Halt                                   
