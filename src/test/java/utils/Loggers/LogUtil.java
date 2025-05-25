package utils.Loggers;

import io.qameta.allure.Allure;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogUtil {
    private static final Logger logger = LoggerFactory.getLogger(LogUtil.class);

    public static void logToAllureAndConsole(String message) {
        logger.info(message);
        Allure.step(message);
    }

    public static void logConsoleOnly(String message) {
        logger.info(message);
    }

    public static void logAllureOnly(String message) {
        Allure.step(message);
    }
}
