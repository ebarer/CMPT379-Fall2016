        Label        -mem-manager-initialize   
        DLabel       $heap-start-ptr           
        DataZ        4                         
        DLabel       $heap-after-ptr           
        DataZ        4                         
        DLabel       $heap-first-free          
        DataZ        4                         
        DLabel       $mmgr-newblock-block      
        DataZ        4                         
        DLabel       $mmgr-newblock-size       
        DataZ        4                         
        PushD        $heap-memory              
        Duplicate                              
        PushD        $heap-start-ptr           
        Exchange                               
        StoreI                                 
        PushD        $heap-after-ptr           
        Exchange                               
        StoreI                                 
        PushI        0                         
        PushD        $heap-first-free          
        Exchange                               
        StoreI                                 
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
        DLabel       $print-format-rational-fraction 
        DataC        95                        %% "_%d/%d"
        DataC        37                        
        DataC        100                       
        DataC        47                        
        DataC        37                        
        DataC        100                       
        DataC        0                         
        DLabel       $print-format-rational-neg-fraction 
        DataC        45                        %% "-_%d/%d"
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
        DLabel       $errors-bad-index         
        DataC        98                        %% "bad index used for array"
        DataC        97                        
        DataC        100                       
        DataC        32                        
        DataC        105                       
        DataC        110                       
        DataC        100                       
        DataC        101                       
        DataC        120                       
        DataC        32                        
        DataC        117                       
        DataC        115                       
        DataC        101                       
        DataC        100                       
        DataC        32                        
        DataC        102                       
        DataC        111                       
        DataC        114                       
        DataC        32                        
        DataC        97                        
        DataC        114                       
        DataC        114                       
        DataC        97                        
        DataC        121                       
        DataC        0                         
        Label        $$bad-index               
        PushD        $errors-bad-index         
        Jump         $$general-runtime-error   
        DLabel       $release-temp-1           
        DataI        0                         
        DLabel       $index-temp-1             
        DataI        0                         
        DLabel       $index-temp-2             
        DataI        0                         
        DLabel       $array-temp-1             
        DataI        0                         
        DLabel       $array-temp-2             
        DataI        0                         
        DLabel       $array-temp-3             
        DataI        0                         
        DLabel       $array-temp-4             
        DataI        0                         
        DLabel       $array-temp-5             
        DataI        0                         
        DLabel       $rational-temp-numerator-1 
        DataI        0                         
        DLabel       $rational-temp-denominator-1 
        DataI        0                         
        DLabel       $rational-temp-numerator-2 
        DataI        0                         
        DLabel       $rational-temp-denominator-2 
        DataI        0                         
        DLabel       $print-temp-1             
        DataI        0                         
        DLabel       $print-temp-2             
        DataI        0                         
        DLabel       $print-temp-3             
        DataI        0                         
        DLabel       $print-rational-temp-1    
        DataI        0                         
        DLabel       $print-rational-temp-2    
        DataI        0                         
        DLabel       $print-rational-temp-3    
        DataI        0                         
        Label        $sub-rational-find-gcd    
        PushD        $rational-temp-numerator-1 
        LoadI                                  
        PushD        $rational-temp-denominator-1 
        LoadI                                  
        PushD        $rational-temp-denominator-2 
        Exchange                               
        StoreI                                 
        PushD        $rational-temp-numerator-2 
        Exchange                               
        StoreI                                 
        Label        gcd-loop                  
        PushD        $rational-temp-denominator-2 
        LoadI                                  
        JumpFalse    gcd-exit-loop             
        PushD        $rational-temp-numerator-2 
        LoadI                                  
        PushD        $rational-temp-denominator-2 
        LoadI                                  
        Remainder                              
        PushD        $rational-temp-numerator-2 
        LoadI                                  
        PushD        $rational-temp-denominator-2 
        LoadI                                  
        PushD        $rational-temp-numerator-2 
        Exchange                               
        StoreI                                 
        PushD        $rational-temp-denominator-2 
        Exchange                               
        StoreI                                 
        PushD        $rational-temp-denominator-2 
        Exchange                               
        StoreI                                 
        Jump         gcd-loop                  
        Label        gcd-exit-loop             
        PushD        $rational-temp-numerator-2 
        LoadI                                  
        Duplicate                              
        JumpPos      gcd-skip-negate-loop      
        Negate                                 
        Label        gcd-skip-negate-loop      
        PushD        $rational-temp-numerator-2 
        Exchange                               
        StoreI                                 
        PushD        $rational-temp-numerator-1 
        LoadI                                  
        PushD        $rational-temp-numerator-2 
        LoadI                                  
        Divide                                 
        PushD        $rational-temp-numerator-1 
        Exchange                               
        StoreI                                 
        PushD        $rational-temp-denominator-1 
        LoadI                                  
        PushD        $rational-temp-numerator-2 
        LoadI                                  
        Divide                                 
        PushD        $rational-temp-denominator-1 
        Exchange                               
        StoreI                                 
        Return                                 
        DLabel       $usable-memory-start      
        DLabel       $global-memory-block      
        DataZ        16                        
        Label        $$main                    
        PushD        $global-memory-block      
        PushI        0                         
        Add                                    %% myArr
        Label        -array-1-                 
        PushI        4                         
        PushI        4                         
        PushI        4                         
        Multiply                               
        PushI        16                        
        Add                                    
        Call         -mem-manager-allocate     
        PushD        $array-temp-1             
        Exchange                               
        StoreI                                 
        Label        -array-1-create-record    
        PushD        $array-temp-1             
        LoadI                                  
        PushI        7                         
        StoreI                                 
        PushD        $array-temp-1             
        LoadI                                  
        PushI        4                         
        Add                                    
        PushI        0                         
        StoreI                                 
        PushD        $array-temp-1             
        LoadI                                  
        PushI        8                         
        Add                                    
        PushI        4                         
        StoreI                                 
        PushD        $array-temp-1             
        LoadI                                  
        PushI        12                        
        Add                                    
        Exchange                               
        StoreI                                 
        PushD        $array-temp-1             
        LoadI                                  
        Label        -array-1-start-store-children 
        Duplicate                              
        PushI        16                        
        Add                                    
        PushI        1                         
        StoreI                                 
        Duplicate                              
        PushI        20                        
        Add                                    
        PushI        2                         
        StoreI                                 
        Duplicate                              
        PushI        24                        
        Add                                    
        PushI        3                         
        StoreI                                 
        Duplicate                              
        PushI        28                        
        Add                                    
        PushI        4                         
        StoreI                                 
        Label        -array-1-end-store-children 
        StoreI                                 
        PushD        $global-memory-block      
        PushI        0                         
        Add                                    %% myArr
        LoadI                                  
        Label        -print-array-2-           
        PushD        $array-temp-1             
        Exchange                               
        StoreI                                 
        PushD        $array-temp-1             
        LoadI                                  
        PushI        12                        
        Add                                    
        LoadI                                  
        PushD        $print-temp-1             
        Exchange                               
        StoreI                                 
        PushD        $array-temp-1             
        LoadI                                  
        PushI        8                         
        Add                                    
        LoadI                                  
        PushD        $print-temp-2             
        Exchange                               
        StoreI                                 
        PushD        $print-temp-3             
        PushI        16                        
        StoreI                                 
        PushI        91                        
        PushD        $print-format-character   
        Printf                                 
        Label        -print-array-2-loop       
        PushD        $print-temp-1             
        LoadI                                  
        JumpFalse    -print-array-2-join       
        PushD        $array-temp-1             
        LoadI                                  
        PushD        $print-temp-3             
        LoadI                                  
        Add                                    
        LoadI                                  
        PushD        $print-format-integer     
        Printf                                 
        PushD        $print-temp-3             
        LoadI                                  
        PushD        $print-temp-2             
        LoadI                                  
        Add                                    
        PushD        $print-temp-3             
        Exchange                               
        StoreI                                 
        PushD        $print-temp-1             
        LoadI                                  
        PushI        1                         
        Subtract                               
        PushD        $print-temp-1             
        Exchange                               
        StoreI                                 
        PushD        $print-temp-1             
        LoadI                                  
        JumpFalse    -print-array-2-join       
        PushI        44                        
        PushD        $print-format-character   
        Printf                                 
        PushI        0                         
        PushD        $print-format-character   
        Printf                                 
        Jump         -print-array-2-loop       
        Label        -print-array-2-join       
        PushI        93                        
        PushD        $print-format-character   
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        Label        -release-stmt-3-          
        PushD        $global-memory-block      
        PushI        0                         
        Add                                    %% myArr
        LoadI                                  
        PushD        $release-temp-1           
        Exchange                               
        StoreI                                 
        PushD        $release-temp-1           
        LoadI                                  
        PushI        4                         
        Add                                    
        LoadI                                  
        PushI        1                         
        BTAnd                                  
        JumpTrue     -release-stmt-3-join      
        PushD        $release-temp-1           
        LoadI                                  
        PushI        4                         
        Add                                    
        LoadI                                  
        PushI        2                         
        BTAnd                                  
        JumpTrue     -release-stmt-3-join      
        PushD        $release-temp-1           
        LoadI                                  
        PushI        4                         
        Add                                    
        Duplicate                              
        LoadI                                  
        PushI        2                         
        BTOr                                   
        StoreI                                 
        PushD        $release-temp-1           
        LoadI                                  
        Call         -mem-manager-deallocate   
        Label        -release-stmt-3-join      
        PushD        $global-memory-block      
        PushI        0                         
        Add                                    %% myArr
        LoadI                                  
        Label        -print-array-4-           
        PushD        $array-temp-1             
        Exchange                               
        StoreI                                 
        PushD        $array-temp-1             
        LoadI                                  
        PushI        12                        
        Add                                    
        LoadI                                  
        PushD        $print-temp-1             
        Exchange                               
        StoreI                                 
        PushD        $array-temp-1             
        LoadI                                  
        PushI        8                         
        Add                                    
        LoadI                                  
        PushD        $print-temp-2             
        Exchange                               
        StoreI                                 
        PushD        $print-temp-3             
        PushI        16                        
        StoreI                                 
        PushI        91                        
        PushD        $print-format-character   
        Printf                                 
        Label        -print-array-4-loop       
        PushD        $print-temp-1             
        LoadI                                  
        JumpFalse    -print-array-4-join       
        PushD        $array-temp-1             
        LoadI                                  
        PushD        $print-temp-3             
        LoadI                                  
        Add                                    
        LoadI                                  
        PushD        $print-format-integer     
        Printf                                 
        PushD        $print-temp-3             
        LoadI                                  
        PushD        $print-temp-2             
        LoadI                                  
        Add                                    
        PushD        $print-temp-3             
        Exchange                               
        StoreI                                 
        PushD        $print-temp-1             
        LoadI                                  
        PushI        1                         
        Subtract                               
        PushD        $print-temp-1             
        Exchange                               
        StoreI                                 
        PushD        $print-temp-1             
        LoadI                                  
        JumpFalse    -print-array-4-join       
        PushI        44                        
        PushD        $print-format-character   
        Printf                                 
        PushI        0                         
        PushD        $print-format-character   
        Printf                                 
        Jump         -print-array-4-loop       
        Label        -print-array-4-join       
        PushI        93                        
        PushD        $print-format-character   
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        PushD        $global-memory-block      
        PushI        8                         
        Add                                    %% myArr2
        Label        -array-7-                 
        PushI        2                         
        PushI        2                         
        PushI        8                         
        Multiply                               
        PushI        16                        
        Add                                    
        Call         -mem-manager-allocate     
        PushD        $array-temp-1             
        Exchange                               
        StoreI                                 
        Label        -array-7-create-record    
        PushD        $array-temp-1             
        LoadI                                  
        PushI        7                         
        StoreI                                 
        PushD        $array-temp-1             
        LoadI                                  
        PushI        4                         
        Add                                    
        PushI        4                         
        StoreI                                 
        PushD        $array-temp-1             
        LoadI                                  
        PushI        8                         
        Add                                    
        PushI        8                         
        StoreI                                 
        PushD        $array-temp-1             
        LoadI                                  
        PushI        12                        
        Add                                    
        Exchange                               
        StoreI                                 
        PushD        $array-temp-1             
        LoadI                                  
        Label        -array-7-start-store-children 
        Duplicate                              
        PushI        16                        
        Add                                    
        Label        -array-5-                 
        PushI        2                         
        PushI        2                         
        PushI        4                         
        Multiply                               
        PushI        16                        
        Add                                    
        Call         -mem-manager-allocate     
        PushD        $array-temp-1             
        Exchange                               
        StoreI                                 
        Label        -array-5-create-record    
        PushD        $array-temp-1             
        LoadI                                  
        PushI        7                         
        StoreI                                 
        PushD        $array-temp-1             
        LoadI                                  
        PushI        4                         
        Add                                    
        PushI        0                         
        StoreI                                 
        PushD        $array-temp-1             
        LoadI                                  
        PushI        8                         
        Add                                    
        PushI        4                         
        StoreI                                 
        PushD        $array-temp-1             
        LoadI                                  
        PushI        12                        
        Add                                    
        Exchange                               
        StoreI                                 
        PushD        $array-temp-1             
        LoadI                                  
        Label        -array-5-start-store-children 
        Duplicate                              
        PushI        16                        
        Add                                    
        PushI        1                         
        StoreI                                 
        Duplicate                              
        PushI        20                        
        Add                                    
        PushI        2                         
        StoreI                                 
        Label        -array-5-end-store-children 
        StoreI                                 
        Duplicate                              
        PushI        24                        
        Add                                    
        Label        -array-6-                 
        PushI        2                         
        PushI        2                         
        PushI        4                         
        Multiply                               
        PushI        16                        
        Add                                    
        Call         -mem-manager-allocate     
        PushD        $array-temp-1             
        Exchange                               
        StoreI                                 
        Label        -array-6-create-record    
        PushD        $array-temp-1             
        LoadI                                  
        PushI        7                         
        StoreI                                 
        PushD        $array-temp-1             
        LoadI                                  
        PushI        4                         
        Add                                    
        PushI        0                         
        StoreI                                 
        PushD        $array-temp-1             
        LoadI                                  
        PushI        8                         
        Add                                    
        PushI        4                         
        StoreI                                 
        PushD        $array-temp-1             
        LoadI                                  
        PushI        12                        
        Add                                    
        Exchange                               
        StoreI                                 
        PushD        $array-temp-1             
        LoadI                                  
        Label        -array-6-start-store-children 
        Duplicate                              
        PushI        16                        
        Add                                    
        PushI        3                         
        StoreI                                 
        Duplicate                              
        PushI        20                        
        Add                                    
        PushI        4                         
        StoreI                                 
        Label        -array-6-end-store-children 
        StoreI                                 
        Label        -array-7-end-store-children 
        StoreI                                 
        PushD        $global-memory-block      
        PushI        8                         
        Add                                    %% myArr2
        LoadI                                  
        PushI        12                        
        Add                                    
        LoadI                                  
        PushD        $print-format-integer     
        Printf                                 
        PushD        $print-format-space       
        Printf                                 
        PushI        58                        
        PushD        $print-format-character   
        Printf                                 
        PushD        $print-format-space       
        Printf                                 
        PushD        $global-memory-block      
        PushI        8                         
        Add                                    %% myArr2
        LoadI                                  
        Label        -print-array-8-           
        PushD        $array-temp-1             
        Exchange                               
        StoreI                                 
        PushD        $array-temp-1             
        LoadI                                  
        PushI        12                        
        Add                                    
        LoadI                                  
        PushD        $print-temp-1             
        Exchange                               
        StoreI                                 
        PushD        $array-temp-1             
        LoadI                                  
        PushI        8                         
        Add                                    
        LoadI                                  
        PushD        $print-temp-2             
        Exchange                               
        StoreI                                 
        PushD        $print-temp-3             
        PushI        16                        
        StoreI                                 
        PushI        91                        
        PushD        $print-format-character   
        Printf                                 
        Label        -print-array-8-loop       
        PushD        $print-temp-1             
        LoadI                                  
        JumpFalse    -print-array-8-join       
        PushD        $array-temp-1             
        LoadI                                  
        PushD        $print-temp-3             
        LoadI                                  
        Add                                    
        PushD        $array-temp-1             
        LoadI                                  
        Exchange                               
        PushD        $print-temp-1             
        LoadI                                  
        Exchange                               
        PushD        $print-temp-2             
        LoadI                                  
        Exchange                               
        PushD        $print-temp-3             
        LoadI                                  
        Exchange                               
        LoadI                                  
        Label        -print-array-9-           
        PushD        $array-temp-1             
        Exchange                               
        StoreI                                 
        PushD        $array-temp-1             
        LoadI                                  
        PushI        12                        
        Add                                    
        LoadI                                  
        PushD        $print-temp-1             
        Exchange                               
        StoreI                                 
        PushD        $array-temp-1             
        LoadI                                  
        PushI        8                         
        Add                                    
        LoadI                                  
        PushD        $print-temp-2             
        Exchange                               
        StoreI                                 
        PushD        $print-temp-3             
        PushI        16                        
        StoreI                                 
        PushI        91                        
        PushD        $print-format-character   
        Printf                                 
        Label        -print-array-9-loop       
        PushD        $print-temp-1             
        LoadI                                  
        JumpFalse    -print-array-9-join       
        PushD        $array-temp-1             
        LoadI                                  
        PushD        $print-temp-3             
        LoadI                                  
        Add                                    
        LoadI                                  
        PushD        $print-format-integer     
        Printf                                 
        PushD        $print-temp-3             
        LoadI                                  
        PushD        $print-temp-2             
        LoadI                                  
        Add                                    
        PushD        $print-temp-3             
        Exchange                               
        StoreI                                 
        PushD        $print-temp-1             
        LoadI                                  
        PushI        1                         
        Subtract                               
        PushD        $print-temp-1             
        Exchange                               
        StoreI                                 
        PushD        $print-temp-1             
        LoadI                                  
        JumpFalse    -print-array-9-join       
        PushI        44                        
        PushD        $print-format-character   
        Printf                                 
        PushI        0                         
        PushD        $print-format-character   
        Printf                                 
        Jump         -print-array-9-loop       
        Label        -print-array-9-join       
        PushI        93                        
        PushD        $print-format-character   
        Printf                                 
        PushD        $print-temp-3             
        Exchange                               
        StoreI                                 
        PushD        $print-temp-2             
        Exchange                               
        StoreI                                 
        PushD        $print-temp-1             
        Exchange                               
        StoreI                                 
        PushD        $array-temp-1             
        Exchange                               
        StoreI                                 
        PushD        $print-temp-3             
        LoadI                                  
        PushD        $print-temp-2             
        LoadI                                  
        Add                                    
        PushD        $print-temp-3             
        Exchange                               
        StoreI                                 
        PushD        $print-temp-1             
        LoadI                                  
        PushI        1                         
        Subtract                               
        PushD        $print-temp-1             
        Exchange                               
        StoreI                                 
        PushD        $print-temp-1             
        LoadI                                  
        JumpFalse    -print-array-8-join       
        PushI        44                        
        PushD        $print-format-character   
        Printf                                 
        PushI        0                         
        PushD        $print-format-character   
        Printf                                 
        Jump         -print-array-8-loop       
        Label        -print-array-8-join       
        PushI        93                        
        PushD        $print-format-character   
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        Label        -release-stmt-10-         
        PushD        $global-memory-block      
        PushI        8                         
        Add                                    %% myArr2
        LoadI                                  
        PushD        $release-temp-1           
        Exchange                               
        StoreI                                 
        PushD        $release-temp-1           
        LoadI                                  
        PushI        4                         
        Add                                    
        LoadI                                  
        PushI        1                         
        BTAnd                                  
        JumpTrue     -release-stmt-10-join     
        PushD        $release-temp-1           
        LoadI                                  
        PushI        4                         
        Add                                    
        LoadI                                  
        PushI        2                         
        BTAnd                                  
        JumpTrue     -release-stmt-10-join     
        PushD        $release-temp-1           
        LoadI                                  
        PushI        4                         
        Add                                    
        Duplicate                              
        LoadI                                  
        PushI        2                         
        BTOr                                   
        StoreI                                 
        PushD        $release-temp-1           
        LoadI                                  
        Call         -mem-manager-deallocate   
        Label        -release-stmt-10-join     
        PushD        $global-memory-block      
        PushI        8                         
        Add                                    %% myArr2
        LoadI                                  
        PushI        12                        
        Add                                    
        LoadI                                  
        PushD        $print-format-integer     
        Printf                                 
        PushD        $print-format-space       
        Printf                                 
        PushI        58                        
        PushD        $print-format-character   
        Printf                                 
        PushD        $print-format-space       
        Printf                                 
        PushD        $global-memory-block      
        PushI        8                         
        Add                                    %% myArr2
        LoadI                                  
        Label        -print-array-11-          
        PushD        $array-temp-1             
        Exchange                               
        StoreI                                 
        PushD        $array-temp-1             
        LoadI                                  
        PushI        12                        
        Add                                    
        LoadI                                  
        PushD        $print-temp-1             
        Exchange                               
        StoreI                                 
        PushD        $array-temp-1             
        LoadI                                  
        PushI        8                         
        Add                                    
        LoadI                                  
        PushD        $print-temp-2             
        Exchange                               
        StoreI                                 
        PushD        $print-temp-3             
        PushI        16                        
        StoreI                                 
        PushI        91                        
        PushD        $print-format-character   
        Printf                                 
        Label        -print-array-11-loop      
        PushD        $print-temp-1             
        LoadI                                  
        JumpFalse    -print-array-11-join      
        PushD        $array-temp-1             
        LoadI                                  
        PushD        $print-temp-3             
        LoadI                                  
        Add                                    
        PushD        $array-temp-1             
        LoadI                                  
        Exchange                               
        PushD        $print-temp-1             
        LoadI                                  
        Exchange                               
        PushD        $print-temp-2             
        LoadI                                  
        Exchange                               
        PushD        $print-temp-3             
        LoadI                                  
        Exchange                               
        LoadI                                  
        Label        -print-array-12-          
        PushD        $array-temp-1             
        Exchange                               
        StoreI                                 
        PushD        $array-temp-1             
        LoadI                                  
        PushI        12                        
        Add                                    
        LoadI                                  
        PushD        $print-temp-1             
        Exchange                               
        StoreI                                 
        PushD        $array-temp-1             
        LoadI                                  
        PushI        8                         
        Add                                    
        LoadI                                  
        PushD        $print-temp-2             
        Exchange                               
        StoreI                                 
        PushD        $print-temp-3             
        PushI        16                        
        StoreI                                 
        PushI        91                        
        PushD        $print-format-character   
        Printf                                 
        Label        -print-array-12-loop      
        PushD        $print-temp-1             
        LoadI                                  
        JumpFalse    -print-array-12-join      
        PushD        $array-temp-1             
        LoadI                                  
        PushD        $print-temp-3             
        LoadI                                  
        Add                                    
        LoadI                                  
        PushD        $print-format-integer     
        Printf                                 
        PushD        $print-temp-3             
        LoadI                                  
        PushD        $print-temp-2             
        LoadI                                  
        Add                                    
        PushD        $print-temp-3             
        Exchange                               
        StoreI                                 
        PushD        $print-temp-1             
        LoadI                                  
        PushI        1                         
        Subtract                               
        PushD        $print-temp-1             
        Exchange                               
        StoreI                                 
        PushD        $print-temp-1             
        LoadI                                  
        JumpFalse    -print-array-12-join      
        PushI        44                        
        PushD        $print-format-character   
        Printf                                 
        PushI        0                         
        PushD        $print-format-character   
        Printf                                 
        Jump         -print-array-12-loop      
        Label        -print-array-12-join      
        PushI        93                        
        PushD        $print-format-character   
        Printf                                 
        PushD        $print-temp-3             
        Exchange                               
        StoreI                                 
        PushD        $print-temp-2             
        Exchange                               
        StoreI                                 
        PushD        $print-temp-1             
        Exchange                               
        StoreI                                 
        PushD        $array-temp-1             
        Exchange                               
        StoreI                                 
        PushD        $print-temp-3             
        LoadI                                  
        PushD        $print-temp-2             
        LoadI                                  
        Add                                    
        PushD        $print-temp-3             
        Exchange                               
        StoreI                                 
        PushD        $print-temp-1             
        LoadI                                  
        PushI        1                         
        Subtract                               
        PushD        $print-temp-1             
        Exchange                               
        StoreI                                 
        PushD        $print-temp-1             
        LoadI                                  
        JumpFalse    -print-array-11-join      
        PushI        44                        
        PushD        $print-format-character   
        Printf                                 
        PushI        0                         
        PushD        $print-format-character   
        Printf                                 
        Jump         -print-array-11-loop      
        Label        -print-array-11-join      
        PushI        93                        
        PushD        $print-format-character   
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        Halt                                   %% End of Execution
        Label        -mem-manager-make-tags    
        DLabel       $mmgr-tags-size           
        DataZ        4                         
        DLabel       $mmgr-tags-start          
        DataZ        4                         
        DLabel       $mmgr-tags-available      
        DataZ        4                         
        DLabel       $mmgr-tags-nextptr        
        DataZ        4                         
        DLabel       $mmgr-tags-prevptr        
        DataZ        4                         
        DLabel       $mmgr-tags-return         
        DataZ        4                         
        PushD        $mmgr-tags-return         
        Exchange                               
        StoreI                                 
        PushD        $mmgr-tags-size           
        Exchange                               
        StoreI                                 
        PushD        $mmgr-tags-start          
        Exchange                               
        StoreI                                 
        PushD        $mmgr-tags-available      
        Exchange                               
        StoreI                                 
        PushD        $mmgr-tags-nextptr        
        Exchange                               
        StoreI                                 
        PushD        $mmgr-tags-prevptr        
        Exchange                               
        StoreI                                 
        PushD        $mmgr-tags-prevptr        
        LoadI                                  
        PushD        $mmgr-tags-size           
        LoadI                                  
        PushD        $mmgr-tags-available      
        LoadI                                  
        PushD        $mmgr-tags-start          
        LoadI                                  
        Call         -mem-manager-one-tag      
        PushD        $mmgr-tags-nextptr        
        LoadI                                  
        PushD        $mmgr-tags-size           
        LoadI                                  
        PushD        $mmgr-tags-available      
        LoadI                                  
        PushD        $mmgr-tags-start          
        LoadI                                  
        Duplicate                              
        PushI        4                         
        Add                                    
        LoadI                                  
        Add                                    
        PushI        9                         
        Subtract                               
        Call         -mem-manager-one-tag      
        PushD        $mmgr-tags-return         
        LoadI                                  
        Return                                 
        Label        -mem-manager-one-tag      
        DLabel       $mmgr-onetag-return       
        DataZ        4                         
        DLabel       $mmgr-onetag-location     
        DataZ        4                         
        DLabel       $mmgr-onetag-available    
        DataZ        4                         
        DLabel       $mmgr-onetag-size         
        DataZ        4                         
        DLabel       $mmgr-onetag-pointer      
        DataZ        4                         
        PushD        $mmgr-onetag-return       
        Exchange                               
        StoreI                                 
        PushD        $mmgr-onetag-location     
        Exchange                               
        StoreI                                 
        PushD        $mmgr-onetag-available    
        Exchange                               
        StoreI                                 
        PushD        $mmgr-onetag-size         
        Exchange                               
        StoreI                                 
        PushD        $mmgr-onetag-location     
        LoadI                                  
        PushI        0                         
        Add                                    
        Exchange                               
        StoreI                                 
        PushD        $mmgr-onetag-size         
        LoadI                                  
        PushD        $mmgr-onetag-location     
        LoadI                                  
        PushI        4                         
        Add                                    
        Exchange                               
        StoreI                                 
        PushD        $mmgr-onetag-available    
        LoadI                                  
        PushD        $mmgr-onetag-location     
        LoadI                                  
        PushI        8                         
        Add                                    
        Exchange                               
        StoreC                                 
        PushD        $mmgr-onetag-return       
        LoadI                                  
        Return                                 
        Label        -mem-manager-allocate     
        DLabel       $mmgr-alloc-return        
        DataZ        4                         
        DLabel       $mmgr-alloc-size          
        DataZ        4                         
        DLabel       $mmgr-alloc-current-block 
        DataZ        4                         
        DLabel       $mmgr-alloc-remainder-block 
        DataZ        4                         
        DLabel       $mmgr-alloc-remainder-size 
        DataZ        4                         
        PushD        $mmgr-alloc-return        
        Exchange                               
        StoreI                                 
        PushI        18                        
        Add                                    
        PushD        $mmgr-alloc-size          
        Exchange                               
        StoreI                                 
        PushD        $heap-first-free          
        LoadI                                  
        PushD        $mmgr-alloc-current-block 
        Exchange                               
        StoreI                                 
        Label        -mmgr-alloc-process-current 
        PushD        $mmgr-alloc-current-block 
        LoadI                                  
        JumpFalse    -mmgr-alloc-no-block-works 
        Label        -mmgr-alloc-test-block    
        PushD        $mmgr-alloc-current-block 
        LoadI                                  
        PushI        4                         
        Add                                    
        LoadI                                  
        PushD        $mmgr-alloc-size          
        LoadI                                  
        Subtract                               
        PushI        1                         
        Add                                    
        JumpPos      -mmgr-alloc-found-block   
        PushD        $mmgr-alloc-current-block 
        LoadI                                  
        Duplicate                              
        PushI        4                         
        Add                                    
        LoadI                                  
        Add                                    
        PushI        9                         
        Subtract                               
        PushI        0                         
        Add                                    
        LoadI                                  
        PushD        $mmgr-alloc-current-block 
        Exchange                               
        StoreI                                 
        Jump         -mmgr-alloc-process-current 
        Label        -mmgr-alloc-found-block   
        PushD        $mmgr-alloc-current-block 
        LoadI                                  
        Call         -mem-manager-remove-block 
        PushD        $mmgr-alloc-current-block 
        LoadI                                  
        PushI        4                         
        Add                                    
        LoadI                                  
        PushD        $mmgr-alloc-size          
        LoadI                                  
        Subtract                               
        PushI        26                        
        Subtract                               
        JumpNeg      -mmgr-alloc-return-userblock 
        PushD        $mmgr-alloc-current-block 
        LoadI                                  
        PushD        $mmgr-alloc-size          
        LoadI                                  
        Add                                    
        PushD        $mmgr-alloc-remainder-block 
        Exchange                               
        StoreI                                 
        PushD        $mmgr-alloc-size          
        LoadI                                  
        PushD        $mmgr-alloc-current-block 
        LoadI                                  
        PushI        4                         
        Add                                    
        LoadI                                  
        Exchange                               
        Subtract                               
        PushD        $mmgr-alloc-remainder-size 
        Exchange                               
        StoreI                                 
        PushI        0                         
        PushI        0                         
        PushI        0                         
        PushD        $mmgr-alloc-current-block 
        LoadI                                  
        PushD        $mmgr-alloc-size          
        LoadI                                  
        Call         -mem-manager-make-tags    
        PushI        0                         
        PushI        0                         
        PushI        1                         
        PushD        $mmgr-alloc-remainder-block 
        LoadI                                  
        PushD        $mmgr-alloc-remainder-size 
        LoadI                                  
        Call         -mem-manager-make-tags    
        PushD        $mmgr-alloc-remainder-block 
        LoadI                                  
        PushI        9                         
        Add                                    
        Call         -mem-manager-deallocate   
        Jump         -mmgr-alloc-return-userblock 
        Label        -mmgr-alloc-no-block-works 
        PushD        $mmgr-alloc-size          
        LoadI                                  
        PushD        $mmgr-newblock-size       
        Exchange                               
        StoreI                                 
        PushD        $heap-after-ptr           
        LoadI                                  
        PushD        $mmgr-newblock-block      
        Exchange                               
        StoreI                                 
        PushD        $mmgr-newblock-size       
        LoadI                                  
        PushD        $heap-after-ptr           
        LoadI                                  
        Add                                    
        PushD        $heap-after-ptr           
        Exchange                               
        StoreI                                 
        PushI        0                         
        PushI        0                         
        PushI        0                         
        PushD        $mmgr-newblock-block      
        LoadI                                  
        PushD        $mmgr-newblock-size       
        LoadI                                  
        Call         -mem-manager-make-tags    
        PushD        $mmgr-newblock-block      
        LoadI                                  
        PushD        $mmgr-alloc-current-block 
        Exchange                               
        StoreI                                 
        Label        -mmgr-alloc-return-userblock 
        PushD        $mmgr-alloc-current-block 
        LoadI                                  
        PushI        9                         
        Add                                    
        PushD        $mmgr-alloc-return        
        LoadI                                  
        Return                                 
        Label        -mem-manager-deallocate   
        DLabel       $mmgr-dealloc-return      
        DataZ        4                         
        DLabel       $mmgr-dealloc-block       
        DataZ        4                         
        PushD        $mmgr-dealloc-return      
        Exchange                               
        StoreI                                 
        PushI        9                         
        Subtract                               
        PushD        $mmgr-dealloc-block       
        Exchange                               
        StoreI                                 
        PushD        $mmgr-dealloc-block       
        LoadI                                  
        PushD        $heap-first-free          
        LoadI                                  
        PushI        0                         
        Add                                    
        Exchange                               
        StoreI                                 
        PushI        0                         
        PushD        $mmgr-dealloc-block       
        LoadI                                  
        PushI        0                         
        Add                                    
        Exchange                               
        StoreI                                 
        PushD        $heap-first-free          
        LoadI                                  
        PushD        $mmgr-dealloc-block       
        LoadI                                  
        Duplicate                              
        PushI        4                         
        Add                                    
        LoadI                                  
        Add                                    
        PushI        9                         
        Subtract                               
        PushI        0                         
        Add                                    
        Exchange                               
        StoreI                                 
        PushI        1                         
        PushD        $mmgr-dealloc-block       
        LoadI                                  
        PushI        8                         
        Add                                    
        Exchange                               
        StoreC                                 
        PushI        1                         
        PushD        $mmgr-dealloc-block       
        LoadI                                  
        Duplicate                              
        PushI        4                         
        Add                                    
        LoadI                                  
        Add                                    
        PushI        9                         
        Subtract                               
        PushI        8                         
        Add                                    
        Exchange                               
        StoreC                                 
        PushD        $mmgr-dealloc-block       
        LoadI                                  
        PushD        $heap-first-free          
        Exchange                               
        StoreI                                 
        PushD        $mmgr-dealloc-return      
        LoadI                                  
        Return                                 
        Label        -mem-manager-remove-block 
        DLabel       $mmgr-remove-return       
        DataZ        4                         
        DLabel       $mmgr-remove-block        
        DataZ        4                         
        DLabel       $mmgr-remove-prev         
        DataZ        4                         
        DLabel       $mmgr-remove-next         
        DataZ        4                         
        PushD        $mmgr-remove-return       
        Exchange                               
        StoreI                                 
        PushD        $mmgr-remove-block        
        Exchange                               
        StoreI                                 
        PushD        $mmgr-remove-block        
        LoadI                                  
        PushI        0                         
        Add                                    
        LoadI                                  
        PushD        $mmgr-remove-prev         
        Exchange                               
        StoreI                                 
        PushD        $mmgr-remove-block        
        LoadI                                  
        Duplicate                              
        PushI        4                         
        Add                                    
        LoadI                                  
        Add                                    
        PushI        9                         
        Subtract                               
        PushI        0                         
        Add                                    
        LoadI                                  
        PushD        $mmgr-remove-next         
        Exchange                               
        StoreI                                 
        Label        -mmgr-remove-process-prev 
        PushD        $mmgr-remove-prev         
        LoadI                                  
        JumpFalse    -mmgr-remove-no-prev      
        PushD        $mmgr-remove-next         
        LoadI                                  
        PushD        $mmgr-remove-prev         
        LoadI                                  
        Duplicate                              
        PushI        4                         
        Add                                    
        LoadI                                  
        Add                                    
        PushI        9                         
        Subtract                               
        PushI        0                         
        Add                                    
        Exchange                               
        StoreI                                 
        Jump         -mmgr-remove-process-next 
        Label        -mmgr-remove-no-prev      
        PushD        $mmgr-remove-next         
        LoadI                                  
        PushD        $heap-first-free          
        Exchange                               
        StoreI                                 
        Label        -mmgr-remove-process-next 
        PushD        $mmgr-remove-next         
        LoadI                                  
        JumpFalse    -mmgr-remove-done         
        PushD        $mmgr-remove-prev         
        LoadI                                  
        PushD        $mmgr-remove-next         
        LoadI                                  
        PushI        0                         
        Add                                    
        Exchange                               
        StoreI                                 
        Label        -mmgr-remove-done         
        PushD        $mmgr-remove-return       
        LoadI                                  
        Return                                 
        DLabel       $heap-memory              
