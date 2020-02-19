package by.epam.training.victoriazhak.task3.parser;

import by.epam.training.victoriazhak.task3.entity.Ship;
import by.epam.training.victoriazhak.task3.exception.InvalidPathException;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.List;

public class JsonShipsParser implements ShipsParser {
    @Override
    public List<Ship> parse(String path) throws InvalidPathException {
        Gson gson = new Gson();
        try {
            FileReader fileReader = new FileReader(path);
            JsonReader jsonReader = new JsonReader(fileReader);
            Type shipsListType = new TypeToken<List<Ship>>() {}.getType();
            List<Ship> ships = gson.fromJson(jsonReader, shipsListType);
            return ships;
        } catch (FileNotFoundException e) {
            throw new InvalidPathException(path, e);
        }
    }
}
