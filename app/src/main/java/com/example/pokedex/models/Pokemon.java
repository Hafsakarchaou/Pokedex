package com.example.pokedex.models;

public class Pokemon {
    private int number;
    public String name;
    public String url;

    public String imageUrl;

    private int weight;

    private int height;
    public int getWeight() {
        return weight;
    }

    public int getHeight() {
        return height;
    }
    public int getNumber() {
        String[] urlPartes = url.split("/");
        return Integer.parseInt(urlPartes[urlPartes.length - 1]);
    }

    public void setNumber(int number) {
        this.number = number;
    }

}
