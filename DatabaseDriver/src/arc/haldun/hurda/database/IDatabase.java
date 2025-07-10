package arc.haldun.hurda.database;

import arc.haldun.hurda.database.objects.Scrap;
import arc.haldun.hurda.database.objects.User;

import java.sql.SQLException;

public interface IDatabase {

    void addScrap(Scrap scrap) throws SQLException, OperationFailedException;
    void updateScrap(Scrap scrap) throws SQLException, OperationFailedException;
    Scrap[] getAllScraps() throws SQLException;
    Scrap getScrap(String scrapName) throws SQLException, OperationFailedException;
    void deleteScrap(String scrapName) throws SQLException, OperationFailedException;

    void addUser(User user) throws OperationFailedException;
    void updateUser(User user) throws OperationFailedException;
    User[] getAllUsers();
    User getUser(int id) throws OperationFailedException;
    void deleteUser(int id) throws OperationFailedException;
    boolean login(User user);
}
