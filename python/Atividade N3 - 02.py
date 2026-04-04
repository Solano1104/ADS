"""
exercicio 1

data = int(input("Digite sua data de nacimento: "))

idade = 2025 - data
print("Sua idade é: ", idade)

"""

"""
exercicio 2

num1 = int(input("Digite o primeiro número:  "))
num2 = int(input("Digite o segundo número:  "))
operador = input("Digite o operador: (* / + -):  ")


if (operador == '*'):
    resultado = num1 * num2
    
elif (operador == '/'):
    resultado = num1 / num2
    
elif (operador == '+'):
    resultado = num1 + num2
    
elif (operador == '-'):
    resultado = num1 / num2
    
else: 
    print("Operador inválido!")
    resultado = None

if resultado is not None:
    print(num1, operador, num2, "=", resultado)

"""

"""
exercicio 3

num1 = int(input("Digite o valor: "))

if num1 % 2 == 0:
    print("Par")
    
else:
    print("Impar")
"""

"""

exercicio 4

n1 = int(input("DIgite sua nota: "))
n2 = int(input("DIgite sua nota: "))
n3 = int(input("DIgite sua nota: "))

media = n1 + n2 + n3 / 3

if media >= 7:
    print("Aprovado", media)
    
elif media <= 7:
    print("Recuperação", media)
    """

"""

exercicio 5

numero = int(input("Digite um número para ver a tabuada: "))

for i in range(1, 11):
    resultado = numero * i
    print(f"{numero} x {i} = {resultado}")
    
    """


"""

exercicio 6
    
temp_celsius = int(input("Digite uma temperatura: "))
temp = (temp_celsius * 9 / 5) + 32
print("Fº",temp)

"""
"""
exercicio 7

n1 = float(input("Digite um valor: "))
n2 = float(input("Digite um valor: "))
n3 = float(input("Digite um valor: "))
n4 = float(input("Digite um valor: "))
n5 = float(input("Digite um valor: "))

media = (n1 + n2 + n3 + n4 + n5) / 5

print("Média é: ", media)

"""
"""

exercicio 8

peso = float(input("DIgite seu peso: "))
alt = float(input("Digite sua altura: "))

IMC = peso / (alt * alt)

print("Seu IMC é: ",IMC)

"""
