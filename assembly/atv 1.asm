section .data
    msgA db "Digite o primeiro numero: ", 0
    msgB db "Digite o segundo numero: ", 0
    msgMaiorA db "O primeiro numero eh maior!", 10, 0
    msgMaiorB db "O segundo numero eh maior!", 10, 0
    msgIgual db "Os numeros sao iguais!", 10, 0

section .bss
    numA resb 4
    numB resb 4

section .text
    global _start

_start:
    mov eax, 4          
    mov ebx, 1      
    mov ecx, msgA
    mov edx, 26
    int 0x80

    mov eax, 3          
    mov ebx, 0      
    mov ecx, numA
    mov edx, 4
    int 0x80

    mov eax, 4
    mov ebx, 1
    mov ecx, msgB
    mov edx, 26
    int 0x80

    mov eax, 3
    mov ebx, 0
    mov ecx, numB
    mov edx, 4
    int 0x80

  
    mov al, [numA]
    sub al, '0'         
    mov bl, [numB]
    sub bl, '0'

    cmp al, bl
    jg  primeiro_maior
    jl  segundo_maior

    ; Se for igual:
    mov eax, 4
    mov ebx, 1
    mov ecx, msgIgual
    mov edx, 24
    int 0x80
    jmp sair

primeiro_maior:
    mov eax, 4
    mov ebx, 1
    mov ecx, msgMaiorA
    mov edx, 30
    int 0x80
    jmp sair

segundo_maior:
    mov eax, 4
    mov ebx, 1
    mov ecx, msgMaiorB
    mov edx, 30
    int 0x80

sair:
    mov eax, 1
    xor ebx, ebx
    int 0x80
