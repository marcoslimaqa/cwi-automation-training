#language: pt
#encoding: iso-8859-1
Funcionalidade: Selenium Teste Exemplo

  @selenium
  Cenario: Acessar Menu Fabrica de Testes
    Dado que estou na HomePage do site da CWI    
    Quando acesso o menu Servicos - Fabrica de Testes
    Entao deve apresentar pagina com informacoes da fabrica de testes
    
  @selenium
  Cenario: Acessar Menu Quem Somos
    Dado que estou na HomePage do site da CWI    
    Quando acesso o menu Sobre a CWI - Quem Somos
    Entao deve apresentar pagina com a historia da empresa