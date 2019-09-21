package jacksonjsonp;

import com.fasterxml.jackson.databind.node.BigIntegerNode;
import com.fasterxml.jackson.databind.node.DecimalNode;
import com.fasterxml.jackson.databind.node.DoubleNode;
import com.fasterxml.jackson.databind.node.IntNode;
import com.fasterxml.jackson.databind.node.LongNode;
import com.fasterxml.jackson.databind.node.NumericNode;

import java.math.BigDecimal;
import java.math.BigInteger;

import javax.json.JsonNumber;

public class JsonNumberJackson implements JsonNumber {
    private NumericNode numeric;

    JsonNumberJackson(NumericNode numeric) {
        this.numeric = numeric;
    }

    JsonNumberJackson(int value) {
        this.numeric = IntNode.valueOf(value);
    }

    JsonNumberJackson(long value) {
        this.numeric = LongNode.valueOf(value);
    }

    JsonNumberJackson(double value) {
        this.numeric = DoubleNode.valueOf(value);
    }

    JsonNumberJackson(BigInteger value) {
        this.numeric = BigIntegerNode.valueOf(value);
    }

    JsonNumberJackson(BigDecimal value) {
        this.numeric = DecimalNode.valueOf(value);
    }

    static JsonNumberJackson fromString(String str, boolean isIntegral) {
        if (isIntegral) {
            try {
                return new JsonNumberJackson(Integer.parseInt(str));
            } catch (NumberFormatException e) {
                try {
                    return new JsonNumberJackson(Long.parseLong(str));
                } catch (NumberFormatException e2) {
                    return new JsonNumberJackson(new BigInteger(str));
                }
            }
        } else {
            BigDecimal bd = new BigDecimal(str);
            if (BigDecimal.valueOf(bd.doubleValue()).compareTo(bd) == 0) {
                return new JsonNumberJackson(bd.doubleValue());
            } else {
                return new JsonNumberJackson(bd);
            }
        }
    }

    @Override
    public boolean isIntegral() {
        return numeric.isIntegralNumber();
    }

    @Override
    public int intValue() {
        return numeric.intValue();
    }

    @Override
    public int intValueExact() {
        if (numeric.canConvertToInt()) {
            return numeric.intValue();
        }
        throw new ArithmeticException("not exact int");
    }

    @Override
    public long longValue() {
        return numeric.longValue();
    }

    @Override
    public long longValueExact() {
        if (numeric.canConvertToLong()) {
            return numeric.longValue();
        }
        throw new ArithmeticException("not exact long");
    }

    @Override
    public BigInteger bigIntegerValue() {
        return numeric.bigIntegerValue();
    }

    @Override
    public BigInteger bigIntegerValueExact() {
        if (numeric.isIntegralNumber()) {
            return numeric.bigIntegerValue();
        }
        throw new ArithmeticException("not integral");
    }

    @Override
    public double doubleValue() {
        return numeric.doubleValue();
    }

    @Override
    public BigDecimal bigDecimalValue() {
        return numeric.decimalValue();
    }

    @Override
    public ValueType getValueType() {
        return ValueType.NUMBER;
    }

    @Override
    public int hashCode() {
        return numeric.hashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        } else if (other instanceof JsonNumber) {
            return numeric.decimalValue().compareTo(((JsonNumber) other).bigDecimalValue()) == 0;
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return numeric.toString();
    }
}
