section .data
    msgPergunta db "Quantas vezes deseja repetir o texto? ", 0
    texto db "Assembly eh legal!", 10, 0
    tamanhoTexto equ $ - texto

section .bss
    entrada resb 8

section .text
    global _start

_start:
    
    mov eax, 4
    mov ebx, 1
    mov ecx, msgPergunta
    mov edx, 39
    int 0x80


    mov eax, 3
    mov ebx, 0
    mov ecx, entrada
    mov edx, 8
    int 0x80


    xor ebx, ebx    
    mov ecx, entrada

converter:
    mov al, [ecx]
    cmp al, 10      
    je pronto
    cmp al, 0
    je pronto
    cmp al, '0'
    jl ignorar
    cmp al, '9'
    jg ignorar
    sub al, '0'
    imul ebx, ebx, 10
    add ebx, eax
continuar:
    inc ecx
    jmp converter

ignorar:
    inc ecx
    jmp converter

pronto:
    mov esi, ebx        

repetir:
    cmp esi, 0
    je fim
    mov eax, 4
    mov ebx, 1
    mov ecx, texto
    mov edx, tamanhoTexto
    int 0x80
    dec esi
    jmp repetir

fim:
    mov eax, 1
    xor ebx, ebx
    int 0x80
