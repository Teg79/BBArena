package bbarena.server;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.Filters;
import org.jboss.shrinkwrap.api.GenericArchive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.importer.ExplodedImporter;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * Created by teg on 18/12/16.
 */
@RunWith(Arquillian.class)
public class TestServer {

    @Deployment
    public static WebArchive createDeployment() {
        // Import Maven runtime dependencies
        File[] files = Maven.resolver().resolve("net.sf.bbarena:bbarena-server:1.0-SNAPSHOT")
                .withTransitivity().asFile();

        // Create deploy file
        WebArchive war = ShrinkWrap.create(WebArchive.class)
                .addPackage("bbarena.server")
                .addAsLibraries(files);
        war.merge(ShrinkWrap.create(GenericArchive.class).as(ExplodedImporter.class)
                        .importDirectory("/Users/teg/Dev/GitHub/BBArena/bbarena-server/src/main").as(GenericArchive.class),
                "/", Filters.includeAll());

        // Show the deploy structure
        System.out.println(war.toString(true));

        return war;
    }

    @Test
    public void testServer(@ArquillianResource URL url) throws InterruptedException, URISyntaxException {
        System.out.println(url.toString());

        // open websocket
        URI endpointURI = new URI("ws://" + url.getHost() + ":" + url.getPort() + "/" + url.getPath() + "/match/m/c");
        System.out.println(endpointURI.toString());
        final WebsocketClientEndpoint clientEndPoint = new WebsocketClientEndpoint(endpointURI);

        // add listener
        clientEndPoint.addMessageHandler(message -> System.out.println(message));

        // send message to websocket
        clientEndPoint.sendMessage("{'event':'addChannel','channel':'ok_btccny_ticker'}");

        // wait 5 seconds for messages from websocket
        Thread.sleep(5000);

    }
}
