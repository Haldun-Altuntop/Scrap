package arc.haldun.hurda.database.objects;

import org.json.JSONException;
import org.json.JSONObject;

public class Scrap {

    private String p01_name;
    private double p02_price, p03_stock, p04_C, p05_Si, p06_Mn, p07_P, p08_S, p09_Fe, p10_O, p11_H2O, p12_CaO,
            p13_MgO, p14_Al2O3, p15_SiO2, p16_Cu, p17_Ni, p18_Cr, p19_Sn, p20_Mo, p21_slag, p22_yield, p23_dH;

    public Scrap(String p01_name, double p02_price, double p03_stock, double p04_C, double p05_Si, double p06_Mn,
                 double p07_P, double p08_S, double p09_Fe, double p10_O, double p11_H2O, double p12_CaO,
                 double p13_MgO, double p14_Al2O3, double p15_SiO2, double p16_Cu, double p17_Ni, double p18_Cr,
                 double p19_Sn, double p20_Mo, double p21_slag, double p22_yield, double p23_dH) {
        this.p01_name = p01_name;
        this.p02_price = p02_price;
        this.p03_stock = p03_stock;
        this.p04_C = p04_C;
        this.p05_Si = p05_Si;
        this.p06_Mn = p06_Mn;
        this.p07_P = p07_P;
        this.p08_S = p08_S;
        this.p09_Fe = p09_Fe;
        this.p10_O = p10_O;
        this.p11_H2O = p11_H2O;
        this.p12_CaO = p12_CaO;
        this.p13_MgO = p13_MgO;
        this.p14_Al2O3 = p14_Al2O3;
        this.p15_SiO2 = p15_SiO2;
        this.p16_Cu = p16_Cu;
        this.p17_Ni = p17_Ni;
        this.p18_Cr = p18_Cr;
        this.p19_Sn = p19_Sn;
        this.p20_Mo = p20_Mo;
        this.p21_slag = p21_slag;
        this.p22_yield = p22_yield;
        this.p23_dH = p23_dH;
    }

    public Scrap(JSONObject json) {

        p01_name = json.optString("p01_name");
        p02_price = json.optDouble("p02_price", 0);
        p03_stock = json.optDouble("p03_stock", 0);
        p04_C = json.optDouble("p04_C", 0);
        p05_Si = json.optDouble("p05_Si", 0);
        p06_Mn = json.optDouble("p06_Mn", 0);
        p07_P = json.optDouble("p07_P", 0);
        p08_S = json.optDouble("p08_S", 0);
        p09_Fe = json.optDouble("p09_Fe", 0);
        p10_O = json.optDouble("p10_O", 0);
        p11_H2O = json.optDouble("p11_H2O", 0);
        p12_CaO = json.optDouble("p12_CaO", 0);
        p13_MgO = json.optDouble("p13_MgO", 0);
        p14_Al2O3 = json.optDouble("p14_Al2O3", 0);
        p15_SiO2 = json.optDouble("p15_SiO2", 0);
        p16_Cu = json.optDouble("p16_Cu", 0);
        p17_Ni = json.optDouble("p17_Ni", 0);
        p18_Cr = json.optDouble("p18_Cr", 0);
        p19_Sn = json.optDouble("p19_Sn", 0);
        p20_Mo = json.optDouble("p20_Mo", 0);
        p21_slag = json.optDouble("p21_slag", 0);
        p22_yield = json.optDouble("p22_yield", 0);
        p23_dH = json.optDouble("p23_dH", 0);
    }

    public String getP01_name() {
        return p01_name;
    }

    public void setP01_name(String p01_name) {
        this.p01_name = p01_name;
    }

    public double getP02_price() {
        return p02_price;
    }

    public void setP02_price(double p02_price) {
        this.p02_price = p02_price;
    }

    public double getP03_stock() {
        return p03_stock;
    }

    public void setP03_stock(double p03_stock) {
        this.p03_stock = p03_stock;
    }

    public double getP04_C() {
        return p04_C;
    }

    public void setP04_C(double p04_C) {
        this.p04_C = p04_C;
    }

    public double getP05_Si() {
        return p05_Si;
    }

    public void setP05_Si(double p05_Si) {
        this.p05_Si = p05_Si;
    }

    public double getP06_Mn() {
        return p06_Mn;
    }

    public void setP06_Mn(double p06_Mn) {
        this.p06_Mn = p06_Mn;
    }

