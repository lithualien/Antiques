package com.github.lithualien.model;

import com.github.lithualien.model.object.Antique;
import com.github.lithualien.model.object.Condition;
import com.github.lithualien.model.object.Location;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.*;

public class DaoImpl implements Dao {

    private static Connection connection;
    private static PreparedStatement statement;
    private static ResultSet result;
    private ObservableList<Antique> antiques;
    private ObservableList<Location> locations;
    private ObservableList<Condition> conditions;
    private String sum, average;

    @Override
    public ObservableList<Antique> getAntiques() {
        String sql = "SELECT antiques.id, antiques.name, conditions.condition, antiques.price, antiques.date, locations.location\n" +
            "FROM ((fourth.antiques\n" +
            "INNER JOIN fourth.conditions\n" +
            "ON antiques.condition_id = conditions.id)\n" +
            "INNER JOIN fourth.locations\n" +
            "ON antiques.location_id = locations.id);\n";
        connectToDatabase();
        setStatement(sql);
        setResult();
        setAntiques();
        closeConnection();
        return antiques;
    }

    @Override
    public ObservableList<Antique> getAntiquesByConditionAndLocation(Condition condition, Location location) {
        String sql = "SELECT antiques.id, antiques.name, conditions.condition, antiques.price, antiques.date, locations.location\n" +
            "FROM ((fourth.antiques\n" +
            "INNER JOIN fourth.conditions\n" +
            "ON antiques.condition_id = conditions.id)\n" +
            "INNER JOIN fourth.locations\n" +
            "ON antiques.location_id = locations.id)\n" +
            "WHERE antiques.condition_id = " + condition.getId() + " AND antiques.location_id = " + location.getId() + ";";
        connectToDatabase();
        setStatement(sql);
        setResult();
        setAntiques();
        closeConnection();
        return antiques;
    }

    @Override
    public ObservableList<Antique> getAntiquesByCondition(Condition condition) {
        String sql = "SELECT antiques.id, antiques.name, conditions.condition, antiques.price, antiques.date, locations.location\n" +
            "FROM ((fourth.antiques\n" +
            "INNER JOIN fourth.conditions\n" +
            "ON antiques.condition_id = conditions.id)\n" +
            "INNER JOIN fourth.locations\n" +
            "ON antiques.location_id = locations.id)\n" +
            "WHERE antiques.condition_id = " + condition.getId() + ";";
        connectToDatabase();
        setStatement(sql);
        setResult();
        setAntiques();
        closeConnection();
        return antiques;
    }

    @Override
    public ObservableList<Antique> getAntiquesByLocation(Location location) {
        String sql = "SELECT antiques.id, antiques.name, conditions.condition, antiques.price, antiques.date, locations.location\n" +
            "FROM ((fourth.antiques\n" +
            "INNER JOIN fourth.conditions\n" +
            "ON antiques.condition_id = conditions.id)\n" +
            "INNER JOIN fourth.locations\n" +
            "ON antiques.location_id = locations.id)\n" +
            "WHERE antiques.location_id = " + location.getId() + ";";
        connectToDatabase();
        setStatement(sql);
        setResult();
        setAntiques();
        closeConnection();
        return antiques;
    }

    @Override
    public ObservableList<Location> getLocations() {
        String sql = "SELECT locations.id, locations.location FROM fourth.locations";
        connectToDatabase();
        setStatement(sql);
        setResult();
        setLocations();
        closeConnection();
        return locations;
    }

    @Override
    public ObservableList<Condition> getConditions() {
        String sql = "SELECT conditions.id, conditions.condition FROM fourth.conditions;";
        connectToDatabase();
        setStatement(sql);
        setResult();
        setConditions();
        closeConnection();
        return conditions;
    }

    @Override
    public String getSumOfAllAntiques() {
        String sql = "SELECT SUM(price) FROM fourth.antiques;";
        connectToDatabase();
        setStatement(sql);
        setResult();
        sum = setLogicQueries();
        formatString();
        return sum;
    }

    @Override
    public ObservableList<Antique> getBelowAverage() {
        getAverage();
        String sql = "SELECT antiques.id, antiques.name, conditions.condition, antiques.price, antiques.date, locations.location\n" +
            "FROM ((fourth.antiques\n" +
            "INNER JOIN fourth.conditions\n" +
            "ON antiques.condition_id = conditions.id)\n" +
            "INNER JOIN fourth.locations\n" +
            "ON antiques.location_id = locations.id)\n" +
            "WHERE antiques.price <= " + average + ";";
        connectToDatabase();
        setStatement(sql);
        setResult();
        setAntiques();
        closeConnection();
        return antiques;
    }

    @Override
    public ObservableList<Antique> getAboveAverage() {
        getAverage();
        String sql = "SELECT antiques.id, antiques.name, conditions.condition, antiques.price, antiques.date, locations.location\n" +
            "FROM ((fourth.antiques\n" +
            "INNER JOIN fourth.conditions\n" +
            "ON antiques.condition_id = conditions.id)\n" +
            "INNER JOIN fourth.locations\n" +
            "ON antiques.location_id = locations.id)\n" +
            "WHERE antiques.price >= " + average + ";";
        connectToDatabase();
        setStatement(sql);
        setResult();
        setAntiques();
        closeConnection();
        return antiques;
    }


    private void getAverage() {
        String sql = "SELECT AVG(price) FROM antiques;";
        connectToDatabase();
        setStatement(sql);
        setResult();
        average = setLogicQueries();
        closeConnection();
    }

    private void connectToDatabase() {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/fourth?autoReconnect=true&useSSL=false", "root", "");
        }
        catch (SQLException e) {
            System.out.println("Bad connection with the database.\n" + e);
        }
    }

    private void closeConnection() {
        try {
            connection.close();
        }
        catch (SQLException e) {
            System.out.println("Bad disconnect from the database.\n" +e);
        }
    }

    private void setStatement(String sql) {
        try {
            statement = connection.prepareStatement(sql);
        }
        catch (SQLException e) {
            System.out.println("Bad statement.\n" + e);
        }
    }

    private void setResult() {
        try {
            result = statement.executeQuery();
        }
        catch (SQLException e) {
            System.out.println("Bad result.\n" + e);
        }
    }

    private void setAntiques() {
        antiques = FXCollections.observableArrayList();
        Antique temp;
        try {
            while(result.next()) {
                temp = new Antique(
                    result.getInt("id"),
                    result.getString("name"),
                    result.getString("condition"),
                    result.getDouble("price"),
                    result.getString("location"),
                    result.getString("date")
                );
                antiques.add(temp);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void setLocations() {
        locations = FXCollections.observableArrayList();
        Location temp;
        try {
            while(result.next()) {
                temp = new Location(
                    result.getInt("id"),
                    result.getString("location")
                );
                locations.add(temp);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void setConditions() {
        conditions = FXCollections.observableArrayList();
        Condition temp;
        try {
            while(result.next()) {
                temp = new Condition(
                    result.getInt("id"),
                    result.getString("condition")
                );
                conditions.add(temp);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private String setLogicQueries() {
        String queryRez = null;
        try {
            while(result.next()) {
                queryRez = result.getString(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return queryRez;
    }

    private void formatString() {
        sum = String.format("%.2f", Double.parseDouble(sum));
    }

}
