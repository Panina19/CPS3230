package edu.uom.currencymanager.currencies;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ExchangeRateTest {

    CurrencyDatabase currencyDB;
    Currency curr1, curr2;
    String currCode1, currCode2;
    double exchRate;

    @Before
    public void setup() throws Exception {
        currencyDB = new CurrencyDatabase();
        currCode1 = "ABC";
        currCode2 = "ZYX";
        curr1 = new Currency(currCode1, "Major Alphabet Currency", true);
        curr2 = new Currency(currCode2, "Minor Reverse Alphabet Currency", false);
        exchRate = 10.01;
    }

    @After
    public void teardown() {
        currencyDB = null;
        curr1 = null;
        curr2 = null;
        currCode1 = null;
        currCode2 = null;
        exchRate = 0.0;
    }

    @Test
    public void TestExchangeRate() throws Exception {
        currencyDB.addCurrency(curr1);
        currencyDB.addCurrency(curr2);
        ExchangeRate exRate = new ExchangeRate(curr1, curr2, exchRate);

        assertTrue(exRate.sourceCurrency.code.equals(currCode1)
                && exRate.destinationCurrency.code.equals(currCode2)
                && exRate.rate == exchRate
        );
        currencyDB.deleteCurrency(currCode1);
        currencyDB.deleteCurrency(currCode2);
    }

    @Test
    public void TestToString() throws Exception {
        currencyDB.addCurrency(curr1);
        currencyDB.addCurrency(curr2);

        ExchangeRate expectedRate = currencyDB.getExchangeRate(currCode1, currCode2);
        expectedRate.rate = exchRate;

        String expectedResult = currCode1.concat(" 1 = ").concat(currCode2).concat(" ").concat(String.valueOf(exchRate));
        assertEquals(expectedResult, expectedRate.toString());

        currencyDB.deleteCurrency(currCode1);
        currencyDB.deleteCurrency(currCode2);
    }
}