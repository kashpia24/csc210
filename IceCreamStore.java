package com.example.icecreamshop;

class IcecreamStore {
    private String[][] username_password = {{"Kashpia", "password"}};
    private String[] itemNames = {"Strawberry","kulfi","Vanila","Gelato"};
    private double[] unitCost= {30.00, 50.00, 60.00, 80.00};
    private double tax = 0.08;

    public String get_user()
    {
        return username_password[0][0];
    }

    public String get_pass()
    {
        return username_password[0][1];
    }



    public double get_price(String itemName)
    {
        for(int i = 0; i < itemNames.length; i++)
        {
            if(itemNames[i] == itemName)
            {
                return unitCost[i];
            }
        }
        return 0.0;
    }



    public String[] get_item()
    {
        return itemNames;
    }


    public double cost(double cost, int quantity)
    {
        return cost * quantity;
    }

    public double tax(double cost)
    {
        return cost * tax;
    }

    public double totalcost(double cost, double tax )
    {
        return cost + tax;
    }

}