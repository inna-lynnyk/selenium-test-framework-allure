package com.masteringselenium.utils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ToDefaultSwitcher {

	public static <T> T getNewOrDefault(T nullable, T defaultValue) {
		return nullable != null ? nullable : defaultValue;
	}
}
