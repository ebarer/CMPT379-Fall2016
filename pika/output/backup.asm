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
DataZ        80
Label        $$main
PushD        $global-memory-block
PushI        0
Add                                    %% sum1
PushI        -2
PushF        4.300000
PushF        3.600000
FAdd
ConvertI
Multiply
StoreI
PushD        $global-memory-block
PushI        4
Add                                    %% sum2
PushI        -2
PushF        4.300000
PushF        3.700000
FAdd
ConvertI
Multiply
StoreI
PushD        $global-memory-block
PushI        0
Add                                    %% sum1
LoadI
PushD        $print-format-integer
Printf
PushD        $print-format-tab
Printf
PushD        $global-memory-block
PushI        4
Add                                    %% sum2
LoadI
PushD        $print-format-integer
Printf
PushD        $print-format-newline
Printf
PushD        $global-memory-block
PushI        8
Add                                    %% sum3
PushD        $global-memory-block
PushI        0
Add                                    %% sum1
LoadI
PushD        $global-memory-block
PushI        4
Add                                    %% sum2
LoadI
Multiply
StoreI
PushD        $global-memory-block
PushI        12
Add                                    %% sum4
PushD        $global-memory-block
PushI        0
Add                                    %% sum1
LoadI
PushD        $global-memory-block
PushI        4
Add                                    %% sum2
LoadI
Multiply
PushF        4.300000
PushF        3.600000
FAdd
ConvertI
PushF        4.300000
PushF        3.700000
FAdd
ConvertI
Duplicate
JumpFalse    $$divide-by-zero
Divide
Add
StoreI
PushD        $global-memory-block
PushI        8
Add                                    %% sum3
LoadI
PushD        $print-format-integer
Printf
PushD        $print-format-tab
Printf
PushD        $global-memory-block
PushI        12
Add                                    %% sum4
LoadI
PushD        $print-format-integer
Printf
PushD        $print-format-newline
Printf
PushD        $global-memory-block
PushI        16
Add                                    %% myVar1
PushI        97
StoreI
PushD        $global-memory-block
PushI        20
Add                                    %% myVar2
PushD        $global-memory-block
PushI        16
Add                                    %% myVar1
LoadI
Nop
StoreC
PushD        $global-memory-block
PushI        16
Add                                    %% myVar1
LoadI
PushD        $print-format-integer
Printf
PushD        $print-format-tab
Printf
PushD        $global-memory-block
PushI        20
Add                                    %% myVar2
LoadC
PushD        $print-format-character
Printf
PushD        $print-format-newline
Printf
PushD        $global-memory-block
PushI        16
Add                                    %% myVar1
PushD        $global-memory-block
PushI        16
Add                                    %% myVar1
LoadI
PushI        4
Add
StoreI
PushD        $global-memory-block
PushI        20
Add                                    %% myVar2
PushD        $global-memory-block
PushI        16
Add                                    %% myVar1
LoadI
Nop
StoreC
PushD        $global-memory-block
PushI        16
Add                                    %% myVar1
LoadI
PushD        $print-format-integer
Printf
PushD        $print-format-tab
Printf
PushD        $global-memory-block
PushI        20
Add                                    %% myVar2
LoadC
PushD        $print-format-character
Printf
PushD        $print-format-newline
Printf
PushD        $global-memory-block
PushI        21
Add                                    %% val1
PushI        1
StoreI
PushD        $global-memory-block
PushI        25
Add                                    %% test1
PushD        $global-memory-block
PushI        21
Add                                    %% val1
LoadI
Nop
StoreC
PushD        $global-memory-block
PushI        26
Add                                    %% val2
PushI        0
Nop
StoreC
PushD        $global-memory-block
PushI        27
Add                                    %% test2
PushD        $global-memory-block
PushI        26
Add                                    %% val2
LoadC
Nop
StoreC
PushD        $global-memory-block
PushI        21
Add                                    %% val1
LoadI
PushD        $print-format-integer
Printf
PushD        $print-format-tab
Printf
PushD        $global-memory-block
PushI        25
Add                                    %% test1
LoadC
JumpTrue     -print-boolean-1-true
PushD        $boolean-false-string
Jump         -print-boolean-1-join
Label        -print-boolean-1-true
PushD        $boolean-true-string
Label        -print-boolean-1-join
PushD        $print-format-boolean
Printf
PushD        $print-format-space
Printf
PushD        $print-format-newline
Printf
PushD        $global-memory-block
PushI        26
Add                                    %% val2
LoadC
PushD        $print-format-character
Printf
PushD        $print-format-tab
Printf
PushD        $global-memory-block
PushI        27
Add                                    %% test2
LoadC
JumpTrue     -print-boolean-2-true
PushD        $boolean-false-string
Jump         -print-boolean-2-join
Label        -print-boolean-2-true
PushD        $boolean-true-string
Label        -print-boolean-2-join
PushD        $print-format-boolean
Printf
PushD        $print-format-newline
Printf
PushD        $global-memory-block
PushI        16
Add                                    %% val3
PushI        101
StoreC
PushD        $global-memory-block
PushI        17
Add                                    %% test3
PushD        $global-memory-block
PushI        16
Add                                    %% val3
LoadC
Nop
StoreC
PushD        $global-memory-block
PushI        18
Add                                    %% val4
PushI        48
StoreC
PushD        $global-memory-block
PushI        19
Add                                    %% test4
PushD        $global-memory-block
PushI        18
Add                                    %% val4
LoadC
Nop
StoreC
PushD        $global-memory-block
PushI        16
Add                                    %% val3
LoadC
PushD        $print-format-character
Printf
PushD        $print-format-tab
Printf
PushD        $global-memory-block
PushI        17
Add                                    %% test3
LoadC
JumpTrue     -print-boolean-3-true
PushD        $boolean-false-string
Jump         -print-boolean-3-join
Label        -print-boolean-3-true
PushD        $boolean-true-string
Label        -print-boolean-3-join
PushD        $print-format-boolean
Printf
PushD        $print-format-newline
Printf
PushD        $global-memory-block
PushI        18
Add                                    %% val4
LoadC
PushD        $print-format-character
Printf
PushD        $print-format-tab
Printf
PushD        $global-memory-block
PushI        19
Add                                    %% test4
LoadC
JumpTrue     -print-boolean-4-true
PushD        $boolean-false-string
Jump         -print-boolean-4-join
Label        -print-boolean-4-true
PushD        $boolean-true-string
Label        -print-boolean-4-join
PushD        $print-format-boolean
Printf
PushD        $print-format-newline
Printf
PushD        $global-memory-block
PushI        20
Add                                    %% int0
PushI        10
StoreI
PushD        $global-memory-block
PushI        24
Add                                    %% int1
PushI        9
StoreI
PushD        $global-memory-block
PushI        20
Add                                    %% int0
LoadI
PushD        $print-format-integer
Printf
PushD        $print-format-tab
Printf
PushD        $global-memory-block
PushI        24
Add                                    %% int1
LoadI
PushD        $print-format-integer
Printf
PushD        $print-format-newline
Printf
PushD        $global-memory-block
PushI        24
Add                                    %% int1
PushI        2
StoreI
PushD        $global-memory-block
PushI        20
Add                                    %% int0
LoadI
PushD        $print-format-integer
Printf
PushD        $print-format-tab
Printf
PushD        $global-memory-block
PushI        24
Add                                    %% int1
LoadI
PushD        $print-format-integer
Printf
PushD        $print-format-newline
Printf
PushD        $global-memory-block
PushI        28
Add                                    %% float0
PushF        5.000000
StoreF
PushD        $global-memory-block
PushI        36
Add                                    %% float1
PushF        4.900000
StoreF
PushD        $global-memory-block
PushI        28
Add                                    %% float0
LoadF
PushD        $print-format-floating
Printf
PushD        $print-format-tab
Printf
PushD        $global-memory-block
PushI        36
Add                                    %% float1
LoadF
PushD        $print-format-floating
Printf
PushD        $print-format-newline
Printf
PushD        $global-memory-block
PushI        36
Add                                    %% float1
PushF        3.500000
StoreF
PushD        $global-memory-block
PushI        28
Add                                    %% float0
LoadF
PushD        $print-format-floating
Printf
PushD        $print-format-tab
Printf
PushD        $global-memory-block
PushI        36
Add                                    %% float1
LoadF
PushD        $print-format-floating
Printf
PushD        $print-format-newline
Printf
PushD        $global-memory-block
PushI        44
Add                                    %% char1
PushI        101
StoreC
PushD        $global-memory-block
PushI        45
Add                                    %% char2
PushI        97
StoreC
PushD        $global-memory-block
PushI        46
Add                                    %% flt1
PushF        1.900000
StoreF
PushD        $global-memory-block
PushI        54
Add                                    %% flt2
PushF        2.000000
StoreF
PushD        $global-memory-block
PushI        62
Add                                    %% bool1
PushI        1
StoreC
PushD        $global-memory-block
PushI        63
Add                                    %% bool2
PushI        0
StoreC
PushD        $global-memory-block
PushI        64
Add                                    %% less_equal
Label        -compare-5-arg1
PushD        $global-memory-block
PushI        20
Add                                    %% int0
LoadI
Label        -compare-5-arg2
PushD        $global-memory-block
PushI        24
Add                                    %% int1
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
PushI        65
Add                                    %% less
Label        -compare-6-arg1
PushD        $global-memory-block
PushI        45
Add                                    %% char2
LoadC
Label        -compare-6-arg2
PushD        $global-memory-block
PushI        44
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
PushI        66
Add                                    %% equal
Label        -compare-7-arg1
PushD        $global-memory-block
PushI        62
Add                                    %% bool1
LoadC
Label        -compare-7-arg2
PushD        $global-memory-block
PushI        63
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
PushI        67
Add                                    %% not_equal
Label        -compare-8-arg1
PushD        $global-memory-block
PushI        62
Add                                    %% bool1
LoadC
Label        -compare-8-arg2
PushD        $global-memory-block
PushI        63
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
PushI        68
Add                                    %% greater
Label        -compare-9-arg1
PushD        $global-memory-block
PushI        46
Add                                    %% flt1
LoadF
Label        -compare-9-arg2
PushD        $global-memory-block
PushI        54
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
PushI        69
Add                                    %% greater_equal
Label        -compare-10-arg1
PushD        $global-memory-block
PushI        46
Add                                    %% flt1
LoadF
Label        -compare-10-arg2
PushD        $global-memory-block
PushI        54
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
PushI        64
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
PushD        $print-format-tab
Printf
PushD        $global-memory-block
PushI        65
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
PushI        66
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
PushD        $print-format-tab
Printf
PushD        $global-memory-block
PushI        67
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
PushI        68
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
PushD        $print-format-tab
Printf
PushD        $global-memory-block
PushI        69
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
PushI        70
Add                                    %% cmp1
Label        -compare-17-arg1
PushD        $global-memory-block
PushI        44
Add                                    %% char1
LoadC
Label        -compare-17-arg2
PushD        $global-memory-block
PushI        44
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
PushI        71
Add                                    %% cmp2
Label        -compare-18-arg1
PushD        $global-memory-block
PushI        44
Add                                    %% char1
LoadC
Label        -compare-18-arg2
PushD        $global-memory-block
PushI        45
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
PushD        $global-memory-block
PushI        72
Add                                    %% myStr
DLabel       -stringConstant-19-myStr
DataC        72                        %% "Hello world!"
DataC        101
DataC        108
DataC        108
DataC        111
DataC        32
DataC        119
DataC        111
DataC        114
DataC        108
DataC        100
DataC        33
DataC        0
PushD        -stringConstant-19-myStr
StoreI
PushD        $global-memory-block
PushI        72
Add                                    %% myStr
LoadI
PushD        $print-format-string
Printf
PushD        $print-format-newline
Printf
PushD        $global-memory-block
PushI        72
Add                                    %% myStr
DLabel       -stringConstant-20-myStr
DataC        67                        %% "CMPT 379: Assignment 1 Complete!"
DataC        77
DataC        80
DataC        84
DataC        32
DataC        51
DataC        55
DataC        57
DataC        58
DataC        32
DataC        65
DataC        115
DataC        115
DataC        105
DataC        103
DataC        110
DataC        109
DataC        101
DataC        110
DataC        116
DataC        32
DataC        49
DataC        32
DataC        67
DataC        111
DataC        109
DataC        112
DataC        108
DataC        101
DataC        116
DataC        101
DataC        33
DataC        0
PushD        -stringConstant-20-myStr
StoreI
PushD        $global-memory-block
PushI        76
Add                                    %% otherString
DLabel       -stringConstant-21-otherString
DataC        72                        %% "Hello world!"
DataC        101
DataC        108
DataC        108
DataC        111
DataC        32
DataC        119
DataC        111
DataC        114
DataC        108
DataC        100
DataC        33
DataC        0
PushD        -stringConstant-21-otherString
StoreI
PushD        $global-memory-block
PushI        72
Add                                    %% myStr
LoadI
PushD        $print-format-string
Printf
PushD        $print-format-newline
Printf
Halt
