package my.bit.sem.activity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import my.bit.sem.message.Operation;


public class ProductionCounter implements Production {

    private static final String WRONG_ARGUMENTS = "Wrong arguments in message";
    public static final Logger logger = LogManager.getLogger();


    @Override
    public String procces(String message, Operation operation) {
        String[] items = message.split(";");

        if (items.length != 2) {
            logger.error("In messsage was wrong count of arguments. Message: " + message);
            return WRONG_ARGUMENTS + message;
        }
        String result = "";

        double[] values = new double[2];
        try {
            values[0] = Double.valueOf(items[0]);
            values[1] = Double.valueOf(items[1]);
        } catch (NumberFormatException e) {
            result = build(items[0], items[1], operation.getOp(), "invalid entry");
            logger.error("Error during parse '" + message + "' to number");
            return result;
        }

        switch (operation) {
            case PLUS:
                result = build(items[0], items[1], operation.getOp(), values[0] + values[1]);
                break;
            case MINUS:
                result = build(items[0], items[1], operation.getOp(), values[0] - values[1]);
            default:
                break;
        }
        return result;
    }


    private String build(String number1, String number2, String operation, Object result) {
        StringBuilder sb = new StringBuilder();
        sb.append(number1 + " ");
        sb.append(operation + " ");
        sb.append(number2 + " ");
        sb.append("= ");
        sb.append(result);
        return sb.toString();
    }

}
