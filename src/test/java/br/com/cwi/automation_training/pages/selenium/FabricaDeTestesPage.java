package br.com.cwi.automation_training.pages.selenium;

import org.openqa.selenium.support.PageFactory;

import br.com.cwi.automation_training.util.TestRule;

public class FabricaDeTestesPage extends FabricaDeTestesElementMap {

	public FabricaDeTestesPage() {
		PageFactory.initElements(TestRule.getDriver(), this);
	}	

	public boolean tituloDaPaginaEstaCorreto() {		
		logPrint("Validou titulo exibido na pagina");
		return tituloPagina.getText().equals("Testes especializados para melhorar a qualidade dos requisitos do seu software.");
	}
}