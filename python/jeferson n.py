class Node:
    def __init__(self,valor):
        self.valor = valor
        self.esq = None
        self.dir = None
        
def inserir(raiz,valor):
    if raiz is Node:
        return Node(valor)
    if valor < raiz.valor:
        raiz.esq = inserir(raiz.esq, valor)
    else:
        raiz.dir = inserir(raiz.dir, valor )
        return  raiz
    
