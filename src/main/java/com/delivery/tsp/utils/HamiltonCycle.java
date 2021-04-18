package com.delivery.tsp.utils;

import com.delivery.tsp.dto.PointToPoint;
import com.delivery.tsp.dto.Result;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class HamiltonCycle {

    private static int V=0;
    // implementation of traveling
    // Salesman Problem
    public static List<Result> travllingSalesmanProblem(int graph[][], int s)
    {
        // store all vertex apart
        // from source vertex
        V=graph.length;
        List<Result> resultList=new ArrayList<>();

        ArrayList<Integer> vertex =
                new ArrayList<Integer>();

        for (int i = 0; i < V; i++)
            if (i != s)
                vertex.add(i);

        // store minimum weight
        // Hamiltonian Cycle.
        int min_path = Integer.MAX_VALUE;
        int count =0;
        do
        {
            count++;
            // store current Path weight(cost)
            int current_pathweight = 0;
            Result result=new Result();
            // compute current path weight
            int k = s;
            StringBuilder sb=new StringBuilder();
            String startPoint=String.valueOf(k);
            String endPoint="";
            int distanceBetweenPoint=0;
            int returnDistance=0;
            for (int i = 0;	 i < vertex.size(); i++){
                current_pathweight +=graph[k][vertex.get(i)];
                distanceBetweenPoint=graph[k][vertex.get(i)];
                startPoint=String.valueOf(k);
                sb.append("["+k+"]=>" + graph[k][vertex.get(i)] + "km ");
                k = vertex.get(i);
                endPoint=String.valueOf(k);
                result.getPointList().add(new PointToPoint(startPoint,endPoint,distanceBetweenPoint));
            }
            current_pathweight += graph[k][s];
            returnDistance=graph[k][s];
            result.setTotalMesure(current_pathweight);
            result.setReturnDistance(returnDistance);
            resultList.add(result);

            sb.append("["+k+"]" + " returnPath: " + graph[k][s] + " | Distance: " + current_pathweight + " km");
            //resultDistancePairList.add(new ResultDistancePair(sb.toString(),current_pathweight));
            //Collections.sort(resultDistancePairList,(o1, o2) -> o1.getDistance()- o2.getDistance());
            System.out.println(sb.toString());
            //System.out.println(resultDistancePairList);
            // update minimum
            min_path = Math.min(min_path,
                    current_pathweight);

        } while (findNextPermutation(vertex));
        System.out.println("count: " + count);
        System.out.println("min path = " + min_path);
        //return min_path;
        Collections.sort(resultList,(o1, o2) -> o1.getTotalMesure()- o2.getTotalMesure());
        return resultList;
    }

    // Function to swap the data
    // present in the left and right indices
    public static ArrayList<Integer> swap(
            ArrayList<Integer> data,
            int left, int right)
    {
        // Swap the data
        int temp = data.get(left);
        data.set(left, data.get(right));
        data.set(right, temp);

        // Return the updated array
        return data;
    }

    // Function to reverse the sub-array
// starting from left to the right
// both inclusive
    public static ArrayList<Integer> reverse(
            ArrayList<Integer> data,
            int left, int right)
    {
        // Reverse the sub-array
        while (left < right)
        {
            int temp = data.get(left);
            data.set(left++,
                    data.get(right));
            data.set(right--, temp);
        }

        // Return the updated array
        return data;
    }

    // Function to find the next permutation
    // of the given integer array
    public static boolean findNextPermutation(
            ArrayList<Integer> data)
    {
        // If the given dataset is empty
        // or contains only one element
        // next_permutation is not possible
        if (data.size() <= 1)
            return false;

        int last = data.size() - 2;

        // find the longest non-increasing
        // suffix and find the pivot
        while (last >= 0)
        {
            if (data.get(last) <
                    data.get(last + 1))
            {
                break;
            }
            last--;
        }

        // If there is no increasing pair
        // there is no higher order permutation
        if (last < 0)
            return false;

        int nextGreater = data.size() - 1;

        // Find the rightmost successor
        // to the pivot
        for (int i = data.size() - 1;
             i > last; i--) {
            if (data.get(i) >
                    data.get(last))
            {
                nextGreater = i;
                break;
            }
        }

        // Swap the successor and
        // the pivot
        data = swap(data,	nextGreater, last);

        // Reverse the suffix
        data = reverse(data, last + 1,data.size() - 1);

        // Return true as the
        // next_permutation is done
        return true;
    }
}
