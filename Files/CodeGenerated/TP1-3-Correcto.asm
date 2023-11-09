.586
.model flat, stdcall
option casemap :none
include \masm32\include\windows.inc
include \masm32\include\kernel32.inc
include \masm32\include\user32.inc
includelib \masm32\lib\kernel32.lib
includelib \masm32\lib\user32.lib
.data
     @aux6 dw ?
     @aux5 dw ?
     @aux4 dw ?
     41c1de0e-6c4b-493e-82c6-0b6e559dc0b5 db "HOLA IF", 0
     @aux9 dw ?
     @aux8 dw ?
     _c.programa1 dw ?
     ae5eb334-8101-4ec0-8d42-f535deaaf208 db "HOLA WHILE", 0
     @aux3 dw ?
     @aux2 dw ?
     @aux1 dw ?
     @aux11 dw ?
     @aux12 db "null", 0
     _d.programa1 dw ?
     db613010-b790-4300-be5e-e9ce7f888160 db "HOLA ELSE", 0
     @aux10 dw ?
     _b.programa1 dw ?
     @aux19 dw ?
     @aux15 db "null", 0
     @aux16 dw ?
     @aux18 db "null", 0
     _a.programa1 dw ?
.code
start:
     MOV eax, 1
     MOV _a.programa1, eax 

     MOV eax, 2
     MOV _b.programa1, eax 

     MOV eax, 4
     MOV _c.programa1, eax 

     MOV eax, 2
     MOV _d.programa1, eax 

Label_12
     MOV eax, _a.programa1
     ADD eax, _b.programa1
     MOV @aux5, eax 

     MOV eax, _c.programa1
     SUB eax, _d.programa1
     MOV @aux6, eax 

     CMP @aux5, @aux6
     JL Label_36

     MOV eax, _b.programa1
     ADD eax, _c.programa1
     MOV @aux8, eax 

     MOV eax, @aux8
     MOV _a.programa1, eax 

     MOV eax, _d.programa1
     SUB eax, 3
     MOV @aux10, eax 

     MOV eax, @aux10
     MOV _c.programa1, eax 

     invoke MessageBox, NULL, addr ae5eb334-8101-4ec0-8d42-f535deaaf208 , addr Print, MB_OK
     JMP Label_12

Label_36
     CMP a.programa1, b.programa1
     JG Label_49

     invoke MessageBox, NULL, addr 41c1de0e-6c4b-493e-82c6-0b6e559dc0b5 , addr Print, MB_OK
     MOV eax, -54678
     MOV _d.programa1, eax 

     JMP Label_55

Label_49
     invoke MessageBox, NULL, addr db613010-b790-4300-be5e-e9ce7f888160 , addr Print, MB_OK
     MOV eax, 5467
     MOV _d.programa1, eax 

Label_55
     invoke ExitProcess, 0
end start
