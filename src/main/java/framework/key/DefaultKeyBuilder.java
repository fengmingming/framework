package framework.key;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DefaultKeyBuilder implements IKeyBuilder{
	private int skip = 10;
	private Map<String,Integer> skips = new HashMap<String,Integer>();
	private Map<String,DefaultKeyBuilder.Key> keyM = new ConcurrentHashMap<String, DefaultKeyBuilder.Key>();

	@Override
	public long getKey(String tablename) {
		Key key = keyM.get(tablename);
		if(key == null){
			synchronized (keyM) {
				key = keyM.get(tablename);
				if(key == null){
					key = new Key();
					keyM.put(tablename, key);
				}
			}
		}
		long curKey = 0;
		synchronized (key) {
			//初始化key
			if(key.curKey == 0){
				init(key,tablename);
			}
			key.curKey = key.curKey + 1;
			curKey = key.curKey;
			if(key.curKey == key.endKey){
				init(key,tablename);
			}
		}
		return curKey;
	}
	
	private final void init(Key key,String tablename){
		key.endKey = getRangeKey(tablename);
		Integer skip = this.skips.get(tablename);
		key.startKey = key.endKey - (skip == null?this.skip:skip);
		key.curKey = key.startKey;
	}

	public int getSkip() {
		return skip;
	}

	public void setSkip(int skip) {
		this.skip = skip;
	}

	public Map<String,Integer> getSkips() {
		return skips;
	}

	public void setSkips(Map<String,Integer> skips) {
		this.skips = skips;
	}
	
	private static class Key{
		public long startKey = 0;
		public long curKey = 0;
		public long endKey = 0;
	}

	@Override
	public long getRangeKey(String tablename) {
		throw new RuntimeException("no implements method getRangeKey");
	}

}
