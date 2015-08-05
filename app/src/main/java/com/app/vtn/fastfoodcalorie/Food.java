package com.app.vtn.fastfoodcalorie;

/**
 * Created by Administrator on 25/7/2015.
 */
public class Food {

    int id;
    String food_name;
    public String food_group;
    public int Calories,Total_Fat,Protein,Vitamin,Iron;
    // constructors
    public Food() {

    }

    public Food(String food_name) {
        this.food_name = food_name;
    }

    public Food(int id, String food_name) {
        this.id = id;
        this.food_name = food_name;
    }

    // setter
    public void setId(int id) {
        this.id = id;
    }

    public void setFoodName(String food_name) {
        this.food_name = food_name;
    }

    // getter
    public int getId() {
        return this.id;
    }

    public String getFoodName() {
        return this.food_name;
    }
}
