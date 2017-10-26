#language: pt
#encoding: iso-8859-1
Funcionalidade: Sikuli Teste Exemplo

  @sikuli
  Cenario: Realizar soma na calculadora do Windows 8 com Sikuli
    Dado que abro o aplicativo "calc.exe"
    E efetuo a soma de 10 + 10
    Entao deve apresentar o resultado 20

  @sikuli
  Cenario: Selecionar opcao calculadora cientifica na calculadora do windows 10
    Dado que abro o aplicativo "calc.exe"
    E abro o menu
    E seleciono a calculadora cientifica
    Entao os botoes da calculadora cientifica devem estar presentes

  @sikuli
  Cenario: Inseir texto no bloco de notas
    Dado que abro o aplicativo "notepad.exe"
    Entao adiciono o texto abaixo
      """
        ______        _____   ____         __ _                          
       / ___\ \      / /_ _| / ___|  ___  / _| |___      ____ _ _ __ ___ 
      | |    \ \ /\ / / | |  \___ \ / _ \| |_| __\ \ /\ / / _` | '__/ _ \
      | |___  \ V  V /  | |   ___) | (_) |  _| |_ \ V  V / (_| | | |  __/
       \____|  \_/\_/  |___|_|____/ \___/|_|  \__| \_/\_/_\__,_|_|  \___|
      """
