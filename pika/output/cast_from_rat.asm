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
        Label        -print-rational-200-      
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
        JumpFalse    -print-rational-200-skip-fraction 
        Label        basicBlock-12             
        PushD        $print-rational-temp-1    
        LoadI                                  
        JumpFalse    -print-rational-200-skip-whole 
        Label        basicBlock-13             
        PushD        $print-rational-temp-3    
        LoadI                                  
        PushD        $print-rational-temp-2    
        LoadI                                  
        Duplicate                              
        JumpPos      -print-rational-200-skip-negate 
        Label        basicBlock-14             
        Negate                                 
        Label        basicBlock-15             
        Label        -print-rational-200-skip-negate 
        PushD        $print-rational-temp-1    
        LoadI                                  
        PushD        $print-format-rational    
        Jump         -print-rational-200-join  
        Label        basicBlock-16             
        Label        -print-rational-200-skip-fraction 
        PushD        $print-rational-temp-1    
        LoadI                                  
        PushD        $print-format-integer     
        Jump         -print-rational-200-join  
        Label        basicBlock-17             
        Label        -print-rational-200-skip-whole 
        PushD        $print-rational-temp-3    
        LoadI                                  
        PushD        $print-rational-temp-2    
        LoadI                                  
        Duplicate                              
        JumpPos      -print-rational-200-skip-fraction-negate 
        Label        basicBlock-18             
        Negate                                 
        PushD        $print-format-rational-neg-fraction 
        Jump         -print-rational-200-join  
        Label        basicBlock-19             
        Label        -print-rational-200-skip-fraction-negate 
        PushD        $print-format-rational-fraction 
        Jump         -print-rational-200-join  
        Label        -print-rational-200-join  
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        PushD        $global-memory-block      
        PushI        8                         
        Add                                    %% convRat
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
        PushI        8                         
        Add                                    %% convRat
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
        Label        -print-rational-201-      
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
        JumpFalse    -print-rational-201-skip-fraction 
        Label        basicBlock-22             
        PushD        $print-rational-temp-1    
        LoadI                                  
        JumpFalse    -print-rational-201-skip-whole 
        Label        basicBlock-23             
        PushD        $print-rational-temp-3    
        LoadI                                  
        PushD        $print-rational-temp-2    
        LoadI                                  
        Duplicate                              
        JumpPos      -print-rational-201-skip-negate 
        Label        basicBlock-24             
        Negate                                 
        Label        basicBlock-25             
        Label        -print-rational-201-skip-negate 
        PushD        $print-rational-temp-1    
        LoadI                                  
        PushD        $print-format-rational    
        Jump         -print-rational-201-join  
        Label        basicBlock-26             
        Label        -print-rational-201-skip-fraction 
        PushD        $print-rational-temp-1    
        LoadI                                  
        PushD        $print-format-integer     
        Jump         -print-rational-201-join  
        Label        basicBlock-27             
        Label        -print-rational-201-skip-whole 
        PushD        $print-rational-temp-3    
        LoadI                                  
        PushD        $print-rational-temp-2    
        LoadI                                  
        Duplicate                              
        JumpPos      -print-rational-201-skip-fraction-negate 
        Label        basicBlock-28             
        Negate                                 
        PushD        $print-format-rational-neg-fraction 
        Jump         -print-rational-201-join  
        Label        basicBlock-29             
        Label        -print-rational-201-skip-fraction-negate 
        PushD        $print-format-rational-fraction 
        Jump         -print-rational-201-join  
        Label        -print-rational-201-join  
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        PushD        $global-memory-block      
        PushI        16                        
        Add                                    %% convInt
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
        Divide                                 
        StoreI                                 
        PushD        $global-memory-block      
        PushI        16                        
        Add                                    %% convInt
        LoadI                                  
        PushD        $print-format-integer     
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        PushD        $global-memory-block      
        PushI        20                        
        Add                                    %% convFloat
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
        ConvertF                               
        Exchange                               
        ConvertF                               
        Exchange                               
        FDivide                                
        StoreF                                 
        PushD        $global-memory-block      
        PushI        20                        
        Add                                    %% convFloat
        LoadF                                  
        PushD        $print-format-floating    
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        PushD        $global-memory-block      
        PushI        0                         
        Add                                    %% myRat1
        PushI        20                        
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
        Label        -print-rational-202-      
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
        JumpFalse    -print-rational-202-skip-fraction 
        Label        basicBlock-32             
        PushD        $print-rational-temp-1    
        LoadI                                  
        JumpFalse    -print-rational-202-skip-whole 
        Label        basicBlock-33             
        PushD        $print-rational-temp-3    
        LoadI                                  
        PushD        $print-rational-temp-2    
        LoadI                                  
        Duplicate                              
        JumpPos      -print-rational-202-skip-negate 
        Label        basicBlock-34             
        Negate                                 
        Label        basicBlock-35             
        Label        -print-rational-202-skip-negate 
        PushD        $print-rational-temp-1    
        LoadI                                  
        PushD        $print-format-rational    
        Jump         -print-rational-202-join  
        Label        basicBlock-36             
        Label        -print-rational-202-skip-fraction 
        PushD        $print-rational-temp-1    
        LoadI                                  
        PushD        $print-format-integer     
        Jump         -print-rational-202-join  
        Label        basicBlock-37             
        Label        -print-rational-202-skip-whole 
        PushD        $print-rational-temp-3    
        LoadI                                  
        PushD        $print-rational-temp-2    
        LoadI                                  
        Duplicate                              
        JumpPos      -print-rational-202-skip-fraction-negate 
        Label        basicBlock-38             
        Negate                                 
        PushD        $print-format-rational-neg-fraction 
        Jump         -print-rational-202-join  
        Label        basicBlock-39             
        Label        -print-rational-202-skip-fraction-negate 
        PushD        $print-format-rational-fraction 
        Jump         -print-rational-202-join  
        Label        -print-rational-202-join  
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        PushD        $global-memory-block      
        PushI        8                         
        Add                                    %% convRat
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
        PushI        8                         
        Add                                    %% convRat
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
        Label        -print-rational-203-      
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
        JumpFalse    -print-rational-203-skip-fraction 
        Label        basicBlock-42             
        PushD        $print-rational-temp-1    
        LoadI                                  
        JumpFalse    -print-rational-203-skip-whole 
        Label        basicBlock-43             
        PushD        $print-rational-temp-3    
        LoadI                                  
        PushD        $print-rational-temp-2    
        LoadI                                  
        Duplicate                              
        JumpPos      -print-rational-203-skip-negate 
        Label        basicBlock-44             
        Negate                                 
        Label        basicBlock-45             
        Label        -print-rational-203-skip-negate 
        PushD        $print-rational-temp-1    
        LoadI                                  
        PushD        $print-format-rational    
        Jump         -print-rational-203-join  
        Label        basicBlock-46             
        Label        -print-rational-203-skip-fraction 
        PushD        $print-rational-temp-1    
        LoadI                                  
        PushD        $print-format-integer     
        Jump         -print-rational-203-join  
        Label        basicBlock-47             
        Label        -print-rational-203-skip-whole 
        PushD        $print-rational-temp-3    
        LoadI                                  
        PushD        $print-rational-temp-2    
        LoadI                                  
        Duplicate                              
        JumpPos      -print-rational-203-skip-fraction-negate 
        Label        basicBlock-48             
        Negate                                 
        PushD        $print-format-rational-neg-fraction 
        Jump         -print-rational-203-join  
        Label        basicBlock-49             
        Label        -print-rational-203-skip-fraction-negate 
        PushD        $print-format-rational-fraction 
        Jump         -print-rational-203-join  
        Label        -print-rational-203-join  
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        PushD        $global-memory-block      
        PushI        16                        
        Add                                    %% convInt
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
        Divide                                 
        StoreI                                 
        PushD        $global-memory-block      
        PushI        16                        
        Add                                    %% convInt
        LoadI                                  
        PushD        $print-format-integer     
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        PushD        $global-memory-block      
        PushI        20                        
        Add                                    %% convFloat
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
        ConvertF                               
        Exchange                               
        ConvertF                               
        Exchange                               
        FDivide                                
        StoreF                                 
        PushD        $global-memory-block      
        PushI        20                        
        Add                                    %% convFloat
        LoadF                                  
        PushD        $print-format-floating    
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        Halt                                   %% End of Execution
