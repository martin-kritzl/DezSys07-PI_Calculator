import java.util.List;

public interface Service {

	private Balancer balancer;

	public boolean addServer(String name, Calculator calc);

	public boolean removeServer(String name);

	public abstract Calculator getServer(String name);

	public List<String> getServerNames();

	public abstract Service(URI ownUri);

	public abstract URI getUri();

}
