package org.owasp.webgoat.features;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.safari.SafariDriver;

public enum Browsers {
    Firefox() {
        @Override
        public WebDriver getDriver() {
            return new FirefoxDriver();
        }
    }, Chrome {
        @Override
        public WebDriver getDriver() {
            return new ChromeDriver();
        }
    }, InternetExplorer {
        @Override
        public WebDriver getDriver() {
            return new InternetExplorerDriver();
        }
    }, Opera {
        @Override
        public WebDriver getDriver() {
            return new OperaDriver();
        }
    }, Safari {
        @Override
        public WebDriver getDriver() {
            return new SafariDriver();
        }
    };

    public abstract WebDriver getDriver();

}
