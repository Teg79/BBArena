package net.sf.bbarena.view.util;

import java.io.*;
import java.net.*;

/**
 * @author markotur
 *
 */
public final class FileUtils
    {
    private final static FileUtils self = new FileUtils();
    
    /**
     * @param f
     * @return
     */
    public static URL getAsURL( String f )
        {
        return self.doGetAsURL( f );
        }

    /**
     * @param f
     * @return
     */
    public static InputStream getAsStream( String f )
        {
        return self.doGetAsStream( f );
        }
    
    /**
     * @param f
     * @return
     */
    private URL doGetAsURL( String f )
        {
        return getClass().getResource( fixName( f ) );
        }

    /**
     * @param f
     * @return
     */
    private InputStream doGetAsStream( String f )
        {
        return getClass().getResourceAsStream( fixName( f ) );
        }

    /**
     * @param f
     * @return
     */
    private String fixName( String f )
        {
        if ( !f.startsWith( "/" ) )
            {
            f = "/" + f;
            }
        f = f.replaceAll( "\\\\", "\\/" );
        return f;
        }
    }