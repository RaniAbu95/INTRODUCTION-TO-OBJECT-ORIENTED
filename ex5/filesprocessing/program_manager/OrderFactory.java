package filesprocessing.program_manager;
import filesprocessing.exceptions.Warnings;
import filesprocessing.orders.*;
import java.io.File;


/**
 * Created by rani.aboraya on 5/26/16.
 */
public class OrderFactory {

    private static final String ABS = "abs";
    private static final String TYPE = "type";
    private static final String SIZE = "size";
    private static final String FLT = "FILTER";
    private String str;
    private File[] files;

    /**
     * The constructor of the order factory.
     * @param files the filtered files.
     * @param str the order command.
     */
    public OrderFactory(File[] files, String str){
        this.files = files;
        this.str = str;
    }

    /**
     * make an Order object based on the order command.
     * @return return Order object.
     * @throws Warnings throws a warning if the order command isn't legal.
     */
    public Order createOrder() throws Warnings {
        switch (this.str){
            case ABS:
                return new AbsOrder(this.files);
            case FLT:
                return new AbsOrder(this.files);

            case TYPE:
                return new TypeOrder(this.files);

            case SIZE:
                return new SizeOrder(this.files);

            default:
                throw new Warnings();
        }
    }

}
