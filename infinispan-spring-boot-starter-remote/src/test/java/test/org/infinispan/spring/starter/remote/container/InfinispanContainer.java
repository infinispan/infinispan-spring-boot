package test.org.infinispan.spring.starter.remote.container;

import org.infinispan.client.hotrod.RemoteCacheManager;
import org.infinispan.client.hotrod.configuration.ClientIntelligence;
import org.infinispan.client.hotrod.configuration.ConfigurationBuilder;
import org.testcontainers.containers.GenericContainer;

import com.github.dockerjava.api.command.InspectContainerResponse;

public class InfinispanContainer extends GenericContainer<InfinispanContainer> {

   private String cacheName;
   private RemoteCacheManager cacheManager;

   public InfinispanContainer() {
      this("jboss/infinispan-server");
   }

   public InfinispanContainer(String imageName) {
      super(imageName);
      withExposedPorts(11222);
   }

   public RemoteCacheManager getCacheManager() {
      return cacheManager;
   }

   public InfinispanContainer withCache(String cacheName) {
      this.cacheName = cacheName;
      return this;
   }

   @Override
   protected void containerIsStarted(final InspectContainerResponse containerInfo) {
      ConfigurationBuilder configBuilder = new ConfigurationBuilder()
            .addServers(getContainerIpAddress() + ":" + getMappedPort(11222))
            .clientIntelligence(ClientIntelligence.BASIC);

      configBuilder.statistics().enable()
            .statistics().jmxEnable();

      cacheManager = new RemoteCacheManager(configBuilder.build());
      if (cacheName != null) {
         getCacheManager().administration().createCache(cacheName, "default");
      }
   }

   @Override
   public void stop() {
      if (cacheManager != null) {
         cacheManager.stop();
      }
      super.stop();
   }
}
