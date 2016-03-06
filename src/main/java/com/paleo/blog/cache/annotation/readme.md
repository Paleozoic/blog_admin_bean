# 了解MyBatis二级缓存：（blog作者还写了一个插件）
http://blog.csdn.net/luanlouis/article/details/41800511
为什么不用此的插件？因为他插件是基于MyBatis的Mapper式缓存，粒度不够细，缓存的更新是以一个或多个Mapper的Cache对象为单位的。无法准确控制缓存。

# AOP实现：
http://www.bubuko.com/infodetail-959947.html

# 其他思路：通过注解自己设置表名还是通过SQL解析器获取表名？
SQL解析器：（1）效率问题 （2）需要拦截SQL
（3）如果用SQL解析器，一个Service里面如果有多个dao执行SQL，又需要如何处理？

for (Object obj : args) {
            sb.append(obj.toString());
            sb.append(Constants.DELIMITER);
        }
该方法拼接字符串的BUG有：（1）并不能表明参数相同（2）可以改为Json的拼接，但是效率降低，key长度可能都很大（3）有一些参数不同，但是调用同一个Service的结果无法缓存（因为一般参数不会全是有效参数，存在部分冗余参数，在SQL中没有用到。所以最好的缓存还是精确到SQL级别）

我又看到Spring的Cache注解！收获巨大，key完全可以在注解里标明，而不需要在AOP切面那儿自己生成！！！！！！
（自己实现或许可以完成，但自觉做得也不会如Spring Cache好，不过Spring Cache需要与spring-data-redis一起使用，
截止至2016-01-21 版本为1.6，测试的redis 最高为2.8，但是目前Redis已经3.0+ 我不想使用spring-data-redis ）
In terms of key value stores, Redis 2.6.x or higher is required. Spring Data Redis is currently tested against the latest 2.6 and 2.8 releases.

还有淘宝的缓存框架：（与业务代码完全解耦，缓存以XML配置）
https://github.com/7hat/blog/blob/d8ad4a4e16e0c1f0ccbfb998a8df9b42c16c15be/taobao-pamirs-proxycache%E6%A1%86%E6%9E%B6%E7%9A%84%E5%AE%9E%E7%8E%B0%E5%8E%9F%E7%90%86.md

OSC搜索缓存系统：http://www.oschina.net/project/tag/132?lang=19&os=0&sort=time&p=1
找到一个叫AutoLoadCache的：http://www.oschina.net/p/autoloadcache
看来介绍和spring cache颇为相似，支持SpEL，决定使用之。（而且还在更新）

其实功能没有Spring Cache强大啊！ （以后了解了Spring Cache原理，打算提取出来）

结果发现有人做了：
http://jinnianshilongnian.iteye.com/blog/2001040
https://github.com/121077313/freyja
http://freyja.iteye.com/blog/1532817


也就是说Spring Cache注解还不是很完美，我认为可以这样设计：
@Cacheable(cacheName = "缓存名称",key="缓存key/SpEL", value="缓存值/SpEL/不填默认返回值",  beforeCondition="方法执行之前的条件/SpEL", afterCondition="方法执行后的条件/SpEL", afterCache="缓存之后执行的逻辑/SpEL")
value也是一个SpEL，这样可以定制要缓存的数据；afterCache定制自己的缓存成功后的其他逻辑。

