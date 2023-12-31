package com.vamco.java.jzq.AI;

import java.awt.*;
import java.util.*;

public class BFS {
    public static int[] ai(int[][] input,int step){
        //0 =
        //1 = X (PC)
        //2 = O (Player)
        long st = System.currentTimeMillis();
        int stepAll = 0;
        //any one has a struct like this {table,step, topStep}
        Map<Integer,Object[]> thisStep = new HashMap<>();
        Map<Integer,Object[]> newStep = new HashMap<>();
        Map<Integer,Object[]> allEnd = new HashMap<>(); //There is a struct as this {table,topStep}
        Set<Integer> fl = new HashSet<>();
        thisStep.put(0,new Object[]{input,step,0,false});
        boolean top = true;
        for (int a = 0;a < 8;a++) {
            for (int i = 0; i < thisStep.size(); i++) {
                Object[] p = thisStep.get(i);
                int[][] table = (int[][]) p[0];
                int player = (int) p[1];
                for (int x = 0; x < 3; x++) {
                    for (int y = 0; y < 3; y++) {
                        stepAll++;
                        if (table[x][y] == 0) {
                            int[][] newTable = new int[3][3];
                            for (int ax = 0;ax < 3;ax++){
                                for (int ay = 0;ay < 3;ay++){
                                    newTable[ax][ay] = table[ax][ay];
                                }
                            }
                            newTable[x][y] = player;
                            int who = isWin(newTable);
                            if (!(who == 0)){
                                if (who == 1){
                                    allEnd.put(-(10 - a),new Object[]{newTable,new int[]{x,y}});
                                    fl.add((10 - a));
                                }else{
                                    allEnd.put((10 - a),new Object[]{newTable,new int[]{x,y}});
                                    fl.add(-(10 - a));
                                }
                                break;
                            }
                            if (top) {
                                if (player == 1) {
                                    newStep.put(newStep.size(), new Object[]{newTable, 2, new int[]{x,y}});
                                } else {
                                    newStep.put(newStep.size(), new Object[]{newTable, 1, new int[]{x,y}});
                                }
                            }else{
                                if (player == 1) {
                                    newStep.put(newStep.size(), new Object[]{newTable, 2, p[2]});
                                } else {
                                    newStep.put(newStep.size(), new Object[]{newTable, 1, p[2]});
                                }
                            }
                        }
                    }
                }
                if (top){
                    top = false;
                }
            }
            thisStep = (Map<Integer, Object[]>) ((HashMap<Integer, Object[]>) newStep).clone();
            newStep.clear();
        }

        if (!(allEnd.isEmpty())) {
            Set<Integer> ml = allEnd.keySet();
            int m = Collections.max(ml);
            Object[] best = allEnd.get(m);
            int[] p = (int[]) best[1];
            long et = System.currentTimeMillis();
            System.out.println("Use time:" + (et - st) + "ms");
            System.out.println("Search tree:" + stepAll);
            System.out.println("The score of best choice:" + m);
            System.out.println("The best good choice:" + Arrays.toString(p));
            return (int[]) best[1];
        }else{
            return null;
        }
    }

    public static int isWin(int[][] t){
        int p = 0;
        for (int x = 0;x < 3;x++){
            if (t[x][0] == 1 && t[x][1] == 1 && t[x][2] == 1){
                p = 1;
            }else if(t[x][0] == 2 && t[x][1] == 2 && t[x][2] == 2){
                p = 2;
            }
        }

        for (int y = 0;y < 3;y++){
            if (t[0][y] == 1 && t[1][y] == 1 && t[2][y] == 1){
                p = 1;
            }else if(t[0][y] == 2 && t[1][y] == 2 && t[2][y] == 2){
                p = 2;
            }
        }

        if (t[0][0] == 1 && t[1][1] == 1 && t[2][2] == 1){
            p = 1;
        }else if(t[0][0] == 2 && t[1][1] == 2 && t[2][2] == 2){
            p = 2;
        }

        if (t[0][2] == 1 && t[1][1] == 1 && t[2][0] == 1){
            p = 1;
        }else if(t[0][2] == 2 && t[1][1] == 2 && t[2][0] == 2){
            p = 2;
        }
        return p;
    }

    public static void displayTable(int[][] m){
        for (int x = 0;x < 3;x++){
            for (int y = 0;y < 3;y++){
                if (m[x][y] == 1){
                    System.out.print("X");
                }else if(m[x][y] == 2){
                    System.out.print("O");
                }else{
                    System.out.print(" ");
                }
                System.out.print("|");
            }
            System.out.println();
        }
    }
    public static void main(String[] args) {
        //1 = PC
        //2 = Player
        Scanner get = new Scanner(System.in);
        int[][] m = new int[][]{
                {0,0,0,0},
                {0,0,0,0},
                {0,0,0,0}
        };
        while (true){
            System.out.print("Please input choice:");
            int x = get.nextInt();
            int y = get.nextInt();
            m[x][y] = 2;
            int[] ret = ai(m, 1);
            if (!(ret == null)) {
                m[ret[0]][ret[1]] = 1;
            }else{
                System.out.println("Okay,you are win!");
                break;
            }
            displayTable(m);
        }
    }
}
