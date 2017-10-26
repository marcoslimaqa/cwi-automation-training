package br.com.cwi.automation_training.steps.winium;

import org.junit.Assert;

import br.com.cwi.automation_training.pages.winium.CalculadoraWin10Page;
import br.com.cwi.automation_training.util.TestRule;
import cucumber.api.java.pt.Dado;
import cucumber.api.java.pt.E;
import cucumber.api.java.pt.Entao;

public class WiniumSteps {

	@Dado("que o aplicativo \"(.*)\" foi aberto")
	public void acessarHomePageCWI(String aplicativo) {
		TestRule.openApplicationWinium(aplicativo);
	}
	
	@E("efetuei a soma de (.*) \\+ (.*)")
	public void somar(String valor1, String valor2) {
		CalculadoraWin10Page calculadoraPage = new CalculadoraWin10Page();
		calculadoraPage.somar(valor1, valor2);
	}
	
	@Entao("deve ser apresentado o resultado (.*)")
	public void apresentouResultado(String resultado) {
		CalculadoraWin10Page calculadoraPage = new CalculadoraWin10Page();
		Assert.assertTrue("O resultado deveria ser " + resultado, calculadoraPage.apresentouResultadoEsperado(resultado));
	}
	
}
