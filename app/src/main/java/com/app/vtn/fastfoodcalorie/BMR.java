package com.app.vtn.fastfoodcalorie;

/**
 * Created by Administrator on 31/7/2015.
 */
public class BMR {

    public static int typegoal=0;
    public static double weightlost=10;
    public static int typeactivity=1;
    public static boolean men=false;
    public static int age=25;
    public static double height=150;
    public static double weight=100;


    public static double getBMR(boolean men,double weight, double height,int age)
    {
        if(men) return 66+(6.23*weight)+(12.7*height)-(6.8*age);
        return 655+(4.35*weight)+(4.7*height)-(4.7*age);
    }
    public static double getAMR(int typeActivity,double bmr)
    {
        // little or no exericise
        if(typeActivity==0) return bmr*1.2;
        // light exercise/work 1-3 days per week
        if(typeActivity==1) return bmr*1.375;
        // moderate exercise/work 3-5 days per week
        if(typeActivity==2) return bmr*1.55;
        // hard exercise/work 6-7 days a week
        if(typeActivity==3) return bmr*1.725;
        // very hard exercise/work 6-7 days a week
        if(typeActivity==4) return bmr*1.9;
        return 0;
    }
    public static double getCalories(double lb)
    {
        return lb*3500;
    }
    public static double getPounds(double kilogram)
    {
        return kilogram*2.20462;
    }
    public static double getInch(double meter)
    {
        return meter*39.3701;
    }
}
