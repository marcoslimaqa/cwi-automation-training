package br.com.cwi.automation_training.steps.sikuli;

import org.junit.Assert;

import br.com.cwi.automation_training.pages.sikuli.notepad.NotepadPage;
import br.com.cwi.automation_training.pages.sikuli.win10calculator.DefaultCalculatorPage;
import br.com.cwi.automation_training.pages.sikuli.win10calculator.MenuPage;
import br.com.cwi.automation_training.pages.sikuli.win10calculator.ScientificCalculatorPage;
import br.com.cwi.automation_training.pages.sikuli.win8calculator.CalculadoraWin8Page;
import br.com.cwi.automation_training.util.TestRule;
import cucumber.api.java.pt.Dado;
import cucumber.api.java.pt.E;
import cucumber.api.java.pt.Entao;

public class SikuliSteps {

	@Dado("que abro o aplicativo \"(.*)\"")
	public void acessarHomePageCWI(String aplicativo) {
		TestRule.openApplicationSikuli(aplicativo);
	}
	
	@E("efetuo a soma de (.*) \\+ (.*)")
	public void somar(String valor1, String valor2) {
		CalculadoraWin8Page calculadoraPage = new CalculadoraWin8Page();
		calculadoraPage.somar(valor1, valor2);
	}
	
	@Entao("deve apresentar o resultado (.*)")
	public void apresentouResultado(String resultado) {
		CalculadoraWin8Page calculadoraPage = new CalculadoraWin8Page();
		Assert.assertTrue("O resultado deveria ser " + resultado, calculadoraPage.apresentouResultadoEsperado());
	}
	
	@E("adiciono o texto abaixo")
	public void adicionarTexto(String texto) {
		NotepadPage notepadPage = new NotepadPage();
		notepadPage.adicionarTexto(texto);
	}
	
	@E("abro o menu")
	public void abriMenu() {
		DefaultCalculatorPage defaultCalculatorPage = new DefaultCalculatorPage();
		defaultCalculatorPage.openMenu();
	}
	
	@E("seleciono a calculadora cientifica")
	public void selecionarCalculadoraCientifica() {
		MenuPage menuPage = new MenuPage();
		menuPage.setScientific();
	}
	
	@E("os botoes da calculadora cientifica devem estar presentes")
	public void calculadoraCientificaApareceu() {
		ScientificCalculatorPage scientificCalculatorPage = new ScientificCalculatorPage();
		Assert.assertTrue("Some element was not found", scientificCalculatorPage.isScientificButtonsPresent());
	}
	
}
