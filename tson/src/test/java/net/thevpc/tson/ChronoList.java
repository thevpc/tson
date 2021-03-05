package net.thevpc.tson;

import net.thevpc.tson.impl.util.Chrono;

import java.util.ArrayList;
import java.util.List;

public class ChronoList {
    private List<Chrono> all = new ArrayList<>();
    private boolean verbose;

    public ChronoList(boolean verbose) {
        this.verbose = verbose;
    }

    public void add(Chrono c) {
        all.add(c);
        if (verbose) {
            System.out.println(c);
        }
    }


    public void podium() {
        if (verbose) {
            System.out.println("");
            System.out.println(" --- PODIUM --- ");
            all.sort(null);
            List<List<String>> rows=new ArrayList<>();
            Col col1=new Col();
            Col col2=new Col();
            Col col3=new Col();
            Col col4=new Col();
            long best=all.get(0).nanos();
            List<String> header=new ArrayList<>();
            header.add(col1.reg("Parser"));
            header.add(col2.reg("Time(s)"));
            header.add(col3.reg("Factor"));
            header.add(col4.reg("Fraction"));
            rows.add(header);

            for (Chrono c : all) {
                List<String> row=new ArrayList<>();
                row.add(col1.reg(c.name()));
                row.add(col2.reg(String.valueOf(c.seconds())));
                row.add(col3.reg(String.valueOf(c.nanos()*100.0/best)));
                row.add(col4.reg(String.valueOf(best*100.0/c.nanos())));
                rows.add(row);
            }
            for (List<String> row : rows) {
                for (String s : row) {
                    System.out.print(col1.format(s));
                    System.out.print("  ");
                }
                System.out.println();
            }
        }
    }
    private class Col{
        int min=0;
        public String reg(String s){
            int slength = s.length();
            if(min< slength){
                min= slength;
            }
            return s;
        }
        public String format(String s){
            StringBuilder sb=new StringBuilder(min);
            sb.append(s);
            while(sb.length()<min){
                sb.append(' ');
            }
            return sb.toString();
        }
    }
}
