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
     _d_programa1 dw ?
     ceb1805f-c459-49a9-89b9-b5b16dfafaae db "HOLA ELSE", 0
     _z_funcion2_programa1 dw ?
     _probando_funcion2_programa1 dw ?
     _a_programa1 dw ?
     _c_programa1 dw ?
     _a_funcion2_programa1 dw ?
     70d14c90-5236-4273-806b-0ae813e5e5d3 db "HOLA IF", 0
     caeb4a9f-49a9-405b-a319-342e8f8711cb db "HOLA WHILE", 0
     @aux11 dw ?
     @aux10 dw ?
     _b_programa1 dw ?
     @aux19 dw ?
     @aux15 dw ?
     @aux17 dw ?
     @aux18 dw ?
.code
start:
     MOV eax, 1
     MOV _a_programa1, ax 

     MOV eax, 2
     MOV _b_programa1, ax 

     MOV eax, 4
     MOV _c_programa1, ax 

     MOV eax, 2
     MOV _d_programa1, ax 

Label_12
     MOV ax, _a_programa1
     ADD ax, _b_programa1
     MOV @aux5, ax 

     MOV ax, _c_programa1
     SUB ax, _d_programa1
     MOV @aux6, ax 

     CMP @aux5, @aux6
     JL Label_36

     MOV ax, _b_programa1
     ADD ax, _c_programa1
     MOV @aux8, ax 

     MOV eax, @aux8
     MOV _a_programa1, ax 

     MOV ax, _d_programa1
     SUB ax, 3
     MOV @aux10, ax 

     MOV eax, @aux10
     MOV _c_programa1, ax 

     JMP Label_12

Label_36
     CMP _a_programa1, _b_programa1
     JG Label_49

     MOV eax, -54678
     MOV _d_programa1, ax 

     JMP Label_55

Label_49
     MOV eax, 5467
     MOV _d_programa1, ax 

Label_55
     MOV eax, _a_programa1
     MOV _probando_funcion2_programa1, ax 

     CALL  funcion2_programa1
     MOV @aux19, eax

     MOV eax, @aux19
     MOV _c_programa1, ax 

     invoke ExitProcess, 0
end start
funcion2:
     MOV eax, 1
     MOV _a_funcion2_programa1, ax 

     MOV eax, 59

ret