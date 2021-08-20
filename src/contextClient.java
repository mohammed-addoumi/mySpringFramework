import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

import beans.Car;
import beans.Personne;
import beans.User;
import beans.UserConfiguration;
import context.AnnotationConfigApplicationContext;
import context.ApplicationContext;

public class contextClient {
	
	public static void main(String[] args) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		ApplicationContext context = new AnnotationConfigApplicationContext(UserConfiguration.class,Personne.class
																									//,Car.class
																									,User.class);
		User user = (User) context.getBean("userBean");
		System.out.println(user);
		/*String test = (String) context.getBean("string");
		System.out.println(test);*/
		Personne personne = (Personne) context.getBean("personneBean");
		System.out.println(personne);
	/*	Car car = (Car) context.getBean("carBean");
		System.out.println(car);*/

	}

}
