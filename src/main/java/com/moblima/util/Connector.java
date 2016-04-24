package com.moblima.util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;

import com.moblima.loginsystem.Account;
import com.moblima.loginsystem.AccountEntity;

public class Connector {
	public Connector() {
		// TODO Auto-generated constructor stub
	}
	
	protected Object openEntity(Class c, String FILE_PATH){
		Object entity = null;
		try {
			FileInputStream fis = new FileInputStream(FILE_PATH);
			ObjectInputStream ois = new ObjectInputStream(fis);
			entity = ois.readObject();
			ois.close();
		} catch (IOException e) {
			entity = createNewShowTimeEntity(c,FILE_PATH);
		}catch (ClassNotFoundException e){
			System.exit(-1);
		}
		return entity;
	}
	
	protected Object createNewShowTimeEntity(Class c, String FILE_PATH){
		Object entity=null;
		try {
			FileOutputStream fos = new FileOutputStream(FILE_PATH);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			
			entity = c.getConstructor(null).newInstance(null);
			oos.writeObject(entity);
			oos.close();
			
		} catch (IOException e) {
			System.out.println("cannot connect to database");
			System.exit(-1);
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return entity;
	}
	
	protected void writeToFile(Object entity, String FILE_PATH){
		try {
			FileOutputStream fos = new FileOutputStream(FILE_PATH);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(entity);
			oos.close();
		} catch (IOException e) {
			System.out.println("cannot connect to database");
			System.exit(-1);
		}
	}
}
