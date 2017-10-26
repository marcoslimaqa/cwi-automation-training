package br.com.cwi.automation_training.pages.selenium;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import br.com.cwi.automation_training.pages.BasePage;

public class FabricaDeTestesElementMap extends BasePage {

	@FindBy(xpath = "//h2[@class='section-title service-title']")
	protected WebElement tituloPagina;
}