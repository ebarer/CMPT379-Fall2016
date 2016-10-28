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
        DataZ        36                        
        DLabel       -stringConstant-162-      
        DataI        6                         
        DataI        9                         
        DataI        9                         
        DataC        76                        %% "Length is"
        DataC        101                       
        DataC        110                       
        DataC        103                       
        DataC        116                       
        DataC        104                       
        DataC        32                        
        DataC        105                       
        DataC        115                       
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
        Label        basicBlock-1              
        Label        $$main                    
        PushD        $global-memory-block      
        PushI        0                         
        Add                                    %% arr1
        Label        -array-159-               
        PushI        7                         
        PushI        7                         
        PushI        4                         
        Multiply                               
        PushI        16                        
        Add                                    
        Call         -mem-manager-allocate     
        Label        basicBlock-2              
        PushD        $array-temp-1             
        Exchange                               
        StoreI                                 
        Label        -array-159-create-record  
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
        Label        -array-159-start-store-children 
        Duplicate                              
        PushI        16                        
        Add                                    
        PushI        0                         
        StoreI                                 
        Duplicate                              
        PushI        20                        
        Add                                    
        PushI        30                        
        StoreI                                 
        Duplicate                              
        PushI        24                        
        Add                                    
        PushI        31                        
        StoreI                                 
        Duplicate                              
        PushI        28                        
        Add                                    
        PushI        99                        
        StoreI                                 
        Duplicate                              
        PushI        32                        
        Add                                    
        PushI        24                        
        StoreI                                 
        Duplicate                              
        PushI        36                        
        Add                                    
        PushI        310                       
        StoreI                                 
        Duplicate                              
        PushI        40                        
        Add                                    
        PushI        -25                       
        StoreI                                 
        Label        -array-159-end-store-children 
        StoreI                                 
        PushD        $global-memory-block      
        PushI        0                         
        Add                                    %% arr1
        LoadI                                  
        Label        basicBlock-4              
        Label        -print-array-160-         
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
        Label        basicBlock-5              
        Label        -print-array-160-loop     
        PushD        $print-temp-1             
        LoadI                                  
        JumpFalse    -print-array-160-join     
        Label        basicBlock-6              
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
        JumpFalse    -print-array-160-join     
        Label        basicBlock-7              
        PushI        44                        
        PushD        $print-format-character   
        Printf                                 
        PushI        0                         
        PushD        $print-format-character   
        Printf                                 
        Jump         -print-array-160-loop     
        Label        basicBlock-8              
        Label        -print-array-160-join     
        PushI        93                        
        PushD        $print-format-character   
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        PushD        $global-memory-block      
        PushI        8                         
        Add                                    %% arr15
        PushD        $global-memory-block      
        PushI        0                         
        Add                                    %% arr1
        LoadI                                  
        StoreI                                 
        PushD        $global-memory-block      
        PushI        8                         
        Add                                    %% arr15
        LoadI                                  
        Label        -print-array-161-         
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
        Label        basicBlock-9              
        Label        -print-array-161-loop     
        PushD        $print-temp-1             
        LoadI                                  
        JumpFalse    -print-array-161-join     
        Label        basicBlock-10             
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
        JumpFalse    -print-array-161-join     
        Label        basicBlock-11             
        PushI        44                        
        PushD        $print-format-character   
        Printf                                 
        PushI        0                         
        PushD        $print-format-character   
        Printf                                 
        Jump         -print-array-161-loop     
        Label        basicBlock-12             
        Label        -print-array-161-join     
        PushI        93                        
        PushD        $print-format-character   
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        PushD        $global-memory-block      
        PushI        16                        
        Add                                    %% i
        PushD        $global-memory-block      
        PushI        0                         
        Add                                    %% arr1
        LoadI                                  
        PushI        12                        
        Add                                    
        LoadI                                  
        StoreI                                 
        PushD        -stringConstant-162-      
        PushI        12                        
        Add                                    
        PushD        $print-format-string      
        Printf                                 
        PushD        $print-format-space       
        Printf                                 
        PushD        $global-memory-block      
        PushI        16                        
        Add                                    %% i
        LoadI                                  
        PushD        $print-format-integer     
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        PushD        $global-memory-block      
        PushI        20                        
        Add                                    %% arr2
        Label        -array-163-               
        PushI        4                         
        PushI        4                         
        PushI        4                         
        Multiply                               
        PushI        16                        
        Add                                    
        Call         -mem-manager-allocate     
        Label        basicBlock-13             
        PushD        $array-temp-1             
        Exchange                               
        StoreI                                 
        Label        -array-163-create-record  
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
        Label        basicBlock-14             
        Label        -array-163-start-store-children 
        Duplicate                              
        PushI        16                        
        Add                                    
        PushI        0                         
        StoreI                                 
        Duplicate                              
        PushI        20                        
        Add                                    
        PushI        30                        
        StoreI                                 
        Duplicate                              
        PushI        24                        
        Add                                    
        PushI        31                        
        StoreI                                 
        Duplicate                              
        PushI        28                        
        Add                                    
        PushI        99                        
        StoreI                                 
        Label        -array-163-end-store-children 
        StoreI                                 
        PushD        $global-memory-block      
        PushI        28                        
        Add                                    %% arr3
        PushD        $global-memory-block      
        PushI        0                         
        Add                                    %% arr1
        LoadI                                  
        StoreI                                 
        PushD        $global-memory-block      
        PushI        28                        
        Add                                    %% arr3
        LoadI                                  
        Label        basicBlock-15             
        Label        -print-array-164-         
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
        Label        basicBlock-16             
        Label        -print-array-164-loop     
        PushD        $print-temp-1             
        LoadI                                  
        JumpFalse    -print-array-164-join     
        Label        basicBlock-17             
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
        JumpFalse    -print-array-164-join     
        Label        basicBlock-18             
        PushI        44                        
        PushD        $print-format-character   
        Printf                                 
        PushI        0                         
        PushD        $print-format-character   
        Printf                                 
        Jump         -print-array-164-loop     
        Label        basicBlock-19             
        Label        -print-array-164-join     
        PushI        93                        
        PushD        $print-format-character   
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        PushD        $global-memory-block      
        PushI        0                         
        Add                                    %% arr1
        LoadI                                  
        PushD        $global-memory-block      
        PushI        20                        
        Add                                    %% arr2
        LoadI                                  
        Subtract                               
        JumpFalse    -compare-165-true         
        Jump         -compare-165-false        
        Label        basicBlock-20             
        Label        -compare-165-true         
        PushI        1                         
        Jump         -compare-165-join         
        Label        basicBlock-21             
        Label        -compare-165-false        
        PushI        0                         
        Label        basicBlock-22             
        Label        -compare-165-join         
        JumpTrue     -print-boolean-167-true   
        Label        basicBlock-23             
        PushD        $boolean-false-string     
        Jump         -print-boolean-167-join   
        Label        basicBlock-24             
        Label        -print-boolean-167-true   
        PushD        $boolean-true-string      
        Label        basicBlock-25             
        Label        -print-boolean-167-join   
        PushD        $print-format-boolean     
        Printf                                 
        PushD        $print-format-space       
        Printf                                 
        PushD        $global-memory-block      
        PushI        0                         
        Add                                    %% arr1
        LoadI                                  
        PushD        $global-memory-block      
        PushI        20                        
        Add                                    %% arr2
        LoadI                                  
        Subtract                               
        JumpFalse    -compare-166-false        
        Jump         -compare-166-true         
        Label        basicBlock-26             
        Label        -compare-166-true         
        PushI        1                         
        Jump         -compare-166-join         
        Label        basicBlock-27             
        Label        -compare-166-false        
        PushI        0                         
        Label        basicBlock-28             
        Label        -compare-166-join         
        JumpTrue     -print-boolean-168-true   
        Label        basicBlock-29             
        PushD        $boolean-false-string     
        Jump         -print-boolean-168-join   
        Label        basicBlock-30             
        Label        -print-boolean-168-true   
        PushD        $boolean-true-string      
        Label        basicBlock-31             
        Label        -print-boolean-168-join   
        PushD        $print-format-boolean     
        Printf                                 
        PushD        $print-format-space       
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        PushD        $global-memory-block      
        PushI        0                         
        Add                                    %% arr1
        LoadI                                  
        PushD        $global-memory-block      
        PushI        28                        
        Add                                    %% arr3
        LoadI                                  
        Subtract                               
        JumpFalse    -compare-169-true         
        Jump         -compare-169-false        
        Label        basicBlock-32             
        Label        -compare-169-true         
        PushI        1                         
        Jump         -compare-169-join         
        Label        basicBlock-33             
        Label        -compare-169-false        
        PushI        0                         
        Label        basicBlock-34             
        Label        -compare-169-join         
        JumpTrue     -print-boolean-171-true   
        Label        basicBlock-35             
        PushD        $boolean-false-string     
        Jump         -print-boolean-171-join   
        Label        basicBlock-36             
        Label        -print-boolean-171-true   
        PushD        $boolean-true-string      
        Label        basicBlock-37             
        Label        -print-boolean-171-join   
        PushD        $print-format-boolean     
        Printf                                 
        PushD        $print-format-space       
        Printf                                 
        PushD        $global-memory-block      
        PushI        0                         
        Add                                    %% arr1
        LoadI                                  
        PushD        $global-memory-block      
        PushI        28                        
        Add                                    %% arr3
        LoadI                                  
        Subtract                               
        JumpFalse    -compare-170-false        
        Jump         -compare-170-true         
        Label        basicBlock-38             
        Label        -compare-170-true         
        PushI        1                         
        Jump         -compare-170-join         
        Label        basicBlock-39             
        Label        -compare-170-false        
        PushI        0                         
        Label        basicBlock-40             
        Label        -compare-170-join         
        JumpTrue     -print-boolean-172-true   
        Label        basicBlock-41             
        PushD        $boolean-false-string     
        Jump         -print-boolean-172-join   
        Label        basicBlock-42             
        Label        -print-boolean-172-true   
        PushD        $boolean-true-string      
        Label        basicBlock-43             
        Label        -print-boolean-172-join   
        PushD        $print-format-boolean     
        Printf                                 
        PushD        $print-format-space       
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        PushD        $global-memory-block      
        PushI        20                        
        Add                                    %% arr2
        LoadI                                  
        PushD        $global-memory-block      
        PushI        28                        
        Add                                    %% arr3
        LoadI                                  
        Subtract                               
        JumpFalse    -compare-173-true         
        Jump         -compare-173-false        
        Label        basicBlock-44             
        Label        -compare-173-true         
        PushI        1                         
        Jump         -compare-173-join         
        Label        basicBlock-45             
        Label        -compare-173-false        
        PushI        0                         
        Label        basicBlock-46             
        Label        -compare-173-join         
        JumpTrue     -print-boolean-175-true   
        Label        basicBlock-47             
        PushD        $boolean-false-string     
        Jump         -print-boolean-175-join   
        Label        basicBlock-48             
        Label        -print-boolean-175-true   
        PushD        $boolean-true-string      
        Label        basicBlock-49             
        Label        -print-boolean-175-join   
        PushD        $print-format-boolean     
        Printf                                 
        PushD        $print-format-space       
        Printf                                 
        PushD        $global-memory-block      
        PushI        20                        
        Add                                    %% arr2
        LoadI                                  
        PushD        $global-memory-block      
        PushI        28                        
        Add                                    %% arr3
        LoadI                                  
        Subtract                               
        JumpFalse    -compare-174-false        
        Jump         -compare-174-true         
        Label        basicBlock-50             
        Label        -compare-174-true         
        PushI        1                         
        Jump         -compare-174-join         
        Label        basicBlock-51             
        Label        -compare-174-false        
        PushI        0                         
        Label        basicBlock-52             
        Label        -compare-174-join         
        JumpTrue     -print-boolean-176-true   
        Label        basicBlock-53             
        PushD        $boolean-false-string     
        Jump         -print-boolean-176-join   
        Label        basicBlock-54             
        Label        -print-boolean-176-true   
        PushD        $boolean-true-string      
        Label        basicBlock-55             
        Label        -print-boolean-176-join   
        PushD        $print-format-boolean     
        Printf                                 
        PushD        $print-format-space       
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        PushD        $global-memory-block      
        PushI        28                        
        Add                                    %% arr3
        PushD        $global-memory-block      
        PushI        20                        
        Add                                    %% arr2
        LoadI                                  
        StoreI                                 
        PushD        $global-memory-block      
        PushI        28                        
        Add                                    %% arr3
        LoadI                                  
        Label        -print-array-177-         
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
        Label        basicBlock-56             
        Label        -print-array-177-loop     
        PushD        $print-temp-1             
        LoadI                                  
        JumpFalse    -print-array-177-join     
        Label        basicBlock-57             
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
        JumpFalse    -print-array-177-join     
        Label        basicBlock-58             
        PushI        44                        
        PushD        $print-format-character   
        Printf                                 
        PushI        0                         
        PushD        $print-format-character   
        Printf                                 
        Jump         -print-array-177-loop     
        Label        basicBlock-59             
        Label        -print-array-177-join     
        PushI        93                        
        PushD        $print-format-character   
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        PushD        $global-memory-block      
        PushI        0                         
        Add                                    %% arr1
        LoadI                                  
        PushD        $global-memory-block      
        PushI        20                        
        Add                                    %% arr2
        LoadI                                  
        Subtract                               
        JumpFalse    -compare-178-true         
        Jump         -compare-178-false        
        Label        basicBlock-60             
        Label        -compare-178-true         
        PushI        1                         
        Jump         -compare-178-join         
        Label        basicBlock-61             
        Label        -compare-178-false        
        PushI        0                         
        Label        basicBlock-62             
        Label        -compare-178-join         
        JumpTrue     -print-boolean-180-true   
        Label        basicBlock-63             
        PushD        $boolean-false-string     
        Jump         -print-boolean-180-join   
        Label        basicBlock-64             
        Label        -print-boolean-180-true   
        PushD        $boolean-true-string      
        Label        basicBlock-65             
        Label        -print-boolean-180-join   
        PushD        $print-format-boolean     
        Printf                                 
        PushD        $print-format-space       
        Printf                                 
        PushD        $global-memory-block      
        PushI        0                         
        Add                                    %% arr1
        LoadI                                  
        PushD        $global-memory-block      
        PushI        20                        
        Add                                    %% arr2
        LoadI                                  
        Subtract                               
        JumpFalse    -compare-179-false        
        Jump         -compare-179-true         
        Label        basicBlock-66             
        Label        -compare-179-true         
        PushI        1                         
        Jump         -compare-179-join         
        Label        basicBlock-67             
        Label        -compare-179-false        
        PushI        0                         
        Label        basicBlock-68             
        Label        -compare-179-join         
        JumpTrue     -print-boolean-181-true   
        Label        basicBlock-69             
        PushD        $boolean-false-string     
        Jump         -print-boolean-181-join   
        Label        basicBlock-70             
        Label        -print-boolean-181-true   
        PushD        $boolean-true-string      
        Label        basicBlock-71             
        Label        -print-boolean-181-join   
        PushD        $print-format-boolean     
        Printf                                 
        PushD        $print-format-space       
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        PushD        $global-memory-block      
        PushI        0                         
        Add                                    %% arr1
        LoadI                                  
        PushD        $global-memory-block      
        PushI        28                        
        Add                                    %% arr3
        LoadI                                  
        Subtract                               
        JumpFalse    -compare-182-true         
        Jump         -compare-182-false        
        Label        basicBlock-72             
        Label        -compare-182-true         
        PushI        1                         
        Jump         -compare-182-join         
        Label        basicBlock-73             
        Label        -compare-182-false        
        PushI        0                         
        Label        basicBlock-74             
        Label        -compare-182-join         
        JumpTrue     -print-boolean-184-true   
        Label        basicBlock-75             
        PushD        $boolean-false-string     
        Jump         -print-boolean-184-join   
        Label        basicBlock-76             
        Label        -print-boolean-184-true   
        PushD        $boolean-true-string      
        Label        basicBlock-77             
        Label        -print-boolean-184-join   
        PushD        $print-format-boolean     
        Printf                                 
        PushD        $print-format-space       
        Printf                                 
        PushD        $global-memory-block      
        PushI        0                         
        Add                                    %% arr1
        LoadI                                  
        PushD        $global-memory-block      
        PushI        28                        
        Add                                    %% arr3
        LoadI                                  
        Subtract                               
        JumpFalse    -compare-183-false        
        Jump         -compare-183-true         
        Label        basicBlock-78             
        Label        -compare-183-true         
        PushI        1                         
        Jump         -compare-183-join         
        Label        basicBlock-79             
        Label        -compare-183-false        
        PushI        0                         
        Label        basicBlock-80             
        Label        -compare-183-join         
        JumpTrue     -print-boolean-185-true   
        Label        basicBlock-81             
        PushD        $boolean-false-string     
        Jump         -print-boolean-185-join   
        Label        basicBlock-82             
        Label        -print-boolean-185-true   
        PushD        $boolean-true-string      
        Label        basicBlock-83             
        Label        -print-boolean-185-join   
        PushD        $print-format-boolean     
        Printf                                 
        PushD        $print-format-space       
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        PushD        $global-memory-block      
        PushI        20                        
        Add                                    %% arr2
        LoadI                                  
        PushD        $global-memory-block      
        PushI        28                        
        Add                                    %% arr3
        LoadI                                  
        Subtract                               
        JumpFalse    -compare-186-true         
        Jump         -compare-186-false        
        Label        basicBlock-84             
        Label        -compare-186-true         
        PushI        1                         
        Jump         -compare-186-join         
        Label        basicBlock-85             
        Label        -compare-186-false        
        PushI        0                         
        Label        basicBlock-86             
        Label        -compare-186-join         
        JumpTrue     -print-boolean-188-true   
        Label        basicBlock-87             
        PushD        $boolean-false-string     
        Jump         -print-boolean-188-join   
        Label        basicBlock-88             
        Label        -print-boolean-188-true   
        PushD        $boolean-true-string      
        Label        basicBlock-89             
        Label        -print-boolean-188-join   
        PushD        $print-format-boolean     
        Printf                                 
        PushD        $print-format-space       
        Printf                                 
        PushD        $global-memory-block      
        PushI        20                        
        Add                                    %% arr2
        LoadI                                  
        PushD        $global-memory-block      
        PushI        28                        
        Add                                    %% arr3
        LoadI                                  
        Subtract                               
        JumpFalse    -compare-187-false        
        Jump         -compare-187-true         
        Label        basicBlock-90             
        Label        -compare-187-true         
        PushI        1                         
        Jump         -compare-187-join         
        Label        basicBlock-91             
        Label        -compare-187-false        
        PushI        0                         
        Label        basicBlock-92             
        Label        -compare-187-join         
        JumpTrue     -print-boolean-189-true   
        Label        basicBlock-93             
        PushD        $boolean-false-string     
        Jump         -print-boolean-189-join   
        Label        basicBlock-94             
        Label        -print-boolean-189-true   
        PushD        $boolean-true-string      
        Label        basicBlock-95             
        Label        -print-boolean-189-join   
        PushD        $print-format-boolean     
        Printf                                 
        PushD        $print-format-space       
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        Halt                                   %% End of Execution
        Label        basicBlock-96             
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
        Label        basicBlock-97             
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
        Label        basicBlock-98             
        PushD        $mmgr-tags-return         
        LoadI                                  
        Return                                 
        Label        basicBlock-99             
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
        Label        basicBlock-100            
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
        Label        basicBlock-101            
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
        Label        basicBlock-102            
        PushD        $mmgr-newblock-block      
        LoadI                                  
        PushD        $mmgr-alloc-current-block 
        Exchange                               
        StoreI                                 
        Label        basicBlock-103            
        Label        -mmgr-alloc-return-userblock 
        PushD        $mmgr-alloc-current-block 
        LoadI                                  
        PushI        9                         
        Add                                    
        PushD        $mmgr-alloc-return        
        LoadI                                  
        Return                                 
