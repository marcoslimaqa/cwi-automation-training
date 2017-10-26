package br.com.cwi.automation_training.pages.sikuli.win8calculator;

import br.com.cwi.automation_training.util.TestRule;
import io.github.marcoslimaqa.sikulifactory.SikuliFactory;

public class CalculadoraWin8Page extends CalculadoraWin8PageElementMap{

	public CalculadoraWin8Page() {
		SikuliFactory.initElements(TestRule.getSikuli(), this);
	}
	
	public void somar(String valor1, String valor2) {
		botaoIgual.wait(10);
		type(valor1);
		type("+");
		type(valor2);
		type("=");
		logPrint("Efetuou a soma de " + valor1 + " + " + valor2);
	}

	public boolean apresentouResultadoEsperado() {
		return imageExists("resultado-20.png", 100);
	}
	
}