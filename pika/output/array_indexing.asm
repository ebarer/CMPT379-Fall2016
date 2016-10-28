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
        DataZ        28                        
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
        Label        $$bad-index               
        PushD        $errors-bad-index         
        Jump         $$general-runtime-error   
        Label        $$general-runtime-error   
        PushD        $errors-general-message   
        Printf                                 
        Halt                                   
        Label        basicBlock-2              
        Label        $$main                    
        PushD        $global-memory-block      
        PushI        0                         
        Add                                    %% a
        Label        -array-1-                 
        PushI        3                         
        PushI        28                        
        Call         -mem-manager-allocate     
        Label        basicBlock-3              
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
        Label        basicBlock-4              
        Label        -array-1-start-store-children 
        Duplicate                              
        PushI        16                        
        Add                                    
        PushI        100                       
        StoreI                                 
        Duplicate                              
        PushI        20                        
        Add                                    
        PushI        200                       
        StoreI                                 
        Duplicate                              
        PushI        24                        
        Add                                    
        PushI        300                       
        StoreI                                 
        Label        -array-1-end-store-children 
        StoreI                                 
        PushD        $global-memory-block      
        PushI        0                         
        Add                                    %% a
        LoadI                                  
        Label        basicBlock-5              
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
        Label        basicBlock-6              
        Label        -print-array-2-loop       
        PushD        $print-temp-1             
        LoadI                                  
        JumpFalse    -print-array-2-join       
        Label        basicBlock-7              
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
        Label        basicBlock-8              
        PushI        44                        
        PushD        $print-format-character   
        Printf                                 
        PushI        0                         
        PushD        $print-format-character   
        Printf                                 
        Jump         -print-array-2-loop       
        Label        basicBlock-9              
        Label        -print-array-2-join       
        PushI        93                        
        PushD        $print-format-character   
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        PushD        $global-memory-block      
        PushI        0                         
        Add                                    %% a
        LoadI                                  
        PushI        0                         
        PushD        $index-temp-2             
        Exchange                               
        StoreI                                 
        PushD        $index-temp-1             
        Exchange                               
        StoreI                                 
        PushD        $index-temp-2             
        LoadI                                  
        JumpNeg      $$bad-index               
        Label        basicBlock-10             
        PushD        $index-temp-1             
        LoadI                                  
        PushI        12                        
        Add                                    
        LoadI                                  
        PushI        1                         
        Subtract                               
        PushD        $index-temp-2             
        LoadI                                  
        Subtract                               
        JumpNeg      $$bad-index               
        Label        basicBlock-11             
        PushD        $index-temp-1             
        LoadI                                  
        PushD        $index-temp-1             
        LoadI                                  
        PushI        8                         
        Add                                    
        LoadI                                  
        PushD        $index-temp-2             
        LoadI                                  
        Multiply                               
        PushI        16                        
        Add                                    
        Add                                    
        PushI        500                       
        StoreI                                 
        PushD        $global-memory-block      
        PushI        0                         
        Add                                    %% a
        LoadI                                  
        Label        -print-array-3-           
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
        Label        basicBlock-12             
        Label        -print-array-3-loop       
        PushD        $print-temp-1             
        LoadI                                  
        JumpFalse    -print-array-3-join       
        Label        basicBlock-13             
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
        JumpFalse    -print-array-3-join       
        Label        basicBlock-14             
        PushI        44                        
        PushD        $print-format-character   
        Printf                                 
        PushI        0                         
        PushD        $print-format-character   
        Printf                                 
        Jump         -print-array-3-loop       
        Label        basicBlock-15             
        Label        -print-array-3-join       
        PushI        93                        
        PushD        $print-format-character   
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        PushD        $global-memory-block      
        PushI        0                         
        Add                                    %% a
        LoadI                                  
        PushI        0                         
        PushD        $index-temp-2             
        Exchange                               
        StoreI                                 
        PushD        $index-temp-1             
        Exchange                               
        StoreI                                 
        PushD        $index-temp-2             
        LoadI                                  
        JumpNeg      $$bad-index               
        Label        basicBlock-16             
        PushD        $index-temp-1             
        LoadI                                  
        PushI        12                        
        Add                                    
        LoadI                                  
        PushI        1                         
        Subtract                               
        PushD        $index-temp-2             
        LoadI                                  
        Subtract                               
        JumpNeg      $$bad-index               
        Label        basicBlock-17             
        PushD        $index-temp-1             
        LoadI                                  
        PushD        $index-temp-1             
        LoadI                                  
        PushI        8                         
        Add                                    
        LoadI                                  
        PushD        $index-temp-2             
        LoadI                                  
        Multiply                               
        PushI        16                        
        Add                                    
        Add                                    
        LoadI                                  
        PushD        $print-format-integer     
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        PushD        $global-memory-block      
        PushI        8                         
        Add                                    %% b
        Label        -array-4-                 
        PushI        2                         
        PushI        24                        
        Call         -mem-manager-allocate     
        Label        basicBlock-18             
        PushD        $array-temp-1             
        Exchange                               
        StoreI                                 
        Label        -array-4-create-record    
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
        Label        basicBlock-19             
        Label        -array-4-start-store-children 
        Duplicate                              
        PushI        16                        
        Add                                    
        PushI        100                       
        StoreI                                 
        Duplicate                              
        PushI        20                        
        Add                                    
        PushI        200                       
        StoreI                                 
        Label        -array-4-end-store-children 
        StoreI                                 
        PushD        $global-memory-block      
        PushI        8                         
        Add                                    %% b
        LoadI                                  
        Label        basicBlock-20             
        Label        -print-array-5-           
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
        Label        basicBlock-21             
        Label        -print-array-5-loop       
        PushD        $print-temp-1             
        LoadI                                  
        JumpFalse    -print-array-5-join       
        Label        basicBlock-22             
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
        JumpFalse    -print-array-5-join       
        Label        basicBlock-23             
        PushI        44                        
        PushD        $print-format-character   
        Printf                                 
        PushI        0                         
        PushD        $print-format-character   
        Printf                                 
        Jump         -print-array-5-loop       
        Label        basicBlock-24             
        Label        -print-array-5-join       
        PushI        93                        
        PushD        $print-format-character   
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        PushD        $global-memory-block      
        PushI        8                         
        Add                                    %% b
        LoadI                                  
        PushI        0                         
        PushD        $index-temp-2             
        Exchange                               
        StoreI                                 
        PushD        $index-temp-1             
        Exchange                               
        StoreI                                 
        PushD        $index-temp-2             
        LoadI                                  
        JumpNeg      $$bad-index               
        Label        basicBlock-25             
        PushD        $index-temp-1             
        LoadI                                  
        PushI        12                        
        Add                                    
        LoadI                                  
        PushI        1                         
        Subtract                               
        PushD        $index-temp-2             
        LoadI                                  
        Subtract                               
        JumpNeg      $$bad-index               
        Label        basicBlock-26             
        PushD        $index-temp-1             
        LoadI                                  
        PushD        $index-temp-1             
        LoadI                                  
        PushI        8                         
        Add                                    
        LoadI                                  
        PushD        $index-temp-2             
        LoadI                                  
        Multiply                               
        PushI        16                        
        Add                                    
        Add                                    
        PushI        99                        
        StoreI                                 
        PushD        $global-memory-block      
        PushI        8                         
        Add                                    %% b
        LoadI                                  
        Label        -print-array-6-           
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
        Label        basicBlock-27             
        Label        -print-array-6-loop       
        PushD        $print-temp-1             
        LoadI                                  
        JumpFalse    -print-array-6-join       
        Label        basicBlock-28             
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
        JumpFalse    -print-array-6-join       
        Label        basicBlock-29             
        PushI        44                        
        PushD        $print-format-character   
        Printf                                 
        PushI        0                         
        PushD        $print-format-character   
        Printf                                 
        Jump         -print-array-6-loop       
        Label        basicBlock-30             
        Label        -print-array-6-join       
        PushI        93                        
        PushD        $print-format-character   
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        PushD        $global-memory-block      
        PushI        8                         
        Add                                    %% b
        LoadI                                  
        PushI        0                         
        PushD        $index-temp-2             
        Exchange                               
        StoreI                                 
        PushD        $index-temp-1             
        Exchange                               
        StoreI                                 
        PushD        $index-temp-2             
        LoadI                                  
        JumpNeg      $$bad-index               
        Label        basicBlock-31             
        PushD        $index-temp-1             
        LoadI                                  
        PushI        12                        
        Add                                    
        LoadI                                  
        PushI        1                         
        Subtract                               
        PushD        $index-temp-2             
        LoadI                                  
        Subtract                               
        JumpNeg      $$bad-index               
        Label        basicBlock-32             
        PushD        $index-temp-1             
        LoadI                                  
        PushD        $index-temp-1             
        LoadI                                  
        PushI        8                         
        Add                                    
        LoadI                                  
        PushD        $index-temp-2             
        LoadI                                  
        Multiply                               
        PushI        16                        
        Add                                    
        Add                                    
        LoadI                                  
        PushD        $print-format-integer     
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        PushD        $global-memory-block      
        PushI        16                        
        Add                                    %% c
        Label        -array-9-                 
        PushI        2                         
        PushI        32                        
        Call         -mem-manager-allocate     
        Label        basicBlock-33             
        PushD        $array-temp-1             
        Exchange                               
        StoreI                                 
        Label        -array-9-create-record    
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
        Label        basicBlock-34             
        Label        -array-9-start-store-children 
        Duplicate                              
        PushI        16                        
        Add                                    
        Label        -array-7-                 
        PushI        2                         
        PushI        24                        
        Call         -mem-manager-allocate     
        Label        basicBlock-35             
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
        Label        basicBlock-36             
        Label        -array-7-start-store-children 
        Duplicate                              
        PushI        16                        
        Add                                    
        PushI        100                       
        StoreI                                 
        Duplicate                              
        PushI        20                        
        Add                                    
        PushI        200                       
        StoreI                                 
        Label        -array-7-end-store-children 
        StoreI                                 
        Duplicate                              
        PushI        24                        
        Add                                    
        Label        basicBlock-37             
        Label        -array-8-                 
        PushI        2                         
        PushI        24                        
        Call         -mem-manager-allocate     
        Label        basicBlock-38             
        PushD        $array-temp-1             
        Exchange                               
        StoreI                                 
        Label        -array-8-create-record    
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
        Label        basicBlock-39             
        Label        -array-8-start-store-children 
        Duplicate                              
        PushI        16                        
        Add                                    
        PushI        300                       
        StoreI                                 
        Duplicate                              
        PushI        20                        
        Add                                    
        PushI        400                       
        StoreI                                 
        Label        -array-8-end-store-children 
        StoreI                                 
        Label        basicBlock-40             
        Label        -array-9-end-store-children 
        StoreI                                 
        PushD        $global-memory-block      
        PushI        16                        
        Add                                    %% c
        LoadI                                  
        Label        -print-array-10-          
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
        Label        basicBlock-41             
        Label        -print-array-10-loop      
        PushD        $print-temp-1             
        LoadI                                  
        JumpFalse    -print-array-10-join      
        Label        basicBlock-42             
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
        Label        basicBlock-43             
        Label        -print-array-11-loop      
        PushD        $print-temp-1             
        LoadI                                  
        JumpFalse    -print-array-11-join      
        Label        basicBlock-44             
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
        JumpFalse    -print-array-11-join      
        Label        basicBlock-45             
        PushI        44                        
        PushD        $print-format-character   
        Printf                                 
        PushI        0                         
        PushD        $print-format-character   
        Printf                                 
        Jump         -print-array-11-loop      
        Label        basicBlock-46             
        Label        -print-array-11-join      
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
        JumpFalse    -print-array-10-join      
        Label        basicBlock-47             
        PushI        44                        
        PushD        $print-format-character   
        Printf                                 
        PushI        0                         
        PushD        $print-format-character   
        Printf                                 
        Jump         -print-array-10-loop      
        Label        basicBlock-48             
        Label        -print-array-10-join      
        PushI        93                        
        PushD        $print-format-character   
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        PushD        $global-memory-block      
        PushI        16                        
        Add                                    %% c
        LoadI                                  
        PushI        0                         
        PushD        $index-temp-2             
        Exchange                               
        StoreI                                 
        PushD        $index-temp-1             
        Exchange                               
        StoreI                                 
        PushD        $index-temp-2             
        LoadI                                  
        JumpNeg      $$bad-index               
        Label        basicBlock-49             
        PushD        $index-temp-1             
        LoadI                                  
        PushI        12                        
        Add                                    
        LoadI                                  
        PushI        1                         
        Subtract                               
        PushD        $index-temp-2             
        LoadI                                  
        Subtract                               
        JumpNeg      $$bad-index               
        Label        basicBlock-50             
        PushD        $index-temp-1             
        LoadI                                  
        PushD        $index-temp-1             
        LoadI                                  
        PushI        8                         
        Add                                    
        LoadI                                  
        PushD        $index-temp-2             
        LoadI                                  
        Multiply                               
        PushI        16                        
        Add                                    
        Add                                    
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
        Label        basicBlock-51             
        Label        -print-array-12-loop      
        PushD        $print-temp-1             
        LoadI                                  
        JumpFalse    -print-array-12-join      
        Label        basicBlock-52             
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
        Label        basicBlock-53             
        PushI        44                        
        PushD        $print-format-character   
        Printf                                 
        PushI        0                         
        PushD        $print-format-character   
        Printf                                 
        Jump         -print-array-12-loop      
        Label        basicBlock-54             
        Label        -print-array-12-join      
        PushI        93                        
        PushD        $print-format-character   
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        PushD        $global-memory-block      
        PushI        16                        
        Add                                    %% c
        LoadI                                  
        PushI        0                         
        PushD        $index-temp-2             
        Exchange                               
        StoreI                                 
        PushD        $index-temp-1             
        Exchange                               
        StoreI                                 
        PushD        $index-temp-2             
        LoadI                                  
        JumpNeg      $$bad-index               
        Label        basicBlock-55             
        PushD        $index-temp-1             
        LoadI                                  
        PushI        12                        
        Add                                    
        LoadI                                  
        PushI        1                         
        Subtract                               
        PushD        $index-temp-2             
        LoadI                                  
        Subtract                               
        JumpNeg      $$bad-index               
        Label        basicBlock-56             
        PushD        $index-temp-1             
        LoadI                                  
        PushD        $index-temp-1             
        LoadI                                  
        PushI        8                         
        Add                                    
        LoadI                                  
        PushD        $index-temp-2             
        LoadI                                  
        Multiply                               
        PushI        16                        
        Add                                    
        Add                                    
        LoadI                                  
        PushI        0                         
        PushD        $index-temp-2             
        Exchange                               
        StoreI                                 
        PushD        $index-temp-1             
        Exchange                               
        StoreI                                 
        PushD        $index-temp-2             
        LoadI                                  
        JumpNeg      $$bad-index               
        Label        basicBlock-57             
        PushD        $index-temp-1             
        LoadI                                  
        PushI        12                        
        Add                                    
        LoadI                                  
        PushI        1                         
        Subtract                               
        PushD        $index-temp-2             
        LoadI                                  
        Subtract                               
        JumpNeg      $$bad-index               
        Label        basicBlock-58             
        PushD        $index-temp-1             
        LoadI                                  
        PushD        $index-temp-1             
        LoadI                                  
        PushI        8                         
        Add                                    
        LoadI                                  
        PushD        $index-temp-2             
        LoadI                                  
        Multiply                               
        PushI        16                        
        Add                                    
        Add                                    
        LoadI                                  
        PushD        $print-format-integer     
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        PushD        $global-memory-block      
        PushI        16                        
        Add                                    %% c
        LoadI                                  
        PushI        0                         
        PushD        $index-temp-2             
        Exchange                               
        StoreI                                 
        PushD        $index-temp-1             
        Exchange                               
        StoreI                                 
        PushD        $index-temp-2             
        LoadI                                  
        JumpNeg      $$bad-index               
        Label        basicBlock-59             
        PushD        $index-temp-1             
        LoadI                                  
        PushI        12                        
        Add                                    
        LoadI                                  
        PushI        1                         
        Subtract                               
        PushD        $index-temp-2             
        LoadI                                  
        Subtract                               
        JumpNeg      $$bad-index               
        Label        basicBlock-60             
        PushD        $index-temp-1             
        LoadI                                  
        PushD        $index-temp-1             
        LoadI                                  
        PushI        8                         
        Add                                    
        LoadI                                  
        PushD        $index-temp-2             
        LoadI                                  
        Multiply                               
        PushI        16                        
        Add                                    
        Add                                    
        LoadI                                  
        PushI        0                         
        PushD        $index-temp-2             
        Exchange                               
        StoreI                                 
        PushD        $index-temp-1             
        Exchange                               
        StoreI                                 
        PushD        $index-temp-2             
        LoadI                                  
        JumpNeg      $$bad-index               
        Label        basicBlock-61             
        PushD        $index-temp-1             
        LoadI                                  
        PushI        12                        
        Add                                    
        LoadI                                  
        PushI        1                         
        Subtract                               
        PushD        $index-temp-2             
        LoadI                                  
        Subtract                               
        JumpNeg      $$bad-index               
        Label        basicBlock-62             
        PushD        $index-temp-1             
        LoadI                                  
        PushD        $index-temp-1             
        LoadI                                  
        PushI        8                         
        Add                                    
        LoadI                                  
        PushD        $index-temp-2             
        LoadI                                  
        Multiply                               
        PushI        16                        
        Add                                    
        Add                                    
        PushI        99                        
        StoreI                                 
        PushD        $global-memory-block      
        PushI        16                        
        Add                                    %% c
        LoadI                                  
        PushI        0                         
        PushD        $index-temp-2             
        Exchange                               
        StoreI                                 
        PushD        $index-temp-1             
        Exchange                               
        StoreI                                 
        PushD        $index-temp-2             
        LoadI                                  
        JumpNeg      $$bad-index               
        Label        basicBlock-63             
        PushD        $index-temp-1             
        LoadI                                  
        PushI        12                        
        Add                                    
        LoadI                                  
        PushI        1                         
        Subtract                               
        PushD        $index-temp-2             
        LoadI                                  
        Subtract                               
        JumpNeg      $$bad-index               
        Label        basicBlock-64             
        PushD        $index-temp-1             
        LoadI                                  
        PushD        $index-temp-1             
        LoadI                                  
        PushI        8                         
        Add                                    
        LoadI                                  
        PushD        $index-temp-2             
        LoadI                                  
        Multiply                               
        PushI        16                        
        Add                                    
        Add                                    
        LoadI                                  
        PushI        0                         
        PushD        $index-temp-2             
        Exchange                               
        StoreI                                 
        PushD        $index-temp-1             
        Exchange                               
        StoreI                                 
        PushD        $index-temp-2             
        LoadI                                  
        JumpNeg      $$bad-index               
        Label        basicBlock-65             
        PushD        $index-temp-1             
        LoadI                                  
        PushI        12                        
        Add                                    
        LoadI                                  
        PushI        1                         
        Subtract                               
        PushD        $index-temp-2             
        LoadI                                  
        Subtract                               
        JumpNeg      $$bad-index               
        Label        basicBlock-66             
        PushD        $index-temp-1             
        LoadI                                  
        PushD        $index-temp-1             
        LoadI                                  
        PushI        8                         
        Add                                    
        LoadI                                  
        PushD        $index-temp-2             
        LoadI                                  
        Multiply                               
        PushI        16                        
        Add                                    
        Add                                    
        LoadI                                  
        PushD        $print-format-integer     
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        PushD        $global-memory-block      
        PushI        16                        
        Add                                    %% c
        LoadI                                  
        PushI        0                         
        PushD        $index-temp-2             
        Exchange                               
        StoreI                                 
        PushD        $index-temp-1             
        Exchange                               
        StoreI                                 
        PushD        $index-temp-2             
        LoadI                                  
        JumpNeg      $$bad-index               
        Label        basicBlock-67             
        PushD        $index-temp-1             
        LoadI                                  
        PushI        12                        
        Add                                    
        LoadI                                  
        PushI        1                         
        Subtract                               
        PushD        $index-temp-2             
        LoadI                                  
        Subtract                               
        JumpNeg      $$bad-index               
        Label        basicBlock-68             
        PushD        $index-temp-1             
        LoadI                                  
        PushD        $index-temp-1             
        LoadI                                  
        PushI        8                         
        Add                                    
        LoadI                                  
        PushD        $index-temp-2             
        LoadI                                  
        Multiply                               
        PushI        16                        
        Add                                    
        Add                                    
        LoadI                                  
        Label        -print-array-13-          
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
        Label        basicBlock-69             
        Label        -print-array-13-loop      
        PushD        $print-temp-1             
        LoadI                                  
        JumpFalse    -print-array-13-join      
        Label        basicBlock-70             
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
        JumpFalse    -print-array-13-join      
        Label        basicBlock-71             
        PushI        44                        
        PushD        $print-format-character   
        Printf                                 
        PushI        0                         
        PushD        $print-format-character   
        Printf                                 
        Jump         -print-array-13-loop      
        Label        basicBlock-72             
        Label        -print-array-13-join      
        PushI        93                        
        PushD        $print-format-character   
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        PushD        $global-memory-block      
        PushI        16                        
        Add                                    %% c
        LoadI                                  
        Label        -print-array-14-          
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
        Label        basicBlock-73             
        Label        -print-array-14-loop      
        PushD        $print-temp-1             
        LoadI                                  
        JumpFalse    -print-array-14-join      
        Label        basicBlock-74             
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
        Label        -print-array-15-          
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
        Label        basicBlock-75             
        Label        -print-array-15-loop      
        PushD        $print-temp-1             
        LoadI                                  
        JumpFalse    -print-array-15-join      
        Label        basicBlock-76             
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
        JumpFalse    -print-array-15-join      
        Label        basicBlock-77             
        PushI        44                        
        PushD        $print-format-character   
        Printf                                 
        PushI        0                         
        PushD        $print-format-character   
        Printf                                 
        Jump         -print-array-15-loop      
        Label        basicBlock-78             
        Label        -print-array-15-join      
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
        JumpFalse    -print-array-14-join      
        Label        basicBlock-79             
        PushI        44                        
        PushD        $print-format-character   
        Printf                                 
        PushI        0                         
        PushD        $print-format-character   
        Printf                                 
        Jump         -print-array-14-loop      
        Label        basicBlock-80             
        Label        -print-array-14-join      
        PushI        93                        
        PushD        $print-format-character   
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        PushD        $global-memory-block      
        PushI        16                        
        Add                                    %% c
        LoadI                                  
        PushI        0                         
        PushD        $index-temp-2             
        Exchange                               
        StoreI                                 
        PushD        $index-temp-1             
        Exchange                               
        StoreI                                 
        PushD        $index-temp-2             
        LoadI                                  
        JumpNeg      $$bad-index               
        Label        basicBlock-81             
        PushD        $index-temp-1             
        LoadI                                  
        PushI        12                        
        Add                                    
        LoadI                                  
        PushI        1                         
        Subtract                               
        PushD        $index-temp-2             
        LoadI                                  
        Subtract                               
        JumpNeg      $$bad-index               
        Label        basicBlock-82             
        PushD        $index-temp-1             
        LoadI                                  
        PushD        $index-temp-1             
        LoadI                                  
        PushI        8                         
        Add                                    
        LoadI                                  
        PushD        $index-temp-2             
        LoadI                                  
        Multiply                               
        PushI        16                        
        Add                                    
        Add                                    
        LoadI                                  
        PushI        12                        
        Add                                    
        LoadI                                  
        PushD        $print-format-integer     
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        PushD        $global-memory-block      
        PushI        24                        
        Add                                    %% i
        PushI        2                         
        StoreI                                 
        Label        -array-16-                
        PushI        3                         
        PushI        28                        
        Call         -mem-manager-allocate     
        Label        basicBlock-83             
        PushD        $array-temp-1             
        Exchange                               
        StoreI                                 
        Label        -array-16-create-record   
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
        Label        basicBlock-84             
        Label        -array-16-start-store-children 
        Duplicate                              
        PushI        16                        
        Add                                    
        PushI        100                       
        StoreI                                 
        Duplicate                              
        PushI        20                        
        Add                                    
        PushI        200                       
        StoreI                                 
        Duplicate                              
        PushI        24                        
        Add                                    
        PushI        300                       
        StoreI                                 
        Label        -array-16-end-store-children 
        PushD        $global-memory-block      
        PushI        24                        
        Add                                    %% i
        LoadI                                  
        PushI        2                         
        Multiply                               
        PushI        2                         
        Subtract                               
        PushD        $index-temp-2             
        Exchange                               
        StoreI                                 
        PushD        $index-temp-1             
        Exchange                               
        StoreI                                 
        PushD        $index-temp-2             
        LoadI                                  
        JumpNeg      $$bad-index               
        Label        basicBlock-85             
        PushD        $index-temp-1             
        LoadI                                  
        PushI        12                        
        Add                                    
        LoadI                                  
        PushI        1                         
        Subtract                               
        PushD        $index-temp-2             
        LoadI                                  
        Subtract                               
        JumpNeg      $$bad-index               
        Label        basicBlock-86             
        PushD        $index-temp-1             
        LoadI                                  
        PushD        $index-temp-1             
        LoadI                                  
        PushI        8                         
        Add                                    
        LoadI                                  
        PushD        $index-temp-2             
        LoadI                                  
        Multiply                               
        PushI        16                        
        Add                                    
        Add                                    
        LoadI                                  
        PushD        $print-format-integer     
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        Label        -array-17-                
        PushI        3                         
        PushI        28                        
        Call         -mem-manager-allocate     
        Label        basicBlock-87             
        PushD        $array-temp-1             
        Exchange                               
        StoreI                                 
        Label        -array-17-create-record   
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
        Label        basicBlock-88             
        Label        -array-17-start-store-children 
        Duplicate                              
        PushI        16                        
        Add                                    
        PushI        1000                      
        StoreI                                 
        Duplicate                              
        PushI        20                        
        Add                                    
        PushI        2000                      
        StoreI                                 
        Duplicate                              
        PushI        24                        
        Add                                    
        PushI        3000                      
        StoreI                                 
        Label        -array-17-end-store-children 
        PushI        1                         
        PushD        $index-temp-2             
        Exchange                               
        StoreI                                 
        PushD        $index-temp-1             
        Exchange                               
        StoreI                                 
        PushD        $index-temp-2             
        LoadI                                  
        JumpNeg      $$bad-index               
        Label        basicBlock-89             
        PushD        $index-temp-1             
        LoadI                                  
        PushI        12                        
        Add                                    
        LoadI                                  
        PushI        1                         
        Subtract                               
        PushD        $index-temp-2             
        LoadI                                  
        Subtract                               
        JumpNeg      $$bad-index               
        Label        basicBlock-90             
        PushD        $index-temp-1             
        LoadI                                  
        PushD        $index-temp-1             
        LoadI                                  
        PushI        8                         
        Add                                    
        LoadI                                  
        PushD        $index-temp-2             
        LoadI                                  
        Multiply                               
        PushI        16                        
        Add                                    
        Add                                    
        LoadI                                  
        PushD        $print-format-integer     
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        Halt                                   %% End of Execution
        Label        basicBlock-91             
        Label        -mem-manager-make-tags    
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
        Label        basicBlock-92             
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
        Label        basicBlock-93             
        PushD        $mmgr-tags-return         
        LoadI                                  
        Return                                 
        Label        basicBlock-94             
        Label        -mem-manager-one-tag      
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
        Label        basicBlock-95             
        Label        -mem-manager-allocate     
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
        Label        basicBlock-96             
        Label        -mmgr-alloc-process-current 
        PushD        $mmgr-alloc-current-block 
        LoadI                                  
        JumpFalse    -mmgr-alloc-no-block-works 
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
        Label        basicBlock-97             
        PushD        $mmgr-newblock-block      
        LoadI                                  
        PushD        $mmgr-alloc-current-block 
        Exchange                               
        StoreI                                 
        Label        basicBlock-98             
        Label        -mmgr-alloc-return-userblock 
        PushD        $mmgr-alloc-current-block 
        LoadI                                  
        PushI        9                         
        Add                                    
        PushD        $mmgr-alloc-return        
        LoadI                                  
        Return                                 
