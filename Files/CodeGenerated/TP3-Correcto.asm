.586
.model flat, stdcall
option casemap :none
include \masm32\include\windows.inc
include \masm32\include\kernel32.inc
include \masm32\include\user32.inc
includelib \masm32\lib\kernel32.lib
includelib \masm32\lib\user32.lib
.data
     @aux5 dw ?
     @aux4 dw ?
     1fc2072c-c18a-4f86-b931-c924eebf2c24 db "HOLA IF", 0
     @aux2 dw ?
     @aux1 dw ?
     _d_programa1 dw ?
     _z_funcion2_programa1 dw ?
     _probando_funcion2_programa1 dw ?
     9edebde1-6ab1-4ff4-a315-3a7c9d2c45ae db "HOLA ELSE", 0
     _a_programa1 dw ?
     _c_programa1 dw ?
     _a_funcion2_programa1 dw ?
     _z_funcion3_funcion2_programa1 dw ?
     _probando_funcion3_funcion2_programa1 dw ?
     _x_funcion2_programa1 dw ?
     498dc725-60ec-45d1-893a-5abe6a88c7e9 db "HOLA WHILE", 0
     @aux11 dw ?
     @aux10 dw ?
     _b_programa1 dw ?
     _a_funcion3_funcion2_programa1 dw ?
.code
start:
Label_8
     MOV ax, _a_programa1
     ADD ax, _b_programa1
     MOV @aux1, ax 

     MOV ax, _c_programa1
     SUB ax, _d_programa1
     MOV @aux2, ax 

     CMP @aux1, @aux2
     JL Label_30

     MOV ax, _b_programa1
     ADD ax, _c_programa1
     MOV @aux4, ax 

     MOV ax, _d_programa1
     SUB ax, 3
     MOV @aux5, ax 

     JMP Label_8

Label_30
     CMP _a_programa1, _b_programa1
     JG Label_42

     JMP Label_47

Label_42
Label_47
     invoke ExitProcess, 0
end start
funcion2:
     MOV eax, 65
     MOV _probando_funcion3_funcion2_programa1, ax 

     CALL  funcion3_funcion2_programa1
     MOV @aux11, eax

     MOV ecx, 59

ret 
funcion3:
     MOV ecx, 60

ret 
