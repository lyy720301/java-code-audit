
import java.io.IOException;

/**
 * 让server2去请求
 */
public class test {
    static {
        try {
            Runtime.getRuntime().exec("calc");
            System.out.println("这个不算");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static void main(String[] args) throws IOException {

    }
}