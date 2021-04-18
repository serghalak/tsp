package com.delivery.tsp.service.impl;

import com.delivery.tsp.dto.CourierDto;
import com.delivery.tsp.dto.PointToPoint;
import com.delivery.tsp.dto.Result;
import com.delivery.tsp.model.DeliveryPoint;
import com.delivery.tsp.repository.DeliveryPointRepository;
import com.delivery.tsp.service.DeliveryPointService;
import com.delivery.tsp.utils.Distance;
import com.delivery.tsp.utils.HamiltonCycle;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

import java.util.List;

@Service
public class DeliveryPointServiceImpl implements DeliveryPointService {

    private static final int KILOMETER_TO_METER=1000;
    private static final double KM_HOUR_TO_M_SEC=3.6;
    private static final int MITUTE_TO_SECOND=60;

    private DeliveryPointRepository deliveryPointRepository;

    public DeliveryPointServiceImpl(DeliveryPointRepository deliveryPointRepository) {
        this.deliveryPointRepository = deliveryPointRepository;
    }

    @Override
    public Iterable<DeliveryPoint> getAllDeliveryPoint() {
        return deliveryPointRepository.findAll();
    }

    @Override
    public String getMinPath(CourierDto courierDto) {
        Iterable<DeliveryPoint> allDeliveryPoint= deliveryPointRepository.findAll();
        List<DeliveryPoint> deliveryPointList=new ArrayList<>();
        allDeliveryPoint.forEach(deliveryPointList::add);

        //validate data
        validateTime(deliveryPointList,courierDto);
        validateDistance(deliveryPointList,courierDto);

        int[][] graph=createMatrixDistance(courierDto,deliveryPointList);

        List<Result> resultList = HamiltonCycle.travllingSalesmanProblem(graph, 0);


        int speed = courierDto.getSpeed();
        int[] spendTime = courierDto.getSpendTime();
        int totalWorkTimeInSeconds=(int)courierDto.getBeginWorkDay()
                .until(courierDto.getEndWorkDay(),ChronoUnit.SECONDS);
        int secondReturnBack=0;

        StringBuilder resultString=null;
        String minPath=null;
        String minWait=null;
        String minWork=null;

        int minTimeWait=Integer.MAX_VALUE;
        int minPathLong=Integer.MAX_VALUE;
        //int leftTimeSec=(int)courierDto.getBeginWorkDay().until(courierDto.getEndWorkDay(),ChronoUnit.SECONDS);

        LocalTime minTimeEndWork=LocalTime.MAX;

        for (Result result: resultList ) {
            resultString=new StringBuilder();
            LocalTime timeDeparture=null;
            LocalTime timeArrived=null;
            int totalTime=0;
            int timeWait=0;
            int timeDrive=0;


            boolean isExit=false;

            List<PointToPoint> pointList = result.getPointList();
            PointToPoint pointToPoint=null;

            for (int i=0;i<pointList.size();i++ ) {
                pointToPoint=pointList.get(i);
                if(i==0){
                    appendStartPoint(resultString,pointToPoint.getStartPoint());
                    timeDeparture=courierDto.getBeginWorkDay();
                }

                appendEndPoint(resultString,pointToPoint.getEndPoint());

                totalTime += (pointToPoint.getMesure()*KILOMETER_TO_METER)/(speed/KM_HOUR_TO_M_SEC);
                timeDrive +=(pointToPoint.getMesure()*KILOMETER_TO_METER)/(speed/KM_HOUR_TO_M_SEC);

                //leftTimeSec -=totalTime;

                //System.out.println("---->totalTime: " + totalTime);
                LocalTime timeOnPoint = timeDeparture.plusSeconds(totalTime);
                //spendTime[i]*60;
                if(deliveryPointList.get(i).getDeliveryTo().isBefore(timeOnPoint)){

                    appendLostDelivery(resultString
                            ,deliveryPointList.get(i).getId()
                            ,timeOnPoint,deliveryPointList.get(i).getDeliveryTo());

                    System.out.println(resultString.toString());
                    isExit=true;
                    break;
                }else {
                    if(timeOnPoint.isBefore(deliveryPointList.get(i).getDeliveryFrom())){

                        timeWait +=timeOnPoint.until(deliveryPointList.get(i).getDeliveryFrom(), ChronoUnit.SECONDS);
                        totalTime += timeOnPoint.until(deliveryPointList.get(i).getDeliveryFrom(), ChronoUnit.SECONDS);;
                    }
                    timeWait +=spendTime[i]*MITUTE_TO_SECOND;
                    totalTime += spendTime[i]*MITUTE_TO_SECOND;
                }

            }

            if (isExit){
                resultString.setLength(0);
                continue;
            }


            //return back
            secondReturnBack=secondReturnBack(result,speed);
            totalTime += secondReturnBack;
            timeDrive += secondReturnBack;
            LocalTime timeOnOffice = timeDeparture.plusSeconds(totalTime);

            if(timeOnOffice.isAfter(courierDto.getEndWorkDay())){

                appendLostOffice(resultString,timeOnOffice,courierDto.getEndWorkDay());

                System.out.println(resultString.toString());
                continue;
            }

            appendRestResult(resultString,result.getTotalMesure(),totalTime,timeWait,timeOnOffice,timeDrive);

            //System.out.println("ResultString: " + resultString.toString());
            //System.out.println("---->"+result);
            //break;
            if(minPathLong>result.getTotalMesure()){
                minPathLong=result.getTotalMesure();
                minPath=resultString.toString();
            }

            if(minTimeWait>timeWait){
                minTimeWait=timeWait;
                minWait=resultString.toString();
            }

            if(minTimeEndWork.isAfter(timeOnOffice)){
                minTimeEndWork=timeOnOffice;
                minWork=resultString.toString();
            }

        }
//        System.out.println("ResultString min path: " + minPath);
//        System.out.println("ResultString min wait " + minWait);
//        System.out.println("ResultString min end of work " + minWork);



        return  "\n\n" + "====================RESULT======================" +"\n"
               + minPath
                + "\n\n" + "-----------------------------------------------" +"\n"
                +minWait
                + "\n\n"+ "-----------------------------------------------"  +"\n"
                +minWork
                + "\n\n"+ "-----------------------------------------------";

    }




