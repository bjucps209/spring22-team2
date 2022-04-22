package model;

import javafx.scene.image.Image;

public class Boss extends Enemy{
    int attackCooldown;
    int hitX = 640;
    int hitY = 700;
    public Boss(int sides, int size, int xCoord, int yCoord, Image image, Screen homeScreen, int vision, Stats stats, double totalHealth){
        super(sides, size, xCoord, yCoord, image, homeScreen, vision, null, stats, totalHealth);
    }
    @Override
    public void performMovement()
    {
        if(attackCooldown==0)
        {
            switch((int)Math.random()*3)
            {
                case 0:
                    performAttack1();
                    break;
                case 1:
                    performAttack2();
                    break;
                case 2:
                    performAttack3();
                    break;
            }
        }
        else
        {
            attackCooldown--;
        }
    }
    public void performAttack1(){}

    public void performAttack2(){}

    public void performAttack3(){}
}
