.586
.model flat, stdcall
option casemap :none
include \masm32\include\windows.inc
include \masm32\include\kernel32.inc
include \masm32\include\user32.inc
includelib \masm32\lib\kernel32.lib
includelib \masm32\lib\user32.lib
.data
     _b.programa1 dw ?
     _a.programa1 dw ?
     @aux2 dw ?
     @aux1 dw ?
.code
start:
     MOV eax, 1
     MOV _a.programa1, eax 

     MOV eax, 2
     MOV _b.programa1, eax 

     invoke ExitProcess, 0
end start
