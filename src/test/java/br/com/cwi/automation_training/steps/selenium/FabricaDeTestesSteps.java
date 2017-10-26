package br.com.cwi.automation_training.steps.selenium;

import org.junit.Assert;

import br.com.cwi.automation_training.pages.selenium.FabricaDeTestesPage;
import cucumber.api.java.pt.Entao;

public class FabricaDeTestesSteps {	
	
	@Entao("deve apresentar pagina com informacoes da fabrica de testes")
	public void validarTituloDaPagina() {
		FabricaDeTestesPage fabricaDeTestesPage = new FabricaDeTestesPage();
		Assert.assertTrue("Titulo exibido esta incorreto!!!", fabricaDeTestesPage.tituloDaPaginaEstaCorreto());
	}
}
