package bbarena.server;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * Created by teg on 18/12/16.
 */
@RunWith(Arquillian.class)
public class TestServer {

    private static final Logger _logger = LoggerFactory.getLogger(TestServer.class);

    @Deployment
    public static WebArchive createDeployment() {
        // Import Maven runtime dependencies
        File[] files = Maven.resolver().resolve("bbarena:bbarena:1.0-SNAPSHOT")
                .withTransitivity().asFile();

        // Create deploy file
        WebArchive war = ShrinkWrap.create(WebArchive.class)
//                .addPackage("bbarena.server")
                .addAsLibraries(files);
//        war.merge(ShrinkWrap.create(GenericArchive.class).as(ExplodedImporter.class)
//                        .importDirectory("src/main/webapp").as(GenericArchive.class),
//                "/", Filters.includeAll());

        // Show the deploy structure
        _logger.info(war.toString(true));

        return war;
    }

    @Test
    public void testServer(@ArquillianResource URL url) throws InterruptedException, URISyntaxException {
        // open websocket
        URI endpointURI = new URI("ws://" + url.getHost() + ":" + url.getPort() + url.getPath() + "match/m/c");
        _logger.info(endpointURI.toString());
        final WebsocketClientEndpoint clientEndPoint = new WebsocketClientEndpoint(endpointURI);

        // add listener
        clientEndPoint.addMessageHandler(message -> System.out.println(message));

        // send message to websocket
        clientEndPoint.sendMessage("start match");

        // wait 5 seconds for messages from websocket
        Thread.sleep(1000);

    }
}
