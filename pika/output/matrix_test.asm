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
        Add                                    %% width
        PushI        4                         
        StoreI                                 
        PushD        $global-memory-block      
        PushI        4                         
        Add                                    %% height
        PushI        7                         
        StoreI                                 
        PushD        $global-memory-block      
        PushI        8                         
        Add                                    %% matrix
        Label        -array-217-               
        PushD        $global-memory-block      
        PushI        0                         
        Add                                    %% width
        LoadI                                  
        PushD        $global-memory-block      
        PushI        0                         
        Add                                    %% width
        LoadI                                  
        Duplicate                              
        JumpNeg      $$bad-index               
        Label        basicBlock-3              
        PushI        8                         
        Multiply                               
        PushI        16                        
        Add                                    
        Call         -mem-manager-allocate     
        Label        basicBlock-4              
        PushD        $array-temp-1             
        Exchange                               
        StoreI                                 
        Label        -array-217-create-record  
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
        Label        basicBlock-5              
        Label        -array-217-start-store-children 
        Label        -populate-array-218-      
        PushD        $array-temp-1             
        Exchange                               
        StoreI                                 
        PushD        $array-temp-1             
        LoadI                                  
        PushI        12                        
        Add                                    
        LoadI                                  
        PushD        $array-temp-2             
        Exchange                               
        StoreI                                 
        PushD        $array-temp-1             
        LoadI                                  
        PushI        8                         
        Add                                    
        LoadI                                  
        PushD        $array-temp-3             
        Exchange                               
        StoreI                                 
        PushD        $array-temp-4             
        PushI        16                        
        StoreI                                 
        Label        basicBlock-6              
        Label        -populate-array-218-loop  
        PushD        $array-temp-2             
        LoadI                                  
        JumpFalse    -populate-array-218-join  
        Label        basicBlock-7              
        PushD        $array-temp-1             
        LoadI                                  
        PushD        $array-temp-4             
        LoadI                                  
        Add                                    
        PushI        0                         
        StoreI                                 
        PushD        $array-temp-4             
        LoadI                                  
        PushD        $array-temp-3             
        LoadI                                  
        Add                                    
        PushD        $array-temp-4             
        Exchange                               
        StoreI                                 
        PushD        $array-temp-2             
        LoadI                                  
        PushI        1                         
        Subtract                               
        PushD        $array-temp-2             
        Exchange                               
        StoreI                                 
        Jump         -populate-array-218-loop  
        Label        basicBlock-8              
        Label        -populate-array-218-join  
        PushD        $array-temp-1             
        LoadI                                  
        Label        -array-217-end-store-children 
        StoreI                                 
        PushD        $global-memory-block      
        PushI        16                        
        Add                                    %% x
        PushI        0                         
        StoreI                                 
        Label        basicBlock-9              
        Label        -while-stmt-222-loop      
        PushD        $global-memory-block      
        PushI        16                        
        Add                                    %% x
        LoadI                                  
        PushD        $global-memory-block      
        PushI        0                         
        Add                                    %% width
        LoadI                                  
        Subtract                               
        JumpNeg      -compare-219-true         
        Jump         -compare-219-false        
        Label        basicBlock-10             
        Label        -compare-219-true         
        PushI        1                         
        Jump         -compare-219-join         
        Label        basicBlock-11             
        Label        -compare-219-false        
        PushI        0                         
        Label        basicBlock-12             
        Label        -compare-219-join         
        JumpFalse    -while-stmt-222-join      
        Label        basicBlock-13             
        PushD        $global-memory-block      
        PushI        8                         
        Add                                    %% matrix
        LoadI                                  
        PushD        $global-memory-block      
        PushI        16                        
        Add                                    %% x
        LoadI                                  
        PushD        $index-temp-2             
        Exchange                               
        StoreI                                 
        PushD        $index-temp-1             
        Exchange                               
        StoreI                                 
        PushD        $index-temp-2             
        LoadI                                  
        JumpNeg      $$bad-index               
        Label        basicBlock-14             
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
        Label        basicBlock-15             
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
        Label        -array-220-               
        PushD        $global-memory-block      
        PushI        4                         
        Add                                    %% height
        LoadI                                  
        PushD        $global-memory-block      
        PushI        4                         
        Add                                    %% height
        LoadI                                  
        Duplicate                              
        JumpNeg      $$bad-index               
        Label        basicBlock-16             
        PushI        8                         
        Multiply                               
        PushI        16                        
        Add                                    
        Call         -mem-manager-allocate     
        Label        basicBlock-17             
        PushD        $array-temp-1             
        Exchange                               
        StoreI                                 
        Label        -array-220-create-record  
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
        Label        basicBlock-18             
        Label        -array-220-start-store-children 
        Label        -populate-array-221-      
        PushD        $array-temp-1             
        Exchange                               
        StoreI                                 
        PushD        $array-temp-1             
        LoadI                                  
        PushI        12                        
        Add                                    
        LoadI                                  
        PushD        $array-temp-2             
        Exchange                               
        StoreI                                 
        PushD        $array-temp-1             
        LoadI                                  
        PushI        8                         
        Add                                    
        LoadI                                  
        PushD        $array-temp-3             
        Exchange                               
        StoreI                                 
        PushD        $array-temp-4             
        PushI        16                        
        StoreI                                 
        Label        basicBlock-19             
        Label        -populate-array-221-loop  
        PushD        $array-temp-2             
        LoadI                                  
        JumpFalse    -populate-array-221-join  
        Label        basicBlock-20             
        PushD        $array-temp-1             
        LoadI                                  
        PushD        $array-temp-4             
        LoadI                                  
        Add                                    
        PushF        0.00000                   
        StoreF                                 
        PushD        $array-temp-4             
        LoadI                                  
        PushD        $array-temp-3             
        LoadI                                  
        Add                                    
        PushD        $array-temp-4             
        Exchange                               
        StoreI                                 
        PushD        $array-temp-2             
        LoadI                                  
        PushI        1                         
        Subtract                               
        PushD        $array-temp-2             
        Exchange                               
        StoreI                                 
        Jump         -populate-array-221-loop  
        Label        basicBlock-21             
        Label        -populate-array-221-join  
        PushD        $array-temp-1             
        LoadI                                  
        Label        -array-220-end-store-children 
        StoreI                                 
        PushD        $global-memory-block      
        PushI        16                        
        Add                                    %% x
        PushD        $global-memory-block      
        PushI        16                        
        Add                                    %% x
        LoadI                                  
        PushI        1                         
        Add                                    
        StoreI                                 
        Jump         -while-stmt-222-loop      
        Label        basicBlock-22             
        Label        -while-stmt-222-join      
        PushD        $global-memory-block      
        PushI        8                         
        Add                                    %% matrix
        LoadI                                  
        Label        -print-array-223-         
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
        Label        basicBlock-23             
        Label        -print-array-223-loop     
        PushD        $print-temp-1             
        LoadI                                  
        JumpFalse    -print-array-223-join     
        Label        basicBlock-24             
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
        Label        -print-array-224-         
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
        Label        basicBlock-25             
        Label        -print-array-224-loop     
        PushD        $print-temp-1             
        LoadI                                  
        JumpFalse    -print-array-224-join     
        Label        basicBlock-26             
        PushD        $array-temp-1             
        LoadI                                  
        PushD        $print-temp-3             
        LoadI                                  
        Add                                    
        LoadF                                  
        PushD        $print-format-floating    
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
        JumpFalse    -print-array-224-join     
        Label        basicBlock-27             
        PushI        44                        
        PushD        $print-format-character   
        Printf                                 
        PushI        0                         
        PushD        $print-format-character   
        Printf                                 
        Jump         -print-array-224-loop     
        Label        basicBlock-28             
        Label        -print-array-224-join     
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
        JumpFalse    -print-array-223-join     
        Label        basicBlock-29             
        PushI        44                        
        PushD        $print-format-character   
        Printf                                 
        PushI        0                         
        PushD        $print-format-character   
        Printf                                 
        Jump         -print-array-223-loop     
        Label        basicBlock-30             
        Label        -print-array-223-join     
        PushI        93                        
        PushD        $print-format-character   
        Printf                                 
        Halt                                   %% End of Execution
        Label        basicBlock-31             
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
        Label        basicBlock-32             
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
        Label        basicBlock-33             
        PushD        $mmgr-tags-return         
        LoadI                                  
        Return                                 
        Label        basicBlock-34             
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
        Label        basicBlock-35             
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
        Label        basicBlock-36             
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
        Label        basicBlock-37             
        PushD        $mmgr-newblock-block      
        LoadI                                  
        PushD        $mmgr-alloc-current-block 
        Exchange                               
        StoreI                                 
        Label        basicBlock-38             
        Label        -mmgr-alloc-return-userblock 
        PushD        $mmgr-alloc-current-block 
        LoadI                                  
        PushI        9                         
        Add                                    
        PushD        $mmgr-alloc-return        
        LoadI                                  
        Return                                 