    public double getP07_P() {
        return p07_P;
    }

    public void setP07_P(double p07_P) {
        this.p07_P = p07_P;
    }

    public double getP08_S() {
        return p08_S;
    }

    public void setP08_S(double p08_S) {
        this.p08_S = p08_S;
    }

    public double getP09_Fe() {
        return p09_Fe;
    }

    public void setP09_Fe(double p09_Fe) {
        this.p09_Fe = p09_Fe;
    }

    public double getP10_O() {
        return p10_O;
    }

    public void setP10_O(double p10_O) {
        this.p10_O = p10_O;
    }

    public double getP11_H2O() {
        return p11_H2O;
    }

    public void setP11_H2O(double p11_H2O) {
        this.p11_H2O = p11_H2O;
    }

    public double getP12_CaO() {
        return p12_CaO;
    }

    public void setP12_CaO(double p12_CaO) {
        this.p12_CaO = p12_CaO;
    }

    public double getP13_MgO() {
        return p13_MgO;
    }

    public void setP13_MgO(double p13_MgO) {
        this.p13_MgO = p13_MgO;
    }

    public double getP14_Al2O3() {
        return p14_Al2O3;
    }

    public void setP14_Al2O3(double p14_Al2O3) {
        this.p14_Al2O3 = p14_Al2O3;
    }

    public double getP15_SiO2() {
        return p15_SiO2;
    }

    public void setP15_SiO2(double p15_SiO2) {
        this.p15_SiO2 = p15_SiO2;
    }

    public double getP16_Cu() {
        return p16_Cu;
    }

    public void setP16_Cu(double p16_Cu) {
        this.p16_Cu = p16_Cu;
    }

    public double getP17_Ni() {
        return p17_Ni;
    }

    public void setP17_Ni(double p17_Ni) {
        this.p17_Ni = p17_Ni;
    }

    public double getP18_Cr() {
        return p18_Cr;
    }

    public void setP18_Cr(double p18_Cr) {
        this.p18_Cr = p18_Cr;
    }

    public double getP19_Sn() {
        return p19_Sn;
    }

    public void setP19_Sn(double p19_Sn) {
        this.p19_Sn = p19_Sn;
    }

    public double getP20_Mo() {
        return p20_Mo;
    }

    public void setP20_Mo(double p20_Mo) {
        this.p20_Mo = p20_Mo;
    }

    public double getP21_slag() {
        return p21_slag;
    }

    public void setP21_slag(double p21_slag) {
        this.p21_slag = p21_slag;
    }

    public double getP22_yield() {
        return p22_yield;
    }

    public void setP22_yield(double p22_yield) {
        this.p22_yield = p22_yield;
    }

    public double getP23_dH() {
        return p23_dH;
    }

    public void setP23_dH(double p23_dH) {
        this.p23_dH = p23_dH;
    }

    public JSONObject toJson() {

        try {
            JSONObject jsonObject = new JSONObject();

            jsonObject.put("p01_name", p01_name);
            jsonObject.put("p02_price", p02_price);
            jsonObject.put("p03_stock", p03_stock);
            jsonObject.put("p04_C", p04_C);
            jsonObject.put("p05_Si", p05_Si);
            jsonObject.put("p06_Mn", p06_Mn);
            jsonObject.put("p07_P", p07_P);
            jsonObject.put("p08_S", p08_S);
            jsonObject.put("p09_Fe", p09_Fe);
            jsonObject.put("p10_O", p10_O);
            jsonObject.put("p11_H2O", p11_H2O);
            jsonObject.put("p12_CaO", p12_CaO);
            jsonObject.put("p13_MgO", p13_MgO);
            jsonObject.put("p14_Al2O3", p14_Al2O3);
            jsonObject.put("p15_SiO2", p15_SiO2);
            jsonObject.put("p16_Cu", p16_Cu);
            jsonObject.put("p17_Ni", p17_Ni);
            jsonObject.put("p18_Cr", p18_Cr);
            jsonObject.put("p19_Sn", p19_Sn);
            jsonObject.put("p20_Mo", p20_Mo);
            jsonObject.put("p21_slag", p21_slag);
            jsonObject.put("p22_yield", p22_yield);
            jsonObject.put("p23_dH", p23_dH);

            return jsonObject;

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String toString() {
        //return getP01_name();

        try {
            return toJson().toString(4);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

    }
}
