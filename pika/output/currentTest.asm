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
        DataZ        20                        
        DLabel       -stringConstant-4-        
        DataI        6                         
        DataI        9                         
        DataI        13                        
        DataC        115                       %% "sum of first "
        DataC        117                       
        DataC        109                       
        DataC        32                        
        DataC        111                       
        DataC        102                       
        DataC        32                        
        DataC        102                       
        DataC        105                       
        DataC        114                       
        DataC        115                       
        DataC        116                       
        DataC        32                        
        DataC        0                         
        DLabel       -stringConstant-5-        
        DataI        6                         
        DataI        9                         
        DataI        15                        
        DataC        32                        %% " factorials is "
        DataC        102                       
        DataC        97                        
        DataC        99                        
        DataC        116                       
        DataC        111                       
        DataC        114                       
        DataC        105                       
        DataC        97                        
        DataC        108                       
        DataC        115                       
        DataC        32                        
        DataC        105                       
        DataC        115                       
        DataC        32                        
        DataC        0                         
        DLabel       -stringConstant-7-        
        DataI        6                         
        DataI        9                         
        DataI        4                         
        DataC        100                       %% "done"
        DataC        111                       
        DataC        110                       
        DataC        101                       
        DataC        0                         
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
        Label        $$main                    
        PushD        $global-memory-block      
        PushI        0                         
        Add                                    %% i
        PushI        1                         
        StoreI                                 
        PushD        $global-memory-block      
        PushI        4                         
        Add                                    %% numFactorials
        PushI        11                        
        StoreI                                 
        PushD        $global-memory-block      
        PushI        8                         
        Add                                    %% sum
        PushI        0                         
        StoreI                                 
        Label        -while-stmt-6-loop        
        PushD        $global-memory-block      
        PushI        0                         
        Add                                    %% i
        LoadI                                  
        PushD        $global-memory-block      
        PushI        4                         
        Add                                    %% numFactorials
        LoadI                                  
        Subtract                               
        JumpNeg      -compare-1-true           
        Jump         -compare-1-false          
        Label        -compare-1-true           
        PushI        1                         
        Jump         -compare-1-join           
        Label        -compare-1-false          
        PushI        0                         
        Label        -compare-1-join           
        JumpFalse    -while-stmt-6-join        
        PushD        $global-memory-block      
        PushI        12                        
        Add                                    %% j
        PushI        1                         
        StoreI                                 
        PushD        $global-memory-block      
        PushI        16                        
        Add                                    %% factorial
        PushI        1                         
        StoreI                                 
        Label        -while-stmt-3-loop        
        PushD        $global-memory-block      
        PushI        12                        
        Add                                    %% j
        LoadI                                  
        PushD        $global-memory-block      
        PushI        0                         
        Add                                    %% i
        LoadI                                  
        Subtract                               
        JumpPos      -compare-2-false          
        Jump         -compare-2-true           
        Label        -compare-2-true           
        PushI        1                         
        Jump         -compare-2-join           
        Label        -compare-2-false          
        PushI        0                         
        Label        -compare-2-join           
        JumpFalse    -while-stmt-3-join        
        PushD        $global-memory-block      
        PushI        16                        
        Add                                    %% factorial
        PushD        $global-memory-block      
        PushI        16                        
        Add                                    %% factorial
        LoadI                                  
        PushD        $global-memory-block      
        PushI        12                        
        Add                                    %% j
        LoadI                                  
        Multiply                               
        StoreI                                 
        PushD        $global-memory-block      
        PushI        12                        
        Add                                    %% j
        PushD        $global-memory-block      
        PushI        12                        
        Add                                    %% j
        LoadI                                  
        PushI        1                         
        Add                                    
        StoreI                                 
        Jump         -while-stmt-3-loop        
        Label        -while-stmt-3-join        
        PushD        $global-memory-block      
        PushI        8                         
        Add                                    %% sum
        PushD        $global-memory-block      
        PushI        8                         
        Add                                    %% sum
        LoadI                                  
        PushD        $global-memory-block      
        PushI        16                        
        Add                                    %% factorial
        LoadI                                  
        Add                                    
        StoreI                                 
        PushD        -stringConstant-4-        
        PushI        12                        
        Add                                    
        PushD        $print-format-string      
        Printf                                 
        PushD        $print-format-space       
        Printf                                 
        PushD        $global-memory-block      
        PushI        0                         
        Add                                    %% i
        LoadI                                  
        PushD        $print-format-integer     
        Printf                                 
        PushD        $print-format-space       
        Printf                                 
        PushD        -stringConstant-5-        
        PushI        12                        
        Add                                    
        PushD        $print-format-string      
        Printf                                 
        PushD        $print-format-space       
        Printf                                 
        PushD        $global-memory-block      
        PushI        8                         
        Add                                    %% sum
        LoadI                                  
        PushD        $print-format-integer     
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
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
        Jump         -while-stmt-6-loop        
        Label        -while-stmt-6-join        
        PushD        -stringConstant-7-        
        PushI        12                        
        Add                                    
        PushD        $print-format-string      
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        Halt                                   %% End of Execution
