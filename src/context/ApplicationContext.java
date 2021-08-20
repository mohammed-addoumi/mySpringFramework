package context;

public interface ApplicationContext {
	
	public Object getBean(String name);
	public <T> T getBean(Class<T> class1);

}
