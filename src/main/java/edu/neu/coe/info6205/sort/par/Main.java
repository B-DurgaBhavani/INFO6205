package edu.neu.coe.info6205.sort.par;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ForkJoinPool;

/**
 * This code has been fleshed out by Ziyao Qiao. Thanks very much.
 * TODO tidy it up a bit.
 */
public class Main {

    public static void main(String[] args) {
        processArgs(args);
        int maxThreads = configuration.get("-P");
        while(maxThreads >= 1) {
            ForkJoinPool myPool = new ForkJoinPool(maxThreads);
//            System.out.println(myPool.getParallelism());
//            System.out.println("Degree of parallelism: " + maxThreads);
            Random random = new Random();
            int datasize = 1000000;
            while(datasize >= 100) {

                int[] array = new int[datasize];
                ArrayList<Long> timeList = new ArrayList<>();
                for (int j = 50; j < 100; j++) {
                    ParSort.cutoff = (datasize/100) * (j + 1);
                    // for (int i = 0; i < array.length; i++) array[i] = random.nextInt(10000000);
                    long time;
                    long startTime = System.currentTimeMillis();
                    for (int t = 0; t < 10; t++) {
                        for (int i = 0; i < array.length; i++) array[i] = random.nextInt(10000000);
                        if (ParSort.cutoff > array.length)
                            Arrays.sort(array);
                        else
                            ParSort.sort(array, 0, array.length, myPool);
                    }
                    long endTime = System.currentTimeMillis();
                    time = (endTime - startTime);
                    timeList.add(time);


                    //                System.out.println("cutoff：" + (ParSort.cutoff) + "\t\t10times Time:" + time + "ms");
                    System.out.println(datasize+"\t"+(ParSort.cutoff) + "\t10times Time:\t" + time + "\t" + maxThreads);

                }
                try {
                    FileOutputStream fis = new FileOutputStream(".\\INFO6205\\src\\result.csv");
                    OutputStreamWriter isr = new OutputStreamWriter(fis);
                    BufferedWriter bw = new BufferedWriter(isr);
                    int j = 0;
                    for (long i : timeList) {
                        String content = (double) 10000 * (j + 1) / 2000000 + "," + (double) i / 10 + "\n";
                        j++;
                        bw.write(content);
                        bw.flush();
                    }
                    bw.close();

                } catch (IOException e) {
                    e.printStackTrace();
                }
                datasize/=100;
            }
            maxThreads/=2;
        }
    }

    private static void processArgs(String[] args) {
        String[] xs = args;
        while (xs.length > 0)
            if (xs[0].startsWith("-")) xs = processArg(xs);
    }

    private static String[] processArg(String[] xs) {
        String[] result = new String[0];
        System.arraycopy(xs, 2, result, 0, xs.length - 2);
        processCommand(xs[0], xs[1]);
        return result;
    }

    private static void processCommand(String x, String y) {
        System.out.println("process Command");
        if (x.equalsIgnoreCase("N")) setConfig(x, Integer.parseInt(y));
        else
            // TODO sort this out
            if (x.equalsIgnoreCase("-P")) { //noinspection ResultOfMethodCallIgnored
                System.out.println("x = p");

                setConfig(x, Integer.parseInt(y));
            }
    }

    private static void setConfig(String x, int i) {
        configuration.put(x, i);
    }

    @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
    private static final Map<String, Integer> configuration = new HashMap<>();


}
