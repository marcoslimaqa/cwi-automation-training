package br.com.cwi.automation_training.steps;

import org.junit.Assert;

import br.com.cwi.automation_training.util.HttpRequest;
import cucumber.api.java.pt.Dado;
import cucumber.api.java.pt.E;
import cucumber.api.java.pt.Entao;
import cucumber.api.java.pt.Quando;

public class RestSteps {

	HttpRequest rest = new HttpRequest();
	
	@Dado("que defino a url da requisicao \"(.*)\"")
	public void definirUrl(String requestUrl) {
		rest.setRequestUrl(requestUrl);
	}
	
	@E("adiciono no header o parametro \"(.*)\" e valor \"(.*)\"")
	public void adicionarParametroNoHeader(String parametro, String valor) {
		rest.setHeaderParam(parametro, valor);
	}
	
	@E("adiciono no corpo o conteudo do arquivo \"(.*)\"")
	public void adicionarConteudoDoArquivoAoCorpo(String fileName) {
		rest.setRequestBodyFromFile(fileName);
	}
	
	@E("adiciono no corpo \"(.*)\"")
	public void adicionarConteudoAoCorpo(String requestBody) {
		rest.setRequestBody(requestBody);
	}
	
	@E("defino o metodo de requisicao como \"(.*)\"")
	public void definirMetodoDeRequisicao(String requestMethod) {
		rest.setRequestMethod(requestMethod);
	}
	
	@Quando("executo a requicao")
	public void executoRequisicao() {
		rest.execute();
	}
	
	@Entao("a resposta deve conter \"(.*)\"")
	public void respostaDeveConter(String texto) {
		
	}
	/**
	 * @param jsonPath : sintaxe para obter informacoes via jsonPath: http://goessner.net/articles/JsonPath/
	 */
	@Entao("o JSON retornado deve conter no caminho \"(.*)\" o texto \"(.*)\"")
	public void respostaDeveConter(String jsonPath, String textoEsperado) {
		Assert.assertTrue("O valor esperado '" + textoEsperado + "' nao foi apresentado no caminho " + jsonPath, 
				rest.jsonEqualsText(jsonPath, textoEsperado));
	}
	
	@Entao("o status da resposta deve ser \"(.*)\"")
	public void statusDaRespostaDeveSer(int responseCode) {
		Assert.assertTrue("O response code esperado " + responseCode + " nao foi apresentado.", 
				rest.isResponseCodeCorrect(responseCode));
	}
	
}