section .data
    msgPergunta db "Digite um numero: ", 0
    msgPar db "O numero eh Par", 10, 0
    msgImpar db "O numero e Impar", 10, 0

section .bss
    entrada resb 10

section .text
    global _start

_start:
    mov eax, 4
    mov ebx, 1
    mov ecx, msgPergunta
    mov edx, 18
    int 0x80

    mov eax, 3
    mov ebx, 0
    mov ecx, entrada
    mov edx, 10
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
    jmp continuar

ignorar:
continuar:
    inc ecx
    jmp converter

pronto:
    mov eax, ebx
    mov edx, 0
    mov ecx, 2
    div ecx
    cmp edx, 0
    je numero_par

numero_impar:
    mov eax, 4
    mov ebx, 1
    mov ecx, msgImpar
    mov edx, 16
    int 0x80
    jmp fim

numero_par:
    mov eax, 4
    mov ebx, 1
    mov ecx, msgPar
    mov edx, 14
    int 0x80

fim:
    mov eax, 1
    xor ebx, ebx
    int 0x80
