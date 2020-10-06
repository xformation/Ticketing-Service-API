package com.syn.tkt.web.rest;

import java.io.File;

public class Test2 {
		public static void main(String[] args) {
			File file=new File("files/company");
			if(!file.exists()) {
				file.mkdirs();
			}
		}
}
