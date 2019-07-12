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
 * ASCII码
 * Author: CK
 * Date: 2016/2/14
 */
public class ASCII {
    /**
     * ASCII码对应表
     */
    public static final String[] ASCII = {
            "NUL",    /* ASCII  0   0x00 "Null" */
            "SOH",    /* ASCII  1   0x01 "Start of Heading" */
            "STX",    /* ASCII  2   0x02 "Start of Text " */
            "ETX",    /* ASCII  3   0x03 "End of Text" */
            "EOT",    /* ASCII  4   0x04 "End of Transmission" */
            "ENQ",    /* ASCII  5   0x05 "Enquiry" */
            "ACK",    /* ASCII  6   0x06 "Acknowledge" */
            "BEL",    /* ASCII  7   0x07 "Bell" */
            "BS",     /* ASCII  8   0x08 "Backspace" */
            "HT",     /* ASCII  9   0x09 "Horizontal Tabulation" */
            "LF",     /* ASCII  10  0x0A "Line Feed" */
            "VT",     /* ASCII  11  0x0B "Vertical Tabulation" */
            "FF",     /* ASCII  12  0x0C "Form Feed" */
            "CR",     /* ASCII  13  0x0D "Carriage Return" */
            "SO",     /* ASCII  14  0x0E "Shift Out" */
            "SI",     /* ASCII  15  0x0F "Shift In" */
            "DLE",    /* ASCII  16  0x10 "Data Link Escape" */
            "DC1",    /* ASCII  17  0x11 "Device Control 1" */
            "DC2",    /* ASCII  18  0x12 "Device Control 2" */
            "DC3",    /* ASCII  19  0x13 "Device Control 3" */
            "DC4",    /* ASCII  20  0x14 "Device Control 4" */
            "NAK",    /* ASCII  21  0x15 "Negative Acknowledge" */
            "SYN",    /* ASCII  22  0x16 "Synchronous Idle" */
            "ETB",    /* ASCII  23  0x17 "End of Transmission Block" */
            "CAN",    /* ASCII  24  0x18 "Cancel" */
            "EM",     /* ASCII  25  0x19 "End of Medium" */
            "SUB",    /* ASCII  26  0x1A "Substitute" */
            "ESC",    /* ASCII  27  0x1B "Escape" */
            "FS",     /* ASCII  28  0x1C "File Separator" */
            "GS",     /* ASCII  29  0x1D "Group Separator" */
            "RS",     /* ASCII  30  0x1E "Record Separator" */
            "US",     /* ASCII  31  0x1F "Unit Separator" */
            "SP",     /* ASCII  32  0x20 "Space" */
            "!",      /* ASCII  33  0x21 "Exclamation Point" */
            "\"",     /* ASCII  34  0x22 "Quotation Mark" */
            "#",      /* ASCII  35  0x23 "Number Sign, pound" */
            "$",      /* ASCII  36  0x24 "Dollar Sign" */
            "%",      /* ASCII  37  0x25 "Percent" */
            "&",      /* ASCII  38  0x26 "Ampersand" */
            "'",      /* ASCII  39  0x27 "Apostrophe, Prime" */
            "(",      /* ASCII  40  0x28 "Left Parenthesis" */
            ")",      /* ASCII  41  0x29 "Right Parenthesis" */
            "*",      /* ASCII  42  0x2A "Asterisk, star" */
            "+",      /* ASCII  43  0x2B "Plus Sign" */
            ",",      /* ASCII  44  0x2C "Comma" */
            "-",      /* ASCII  45  0x2D "Hyphen Minus Sign" */
            ".",      /* ASCII  46  0x2E "Period, Decimal Point, dot" */
            "/",      /* ASCII  47  0x2F "Slash, Virgule" */
            "0",      /* ASCII  48  0x30 "0" */
            "1",      /* ASCII  49  0x31 "1" */
            "2",      /* ASCII  50  0x32 "2" */
            "3",      /* ASCII  51  0x33 "3" */
            "4",      /* ASCII  52  0x34 "4" */
            "5",      /* ASCII  53  0x35 "5" */
            "6",      /* ASCII  54  0x36 "6" */
            "7",      /* ASCII  55  0x37 "7" */
            "8",      /* ASCII  56  0x38 "8" */
            "9",      /* ASCII  57  0x39 "9" */
            ":",      /* ASCII  58  0x3A "Colon" */
            ";",      /* ASCII  59  0x3B "Semicolon" */
            "<",      /* ASCII  60  0x3C "Less-than Sign" */
            "=",      /* ASCII  61  0x3D "Equal Sign" */
            ">",      /* ASCII  62  0x3E "Greater-than Sign" */
            "?",      /* ASCII  63  0x3F "Question Mark" */
            "@",      /* ASCII  64  0x40 "At Sign" */
            "A",      /* ASCII  65  0x41 "A" */
            "B",      /* ASCII  66  0x42 "B" */
            "C",      /* ASCII  67  0x43 "C" */
            "D",      /* ASCII  68  0x44 "D" */
            "E",      /* ASCII  69  0x45 "E" */
            "F",      /* ASCII  70  0x46 "F" */
            "G",      /* ASCII  71  0x47 "G" */
            "H",      /* ASCII  72  0x48 "H" */
            "I",      /* ASCII  73  0x49 "I" */
            "J",      /* ASCII  74  0x4A "J" */
            "K",      /* ASCII  75  0x4B "K" */
            "L",      /* ASCII  76  0x4C "L" */
            "M",      /* ASCII  77  0x4D "M" */
            "N",      /* ASCII  78  0x4E "N" */
            "O",      /* ASCII  79  0x4F "O" */
            "P",      /* ASCII  80  0x50 "P" */
            "Q",      /* ASCII  81  0x51 "Q" */
            "R",      /* ASCII  82  0x52 "R" */
            "S",      /* ASCII  83  0x53 "S" */
            "T",      /* ASCII  84  0x54 "T" */
            "U",      /* ASCII  85  0x55 "U" */
            "V",      /* ASCII  86  0x56 "V" */
            "W",      /* ASCII  87  0x57 "W" */
            "X",      /* ASCII  88  0x58 "X" */
            "Y",      /* ASCII  89  0x59 "Y" */
            "Z",      /* ASCII  90  0x5A "Z" */
            "[",      /* ASCII  91  0x5B "Opening Bracket" */
            "\\",     /* ASCII  92  0x5C "Reverse Slant" */
            "]",      /* ASCII  93  0x5D "Closing Bracket" */
            "^",      /* ASCII  94  0x5E "Circumflex, Caret" */
            "_",      /* ASCII  95  0x5F "Underline, Underscore" */
            "`",      /* ASCII  96  0x60 "Grave Accent" */
            "a",      /* ASCII  97  0x61 "a" */
            "b",      /* ASCII  98  0x62 "b" */
            "c",      /* ASCII  99  0x63 "c" */
            "d",      /* ASCII  100 0x64 "d" */
            "e",      /* ASCII  101 0x65 "e" */
            "f",      /* ASCII  102 0x66 "f" */
            "g",      /* ASCII  103 0x67 "g" */
            "h",      /* ASCII  104 0x68 "h" */
            "i",      /* ASCII  105 0x69 "i" */
            "j",      /* ASCII  106 0x6A "j" */
            "k",      /* ASCII  107 0x6B "k" */
            "l",      /* ASCII  108 0x6C "l" */
            "m",      /* ASCII  109 0x6D "m" */
            "n",      /* ASCII  110 0x6E "n" */
            "o",      /* ASCII  111 0x6F "o" */
            "p",      /* ASCII  112 0x70 "p" */
            "q",      /* ASCII  113 0x71 "q" */
            "r",      /* ASCII  114 0x72 "r" */
            "s",      /* ASCII  115 0x73 "s" */
            "t",      /* ASCII  116 0x74 "t" */
            "u",      /* ASCII  117 0x75 "u" */
            "v",      /* ASCII  118 0x76 "v" */
            "w",      /* ASCII  119 0x77 "w" */
            "x",      /* ASCII  120 0x78 "x" */
            "y",      /* ASCII  121 0x79 "y" */
            "z",      /* ASCII  122 0x7A "z" */
            "{",      /* ASCII  123 0x7B "Opening Brace" */
            "|",      /* ASCII  124 0x7C "Vertical Line" */
            "}",      /* ASCII  125 0x7D "Closing Brace" */
            "~",      /* ASCII  126 0x7E "Tilde" */
            "DEL",    /* ASCII  127 0x7F "Delete" */
            "",       /* ASCII  128 0x80 "Reserved" */
            "",       /* ASCII  129 0x81 "Reserved" */
            "",       /* ASCII  130 0x82 "Reserved" */
            "",       /* ASCII  131 0x83 "Reserved" */
            "IND",    /* ASCII  132 0x84 "Index" */
            "NEL",    /* ASCII  133 0x85 "Next Line" */
            "SSA",    /* ASCII  134 0x86 "Start of Selected Area" */
            "ESA",    /* ASCII  135 0x87 "End of Selected Area" */
            "HTS",    /* ASCII  136 0x88 "Horizontal Tab Set" */
            "HTJ",    /* ASCII  137 0x89 "Horizontal Tabwith Just" */
            "VTS",    /* ASCII  138 0x8A "Vertical Tab Set" */
            "PLD",    /* ASCII  139 0x8B "Partial Line Down" */
            "PLU",    /* ASCII  140 0x8C "Partial Line Up" */
            "RI",     /* ASCII  141 0x8D "Reverse Index" */
            "SS2",    /* ASCII  142 0x8E "Single Shift Two" */
            "SS3",    /* ASCII  143 0x8F "Single Shift Three" */
            "DCS",    /* ASCII  144 0x90 "Device Control String" */
            "PU1",    /* ASCII  145 0x91 "Private Use One" */
            "PU2",    /* ASCII  146 0x92 "Private Use Two" */
            "STS",    /* ASCII  147 0x93 "Set Transmit State" */
            "CCH",    /* ASCII  148 0x94 "Cancel Character" */
            "MW",     /* ASCII  149 0x95 "Message Waiting" */
            "SPA",    /* ASCII  150 0x96 "Start of Protected Area" */
            "EPA",    /* ASCII  151 0x97 "End of Protected Area" */
            "",       /* ASCII  152 0x98 "Reserved" */
            "",       /* ASCII  153 0x99 "Reserved" */
            "",       /* ASCII  154 0x9A "Reserved" */
            "CSI",    /* ASCII  155 0x9B "Control Sequence Introducer" */
            "ST",     /* ASCII  156 0x9C "String Terminator" */
            "OSC",    /* ASCII  157 0x9D "Operating System Command" */
            "PM",     /* ASCII  158 0x9E "Privacy Message" */
            "APC",    /* ASCII  159 0x9F "Application Program Command" */
            "nbsp",   /* ASCII  160 0xA0 "non breaking space" */
            "iexcl",  /* ASCII  161 0xA1 "inverted exclamation" */
            "cent",   /* ASCII  162 0xA2 "Cent Sign" */
            "pound",  /* ASCII  163 0xA3 "pound" */
            "curren", /* ASCII  164 0xA4 "currency" */
            "yen",    /* ASCII  165 0xA5 "yen" */
            "brvbar", /* ASCII  166 0xA6 "breve bar" */
            "sect",   /* ASCII  167 0xA7 "section" */
            "uml",    /* ASCII  168 0xA8 "umlaut" */
            "copy",   /* ASCII  169 0xA9 "copyright" */
            "ordf",   /* ASCII  170 0xAA "feminine ordinal" */
            "laquo",  /* ASCII  171 0xAB "left angle quote" */
            "not",    /* ASCII  172 0xAC "Logical NOT" */
            "shy",    /* ASCII  173 0xAD "soft hyphen" */
            "reg",    /* ASCII  174 0xAE "registered trademark" */
            "macr",   /* ASCII  175 0xAF "macron" */
            "deg",    /* ASCII  176 0xB0 "degree" */
            "plusmn", /* ASCII  177 0xB1 "plusminus" */
            "sup2",   /* ASCII  178 0xB2 "superscript 2" */
            "sup3",   /* ASCII  179 0xB3 "superscript 3" */
            "acute",  /* ASCII  180 0xB4 "acute" */
            "micro",  /* ASCII  181 0xB5 "micro" */
            "para",   /* ASCII  182 0xB6 "paragraph" */
            "middot", /* ASCII  183 0xB7 "mid dot" */
            "cedil",  /* ASCII  184 0xB8 "cedilla" */
            "sup1",   /* ASCII  185 0xB9 "superscript 1" */
            "ordm",   /* ASCII  186 0xBA "masculine ordinal" */
            "raquo",  /* ASCII  187 0xBB "right angle quote" */
            "frac14", /* ASCII  188 0xBC "1/4" */
            "frac12", /* ASCII  189 0xBD "1/2" */
            "frac34", /* ASCII  190 0xBE "3/4" */
            "iquest", /* ASCII  191 0xBF "inverted ?" */
            "Agrave", /* ASCII  192 0xC0 "Agrave"  */
            "Aacute", /* ASCII  193 0xC1 "Aacute"  */
            "Acirc",  /* ASCII  194 0xC2 "Acirc"   */
            "Atilde", /* ASCII  195 0xC3 "Atilde"  */
            "Auml",   /* ASCII  196 0xC4 "Auml"    */
            "Aring",  /* ASCII  197 0xC5 "Aring"   */
            "AElig",  /* ASCII  198 0xC6 "AElig"   */
            "Ccedil", /* ASCII  199 0xC7 "Ccedil"  */
            "Egrave", /* ASCII  200 0xC8 "Egrave"  */
            "Eacute", /* ASCII  201 0xC9 "Eacute"  */
            "Ecirc",  /* ASCII  202 0xCA "Ecirc"   */
            "Euml",   /* ASCII  203 0xCB "Euml"    */
            "Igrave", /* ASCII  204 0xCC "Igrave"  */
            "Iacute", /* ASCII  205 0xCD "Iacute"  */
            "Icirc",  /* ASCII  206 0xCE "Icirc"   */
            "Iuml",   /* ASCII  207 0xCF "Iuml"    */
            "ETH",    /* ASCII  208 0xD0 "ETH"     */
            "Ntilde", /* ASCII  209 0xD1 "Ntilde"  */
            "Ograve", /* ASCII  210 0xD2 "Ograve"  */
            "Oacute", /* ASCII  211 0xD3 "Oacute"  */
            "Ocirc",  /* ASCII  212 0xD4 "Ocirc"   */
            "Otilde", /* ASCII  213 0xD5 "Otilde"  */
            "Ouml",   /* ASCII  214 0xD6 "Ouml"    */
            "times",  /* ASCII  215 0xD7 "times"   */
            "Oslash", /* ASCII  216 0xD8 "Oslash"  */
            "Ugrave", /* ASCII  217 0xD9 "Ugrave"  */
            "Uacute", /* ASCII  218 0xDA "Uacute"  */
            "Ucirc",  /* ASCII  219 0xDB "Ucirc"   */
            "Uuml",   /* ASCII  220 0xDC "Uuml"    */
            "Yacute", /* ASCII  221 0xDD "Yacute"  */
            "THORN",  /* ASCII  222 0xDE "THORN"   */
            "szlig",  /* ASCII  223 0xDF "szlig"   */
            "agrave", /* ASCII  224 0xE0 "agrave"  */
            "aacute", /* ASCII  225 0xE1 "aacute"  */
            "acirc",  /* ASCII  226 0xE2 "acirc"   */
            "atilde", /* ASCII  227 0xE3 "atilde"  */
            "auml",   /* ASCII  228 0xE4 "auml"    */
            "aring",  /* ASCII  229 0xE5 "aring"   */
            "aelig",  /* ASCII  230 0xE6 "aelig"   */
            "ccedil", /* ASCII  231 0xE7 "ccedil"  */
            "egrave", /* ASCII  232 0xE8 "egrave"  */
            "eacute", /* ASCII  233 0xE9 "eacute"  */
            "ecirc",  /* ASCII  234 0xEA "ecirc"   */
            "euml",   /* ASCII  235 0xEB "euml"    */
            "igrave", /* ASCII  236 0xEC "igrave"  */
            "iacute", /* ASCII  237 0xED "iacute"  */
            "icirc",  /* ASCII  238 0xEE "icirc"   */
            "iuml",   /* ASCII  239 0xEF "iuml"    */
            "eth",    /* ASCII  240 0xF0 "eth"     */
            "ntilde", /* ASCII  241 0xF1 "ntilde"  */
            "ograve", /* ASCII  242 0xF2 "ograve"  */
            "oacute", /* ASCII  243 0xF3 "oacute"  */
            "ocirc",  /* ASCII  244 0xF4 "ocirc"   */
            "otilde", /* ASCII  245 0xF5 "otilde"  */
            "ouml",   /* ASCII  246 0xF6 "ouml"    */
            "divide", /* ASCII  247 0xF7 "divide"  */
            "oslash", /* ASCII  248 0xF8 "oslash"  */
            "ugrave", /* ASCII  249 0xF9 "ugrave"  */
            "uacute", /* ASCII  250 0xFA "uacute"  */
            "ucirc",  /* ASCII  251 0xFB "ucirc"   */
            "uuml",   /* ASCII  252 0xFC "uuml"    */
            "yacute", /* ASCII  253 0xFD "yacute"  */
            "thorn",  /* ASCII  254 0xFE "thorn"   */
            "yuml"    /* ASCII  255 0xFF "yuml"    */
    };

