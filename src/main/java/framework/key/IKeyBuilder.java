package framework.key;

public interface IKeyBuilder {

	public long getKey(String tablename);
	
	public long getRangeKey(String tablename);
}
