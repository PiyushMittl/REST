package com.piyush.rest.dynamo.crud;

// Copyright 2012-2015 Amazon.com, Inc. or its affiliates. All Rights Reserved.
// Licensed under the Apache License, Version 2.0.

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.document.DeleteItemOutcome;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.PrimaryKey;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.UpdateItemOutcome;
import com.amazonaws.services.dynamodbv2.document.spec.DeleteItemSpec;
import com.amazonaws.services.dynamodbv2.document.spec.UpdateItemSpec;
import com.amazonaws.services.dynamodbv2.document.utils.NameMap;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;
import com.amazonaws.services.dynamodbv2.model.ReturnValue;

public class Operations {

	///
	// static AmazonDynamoDB client =
	// AmazonDynamoDBClientBuilder.standard().build();

	static AmazonDynamoDBClient client = new AmazonDynamoDBClient(
			new BasicAWSCredentials("piyushAKIAJUDOQ7VAFE4J3RAA", "piyushW+OgfbueLxuAij/2/GV8HTfxWEZexOd0pi0CI6z1"))
					.withRegion(Regions.US_EAST_1);

	static DynamoDB dynamoDB = new DynamoDB(client);

	// static String tableName = "ProductCatalog";
	static String tableName = "student";

	public static void main(String[] args) throws IOException {

		// createItems();
		// get("student",711);
		 update("student",1);
		
		//retrieveItem();

		// Perform various updates.
		// updateMultipleAttributes();
		// updateAddNewAttribute();
		// updateExistingAttributeConditionally();

		// Delete the item.
		// deleteItem();

	}

	public void save(String tableName, Map<String, String> data) {

		Table table = dynamoDB.getTable(tableName);
		try {

			Item item = new Item().withPrimaryKey("Id", new Random().nextInt(99) + 1)
					.withString("name", "" + data.get("name")).withString("age", "" + data.get("age"));
			table.putItem(item);

		} catch (Exception e) {
			System.err.println("Create items failed.");
			System.err.println(e.getMessage());

		}
	}

	public static String get(String tableName, int primarKey) {
		Table table = dynamoDB.getTable(tableName);

		try {


			Item item = table.getItem("Id", primarKey);

			System.out.println("Printing item after retrieving it....");
			System.out.println(item.toJSONPretty());
			return item.toJSONPretty();

		} catch (Exception e) {
			System.err.println("GetItem failed.");
			System.err.println(e.getMessage());
		}
		return "item not available";
	}

	public static String update(String tableName, int primaryKey) {
		Table table = dynamoDB.getTable(tableName);

		try {

			UpdateItemSpec updateItemSpec = new UpdateItemSpec().withPrimaryKey("Id", primaryKey)
					.withUpdateExpression("set age = :r")
					.withValueMap(new ValueMap().withString(":r", "sameer"));
			
			
			try {
	            table.updateItem(updateItemSpec);
	            System.out.println("Updating the item...");
	            System.out.println("Printing item after retrieving it....");
	        } catch (Exception e) {
	            System.out.println("UpdateItem failed");
	            e.printStackTrace();
	        }

			Item item = table.getItem("Id", primaryKey);
			System.out.println(item.toJSONPretty());
			return item.toJSONPretty();

		} catch (Exception e) {
			System.err.println("GetItem failed.");
			System.err.println(e.getMessage());
		}
		return "item not available";
	}

	private static void createItems() {

		Table table = dynamoDB.getTable(tableName);
		try {

			Item item = new Item().withPrimaryKey("Id", 120).withString("Title", "Book 120 Title")
					.withString("ISBN", "120-1111111111")
					.withStringSet("Authors", new HashSet<String>(Arrays.asList("Author12", "Author22")))
					.withNumber("Price", 20).withString("Dimensions", "8.5x11.0x.75").withNumber("PageCount", 500)
					.withBoolean("InPublication", false).withString("ProductCategory", "Book");
			table.putItem(item);

			item = new Item().withPrimaryKey("Id", 121).withString("Title", "Book 121 Title")
					.withString("ISBN", "121-1111111111")
					.withStringSet("Authors", new HashSet<String>(Arrays.asList("Author21", "Author 22")))
					.withNumber("Price", 20).withString("Dimensions", "8.5x11.0x.75").withNumber("PageCount", 500)
					.withBoolean("InPublication", true).withString("ProductCategory", "Book");
			table.putItem(item);

		} catch (Exception e) {
			System.err.println("Create items failed.");
			System.err.println(e.getMessage());

		}
	}

