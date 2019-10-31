package com.masteringselenium.components;

public interface Clickable {
    Clickable click();

    Clickable verifyEnabled();

    Clickable verifyDisabled();
}
