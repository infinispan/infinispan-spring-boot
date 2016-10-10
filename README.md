# Infinispan Spring Boot Starter

## Getting Started
Add our spring boot starter as dependency in your project's pom file:
```xml
<dependency>
    <groupId>org.infinispan</groupId>
    <artifactId>spring-boot-starter-infinispan</artifactId>
    <version>1.0.0-SNAPSHOT</version>
</dependency>
```

Once you add infinispan's `spring-boot-starter` as a dependency to your project, a bean of type `EmbeddedCacheManager` can
be `autowired` into your java configuration classes. Just simply add:
 
```java
private final EmbeddedCacheManager cacheManager;

@Autowired
public YourClassName(EmbeddedCacheManager cacheManager) {
    this.cacheManager = cacheManager;
} 
```

And that's it. You can use it now as you wish. For example:
```java
cacheManager.getCache("testCache").put("testKey", "testValue");
System.out.println("Received value from cache: " + cacheManager.getCache("testCache").get("testKey"));
```

## Customization

You can further customize the cache manager by creating beans of types `InfinispanCacheConfigurer` and/or `InfinispanGlobalConfigurer`.

You can have multiple beans of type `InfinispanCacheConfigurer`. But you can *only* have one bean of type `InfinispanGlobalConfigurer`. 

An example of `InfinispanCacheConfigurer` bean:

```java
@Bean
public InfinispanCacheConfigurer cacheConfigurer() {
	return manager -> {
		final Configuration ispnConfig = new ConfigurationBuilder()
                        .clustering()
                        .cacheMode(CacheMode.LOCAL)
                        .build();

		manager.defineConfiguration("local-sync-config", ispnConfig);
	};
}
```

Moreover, you can specify the location of the infinispan XML configuration file by setting the property `spring.infinispan.config-xml` in `application.properties` or `application.yml`. Example:
```xml
spring.infinispan.config-xml=infinispan-conf.xml
```

Please note, if `spring.infinispan.config-xml` is used, the global configuration returned by the bean of type `InfinispanGlobalConfigurer` will *not* be used.

### Example Project
Please take a look at the [`infinispan-simple-tutorials`](https://github.com/blocha/infinispan-simple-tutorials) and look for `spring-boot` project.

## Why Spring starter modules are optional?
In order to support large variety of `Spring Starter` projects, we don't want to enforce any Spring versions on user side. 
All you need to do is to add proper `org.springframework.boot:spring-boot-starter` artifact to your classpath.