    /**
     * ASCII转换成EBCDIC 转换表
     */
    public static byte[] AToE = {
    /*0  */ 0, 1, 2, 3, 55, 45, 46, 47, 22, 5, 37, 11, 12, 13, 14, 15,
    /*16 */ 16, 17, 18, 63, 60, 61, 50, 38, 24, 25, 63, 39, 28, 29, 30, 31,
    /*32 */ 64, 90, 127, 123, 91, 108, 80, 125, 77, 93, 92, 78, 107, 96, 75, 97,
    /*48 */ -16, -15, -14, -13, -12, -11, -10, -9, -8, -7, 122, 94, 76, 126, 110, 111,
    /*64 */ 124, -63, -62, -61, -60, -59, -58, -57, -56, -55, -47, -46, -45, -44, -43, -42,
    /*80 */ -41, -40, -39, -30, -29, -28, -27, -26, -25, -24, -23, 63, 63, 63, 63, 109,
    /*96 */ -71, -127, -126, -125, -124, -123, -122, -121, -120, -119, -111, -110, -109, -108, -107, -106,
    /*112*/ -105, -104, -103, -94, -93, -92, -91, -90, -89, -88, -87, 63, 79, 63, 63, 7,
    /*128*/ 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63,
    /*144*/ 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63,
    /*160*/ 64, 63, 74, 123, 63, 63, 63, 63, 63, 63, 63, 63, 95, 96, 63, 63,
    /*176*/ 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63,
    /*192*/ 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63,
    /*208*/ 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63,
    /*224*/ 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63,
    /*240*/ 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63
    };
}
