package com.leanstacks.ws;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.joda.JodaModule;

/**
 * This class extends the functionality of AbstractTest. AbstractControllerTest is the parent of all web controller unit
 * test classes. The class ensures that a type of WebApplicationContext is built and prepares a MockMvc instance for use
 * in test methods.
 * 
 * @author Matt Warman
 */
public abstract class AbstractControllerTest extends AbstractTest {

    /**
     * Maps an Object into a JSON String. Uses a Jackson ObjectMapper.
     * 
     * @param obj The Object to map.
     * @return A String of JSON.
     * @throws JsonProcessingException Thrown if an error occurs while mapping.
     */
    protected String mapToJson(final Object obj) throws JsonProcessingException {
        final ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JodaModule());
        return mapper.writeValueAsString(obj);
    }

    /**
     * Maps a String of JSON into an instance of a Class of type T. Uses a Jackson ObjectMapper.
     * 
     * @param json A String of JSON.
     * @param clazz A Class of type T. The mapper will attempt to convert the JSON into an Object of this Class type.
     * @return An Object of type T.
     * @throws JsonParseException Thrown if an error occurs while mapping.
     * @throws JsonMappingException Thrown if an error occurs while mapping.
     * @throws IOException Thrown if an error occurs while mapping.
     */
    protected <T> T mapFromJson(final String json, final Class<T> clazz)
            throws JsonParseException, JsonMappingException, IOException {
        final ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.registerModule(new JodaModule());
        return mapper.readValue(json, clazz);
    }

}
