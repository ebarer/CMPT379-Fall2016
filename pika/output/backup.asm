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
DataZ        16
Label        $$main
PushD        $global-memory-block
PushI        0
Add                                    %% sum0
PushI        -2
PushI        3
Add
StoreI
PushD        $global-memory-block
PushI        4
Add                                    %% sum1
PushF        3.000000
PushF        -420000.000000
FAdd
StoreF
PushD        $global-memory-block
PushI        12
Add                                    %% sum2
PushI        -2
PushF        4.300000
PushF        3.600000
FAdd
ConvertI
Duplicate
JumpFalse    $$divide-by-zero
Divide
StoreI
PushD        $global-memory-block
PushI        12
Add                                    %% sum2
LoadI
PushD        $print-format-integer
Printf
Halt
