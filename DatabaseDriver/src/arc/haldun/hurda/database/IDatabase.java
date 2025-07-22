package arc.haldun.hurda.database;

import arc.haldun.hurda.database.objects.ChargeMix;
import arc.haldun.hurda.database.objects.GeneralParameter;
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

    void addChargeMix(ChargeMix cm) throws OperationFailedException;
    void updateChargeMix(ChargeMix cm) throws OperationFailedException;
    ChargeMix[] getAllChargeMixes() throws OperationFailedException;
    ChargeMix getChargeMix(String chargeMixName) throws OperationFailedException;
    void deleteChargeMix(String chargeMixName) throws OperationFailedException;

    void addGeneralParameter(GeneralParameter parameter) throws OperationFailedException;
    void updateGeneralParameter(GeneralParameter parameter) throws OperationFailedException;
    GeneralParameter[] getAllGeneralParameters() throws OperationFailedException;
    GeneralParameter getGeneralParameter(String parameterName) throws OperationFailedException;
    void deleteGeneralParameter(String parameterName) throws OperationFailedException;
}
