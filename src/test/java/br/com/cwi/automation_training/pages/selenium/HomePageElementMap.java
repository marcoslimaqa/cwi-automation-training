package br.com.cwi.automation_training.pages.selenium;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import br.com.cwi.automation_training.pages.BasePage;

public class HomePageElementMap extends BasePage {

	@FindBy(xpath = "//*[@class='dropdown-toggle sobreCWI']")
	protected WebElement menuSobreCWI;
	
	@FindBy(xpath = "//a[text()='Quem Somos']")
	protected WebElement submenuQuemSomos;
	
	@FindBy(xpath = "//*[@class='dropdown-toggle servicos']")
	protected WebElement menuServicos;
	
	@FindBy(xpath = "//a[text()='Fábrica de Testes']")
	protected WebElement submenuFabricaDeTestes;
	
	@FindBy(xpath = "//*[@class='qualidade']")
	protected WebElement menuQualidade;
	
	@FindBy(xpath = "//*[@class='oportunidades active']")
	protected WebElement menuOportunidades;
		
}