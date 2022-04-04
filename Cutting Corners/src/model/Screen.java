package model;

import java.util.*;

public class Screen {
    ArrayList<Entity> entities;
    Cell[][] grid;

    public void addEnemyGroup(Enemy[] group){
        for (Enemy enemy: group){entities.add(enemy);}
    }

    public void addEntity(Entity entity){entities.add(entity);}
}