	private static void retrieveItem() {
		Table table = dynamoDB.getTable(tableName);

		try {

			// Item item = table.getItem("Id", 120, "Id, ISBN, Title, Authors, Price",null);
			// Item item = table.getItem("Id", 120);

			Item item = table.getItem("Id", 1);

			System.out.println("Printing item after retrieving it....");
			System.out.println(item.toJSONPretty());

		} catch (Exception e) {
			System.err.println("GetItem failed.");
			System.err.println(e.getMessage());
		}

	}

	private static void updateAddNewAttribute() {
		Table table = dynamoDB.getTable(tableName);

		try {

			UpdateItemSpec updateItemSpec = new UpdateItemSpec().withPrimaryKey("Id", 121)
					.withUpdateExpression("set #na = :val1").withNameMap(new NameMap().with("#na", "NewAttribute"))
					.withValueMap(new ValueMap().withString(":val1", "Some value"))
					.withReturnValues(ReturnValue.ALL_NEW);

			UpdateItemOutcome outcome = table.updateItem(updateItemSpec);

			// Check the response.
			System.out.println("Printing item after adding new attribute...");
			System.out.println(outcome.getItem().toJSONPretty());

		} catch (Exception e) {
			System.err.println("Failed to add new attribute in " + tableName);
			System.err.println(e.getMessage());
		}
	}

	private static void updateMultipleAttributes() {

		Table table = dynamoDB.getTable(tableName);

		try {

			UpdateItemSpec updateItemSpec = new UpdateItemSpec().withPrimaryKey("Id", 120)
					.withUpdateExpression("add #a :val1 set #na=:val2")
					.withNameMap(new NameMap().with("#a", "Authors").with("#na", "NewAttribute"))
					.withValueMap(new ValueMap().withStringSet(":val1", "Author YY", "Author ZZ").withString(":val2",
							"someValue"))
					.withReturnValues(ReturnValue.ALL_NEW);

			UpdateItemOutcome outcome = table.updateItem(updateItemSpec);

			// Check the response.
			System.out.println("Printing item after multiple attribute update...");
			System.out.println(outcome.getItem().toJSONPretty());

		} catch (Exception e) {
			System.err.println("Failed to update multiple attributes in " + tableName);
			System.err.println(e.getMessage());

		}
	}

	private static void updateExistingAttributeConditionally() {

		Table table = dynamoDB.getTable(tableName);

		try {

			// Specify the desired price (25.00) and also the condition (price =
			// 20.00)

			UpdateItemSpec updateItemSpec = new UpdateItemSpec().withPrimaryKey("Id", 120)
					.withReturnValues(ReturnValue.ALL_NEW).withUpdateExpression("set #p = :val1")
					.withConditionExpression("#p = :val2").withNameMap(new NameMap().with("#p", "Price"))
					.withValueMap(new ValueMap().withNumber(":val1", 25).withNumber(":val2", 20));

			UpdateItemOutcome outcome = table.updateItem(updateItemSpec);

			// Check the response.
			System.out.println("Printing item after conditional update to new attribute...");
			System.out.println(outcome.getItem().toJSONPretty());

		} catch (Exception e) {
			System.err.println("Error updating item in " + tableName);
			System.err.println(e.getMessage());
		}
	}

	private static void deleteItem() {

		Table table = dynamoDB.getTable(tableName);

		try {

			DeleteItemSpec deleteItemSpec = new DeleteItemSpec().withPrimaryKey("Id", 120)
					.withConditionExpression("#ip = :val").withNameMap(new NameMap().with("#ip", "InPublication"))
					.withValueMap(new ValueMap().withBoolean(":val", false)).withReturnValues(ReturnValue.ALL_OLD);

			DeleteItemOutcome outcome = table.deleteItem(deleteItemSpec);

			// Check the response.
			System.out.println("Printing item that was deleted...");
			System.out.println(outcome.getItem().toJSONPretty());

		} catch (Exception e) {
			System.err.println("Error deleting item in " + tableName);
			System.err.println(e.getMessage());
		}
	}
}
