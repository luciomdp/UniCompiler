.586
.model flat, stdcall
option casemap :none
include \masm32\include\windows.inc
include \masm32\include\kernel32.inc
include \masm32\include\user32.inc
includelib \masm32\lib\kernel32.lib
includelib \masm32\lib\user32.lib
.data
     @aux7 dw ?
     @aux6 dw ?
     @aux5 dw ?
     @aux4 dw ?
     c.programa1 dw ?
     @aux2 dw ?
     @aux1 dw ?
     d.programa1 dw ?
     b.programa1 dw ?
     a.programa1 dw ?
.code
start:
Label0
     MOV eax, _a.programa1
     ADD eax, _b.programa1
     MOV @aux1, eax 

     MOV eax, _c.programa1
     SUB eax, _d.programa1
     MOV @aux2, eax 

     CMP @aux1, @aux2
     JG Label22

     MOV eax, _b.programa1
     ADD eax, _c.programa1
     MOV @aux4, eax 

     MOV eax, @aux4
     MOV _a.programa1, eax 

     MOV eax, _d.programa1
     SUB eax, 3
     MOV @aux6, eax 

     MOV eax, @aux6
     MOV _c.programa1, eax 

     JMP Label0

Label22
     invoke ExitProcess, 0
end start
