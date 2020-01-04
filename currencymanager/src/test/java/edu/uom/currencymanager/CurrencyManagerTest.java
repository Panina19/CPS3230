package edu.uom.currencymanager;

import edu.uom.currencymanager.currencies.Currency;
import edu.uom.currencymanager.currencies.CurrencyDatabase;
import edu.uom.currencymanager.currencies.ExchangeRate;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class CurrencyManagerTest {

    CurrencyManager currencyMan;
    CurrencyDatabase currencyDB;
    Currency curr1, curr2;
    String currCode1, currCode2;
    String currName1, currName2;
    boolean currMajor1, currMajor2;

    @Before
    public void setup() throws Exception {
        currencyMan = new CurrencyManager();
        currencyDB = new CurrencyDatabase();
        currCode1 = "ABC";
        currName1 = "Major Alphabet Currency";
        currMajor1 = true;
        currCode2 = "ZYX";
        currName2 = "Major Reverse Alphabet Currency";
        currMajor2 = true;
        curr1 = new Currency(currCode1, currName1, currMajor1);
        curr2 = new Currency(currCode2, currName2, currMajor2);
    }

    @After
    public void teardown() {
        currencyMan = null;
        currencyDB = null;
        curr1 = null;
        curr2 = null;
        currCode1 = null;
        currCode2 = null;
        currName1 = null;
        currName2 = null;
        currMajor1 = false;
        currMajor2 = false;
    }

    @Test
    public void TestGetMajorCurrencyRatesWithNoMajorCurrenciesInDB() throws Exception {
        List<ExchangeRate> exchangeRates =  currencyMan.getMajorCurrencyRates();
        currencyMan.addCurrency(currCode1, currName1, currMajor1);
        currencyMan.addCurrency(currCode2, currName2, currMajor2);
        List<ExchangeRate> updatedExchangeRates = currencyMan.getMajorCurrencyRates();

        assertTrue(updatedExchangeRates.size() > exchangeRates.size());
        currencyMan.deleteCurrencyWithCode(currCode1);
        currencyMan.deleteCurrencyWithCode(currCode2);
    }

    @Test
    public void TestGetMajorCurrencyRatesWithMajorCurrenciesInDB() throws Exception {
        currencyMan.addCurrency(currCode1, currName1, currMajor1);
        List<ExchangeRate> exchangeRates =  currencyMan.getMajorCurrencyRates();
        currencyMan.addCurrency(currCode2, currName2, currMajor2);
        List<ExchangeRate> updatedExchangeRates = currencyMan.getMajorCurrencyRates();

        assertTrue(updatedExchangeRates.size() > exchangeRates.size());
        currencyMan.deleteCurrencyWithCode(currCode1);
        currencyMan.deleteCurrencyWithCode(currCode2);
    }

    @Test
    public void TestGetExchangeRateInvalidCurr() throws Exception{
        currencyMan.addCurrency(currCode1, currName1, currMajor1);
        try {
            currencyMan.getExchangeRate("QWE", currCode1);
        } catch (Exception e) {
            assertEquals("Unkown currency: QWE", e.getMessage());
        }
        currencyMan.deleteCurrencyWithCode(currCode1);
    }

    @Test
    public void TestGetExchangeRateRuns() throws Exception{
        currencyMan.addCurrency(currCode1, currName1, currMajor1);
        currencyMan.addCurrency(currCode2, currName2, currMajor2);
        ExchangeRate exchRate = currencyMan.getExchangeRate(currCode1, currCode2);
        assertNotNull(exchRate);
        currencyMan.deleteCurrencyWithCode(currCode1);
        currencyMan.deleteCurrencyWithCode(currCode2);
    }

    @Test
    public void TestAddCurrencyCode() throws Exception {
        currencyMan.addCurrency(currCode1, currName1, currMajor1);
        Currency addedCurr = currencyMan.currencyDatabase.getCurrencyByCode(currCode1);
        assertEquals(currCode1, addedCurr.code);
        currencyMan.deleteCurrencyWithCode(currCode1);
    }

    @Test
    public void TestAddCurrencyName() throws Exception {
        currencyMan.addCurrency(currCode1, currName1, currMajor1);
        Currency addedCurr = currencyMan.currencyDatabase.getCurrencyByCode(currCode1);
        assertEquals(currName1, addedCurr.name);
        currencyMan.deleteCurrencyWithCode(currCode1);
    }

    @Test
    public void TestAddCurrencyMajor() throws Exception {
        currencyMan.addCurrency(currCode1, currName1, currMajor1);
        Currency addedCurr = currencyMan.currencyDatabase.getCurrencyByCode(currCode1);
        assertEquals(currMajor1, addedCurr.major);
        currencyMan.deleteCurrencyWithCode(currCode1);
    }

    @Test
    public void TestAddCurrencyCodeMoreThanThreeChars() {
        try {
            currencyMan.addCurrency("ABCD", currName1, currMajor1);
        } catch (Exception e) {
            assertEquals("A currency code should have 3 characters.", e.getMessage());
        }
    }

    @Test
    public void TestAddCurrencyCodeLessThanThreeChars() {
        try {
            currencyMan.addCurrency("A", currName1, currMajor1);
        } catch (Exception e) {
            assertEquals("A currency code should have 3 characters.", e.getMessage());
        }
    }

    @Test
    public void TestAddCurrencyNameLessThanFourChars() {
        try {
            currencyMan.addCurrency(currCode1, "ABC", currMajor1);
        } catch (Exception e) {
            assertEquals("A currency's name should be at least 4 characters long.", e.getMessage());
        }

    }

    @Test
    public void TestAddCurrencyAlreadyExists() throws Exception {
        currencyMan.addCurrency(currCode1, currName1, currMajor1);
        try {
            currencyMan.addCurrency(currCode1, currName1, currMajor1);
        }catch (Exception e) {
            String expectedErrorMsg = "The currency ".concat(currCode1).concat(" already exists.");
            assertEquals(expectedErrorMsg, e.getMessage());
        }
        currencyMan.deleteCurrencyWithCode(currCode1);
    }

    @Test
    public void TestDeleteCurrencyWithCode() throws Exception {
        currencyMan.addCurrency(currCode1, currName1, currMajor1);
        currencyMan.deleteCurrencyWithCode(currCode1);
        assertFalse(currencyMan.currencyDatabase.currencyExists(currCode1));
    }

    @Test
    public void TestDeleteCurrencyWithCodeNonExistent() throws Exception {
        try {
            currencyMan.deleteCurrencyWithCode(currCode1);
        }
        catch (Exception e) {
            String expectedErrorMsg = "Currency does not exist: ".concat(currCode1);
            assertEquals(expectedErrorMsg, e.getMessage());
        }
    }
}