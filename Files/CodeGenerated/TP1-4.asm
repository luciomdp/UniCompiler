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
     c.programa1 dd ?
     f5c80d90-1750-42ab-b5d9-89783d387646 db "Esto es un string", 0
No se ha podido generar el codigo debido a la ocurrencia de errores en el codigo fuente 