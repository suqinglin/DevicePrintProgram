package com.suql.utils;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

class DefaultHostnameVerifier implements HostnameVerifier {

	public boolean verify(String hostname, SSLSession session) {
		// TODO Auto-generated method stub
		return true;
	}
}
