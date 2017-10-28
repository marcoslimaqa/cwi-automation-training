package br.com.cwi.automation_training.pages.treinamento;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import br.com.cwi.automation_training.pages.BasePage;

public class TreinamentoElementMap extends BasePage {

	protected WebElement search_query_top;
	
	@FindBy(css = ".button-search")
	protected WebElement botaoPesquisar;
	
}