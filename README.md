# Infinispan Spring Boot Starter

## Getting Started

The Infinispan Spring Boot Starter can operate on one of two modes:

* Embedded (Infinispan operates along with your application)
* Client/Server (your app connects to a remote Infinispan cluster)

In order to get started, just add our Spring Boot Starter as dependency in your project's pom file:
```xml
<dependency>
    <groupId>org.infinispan</groupId>
    <artifactId>inifinispan-spring-boot-starter</artifactId>
    <version>${version.infinispan.starter}</version>
</dependency>
```

For the Client/Server mode that's all you will need (`infinispan-client-hotrod` artifact will be pulled transitively).

And for the Embedded mode you will need add `infinispan-core`:
```xml
<dependency>
    <groupId>org.infinispan</groupId>
    <artifactId>inifinispan-spring-boot-starter</artifactId>
    <version>${version.infinispan.starter}</version>
    <excludes>
        <exclude>
            <groupId>org.infinispan</groupId>
            <artifactId>infinispan-core</artifactId>
        </exclude>
    </excludes>
</dependency>
<dependency>
    <groupId>org.infinispan</groupId>
    <artifactId>infinispan-client-hotrod</artifactId>
    <version>${version.infinispan}</version>
</dependency>
```

## Using Embedded mode

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

### Customization

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

Moreover, you can specify the location of the infinispan XML configuration file by setting the property `infinispan.embedded.config-xml` in `application.properties` or `application.yml`. Example:
```xml
infinispan.embedded.config-xml=infinispan-conf.xml
```

Please note, if `infinispan.embedded.config-xml` is used, the global configuration returned by the bean of type `InfinispanGlobalConfigurer` will *not* be used.

## Using Client/Server mode

The Starter will try to locate `hotrod-client.properties` file placed on the classpath and create a `RemoteCacheManager` based on it.
 A sample file may look like the following
 
```text
infinispan.client.hotrod.server_list=127.0.0.1:6667
```

After the configuration file is accessible on the classpath, just add this code snippet to your app:
```java
private final RemoteCacheManager cacheManager;

@Autowired
public YourClassName(RemoteCacheManager cacheManager) {
    this.cacheManager = cacheManager;
} 
```

### Customization

The default filename for Hot Rod client can be altered using the following property: `infinispan.remote.client-properties`.

It is also possible to create a custom configuration using `InfinispanRemoteConfigurer`:
```java
@Bean
public InfinispanRemoteConfigurer infinispanRemoteConfigurer() {
   return () -> new ConfigurationBuilder()
                     .addServer()
                     .host("127.0.0.1")
                     .port(12345)
                     .build();
}
```

### Example Project
Please take a look at the [`infinispan-simple-tutorials`](https://github.com/blocha/infinispan-simple-tutorials) and look for `spring-boot` project.
