package br.com.cwi.automation_training.pages.sikuli.win10calculator;

import br.com.cwi.automation_training.util.TestRule;
import io.github.marcoslimaqa.sikulifactory.SikuliFactory;

public class DefaultCalculatorPage extends DefaultCalculatorPageElementMap {

	public DefaultCalculatorPage() {
		SikuliFactory.initElements(TestRule.getSikuli(), this);
	}

	public void openMenu() {
		menu.wait(5);
		appFocus("Calculadora");
		menu.click();
		logPrint("Clicou no menu");
	}
	
}
