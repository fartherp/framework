/**
 *    Copyright (c) 2014-2019 CK.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package com.github.fartherp.framework.common.code;

/**
 * EBCDIC码
 * @author CK
 * @date 2016/2/14
 */
public class EBCDIC {
    /**
     * EBCDIC码对应表
     */
    public static final String[] EBCDIC = {
            "NUL",    /* EBCDIC  0   0x00  "Null" */
            "SOH",    /* EBCDIC  1   0x01  "Start of Heading" */
            "STX",    /* EBCDIC  2   0x02  "Start of Text" */
            "ETX",    /* EBCDIC  3   0x03  "End of Text" */
            "PF",     /* EBCDIC  4   0x04  "Punch Off" */
            "HT",     /* EBCDIC  5   0x05  "Horizontal Tab" */
            "LC",     /* EBCDIC  6   0x06  "Lower Case" */
            "DEL",    /* EBCDIC  7   0x07  "Delete" */
            "",       /* EBCDIC  8   0x08  "" */
            "",       /* EBCDIC  9   0x09  "" */
            "SMM",    /* EBCDIC  10  0x0A  "Start of Manual Message" */
            "VT",     /* EBCDIC  11  0x0B  "Vertical Tab" */
            "FF",     /* EBCDIC  12  0x0C  "Form Feed" */
            "CR",     /* EBCDIC  13  0x0D  "Carriage Return" */
            "SO",     /* EBCDIC  14  0x0E  "Shift Out" */
            "SI",     /* EBCDIC  15  0x0F  "Shift In" */
            "DLE",    /* EBCDIC  16  0x10  "Data Link Escape" */
            "DC1",    /* EBCDIC  17  0x11  "Device Control 1" */
            "DC2",    /* EBCDIC  18  0x12  "Device Control 2" */
            "TM",     /* EBCDIC  19  0x13  "Tape Mark" */
            "RES",    /* EBCDIC  20  0x14  "Restore" */
            "NL",     /* EBCDIC  21  0x15  "New Line" */
            "BS",     /* EBCDIC  22  0x16  "Backspace" */
            "IL",     /* EBCDIC  23  0x17  "Idle" */
            "CAN",    /* EBCDIC  24  0x18  "Cancel" */
            "EM",     /* EBCDIC  25  0x19  "End of Medium" */
            "CC",     /* EBCDIC  26  0x1A  "Cursor Control" */
            "CU1",    /* EBCDIC  27  0x1B  "Customer Use 1" */
            "IFS",    /* EBCDIC  28  0x1C  "Interchange File Separator" */
            "IGS",    /* EBCDIC  29  0x1D  "Interchange Group Separator" */
            "IRS",    /* EBCDIC  30  0x1E  "Interchange Record Separator" */
            "IUS",    /* EBCDIC  31  0x1F  "Interchange Unit Separator" */
            "DS",     /* EBCDIC  32  0x20  "Digit Select" */
            "SOS",    /* EBCDIC  33  0x21  "Start of Significance" */
            "FS",     /* EBCDIC  34  0x22  "Field Separator" */
            "",       /* EBCDIC  35  0x23  "" */
            "BYP",    /* EBCDIC  36  0x24  "Bypass" */
            "LF",     /* EBCDIC  37  0x25  "Line Feed" */
            "ETB",    /* EBCDIC  38  0x26  "End of Transmission Block" */
            "ESC",    /* EBCDIC  39  0x27  "Escape" */
            "",       /* EBCDIC  40  0x28  "" */
            "",       /* EBCDIC  41  0x29  "" */
            "SM",     /* EBCDIC  42  0x2A  "Set Mode" */
            "CU2",    /* EBCDIC  43  0x2B  "Customer Use 2" */
            "",       /* EBCDIC  44  0x2C  "" */
            "ENQ",    /* EBCDIC  45  0x2D  "Enquiry" */
            "ACK",    /* EBCDIC  46  0x2E  "Acknowledge" */
            "BEL",    /* EBCDIC  47  0x2F  "Bell" */
            "",       /* EBCDIC  48  0x30  "" */
            "",       /* EBCDIC  49  0x31  "" */
            "SYN",    /* EBCDIC  50  0x32  "Synchronous Idle" */
            "",       /* EBCDIC  51  0x33  "" */
            "PN",     /* EBCDIC  52  0x34  "Punch On" */
            "RS",     /* EBCDIC  53  0x35  "Reader Stop" */
            "UC",     /* EBCDIC  54  0x36  "Upper Case" */
            "EOT",    /* EBCDIC  55  0x37  "End of Transmission" */
            "",       /* EBCDIC  56  0x38  "" */
            "",       /* EBCDIC  57  0x39  "" */
            "",       /* EBCDIC  58  0x3A  "" */
            "CU3",    /* EBCDIC  59  0x3B  "Customer Use 3" */
            "DC4",    /* EBCDIC  60  0x3C  "Device Control 4" */
            "NAK",    /* EBCDIC  61  0x3D  "Negative Acknowledge" */
            "",       /* EBCDIC  62  0x3E  "" */
            "SUB",    /* EBCDIC  63  0x3F  "Substitute" */
            "SP",     /* EBCDIC  64  0x40  "Space" */
            "",       /* EBCDIC  65  0x41  "" */
            "",       /* EBCDIC  66  0x42  "" */
            "",       /* EBCDIC  67  0x43  "" */
            "",       /* EBCDIC  68  0x44  "" */
            "",       /* EBCDIC  69  0x45  "" */
            "",       /* EBCDIC  70  0x46  "" */
            "",       /* EBCDIC  71  0x47  "" */
            "",       /* EBCDIC  72  0x48  "" */
            "",       /* EBCDIC  73  0x49  "" */
            "cent",   /* EBCDIC  74  0x4A  "Cent Sign" */
            ".",      /* EBCDIC  75  0x4B  "Period, Decimal Point, dot" */
            "<",      /* EBCDIC  76  0x4C  "Less-than Sign" */
            "(",      /* EBCDIC  77  0x4D  "Left Parenthesis" */
            "+",      /* EBCDIC  78  0x4E  "Plus Sign" */
            "|",      /* EBCDIC  79  0x4F  "Logical OR" */
            "&",      /* EBCDIC  80  0x50  "Ampersand" */
            "",       /* EBCDIC  81  0x51  "" */
            "",       /* EBCDIC  82  0x52  "" */
            "",       /* EBCDIC  83  0x53  "" */
            "",       /* EBCDIC  84  0x54  "" */
            "",       /* EBCDIC  85  0x55  "" */
            "",       /* EBCDIC  86  0x56  "" */
            "",       /* EBCDIC  87  0x57  "" */
            "",       /* EBCDIC  88  0x58  "" */
            "",       /* EBCDIC  89  0x59  "" */
            "!",      /* EBCDIC  90  0x5A  "Exclamation Point" */
            "$",      /* EBCDIC  91  0x5B  "Dollar Sign" */
            "*",      /* EBCDIC  92  0x5C  "Asterisk, star" */
            ")",      /* EBCDIC  93  0x5D  "Right Parenthesis" */
            ";",      /* EBCDIC  94  0x5E  "Semicolon" */
            "not",    /* EBCDIC  95  0x5F  "Logical NOT" */
            "-",      /* EBCDIC  96  0x60  "Hyphen, Minus Sign" */
            "/",      /* EBCDIC  97  0x61  "slash" */
            "",       /* EBCDIC  98  0x62  "" */
            "",       /* EBCDIC  99  0x63  "" */
            "",       /* EBCDIC  100 0x64  "" */
            "",       /* EBCDIC  101 0x65  "" */
            "",       /* EBCDIC  102 0x66  "" */
            "",       /* EBCDIC  103 0x67  "" */
            "",       /* EBCDIC  104 0x68  "" */
            "",       /* EBCDIC  105 0x69  "" */
            "",       /* EBCDIC  106 0x6A  "" */
            ",",      /* EBCDIC  107 0x6B  "Comma" */
            "%",      /* EBCDIC  108 0x6C  "Percent" */
            "_",      /* EBCDIC  109 0x6D  "Underline, Underscore" */
            ">",      /* EBCDIC  110 0x6E  "Greater-than Sign" */
            "?",      /* EBCDIC  111 0x6F  "Question Mark" */
            "",       /* EBCDIC  112 0x70  "" */
            "",       /* EBCDIC  113 0x71  "" */
            "",       /* EBCDIC  114 0x72  "" */
            "",       /* EBCDIC  115 0x73  "" */
            "",       /* EBCDIC  116 0x74  "" */
            "",       /* EBCDIC  117 0x75  "" */
            "",       /* EBCDIC  118 0x76  "" */
            "",       /* EBCDIC  119 0x77  "" */
            "",       /* EBCDIC  120 0x78  "" */
            "",       /* EBCDIC  121 0x79  "" */
            ":",      /* EBCDIC  122 0x7A  "Colon" */
            "#",      /* EBCDIC  123 0x7B  "Number Sign" */
            "@",      /* EBCDIC  124 0x7C  "At Sign" */
            "'",      /* EBCDIC  125 0x7D  "Apostrophe, Prime" */
            "=",      /* EBCDIC  126 0x7E  "Equal Sign" */
            "\"",     /* EBCDIC  127 0x7F  "Quotation Mark" */
            "",       /* EBCDIC  128 0x80  "" */
            "a",      /* EBCDIC  129 0x81  "a" */
            "b",      /* EBCDIC  130 0x82  "b" */
            "c",      /* EBCDIC  131 0x83  "c" */
            "d",      /* EBCDIC  132 0x84  "d" */
            "e",      /* EBCDIC  133 0x85  "e" */
            "f",      /* EBCDIC  134 0x86  "f" */
            "g",      /* EBCDIC  135 0x87  "g" */
            "h",      /* EBCDIC  136 0x88  "h" */
            "i",      /* EBCDIC  137 0x89  "i" */
            "",       /* EBCDIC  138 0x8A  "" */
            "",       /* EBCDIC  139 0x8B  "" */
            "",       /* EBCDIC  140 0x8C  "" */
            "",       /* EBCDIC  141 0x8D  "" */
            "",       /* EBCDIC  142 0x8E  "" */
            "",       /* EBCDIC  143 0x8F  "" */
            "",       /* EBCDIC  144 0x90  "" */
            "j",      /* EBCDIC  145 0x91  "j" */
            "k",      /* EBCDIC  146 0x92  "k" */
            "l",      /* EBCDIC  147 0x93  "l" */
            "m",      /* EBCDIC  148 0x94  "m" */
            "n",      /* EBCDIC  149 0x95  "n" */
            "o",      /* EBCDIC  150 0x96  "o" */
            "p",      /* EBCDIC  151 0x97  "p" */
            "q",      /* EBCDIC  152 0x98  "q" */
            "r",      /* EBCDIC  153 0x99  "r" */
            "",       /* EBCDIC  154 0x9A  "" */
            "",       /* EBCDIC  155 0x9B  "" */
            "",       /* EBCDIC  156 0x9C  "" */
            "",       /* EBCDIC  157 0x9D  "" */
            "",       /* EBCDIC  158 0x9E  "" */
            "",       /* EBCDIC  159 0x9F  "" */
            "",       /* EBCDIC  160 0xA0  "" */
            "",       /* EBCDIC  161 0xA1  "" */
            "s",      /* EBCDIC  162 0xA2  "s" */
            "t",      /* EBCDIC  163 0xA3  "t" */
            "u",      /* EBCDIC  164 0xA4  "u" */
            "v",      /* EBCDIC  165 0xA5  "v" */
            "w",      /* EBCDIC  166 0xA6  "w" */
            "x",      /* EBCDIC  167 0xA7  "x" */
            "y",      /* EBCDIC  168 0xA8  "y" */
            "z",      /* EBCDIC  169 0xA9  "z" */
            "",       /* EBCDIC  170 0xAA  "" */
            "",       /* EBCDIC  171 0xAB  "" */
            "",       /* EBCDIC  172 0xAC  "" */
            "",       /* EBCDIC  173 0xAD  "" */
            "",       /* EBCDIC  174 0xAE  "" */
            "",       /* EBCDIC  175 0xAF  "" */
            "",       /* EBCDIC  176 0xB0  "" */
            "",       /* EBCDIC  177 0xB1  "" */
            "",       /* EBCDIC  178 0xB2  "" */
            "",       /* EBCDIC  179 0xB3  "" */
            "",       /* EBCDIC  180 0xB4  "" */
            "",       /* EBCDIC  181 0xB5  "" */
            "",       /* EBCDIC  182 0xB6  "" */
            "",       /* EBCDIC  183 0xB7  "" */
            "",       /* EBCDIC  184 0xB8  "" */
            "`",      /* EBCDIC  185 0xB9  "Grave Accent" */
            "",       /* EBCDIC  186 0xBA  "" */
            "",       /* EBCDIC  187 0xBB  "" */
            "",       /* EBCDIC  188 0xBC  "" */
            "",       /* EBCDIC  189 0xBD  "" */
            "",       /* EBCDIC  190 0xBE  "" */
            "",       /* EBCDIC  191 0xBF  "" */
            "",       /* EBCDIC  192 0xC0  "" */
            "A",      /* EBCDIC  193 0xC1  "A" */
            "B",      /* EBCDIC  194 0xC2  "B" */
            "C",      /* EBCDIC  195 0xC3  "C" */
            "D",      /* EBCDIC  196 0xC4  "D" */
            "E",      /* EBCDIC  197 0xC5  "E" */
            "F",      /* EBCDIC  198 0xC6  "F" */
            "G",      /* EBCDIC  199 0xC7  "G" */
            "H",      /* EBCDIC  200 0xC8  "H" */
            "I",      /* EBCDIC  201 0xC9  "I" */
            "",       /* EBCDIC  202 0xCA  "" */
            "",       /* EBCDIC  203 0xCB  "" */
            "",       /* EBCDIC  204 0xCC  "" */
            "",       /* EBCDIC  205 0xCD  "" */
            "",       /* EBCDIC  206 0xCE  "" */
            "",       /* EBCDIC  207 0xCF  "" */
            "",       /* EBCDIC  208 0xD0  "" */
            "J",      /* EBCDIC  209 0xD1  "J" */
            "K",      /* EBCDIC  210 0xD2  "K" */
            "L",      /* EBCDIC  211 0xD3  "L" */
            "M",      /* EBCDIC  212 0xD4  "M" */
            "N",      /* EBCDIC  213 0xD5  "N" */
            "O",      /* EBCDIC  214 0xD6  "O" */
            "P",      /* EBCDIC  215 0xD7  "P" */
            "Q",      /* EBCDIC  216 0xD8  "Q" */
            "R",      /* EBCDIC  217 0xD9  "R" */
            "",       /* EBCDIC  218 0xDA  "" */
            "",       /* EBCDIC  219 0xDB  "" */
            "",       /* EBCDIC  220 0xDC  "" */
            "",       /* EBCDIC  221 0xDD  "" */
            "",       /* EBCDIC  222 0xDE  "" */
            "",       /* EBCDIC  223 0xDF  "" */
            "",       /* EBCDIC  224 0xE0  "" */
            "",       /* EBCDIC  225 0xE1  "" */
            "S",      /* EBCDIC  226 0xE2  "S" */
            "T",      /* EBCDIC  227 0xE3  "T" */
            "U",      /* EBCDIC  228 0xE4  "U" */
            "V",      /* EBCDIC  229 0xE5  "V" */
            "W",      /* EBCDIC  230 0xE6  "W" */
            "X",      /* EBCDIC  231 0xE7  "X" */
            "Y",      /* EBCDIC  232 0xE8  "Y" */
            "Z",      /* EBCDIC  233 0xE9  "Z" */
            "",       /* EBCDIC  234 0xEA  "" */
            "",       /* EBCDIC  235 0xEB  "" */
            "",       /* EBCDIC  236 0xEC  "" */
            "",       /* EBCDIC  237 0xED  "" */
            "",       /* EBCDIC  238 0xEE  "" */
            "",       /* EBCDIC  239 0xEF  "" */
            "0",      /* EBCDIC  240 0xF0  "0" */
            "1",      /* EBCDIC  241 0xF1  "1" */
            "2",      /* EBCDIC  242 0xF2  "2" */
            "3",      /* EBCDIC  243 0xF3  "3" */
            "4",      /* EBCDIC  244 0xF4  "4" */
            "5",      /* EBCDIC  245 0xF5  "5" */
            "6",      /* EBCDIC  246 0xF6  "6" */
            "7",      /* EBCDIC  247 0xF7  "7" */
            "8",      /* EBCDIC  248 0xF8  "8" */
            "9",      /* EBCDIC  249 0xF9  "9" */
            "",       /* EBCDIC  250 0xFA  "" */
            "",       /* EBCDIC  251 0xFB  "" */
            "",       /* EBCDIC  252 0xFC  "" */
            "",       /* EBCDIC  253 0xFD  "" */
            "",       /* EBCDIC  254 0xFE  "" */
            ""        /* EBCDIC  255 0xFF  "" */
    };

