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
        DLabel       $usable-memory-start      
        DLabel       $global-memory-block      
        DataZ        36                        
        Label        $$main                    
        PushD        $global-memory-block      
        PushI        0                         
        Add                                    %% int1
        PushI        1                         
        StoreI                                 
        PushD        $global-memory-block      
        PushI        4                         
        Add                                    %% int2
        PushI        2                         
        StoreI                                 
        PushD        $global-memory-block      
        PushI        8                         
        Add                                    %% char1
        PushI        101                       
        StoreC                                 
        PushD        $global-memory-block      
        PushI        9                         
        Add                                    %% char2
        PushI        97                        
        StoreC                                 
        PushD        $global-memory-block      
        PushI        10                        
        Add                                    %% flt1
        PushF        1.900000                  
        StoreF                                 
        PushD        $global-memory-block      
        PushI        18                        
        Add                                    %% flt2
        PushF        2.000000                  
        StoreF                                 
        PushD        $global-memory-block      
        PushI        26                        
        Add                                    %% bool1
        PushI        1                         
        StoreC                                 
        PushD        $global-memory-block      
        PushI        27                        
        Add                                    %% bool2
        PushI        0                         
        StoreC                                 
        PushD        $global-memory-block      
        PushI        28                        
        Add                                    %% less_equal
        Label        -compare-5-arg1           
        PushD        $global-memory-block      
        PushI        0                         
        Add                                    %% int1
        LoadI                                  
        Label        -compare-5-arg2           
        PushD        $global-memory-block      
        PushI        4                         
        Add                                    %% int2
        LoadI                                  
        Label        -compare-5-sub            
        Subtract                               
        JumpPos      -compare-5-false          
        Jump         -compare-5-true           
        Label        -compare-5-true           
        PushI        1                         
        Jump         -compare-5-join           
        Label        -compare-5-false          
        PushI        0                         
        Jump         -compare-5-join           
        Label        -compare-5-join           
        StoreC                                 
        PushD        $global-memory-block      
        PushI        29                        
        Add                                    %% less
        Label        -compare-6-arg1           
        PushD        $global-memory-block      
        PushI        9                         
        Add                                    %% char2
        LoadC                                  
        Label        -compare-6-arg2           
        PushD        $global-memory-block      
        PushI        8                         
        Add                                    %% char1
        LoadC                                  
        Label        -compare-6-sub            
        Subtract                               
        JumpNeg      -compare-6-true           
        Jump         -compare-6-false          
        Label        -compare-6-true           
        PushI        1                         
        Jump         -compare-6-join           
        Label        -compare-6-false          
        PushI        0                         
        Jump         -compare-6-join           
        Label        -compare-6-join           
        StoreC                                 
        PushD        $global-memory-block      
        PushI        30                        
        Add                                    %% equal
        Label        -compare-7-arg1           
        PushD        $global-memory-block      
        PushI        26                        
        Add                                    %% bool1
        LoadC                                  
        Label        -compare-7-arg2           
        PushD        $global-memory-block      
        PushI        27                        
        Add                                    %% bool2
        LoadC                                  
        Label        -compare-7-sub            
        Subtract                               
        JumpFalse    -compare-7-true           
        Jump         -compare-7-false          
        Label        -compare-7-true           
        PushI        1                         
        Jump         -compare-7-join           
        Label        -compare-7-false          
        PushI        0                         
        Jump         -compare-7-join           
        Label        -compare-7-join           
        StoreC                                 
        PushD        $global-memory-block      
        PushI        31                        
        Add                                    %% not_equal
        Label        -compare-8-arg1           
        PushD        $global-memory-block      
        PushI        26                        
        Add                                    %% bool1
        LoadC                                  
        Label        -compare-8-arg2           
        PushD        $global-memory-block      
        PushI        27                        
        Add                                    %% bool2
        LoadC                                  
        Label        -compare-8-sub            
        Subtract                               
        JumpFalse    -compare-8-false          
        Jump         -compare-8-true           
        Label        -compare-8-true           
        PushI        1                         
        Jump         -compare-8-join           
        Label        -compare-8-false          
        PushI        0                         
        Jump         -compare-8-join           
        Label        -compare-8-join           
        StoreC                                 
        PushD        $global-memory-block      
        PushI        32                        
        Add                                    %% greater
        Label        -compare-9-arg1           
        PushD        $global-memory-block      
        PushI        10                        
        Add                                    %% flt1
        LoadF                                  
        Label        -compare-9-arg2           
        PushD        $global-memory-block      
        PushI        18                        
        Add                                    %% flt2
        LoadF                                  
        Label        -compare-9-sub            
        FSubtract                              
        JumpFPos     -compare-9-true           
        Jump         -compare-9-false          
        Label        -compare-9-true           
        PushI        1                         
        Jump         -compare-9-join           
        Label        -compare-9-false          
        PushI        0                         
        Jump         -compare-9-join           
        Label        -compare-9-join           
        StoreC                                 
        PushD        $global-memory-block      
        PushI        33                        
        Add                                    %% greater_equal
        Label        -compare-10-arg1          
        PushD        $global-memory-block      
        PushI        10                        
        Add                                    %% flt1
        LoadF                                  
        Label        -compare-10-arg2          
        PushD        $global-memory-block      
        PushI        18                        
        Add                                    %% flt2
        LoadF                                  
        Label        -compare-10-sub           
        FSubtract                              
        JumpFNeg     -compare-10-false         
        Jump         -compare-10-true          
        Label        -compare-10-true          
        PushI        1                         
        Jump         -compare-10-join          
        Label        -compare-10-false         
        PushI        0                         
        Jump         -compare-10-join          
        Label        -compare-10-join          
        StoreC                                 
        PushD        $global-memory-block      
        PushI        28                        
        Add                                    %% less_equal
        LoadC                                  
        JumpTrue     -print-boolean-11-true    
        PushD        $boolean-false-string     
        Jump         -print-boolean-11-join    
        Label        -print-boolean-11-true    
        PushD        $boolean-true-string      
        Label        -print-boolean-11-join    
        PushD        $print-format-boolean     
        Printf                                 
        PushD        $print-format-space       
        Printf                                 
        PushD        $global-memory-block      
        PushI        29                        
        Add                                    %% less
        LoadC                                  
        JumpTrue     -print-boolean-12-true    
        PushD        $boolean-false-string     
        Jump         -print-boolean-12-join    
        Label        -print-boolean-12-true    
        PushD        $boolean-true-string      
        Label        -print-boolean-12-join    
        PushD        $print-format-boolean     
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        PushD        $global-memory-block      
        PushI        30                        
        Add                                    %% equal
        LoadC                                  
        JumpTrue     -print-boolean-13-true    
        PushD        $boolean-false-string     
        Jump         -print-boolean-13-join    
        Label        -print-boolean-13-true    
        PushD        $boolean-true-string      
        Label        -print-boolean-13-join    
        PushD        $print-format-boolean     
        Printf                                 
        PushD        $print-format-space       
        Printf                                 
        PushD        $global-memory-block      
        PushI        31                        
        Add                                    %% not_equal
        LoadC                                  
        JumpTrue     -print-boolean-14-true    
        PushD        $boolean-false-string     
        Jump         -print-boolean-14-join    
        Label        -print-boolean-14-true    
        PushD        $boolean-true-string      
        Label        -print-boolean-14-join    
        PushD        $print-format-boolean     
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        PushD        $global-memory-block      
        PushI        32                        
        Add                                    %% greater
        LoadC                                  
        JumpTrue     -print-boolean-15-true    
        PushD        $boolean-false-string     
        Jump         -print-boolean-15-join    
        Label        -print-boolean-15-true    
        PushD        $boolean-true-string      
        Label        -print-boolean-15-join    
        PushD        $print-format-boolean     
        Printf                                 
        PushD        $print-format-space       
        Printf                                 
        PushD        $global-memory-block      
        PushI        33                        
        Add                                    %% greater_equal
        LoadC                                  
        JumpTrue     -print-boolean-16-true    
        PushD        $boolean-false-string     
        Jump         -print-boolean-16-join    
        Label        -print-boolean-16-true    
        PushD        $boolean-true-string      
        Label        -print-boolean-16-join    
        PushD        $print-format-boolean     
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        PushD        $global-memory-block      
        PushI        34                        
        Add                                    %% cmp1
        Label        -compare-17-arg1          
        PushD        $global-memory-block      
        PushI        8                         
        Add                                    %% char1
        LoadC                                  
        Label        -compare-17-arg2          
        PushD        $global-memory-block      
        PushI        8                         
        Add                                    %% char1
        LoadC                                  
        Label        -compare-17-sub           
        Subtract                               
        JumpFalse    -compare-17-true          
        Jump         -compare-17-false         
        Label        -compare-17-true          
        PushI        1                         
        Jump         -compare-17-join          
        Label        -compare-17-false         
        PushI        0                         
        Jump         -compare-17-join          
        Label        -compare-17-join          
        StoreC                                 
        PushD        $global-memory-block      
        PushI        35                        
        Add                                    %% cmp2
        Label        -compare-18-arg1          
        PushD        $global-memory-block      
        PushI        8                         
        Add                                    %% char1
        LoadC                                  
        Label        -compare-18-arg2          
        PushD        $global-memory-block      
        PushI        9                         
        Add                                    %% char2
        LoadC                                  
        Label        -compare-18-sub           
        Subtract                               
        JumpFalse    -compare-18-true          
        Jump         -compare-18-false         
        Label        -compare-18-true          
        PushI        1                         
        Jump         -compare-18-join          
        Label        -compare-18-false         
        PushI        0                         
        Jump         -compare-18-join          
        Label        -compare-18-join          
        StoreC                                 
        Halt                                   