package parser;

import by.epam.training.victoriazhak.task3.entity.LoadingType;
import by.epam.training.victoriazhak.task3.entity.Ship;
import by.epam.training.victoriazhak.task3.exception.InvalidPathException;
import by.epam.training.victoriazhak.task3.parser.JsonShipsParser;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.util.List;

public class JsonShipParserTest {
    private final JsonShipsParser parser = new JsonShipsParser();
    private static final String FILE_PATH = "src/test/java/resources/ships.json";

    @Test
    public void shouldParseJsonFile() throws InvalidPathException {
        //given
        //when
        List<Ship> ships = parser.parse(FILE_PATH);
        //then
        Assert.assertEquals(10, ships.size());

        Ship first = ships.get(0);
        Assert.assertEquals("1st", first.getName());
        Assert.assertEquals(40, first.getDeadWeight());
        Assert.assertEquals(LoadingType.UNLOAD_LOAD, first.getLoadingType());
        Assert.assertEquals(40, first.getContainersWeight());

        Ship tenth = ships.get(9);
        Assert.assertEquals("10th", tenth.getName());
        Assert.assertEquals(40, tenth.getDeadWeight());
        Assert.assertEquals(LoadingType.LOAD, tenth.getLoadingType());
        Assert.assertEquals(20, tenth.getContainersWeight());
    }
}
