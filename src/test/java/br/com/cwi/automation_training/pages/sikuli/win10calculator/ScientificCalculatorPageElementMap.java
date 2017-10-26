package br.com.cwi.automation_training.pages.sikuli.win10calculator;

import br.com.cwi.automation_training.pages.BasePage;
import io.github.marcoslimaqa.sikulifactory.FindBy;
import io.github.marcoslimaqa.sikulifactory.SikuliElement;

public class ScientificCalculatorPageElementMap extends BasePage {
	
	@FindBy(image = "sin-button.png", similarity = 90)
	protected SikuliElement sinButton;

	@FindBy(image = "cos-button.png", similarity = 90)
	protected SikuliElement cosButton;
	
}