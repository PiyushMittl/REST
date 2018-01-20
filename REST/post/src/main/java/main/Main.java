package main;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;

public class Main implements RequestStreamHandler {
	private static Connection connection = null;

	public Object customHandleRequest(Object inputStream, Context context) throws IOException {
		System.out.println("inside customHandleRequest");
		String request = inputStream.toString();
		System.out.println(request);
		return null;
	}

	@Override
	public void handleRequest(InputStream input, OutputStream output, Context context) throws IOException {
		System.out.println("inside handleRequest");
		String request = input.toString();
		System.out.println(request);
	}
}
