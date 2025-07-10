package arc.haldun.hurda.database;

import arc.haldun.hurda.database.objects.Scrap;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Test {

    public static void main(String[] args) throws IOException, JSONException {

        /*
        Connector.connect("cryogold.com.tr", "cryogold_com_tr_elib", "haldun",
                "E-LibDatabaseUserHaldun");

         */

        Scanner scanner = new Scanner(System.in);
        ArrayList<Scrap> scraps = new ArrayList<>();

        boolean devam = true;
        do {
            Scrap scrap = getScrap(scanner);
            scraps.add(scrap);

            System.out.println("Yeni hurda? (e/h)");
            String s = scanner.nextLine();
            s = s.toLowerCase();
            devam = s.equals("e");
        } while (devam);

        JSONObject scrapsJson = new JSONObject();

        for (int i = 0; i < scraps.size(); i++) {
            scrapsJson.put(String.valueOf(i), scraps.get(i).toJson());
        }

        new FileWriter("scrap-store.json").write(scrapsJson.toString(4));
    }

    private static Scrap getScrap(Scanner scanner) {
        String p01_name;
        double p02_price, p03_stock, p04_C, p05_Si, p06_Mn, p07_P, p08_S, p09_Fe, p10_O, p11_H2O, p12_CaO,
                p13_MgO, p14_Al2O3, p15_SiO2, p16_Cu, p17_Ni, p18_Cr, p19_Sn, p20_Mo, p21_slag, p22_yield, p23_dH;

        System.out.print("Name: ");
        p01_name = scanner.nextLine();

        System.out.print("Price: ");
        p02_price = Double.parseDouble(scanner.nextLine());

        System.out.print("Stock: ");
        p03_stock = Double.parseDouble(scanner.nextLine());

        System.out.print("C: ");
        p04_C = Double.parseDouble(scanner.nextLine());

        System.out.print("Si: ");
        p05_Si = Double.parseDouble(scanner.nextLine());

        System.out.print("Mn: ");
        p06_Mn = Double.parseDouble(scanner.nextLine());

        System.out.print("P: ");
        p07_P = Double.parseDouble(scanner.nextLine());

        System.out.print("S: ");
        p08_S = Double.parseDouble(scanner.nextLine());

        System.out.print("Fe: ");
        p09_Fe = Double.parseDouble(scanner.nextLine());

        System.out.print("O: ");
        p10_O = Double.parseDouble(scanner.nextLine());

        System.out.print("H2O: ");
        p11_H2O = Double.parseDouble(scanner.nextLine());

        System.out.print("CaO: ");
        p12_CaO = Double.parseDouble(scanner.nextLine());

        System.out.print("MgO: ");
        p13_MgO = Double.parseDouble(scanner.nextLine());

        System.out.print("Al2O3: ");
        p14_Al2O3 = Double.parseDouble(scanner.nextLine());

        System.out.print("SiO2: ");
        p15_SiO2 = Double.parseDouble(scanner.nextLine());

        System.out.print("Cu: ");
        p16_Cu = Double.parseDouble(scanner.nextLine());

        System.out.print("Ni: ");
        p17_Ni = Double.parseDouble(scanner.nextLine());

        System.out.print("Cr: ");
        p18_Cr = Double.parseDouble(scanner.nextLine());

        System.out.print("Sn: ");
        p19_Sn = Double.parseDouble(scanner.nextLine());

        System.out.print("Mo: ");
        p20_Mo = Double.parseDouble(scanner.nextLine());

        System.out.print("Slag: ");
        p21_slag = Double.parseDouble(scanner.nextLine());

        System.out.print("Yield: ");
        p22_yield = Double.parseDouble(scanner.nextLine());

        System.out.print("dH: ");
        p23_dH = Double.parseDouble(scanner.nextLine());

        return new Scrap(p01_name, p02_price, p03_stock, p04_C, p05_Si, p06_Mn, p07_P, p08_S, p09_Fe, p10_O,
                p11_H2O, p12_CaO, p13_MgO, p14_Al2O3, p15_SiO2, p16_Cu, p17_Ni, p18_Cr, p19_Sn, p20_Mo, p21_slag,
                p22_yield, p23_dH);
    }

    public static Scrap[] prepareScraps() {

        ArrayList<Scrap> scraps = new ArrayList<>();

        scraps.add(new Scrap(
                "HMS",
                0,
                0,
                0.15,
                0.20,
                0.50,
                0.04,
                0.04,
                93.8,
                1.50,
                0.50,
                0.30,
                0.10,
                0.60,
                2,
                0.12,
                0.07,
                0.11,
                0.01,
                0.01,
                120,
                91.4,
                394
        ));

        scraps.add(new Scrap(
                "Shredded",
                0,
                0,
                0.60,
                0.30,
                0.43,
                0.05,
                0.04,
                94.6,
                0.40,
                1,
                0.20,
                0.33,
                0.50,
                1,
                0.25,
                0.60,
                0.19,
                0.015,
                0.005,
                110,
                92.5,
                374.6
        ));

        scraps.add(new Scrap(
                "HC Scrap",
                0,
                0,
                4.25,
                0.80,
                0.60,
                0.09,
                0.10,
                91.5,
                0.10,
                0.01,
                0.60,
                0.40,
                0.40,
                1.20,
                0,
                0,
                0,
                0,
                0,
                132,
                88.9,
                361
        ));

        return scraps.toArray(new Scrap[0]);
    }
}
