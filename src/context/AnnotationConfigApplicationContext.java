package context;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import annotations.Autowired;
import annotations.Bean;
import annotations.Component;
import annotations.Configuration;
import annotations.Qualifier;

public class AnnotationConfigApplicationContext implements ApplicationContext{
	
	Map<String,Object> objects;
	
	
	public AnnotationConfigApplicationContext(Class<?> class1,Class<?>...classes) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		if (!class1.isAnnotationPresent(Configuration.class)) {
			throw new IllegalArgumentException("the class should be annotated with configuration annotation");
		}
		objects = new HashMap<>();
		createObjects(class1);
		scannClasses(classes);
		checkClassDependencies();
		}
	
	
	private void checkClassDependencies() throws IllegalArgumentException, IllegalAccessException {
		for(Entry<String, Object> entry:objects.entrySet()) {
			checkDependencies(entry.getValue());
		}	
	}


	public void createObjects(Class<?> class1) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		Method[] declaredMethods = class1.getDeclaredMethods();
		for(Method method:declaredMethods) {
			if (method.isAnnotationPresent(Bean.class)) {
				Class<?> class2 = method.getReturnType();
				
				System.out.println("the method " + method.getName() + "is a bean and return type " + method.getReturnType().getSimpleName() );
				capitalizeFirstLetter(method.getName());
				objects.put(method.getName(),method.invoke(class1.getConstructor().newInstance()));
			}
		}
	}
	
	public Object getBean(String name) {
		if(objects.get(name) == null) throw new RuntimeException("no bean with the name " + name);
		return  objects.get(name);
	}
	
	@SuppressWarnings("unchecked")
	public <T> T getBean(Class<T> classes) {
		int i=0;
		T object = null;
		
		for(Entry<String, Object> entry:objects.entrySet()) {
			if(entry.getValue().getClass().equals(classes)) {
				i++;
				object = (T) entry.getValue();
			}
		}
		
		if(i==0) throw new RuntimeException("no bean of that class present");
		if(i>1) throw new RuntimeException("More than one bean of that class ");
		return object;
	}
	
	
	public <T> void scannClasses(Class<?>...classes) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		for(Class<?> class1:classes) {
			if(class1.isAnnotationPresent(Component.class)) {
				Component annotation = class1.getAnnotation(Component.class);
				String nameValue = annotation.name();
				String beanName = (nameValue.equals("")?capitalizeFirstLetter(class1.getSimpleName())
																						:nameValue);
				checkDuplicateBean(beanName);
				@SuppressWarnings("unchecked")
				T object = (T)class1.getConstructor().newInstance();
				objects.put(beanName, object);
			}
		}
	}
	
	private <T> void checkDependencies(T object) throws IllegalArgumentException, IllegalAccessException {
		Field[] fields = object.getClass().getDeclaredFields();
		for(Field field:fields) {
			field.setAccessible(true);
			if(field.isAnnotationPresent(Autowired.class)) {
				injectDependency(field,object);
			}
		}
		// TODO Auto-generated method stub
	}


	private <T> void injectDependency(Field field, T object) throws IllegalArgumentException, IllegalAccessException {
		Object object2 = null;
		int i=0;
		if(field.isAnnotationPresent(Qualifier.class) && !field.getAnnotation(Qualifier.class).value().equals("")) {
			String value = field.getAnnotation(Qualifier.class).value();
			if(!value.equals("")) {
				if(objects.containsKey(value)) object2 = objects.get(value);
				else throw new RuntimeException("No bean with that quqlifier name");
			}
		}
		else {
			for(Entry<String, Object> entry:objects.entrySet()) {
				if(entry.getValue().getClass().equals(field.getType())) {
					object2 = entry.getValue();
					i++;
					if(i>1)break;
				}
			}
			
			if(object2 == null) throw new RuntimeException("no bean with the type " + field.getType().getSimpleName()+" is present");
			if(object2!=null && i>1) throw new RuntimeException("More than one bean with the type " 
																+ field.getType().getSimpleName()
																+" consider using the qualifier");
		}
		field.set(object, object2);
	}


	public String capitalizeFirstLetter(String input) {
		StringBuilder output =new StringBuilder("");
		output.append(input.substring(0, 1).toLowerCase());
		output.append(input.substring(1));
		return output.toString();
	}
	
	public void checkDuplicateBean(String beanName) {
		if(!(objects.get(beanName) == null)) throw new RuntimeException("duplicate bean with the name " + beanName);
	}

}