    private static void appendRestResult(StringBuilder resultString
            , int totalMesure
            ,int totalTime
            , int timeWait
            , LocalTime timeOnOffice
            , int timeDrive){

        resultString.append(" | Distance: ");
        resultString.append(totalMesure);
        resultString.append("km. Time on the road: ");
        resultString.append(Distance.secondsToHours(totalTime));
        resultString.append(". Time waiting: ");
        resultString.append(Distance.secondsToHours(timeWait));
        resultString.append(". Work finished ");
        resultString.append(timeOnOffice);
        resultString.append(" Time drive " + Distance.secondsToHours(timeDrive));
    }

    private static void appendLostOffice(StringBuilder resultString
            ,LocalTime timeOnOffice
            ,LocalTime timeEndWorkDay){
        resultString.append(" we arrived ");
        resultString.append(timeOnOffice);
        resultString.append(" instead ");
        resultString.append(timeEndWorkDay);
    }

    private static void appendLostDelivery(StringBuilder resultString
            , int point
            ,LocalTime timeOnPoint
            ,LocalTime timeDeliveryTo){

        resultString.append(" we arrived to point: ");
        resultString.append(" => [");
        resultString.append(point);
        resultString.append("] ");
        resultString.append(timeOnPoint);
        resultString.append(" instead ");
        resultString.append(timeDeliveryTo);
    }

    private static void appendStartPoint(StringBuilder resultString, String startPoint){
        resultString.append("[");
        resultString.append(startPoint);
        resultString.append("]");
    }
    private static void appendEndPoint(StringBuilder resultString,String endPoint){
        resultString.append(" => ");
        resultString.append("[");
        resultString.append(endPoint);
        resultString.append("]");
    }

    private static int secondReturnBack(Result result,int speed){
        return (int) ((result.getReturnDistance()*KILOMETER_TO_METER)/(speed/KM_HOUR_TO_M_SEC));
    }

    private static void validateTime(List<DeliveryPoint> deliveryPointList
            , CourierDto courierDto){

        LocalTime timeEndWorkDay=courierDto.getEndWorkDay();
        LocalTime timeBeginWorkDay=courierDto.getBeginWorkDay();

        for (DeliveryPoint deliveryPoint:deliveryPointList) {
            if(timeEndWorkDay.isBefore(deliveryPoint.getDeliveryFrom())){
                throw new RuntimeException("Work time courier finished before delivery from time");
            }
            if(timeBeginWorkDay.isAfter(deliveryPoint.getDeliveryTo())){
                throw new RuntimeException("Work time courier started after delivery to time");
            }


        }
    }

    private static void validateDistance(List<DeliveryPoint> deliveryPointList
            , CourierDto courierDto){
        long workTime=courierDto.getBeginWorkDay().until(courierDto.getEndWorkDay(),ChronoUnit.SECONDS);
        for (DeliveryPoint deliveryPoint:deliveryPointList) {

            int distanceBetweenPoints=Distance.distFrom(
                    courierDto.getLotitude()
                    ,courierDto.getLongitude()
                    ,deliveryPoint.getLatitude()
                    ,deliveryPoint.getLongitude());
            int timeSecondsForDistance= (int) (distanceBetweenPoints*KILOMETER_TO_METER/(courierDto.getSpeed()/KM_HOUR_TO_M_SEC));
            int secondsWorkCourier = (int) courierDto.getBeginWorkDay()
                    .until(courierDto.getEndWorkDay(),ChronoUnit.SECONDS);

            if(timeSecondsForDistance*2>secondsWorkCourier){
                throw new RuntimeException("Distance to [" + deliveryPoint.getId()+"] is too much!!!");
            }
        }
    }


    private static int[][] createMatrixDistance(CourierDto courierDto,List<DeliveryPoint> deliveryPointList){

        int size = deliveryPointList.size();
        int[][]graph=new int[size+1][size+1];

        for(int i=0;i<=size;i++){
            for (int j=0;j<=size;j++){
                graph[i][j]= Distance.distFrom(
                        (i==0) ? courierDto.getLotitude() : deliveryPointList.get(i-1).getLatitude()
                        ,(i==0) ? courierDto.getLongitude() : deliveryPointList.get(i-1).getLongitude()
                        ,(j==0) ? courierDto.getLotitude() : deliveryPointList.get(j-1).getLatitude()
                        ,(j==0) ? courierDto.getLongitude() : deliveryPointList.get(j-1).getLongitude());
            }
        }
        return graph;
    }

    private static int[][] createMatrixWaiting(CourierDto courierDto,List<DeliveryPoint> deliveryPointList){
        return null;
    }

    @Override
    public String getMinWaitTime(CourierDto courierDto) {
        return null;
    }

    @Override
    public String getMinWorkTime(CourierDto courierDto) {
        return null;
    }
}
