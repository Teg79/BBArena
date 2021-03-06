package bbarena.server.json;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import bbarena.model.Arena;
import bbarena.model.pitch.Pitch;

/**
 * Created by teg on 26/12/16.
 */
public class ArenaConverter implements Converter {

    public boolean canConvert(Class clazz) {
        return clazz.isAssignableFrom(Arena.class)
                || clazz.isAssignableFrom(Pitch.class);
//                || clazz.isAssignableFrom(Team.class);
        //|| clazz.isAssignableFrom(PlayerTemplate.class);
    }

    public void marshal(Object value, HierarchicalStreamWriter writer,
                        MarshallingContext context) {
    }

    public Object unmarshal(HierarchicalStreamReader reader,
                            UnmarshallingContext context) {
        return null;
    }

}
