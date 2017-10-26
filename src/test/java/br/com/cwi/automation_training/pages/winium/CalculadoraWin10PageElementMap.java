package br.com.cwi.automation_training.pages.winium;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import br.com.cwi.automation_training.pages.BasePage;

public class CalculadoraWin10PageElementMap extends BasePage {
	@FindBy(id="CalculatorResults")
	protected WebElement areaResultado;
	
	@FindBy(id="plusButton")
	protected WebElement botaoSomar;
	
	@FindBy(id="equalButton")
	protected WebElement botaoIgual;
}