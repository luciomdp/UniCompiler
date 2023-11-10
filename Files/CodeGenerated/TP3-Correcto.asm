.586
.model flat, stdcall
option casemap :none
include \masm32\include\windows.inc
include \masm32\include\kernel32.inc
include \masm32\include\user32.inc
includelib \masm32\lib\kernel32.lib
includelib \masm32\lib\user32.lib
.data
     @aux22 dw ?
     @aux6 dw ?
     @aux23 dw ?
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
     _z.funcion2.programa1 dw ?
     _a.programa1 dw ?
     _c.programa1 dw ?
     3472d988-29d4-4b0e-ae51-f72ba15102c5 db "HOLA IF", 0
     176d8877-d207-4794-ab77-e325055b73ca db "HOLA WHILE", 0
     _a.funcion2.programa1 dw ?
     @aux11 dw ?
     3d81c520-808f-4ff1-b10a-93e6376ba8db db "HOLA ELSE", 0
     @aux10 dw ?
     _b.programa1 dw ?
     @aux19 dw ?
     @aux16 dw ?
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

     invoke MessageBox, NULL, addr 176d8877-d207-4794-ab77-e325055b73ca , addr Print, MB_OK
     JMP Label_12

Label_36
     CMP a.programa1, b.programa1
     JG Label_49

     invoke MessageBox, NULL, addr 3472d988-29d4-4b0e-ae51-f72ba15102c5 , addr Print, MB_OK
     MOV eax, -54678
     MOV _d.programa1, eax 

     JMP Label_55

Label_49
     invoke MessageBox, NULL, addr 3d81c520-808f-4ff1-b10a-93e6376ba8db , addr Print, MB_OK
     MOV eax, 5467
     MOV _d.programa1, eax 

Label_55
     MOV eax, _a.programa1
     MOV _probando.funcion2.programa1, eax 

     CALL  funcion2.programa1
     MOV @aux21, eax

     MOV eax, @aux21
     MOV _c.programa1, eax 

     invoke ExitProcess, 0
end start
funcion2:
     MOV eax, 1
     MOV _a.funcion2.programa1, eax 

     MOV eax, 59

ret