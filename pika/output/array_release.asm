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
        DataZ        16                        
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
        Add                                    %% myArr
        Label        -array-18-                
        PushI        4                         
        PushI        4                         
        PushI        4                         
        Multiply                               
        PushI        16                        
        Add                                    
        Call         -mem-manager-allocate     
        Label        basicBlock-2              
        PushD        $array-temp-1             
        Exchange                               
        StoreI                                 
        Label        -array-18-create-record   
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
        Label        basicBlock-3              
        Label        -array-18-start-store-children 
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
        Label        -array-18-end-store-children 
        StoreI                                 
        PushD        $global-memory-block      
        PushI        0                         
        Add                                    %% myArr
        LoadI                                  
        PushD        $release-temp-1           
        Exchange                               
        StoreI                                 
        Label        basicBlock-4              
        Label        -release-stmt-19-         
        PushD        $release-temp-1           
        LoadI                                  
        PushI        4                         
        Add                                    
        LoadI                                  
        PushI        1                         
        BTAnd                                  
        JumpTrue     -release-stmt-19-join     
        Label        basicBlock-5              
        PushD        $release-temp-1           
        LoadI                                  
        PushI        4                         
        Add                                    
        LoadI                                  
        PushI        2                         
        BTAnd                                  
        JumpTrue     -release-stmt-19-join     
        Label        basicBlock-6              
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
        Label        basicBlock-7              
        Jump         -release-stmt-19-join     
        Label        basicBlock-8              
        Label        -release-stmt-19-join     
        PushD        $global-memory-block      
        PushI        8                         
        Add                                    %% myArr2
        Label        -array-22-                
        PushI        2                         
        PushI        2                         
        PushI        8                         
        Multiply                               
        PushI        16                        
        Add                                    
        Call         -mem-manager-allocate     
        Label        basicBlock-9              
        PushD        $array-temp-1             
        Exchange                               
        StoreI                                 
        Label        -array-22-create-record   
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
        Label        basicBlock-10             
        Label        -array-22-start-store-children 
        Duplicate                              
        PushI        16                        
        Add                                    
        Label        -array-20-                
        PushI        2                         
        PushI        2                         
        PushI        4                         
        Multiply                               
        PushI        16                        
        Add                                    
        Call         -mem-manager-allocate     
        Label        basicBlock-11             
        PushD        $array-temp-1             
        Exchange                               
        StoreI                                 
        Label        -array-20-create-record   
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
        Label        basicBlock-12             
        Label        -array-20-start-store-children 
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
        Label        -array-20-end-store-children 
        StoreI                                 
        Duplicate                              
        PushI        24                        
        Add                                    
        Label        basicBlock-13             
        Label        -array-21-                
        PushI        2                         
        PushI        2                         
        PushI        4                         
        Multiply                               
        PushI        16                        
        Add                                    
        Call         -mem-manager-allocate     
        Label        basicBlock-14             
        PushD        $array-temp-1             
        Exchange                               
        StoreI                                 
        Label        -array-21-create-record   
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
        Label        basicBlock-15             
        Label        -array-21-start-store-children 
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
        Label        -array-21-end-store-children 
        StoreI                                 
        Label        basicBlock-16             
        Label        -array-22-end-store-children 
        StoreI                                 
        PushD        $global-memory-block      
        PushI        8                         
        Add                                    %% myArr2
        LoadI                                  
        PushD        $release-temp-1           
        Exchange                               
        StoreI                                 
        Label        -release-stmt-23-         
        PushD        $release-temp-1           
        LoadI                                  
        PushI        4                         
        Add                                    
        LoadI                                  
        PushI        1                         
        BTAnd                                  
        JumpTrue     -release-stmt-23-join     
        Label        basicBlock-17             
        PushD        $release-temp-1           
        LoadI                                  
        PushI        4                         
        Add                                    
        LoadI                                  
        PushI        2                         
        BTAnd                                  
        JumpTrue     -release-stmt-23-join     
        Label        basicBlock-18             
        PushD        $release-temp-1           
        LoadI                                  
        PushD        $release-temp-1           
        LoadI                                  
        PushI        12                        
        Add                                    
        LoadI                                  
        PushD        $release-temp-2           
        Exchange                               
        StoreI                                 
        PushD        $release-temp-3           
        PushI        8                         
        StoreI                                 
        PushD        $release-temp-4           
        PushI        16                        
        StoreI                                 
        Label        basicBlock-19             
        Label        -release-stmt-23-loop     
        PushD        $release-temp-2           
        LoadI                                  
        JumpFalse    -release-stmt-23-join-loop 
        Label        basicBlock-20             
        PushD        $release-temp-1           
        LoadI                                  
        PushD        $release-temp-4           
        LoadI                                  
        Add                                    
        PushD        $release-temp-1           
        Exchange                               
        StoreI                                 
        Label        -release-stmt-24-         
        PushD        $release-temp-1           
        LoadI                                  
        PushI        4                         
        Add                                    
        LoadI                                  
        PushI        1                         
        BTAnd                                  
        JumpTrue     -release-stmt-24-join     
        Label        basicBlock-21             
        PushD        $release-temp-1           
        LoadI                                  
        PushI        4                         
        Add                                    
        LoadI                                  
        PushI        2                         
        BTAnd                                  
        JumpTrue     -release-stmt-24-join     
        Label        basicBlock-22             
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
        Label        basicBlock-23             
        Jump         -release-stmt-24-join     
        Label        basicBlock-24             
        Label        -release-stmt-24-join     
        PushD        $release-temp-4           
        LoadI                                  
        PushD        $release-temp-3           
        LoadI                                  
        Add                                    
        PushD        $release-temp-4           
        Exchange                               
        StoreI                                 
        PushD        $release-temp-2           
        LoadI                                  
        PushI        1                         
        Subtract                               
        PushD        $release-temp-2           
        Exchange                               
        StoreI                                 
        Jump         -release-stmt-23-loop     
        Label        basicBlock-25             
        Label        -release-stmt-23-join-loop 
        PushD        $release-temp-1           
        Exchange                               
        StoreI                                 
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
        Label        basicBlock-26             
        Jump         -release-stmt-23-join     
        Label        basicBlock-27             
        Label        -release-stmt-23-join     
        PushD        $global-memory-block      
        PushI        8                         
        Add                                    %% myArr2
        LoadI                                  
        PushD        $release-temp-1           
        Exchange                               
        StoreI                                 
        Label        -release-stmt-25-         
        PushD        $release-temp-1           
        LoadI                                  
        PushI        4                         
        Add                                    
        LoadI                                  
        PushI        1                         
        BTAnd                                  
        JumpTrue     -release-stmt-25-join     
        Label        basicBlock-28             
        PushD        $release-temp-1           
        LoadI                                  
        PushI        4                         
        Add                                    
        LoadI                                  
        PushI        2                         
        BTAnd                                  
        JumpTrue     -release-stmt-25-join     
        Label        basicBlock-29             
        PushD        $release-temp-1           
        LoadI                                  
        PushD        $release-temp-1           
        LoadI                                  
        PushI        12                        
        Add                                    
        LoadI                                  
        PushD        $release-temp-2           
        Exchange                               
        StoreI                                 
        PushD        $release-temp-3           
        PushI        8                         
        StoreI                                 
        PushD        $release-temp-4           
        PushI        16                        
        StoreI                                 
        Label        basicBlock-30             
        Label        -release-stmt-25-loop     
        PushD        $release-temp-2           
        LoadI                                  
        JumpFalse    -release-stmt-25-join-loop 
        Label        basicBlock-31             
        PushD        $release-temp-1           
        LoadI                                  
        PushD        $release-temp-4           
        LoadI                                  
        Add                                    
        PushD        $release-temp-1           
        Exchange                               
        StoreI                                 
        Label        -release-stmt-26-         
        PushD        $release-temp-1           
        LoadI                                  
        PushI        4                         
        Add                                    
        LoadI                                  
        PushI        1                         
        BTAnd                                  
        JumpTrue     -release-stmt-26-join     
        Label        basicBlock-32             
        PushD        $release-temp-1           
        LoadI                                  
        PushI        4                         
        Add                                    
        LoadI                                  
        PushI        2                         
        BTAnd                                  
        JumpTrue     -release-stmt-26-join     
        Label        basicBlock-33             
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
        Label        basicBlock-34             
        Jump         -release-stmt-26-join     
        Label        basicBlock-35             
        Label        -release-stmt-26-join     
        PushD        $release-temp-4           
        LoadI                                  
        PushD        $release-temp-3           
        LoadI                                  
        Add                                    
        PushD        $release-temp-4           
        Exchange                               
        StoreI                                 
        PushD        $release-temp-2           
        LoadI                                  
        PushI        1                         
        Subtract                               
        PushD        $release-temp-2           
        Exchange                               
        StoreI                                 
        Jump         -release-stmt-25-loop     
        Label        basicBlock-36             
        Label        -release-stmt-25-join-loop 
        PushD        $release-temp-1           
        Exchange                               
        StoreI                                 
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
        Label        basicBlock-37             
        Jump         -release-stmt-25-join     
        Label        basicBlock-38             
        Label        -release-stmt-25-join     
        Halt                                   %% End of Execution
        Label        basicBlock-39             
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
        Label        basicBlock-40             
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
        Label        basicBlock-41             
        PushD        $mmgr-tags-return         
        LoadI                                  
        Return                                 
        Label        basicBlock-42             
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
        Label        basicBlock-43             
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
        Label        basicBlock-44             
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
        Label        basicBlock-45             
        PushD        $mmgr-newblock-block      
        LoadI                                  
        PushD        $mmgr-alloc-current-block 
        Exchange                               
        StoreI                                 
        Label        basicBlock-46             
        Label        -mmgr-alloc-return-userblock 
        PushD        $mmgr-alloc-current-block 
        LoadI                                  
        PushI        9                         
        Add                                    
        PushD        $mmgr-alloc-return        
        LoadI                                  
        Return                                 
        Label        basicBlock-47             
        Label        -mem-manager-deallocate   
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
