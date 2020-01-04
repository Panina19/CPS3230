package edu.uom.currencymanager.currencyserver;

import edu.uom.currencymanager.currencyserver.DefaultCurrencyServer;
import edu.uom.currencymanager.currencyserver.CurrencyServer;

import edu.uom.currencymanager.currencies.Currency;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class DefaultCurrencyServerTest {
    DefaultCurrencyServer defaultCurrencyServer = new DefaultCurrencyServer();
    Currency curr1, curr2;
    String currCode1, currCode2;
    double maxRate, minRate;

    @Before
    public void setup() {
        currCode1 = "ABC";
        currCode2 = "ZYX";
        maxRate = 1.5;
        minRate = 0.0;
    }

    @After
    public void teardown() {
        defaultCurrencyServer = null;
        currCode1 = null;
        currCode2 = null;
        maxRate = 0.0;
        minRate = 0.0;
    }

    @Test
    public void TestGetExchangeRateGreaterThanMinRate() {
        double exchangeRate = defaultCurrencyServer.getExchangeRate(currCode1, currCode2);
        Assert.assertTrue(exchangeRate>=0.0);
    }

    @Test
    public void TestGetExchangeRateLesserThanMaxRate() {
        double exchangeRate = defaultCurrencyServer.getExchangeRate(currCode1, currCode2);
        Assert.assertTrue(exchangeRate <= maxRate );
    }

    @Test
    public void TestGetExchangeRateValid() {
        double exchangeRate = defaultCurrencyServer.getExchangeRate(currCode1, currCode2);
        Assert.assertNotNull(exchangeRate);
    }
}
