package com.github.lithualien.model;

import com.github.lithualien.model.object.Antique;
import com.github.lithualien.model.object.Condition;
import com.github.lithualien.model.object.Location;
import javafx.collections.ObservableList;

public interface Dao {
    ObservableList<Antique> getAntiques();
    ObservableList<Antique> getAntiquesByConditionAndLocation(Condition condition, Location location);
    ObservableList<Antique> getAntiquesByCondition(Condition condition);
    ObservableList<Antique> getAntiquesByLocation(Location location);
    ObservableList<Location> getLocations();
    ObservableList<Condition> getConditions();
    String getSumOfAllAntiques();
    ObservableList<Antique> getBelowAverage();
    ObservableList<Antique> getAboveAverage();
}
