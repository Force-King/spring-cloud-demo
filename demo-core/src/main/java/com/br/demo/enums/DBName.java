package com.br.demo.enums;

public enum DBName {

  DEMO("demo"),
  DEMO_R("demoread");


  private DBName(String name){
    this.name = name;
  }
  
  private String name;
  
  public String getName() {
    return name;
  }
}
