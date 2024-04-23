package com.synectiks.tkt.web.rest;

import java.io.File;
import java.io.IOException;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.time.LocalDate;

import com.google.common.io.Files;

public class Test {
		public static void main(String[] args) throws IOException {
				System.out.println(LocalDate.of(2020, 10, 15));
				System.out.println(LocalDate.now().getDayOfMonth()+10);

		}
}
