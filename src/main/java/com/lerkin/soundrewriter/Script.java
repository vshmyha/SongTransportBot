package com.lerkin.soundrewriter;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

public class Script {

    public static final String LOGIN = "login";
    public static final String PASSWORD = "password";
    public static final String ARTIST = "уннв";

    public static RewriteScriptInfo mainScript(WebDriver webDriver) {
        RewriteScriptInfo scriptInfo = new RewriteScriptInfo();
        webDriver.get("https://soundcloud.com/daniil-47395300/sets/set");
        waitFor(2);
        List<WebElement> wedTrackList = webDriver.findElements(By.className("trackItem__trackTitle"));
        List<String> trackList = wedTrackList.stream()
                .map(webElement -> {
                    String text = webElement.getText();
                    return text;
                })
                .collect(Collectors.toList());


        webDriver.get("https://vk.com/");
        WebElement loginField = webDriver.findElement(By.id("index_email"));
        loginField.sendKeys(LOGIN);
        WebElement passwordField = webDriver.findElement(By.id("index_pass"));
        passwordField.sendKeys(PASSWORD);

        WebElement logBtn = webDriver.findElement(By.id("index_login_button"));
        click(logBtn);

        WebElement search = webDriver.findElement(By.id("ts_input"));
        search.sendKeys(ARTIST);
        waitFor(2);
        WebElement searchButton = webDriver.findElement(By.className("ts_search_link"));

        waitFor(2);
        click(searchButton, 5);

        Actions actions = new Actions(webDriver);
        WebElement searchLine = webDriver.findElement(By.id("search_query"));


        Collections.reverse(trackList);
        trackList.stream()
                .map(track -> {
                    searchLine.clear();
                    searchLine.sendKeys(track);
                    searchLine.sendKeys(Keys.ENTER);
                    waitFor(2);
                    SoughtTrack soughtTrack;
                    try {
                        WebElement firstRow = webDriver.findElement(By.className("audio_row__inner"));
                        actions.moveToElement(firstRow).perform();
                        waitFor(1);
                        WebElement action = webDriver.findElement(By.className("audio_row__action_more"));
                        actions.moveToElement(action).perform();
                        waitFor(1);
                        WebElement pl = webDriver.findElement(By.className("audio_row__more_action_add_to_playlist"));
                        actions.moveToElement(pl).perform();
                        waitFor(1);
                        WebElement unnw = webDriver.findElement(By.className("audio_row__more_action_pl_141808049_64683025"));
                        actions.moveToElement(unnw).click().perform();
                        waitFor(1);

                        List<WebElement> performers = webDriver.findElements(By.className("audio_row__performers"));
                        WebElement webPerformer = performers.stream()
                                .findFirst().orElseThrow(() -> new NoSuchElementException("Performer not found"));
                        String performer = webPerformer.getText();

                        List<WebElement> titles = webDriver.findElements(By.className("audio_row__title_inner"));
                        WebElement webTitle = titles.stream()
                                .findFirst().orElseThrow(() -> new NoSuchElementException("Performer not found"));
                        String title = webTitle.getText();

                        soughtTrack = new SoughtTrack(performer, title, true);
                    } catch (NoSuchElementException e) {
                        soughtTrack = new SoughtTrack(track, false);
                    }
                    return soughtTrack;
                })
                .forEach(soughtTrack -> {
                    List<String> toAddList;
                    if (soughtTrack.founded) {
                        toAddList = scriptInfo.getFounded();
                    } else {
                        toAddList = scriptInfo.getNotFound();
                    }
                    toAddList.add(soughtTrack.name);
                });


        return scriptInfo;
    }

    private static void click(WebElement webElement) {
        click(webElement, 3);
    }

    private static void click(WebElement webElement, int waitForSec) {
        webElement.click();
        waitFor(waitForSec);
    }

    private static void waitFor(int sec) {
        try {
            Thread.sleep(sec * 1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    static class SoughtTrack {
        String name;
        boolean founded;

        public SoughtTrack(String performer, String title, boolean founded) {
            this.name = performer + " " + title;
            this.founded = founded;
        }

        public SoughtTrack(String name, boolean founded) {
            this.name = name;
            this.founded = founded;
        }
    }
}
