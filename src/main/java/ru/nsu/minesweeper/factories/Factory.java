package ru.nsu.minesweeper.factories;

import ru.nsu.minesweeper.exceptions.ClassExistenceException;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Properties;

public abstract class Factory {
    protected final HashMap<String, String> names;

    protected Factory() throws IOException {
        Properties properties = new Properties();
        FileReader reader = new FileReader("C:\\Users\\ishat\\IdeaProjects\\minesweeper\\src\\main\\java\\ru\\nsu\\minesweeper\\settings.config");
        properties.load(reader);

        names = new HashMap<>();
        for (var i : properties.stringPropertyNames()) {
            names.put(i, properties.getProperty(i));
        }
    }

    protected abstract String getFileName();

    public abstract Object createObject(Object desc) throws ClassExistenceException, ClassNotFoundException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException;

}