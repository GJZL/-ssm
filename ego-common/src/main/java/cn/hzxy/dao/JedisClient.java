package cn.hzxy.dao;

public interface JedisClient {

	String get(String key);

	String set(String key, String value);

	String hget(String hkey, String key);

	long hset(String hkey, String key, String value);

	// 将 key 中储存的数字值增一。
	long incr(String key);

	// seconds 为给定 key 设置过期时间。
	long expire(String key, int second);

	// 以秒为单位，返回给定 key 的剩余生存时间(TTL, time to live)。
	long ttl(String key);

	long del(String key);

	long hdel(String hkey, String key);

}
