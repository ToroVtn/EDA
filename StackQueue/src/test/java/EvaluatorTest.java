import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;

class EvaluatorTest {
    @Test
    void test1() throws NoSuchMethodException, SecurityException,
            IllegalAccessException, IllegalArgumentException,
            InvocationTargetException {
        Sorpresa sorpresaInstance = new Sorpresa();
        Method myMethod = Sorpresa.class.getDeclaredMethod("f");
        myMethod.setAccessible(true);
        double result = (Double) myMethod.invoke(sorpresaInstance);
        assertEquals( 35, result);
    }

    @Test
    void test2() throws NoSuchMethodException, SecurityException,
            IllegalAccessException, IllegalArgumentException,
            InvocationTargetException {
        Sorpresa sorpresaInstance = new Sorpresa();
        Method myMethod = Sorpresa.class.getDeclaredMethod("f", double.class);
        myMethod.setAccessible(true);
        double result = (Double) myMethod.invoke(sorpresaInstance, 34.5);
        assertEquals( 34.5, result);
    }

    @Test
    void test() {
        // inyecto en la standard input
        String input = "15 + 3";
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);
        Evaluator myEval = new Evaluator();
        double rta = myEval.evaluate();
        assertEquals( 18, rta);
    }
}