    /**
     * EBCDIC转换成ASCII 转换表
     */
    public static byte[] EToA = {
       /*0  */ 0, 1, 2, 3, 26, 9, 26, 127, 26, 26, 26, 11, 12, 13, 14, 15,
       /*16 */ 16, 17, 18, 26, 26, 10, 8, 26, 24, 25, 26, 26, 28, 29, 30, 31,
       /*32 */ 26, 26, 28, 26, 26, 10, 23, 27, 26, 26, 26, 26, 26, 5, 6, 7,
       /*48 */ 26, 26, 22, 26, 26, 30, 26, 4, 26, 26, 26, 26, 20, 21, 26, 26,
       /*64 */ 32, 26, 26, 26, 26, 26, 26, 26, 26, 26, -94, 46, 60, 40, 43, 124,
       /*80 */ 38, 26, 26, 26, 26, 26, 26, 26, 26, 26, 33, 36, 42, 41, 59, -84,
       /*96 */ 45, 47, 26, 26, 26, 26, 26, 26, 26, 26, 26, 44, 37, 95, 62, 63,
       /*112*/ 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 58, 35, 64, 39, 61, 34,
       /*128*/ 26, 97, 98, 99, 100, 101, 102, 103, 104, 105, 26, 26, 26, 26, 26, 26,
       /*144*/ 26, 106, 107, 108, 109, 110, 111, 112, 113, 114, 26, 26, 26, 26, 26, 26,
       /*160*/ 26, 26, 115, 116, 117, 118, 119, 120, 121, 122, 26, 26, 26, 26, 26, 26,
       /*176*/ 26, 26, 26, 26, 26, 26, 26, 26, 26, 96, 26, 26, 26, 26, 26, 26,
       /*192*/ 26, 65, 66, 67, 68, 69, 70, 71, 72, 73, 26, 26, 26, 26, 26, 26,
       /*208*/ 26, 74, 75, 76, 77, 78, 79, 80, 81, 82, 26, 26, 26, 26, 26, 26,
       /*224*/ 26, 26, 83, 84, 85, 86, 87, 88, 89, 90, 26, 26, 26, 26, 26, 26,
       /*240*/ 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 26, 26, 26, 26, 26, 26
    };
}
