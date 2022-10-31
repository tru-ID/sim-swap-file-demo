package com.silenauth.simcheck.demo;

import java.util.Iterator;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication implements CommandLineRunner {

	@Autowired
	private FileProcessor fileProcessor;
	@Autowired
	private SIMProcessor simProcessor;

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		if (args.length != 4)
			System.out.println("Usage: <filename.csv>");
		long start = System.currentTimeMillis();
		List<List<String>> rows = fileProcessor.load(args[0]);
		if (rows!=null && !rows.isEmpty()) {
			Iterator<List<String>> it = rows.iterator();
			int counter = 0;
			while (it.hasNext())  {
				List<String> phones = it.next();
				if (phones.isEmpty()) continue;
				counter++;
				String phone = phones.get(0);
				JSONObject res = simProcessor.doSim(phone, "row_"+counter);
				if (res!=null) {
					try {
					String status = res.getString("status");
					if ("COMPLETED".equals(status)) {
						System.out.println(phone+","+status+","+res.getBoolean("no_sim_change"));
					} else 
						System.out.println(phone+","+status+","+res.optString("error_code"));

					} catch (JSONException e) {
						System.out.println(phone+",ERROR,"+res.optString("detail"));
					}
				} else
					System.out.println(phone+",ERROR");
			}
			System.out.println("\nProcessed file: "+args[0]+" rows "+counter+" in "+ ((System.currentTimeMillis()-start)/1000) + "s");
		} else	
			System.out.println("Error: Cannot open the filename: "+ args[0]);
	}

}
