#language: pt
#encoding: iso-8859-1
Funcionalidade: Webservices Teste Exemplo

  @webservices
  Cenario: POST - Realizar requisicao de exemplo
    Dado que defino a url da requisicao "https://httpbin.org/post"
    E adiciono no header o parametro "Estou-Aprendendo" e valor "Hello World!"
    E defino o metodo de requisicao como "POST"
    Quando executo a requicao
    Entao o JSON retornado deve conter no caminho "$.headers.Estou-Aprendendo" o texto "Hello World!"

  #API: https://developer.yahoo.com/weather/
  @webservices
  Cenario: GET - Consultar previsao do tempo para Porto Alegre RS
    Dado que defino a url da requisicao "https://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20weather.forecast%20where%20woeid%20in%20(select%20woeid%20from%20geo.places(1)%20where%20text%3D%22porto%20alegre%2C%20br%22)&format=json&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys"
    E adiciono no header o parametro "q" e valor "select * from weather.forecast where woeid in (select woeid from geo.places(1) where text=\"Porto Alegre, br\")"
    E adiciono no header o parametro "format" e valor "json"
    E adiciono no header o parametro "env" e valor "store%3A%2F%2Fdatatables.org%2Falltableswithkeys"
    E defino o metodo de requisicao como "GET"
    Quando executo a requicao
    Entao o status da resposta deve ser "200"
    E o JSON retornado deve conter no caminho "$.query.results.channel.location.city" o texto "Porto Alegre"
    E o JSON retornado deve conter no caminho "$.query.results.channel.location.country" o texto "Brazil"
    E o JSON retornado deve conter no caminho "$.query.results.channel.location.region" o texto " RS"
