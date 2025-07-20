package arc.haldun.hurda.database;

import arc.haldun.hurda.database.objects.ChargeMix;
import arc.haldun.hurda.database.objects.Scrap;
import arc.haldun.hurda.database.objects.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MariaDB  implements IDatabase{

    @Override
    public void addScrap(Scrap scrap) throws SQLException, OperationFailedException {

        String sql = "INSERT INTO hurdalar (" +
                "p01_name," +
                "p02_price," +
                "p03_stock," +
                "p04_C," +
                "p05_Si," +
                "p06_Mn," +
                "p07_P," +
                "p08_S," +
                "p09_Fe," +
                "p10_O," +
                "p11_H2O," +
                "p12_CaO," +
                "p13_MgO," +
                "p14_Al2O3," +
                "p15_SiO2," +
                "p16_Cu," +
                "p17_Ni," +
                "p18_Cr," +
                "p19_Sn," +
                "p20_Mo," +
                "p21_slag," +
                "p22_yield," +
                "p23_dH" +
                ")" +
                "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        PreparedStatement preparedStatement = Connector.getConnection().prepareStatement(sql);

        try (preparedStatement) {
            preparedStatement.setString(1, scrap.getP01_name());
            preparedStatement.setDouble(2, scrap.getP02_price());
            preparedStatement.setDouble(3, scrap.getP03_stock());
            preparedStatement.setDouble(4, scrap.getP04_C());
            preparedStatement.setDouble(5, scrap.getP05_Si());
            preparedStatement.setDouble(6, scrap.getP06_Mn());
            preparedStatement.setDouble(7, scrap.getP07_P());
            preparedStatement.setDouble(8, scrap.getP08_S());
            preparedStatement.setDouble(9, scrap.getP09_Fe());
            preparedStatement.setDouble(10, scrap.getP10_O());
            preparedStatement.setDouble(11, scrap.getP11_H2O());
            preparedStatement.setDouble(12, scrap.getP12_CaO());
            preparedStatement.setDouble(13, scrap.getP13_MgO());
            preparedStatement.setDouble(14, scrap.getP14_Al2O3());
            preparedStatement.setDouble(15, scrap.getP15_SiO2());
            preparedStatement.setDouble(16, scrap.getP16_Cu());
            preparedStatement.setDouble(17, scrap.getP17_Ni());
            preparedStatement.setDouble(18, scrap.getP18_Cr());
            preparedStatement.setDouble(19, scrap.getP19_Sn());
            preparedStatement.setDouble(20, scrap.getP20_Mo());
            preparedStatement.setDouble(21, scrap.getP21_slag());
            preparedStatement.setDouble(22, scrap.getP22_yield());
            preparedStatement.setDouble(23, scrap.getP23_dH());
            int result = preparedStatement.executeUpdate();
            if (result == 0) throw new OperationFailedException("Hurda eklenirken bir sorun oluştu.");

        } catch (SQLIntegrityConstraintViolationException e) {
            throw new OperationFailedException(scrap.getP01_name() + " adında bir hurda zaten var");
        }
    }

    @Override
    public void updateScrap(Scrap scrap) throws SQLException, OperationFailedException {

        String sql = "UPDATE hurdalar SET " +
                "p01_name = ?," +
                "p02_price = ?," +
                "p03_stock = ?," +
                "p04_C = ?," +
                "p05_Si = ?," +
                "p06_Mn = ?," +
                "p07_P = ?," +
                "p08_S = ?," +
                "p09_Fe = ?," +
                "p10_O = ?," +
                "p11_H2O = ?," +
                "p12_CaO = ?," +
                "p13_MgO = ?," +
                "p14_Al2O3 = ?," +
                "p15_SiO2 = ?," +
                "p16_Cu = ?," +
                "p17_Ni = ?," +
                "p18_Cr = ?," +
                "p19_Sn = ?," +
                "p20_Mo = ?," +
                "p21_slag = ?," +
                "p22_yield = ?," +
                "p23_dH = ? " +
                "WHERE p01_name = ?";

        PreparedStatement preparedStatement = Connector.getConnection().prepareStatement(sql);
        preparedStatement.setString(1, scrap.getP01_name());
        preparedStatement.setDouble(2, scrap.getP02_price());
        preparedStatement.setDouble(3, scrap.getP03_stock());
        preparedStatement.setDouble(4, scrap.getP04_C());
        preparedStatement.setDouble(5, scrap.getP05_Si());
        preparedStatement.setDouble(6, scrap.getP06_Mn());
        preparedStatement.setDouble(7, scrap.getP07_P());
        preparedStatement.setDouble(8, scrap.getP08_S());
        preparedStatement.setDouble(9, scrap.getP09_Fe());
        preparedStatement.setDouble(10, scrap.getP10_O());
        preparedStatement.setDouble(11, scrap.getP11_H2O());
        preparedStatement.setDouble(12, scrap.getP12_CaO());
        preparedStatement.setDouble(13, scrap.getP13_MgO());
        preparedStatement.setDouble(14, scrap.getP14_Al2O3());
        preparedStatement.setDouble(15, scrap.getP15_SiO2());
        preparedStatement.setDouble(16, scrap.getP16_Cu());
        preparedStatement.setDouble(17, scrap.getP17_Ni());
        preparedStatement.setDouble(18, scrap.getP18_Cr());
        preparedStatement.setDouble(19, scrap.getP19_Sn());
        preparedStatement.setDouble(20, scrap.getP20_Mo());
        preparedStatement.setDouble(21, scrap.getP21_slag());
        preparedStatement.setDouble(22, scrap.getP22_yield());
        preparedStatement.setDouble(23, scrap.getP23_dH());
        preparedStatement.setString(24, scrap.getP01_name());

        int result = preparedStatement.executeUpdate();

        preparedStatement.close();

        if (result == 0) throw new OperationFailedException("Hurda bilgileri güncellenemedi: " + scrap.getP01_name());
    }

    @Override
    public Scrap[] getAllScraps() throws SQLException {

        ArrayList<Scrap> scraps = new ArrayList<>();

        String sql = "SELECT * FROM hurdalar";

        Statement statement = Connector.getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery(sql);

        while (resultSet.next()) {

            Scrap scrap = new Scrap(
                    resultSet.getString("p01_name"),
                    resultSet.getDouble("p02_price"),
                    resultSet.getDouble("p03_stock"),
                    resultSet.getDouble("p04_C"),
                    resultSet.getDouble("p05_Si"),
                    resultSet.getDouble("p06_Mn"),
                    resultSet.getDouble("p07_P"),
                    resultSet.getDouble("p08_S"),
                    resultSet.getDouble("p09_Fe"),
                    resultSet.getDouble("p10_O"),
                    resultSet.getDouble("p11_H2O"),
                    resultSet.getDouble("p12_CaO"),
                    resultSet.getDouble("p13_MgO"),
                    resultSet.getDouble("p14_Al2O3"),
                    resultSet.getDouble("p15_SiO2"),
                    resultSet.getDouble("p16_Cu"),
                    resultSet.getDouble("p17_Ni"),
                    resultSet.getDouble("p18_Cr"),
                    resultSet.getDouble("p19_Sn"),
                    resultSet.getDouble("p20_Mo"),
                    resultSet.getDouble("p21_slag"),
                    resultSet.getDouble("p22_yield"),
                    resultSet.getDouble("p23_dH")
            );

            scraps.add(scrap);
        }

        statement.close();

        return scraps.toArray(new Scrap[0]);
    }

    @Override
    public Scrap getScrap(String scrapName) throws SQLException, OperationFailedException {

        String sql = "SELECT * FROM hurdalar";

        Statement statement = Connector.getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery(sql);

        while (resultSet.next()) {

            if (resultSet.getString("p01_name").equals(scrapName)) {

                Scrap scrap = new Scrap(
                        resultSet.getString("p01_name"),
                        resultSet.getDouble("p02_price"),
                        resultSet.getDouble("p03_stock"),
                        resultSet.getDouble("p04_C"),
                        resultSet.getDouble("p05_Si"),
                        resultSet.getDouble("p06_Mn"),
                        resultSet.getDouble("p07_P"),
                        resultSet.getDouble("p08_S"),
                        resultSet.getDouble("p09_Fe"),
                        resultSet.getDouble("p10_O"),
                        resultSet.getDouble("p11_H2O"),
                        resultSet.getDouble("p12_CaO"),
                        resultSet.getDouble("p13_MgO"),
                        resultSet.getDouble("p14_Al2O3"),
                        resultSet.getDouble("p15_SiO2"),
                        resultSet.getDouble("p16_Cu"),
                        resultSet.getDouble("p17_Ni"),
                        resultSet.getDouble("p18_Cr"),
                        resultSet.getDouble("p19_Sn"),
                        resultSet.getDouble("p20_Mo"),
                        resultSet.getDouble("p21_slag"),
                        resultSet.getDouble("p22_yield"),
                        resultSet.getDouble("p23_dH")
                );

                statement.close();

                return scrap;
            }
        }

        throw new OperationFailedException("Aranan hurda bulunamadı: " + scrapName);
    }

    @Override
    public void deleteScrap(String scrapName) throws SQLException, OperationFailedException {

        String sql = "DELETE FROM hurdalar WHERE p01_name=?";

        PreparedStatement preparedStatement = Connector.getConnection().prepareStatement(sql);
        preparedStatement.setString(1, scrapName);

        int result = preparedStatement.executeUpdate();

        preparedStatement.close();

        System.out.println(result);

        if (result == 0) {
            throw new OperationFailedException("Hurda silinemedi: " + scrapName);
        }
    }

    @Override
    public void addUser(User user) throws OperationFailedException {

        try {
            String sql = "INSERT INTO hurda_users (name, password) VALUES (?, ?)";

            PreparedStatement preparedStatement = Connector.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getPassword());

            int result = preparedStatement.executeUpdate(sql);

            if (result == 0) throw new OperationFailedException("Kullanıcı eklenirken bir sorun oluştu.");

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                int userId = generatedKeys.getInt(1);
                user.setId(userId);
            }

            preparedStatement.close();

        } catch (SQLException e) {
            throw new OperationFailedException(e);
        }
    }

    @Override
    public void updateUser(User user) throws OperationFailedException {

        try {
            String sql = "UPDATE hurda_users " +
                    "name = ?," +
                    "password = ?," +
                    "company = ? " +
                    "WHERE id = ?";

            Statement statement = Connector.getConnection().createStatement();
            int result = statement.executeUpdate(sql);
            statement.close();

            if (result == 0) throw new OperationFailedException(
                    String.format("Kullanıcı güncellenemedi. Id değeri doğru mu? (id=%s)", user.getId())
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public User[] getAllUsers() {

        try {
            String sql = "SELECT * FROM hurda_users";

            Statement statement = Connector.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            ArrayList<User> users = new ArrayList<>();

            while (resultSet.next()) {

                User user = new User(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("password")
                );

                users.add(user);
            }

            statement.close();

            return users.toArray(new User[0]);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public User getUser(int id) throws OperationFailedException {

        try {
            String sql = "SELECT * FROM hurda_users WHERE id = ?";

            PreparedStatement preparedStatement = Connector.getConnection().prepareStatement(sql);
            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {

                preparedStatement.close();

                return new User(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("password")
                );
            } else {
                preparedStatement.close();
                throw new OperationFailedException(String.format("Kullanıcı bulunamadı (id = %s)", id));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteUser(int id) throws OperationFailedException {

        try {
            String sql = "DELETE FROM hurda_users WHERE id = ?";

            PreparedStatement preparedStatement = Connector.getConnection().prepareStatement(sql);
            preparedStatement.setInt(1, id);

            int result = preparedStatement.executeUpdate();
            preparedStatement.close();

            if (result == 0) throw new OperationFailedException("Kullanıcı silinemedi: id = " + id);
            if (result > 1) System.err.println(result + " SATIR SİLİNDİ");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean login(User user) {

        try {
            String sql = "SELECT * FROM hurda_users WHERE " +
                    "name = " + user.getName() + ", " +
                    "password = " + user.getPassword();

            Statement statement = Connector.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            boolean res = resultSet.next();
            resultSet.close();

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                int userId = generatedKeys.getInt(1);
                user.setId(userId);
            }
            statement.close();

            return res;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void addChargeMix(ChargeMix cm) throws OperationFailedException {

        String sql = "INSERT INTO charge_mix " +
                "(p01_name,p02_unit_rate,p03_percentage,p04_melt_factor) " +
                "VALUES (?,?,?,?)";

        try (PreparedStatement preparedStatement = Connector.getConnection().prepareStatement(sql)) {

            preparedStatement.setString(1, cm.getP01_name());
            preparedStatement.setDouble(2, cm.getP02_unitRate());
            preparedStatement.setDouble(3, cm.getP03_percentage());
            preparedStatement.setDouble(4, cm.getP04_meltFactor());

            preparedStatement.execute();

        } catch (SQLException e) {
            if (e instanceof SQLIntegrityConstraintViolationException)
                throw new OperationFailedException(cm.getP01_name() + " adında Charge Mix zaten mevcut");
            else throw new OperationFailedException(e);
        }
    }

    @Override
    public void updateChargeMix(ChargeMix cm) throws OperationFailedException {

        String sql = "UPDATE charge_mix SET " +
                "p02_unit_rate=?," +
                "p03_percentage=?," +
                "p04_melt_factor=? " +
                "WHERE p01_name=" + cm.getP01_name();

        try (PreparedStatement preparedStatement = Connector.getConnection().prepareStatement(sql)) {

            preparedStatement.setDouble(1, cm.getP02_unitRate());
            preparedStatement.setDouble(2, cm.getP03_percentage());
            preparedStatement.setDouble(3, cm.getP04_meltFactor());

            int affectedRows = preparedStatement.executeUpdate(sql);

            if (affectedRows == 0) {
                throw new OperationFailedException("Charge Mix güncellenemedi: " + cm.getP01_name());
            }

        } catch (SQLException e) {
            throw new OperationFailedException(e);
        }
    }

    @Override
    public ChargeMix[] getAllChargeMixes() throws OperationFailedException {

        List<ChargeMix> chargeMixes = new ArrayList<>();

        String sql = "SELECT * FROM charge_mix";

        try (Statement statement = Connector.getConnection().createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                String name = resultSet.getString(1);
                double unitRate = resultSet.getDouble(2);
                double percentage = resultSet.getDouble(3);
                double meltFactor = resultSet.getDouble(4);

                chargeMixes.add(new ChargeMix(
                        name,
                        unitRate,
                        percentage,
                        meltFactor
                ));
            }

        } catch (SQLException e) {
            throw new OperationFailedException(e);
        }

        return chargeMixes.toArray(new ChargeMix[0]);
    }

    @Override
    public ChargeMix getChargeMix(String chargeMixName) throws OperationFailedException {

        String sql = "SELECT * FROM charge_mixes WHERE p01_name='?'";

        try (PreparedStatement preparedStatement = Connector.getConnection().prepareStatement(sql)) {
            preparedStatement.setString(1, chargeMixName);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return new ChargeMix(
                        resultSet.getString("p01_name"),
                        resultSet.getDouble("p02_unit_rate"),
                        resultSet.getDouble("p03_percentage"),
                        resultSet.getDouble("p04_melt_factor")
                );
            } else throw new OperationFailedException("Charge mix bulunamadı: " + chargeMixName);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteChargeMix(String chargeMixName) throws OperationFailedException {

        String sql = "DELETE FROM charge_mixes WHERE p01_name=" + chargeMixName;

        try (Statement statement = Connector.getConnection().createStatement()) {
            int affectedRows = statement.executeUpdate(sql);

            if (affectedRows == 0) throw new OperationFailedException("Charge Mix silinemedi: " + chargeMixName);
            else if (affectedRows > 1) throw new OperationFailedException("SQL HATASI! BİRDEN FAZLA SATIR SİLİNDİ!");
        } catch (SQLException e) {
            throw new OperationFailedException(e);
        }
    }
}
