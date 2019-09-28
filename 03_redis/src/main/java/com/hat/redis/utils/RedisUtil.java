package com.hat.redis.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.types.RedisClientInfo;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Component
public class RedisUtil {
    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    public void aaa() {
        List<RedisClientInfo> ll = redisTemplate.getClientList();
        System.out.println(ll);
    }

    /** *******************【key的常用操作】***********************/
    /**
     * 根据key删除数据
     *
     * @param keys 可变参数，可以删除多个key
     * @return 返回删除了几条数据
     */
    public long del(String... keys) {
        if (keys.length > 0 && keys != null) {
            if (keys.length == 1) {
                redisTemplate.delete(keys[0]);
                return keys.length;
            } else {
                redisTemplate.delete(CollectionUtils.arrayToList(keys));
                return keys.length;
            }
        }
        return 0;
    }

    /**
     * 给指定key设置过期的时间，单位（秒）
     *
     * @param key  指定key
     * @param time 从当前开始，设置(x)秒后过期
     */
    public boolean expire(String key, long time) {
        return redisTemplate.expire(key, time, TimeUnit.SECONDS);
    }

    /**
     * 给指定key设置过期的时间(指定过期日期)
     *
     * @param key  指定key
     * @param date 过期的日期
     * @return
     */
    public boolean expireAt(String key, Date date) {
        return redisTemplate.expireAt(key, date);
    }

    /**
     * 获取指定key过期时间
     *
     * @param key      key值
     * @param timeUtil 指定返回的类型，null则返回（秒），如需返回其他类型则自行指定
     * @return 返回0则表示永久不过期
     */
    public long getExpire(String key, TimeUnit timeUtil) {
        if (timeUtil == null) {
            return redisTemplate.getExpire(key);
        } else {
            return redisTemplate.getExpire(key, timeUtil);
        }
    }

