package br.com.cwi.automation_training.pages.sikuli.win10calculator;

import br.com.cwi.automation_training.util.TestRule;
import io.github.marcoslimaqa.sikulifactory.SikuliFactory;

public class ScientificCalculatorPage extends ScientificCalculatorPageElementMap {

	public ScientificCalculatorPage() {
		SikuliFactory.initElements(TestRule.getSikuli(), this);
	}

	public boolean isScientificButtonsPresent() {
		boolean isScientificButtonsPresent = true;
		isScientificButtonsPresent = sinButton.exists(5) ? isScientificButtonsPresent : false;
		isScientificButtonsPresent = cosButton.exists(5) ? isScientificButtonsPresent : false;
		
		if (isScientificButtonsPresent) {
			logPrint("Botoes de calculadora cientifica foram apresentados corretamente.");
			return true;
		}
		logPrintFail("Algum botao da calculadora cientifica nao foi apresentado");
		return false;
	}
}
