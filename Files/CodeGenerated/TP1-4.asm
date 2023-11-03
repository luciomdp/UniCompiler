.586
.model flat, stdcall
option casemap :none
include \masm32\include\windows.inc
include \masm32\include\kernel32.inc
include \masm32\include\user32.inc
includelib \masm32\lib\kernel32.lib
includelib \masm32\lib\user32.lib
.data
     b.programa1dw ?
     c.programa1dw ?
     a.programa1dw ?
     @aux2dw ?
     @aux1dw ?
.code
start:
     MOV eax, _c.programa1
     ADD eax, _a.programa1
     MOV @aux1, eax 

     MOV eax, _b.programa1
     MOV _@aux1, eax 

     invoke ExitProcess, 0
end start
