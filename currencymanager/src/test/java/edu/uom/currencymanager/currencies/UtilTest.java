package edu.uom.currencymanager.currencies;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class UtilTest {
    @Test
    public void TestFormatSmallAmount() {
        double amount = 0.1;
        String stringFormat = Util.formatAmount(amount);
        assertEquals( "0.10",stringFormat);
    }
    @Test
    public void TestFormatPositiveAmount() {
        double amount = 1234567890.12;
        String stringFormat = Util.formatAmount(amount);
        assertEquals( "1,234,567,890.12",stringFormat);
    }

    @Test
    public void TestFormatNegativeAmount() {
        double amount = -1234567890.12;
        String stringFormat = Util.formatAmount(amount);
        assertEquals( "-1,234,567,890.12",stringFormat);
    }

    @Test
    public void TestFormatZeroAmount() {
        double amount = 0.0;
        String stringFormat = Util.formatAmount(amount);
        assertEquals( "0.00",stringFormat);
    }

    @Test
    public void TestFormatRoundingAmount() {
        double amount = 1234567890.123456789;
        String stringFormat = Util.formatAmount(amount);
        assertEquals( "1,234,567,890.12",stringFormat);
    }
}
