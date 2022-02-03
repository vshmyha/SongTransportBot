package com.lerkin.soundrewriter;

import org.openqa.selenium.WebDriver;

public class Main {
    public static void main(String[] args) {
        WebDriver driver = SeleniumUtil.initDriver();
        RewriteScriptInfo scriptInfo = Script.mainScript(driver);
        System.out.println(scriptInfo);
    }
}
