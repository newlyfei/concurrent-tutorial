package com.tutorial.concurrent.countdownlatch.example.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import com.tutorial.concurrent.countdownlatch.example.service.verifier.BaseHealthChecker;
import com.tutorial.concurrent.countdownlatch.example.service.verifier.CacheHealthChecker;
import com.tutorial.concurrent.countdownlatch.example.service.verifier.DatabaseHealthChecker;
import com.tutorial.concurrent.countdownlatch.example.service.verifier.NetworkHealthChecker;

public class ApplicationStartupUtil 
{
	private static List<BaseHealthChecker> _services;
	private static CountDownLatch _latch;
	
	private ApplicationStartupUtil()
	{
	}
	
	private final static ApplicationStartupUtil INSTANCE = new ApplicationStartupUtil();
	
	public static ApplicationStartupUtil getInstance()
	{
		return INSTANCE;
	}
	
	public static boolean checkExternalServices() throws Exception
	{
		_latch = new CountDownLatch(3);
		_services = new ArrayList<BaseHealthChecker>();
		_services.add(new NetworkHealthChecker(_latch));
		_services.add(new CacheHealthChecker(_latch));
		_services.add(new DatabaseHealthChecker(_latch));
		
		Executor executor = Executors.newFixedThreadPool(_services.size());
		
		for(final BaseHealthChecker v : _services) 
		{
			executor.execute(v);
		}
		
		_latch.await();
		
		for(final BaseHealthChecker v : _services) 
		{
			if( ! v.isServiceUp())
			{
				return false;
			}
		}
		return true;
	}
	
	
}
