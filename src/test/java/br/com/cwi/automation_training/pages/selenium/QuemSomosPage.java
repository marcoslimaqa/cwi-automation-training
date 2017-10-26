package br.com.cwi.automation_training.pages.selenium;

import org.openqa.selenium.support.PageFactory;

import br.com.cwi.automation_training.util.TestRule;

public class QuemSomosPage extends QuemSomosPageElementMap {

	public QuemSomosPage() {
		PageFactory.initElements(TestRule.getDriver(), this);
	}	

	public boolean tituloDaPaginaEstaCorreto() {
		logPrint("Validou ttulo da pagina");
		return tituloPagina.getText().equals("O software que você procura talvez não exista, mas a empresa que irá fabricá-lo sim.");
	}
}