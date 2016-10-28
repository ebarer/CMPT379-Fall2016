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
        DataZ        32                        
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
        Label        $$general-runtime-error   
        PushD        $errors-general-message   
        Printf                                 
        Halt                                   
        Label        basicBlock-2              
        Label        $$divide-by-zero          
        PushD        $errors-divide-by-zero    
        Jump         $$general-runtime-error   
        Label        basicBlock-3              
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
        Label        basicBlock-4              
        Label        gcd-loop                  
        PushD        $rational-temp-denominator-2 
        LoadI                                  
        JumpFalse    gcd-exit-loop             
        Label        basicBlock-5              
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
        Label        basicBlock-6              
        Label        gcd-exit-loop             
        PushD        $rational-temp-numerator-2 
        LoadI                                  
        Duplicate                              
        JumpPos      gcd-skip-negate-loop      
        Label        basicBlock-7              
        Negate                                 
        Label        basicBlock-8              
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
        Label        basicBlock-9              
        Label        $$main                    
        PushD        $global-memory-block      
        PushI        0                         
        Add                                    %% myRat1
        PushI        -3                        
        PushI        8                         
        PushD        $rational-temp-denominator-1 
        Exchange                               
        StoreI                                 
        PushD        $rational-temp-numerator-1 
        Exchange                               
        StoreI                                 
        Call         $sub-rational-find-gcd    
        Label        basicBlock-10             
        Duplicate                              
        PushI        4                         
        Add                                    
        PushD        $rational-temp-denominator-1 
        LoadI                                  
        StoreI                                 
        PushD        $rational-temp-numerator-1 
        LoadI                                  
        StoreI                                 
        PushD        $global-memory-block      
        PushI        0                         
        Add                                    %% myRat1
        Duplicate                              
        PushI        4                         
        Add                                    
        LoadI                                  
        Exchange                               
        LoadI                                  
        Exchange                               
        PushD        $rational-temp-denominator-1 
        Exchange                               
        StoreI                                 
        PushD        $rational-temp-numerator-1 
        Exchange                               
        StoreI                                 
        Label        -print-rational-243-      
        PushD        $rational-temp-numerator-1 
        LoadI                                  
        PushD        $rational-temp-denominator-1 
        LoadI                                  
        Duplicate                              
        JumpFalse    $$divide-by-zero          
        Label        basicBlock-11             
        Divide                                 
        PushD        $print-rational-temp-1    
        Exchange                               
        StoreI                                 
        PushD        $rational-temp-numerator-1 
        LoadI                                  
        PushD        $rational-temp-denominator-1 
        LoadI                                  
        Remainder                              
        PushD        $print-rational-temp-2    
        Exchange                               
        StoreI                                 
        PushD        $rational-temp-denominator-1 
        LoadI                                  
        PushD        $print-rational-temp-3    
        Exchange                               
        StoreI                                 
        PushD        $print-rational-temp-2    
        LoadI                                  
        JumpFalse    -print-rational-243-skip-fraction 
        Label        basicBlock-12             
        PushD        $print-rational-temp-1    
        LoadI                                  
        JumpFalse    -print-rational-243-skip-whole 
        Label        basicBlock-13             
        PushD        $print-rational-temp-3    
        LoadI                                  
        PushD        $print-rational-temp-2    
        LoadI                                  
        Duplicate                              
        JumpPos      -print-rational-243-skip-negate 
        Label        basicBlock-14             
        Negate                                 
        Label        basicBlock-15             
        Label        -print-rational-243-skip-negate 
        PushD        $print-rational-temp-1    
        LoadI                                  
        PushD        $print-format-rational    
        Jump         -print-rational-243-join  
        Label        basicBlock-16             
        Label        -print-rational-243-skip-fraction 
        PushD        $print-rational-temp-1    
        LoadI                                  
        PushD        $print-format-integer     
        Jump         -print-rational-243-join  
        Label        basicBlock-17             
        Label        -print-rational-243-skip-whole 
        PushD        $print-rational-temp-3    
        LoadI                                  
        PushD        $print-rational-temp-2    
        LoadI                                  
        Duplicate                              
        JumpPos      -print-rational-243-skip-fraction-negate 
        Label        basicBlock-18             
        Negate                                 
        PushD        $print-format-rational-neg-fraction 
        Jump         -print-rational-243-join  
        Label        basicBlock-19             
        Label        -print-rational-243-skip-fraction-negate 
        PushD        $print-format-rational-fraction 
        Jump         -print-rational-243-join  
        Label        -print-rational-243-join  
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        PushD        $global-memory-block      
        PushI        0                         
        Add                                    %% myRat1
        PushI        -8                        
        PushI        8                         
        PushD        $rational-temp-denominator-1 
        Exchange                               
        StoreI                                 
        PushD        $rational-temp-numerator-1 
        Exchange                               
        StoreI                                 
        Call         $sub-rational-find-gcd    
        Label        basicBlock-20             
        Duplicate                              
        PushI        4                         
        Add                                    
        PushD        $rational-temp-denominator-1 
        LoadI                                  
        StoreI                                 
        PushD        $rational-temp-numerator-1 
        LoadI                                  
        StoreI                                 
        PushD        $global-memory-block      
        PushI        0                         
        Add                                    %% myRat1
        Duplicate                              
        PushI        4                         
        Add                                    
        LoadI                                  
        Exchange                               
        LoadI                                  
        Exchange                               
        PushD        $rational-temp-denominator-1 
        Exchange                               
        StoreI                                 
        PushD        $rational-temp-numerator-1 
        Exchange                               
        StoreI                                 
        Label        -print-rational-244-      
        PushD        $rational-temp-numerator-1 
        LoadI                                  
        PushD        $rational-temp-denominator-1 
        LoadI                                  
        Duplicate                              
        JumpFalse    $$divide-by-zero          
        Label        basicBlock-21             
        Divide                                 
        PushD        $print-rational-temp-1    
        Exchange                               
        StoreI                                 
        PushD        $rational-temp-numerator-1 
        LoadI                                  
        PushD        $rational-temp-denominator-1 
        LoadI                                  
        Remainder                              
        PushD        $print-rational-temp-2    
        Exchange                               
        StoreI                                 
        PushD        $rational-temp-denominator-1 
        LoadI                                  
        PushD        $print-rational-temp-3    
        Exchange                               
        StoreI                                 
        PushD        $print-rational-temp-2    
        LoadI                                  
        JumpFalse    -print-rational-244-skip-fraction 
        Label        basicBlock-22             
        PushD        $print-rational-temp-1    
        LoadI                                  
        JumpFalse    -print-rational-244-skip-whole 
        Label        basicBlock-23             
        PushD        $print-rational-temp-3    
        LoadI                                  
        PushD        $print-rational-temp-2    
        LoadI                                  
        Duplicate                              
        JumpPos      -print-rational-244-skip-negate 
        Label        basicBlock-24             
        Negate                                 
        Label        basicBlock-25             
        Label        -print-rational-244-skip-negate 
        PushD        $print-rational-temp-1    
        LoadI                                  
        PushD        $print-format-rational    
        Jump         -print-rational-244-join  
        Label        basicBlock-26             
        Label        -print-rational-244-skip-fraction 
        PushD        $print-rational-temp-1    
        LoadI                                  
        PushD        $print-format-integer     
        Jump         -print-rational-244-join  
        Label        basicBlock-27             
        Label        -print-rational-244-skip-whole 
        PushD        $print-rational-temp-3    
        LoadI                                  
        PushD        $print-rational-temp-2    
        LoadI                                  
        Duplicate                              
        JumpPos      -print-rational-244-skip-fraction-negate 
        Label        basicBlock-28             
        Negate                                 
        PushD        $print-format-rational-neg-fraction 
        Jump         -print-rational-244-join  
        Label        basicBlock-29             
        Label        -print-rational-244-skip-fraction-negate 
        PushD        $print-format-rational-fraction 
        Jump         -print-rational-244-join  
        Label        -print-rational-244-join  
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        PushD        $global-memory-block      
        PushI        0                         
        Add                                    %% myRat1
        PushI        0                         
        PushI        8                         
        PushD        $rational-temp-denominator-1 
        Exchange                               
        StoreI                                 
        PushD        $rational-temp-numerator-1 
        Exchange                               
        StoreI                                 
        Call         $sub-rational-find-gcd    
        Label        basicBlock-30             
        Duplicate                              
        PushI        4                         
        Add                                    
        PushD        $rational-temp-denominator-1 
        LoadI                                  
        StoreI                                 
        PushD        $rational-temp-numerator-1 
        LoadI                                  
        StoreI                                 
        PushD        $global-memory-block      
        PushI        0                         
        Add                                    %% myRat1
        Duplicate                              
        PushI        4                         
        Add                                    
        LoadI                                  
        Exchange                               
        LoadI                                  
        Exchange                               
        PushD        $rational-temp-denominator-1 
        Exchange                               
        StoreI                                 
        PushD        $rational-temp-numerator-1 
        Exchange                               
        StoreI                                 
        Label        -print-rational-245-      
        PushD        $rational-temp-numerator-1 
        LoadI                                  
        PushD        $rational-temp-denominator-1 
        LoadI                                  
        Duplicate                              
        JumpFalse    $$divide-by-zero          
        Label        basicBlock-31             
        Divide                                 
        PushD        $print-rational-temp-1    
        Exchange                               
        StoreI                                 
        PushD        $rational-temp-numerator-1 
        LoadI                                  
        PushD        $rational-temp-denominator-1 
        LoadI                                  
        Remainder                              
        PushD        $print-rational-temp-2    
        Exchange                               
        StoreI                                 
        PushD        $rational-temp-denominator-1 
        LoadI                                  
        PushD        $print-rational-temp-3    
        Exchange                               
        StoreI                                 
        PushD        $print-rational-temp-2    
        LoadI                                  
        JumpFalse    -print-rational-245-skip-fraction 
        Label        basicBlock-32             
        PushD        $print-rational-temp-1    
        LoadI                                  
        JumpFalse    -print-rational-245-skip-whole 
        Label        basicBlock-33             
        PushD        $print-rational-temp-3    
        LoadI                                  
        PushD        $print-rational-temp-2    
        LoadI                                  
        Duplicate                              
        JumpPos      -print-rational-245-skip-negate 
        Label        basicBlock-34             
        Negate                                 
        Label        basicBlock-35             
        Label        -print-rational-245-skip-negate 
        PushD        $print-rational-temp-1    
        LoadI                                  
        PushD        $print-format-rational    
        Jump         -print-rational-245-join  
        Label        basicBlock-36             
        Label        -print-rational-245-skip-fraction 
        PushD        $print-rational-temp-1    
        LoadI                                  
        PushD        $print-format-integer     
        Jump         -print-rational-245-join  
        Label        basicBlock-37             
        Label        -print-rational-245-skip-whole 
        PushD        $print-rational-temp-3    
        LoadI                                  
        PushD        $print-rational-temp-2    
        LoadI                                  
        Duplicate                              
        JumpPos      -print-rational-245-skip-fraction-negate 
        Label        basicBlock-38             
        Negate                                 
        PushD        $print-format-rational-neg-fraction 
        Jump         -print-rational-245-join  
        Label        basicBlock-39             
        Label        -print-rational-245-skip-fraction-negate 
        PushD        $print-format-rational-fraction 
        Jump         -print-rational-245-join  
        Label        -print-rational-245-join  
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        PushD        $global-memory-block      
        PushI        0                         
        Add                                    %% myRat1
        PushI        -22                       
        PushI        8                         
        PushD        $rational-temp-denominator-1 
        Exchange                               
        StoreI                                 
        PushD        $rational-temp-numerator-1 
        Exchange                               
        StoreI                                 
        Call         $sub-rational-find-gcd    
        Label        basicBlock-40             
        Duplicate                              
        PushI        4                         
        Add                                    
        PushD        $rational-temp-denominator-1 
        LoadI                                  
        StoreI                                 
        PushD        $rational-temp-numerator-1 
        LoadI                                  
        StoreI                                 
        PushD        $global-memory-block      
        PushI        0                         
        Add                                    %% myRat1
        Duplicate                              
        PushI        4                         
        Add                                    
        LoadI                                  
        Exchange                               
        LoadI                                  
        Exchange                               
        PushD        $rational-temp-denominator-1 
        Exchange                               
        StoreI                                 
        PushD        $rational-temp-numerator-1 
        Exchange                               
        StoreI                                 
        Label        -print-rational-246-      
        PushD        $rational-temp-numerator-1 
        LoadI                                  
        PushD        $rational-temp-denominator-1 
        LoadI                                  
        Duplicate                              
        JumpFalse    $$divide-by-zero          
        Label        basicBlock-41             
        Divide                                 
        PushD        $print-rational-temp-1    
        Exchange                               
        StoreI                                 
        PushD        $rational-temp-numerator-1 
        LoadI                                  
        PushD        $rational-temp-denominator-1 
        LoadI                                  
        Remainder                              
        PushD        $print-rational-temp-2    
        Exchange                               
        StoreI                                 
        PushD        $rational-temp-denominator-1 
        LoadI                                  
        PushD        $print-rational-temp-3    
        Exchange                               
        StoreI                                 
        PushD        $print-rational-temp-2    
        LoadI                                  
        JumpFalse    -print-rational-246-skip-fraction 
        Label        basicBlock-42             
        PushD        $print-rational-temp-1    
        LoadI                                  
        JumpFalse    -print-rational-246-skip-whole 
        Label        basicBlock-43             
        PushD        $print-rational-temp-3    
        LoadI                                  
        PushD        $print-rational-temp-2    
        LoadI                                  
        Duplicate                              
        JumpPos      -print-rational-246-skip-negate 
        Label        basicBlock-44             
        Negate                                 
        Label        basicBlock-45             
        Label        -print-rational-246-skip-negate 
        PushD        $print-rational-temp-1    
        LoadI                                  
        PushD        $print-format-rational    
        Jump         -print-rational-246-join  
        Label        basicBlock-46             
        Label        -print-rational-246-skip-fraction 
        PushD        $print-rational-temp-1    
        LoadI                                  
        PushD        $print-format-integer     
        Jump         -print-rational-246-join  
        Label        basicBlock-47             
        Label        -print-rational-246-skip-whole 
        PushD        $print-rational-temp-3    
        LoadI                                  
        PushD        $print-rational-temp-2    
        LoadI                                  
        Duplicate                              
        JumpPos      -print-rational-246-skip-fraction-negate 
        Label        basicBlock-48             
        Negate                                 
        PushD        $print-format-rational-neg-fraction 
        Jump         -print-rational-246-join  
        Label        basicBlock-49             
        Label        -print-rational-246-skip-fraction-negate 
        PushD        $print-format-rational-fraction 
        Jump         -print-rational-246-join  
        Label        -print-rational-246-join  
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        PushD        $global-memory-block      
        PushI        8                         
        Add                                    %% myRat2
        PushI        5                         
        PushI        10                        
        PushD        $rational-temp-denominator-1 
        Exchange                               
        StoreI                                 
        PushD        $rational-temp-numerator-1 
        Exchange                               
        StoreI                                 
        Call         $sub-rational-find-gcd    
        Label        basicBlock-50             
        Duplicate                              
        PushI        4                         
        Add                                    
        PushD        $rational-temp-denominator-1 
        LoadI                                  
        StoreI                                 
        PushD        $rational-temp-numerator-1 
        LoadI                                  
        StoreI                                 
        PushD        $global-memory-block      
        PushI        8                         
        Add                                    %% myRat2
        Duplicate                              
        PushI        4                         
        Add                                    
        LoadI                                  
        Exchange                               
        LoadI                                  
        Exchange                               
        PushD        $rational-temp-denominator-1 
        Exchange                               
        StoreI                                 
        PushD        $rational-temp-numerator-1 
        Exchange                               
        StoreI                                 
        Label        -print-rational-247-      
        PushD        $rational-temp-numerator-1 
        LoadI                                  
        PushD        $rational-temp-denominator-1 
        LoadI                                  
        Duplicate                              
        JumpFalse    $$divide-by-zero          
        Label        basicBlock-51             
        Divide                                 
        PushD        $print-rational-temp-1    
        Exchange                               
        StoreI                                 
        PushD        $rational-temp-numerator-1 
        LoadI                                  
        PushD        $rational-temp-denominator-1 
        LoadI                                  
        Remainder                              
        PushD        $print-rational-temp-2    
        Exchange                               
        StoreI                                 
        PushD        $rational-temp-denominator-1 
        LoadI                                  
        PushD        $print-rational-temp-3    
        Exchange                               
        StoreI                                 
        PushD        $print-rational-temp-2    
        LoadI                                  
        JumpFalse    -print-rational-247-skip-fraction 
        Label        basicBlock-52             
        PushD        $print-rational-temp-1    
        LoadI                                  
        JumpFalse    -print-rational-247-skip-whole 
        Label        basicBlock-53             
        PushD        $print-rational-temp-3    
        LoadI                                  
        PushD        $print-rational-temp-2    
        LoadI                                  
        Duplicate                              
        JumpPos      -print-rational-247-skip-negate 
        Label        basicBlock-54             
        Negate                                 
        Label        basicBlock-55             
        Label        -print-rational-247-skip-negate 
        PushD        $print-rational-temp-1    
        LoadI                                  
        PushD        $print-format-rational    
        Jump         -print-rational-247-join  
        Label        basicBlock-56             
        Label        -print-rational-247-skip-fraction 
        PushD        $print-rational-temp-1    
        LoadI                                  
        PushD        $print-format-integer     
        Jump         -print-rational-247-join  
        Label        basicBlock-57             
        Label        -print-rational-247-skip-whole 
        PushD        $print-rational-temp-3    
        LoadI                                  
        PushD        $print-rational-temp-2    
        LoadI                                  
        Duplicate                              
        JumpPos      -print-rational-247-skip-fraction-negate 
        Label        basicBlock-58             
        Negate                                 
        PushD        $print-format-rational-neg-fraction 
        Jump         -print-rational-247-join  
        Label        basicBlock-59             
        Label        -print-rational-247-skip-fraction-negate 
        PushD        $print-format-rational-fraction 
        Jump         -print-rational-247-join  
        Label        -print-rational-247-join  
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        PushD        $global-memory-block      
        PushI        16                        
        Add                                    %% test
        PushD        $global-memory-block      
        PushI        0                         
        Add                                    %% myRat1
        Duplicate                              
        PushI        4                         
        Add                                    
        LoadI                                  
        Exchange                               
        LoadI                                  
        Exchange                               
        PushD        $global-memory-block      
        PushI        8                         
        Add                                    %% myRat2
        Duplicate                              
        PushI        4                         
        Add                                    
        LoadI                                  
        Exchange                               
        LoadI                                  
        Exchange                               
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
        Add                                    
        PushD        $rational-temp-denominator-1 
        LoadI                                  
        PushD        $rational-temp-denominator-2 
        LoadI                                  
        Multiply                               
        PushD        $rational-temp-denominator-1 
        Exchange                               
        StoreI                                 
        PushD        $rational-temp-numerator-1 
        Exchange                               
        StoreI                                 
        Call         $sub-rational-find-gcd    
        Label        basicBlock-60             
        PushD        $rational-temp-numerator-1 
        LoadI                                  
        PushD        $rational-temp-denominator-1 
        LoadI                                  
        PushD        $rational-temp-denominator-1 
        Exchange                               
        StoreI                                 
        PushD        $rational-temp-numerator-1 
        Exchange                               
        StoreI                                 
        Call         $sub-rational-find-gcd    
        Label        basicBlock-61             
        Duplicate                              
        PushI        4                         
        Add                                    
        PushD        $rational-temp-denominator-1 
        LoadI                                  
        StoreI                                 
        PushD        $rational-temp-numerator-1 
        LoadI                                  
        StoreI                                 
        PushD        $global-memory-block      
        PushI        16                        
        Add                                    %% test
        Duplicate                              
        PushI        4                         
        Add                                    
        LoadI                                  
        Exchange                               
        LoadI                                  
        Exchange                               
        PushD        $rational-temp-denominator-1 
        Exchange                               
        StoreI                                 
        PushD        $rational-temp-numerator-1 
        Exchange                               
        StoreI                                 
        Label        -print-rational-248-      
        PushD        $rational-temp-numerator-1 
        LoadI                                  
        PushD        $rational-temp-denominator-1 
        LoadI                                  
        Duplicate                              
        JumpFalse    $$divide-by-zero          
        Label        basicBlock-62             
        Divide                                 
        PushD        $print-rational-temp-1    
        Exchange                               
        StoreI                                 
        PushD        $rational-temp-numerator-1 
        LoadI                                  
        PushD        $rational-temp-denominator-1 
        LoadI                                  
        Remainder                              
        PushD        $print-rational-temp-2    
        Exchange                               
        StoreI                                 
        PushD        $rational-temp-denominator-1 
        LoadI                                  
        PushD        $print-rational-temp-3    
        Exchange                               
        StoreI                                 
        PushD        $print-rational-temp-2    
        LoadI                                  
        JumpFalse    -print-rational-248-skip-fraction 
        Label        basicBlock-63             
        PushD        $print-rational-temp-1    
        LoadI                                  
        JumpFalse    -print-rational-248-skip-whole 
        Label        basicBlock-64             
        PushD        $print-rational-temp-3    
        LoadI                                  
        PushD        $print-rational-temp-2    
        LoadI                                  
        Duplicate                              
        JumpPos      -print-rational-248-skip-negate 
        Label        basicBlock-65             
        Negate                                 
        Label        basicBlock-66             
        Label        -print-rational-248-skip-negate 
        PushD        $print-rational-temp-1    
        LoadI                                  
        PushD        $print-format-rational    
        Jump         -print-rational-248-join  
        Label        basicBlock-67             
        Label        -print-rational-248-skip-fraction 
        PushD        $print-rational-temp-1    
        LoadI                                  
        PushD        $print-format-integer     
        Jump         -print-rational-248-join  
        Label        basicBlock-68             
        Label        -print-rational-248-skip-whole 
        PushD        $print-rational-temp-3    
        LoadI                                  
        PushD        $print-rational-temp-2    
        LoadI                                  
        Duplicate                              
        JumpPos      -print-rational-248-skip-fraction-negate 
        Label        basicBlock-69             
        Negate                                 
        PushD        $print-format-rational-neg-fraction 
        Jump         -print-rational-248-join  
        Label        basicBlock-70             
        Label        -print-rational-248-skip-fraction-negate 
        PushD        $print-format-rational-fraction 
        Jump         -print-rational-248-join  
        Label        -print-rational-248-join  
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        PushD        $global-memory-block      
        PushI        16                        
        Add                                    %% test
        PushD        $global-memory-block      
        PushI        0                         
        Add                                    %% myRat1
        Duplicate                              
        PushI        4                         
        Add                                    
        LoadI                                  
        Exchange                               
        LoadI                                  
        Exchange                               
        PushD        $global-memory-block      
        PushI        8                         
        Add                                    %% myRat2
        Duplicate                              
        PushI        4                         
        Add                                    
        LoadI                                  
        Exchange                               
        LoadI                                  
        Exchange                               
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
        PushD        $rational-temp-denominator-1 
        LoadI                                  
        PushD        $rational-temp-denominator-2 
        LoadI                                  
        Multiply                               
        PushD        $rational-temp-denominator-1 
        Exchange                               
        StoreI                                 
        PushD        $rational-temp-numerator-1 
        Exchange                               
        StoreI                                 
        Call         $sub-rational-find-gcd    
        Label        basicBlock-71             
        PushD        $rational-temp-numerator-1 
        LoadI                                  
        PushD        $rational-temp-denominator-1 
        LoadI                                  
        PushD        $rational-temp-denominator-1 
        Exchange                               
        StoreI                                 
        PushD        $rational-temp-numerator-1 
        Exchange                               
        StoreI                                 
        Call         $sub-rational-find-gcd    
        Label        basicBlock-72             
        Duplicate                              
        PushI        4                         
        Add                                    
        PushD        $rational-temp-denominator-1 
        LoadI                                  
        StoreI                                 
        PushD        $rational-temp-numerator-1 
        LoadI                                  
        StoreI                                 
        PushD        $global-memory-block      
        PushI        16                        
        Add                                    %% test
        Duplicate                              
        PushI        4                         
        Add                                    
        LoadI                                  
        Exchange                               
        LoadI                                  
        Exchange                               
        PushD        $rational-temp-denominator-1 
        Exchange                               
        StoreI                                 
        PushD        $rational-temp-numerator-1 
        Exchange                               
        StoreI                                 
        Label        -print-rational-249-      
        PushD        $rational-temp-numerator-1 
        LoadI                                  
        PushD        $rational-temp-denominator-1 
        LoadI                                  
        Duplicate                              
        JumpFalse    $$divide-by-zero          
        Label        basicBlock-73             
        Divide                                 
        PushD        $print-rational-temp-1    
        Exchange                               
        StoreI                                 
        PushD        $rational-temp-numerator-1 
        LoadI                                  
        PushD        $rational-temp-denominator-1 
        LoadI                                  
        Remainder                              
        PushD        $print-rational-temp-2    
        Exchange                               
        StoreI                                 
        PushD        $rational-temp-denominator-1 
        LoadI                                  
        PushD        $print-rational-temp-3    
        Exchange                               
        StoreI                                 
        PushD        $print-rational-temp-2    
        LoadI                                  
        JumpFalse    -print-rational-249-skip-fraction 
        Label        basicBlock-74             
        PushD        $print-rational-temp-1    
        LoadI                                  
        JumpFalse    -print-rational-249-skip-whole 
        Label        basicBlock-75             
        PushD        $print-rational-temp-3    
        LoadI                                  
        PushD        $print-rational-temp-2    
        LoadI                                  
        Duplicate                              
        JumpPos      -print-rational-249-skip-negate 
        Label        basicBlock-76             
        Negate                                 
        Label        basicBlock-77             
        Label        -print-rational-249-skip-negate 
        PushD        $print-rational-temp-1    
        LoadI                                  
        PushD        $print-format-rational    
        Jump         -print-rational-249-join  
        Label        basicBlock-78             
        Label        -print-rational-249-skip-fraction 
        PushD        $print-rational-temp-1    
        LoadI                                  
        PushD        $print-format-integer     
        Jump         -print-rational-249-join  
        Label        basicBlock-79             
        Label        -print-rational-249-skip-whole 
        PushD        $print-rational-temp-3    
        LoadI                                  
        PushD        $print-rational-temp-2    
        LoadI                                  
        Duplicate                              
        JumpPos      -print-rational-249-skip-fraction-negate 
        Label        basicBlock-80             
        Negate                                 
        PushD        $print-format-rational-neg-fraction 
        Jump         -print-rational-249-join  
        Label        basicBlock-81             
        Label        -print-rational-249-skip-fraction-negate 
        PushD        $print-format-rational-fraction 
        Jump         -print-rational-249-join  
        Label        -print-rational-249-join  
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        PushD        $global-memory-block      
        PushI        16                        
        Add                                    %% test
        PushD        $global-memory-block      
        PushI        0                         
        Add                                    %% myRat1
        Duplicate                              
        PushI        4                         
        Add                                    
        LoadI                                  
        Exchange                               
        LoadI                                  
        Exchange                               
        PushD        $global-memory-block      
        PushI        8                         
        Add                                    %% myRat2
        Duplicate                              
        PushI        4                         
        Add                                    
        LoadI                                  
        Exchange                               
        LoadI                                  
        Exchange                               
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
        PushD        $rational-temp-numerator-2 
        LoadI                                  
        Multiply                               
        PushD        $rational-temp-denominator-1 
        LoadI                                  
        PushD        $rational-temp-denominator-2 
        LoadI                                  
        Multiply                               
        PushD        $rational-temp-denominator-1 
        Exchange                               
        StoreI                                 
        PushD        $rational-temp-numerator-1 
        Exchange                               
        StoreI                                 
        Call         $sub-rational-find-gcd    
        Label        basicBlock-82             
        PushD        $rational-temp-numerator-1 
        LoadI                                  
        PushD        $rational-temp-denominator-1 
        LoadI                                  
        PushD        $rational-temp-denominator-1 
        Exchange                               
        StoreI                                 
        PushD        $rational-temp-numerator-1 
        Exchange                               
        StoreI                                 
        Call         $sub-rational-find-gcd    
        Label        basicBlock-83             
        Duplicate                              
        PushI        4                         
        Add                                    
        PushD        $rational-temp-denominator-1 
        LoadI                                  
        StoreI                                 
        PushD        $rational-temp-numerator-1 
        LoadI                                  
        StoreI                                 
        PushD        $global-memory-block      
        PushI        16                        
        Add                                    %% test
        Duplicate                              
        PushI        4                         
        Add                                    
        LoadI                                  
        Exchange                               
        LoadI                                  
        Exchange                               
        PushD        $rational-temp-denominator-1 
        Exchange                               
        StoreI                                 
        PushD        $rational-temp-numerator-1 
        Exchange                               
        StoreI                                 
        Label        -print-rational-250-      
        PushD        $rational-temp-numerator-1 
        LoadI                                  
        PushD        $rational-temp-denominator-1 
        LoadI                                  
        Duplicate                              
        JumpFalse    $$divide-by-zero          
        Label        basicBlock-84             
        Divide                                 
        PushD        $print-rational-temp-1    
        Exchange                               
        StoreI                                 
        PushD        $rational-temp-numerator-1 
        LoadI                                  
        PushD        $rational-temp-denominator-1 
        LoadI                                  
        Remainder                              
        PushD        $print-rational-temp-2    
        Exchange                               
        StoreI                                 
        PushD        $rational-temp-denominator-1 
        LoadI                                  
        PushD        $print-rational-temp-3    
        Exchange                               
        StoreI                                 
        PushD        $print-rational-temp-2    
        LoadI                                  
        JumpFalse    -print-rational-250-skip-fraction 
        Label        basicBlock-85             
        PushD        $print-rational-temp-1    
        LoadI                                  
        JumpFalse    -print-rational-250-skip-whole 
        Label        basicBlock-86             
        PushD        $print-rational-temp-3    
        LoadI                                  
        PushD        $print-rational-temp-2    
        LoadI                                  
        Duplicate                              
        JumpPos      -print-rational-250-skip-negate 
        Label        basicBlock-87             
        Negate                                 
        Label        basicBlock-88             
        Label        -print-rational-250-skip-negate 
        PushD        $print-rational-temp-1    
        LoadI                                  
        PushD        $print-format-rational    
        Jump         -print-rational-250-join  
        Label        basicBlock-89             
        Label        -print-rational-250-skip-fraction 
        PushD        $print-rational-temp-1    
        LoadI                                  
        PushD        $print-format-integer     
        Jump         -print-rational-250-join  
        Label        basicBlock-90             
        Label        -print-rational-250-skip-whole 
        PushD        $print-rational-temp-3    
        LoadI                                  
        PushD        $print-rational-temp-2    
        LoadI                                  
        Duplicate                              
        JumpPos      -print-rational-250-skip-fraction-negate 
        Label        basicBlock-91             
        Negate                                 
        PushD        $print-format-rational-neg-fraction 
        Jump         -print-rational-250-join  
        Label        basicBlock-92             
        Label        -print-rational-250-skip-fraction-negate 
        PushD        $print-format-rational-fraction 
        Jump         -print-rational-250-join  
        Label        -print-rational-250-join  
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        PushD        $global-memory-block      
        PushI        16                        
        Add                                    %% test
        PushD        $global-memory-block      
        PushI        0                         
        Add                                    %% myRat1
        Duplicate                              
        PushI        4                         
        Add                                    
        LoadI                                  
        Exchange                               
        LoadI                                  
        Exchange                               
        PushD        $global-memory-block      
        PushI        8                         
        Add                                    %% myRat2
        Duplicate                              
        PushI        4                         
        Add                                    
        LoadI                                  
        Exchange                               
        LoadI                                  
        Exchange                               
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
        PushD        $rational-temp-denominator-1 
        Exchange                               
        StoreI                                 
        PushD        $rational-temp-numerator-1 
        Exchange                               
        StoreI                                 
        Call         $sub-rational-find-gcd    
        Label        basicBlock-93             
        PushD        $rational-temp-numerator-1 
        LoadI                                  
        PushD        $rational-temp-denominator-1 
        LoadI                                  
        PushD        $rational-temp-denominator-1 
        Exchange                               
        StoreI                                 
        PushD        $rational-temp-numerator-1 
        Exchange                               
        StoreI                                 
        Call         $sub-rational-find-gcd    
        Label        basicBlock-94             
        Duplicate                              
        PushI        4                         
        Add                                    
        PushD        $rational-temp-denominator-1 
        LoadI                                  
        StoreI                                 
        PushD        $rational-temp-numerator-1 
        LoadI                                  
        StoreI                                 
        PushD        $global-memory-block      
        PushI        16                        
        Add                                    %% test
        Duplicate                              
        PushI        4                         
        Add                                    
        LoadI                                  
        Exchange                               
        LoadI                                  
        Exchange                               
        PushD        $rational-temp-denominator-1 
        Exchange                               
        StoreI                                 
        PushD        $rational-temp-numerator-1 
        Exchange                               
        StoreI                                 
        Label        -print-rational-251-      
        PushD        $rational-temp-numerator-1 
        LoadI                                  
        PushD        $rational-temp-denominator-1 
        LoadI                                  
        Duplicate                              
        JumpFalse    $$divide-by-zero          
        Label        basicBlock-95             
        Divide                                 
        PushD        $print-rational-temp-1    
        Exchange                               
        StoreI                                 
        PushD        $rational-temp-numerator-1 
        LoadI                                  
        PushD        $rational-temp-denominator-1 
        LoadI                                  
        Remainder                              
        PushD        $print-rational-temp-2    
        Exchange                               
        StoreI                                 
        PushD        $rational-temp-denominator-1 
        LoadI                                  
        PushD        $print-rational-temp-3    
        Exchange                               
        StoreI                                 
        PushD        $print-rational-temp-2    
        LoadI                                  
        JumpFalse    -print-rational-251-skip-fraction 
        Label        basicBlock-96             
        PushD        $print-rational-temp-1    
        LoadI                                  
        JumpFalse    -print-rational-251-skip-whole 
        Label        basicBlock-97             
        PushD        $print-rational-temp-3    
        LoadI                                  
        PushD        $print-rational-temp-2    
        LoadI                                  
        Duplicate                              
        JumpPos      -print-rational-251-skip-negate 
        Label        basicBlock-98             
        Negate                                 
        Label        basicBlock-99             
        Label        -print-rational-251-skip-negate 
        PushD        $print-rational-temp-1    
        LoadI                                  
        PushD        $print-format-rational    
        Jump         -print-rational-251-join  
        Label        basicBlock-100            
        Label        -print-rational-251-skip-fraction 
        PushD        $print-rational-temp-1    
        LoadI                                  
        PushD        $print-format-integer     
        Jump         -print-rational-251-join  
        Label        basicBlock-101            
        Label        -print-rational-251-skip-whole 
        PushD        $print-rational-temp-3    
        LoadI                                  
        PushD        $print-rational-temp-2    
        LoadI                                  
        Duplicate                              
        JumpPos      -print-rational-251-skip-fraction-negate 
        Label        basicBlock-102            
        Negate                                 
        PushD        $print-format-rational-neg-fraction 
        Jump         -print-rational-251-join  
        Label        basicBlock-103            
        Label        -print-rational-251-skip-fraction-negate 
        PushD        $print-format-rational-fraction 
        Jump         -print-rational-251-join  
        Label        -print-rational-251-join  
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        PushD        $global-memory-block      
        PushI        24                        
        Add                                    %% other
        PushI        -22                       
        PushI        8                         
        PushI        5                         
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
        PushD        $rational-temp-denominator-1 
        Exchange                               
        StoreI                                 
        PushD        $rational-temp-numerator-1 
        Exchange                               
        StoreI                                 
        Call         $sub-rational-find-gcd    
        Label        basicBlock-104            
        PushD        $rational-temp-numerator-1 
        LoadI                                  
        PushD        $rational-temp-denominator-1 
        LoadI                                  
        PushD        $rational-temp-denominator-1 
        Exchange                               
        StoreI                                 
        PushD        $rational-temp-numerator-1 
        Exchange                               
        StoreI                                 
        Call         $sub-rational-find-gcd    
        Label        basicBlock-105            
        Duplicate                              
        PushI        4                         
        Add                                    
        PushD        $rational-temp-denominator-1 
        LoadI                                  
        StoreI                                 
        PushD        $rational-temp-numerator-1 
        LoadI                                  
        StoreI                                 
        PushD        $global-memory-block      
        PushI        24                        
        Add                                    %% other
        Duplicate                              
        PushI        4                         
        Add                                    
        LoadI                                  
        Exchange                               
        LoadI                                  
        Exchange                               
        PushD        $rational-temp-denominator-1 
        Exchange                               
        StoreI                                 
        PushD        $rational-temp-numerator-1 
        Exchange                               
        StoreI                                 
        Label        -print-rational-252-      
        PushD        $rational-temp-numerator-1 
        LoadI                                  
        PushD        $rational-temp-denominator-1 
        LoadI                                  
        Duplicate                              
        JumpFalse    $$divide-by-zero          
        Label        basicBlock-106            
        Divide                                 
        PushD        $print-rational-temp-1    
        Exchange                               
        StoreI                                 
        PushD        $rational-temp-numerator-1 
        LoadI                                  
        PushD        $rational-temp-denominator-1 
        LoadI                                  
        Remainder                              
        PushD        $print-rational-temp-2    
        Exchange                               
        StoreI                                 
        PushD        $rational-temp-denominator-1 
        LoadI                                  
        PushD        $print-rational-temp-3    
        Exchange                               
        StoreI                                 
        PushD        $print-rational-temp-2    
        LoadI                                  
        JumpFalse    -print-rational-252-skip-fraction 
        Label        basicBlock-107            
        PushD        $print-rational-temp-1    
        LoadI                                  
        JumpFalse    -print-rational-252-skip-whole 
        Label        basicBlock-108            
        PushD        $print-rational-temp-3    
        LoadI                                  
        PushD        $print-rational-temp-2    
        LoadI                                  
        Duplicate                              
        JumpPos      -print-rational-252-skip-negate 
        Label        basicBlock-109            
        Negate                                 
        Label        basicBlock-110            
        Label        -print-rational-252-skip-negate 
        PushD        $print-rational-temp-1    
        LoadI                                  
        PushD        $print-format-rational    
        Jump         -print-rational-252-join  
        Label        basicBlock-111            
        Label        -print-rational-252-skip-fraction 
        PushD        $print-rational-temp-1    
        LoadI                                  
        PushD        $print-format-integer     
        Jump         -print-rational-252-join  
        Label        basicBlock-112            
        Label        -print-rational-252-skip-whole 
        PushD        $print-rational-temp-3    
        LoadI                                  
        PushD        $print-rational-temp-2    
        LoadI                                  
        Duplicate                              
        JumpPos      -print-rational-252-skip-fraction-negate 
        Label        basicBlock-113            
        Negate                                 
        PushD        $print-format-rational-neg-fraction 
        Jump         -print-rational-252-join  
        Label        basicBlock-114            
        Label        -print-rational-252-skip-fraction-negate 
        PushD        $print-format-rational-fraction 
        Jump         -print-rational-252-join  
        Label        -print-rational-252-join  
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        Halt                                   %% End of Execution
