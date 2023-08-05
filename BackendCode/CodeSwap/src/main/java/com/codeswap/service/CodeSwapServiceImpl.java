package com.codeswap.service;

import java.io.UnsupportedEncodingException;

public interface CodeSwapServiceImpl {

	public String generatePromptFromCodeSnippet(String codeSnippet, String language);
	
	public String generatePromptForCodeDebugging(String encodedCodeSnippet) throws UnsupportedEncodingException;
	
	public String generatePromptForCodeQualityChecking(String codeSnippet);
	
}
