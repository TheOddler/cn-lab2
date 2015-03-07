package cnlab2;

import org.junit.Test;

import cnlab2.common.URI;

public class derp {
    
    @Test
    public void test() throws IllegalAccessException {
        URI u = new URI("http://www.example.com", 80);
        System.out.println(u);
    }
    
}
