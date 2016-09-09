package edu.nust.mvc.utils;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;


public class ScanPackageUtils {
	
	public static List<Class> scanPackage(String packageName,Class annotationClass) {
		List<Class> controllerClassList = new ArrayList<Class>();
		List<String> classNameList = new ArrayList<String>();
		String packagePath = packageName.replace(".","/");
		try{
			Enumeration<URL> dirs = Thread.currentThread().getContextClassLoader().getResources(packagePath);
			while(dirs.hasMoreElements()){
				URL url = dirs.nextElement();
				String protocol = url.getProtocol();
				if(protocol.equals("file")){
					String wholePackagePath = URLDecoder.decode(url.getFile(),"utf-8");
					classNameList = getClassNameList(wholePackagePath,packageName );
				}else if(protocol.equals("jar")){
					//need to complete
					System.out.println("method scaned jar type is not finished");
				}
			}
			getControllerClassList(classNameList,controllerClassList,annotationClass);
		}catch(IOException e){
			e.printStackTrace();
		}
		return controllerClassList;
	}
	
	private static void getControllerClassList(List<String> classNameList, List<Class> controllerClassList, Class annotationClass){
		try{
			for (String className : classNameList) {
				Class clazz = Thread.currentThread().getContextClassLoader().loadClass(className);
				Object annotation = clazz.getAnnotation(annotationClass);
				if(annotation != null){
					controllerClassList.add(clazz);
				}
			}
		}catch(ClassNotFoundException e){
			e.printStackTrace();
		}
	}
	
	private static List<String> getClassNameList(String filePath,String prefix) {
		List<String> classNameList = new ArrayList<String>();
		File file = new File(filePath);
		if(file.exists() && file.isDirectory()){
			List<File> childFiles = new ArrayList<File>();
			getChildFiles(file,childFiles);
			for (File child : childFiles) {
				String className = getAbsoluteClassName(child, prefix);
				classNameList.add(className);
			}
		}
		return classNameList;
	}
	
	private static List<File> getChildFiles(File file,List<File> childFiles) {
		//FileFilter过滤目录和.class文件以外的File。
		File[] files = file.listFiles(new FileFilter() {

			public boolean accept(File pathname) {
				return (pathname.isDirectory() || pathname.getName().endsWith(".class"));
			}
		});
		for (File child : files) {
			if(child.isDirectory()){
				getChildFiles(child,childFiles);
			}else{
				childFiles.add(child);
			}
		}
		return childFiles;
	}
	
	private static String getAbsoluteClassName(File file,String packageName){
		String childName = file.getName();
		String packagePath = packageName.replace(".","\\");
		String childAbsolutePath = file.getAbsolutePath();
		int packageIndex = childAbsolutePath.indexOf(packagePath);
		String packageAbsolutePath = childAbsolutePath.substring(packageIndex,childAbsolutePath.indexOf(childName));
		packageName = packageAbsolutePath.replace("\\",".");
		String className = packageName + childName.substring(0,childName.indexOf(".class"));
		return className;
	}
}
