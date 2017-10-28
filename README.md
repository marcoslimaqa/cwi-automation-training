## Informações necessárias para o treinamento

Os slides das aulas estão [aqui](arquivos-treinamento).

## Clonando este projeto e importando para o Eclipse

#### Obtendo uma cópia deste projeto para o seu Github

1. Logar no Github
1. Acessar o projeto: https://github.com/marcoslimaqa/cwi-automation-training
1. Clicar no botão Fork

#### Download do projeto no Eclipse

1. Acesse o projeto que foi copiado para o seu Github: https://github.com/[usuario-github]/cwi-automation-training
1. Vá em: Clone or Download > Copy to Clipboard
1. Abra o Eclipse
1. Acessar o menu: File > Import > Git
1. Clone URI
1. Avançar as etapas até o final (Next > Next > Finish)
1. Aguarda download do projeto
1. Ajuste a visualização das packages: Acessar o icone com seta para baixo ao lado do Package Explorer > Package Presentation > Hierarchical

#### Efetuando commit pelo Eclipse:
1. Clique com o botão direito no nome do projeto
1. Team > Commit

## Comandos e atalhos úteis
- F3 - Acessa o código por trás de um step da feature ou qualquer método java pelo Eclipse
- Ctrl + Shift + F: Nos arquivos .featuare é útil para auto-identação
- TestRule.openApplicationChrome(url) - Abrir navegador
- logPrint("Texto") - Adiciona print da tela ao report html e pdf
- waitElement(elemento, 15) - Espera por um elemento por no máximo 15 segundos
