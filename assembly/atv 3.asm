section .data
    msgPergunta db "Digite seu nome: ", 0
    msgInicio db "Meu nome eh ", 0
    quebraLinha db 10, 0

section .bss
    nome resb 50

section .text
    global _start

_start:
    mov eax, 4
    mov ebx, 1
    mov ecx, msgPergunta
    mov edx, 17
    int 0x80

    mov eax, 3
    mov ebx, 0
    mov ecx, nome
    mov edx, 50
    int 0x80

    mov eax, 4
    mov ebx, 1
    mov ecx, msgInicio
    mov edx, 12
    int 0x80

    mov eax, 4
    mov ebx, 1
    mov ecx, nome
imprimir_nome:
    cmp byte [ecx], 10
    je fim_nome
    cmp byte [ecx], 0
    je fim_nome
    inc ecx
    jmp imprimir_nome

fim_nome:
    sub ecx, nome
    mov edx, ecx
    mov ecx, nome
    mov eax, 4
    mov ebx, 1
    int 0x80

    mov eax, 4
    mov ebx, 1
    mov ecx, quebraLinha
    mov edx, 1
    int 0x80

    mov eax, 1
    xor ebx, ebx
    int 0x80
