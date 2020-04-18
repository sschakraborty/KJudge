package com.sschakraborty.platform.kjudge.core.io;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class IOUtility {
	public static BufferedReader wrapReader(InputStream inputStream) {
		return new BufferedReader(new InputStreamReader(inputStream));
	}
}