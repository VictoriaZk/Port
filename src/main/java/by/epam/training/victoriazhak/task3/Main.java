package by.epam.training.victoriazhak.task3;

import by.epam.training.victoriazhak.task3.entity.Port;
import by.epam.training.victoriazhak.task3.entity.Ship;
import by.epam.training.victoriazhak.task3.exception.InvalidPathException;
import by.epam.training.victoriazhak.task3.parser.JsonShipsParser;
import by.epam.training.victoriazhak.task3.parser.ShipsParser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    private static final Logger LOGGER = LogManager.getLogger(Main.class);
    private static final String FILE_PATH = "src/test/java/resources/ships.json";

    public static void main(String[] args) {
        Main main = new Main();
        main.execute();
    }

    private void execute() {
        ShipsParser shipsParser = new JsonShipsParser();
        try {
            List<Ship> ships = shipsParser.parse(FILE_PATH);
            List<Thread> shipThreads = createShipThreads(ships);
            startThreads(shipThreads);
        } catch (InvalidPathException e) {
            LOGGER.error(e);
        }
    }
    private void startThreads(List<Thread> threads) {
        int docksAmount = Port.getDocksAmount();
        ExecutorService service = Executors.newFixedThreadPool(docksAmount);
        threads.forEach( o -> service.submit(o) );
        service.shutdown();
    }

    private List<Thread> createShipThreads(List<Ship> ships) {
        List<Thread> threads = new ArrayList<>();
        ships.forEach( o -> threads.add(new Thread(o)) );
        return threads;
    }
}
