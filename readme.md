# blog_admin_bean
此项目为业余作品，从GIT OSC迁移至GitHub。
大约在完成于2016-03，目前不再维护。

# 模块
- 缓存设计：服务级缓存，AOP控制。缓存写入Redis。缓存模块修改了SpringCache以及参考了[freyja](https://github.com/121077313/freyja)
- 权限控制：Shiro控制，常见的用户权限表设计。
- 防重提交：[仿照蘑菇街](http://mogu.io/prevent-duplicate-requests-4)，增加服务器端token机制（类分布式锁）的防重复提交。

# BUGS
- [Shiro的RememberMe无效](http://blog.csdn.net/nsrainbow/article/details/36945267)
- 缓存的key生成器没有考虑方法参数为null的情况

# TODO
- 权限组的权限实现
- 对于角色的[用户授权-是否可管理]，前端没得选择，默认为不可管理，本应加个checkbox来控制的。0.0# 前端太渣，那个交互写不出来。
- 登陆失效后，应该给出弹出框让他登陆。还有一些地方的错误提示，应该[面向用户]
- BUG FIX
- 检查一下仿照蘑菇街的防重提交机制实现是否正确。
