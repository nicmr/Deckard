package dev.nicolasmohr.deckard;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Datasheet {
    public String version;
    public ArrayList<Battleground> battlegrounds;

    public Datasheet () {
        this.version = "v0";
        this.battlegrounds = new ArrayList<>();
    }

    public static Datasheet fromFile(String path) {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        File file = new File(path);
        ObjectMapper om = new ObjectMapper(new YAMLFactory());

        Datasheet ds = new Datasheet();

        try {
            ds = om.readValue(file, Datasheet.class);
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            return ds;
        }
    }
}
