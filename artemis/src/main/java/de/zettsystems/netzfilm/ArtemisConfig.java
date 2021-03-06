package de.zettsystems.netzfilm;

import org.apache.activemq.artemis.api.core.TransportConfiguration;
import org.apache.activemq.artemis.core.remoting.impl.netty.NettyAcceptorFactory;
import org.apache.activemq.artemis.core.remoting.impl.netty.NettyConnectorFactory;
import org.springframework.boot.autoconfigure.jms.artemis.ArtemisConfigurationCustomizer;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ArtemisConfig implements ArtemisConfigurationCustomizer {

    /**
     * In der Standard-Spring-Boot-Config eines embedded artemis ist dieser nicht von außen erreichbar.
     * Daher fügen wir hier einen Connector und Acceptor hinzu.
     */
    @Override
    public void customize(org.apache.activemq.artemis.core.config.Configuration configuration) {
        configuration.addConnectorConfiguration("nettyConnector", new TransportConfiguration(NettyConnectorFactory.class.getName()));
        configuration.addAcceptorConfiguration(new TransportConfiguration(NettyAcceptorFactory.class.getName()));
    }
}
