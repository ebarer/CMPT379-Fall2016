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
        DataZ        116                       
        Label        $$main                    
        PushD        $global-memory-block      
        PushI        0                         
        Add                                    %% int0
        PushI        10                        
        StoreI                                 
        PushD        $global-memory-block      
        PushI        4                         
        Add                                    %% float0
        PushF        5.000000                  
        StoreF                                 
        PushD        $global-memory-block      
        PushI        12                        
        Add                                    %% float1
        PushF        5.000000                  
        StoreF                                 
        PushD        $global-memory-block      
        PushI        20                        
        Add                                    %% float2
        PushF        -5.000000                 
        StoreF                                 
        PushD        $global-memory-block      
        PushI        28                        
        Add                                    %% float3
        PushF        0.500000                  
        StoreF                                 
        PushD        $global-memory-block      
        PushI        36                        
        Add                                    %% float4
        PushF        -0.500000                 
        StoreF                                 
        PushD        $global-memory-block      
        PushI        44                        
        Add                                    %% float5
        PushF        0.500000                  
        StoreF                                 
        PushD        $global-memory-block      
        PushI        52                        
        Add                                    %% float6
        PushF        -0.500000                 
        StoreF                                 
        PushD        $global-memory-block      
        PushI        60                        
        Add                                    %% float7
        PushF        500.000000                
        StoreF                                 
        PushD        $global-memory-block      
        PushI        68                        
        Add                                    %% float8
        PushF        500.000000                
        StoreF                                 
        PushD        $global-memory-block      
        PushI        76                        
        Add                                    %% float9
        PushF        0.050000                  
        StoreF                                 
        PushD        $global-memory-block      
        PushI        84                        
        Add                                    %% float10
        PushF        -0.005000                 
        StoreF                                 
        PushD        $global-memory-block      
        PushI        44                        
        Add                                    %% float5
        LoadF                                  
        PushD        $print-format-floating    
        Printf                                 
        PushD        $print-format-space       
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        PushD        $print-format-space       
        Printf                                 
        PushD        $global-memory-block      
        PushI        84                        
        Add                                    %% float10
        LoadF                                  
        PushD        $print-format-floating    
        Printf                                 
        PushD        $print-format-space       
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        PushD        $global-memory-block      
        PushI        92                        
        Add                                    %% sum0
        PushD        $global-memory-block      
        PushI        20                        
        Add                                    %% float2
        LoadF                                  
        PushD        $global-memory-block      
        PushI        36                        
        Add                                    %% float4
        LoadF                                  
        FAdd                                   
        StoreF                                 
        PushD        $global-memory-block      
        PushI        92                        
        Add                                    %% sum0
        LoadF                                  
        PushD        $print-format-floating    
        Printf                                 
        PushD        $print-format-space       
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        PushD        $global-memory-block      
        PushI        100                       
        Add                                    %% sum1
        PushD        $global-memory-block      
        PushI        52                        
        Add                                    %% float6
        LoadF                                  
        PushD        $global-memory-block      
        PushI        68                        
        Add                                    %% float8
        LoadF                                  
        FAdd                                   
        StoreF                                 
        PushD        $global-memory-block      
        PushI        100                       
        Add                                    %% sum1
        LoadF                                  
        PushD        $print-format-floating    
        Printf                                 
        PushD        $print-format-space       
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        PushD        $global-memory-block      
        PushI        108                       
        Add                                    %% sum2
        PushD        $global-memory-block      
        PushI        28                        
        Add                                    %% float3
        LoadF                                  
        PushD        $global-memory-block      
        PushI        76                        
        Add                                    %% float9
        LoadF                                  
        FAdd                                   
        StoreF                                 
        PushD        $global-memory-block      
        PushI        108                       
        Add                                    %% sum2
        LoadF                                  
        PushD        $print-format-floating    
        Printf                                 
        PushD        $print-format-space       
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        Halt                                   
