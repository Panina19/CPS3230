package edu.uom.currencymanager.currencies;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CurrencyTest {

    CurrencyDatabase currencyDB;
    Currency curr;
    String currCode;
    String currName;
    boolean currMajor;

    @Before
    public void setup() throws Exception {
        currencyDB = new CurrencyDatabase();
        currCode = "ABC";
        currName = "Major Alphabet Currency";
        currMajor = true;
        curr = new Currency(currCode, currName, currMajor);
    }

    @After
    public void teardown() {
        currencyDB = null;
        curr = null;
        currName = null;
        currMajor = false;
        currCode = null;
    }

    @Test
    public void TestCurrencyCodeValidity() {
        assertTrue(curr.code.equals(currCode));
    }

    @Test
    public void TestCurrencyNameValidity() {
        assertTrue(curr.name.equals(currName));
    }

    @Test
    public void TestCurrencyMajorValidity() {
        assertTrue(curr.major == currMajor);
    }

    @Test
    public void TestCurrencyPropertiesFromString() throws Exception {
        String resultString = currCode.concat(",").concat(currName).concat(",").concat("yes");
        Currency newCurr = Currency.fromString(resultString);
        assertTrue(newCurr.code.equals(curr.code) && newCurr.name.equals(curr.name) &&
                newCurr.major == curr.major);
    }
    @Test
    public void TestCurrencyParametersFromString() throws Exception {
        String resultString = currCode.concat(",").concat(currName).concat(",").concat("yes");
        Currency newCurr = Currency.fromString(resultString);
        assertTrue(newCurr.code.equals(currCode) && newCurr.name.equals(currName) &&
                newCurr.major == currMajor);
    }

    @Test
    public void TestToString() throws Exception {
        currencyDB.addCurrency(curr);
        String transformedCurr = curr.toString();
        assertEquals("ABC - Major Alphabet Currency", transformedCurr);
        currencyDB.deleteCurrency("ABC");
    }
}