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
     @aux9 dw ?
     @aux20 dw ?
     @aux8 dw ?
     @aux21 dw ?
     @aux3 dw ?
     @aux2 dw ?
     @aux1 dw ?
     _d.programa1 dw ?
     424e2bf0-466e-45f8-97ca-d92519305fe7 db "HOLA ELSE", 0
     _z.funcion2.programa1 dw ?
     _a.programa1 dw ?
     8a134712-e738-4f42-8f63-fdff8759eb28 db "HOLA WHILE", 0
     _c.programa1 dw ?
     a97cb696-7cd0-4467-81b4-b826399f7d22 db "HOLA IF", 0
     _a.funcion2.programa1 dw ?
     @aux11 dw ?
     @aux10 dw ?
     _b.programa1 dw ?
     @aux19 dw ?
     @aux15 dw ?
     @aux17 dw ?
     @aux18 dw ?
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

     JMP Label_12

Label_36
     CMP _a.programa1, _b.programa1
     JG Label_49

     MOV eax, -54678
     MOV _d.programa1, eax 

     JMP Label_55

Label_49
     MOV eax, 5467
     MOV _d.programa1, eax 

Label_55
     MOV eax, _a.programa1
     MOV _probando.funcion2.programa1, eax 

     CALL  funcion2.programa1
     MOV @aux19, eax

     MOV eax, @aux19
     MOV _c.programa1, eax 

     invoke ExitProcess, 0
end start
funcion2:
     MOV eax, 1
     MOV _a.funcion2.programa1, eax 

     MOV eax, 59

ret