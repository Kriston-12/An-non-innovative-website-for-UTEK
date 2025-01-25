package com.utek.disasterrelief.demos.entity;


public class Resource {

  private long id;
  private String name;
  private String address;
  private long instantNoodles;
  private long tissuePaper;
  private long towel;
  private long water;
  private String status;


  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }


  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }


  public long getInstantNoodles() {
    return instantNoodles;
  }

  public void setInstantNoodles(long instantNoodles) {
    this.instantNoodles = instantNoodles;
  }


  public long getTissuePaper() {
    return tissuePaper;
  }

  public void setTissuePaper(long tissuePaper) {
    this.tissuePaper = tissuePaper;
  }


  public long getTowel() {
    return towel;
  }

  public void setTowel(long towel) {
    this.towel = towel;
  }


  public long getWater() {
    return water;
  }

  public void setWater(long water) {
    this.water = water;
  }


  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

}
