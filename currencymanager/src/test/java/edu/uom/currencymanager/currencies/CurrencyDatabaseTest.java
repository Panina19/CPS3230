package edu.uom.currencymanager.currencies;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class CurrencyDatabaseTest {
    CurrencyDatabase currencyDB;
    Currency curr1, curr2;
    String currCode1, currCode2;

    @Before
    public void setup() throws Exception {
        currencyDB = new CurrencyDatabase();
        currCode1 = "ABC";
        currCode2 = "ZYX";
        curr1 = new Currency(currCode1, "Major Alphabet Currency", true);
        curr2 = new Currency(currCode2, "Minor Reverse Alphabet Currency", false);
    }

    @After
    public void teardown() {
        currencyDB = null;
        curr1 = null;
        curr2 = null;
        currCode1 = null;
        currCode2 = null;
    }

    @Test
    public void TestAddMajorCurrency() throws Exception {
        currencyDB.addCurrency(curr1);
        assertTrue(currencyDB.currencyExists(currCode1));
        currencyDB.deleteCurrency(currCode1);
    }

    @Test
    public void TestAddMinorCurrency() throws Exception {
        currencyDB.addCurrency(curr2);
        assertTrue(currencyDB.currencyExists(currCode2));
        currencyDB.deleteCurrency(currCode2);
    }

    @Test
    public void TestDeleteCurrency() throws Exception {
        currencyDB.addCurrency(curr1);
        currencyDB.deleteCurrency(currCode1);
        assertFalse(currencyDB.currencyExists(currCode1));
    }

    @Test
    public void TestGetCurrencyByCode() throws Exception {
        currencyDB.addCurrency(curr2);
        Currency currCode = currencyDB.getCurrencyByCode(currCode2);
        assertEquals(currCode2, currCode.code);
        currencyDB.deleteCurrency(currCode2);
    }

    @Test
    public void TestCurrencyExists() throws Exception {
        currencyDB.addCurrency(curr1);
        assertTrue(currencyDB.currencyExists(currCode1));
        currencyDB.deleteCurrency(currCode1);
    }

    @Test
    public void TestGetCurrencies() {
        List<Currency> oldCurrencies;
        List<Currency> newCurrencies = new ArrayList<Currency>() {{
            add(curr1);
            add(curr2);
        }};

        oldCurrencies = currencyDB.currencies;
        currencyDB.currencies = newCurrencies;

        List<Currency> retCurrencies = currencyDB.getCurrencies();

        assertEquals(newCurrencies, retCurrencies);
        currencyDB.currencies = oldCurrencies;
    }

    @Test
    public void TestGetOneMajorCurrencies() throws Exception {
        List<Currency> oldCurrencies;
        List<Currency> newCurrencies = new ArrayList<Currency>() {{
            add(curr1);
            add(curr2);
        }};

        oldCurrencies = currencyDB.currencies;
        currencyDB.currencies = newCurrencies;

        List<Currency> retCurrency = currencyDB.getMajorCurrencies();

        assertEquals(1, retCurrency.size());

        currencyDB.deleteCurrency(currCode1);
        currencyDB.deleteCurrency(currCode2);

        currencyDB.currencies = oldCurrencies;
    }

    @Test
    public void TestGetCorrectMajorCurrencies() throws Exception {
        List<Currency> oldCurrencies;
        List<Currency> newCurrencies = new ArrayList<Currency>() {{
            add(curr1);
            add(curr2);
        }};

        oldCurrencies = currencyDB.currencies;
        currencyDB.currencies = newCurrencies;
        List<Currency> expectedMajorCurrencies = new ArrayList<Currency>() {{
            add(curr1);
        }};

        List<Currency> retCurrency = currencyDB.getMajorCurrencies();

        assertEquals(expectedMajorCurrencies, retCurrency);

        currencyDB.deleteCurrency(currCode1);
        currencyDB.deleteCurrency(currCode2);

        currencyDB.currencies = oldCurrencies;
    }

    @Test
    public void TestInvalidSourceCurrencyGetExchangeRate() throws Exception {
        try {
            String invalidCurrCode = "QWE";
            currencyDB.addCurrency(curr1);
            currencyDB.getExchangeRate(invalidCurrCode, currCode1);
        } catch (Exception e) {
            assertEquals("Unkown currency: QWE", e.getMessage());
        }
        currencyDB.deleteCurrency(currCode1);
    }

    @Test
    public void TestInvalidDestCurrencyGetExchangeRate() throws Exception {
        try {
            String invalidCurrCode = "QWE";
            currencyDB.addCurrency(curr1);
            currencyDB.getExchangeRate(currCode1, invalidCurrCode);
        } catch (Exception e) {
            assertEquals("Unkown currency: QWE", e.getMessage());
        }
        currencyDB.deleteCurrency(currCode1);
    }

    @Test
    public void TestGetExchangeRate_UnknownRate() throws Exception {
        currencyDB.addCurrency(curr1);
        currencyDB.addCurrency(curr2);
        ExchangeRate exchangeRate = new ExchangeRate(curr1, curr2, 1.23);

        ExchangeRate testRate = currencyDB.getExchangeRate(currCode1, currCode2);
        testRate.rate = 1.23;

        String outputText = currCode1.concat(" 1 = ").concat(currCode2).concat(" ").concat(String.valueOf(exchangeRate.rate));
        assertEquals(outputText, testRate.toString());
        currencyDB.deleteCurrency(currCode1);
        currencyDB.deleteCurrency(currCode2);
    }


    @Test
    public void TestGetExchangeRate_ExceededTime() throws Exception {

        //Setup
        long EXCEEDED_MILLIS = (5*60*1000)+1;
        currencyDB.addCurrency(curr1);
        currencyDB.addCurrency(curr2);

        ExchangeRate exceededTimeExchangeRate = currencyDB.getExchangeRate(currCode1, currCode2);
        exceededTimeExchangeRate.timeLastChecked = exceededTimeExchangeRate.timeLastChecked - EXCEEDED_MILLIS;

        ExchangeRate normalExchangeRate = currencyDB.getExchangeRate(currCode1,currCode2);

        assertFalse(exceededTimeExchangeRate == normalExchangeRate);

        currencyDB.deleteCurrency(currCode1);
        currencyDB.deleteCurrency(currCode2);
    }

}
