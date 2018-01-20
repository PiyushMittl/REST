package main;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Main implements RequestStreamHandler {
	private static Connection connection = null;

	@Override
	public void handleRequest(InputStream istream, OutputStream ostream, Context ctx) throws IOException {
		// TODO Auto-generated method stub

		Map<String, String> payload = new HashMap();

		payload.put("key1", "value1");
		payload.put("key2", "value2");
		payload.put("key3", "value3");

		
		TestClass tc=new TestClass();
		tc.setName("hello");
		
		ObjectMapper mapper = new ObjectMapper();
		String jsonInString = mapper.writeValueAsString(payload);
		
		System.out.println(jsonInString);
		
		ostream.write(new ObjectMapper().writeValueAsString(tc).toString().getBytes());

	}

}
