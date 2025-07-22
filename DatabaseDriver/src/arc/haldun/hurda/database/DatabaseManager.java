package arc.haldun.hurda.database;

import arc.haldun.hurda.database.objects.ChargeMix;
import arc.haldun.hurda.database.objects.GeneralParameter;
import arc.haldun.hurda.database.objects.Scrap;
import arc.haldun.hurda.database.objects.User;

import java.sql.SQLException;

public class DatabaseManager {

    private static IDatabase database;

    public static void initDatabase(IDatabase database) {
        DatabaseManager.database = database;
    }

    public static void addScrap(Scrap scrap, IDatabase database) throws SQLException, OperationFailedException {
        database.addScrap(scrap);
    }

    public static void addScrap(Scrap scrap) throws SQLException, OperationFailedException {
        addScrap(scrap, database);
    }

    public static void updateScrap(Scrap scrap) throws SQLException, OperationFailedException {
        updateScrap(scrap, database);
    }

    public static void updateScrap(Scrap scrap, IDatabase database) throws SQLException, OperationFailedException {
        database.updateScrap(scrap);
    }

    public static Scrap[] getAllScraps(IDatabase database) throws SQLException {
        return database.getAllScraps();
    }

    public static Scrap[] getAllScraps() throws SQLException {
        return getAllScraps(database);
    }

    public static Scrap getScrap(String scrapName, IDatabase database) throws SQLException, OperationFailedException {
        return database.getScrap(scrapName);
    }

    public static Scrap getScrap(String scrapName) throws SQLException, OperationFailedException {
        return getScrap(scrapName, database);
    }

    public static void deleteScrap(String scrapName, IDatabase database) throws SQLException, OperationFailedException {
        database.deleteScrap(scrapName);
    }

    public static void deleteScrap(String scrapName) throws SQLException, OperationFailedException {
        deleteScrap(scrapName, database);
    }

    //**************************************** USER **************************************

    public static void addUser(User user, IDatabase database) throws OperationFailedException {
        database.addUser(user);
    }

    public static void addUser(User user) throws OperationFailedException {
        addUser(user, database);
    }

    public static void updateUser(User user, IDatabase database) throws OperationFailedException {
        database.updateUser(user);
    }

    public static void updateUser(User user) throws OperationFailedException {
        updateUser(user, database);
    }

    public static User[] getAllUsers(IDatabase database) {
        return database.getAllUsers();
    }

    public static User[] getAllUsers() {
        return getAllUsers(database);
    }

    public static User getUser(int id) throws OperationFailedException {
        return getUser(id, database);
    }

    public static User getUser(int id, IDatabase database) throws OperationFailedException {
        return database.getUser(id);
    }

    public static void deleteUser(int id, IDatabase database) throws OperationFailedException {
        database.deleteUser(id);
    }

    public void deleteUser(int id) throws OperationFailedException {
        deleteUser(id, database);
    }

    public static boolean login(User user, IDatabase database) {
        return database.login(user);
    }

    public static boolean login(User user) {
        return login(user, database);
    }

    public static void addChargeMix(ChargeMix cm, IDatabase database) throws OperationFailedException {
        database.addChargeMix(cm);
    }

    public static void addChargeMix(ChargeMix cm) throws OperationFailedException {
        addChargeMix(cm, database);
    }

    public static void updateChargeMix(ChargeMix cm, IDatabase database) throws OperationFailedException {
        database.updateChargeMix(cm);
    }

    public static void updateChargeMix(ChargeMix cm) throws OperationFailedException {
        updateChargeMix(cm, database);
    }

    public static ChargeMix[] getAllChargeMixes(IDatabase database) throws OperationFailedException {
        return database.getAllChargeMixes();
    }

    public static ChargeMix[] getAllChargeMixes() throws OperationFailedException {
        return getAllChargeMixes(database);
    }

    public static void deleteChargeMix(String chargeMixName, IDatabase database) throws OperationFailedException {
        database.deleteChargeMix(chargeMixName);
    }

    public static void deleteChargeMix(String chargeMixName) throws OperationFailedException {
        deleteChargeMix(chargeMixName, database);
    }

    public static void addGeneralParameter(GeneralParameter parameter, IDatabase database) throws OperationFailedException {
        database.addGeneralParameter(parameter);
    }

    public static void addGeneralParameter(GeneralParameter parameter) throws OperationFailedException {
        addGeneralParameter(parameter, database);
    }

    public static void updateGeneralParameter(GeneralParameter parameter, IDatabase database) throws OperationFailedException {
        database.updateGeneralParameter(parameter);
    }

    public static void updateGeneralParameter(GeneralParameter parameter) throws OperationFailedException {
        updateGeneralParameter(parameter, database);
    }

    public static GeneralParameter[] getAllGeneralParameters(IDatabase database) throws OperationFailedException {
        return database.getAllGeneralParameters();
    }

    public static GeneralParameter[] getAllGeneralParameters() throws OperationFailedException {
        return getAllGeneralParameters(database);
    }

    public static GeneralParameter getGeneralParameter(String parameterName, IDatabase database) throws OperationFailedException {
        return database.getGeneralParameter(parameterName);
    }

    public static GeneralParameter getGeneralParameter(String parameterName) throws OperationFailedException {
        return getGeneralParameter(parameterName, database);
    }

    public static void deleteGeneralParameter(String parameterName, IDatabase database) throws OperationFailedException {
        database.deleteGeneralParameter(parameterName);
    }

    public static void deleteGeneralParameter(String parameterName) throws OperationFailedException {
        deleteGeneralParameter(parameterName, database);
    }
}
