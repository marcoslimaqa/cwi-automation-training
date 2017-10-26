package br.com.cwi.automation_training.steps.selenium;

import org.junit.Assert;

import br.com.cwi.automation_training.pages.selenium.QuemSomosPage;
import cucumber.api.java.pt.Entao;

public class QuemSomosSteps {	
	
	@Entao("deve apresentar pagina com a historia da empresa")
	public void validarTituloDaPagina() {
		QuemSomosPage quemSomosPage = new QuemSomosPage();
		Assert.assertTrue("Titulo exibido esta incorreto!!!", quemSomosPage.tituloDaPaginaEstaCorreto());
	}
}