    /**
     * 是否存在key
     *
     * @param key 指定key
     * @return 返回true存在、false不存在
     */
    public boolean hasKey(String key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * 根据匹配条件查找key（正则表达式）
     *
     * @param pattern 匹配条件
     * @return 返回符合匹配条件的key集合，没有符合条件的就返回[]
     */
    public Set<String> keys(String pattern) {
        return redisTemplate.keys(pattern);
    }

    /**
     * 把当前数据库的key移动到另外一个数据库中
     *
     * @param key     要移动的key
     * @param dbindex 数据库的索引(第几个数据库，从0开始)
     * @return 返回true移动成功，false移动失败
     */
    public boolean move(String key, int dbindex) {
        return redisTemplate.move(key, dbindex);
    }

    /**
     * 移除key的过期时间，即把设置了过期时间的key重新设置成永久不过期
     *
     * @param key
     * @return 返回true移除成功，false移除失败
     */
    public boolean persist(String key) {
        return redisTemplate.persist(key);
    }

    /**
     * 更改key名称
     *
     * @param oldKey 原来key名称
     * @param newKey 更改后key名称
     * @return 返回true更改成功，false更改失败
     */
    public boolean rename(String oldKey, String newKey) {
        try {
            redisTemplate.rename(oldKey, newKey);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 当newKey不存在时才更改key名称
     *
     * @param oldKey
     * @param newKey
     * @return 返回true更改成功，false更改失败
     */
    public boolean renameNX(String oldKey, String newKey) {
        return redisTemplate.renameIfAbsent(oldKey, newKey);
    }

    /** *******************【string类型的常用操作】***********************/
    /**
     * 往redis中添加数据，永久不过期
     *
     * @param key   添加的key
     * @param value 对应key的value
     * @return 返回true添加成功，fasle添加失败
     */
    public boolean set(String key, String value) {
        try {
            redisTemplate.opsForValue().set(key, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 往redis中添加数据，并指定过期时间，timeout小于1时则设置成永久不过期
     *
     * @param key     创建的key
     * @param value   对应key的value
     * @param timeout 过期时间，单位(秒)
     * @return 返回true添加成功，false添加失败
     */
    public boolean set(String key, String value, long timeout) {
        try {
            if (timeout > 0) {
                redisTemplate.opsForValue().set(key, value, timeout, TimeUnit.SECONDS);
            } else {
                set(key, value);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 只有当key不存在时才添加key-value
     *
     * @param key   创建的key
     * @param value 对应key的value
     * @return 返回true添加成功，false添加失败
     */
    public boolean setNX(String key, String value) {
        try {
            redisTemplate.opsForValue().setIfAbsent(key, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 只有当key不存在时才添加key-value,并指定过期时间，timeout小于1时则设置成永久不过期
     *
     * @param key     创建的key
     * @param value   对应key的value
     * @param timeout 过期时间，单位(秒)
     * @return 返回true添加成功，false添加失败
     */
    public boolean setNX(String key, String value, long timeout) {
        try {
            if (timeout > 0) {
                redisTemplate.opsForValue().setIfAbsent(key, value, timeout, TimeUnit.SECONDS);
            } else {
                setNX(key, value);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 如果key存在则更改value值
     *
     * @param key   指定的key
     * @param value 新的value
     * @return 返回true更改成功，false更改失败
     */
    public boolean setIfPresent(String key, String value) {
        return redisTemplate.opsForValue().setIfPresent(key, value);
    }

    /**
     * 如果key存在则更改value值,并重新设置过期时间(如果更改前有设置过期时间),小于1时永久不过期
     *
     * @param key     指定的key
     * @param value   新的value
     * @param timeout 新的过期时间，单位(秒)
     * @return 返回true更改成功，false更改失败
     */
    public boolean setIfPresent(String key, String value, long timeout) {
        if (timeout > 0) {
            return redisTemplate.opsForValue().setIfPresent(key, value, timeout, TimeUnit.SECONDS);
        } else {
            return setIfPresent(key, value);
        }
    }

    /**
     * 给指定key递增(value值需为数字)
     *
     * @param key   指定的key
     * @param delta 递增数(大于0)
     * @return
     */
    public long incr(String key, long delta) {
        if (delta < 0) {
            throw new RuntimeException("递增数不能小于0");
        } else {
            return redisTemplate.opsForValue().increment(key, delta);
        }
    }

    /**
     * 给指定key递减(value值需为数字)
     *
     * @param key   指定的key
     * @param delta 递减数(大于0)
     * @return
     */
    public long decr(String key, long delta) {
        if (delta < 0) {
            throw new RuntimeException("递减数不能小于0");
        } else {
            return redisTemplate.opsForValue().decrement(key, delta);
        }
    }

    /**
     * 获取指定key的value值
     * @param key 指定的key
     * @return 返回指定key的value值
     */
    public Object get(String key){
        return redisTemplate.opsForValue().get(key);
    }



    /** *******************【Hash类型的常用操作】***********************/

    /**
     *  根据key创建一个hash类型（相当于一个表），然后再根据hashKey-value创建数据
     *  结构： key：
     *             hashKey - value
     *             hashKey2 - value2
     * @param key    指定的key，一个key中可以有多对数据
     * @param hashKey  与数据映射的hashKey值
     * @param value     hashKey对应的value
     * @return  返回true添加成功，false添加失败
     */
    public boolean hset(String key,Object hashKey,Object value){
        try {
            redisTemplate.opsForHash().put(key,hashKey,value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 查找key中hashKey的value值
     * @param key  指定的key
     * @param hashKey   与数据映射的hashKey值
     * @return  返回与hashKey对应的value值
     */
    public Object hget(String key, Object hashKey){
        return redisTemplate.opsForHash().get(key,hashKey);
//        System.out.println("***************************");
//        System.out.println(redisTemplate.opsForHash().keys(key));
//        System.out.println("***************************");
//        System.out.println(redisTemplate.opsForHash().size(key));
//        System.out.println("***************************");
//        System.out.println(redisTemplate.opsForHash().values(key));

    }

    /**
     * 往key中添加多条hash数据
     * @param key  指定的key
     * @param map  多条hash数据封装成map
     * @return  返回true添加成功，false添加失败
     */
    public boolean hsetAll(String key, Map<Object,Object> map){
        try {
            redisTemplate.opsForHash().putAll(key, map);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 获取指定key中的所有hash数据并返回
     * @param key 指定key
     * @return 返回指定key的所有数据
     */
    public List hgetAll(String key){
        return redisTemplate.opsForHash().values(key);
    }

    /**
     * 只有当hashKey不存在时才添加该数据
     * @param key  指定的key
     * @param hashKey  与value映射的hashKey
     * @param value   hashKey对应的value
     * @return  返回true添加成功，false添加失败
     */
    public boolean hsetNX(String key,Object hashKey, Object value){
        return redisTemplate.opsForHash().putIfAbsent(key,hashKey,value);
    }

    /**
     * 指定key中hashKey的值递增(delta > 0 )或递减(delta < 0)(value需为数字，否者会报异常)
     * @param key  指定的key
     * @param hashKey  与value映射的hashKey
     * @param delta 递增数(负数则为递减)
     * @return 返回递增或递减后的值
     */
    public double hincr(String key,Object hashKey,double delta){
        return redisTemplate.opsForHash().increment(key,hashKey,delta);
    }

    /**
     *  判断key中是否存在hashKey
     * @param key  指定key
     * @param hashKey  与value映射的hashKey
     * @return  返回true存在，false不存在
     */
    public boolean hasKey(String key,Object hashKey){
        return redisTemplate.opsForHash().hasKey(key,hashKey);
    }

    /**
     * 查询key中所有键值对（hashKey-value）
     * @param key 指定key
     * @return 返回[hashKey=value，hashKey2=value2]形式的对象
     */
    public Map hentries(String key){
        return redisTemplate.opsForHash().entries(key);
    }

    /**
     * 删除key中指定的hashKeys键值对，hashKey可以传多个来删除多对数据
     * @param key  指定的key
     * @param hashKeys  与value映射的hashKey
     * @return 返回删除的数据个数
     */
    public long  hdel(String key,Object... hashKeys){
        return redisTemplate.opsForHash().delete(key,hashKeys);
    }

    /**
     * 在一个hash表(key)中添加键(hashKey)值(value)对数据，且设置过期时间
     * @param key  指定的key
     * @param hashKey  与value映射的hashKey
     * @param value    与hashKey对应的value
     * @param timeout   过期时间，单位(秒)，注意，小于等于0则永久不过期
     * @return  返回true成功，返回false失败
     */
    public boolean hset(String key,Object hashKey,Object value,long timeout){
        try {
            redisTemplate.opsForHash().put(key,hashKey,value);
            if(timeout > 0){
                expire(key,timeout);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


}
