package com.dgex.offspring.nxtCore.core;

import java.util.ArrayList;
import java.util.List;

import nxt.Account;
import nxt.Asset;
import nxt.Order;
import nxt.Order.Ask;
import nxt.Order.Bid;
import nxt.Trade;

import com.dgex.offspring.nxtCore.service.IAsset;

public class AssetHelper implements IAsset {

  private final Long issuerId;
  private final String name;
  private final String description;
  private final int quantity;
  private final Long id;
  private Asset asset = null;

  public AssetHelper(Asset asset) {
    this(asset.getId(), asset.getAccountId(), asset.getName(), asset
        .getDescription(), asset.getQuantity());
    this.asset = asset;
  }

  public AssetHelper(Long id, Long issuerId, String name, String description,
      int quantity) {
    this.id = id;
    this.issuerId = issuerId;
    this.name = name;
    this.description = description;
    this.quantity = quantity;
  }

  @Override
  public Asset getNative() {
    if (asset != null)
      return asset;

    return (asset = Asset.getAsset(id));
  }

  @Override
  public boolean equals(Object other) {
    return other instanceof AssetHelper
        && ((AssetHelper) other).id.equals(this.id);
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public Account getIssuer() {
    return Account.getAccount(issuerId);
  }

  @Override
  public String getDescription() {
    return description;
  }

  @Override
  public Long getId() {
    return id;
  }

  @Override
  public int getQuantity() {
    return quantity;
  }

  @Override
  public List<Bid> getBidOrders() {
    return new ArrayList<Bid>(Order.Bid.getSortedOrders(id));
  }

  @Override
  public List<Ask> getAskOrders() {
    return new ArrayList<Ask>(Order.Ask.getSortedOrders(id));
  }

  @Override
  public List<Trade> getTrades() {
    return Trade.getTrades(id);
  }

  @Override
  public int getAssetIssuerBalance() {
    Account a = getIssuer();
    if (a == null)
      return 0;

    Integer balance = a.getAssetBalances().get(id);
    if (balance == null)
      return 0;

    return balance.intValue();
  }

  @Override
  public int getAssetIssuerUnconfirmedBalance() {
    Account a = getIssuer();
    if (a == null)
      return 0;

    Integer balance = a.getUnconfirmedAssetBalance(id);
    if (balance == null)
      return 0;

    return balance.intValue();
  }
}
