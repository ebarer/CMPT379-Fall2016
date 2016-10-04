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
        Label        $$general-runtime-error   
        PushD        $errors-general-message   
        Printf                                 
        Halt                                   
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
        Label        $$divide-by-zero          
        PushD        $errors-divide-by-zero    
        Jump         $$general-runtime-error   
        DLabel       $usable-memory-start      
        DLabel       $global-memory-block      
        DataZ        32                        
        Label        $$main                    
        PushD        $global-memory-block      
        PushI        0                         
        Add                                    %% sum1
        PushI        -2                        
        PushF        4.300000                  
        PushF        3.600000                  
        FAdd                                   
        ConvertI                               
        Multiply                               
        StoreI                                 
        PushD        $global-memory-block      
        PushI        4                         
        Add                                    %% sum2
        PushI        -2                        
        PushF        4.300000                  
        PushF        3.700000                  
        FAdd                                   
        ConvertI                               
        Multiply                               
        StoreI                                 
        PushD        $global-memory-block      
        PushI        0                         
        Add                                    %% sum1
        LoadI                                  
        PushD        $print-format-integer     
        Printf                                 
        PushD        $print-format-space       
        Printf                                 
        PushD        $global-memory-block      
        PushI        4                         
        Add                                    %% sum2
        LoadI                                  
        PushD        $print-format-integer     
        Printf                                 
        PushD        $print-format-space       
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        PushD        $global-memory-block      
        PushI        8                         
        Add                                    %% sum3
        PushD        $global-memory-block      
        PushI        0                         
        Add                                    %% sum1
        LoadI                                  
        PushD        $global-memory-block      
        PushI        4                         
        Add                                    %% sum2
        LoadI                                  
        Multiply                               
        StoreI                                 
        PushD        $global-memory-block      
        PushI        12                        
        Add                                    %% sum4
        PushD        $global-memory-block      
        PushI        0                         
        Add                                    %% sum1
        LoadI                                  
        PushD        $global-memory-block      
        PushI        4                         
        Add                                    %% sum2
        LoadI                                  
        Multiply                               
        PushF        4.300000                  
        PushF        3.600000                  
        FAdd                                   
        ConvertI                               
        PushF        4.300000                  
        PushF        3.700000                  
        FAdd                                   
        ConvertI                               
        Duplicate                              
        JumpFalse    $$divide-by-zero          
        Divide                                 
        Add                                    
        StoreI                                 
        PushD        $global-memory-block      
        PushI        8                         
        Add                                    %% sum3
        LoadI                                  
        PushD        $print-format-integer     
        Printf                                 
        PushD        $print-format-space       
        Printf                                 
        PushD        $global-memory-block      
        PushI        12                        
        Add                                    %% sum4
        LoadI                                  
        PushD        $print-format-integer     
        Printf                                 
        PushD        $print-format-space       
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        PushD        $global-memory-block      
        PushI        16                        
        Add                                    %% myVar1
        PushI        97                        
        StoreI                                 
        PushD        $global-memory-block      
        PushI        20                        
        Add                                    %% myVar2
        PushD        $global-memory-block      
        PushI        16                        
        Add                                    %% myVar1
        LoadI                                  
        Nop                                    
        StoreC                                 
        PushD        $global-memory-block      
        PushI        16                        
        Add                                    %% myVar1
        LoadI                                  
        PushD        $print-format-integer     
        Printf                                 
        PushD        $print-format-space       
        Printf                                 
        PushD        $global-memory-block      
        PushI        20                        
        Add                                    %% myVar2
        LoadC                                  
        PushD        $print-format-character   
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        PushD        $global-memory-block      
        PushI        16                        
        Add                                    %% myVar1
        PushD        $global-memory-block      
        PushI        16                        
        Add                                    %% myVar1
        LoadI                                  
        PushI        4                         
        Add                                    
        StoreI                                 
        PushD        $global-memory-block      
        PushI        20                        
        Add                                    %% myVar2
        PushD        $global-memory-block      
        PushI        16                        
        Add                                    %% myVar1
        LoadI                                  
        Nop                                    
        StoreC                                 
        PushD        $global-memory-block      
        PushI        16                        
        Add                                    %% myVar1
        LoadI                                  
        PushD        $print-format-integer     
        Printf                                 
        PushD        $print-format-space       
        Printf                                 
        PushD        $global-memory-block      
        PushI        20                        
        Add                                    %% myVar2
        LoadC                                  
        PushD        $print-format-character   
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        PushD        $global-memory-block      
        PushI        21                        
        Add                                    %% val1
        PushI        1                         
        StoreI                                 
        PushD        $global-memory-block      
        PushI        25                        
        Add                                    %% test1
        PushD        $global-memory-block      
        PushI        21                        
        Add                                    %% val1
        LoadI                                  
        Nop                                    
        StoreC                                 
        PushD        $global-memory-block      
        PushI        26                        
        Add                                    %% val2
        PushI        0                         
        Nop                                    
        StoreC                                 
        PushD        $global-memory-block      
        PushI        27                        
        Add                                    %% test2
        PushD        $global-memory-block      
        PushI        26                        
        Add                                    %% val2
        LoadC                                  
        Nop                                    
        StoreC                                 
        PushD        $global-memory-block      
        PushI        21                        
        Add                                    %% val1
        LoadI                                  
        PushD        $print-format-integer     
        Printf                                 
        PushD        $print-format-space       
        Printf                                 
        PushD        $global-memory-block      
        PushI        25                        
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
        PushD        $print-format-newline     
        Printf                                 
        PushD        $global-memory-block      
        PushI        26                        
        Add                                    %% val2
        LoadC                                  
        PushD        $print-format-character   
        Printf                                 
        PushD        $print-format-space       
        Printf                                 
        PushD        $global-memory-block      
        PushI        27                        
        Add                                    %% test2
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
        PushD        $print-format-newline     
        Printf                                 
        PushD        $global-memory-block      
        PushI        28                        
        Add                                    %% val3
        PushI        101                       
        StoreC                                 
        PushD        $global-memory-block      
        PushI        29                        
        Add                                    %% test3
        PushD        $global-memory-block      
        PushI        28                        
        Add                                    %% val3
        LoadC                                  
        Nop                                    
        StoreC                                 
        PushD        $global-memory-block      
        PushI        30                        
        Add                                    %% val4
        PushI        48                        
        StoreC                                 
        PushD        $global-memory-block      
        PushI        31                        
        Add                                    %% test4
        PushD        $global-memory-block      
        PushI        30                        
        Add                                    %% val4
        LoadC                                  
        Nop                                    
        StoreC                                 
        PushD        $global-memory-block      
        PushI        28                        
        Add                                    %% val3
        LoadC                                  
        PushD        $print-format-character   
        Printf                                 
        PushD        $print-format-space       
        Printf                                 
        PushD        $global-memory-block      
        PushI        29                        
        Add                                    %% test3
        LoadC                                  
        JumpTrue     -print-boolean-3-true     
        PushD        $boolean-false-string     
        Jump         -print-boolean-3-join     
        Label        -print-boolean-3-true     
        PushD        $boolean-true-string      
        Label        -print-boolean-3-join     
        PushD        $print-format-boolean     
        Printf                                 
        PushD        $print-format-space       
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        PushD        $global-memory-block      
        PushI        30                        
        Add                                    %% val4
        LoadC                                  
        PushD        $print-format-character   
        Printf                                 
        PushD        $print-format-space       
        Printf                                 
        PushD        $global-memory-block      
        PushI        31                        
        Add                                    %% test4
        LoadC                                  
        JumpTrue     -print-boolean-4-true     
        PushD        $boolean-false-string     
        Jump         -print-boolean-4-join     
        Label        -print-boolean-4-true     
        PushD        $boolean-true-string      
        Label        -print-boolean-4-join     
        PushD        $print-format-boolean     
        Printf                                 
        PushD        $print-format-space       
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        Halt                                   