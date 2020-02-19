package by.epam.training.victoriazhak.task3.parser;

import by.epam.training.victoriazhak.task3.entity.Ship;
import by.epam.training.victoriazhak.task3.exception.InvalidPathException;
import java.util.List;

public interface ShipsParser {
    List<Ship> parse(String path) throws InvalidPathException;
}
