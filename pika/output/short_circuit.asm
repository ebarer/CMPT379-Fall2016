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
        DataZ        4                         
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
        Add                                    %% test1
        PushI        3                         
        PushI        5                         
        Subtract                               
        JumpNeg      -compare-266-true         
        Jump         -compare-266-false        
        Label        basicBlock-2              
        Label        -compare-266-true         
        PushI        1                         
        Jump         -compare-266-join         
        Label        basicBlock-3              
        Label        -compare-266-false        
        PushI        0                         
        Label        basicBlock-4              
        Label        -compare-266-join         
        Duplicate                              
        JumpTrue     -boolean-267-join         
        Label        basicBlock-5              
        PushI        2                         
        PushI        9                         
        Subtract                               
        JumpPos      -compare-267-true         
        Jump         -compare-267-false        
        Label        basicBlock-6              
        Label        -compare-267-true         
        PushI        1                         
        Jump         -compare-267-join         
        Label        basicBlock-7              
        Label        -compare-267-false        
        PushI        0                         
        Label        basicBlock-8              
        Label        -compare-267-join         
        Or                                     
        Label        basicBlock-9              
        Label        -boolean-267-join         
        StoreC                                 
        PushD        $global-memory-block      
        PushI        1                         
        Add                                    %% test2
        PushI        3                         
        PushI        5                         
        Subtract                               
        JumpPos      -compare-269-true         
        Jump         -compare-269-false        
        Label        basicBlock-10             
        Label        -compare-269-true         
        PushI        1                         
        Jump         -compare-269-join         
        Label        basicBlock-11             
        Label        -compare-269-false        
        PushI        0                         
        Label        basicBlock-12             
        Label        -compare-269-join         
        Duplicate                              
        JumpFalse    -boolean-270-join         
        Label        basicBlock-13             
        PushI        2                         
        PushI        9                         
        Subtract                               
        JumpNeg      -compare-270-true         
        Jump         -compare-270-false        
        Label        basicBlock-14             
        Label        -compare-270-true         
        PushI        1                         
        Jump         -compare-270-join         
        Label        basicBlock-15             
        Label        -compare-270-false        
        PushI        0                         
        Label        basicBlock-16             
        Label        -compare-270-join         
        And                                    
        Label        basicBlock-17             
        Label        -boolean-270-join         
        StoreC                                 
        PushD        $global-memory-block      
        PushI        2                         
        Add                                    %% test3
        PushI        3                         
        PushI        5                         
        Subtract                               
        JumpNeg      -compare-272-true         
        Jump         -compare-272-false        
        Label        basicBlock-18             
        Label        -compare-272-true         
        PushI        1                         
        Jump         -compare-272-join         
        Label        basicBlock-19             
        Label        -compare-272-false        
        PushI        0                         
        Label        basicBlock-20             
        Label        -compare-272-join         
        Duplicate                              
        JumpFalse    -boolean-273-join         
        Label        basicBlock-21             
        PushI        2                         
        PushI        9                         
        Subtract                               
        JumpPos      -compare-273-true         
        Jump         -compare-273-false        
        Label        basicBlock-22             
        Label        -compare-273-true         
        PushI        1                         
        Jump         -compare-273-join         
        Label        basicBlock-23             
        Label        -compare-273-false        
        PushI        0                         
        Label        basicBlock-24             
        Label        -compare-273-join         
        And                                    
        Label        basicBlock-25             
        Label        -boolean-273-join         
        StoreC                                 
        PushD        $global-memory-block      
        PushI        3                         
        Add                                    %% test4
        PushI        3                         
        PushI        5                         
        Subtract                               
        JumpPos      -compare-275-true         
        Jump         -compare-275-false        
        Label        basicBlock-26             
        Label        -compare-275-true         
        PushI        1                         
        Jump         -compare-275-join         
        Label        basicBlock-27             
        Label        -compare-275-false        
        PushI        0                         
        Label        basicBlock-28             
        Label        -compare-275-join         
        Duplicate                              
        JumpTrue     -boolean-276-join         
        Label        basicBlock-29             
        PushI        2                         
        PushI        9                         
        Subtract                               
        JumpNeg      -compare-276-true         
        Jump         -compare-276-false        
        Label        basicBlock-30             
        Label        -compare-276-true         
        PushI        1                         
        Jump         -compare-276-join         
        Label        basicBlock-31             
        Label        -compare-276-false        
        PushI        0                         
        Label        basicBlock-32             
        Label        -compare-276-join         
        Or                                     
        Label        basicBlock-33             
        Label        -boolean-276-join         
        StoreC                                 
        PushD        $global-memory-block      
        PushI        0                         
        Add                                    %% test1
        LoadC                                  
        JumpTrue     -print-boolean-277-true   
        Label        basicBlock-34             
        PushD        $boolean-false-string     
        Jump         -print-boolean-277-join   
        Label        basicBlock-35             
        Label        -print-boolean-277-true   
        PushD        $boolean-true-string      
        Label        basicBlock-36             
        Label        -print-boolean-277-join   
        PushD        $print-format-boolean     
        Printf                                 
        PushD        $print-format-space       
        Printf                                 
        PushD        $global-memory-block      
        PushI        1                         
        Add                                    %% test2
        LoadC                                  
        JumpTrue     -print-boolean-278-true   
        Label        basicBlock-37             
        PushD        $boolean-false-string     
        Jump         -print-boolean-278-join   
        Label        basicBlock-38             
        Label        -print-boolean-278-true   
        PushD        $boolean-true-string      
        Label        basicBlock-39             
        Label        -print-boolean-278-join   
        PushD        $print-format-boolean     
        Printf                                 
        PushD        $print-format-space       
        Printf                                 
        PushD        $global-memory-block      
        PushI        2                         
        Add                                    %% test3
        LoadC                                  
        JumpTrue     -print-boolean-279-true   
        Label        basicBlock-40             
        PushD        $boolean-false-string     
        Jump         -print-boolean-279-join   
        Label        basicBlock-41             
        Label        -print-boolean-279-true   
        PushD        $boolean-true-string      
        Label        basicBlock-42             
        Label        -print-boolean-279-join   
        PushD        $print-format-boolean     
        Printf                                 
        PushD        $print-format-space       
        Printf                                 
        PushD        $global-memory-block      
        PushI        3                         
        Add                                    %% test4
        LoadC                                  
        JumpTrue     -print-boolean-280-true   
        Label        basicBlock-43             
        PushD        $boolean-false-string     
        Jump         -print-boolean-280-join   
        Label        basicBlock-44             
        Label        -print-boolean-280-true   
        PushD        $boolean-true-string      
        Label        basicBlock-45             
        Label        -print-boolean-280-join   
        PushD        $print-format-boolean     
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        PushD        $global-memory-block      
        PushI        0                         
        Add                                    %% test1
        PushI        3                         
        PushI        2                         
        Multiply                               
        PushI        5                         
        Subtract                               
        JumpNeg      -compare-282-true         
        Jump         -compare-282-false        
        Label        basicBlock-46             
        Label        -compare-282-true         
        PushI        1                         
        Jump         -compare-282-join         
        Label        basicBlock-47             
        Label        -compare-282-false        
        PushI        0                         
        Label        basicBlock-48             
        Label        -compare-282-join         
        Duplicate                              
        JumpTrue     -boolean-283-join         
        Label        basicBlock-49             
        PushI        2                         
        PushI        9                         
        Subtract                               
        JumpPos      -compare-283-true         
        Jump         -compare-283-false        
        Label        basicBlock-50             
        Label        -compare-283-true         
        PushI        1                         
        Jump         -compare-283-join         
        Label        basicBlock-51             
        Label        -compare-283-false        
        PushI        0                         
        Label        basicBlock-52             
        Label        -compare-283-join         
        Or                                     
        Label        basicBlock-53             
        Label        -boolean-283-join         
        StoreC                                 
        PushD        $global-memory-block      
        PushI        0                         
        Add                                    %% test1
        LoadC                                  
        JumpTrue     -print-boolean-284-true   
        Label        basicBlock-54             
        PushD        $boolean-false-string     
        Jump         -print-boolean-284-join   
        Label        basicBlock-55             
        Label        -print-boolean-284-true   
        PushD        $boolean-true-string      
        Label        basicBlock-56             
        Label        -print-boolean-284-join   
        PushD        $print-format-boolean     
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        Halt                                   %% End of Execution
