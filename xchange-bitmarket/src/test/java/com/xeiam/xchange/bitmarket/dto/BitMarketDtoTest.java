package com.xeiam.xchange.bitmarket.dto;

import com.xeiam.xchange.bitmarket.BitMarketCompareUtils;
import com.xeiam.xchange.bitmarket.BitMarketTestSupport;
import com.xeiam.xchange.bitmarket.dto.account.BitMarketAccountInfoResponse;
import com.xeiam.xchange.bitmarket.dto.account.BitMarketDepositResponse;
import com.xeiam.xchange.bitmarket.dto.account.BitMarketWithdrawResponse;
import com.xeiam.xchange.bitmarket.dto.marketdata.BitMarketOrderBook;
import com.xeiam.xchange.bitmarket.dto.marketdata.BitMarketTicker;
import com.xeiam.xchange.bitmarket.dto.marketdata.BitMarketTrade;
import com.xeiam.xchange.bitmarket.dto.trade.BitMarketCancelResponse;
import com.xeiam.xchange.bitmarket.dto.trade.BitMarketTradeResponse;
import org.junit.Test;

import java.io.IOException;

import static org.fest.assertions.api.Assertions.assertThat;

public class BitMarketDtoTest extends BitMarketTestSupport {

  @Test public void shouldParseMarketAccountInfo() throws IOException {
    // when
    BitMarketAccountInfoResponse response = parse("account/example-info-data", BitMarketAccountInfoResponse.class);

    // then
    verifySuccessResponse(response);
    BitMarketCompareUtils.compareBitMarketBalances(response.getData().getBalance(), PARSED_BALANCE);
    verifyResponseLimit(response.getLimit(), 1, 200, 1395750600L);
  }

  @Test public void shouldParseMarketAccountInfoError() throws IOException {
    verifyErrorResponse(BitMarketAccountInfoResponse.class);
  }

  @Test public void shouldParseCancelOrder() throws IOException {
    // when
    BitMarketCancelResponse response = parse("trade/example-cancel-order", BitMarketCancelResponse.class);

    // then
    verifySuccessResponse(response);
    BitMarketCompareUtils.compareBitMarketBalances(response.getData(), PARSED_BALANCE);
    verifyResponseLimit(response.getLimit(), 1, 200, 1395750600L);
  }

  @Test public void shouldParseCancelOrderError() throws IOException {
    verifyErrorResponse(BitMarketCancelResponse.class);
  }

  @Test public void shouldParseDeposit() throws IOException {
    // when
    BitMarketDepositResponse response = parse("account/example-deposit-data", BitMarketDepositResponse.class);

    // then
    verifySuccessResponse(response);
    assertThat(response.getData()).isEqualTo("BITMarket");
    verifyResponseLimit(response.getLimit(), 1, 200, 1395750600L);
  }

  @Test public void shouldParseDepositError() throws IOException {
    verifyErrorResponse(BitMarketDepositResponse.class);
  }

  @Test public void shouldParseWithdraw() throws IOException {
    // when
    BitMarketWithdrawResponse response = parse("account/example-withdraw-data", BitMarketWithdrawResponse.class);

    // then
    verifySuccessResponse(response);
    assertThat(response.getData()).isEqualTo("12345");
    verifyResponseLimit(response.getLimit(), 1, 200, 1395750600L);
  }

  @Test public void shouldParseWithdrawError() throws IOException {
    verifyErrorResponse(BitMarketWithdrawResponse.class);
  }

  @Test public void shouldParseMarketTrade() throws IOException {
    // when
    BitMarketTradeResponse response = parse("trade/example-trade-data", BitMarketTradeResponse.class);

    // then
    verifySuccessResponse(response);

    BitMarketCompareUtils.compareBitMarketOrders(response.getData().getOrder(), PARSED_TRADE_ORDER);
    BitMarketCompareUtils.compareBitMarketBalances(response.getData().getBalance(), PARSED_TRADE_BALANCE);

    verifyResponseLimit(response.getLimit(), 39, 6000, 1432920000L);
  }

  @Test public void shouldParseMarketTradeError() throws IOException {
    verifyErrorResponse(BitMarketTradeResponse.class);
  }

  @Test public void shouldParseOrderBook() throws IOException {
    // when
    BitMarketOrderBook response = parse("marketdata/example-order-book-data", BitMarketOrderBook.class);

    // then
    BitMarketCompareUtils.compareBitMarketOrderBooks(response, PARSED_ORDER_BOOK);
  }

  @Test public void shouldParseTicker() throws IOException {
    // when
    BitMarketTicker response = parse("marketdata/example-ticker-data", BitMarketTicker.class);

    // then
    BitMarketCompareUtils.compareBitMarketBitMarketTickers(response, PARSED_TICKER);
  }

  @Test public void shouldParseTrades() throws IOException {
    // when
    BitMarketTrade[] trades = parse("marketdata/example-trades-data", BitMarketTrade[].class);

    // then
    assertThat(trades).hasSize(3);
    for (int i=0; i<trades.length; i++) {
      BitMarketCompareUtils.compareBitMarketTrades(trades[i], PARSED_TRADES[i]);
    }
  }
}