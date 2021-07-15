package com.myfeeds.dynamodb;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;


public class ShopifyConverter {
    
    static int sizeCol = 2;

    static Map<Integer,String> defaultsMap = new HashMap<Integer,String>();

    public static void main(String[] args) throws IOException
    {


        defaultsMap.put(7, "Size");
        defaultsMap.put(17, "deny");
        defaultsMap.put(18, "manual");
        defaultsMap.put(14, "0");

        FileInputStream inputStream = new FileInputStream(new File("/Users/karolos/code/clouddevudacity/lam/capstone/myvoice/src/main/java/com/myfeeds/dynamodb/input.txt"));

        byte[] bytes = inputStream.readAllBytes();

        String[] lines = new String(bytes).split("\n");



        String[] shopifyColumns = getShopifyCols();
        
        for (int i = 0; i < shopifyColumns.length; i++) {
            
            System.out.println(shopifyColumns[i]+"\t"+i);
        }
        
        
        

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < shopifyColumns.length-1; i++) {
            
            sb.append(shopifyColumns[i]).append(",");
        }
        sb.append(shopifyColumns[shopifyColumns.length-1]).append("\n");


        for ( int i = 1; i < lines.length; i++) {

            System.out.println(lines[i]);

            String[] lineComps = lines[i].split("\t");
            String sizes = lineComps[sizeCol];
          
        
            Map<String, Long> counts =
            Arrays.asList(sizes.split(",")).stream().collect(Collectors.groupingBy(e -> e, Collectors.counting()));

            for ( String size : counts.keySet())
            {
                    for (int j = 0; j < shopifyColumns.length-1; j++) {
                        sb.append(whatToAppend(j,lineComps ,size , counts.get(size))).append(",");
                    }

                    sb.append("active").append("\n");
            }
        }


        System.out.println(sb.toString());
      
        FileOutputStream fos = new FileOutputStream(new File("/Users/karolos/code/clouddevudacity/lam/capstone/myvoice/src/main/java/com/myfeeds/dynamodb/output.csv"));

        fos.write(sb.toString().getBytes());
        fos.close();

    }

    private static String whatToAppend(int j, String[] lineComps, String size, long sizeCount) {

       
        if(j == 8 )
        {
           
                return size;
           
        }


        if(j == 19)
        {
            return String.valueOf(Math.ceil(Double.parseDouble(lineComps[4])*2.0));
        }

        if(j == 16)
        {
            if ( size.equalsIgnoreCase("One size"))
                return lineComps[5];
            else
                return String.valueOf(sizeCount);
        }

        if( j == 0 )
        {
            return (lineComps[0]+lineComps[3]).replaceAll(" ", "");
        }



        if( j == 1 )
        {
            return (lineComps[3]);
        }

        if( j == 3 )
        {
            return (lineComps[0]);
        }

        if( j == 46 )
        {
            return (lineComps[4]);
        }

        if( defaultsMap.containsKey(j))
          return defaultsMap.get(j);

        return "";
    }

    private static String[] getShopifyCols() throws IOException
    {
        FileInputStream inputStream = new FileInputStream(new File("/Users/karolos/code/clouddevudacity/lam/capstone/myvoice/src/main/java/com/myfeeds/dynamodb/targetformat"));

        byte[] bytes = inputStream.readAllBytes();

        String[] lines = new String(bytes).split("\n")[0].split("\t");
    
        return lines;
    }
}
