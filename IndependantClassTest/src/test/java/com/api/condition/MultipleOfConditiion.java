package com.api.condition;

import org.assertj.core.api.Condition;

/**
 * Created by reda on 10/24/14.
 */
public class MultipleOfConditiion extends Condition<Long> {

    private int modulo;

    public MultipleOfConditiion() {

    }
    public MultipleOfConditiion(int modulo) {
        this();
        this.modulo = modulo;
    }

    @Override
    public boolean matches(Long value) {
        return value.intValue() % modulo == 0;
    }

    public int getModulo() {
        return modulo;
    }

    public void setModulo(int modulo) {
        this.modulo = modulo;
    }
}
