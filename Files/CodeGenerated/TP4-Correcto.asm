.586
.model flat, stdcall
option casemap :none
include \masm32\include\windows.inc
include \masm32\include\kernel32.inc
include \masm32\include\user32.inc
includelib \masm32\lib\kernel32.lib
includelib \masm32\lib\user32.lib
.data
     b.programa1 dw ?
     a.programa1 dw ?
     @aux2 dw ?
     @aux1 dw ?
.code
start:
     MOV eax, _a.programa1
     MOV _1, eax 

     MOV eax, _b.programa1
     MOV _2, eax 

     invoke ExitProcess, 0
end start
