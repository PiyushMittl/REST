package com.piyush.rest.dynamo.operations;

// Copyright 2012-2015 Amazon.com, Inc. or its affiliates. All Rights Reserved.
// Licensed under the Apache License, Version 2.0.

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.io.IOUtils;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.piyush.rest.dynamo.crud.Operations;

public class Update implements RequestStreamHandler{

	public void handleRequest(InputStream iStream, OutputStream oStream, Context ctx) throws IOException {
		System.out.println("inside customHandleRequest");
		
		String payload = IOUtils.toString(iStream, "UTF-8");
		
		Map<String, Object> payloadObject = new ObjectMapper().readValue(payload, Map.class);

		Map<String, String> payloadData = (LinkedHashMap) payloadObject.get("data");
		for (Map.Entry<String, String> e : payloadData.entrySet()) {

			System.out.println("Key " + e.getKey());
			System.out.println("value " + e.getValue());

		}

		Operations op=new Operations();
		String data=op.update("student", Integer.parseInt(payloadData.get("Id")));
		
		//oStream.write(new ObjectMapper().writeValueAsString(data).toString().getBytes());
		oStream.write(data.getBytes());
	}
}

