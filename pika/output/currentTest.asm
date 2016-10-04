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
        DataZ        19                        
        DLabel       -stringConstant-2-myStr   
        DataC        69                        %% "Elliot is the best."
        DataC        108                       
        DataC        108                       
        DataC        105                       
        DataC        111                       
        DataC        116                       
        DataC        32                        
        DataC        105                       
        DataC        115                       
        DataC        32                        
        DataC        116                       
        DataC        104                       
        DataC        101                       
        DataC        32                        
        DataC        98                        
        DataC        101                       
        DataC        115                       
        DataC        116                       
        DataC        46                        
        DataC        0                         
        DLabel       -stringConstant-3-myStr   
        DataC        69                        %% "Elliot is better than Farzin."
        DataC        108                       
        DataC        108                       
        DataC        105                       
        DataC        111                       
        DataC        116                       
        DataC        32                        
        DataC        105                       
        DataC        115                       
        DataC        32                        
        DataC        98                        
        DataC        101                       
        DataC        116                       
        DataC        116                       
        DataC        101                       
        DataC        114                       
        DataC        32                        
        DataC        116                       
        DataC        104                       
        DataC        97                        
        DataC        110                       
        DataC        32                        
        DataC        70                        
        DataC        97                        
        DataC        114                       
        DataC        122                       
        DataC        105                       
        DataC        110                       
        DataC        46                        
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
        Add                                    %% char1
        PushI        101                       
        StoreC                                 
        PushD        $global-memory-block      
        PushI        1                         
        Add                                    %% float1
        PushF        4.500000                  
        StoreF                                 
        PushD        $global-memory-block      
        PushI        9                         
        Add                                    %% bool1
        PushI        1                         
        StoreC                                 
        PushD        $global-memory-block      
        PushI        10                        
        Add                                    %% sum1
        PushI        -2                        
        PushF        4.300000                  
        PushF        3.600000                  
        FAdd                                   
        ConvertI                               
        Multiply                               
        StoreI                                 
        PushD        $global-memory-block      
        PushI        14                        
        Add                                    %% less_equal
        Label        -compare-1-arg1           
        PushI        4                         
        Label        -compare-1-arg2           
        PushI        5                         
        Label        -compare-1-sub            
        Subtract                               
        JumpPos      -compare-1-false          
        Jump         -compare-1-true           
        Label        -compare-1-true           
        PushI        1                         
        Jump         -compare-1-join           
        Label        -compare-1-false          
        PushI        0                         
        Jump         -compare-1-join           
        Label        -compare-1-join           
        StoreC                                 
        PushD        $global-memory-block      
        PushI        15                        
        Add                                    %% myStr
        PushD        -stringConstant-2-myStr   
        StoreI                                 
        PushD        $global-memory-block      
        PushI        15                        
        Add                                    %% myStr
        LoadI                                  
        PushD        $print-format-string      
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        PushD        $global-memory-block      
        PushI        15                        
        Add                                    %% myStr
        PushD        -stringConstant-3-myStr   
        StoreI                                 
        PushD        $global-memory-block      
        PushI        15                        
        Add                                    %% myStr
        LoadI                                  
        PushD        $print-format-string      
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        Halt                                   
