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
        DLabel       $array-recurse-released   
        DataC        65                        %% "Array recursively released\n"
        DataC        114                       
        DataC        114                       
        DataC        97                        
        DataC        121                       
        DataC        32                        
        DataC        114                       
        DataC        101                       
        DataC        99                        
        DataC        117                       
        DataC        114                       
        DataC        115                       
        DataC        105                       
        DataC        118                       
        DataC        101                       
        DataC        108                       
        DataC        121                       
        DataC        32                        
        DataC        114                       
        DataC        101                       
        DataC        108                       
        DataC        101                       
        DataC        97                        
        DataC        115                       
        DataC        101                       
        DataC        100                       
        DataC        10                        
        DataC        0                         
        DLabel       $array-released           
        DataC        65                        %% "Array already released\n"
        DataC        114                       
        DataC        114                       
        DataC        97                        
        DataC        121                       
        DataC        32                        
        DataC        97                        
        DataC        108                       
        DataC        114                       
        DataC        101                       
        DataC        97                        
        DataC        100                       
        DataC        121                       
        DataC        32                        
        DataC        114                       
        DataC        101                       
        DataC        108                       
        DataC        101                       
        DataC        97                        
        DataC        115                       
        DataC        101                       
        DataC        100                       
        DataC        10                        
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
        DLabel       $release-temp-1           
        DataI        0                         
        DLabel       $release-temp-2           
        DataI        0                         
        DLabel       $release-temp-3           
        DataI        0                         
        DLabel       $release-temp-4           
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
        DLabel       $usable-memory-start      
        DLabel       $global-memory-block      
        DataZ        1                         
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
        DLabel       $mmgr-dealloc-return      
        DataZ        4                         
        DLabel       $mmgr-dealloc-block       
        DataZ        4                         
        DLabel       $mmgr-remove-return       
        DataZ        4                         
        DLabel       $mmgr-remove-block        
        DataZ        4                         
        DLabel       $mmgr-remove-prev         
        DataZ        4                         
        DLabel       $mmgr-remove-next         
        DataZ        4                         
        DLabel       $heap-memory              
        Label        basicBlock-1              
        Label        $$main                    
        PushD        $global-memory-block      
        PushI        0                         
        Add                                    %% test
        PushI        3                         
        PushI        5                         
        PushI        9                         
        PushI        10                        
        PushD        $rational-temp-denominator-2 
        Exchange                               
        StoreI                                 
        PushD        $rational-temp-numerator-2 
        Exchange                               
        StoreI                                 
        PushD        $rational-temp-denominator-1 
        Exchange                               
        StoreI                                 
        PushD        $rational-temp-numerator-1 
        Exchange                               
        StoreI                                 
        PushD        $rational-temp-numerator-1 
        LoadI                                  
        PushD        $rational-temp-denominator-2 
        LoadI                                  
        Multiply                               
        PushD        $rational-temp-numerator-2 
        LoadI                                  
        PushD        $rational-temp-denominator-1 
        LoadI                                  
        Multiply                               
        Subtract                               
        JumpPos      -compare-253-false        
        Jump         -compare-253-true         
        Label        basicBlock-2              
        Label        -compare-253-true         
        PushI        1                         
        Jump         -compare-253-join         
        Label        basicBlock-3              
        Label        -compare-253-false        
        PushI        0                         
        Label        basicBlock-4              
        Label        -compare-253-join         
        StoreC                                 
        PushD        $global-memory-block      
        PushI        0                         
        Add                                    %% test
        LoadC                                  
        JumpTrue     -print-boolean-254-true   
        Label        basicBlock-5              
        PushD        $boolean-false-string     
        Jump         -print-boolean-254-join   
        Label        basicBlock-6              
        Label        -print-boolean-254-true   
        PushD        $boolean-true-string      
        Label        basicBlock-7              
        Label        -print-boolean-254-join   
        PushD        $print-format-boolean     
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        PushD        $global-memory-block      
        PushI        0                         
        Add                                    %% test
        PushI        3                         
        PushI        5                         
        PushI        9                         
        PushI        10                        
        PushD        $rational-temp-denominator-2 
        Exchange                               
        StoreI                                 
        PushD        $rational-temp-numerator-2 
        Exchange                               
        StoreI                                 
        PushD        $rational-temp-denominator-1 
        Exchange                               
        StoreI                                 
        PushD        $rational-temp-numerator-1 
        Exchange                               
        StoreI                                 
        PushD        $rational-temp-numerator-1 
        LoadI                                  
        PushD        $rational-temp-denominator-2 
        LoadI                                  
        Multiply                               
        PushD        $rational-temp-numerator-2 
        LoadI                                  
        PushD        $rational-temp-denominator-1 
        LoadI                                  
        Multiply                               
        Subtract                               
        JumpNeg      -compare-255-true         
        Jump         -compare-255-false        
        Label        basicBlock-8              
        Label        -compare-255-true         
        PushI        1                         
        Jump         -compare-255-join         
        Label        basicBlock-9              
        Label        -compare-255-false        
        PushI        0                         
        Label        basicBlock-10             
        Label        -compare-255-join         
        StoreC                                 
        PushD        $global-memory-block      
        PushI        0                         
        Add                                    %% test
        LoadC                                  
        JumpTrue     -print-boolean-256-true   
        Label        basicBlock-11             
        PushD        $boolean-false-string     
        Jump         -print-boolean-256-join   
        Label        basicBlock-12             
        Label        -print-boolean-256-true   
        PushD        $boolean-true-string      
        Label        basicBlock-13             
        Label        -print-boolean-256-join   
        PushD        $print-format-boolean     
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        PushD        $global-memory-block      
        PushI        0                         
        Add                                    %% test
        PushI        3                         
        PushI        5                         
        PushI        9                         
        PushI        10                        
        PushD        $rational-temp-denominator-2 
        Exchange                               
        StoreI                                 
        PushD        $rational-temp-numerator-2 
        Exchange                               
        StoreI                                 
        PushD        $rational-temp-denominator-1 
        Exchange                               
        StoreI                                 
        PushD        $rational-temp-numerator-1 
        Exchange                               
        StoreI                                 
        PushD        $rational-temp-numerator-1 
        LoadI                                  
        PushD        $rational-temp-denominator-2 
        LoadI                                  
        Multiply                               
        PushD        $rational-temp-numerator-2 
        LoadI                                  
        PushD        $rational-temp-denominator-1 
        LoadI                                  
        Multiply                               
        Subtract                               
        JumpFalse    -compare-257-true         
        Jump         -compare-257-false        
        Label        basicBlock-14             
        Label        -compare-257-true         
        PushI        1                         
        Jump         -compare-257-join         
        Label        basicBlock-15             
        Label        -compare-257-false        
        PushI        0                         
        Label        basicBlock-16             
        Label        -compare-257-join         
        StoreC                                 
        PushD        $global-memory-block      
        PushI        0                         
        Add                                    %% test
        LoadC                                  
        JumpTrue     -print-boolean-258-true   
        Label        basicBlock-17             
        PushD        $boolean-false-string     
        Jump         -print-boolean-258-join   
        Label        basicBlock-18             
        Label        -print-boolean-258-true   
        PushD        $boolean-true-string      
        Label        basicBlock-19             
        Label        -print-boolean-258-join   
        PushD        $print-format-boolean     
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        PushD        $global-memory-block      
        PushI        0                         
        Add                                    %% test
        PushI        3                         
        PushI        5                         
        PushI        9                         
        PushI        10                        
        PushD        $rational-temp-denominator-2 
        Exchange                               
        StoreI                                 
        PushD        $rational-temp-numerator-2 
        Exchange                               
        StoreI                                 
        PushD        $rational-temp-denominator-1 
        Exchange                               
        StoreI                                 
        PushD        $rational-temp-numerator-1 
        Exchange                               
        StoreI                                 
        PushD        $rational-temp-numerator-1 
        LoadI                                  
        PushD        $rational-temp-denominator-2 
        LoadI                                  
        Multiply                               
        PushD        $rational-temp-numerator-2 
        LoadI                                  
        PushD        $rational-temp-denominator-1 
        LoadI                                  
        Multiply                               
        Subtract                               
        JumpFalse    -compare-259-false        
        Jump         -compare-259-true         
        Label        basicBlock-20             
        Label        -compare-259-true         
        PushI        1                         
        Jump         -compare-259-join         
        Label        basicBlock-21             
        Label        -compare-259-false        
        PushI        0                         
        Label        basicBlock-22             
        Label        -compare-259-join         
        StoreC                                 
        PushD        $global-memory-block      
        PushI        0                         
        Add                                    %% test
        LoadC                                  
        JumpTrue     -print-boolean-260-true   
        Label        basicBlock-23             
        PushD        $boolean-false-string     
        Jump         -print-boolean-260-join   
        Label        basicBlock-24             
        Label        -print-boolean-260-true   
        PushD        $boolean-true-string      
        Label        basicBlock-25             
        Label        -print-boolean-260-join   
        PushD        $print-format-boolean     
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        PushD        $global-memory-block      
        PushI        0                         
        Add                                    %% test
        PushI        3                         
        PushI        5                         
        PushI        9                         
        PushI        10                        
        PushD        $rational-temp-denominator-2 
        Exchange                               
        StoreI                                 
        PushD        $rational-temp-numerator-2 
        Exchange                               
        StoreI                                 
        PushD        $rational-temp-denominator-1 
        Exchange                               
        StoreI                                 
        PushD        $rational-temp-numerator-1 
        Exchange                               
        StoreI                                 
        PushD        $rational-temp-numerator-1 
        LoadI                                  
        PushD        $rational-temp-denominator-2 
        LoadI                                  
        Multiply                               
        PushD        $rational-temp-numerator-2 
        LoadI                                  
        PushD        $rational-temp-denominator-1 
        LoadI                                  
        Multiply                               
        Subtract                               
        JumpPos      -compare-261-true         
        Jump         -compare-261-false        
        Label        basicBlock-26             
        Label        -compare-261-true         
        PushI        1                         
        Jump         -compare-261-join         
        Label        basicBlock-27             
        Label        -compare-261-false        
        PushI        0                         
        Label        basicBlock-28             
        Label        -compare-261-join         
        StoreC                                 
        PushD        $global-memory-block      
        PushI        0                         
        Add                                    %% test
        LoadC                                  
        JumpTrue     -print-boolean-262-true   
        Label        basicBlock-29             
        PushD        $boolean-false-string     
        Jump         -print-boolean-262-join   
        Label        basicBlock-30             
        Label        -print-boolean-262-true   
        PushD        $boolean-true-string      
        Label        basicBlock-31             
        Label        -print-boolean-262-join   
        PushD        $print-format-boolean     
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        PushD        $global-memory-block      
        PushI        0                         
        Add                                    %% test
        PushI        3                         
        PushI        5                         
        PushI        9                         
        PushI        10                        
        PushD        $rational-temp-denominator-2 
        Exchange                               
        StoreI                                 
        PushD        $rational-temp-numerator-2 
        Exchange                               
        StoreI                                 
        PushD        $rational-temp-denominator-1 
        Exchange                               
        StoreI                                 
        PushD        $rational-temp-numerator-1 
        Exchange                               
        StoreI                                 
        PushD        $rational-temp-numerator-1 
        LoadI                                  
        PushD        $rational-temp-denominator-2 
        LoadI                                  
        Multiply                               
        PushD        $rational-temp-numerator-2 
        LoadI                                  
        PushD        $rational-temp-denominator-1 
        LoadI                                  
        Multiply                               
        Subtract                               
        JumpNeg      -compare-263-false        
        Jump         -compare-263-true         
        Label        basicBlock-32             
        Label        -compare-263-true         
        PushI        1                         
        Jump         -compare-263-join         
        Label        basicBlock-33             
        Label        -compare-263-false        
        PushI        0                         
        Label        basicBlock-34             
        Label        -compare-263-join         
        StoreC                                 
        PushD        $global-memory-block      
        PushI        0                         
        Add                                    %% test
        LoadC                                  
        JumpTrue     -print-boolean-264-true   
        Label        basicBlock-35             
        PushD        $boolean-false-string     
        Jump         -print-boolean-264-join   
        Label        basicBlock-36             
        Label        -print-boolean-264-true   
        PushD        $boolean-true-string      
        Label        basicBlock-37             
        Label        -print-boolean-264-join   
        PushD        $print-format-boolean     
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        Halt                                   %% End of Execution
