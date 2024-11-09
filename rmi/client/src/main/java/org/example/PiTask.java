package org.example;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 更具体的实现放在server中
 */
public class PiTask implements Task<BigDecimal>, Serializable {

    private static final long serialVersionUID = 227L;


    /**
     * digits of precision after the decimal point
     */
    private final int digits;

    /**
     * Construct a task to calculate pi to the specified
     * precision.
     */
    public PiTask(int digits) {
        this.digits = digits;
    }

    /**
     * Calculate pi.
     */
    public BigDecimal execute() {
        System.out.println("A: execute()");
        return new BigDecimal(-1);
    }
}