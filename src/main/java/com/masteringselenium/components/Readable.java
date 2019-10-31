package com.masteringselenium.components;

public interface Readable {
    Readable verifyContainsText(String expectedText);

    Readable verifyExactText(String expectedText);
}
