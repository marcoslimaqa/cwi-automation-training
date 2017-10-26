package br.com.cwi.automation_training.pages.winium;

import org.openqa.selenium.support.PageFactory;

import br.com.cwi.automation_training.util.TestRule;

public class CalculadoraWin10Page extends CalculadoraWin10PageElementMap{

	public CalculadoraWin10Page() {
		PageFactory.initElements(TestRule.getDriver(), this);
	}
	
	public void somar(String valor1, String valor2) {
		areaResultado.sendKeys(valor1);
		logPrint("Informou o valor: " + valor1);
		botaoSomar.click();
		logPrint("Informou o valor: " + valor2);
		areaResultado.sendKeys(valor2);
		botaoIgual.click();
		logPrint("Efetuou a soma de " + valor1 + " + " + valor2);
	}

	public boolean apresentouResultadoEsperado(String resultado) {
		if (areaResultado.getAttribute("Name").contains(resultado)) {
			logPass("Apresentou o resultado esperado: " + resultado);
			return true;
		}
		logFail("O resultado da soma esta incorreto: " + resultado);
		return false;
	}
	
}