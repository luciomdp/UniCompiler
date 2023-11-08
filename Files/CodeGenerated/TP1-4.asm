.586
.model flat, stdcall
option casemap :none
include \masm32\include\windows.inc
include \masm32\include\kernel32.inc
include \masm32\include\user32.inc
includelib \masm32\lib\kernel32.lib
includelib \masm32\lib\user32.lib
.data
     0421ca52-69e1-48f3-aa3e-86c2839cb18b db "Esto es un string", 0
     b.programa1 dw ?
     c.programa1 dd ?
     a.programa1 dw ?
Error: no se puede convertir el tipo de dato ULONGINT